package com.dbc.util;
import java.io.*;

public class FileUtil
{
	// 验证字符串是否为正确路径名的正则表达式  
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";  
	// 通过 sPath.matches(matches) 方法的返回值判断是否正确  
	// sPath 为路径字符串 
	
	//创建一个文件夹，如果文件夹存在，则不创建
	  public static void createDir(String dirname)
	  {
	    File   dirFile;
	    File   tempFile;
	    boolean bFile;
	    String   sFileName;
	    bFile = false;
	    //dirFile = new File("E:\\test");
		dirFile = new File(dirname);
	    bFile   = dirFile.exists();
	    if( bFile == true )
	    {
	      System.out.println("The folder exists.");
	    }
	    else
	    {
	      System.out.println("The folder do not exist,now trying to create a one...");
	      bFile = dirFile.mkdir();
	      if( bFile == true )
	      {
	        System.out.println("Create successfully!");
	      }
	      else
	      {
	        System.out.println("Disable to make the folder,please check the disk is full or not.");
	       // System.exit(1);
	      }
	    }
	        //System.out.println("Now we put files in to the folder...");
			//        for(int i=0; i<5; i++)
			//        {
			//          sFileName = new String("E:\\test\\");
			//          sFileName += String.valueOf(i);
			//          tempFile = new File(sFileName);
			//          bFile = tempFile.createNewFile();
			//        }
			//      if( bFile == true )
			//        System.out.println("Successfully put files in to the folder!");
			//      else
			//        System.out.println("Sorry sir,i don't finish the job!");
	  }
  
	  public static void renameDir(String dirname,String dirrename)
	  {
	    File   dirFile;
	    File   dirFile2;
	    File   tempFile;
	    boolean bFile;
	    String   sFileName;
	    bFile = false;
      //  dirFile = new File("E:\\test");
    	dirFile = new File(dirname);
    	dirFile2 = new File(dirrename);
        bFile   = dirFile.exists();

        if( bFile == true )
        {
          System.out.println("The folder exists.gotorename");
          dirFile.renameTo(dirFile2);
        }
        else
        {
          System.out.println("The folder do not exist,now trying to create a one...");
          bFile = dirFile.mkdir();
          if( bFile == true )
          {
            System.out.println("Create successfully!");
          }
          else
          {
            System.out.println("Disable to make the folder,please check the disk is full or not.");
           // System.exit(1);
          }
        }
  }
  
  public static boolean CreateFile(String destFileName) {
	   File file = new File(destFileName);
	   if (file.exists()) {
	    System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
	    return false;
	   }
	   if (destFileName.endsWith(File.separator)) {
	    System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
	    return false;
	   }
	   if (!file.getParentFile().exists()) {
	    System.out.println("目标文件所在路径不存在，准备创建。。。");
	    if (!file.getParentFile().mkdirs()) {
	    System.out.println("创建目录文件所在的目录失败！");
	    return false;
	    }
	   }
	   
	// 创建目标文件
	   try {
	    if (file.createNewFile()) {
	    System.out.println("创建单个文件" + destFileName + "成功！");
	    return true;
	    } else {
	    System.out.println("创建单个文件" + destFileName + "失败！");
	    return false;
	    }
	   } catch (IOException e) {
	    e.printStackTrace();
	    System.out.println("创建单个文件" + destFileName + "失败！");
	    return false;
	   }
	}
  
  public void delFolder(String folderPath) {
	  try {
	     delAllFile(folderPath); // 删除完里面所有内容
	     String filePath = folderPath;
	     filePath = filePath.toString();
	     File myFilePath = new File(filePath);
	     myFilePath.delete(); // 删除空文件夹
	  } catch (Exception e) {
	     e.printStackTrace();
	  }
	  }
  
  public  boolean delAllFile(String path) {
	  boolean flag = false;
	  File file = new File(path);
	  if (!file.exists()) {
	     return flag;
	  }
	  if (!file.isDirectory()) {
	     return flag;
	  }
	  String[] tempList = file.list();
	  File temp = null;
	  for (int i = 0; i < tempList.length; i++) {
	     if (path.endsWith(File.separator)) {
	      temp = new File(path + tempList[i]);
	     } else {
	      temp = new File(path + File.separator + tempList[i]);
	     }
	     if (temp.isFile()) {
	      temp.delete();
	     }
	     if (temp.isDirectory()) {
	      delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
	      delFolder(path + "/" + tempList[i]);// 再删除空文件夹
	      flag = true;
	     }
	  }
	  return flag;
	  } 
  
  //复制文件
  public static void copyFile(File sourceFile, File targetFile) throws IOException {
      BufferedInputStream inBuff = null;
      BufferedOutputStream outBuff = null;
      try {
          // 新建文件输入流并对它进行缓冲
          inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

          // 新建文件输出流并对它进行缓冲
          outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

          // 缓冲数组
          byte[] b = new byte[1024 * 5];
          int len;
          while ((len = inBuff.read(b)) != -1) {
              outBuff.write(b, 0, len);
          }
          // 刷新此缓冲的输出流
          outBuff.flush();
      } finally {
          // 关闭流
          if (inBuff != null)
              inBuff.close();
          if (outBuff != null)
              outBuff.close();
      }
  }
  
//复制文件,参数是文件路径
  public static void copyFile(String sourceFilepath, String targetFilepath) throws IOException {
      BufferedInputStream inBuff = null;
      BufferedOutputStream outBuff = null;
      File sourceFile=new File(sourceFilepath);
      File targetFile=new File(targetFilepath);
      try {
          // 新建文件输入流并对它进行缓冲
          inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

          // 新建文件输出流并对它进行缓冲
          outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

          // 缓冲数组
          byte[] b = new byte[1024 * 5];
          int len;
          while ((len = inBuff.read(b)) != -1) {
              outBuff.write(b, 0, len);
          }
          // 刷新此缓冲的输出流
          outBuff.flush();
      } finally {
          // 关闭流
          if (inBuff != null)
              inBuff.close();
          if (outBuff != null)
              outBuff.close();
      }
  }

  // 复制文件夹
  public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
      // 新建目标目录
      (new File(targetDir)).mkdirs();
      // 获取源文件夹当前下的文件或目录
      File[] file = (new File(sourceDir)).listFiles();
      for (int i = 0; i < file.length; i++) {
          if (file[i].isFile()) {
              // 源文件
              File sourceFile = file[i];
              // 目标文件
              File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
              copyFile(sourceFile, targetFile);
          }
          if (file[i].isDirectory()) {
              // 准备复制的源文件夹
              String dir1 = sourceDir + "/" + file[i].getName();
              // 准备复制的目标文件夹
              String dir2 = targetDir + "/" + file[i].getName();
              copyDirectiory(dir1, dir2);
          }
      }
  }
  
}  
