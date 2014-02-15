/*
 * Created on 2005-11-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.test;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.controller.*;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BusinessLogicTestClient {
	private EJBControllerHome eJBControllerHome = null;
	private static EJBController controller = null;
	public static void main(String[] args) {
		BusinessLogicTestClient client = new BusinessLogicTestClient();
		System.out.println("■■■■开始进行测试...■■■■");
		try {
			//测试体，在这里追加测试方法
			//修改账户信息
			//AccountCommandTest.testAlterAccountInfo(controller);
			//测试取消账户
			AccountCommandTest.testCloseAccount(controller);	
			//测试创建账户
			//AccountCommandTest.testCreateAccount(controller);
		} catch (Exception e) {
			if (e instanceof CommandException) {
				System.out.println("■发生CommandException...■");
				System.out.println(e.getMessage());
			}
			System.out.println(e.getClass());
			e.printStackTrace();
		}
		System.out.println("■■■■测试成功结束■■■■");
	}

	//Construct the EJB test client
	public BusinessLogicTestClient() {
		try {
			Context ctx = getInitialContext();
			Object ref = ctx.lookup("EJBController");
			eJBControllerHome = (EJBControllerHome) PortableRemoteObject.narrow(ref, EJBControllerHome.class);
			controller = eJBControllerHome.create();
			System.out.println("ejbcontroller create...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Context getInitialContext() throws Exception {
		String url = "t3://localhost:7001";
		String user = "yangchen";
		String password = "yangchen";
		Properties properties = null;
		try {
			properties = new Properties();
			properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			properties.put(Context.PROVIDER_URL, url);
			if (user != null) {
				properties.put(Context.SECURITY_PRINCIPAL, user);
				properties.put(Context.SECURITY_CREDENTIALS, password == null ? "" : password);
			}

			return new InitialContext(properties);
		} catch (Exception e) {
			System.out.println("Unable to connect to WebLogic server at " + url);
			System.out.println("Please make sure that the server is running.");
			throw e;
		}
	}
}
