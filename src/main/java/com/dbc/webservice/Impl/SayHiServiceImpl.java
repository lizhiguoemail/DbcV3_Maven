package com.dbc.webservice.Impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.dbc.webservice.SayHiService;

/**
* 作为测试的WebService实现类
* @author caihuajun
* 2015-08-11
*/
@WebService(endpointInterface = "com.dbc.webservice.SayHiService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class SayHiServiceImpl implements SayHiService{
	public void SayHiDefault() {
		System.out.println("Hi, Johness!");
	}
	 
	public void SayHi(String name) {
	    System.out.println("Hi, " + name + "!");
	}
	
	public int CheckTime(Date clientTime) {
		// 精确到秒
		String dateServer = new java.sql.Date(System.currentTimeMillis()).toString()+ " "+ new java.sql.Time(System.currentTimeMillis());
		String dateClient = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(clientTime);
		return dateServer.equals(dateClient) ? 1 : 0;
	}
	
	/**
     * <b>function:</b>传递文件
     * @author hoojo
     * @createDate Dec 18, 2010 1:27:58 PM
     * @param handler DataHandler这个参数必须
     * @param fileName 文件名称
     * @return upload Info
     */
    public String upload(DataHandler handler, String fileName) {
        if (fileName != null && !"".equals(fileName)) {
            File file = new File(fileName);
            if (handler != null) {
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    is = handler.getInputStream();
                    fos = new FileOutputStream(file);
                    byte[] buff = new byte[1024 * 8];
                    int len = 0;
                    while ((len = is.read(buff)) > 0) {
                        fos.write(buff, 0, len);
                    }
                } catch(FileNotFoundException e) {
                    return "fileNotFound";
                } catch (Exception e) {
                    return "upload File failure";
                } finally {
                    try {
                        if (fos != null) {
                            fos.flush();
                            fos.close();
                        }
                        if (is != null) {
                            is.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return "file absolute path:" + file.getAbsolutePath();
            } else {
                return "handler is null";
            }
        } else {
            return "fileName is null";
        }
    }
}
