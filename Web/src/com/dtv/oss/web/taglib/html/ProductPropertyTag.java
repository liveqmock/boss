package com.dtv.oss.web.taglib.html;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import utils.system;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.CurrentCustomer;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

/**
 * 用于成product或package下全部product属性的标签，
 * showType为hidden时该标签用于把上页设置的值传递到下页面，当showType为label用于在页面上展示上页面设置的值,当为其它（包括不填）时生成设置页面，
 * 此时必须有一个productID或 packageID传进来，不能一个都没用，
 * Match为Map格式，内容(productID+"_"+propertyCode,value)
 * 所有控件命名为productID+propertyCode,包含一个hidden的文本框，内容为propertyCode集合，字符串形式，以‘;’号分隔，取property设置值时应该可以用到
 * 单元格宽度属性被我删掉了，因为不完整，本标签的输出为
 * <tr></tr>
 * 可在标签前加一空行来设定，如
 * <tr>
 * <td width='20'></td>
 * </tr>
 * ,上下<table>请自行添加
 * headStyle(第一行标题产品名称)wordStyle（属性控件前label）tdWordStyle（label所在单元格样式）等没有默认值，请设置为妙
 * showType为标签在页面上的出现样式，数据展示（label），数据收集并可提交到下一页面（hidden），数值设定(包含多样格式控件，不要设置该项，或设置错误的值)
 * 上下页面请用同一个serverName,否则可以不设，都不设
 * 6-23设置完成密码部分内容设置,增加字段passName,用来设置两个密码输入框的Name,在webaction中按此name取新密码,得到两元素的数组,可以检查是否匹配等
 * 包含密码的属性CODE在这里写了一个常量,webaction中取值时也需要用到,可以考虑做成tag的property项
 * 初始密码根据passwordsuperset中来,有不需要的字符删除即可. 6-26修改产品包列表提交的问题.
 * 2008-1-10 增加 了isChangePassword属性,为了在TEXT时能显示request中值,和密码修改区分开,有效值为true/false,可空,
 * @author 260327h
 * 
 */
public class ProductPropertyTag extends BaseHandlerTag {

	private static final String passwordsuperset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static final String passwordfield = "30003";

	private static final int passwordlength = 8;

	private static final String passwordfieldname = "passwordfield";

	// 密码框名称
	private String passName;

	// 标签在页面上的用途
	private String showType;

	// 产品包ID
	private String packageID;

	// 产品ID
	private String productID;

	// 匹配的名
	private String match;

	// 文字在页面显示的样式
	private String wordStyle;

	// 表格文字样式
	private String tdWordStyle;

	// 表格控件样式
	private String tdControlStyle;

	// 控件在页面显示的样式
	private String controlStyle;

	// 控件大小
	private String controlSize;

	// 每行显示调查的数量
	private String column;

	// TD的高度
	private String tdHeight;

	// 文字处于表格的位置
	private String wordAlign;

	// 控件处于表格的位置
	private String controlAlign;

	// 控件名称
	private String serveyName;

	private String headStyle;

	// 当前记录的序列,TAG内部使用
	private int columnNo;

	private Map matchMap;

	private Map checkValueMap;

	//是否密码修改.
	private String isChangePassword;
//	private int iColSpan;

	// 静态常量用
	// 控件显示的长度
	private static int size = 25;

	// 页面布局中每行显示调查的个数
	private int controlNO = 2;

	// 文字处于表格的位置
	private static String inWordAlign = "right";

	// 控件处于表格的位置
	private static String inControlAlign = "left";

	// 控件名
	private static String inServeyName = "txtProductProperty";

	// 显示的propertyCode列表
	private String propertyCodes = "";

	public int doStartTag() throws JspException {
		 //System.out.println("根据页面传入的packageID\n" + packageID);
		 //System.out.println("根据页面传入的productID\n" + productID);
		StringBuffer res = new StringBuffer();

		propertyCodes = "";// 标签开始时清空一下这个变量???真的要清空一下!!!!一个tag的范围在哪里,?

		checkValueMap = new LinkedHashMap();
		checkValueMap.put("Y", "是");
		checkValueMap.put("N", "否");

		//System.out.println("进入标签");
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),
				"invoke doStartTag...");

		/**
		 * 设置一些页面显示默认值
		 */
		// 默认每行显示2个属性
		columnNo = WebUtil.StringToInt(column);
		if (columnNo == 0){
			columnNo = controlNO*2;
		}else{
			controlNO=(int)columnNo/2;
		}
		//System.out.println("当前要显示列数:\n" + columnNo);
//		iColSpan = columnNo * 2;// 每个属性要两个单元格,
		if (controlSize == null || "".equals(controlSize)
				|| WebUtil.StringToInt(this.controlSize) == 0) {
			controlSize = "" + size;
		}

		if (controlAlign == null || "".equals(controlAlign))
			controlAlign = inControlAlign;
		if (wordAlign == null || "".equals(wordAlign))
			wordAlign = inWordAlign;

		if (serveyName == null || "".equals(serveyName))
			serveyName = inServeyName;
		// passwordfieldname设置默认值
		if (passName == null || "".equals(passName))
			passName = passwordfieldname;

		// 取设置的匹配值,
		if (match != null && (!"".equals(match)))
			matchMap = (Map) pageContext.getAttribute(match);

		if (matchMap == null)
			matchMap = new LinkedHashMap();

		if (showType == null)
			showType = "";

		if (showType.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_HIDE)) {
			ServletRequest request = this.pageContext.getRequest();
			String[] hiddenValue = request.getParameterValues(serveyName);
			if (hiddenValue != null) {
				for (int j = 0; j < hiddenValue.length; j++) {
					String[] val = hiddenValue[j].split(";");

					for (int i = 0; i < val.length; i++) {
						ControlProperty con = new ControlProperty();
						String name = val[i];
						if(name==null||name.equals(""))continue;
						name = serveyName + "_" + name;
						String defaultValue = request.getParameter(name);
						con.setPropertyName(name);
						con.setDefaultValue(defaultValue);
						con.setType(CommonKeys.SERVEY_SHOW_TYPE_HIDE);
						 //System.out.println("设置hidden控件 name:" + name);
						 //System.out.println("设置hidden控件 defaultValue:"+ defaultValue);
						res.append(this.makeHiddenTextControl(con));
					}
					propertyCodes += hiddenValue[j];
				}
			}
		} else {
			// 如果做label用，则一定上页面有设定的值，优先取用
			if (showType.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_LABEL)||showType.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_TEXT)) {
				ServletRequest request = this.pageContext.getRequest();
				String[] hiddenValue = request.getParameterValues(serveyName);
				if (hiddenValue != null) {
					for (int j = 0; j < hiddenValue.length; j++) {
						String[] val = hiddenValue[j].split(";");
						for (int i = 0; i < val.length; i++) {
							String name = val[i];
							name = serveyName + "_" + name;
							String defaultValue = request.getParameter(name);
							matchMap.put(name, defaultValue);
							 //System.out.println("压入的匹配值name:\n" + name);
							 //System.out.println("压入的匹配值defaultValue:\n" +defaultValue);
						}
					}
				}
			}
			// packageID 和productID不能同时为空
			if ((packageID == null || packageID.equals(""))
					&& (productID == null || productID.equals(""))) {
				WebPrint
						.PrintErrorInfo(this.getClass().getName(), "错误：缺少必要参数！");
				return SKIP_BODY;
			}

			if (packageID != null && !packageID.equals("")) {
				res.append(getPorductList(Integer.parseInt(packageID)));
			} else if (productID != null && !productID.equals("")) {
				ProductDTO product = new ProductDTO();
				product = Postern.getProductDTOByProductID(Integer
						.parseInt(productID));
				 //System.out.println("根据页面传入的productID得到的product\n" + product);
				if (product != null)
					res.append(getProductPropertyList(product));
			}
		}

		String hiddenPropertyCodes = "<input type=\"hidden\" name=\"" + serveyName
				+ "\"  value=\"" + propertyCodes + "\">";

		res.append(hiddenPropertyCodes);// 放一堆propertyCode进去
//		System.out.println("最后的结果是\n" + res.toString());
		ResponseUtils.write(pageContext, res.toString());
		 //System.out.println("packageID:\n" + packageID);
		 //System.out.println("productID:\n" + productID);
		 //System.out.println("要匹配的值:\n" + matchMap);

		return EVAL_BODY_BUFFERED;
	}

	/**
	 * 根据一个产品包的ID来生成一堆东西,
	 * 
	 * @param packageID
	 * @return
	 */
	private String getPorductList(int packageID) {
		// System.out.println("传入的packageID为\n" + packageID);
		StringBuffer res = new StringBuffer();
		ArrayList productList = Postern
				.getProductListByProductPackageID(packageID);
		for (int i = 0; i < productList.size(); i++) {
			ProductDTO product = (ProductDTO) productList.get(i);
			if (product != null)
				res.append(getProductPropertyList(product));
		}
		return res.toString();
	}

	/**
	 * 根据一个产品DTO得到该产品下所有的属性 属性陈列之间没有分隔,
	 * 
	 * @param dto
	 * @return
	 */
	private String getProductPropertyList(ProductDTO dto) {
		try{
		// System.out.println("传入的productDto为\n" + dto.toString());
		StringBuffer res = new StringBuffer();
		// 加头
		ArrayList propertyList = Postern.getProductPropertyListByProductID(dto
				.getProductID());
		// 没有配置属性时什么都不显示
		if (propertyList == null || propertyList.size() < 1) {
			return "";
		}
		res.append(makeProductHead(dto));

//		//最少要用到这么多个格子
//		int loopLimit = propertyList.size();
//		//湊整了
//		if (loopLimit % columnNo != 0) {
//			loopLimit = propertyList.size() + columnNo - propertyList.size()
//					% columnNo;
//		}
		int curCol=0;
		for (int i = 0; i < propertyList.size(); i++) {
			ProductPropertyDTO property = null;
			if (i < propertyList.size()) {
				property = (ProductPropertyDTO) propertyList.get(i);
			}
			ControlProperty con = getControlProperty(property);
			// System.out.println("得到的ControlProperty为\n" + con);

			// 加行首
			if (curCol == 0) {
				res.append("<tr>");
			}
			res.append(getLabelAndControl(con));
			curCol+=2;
			//System.out.println("i:::::::::"+i+" 1curCol>>>"+curCol);
			//System.out.println("propertyList.size()>>>"+propertyList.size());
//			while((columnNo-curCol<2||propertyList.size()*2<columnNo)&&columnNo-curCol>0){
			while((columnNo-curCol<2||i+1==propertyList.size())&&columnNo-curCol>0){
				res.append(makeEmptyLabel());
				curCol+=1;
			}
			//System.out.println("i:::::::::"+i+" 2curCol>>>"+curCol);
			// 加行尾
			if (curCol == columnNo) {
				res.append("</tr>");
				curCol=0;
			}
			// 如果当前属性是密码,并且有isChangePassword为true,则认为是在修改密码,在输出上增加两个文本框,设置新密码
			if (property != null
					&& property.getPropertyCode().equalsIgnoreCase(
							passwordfield)
					&& showType
							.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_TEXT)
					&& isChangePassword!=null&&"TRUE".equalsIgnoreCase(isChangePassword)) {
				ControlProperty newPassword = new ControlProperty(
						CommonKeys.SERVEY_SHOW_TYPE_PASSWORD, passName, "新密码",
						"");
				ControlProperty validatePassword = new ControlProperty(
						CommonKeys.SERVEY_SHOW_TYPE_PASSWORD, passName, "确认密码",
						"");
				res.append("<tr>");
				res.append(getLabelAndControl(newPassword));
				res.append(getLabelAndControl(validatePassword));
				res.append("</tr>");
			}
			//System.out.println("res.toString()" + res.toString());
		}
		return res.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	// 把一个属性DTO封装成控件对象,
	private ControlProperty getControlProperty(ProductPropertyDTO property) {
		ControlProperty con = new ControlProperty();
		 //System.out.println("传入的property为\n" + property);

		if (property == null)
			return con;

		String propertyCode = property.getPropertyCode();
		String name = propertyCode;// 将来出现在控件的name属性中
		String desc = property.getPropertyName();// 出现在控件前面的的label部分

		// name=name.substring(0, 1).toUpperCase()+ name.substring(1);
		// 控件名前拼接上产品ID，因为页面上可能会出现产品列表
		name = property.getProductId() + "_" + name;
		propertyCodes += name + ";";
		name = serveyName + "_" + name;

		String defaultValue = property.getPropertyValue();
		// System.out.println("从DTO传入的defaultValue\n" + defaultValue);

		if (matchMap != null) {
			String strMatch = (String) matchMap.get(name);
			if (strMatch != null && !strMatch.equals("")) {
				defaultValue = strMatch;
			}
		}
		con.setPropertyName(name);
		con.setDescription(desc);
		con.setDefaultValue(defaultValue == null ? "" : defaultValue);

		// 当页面上设置了showType的值为label时，标签被用来展示，全部control为label
		if (showType.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_LABEL)) {
			con.setType(CommonKeys.SERVEY_SHOW_TYPE_LABEL);
			//当TAG做LABEL展示时,如果值类型是boolean,则设置取值列表,后面动作会把Y N 换成汉字
			if (property.getPropertyType() != null
					&& property.getPropertyType().equalsIgnoreCase("B")) {
				con.setSelectList(checkValueMap);
			}
		} else if (propertyCode.equalsIgnoreCase(passwordfield)
				&& showType.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_TEXT)
				&& matchMap.isEmpty()) {
			// 当前propertyCode为密码字段,并TAG做文本显示时,且match为空,则认为进行密码设置,设置密码为初始值
			// System.out.println("设置密码文本");
			con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
			//con.setDefaultValue(getRandomString(passwordlength));
			if (property.getPropertyMode().equalsIgnoreCase("A")) {
				con.setDefaultValue(getRandomString(6));
			}
			LogUtility.log(ProductPropertyTag.class, LogLevel.DEBUG, "getControlProperty,密码字段=");
		} else {
			// 当取值模式为输入时
			if (property.getPropertyMode().equalsIgnoreCase("I")) {
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
				// ? 当PropertyMode
				// 为任意取值（界面输入），页面表现为文本输入框，但当PropertyType为布尔值时，页面表现为checkbox
				if (property.getPropertyType() != null
						&& property.getPropertyType().equalsIgnoreCase("B")) {
					// con.setType(CommonKeys.SERVEY_SHOW_TYPE_CHECKBOX);
					con.setType(CommonKeys.SERVEY_SHOW_TYPE_RADIO);
					con.setSelectList(checkValueMap);
				}
			}
			// 当PropertyMode 为从属性配置表中取时，查表T_PropertyValueSetting，页面表现为下拉选择框。
			if (property.getPropertyMode().equalsIgnoreCase("S")) {
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_SELECT);
				con.setSelectList(Postern.getPropertyValueSetting(property
						.getPropertyCode()));
			}
			// 当PropertyMode 为从系统资源中选取，需要为每一种资源写一个取值的方法，暂时空着
			if (property.getPropertyMode().equalsIgnoreCase("R")) {
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
			}
			// 固定
			if (property.getPropertyMode().equalsIgnoreCase("F")) {
				//2008-03-25 hjp 注释此语句。产品属性的"取值方式"为固定时，也要维护，设置为TEXT
				//con.setType(CommonKeys.SERVEY_SHOW_TYPE_LABEL);
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
			}
			//随机
			if (property.getPropertyMode().equalsIgnoreCase("A")) {
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
				con.setDefaultValue(getRandomString(6));
			}
		}
		return con;
	}

	// 做出显示产品名称的头来
	private String makeProductHead(ProductDTO dto) {
		StringBuffer res = new StringBuffer();
		if("none".equalsIgnoreCase(headStyle)){
			return res.toString();
		}
		res.append("<tr>");
		res.append("<td colspan=\"" + columnNo + "\"");
		res.append(" algin=\"" + this.wordAlign + "\"");
		res.append(" class=\"" + headStyle + "\"");
		res.append(">");
		res.append(dto.getProductName());
		res.append("</td>");
		res.append("</tr>");
		return res.toString();
	}

	/**
	 * 生成一对属性,两个单元格,包含一个标签和一个值显示控件,
	 * 
	 * @param index
	 * @param dto
	 * @return
	 */
	private String getLabelAndControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		if (pro == null)
			pro = new ControlProperty();
		res.append(makePropertyLabel(pro.getDescription()));
		res.append(makePropertyControl(pro));
		return res.toString();
	}

	/**
	 * 制造出一个单元格,包含label
	 * 
	 * @param label
	 * @return
	 */
	private String makePropertyLabel(String label) {
		StringBuffer res = new StringBuffer();
		res.append("<td align=\"" + this.wordAlign + "\"");
		res.append(" class=\"" + this.tdWordStyle + "\"");
		res.append(">");
		res.append(label);
		res.append("</td>");
		return res.toString();
	}

	private String makeEmptyLabel() {
		StringBuffer res = new StringBuffer();
		res.append("<td align=\"" + this.wordAlign + "\"");
		res.append(" class=\"" + this.tdControlStyle + "\"");
		res.append(">");
		res.append("&nbsp;");
		res.append("</td>");
		return res.toString();
	}
	/**
	 * 制造出一个单元格,包含control
	 * 
	 * @param dto
	 * @param propertyName
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	private String makePropertyControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		res.append("<td align=\"" + this.controlAlign + "\"");
		res.append(" class=\"" + this.tdControlStyle + "\"");
		res.append(">");
		String defaultValue = pro.getDefaultValue();
		//System.out.println("makePropertyControl==pro.getType()=="+pro.getType());
		// 根据
		if (pro.getType().equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_TEXT)) {
			res.append(makeTextControl(pro));
		} else if (pro.getType().equalsIgnoreCase(
				CommonKeys.SERVEY_SHOW_TYPE_SELECT)) {
			res.append(makeSelectControl(pro));
		} else if (pro.getType().equalsIgnoreCase(
				CommonKeys.SERVEY_SHOW_TYPE_RADIO)) {
			res.append(makeRadioControl(pro));
		} else if (pro.getType().equalsIgnoreCase(
				CommonKeys.SERVEY_SHOW_TYPE_CHECKBOX)) {
			res.append(makeCheckBoxControl(pro));
		} else if (pro.getType().equalsIgnoreCase(
				CommonKeys.SERVEY_SHOW_TYPE_HIDE)) {
			res.append(makeHiddenTextControl(pro));
		} else if (pro.getType().equalsIgnoreCase(
				CommonKeys.SERVEY_SHOW_TYPE_PASSWORD)) {
			res.append(makePasswordControl(pro));
		} else if (pro.getType().equalsIgnoreCase(
				CommonKeys.SERVEY_SHOW_TYPE_LABEL)) {
			res.append(makeLabelControl(pro));
		} else {
			res.append(defaultValue);
		}
		res.append("</td>");
		//System.out.println("makePropertyControl==res.toString()=="+res.toString());
		return res.toString();
	}

	private String makeLabelControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		String defaultValue = pro.getDefaultValue();
		if (pro.getSelectList() != null && !pro.getSelectList().isEmpty())
			defaultValue = (String)pro.getSelectList().get(defaultValue);

		res.append(defaultValue);
		res.append(makeHiddenTextControl(pro));

		return res.toString();
	}

	private String makeHiddenTextControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		String defaultValue = pro.getDefaultValue();

		String name = pro.getPropertyName();
		if (name==null||name.equals(""))return "";
		res.append("<input type=\"hidden\" ");
		res.append("name=\"" + name + "\"");
		res.append(" value=\"" + defaultValue + "\"");
		res.append(">");
		return res.toString();
	}

	private String makeTextControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		String defaultValue = pro.getDefaultValue();

		String name = pro.getPropertyName();
		res.append("<input type=\"text\" ");
		res.append("name=\"" + name + "\"");
		res.append(" size=\"" + controlSize + "\"");
		res.append(" class=\"" + controlStyle + "\"");
		res.append(" value=\"" + defaultValue + "\"");
		res.append(">");
		return res.toString();
	}

	private String makePasswordControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		String defaultValue = pro.getDefaultValue();

		String name = pro.getPropertyName();
		res.append("<input type=\"password\" ");
		res.append("name=\"" + name + "\"");
		res.append(" size=\"" + controlSize + "\"");
		res.append(" class=\"" + controlStyle + "\"");
		res.append(" value=\"" + defaultValue + "\"");
		res.append(">");
		return res.toString();
	}

	private String makeRadioControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		String defaultValue = pro.getDefaultValue();
		String name = pro.getPropertyName();
		Map map = pro.getSelectList();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = map.get(key).toString();
			res.append("<input type=\"radio\" name=\"" + name + "\"");
			if (key.equalsIgnoreCase(defaultValue))
				res.append(" checked");
			res.append(" class=\"" + controlStyle + "\"");
			res.append(" value=\"" + key + "\">");
			res.append(value);
			res.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return res.toString();
	}

	private String makeCheckBoxControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		String defaultValue = pro.getDefaultValue();
		String name = pro.getPropertyName();
		res.append("<input type=\"checkbox\" ");
		res.append(" name=\"" + name + "\"");
		if (defaultValue.equalsIgnoreCase("Y")
				|| defaultValue.equalsIgnoreCase("YES"))
			res.append(" checked");
		res.append(" class=\"" + controlStyle + "\"");
		res.append(" value=\"" + name + "\"");
		// res.append(" value=\"" + name + "\"");
		res.append(">");
		return res.toString();
	}

	private String makeSelectControl(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		String defaultValue = pro.getDefaultValue();

		String name = pro.getPropertyName();
		Map map = pro.getSelectList();
		res.append("<select name=\"" + name + "\"");
		// res.append(" size=\"" + controlSize + "\"");
		res.append(" class=\"" + controlStyle + "\"");
		res.append(">");
		res.append("<option value=\"\">-----------------------</option>");
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = map.get(key).toString();
			res.append("<option ");
			if (key.equalsIgnoreCase(defaultValue)) {
				res.append(" selected ");
			}
			res.append("value=\"");
			res.append(key);
			res.append("\">");
			res.append(value);
			res.append("</option>");
		}
		res.append("</select>");
		return res.toString();
	}

	private String makeButton(ControlProperty pro) {
		StringBuffer res = new StringBuffer();
		String defaultValue = pro.getDefaultValue();

		String name = pro.getPropertyName();
		Map map = pro.getSelectList();
		res.append("<select name=\"" + name + "\"");
		// res.append(" size=\"" + controlSize + "\"");
		res.append(" class=\"" + controlStyle + "\"");
		res.append(">");
		res.append("<option value=\"\">-----------------------</option>");
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = map.get(key).toString();
			res.append("<option ");
			if (key.equalsIgnoreCase(defaultValue)) {
				res.append(" selected ");
			}
			res.append(" value=\"");
			res.append(key);
			res.append("\">");
			res.append(value);
			res.append("</option>");
		}
		res.append("</select>");
		return res.toString();
	}

	public int doEndTag() throws JspException {
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),
				"invoke doAfterBody ...");
		bodyContent = getBodyContent();
		if (bodyContent != null) {
			try {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
			} catch (Exception e) {
				WebPrint.PrintErrorInfo(this.getClass().getName(),
						"JspWriter error:" + e.getMessage());
			}
			bodyContent.clearBody();
		}
		return (SKIP_BODY);
	}

	public void release() {
		this.controlSize = null;
		this.controlStyle = null;
		this.match = null;
		this.column = null;
		this.columnNo = 0;
		this.tdControlStyle = null;
		this.tdHeight = null;
		this.tdWordStyle = null;
		this.wordStyle = null;
		this.wordAlign = null;
		this.controlAlign = null;
		this.serveyName = null;
	}

	public String getControlSize() {
		return controlSize;
	}

	public String getControlAlign() {
		return controlAlign;
	}

	public void setControlAlign(String controlAlign) {
		this.controlAlign = controlAlign;
	}

	public String getServeyName() {
		return serveyName;
	}

	public void setServeyName(String serveyName) {
		this.serveyName = serveyName;
	}

	public String getWordAlign() {
		return wordAlign;
	}

	public void setWordAlign(String wordAlign) {
		this.wordAlign = wordAlign;
	}

	public void setControlSize(String controlSize) {
		this.controlSize = controlSize;
	}

	public String getControlStyle() {
		return controlStyle;
	}

	public void setControlStyle(String controlStyle) {
		this.controlStyle = controlStyle;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}


	public String getTdControlStyle() {
		return tdControlStyle;
	}

	public void setTdControlStyle(String tdControlStyle) {
		this.tdControlStyle = tdControlStyle;
	}

	public String getTdHeight() {
		return tdHeight;
	}

	public void setTdHeight(String tdHeight) {
		this.tdHeight = tdHeight;
	}

	public String getTdWordStyle() {
		return tdWordStyle;
	}

	public void setTdWordStyle(String tdWordStyle) {
		this.tdWordStyle = tdWordStyle;
	}

	public String getWordStyle() {
		return wordStyle;
	}

	public void setWordStyle(String wordStyle) {
		this.wordStyle = wordStyle;
	}

	/**
	 * @return Returns the packageID.
	 */
	public String getPackageID() {
		return packageID;
	}

	/**
	 * @param packageID
	 *            The packageID to set.
	 */
	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

	/**
	 * @return Returns the productID.
	 */
	public String getProductID() {
		return productID;
	}

	/**
	 * @param productID
	 *            The productID to set.
	 */
	public void setProductID(String productID) {
		this.productID = productID;
	}

	/**
	 * @return Returns the headStyle.
	 */
	public String getHeadStyle() {
		return headStyle;
	}

	/**
	 * @param headStyle
	 *            The headStyle to set.
	 */
	public void setHeadStyle(String headStyle) {
		this.headStyle = headStyle;
	}

	/**
	 * @return Returns the showType.
	 */
	public String getShowType() {
		return showType;
	}

	/**
	 * @param showType
	 *            The showType to set.
	 */
	public void setShowType(String showType) {
		this.showType = showType;
	}

	private String getRandomString(int length) {
		StringBuffer res = new StringBuffer();
		Random rand = new Random();
		int index = 0;
		while (true) {
			int curIndex = rand.nextInt(passwordsuperset.length());
			// System.out.println(curIndex);
			index++;
			if (index > length)
				break;
			char ch = passwordsuperset.charAt(curIndex);
			res.append(ch);
		}
		// System.out.println(res.toString());
		return res.toString();
	}

	/**
	 * @return Returns the passName.
	 */
	public String getPassName() {
		return passName;
	}

	/**
	 * @param passName
	 *            The passName to set.
	 */
	public void setPassName(String passName) {
		this.passName = passName;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getIsChangePassword() {
		return isChangePassword;
	}

	public void setIsChangePassword(String isChangePassword) {
		this.isChangePassword = isChangePassword;
	}

}

class ControlProperty {
	private String type;

	private String propertyName;

	private String description;

	private Map selectList;

	private String defaultValue;

	public ControlProperty() {
		this.type = "";
		this.propertyName = "";
		this.description = "";
		this.defaultValue = "";
		this.selectList = new LinkedHashMap();
	}

	public ControlProperty(String type, String propertyName, String description) {
		this.type = type;
		this.propertyName = propertyName;
		this.description = description;
	}

	public ControlProperty(String type, String propertyName,
			String description, String defaultValue) {
		this.type = type;
		this.propertyName = propertyName;
		this.description = description;
		this.defaultValue = defaultValue;
	}

	public ControlProperty(String type, String propertyName,
			String description, String defaultValue, Map selectList) {
		this.type = type;
		this.propertyName = propertyName;
		this.description = description;
		this.defaultValue = defaultValue;
		this.selectList = selectList;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the propertyName.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName
	 *            The propertyName to set.
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the defaultValue.
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            The defaultValue to set.
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return Returns the selectList.
	 */
	public Map getSelectList() {
		if (selectList == null)
			selectList = new LinkedHashMap();
		return selectList;
	}

	/**
	 * @param selectList
	 *            The selectList to set.
	 */
	public void setSelectList(Map selectList) {
		this.selectList = selectList;
	}

	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("\n+++++++++++++++++++++++++++++++++++");
		res.append("\ntype            " + type);
		res.append("\npropertyName    " + this.propertyName);
		res.append("\ndescription     " + this.description);
		res.append("\ndefaultValue    " + this.defaultValue);
		res.append("\nselectList      " + this.selectList);
		return res.toString();
	}
}
