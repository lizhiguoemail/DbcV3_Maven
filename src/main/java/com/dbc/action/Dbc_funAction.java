package com.dbc.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import com.dbc.pojo.Dbc_nav;
import com.dbc.pojo.Dbc_userinfo;
import com.dbc.service.Dbc_logService;
import com.dbc.service.Dbc_navService;
import com.dbc.util.Dbc_smallpic;
import com.dbc.util.FileUtil;
import com.dbc.util.Dbc_custom_constants;
import com.dbc.util.Dbcutil;
import com.dbc.util.PageParm;
import com.opensymphony.xwork2.ModelDriven;

public class Dbc_funAction extends BaseAction{
	
	private String methode;
	
	private File upload_file; //上传的文件
	
    private String upload_fileFileName; //文件名称
    
    private String upload_fileContentType; //文件类型
    
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	@Override
	public String execute(){
		String returnstr="";
		request = ServletActionContext.getRequest();
		response= ServletActionContext.getResponse();
		if("touploadpic".equals(methode)){
			returnstr=this.touploadpic();
		}
		else if("uploadpic".equals(methode)){
			returnstr=this.uploadpic();
		}
		else if("toshowpic".equals(methode)){
			returnstr=this.toshowpic();
		}
		else if("touploadatt".equals(methode)){
			returnstr=this.touploadatt();
		}
		else if("touploadsql".equals(methode)){
			returnstr=this.touploadsql();
		}
		else if("uploadsql".equals(methode)){
			returnstr=this.uploadsql();
		}
		else if("uploadatt".equals(methode)){
			returnstr=this.uploadatt();
		}
		else{
		  request.setAttribute("action","dbc_fun");
		  request.setAttribute("methode",methode);
		  request.setAttribute("exception", Dbc_custom_constants.nomethode);
		  returnstr="Exception";
		}
	    return returnstr;
	}
	
	/**
	 * @Title toshowpic
	 * @Description  查看图片
	 * @return string  返回值
	 */
	public String toshowpic(){
		String picpath=request.getParameter("picpath");
		String textname=request.getParameter("textname");
		request.setAttribute("picpath", picpath);
		request.setAttribute("textname", textname);
		return "showpic";
	}
	
	/**
	 * @Title touploadpic
	 * @Description  上传图片
	 * @return string  返回值
	 */
	public String touploadpic(){
		String picpath=request.getParameter("picpath");
		String textname=request.getParameter("textname");
		String maxsize=request.getParameter("maxsize");
		request.setAttribute("picpath", picpath);
		request.setAttribute("textname", textname);
		request.setAttribute("maxsize", maxsize);
		return "uploadpic";
	}
	
	/**
	 * @Title uploadpic
	 * @Description  上传图片
	 * @return string  返回值
	 */
	public String uploadpic(){
		String returnstr="uploadpic";
		String textname=request.getParameter("textname");
		String picpath=request.getParameter("picpath");
		String maxsize=request.getParameter("maxsize");
		request.setAttribute("textname", textname);
		request.setAttribute("picpath", picpath);
		request.setAttribute("maxsize", maxsize);
		String separ = File.separator;
		String[] arr=picpath.split("-");
		FileUtil c=new FileUtil();
		String dirname=request.getSession().getServletContext().getRealPath("/");
		String uploadpath="";
		if(arr!=null&&arr.length>0){
			for(int i=0;i<arr.length;i++){
				if(arr[i]!=null&&!"".equals(arr[i])){
					if(i==0){
						uploadpath=arr[i];
						//如果文件夹不存在，新建文件夹
						c.createDir(dirname+uploadpath);
					}
					else{
						uploadpath=uploadpath+separ+arr[i];
						//如果文件夹不存在，新建文件夹
						c.createDir(dirname+uploadpath);
					}
				}
			}
		}
		dirname=dirname+uploadpath;
		//c.createDir(dirname);
		 if (upload_file != null) {
			 boolean isbigmaxsize=false;
			 Long maxsizelong=null;
			 if(maxsize!=null&&!"".equals(maxsize)){
				 maxsizelong=Long.parseLong(maxsize);
			 }
			 if(maxsizelong!=null&&upload_file.length()>maxsizelong){
				 isbigmaxsize=true;
			 }
			 if(isbigmaxsize){
				 request.setAttribute("message", "上传的文件不能超过"+maxsize+"KB");
			 }
			 else{
			 	if(isImageFile(upload_fileFileName)){
			 		File savefile = new File(new File(dirname), upload_fileFileName);
		            if (!savefile.getParentFile().exists()){
		            	savefile.getParentFile().mkdirs();
		            }
		            if(savefile.exists()){
		            	int n=1;
		            	String newupload_fileFileName=upload_fileFileName;
		            	while(true){
		            		newupload_fileFileName=""+n+"-"+upload_fileFileName;
		            		File savefilenew = new File(new File(dirname), newupload_fileFileName);
		            		if(savefilenew.exists()){
		            			n++;
		            		}
		            		else{
		            			try {
									FileUtils.copyFile(upload_file, savefilenew);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		            			upload_fileFileName=newupload_fileFileName;
		            			break;
		            		}
		            	}
	            	}
		            else{
		            	try {
							FileUtils.copyFile(upload_file, savefile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            String base_path=request.getSession().getServletContext().getRealPath(separ);
		            Long smallsize=Long.parseLong("1024")*1024/10; //如果上传的图片大于100KB，则生成缩略图
		            if(upload_file.length()>smallsize){
		            	Dbc_smallpic.getThumbnail(base_path+uploadpath+separ+upload_fileFileName, base_path+uploadpath+separ+"small_"+upload_fileFileName, 240, 180);
		            }
		            else{
		            	File savefilesmall = new File(new File(dirname), "small_"+upload_fileFileName);
		            	try {
							FileUtils.copyFile(upload_file, savefilesmall);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            request.setAttribute("message", "文件上传成功");
		            request.setAttribute("uploadpath", uploadpath+separ+upload_fileFileName);
		            returnstr="uploadsuccess";
			 	}
			 	else{
			 		request.setAttribute("message", "上传的文件不是图片类型");
			 	}
			 }
	     }
		return returnstr;
	}
	
	/**
	 * @Title touploadatt
	 * @Description  上传附件
	 * @return string  返回值
	 */
	public String touploadatt(){
		String attpath=request.getParameter("attpath");
		String textname=request.getParameter("textname");
		String maxsize=request.getParameter("maxsize");
		request.setAttribute("attpath", attpath);
		request.setAttribute("textname", textname);
		request.setAttribute("maxsize", maxsize);
		return "uploadatt";
	}
	
	/**
	 * @Title uploadatt
	 * @Description  上传附件
	 * @return string  返回值
	 */
	public String uploadatt(){
		String returnstr="uploadatt";
		String textname=request.getParameter("textname");
		String attpath=request.getParameter("attpath");
		String maxsize=request.getParameter("maxsize");
		request.setAttribute("textname", textname);
		request.setAttribute("attpath", attpath);
		request.setAttribute("maxsize", maxsize);
		String separ = File.separator;
		String[] arr=attpath.split("-");
		FileUtil c=new FileUtil();
		String dirname=request.getSession().getServletContext().getRealPath("/");
		String uploadpath="";
		if(arr!=null&&arr.length>0){
			for(int i=0;i<arr.length;i++){
				if(arr[i]!=null&&!"".equals(arr[i])){
					if(i==0){
						uploadpath=arr[i];
						//如果文件夹不存在，新建文件夹
						c.createDir(dirname+uploadpath);
					}
					else{
						uploadpath=uploadpath+separ+arr[i];
						//如果文件夹不存在，新建文件夹
						c.createDir(dirname+uploadpath);
					}
					
				}
			}
		}
		dirname=dirname+uploadpath;
		//c.createDir(dirname);
		 if (upload_file != null) {
			 boolean isbigmaxsize=false;
			 Long maxsizelong=null;
			 if(maxsize!=null&&!"".equals(maxsize)){
				 maxsizelong=Long.parseLong(maxsize);
			 }
			 if(maxsizelong!=null&&upload_file.length()>maxsizelong){
				 isbigmaxsize=true;
			 }
			 if(isbigmaxsize){
				 request.setAttribute("message", "上传的文件不能超过"+maxsize+"KB");
			 }
			 else{
				 if(isFile(upload_fileFileName)){
				 		File savefile = new File(new File(dirname), upload_fileFileName);
			            if (!savefile.getParentFile().exists()){
			            	savefile.getParentFile().mkdirs();
			            }
		                if(savefile.exists()){
			            	int n=1;
			            	String newupload_fileFileName=upload_fileFileName;
			            	while(true){
			            		newupload_fileFileName=""+n+"-"+upload_fileFileName;
			            		File savefilenew = new File(new File(dirname), newupload_fileFileName);
			            		if(savefilenew.exists()){
			            			n++;
			            		}
			            		else{
			            			try {
										FileUtils.copyFile(upload_file, savefilenew);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
			            			upload_fileFileName=newupload_fileFileName;
			            			break;
			            		}
			            	}
		            	}
			            else{
			            	try {
								FileUtils.copyFile(upload_file, savefile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
			            request.setAttribute("message", "文件上传成功");
			            request.setAttribute("uploadpath", uploadpath+separ+upload_fileFileName);
			            returnstr="uploadsuccess";
				 	}
				 	else{
				 		request.setAttribute("message", "上传的文件不是正确的类型");
				 	}
			 }
	      }
		return returnstr;
	}
	
	/**
	 * @Title touploadsql
	 * @Description  上传sql文件
	 * @return string  返回值
	 */
	public String touploadsql(){
		String sqlpath=request.getParameter("sqlpath");
		request.setAttribute("sqlpath", sqlpath);
		return "uploadsql";
	}
	
	/**
	 * @Title uploadsql
	 * @Description  上传sql文件
	 * @return string  返回值
	 */
	public String uploadsql(){
		String returnstr="uploadsql";
		String sqlpath=request.getParameter("sqlpath");
		request.setAttribute("sqlpath", sqlpath);
		String separ = File.separator;
		String[] arr=sqlpath.split("-");
		FileUtil c=new FileUtil();
		String dirname=request.getSession().getServletContext().getRealPath(separ);
		String uploadpath="";
		if(arr!=null&&arr.length>0){
			for(int i=0;i<arr.length;i++){
				if(arr[i]!=null&&!"".equals(arr[i])){
					if(i==0){
						uploadpath=arr[i];
						//如果文件夹不存在，新建文件夹
						c.createDir(dirname+uploadpath);
					}
					else{
						uploadpath=uploadpath+separ+arr[i];
						//如果文件夹不存在，新建文件夹
						c.createDir(dirname+uploadpath);
					}
					
				}
			}
		}
		dirname=dirname+uploadpath;
		//c.createDir(dirname);
		 if (upload_file != null) {
			 	if(isSql(upload_fileFileName)){
			 		File savefile = new File(new File(dirname), upload_fileFileName);
		            if (!savefile.getParentFile().exists()){
		            	savefile.getParentFile().mkdirs();
		            }
	                if(savefile.exists()){
		            	int n=1;
		            	String newupload_fileFileName=upload_fileFileName;
		            	while(true){
		            		newupload_fileFileName=""+n+"-"+upload_fileFileName;
		            		File savefilenew = new File(new File(dirname), newupload_fileFileName);
		            		if(savefilenew.exists()){
		            			n++;
		            		}
		            		else{
		            			try {
									FileUtils.copyFile(upload_file, savefilenew);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		            			upload_fileFileName=newupload_fileFileName;
		            			break;
		            		}
		            	}
	            	}
		            else{
		            	try {
							FileUtils.copyFile(upload_file, savefile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            request.setAttribute("message", "sql文件上传成功!");
		            request.setAttribute("uploadpath", uploadpath+separ+upload_fileFileName);
		            returnstr="uploadsuccess2";
			 	}
			 	else{
			 		request.setAttribute("message", "上传的sql文件不是正确的类型");
			 	}
	            
	        }
		return returnstr;
	}
	
	private boolean isImageFile(String imgFileName) {  
        boolean isImage = false;  
        String[] imgExts = {".gif", ".jpg", ".jpeg",".bmp", ".png"};  
        for(String ext : imgExts) {  
            if(imgFileName.toLowerCase().endsWith(ext)) {  
                isImage = true;  
            }  
        }  
  
        return isImage;  
    }
	
	private boolean isFile(String FileName) {  
        boolean isFile = true;  
        String[] imgExts = {".exe", ".EXE", ".bat",".BAT"};  
        for(String ext : imgExts) {  
            if(FileName.toLowerCase().endsWith(ext)) {  
            	isFile = false;  
            }  
        }  
        return isFile;  
    }
	
	private boolean isSql(String FileName) {  
        boolean issql = false;  
        String[] imgExts = {".sql", ".SQL"};  
        for(String ext : imgExts) {  
            if(FileName.toLowerCase().endsWith(ext)) {  
            	issql = true;  
            }  
        }  
        return issql;  
    }
	
	/*private boolean isGoodSize(File imgFile){
		//判断文件大小：
		imgFile.length();
		//获取到的就是文件的大小，单位是B（Byte）
		long length = imgFile.length();
		String msg = "您上传的文件大小为：" + (length / 1024)+ "KB";
		return false;
	}*/
	

	
    public String getMethode() {
		return methode;
	}

	public void setMethode(String methode) {
		this.methode = methode;
	}

	public File getUpload_file() {
		return upload_file;
	}

	public void setUpload_file(File uploadFile) {
		upload_file = uploadFile;
	}

	public String getUpload_fileFileName() {
		return upload_fileFileName;
	}

	public void setUpload_fileFileName(String uploadFileFileName) {
		upload_fileFileName = uploadFileFileName;
	}

	public String getUpload_fileContentType() {
		return upload_fileContentType;
	}

	public void setUpload_fileContentType(String uploadFileContentType) {
		upload_fileContentType = uploadFileContentType;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
