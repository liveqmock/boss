package com.dtv.oss.web.action.groupcustomer;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebUtil;

public class GroupCustomerBatchImportWebAction extends PayFeeWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		GroupCustomerEJBEvent event = new GroupCustomerEJBEvent();
		try {
			FileUpload upload=new FileUpload(request,1024000,"csv");
			String actionType = request.getParameter("txtActionType");
			if (WebUtil.StringHaveContent(actionType)) {
				if (actionType.equalsIgnoreCase("fileUpload")) {

					// ȡ�ú�ͬ��
					String contractNo = request.getParameter("txtContractNo");
					LogUtility.log(this.getClass(), LogLevel.DEBUG, "contractNo:"
							+ contractNo);
					if (WebUtil.StringHaveContent(contractNo)) {
						event.setContractNO(contractNo);
					} else {
						throw new WebActionException("��Ч�ĺ�ͬ��");
					}

					// ȡ�ü���ID
					String groupCustomerID = request.getParameter("txtGroupCustID");
					if (WebUtil.StringHaveContent(groupCustomerID)) {
						event.setCustomerID(WebUtil.StringToInt(groupCustomerID));
					} else {
						throw new WebActionException("��Ч���ſͻ�");
					}

					// ȡ�ø����ʻ�ID
					String accountID = request.getParameter("txtAccount");
					if (WebUtil.StringHaveContent(accountID)) {
						event.setAccountID(Integer.parseInt(accountID));
					} else {
						throw new WebActionException("��Ч�����ʻ�");
					}

					LogUtility.log(this.getClass(), LogLevel.DEBUG, "�ļ��ϴ�");
					String filePath = upload.saveFile();
					event.setFilePath(filePath);
					event.setGroupCustomerWrapList(parseFile(filePath));
					request.setAttribute("txtFilePath", filePath);
					LogUtility.log(this.getClass(), LogLevel.DEBUG, "�ļ�����·��:"
							+ event.getFilePath());
					event.setActionType(GroupCustomerEJBEvent.GROUPCUSTOMERBATCHIMPORT);
				} else if (actionType.equalsIgnoreCase("batch_import_confirm")) {
					event.setContractNO(request.getParameter("txtContractNo"));
					event.setCustomerID(WebUtil.StringToInt(request
							.getParameter("txtGroupCustID")));
					event.setAccountID(WebUtil.StringToInt(request
							.getParameter("txtAccount")));
					event.setOpenSourceType(request
							.getParameter("txtOpenSourceType"));
					event.setFilePath(request.getParameter("txtFilePath"));
					event.setGroupCustomerWrapList(parseFile(event.getFilePath()));
					event
							.setActionType(GroupCustomerEJBEvent.GROUPCUSTOMERBATCHIMPORTCONFIRM);
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		return event;
	}

	private Collection parseFile(String filePath) throws WebActionException{
		return FileUtility
		.parseCSVFileWithGroupCustomer(filePath);
	} 

}
