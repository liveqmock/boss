/**
 * 
 */
package com.dtv.oss.web.taglib.html;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.tree.GenerateTree;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.CurrentOperator;
import com.dtv.oss.web.util.LogonWebCurrentOperator;
import com.dtv.oss.web.util.WebPrint;

/**
 * 用来输出layer格式的hetml树
 * @author 240910y
 *
 */
public class JavaScriptTreeWriteTag extends BodyTagSupport {
	private String needCreatRadioType = null;
	private String retrunID = "";
	private String retrunDesc = "";
	private String type = "";
	private String labelType = null;
	
	/**
	 * @return Returns the retrunDesc.
	 */
	public String getLabelType() {
		return labelType;
	}

	/**
	 * @param retrunDesc The retrunDesc to set.
	 */
	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	/**
	 * @return Returns the retrunDesc.
	 */
	public String getRetrunDesc() {
		return retrunDesc;
	}

	/**
	 * @param retrunDesc The retrunDesc to set.
	 */
	public void setRetrunDesc(String retrunDesc) {
		this.retrunDesc = retrunDesc;
	}

	/**
	 * @return Returns the retrunID.
	 */
	public String getRetrunID() {
		return retrunID;
	}

	/**
	 * @param retrunID The retrunID to set.
	 */
	public void setRetrunID(String retrunID) {
		this.retrunID = retrunID;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getNeedCreatRadioType() {
		return needCreatRadioType;
	}

	public void setNeedCreatRadioType(String pProperty) {
		needCreatRadioType = pProperty;
	}

	public int doStartTag() throws JspException {
		LogonWebCurrentOperator wrapOper = (LogonWebCurrentOperator) CurrentOperator
				.GetCurrentOperator(pageContext.getSession());
		if (type == null || type.equals("")) {
			WebPrint.PrintErrorInfo(this.getClass().getName(), "显示类型不确定");
			return SKIP_BODY;
		}
		if (wrapOper == null) {
			WebPrint.PrintErrorInfo(this.getClass().getName(),
					"current operator wrap is null");
			return SKIP_BODY;
		}

		OperatorDTO dtoOper = (OperatorDTO) wrapOper.getCurrentOperator();
		if (dtoOper == null) {
			WebPrint.PrintErrorInfo(this.getClass().getName(),
					"current operator dto is null");
			return SKIP_BODY;
		}
		int orgID = dtoOper.getOrgID();

		//修改,在这里先分隔有没有all,如果是all全部显示,则OPERRATER取最大公司
		WebPrint.PrintDebugInfo(this.getClass().getName(),
				"needCreatRadioType1:" + needCreatRadioType);
		String[] showTypes = needCreatRadioType.split(";");
		WebPrint.PrintDebugInfo(this.getClass().getName(), "showTypes:"
				+ showTypes.length);
		if (showTypes.length > 1) {
			String showAll = showTypes[0];
			if (showAll.equalsIgnoreCase("all")) {
				orgID = Postern.getRootOrganizationID();
			}
			needCreatRadioType = showTypes[1];
		}
		WebPrint.PrintDebugInfo(this.getClass().getName(),
				"needCreatRadioType2:" + needCreatRadioType);
		WebPrint.PrintDebugInfo(this.getClass().getName(), "orgID:" + orgID);

		boolean isShowAll = false, isShowLeaf = false;
		List typeList = new ArrayList();
		String types[] = needCreatRadioType.split(",");
		if (types != null && types.length != 0) {
			for (int i = 0; i < types.length; i++) {
				if ("all".equalsIgnoreCase(types[i])) {
					isShowAll = true;
					break;
				} else if ("leaf".equalsIgnoreCase(types[i])) {
					isShowLeaf = true;
				} else {
					typeList.add(types[i]);
				}
			}
		}
		StringBuffer treeMessage = new StringBuffer();

		System.out.println("tag:" + retrunID);

		if (type.equalsIgnoreCase("district") || type.startsWith("dist")) {
			WebPrint.PrintDebugInfo(this.getClass().getName(),
					"*******tag labelType:" + labelType);
			
			treeMessage = GenerateTree.generateDistrictJavaScriptTree(orgID,
					typeList, isShowAll, isShowLeaf, labelType, retrunID,
					retrunDesc);

		}

		else if (type.equalsIgnoreCase("organization")
				|| type.startsWith("org"))
			treeMessage = GenerateTree.generateOrganizationJavaScriptTree(
					dtoOper.getOrgID(), typeList,isShowAll,labelType, retrunID, retrunDesc);
		
		ResponseUtils.write(pageContext, treeMessage.toString());
		return (SKIP_BODY);
	}

}
