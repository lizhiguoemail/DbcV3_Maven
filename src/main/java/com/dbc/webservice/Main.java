package com.dbc.webservice;

import javax.xml.ws.Endpoint;
import com.dbc.webservice.Impl.SayHiServiceImpl;

/*
* --------发布(一般有两种方式)：--------
* 方式一(此方式只能作为调试，有以下bug：
* jdk1.6u17?以下编译器不支持以Endpoint.publish方式发布document方式的soap，必须在service接口和实现类添加“@SOAPBinding(style = SOAPBinding.Style.RPC)”注解；
* 访问受限，似乎只能本机访问(应该会绑定到publish的URL上，如下使用localhost的话就只能本机访问)……)：
*/
public class Main {

/**
* 发布WebService
* 简单
*/
public static void main(String[] args) {
	Endpoint.publish("http://localhost:8080/dbcv2/service/sayHi", new SayHiServiceImpl());
}

/*
 * 方式二(基于web服务器Servlet方式)：
 * 以Tomcat为例，首先编写sun-jaxws.xml文件并放到WEB-INF下：
 * 
  <?xml version="1.0" encoding="UTF-8"?>
   <endpoints xmlns="http://java.sun.com/xml/ns/jax-ws/ri/runtime" version="2.0">
   <endpoint name="SayHiService"
       implementation="service.imp.SayHiServiceImpl"
      url-pattern="/service/sayHi" />
   </endpoints>
   
 *然后改动web.xml，添加listener和servlet(url-pattern要相同哦)：
   <?xml version="1.0" encoding="UTF-8"?>
	<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" version="2.5">
    <listener>  
        <listener-class>
            com.sun.xml.ws.transport.http.servlet.WSServletContextListener  
        </listener-class>
    </listener>
    <servlet>
        <servlet-name>SayHiService</servlet-name>  
        <servlet-class>
            com.sun.xml.ws.transport.http.servlet.WSServlet  
        </servlet-class>
    </servlet>  
    <servlet-mapping>  
        <servlet-name>SayHiService</servlet-name>  
        <url-pattern>/service/sayHi</url-pattern>  
    </servlet-mapping>
    <welcome-file-list>
        <welcome-file>index.html</welcome-file>
        <welcome-file>index.htm</welcome-file>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
</web-app>

*最后部署到Tomcat里，值得一提的是您可能需要添加以下jar包(因为Tomcat没有)：
*jaxb-api.jar
*jaxb-impl.jar
*jaxb-xjc.jar
*jaxws-api.jar
*jaxws-rt.jar
*jaxws-tools.jar
*stax-ex.jar
*streambuffer.jar
*
*服务端工作就完成了，注意两个事情。

　　注意：项目需要使用UTF-8编码(至少sun-jaxws.xml必须是UTF-8格式的)；

　　　　对于MyEclipse的内置Tomcat，可能会出现不需要手动添加上述jar包，但独立部署时应该添加，因为它们使用的class-path不一样；

　　　　多个不同路径的接口也要使用同一个WSServlet；

　　　　最好加上@SOAPBinding(style = SOAPBinding.Style.RPC)注解。

　　部署好了之后打开浏览器输入网址：http://localhost:8080/testjws/service/sayHi?wsdl。可以看到东西就证明发布成功了。

	最后是客户端使用，由于WebService是平台和语言无关的基于xml的，所以我们完全可以使用不同语言来编写或生成客户端。

　　一般有三种方式来使用(对于Java语言而言)：

　　　　一，使用jdk自带工具wsimport生成客户端：
		cd Desktop
		mkdir src
		"%JAVA_HOME%\bin\wsimport" -keep -d .\src -p testjws.client http://localhost:8080/testjws/service/sayHI?wsdl
		
		parsing WSDL...
		Generating code...
		compiling code...
		
		jdk自带的wsimport工具生成，上面命令我是把客户端文件生成到了桌面src文件中(-d)，并保留了源文件(-keep)，指定了包名(-p)。
		然后我们就可以使用生成的文件来调用服务器暴露的方法了：
		值得一提的是你生成使用的jdk和你客户端的jre需要配套！
*/

}
