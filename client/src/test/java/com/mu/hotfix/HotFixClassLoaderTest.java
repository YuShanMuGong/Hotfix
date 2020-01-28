package com.mu.hotfix;

import com.mu.hotfix.client.core.HotFixClassLoader;
import org.junit.Assert;
import org.junit.Test;

public class HotFixClassLoaderTest {

    @Test
    public void test_loadClass_normal() throws Exception{
        HotFixClassLoader hotFixClassLoader = new HotFixClassLoader(getClass().getClassLoader());
        Class<?> cl = hotFixClassLoader.loadClass("com.mu.test.Person");
        Assert.assertNotNull(cl);
    }

}
