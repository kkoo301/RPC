package com.alipay.sofa.rpc.ext;

import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;
import com.alipay.sofa.rpc.common.utils.ClassLoaderUtils;
import com.alipay.sofa.rpc.common.utils.ClassTypeUtils;
import com.alipay.sofa.rpc.common.utils.ClassUtils;
import com.alipay.sofa.rpc.common.utils.FileUtils;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import java.io.IOException;
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

    private final Cache<String, ExtensionClass<T>> ALL_EXTENSION = CacheBuilder.newBuilder().build();

    public <T> ExtensionLoader(Class<T> clazz) {
        this(clazz, true, null);
    }

    public <T> ExtensionLoader(Class<T> clazz, ExtensionLoaderListener<T> listener) {
        this(clazz, true, listener);
    }

    public <T> ExtensionLoader(Class<T> clazz, boolean autoLoad, ExtensionLoaderListener<T> listener) {

        String typeStr = ClassTypeUtils.getTypeStr(clazz);
        System.out.println(typeStr);

        Extensible extensible = clazz.getAnnotation(Extensible.class);

        String file = extensible.file();

        List<String> paths = RpcConfigs.getOrDefaultValue(RpcOptions.EXTENSION_LOAD_PATH, Lists.newArrayList());
        for (String path : paths) {
            loadFromFile(path + typeStr + ".properties");
        }
    }

    private void loadFromFile(String path) {
        try {
            ClassLoader classLoader = ClassLoaderUtils.getClassLoader(getClass());
            loadFromClassLoader(classLoader, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadFromClassLoader(ClassLoader classLoader, String path) throws IOException {
        Enumeration<URL> resources = classLoader.getResources(path);
        while (null != resources && resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            Properties properties = FileUtils.readUrl2Properties(resource, UTF_8);
            for (Object keyObj : properties.keySet()) {
                String key = String.valueOf(keyObj);
                String value = properties.getProperty(key);
                parsePropertiesCfg(key, value);
            }
        }
    }

    private void parsePropertiesCfg(String alias, String className) {
        Class tmp = ClassUtils.forName(className, false);
        Class<? extends T> implClass = (Class<? extends T>) tmp;
        Extension extension = implClass.getAnnotation(Extension.class);
        ExtensionClass extensionClass = buildClass(extension, implClass);
        ALL_EXTENSION.put(alias, extensionClass);
    }

    private ExtensionClass buildClass(Extension extension, Class<? extends T> implClass) {
        ExtensionClass extensionClass = new ExtensionClass(implClass, extension.value());
        extensionClass.setCode(extension.code());
        extensionClass.setOrder(extension.order());
        extensionClass.setOverride(extension.override());
        extensionClass.setRejection(extension.rejection());
        return extensionClass;
    }


    public T getExtension(String alias, Class[] argTypes, Object[] args) {
        ExtensionClass<T> extensionClass = ALL_EXTENSION.getIfPresent(alias);
        return extensionClass.getExtInstance(argTypes, args);
    }
}
