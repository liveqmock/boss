package com.dtv.oss.service.listhandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.domain.Organization;
import com.dtv.oss.domain.OrganizationHome;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.ExtraDAO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.dao.GenericDAO4SCNA;
import com.dtv.oss.service.dao.TotalSizeDAO;
import com.dtv.oss.service.dao.TotalSizeDAO4SCNA;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CsrBusinessUtility;
import com.dtv.oss.service.util.HomeLocater;

public abstract class ValueListHandler implements ValueListIterator {
	public static final int OPERATOR_BELONG_TO_GENERAL_COMPANY = 1;

	public static final int OPERATOR_BELONG_TO_SUBCOMPANY = 2;

	public static final int OPERATOR_BELONG_TO_STREET_STATION = 3;

	public static final String ssqlTable_1 = " (select ID, NAME from t_districtsetting  where BELONGTO = 1 ) dist ";

	public static final String ssqlWhere = "and  addr.DISTRICTID  in ( select ID  from t_districtsetting  CONNECT BY PRIOR  id = BelongTo start with id = dist.id   ) ";

	public static final String ssqlWhere_z = " and  csi.CreateOrgID in (select ORGID from  T_ORGANIZATION CONNECT BY PRIOR  ORGID = PARENTORGID start with ORGID=org.ORGID ) ";

	private StringBuffer recordCountQueryBuffer = new StringBuffer("");

	private StringBuffer recordDataQueryBuffer = new StringBuffer("");

	private static final String recordCountPrefixString = "select count(*) from ";

	protected List list;
	
	//���ڶԲ�ѯ���з���ͼ�ͳ���õ�
	private String sumByGroupQuerySQL=null;
	
	//���ڴ�ŷ���ͳ�ƽ����HashMap
	protected HashMap sumByGroupHashMap = null;


	// ��չ����,add by jason.zhou
	protected Object extraObj = null;

	// ��չ��ѯ���� , add by jason.zhou
	private String extraQuerySQL = null;

	protected ListIterator listIterator;

	// ������ΪȨ�޿��ƣ����Ʋ���Ա�������ݵķ�Χ
	protected int operatorID;

	private int orgID = -1000;

	// �������Ƽ����������Ĵ�С
	private int from;

	private int to;

	private int totalRecordSize;
	
	//������֯����������ͳ��(���ò�ѯ����)��
	public static final String orgShow = ",org.orgid id,org.orgname name";
	public static final String orgTable = ",t_organization org";
	public static final String orgWhere = " and org.orgid in (select orgt.orgid from t_organization orgt " +
			"CONNECT BY PRIOR orgt.PARENTORGID=orgt.ORGID start with orgt.orgid in" +
			"(select orgDsict.orgid from T_OrgGovernedDistrict orgDsict,t_address adds " +
			"where adds.addressid=cust.addressid and orgDsict.Districtid=adds.districtid )) " +
			"and org.parentorgid=";
	public static final String custInfoOrgWhere = " and org.orgid in (select orgt.orgid from t_organization orgt " +
	"CONNECT BY PRIOR orgt.PARENTORGID=orgt.ORGID start with orgt.orgid in" +
	"(select orgDsict.orgid from T_OrgGovernedDistrict orgDsict,t_address adds " +
	"where adds.addressid=cust.FromAddressID and orgDsict.Districtid=adds.districtid )) " +
	"and org.parentorgid=";
	public static final String orgGroup = ",org.orgid,org.orgname";
	//��ǰorg�ڵ�
	public static final String curOrgWhere =" and org.orgid=";
	
	public static final String distShow = ",dist.id id,dist.name name";
	public static final String distTable = ",t_address addr,(select ID, NAME from t_districtsetting  where BELONGTO=";
	public static final String distWhere = " and addr.DISTRICTID in " +
	"(select ID from t_districtsetting CONNECT BY PRIOR id = BelongTo start with id = dist.id) and cust.ADDRESSID = addr.ADDRESSID";
	public static final String custInfoDistWhere = " and cust.FromAddressID = addr.ADDRESSID and addr.DISTRICTID in " +
			"(select ID from t_districtsetting CONNECT BY PRIOR id = BelongTo start with id = dist.id)";
	public static final String distGroup = ",dist.id,dist.name";
	
	//��ǰ����Ľڵ�
	public static final String curDistTable=",t_districtsetting dist,t_address addr ";
	public static final String curDistWhere = " and cust.AddressID=addr.Addressid and dist.id=addr.DistrictID and dist.id= ";
	
	//�����Ż���sql:
	public static final String distShowNew = ",dist.id id,dist.name name";
	public static final String distWhereNew = " and cust.ADDRESSID = addr.ADDRESSID and addr.DISTRICTID = dist.sonid";
	public static final String distGroupNew = ",dist.id,dist.name order by dist.id desc";
	
	//�ӿͻ���������
	public static final String orgShowNew = ",org.id id,org.name name";
	public static final String orgWhereNew = " and cust.ADDRESSID=addr.ADDRESSID and addr.DISTRICTID=orgDsict.Districtid" +
											 " and orgDsict.orgid=org.sonid ";
	public static final String orgGroupNew = ",org.id,org.name order by org.id desc";
	//�������������
	public static final String orgShowNewByCsi = ",org.id id,org.name name";
	public static final String orgWhereNewByCsi = " and csi.CreateOrgID=org.sonid";
	public static final String orgGroupNewByCsi = ",org.id,org.name order by org.id desc";
	
	//�����Ż���sql:end
	
	
	
	
	

	public ValueListHandler() {
	}

	// sqlƴ�ӵõ�distTable��ֵ
	public String distTable(String dto) {
		String ssqlTable_f = " (select ID, NAME from t_districtsetting  where BELONGTO = ";
		String ssqlTable_b = " ) dist ";
		String ssqlTable = ssqlTable_f + dto + ssqlTable_b;
		return ssqlTable;
	}

	public String orgTable(String dto) {
		String ssqlTable_f = " T_CustServiceInteraction csi, (select ORGID,ORGNAME from T_ORGANIZATION   where PARENTORGID = ";
		String ssqlTable_b = " ) org ";
		String ssqlTable = ssqlTable_f + dto + ssqlTable_b;
		return ssqlTable;
	}

	// valueListHandler�ع�����׷��------��ʼ
	// ��������ͳ�Ƽ�¼���Ķ�Ӧ�ı���
	protected void setRecordCountQueryTable(String tableName) {
		this.recordCountQueryBuffer.append(recordCountPrefixString).append(
				tableName);
	}

	// ����������ı���֮�����ò�ѯ��������setRecordCountQueryTable�����ڸ÷���֮ǰʹ�ã�
	protected void setRecordCountSuffixBuffer(
			StringBuffer recordCountQueryBuffer) {
		this.recordCountQueryBuffer.append(recordCountQueryBuffer);
	}

	// ����ֱ�������ѯ��TABLE�Ͳ�ѯ�����������������setRecordCountQueryTable��setRecordCountSuffixBuffer��ϵ����ã�
	protected void setRecordCountSuffixCondBuffer(
			StringBuffer recordCountQueryBuffer) {
		this.recordCountQueryBuffer.append(recordCountPrefixString).append("(")
				.append(recordCountQueryBuffer).append(")");
	}

	private String getRecordCountQueryString() {
		return this.recordCountQueryBuffer.toString();
	}

	protected void setRecordDataQueryBuffer(StringBuffer recordDataQueryBuffer) {
		this.recordDataQueryBuffer.append(recordDataQueryBuffer);
	}

	private String getRecordDataQueryBuffer() {
		return this.recordDataQueryBuffer.toString();
	}

	public Object getExtraObj() {
		return extraObj;
	}

	public void setExtraObj(Object extraObj) {
		this.extraObj = extraObj;
	}

	public String getExtraQuerySQL() {
		return extraQuerySQL;
	}

	public void setExtraQuerySQL(String extraQuerySQL) {
		this.extraQuerySQL = extraQuerySQL;
	}

	//��2007-12-19����,��ǰ̨�������ǲ��ǽ��з�ҳ,���ڵ�������,
	//���ھ����ǲ������ز�ѯ.
	private boolean isDown=false;

	/**
	 * @param dao
	 *            ����ʵ������
	 * @param isWrap
	 *            �Ƿ���ӷ�װ������鿴��װ����
	 * @param isCount
	 *            �Ƿ���Ҫȡ�õ�ǰ��ѯ�����ļ�¼��
	 * @throws ListHandlerException
	 */
	protected void executeSearch(GenericDAO dao, boolean isWrap, boolean isCount)
			throws ListHandlerException {
		try {
			String selectCriteria = recordDataQueryBuffer.toString();
			String selectCountCriteria = getRecordCountQueryString();
			if (selectCriteria == null || "".equals(selectCriteria)) {
				LogUtility.log(ValueListHandler.class, LogLevel.WARN,
						"in executeSearch method query criteria required...");
				throw new ListHandlerException(
						"data query criteria required...");
			}
			if (!isDown) {
				if (isWrap) {
					selectCriteria = wrapSQL(selectCriteria);
				}
				if (isCount) {
					if (selectCountCriteria == null
							|| "".equals(selectCountCriteria)) {
						LogUtility
								.log(ValueListHandler.class, LogLevel.WARN,
										"in executeSearch method data count criteria required...");
						throw new ListHandlerException(
								"data count query criteria required...");
					}
					// ������ѯ��ǰ�����µ��ܵļ�¼��Ŀ
					LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
							"in executeSearch method data count query start");
					TotalSizeDAO tsDao = new TotalSizeDAO();
					setTotalRecordSize(tsDao.executeSelect(selectCountCriteria));
					LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
							"in executeSearch method data count query end");
				}
				// ������ѯ��ǰ�����µļ�¼��ϸ
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in executeSearch method data query start");
				List resultsList = dao.executeSelect(selectCriteria);
				setList(resultsList);
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in executeSearch method data query end");
			} else {
				// ���������,��sql����dao���󷵻ظ�webaction,�����ش����ֽ��в�ѯ.���ڴ˴ν���.
				List list = new ArrayList();
				list.add(recordDataQueryBuffer.toString());
				setList(list);
			}
		} catch (Exception e) {
			LogUtility.log(ValueListHandler.class, LogLevel.WARN,
					"executeSearch", e);
			throw new ListHandlerException(e.getMessage());
		}
	}

	/**
	 * Ϊ֧����չ��ѯ�����õķ���, add by jason.zhou
	 * 
	 * @param dao
	 * @param isWrap
	 * @param isCount
	 * @throws ListHandlerException
	 */
	protected void executeSearch(GenericDAO dao, boolean isWrap,
			boolean isCount, boolean isExtra) throws ListHandlerException {

		executeSearch(dao, isWrap, isCount);

		if (isExtra) {
			try {
				if (getExtraQuerySQL() == null || "".equals(getExtraQuerySQL())) {
					LogUtility
							.log(ValueListHandler.class, LogLevel.WARN,
									"in extra executeSearch method query criteria required...");
					throw new ListHandlerException(
							"data query criteria required...");
				}
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in extra executeSearch method data count query start");
				ExtraDAO extraDAO = new ExtraDAO();
				setExtraObj(extraDAO.executeSelect(getExtraQuerySQL()));
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in extra executeSearch :" + getExtraQuerySQL());
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in extra executeSearch method data count query end");
			} catch (Exception e) {
				LogUtility.log(ValueListHandler.class, LogLevel.WARN,
						"executeSearch", e);
				throw new ListHandlerException(e.getMessage());
			}
		}
	}

	/**
	 * Ϊ֧����չ��ѯ�����õķ���, add by jason.zhou
	 * 
	 * @param dao
	 * @param isWrap
	 * @param isCount
	 * @throws ListHandlerException
	 */
	protected void executeSearch(GenericDAO dao, boolean isWrap,
			boolean isCount, GenericDAO extraDAO) throws ListHandlerException {

		executeSearch(dao, isWrap, isCount);

		if (extraDAO != null) {
			try {
				if (getExtraQuerySQL() == null || "".equals(getExtraQuerySQL())) {
					LogUtility
							.log(ValueListHandler.class, LogLevel.WARN,
									"in extra executeSearch method query criteria required...");
					throw new ListHandlerException(
							"data query criteria required...");
				}
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in extra executeSearch method data count query start");
				setExtraObj(extraDAO.executeSelect(getExtraQuerySQL()));
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in extra executeSearch :" + getExtraQuerySQL());
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in extra executeSearch method data count query end");
			} catch (Exception e) {
				LogUtility.log(ValueListHandler.class, LogLevel.WARN,
						"executeSearch", e);
				throw new ListHandlerException(e.getMessage());
			}
		}
	}

	/**
	 * ��Ҫ��ѯA�������ݿ��е�����ʱ
	 * 
	 * @param dao
	 *            ����ʵ������
	 * @param isWrap
	 *            �Ƿ���ӷ�װ������鿴��װ����
	 * @param isCount
	 *            �Ƿ���Ҫȡ�õ�ǰ��ѯ�����ļ�¼��
	 * @throws ListHandlerException
	 */
	protected void executeSearch(GenericDAO4SCNA dao, boolean isWrap,
			boolean isCount) throws ListHandlerException {
		try {
			String selectCriteria = recordDataQueryBuffer.toString();
			String selectCountCriteria = getRecordCountQueryString();
			if (selectCriteria == null || "".equals(selectCriteria)) {
				LogUtility.log(ValueListHandler.class, LogLevel.WARN,
						"in executeSearch method query criteria required...");
				throw new ListHandlerException(
						"data query criteria required...");
			}
			if (isWrap) {
				selectCriteria = wrapSQL(selectCriteria);
			}
			if (isCount) {
				if (selectCountCriteria == null
						|| "".equals(selectCountCriteria)) {
					LogUtility
							.log(ValueListHandler.class, LogLevel.WARN,
									"in executeSearch method data count criteria required...");
					throw new ListHandlerException(
							"data count query criteria required...");
				}
				// ������ѯ��ǰ�����µ��ܵļ�¼��Ŀ
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in executeSearch method data count query start");
				TotalSizeDAO4SCNA tsDao = new TotalSizeDAO4SCNA();
				setTotalRecordSize(tsDao.executeSelect(selectCountCriteria));
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in executeSearch method data count query end");
			}
			// ������ѯ��ǰ�����µļ�¼��ϸ
			LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
					"in executeSearch method data query start");
			List resultsList = dao.executeSelect(selectCriteria);
			setList(resultsList);
			LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
					"in executeSearch method data query end");
		} catch (Exception e) {
			LogUtility.log(ValueListHandler.class, LogLevel.WARN,
					"executeSearch", e);
			throw new ListHandlerException(e.getMessage());
		}
	}

	// valueListHandler�ع�����׷��------����
	protected void setList(List list) throws IteratorException {
		this.list = list;
		if (list != null)
			listIterator = list.listIterator();
		else
			throw new IteratorException("List empty");
	}

	public List getList() {
		return list;
	}

	public int getSize() throws IteratorException {
		int size = 0;

		if (list != null)
			size = list.size();
		else
			throw new IteratorException("No Data."); // No Data

		return size;
	}

	public Object getCurrentElement() throws IteratorException {

		Object obj = null;
		// Will not advance iterator
		if (list != null) {
			int currIndex = listIterator.nextIndex();
			obj = list.get(currIndex);
		} else
			throw new IteratorException("List is empty");
		return obj;

	}

	public List getPreviousElements(int count) throws IteratorException {
		int i = 0;
		Object object = null;
		LinkedList list = new LinkedList();
		if (listIterator != null) {
			while (listIterator.hasPrevious() && (i < count)) {
				object = listIterator.previous();
				list.add(object);
				i++;
			}
		}// end if
		else
			throw new IteratorException("No data"); // No data

		return list;
	}

	public List getNextElements(int count) throws IteratorException {
		int i = 0;
		Object object = null;
		LinkedList list = new LinkedList();
		if (listIterator != null) {
			while (listIterator.hasNext() && (i < count)) {
				object = listIterator.next();
				list.add(object);
				i++;
			}
		} // end if
		else
			throw new IteratorException("No data"); // No data

		return list;
	}

	public void resetIndex() throws IteratorException {
		if (listIterator != null) {
			listIterator = list.listIterator();
		} else
			throw new IteratorException("No data"); // No data
	}

	public abstract void setCriteria(Object dto) throws ListHandlerException;

	protected void makeSQLByStringField(String dbField, String dtoField,
			StringBuffer selectStatement) {
		makeSQLByField(dbField, dtoField, selectStatement, false);
	}

	protected void makeSQLByStringField(String dbField, String dtoField,
			StringBuffer selectStatement, String operator) {
		if (dtoField != null && !dtoField.trim().equals("")) {
			selectStatement.append(" and " + dbField + " " + operator + " '%"
					+ dtoField.trim() + "%'");
		}

	}

	protected void makeSQLByStringFieldSuffix(String dbField, String dtoField,
			StringBuffer selectStatement, String operator) {
		if (dtoField != null && !dtoField.trim().equals("")) {
			selectStatement.append(" and " + dbField + " " + operator + " '"
					+ dtoField.trim() + "%'");
		}

	}

	protected void makeSQLByStringFieldIn(String dbField, String dtoField,
			StringBuffer selectStatement) {
		if (dtoField != null && !dtoField.trim().equals("")) {
			selectStatement.append(" and " + dbField + " in ('"
					+ dtoField.replaceAll(";", "','") + "')");
		}

	}

	protected void makeSQLByIntFieldIn(String dbField, String dtoField,
			StringBuffer selectStatement) {

		if (dtoField != null && !dtoField.trim().equals("")) {
			selectStatement.append(" and " + dbField + " in ("
					+ dtoField.replaceAll(";", ",") + ")");
		}

	}

	protected void makeSQLByIntField(String dbField, int dtoField,
			StringBuffer selectStatement) {
		if (dtoField == 0)
			return;

		selectStatement.append(" and " + dbField + "=" + dtoField);

	}

	private void makeSQLByField(String dbField, String dtoField,
			StringBuffer selectStatement, boolean isNumeric) {
		if (dtoField != null && !dtoField.trim().equals("")) {
			if ((isNumeric) && (new Integer(dtoField).intValue() == 0))
				return;

			selectStatement.append(" and " + dbField + "='" + dtoField.trim() + "'");
		}

	}

	public int getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(int operator) {
		this.operatorID = operator;
	}

	protected boolean isParentCompany() throws ListHandlerException {
		Operator operator = getOperator();
		try {
			OrganizationHome orgnizationHome = (OrganizationHome) HomeLocater
					.getOrganizationHome();
			Organization organization = orgnizationHome
					.findByPrimaryKey(new Integer(operator.getOrgID()));
			int parentOrgID = organization.getParentOrgID();
			orgID = organization.getOrgID().intValue();
			if (parentOrgID == 0) {
				return true; // �ܹ�˾
			}
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			throw new ListHandlerException(e.getMessage());
		} catch (ObjectNotFoundException oe) {
			oe.printStackTrace();
			throw new ListHandlerException(oe.getMessage());
		} catch (FinderException fe) {
			fe.printStackTrace();
			throw new ListHandlerException(fe.getMessage());
		}
		return false;
	}

	protected String getOrgIDs() throws ListHandlerException {
		if (orgID == -1000) {
			if (isParentCompany()) {
				return null;
			}
		}
		return CsrBusinessUtility.getSubOrgID(orgID);
	}

	protected Operator getOperator() throws ListHandlerException {
		Operator operator = null;
		try {

			OperatorHome operatorhome = (OperatorHome) HomeLocater
					.getOperatorHome();
			operator = operatorhome.findByPrimaryKey(new Integer(
					getOperatorID()));
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			throw new ListHandlerException(e.getMessage());
		} catch (ObjectNotFoundException oe) {
			oe.printStackTrace();
			throw new ListHandlerException(oe.getMessage());
		} catch (FinderException fe) {
			fe.printStackTrace();
			throw new ListHandlerException(fe.getMessage());
		}

		return operator;
	}

	protected void appendString(String str, StringBuffer selectStatement) {
		if ((str == null) || str.trim().equals(""))
			return;

		selectStatement.append(" and " + str);

	}

	/**
	 * ȡ�õ�ǰ����Ա������֯
	 * 
	 * @return
	 * @throws ListHandlerException
	 */
	protected int getCurrentOperatorSubjectOrg() throws ListHandlerException {
		return getOperator().getOrgID();
	}

	/**
	 * �����֯���������ѯ,ȡ��ǰ����Ա������֯
	 * 
	 * @param fieldName
	 * @param selectStatement
	 * @throws ListHandlerException
	 */
	protected void appendStringWithOrgGovernedDistrict(String fieldName,
			StringBuffer selectStatement) throws ListHandlerException {
		appendStringWithOrgGovernedDistrict(fieldName, 0, selectStatement);
	}

	/**
	 * �����֯���������ѯ���
	 * 
	 * @param fieldName
	 *            ��ǰ��ѯ������ID
	 * @param orgID
	 *            Ҫ��ѯ����֯ID
	 * @param selectStatement
	 * @throws ListHandlerException
	 */
	protected void appendStringWithOrgGovernedDistrict(String fieldName,
			int orgID, StringBuffer selectStatement)
			throws ListHandlerException {
		// ���ORGIDΪ0,�ӵ�ǰ��֯�õ�һ���пͻ�����Ȩ���ϼ���֯
		if (orgID == 0)
			orgID = BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg());
		if (orgID == 0) {
			LogUtility.log(ValueListHandler.class, LogLevel.ERROR, "��ǰ����Ա������֯����");
			throw new ListHandlerException("�����쳣");
		}
		appendString(" Exists (select id from t_districtsetting Where Id= "+fieldName+" connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="
						+ orgID + "))", selectStatement);
	}
	/**
	 * �����֯������������֯ƥ������.
	 * @param fieldName
	 * @param orgID
	 * @param selectStatement
	 * @throws ListHandlerException
	 */
	protected void appendStringWithOrgAndSubOrg(String fieldName,
			int orgID, StringBuffer selectStatement)
			throws ListHandlerException {
		// ���ORGIDΪ0,�ӵ�ǰ��֯�õ�һ���пͻ�����Ȩ���ϼ���֯
		if (orgID == 0)
			orgID = BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg());
		if (orgID == 0) {
			LogUtility.log(ValueListHandler.class, LogLevel.ERROR, "��ǰ����Ա������֯����");
			throw new ListHandlerException("�����쳣");
		}
		appendString(" exists (select org.orgid from t_organization org where org.orgid= "+fieldName+" connect by prior org.orgid = org.parentorgid start with org.orgid = "
						+ orgID + ")", selectStatement);
	}
	/**
	 * ��ӿͻ���������Ĳ�ѯ����
	 * @param fieldName ��ǰ��ѯ�������ֶ�����
	 * @param districtID  ����ID
	 * @param selectStatement
	 */
	protected void appendStringWithDistrictID(String fieldName,
			int districtID, StringBuffer selectStatement){
		appendString( "Exists (select id from t_districtsetting Where Id= "+fieldName+" connect by prior id=belongto start with id ="+ districtID + ")", selectStatement);
	}
	protected void appendStringWithOrgID(String fieldName,
			int orgID, StringBuffer selectStatement){
		appendString(
				fieldName
						+ " in (select orgid from t_organization connect by prior orgid=parentorgid start with orgid ="+ orgID + ")", selectStatement);
	}
	
	/**
	 * ��Ӵ�ͻ��������ӿͻ�ID
	 * @param fieldName
	 * @param customerID
	 * @param selectStatement
	 */
	protected void appendStringWithGroupCustomerID(String fieldName,String customerID, StringBuffer selectStatement){
		appendString(
				fieldName
						+ " in (select customerid from t_customer connect by prior customerid = groupcustid start with customerid = "+ customerID + ")", selectStatement);
	}
	/**
	 * ��ӿͻ������ѯ
	 * @param fieldName
	 * @param customerID
	 * @param selectStatement
	 */
	protected void appendStringWithGroupCustomerStyle(String fieldName,String customerStyle, StringBuffer selectStatement){
		String sql = "";
		if (CommonConstDefinition.CUSTOMERSTYLE_GROUP.equals(customerStyle)) {
			sql = " in (select customerid from t_customer where CUSTOMERSTYLE='"
					+ CommonConstDefinition.CUSTOMERSTYLE_GROUP + "')";
		} else {
			sql = " not in (select customerid from t_customer where CUSTOMERSTYLE='"
				+ CommonConstDefinition.CUSTOMERSTYLE_GROUP + "')";
		}
		appendString(
				fieldName
						+ sql, selectStatement);
	}	
	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getTotalRecordSize() {
		return totalRecordSize;
	}

	public void setTotalRecordSize(int recordSize) {
		this.totalRecordSize = recordSize;
	}

	protected void getTotalSize(String strSql) throws ListHandlerException {
		try {
			String totalSizeSql = "select count(*) from (" + strSql + ")";
			TotalSizeDAO tsDao = new TotalSizeDAO();
			setTotalRecordSize(tsDao.executeSelect(totalSizeSql));
		} catch (SQLException e) {
			throw new ListHandlerException(e.getMessage());

		}
	}

	protected void getTotalSizeWithSQL() throws ListHandlerException {
		try {
			TotalSizeDAO tsDao = new TotalSizeDAO();
			setTotalRecordSize(tsDao.executeSelect(getRecordCountQueryString()));
		} catch (SQLException e) {
			throw new ListHandlerException(e.getMessage());

		}
	}

	protected void getTotalSizeWithSQL(String strSql)
			throws ListHandlerException {
		try {
			TotalSizeDAO tsDao = new TotalSizeDAO();
			setTotalRecordSize(tsDao.executeSelect(strSql));
		} catch (SQLException e) {
			throw new ListHandlerException(e.getMessage());

		}
	}

	protected void getTotalSizeWithSQL4SCNA(String strSql)
			throws ListHandlerException {
		try {
			TotalSizeDAO4SCNA tsDao = new TotalSizeDAO4SCNA();
			setTotalRecordSize(tsDao.executeSelect(strSql));
		} catch (SQLException e) {
			throw new ListHandlerException(e.getMessage());

		}
	}

	protected String wrapSQL(String strSql) {
		if (getFrom() < 0)
			setFrom(0);
		if (getTo() - getFrom() > 500)
			setTo(getFrom() + 500);
		if (getTo() - getFrom() <= 0)
			setTo(getFrom() + 20);
		String strWrapSql = "";
		strWrapSql = "select * from (select rownum mynum, xxx.* from ("
				+ strSql + ") xxx ) where mynum between " + getFrom() + " and "
				+ getTo();
		return strWrapSql;
	}

	/**
	 * New function to provide requested list, according to from index and range
	 * Old one only provide list based on current index, which is accused by web
	 * engineer
	 * 
	 * @param from
	 * @param range
	 * @return List
	 */
	public List getResultList(int from, int to) throws IteratorException {
		int i = 1;
		Object object = null;
		java.util.LinkedList resultlist = new java.util.LinkedList();
		java.util.ListIterator listIterator;
		listIterator = this.list.listIterator();

		while (to >= from && i <= to && i <= this.list.size()) {
			object = listIterator.next();
			if (i >= from) {
				resultlist.add(object);
			}
			i++;
		}
		return resultlist;
	}

	protected String getSelectExpression4Catvterminal(String prefix) {
		String strSelect = "catv#id catv_id, catv#catvid catv_catvid, catv#CONBATCHID catv_CONBATCHID, catv#STATUS catv_status,"
				+ "catv#RECORDSOURCE catv_RECORDSOURCE, catv#STATUSREASON catv_STATUSREASON,catv#COUNTY catv_COUNTY,catv#DETAILEDADDRESS catv_DETAILEDADDRESS,"
				+ "catv#POSTCODE catv_POSTCODE,catv#ORGID catv_ORGID,catv#STREETSTATION catv_STREETSTATION,catv#ZONESTATION catv_ZONESTATION,catv#USERID catv_USERID,"
				+ "catv#PORTNUMBER catv_PORTNUMBER,catv#FIBERNODE catv_FIBERNODE,catv#CABLETYPE catv_CABLETYPE,catv#BIDIRECTIONFLAG catv_BIDIRECTIONFLAG,"
				+ "catv#CREATETIME catv_CREATETIME,catv#ACTIVETIME catv_ACTIVETIME,catv#PAUSETIME catv_PAUSETIME,catv#DT_CREATE catv_DT_CREATE,catv#DT_LASTMOD catv_DT_LASTMOD";
		return strSelect.replaceAll("catv#", prefix);
	}

	protected String getSelectExpression4Account(String prefix) {
		String strSelect = "acct#accountid acct_accountid,acct#BALANCE acct_balance";
		return strSelect.replaceAll("acct#", prefix);
	}

	protected String getSelectExpression4Customer(String prefix) {
		String strSelect = "cust#customerid cust_customerid, cust#SURNAME cust_SURNAME, cust#gender cust_gender,cust#TELEPHONEO cust_TELEPHONEO,cust#TELEPHONEH cust_TELEPHONEH, cust#TELEPHONEMOBILE cust_TELEPHONEMOBILE";
		return strSelect.replaceAll("cust#", prefix);
	}

	protected String getSelectExpression4CustserviceInteraction(String prefix) {
		String strSelect = "csi#ID csi_ID,csi#REFERSHEETID csi_REFERSHEETID,csi#TYPE csi_TYPE,csi#STATUSREASON csi_STATUSREASON,csi#SERVICETYPE csi_SERVICETYPE,"
				+ "csi#DESCRIPTION csi_DESCRIPTION,csi#CREATETIME csi_CREATETIME,csi#CREATEOPERATORID csi_CREATEOPERATORID,csi#STATUS csi_STATUS,"
				+ "csi#IMMEDIATEFEE csi_IMMEDIATEFEE,csi#IMMEDIATEFEEPAYED csi_IMMEDIATEFEEPAYED,csi#PREPAYRENTFEE csi_PREPAYRENTFEE,csi#CUSTOMERID csi_CUSTOMERID,"
				+ "csi#ACCOUNTID csi_ACCOUNTID,csi#CATVID csi_CATVID,csi#NEWCUSTOMERINFOID csi_NEWCUSTOMERINFOID,csi#NEWPORTADDED csi_NEWPORTADDED,"
				+ "csi#TRANSCUSTINFOID csi_TRANSCUSTINFOID,csi#COMMENTS csi_COMMENTS,csi#DT_CREATE csi_DT_CREATE,csi#DT_LASTMOD csi_DT_LASTMOD";
		return strSelect.replaceAll("csi#", prefix);
	}

	protected String getSelectExpression4NewCustomerInfo(String prefix) {
		String strSelect = "nci#ID nci_ID,CUSTOMERSTYLE,CUSTOMERTYPE,CUSTOMERGENDER,CUSTOMERTITLE,CUSTOMERSURNAME,CUSTOMERGIVENNAME,CUSTOMERBIRTHDAY,CUSTOMERNATIONALITY,"
				+ "CUSTOMERCARDTYPE,CUSTOMERCARDID,CUSTOMEROCCUPATION,CUSTOMERTELEPHONEH,CUSTOMERTELEPHONEO,CUSTOMERTELEPHONEM,CUSTOMERFAX,CUSTOMEREMAIL,ACCOUNTMOPID,ACCOUNTBANKACCOUNT,"
				+ "BILLINGCOUNTY,BILLINGADDRESS,BILLINGPOSTCODE,CATVRECORDSOURCE,CATVCOUNTY,CATVADDRESS,CATVPOSTCODE,CATVORGID,CATVSTREETSTATION,CATVZONESTATION,CATVPORTNUMBER,CATVFIBERNODE,CATVCABLETYPE,CATVBIDIRECTIONFLAG,"
				+ "nci#STATUS nci_STATUS,nci#DT_CREATE nci_DT_CREATE,nci#DT_LASTMOD nci_DT_LASTMOD";
		return strSelect.replaceAll("nci#", prefix);
	}

	protected String getSelectExpression4TransCustInfo(String prefix) {
		String strSelect = "tci#SEQNO tci_seqno, tci#TRANSSHEETID, tci#TRANSCUSTID, tci#TRANSACCOUNTID, tci#TRANSCATVID, tci#TRANSPORTNUM, tci#FROMCOUNTYID, tci#FROMORGID,"
				+ "tci#FROMSTREETSTATIONID,tci#FROMZONESTATIONID,tci#TOORGID,tci#TOCOUNTYID,tci#CREATETIME,tci#STATUS tci_STATUS,tci#DT_CREATE tci_DT_CREATE,tci#DT_LASTMOD tci_DT_LASTMOD";
		return strSelect.replaceAll("tci#", prefix);
	}

	protected int Integer2Int(Integer param) {
		return (param == null) ? 0 : param.intValue();
	}
	//�õ����ݵ���ͳ�Ƶĵ�����������
	protected String getDistTableNew(String id) {
		String strSelect = ",t_customer cust,t_address addr,(select distinct * from (select distinct sondist.id sonid,sondist.name sonname" +
				",parentdist.id id,parentdist.name name from" +
				"(select ID, NAME from t_districtsetting where belongto = "+id+") parentdist," +
				"t_districtsetting sondist " +
				"CONNECT BY PRIOR sondist.id = sondist.BelongTo start with sondist.id = parentdist.id) " +
				"where id in(select id from t_districtsetting CONNECT BY PRIOR BelongTo = id start with id =sonid) "+
				//ֱ����,����Ҫʱ���þ�ע�ͼ���
				"union all select id sonid,name sonname,id,name||'(*)' name from t_districtsetting where id="+id+
				")dist";
		
		return strSelect;
	}
	
	//�õ�������֯ͳ�Ƶ���֯��������� �ӿͻ���������
	protected String getOrgTableNew(String id) {
		String strSelect = ",t_customer cust,t_address addr,"+
		  "(select distinct * from"+ 
		  "(select distinct sonorg.orgid sonid,sonorg.orgname sonname,parentorg.orgid id,parentorg.orgname name"+
		  " from (select orgid,orgname from t_organization where parentorgid="+id+
		  ") parentorg,t_organization sonorg"+
		  " CONNECT BY PRIOR sonorg.orgid = sonorg.Parentorgid start with sonorg.orgid = parentorg.orgid)"+ 
		  " where id in(select orgid from t_organization CONNECT BY PRIOR Parentorgid = orgid start with orgid =sonid)"+
		  //ֱ����,����Ҫʱ���þ�ע�ͼ���
          " union all select orgid sonid,orgname sonname,orgid id,orgname||'(*)' name from t_organization where orgid="+id+
          ")org,T_OrgGovernedDistrict orgDsict";
		
		return strSelect;
	}
	//�õ�������֯ͳ�Ƶ���֯��������� �������������
	protected String getOrgTableNewByCsi(String id) {
		String strSelect = ",(select distinct * from"+ 
		  "(select distinct sonorg.orgid sonid,sonorg.orgname sonname,parentorg.orgid id,parentorg.orgname name"+
		  " from (select orgid,orgname from t_organization where parentorgid="+id+
		  ") parentorg,t_organization sonorg"+
		  " CONNECT BY PRIOR sonorg.orgid = sonorg.Parentorgid start with sonorg.orgid = parentorg.orgid)"+ 
		  " where id in(select orgid from t_organization CONNECT BY PRIOR Parentorgid = orgid start with orgid =sonid)"+
		  //ֱ����,����Ҫʱ���þ�ע�ͼ���
          " union all select orgid sonid,orgname sonname,orgid id,orgname||'(*)' name from t_organization where orgid="+id+
          ")org";
		
		return strSelect;
	}
	
	/**
	 * ͳ���뱨��ģ�����ڷ�װ����ʽ����ʾ�����0������
	 * @param buff��û�������sql���
	 * @param statType D������O����֯
	 * @param id ҳ��ѡ����������֯id
	 * @return
	 */
	protected StringBuffer wrapDistrictOrOrgOrderAndShowAllForStat(StringBuffer buff,String statType,String id){
		StringBuffer ret=new StringBuffer();
		if("D".equalsIgnoreCase(statType))
			ret.append("select statdata.subname,nvl(statdata.amount,0) amount,setting.id,setting.name from (select * from t_districtsetting where belongto ="+id+")setting,(");
		else if("O".equalsIgnoreCase(statType))
			ret.append("select statdata.subname,nvl(statdata.amount,0) amount,setting.id,setting.name from ( select * from t_organization where parentorgid ="+id+")setting,(");
		
		ret.append(buff);
		if("D".equalsIgnoreCase(statType))
			ret.append(") statdata where statdata.id (+)= setting.id order by setting.districtcode desc,setting.id desc");
		else if("O".equalsIgnoreCase(statType))
			ret.append(") statdata where statdata.id (+)= setting.orgid order by setting.orgcode desc,setting.orgid desc");
		return ret;
	}
	

	
	/**
	 * ͳ���뱨��ģ�����ڷ�װ����ʽ
	 * @param buff��û�������sql���
	 * @param statType D������O����֯
	 * @return
	 */
	protected StringBuffer wrapDistrictOrOrgOrderForStat(StringBuffer buff,String statType){
		StringBuffer ret=new StringBuffer();
		if("D".equalsIgnoreCase(statType))
			ret.append("select statdata.* from t_districtsetting setting,(");
		else if("O".equalsIgnoreCase(statType))
			ret.append("select statdata.* from t_organization setting,(");
		
		ret.append(buff);
		if("D".equalsIgnoreCase(statType))
			ret.append(") statdata where statdata.id=setting.id order by setting.districtcode desc,setting.id desc");
		else if("O".equalsIgnoreCase(statType))
			ret.append(") statdata where statdata.id=setting.orgid order by setting.orgcode desc,setting.orgid desc");
		return ret;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}
	
	public void setSumByGroupQuerySQL(String sumByGroupQuerySQL) {
		this.sumByGroupQuerySQL = sumByGroupQuerySQL;
	}
	
	public void setSumByGroupHashMap(HashMap sumByGroupHashMap) {
		this.sumByGroupHashMap = sumByGroupHashMap;
	}
	
	public String getSumByGroupQuerySQL() {
		return sumByGroupQuerySQL;
	}
	
	/**
	 * @return the sumByGroupHashMap
	 */
	public HashMap getSumByGroupHashMap() {
		return sumByGroupHashMap;
	}
	
	//Ϊ��߲�ѯ�ٶȶ���ƣ����sql��伴�ɵõ���sqlִ�е����� begin author by david.Yang
	protected void executeSearch(GenericDAO dao) throws ListHandlerException {
		try {
			String selectCriteria = recordDataQueryBuffer.toString();
			if (selectCriteria == null || "".equals(selectCriteria)) {
				LogUtility.log(ValueListHandler.class, LogLevel.WARN,
						"in executeSearch method query criteria required...");
				throw new ListHandlerException(
						"data query criteria required...");
			    }
			
				// ������ѯ��ǰ�����µļ�¼��ϸ
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in executeSearch method data query start");
				
				List rowCountList =new ArrayList();
				List resultsList = dao.executeSelect(selectCriteria);
				setList(resultsList);
				LogUtility.log(ValueListHandler.class, LogLevel.DEBUG,
						"in executeSearch method data query end");
		}catch (Exception e) {
			LogUtility.log(ValueListHandler.class, LogLevel.WARN,
					"executeSearch", e);
			throw new ListHandlerException(e.getMessage());
		}
	}

}