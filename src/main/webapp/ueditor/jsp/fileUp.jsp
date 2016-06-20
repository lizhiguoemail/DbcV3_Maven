<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.dbc.util.Uploader"%>


<%
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");

    Uploader up = new Uploader(request);
    up.setSavePath("/upload/file"); //保存路径
    String[] fileType = {".rar" , ".doc" , ".docx" , ".zip" , ".pdf" , ".txt" , ".swf", ".wmv", ".xls", ".xlsx", ".csv"};  //允许的文件类型
    up.setAllowFiles(fileType);
    up.setMaxSize(100000);        //允许的文件最大尺寸，单位KB
    up.upload();
    response.getWriter().print("{'url':'"+up.getUrl()+"','fileType':'"+up.getType()+"','state':'"+up.getState()+"','original':'"+up.getOriginalName()+"'}");

%>
