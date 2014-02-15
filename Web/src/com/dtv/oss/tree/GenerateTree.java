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
	 * 创建动态树的显示结果
	 * 
	 * @param dynamicRootNode
	 *            传入一个根节点
	 * @return 显示结果信息
	 */
	public static StringBuffer generateDynamicTree(
			DynamicRootNode dynamicRootNode) {

		StringBuffer returnMessage = new StringBuffer();
		// 节点被点击后树应该展开的深度
		int currrentClickDeep = 1;

		if (dynamicRootNode != null) {
			// 得到当前节点下的子节点
			Map currentMap = dynamicRootNode.getNodeMap();
			currrentClickDeep = dynamicRootNode.getCurrentClickDeep();
			Iterator nodeTreeIterator = currentMap.keySet().iterator();
			while (nodeTreeIterator.hasNext()) {
				StringBuffer currentParentBuffer = new StringBuffer();
				DynamicTreeNode currentNode = (DynamicTreeNode) currentMap
						.get((String) nodeTreeIterator.next());
				// 得到当前节点的显示内容
				returnMessage.append(writeTree(currrentClickDeep,
						currentParentBuffer, currentNode));
			}
		}
		return returnMessage;
	}

	/**
	 * 输出树的根结点开始的所有节点
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
		// 方法会迭代调用,
		// 当第一次进来时
		if (currentParentBuffer.length() == 0) {
			// 当前用户节点输出开始
			currentStringBuffer.append("<tr valign=\"middle\">\r\n");
			currentStringBuffer.append("<td></td>\r\n");
			// 当前用户节点的输出信息存储
			currentNodeBuffer.append(currentParentBuffer);
			currentNodeBuffer.append("<tr valign=\"middle\">\r\n");
			currentNodeBuffer.append("<td></td>\r\n");

			// 添加上节点上显示内容
			currentStringBuffer.append(outPutCurrentNodeMessage(
					currrentClickDeep, currentNode));
			// 当前用户节点输出结束
			currentStringBuffer.append("</tr>\r\n");

			// 当前用户节点的输出信息存储
			String currentAsParentReference = getPicture(6);

			// 添加节点前面的线条部分的内容
			// 当前节点的打开关闭状态
			if ("ON".equalsIgnoreCase(currentNode.getOnAndOff())) {
				// 如果当前节点有弟弟
				if (currentNode.haveBrother()) {
					currentNodeBuffer.append("<td><img src=\""
							+ currentAsParentReference
							+ "\" alt=\"\" border=\"0\"></td>\r\n");
				} else {
					currentNodeBuffer.append("<td></td>\r\n");
				}
				// 如果当前节点有孩子
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
		// 不是第一次
		else {
			currentStringBuffer.append(currentParentBuffer.toString());
			currentNodeBuffer.append(currentParentBuffer);
			// 添加上节点上显示内容
			currentStringBuffer.append(outPutCurrentNodeMessage(
					currrentClickDeep, currentNode));
			// 当前用户节点输出结束
			currentStringBuffer.append("</tr>\r\n");
			// 当前用户节点的输出信息存储
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
	 * 取得图片的位置信息
	 * 
	 * @param position
	 * @return
	 */
	private static String getPicture(int position) {
		String resultPicture = "";
		switch (position) {
		// 当前节点后面没有兄弟节点的展开图标地址（未展开）
		case 1:
			resultPicture = "img/tree/tree_Lplus.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("child_nobrother_close");
			break;
		// 前面后面都有兄弟节点图标地址（未展开）
		case 2:
			resultPicture = "img/tree/tree_Tplus.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("child_brother_close");
			break;
		// 当前节点后面没有兄弟节点的展开图标地址（展开）
		case 3:
			resultPicture = "img/tree/tree_Lminus.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("child_nobrother_open");
			break;
		// 当前节点前面后面都有兄弟的展开图标地址（展开）
		case 4:
			resultPicture = "img/tree/tree_Tminus.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("child_brother_open");
			break;
		// 十字线图标地址
		case 5:
			resultPicture = "img/tree/tree_T.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("nochild_brother");
			break;
		// 竖线图标地址
		case 6:
			resultPicture = "img/tree/tree_I.gif";
			// resultPicture=TreeNodeConfig.getInstance().getPictureReference("nochild_nobrother");
			break;
		// 折线图标地址
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
	 * 输出当前节点信息的共同输出函数 生成当前节点显示的图片和文字及链接等内容,图片部分一个单元格,文字部分一个单元格
	 * 
	 * @param currrentClickDeep当前节点展开层数
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

		// 如果有子节点并且后面有平行节点
		if (currentNode.hasChildNode() && currentNode.haveBrother()) {
			// 如果当前节点关闭状态,
			if ("OFF".equalsIgnoreCase(currentNode.getOnAndOff())) {
				currentpictureReference = getPicture(2);
			}
			// 如果当前节点为打状态,
			else if ("ON".equalsIgnoreCase(currentNode.getOnAndOff())) {
				currentpictureReference = getPicture(4);
			}
		}
		// 如果有子节点并且后面没有平行节点
		else if (currentNode.hasChildNode() && !currentNode.haveBrother()) {
			if ("OFF".equalsIgnoreCase(currentNode.getOnAndOff())) {
				currentpictureReference = getPicture(1);
			} else if ("ON".equalsIgnoreCase(currentNode.getOnAndOff())) {
				currentpictureReference = getPicture(3);
			}
		}
		// 有弟弟,没孩子
		else if (!currentNode.hasChildNode() && currentNode.haveBrother()) {
			currentpictureReference = getPicture(5);
		}
		// 没弟弟,没孩子
		else if (!currentNode.hasChildNode() && !currentNode.haveBrother()) {
			currentpictureReference = getPicture(7);
		}

		// 当前用户节点前图片部分输出,(打开状态,关闭状态,及打开状态时的操作action)
		// 当前节点有孩子
		if (currentNode.hasChildNode()) {
			currentStringBuffer.append("<td><a href=" + "\"treeAction.do?key="
					+ key + "\"><img src=\"" + currentpictureReference
					+ "\" alt=\"" + "close node\""
					+ " border=\"0\"></a></td>\r\n");
		}
		// 当前节点没孩子
		else {
			currentStringBuffer.append("<td><img src=\""
					+ currentpictureReference + "\" alt=\"" + "close node\""
					+ " border=\"0\"></td>\r\n");
		}

		// 点击当前节点是否进行数据检索

		if (currentNode.getCurrentNode().canAction()) {
			String actionTarget = currentNode.getCurrentNode()
					.getAtcionTarget();
			if (actionTarget != null && !"".equals(actionTarget)) {
				Map currentKeyAndValueMap = currentNode.getCurrentNode()
						.getKeyAndValueMap();
				String keyAndValueStr = "";
				// 如果当前节点的currentKeyAndValueMap(提交的参数)未有任何值
				if (currentKeyAndValueMap == null) {
					// 如果当前操作节点的ID为空
					if (key == null || "".equals(key)) {
						// 是否需要翻页功能
						if (currentNode.getCurrentNode().canHaveMultiplePage()) {
							keyAndValueStr = "?txtFrom="
									+ currentNode.getCurrentNode()
											.getPageFrom() + "&txtTo="
									+ currentNode.getCurrentNode().getPageTo();
						} else {
							keyAndValueStr = "";
						}
					}
					// 如果当前操作节点的ID不空
					else {
						// 是否需要翻页功能
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
				// 如果当前节点的currentKeyAndValueMap(提交的参数)有值
				else {
					Iterator keyIterator = currentKeyAndValueMap.keySet()
							.iterator();
					// 键值对数目
					int keyRecordCount = 0;
					while (keyIterator.hasNext()) {
						String parameterKey = (String) keyIterator.next();
						String parameterValue = (String) currentKeyAndValueMap
								.get(parameterKey);
						// 第一条
						if (keyRecordCount == 0) {
							keyAndValueStr = "?" + parameterKey + "="
									+ parameterValue;
						} else {
							keyAndValueStr = keyAndValueStr + "&"
									+ parameterKey + "=" + parameterValue;
						}
						keyRecordCount++;
					}
					// 需要翻页
					if (currentNode.getCurrentNode().canHaveMultiplePage()) {
						keyAndValueStr = keyAndValueStr + "&txtFrom="
								+ currentNode.getCurrentNode().getPageFrom()
								+ "&txtTo="
								+ currentNode.getCurrentNode().getPageTo();
					}
				}
				// 生成文字显示部分
				currentStringBuffer
						.append("<td nowrap colspan=\""
								+ String.valueOf(colspan)
								+ "\"> <a href=\""
								+ actionTarget
								+ keyAndValueStr
								+ "\"  target=\"rightFrame\" class=\"tree-control-unselected\" onclick=\"self.location.href='treeAction.do?key="
								+ key + "'\">" + keyName + "</a></td>\r\n");
			}
			// 如果当前节点上没有操作,添加空的链接
			else {
				currentStringBuffer
						.append("<td nowrap colspan=\""
								+ String.valueOf(colspan)
								+ "\"> <a href=\"#"
								+ "\"   target=\"leftFrame\"  class=\"tree-control-unselected\" onclick=\"self.location.href='treeAction.do?key="
								+ key + "'\">" + keyName + "</a></td>\r\n");
			}

		}
		// 如果当前节点上不用操作,添加空的链接
		else {
			currentStringBuffer
					.append("<td nowrap colspan=\""
							+ String.valueOf(colspan)
							+ "\"> <a href=\"#"
							+ "\" target=\"leftFrame\" class=\"tree-control-unselected\" onclick=\"self.location.href='treeAction.do?key="
							+ key + "'\">" + keyName + "</a></td>\r\n");
		}
		// 返回输出信息
		return currentStringBuffer;
	}

	/**
	 * 该方法用于打开树上一系列节点
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
			// 如果当前节点的儿子节点中有KEY,则打开
			if (currentTreeNode.containChild(key)) {
				currentTreeNode.setOnAndOff("OFF");
				currentTreeNode.getChildNode(key).setOnAndOff("OFF");
				LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
						"openTreeNode0:" + currentTreeNode.getChildNode(key));
				return true;
			}
			// 否则就让儿子去找孙子
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
	 * 用来实现嵌套查找并更改内容
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
	 * 根据用户对节点的点击结果更改树中的对应内容
	 * 
	 * @param key
	 * @param dynamicRootNode
	 * @return 返回更改后的根树对象
	 */
	public static DynamicRootNode changeDynamicTreeNode(String key,
			DynamicRootNode dynamicRootNode) {
		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
				"changeDynamicTreeNode当前Key:" + key);
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
	 * javascript树生成
	 */

	private static String fatherDivStyle = "";

	private static String childDivStyle = "";

	// private static String onMouseOverStyle = "";
	//
	// private static String onMouseOutStyle = "";

	// 所有区域ID的关系
	private static Map idMapTypeMap = null;

	// 所有的区域DTO
	private static Map treeHashMap = null;

	// 当前管理的区域
	private static List orgGovernedDistrict = null;

	// 当前管理区域所有的节点
	private static List curDistrictPathList = null;

	// radio可用的类型列表
	private static List createRadioList = null;

	// 层缩进
	private static int space = 18;

	// 展开的级别,当树组织小于这个数打,子节点默认打开,树从0开始计层
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
		// 取得当前管理组织可以拥有的所有区域
		organizationIDMap = Postern.getOrganizationForCreateTree();
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"取出organizationIDMap\r\n" + organizationIDMap.toString());
//		System.out.println("取出organizationIDMap\r\n"
//				+ organizationIDMap.toString());


//		orgAndSubOrgConfig = Postern.getOrgAndSubOrgConfig();

		/**
		 * rootOrgID是从当前组织向上回溯到具有客户管理权限的最近上级组织,
		 * 这个有点说不通,因为显示组织和客户管理权限并没有直接关系,但是不回溯的时候像总师办等这种没有直属下级的操作员登陆会只显示当前部门,
		 * 无法满足当前需求,
		 */
		int rootOrgID = Postern.getParentHasCustomerOrgID(orgID);

		curEnableOrgList = null;
		curEnableOrgList=getEnableNodeList(rootOrgID);
//		System.out.println("取出curEnableOrgList\r\n"
//				+ curEnableOrgList.toString());

		curOrganizationPathList = null;
		curOrganizationPathList = getPathNodeList(orgID);
		curOrganizationPathList.addAll(curEnableOrgList);
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"取出curOrganizationPathList\r\n"
//						+ curOrganizationPathList.toString());
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"取出curOrganizationPathList\r\n"
//				+ curOrganizationPathList.toString());


//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"取出curEnableOrgList\r\n" + curEnableOrgList.toString());
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"取出curEnableOrgList\r\n"
//				+ curEnableOrgList.toString());

		if (organizationHashMap == null || organizationHashMap.isEmpty()
				|| organizationIDMap == null || organizationIDMap.isEmpty()
				|| curOrganizationPathList == null
				|| curOrganizationPathList.isEmpty()
				|| curEnableOrgList == null || curEnableOrgList.isEmpty()) {
			treeBuffer.append("数据提取出错");
			LogUtility.log(GenerateTree.class, LogLevel.ERROR, "生成树的数据准备不足");
			return treeBuffer;
		}
		if ((showOrgTypeList == null || showOrgTypeList.isEmpty())&&!isShowAll) {
			treeBuffer.append("设置出错");
			LogUtility.log(GenerateTree.class, LogLevel.ERROR,
					"设置出错,没有设置可用组织类型");
			return treeBuffer;
		}

		treeBuffer.append(generateOrganizationRootNode(0, 0));
		treeBuffer.append(addNodeFunction(retrunOrgID, retrunOrgDesc));

		// LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
		// "最后的结果:=================================\r\n"
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
		// 取当前节点下的子节点
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
				//organizationIDMap取值没有进行状态过滤,会取所有ORG,organizationHashMap进行了状态过滤,集合小于前者,会出现空值,进行过滤
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
				String curDesc = desc.equals("") ? desc : desc + "◇";
				curDesc = curDesc + curOrg.getOrgName();

				boolean isCreateRadio = false;
				String curType = curOrg.getOrgType();
				if (curEnableOrgList.contains(curID))
					isCreateRadio = true;

				boolean isLastNode = false;
				if (i == nodes.size() - 1)
					isLastNode = true;

				ArrayList childNodes = getOrgChildNodes(curOrg.getOrgID());

				// 如果当前节点是个父节点,进入跌代
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
		// 取得当前操作员可以管理客户的组织机构id,具有客户管理权的最近的组织
		int rootOrgID = Postern.getParentHasCustomerOrgID(orgID);
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,"rootOrgID:" + rootOrgID);
		
		curDistrictPathList = null;
		curDistrictPathList = Postern
				.getDistrictTreePathListWithOrgID(rootOrgID);
		
		
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
//				"取出curDistrictPathList\r\n" + curDistrictPathList.toString());

		orgGovernedDistrict = null;
		orgGovernedDistrict = Postern.getOrgGovernedDistrictByOrgID(rootOrgID);
	//	LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
	//			"取出orgGovernedDistrict\r\n" + orgGovernedDistrict.toString());
	}
	/**
	 * 生成区域选择的树
	 * 
	 * @param orgID哪个组织管理的区域
	 * @param crtateRadionList可以选择的区域列表
	 * @param retrunDistrictID返回ID值的控件
	 * @param retrunDistrictDesc返回描述字串的控件
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
		
        // 取得当前管理组织可以拥有的所有区域
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
			treeBuffer.append("数据提取出错");
			LogUtility.log(GenerateTree.class, LogLevel.ERROR, "生成树的数据准备不足");
			return treeBuffer;
		}
		if (retrunDistrictID == null || retrunDistrictID.equals("")
				|| retrunDistrictDesc == null || retrunDistrictDesc.equals("")) {
			treeBuffer.append("设置出错");
			LogUtility.log(GenerateTree.class, LogLevel.ERROR,
					"设置出错,没有设置有效的返回值接收控件");
			return treeBuffer;
		}

		treeBuffer.append(generateDistrictRootNode(0, 0));
		treeBuffer
				.append(addNodeFunction(retrunDistrictID, retrunDistrictDesc));

		// LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
		// "最后的结果:=================================\r\n"
		// + treeBuffer.toString());

		return treeBuffer;
	}

	/**
	 * 编译根节点,根节点从belongto为0起
	 * 
	 * @param rootDistrictID
	 *            根节点ID
	 * @return
	 */
	private static StringBuffer generateDistrictRootNode(int rootDistrictID, int level) {
		StringBuffer res = new StringBuffer();

//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG, "rootDistrictID:"
//				+ rootDistrictID);
//		LogUtility.log(GenerateTree.class, LogLevel.DEBUG, "level:" + level);
		level++;

		// res.append(addFatherNodeDiv(rootDistrictID+"", "行政区域","",
		// false, true,level<showLevel?true:false));

		// res.append(addChildDivStart(rootDistrictID+"",true));
		res.append("<div id=\"childHead\" style=\"display:'block' class=\""
				+ childDivStyle + "\">");
		// 取当前节点下的子节点
		ArrayList childNodes = getDistrictChildNodes(rootDistrictID);	
		res.append(generateDistrictChildNode(childNodes, level));
		res.append(addChildDivEnd());
		return res;
	}

	/**
	 * 根据组织机构取他下面可以管理的孩子
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
	 * 编译一个节点,会跌代进行,
	 * 
	 * @param nodes
	 *            节点的集合,如果集合中的节点存在子节点,进入跌代
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


				// 如果当前节点是个父节点,进入跌代
				if (childNodes != null && !childNodes.isEmpty()) {
					//如果isOnlyLeaf成立,所有父节点不可选,
					res.append(addFatherNodeDiv(curID.toString(), curDS
							.getName(), curDesc, isCreateRadio&&!isOnlyLeaf, isLastNode,
							isOpen,"distTree"));

					res.append(addChildDivStart(curID.toString(), isOpen));
                    //	当前层大于加载的限制,不递归,只生成空的子节点
					
					if(level < showLevel){
					   res.append(generateDistrictChildNode(childNodes,level));
					}
					res.append(addChildDivEnd());
				} else {
					//如果显示节点类型不空并isOnlyLeaf为真,取isCreateRadio,
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
	 * 取任意节点下节点的方法 .
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
	 * 生成父节点层,由于父节点层后面还有个子节点集合层
	 * 
	 * @param id
	 *            父节点层的ID,
	 * @param name
	 *            显示的内容
	 * @param isCreateRadio
	 *            是否radio
	 * @return
	 */
	private static StringBuffer addFatherNodeDiv(String id, String name, String desc,
			boolean isCreateRadio, boolean isLast, boolean isOpen,String nodeName) {
		StringBuffer res = new StringBuffer();

		// 是否要添加子层封闭
		res.append(" <div id=\"").append(id).append("\"");
		// 设置父节点层样式
		res.append(" class=\"").append(fatherDivStyle).append("\"");
		// 设置层上点击事件,
		if (!"orgTree".equals(nodeName)){
		   res.append(" onclick=\"node_onClick('child").append(id).append("','imgA").append(id).append("',").append(id).append(", '").append(nodeName).append("')\"");
		} else{
		   res.append(" onclick=\"node_onClick(child").append(id).append(",imgA").append(id).append(",").append(id).append(", '").append(nodeName).append("')\"");
		}
		// // 设置父节点上鼠标悬停样式
		// res.append(" onmouseover=\"this.className=" + onMouseOverStyle +
		// "\"");
		// // 设置父节点上鼠标移出样式,
		// res.append(" onmouseout=\"this.className=" + onMouseOutStyle +
		// "\">");
		res.append(">");
		res.append("\r\n");

		res
				.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td nowrap valign=\"middle\">");
		// 设置父节点上树形结构图片,默认为关闭
		res.append("<img src=\"");
		if (isOpen) {
			res.append(openImg);
		} else {
			res.append(closeImg);
		}
		res.append("\" id=\"imgA").append(id).append("\" style='cursor:hand'");
		res.append(" >");

		res.append("</td><td nowrap valign=\"middle\" style='cursor:hand'>");
		// // 设置父节点上树形项目图片,默认为关闭
		// res.append("<img src=\"" + getPicture(1) + "\" id=\"imgB"
		// + id + "\">");
		// res.append("\r\n");
		// 设置radio
		if("checkbox".equalsIgnoreCase(labelType))
			res.append(addCheckBox(id, desc, isCreateRadio));	
		else
		  res.append(addRadio(id, desc, isCreateRadio));

		res.append(name);
		// // 设置父节点上显示内容,超链暂时不用
		// res.append("<a valign=\"center\" href=\"#\">" + name + "</a>");
		res.append("</td></tr></table>");
		res.append("\r\n");
		res.append("</div>");
		res.append("\r\n");

		return res;
	}

	/**
	 * 设置子节点层的开头,参一为父节点
	 * 
	 * @param childDivID
	 *            子节点层的ID
	 * @return
	 */
	private static StringBuffer addChildDivStart(String childDivID, boolean isOpen) {
		StringBuffer res = new StringBuffer();
		// 设置父节点下子层的ID,样式,和默认样式,默认形为父节点下一层是子节点,否则出错,
		res.append("<div id=\"child").append(childDivID).append("\" class=\"")
		   .append(childDivStyle).append("\"");
		// 设置层上缩进

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
	 * 生成层结尾
	 * 
	 * @return
	 */
	private static StringBuffer addChildDivEnd() {
		StringBuffer res = new StringBuffer();
		res.append("</div>");
		res.append("\r\n");
		// LogUtility.log(GenerateTree.class, LogLevel.DEBUG,
		// "addChildDivEnd()生成的内容:" + res.toString());
		return res;
	}

	/**
	 * 生成子节点内容,只一行的东西,包括树的图片,节点的图片,radio,显示的内容(超链),
	 * 
	 * @param name
	 *            显示的主要内容
	 * @param url
	 *            连接
	 * @param target
	 *            目标
	 * @param isCreateRadio
	 *            是否可用的radio
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
		// 暂时不用超链
		// res.append("<a valign=\"center\" href=\"" + url + "\" target=\"" +
		// target + "\">" + name
		// + "</a>" + "<br>");
		res.append("</td></tr></table>");
		res.append("\r\n");

		return res;
	}

	/**
	 * 生成radio控件
	 * 
	 * @param isCreateRadio
	 *            控件是否可用
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
	 * 生成checkbox控件
	 * 
	 * @param isCreate
	 *            控件是否可用
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
	 * 生成页面点击动作处理的javascript函数
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
		
		//david.Yang做以下修改
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
        
		
	/*  注释理由：多选后带来了重复的元素值
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
