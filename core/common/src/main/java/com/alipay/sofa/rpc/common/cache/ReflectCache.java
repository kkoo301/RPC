package com.alipay.sofa.rpc.common.cache;

import com.alipay.sofa.rpc.common.utils.ClassLoaderUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Map;
import java.util.WeakHashMap;

public final class ReflectCache {

    /**
     * 应用对应的ClassLoader
     */
    private static final Cache<String, ClassLoader> APPNAME_CLASSLOAD_MAP = CacheBuilder.newBuilder().build();

    /**
     * 服务对应的ClassLoader
     */
    private static final Cache<String, ClassLoader> SERVICE_CLASSLOAD_MAP = CacheBuilder.newBuilder().build();

    /**
     * 注册APP的ClassLoader
     *
     * @param appUniqueName
     * @param classLoader
     */
    public static void registerAppClassLoader(String appUniqueName, ClassLoader classLoader) {
        APPNAME_CLASSLOAD_MAP.put(appUniqueName, classLoader);
    }

    /**
     * 注册APP的ClassLoader
     *
     * @param appUniqueName
     */
    public static void unRegisterAppClassLoader(String appUniqueName) {
        APPNAME_CLASSLOAD_MAP.invalidate(appUniqueName);
    }

    /**
     * 获取APP的自定义ClassLoader
     *
     * @param appUniqueName
     * @return
     */
    public static ClassLoader getAppClassLoader(String appUniqueName) {
        ClassLoader classLoader = APPNAME_CLASSLOAD_MAP.getIfPresent(appUniqueName);
        if (null == classLoader) {
            classLoader = ClassLoaderUtils.getCurrentClassLoader();
        }
        return classLoader;
    }

    /**
     * 注册Service的ClassLoader
     *
     * @param serviceUniqueName
     * @param classLoader
     */
    public static void registerServiceClassLoader(String serviceUniqueName, ClassLoader classLoader) {
        SERVICE_CLASSLOAD_MAP.put(serviceUniqueName, classLoader);
    }

    /**
     * 反注册Service的ClassLoader
     *
     * @param serviceUniqueName
     */
    public static void unRegisterServiceClassLoader(String serviceUniqueName) {
        SERVICE_CLASSLOAD_MAP.invalidate(serviceUniqueName);
    }

    /**
     * 获取服务的自定义ClassLoader
     *
     * @param serviceUniqueName
     * @return
     */
    public static ClassLoader getServiceClassLoader(String serviceUniqueName) {
        ClassLoader classLoader = SERVICE_CLASSLOAD_MAP.getIfPresent(serviceUniqueName);
        if (null == classLoader) {
            classLoader = ClassLoaderUtils.getCurrentClassLoader();
        }
        return classLoader;
    }

    private static final Cache<String, Map<ClassLoader, Class>> CLASS_CACHE = CacheBuilder.newBuilder().build();

    public static void putClassCache(String typeStr, Class clazz) {
        Map<ClassLoader, Class> map = new WeakHashMap<>();
        map.put(clazz.getClassLoader(), clazz);
        CLASS_CACHE.put(typeStr, map);
    }

    public static Class getClassCache(String typeStr) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (null == contextClassLoader) {
            return null;
        }
        Map<ClassLoader, Class> loaderClassMap = CLASS_CACHE.getIfPresent(typeStr);
        return loaderClassMap == null ? null : loaderClassMap.get(contextClassLoader);
    }

    private static final Cache<Class, String> TYPE_STR_CACHE = CacheBuilder.newBuilder().build();

    public static String getTypeStrCache(Class clazz) {
        return TYPE_STR_CACHE.getIfPresent(clazz);
    }

    public static void putTypeStrCache(Class clazz, String typeStr) {
        TYPE_STR_CACHE.put(clazz, typeStr);
    }


    public static void cleanAll() {
        APPNAME_CLASSLOAD_MAP.cleanUp();
        SERVICE_CLASSLOAD_MAP.cleanUp();
        CLASS_CACHE.cleanUp();
        TYPE_STR_CACHE.cleanUp();
    }
}
