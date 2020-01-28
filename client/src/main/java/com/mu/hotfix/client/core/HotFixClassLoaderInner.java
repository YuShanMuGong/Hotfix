package com.mu.hotfix.client.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HotFixClassLoaderInner extends ClassLoader {

    private HotFixClassLoader hotFixClassLoader;

    private String classLoaderName;

    private List<String> loadedClassName;

    HotFixClassLoaderInner(ClassLoader parentClassLoader , String name , HotFixClassLoader hotFixClassLoader){
        super(parentClassLoader);
        this.classLoaderName = name;
        loadedClassName = new ArrayList<>();
        this.hotFixClassLoader = hotFixClassLoader;
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name){
        byte[] classBytes = hotFixClassLoader.findClassBytes(name);
        Class<?> cl = defineClass(name,classBytes,0,classBytes.length);
        loadedClassName.add(name);
        return cl;
    }

    public List<String> getLoadedClassNameList(){
        return Collections.unmodifiableList(loadedClassName);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + classLoaderName;
    }
}
