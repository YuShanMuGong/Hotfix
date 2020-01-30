package com.mu.hotfix;

import com.mu.hotfix.client.core.HotFixClassLoader;
import com.mu.hotfix.client.srv.EmbeddableHttpSrv;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class HotFixClassLoaderTest {

    @Test
    public void test_loadClass_normal() throws Exception{
        HotFixClassLoader hotFixClassLoader = new HotFixClassLoader(getClass().getClassLoader());
        hotFixClassLoader.init();
        hotFixClassLoader.start();
        Class<?> cl = hotFixClassLoader.loadClass("com.mu.learn.test.Test");
        Assert.assertNotNull(cl);
        System.out.println(cl.getSimpleName());
        cl.getDeclaredMethod("show").invoke(null);
        LockSupport.park();
    }

    @Test
    public void test_embeddableHttpSrv() throws Exception{
        EmbeddableHttpSrv httpSrv = new EmbeddableHttpSrv(null,9000);
        httpSrv.start();
        LockSupport.park();
    }

}
