
package com.dtv.oss.web.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.csr.OpenAccountGeneralEJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * ���ù����Ʒ/�ײ�/�Ż�/�豸��Ϣ
 * @author 260327h
 *
 */
public abstract class BatchBuyWebAction extends GeneralWebAction {

/**
 * ȡ�ñ��ι�����������,����list,list�а���������,�������n��,��list����n��map,
 * �ڴι������ݵĶ���,����parseBuyContentInfo();
 * @param request
 * @return
 * @throws WebActionException
 */
public ArrayList getBuyProductInfo(HttpServletRequest request) throws WebActionException {
		String buyContent=request.getParameter("txtBuyContent");
		if(buyContent==null||"".equals(buyContent)){
			return null;
		}
		buyContent=getXMLContent(buyContent);

		ArrayList buyList=new ArrayList();
		Element root=loadDocument(buyContent);
		NodeList buyGroups=root.getChildNodes();
		for(int i=0;i<buyGroups.getLength();i++){
			Node buyGroup=buyGroups.item(i);
			buyList.add(parseBuyContentInfo(buyGroup));
		}
		LogUtility.log(BatchBuyWebAction.class,LogLevel.DEBUG,"buyList:",buyList);
		return buyList;
	}
	
	private String getXMLContent(String s){
		return s.replaceAll("\\[", "<").replaceAll("\\]", ">").replaceAll("'", "\"");
	}
	/**
	 * ȡ��һ�ι��������,�����豸��Ϣ.
	 * ���ؽ��map,����campaignIdList,PackageIdList,��ԭ�з�װ��ʽ,���ؽ��δ���ֶ��Ʒ��Ʒ���͵���Ʒ��Ʒ��,
	 * ��δ�����ײͺ��Ż�,
	 * packageIdList���Ѿ��������ײͻ����Żݶ�Ӧ�Ĳ�Ʒ��,�����Ϣ��ҳ����ȡ�õ�,��bundle2campaign����,��xml���м�¼,
	 * ������ֱ�����ý�ȥ,��̨������ȡһ����.
	 * ���ص�map�л�����ѡ����豸/�绰/������Ϣ,���xml�а���devices/propertys/phone�ڵ�Ļ�,
	 * ��������Ҳ�Ǹ�Map,��key=�ڼ��׹���,value=����(һ���豸,����һ�׵绰/����),
	 * ,
	 * �豸��Ϣ��list��ʽ��װ,ÿ��Ԫ���Ǹ�hashmap,һ��hashmap�з���һ������,����ж��׹���,���ж��hashmap,
	 * ÿ��hashmap�д��ѡ���serialNo��deviceClass
	 * @param buyGroupNode
	 * @return
	 * @throws WebActionException
	 */
	private Map parseBuyContentInfo(Node buyGroupNode) throws WebActionException{
		
		LogUtility.log(BatchBuyWebAction.class,LogLevel.DEBUG,"buyGroupNode:",buyGroupNode);
		if(!"group".equalsIgnoreCase(buyGroupNode.getNodeName())){return null;}
		
		HashMap buyMap=new HashMap();
		String buyNum="";
		String buyIndex="";
		ArrayList packageList=new ArrayList();
		ArrayList campaignList=new ArrayList();
		Map devicesMap=new LinkedHashMap();
		Map phoneMap=new LinkedHashMap();
		Map propertysMap=new LinkedHashMap();

		if(buyGroupNode.hasAttributes()){
			NamedNodeMap buyGroupAttributes=buyGroupNode.getAttributes();
			buyNum=buyGroupAttributes.getNamedItem("buyNum").getNodeValue();
			buyMap.put(OpenAccountGeneralEJBEvent.KEY_BUYNUM, WebUtil.StringToInteger(buyNum));
			buyIndex=buyGroupAttributes.getNamedItem("index").getNodeValue();
			buyMap.put(OpenAccountGeneralEJBEvent.KEY_BUYINDEX, WebUtil.StringToInteger(buyIndex));
		}else{
			throw new WebActionException("û����Ч�Ĺ�������.");
		}
		LogUtility.log(BatchBuyWebAction.class,LogLevel.DEBUG,"buyNum:",buyNum);
		NodeList nodeList=buyGroupNode.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			Node buyItem=nodeList.item(i);
			String nodeName=buyItem.getNodeName();
			String buyId="";
			String buyName="";
			if(buyItem.hasAttributes()){
				NamedNodeMap nodeAttributes=buyItem.getAttributes();
				if(nodeAttributes.getNamedItem("id")!=null){
					buyId=nodeAttributes.getNamedItem("id").getNodeValue();
				}
				if(nodeAttributes.getNamedItem("name")!=null){
					buyName=nodeAttributes.getNamedItem("name").getNodeValue();
				}
				LogUtility.log(BatchBuyWebAction.class,LogLevel.DEBUG,"id:"+buyId+"\tName:"+buyName);
				if("bundle".equalsIgnoreCase(nodeName)||"campaign".equalsIgnoreCase(nodeName)){
					campaignList.add(WebUtil.StringToInteger(buyId));
					if(nodeAttributes.getNamedItem("packageList")!=null){
						String bundle2CampaignList=nodeAttributes.getNamedItem("packageList").getNodeValue();
						LogUtility.log(BatchBuyWebAction.class,LogLevel.DEBUG,"bundle2CampaignList:",bundle2CampaignList);
						ArrayList bpList=WebUtil.getIntegerListByString(bundle2CampaignList);
						LogUtility.log(BatchBuyWebAction.class,LogLevel.DEBUG,"bpList:",bpList);
						if(bpList!=null&&!bpList.isEmpty()){
							packageList.addAll(bpList);
						}
					}
					
				}else if("mpackage".equalsIgnoreCase(nodeName)||"spackage".equalsIgnoreCase(nodeName)){
					packageList.add(WebUtil.StringToInteger(buyId));
				}
			}
			if("devices".equalsIgnoreCase(nodeName)&&buyItem.hasChildNodes()&&buyItem.hasAttributes()){
				NamedNodeMap indexAtt=buyItem.getAttributes();
				Integer index=null;
				if(indexAtt.getNamedItem("index")!=null){
					index=WebUtil.StringToInteger(indexAtt.getNamedItem("index").getNodeValue());
				}else{
					LogUtility.log(BatchBuyWebAction.class,LogLevel.DEBUG,"������Ϣ������,devicesȱ��indexֵ.");
					throw new WebActionException("������Ϣ������.");
				}
				NodeList deviceNodeList=buyItem.getChildNodes();
				HashMap deviceMap=new HashMap();
				for(int di=0;di<deviceNodeList.getLength();di++){
					Node deviceItem=deviceNodeList.item(di);
					String serialNo="";
					String deviceClass="";
					if(deviceItem.hasAttributes()){
						NamedNodeMap deviceAtt=deviceItem.getAttributes();
						if(deviceAtt.getNamedItem("serialNo")!=null&&deviceAtt.getNamedItem("deviceClass")!=null){
							serialNo=deviceAtt.getNamedItem("serialNo").getNodeValue();
							deviceClass=deviceAtt.getNamedItem("deviceClass").getNodeValue();
							deviceMap.put(serialNo, deviceClass);
						}
					}
				}
				devicesMap.put(index,deviceMap);
			}
			if("propertys".equalsIgnoreCase(nodeName)&&buyItem.hasChildNodes()){
				NamedNodeMap indexAtt=buyItem.getAttributes();
				Integer index=null;
				if(indexAtt.getNamedItem("index")!=null){
					index=WebUtil.StringToInteger(indexAtt.getNamedItem("index").getNodeValue());
				}else{
					LogUtility.log(BatchBuyWebAction.class,LogLevel.DEBUG,"������Ϣ������,propertysȱ��indexֵ.");
					throw new WebActionException("������Ϣ������.");
				}				
				NodeList propertyNodeList=buyItem.getChildNodes();
				Map propertyMap=new HashMap();
				for(int pi=0;pi<propertyNodeList.getLength();pi++){
					Node propertyNode=propertyNodeList.item(pi);
					Map propertyCodeMap=new HashMap();
					String productId="";
					if(propertyNode.hasAttributes()){
						NamedNodeMap proAtt=propertyNode.getAttributes();
						if(proAtt.getNamedItem("propertyCode")!=null&&proAtt.getNamedItem("value")!=null&&proAtt.getNamedItem("productId")!=null){
							String code=proAtt.getNamedItem("propertyCode").getNodeValue();
							String value=proAtt.getNamedItem("value").getNodeValue();
							productId=proAtt.getNamedItem("productId").getNodeValue();
							propertyCodeMap.put(code, value);
						}
					}
					if(propertyMap.containsKey(productId)){
						((Map)propertyMap.get(productId)).putAll(propertyCodeMap);
					}else{
						propertyMap.put(productId, propertyCodeMap);
					}
				}
				propertysMap.put(index, propertyMap);
			}
			if("phone".equalsIgnoreCase(nodeName)&&buyItem.hasAttributes()){
				NamedNodeMap phoneAtt=buyItem.getAttributes();
				Map phone =new HashMap();
				if(phoneAtt.getNamedItem("index")!=null&&phoneAtt.getNamedItem("phoneNo")!=null
						&&phoneAtt.getNamedItem("itemId")!=null){
					Integer index=WebUtil.StringToInteger(phoneAtt.getNamedItem("index").getNodeValue());
					String phoneNo=phoneAtt.getNamedItem("phoneNo").getNodeValue();
					Integer itemId=WebUtil.StringToInteger(phoneAtt.getNamedItem("itemId").getNodeValue());
					phone.put(phoneNo, itemId);
					phoneMap.put(index, phone);
				}
			}
		}
		if(packageList!=null&&!packageList.isEmpty()){
			buyMap.put(OpenAccountGeneralEJBEvent.KEY_MPACKAGE, packageList);
		}
		if(campaignList!=null&&!campaignList.isEmpty()){
			buyMap.put(OpenAccountGeneralEJBEvent.KEY_CAMPAIGN, campaignList);
		}
		if(devicesMap!=null&&!devicesMap.isEmpty()){
			buyMap.put(OpenAccountGeneralEJBEvent.KEY_DEVICES, devicesMap);
		}
		if(phoneMap!=null&&!phoneMap.isEmpty()){
			buyMap.put(OpenAccountGeneralEJBEvent.KEY_PHONE, phoneMap);
		}
		if(propertysMap!=null&&!propertysMap.isEmpty()){
			buyMap.put(OpenAccountGeneralEJBEvent.KEY_PROPERTYS, propertysMap);
		}
		return buyMap;
	}

	

	private Element loadDocument(String content) {
		try {
			ByteArrayInputStream in=new ByteArrayInputStream(content.getBytes("UTF-8"));
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
			Document doc = parser.parse(in);
			return doc.getDocumentElement();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * û���õ��ķ���,�����������������豸ѡ��ҳ�������,���ǵ�ά��������,��������������,ֱ����jsp��д��,
	 * @param request
	 * @return
	 */
//	public String createDeviceChooseHtml(HttpServletRequest request){
//		String buyContent=request.getParameter("txtBuyContent");
//		if(buyContent==null||"".equals(buyContent)){
//			return null;
//		}
//		
//		String macInfoIndex="";
//		StringBuffer contentHtml=new StringBuffer();
//		buyContent=getXMLContent(buyContent);
//		Element root=loadDocument(buyContent);
//		NodeList buyGroups=root.getChildNodes();
//		for(int i=0;i<buyGroups.getLength();i++){
//			contentHtml.append("<table id=\"buyList\" align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"list_bg\">");
//			contentHtml.append("<tr id=\"buyListHead\" class=\"list_head\">");
//			contentHtml.append("<td width=\"10%\" class=\"list_head\">���</td>");
//			contentHtml.append("<td width=\"20%\" class=\"list_head\">�ײ�</td>");
//			contentHtml.append("<td width=\"20%\" class=\"list_head\">���Ʒ��Ʒ��</td>");
//			contentHtml.append("<td width=\"20%\" class=\"list_head\">����Ʒ��Ʒ��</td>");
//			contentHtml.append("<td width=\"20%\" class=\"list_head\">�Ż�</td>");
//			contentHtml.append("<td width=\"10%\" class=\"list_head\">����</td>");
//			contentHtml.append("</tr>");
//			contentHtml.append("<tr >");
//			
//			String packageIdList="";
//			Node buyGroup=buyGroups.item(i);
//			int buyNum=0;
//			String buyIndex="";
//			if(buyGroup.hasAttributes()){
//				NamedNodeMap buyGroupAttributes=buyGroup.getAttributes();
//				buyNum=WebUtil.StringToInt(buyGroupAttributes.getNamedItem("buyNum").getNodeValue());
//				buyIndex=buyGroupAttributes.getNamedItem("index").getNodeValue();
//			}
//			macInfoIndex+=buyIndex;
//			StringBuffer bundleHtml=new StringBuffer();
//			StringBuffer mPackageHtml=new StringBuffer();
//			StringBuffer sPackageHtml=new StringBuffer();
//			StringBuffer campaignHtml=new StringBuffer();
//
//			if(buyGroup.hasChildNodes()){
//				NodeList buyList=buyGroup.getChildNodes();
//				for(int j=0;j<buyList.getLength();j++){
//					Node buyItem=buyList.item(j);
//					String nodeName=buyItem.getNodeName();
//					String buyId="";
//					String buyName="";
//					if(buyItem.hasAttributes()){
//						NamedNodeMap nodeAttributes=buyItem.getAttributes();
//						buyId=nodeAttributes.getNamedItem("id").getNodeValue();
//						buyName=nodeAttributes.getNamedItem("name").getNodeValue();
//						if("bundle".equalsIgnoreCase(nodeName)||"campaign".equalsIgnoreCase(nodeName)){
//							String subPackageList=nodeAttributes.getNamedItem("packageList").getNodeValue();
//							packageIdList=packageIdList+(packageIdList!=null&&!"".equals(packageIdList)&&packageIdList.endsWith(",")?"":",")+subPackageList;
//						}else{
//							packageIdList=packageIdList+(packageIdList!=null&&!"".equals(packageIdList)&&packageIdList.endsWith(",")?"":",")+buyId;
//						}
//					}
//					if("bundle".equalsIgnoreCase(nodeName)){
//						bundleHtml.append(buyId).append("(").append(buyName).append(")").append("<br>");
//					}else if("mpackage".equalsIgnoreCase(nodeName)){
//						mPackageHtml.append(buyId).append("(").append(buyName).append(")").append("<br>");
//					}else if("spackage".equalsIgnoreCase(nodeName)){
//						sPackageHtml.append(buyId).append("(").append(buyName).append(")").append("<br>");
//					}else if("campaign".equalsIgnoreCase(nodeName)){
//						campaignHtml.append(buyId).append("(").append(buyName).append(")").append("<br>");
//					}
//					if("devices".equalsIgnoreCase(nodeName)){
//						//׼��������豸��Ϣ.
//					}
//				}
//			}
//			contentHtml.append("<td align=\"center\">").append(buyIndex).append("</td>");
//			contentHtml.append("<td>").append(bundleHtml).append("</td>");
//			contentHtml.append("<td>").append(mPackageHtml).append("</td>");
//			contentHtml.append("<td>").append(sPackageHtml).append("</td>");
//			contentHtml.append("<td>").append(campaignHtml).append("</td>");
//			contentHtml.append("<td align=\"center\">").append(buyNum).append("</td>");
//			contentHtml.append("</tr>");
//			contentHtml.append("</table>");
//			Map deviceMap=Postern.getDeviceClassMapByPackageIDList(packageIdList);
//			if(deviceMap==null||deviceMap.isEmpty())continue;
//			for(int n=0;n<buyNum;n++){
//				boolean isFirstLine=true;
//				macInfoIndex=macInfoIndex+","+n;
//				contentHtml.append("<table id=\"buyList\" align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"list_bg\">");
//				for(Iterator dit=deviceMap.entrySet().iterator();dit.hasNext();){
//					Entry de=(Entry) dit.next();
//					Integer productId=(Integer) de.getKey();
//					DeviceClassDTO dcDto=(DeviceClassDTO) de.getValue();
//					String deviceClass=dcDto.getDeviceClass();
//					macInfoIndex=macInfoIndex+";"+deviceClass;
//					contentHtml.append("<tr >");
//					if(isFirstLine){
//						contentHtml.append("<td rowspan=\"").
//						append(deviceMap.size()).append(">").append("��").
//						append(n+1).append("��").append("</td>");
//						isFirstLine=!isFirstLine;
//					}
//					contentHtml.append("<td>").append(deviceClass).append(":</td>");
//					contentHtml.append("<td>")
//					.append("<input type=\"text\" name=\"").append(macInfoIndex).append("\" size=\"25\" maxlength=\"20\"")
//					.append(" value=\"serialNo\" class=\"text\"")
//					.append(" onchange=\"javascript:getMacInmac('").append(macInfoIndex).append("');\">\"")
//					.append("<input type=\"button\" value=\"ѡ��\" class=\"button\"")
//					.append(" onclick=\"javascript:query_device_item('").append(productId.intValue())
//					.append("','").append(deviceClass).append("','','").append(macInfoIndex).append("');\">")
//					.append("<input name=\"input\" type=\"hidden\" >")
//					.append("</td>");
//					contentHtml.append("<td>").append("<span  id=\"").append(deviceClass).append("_div\" name=\"sspan\"></span>").append("</td>");
//					contentHtml.append("</tr>");
//				}
//				contentHtml.append("</table>");
//			}
//		}
//		return contentHtml.toString();
//	}
}
