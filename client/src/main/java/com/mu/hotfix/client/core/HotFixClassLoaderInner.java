package com.mu.hotfix.client.core;

import com.mu.hotfix.common.DTO.RemoteClassDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HotFixClassLoaderInner extends ClassLoader {

    private HotFixClassLoader hotFixClassLoader;

    private String classLoaderName;

    private List<RemoteClassDTO> loadedClass;

    HotFixClassLoaderInner(ClassLoader parentClassLoader , String name , HotFixClassLoader hotFixClassLoader){
        super(parentClassLoader);
        this.classLoaderName = name;
        loadedClass = new ArrayList<>();
        this.hotFixClassLoader = hotFixClassLoader;
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name){
        RemoteClassDTO remoteClassDTO = hotFixClassLoader.findClassBytes(name);
        byte[] classBytes = remoteClassDTO.getContent();
        Class<?> cl = defineClass(name,classBytes,0,classBytes.length);
        loadedClass.add(remoteClassDTO);
        return cl;
    }

    public List<RemoteClassDTO> getLoadedClass(){
        return Collections.unmodifiableList(loadedClass);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + classLoaderName;
    }
}
