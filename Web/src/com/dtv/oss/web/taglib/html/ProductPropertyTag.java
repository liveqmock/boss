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
 * ���ڳ�product��package��ȫ��product���Եı�ǩ��
 * showTypeΪhiddenʱ�ñ�ǩ���ڰ���ҳ���õ�ֵ���ݵ���ҳ�棬��showTypeΪlabel������ҳ����չʾ��ҳ�����õ�ֵ,��Ϊ�������������ʱ��������ҳ�棬
 * ��ʱ������һ��productID�� packageID������������һ����û�ã�
 * MatchΪMap��ʽ������(productID+"_"+propertyCode,value)
 * ���пؼ�����ΪproductID+propertyCode,����һ��hidden���ı�������ΪpropertyCode���ϣ��ַ�����ʽ���ԡ�;���ŷָ���ȡproperty����ֵʱӦ�ÿ����õ�
 * ��Ԫ�������Ա���ɾ���ˣ���Ϊ������������ǩ�����Ϊ
 * <tr></tr>
 * ���ڱ�ǩǰ��һ�������趨����
 * <tr>
 * <td width='20'></td>
 * </tr>
 * ,����<table>���������
 * headStyle(��һ�б����Ʒ����)wordStyle�����Կؼ�ǰlabel��tdWordStyle��label���ڵ�Ԫ����ʽ����û��Ĭ��ֵ��������Ϊ��
 * showTypeΪ��ǩ��ҳ���ϵĳ�����ʽ������չʾ��label���������ռ������ύ����һҳ�棨hidden������ֵ�趨(����������ʽ�ؼ�����Ҫ���ø�������ô����ֵ)
 * ����ҳ������ͬһ��serverName,������Բ��裬������
 * 6-23����������벿����������,�����ֶ�passName,����������������������Name,��webaction�а���nameȡ������,�õ���Ԫ�ص�����,���Լ���Ƿ�ƥ���
 * �������������CODE������д��һ������,webaction��ȡֵʱҲ��Ҫ�õ�,���Կ�������tag��property��
 * ��ʼ�������passwordsuperset����,�в���Ҫ���ַ�ɾ������. 6-26�޸Ĳ�Ʒ���б��ύ������.
 * 2008-1-10 ���� ��isChangePassword����,Ϊ����TEXTʱ����ʾrequest��ֵ,�������޸����ֿ�,��ЧֵΪtrue/false,�ɿ�,
 * @author 260327h
 * 
 */
public class ProductPropertyTag extends BaseHandlerTag {

	private static final String passwordsuperset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static final String passwordfield = "30003";

	private static final int passwordlength = 8;

	private static final String passwordfieldname = "passwordfield";

	// ���������
	private String passName;

	// ��ǩ��ҳ���ϵ���;
	private String showType;

	// ��Ʒ��ID
	private String packageID;

	// ��ƷID
	private String productID;

	// ƥ�����
	private String match;

	// ������ҳ����ʾ����ʽ
	private String wordStyle;

	// ���������ʽ
	private String tdWordStyle;

	// ���ؼ���ʽ
	private String tdControlStyle;

	// �ؼ���ҳ����ʾ����ʽ
	private String controlStyle;

	// �ؼ���С
	private String controlSize;

	// ÿ����ʾ���������
	private String column;

	// TD�ĸ߶�
	private String tdHeight;

	// ���ִ��ڱ���λ��
	private String wordAlign;

	// �ؼ����ڱ���λ��
	private String controlAlign;

	// �ؼ�����
	private String serveyName;

	private String headStyle;

	// ��ǰ��¼������,TAG�ڲ�ʹ��
	private int columnNo;

	private Map matchMap;

	private Map checkValueMap;

	//�Ƿ������޸�.
	private String isChangePassword;
//	private int iColSpan;

	// ��̬������
	// �ؼ���ʾ�ĳ���
	private static int size = 25;

	// ҳ�沼����ÿ����ʾ����ĸ���
	private int controlNO = 2;

	// ���ִ��ڱ���λ��
	private static String inWordAlign = "right";

	// �ؼ����ڱ���λ��
	private static String inControlAlign = "left";

	// �ؼ���
	private static String inServeyName = "txtProductProperty";

	// ��ʾ��propertyCode�б�
	private String propertyCodes = "";

	public int doStartTag() throws JspException {
		 //System.out.println("����ҳ�洫���packageID\n" + packageID);
		 //System.out.println("����ҳ�洫���productID\n" + productID);
		StringBuffer res = new StringBuffer();

		propertyCodes = "";// ��ǩ��ʼʱ���һ���������???���Ҫ���һ��!!!!һ��tag�ķ�Χ������,?

		checkValueMap = new LinkedHashMap();
		checkValueMap.put("Y", "��");
		checkValueMap.put("N", "��");

		//System.out.println("�����ǩ");
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),
				"invoke doStartTag...");

		/**
		 * ����һЩҳ����ʾĬ��ֵ
		 */
		// Ĭ��ÿ����ʾ2������
		columnNo = WebUtil.StringToInt(column);
		if (columnNo == 0){
			columnNo = controlNO*2;
		}else{
			controlNO=(int)columnNo/2;
		}
		//System.out.println("��ǰҪ��ʾ����:\n" + columnNo);
//		iColSpan = columnNo * 2;// ÿ������Ҫ������Ԫ��,
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
		// passwordfieldname����Ĭ��ֵ
		if (passName == null || "".equals(passName))
			passName = passwordfieldname;

		// ȡ���õ�ƥ��ֵ,
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
						 //System.out.println("����hidden�ؼ� name:" + name);
						 //System.out.println("����hidden�ؼ� defaultValue:"+ defaultValue);
						res.append(this.makeHiddenTextControl(con));
					}
					propertyCodes += hiddenValue[j];
				}
			}
		} else {
			// �����label�ã���һ����ҳ�����趨��ֵ������ȡ��
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
							 //System.out.println("ѹ���ƥ��ֵname:\n" + name);
							 //System.out.println("ѹ���ƥ��ֵdefaultValue:\n" +defaultValue);
						}
					}
				}
			}
			// packageID ��productID����ͬʱΪ��
			if ((packageID == null || packageID.equals(""))
					&& (productID == null || productID.equals(""))) {
				WebPrint
						.PrintErrorInfo(this.getClass().getName(), "����ȱ�ٱ�Ҫ������");
				return SKIP_BODY;
			}

			if (packageID != null && !packageID.equals("")) {
				res.append(getPorductList(Integer.parseInt(packageID)));
			} else if (productID != null && !productID.equals("")) {
				ProductDTO product = new ProductDTO();
				product = Postern.getProductDTOByProductID(Integer
						.parseInt(productID));
				 //System.out.println("����ҳ�洫���productID�õ���product\n" + product);
				if (product != null)
					res.append(getProductPropertyList(product));
			}
		}

		String hiddenPropertyCodes = "<input type=\"hidden\" name=\"" + serveyName
				+ "\"  value=\"" + propertyCodes + "\">";

		res.append(hiddenPropertyCodes);// ��һ��propertyCode��ȥ
//		System.out.println("���Ľ����\n" + res.toString());
		ResponseUtils.write(pageContext, res.toString());
		 //System.out.println("packageID:\n" + packageID);
		 //System.out.println("productID:\n" + productID);
		 //System.out.println("Ҫƥ���ֵ:\n" + matchMap);

		return EVAL_BODY_BUFFERED;
	}

	/**
	 * ����һ����Ʒ����ID������һ�Ѷ���,
	 * 
	 * @param packageID
	 * @return
	 */
	private String getPorductList(int packageID) {
		// System.out.println("�����packageIDΪ\n" + packageID);
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
	 * ����һ����ƷDTO�õ��ò�Ʒ�����е����� ���Գ���֮��û�зָ�,
	 * 
	 * @param dto
	 * @return
	 */
	private String getProductPropertyList(ProductDTO dto) {
		try{
		// System.out.println("�����productDtoΪ\n" + dto.toString());
		StringBuffer res = new StringBuffer();
		// ��ͷ
		ArrayList propertyList = Postern.getProductPropertyListByProductID(dto
				.getProductID());
		// û����������ʱʲô������ʾ
		if (propertyList == null || propertyList.size() < 1) {
			return "";
		}
		res.append(makeProductHead(dto));

//		//����Ҫ�õ���ô�������
//		int loopLimit = propertyList.size();
//		//������
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
			// System.out.println("�õ���ControlPropertyΪ\n" + con);

			// ������
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
			// ����β
			if (curCol == columnNo) {
				res.append("</tr>");
				curCol=0;
			}
			// �����ǰ����������,������isChangePasswordΪtrue,����Ϊ�����޸�����,����������������ı���,����������
			if (property != null
					&& property.getPropertyCode().equalsIgnoreCase(
							passwordfield)
					&& showType
							.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_TEXT)
					&& isChangePassword!=null&&"TRUE".equalsIgnoreCase(isChangePassword)) {
				ControlProperty newPassword = new ControlProperty(
						CommonKeys.SERVEY_SHOW_TYPE_PASSWORD, passName, "������",
						"");
				ControlProperty validatePassword = new ControlProperty(
						CommonKeys.SERVEY_SHOW_TYPE_PASSWORD, passName, "ȷ������",
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

	// ��һ������DTO��װ�ɿؼ�����,
	private ControlProperty getControlProperty(ProductPropertyDTO property) {
		ControlProperty con = new ControlProperty();
		 //System.out.println("�����propertyΪ\n" + property);

		if (property == null)
			return con;

		String propertyCode = property.getPropertyCode();
		String name = propertyCode;// ���������ڿؼ���name������
		String desc = property.getPropertyName();// �����ڿؼ�ǰ��ĵ�label����

		// name=name.substring(0, 1).toUpperCase()+ name.substring(1);
		// �ؼ���ǰƴ���ϲ�ƷID����Ϊҳ���Ͽ��ܻ���ֲ�Ʒ�б�
		name = property.getProductId() + "_" + name;
		propertyCodes += name + ";";
		name = serveyName + "_" + name;

		String defaultValue = property.getPropertyValue();
		// System.out.println("��DTO�����defaultValue\n" + defaultValue);

		if (matchMap != null) {
			String strMatch = (String) matchMap.get(name);
			if (strMatch != null && !strMatch.equals("")) {
				defaultValue = strMatch;
			}
		}
		con.setPropertyName(name);
		con.setDescription(desc);
		con.setDefaultValue(defaultValue == null ? "" : defaultValue);

		// ��ҳ����������showType��ֵΪlabelʱ����ǩ������չʾ��ȫ��controlΪlabel
		if (showType.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_LABEL)) {
			con.setType(CommonKeys.SERVEY_SHOW_TYPE_LABEL);
			//��TAG��LABELչʾʱ,���ֵ������boolean,������ȡֵ�б�,���涯�����Y N ���ɺ���
			if (property.getPropertyType() != null
					&& property.getPropertyType().equalsIgnoreCase("B")) {
				con.setSelectList(checkValueMap);
			}
		} else if (propertyCode.equalsIgnoreCase(passwordfield)
				&& showType.equalsIgnoreCase(CommonKeys.SERVEY_SHOW_TYPE_TEXT)
				&& matchMap.isEmpty()) {
			// ��ǰpropertyCodeΪ�����ֶ�,��TAG���ı���ʾʱ,��matchΪ��,����Ϊ������������,��������Ϊ��ʼֵ
			// System.out.println("���������ı�");
			con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
			//con.setDefaultValue(getRandomString(passwordlength));
			if (property.getPropertyMode().equalsIgnoreCase("A")) {
				con.setDefaultValue(getRandomString(6));
			}
			LogUtility.log(ProductPropertyTag.class, LogLevel.DEBUG, "getControlProperty,�����ֶ�=");
		} else {
			// ��ȡֵģʽΪ����ʱ
			if (property.getPropertyMode().equalsIgnoreCase("I")) {
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
				// ? ��PropertyMode
				// Ϊ����ȡֵ���������룩��ҳ�����Ϊ�ı�����򣬵���PropertyTypeΪ����ֵʱ��ҳ�����Ϊcheckbox
				if (property.getPropertyType() != null
						&& property.getPropertyType().equalsIgnoreCase("B")) {
					// con.setType(CommonKeys.SERVEY_SHOW_TYPE_CHECKBOX);
					con.setType(CommonKeys.SERVEY_SHOW_TYPE_RADIO);
					con.setSelectList(checkValueMap);
				}
			}
			// ��PropertyMode Ϊ���������ñ���ȡʱ�����T_PropertyValueSetting��ҳ�����Ϊ����ѡ���
			if (property.getPropertyMode().equalsIgnoreCase("S")) {
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_SELECT);
				con.setSelectList(Postern.getPropertyValueSetting(property
						.getPropertyCode()));
			}
			// ��PropertyMode Ϊ��ϵͳ��Դ��ѡȡ����ҪΪÿһ����Դдһ��ȡֵ�ķ�������ʱ����
			if (property.getPropertyMode().equalsIgnoreCase("R")) {
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
			}
			// �̶�
			if (property.getPropertyMode().equalsIgnoreCase("F")) {
				//2008-03-25 hjp ע�ʹ���䡣��Ʒ���Ե�"ȡֵ��ʽ"Ϊ�̶�ʱ��ҲҪά��������ΪTEXT
				//con.setType(CommonKeys.SERVEY_SHOW_TYPE_LABEL);
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
			}
			//���
			if (property.getPropertyMode().equalsIgnoreCase("A")) {
				con.setType(CommonKeys.SERVEY_SHOW_TYPE_TEXT);
				con.setDefaultValue(getRandomString(6));
			}
		}
		return con;
	}

	// ������ʾ��Ʒ���Ƶ�ͷ��
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
	 * ����һ������,������Ԫ��,����һ����ǩ��һ��ֵ��ʾ�ؼ�,
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
	 * �����һ����Ԫ��,����label
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
	 * �����һ����Ԫ��,����control
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
		// ����
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
