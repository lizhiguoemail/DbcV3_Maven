package com.dbc.util;

import java.io.BufferedInputStream;      
import java.io.BufferedOutputStream;      
import java.io.File;      
import java.io.FileInputStream;      
import java.io.FileNotFoundException;
import java.io.FileOutputStream;      
import java.io.IOException;
import java.util.zip.ZipEntry;      
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream; 

public class Zip {
	
	/**     
     * 压缩文件夹     
     * @param zipPath   生成的zip文件路径     
     * @param filePath  需要压缩的文件夹路径     
     * @throws Exception     
     */     
    public void zipFolder(String zipPath, String filePath) throws Exception {      
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));      
        File f = new File(filePath);      
        zipFiles(out, f, "");      
        out.close();      
    }         
     
    /**     
     * 压缩单一个文件     
     * @param zipPath   生成的zip文件路径     
     * @param filePath  需要压缩的文件路径     
     * @throws Exception     
     */     
    public void zipFile(String zipPath, String filePath) throws Exception {      
        File f = new File(filePath);      
        FileInputStream fis = new FileInputStream(f);      
        BufferedInputStream bis = new BufferedInputStream(fis);      
        byte[] buf = new byte[1024];      
        int len;      
        FileOutputStream fos = new FileOutputStream(zipPath);      
        BufferedOutputStream bos = new BufferedOutputStream(fos);      
        ZipOutputStream zos = new ZipOutputStream(bos);//压缩包      
        ZipEntry ze = new ZipEntry(f.getName());//这是压缩包名里的文件名      
        zos.putNextEntry(ze);// 写入新的ZIP文件条目并将流定位到条目数据的开始处      
     
        while ((len = bis.read(buf)) != -1) {      
            zos.write(buf, 0, len);      
            zos.flush();      
        }      
        bis.close();      
        zos.close();      
     
    }      
          
    /**     
     * 递归调用，压缩文件夹和子文件夹的所有文件     
     * @param out     
     * @param f     
     * @param base     
     * @throws Exception     
     */     
    private void zipFiles(ZipOutputStream out, File f, String base) throws Exception {      
        if (f.isDirectory()) {      
            File[] fl = f.listFiles();      
            out.putNextEntry(new ZipEntry(base + "/"));      
            base = base.length() == 0 ? "" : base + "/";      
            for (int i = 0; i < fl.length; i++) {      
                zipFiles(out, fl[i], base + fl[i].getName());//递归压缩子文件夹      
            }      
        } else {      
            out.putNextEntry(new ZipEntry(base));      
            FileInputStream in = new FileInputStream(f);      
            int b;      
            //System.out.println(base);      
            while ((b = in.read()) != -1) {      
                out.write(b);      
            }      
            in.close();      
        }      
    }   
    
   /**
    * 定义解压缩zip文件的方法
    * 
    * @param zipFileName
    * @param outputDirectory
    */
    public void unzip(String zipFileName, String outputDirectory) {
         try {
             ZipInputStream in = new ZipInputStream(new FileInputStream(
                     zipFileName));
             //获取ZipInputStream中的ZipEntry条目，一个zip文件中可能包含多个ZipEntry，
             //当getNextEntry方法的返回值为null，则代表ZipInputStream中没有下一个ZipEntry，
             //输入流读取完成；
             ZipEntry z = in.getNextEntry();
             while (z != null) {
                 System.out.println("unziping " + z.getName());
                 //创建以zip包文件名为目录名的根目录
                 File f = new File(outputDirectory);
                 f.mkdir();
                 if (z.isDirectory()) {
                     String name = z.getName();
                     name = name.substring(0, name.length() - 1);
                       System.out.println("name " + name);
                     f = new File(outputDirectory + File.separator + name);
                     f.mkdir();
                       System.out.println("mkdir " + outputDirectory
                               + File.separator + name);
                 }
                 else {
                     f = new File(outputDirectory + File.separator + z.getName());
                     f.createNewFile();
                     FileOutputStream out = new FileOutputStream(f);
                     int b;
                     while ((b = in.read()) != -1) {
                         out.write(b);
                     }
                     out.close();
                 }
                 //读取下一个ZipEntry
                 z = in.getNextEntry();
             }
             in.close();
         }
         catch (FileNotFoundException e) {
             // TODO 自动生成 catch 块
             e.printStackTrace();
         }
         catch (IOException e) {
             // TODO 自动生成 catch 块
             e.printStackTrace();
         }
     }
    
    /**
     * 功能:压缩多个文件成一个zip文件
     * <p>作者 陈亚标 Jul 16, 2010 10:59:40 AM
     * @param srcfile：源文件列表
     * @param zipfile：压缩后的文件
     */
    public  void zipFiles(File[] srcfile,File zipfile){
        byte[] buf=new byte[1024];
        try {
            //ZipOutputStream类：完成文件或文件夹的压缩
            ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
            for(int i=0;i<srcfile.length;i++){
                FileInputStream in=new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while((len=in.read(buf))>0){
                    out.write(buf,0,len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**     
     * @param args     
     */     
    public static void main(String[] args) {      
        System.out.println("Zip File Begin");      
     
        Zip zip = new Zip();      
        String zipPath = "D:\\test.zip";      
        String filePath = "D:\\test.txt";      
        try {      
            zip.zipFile(zipPath, filePath);//压缩文件      
            zip.zipFolder(zipPath, filePath);//压缩文件夹   
            
          //2个源文件
            File f1=new File("D:\\workspace\\flexTest\\src\\com\\biao\\test\\abc.txt");
            File f2=new File("D:\\workspace\\flexTest\\src\\com\\biao\\test\\test.zip");
            File[] srcfile={f1,f2};
            //压缩后的文件
            File zipfile=new File("D:\\workspace\\flexTest\\src\\com\\biao\\test\\biao.zip");
            //TestZIP.zipFiles(srcfile, zipfile);
            
            zip.unzip("c:\\test11.zip", "c:\\test11");
            //如果此处不指定解压的具体目录，如：test2，那么则直接解压到根目录下
            //        t.unzip("c:\\test2.zip", "c:\\test2");

        } catch (Exception ex) {      
            ex.printStackTrace();      
        }      
        System.out.println("Zip File Done");      
    }      
}
