/*
 * Created on 2004-10-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.TreeViewEJBEvent;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;


/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TreeViewCommand extends Command {
	private static final String commandName = "TreeViewCommand";
	private static final Class clazz = TreeViewCommand.class;
	private int operatorID = 0;
	private String loginIP="";
	private String initialString="one";
	private int initialData=0;
	CommandResponseImp response = null;
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		TreeViewEJBEvent inEvent = (TreeViewEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		loginIP =inEvent.getRemoteHostAddress();
		if (getVerbose()) {
			System.out.println("Enter " + commandName + " execute() now.");
		}
		try {
			switch (inEvent.getActionType()) {
				case TreeViewEJBEvent.QUERY_TEST_TREE_VIEW:
					treeView(inEvent);
					break;
				default :
					throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} catch (CommandException ce) {
			LogUtility.log(clazz,LogLevel.ERROR,this,ce);
			throw ce;
		}catch (Exception e) {
			LogUtility.log(clazz,LogLevel.ERROR,this,e);
			throw new CommandException(ErrorCode.APP_GENERAL_ERROR);
		}
		return response;
	}
	private void treeView(TreeViewEJBEvent inEvent) throws CommandException{
		try {
			//DynamicRootNode dynamicRootNode=inEvent.getDynamicRootNode();
			//if(dynamicRootNode!=null){
			//	dynamicRootNode=treeViewChange(inEvent.getKeyValue(),dynamicRootNode);
			//}
			//response.setPayload(dynamicRootNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	private DynamicRootNode  treeViewChange(String myKey ,DynamicRootNode dynamicRootNode) throws CommandException{
		try {
			dynamicRootNode=GenerateTree.changeDynamicTreeNode(myKey,dynamicRootNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dynamicRootNode;
	}*/
}
