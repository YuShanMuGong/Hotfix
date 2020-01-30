package com.mu.hotfix.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {

    public static byte[] readFile(String path){
        if(StringUtil.isEmpty(path)){
            throw new IllegalArgumentException("readFile params error");
        }
        File file = new File(path);
        if(!file.exists() || !file.canRead()){
            throw new IllegalStateException("read file fail,file not exist or can not read");
        }
        try(FileInputStream fin = new FileInputStream(file) ;
                ByteArrayOutputStream bout = new ByteArrayOutputStream()){
            byte[] read = new byte[1024];
            int l;
            while ( (l = fin.read(read) ) != -1){
                bout.write(read,0,l);
            }
            return bout.toByteArray();
        }catch (Exception e){
            throw new IllegalStateException("read file fail",e);
        }
    }

    /**
     * 确保文件夹存在
     * @param dirPath
     */
    public static void dirExist(String dirPath){
        File file = new File(dirPath);
        if(!file.exists() || !file.isDirectory()){
            if(!file.mkdirs()){
                throw new IllegalStateException("mkdirs fail");
            }
        }
    }

    public static void newOrReplaceFile(String filePath , byte[] contents){
        if(StringUtil.isEmpty(filePath) || ByteArrayUtil.isEmpty(contents)){
            throw new IllegalArgumentException("newOrReplaceFile params error");
        }
        File file = new File(filePath);
        if(file.exists() && !file.canWrite()){
            throw new IllegalStateException("file can not write");
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (Exception e){
                throw new IllegalStateException("new file error",e);
            }
        }
        try(FileOutputStream fout = new FileOutputStream(file)){
            fout.write(contents);
            fout.flush();
        }catch (Exception e){
            throw new IllegalStateException("write file error",e);
        }
    }

}
