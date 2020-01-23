package com.mu.hotfix.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {

    public static byte[] readFile(String path){
        if(StringUtil.isEmpty(path)){
            return null;
        }
        File file = new File(path);
        if(!file.exists() || !file.canRead()){
            return null;
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
            return null;
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
