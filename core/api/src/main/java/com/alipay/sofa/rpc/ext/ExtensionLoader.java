package com.alipay.sofa.rpc.ext;

import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;
import com.alipay.sofa.rpc.common.utils.*;
import com.alipay.sofa.rpc.core.exception.SofaRpcRuntimeException;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ExtensionLoader<T> {

    /**
     * slf4j Logger for this class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ExtensionLoader.class);

    private final static String SUFFIX = ".properties";

    private final Class<T> interfaceClass;

    private final String interfaceName;

    private final Extensible extensible;

    private final Cache<String, T> factory;

    /**
     * 加载监听器
     */
    protected final ExtensionLoaderListener<T> listener;


    private final Cache<String, ExtensionClass<T>> allExtensions;

    public ExtensionLoader(Class<T> interfaceClass) {
        this(interfaceClass, true, null);
    }

    public ExtensionLoader(Class<T> interfaceClass, ExtensionLoaderListener<T> listener) {
        this(interfaceClass, true, listener);
    }

    public ExtensionLoader(Class<T> interfaceClass, boolean autoLoad, ExtensionLoaderListener<T> listener) {

        if (null == interfaceClass || !(interfaceClass.isInterface() || Modifier.isAbstract(interfaceClass.getModifiers()))) {
            throw new IllegalArgumentException("Extensible class must be interface or abstract class!");
        }

        this.interfaceClass = interfaceClass;
        this.interfaceName = ClassTypeUtils.getTypeStr(this.interfaceClass);
        this.listener = listener;

        Extensible extensible = interfaceClass.getAnnotation(Extensible.class);

        if (null == extensible) {
            throw new IllegalArgumentException("Error when load extensible interface " + interfaceName + ", must add annotation @Extensible.");
        }
        this.extensible = extensible;
        this.factory = extensible.singleton() ? CacheBuilder.newBuilder().build() : null;
        this.allExtensions = CacheBuilder.newBuilder().build();

        if (autoLoad) {
            List<String> paths = RpcConfigs.getOrDefaultValue(RpcOptions.EXTENSION_LOAD_PATH, Lists.newArrayList());
            for (String path : paths) {
                loadFromFile(path);
            }
        }
    }

    private void loadFromFile(String path) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Loading extension of extensible {} from path: {}", interfaceName, path);
        }

        String file = StringUtils.isBlank(extensible.file()) ? interfaceName : extensible.file().trim();
        String fullFileName = path + file + SUFFIX;

        try {
            ClassLoader classLoader = ClassLoaderUtils.getClassLoader(getClass());
            loadFromClassLoader(classLoader, fullFileName);
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Failed to load extension of extensible " + interfaceName + " from path:" + fullFileName, e);
            }
        }

    }

    private void loadFromClassLoader(ClassLoader classLoader, String path) throws IOException {
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            Properties properties = FileUtils.readUrl2Properties(resource, UTF_8);
            for (Object keyObj : properties.keySet()) {
                String key = String.valueOf(keyObj);
                String value = properties.getProperty(key);
                parsePropertiesCfg(resource, key, value);
            }
        }
    }

    private void parsePropertiesCfg(URL fileUrl, String alias, String className) {
        if (StringUtils.isEmpty(alias) || StringUtils.isEmpty(className)) {
            return;
        }
        Class tmp;
        try {
            tmp = ClassUtils.forName(className, false);
        } catch (Exception e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Extension {} of extensible {} is disabled, cause by: {}", className, interfaceName, ExceptionUtils.toShortString(e, 2));
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Extension {} of extensible {} is disabled.", className, interfaceClass, e);
            }
            return;
        }

        if (!interfaceClass.isAssignableFrom(tmp)) {
            throw new IllegalArgumentException("Error when load extension of extensible " + interfaceName + " from file:" + fileUrl + ", " + className + " is not subtype of interface.");
        }

        Class<? extends T> implClass = (Class<? extends T>) tmp;
        Extension extension = implClass.getAnnotation(Extension.class);
        if (extension == null) {
            throw new IllegalArgumentException("Error when load extension of extensible " + interfaceName +
                    " from file:" + fileUrl + ", " + className + " must add annotation @Extension.");
        } else {
            String aliasInCode = extension.value();
            if (StringUtils.isBlank(aliasInCode)) {
                // 扩展实现类未配置@Extension 标签
                throw new IllegalArgumentException("Error when load extension of extensible " + interfaceClass +
                        " from file:" + fileUrl + ", " + className + "'s alias of @Extension is blank");
            }
            if (alias == null) {
                // spi文件里没配置，用代码里的
                alias = aliasInCode;
            } else {
                // spi文件里配置的和代码里的不一致
                if (!aliasInCode.equals(alias)) {
                    throw new IllegalArgumentException("Error when load extension of extensible " + interfaceName +
                            " from file:" + fileUrl + ", aliases of " + className + " are " +
                            "not equal between " + aliasInCode + "(code) and " + alias + "(file).");
                }
            }
            // 接口需要编号，实现类没设置
            if (extensible.coded() && extension.code() < 0) {
                throw new IllegalArgumentException("Error when load extension of extensible " + interfaceName +
                        " from file:" + fileUrl + ", code of @Extension must >=0 at " + className + ".");
            }
        }

        // 不可以是default和*
        if (StringUtils.DEFAULT.equals(alias) || StringUtils.ALL.equals(alias)) {
            throw new IllegalArgumentException("Error when load extension of extensible " + interfaceName +
                    " from file:" + fileUrl + ", alias of @Extension must not \"default\" and \"*\" at " + className + ".");
        }

        //检查是否存在
        ExtensionClass<T> old = allExtensions.getIfPresent(alias);
        ExtensionClass<T> extensionClass = null;
        //存在
        if (null != old) {
            if (extension.override()) {
                if (extension.order() < old.getOrder()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Extension of extensible {} with alias {} override from {} to {} failure, " +
                                        "cause by: order of old extension is higher",
                                interfaceName, alias, old.getClazz(), implClass);
                    }
                } else {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("Extension of extensible {} with alias {}: {} has been override to {}",
                                interfaceName, alias, old.getClazz(), implClass);
                    }
                    // 如果当前扩展可以覆盖其它同名扩展
                    extensionClass = buildClass(extension, implClass, alias);
                }
            } else {
                if (old.isOverride() && old.getOrder() >= extension.order()) {
                    // 如果已加载覆盖扩展，再加载到原始扩展
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("Extension of extensible {} with alias {}: {} has been loaded, ignore origin {}",
                                interfaceName, alias, old.getClazz(), implClass);
                    }
                } else {
                    // 如果不能被覆盖，抛出已存在异常
                    throw new IllegalStateException(
                            "Error when load extension of extensible " + interfaceClass + " from file:" + fileUrl +
                                    ", Duplicate class with same alias: " + alias + ", " + old.getClazz() + " and " + implClass);
                }
            }
        } else {
            extensionClass = buildClass(extension, implClass, alias);
        }

        if (null != extensionClass) {

            //todo 检查互斥点
            loadSuccess(alias, extensionClass);
        }
    }

    private void loadSuccess(String alias, ExtensionClass<T> extensionClass) {
        if (null != listener) {
            try {
                listener.onLoad(extensionClass);
            } catch (Exception e) {
                LOGGER.error("Error when load extension of extensible " + interfaceClass + " with alias: " + alias + ".", e);
            }
        }
        allExtensions.put(alias, extensionClass);
    }

    private ExtensionClass buildClass(Extension extension, Class<? extends T> implClass, String alias) {
        ExtensionClass extensionClass = new ExtensionClass(implClass, alias);
        extensionClass.setCode(extension.code());
        extensionClass.setOrder(extension.order());
        extensionClass.setOverride(extension.override());
        extensionClass.setRejection(extension.rejection());
        extensionClass.setSingleton(extensible.singleton());
        return extensionClass;
    }

    public T getExtension(String alias, Class[] argTypes, Object[] args) {
        ExtensionClass<T> extensionClass = allExtensions.getIfPresent(alias);
        if (null == extensionClass) {
            throw new SofaRpcRuntimeException("Not found extension of " + interfaceName + " named: \"" + alias + "\"!");
        }
        if (extensionClass.isSingleton() && null != factory) {
            T t = factory.getIfPresent(alias);
            if (null == t) {
                t = extensionClass.getExtInstance(argTypes, args);
                factory.put(alias, t);

            }
            return t;
        }
        return extensionClass.getExtInstance(argTypes, args);
    }

    public T getExtension(String alias) {
        ExtensionClass<T> extensionClass = allExtensions.getIfPresent(alias);
        if (null == extensionClass) {
            throw new SofaRpcRuntimeException("Not found extension of " + interfaceName + " named: \"" + alias + "\"!");
        }
        if (extensionClass.isSingleton() && null != factory) {
            T t = factory.getIfPresent(alias);
            if (null == t) {
                t = extensionClass.getExtInstance();
                factory.put(alias, t);
            }
            return t;
        }
        return extensionClass.getExtInstance();
    }
}
