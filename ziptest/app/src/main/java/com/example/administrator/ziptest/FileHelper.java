package com.example.administrator.ziptest;

/**
 * Created by Administrator on 2017/8/31.
 */
import android.content.Context;
import android.os.Environment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
    private Context context;
    /** SD卡是否存在**/
    private boolean hasSD = false;
    /** SD卡的路径**/
    private String SDPATH;
    /** 当前程序包的路径**/
    private String FILESPATH;
    public FileHelper(Context context) {
        this.context = context;
        hasSD = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        SDPATH = Environment.getExternalStorageDirectory().getPath();
        FILESPATH = this.context.getFilesDir().getPath();
        createDest();
    }
    private void createDest(){
        //新建一个File，传入文件夹目录
        File file = new File(SDPATH + "//test");
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建<span style="color:#FF0000;">目录中包含却不存在</span>的文件夹
            try{
                file.mkdirs();
                SDPATH = SDPATH+"/test";
            }
            catch (Exception e){}
        }
        else{
            SDPATH = SDPATH+"/test";
        }
    }
    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + "//" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     */
    public boolean deleteSDFile(String fileName) {
        File file = new File(SDPATH + "//" + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        return file.delete();
    }
    /**
     * 写入内容到SD卡中的txt文本中
     * str为内容
     */
    public void writeSDFile(String str,String fileName)
    {
        try {
            FileWriter fw = new FileWriter(SDPATH + "//" + fileName);
            File f = new File(SDPATH + "//" + fileName);
            fw.write(str);
            FileOutputStream os = new FileOutputStream(f);
            DataOutputStream out = new DataOutputStream(os);
            out.writeShort(2);
            out.writeUTF("");
            System.out.println(out);
            fw.flush();
            fw.close();
            System.out.println(fw);
        } catch (Exception e) {
        }
    }
    /**
     * 读取SD卡中文本文件
     *
     * @param fileName
     * @return
     */
    public String readSDFile(String fileName) {
        StringBuffer sb = new StringBuffer();
        File file = new File(SDPATH + "//" + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public boolean encrypt(String path,String password) {
        File file = new File(path);
        if (file.isFile()) {
            String fileName = file.getName();
            int index = file.getName().indexOf(".");
            if (index != -1) {
                fileName = fileName.substring(0, index);
            }
            String dir = file.getParent() + "/" + fileName + ".zip";
            try {
                DecryptionZipUtil.zip(path, dir, password);
            }
            catch (Exception e){return false;}
            return true;
        }
        return false;
    }

    public boolean unencrypt(String path,String output,String password) {
        try {
            DecryptionZipUtil.unzip(path,output,password);
        }
        catch (Exception e){return false;}
        return true;
    }

    public String getFILESPATH() {
        return FILESPATH;
    }
    public String getSDPATH() {
        return SDPATH;
    }
    public boolean hasSD() {
        return hasSD;
    }
}
