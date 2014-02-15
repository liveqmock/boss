/*
 * Created on 2004-11-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.tree;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.DistrictSetting;
import com.dtv.oss.util.Organization;
import com.dtv.oss.util.Postern;

/**
 * @author 240910y
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GenerateTree {
	/**
	 * 
	 */
	private static String labelType=null;
	

	public GenerateTree() {
		super();
	}

	/**
	 * ������̬������ʾ���
	 * 
	 * @param dynamicRootNode
	 *            ����һ�����ڵ�
	 * @return ��ʾ�����Ϣ
	 */
	public static StringBuffer generateDynamicTree(
			DynamicRootNode dynamicRootNode) {

		StringBuffer returnMessage = new StringBuffer();
		// �ڵ㱻�������Ӧ��չ�������
		int currrentClickDeep = 1;

		if (dynamicRootNode != null) {
			// �õ���ǰ�ڵ��µ��ӽڵ�
			Map currentMap = dynamicRootNode.getNodeMap();
			currrentClickDeep = dynamicRootNode.getCurrentClickDeep();
			Iterator nodeTreeIterator = currentMap.keySet().iterator();
			while (nodeTreeIterator.hasNext()) {
				StringBuffer currentParentBuffer = new StringBuffer();
				DynamicTreeNode currentNode = (DynamicTreeNode) currentMap
						.get((String) nodeTreeIterator.next());
				// �õ���ǰ�ڵ����ʾ����
				returnMessage.append(writeTree(currrentClickDeep,
						currentParentBuffer, currentNode));
			}
		}
		return returnMessage;
	}

	/**
	 * ������ĸ���㿪ʼ�����нڵ�
	 * 
	 * @param currrentClickDeep
	 * @param currentParentBuffer
	 * @param currentNode
	 * @return
	 */
	private static StringBuffer writeTree(int currrentClickDeep,
			StringBuffer currentParentBuffer, DynamicTreeNode currentNode) {

		// LogUtility.log(GenerateTree.class,LogLevel.DEBUG,"writeTree:"+currentNode);

		StringBuffer currentStringBuffer = new StringBuffer();
		StringBuffer currentNodeBuffer = new StringBuffer();

		// int currentDeep = currentNode.getCurrentLevel();
		// �������������,
		// ����һ�ν���ʱ
		if (currentParentBuffer.length() == 0) {
			// ��ǰ�û��ڵ������ʼ
			currentStringBuffer.append("<tr valign=\"middle\">\r\n");
			currentStringBuffer.append("<td></td>\r\n");
			// ��ǰ�û��ڵ�������Ϣ�洢
			currentNodeBuffer.append(currentParentBuffer);
			currentNodeBuffer.append("<tr valign=\"middle\">\r\n");
			currentNodeBuffer.append("<td></td>\r\n");

			// ����Ͻڵ�����ʾ����
			currentStringBuffer.append(outPutCurrentNodeMessage(
					currrentClickDeep, currentNode));
			// ��ǰ�û��ڵ��������
			currentStringBuffer.append("</tr>\r\n");

			// ��ǰ�û��ڵ�������Ϣ�洢
			String currentAsParentReference = getPicture(6);

			// ��ӽڵ�ǰ����������ֵ�����
			// ��ǰ�ڵ�Ĵ򿪹ر�״̬
			if ("ON".equalsIgnoreCase(currentNode.getOnAndOff())) {
				// �����ǰ�ڵ��еܵ�
				if (currentNode.haveBrother()) {
					currentNodeBuffer.append("<td><img src=\""
							+ currentAsParentReference
							+ "\" alt=\"\" border=\"0\"></td>\r\n");
				} else {
					currentNodeBuffer.append("<td></td>\r\n");
				}
				// �����ǰ�ڵ��к���
				if (currentNode.hasChildNode()) {
					Map childMap = currentNode.getChildNodeMap();
					Iterator childNodeIterator = childMap.keySet().iterator();
					while (childNodeIterator.hasNext()) {
						currentStringBuffer
								.append(writeTree(currrentClickDeep,
										currentNodeBuffer,
										(DynamicTreeNode) childMap
												.get((String) childNodeIterator
														.next())));
					}
				}
			}
		}
		// ���ǵ�һ��
		else {
			currentStringBuffer.append(currentParentBuffer.toString());
			currentNodeBuffer.append(currentParentBuffer);
			// ����Ͻڵ�����ʾ����
			currentStringBuffer.append(outPutCurrentNodeMessage(
					currrentClickDeep, currentNode));
			// ��ǰ�û��ڵ��������
			currentStringBuffer.append("</tr>\r\n");
			// ��ǰ�û��ڵ�������Ϣ�洢
			String currentAsParentReference = getPicture(6);

			if ("ON".equalsIgnoreCase(currentNode.getOnAndOff())) {
				if (currentNode.haveBrother()) {
					currentNodeBuffer.append("<td><img src=\""
							+ currentAsParentReference
							+ "\" alt=\"\" border=\"0\"></td>\r\n");
				} else {
					currentNodeBuffer.append("<td></td>\r\n");
				}
				if (currentNode.hasChildNode()) {
					Map childMap = currentNode.getChildNodeMap();
					Iterator childNodeIterator = childMap.keySet().iterator();
					while (childNodeIterator.hasNext()) {
						currentStringBuffer
								.append(writeTree(currrentClickDeep,
										currentNodeBuffer,
										(DynamicTreeNode) childMap
												.get((String) childNodeIterator
														.next())));
					}
				}
			}
		}

		return currentStringBuffer;
	}

	/**
	 * ȡ��ͼƬ��λ����Ϣ
	 * 
	 * @param position
	 * @return
	 */
	private static String getPicture(int position) {
		String resultPicture = "";
		switch (position) {
		// ��ǰ�ڵ����û���ֵܽڵ��չ��ͼ���ַ��δչ����
		case 1:
			resultPicture = "img/tree/tree_Lplus.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("child_nobrother_close");
			break;
		// ǰ����涼���ֵܽڵ�ͼ���ַ��δչ����
		case 2:
			resultPicture = "img/tree/tree_Tplus.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("child_brother_close");
			break;
		// ��ǰ�ڵ����û���ֵܽڵ��չ��ͼ���ַ��չ����
		case 3:
			resultPicture = "img/tree/tree_Lminus.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("child_nobrother_open");
			break;
		// ��ǰ�ڵ�ǰ����涼���ֵܵ�չ��ͼ���ַ��չ����
		case 4:
			resultPicture = "img/tree/tree_Tminus.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("child_brother_open");
			break;
		// ʮ����ͼ���ַ
		case 5:
			resultPicture = "img/tree/tree_T.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("nochild_brother");
			break;
		// ����ͼ���ַ
		case 6:
			resultPicture = "img/tree/tree_I.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("nochild_nobrother");
			break;
		// ����ͼ���ַ
		case 7:
			resultPicture = "img/tree/tree_L.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("uprightness_beeline");
			break;
		default:
			resultPicture = "";
		}
		return resultPicture;
	}

	/**
	 * �����ǰ�ڵ���Ϣ�Ĺ�ͬ������� ���ɵ�ǰ�ڵ���ʾ��ͼƬ�����ּ����ӵ�����,ͼƬ����һ����Ԫ��,���ֲ���һ����Ԫ��
	 * 
	 * @param currrentClickDeep��ǰ�ڵ�չ������
	 * @param currentNode
	 * @return
	 */
	private static StringBuffer outPutCurrentNodeMessage(int currrentClickDeep,
			DynamicTreeNode currentNode) {

		StringBuffer currentStringBuffer = new StringBuffer();
		String key = currentNode.getCurrentNode().getKey();
		String keyName = currentNode.getCurrentNode().getKeyName();

		int currentLevel = currentNode.getCurrentLevel();
		int colspan = currrentClickDeep - currentLevel + 1;

		String currentpictureReference = "";

		// ������ӽڵ㲢�Һ�����ƽ�нڵ�
		if (currentNode.hasChildNode() && currentNode.haveBrother()) {
			// �����ǰ�ڵ�ر�״̬,
			if ("OFF".equalsIgnoreCase(currentNode.getOnAndOff())) {
				currentpictureReference = getPicture(2);
			}
			// �����ǰ�ڵ�Ϊ��״̬,
			else if ("ON".equalsIgnoreCase(currentNode.getOnAndOff())) {
				currentpictureReference = getPicture(4);
			}
		}
		// ������ӽڵ㲢�Һ���û��ƽ�нڵ�
		else if (currentNode.hasChildNode() && !currentNode.haveBrother()) {
			if ("OFF".equalsIgnoreCase(currentNode.getOnAndOff())) {
				currentpictureReference = getPicture(1);
			} else if ("ON".equalsIgnoreCase(currentNode.getOnAndOff())) {
				currentpictureReference = getPicture(3);
			}
		}
		// �еܵ�,û����
		else if (!currentNode.hasChildNode() && currentNode.haveBrother()) {
			currentpictureReference = getPicture(5);
		}
		// û�ܵ�,û����
		else if (!currentNode.hasChildNode() && !currentNode.haveBrother()) {
			currentpictureReference = getPicture(7);
		}

		// ��ǰ�û��ڵ�ǰͼƬ�������,(��״̬,�ر�״̬,����״̬ʱ�Ĳ���action)
		// ��ǰ�ڵ��к���
		if (currentNode.hasChildNode()) {
			currentStringBuffer.append("<td><a href=" + "\"treeAction.do?key="
					+ key + "\"><img src=\"" + currentpictureReference
					+ "\" alt=\"" + "close node\""
					+ " border=\"0\"></a></td>\r\n");
		}
		// ��ǰ�ڵ�û����
		else {
			currentStringBuffer.append("<td><img src=\""
					+ currentpictureReference + "\" alt=\"" + "close node\""
					+ " border=\"0\"></td>\r\n");
		}

		// �����ǰ�ڵ��Ƿ�������ݼ���

		if (currentNode.getCurrentNode().canAction()) {
			String actionTarget = currentNode.getCurrentNode()
					.getAtcionTarget();
			if (actionTarget != null && !"".equals(actionTarget)) {
				Map currentKeyAndValueMap = currentNode.getCurrentNode()
						.getKeyAndValueMap();
				String keyAndValueStr = "";
				// �����ǰ�ڵ��currentKeyAndValueMap(�ύ�Ĳ���)δ���κ�ֵ
				if (currentKeyAndValueMap == null) {
					// �����ǰ�����ڵ��IDΪ��
					if (key == null || "".equals(key)) {
						// �Ƿ���Ҫ��ҳ����
						if (currentNode.getCurrentNode().canHaveMultiplePage()) {
							keyAndValueStr = "?txtFrom="
									+ currentNode.getCurrentNode()
											.getPageFrom() + "&txtTo="
									+ currentNode.getCurrentNode().getPageTo();
						} else {
							keyAndValueStr = "";
						}
					}
					// �����ǰ�����ڵ��ID����
					else {
						// �Ƿ���Ҫ��ҳ����
						if (currentNode.getCurrentNode().canHaveMultiplePage()) {
							keyAndValueStr = "?key="
									+ key
									+ "&txtFrom="
									+ currentNode.getCurrentNode()
											.getPageFrom() + "&txtTo="
									+ currentNode.getCurrentNode().getPageTo();
						} else {
							keyAndValueStr = "?key=" + key;
						}
					}
				}
				// �����ǰ�ڵ��currentKeyAndValueMap(�ύ�Ĳ���)��ֵ
				else {
					Iterator keyIterator = currentKeyAndValueMap.keySet()
							.iterator();
					// ��ֵ����Ŀ
					int keyRecordCount = 0;
					while (keyIterator.hasNext()) {
						String parameterKey = (String) keyIterator.next();
						String parameterValue = (String) currentKeyAndValueMap
								.get(parameterKey);
						// ��һ��
						if (keyRecordCount == 0) {
							keyAndValueStr = "?" + parameterKey + "="
									+ parameterValue;
						} else {
							keyAndValueStr = keyAndValueStr + "&"
									+ parameterKey + "=" + parameterValue;
						}
						keyRecordCount++;
					}
					// ��Ҫ��ҳ
					if (currentNode.getCurrentNode().canHaveMultiplePage()) {
						keyAndValueStr = keyAndValueStr + "&txtFrom="
								+ currentNode.getCurrentNode().getPageFrom()
								+ "&txtTo="
								+ currentNode.getCurrentNode().getPageTo();
					}
				}
				// ����������ʾ����
				currentStringBuffer
						.append("<td nowrap colspan=\""
								+ String.valueOf(colspan)
								+ "\"> <a href=\""
								+ actionTarget
								+ keyAndValueStr
								+ "\"  target=\"rightFrame\" class=\"tree-control-unselected\" onclick=\"self.location.href='treeAction.do?key="
								+ key + "'\">" + keyName + "</a></td>\r\n");
			}
			// �����ǰ�ڵ���û�в���,��ӿյ�����
			else {
				currentStringBuffer
						.append("<td nowrap colspan=\""
								+ String.valueOf(colspan)
								+ "\"> <a href=\"#"
								+ "\"   target=\"leftFrame\"  class=\"tree-control-unselected\" onclick=\"self.location.href='treeAction.do?key="
								+ key + "'\">" + keyName + "</a></td>\r\n");
			}

		}
		// �����ǰ�ڵ��ϲ��ò���,��ӿյ�����
		else {
			currentStringBuffer
					.append("<td nowrap colspan=\""
							+ String.valueOf(colspan)
							+ "\"> <a href=\"#"
							+ "\" target=\"leftFrame\" class=\"tree-control-unselected\" onclick=\"self.location.href='treeAction.do?key="
							+ key + "'\">" + keyName + "</a></td>\r\n");
		}
		// ���������Ϣ
		return currentStringBuffer;
	}

	/**
	 * �÷������ڴ�����һϵ�нڵ�
	 * 
	 * @param key
	 * @param dynamicRootNode
	 */
	public static void openTree(String key, DynamicRootNode dynamicRootNode) {
		try {
			Map currentMap = dynamicRootNode.getNodeMap();

			Set keyvalueSet = currentMap.keySet();

			if (keyvalueSet != null && keyvalueSet.contains(key)) {
				DynamicTreeNode dynamicTreeNode = (DynamicTreeNode) currentMap
						.get(key);
				dynamicTreeNode.setOnAndOff("OFF");
				dynamicRootNode.setCurrentClickDeep(dynamicTreeNode);
			} else {
				Iterator keyIterator = keyvalueSet.iterator();
				while (keyIterator.hasNext()) {
					DynamicTreeNode dynamicTreeNode = (DynamicTreeNode) currentMap
							.get((String) keyIterator.next());
					if (openTreeNode(key, dynamicTreeNode)) {
						dynamicTreeNode.setOnAndOff("OFF");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean openTreeNode(String key,
			DynamicTreeNode currentTreeNode) {
		Map childNodeMap = currentTreeNode.getChildNodeMap();
		if (currentTreeNode.hasChildNode()) {
			// �����ǰ�ڵ�Ķ��ӽڵ�����KEY,���
			if (currentTreeNode.containChild(key)) {
				currentTreeNode.setOnAndOff("OFF");
				currentTreeNode.getChildNode(key).setOnAndOff("OFF");
				LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
						"openTreeNode0:" + currentTreeNode.getChildNode(key));
				return true;
			}
			// ������ö���ȥ������
			else {
				Iterator childIterator = childNodeMap.keySet().iterator();
				boolean res = false;
				while (childIterator.hasNext()) {
					DynamicTreeNode dynamicTreeNode = (DynamicTreeNode) childNodeMap
							.get(childIterator.next());
					res = openTreeNode(key, dynamicTreeNode);
					if (res) {
						dynamicTreeNode.setOnAndOff("OFF");
						LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
								"openTreeNode1:" + dynamicTreeNode);
						break;
					}
				}
				return res;
			}
		}
		return false;
	}

	/**
	 * ����ʵ��Ƕ�ײ��Ҳ���������
	 * 
	 * @param key
	 * @param currentTreeNode
	 * @param dynamicRootNode
	 */
	private static void changeDynamicTreeNode(String key,
			DynamicTreeNode currentTreeNode, DynamicRootNode dynamicRootNode) {
		Map childNodeMap = currentTreeNode.getChildNodeMap();
		if (childNodeMap != null) {
			Set childSet = childNodeMap.keySet();
			if (childSet.contains(key)) {
				DynamicTreeNode dynamicTreeNode = (DynamicTreeNode) childNodeMap
						.get(key);
				dynamicTreeNode.setOnAndOff(dynamicTreeNode.getOnAndOff());
				dynamicRootNode.setCurrentClickDeep(dynamicTreeNode);
			} else {
				Iterator childIterator = childNodeMap.keySet().iterator();
				while (childIterator.hasNext()) {
					DynamicTreeNode dynamicTreeNode = (DynamicTreeNode) childNodeMap
							.get(childIterator.next());
					changeDynamicTreeNode(key, dynamicTreeNode, dynamicRootNode);
				}
			}
		}
	}

	/**
	 * �����û��Խڵ�ĵ������������еĶ�Ӧ����
	 * 
	 * @param key
	 * @param dynamicRootNode
	 * @return ���ظ��ĺ�ĸ�������
	 */
	public static DynamicRootNode changeDynamicTreeNode(String key,
			DynamicRootNode dynamicRootNode) {
		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
				"changeDynamicTreeNode��ǰKey:" + key);
		try {
			Map currentMap = dynamicRootNode.getNodeMap();
			Set keyvalueSet = currentMap.keySet();

			if (keyvalueSet != null && keyvalueSet.contains(key)) {
				DynamicTreeNode dynamicTreeNode = (DynamicTreeNode) currentMap
						.get(key);
				dynamicTreeNode.setOnAndOff(dynamicTreeNode.getOnAndOff());// ??
				dynamicRootNode.setCurrentClickDeep(dynamicTreeNode);
			} else {
				Iterator keyIterator = keyvalueSet.iterator();
				while (keyIterator.hasNext()) {
					DynamicTreeNode dynamicTreeNode = (DynamicTreeNode) currentMap
							.get((String) keyIterator.next());
					GenerateTree.changeDynamicTreeNode(key, dynamicTreeNode,
							dynamicRootNode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dynamicRootNode;
	}

	/**
	 * =========================================================================================================
	 * javascript������
	 */

	private static String fatherDivStyle = "";

	private static String childDivStyle = "";

	// private static String onMouseOverStyle = "";
	//
	// private static String onMouseOutStyle = "";

	// ��������ID�Ĺ�ϵ
	private static Map idMapTypeMap = null;

	// ���е�����DTO
	private static Map treeHashMap = null;

	// ��ǰ���������
	private static List orgGovernedDistrict = null;

	// ��ǰ�����������еĽڵ�
	private static List curDistrictPathList = null;

	// radio���õ������б�
	private static List createRadioList = null;

	// ������
	private static int space = 18;

	// չ���ļ���,������֯С���������,�ӽڵ�Ĭ�ϴ�,����0��ʼ�Ʋ�
	private static int showLevel = 3;

	private static String openImg = "img/tree/handlerightmiddle.gif";

	private static String closeImg = "img/tree/handledownlast.gif";

	private static Map organizationIDMap = null;

	private static List curOrganizationPathList = null;

	private static List curEnableOrgList = null;

	private static Map organizationHashMap = null;

//	private static Map orgAndSubOrgConfig = null;

	private static List showOrgTypeList = null;
	
	private static boolean isShowAll=false;
	
	private static boolean isOnlyLeaf=false;

	public static StringBuffer generateOrganizationJavaScriptTree(int orgID,
			List showList,boolean isAll,String lType, String retrunOrgID, String retrunOrgDesc) {
		StringBuffer treeBuffer = new StringBuffer();

		labelType=lType;
		showOrgTypeList = showList;
		isShowAll=isAll;
		organizationHashMap = Postern.getAllOrganizationCache();

		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"isShowAll:"+isShowAll);
		// ȡ�õ�ǰ������֯����ӵ�е���������
		organizationIDMap = Postern.getOrganizationForCreateTree();
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"ȡ��organizationIDMap\r\n" + organizationIDMap.toString());
//		System.out.println("ȡ��organizationIDMap\r\n"
//				+ organizationIDMap.toString());


//		orgAndSubOrgConfig = Postern.getOrgAndSubOrgConfig();

		/**
		 * rootOrgID�Ǵӵ�ǰ��֯���ϻ��ݵ����пͻ�����Ȩ�޵�����ϼ���֯,
		 * ����е�˵��ͨ,��Ϊ��ʾ��֯�Ϳͻ�����Ȩ�޲�û��ֱ�ӹ�ϵ,���ǲ����ݵ�ʱ������ʦ�������û��ֱ���¼��Ĳ���Ա��½��ֻ��ʾ��ǰ����,
		 * �޷����㵱ǰ����,
		 */
		int rootOrgID = Postern.getParentHasCustomerOrgID(orgID);

		curEnableOrgList = null;
		curEnableOrgList=getEnableNodeList(rootOrgID);
//		System.out.println("ȡ��curEnableOrgList\r\n"
//				+ curEnableOrgList.toString());

		curOrganizationPathList = null;
		curOrganizationPathList = getPathNodeList(orgID);
		curOrganizationPathList.addAll(curEnableOrgList);
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"ȡ��curOrganizationPathList\r\n"
//						+ curOrganizationPathList.toString());
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"ȡ��curOrganizationPathList\r\n"
//				+ curOrganizationPathList.toString());


//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"ȡ��curEnableOrgList\r\n" + curEnableOrgList.toString());
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"ȡ��curEnableOrgList\r\n"
//				+ curEnableOrgList.toString());

		if (organizationHashMap == null || organizationHashMap.isEmpty()
				|| organizationIDMap == null || organizationIDMap.isEmpty()
				|| curOrganizationPathList == null
				|| curOrganizationPathList.isEmpty()
				|| curEnableOrgList == null || curEnableOrgList.isEmpty()) {
			treeBuffer.append("������ȡ����");
			LogUtility.log(GenerateTree.class, LogLevel.ERROR, "������������׼������");
			return treeBuffer;
		}
		if ((showOrgTypeList == null || showOrgTypeList.isEmpty())&&!isShowAll) {
			treeBuffer.append("���ó���");
			LogUtility.log(GenerateTree.class, LogLevel.ERROR,
					"���ó���,û�����ÿ�����֯����");
			return treeBuffer;
		}

		treeBuffer.append(generateOrganizationRootNode(0, 0));
		treeBuffer.append(addNodeFunction(retrunOrgID, retrunOrgDesc));

		// LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
		// "���Ľ��:=================================\r\n"
		// + treeBuffer.toString());

		return treeBuffer;
	}
	private static List getPathNodeList(int orgID) {
		List curList = new ArrayList();
		curList.add(new Integer(orgID));
		
		Organization org = (Organization) organizationHashMap.get(orgID + "");
		int parentOrgID=org.getParentOrgID();
		if(parentOrgID!=0){
			curList.addAll(getPathNodeList(parentOrgID));
		}else
			curList.add(new Integer(parentOrgID));
		return curList;
	}
	private static List getEnableNodeList(int orgID) {
		List curList = new ArrayList();
		curList.add(new Integer(orgID));

		List tmpList = (List) organizationIDMap.get(new Integer(orgID));
		if (tmpList == null){
			return curList;
		}

		for (int i = 0; i < tmpList.size(); i++) {
			Integer curOrgID = (Integer) tmpList.get(i);
			List childList = (List) organizationIDMap.get(curOrgID);
			if (childList != null) {
				curList.addAll(getEnableNodeList(curOrgID.intValue()));
			}else{
				curList.add(curOrgID);
			}
		}
		return curList;
	}
	private static StringBuffer generateOrganizationRootNode(int rootOrgID, int level) {
		StringBuffer res = new StringBuffer();

//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG, "rootOrgID:"
//				+ rootOrgID);
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG, "level:" + level);
		level++;

		res.append("<div id=\"childHead\" style=\"display:'block' class=\"")
		   .append(childDivStyle).append("\">");
		// ȡ��ǰ�ڵ��µ��ӽڵ�
		ArrayList childNodes = getOrgChildNodes(rootOrgID);

		res.append(generateOrgChildNode(childNodes, "", level));
		res.append(addChildDivEnd());

		return res;
	}

	private static ArrayList getOrgChildNodes(int orgID) {
		Integer iID = new Integer(orgID);

		ArrayList res = new ArrayList();
		ArrayList childNodes = (ArrayList) organizationIDMap.get(iID);

//		Organization curOrg = (Organization) organizationHashMap.get(iID.toString());
		if (childNodes != null)
			for (int i = 0; i < childNodes.size(); i++) {
				Integer curChildID = (Integer) childNodes.get(i);
				Organization curOrg = (Organization) organizationHashMap
						.get(curChildID.toString());
				//organizationIDMapȡֵû�н���״̬����,��ȡ����ORG,organizationHashMap������״̬����,����С��ǰ��,����ֿ�ֵ,���й���
				if(curOrg==null)continue;
				String curType = curOrg.getOrgType();
				

					if (curType != null && curType != ""
						&& curOrganizationPathList.contains(curChildID)
						&& (isShowAll||showOrgTypeList.contains(curType))) {
					res.add(curChildID);
				}
			}
		return res;
	}

	private static StringBuffer generateOrgChildNode(ArrayList nodes, String desc,
			int level) {
		StringBuffer res = new StringBuffer();

		if (nodes == null || nodes.isEmpty())
			return res;

//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"generateOrgChildNode().nodes:" + nodes);
//
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG, "level:" + level);

		level++;
		boolean isOpen = level < showLevel ? true : false;
		for (int i = 0; i < nodes.size(); i++) {
			Integer curID = (Integer) nodes.get(i);
			Organization curOrg = (Organization) organizationHashMap.get(curID
					.toString());
			if (curOrg != null) {
				String curDesc = desc.equals("") ? desc : desc + "��";
				curDesc = curDesc + curOrg.getOrgName();

				boolean isCreateRadio = false;
				String curType = curOrg.getOrgType();
				if (curEnableOrgList.contains(curID))
					isCreateRadio = true;

				boolean isLastNode = false;
				if (i == nodes.size() - 1)
					isLastNode = true;

				ArrayList childNodes = getOrgChildNodes(curOrg.getOrgID());

				// �����ǰ�ڵ��Ǹ����ڵ�,�������
				if (childNodes != null && !childNodes.isEmpty()) {

					res.append(addFatherNodeDiv(curID.toString(), curOrg
							.getOrgName(), curDesc, isCreateRadio, isLastNode,
							isOpen,"orgTree"));

					res.append(addChildDivStart(curID.toString(), isOpen));
					res
							.append(generateOrgChildNode(childNodes, curDesc,
									level));
					res.append(addChildDivEnd());
				} else {
					res.append(addChildNodeDiv(curID.toString(), curOrg
							.getOrgName(), curDesc, "#", "", isCreateRadio,
							isLastNode));
				}
			}

		}

		return res;
	}

	private static void initDistrictInfo(int orgID){
		// ȡ�õ�ǰ����Ա���Թ���ͻ�����֯����id,���пͻ�����Ȩ���������֯
		int rootOrgID = Postern.getParentHasCustomerOrgID(orgID);
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"rootOrgID:" + rootOrgID);
		
		curDistrictPathList = null;
		curDistrictPathList = Postern
				.getDistrictTreePathListWithOrgID(rootOrgID);
		
		
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"ȡ��curDistrictPathList\r\n" + curDistrictPathList.toString());

		orgGovernedDistrict = null;
		orgGovernedDistrict = Postern.getOrgGovernedDistrictByOrgID(rootOrgID);
	//	LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
	//			"ȡ��orgGovernedDistrict\r\n" + orgGovernedDistrict.toString());
	}
	/**
	 * ��������ѡ�����
	 * 
	 * @param orgID�ĸ���֯���������
	 * @param crtateRadionList����ѡ��������б�
	 * @param retrunDistrictID����IDֵ�Ŀؼ�
	 * @param retrunDistrictDesc���������ִ��Ŀؼ�
	 * @return
	 */
	public static StringBuffer generateDistrictJavaScriptTree(int orgID,
			List crtateRadionList,boolean isAll,boolean isLeaf,String lType, String retrunDistrictID,
			String retrunDistrictDesc) {
		StringBuffer treeBuffer = new StringBuffer();
		labelType=lType;
		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"labelType:"+labelType);
		createRadioList = crtateRadionList;
		isShowAll=isAll;
		isOnlyLeaf=isLeaf;
		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"isShowAll:"+isShowAll);
		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"isOnlyLeaf:"+isOnlyLeaf);
		
		initDistrictInfo(orgID);
		
        // ȡ�õ�ǰ������֯����ӵ�е���������
		idMapTypeMap =Postern.getDistrictSettingForCreateTree();
				
		treeHashMap = Postern.getAllDistrictSettingCache();

	//	LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"treeHashMap"+treeHashMap);
	//	LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"createRadioList"+createRadioList);
	//	LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"orgGovernedDistrict"+orgGovernedDistrict);
	//	LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"idMapTypeMap"+idMapTypeMap);
	//	LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"curDistrictPathList"+curDistrictPathList);
		if (treeHashMap == null || treeHashMap.isEmpty()
				|| ((createRadioList == null || createRadioList.isEmpty())&&!isShowAll&&!isOnlyLeaf)
				|| orgGovernedDistrict == null || orgGovernedDistrict.isEmpty()
				|| idMapTypeMap == null || idMapTypeMap.isEmpty()
				|| curDistrictPathList == null || curDistrictPathList.isEmpty()) {
			treeBuffer.append("������ȡ����");
			LogUtility.log(GenerateTree.class, LogLevel.ERROR, "������������׼������");
			return treeBuffer;
		}
		if (retrunDistrictID == null || retrunDistrictID.equals("")
				|| retrunDistrictDesc == null || retrunDistrictDesc.equals("")) {
			treeBuffer.append("���ó���");
			LogUtility.log(GenerateTree.class, LogLevel.ERROR,
					"���ó���,û��������Ч�ķ���ֵ���տؼ�");
			return treeBuffer;
		}

		treeBuffer.append(generateDistrictRootNode(0, 0));
		treeBuffer
				.append(addNodeFunction(retrunDistrictID, retrunDistrictDesc));

		// LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
		// "���Ľ��:=================================\r\n"
		// + treeBuffer.toString());

		return treeBuffer;
	}

	/**
	 * ������ڵ�,���ڵ��belongtoΪ0��
	 * 
	 * @param rootDistrictID
	 *            ���ڵ�ID
	 * @return
	 */
	private static StringBuffer generateDistrictRootNode(int rootDistrictID, int level) {
		StringBuffer res = new StringBuffer();

//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG, "rootDistrictID:"
//				+ rootDistrictID);
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG, "level:" + level);
		level++;

		// res.append(addFatherNodeDiv(rootDistrictID+"", "��������","",
		// false, true,level<showLevel?true:false));

		// res.append(addChildDivStart(rootDistrictID+"",true));
		res.append("<div id=\"childHead\" style=\"display:'block' class=\""
				+ childDivStyle + "\">");
		// ȡ��ǰ�ڵ��µ��ӽڵ�
		ArrayList childNodes = getDistrictChildNodes(rootDistrictID);	
		res.append(generateDistrictChildNode(childNodes, level));
		res.append(addChildDivEnd());
		return res;
	}

	/**
	 * ������֯����ȡ��������Թ���ĺ���
	 * 
	 * @param distID
	 * @return
	 */
	private static ArrayList getDistrictChildNodes(int distID) {
		Integer iID = new Integer(distID);

		ArrayList res = new ArrayList();
		ArrayList childNodes = (ArrayList) idMapTypeMap.get(iID);
		
		if (childNodes != null)
			for (int i = 0; i < childNodes.size(); i++) {
				Integer curID = (Integer) childNodes.get(i);
				if (curDistrictPathList.contains(curID)) {
					res.add(curID);
				}
			}
		return res;
	}

	/**
	 * ����һ���ڵ�,���������,
	 * 
	 * @param nodes
	 *            �ڵ�ļ���,��������еĽڵ�����ӽڵ�,�������
	 * @return
	 */
	private static StringBuffer generateDistrictChildNode(ArrayList nodes, int level) {
		StringBuffer res = new StringBuffer();

		if (nodes == null || nodes.isEmpty())
			return res;

//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"generateChildNode().nodes:" + nodes);
//
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG, "level:" + level);

		level++;
		boolean isOpen = level < showLevel ? true : false;
		for (int i = 0; i < nodes.size(); i++) {
			Integer curID = (Integer) nodes.get(i);
			DistrictSetting curDS = (DistrictSetting) treeHashMap.get(curID
					.toString());
			if (curDS != null) {
				String curDesc = Postern.getDistrictDesc(curID.intValue()); 
 
				boolean isCreateRadio = false;
				String curType = curDS.getType();
				if (orgGovernedDistrict.contains(curID)
						&&(isShowAll
						||(curType != null 
								&& !"".equals(curType)
								&& createRadioList.contains(curType)
								))
					){
					isCreateRadio = true;
				}

				boolean isLastNode = false;
				if (i == nodes.size() - 1)
					isLastNode = true;

				ArrayList childNodes = getDistrictChildNodes(curDS.getId());


				// �����ǰ�ڵ��Ǹ����ڵ�,�������
				if (childNodes != null && !childNodes.isEmpty()) {
					//���isOnlyLeaf����,���и��ڵ㲻��ѡ,
					res.append(addFatherNodeDiv(curID.toString(), curDS
							.getName(), curDesc, isCreateRadio&&!isOnlyLeaf, isLastNode,
							isOpen,"distTree"));

					res.append(addChildDivStart(curID.toString(), isOpen));
                    //	��ǰ����ڼ��ص�����,���ݹ�,ֻ���ɿյ��ӽڵ�
					
					if(level < showLevel){
					   res.append(generateDistrictChildNode(childNodes,level));
					}
					res.append(addChildDivEnd());
				} else {
					//�����ʾ�ڵ����Ͳ��ղ�isOnlyLeafΪ��,ȡisCreateRadio,
					isCreateRadio=(createRadioList!=null&&!createRadioList.isEmpty()&&isOnlyLeaf)?
							(isCreateRadio):(isOnlyLeaf||isCreateRadio);
					res.append(addChildNodeDiv(curID.toString(), curDS
							.getName(), curDesc, "#", "", isCreateRadio,
							isLastNode));
				}
			}

		}

		return res;
	}
	
	
	
	/**
	 * ȡ����ڵ��½ڵ�ķ��� .
	 * @param rootid
	 * @param orgID
	 * @return
	 */
	public static String getDistrictSettingNode(int rootid,int orgID){
		StringBuffer res = new StringBuffer();
		initDistrictInfo(orgID);
		res.append(generateDistrictRootNode(rootid,showLevel));
		return res.toString();
	}
	
	/**
	 * ���ɸ��ڵ��,���ڸ��ڵ����滹�и��ӽڵ㼯�ϲ�
	 * 
	 * @param id
	 *            ���ڵ���ID,
	 * @param name
	 *            ��ʾ������
	 * @param isCreateRadio
	 *            �Ƿ�radio
	 * @return
	 */
	private static StringBuffer addFatherNodeDiv(String id, String name, String desc,
			boolean isCreateRadio, boolean isLast, boolean isOpen,String nodeName) {
		StringBuffer res = new StringBuffer();

		// �Ƿ�Ҫ����Ӳ���
		res.append(" <div id=\"").append(id).append("\"");
		// ���ø��ڵ����ʽ
		res.append(" class=\"").append(fatherDivStyle).append("\"");
		// ���ò��ϵ���¼�,
		if (!"orgTree".equals(nodeName)){
		   res.append(" onclick=\"node_onClick('child").append(id).append("','imgA").append(id).append("',").append(id).append(", '").append(nodeName).append("')\"");
		} else{
		   res.append(" onclick=\"node_onClick(child").append(id).append(",imgA").append(id).append(",").append(id).append(", '").append(nodeName).append("')\"");
		}
		// // ���ø��ڵ��������ͣ��ʽ
		// res.append(" onmouseover=\"this.className=" + onMouseOverStyle +
		// "\"");
		// // ���ø��ڵ�������Ƴ���ʽ,
		// res.append(" onmouseout=\"this.className=" + onMouseOutStyle +
		// "\">");
		res.append(">");
		res.append("\r\n");

		res
				.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td nowrap valign=\"middle\">");
		// ���ø��ڵ������νṹͼƬ,Ĭ��Ϊ�ر�
		res.append("<img src=\"");
		if (isOpen) {
			res.append(openImg);
		} else {
			res.append(closeImg);
		}
		res.append("\" id=\"imgA").append(id).append("\" style='cursor:hand'");
		res.append(" >");

		res.append("</td><td nowrap valign=\"middle\" style='cursor:hand'>");
		// // ���ø��ڵ���������ĿͼƬ,Ĭ��Ϊ�ر�
		// res.append("<img src=\"" + getPicture(1) + "\" id=\"imgB"
		// + id + "\">");
		// res.append("\r\n");
		// ����radio
		if("checkbox".equalsIgnoreCase(labelType))
			res.append(addCheckBox(id, desc, isCreateRadio));	
		else
		  res.append(addRadio(id, desc, isCreateRadio));

		res.append(name);
		// // ���ø��ڵ�����ʾ����,������ʱ����
		// res.append("<a valign=\"center\" href=\"#\">" + name + "</a>");
		res.append("</td></tr></table>");
		res.append("\r\n");
		res.append("</div>");
		res.append("\r\n");

		return res;
	}

	/**
	 * �����ӽڵ��Ŀ�ͷ,��һΪ���ڵ�
	 * 
	 * @param childDivID
	 *            �ӽڵ���ID
	 * @return
	 */
	private static StringBuffer addChildDivStart(String childDivID, boolean isOpen) {
		StringBuffer res = new StringBuffer();
		// ���ø��ڵ����Ӳ��ID,��ʽ,��Ĭ����ʽ,Ĭ����Ϊ���ڵ���һ�����ӽڵ�,�������,
		res.append("<div id=\"child").append(childDivID).append("\" class=\"")
		   .append(childDivStyle).append("\"");
		// ���ò�������

		res.append(" style=\"display:'");
		if (isOpen) {
			res.append("block");
		} else {
			res.append("none");
		}
		res.append("';");
		res.append("padding-left:");
		res.append(space);
		res.append("px;\"");
		res.append(">");

		res.append("\r\n");

		return res;
	}

	/**
	 * ���ɲ��β
	 * 
	 * @return
	 */
	private static StringBuffer addChildDivEnd() {
		StringBuffer res = new StringBuffer();
		res.append("</div>");
		res.append("\r\n");
		// LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
		// "addChildDivEnd()���ɵ�����:" + res.toString());
		return res;
	}

	/**
	 * �����ӽڵ�����,ֻһ�еĶ���,��������ͼƬ,�ڵ��ͼƬ,radio,��ʾ������(����),
	 * 
	 * @param name
	 *            ��ʾ����Ҫ����
	 * @param url
	 *            ����
	 * @param target
	 *            Ŀ��
	 * @param isCreateRadio
	 *            �Ƿ���õ�radio
	 * @return
	 */
	private static StringBuffer addChildNodeDiv(String id, String name, String desc,
			String url, String target, boolean isCreateRadio, boolean isLast) {
		StringBuffer res = new StringBuffer();

		res
				.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td nowrap valign=\"middle\">");
		res.append("<img src=\"").append(openImg).append("\">");

		res.append("</td><td nowrap valign=\"middle\" style='cursor:hand'>");
        if("checkbox".equalsIgnoreCase(labelType))
        	res.append(addCheckBox(id, desc, isCreateRadio));	
        else
        	res.append(addRadio(id, desc, isCreateRadio));
        
		res.append(name);
		// ��ʱ���ó���
		// res.append("<a valign=\"center\" href=\"" + url + "\" target=\"" +
		// target + "\">" + name
		// + "</a>" + "<br>");
		res.append("</td></tr></table>");
		res.append("\r\n");

		return res;
	}

	/**
	 * ����radio�ؼ�
	 * 
	 * @param isCreateRadio
	 *            �ؼ��Ƿ����
	 * @return
	 */
	private static StringBuffer addRadio(String id, String desc, boolean isCreateRadio) {
		StringBuffer res = new StringBuffer();

		res.append("<input type=\"radio\" name=\"DistrictSettingRadio\"");
		res.append(" value=\"");
		res.append(id);
		res.append("\"");
		res.append(" onclick=\"radio_onClick(this");
		res.append(",'");
		res.append(desc);
		res.append("')\"");
		if (!isCreateRadio) {
			res.append(" disabled");
		}
		res.append(" >");

		return res;
	}
	/**
	 * ����checkbox�ؼ�
	 * 
	 * @param isCreate
	 *            �ؼ��Ƿ����
	 * @return
	 */
	private static StringBuffer addCheckBox(String id, String desc, boolean isCreate) {
		StringBuffer res = new StringBuffer();

		res.append("<input type=\"checkbox\" name=\"DistrictSettingBox\"");
		res.append(" value=\"");
		res.append(id);
		res.append("\"");
		res.append(" onclick=\"checkbox_onClick(this,'");
		res.append(desc);
		res.append("')\"");
		if (!isCreate) {
			res.append(" disabled");
		}
		res.append(" >");

		return res;
	}
	 
	/**
	 * ����ҳ�������������javascript����
	 * 
	 * @return
	 */
	private static StringBuffer addNodeFunction(String idControl, String descControl) {
		StringBuffer res = new StringBuffer();
		res.append("<script language =\"javascript\">");
		res.append("\r\n");
		res.append("var closeImg=\"");
		res.append(closeImg);
		res.append("\";");
		res.append("\r\n");
		res.append("var openImg=\"");
		res.append(openImg);
		res.append("\";");
		res.append("\r\n");
		res.append("function node_onClick(who , imgA , pk , nodeName) {");
		res.append("\r\n");
		res.append("  if(who.innerHTML){");
		res.append("\r\n");
		res.append("    if(who.style.display==\"none\") {");
		res.append("\r\n");
		res.append("      who.style.display=\"block\";");
		res.append("\r\n");
		res.append("      imgA.src = openImg;");
		res.append("\r\n");
		res.append("    }else {");
		res.append("\r\n");
		res.append("      who.style.display=\"none\";");
		res.append("\r\n");
		res.append("      imgA.src = closeImg;");
		res.append("\r\n");
		// res.append("imgA.src = \"" + imgFatherTreeClose + "\";");
		// res.append("imgB.src = \"" + imgFatherItemClose + "\";");
		res.append("    }");
		res.append("\r\n");
		res.append("  }else{");
		res.append("\r\n");
		res.append("    getChildNode(who,imgA,pk,nodeName);");
		res.append("\r\n");
		res.append("  }");
		res.append("\r\n");
		res.append("}");
		res.append("\r\n");

		res.append("function radio_onClick(elements , desc ) {");
		res.append("\r\n");
		res.append("dsid=");
		res.append(idControl);
		res.append(";");
		res.append("\r\n");
		res.append("dsdesc=");
		res.append(descControl);
		res.append(";");
		res.append("\r\n");
		res.append("if(dsid!=null&&dsid!=\"\")");
		res.append("\r\n");
		res.append("dsid.value=elements.value;");
		res.append("\r\n");
		res.append("if(dsdesc!=null&&dsdesc!=\"\")");
		res.append("\r\n");
		res.append("dsdesc.value=desc;");
		res.append("\r\n");
		res.append("parent.div_reload(elements);");
		res.append("\r\n");
		res.append("}");
		res.append("\r\n");
		
		res.append("function checkbox_onClick(elements , desc ) {");
		res.append("\r\n");
		 
		res.append("dsid=");
		res.append(idControl);
		res.append(";");
		res.append("\r\n");
		res.append("dsdesc=");
		res.append(descControl);
		res.append(";");
		res.append("\r\n");
		
		//david.Yang�������޸�
		res.append("if(elements.checked){ \r\n"
				   +"  if (dsdesc.value ==''){ \r\n "
				   +"     dsdesc.value = elements.value+'#'+ desc; \r\n "
				   +"  }else{ \r\n"
				   +"     dsdesc.value = dsdesc.value +','+elements.value+'#'+ desc ; \r\n"
				   +"  } \r\n  " 
				   +"}else{ \r\n"
				   +"   var otherDesStr='' \r\n"
				   +"   var newDesStr = dsdesc.value.split(\",\"); \r\n"
				   +"   for(j=0;j<newDesStr.length;j++) { \r\n"
				   +"      var descStr =elements.value+'#'+desc; "
				   +"      if(newDesStr[j]!=descStr){ \r\n "
				   +"         if(otherDesStr ==''){ \r\n "
				   +"            otherDesStr =newDesStr[j]; \r\n "
				   +"         }else{ \r\n "
				   +"            otherDesStr =otherDesStr+','+ newDesStr[j] ; \r\n"
				   +"         }  \r\n"
				   +"      } \r\n"
				   +"    } \r\n"
				   +"    dsdesc.value = otherDesStr ; \r\n"
				   +"} \r\n " );		
		res.append(" dsid.value =''; \r\n");
		res.append(" checkName =document.getElementsByName(elements.name); \r\n");  
        res.append(" for(ct=0;ct<checkName.length;ct++) { \r\n"
                  +"   if (checkName[ct].checked){ \r\n"  
                  +"      if (dsid.value ==''){ \r\n"
                  +"          dsid.value =checkName[ct].value \r\n"
                  +"      }else{ \r\n"
                  +"          dsid.value =dsid.value+','+checkName[ct].value \r\n"
                  +"      } \r\n"
                  +"   } \r\n"
                  +" } \r\n"
        );
        
		
	/*  ע�����ɣ���ѡ��������ظ���Ԫ��ֵ
		res.append("if(elements.checked){");
		res.append("\r\n"); 
		res.append("if(dsid!=null&&dsid!=\"\")");
		res.append("\r\n");
		res.append("dsid.value+=elements.value +',' ;");
		res.append("\r\n");
		res.append("if(dsdesc!=null&&dsdesc!=\"\")");
		res.append("\r\n");
		res.append("dsdesc.value+=desc+',' ;");
		res.append("\r\n");
		res.append("} else {");
		res.append("\r\n");
		res.append("var otherStr=''");
		res.append("\r\n");
		res.append("var otherDesStr=''");
		res.append("\r\n");
		res.append("var newStr = dsid.value.split(\",\"); ");
		res.append("\r\n");
		res.append("var newDesStr = dsdesc.value.split(\",\"); ");
		res.append("\r\n");
		res.append("for(i=0;i<newStr.length;i++) {");
		res.append("\r\n");
		res.append("if(newStr[i]!=elements.value+'')");
		res.append("\r\n");
		res.append("otherStr+=newStr[i]+','; }");
		res.append("\r\n");
		res.append("dsid.value = otherStr ;");
		res.append("\r\n");
		res.append("for(j=0;j<newDesStr.length;j++) {");
		res.append("\r\n");
		res.append("if(newDesStr[j]!=desc)");
		res.append("\r\n");
		res.append("otherDesStr+=newDesStr[j]+','; }");
		res.append("\r\n");
		res.append("dsdesc.value = otherDesStr ;} ");
		res.append("parent.div_reload(elements);");
		res.append("\r\n");
	*/
		res.append("}");
        
		
		res.append("\r\n");
		res.append("</script>");
		res.append("\r\n");

		return res;
	}

	public static void main(String[] args) {
		Postern.reInit();
//		String[] types = "S,D".split(",");
//		ArrayList list = new ArrayList();
//		for (int i = 0; i < types.length; i++) {
//			list.add(types[i]);
//		}
//		StringBuffer res=generateDistrictJavaScriptTree(1, list,false,false,"", "aaa", "bbb");
//		StringBuffer res=generateBoroughJavaScriptTree(1, false, false, "checkBox", "aaa", "bbb");
		System.out.println("ok==============");
	}

}
