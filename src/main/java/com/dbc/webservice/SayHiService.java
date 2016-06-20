package com.dbc.webservice;

import java.util.Date;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
* 作为测试的WebService接口
* @author caihuajun
* 2015-08-11
* 
* 我们使用JAX-WS开发WebService只需要很简单的几个步骤：写接口和实现=>发布=>生成客户端(测试或使用)。
* 而在开发阶段我们也不需要导入外部jar包，因为这些api都是现成的。
* 首先是接口的编写(接口中只需要把类注明为@WebService，把要暴露给客户端的方法注明为@WebMethod即可，
* 其余如@WebResult、@WebParam等都不是必要的，而客户端和服务端的通信用RPC和Message-Oriented两种。
*/
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SayHiService {
	/**
	* 执行测试的WebService方法
	*/
	@WebMethod
	void SayHiDefault();
	
	/**
	* 执行测试的WebService方法(有参)
	* 
	* @param name
	*/
	@WebMethod
	void SayHi(@WebParam(name = "name") String name);
	
	/**
	* 执行测试的WebService方法(用于时间校验)
	* 
	* @param clentTime 客户端时间
	* @return 0表示时间校验失败 1表示校验成功
	*/
	@WebMethod
	@WebResult(name = "valid")
	int CheckTime(@WebParam(name = "clientTime") Date clientTime);
	
	/**
     * <b>function:</b>传递文件
     * @author hoojo
     * @createDate Dec 18, 2010 1:27:58 PM
     * @param handler DataHandler这个参数必须
     * @param fileName 文件名称
     * @return upload Info
     */
	@WebMethod
	@WebResult(name = "valid")
    String upload(@WebParam(name="handler") DataHandler handler, @WebParam(name="fileName") String fileName);
}
