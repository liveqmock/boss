package com.dtv.oss.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.tree.Constant;
import com.dtv.oss.tree.DynamicTreeNode;
import com.dtv.oss.tree.NodeMessage;

/**
 * 用于构建树形结构的类,
 * 
 * @author 260327h
 * 
 */
public class TreePostern {

	// 缓存,生成树的数据
	private static ArrayList treeList = null;
	private static Map orgConfig=null;

	private static final String strAction = "district_setting_query_result.do";

	private static final String strSortNodeAction = "#";

	private static final String strOrgQueryAction = "organization_query_result.do";
	
	private static final String strCAAction = "ca_info_index.do";
	
	private static final String strVODAction = "vod_interface_index.do";
	
	private static final String strLdapction = "ldap_interface_index.do";
	
	private static final String strVoiceAction = "voip_info_index.do";
	
	private static final String strBandAction = "band_info_index.do";
	

	private static final String dist_query_sql = "select * from t_districtsetting start with belongto=0 connect by prior id = belongto ORDER BY districtcode DESC";

	private static final String org_query_sql = "select * from t_organization connect by prior orgid=parentorgid start with orgtype='C' order by orgtype,orgsubtype,orgid";

	// 检索数据库,得到需要的数据,
	private static ArrayList initTreeData(String type) {
		ArrayList treeData = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		if (type.equalsIgnoreCase("dist"))
			sql = dist_query_sql;
		else if (type.equalsIgnoreCase("org"))
			sql = org_query_sql;
		else
			return treeData;

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			// initialize t_organization
			rs = stmt.executeQuery(sql);
			Object theDto = null;
			while (rs.next()) {
				if (type.equalsIgnoreCase("dist"))
					theDto = DtoFiller.fillDistrictSettingDTO(rs);
				else if (type.equalsIgnoreCase("org"))
					theDto = DtoFiller.fillOrganizationDTO(rs);
				treeData.add(theDto);
			}
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out.println("TreePostern getTreeData exception:"
						+ e.getMessage());
				e.printStackTrace();
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return treeData;
	}


	public static DynamicTreeNode initRootNode(int key, int level, String type) {
		treeList = initTreeData(type);
		orgConfig=Postern.getOrgAndSubOrgConfig();

		DynamicTreeNode districtNode = null;

		if (type.equalsIgnoreCase("dist")) {
			districtNode = initDistrictSettingRootNode(key, level);
			districtNode.setChildNodeMap(buildDistrictSettingChild(key, level));
		} else {
			districtNode = initOrganizationRootNode(key, level);
			districtNode
					.setChildNodeMap(buildOrganizationChild(key + "", level));
		}

		return districtNode;
	}
	
	public static Collection initRootNode(int level,ArrayList list) {
		 
		DynamicTreeNode interfaceNode = null;
		
        Collection nodeCol = initInterfaceSettingRootNode(level,list);
		 
	 

		return nodeCol;
	}
	/**
	 * 初始化网络接口根节点
	 * 
	 */
	private static Collection initInterfaceSettingRootNode(int level,ArrayList list) {

		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "初始化网络接口节点====");
		 
		int count = list.size();
		Collection colall=new ArrayList();
		//当只有一个节点的情况
		if(count==1){
		   
			Map.Entry entry=(Map.Entry)list.get(0);
			String interfaceKey=(String)entry.getKey();
			String interfaceName=(String)entry.getValue();
			DynamicTreeNode anyNode = new DynamicTreeNode();
			anyNode.setCurrentLevel(level);
			anyNode.setHaveBrother(false); 
			anyNode.setIsFirstChild(true);
			anyNode.setIsLastChild(true);
			NodeMessage anyMessage = new NodeMessage();
			anyMessage.setKey(interfaceKey);
			anyMessage.setKeyName(interfaceName);
			anyMessage.setCanAction(true);
			setAction(interfaceKey,anyMessage);
			anyNode.setCurrentNode(anyMessage);
			colall.add(anyNode);
		} else{
			for(int i=0 ; i<count-1;i++){
				Map.Entry entry=(Map.Entry)list.get(i);
				String interfaceKey=(String)entry.getKey();
				String interfaceName=(String)entry.getValue();
				DynamicTreeNode anyNode = new DynamicTreeNode();
				anyNode.setCurrentLevel(level);
				anyNode.setHaveBrother(true);
				anyNode.setIsFirstChild(false);
				anyNode.setIsLastChild(false);
				NodeMessage anyMessage = new NodeMessage();
				anyMessage.setKey(interfaceKey);
				anyMessage.setKeyName(interfaceName);
				anyMessage.setCanAction(true);
				setAction(interfaceKey,anyMessage);
				anyNode.setCurrentNode(anyMessage);
				colall.add(anyNode);
			}
			Map.Entry entry=(Map.Entry)list.get(count-1);
			String lastInterfaceKey=(String)entry.getKey();
			String lastInterfaceName=(String)entry.getValue();
			DynamicTreeNode anyNode = new DynamicTreeNode();
			anyNode.setCurrentLevel(level);
			anyNode.setHaveBrother(false);
			anyNode.setIsFirstChild(false);
			anyNode.setIsLastChild(true);
			NodeMessage anyMessage = new NodeMessage();
			anyMessage.setKey(lastInterfaceKey);
			anyMessage.setKeyName(lastInterfaceName);
			anyMessage.setCanAction(true);
			setAction(lastInterfaceKey,anyMessage);
			anyNode.setCurrentNode(anyMessage);
			colall.add(anyNode);
		}
		 
		return colall;
		 
		/** 
		DynamicTreeNode caNode = new DynamicTreeNode();
		caNode.setCurrentLevel(level);
		caNode.setHaveBrother(true);
		caNode.setIsFirstChild(false);
		caNode.setIsLastChild(false);
		
		  NodeMessage caMessage = new NodeMessage();
		  caMessage.setKey("CA_" + 0);
		  caMessage.setKeyName("CA接口");
		  caMessage.setCanAction(true);
		  caMessage.setAtcionTarget(strCAAction);
		  caNode.setCurrentNode(caMessage);
		  list.add(caNode);
		  DynamicTreeNode vodNode = new DynamicTreeNode();
		  vodNode.setCurrentLevel(level);
		  vodNode.setHaveBrother(true);
		  vodNode.setIsFirstChild(false);
		  vodNode.setIsLastChild(false); 
		  
		  NodeMessage vodMessage = new NodeMessage();
		  vodMessage.setKey("VOD_" + 0);
		  vodMessage.setKeyName("VOD接口");
		  vodMessage.setCanAction(true);
		  vodMessage.setAtcionTarget(strVODAction);
		  vodNode.setCurrentNode(vodMessage);
		  list.add(vodNode);
		  DynamicTreeNode ldapNode = new DynamicTreeNode();
		  ldapNode.setCurrentLevel(level);
		  ldapNode.setHaveBrother(true);
		  ldapNode.setIsFirstChild(false);
		  ldapNode.setIsLastChild(false); 
		  
		  NodeMessage ldapMessage = new NodeMessage();
		  ldapMessage.setKey("LDAP_" + 0);
		  ldapMessage.setKeyName("LDAP接口");
		  ldapMessage.setCanAction(true);
		  ldapMessage.setAtcionTarget(strLdapction);
		  ldapNode.setCurrentNode(ldapMessage);
		  list.add(ldapNode);
		
		  DynamicTreeNode voiceNode = new DynamicTreeNode();
		  voiceNode.setCurrentLevel(level);
		  voiceNode.setHaveBrother(false);
		  voiceNode.setIsFirstChild(true);
		  voiceNode.setIsLastChild(true); 
		
		  NodeMessage voiceMessage = new NodeMessage();
		  voiceMessage.setKey("VOIP_" + 0);
		  voiceMessage.setKeyName("语音接口");
		  voiceMessage.setCanAction(true);
		  voiceMessage.setAtcionTarget(strVoiceAction);
		  voiceNode.setCurrentNode(voiceMessage);
		  list.add(voiceNode);
		 
		  NodeMessage districtMessage2 = new NodeMessage();
		  districtMessage.setKey("LDAP_" + 2);
		  districtMessage.setKeyName("VOD接口");
		  districtMessage.setCanAction(false);
		  districtMessage.setAtcionTarget(strLDAPAction);
		  districtNode.setCurrentNode(districtMessage2);
		  **/

		
	}

	/**
	 * @param interfaceKey
	 */
	private static void setAction(String interfaceKey,NodeMessage message) {
		if("C".equalsIgnoreCase(interfaceKey))
			message.setAtcionTarget(strCAAction); 
		if("L".equalsIgnoreCase(interfaceKey))
			message.setAtcionTarget(strLdapction); 
		if("N".equalsIgnoreCase(interfaceKey))
			message.setAtcionTarget(strVoiceAction); 
		if("V".equalsIgnoreCase(interfaceKey))
			message.setAtcionTarget(strVODAction); 
		if("B".equalsIgnoreCase(interfaceKey))
			message.setAtcionTarget(strBandAction);
	}


	/**
	 * 初始化行政根节点
	 * 
	 */
	private static DynamicTreeNode initDistrictSettingRootNode(int key,
			int level) {

		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "初始化行政根节点====");
		DynamicTreeNode districtNode = new DynamicTreeNode();
		districtNode.setCurrentLevel(level);
		districtNode.setHaveBrother(false);
		districtNode.setIsFirstChild(false);
		districtNode.setIsLastChild(true);
		NodeMessage districtMessage = new NodeMessage();
		districtMessage.setKey("district_" + key);
		districtMessage.setKeyName("行政区");
		districtMessage.setCanAction(true);
		districtMessage.setAtcionTarget(strAction);
		districtMessage.putKeyAndValue("txtQryBelongTo", String.valueOf(0));
		districtNode.setCurrentNode(districtMessage);

		// districtNode.setOnAndOff("ON");

		return districtNode;
	}

	/**
	 * 初始化组织根节点
	 * 
	 */
	private static DynamicTreeNode initOrganizationRootNode(int key, int level) {

		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "初始化组织根节点====");

		DynamicTreeNode organizationNode = new DynamicTreeNode();
		organizationNode.setCurrentLevel(1);
		organizationNode.setHaveBrother(false);
		organizationNode.setIsFirstChild(false);
		organizationNode.setIsLastChild(true);
		NodeMessage organizationMessage = new NodeMessage();
		organizationMessage.setKey("org_0");
		organizationMessage.setKeyName("组织机构");
		organizationMessage.setCanAction(true);
		organizationMessage.setAtcionTarget(strOrgQueryAction);
		organizationMessage.putKeyAndValue("txtQryBelongTo", key+"");
		organizationNode.setCurrentNode(organizationMessage);

		return organizationNode;
	}


	// 递归的方法,生成整颗树,
	private static LinkedHashMap buildOrganizationChild(String key, int level) {

		LogUtility.log(TreePostern.class, LogLevel.DEBUG,
				"进入buildOrganizationChild,当前KEY:" + key);
		LogUtility.log(TreePostern.class, LogLevel.DEBUG,
				"进入buildOrganizationChild,当前level:" + level);
		LinkedHashMap nodeChild = new LinkedHashMap();
		// OrganizationDTO
		boolean isFirstChild = false;
		boolean isLastChild = false;
		boolean haveBrother = false;

		ArrayList Childs = new ArrayList();

		int intKey = 0;
		/**
		 * makeTreeNodeByOrganizationDTO()中使用,
		 * 因为树形加入了额外的东西,和原数据库中不同,显示上的parent有变化
		 */
		String parentKey = "";
		//当前DTO,用来取type
		OrganizationDTO curDto = null;
		//当前节点的类型,用于取孩子
		String curType = "";
		/**
		 * childs集合会有三种取值情况, 1,传进来key是ID,但该ID为总公司或分公司类型,该节点下应该挂公用数据生成的节点,
		 * 2,传进来key是ID,普通情况, curType为null,正常取值.
		 * 3,传进来一个字符串,则认为是解析公用数据节点,将该字串分解,取出parentID,type,name,用parentID和type取集合,
		 * 情况改变了,如果该节点TYPE有SUBTYPE则会产生下级节点
		 */
		try {
			intKey = Integer.parseInt(key);
			curDto = getOrganizationDto(intKey);
			parentKey = "org" + "_" + key;
//			System.out.println("cruDto========" + curDto);
			if (curDto != null && curDto.getOrgType() != null) {
				//取下级类型
				String subOrgType=(String) orgConfig.get(curDto.getOrgType());
				//System.out.println("subOrgType=======" + subOrgType);
				//下级类型不空
				if (subOrgType!=null&&!subOrgType.equalsIgnoreCase(""))
					Childs = getSortNodes(key,subOrgType);
			} else {
				Childs = getOrganizationChild(intKey, curType);
			}
		} catch (NumberFormatException e) {
			String[] unType = key.split("=");
			intKey = Integer.parseInt(unType[0]);
			curType = unType[1];
			Childs = getOrganizationChild(intKey, curType);
			parentKey = "org" + "_" + intKey + "_" + curType;
		}

		// 孩子们多于一,则认为是有brother的
		if (Childs.size() > 1)
			haveBrother = true;
		for (int i = 0; i < Childs.size(); i++) {
			// 第一个孩子
			if (i == 0)
				isFirstChild = true;
			else
				isFirstChild = false;
			// 最后一个孩子
			if ((i + 1) == Childs.size()) {
				haveBrother = false;
				isLastChild = true;
			} else
				isLastChild = false;

			DynamicTreeNode node = null;
			String curKey = null;
			Object obj = Childs.get(i);
			/**
			 * 如果当前集合下包装的是DTO,则把DTO封装成NODE对象添加 如果是一个String,
			 */
			if (obj instanceof OrganizationDTO) {
				OrganizationDTO theDto = (OrganizationDTO) obj;
				// 把DTO对象加工成一个NODE对象
				node = makeTreeNodeByOrganizationDTO(theDto, parentKey,
						level + 1, isFirstChild, isLastChild, haveBrother);
				int orgID = theDto.getOrgID();
				curKey = orgID + "";

				if (isOrganizationFatherNode(orgID)) {
					// LogUtility.log(TreePostern2.class, LogLevel.DEBUG,
					// "将进入了一次迭代,当前KEY:" + curKey + "当前level:" + level);
					node.setChildNodeMap(buildOrganizationChild(curKey,
							level + 1));
				}
			}
			// 如果当前集合是TYPE集合,
			else if (obj instanceof String) {
				node = makeTreeNodeByOrgType(obj.toString(), level + 1,
						isFirstChild, isLastChild, haveBrother);
				// type节点都当做你节点,不用判断
				node.setChildNodeMap(buildOrganizationChild(obj.toString(),
						level + 1));
			}
//			LogUtility
//					.log(TreePostern.class, LogLevel.DEBUG, "构造的NODE:" + node);
			nodeChild.put(node.getCurrentNode().getKey(), node);
		}

		return nodeChild;
	}
	
//	private static ArrayList getOrgChilds(String key){
//		ArrayList res=new ArrayList();
//		String[] arrKey=key.split("=");
//		int intKey=0;
//		String curType="";
//		String subOrgType="";
//		//当前节点是个分类节点
//		if(arrKey.length==3){
//			intKey=Integer.parseInt(arrKey[0]);
//			curType=arrKey[1];
//		}
//		intKey=Integer.parseInt(key);
//		OrganizationDTO curDto = getOrganizationDto(intKey);
//		curType=curDto.getOrgType();
//		subOrgType=(String) orgConfig.get(curDto.getOrgType());
//		if(subOrgType!=null&&!subOrgType.equals("")){
//			return getSortNodes(intKey+"",subOrgType);
//		}else{
//			
//		}
//		return res;
//	}

	
	private static ArrayList getSortNodes(String id,String subOrgType) {
		Map orgTypes = Postern
				.getHashKeyValueByName("SET_S_ORGANIZATIONORGTYPE");
		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "从公用数据得到的map:"
				+ orgTypes);
		
		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "用来筛选分公司的subOrgType:"+ subOrgType);

		Iterator it = orgTypes.keySet().iterator();

		ArrayList res = new ArrayList();
		while (it.hasNext()) {
			String key = it.next().toString();
			String name = orgTypes.get(key).toString();
			if(subOrgType.indexOf(key)>=0)
				res.add(id + "=" + key + "=" + name);
		}
		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "得到的分类节点:" + res);
		return res;
	}

	private static DynamicTreeNode makeTreeNodeByOrgType(String type,
			int currentLevel, boolean isFirst, boolean isLast,
			boolean haveBrother) {
//		LogUtility.log(TreePostern.class, LogLevel.DEBUG,
//				"makeTreeNodeByOrgType" + type);

		String[] arType = type.split("=");
		DynamicTreeNode typeNode = new DynamicTreeNode();
		if (arType.length < 1)
			return typeNode;
		String typeKey = "";
		String beLongToKey = "";
		typeKey = "org" + "_" + arType[0] + "_" + arType[1];
		beLongToKey = "org" + "_" + arType[0];
		// 树节点构造
		typeNode.setCurrentLevel(currentLevel);
		typeNode.setIsFirstChild(isFirst);
		typeNode.setHaveBrother(haveBrother);
		typeNode.setIsLastChild(isLast);
		typeNode.setParentNodeKey(beLongToKey);

		NodeMessage typeMessage = new NodeMessage();
		typeMessage.setKey(typeKey);
		typeMessage.setKeyName(arType[2]);
		typeMessage.setCanAction(true);
		typeMessage.putKeyAndValue("txtQryBelongTo", typeKey);
		typeMessage.setAtcionTarget(strOrgQueryAction);
		typeNode.setCurrentNode(typeMessage);

		return typeNode;

	}

	private static boolean isOrganizationFatherNode(int key) {
		for (int i = 0; i < treeList.size(); i++) {
			OrganizationDTO theDto = (OrganizationDTO) treeList.get(i);
			if (theDto.getParentOrgID() == key)
				return true;
		}
		return false;
	}

	private static DynamicTreeNode makeTreeNodeByOrganizationDTO(
			OrganizationDTO theDto, String parentKey, int currentLevel,
			boolean isFirst, boolean isLast, boolean haveBrother) {

		String orgKey = "";
		orgKey = "org" + "_" + theDto.getOrgID();
		// 树节点构造
		DynamicTreeNode orgNode = new DynamicTreeNode();
		orgNode.setCurrentLevel(currentLevel);
		orgNode.setIsFirstChild(isFirst);
		orgNode.setHaveBrother(haveBrother);
		orgNode.setIsLastChild(isLast);
		orgNode.setParentNodeKey(parentKey);

		NodeMessage orgMessage = new NodeMessage();
		orgMessage.setKey(orgKey);
		orgMessage.setKeyName(theDto.getOrgName());
		orgMessage.setCanAction(true);
		orgMessage.putKeyAndValue("txtQryBelongTo", theDto.getOrgID() + "");
		orgMessage.setAtcionTarget(strOrgQueryAction);
		orgNode.setCurrentNode(orgMessage);

		return orgNode;

	}

	/**
	 * 得到组织节点孩子的方法 
	 * 根据父节点的type不同,取不一样的孩子
	 * 
	 * @param key
	 * @return
	 */
	private static ArrayList getOrganizationChild(int key, String type) {
		ArrayList childs = new ArrayList();
//		//先取出子节点type
//		String subType=orgConfig.get(type).toString();

		for (int i = 0; i < treeList.size(); i++) {
			OrganizationDTO theDto = (OrganizationDTO) treeList.get(i);
			if (type != null && !type.equals("")) {
				// 孩子的父亲必须先是正确的,并且类型在父类型表中,
				if (theDto.getParentOrgID() == key
						&& theDto.getOrgType().equalsIgnoreCase(type)) {
					childs.add(theDto);
				}
			} else {
				if (theDto.getParentOrgID() == key) {
					childs.add(theDto);
				}
			}
		}
		return childs;
	}

	private static OrganizationDTO getOrganizationDto(int id) {
		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "getDto:,当前id:" + id);
		for (int i = 0; i < treeList.size(); i++) {
			OrganizationDTO theDto = (OrganizationDTO) treeList.get(i);
			if (theDto.getOrgID() == id) {
				return theDto;
			}
		}
		return new OrganizationDTO();
	}
	/**
	 * ============================================================================================
	 * 以下方法属于行政树生成
	 */
	/**
	 * 生成一个父节点下的子节点集合,该方法会递归调用
	 * 
	 * @param key,父节点的ID
	 * @param level,父节点当前级别
	 * @return
	 */
	private static LinkedHashMap buildDistrictSettingChild(int key, int level) {
		LinkedHashMap nodeChild = new LinkedHashMap();

		boolean isFirstChild = false;
		boolean isLastChild = false;
		boolean haveBrother = false;
		ArrayList dtoChild = getDistrictSettingChild(key);
		// 孩子们多于一,则认为是有brother的
		if (dtoChild.size() > 1)
			haveBrother = true;
		for (int i = 0; i < dtoChild.size(); i++) {
			// 第一个孩子
			if (i == 0)
				isFirstChild = true;
			else
				isFirstChild = false;
			// 最后一个孩子
			if ((i + 1) == dtoChild.size()) {
				haveBrother = false;
				isLastChild = true;
			} else
				isLastChild = false;

			DistrictSettingDTO theDto = (DistrictSettingDTO) dtoChild.get(i);
			// 把DTO对象加工成一个NODE对象
			DynamicTreeNode node = makeTreeNodeByDistrictSettingDTO(theDto,
					level + 1, isFirstChild, isLastChild, haveBrother);
			int curKey = theDto.getId();

			if (isDistrictSettingFatherNode(curKey)) {
				// LogUtility.log(TreePostern2.class, LogLevel.DEBUG,
				// "将进入了一次迭代,当前KEY:" + curKey + "当前level:" + level);
				node.setChildNodeMap(buildDistrictSettingChild(curKey,
						level + 1));
			}
			nodeChild.put(node.getCurrentNode().getKey(), node);
		}

		return nodeChild;
	}

	/**
	 * 用来得到一个节点下所有子节点,
	 * 
	 * @param key
	 *            父节点id
	 * @return 子节点集合,子节点都是DTO,
	 */
	private static ArrayList getDistrictSettingChild(int key) {
		ArrayList child = new ArrayList();

		for (int i = 0; i < treeList.size(); i++) {
			DistrictSettingDTO theDto = (DistrictSettingDTO) treeList.get(i);
			if (theDto.getBelongTo() == key) {
				child.add(treeList.get(i));
			}
		}
		return child;
	}

	/**
	 * 把一个DTO加工成为NODE
	 * 
	 * @param theDto,
	 * @param currentLevel
	 * @param isFirst
	 * @param isLast
	 * @param haveBrother
	 * @return
	 */
	private static DynamicTreeNode makeTreeNodeByDistrictSettingDTO(
			DistrictSettingDTO theDto, int currentLevel, boolean isFirst,
			boolean isLast, boolean haveBrother) {

		String districtKey = "";
		String beLongToKey = "";
		districtKey = "district" + "_" + theDto.getId();
		beLongToKey = "district" + "_" + theDto.getBelongTo();
		// 树节点构造
		DynamicTreeNode districtNode = new DynamicTreeNode();
		districtNode.setCurrentLevel(currentLevel);
		districtNode.setIsFirstChild(isFirst);
		districtNode.setHaveBrother(haveBrother);
		districtNode.setIsLastChild(isLast);
		districtNode.setParentNodeKey(beLongToKey);

		NodeMessage districtMessage = new NodeMessage();
		districtMessage.setKey(districtKey);
		districtMessage.setKeyName(theDto.getName());
		districtMessage.setCanAction(true);
		districtMessage.putKeyAndValue("txtQryBelongTo", theDto.getId() + "");
		districtMessage.setAtcionTarget(strAction);
		districtNode.setCurrentNode(districtMessage);

		return districtNode;

	}

	/**
	 * 判断有没有孩子
	 * 
	 * @param key
	 * @return
	 */
	private static boolean isDistrictSettingFatherNode(int key) {
		for (int i = 0; i < treeList.size(); i++) {
			DistrictSettingDTO theDto = (DistrictSettingDTO) treeList.get(i);
			if (theDto.getBelongTo() == key)
				return true;
		}
		return false;
	}

}
// ==============================================================================================
// 旧的类
// package com.dtv.oss.util;
//
// import java.sql.Connection;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.Iterator;
//
// import com.dtv.oss.dto.DistrictSettingDTO;
// import com.dtv.oss.log.LogLevel;
// import com.dtv.oss.log.LogUtility;
// import com.dtv.oss.tree.Constant;
// import com.dtv.oss.tree.DynamicTreeNode;
// import com.dtv.oss.tree.NodeMessage;
//
// public class TreePostern {
// public static ArrayList treeList = new ArrayList();
//
// // public static Connection con = null;
// // public static Statement stmt = null;
//
// public static void getDistrictSettingData(String belongTo,
// int currentLevel, DynamicTreeNode dynamicTreeNode) {
// Connection con = null;
// Statement stmt = null;
// ResultSet rs = null;
// String districtKey = "";
// int currentLevelCount = currentLevel + 1;
// int myCount = 0;
// try {
// con = DBUtil.getConnection();
// stmt = con.createStatement();
// // initialize t_organization
// rs = stmt
// .executeQuery("select * from T_DISTRICTSETTING where BELONGTO="
// + belongTo);
// while (rs.next()) {
// String id = String.valueOf(rs.getInt("id"));
// String districtCode = rs.getString("DISTRICTCODE");
// String name = rs.getString("name");
// String type = rs.getString("type");
// districtKey = "district" + "_" + id;
// // 树节点构造
// DynamicTreeNode districtNode = new DynamicTreeNode();
// districtNode.setCurrentLevel(currentLevelCount);
// if (myCount == 0) {
// districtNode.setIsFirstChild(true);
// } else {
// districtNode.setIsFirstChild(false);
// }
// districtNode.setHaveBrother(true);
// districtNode.setIsLastChild(false);
//
// NodeMessage districtMessage = new NodeMessage();
// districtMessage.setKey(districtKey);
// districtMessage.setKeyName(name);
// // 为了通用性，不再以类别来决定是否做子查询
// // if(type!=null && "S".equals(type)){
// // districtMessage.setCanAction(false);
// // }else{
// districtMessage.setCanAction(true);
// districtMessage.putKeyAndValue("txtQryBelongTo", id);
//
// // }
// districtMessage
// .setAtcionTarget("district_setting_query_result.do");
// districtNode.setCurrentNode(districtMessage);
// getDistrictSettingData(id, currentLevelCount, districtNode);
// dynamicTreeNode.setChildNode(districtNode.getCurrentNode()
// .getKey(), districtNode);
// myCount = myCount + 1;
// }
// if (districtKey != "" & districtKey != null) {
// dynamicTreeNode.getChildNode(districtKey).setHaveBrother(false);
// dynamicTreeNode.getChildNode(districtKey).setIsLastChild(true);
// }
// rs.close();
// } catch (Exception e) {
// if (Constant.DEBUGMODE) {
// System.out.println("TreePostern initialize exception:"
// + e.getMessage());
// e.printStackTrace();
// }
// } finally {
// if (stmt != null) {
// try {
// stmt.close();
// } catch (SQLException e) {
// }
// }
// if (con != null) {
// try {
// con.close();
// } catch (SQLException e) {
// }
// }
// }
// }
//
// // 从数组构造树
// public static void buildTree(String belongTo, int currentLevel,
// DynamicTreeNode dynamicTreeNode) {
// int lastId = 0;
// int curId = 0;
// int curBelongTo = 0;
// int lastBelongTo = 0;
// String districtKey = "";
// String lastKey = "";
//
// getTreeData();
// LogUtility.log(TreePostern.class, LogLevel.DEBUG, "treeList.size():"
// + treeList.size());
// Iterator it = treeList.iterator();
// while (it.hasNext()) {
// DistrictSettingDTO theDto = (DistrictSettingDTO) it.next();
// System.out.println("id= " + theDto.getId());
// LogUtility.log(TreePostern.class, LogLevel.DEBUG, "theDto.getId():"
// + theDto.getId());
// curId = theDto.getId();
// curBelongTo = theDto.getBelongTo();
// districtKey = "district" + "_" + curId;
// lastKey = "district" + "_" + lastId;
// buildTreeNode(theDto, currentLevel, dynamicTreeNode);
//
// if (curBelongTo != lastBelongTo) {
// dynamicTreeNode.getChildNode(lastKey).setHaveBrother(false);
// dynamicTreeNode.getChildNode(lastKey).setIsLastChild(true);
// if (curBelongTo == lastId) {
// dynamicTreeNode.getChildNode(districtKey).setIsFirstChild(
// true);
// }
// }
// lastId = curId;
// lastBelongTo = curBelongTo;
// }
// }
//
// // 把数据读取到数组中
// public static void getTreeData() {
// Connection con = null;
// Statement stmt = null;
// ResultSet rs = null;
//
// try {
// con = DBUtil.getConnection();
// stmt = con.createStatement();
// // initialize t_organization
// rs = stmt
// .executeQuery("select * from t_districtsetting start with belongto=0 connect
// by prior id = belongto");
// while (rs.next()) {
// DistrictSettingDTO theDto = new DistrictSettingDTO();
// theDto.setId(rs.getInt("id"));
// theDto.setName(rs.getString("name"));
// theDto.setBelongTo(rs.getInt("belongto"));
// theDto.setStatus(rs.getString("status"));
// treeList.add(theDto);
// }
// rs.close();
//
// } catch (Exception e) {
// if (Constant.DEBUGMODE) {
// System.out.println("TreePostern getTreeData exception:"
// + e.getMessage());
// e.printStackTrace();
// }
// } finally {
// if (stmt != null) {
// try {
// stmt.close();
// } catch (SQLException e) {
// }
// }
// if (con != null) {
// try {
// con.close();
// } catch (SQLException e) {
// }
// }
// }
// }
//
// // 构造树的一个节点
// public static void buildTreeNode(DistrictSettingDTO theDto,
// int currentLevel, DynamicTreeNode dynamicTreeNode) {
//
// String districtKey = "";
// String beLongToKey = "";
// districtKey = "district" + "_" + theDto.getId();
// beLongToKey = "district" + "_" + theDto.getBelongTo();
// // 树节点构造
// DynamicTreeNode districtNode = new DynamicTreeNode();
// districtNode.setCurrentLevel(currentLevel);
// districtNode.setIsFirstChild(false);
// districtNode.setHaveBrother(true);
// districtNode.setIsLastChild(false);
//
// NodeMessage districtMessage = new NodeMessage();
// districtMessage.setKey(districtKey);
// districtMessage.setKeyName(theDto.getName());
// districtMessage.setCanAction(true);
// districtMessage.putKeyAndValue("txtQryBelongTo", theDto.getId() + "");
// districtMessage.setAtcionTarget("district_setting_query_result.do");
// districtNode.setCurrentNode(districtMessage);
// System.out.println("belongtokey = " + beLongToKey);
// System.out.println("districtKey = " + districtKey);
// dynamicTreeNode.getChildNode(beLongToKey).setChildNode(districtKey,
// districtNode);
//
// }
//
// /*
// * public static void main(String args[]){ DynamicTreeNode districtNode =new
// * DynamicTreeNode(); districtNode.setCurrentLevel(1);
// * districtNode.setHaveBrother(false); districtNode.setIsFirstChild(false);
// * districtNode.setIsLastChild(true); NodeMessage districtMessage =new
// * NodeMessage(); districtMessage.setKey("district_0");
// * districtMessage.setKeyName("行政区"); districtMessage.setCanAction(true);
// * districtMessage.setAtcionTarget("district_setting_query_result.do");
// * districtMessage.putKeyAndValue("txtQryBelongTo",String.valueOf(0));
// * districtNode.setCurrentNode(districtMessage); try{
// * buildTree("0",1,districtNode); } catch(Exception e){
// * if(Constant.DEBUGMODE){ System.out.println("main exception in
// * TreePostern:" + e.getMessage()); e.printStackTrace(); } } finally{
// * System.out.println("heelo"); } }
// */
// /*
// * public static void main(String args[]){ DynamicTreeNode districtNode =new
// * DynamicTreeNode(); districtNode.setCurrentLevel(1);
// * districtNode.setHaveBrother(false); districtNode.setIsFirstChild(false);
// * districtNode.setIsLastChild(true); NodeMessage districtMessage =new
// * NodeMessage(); districtMessage.setKey("district_0");
// * districtMessage.setKeyName("行政区"); districtMessage.setCanAction(true);
// * districtMessage.setAtcionTarget("district_setting_query_result.do");
// * districtMessage.putKeyAndValue("txtQryBelongTo",String.valueOf(0));
//	 * districtNode.setCurrentNode(districtMessage);
//	 * 
//	 * TreePostern.getDistrictSettingData("0",1,districtNode);
//	 *  }
//	 */
//}
