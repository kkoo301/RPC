package com.alipay.sofa.rpc.common.cache;

import com.alipay.sofa.rpc.common.utils.ClassLoaderUtils;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutionException;

public class ReflectCacheTest {


    public void tesAppClassLoader() throws ExecutionException {
        URLClassLoader cl1 = new URLClassLoader(new URL[0]);
        URLClassLoader cl2 = new URLClassLoader(new URL[0]);
        ReflectCache.registerAppClassLoader("aaa", cl1);
        ReflectCache.registerAppClassLoader("bbb", cl2);
        Assert.assertEquals(cl1, ReflectCache.getAppClassLoader("aaa"));
        Assert.assertEquals(cl2, ReflectCache.getAppClassLoader("bbb"));
        Assert.assertEquals(ClassLoaderUtils.getCurrentClassLoader(), ReflectCache.getAppClassLoader("ccc"));
        ReflectCache.cleanAll();
    }

    @Test
    public void tesServiceClassLoader() throws ExecutionException {
        URLClassLoader cl1 = new URLClassLoader(new URL[0]);
        URLClassLoader cl2 = new URLClassLoader(new URL[0]);
        ReflectCache.registerServiceClassLoader("aaa", cl1);
        ReflectCache.registerServiceClassLoader("bbb", cl2);
        Assert.assertEquals(cl1, ReflectCache.getServiceClassLoader("aaa"));
        Assert.assertEquals(cl2, ReflectCache.getServiceClassLoader("bbb"));
        Assert.assertEquals(ClassLoaderUtils.getCurrentClassLoader(), ReflectCache.getServiceClassLoader("ccc"));
        ReflectCache.cleanAll();
    }


}
