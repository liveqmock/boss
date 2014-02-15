package com.dtv.oss.web.taglib.logic;

/**
 * 页面元素可见性配置标签,
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
							+ "表单:" + "在数据库中没有配置");
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

			// 定义formCol,整个表单包含多少列,
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
							+ "页面没有元素:" + elementName);
					continue;
				}
				String elementContent = element[0];
//				WebPrint.PrintDebugInfo(this.getClass().getName(),
//						"\n>>>>>elementContent:" + elementContent.length());
				if (elementContent == null || elementContent.trim().equals("")) {
					WebPrint.PrintDebugInfo(this.getClass().getName(),
							"\n>>>>>elementName:" + elementName + "没有内容.");
					continue;
				}

				// 当前element有多少列
				int eleCol = (element[2] == null && element[2].equals("")) ? 0
						: Integer.parseInt(element[2]);
				if (eleCol > formCol) {
					WebPrint.PrintErrorInfo(this.getClass().getName(),
							elementName + "包含了太多的单元格,");
				}
				// System.out.println("\ncreateForm.eleCol:"+eleCol);

				int nextEleCol = 0;// 下一个元素的列数,如果下一元素太多列,需要添加空格并开新行,
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

				int currentCol = colIndex % formCol;// 位于当前行第几列.
				if (currentCol == 0 && !elementContent.trim().startsWith("<tr")) {// ||!block.equalsIgnoreCase(newBlock)
					// 如果当前是第0列,或者出现了新的块信息,就加一个行着标记.
					res.append(rowHead);
				}
				// System.out.println("\ncreateForm.formCol:"+formCol);
				// System.out.println("\ncreateForm.colIndex:"+colIndex);
				// System.out.println("\ncreateForm.currentCol:"+currentCol);
				// System.out.println("\ncreateForm.curBlock:"+curBlock+">nextBlock:"+nextBlock+":"+(curBlock!=null&&!curBlock.equals("")&&!curBlock.equals(nextBlock)));

				// 添加当前element正文内容.
				res.append(elementContent);
				currentCol = currentCol + eleCol;
				// 如果当前列加下一元素列大于当前form宽度,或者有新的block信息,.则添加空单元格进去,
				if ((currentCol + nextEleCol > formCol)
						|| (curBlock != null && !curBlock.equals("") && !curBlock
								.equals(nextBlock))) {
					int addEles = (formCol - currentCol) / defaultElementColumn;
					res.append(createEmptyElement(addEles));
					colIndex = colIndex + formCol - currentCol;// 在总列数里加上空的列数
					currentCol = formCol;//
				}

				colIndex = colIndex + eleCol;
				// System.out.println("\ncreateForm.colIndex:"+colIndex);
				// System.out.println("\ncreateForm.currentCol:"+currentCol);

				// 如果到了结尾,行没有填满,写空格进去,
				if (i == elementSetting.size() - 1 && formCol != currentCol) {
					int addEles = (formCol - currentCol) / defaultElementColumn;
					res.append(createEmptyElement(addEles));
					colIndex = colIndex + formCol - currentCol;
				}
				if (formCol == currentCol
						&& !elementContent.trim().endsWith("</tr>")) {// 行尾
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
						+ "无效的设置,页面找不到对应element");
				continue;
			}
			String functionName = element[1];
			if (functionName != null && !functionName.equals("")) {
				if (formFunctionName == null || formFunctionName.equals("")) {
					WebPrint.PrintErrorInfo(this.getClass().getName(), formName
							+ "没有设置functionName");
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