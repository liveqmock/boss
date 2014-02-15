/**
 * 
 */
package com.dtv.oss.web.util;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.Postern;

/**
 * @author 240910y
 *
 */
public class ConfigMenuContextListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		event.getServletContext().removeAttribute(CommonKeys.SYSTEM_CONFIG_MENU_KEY);
	}
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		try{
			LogUtility.log(ConfigMenuContextListener.class, LogLevel.DEBUG, "初始化系统菜单配置开始");
			String sysModelConfigName=event.getServletContext().getInitParameter("boss.config.model.configname");
			Map menuMap=Postern.getSystemConfigMenu(sysModelConfigName);
			event.getServletContext().setAttribute(CommonKeys.SYSTEM_CONFIG_MENU_KEY, menuMap);
			LogUtility.log(ConfigMenuContextListener.class, LogLevel.DEBUG, "初始化系统菜单配置结束");
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
