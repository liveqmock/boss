package com.dtv.oss.web.taglib.logic;

/**
 * ҳ��Ԫ�ؿɼ������ñ�ǩ,
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.*;

import com.dtv.oss.dto.DynamicFormSettingDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.*;

public class DynamicFormTag extends DynamicFormElementTag {
	private static final int defaultColumn = 4;

	private static final String emptyElement = "<td class=\"list_bg2\" align=\"right\"></td><td class=\"list_bg1\"></td>";

	private static final String rowHead = "\n<tr>\n";

	private static final String rowTail = "\n</tr>\n";

	private LinkedHashMap elements;

	private String formName;

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public int doStartTag() throws JspException {
		WebPrint.PrintDebugInfo(this.getClass().getName(), "\n>>>>>" + id
				+ "  >>>>formName:" + formName);
		// if(column==null||column.equals(""))column=defaultColumn;
		return (EVAL_BODY_BUFFERED);
	}

	public int doAfterBody() throws JspException {
		WebPrint.PrintDebugInfo(this.getClass().getName(), "  >>>>formName:"
				+ formName + "  doAfterBody enter ...");

		try {
			elements = (LinkedHashMap) pageContext.getAttribute(BUFFERNAME);
			// WebPrint.PrintDebugInfo(this.getClass().getName(),"elements"+elements);
			// WebPrint.PrintDebugInfo(this.getClass().getName(),"bodyContent"+bodyContent);
			if (bodyContent != null && elements != null) {
				ArrayList settingList = Postern
						.getDynamicFormSettingList(formName);
				if (settingList != null && !settingList.isEmpty()) {
					StringBuffer outBuf = new StringBuffer();
					outBuf.append(createForm(settingList));
					outBuf.append(createJavascriptFunction(settingList));

					// WebPrint.PrintDebugInfo(this.getClass().getName(),"outBuf.toString():\n"+outBuf.toString());
					JspWriter out = pageContext.getOut();
					if (out instanceof BodyContent)
						out = ((BodyContent) out).getEnclosingWriter();
					out.println(outBuf.toString());
				} else {
					WebPrint.PrintErrorInfo(this.getClass().getName(), formName
							+ "��:" + "�����ݿ���û������");
				}

			}
			WebPrint.PrintDebugInfo(this.getClass().getName(),
					"  >>>>formName:" + formName + "   doAfterBody return.");
		} catch (Exception e) {
			e.printStackTrace();
			WebPrint.PrintErrorInfo(this.getClass().getName(), e.getMessage());
		}

		return (SKIP_BODY);

	}

	private String createForm(ArrayList elementSetting) {
		WebPrint.PrintDebugInfo(this.getClass().getName(), "\n>>>>>createForm"
				+ "  >>>>formName:" + formName);
		StringBuffer res = new StringBuffer();
		try {
			if (elements == null || elements.isEmpty())
				return null;
			int colIndex = 0;

			// ����formCol,����������������,
			int formCol = (column != null && !column.equals("")) ? Integer
					.parseInt(column) : defaultColumn;

			for (int i = 0; i < elementSetting.size(); i++) {
				DynamicFormSettingDTO formDTO = (DynamicFormSettingDTO) elementSetting
						.get(i);
				String elementName = formDTO.getElementName().toUpperCase();
				WebPrint.PrintDebugInfo(this.getClass().getName(),
						"\ncreateForm.elementName:" + elementName);
				String[] element = (String[]) elements.get(elementName);
				if (element == null || element.length == 0) {
					WebPrint.PrintErrorInfo(this.getClass().getName(), formName
							+ "ҳ��û��Ԫ��:" + elementName);
					continue;
				}
				String elementContent = element[0];
//				WebPrint.PrintDebugInfo(this.getClass().getName(),
//						"\n>>>>>elementContent:" + elementContent.length());
				if (elementContent == null || elementContent.trim().equals("")) {
					WebPrint.PrintDebugInfo(this.getClass().getName(),
							"\n>>>>>elementName:" + elementName + "û������.");
					continue;
				}

				// ��ǰelement�ж�����
				int eleCol = (element[2] == null && element[2].equals("")) ? 0
						: Integer.parseInt(element[2]);
				if (eleCol > formCol) {
					WebPrint.PrintErrorInfo(this.getClass().getName(),
							elementName + "������̫��ĵ�Ԫ��,");
				}
				// System.out.println("\ncreateForm.eleCol:"+eleCol);

				int nextEleCol = 0;// ��һ��Ԫ�ص�����,�����һԪ��̫����,��Ҫ��ӿո񲢿�����,
				String nextBlock = "";
				if (i + 1 < elementSetting.size()) {
					DynamicFormSettingDTO nextFormDTO = (DynamicFormSettingDTO) elementSetting
							.get(i + 1);
					nextBlock = nextFormDTO.getInfoBlock();
					String[] nextElement = (String[]) getNextElement(
							elementSetting, i);
					if (nextElement != null && nextElement.length != 0) {
						nextEleCol = (nextElement[2] != null && !nextElement[2]
								.equals("")) ? Integer.parseInt(nextElement[2])
								: 0;
					}
				}
				// System.out.println("\ncreateForm.nextEleCol:" + nextEleCol);

				String curBlock = formDTO.getInfoBlock();

				int currentCol = colIndex % formCol;// λ�ڵ�ǰ�еڼ���.
				if (currentCol == 0 && !elementContent.trim().startsWith("<tr")) {// ||!block.equalsIgnoreCase(newBlock)
					// �����ǰ�ǵ�0��,���߳������µĿ���Ϣ,�ͼ�һ�����ű��.
					res.append(rowHead);
				}
				// System.out.println("\ncreateForm.formCol:"+formCol);
				// System.out.println("\ncreateForm.colIndex:"+colIndex);
				// System.out.println("\ncreateForm.currentCol:"+currentCol);
				// System.out.println("\ncreateForm.curBlock:"+curBlock+">nextBlock:"+nextBlock+":"+(curBlock!=null&&!curBlock.equals("")&&!curBlock.equals(nextBlock)));

				// ��ӵ�ǰelement��������.
				res.append(elementContent);
				currentCol = currentCol + eleCol;
				// �����ǰ�м���һԪ���д��ڵ�ǰform���,�������µ�block��Ϣ,.����ӿյ�Ԫ���ȥ,
				if ((currentCol + nextEleCol > formCol)
						|| (curBlock != null && !curBlock.equals("") && !curBlock
								.equals(nextBlock))) {
					int addEles = (formCol - currentCol) / defaultElementColumn;
					res.append(createEmptyElement(addEles));
					colIndex = colIndex + formCol - currentCol;// ������������Ͽյ�����
					currentCol = formCol;//
				}

				colIndex = colIndex + eleCol;
				// System.out.println("\ncreateForm.colIndex:"+colIndex);
				// System.out.println("\ncreateForm.currentCol:"+currentCol);

				// ������˽�β,��û������,д�ո��ȥ,
				if (i == elementSetting.size() - 1 && formCol != currentCol) {
					int addEles = (formCol - currentCol) / defaultElementColumn;
					res.append(createEmptyElement(addEles));
					colIndex = colIndex + formCol - currentCol;
				}
				if (formCol == currentCol
						&& !elementContent.trim().endsWith("</tr>")) {// ��β
					res.append(rowTail);
				}
			}
			// WebPrint.PrintDebugInfo(this.getClass().getName(),"\ncreateForm.res.toString()"+res.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	private String[] getNextElement(ArrayList eleSetting, int index) {
		for (int i = index + 1; i < eleSetting.size(); i++) {
			DynamicFormSettingDTO nextFormDTO = (DynamicFormSettingDTO) eleSetting
					.get(i);
			String eleName = nextFormDTO.getElementName().toUpperCase();
			String[] curEle = (String[]) elements.get(eleName);
			if (curEle == null)
				continue;
			return curEle;
		}
		return null;
	}

	private String createEmptyElement(int eles) {
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < eles; i++) {
			res.append(emptyElement);
		}
		return res.toString();
	}

	private String createJavascriptFunction(ArrayList elementSetting) {
		StringBuffer res = new StringBuffer();
		String formFunctionName = getFunctionName();
		boolean isFirst = false;
		res.append("\n<Script language=\"JavaScript\">").append("\n");

		for (int i = 0; i < elementSetting.size(); i++) {
			DynamicFormSettingDTO formDTO = (DynamicFormSettingDTO) elementSetting
					.get(i);
			String elementName = formDTO.getElementName().toUpperCase();
			String[] element = (String[]) elements.get(elementName);
			if (element == null || element.length == 0) {
				WebPrint.PrintErrorInfo(this.getClass().getName(), elementName
						+ "��Ч������,ҳ���Ҳ�����Ӧelement");
				continue;
			}
			String functionName = element[1];
			if (functionName != null && !functionName.equals("")) {
				if (formFunctionName == null || formFunctionName.equals("")) {
					WebPrint.PrintErrorInfo(this.getClass().getName(), formName
							+ "û������functionName");
					break;
				}
				if (!isFirst) {
					formFunctionName = formFunctionName.endsWith("()") ? formFunctionName
							: formFunctionName + "()";
					res.append("function ").append(formFunctionName)
							.append("{").append("\n");
					isFirst = true;
				}
				res.append("\t").append("if(!").append(functionName).append(
						")return false").append(";").append("\n");

			}
		}
		if (!isFirst) {
			formFunctionName = formFunctionName.endsWith("()") ? formFunctionName
					: formFunctionName + "()";
			res.append("function ").append(formFunctionName).append("{")
					.append("\n");
		}
		res.append("\t").append("return true;").append("\n");
		res.append("}\n").append("</Script>");

		// WebPrint.PrintDebugInfo(this.getClass().getName(),"\ncreateJavascriptFunction.res.toString()"+res.toString());

		return res.toString();
	}

	public void release() {
		elements.clear();
		super.release();

	}

}