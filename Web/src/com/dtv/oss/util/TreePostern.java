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
 * ���ڹ������νṹ����,
 * 
 * @author 260327h
 * 
 */
public class TreePostern {

	// ����,������������
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

	// �������ݿ�,�õ���Ҫ������,
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
	 * ��ʼ������ӿڸ��ڵ�
	 * 
	 */
	private static Collection initInterfaceSettingRootNode(int level,ArrayList list) {

		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "��ʼ������ӿڽڵ�====");
		 
		int count = list.size();
		Collection colall=new ArrayList();
		//��ֻ��һ���ڵ�����
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
		  caMessage.setKeyName("CA�ӿ�");
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
		  vodMessage.setKeyName("VOD�ӿ�");
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
		  ldapMessage.setKeyName("LDAP�ӿ�");
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
		  voiceMessage.setKeyName("�����ӿ�");
		  voiceMessage.setCanAction(true);
		  voiceMessage.setAtcionTarget(strVoiceAction);
		  voiceNode.setCurrentNode(voiceMessage);
		  list.add(voiceNode);
		 
		  NodeMessage districtMessage2 = new NodeMessage();
		  districtMessage.setKey("LDAP_" + 2);
		  districtMessage.setKeyName("VOD�ӿ�");
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
	 * ��ʼ���������ڵ�
	 * 
	 */
	private static DynamicTreeNode initDistrictSettingRootNode(int key,
			int level) {

		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "��ʼ���������ڵ�====");
		DynamicTreeNode districtNode = new DynamicTreeNode();
		districtNode.setCurrentLevel(level);
		districtNode.setHaveBrother(false);
		districtNode.setIsFirstChild(false);
		districtNode.setIsLastChild(true);
		NodeMessage districtMessage = new NodeMessage();
		districtMessage.setKey("district_" + key);
		districtMessage.setKeyName("������");
		districtMessage.setCanAction(true);
		districtMessage.setAtcionTarget(strAction);
		districtMessage.putKeyAndValue("txtQryBelongTo", String.valueOf(0));
		districtNode.setCurrentNode(districtMessage);

		// districtNode.setOnAndOff("ON");

		return districtNode;
	}

	/**
	 * ��ʼ����֯���ڵ�
	 * 
	 */
	private static DynamicTreeNode initOrganizationRootNode(int key, int level) {

		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "��ʼ����֯���ڵ�====");

		DynamicTreeNode organizationNode = new DynamicTreeNode();
		organizationNode.setCurrentLevel(1);
		organizationNode.setHaveBrother(false);
		organizationNode.setIsFirstChild(false);
		organizationNode.setIsLastChild(true);
		NodeMessage organizationMessage = new NodeMessage();
		organizationMessage.setKey("org_0");
		organizationMessage.setKeyName("��֯����");
		organizationMessage.setCanAction(true);
		organizationMessage.setAtcionTarget(strOrgQueryAction);
		organizationMessage.putKeyAndValue("txtQryBelongTo", key+"");
		organizationNode.setCurrentNode(organizationMessage);

		return organizationNode;
	}


	// �ݹ�ķ���,����������,
	private static LinkedHashMap buildOrganizationChild(String key, int level) {

		LogUtility.log(TreePostern.class, LogLevel.DEBUG,
				"����buildOrganizationChild,��ǰKEY:" + key);
		LogUtility.log(TreePostern.class, LogLevel.DEBUG,
				"����buildOrganizationChild,��ǰlevel:" + level);
		LinkedHashMap nodeChild = new LinkedHashMap();
		// OrganizationDTO
		boolean isFirstChild = false;
		boolean isLastChild = false;
		boolean haveBrother = false;

		ArrayList Childs = new ArrayList();

		int intKey = 0;
		/**
		 * makeTreeNodeByOrganizationDTO()��ʹ��,
		 * ��Ϊ���μ����˶���Ķ���,��ԭ���ݿ��в�ͬ,��ʾ�ϵ�parent�б仯
		 */
		String parentKey = "";
		//��ǰDTO,����ȡtype
		OrganizationDTO curDto = null;
		//��ǰ�ڵ������,����ȡ����
		String curType = "";
		/**
		 * childs���ϻ�������ȡֵ���, 1,������key��ID,����IDΪ�ܹ�˾��ֹ�˾����,�ýڵ���Ӧ�ùҹ����������ɵĽڵ�,
		 * 2,������key��ID,��ͨ���, curTypeΪnull,����ȡֵ.
		 * 3,������һ���ַ���,����Ϊ�ǽ����������ݽڵ�,�����ִ��ֽ�,ȡ��parentID,type,name,��parentID��typeȡ����,
		 * ����ı���,����ýڵ�TYPE��SUBTYPE�������¼��ڵ�
		 */
		try {
			intKey = Integer.parseInt(key);
			curDto = getOrganizationDto(intKey);
			parentKey = "org" + "_" + key;
//			System.out.println("cruDto========" + curDto);
			if (curDto != null && curDto.getOrgType() != null) {
				//ȡ�¼�����
				String subOrgType=(String) orgConfig.get(curDto.getOrgType());
				//System.out.println("subOrgType=======" + subOrgType);
				//�¼����Ͳ���
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

		// �����Ƕ���һ,����Ϊ����brother��
		if (Childs.size() > 1)
			haveBrother = true;
		for (int i = 0; i < Childs.size(); i++) {
			// ��һ������
			if (i == 0)
				isFirstChild = true;
			else
				isFirstChild = false;
			// ���һ������
			if ((i + 1) == Childs.size()) {
				haveBrother = false;
				isLastChild = true;
			} else
				isLastChild = false;

			DynamicTreeNode node = null;
			String curKey = null;
			Object obj = Childs.get(i);
			/**
			 * �����ǰ�����°�װ����DTO,���DTO��װ��NODE������� �����һ��String,
			 */
			if (obj instanceof OrganizationDTO) {
				OrganizationDTO theDto = (OrganizationDTO) obj;
				// ��DTO����ӹ���һ��NODE����
				node = makeTreeNodeByOrganizationDTO(theDto, parentKey,
						level + 1, isFirstChild, isLastChild, haveBrother);
				int orgID = theDto.getOrgID();
				curKey = orgID + "";

				if (isOrganizationFatherNode(orgID)) {
					// LogUtility.log(TreePostern2.class, LogLevel.DEBUG,
					// "��������һ�ε���,��ǰKEY:" + curKey + "��ǰlevel:" + level);
					node.setChildNodeMap(buildOrganizationChild(curKey,
							level + 1));
				}
			}
			// �����ǰ������TYPE����,
			else if (obj instanceof String) {
				node = makeTreeNodeByOrgType(obj.toString(), level + 1,
						isFirstChild, isLastChild, haveBrother);
				// type�ڵ㶼������ڵ�,�����ж�
				node.setChildNodeMap(buildOrganizationChild(obj.toString(),
						level + 1));
			}
//			LogUtility
//					.log(TreePostern.class, LogLevel.DEBUG, "�����NODE:" + node);
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
//		//��ǰ�ڵ��Ǹ�����ڵ�
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
		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "�ӹ������ݵõ���map:"
				+ orgTypes);
		
		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "����ɸѡ�ֹ�˾��subOrgType:"+ subOrgType);

		Iterator it = orgTypes.keySet().iterator();

		ArrayList res = new ArrayList();
		while (it.hasNext()) {
			String key = it.next().toString();
			String name = orgTypes.get(key).toString();
			if(subOrgType.indexOf(key)>=0)
				res.add(id + "=" + key + "=" + name);
		}
		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "�õ��ķ���ڵ�:" + res);
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
		// ���ڵ㹹��
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
		// ���ڵ㹹��
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
	 * �õ���֯�ڵ㺢�ӵķ��� 
	 * ���ݸ��ڵ��type��ͬ,ȡ��һ���ĺ���
	 * 
	 * @param key
	 * @return
	 */
	private static ArrayList getOrganizationChild(int key, String type) {
		ArrayList childs = new ArrayList();
//		//��ȡ���ӽڵ�type
//		String subType=orgConfig.get(type).toString();

		for (int i = 0; i < treeList.size(); i++) {
			OrganizationDTO theDto = (OrganizationDTO) treeList.get(i);
			if (type != null && !type.equals("")) {
				// ���ӵĸ��ױ���������ȷ��,���������ڸ����ͱ���,
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
		LogUtility.log(TreePostern.class, LogLevel.DEBUG, "getDto:,��ǰid:" + id);
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
	 * ���·�����������������
	 */
	/**
	 * ����һ�����ڵ��µ��ӽڵ㼯��,�÷�����ݹ����
	 * 
	 * @param key,���ڵ��ID
	 * @param level,���ڵ㵱ǰ����
	 * @return
	 */
	private static LinkedHashMap buildDistrictSettingChild(int key, int level) {
		LinkedHashMap nodeChild = new LinkedHashMap();

		boolean isFirstChild = false;
		boolean isLastChild = false;
		boolean haveBrother = false;
		ArrayList dtoChild = getDistrictSettingChild(key);
		// �����Ƕ���һ,����Ϊ����brother��
		if (dtoChild.size() > 1)
			haveBrother = true;
		for (int i = 0; i < dtoChild.size(); i++) {
			// ��һ������
			if (i == 0)
				isFirstChild = true;
			else
				isFirstChild = false;
			// ���һ������
			if ((i + 1) == dtoChild.size()) {
				haveBrother = false;
				isLastChild = true;
			} else
				isLastChild = false;

			DistrictSettingDTO theDto = (DistrictSettingDTO) dtoChild.get(i);
			// ��DTO����ӹ���һ��NODE����
			DynamicTreeNode node = makeTreeNodeByDistrictSettingDTO(theDto,
					level + 1, isFirstChild, isLastChild, haveBrother);
			int curKey = theDto.getId();

			if (isDistrictSettingFatherNode(curKey)) {
				// LogUtility.log(TreePostern2.class, LogLevel.DEBUG,
				// "��������һ�ε���,��ǰKEY:" + curKey + "��ǰlevel:" + level);
				node.setChildNodeMap(buildDistrictSettingChild(curKey,
						level + 1));
			}
			nodeChild.put(node.getCurrentNode().getKey(), node);
		}

		return nodeChild;
	}

	/**
	 * �����õ�һ���ڵ��������ӽڵ�,
	 * 
	 * @param key
	 *            ���ڵ�id
	 * @return �ӽڵ㼯��,�ӽڵ㶼��DTO,
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
	 * ��һ��DTO�ӹ���ΪNODE
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
		// ���ڵ㹹��
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
	 * �ж���û�к���
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
// �ɵ���
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
// // ���ڵ㹹��
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
// // Ϊ��ͨ���ԣ�����������������Ƿ����Ӳ�ѯ
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
// // �����鹹����
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
// // �����ݶ�ȡ��������
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
// // ��������һ���ڵ�
// public static void buildTreeNode(DistrictSettingDTO theDto,
// int currentLevel, DynamicTreeNode dynamicTreeNode) {
//
// String districtKey = "";
// String beLongToKey = "";
// districtKey = "district" + "_" + theDto.getId();
// beLongToKey = "district" + "_" + theDto.getBelongTo();
// // ���ڵ㹹��
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
// * districtMessage.setKeyName("������"); districtMessage.setCanAction(true);
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
// * districtMessage.setKeyName("������"); districtMessage.setCanAction(true);
// * districtMessage.setAtcionTarget("district_setting_query_result.do");
// * districtMessage.putKeyAndValue("txtQryBelongTo",String.valueOf(0));
//	 * districtNode.setCurrentNode(districtMessage);
//	 * 
//	 * TreePostern.getDistrictSettingData("0",1,districtNode);
//	 *  }
//	 */
//}
