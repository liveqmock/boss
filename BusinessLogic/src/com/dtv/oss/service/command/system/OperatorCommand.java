/*
 * Created on 2004-8-12
 * 
 * @author yanjian
 */
package com.dtv.oss.service.command.system;

import java.util.Map;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

import com.dtv.oss.domain.OpGroup;
import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.domain.SystemPrivilege;
import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.dto.wrap.system.OperatorLoginResult;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.OperatorEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemEJBEvent;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.CryptoUtility;
import com.dtv.oss.util.Constant;
import com.dtv.oss.util.*;
import com.dtv.oss.service.util.*;

public class OperatorCommand extends Command {
	private static final String commandName = "SystemCommand";
	HomeFactory homeFac = null;
	private int operatorID = 0;

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		OperatorEJBEvent inEvent = (OperatorEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		if (getVerbose()) {
			System.out.println("Enter " + commandName + " execute() now.");
		}
		try {
			homeFac = HomeFactory.getFactory();
			switch (inEvent.getActionType()) {
				case SystemEJBEvent.OPERATOR_LOGIN :
					login(inEvent.getOperator());
					break;
				case SystemEJBEvent.OPERATOR_LOGOUT :
					logout(inEvent.getOperator());
					break;
				case SystemEJBEvent.OPERATOR_CHANGE_PWD :
					changePwd(inEvent.getOperator());
					break;
				default :
					throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} catch (CommandException ce) {
			LogUtility.log(OperatorCommand.class,LogLevel.WARN,"execute",ce);
			throw ce;
		} catch (HomeFactoryException he) {
			LogUtility.log(OperatorCommand.class,LogLevel.WARN,"execute",he);
			throw new CommandException(ErrorCode.HOMEFAC_CANNT_LOOKUP);
		} catch (CreateException ce) {
			LogUtility.log(OperatorCommand.class,LogLevel.WARN,"execute",ce);
			throw new CommandException(ErrorCode.ENTITYBEAN_CANNT_CREATE);
		} catch (FinderException fe) {
			LogUtility.log(OperatorCommand.class,LogLevel.WARN,"execute",fe);
			throw new CommandException(ErrorCode.ENTITYBEAN_HOMEINTERFACE_CANNTFIND);
		} catch (Exception e) {
			LogUtility.log(OperatorCommand.class,LogLevel.WARN,"execute",e);
			throw new CommandException(ErrorCode.APP_GENERAL_ERROR);
		}
		return response;
	}

	private void login(OperatorDTO dto)  throws ServiceException ,HomeFactoryException,FinderException{
		OperatorHome operatorhome = (OperatorHome) HomeLocater.getOperatorHome();
		Operator op = null;
		String loginIP = dto.getOperatorDesc();

		try {
			op = operatorhome.findByLoginID(dto.getLoginID());
            if (op != null) {
            	LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在login方法中找当前的操作员");
                int count = BusinessUtility.getInvalidParentOrgCount(op.getOrgID());
                if (count > 0) {
                	LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在login方法中找到了多个无效的组织ID");
                    response.setPayload(null);
                    SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "该操作员组织部门无效，操作员登录失败 LoginID=" + dto.getLoginID(), "操作员登录", loginIP, SystemLogRecorder.LOGMODULE_LOGMANAGE, op.getOperatorID().intValue());
                    return;
                }
                
            
            }
		} catch (ObjectNotFoundException e) {
			LogUtility.log(OperatorCommand.class,LogLevel.WARN,"login",e);
			response.setPayload(null);
			SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "操作员登录失败, LoginID=" + dto.getLoginID(), "操作员登录", loginIP, SystemLogRecorder.LOGMODULE_LOGMANAGE, operatorID);
		}
		if("I".equals(op.getStatus())) {
			LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在login方法中找到了的操作员状态无效");
			response.setPayload(null);
			SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "该操作员状态无效，操作员登录失败 LoginID=" + dto.getLoginID(), "操作员登录", loginIP, SystemLogRecorder.LOGMODULE_LOGMANAGE, operatorID);
			return ;
		}
		if (op.getLoginPwd().equals(CryptoUtility.enpwd(dto.getLoginPwd()))) {
			dto.setOperatorID(op.getOperatorID().intValue());
			dto.setOperatorName(op.getOperatorName());
			dto.setOperatorDesc(op.getOperatorDesc());
			dto.setOrgID(op.getOrgID());
			dto.setLevelID(op.getLevelID());
			dto.setStatus(op.getStatus());
			dto.setDtCreate(op.getDtCreate());
			dto.setDtLastmod(op.getDtLastmod());

			Map map = new java.util.HashMap();
			Collection groups = op.getOpGroup_R();
			LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在login方法中找到操作员所有的权限");
			for (Iterator i = groups.iterator(); i.hasNext();) {
				OpGroup group = (OpGroup) i.next();
				Collection privileges = group.getSystemPrivilege_R();
				for (Iterator j = privileges.iterator(); j.hasNext();) {
					SystemPrivilege privilege = (SystemPrivilege) j.next();
					map.put(String.valueOf(privilege.getPrivID()),String.valueOf(privilege.getPrivID()));
				}
			}
			OperatorLoginResult result = new OperatorLoginResult(dto, map);
			response.setPayload(result);
			this.operatorID= dto.getOperatorID();
			LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在login方法中登陆成功");
			SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "操作员登录成功, LoginID=" + dto.getLoginID(), "操作员登录", loginIP, SystemLogRecorder.LOGMODULE_LOGMANAGE, operatorID);
		} else {
			LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在login方法中找到操作员的密码和输入的密码不对");
			response.setPayload(null);
			SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "操作员登录失败, LoginID=" + dto.getLoginID(), "操作员登录", loginIP, SystemLogRecorder.LOGMODULE_LOGMANAGE, operatorID);
		}

	}

	private void logout(OperatorDTO dto)  throws ServiceException {
		SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "操作员退出, LoginID=" + dto.getLoginID(), "操作员退出", dto.getOperatorDesc(), SystemLogRecorder.LOGMODULE_LOGMANAGE, operatorID);
	}

	/*
	 * 修改密码
	 * dto 的 operatorName 字段中存放的是 新密码
	 * 修改成功 返回 dto
	 * 否则 返回 null
	 */

	private void changePwd(OperatorDTO dto) throws ServiceException, HomeFactoryException, CreateException, FinderException {
		OperatorHome operatorhome = (OperatorHome) HomeLocater.getOperatorHome();
		Operator op = null;
		String loginIP = dto.getOperatorDesc();
		
		try {
			op = operatorhome.findByLoginID(dto.getLoginID());
			LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在changePwd方法中找到操作员");
		} catch (ObjectNotFoundException e) {
			LogUtility.log(OperatorCommand.class,LogLevel.WARN,"changePwd",e);
			response.setPayload(null);
			SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "修改密码失败,操作员ID=" + operatorID+",LoginID="+dto.getLoginID(), "修改操作员密码", loginIP, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID);
		}

		if (op.getLoginPwd().equals(CryptoUtility.enpwd(dto.getLoginPwd()))) {
			op.setLoginPwd(CryptoUtility.enpwd(dto.getOperatorName()));
			op.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			
			dto.setOperatorID(op.getOperatorID().intValue());
			dto.setOperatorName(op.getOperatorName());
			dto.setOperatorDesc(op.getOperatorDesc());
			dto.setOrgID(op.getOrgID());
			dto.setLevelID(op.getLevelID());
			dto.setStatus(op.getStatus());
			dto.setDtCreate(op.getDtCreate());
			dto.setDtLastmod(op.getDtLastmod());

			response.setPayload(dto);
			LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在changePwd方法中修改密码成功");
			SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "修改密码成功, 操作员ID=" + operatorID+",LoginID="+dto.getLoginID(), "修改操作员密码", loginIP, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID);
		} else {
			LogUtility.log(OperatorCommand.class,LogLevel.DEBUG,"在changePwd方法中找到操作员的密码和输入的密码不对");
			response.setPayload(null);
			SystemLogRecorder.createSystemLog(Constant.SYSTEMLOG_LOGCLASS_KEY, Constant.SYSTEMLOG_LOGTYPE_APPLICATION, "修改密码失败, 操作员ID=" + operatorID+",LoginID="+dto.getLoginID(), "修改操作员密码", loginIP, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID);
		}
	}

}
