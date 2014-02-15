/*
 * Created on 2004-8-9
 *
 * @author Mac Wang
 */
package com.dtv.oss.service.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import com.dtv.oss.dto.custom.BatchNoDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.FinderException;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.domain.FapiaoVolumn;
import com.dtv.oss.domain.FapiaoVolumnHome;
import com.dtv.oss.domain.TerminalDevice;
import com.dtv.oss.domain.TerminalDeviceHome;
import com.dtv.oss.dto.*;
import com.dtv.oss.dto.wrap.customer.Account2AddressWrap;
import com.dtv.oss.dto.wrap.customer.CustomerInfoWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.component.ImmediatePayFeeService;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.util.TimestampUtility;

public class BusinessUtility {
	private static final Class clazz = BusinessUtility.class;

	/**
	 * ȡ�üƷ�����ID�����������ͺ�ϵͳʱ��
	 * 
	 * @param customerID
	 * @return
	 */
	public static int getBillingCycleIDByTypeID() {
		return getIntBySQL("select ID from T_BILLINGCYCLE where CTYPE='B' and (trunc(sysdate) between OTHERCYCLEBTIME and OTHERCYCLEETIME )");
	}

	public static int getProductIDByModel(String model) {
		return  getIntBySQL("select productid from t_devicematchtoproduct where devicemodel='"
				+ model + "'");
	}

	public static int getLoginIDCount(String login) {
		return  getIntBySQL("select  count(*) as COUNT from t_operator where loginid='"
				+ login + "'");
	}

	public static int getCAProductDefCount(int hostID, int opiID,
			String caproductID) {
		return  getIntBySQL("select  count(*) as COUNT from t_caproductdef where hostid="
				           +   hostID
				           + " and opiid="
				           +   opiID
				           + " and caproductid"
				           + "'"+ caproductID + "'");
	}

	public static int getCASMSProductCount(int productID, int conditionID) {
		return  getIntBySQL("select  count(*) as COUNT from t_caproduct where productid="
				           + productID + " and conditionid=" + conditionID);

	}

	/**
	 * ȡ��LdapProdToSmsProdDTO
	 * 
	 * @param productID
	 * @return
	 */
	public static LdapProdToSmsProdDTO getProductDtoBySmsProductID(int smsProdId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LdapProdToSmsProdDTO dto = null;
		String strSql = "select * from t_ldapprodtosmsprod t where t.smsproductid="
				      + smsProdId;

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillfillLdapProdToSmsProdDTO(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductDtoBySmsProductID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	/**
	 * ȡ��VODInterfaceProductDTO
	 * 
	 * @param productID
	 * @return
	 */
	public static VODInterfaceProductDTO getDtoBySmsProductID(int productID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		VODInterfaceProductDTO dto = null;
		String strSql = "select * from t_vodproduct t where t.smsproductid="
				      + productID;

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillVODInterfaceProductDTO(rs);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDtoBySmsProductID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	/**
	 * ȡ��LdapProductDTO
	 * 
	 * @param productName
	 * @return
	 */
	public static LdapProductDTO getDtoByProductName(String productName) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LdapProductDTO dto = null;
		String strSql = "select * from t_ldapproduct t where t.productName='"
				+ productName + "'";

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillLdapProductDTO(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDtoByProductName", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	/**
	 * ȡ��LdapAttrConfigDTO
	 * 
	 * @param productName
	 * @return
	 */
	public static LdapAttrConfigDTO getDtoByAttrName(String attrName) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LdapAttrConfigDTO dto = null;
		String strSql = "select * from t_ldapattrconfig t where t.attrname='"
				      + attrName + "'";

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillLdapAttrConfigDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDtoByAttrName", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	/**
	 * ȡ��LdapAttrConfigDTO
	 * 
	 * @param productName
	 * @return
	 */
	public static LdapConditionDTO getDtoByCondName(String condName) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LdapConditionDTO dto = null;
		String strSql = "select * from T_LDAPCONDITION t where t.condname='"
				+ condName + "'";

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillLdapConditionDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDtoByCondName", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static InvoiceDTO getInvoiceDTOByBarcode(String barCode)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InvoiceDTO dto = null;
		String strSql = "select * from  t_invoice t where t.barCode='"
				+ barCode + "'";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				dto = DtoFiller.fillInvoiceDTO(rs);
				count = count + 1;
			}
			rs.getStatement().close();
			if (count > 1) {
				throw new ServiceException("���ݲ���ȷ�������ݿ��д���" + count + "�����ʵ������룡");
			} else if (count == 0) {
				throw new ServiceException("�����ڸ������룡");
			}

		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getInvoiceDTOByBarcode", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static InvoiceDTO getInvoiceDTOByInvoiceNo(int invoiceNo)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InvoiceDTO dto = null;
		String strSql = "select * from  t_invoice t where t.invoiceNo="
				+ invoiceNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillInvoiceDTO(rs);
			}
			if (dto == null)
				throw new ServiceException("�����ڸ��ʵ���");

		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getInvoiceDTOByBarcode", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	/**
	 * �Ƿ����״̬Ϊ����ʼ,ϵͳ��ͣ����ҵ���˻�
	 * 
	 * @param customerID
	 * @param serviceAccountID
	 * @return
	 */
	public static boolean isNormalServiceAccount(int customerID,
			int serviceAccountID) {
		boolean isNormal = false;
		String strSql = "select COUNT(*) COUNT from T_SERVICEACCOUNT WHERE (STATUS='I' OR STATUS='S') AND CUSTOMERID="
				+ customerID;
		if (serviceAccountID != 0) {
			strSql += "AND SERVICEACCOUNTID=" + serviceAccountID;

		}
		int count = getIntBySQL(strSql);
		if (count == 0) {
		    isNormal = true;
		}
		return isNormal;
	}

	/**
	 * �Ƿ����״̬Ϊ����ʼ,ϵͳ��ͣ���Ŀͻ���Ʒ
	 * 
	 * @param customerID
	 * @param serviceAccountID
	 * @return
	 */
	public static boolean isNormalCustomerProduct(int customerID,
			int serviceAccountID) {
		boolean isNormal = false;
		String strSql = "select COUNT(*) COUNT from T_CUSTOMERPRODUCT WHERE (STATUS='I' OR STATUS='S') AND CUSTOMERID="
				+ customerID;
		if (serviceAccountID != 0) {
			strSql += "AND SERVICEACCOUNTID=" + serviceAccountID;

		}
		int count = getIntBySQL(strSql);
		if (count == 0) {
			isNormal = true;
		}
		return isNormal;
	}

	/**
	 * �Ƿ����״̬Ϊ��һ��Ƿ��,����Ƿ�ѣ����˻�
	 * 
	 * @param customerID
	 * @param accountID
	 * @return
	 */
	public static boolean isNormalAccount(int customerID, int accountID) {
		boolean isNormal = false;
		String strSql = "select COUNT(*) COUNT from T_ACCOUNT WHERE (STATUS<>'N' AND STATUS<>'C') AND CUSTOMERID="
				+ customerID;
		if (accountID != 0) {
			strSql += "AND ACCOUNTID=" + accountID;
		}
		int count = getIntBySQL(strSql);
		if (count == 0) {
			isNormal = true;
		}
		return isNormal;
	}

	/**
	 * �Ƿ����һ�����ϵķ�ȡ�����ʻ�
	 * 
	 * @param customerID
	 * @param accountID
	 * @return
	 */
	public static boolean isExitOneMoreAccount(int customerID) {
		boolean isExitOneMore = false;
		String strSql = "select COUNT(*) COUNT from T_ACCOUNT WHERE  STATUS<>'C' AND CUSTOMERID="
				+ customerID;
		int count =getIntBySQL(strSql);
		if (count >= 2) {
			isExitOneMore = true;
		}
		return isExitOneMore;
	}

	/**
	 * �Ƿ���ڣ��½�,���ڴ���,��������������Ϣ
	 * 
	 * @param customerID
	 * @return
	 */
	public static boolean isNormalCSI(int customerID) {
		boolean isNormal = false;
		String strSql = "select COUNT(*) COUNT from T_CUSTSERVICEINTERACTION WHERE (STATUS='N' OR STATUS='P' OR STATUS='W') AND TYPE != 'BK' AND CUSTOMERID="
				+ customerID;

		int count = getIntBySQL(strSql);
		if (count == 0) {
			isNormal = true;
		}
		return isNormal;
	}

	/**
	 * �Ƿ����û�н�����Ͷ�ߵ�
	 * 
	 * @param customerID
	 * @return
	 */
	public static boolean isNormalCustomerComplain(int customerID) {
		boolean isNormal = false;
		String strSql = "select COUNT(*) COUNT from T_CustomerComplain WHERE STATUS='D' AND CUSTOMERID="
				+ customerID;
		int count = getIntBySQL(strSql);
		if (count == 0) {
			isNormal = true;
		}
		return isNormal;
	}

	/**
	 * �Ƿ���ڣ�������,���ڴ���,ά�޲��ɹ������޵�
	 * 
	 * @param customerID
	 * @param serviceAccountID
	 * @return
	 */
	public static boolean isNormalCustomerProblem(int customerID,
			int serviceAccountID) {
		boolean isNormal = false;
		String strSql = "select COUNT(*) COUNT from T_CUSTOMERPROBLEM WHERE (STATUS<>'T' AND STATUS<>'D' AND STATUS<>'CN') AND CUSTID="
				+ customerID;
		if (serviceAccountID != 0) {
			strSql += "AND CUSTSERVICEACCOUNTID=" + serviceAccountID;

		}
		int count = getIntBySQL(strSql);
		if (count == 0) {
			isNormal = true;
		}
		return isNormal;
	}

	/**
	 * ���ݴ���Ĳ�ѯ�����ϼƲ������˵�������δ�����úϼƻ��������˺�δ���˷��úϼ�
	 * 
	 * @param condition
	 * @param partType(�����ˣ�D��δ���ˣ�W�����������ֻ����ͳ�Ʋ������˵ģ������˻���δ����
	 *            ��ʱ�������Ҫ����ָ����ֵ������ʱ��ֱ������null
	 * @return
	 */
	public static double getSumFeeValueByCond(String condition, String partType) {
		String strSql = "";
		if (CommonConstDefinition.SETOFFFLAG_W.equals(partType)) {
			strSql = "select sum (VALUE-SETOFFAMOUNT) VALUE from T_ACCOUNTITEM WHERE STATUS<>'I' AND SETOFFFLAG='"
					+ CommonConstDefinition.SETOFFFLAG_P + "' ";
		} else if (CommonConstDefinition.SETOFFFLAG_D.equals(partType)) {
			strSql = "select sum (SETOFFAMOUNT) VALUE from T_ACCOUNTITEM WHERE STATUS<>'I'  AND SETOFFFLAG='"
					+ CommonConstDefinition.SETOFFFLAG_P + "' ";
		} else {
			strSql = "select sum (VALUE) VALUE from T_ACCOUNTITEM WHERE STATUS<>'I' ";
		}

		if (condition != null && !"".equals(condition)) {
			strSql = strSql + " and " + condition;
		}
		double totalValue = getdoubleBySQL(strSql);
		return totalValue;
	}

	/**
	 * ȡ�÷����б�(�����Ƿ����˷����ı������ͷ��ý��)
	 * 
	 * @param referID
	 * @param referType
	 * @param isReturnFee
	 * @return
	 */
	public static Collection getFeeListByTypeAndID(int referID,
			String referType, boolean isReturnFee) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiFeeList = new ArrayList();

		String strSql = "select * from T_ACCOUNTITEM WHERE STATUS<>'I' AND REFERTYPE= '"
				+ referType + "' AND REFERID=" + referID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO accountItemDTO = DtoFiller
						.fillAccountItemDTO(rs);
				if (isReturnFee) {
					accountItemDTO.setAiNO(0);
					accountItemDTO.setValue(-accountItemDTO.getValue());
				}
				csiFeeList.add(accountItemDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getFeeListByTypeAndID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiFeeList;
	}

	public static Collection getFeeListByReferInfo(String referType, int referId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiFeeList = new ArrayList();
		String strSql = "select * from T_ACCOUNTITEM WHERE STATUS<>'I' AND  REFERTYPE='"
				+ referType + "' AND REFERID=" + referId;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO accountItemDTO = DtoFiller
						.fillAccountItemDTO(rs);
				csiFeeList.add(accountItemDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getNosetoffFeeListByReferInfo", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiFeeList;
	}

	/**
	 * �����ʻ�idȡ�÷���
	 * 
	 * @param invoiceNo
	 * @return
	 */
	public static Collection getFeeListByAccountID(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiFeeList = new ArrayList();
		String strSql = "select * from T_ACCOUNTITEM WHERE STATUS<>'I' AND SETOFFFLAG<>'D' AND INVOICEDFLAG= 'N' AND ACCTID="
				+ accountID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO accountItemDTO = DtoFiller
						.fillAccountItemDTO(rs);
				csiFeeList.add(accountItemDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getFeeListByAccountID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiFeeList;
	}

	/**
	 * �����ʵ�ȡ�÷���
	 * 
	 * @param invoiceNo
	 * @return
	 */
	public static Collection getFeeListByInvoice(int invoiceNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiFeeList = new ArrayList();
		String strSql = "select * from T_ACCOUNTITEM WHERE STATUS<>'I' AND SETOFFFLAG<>'D' AND INVOICEDFLAG= 'Y' AND INVOICENO="
				+ invoiceNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO accountItemDTO = DtoFiller
						.fillAccountItemDTO(rs);
				csiFeeList.add(accountItemDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getFeeListByInvoice", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiFeeList;
	}

	/**
	 * �����ʵ�ȡ������֧��
	 * 
	 * @param invoiceNo
	 * @return
	 */
	public static Collection getAllFeeListByInvoice(int invoiceNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiFeeList = new ArrayList();
		String strSql = "select * from T_ACCOUNTITEM WHERE STATUS<>'I' AND INVOICEDFLAG= 'Y' AND INVOICENO="
				+ invoiceNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO accountItemDTO = DtoFiller
						.fillAccountItemDTO(rs);
				csiFeeList.add(accountItemDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getFeeListByInvoice", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiFeeList;
	}

	/**
	 * �����ʵ�ȡ������Ԥ��ֿۼ�¼
	 * 
	 * @param invoiceNo
	 * @return
	 */
	public static Collection getAllPrePaymentListByInvoice(int invoiceNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiPrePaymentList = new ArrayList();
		String strSql = "select * from T_PrePaymentDeductionRecord WHERE STATUS<>'I' AND INVOICEDFLAG= 'Y' AND INVOICENO="
				+ invoiceNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PrepaymentDeductionRecordDTO prepaymentDTO = DtoFiller
						.fillPrepaymentDeductionRecordDTO(rs);
				csiPrePaymentList.add(prepaymentDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAllPrePaymentListByInvoice", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiPrePaymentList;
	}

	/**
	 * �����ʵ�ȡ�����з���
	 * 
	 * @param invoiceNo
	 * @return
	 */
	public static Collection getAllPaymentListByInvoice(int invoiceNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiPaymentList = new ArrayList();
		String strSql = "select * from T_PaymentRecord WHERE STATUS<>'I' AND INVOICEDFLAG= 'Y' AND INVOICENO="
				+ invoiceNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PaymentRecordDTO paymentRecordDTO = DtoFiller
						.fillPaymentRecordDTO(rs);
				csiPaymentList.add(paymentRecordDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAllPaymentListByInvoice", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiPaymentList;
	}

	/**
	 * ȡ��Ԥ��ֿ��б�
	 * 
	 * @param referID
	 * @param referType
	 * @return
	 */
	public static Collection getPreDeductionRecordListByTypeAndID(int referID,
			String referType, boolean isReturnFee) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection deductionRecordList = new ArrayList();
		String strSql = "select * from T_PREPAYMENTDEDUCTIONRECORD WHERE ReferSheetType= '"
				+ referType + "' AND ReferSheetID=" + referID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PrepaymentDeductionRecordDTO prepaymentDeductionRecordDTO = DtoFiller
						.fillPrepaymentDeductionRecordDTO(rs);
				if (isReturnFee) {
					prepaymentDeductionRecordDTO.setSeqNo(0);
					prepaymentDeductionRecordDTO
							.setAmount(-prepaymentDeductionRecordDTO
									.getAmount());
					prepaymentDeductionRecordDTO.setCreateTime(TimestampUtility
							.getCurrentTimestamp());
				}
				deductionRecordList.add(prepaymentDeductionRecordDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getPreDeductionRecordListByTypeAndID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return deductionRecordList;
	}

	/**
	 * ȡ��Ԥ��ֿ��ܽ��
	 * 
	 * @param feeCond
	 * @return
	 */
	public static double getSumDeductionRecordValueByFeeCond(String feeCond) {
		String strSql = "select sum( AMOUNT) AMOUNT from T_PREPAYMENTDEDUCTIONRECORD";
		if (feeCond != null && !"".equals(feeCond)) {
			strSql = strSql + " WHERE " + feeCond;
		}
		
		double totalValue = getdoubleBySQL(strSql);
		return totalValue;
	}

	/**
	 * ȡ��֧���ܶ���ܽ��
	 * 
	 * @param referID
	 * @param referType
	 * @param payType
	 * @return
	 */
	public static double getSumPaymentRecordValueByTypeAndID(int referID,
			String referType, String payType, String cashFlag) {
		String strSql = "select sum( AMOUNT) AMOUNT from T_PAYMENTRECORD WHERE REFERTYPE= '"
				+ referType + "' AND REFERID=" + referID;
		if (payType != null && !"".equals(payType)) {
			strSql += "  and PAYTYPE in(" + payType + ")";
		}
		if (cashFlag != null && !"".equals(cashFlag)) {
			strSql += "  and MOPID in(select MOPID  from T_METHODOFPAYMENT where PAYMENTFLAG='Y' AND CASHFLAG='"
					+ cashFlag + "')";
		}
		double totalValue = getdoubleBySQL(strSql);
		return totalValue;
	}

	/**
	 * ȡ��֧���б�
	 * 
	 * @param referID
	 * @param referType
	 * @param isReturnFee
	 * @return
	 */
	public static Collection getPaymentListByTypeAndID(int referID,
			String referType, boolean isReturnFee) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiPaymentList = new ArrayList();
		String strSql = "select *  from T_PAYMENTRECORD where REFERTYPE = '"
				+ referType + "' and  REFERID=" + referID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PaymentRecordDTO paymentRecordDTO = DtoFiller
						.fillPaymentRecordDTO(rs);
				if (isReturnFee) {
					paymentRecordDTO.setSeqNo(0);
					paymentRecordDTO.setAmount(-paymentRecordDTO.getAmount());
				}
				csiPaymentList.add(paymentRecordDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getPaymentListByTypeAndID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiPaymentList;
	}

	/**
	 * ���ݸ��ѷ�ʽid�ж��Ƿ����ֽ�֧��
	 * 
	 * @param mopID
	 * @return
	 */
	public static boolean isCash(int mopID) {
		boolean isCash = false;
		String strSql = "select CASHFLAG from T_METHODOFPAYMENT WHERE MOPID="
				+ mopID;
		String cashFlag =getStringBySQL(strSql);
		if ("Y".equals(cashFlag)) {
			isCash = true;
		}
		return isCash;
	}

	/**
	 * ���ݿͻ���Ʒ��idȡ�ö�Ӧ�Ŀͻ�����
	 * 
	 * @param productID
	 * @return
	 */
	public static CustomerCampaignDTO getCustomerCampaignByPsID(int psID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CustomerCampaignDTO customerCampaignDTO = null;
		String strSql = "select * from T_CustomerCampaign where (sysdate between DateFrom and  DateTo) And CCID in(select distinct(CCID) from T_CPCampaignMap where CustProductID="
				+ psID + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				customerCampaignDTO = DtoFiller.fillCustomerCampaignDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustomerCampaignByPsID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return customerCampaignDTO;
	}

	// �ж��ʻ����Ƿ����δ���ʵ������������ڻ�Ƿ��׷�ֻ��ʣ�
	public static boolean existUnpaidInvoice(int accountid) {
		Connection conn = DBUtil.getConnection();
		boolean bFlag = false;
		try {
			String strSql = "select invoiceno from t_invoice where status in ('"
					+ CommonConstDefinition.INVOICE_STATUS_OWE
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_WAIT
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_BAD
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_QFZT
					+ "') and acctid = " + accountid;

			bFlag = DBUtil.hasValidRecordExist(conn, strSql);

		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"existUnpaidInvoice", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return bFlag;
	}

	public static boolean existNopaidInvoice(int accountid) {
		boolean bFlag = false;
		String strSql = "select count(*) from t_invoice where status in ('"
					+ CommonConstDefinition.INVOICE_STATUS_OWE
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_WAIT
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_BAD
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_QFZT
					+ "') and acctid = " + accountid;

		System.out.println("strSql===========" + strSql);

		int invoiceAmount =getIntBySQL(strSql);
		if (invoiceAmount > 1) {
			bFlag = true;
		}
		return bFlag;
	}

	/**
	 * ȡ�ø��˻������еĴ��������ڻ�Ƿ��׷�ֻ��ʵ�����
	 * 
	 * @param accountid
	 * @return
	 */
	public static int getUnpaidInvoiceCount(int accountid) {
		String strSql = "select count(*) count from t_invoice where status in ('"
					+ CommonConstDefinition.INVOICE_STATUS_OWE
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_WAIT
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_BAD
					+ "','"
					+ CommonConstDefinition.INVOICE_STATUS_QFZT
					+ "') and acctid = " + accountid;
		int invoiceCount =getIntBySQL(strSql);
		return invoiceCount;
	}

	/**
	 * ���ݿͻ���ID���˻�idȡ��������Ч���˻���Ϣ
	 * 
	 * @param customerID
	 * @param accountID
	 * @return
	 */
	public static Collection getAcctDTOListByCustIDAndAcctID(int customerID,
			int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList accountDTOList = new ArrayList();
		String strSql = "select * from T_ACCOUNT where STATUS<>'C'";
		if (customerID != 0) {
			strSql = strSql + " AND CUSTOMERID=" + customerID;
		}
		if (accountID != 0) {
			strSql = strSql + "  AND ACCOUNTID=" + accountID;
		}
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				accountDTOList.add(DtoFiller.fillAccountDTO(rs));
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAcctDTOListByCustIDAndAcctID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return accountDTOList;
	}

	/**
	 * �˻�idȡ��������Ч���˻���Ϣ
	 * 
	 * @param accountID
	 * @return
	 */
	public static AccountDTO getAcctDTOByAcctID(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AccountDTO accountDTO = null;
		String strSql = "select * from T_ACCOUNT where ACCOUNTID=" + accountID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				accountDTO = DtoFiller.fillAccountDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAcctDTOByAcctID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return accountDTO;
	}

	/**
	 * �����ʻ���IDȡ�ö�Ӧ�Ĳ�Ʒid�б�
	 * 
	 * @param accountID
	 * @param status
	 * @return
	 */
	public static Collection getPsIDListByAccountIDAndStatus(int accountID,
			String status) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList psIDList = new ArrayList();
		String strSql = "select PSID from T_CUSTOMERPRODUCT where   ACCOUNTID="
				+ accountID;
		if (status != null && !"".equals(status)) {
			strSql = strSql + "  AND STATUS='" + status + "'";
		} else {
			strSql = strSql + "  AND STATUS<>'C'";
		}
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				psIDList.add(new Integer(rs.getInt("PSID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getPsIDListByAccountIDAndStatus", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return psIDList;
	}

	/**
	 * ����ҵ���˻�id�����ʻ�id�Ͳ�Ʒ��״̬���psid�б�
	 * 
	 * @param serviceAccount
	 * @param accountID
	 * @param status
	 *            �������ǣ���Ϊ��ȡ�÷�ȡ��״̬�Ĳ�Ʒ
	 * @return
	 */
	public static Collection getPsIDListByCond(int serviceAccount,
			int accountID, String status) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList psIDList = new ArrayList();
		String strSql = "select PSID from T_CUSTOMERPRODUCT where 1=1  ";
		if (serviceAccount != 0) {
			strSql = strSql + "  AND SERVICEACCOUNTID=" + serviceAccount;

		}
		if (accountID != 0) {
			strSql = strSql + "  AND ACCOUNTID=" + accountID;

		}
		if (status != null && !"".equals(status)) {
			strSql = strSql + "  AND STATUS='" + status + "'";
		} else {
			strSql = strSql + "  AND STATUS<>'C'";
		}
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				psIDList.add(new Integer(rs.getInt("PSID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getPsIDListByCond", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return psIDList;
	}

	/**
	 * ���ݿͻ���ID,����ҵ���˻���id,����״̬ȡ��������Ч��ҵ���˻�ID
	 * 
	 * @param customerID
	 *            ���봫��
	 * @param serviceAccountID
	 *            ��ѡ
	 * @param status
	 *            ��ѡ������������Ϊ��ѯ��ȡ��״̬��ҵ���˻�
	 * @return
	 */
	public static Collection getSADTOLisByCustIDAndSAIDAndStatus(
			int customerID, int serviceAccountID, String status) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList serviceAccountDTOList = new ArrayList();
		String strSql = "select * from T_SERVICEACCOUNT where CUSTOMERID="
				+ customerID;
		if (serviceAccountID != 0) {
			strSql = strSql + "  AND SERVICEACCOUNTID=" + serviceAccountID;
		}
		if (status != null && !"".equals(status)) {
			strSql = strSql + "  AND STATUS='" + status + "'";
		} else {
			strSql = strSql + "  AND  STATUS <> 'C' ";

		}
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				serviceAccountDTOList.add(DtoFiller.fillServiceAccountDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getSADTOLByCustIDAndSAIDAndStatus", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return serviceAccountDTOList;
	}

	/**
	 * ���ݿͻ���IDȡ�ÿͻ���Ϣ
	 * 
	 * @param productID
	 * @return
	 */
	public static CustomerDTO getCustomerDTOByCustomerID(int customerID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CustomerDTO customerDTO = null;
		String strSql = "select * from T_CUSTOMER where CUSTOMERID="
				+ customerID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				customerDTO = DtoFiller.fillCustomerDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustomerDTOByCustomerID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return customerDTO;
	}

	/**
	 * �����ʵ���ȡ���ʵ������Ϣ
	 * 
	 * @param invoiceNO
	 * @return
	 */
	public static InvoiceDTO getInvoiceDTOByInvoiceNO(int invoiceNO) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InvoiceDTO invoiceDTO = null;
		String strSql = "select *  from T_INVOICE where INVOICENO=" + invoiceNO;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				invoiceDTO = DtoFiller.fillInvoiceDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getInvoiceDTOByInvoiceNO", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return invoiceDTO;
	}

	/**
	 * ���ݱ���IDȡ��custproblemProcessDto
	 * 
	 * @param custproblemid
	 * @return CustProblemProcessDTO
	 */
	public static CustProblemProcessDTO getCustProblemProcessDTOByID(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CustProblemProcessDTO cpDTO = null;
		String strSql = "select * from t_custproblemprocess where action='A' and custproblemid="
				+ id;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				cpDTO = DtoFiller.fillCustProblemProcessDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustProblemProcessDTOByID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return cpDTO;
	}

	/**
	 * ���ݱ��޵���IDȡ�ñ��޵�����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public static CustomerProblemDTO getCustomerProblemDTOByID(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CustomerProblemDTO customerProblemDTO = null;
		String strSql = "select * from T_CUSTOMERPROBLEM where id=" + id;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				customerProblemDTO = DtoFiller.fillCustomerProblemDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustomerProblemDTOByID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return customerProblemDTO;
	}

	/**
	 * ���ݹ�����IDȡ�ù�������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public static JobCardDTO getJobCardDTOByJobCardId(int jobCardId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		JobCardDTO jobCardDTO = null;
		String strSql = "select *  from T_JOBCARD WHERE JOBCARDID=" + jobCardId;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				jobCardDTO = DtoFiller.fillJobCardDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getJobCardDTOByJobCardId", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return jobCardDTO;
	}

	/**
	 * ��������IDȡ����������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public static CustServiceInteractionDTO getCSIDTOByID(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CustServiceInteractionDTO custServiceInteractionDTO = null;
		String strSql = "select *  from T_CUSTSERVICEINTERACTION where id="
				+ id;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				custServiceInteractionDTO = DtoFiller.fillCustServiceInteractionDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCSIDTOByID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return custServiceInteractionDTO;
	}

	/**
	 * ���ݿͻ���ַ��IDȡ�ÿͻ���ַ��Ϣ
	 * 
	 * @param productID
	 * @return
	 */
	public static AddressDTO getAddressDTOByAddressID(int addressID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AddressDTO addressDTO = null;
		String strSql = "select * from T_ADDRESS where ADDRESSID=" + addressID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				addressDTO = DtoFiller.fillAddressDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAddressDTOByAddressID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return addressDTO;
	}

	/**
	 * ����ҵ���˻�ȡ�ø�ҵ���˻���Ӧ�����д���
	 * 
	 * @param productID
	 * @return
	 */
	public static Map getCampaignIDByServiceAccountID(int serviceAccountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap custCampaignMap = new HashMap();
		StringBuffer strSql = new StringBuffer();
		strSql.append("select distinct cam.* from t_customercampaign cam ");
		strSql.append("join t_cpcampaignmap cpMap on cam.ccid=cpMap.Ccid ");
		strSql.append("join t_campaign c on cam.campaignid=c.campaignid ");
		strSql.append("and c.campaigntype='").append(
				CommonConstDefinition.CAMPAIGNCAMPAIGNTYPE_NORMAL).append("' ");
		strSql
				.append("join t_customerproduct cp on cpMap.Custproductid=cp.psid ");
		strSql.append("where cam.status in ('").append(
				CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL).append(
				"','");
		strSql.append(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NEW).append(
				"') ");
		strSql.append("and serviceaccountid=").append(serviceAccountID);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				custCampaignMap.put(DtoFiller.fillCustomerCampaignDTO(rs),
						new Integer(rs.getInt("campaignid")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignIDByServiceAccountID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return custCampaignMap;
	}

	/**
	 * �����ʻ�IDȡ��ǰ�ͻ���Ч�Ĵ���
	 * 
	 * @param accountID
	 * @return
	 */
	public static Map getCampaignIDByAccountID(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap custCampaignMap = new HashMap();

		StringBuffer strSql = new StringBuffer();
		strSql.append("select cc.* from t_customercampaign cc ");
		strSql.append("join t_campaign c on cc.campaignid=c.campaignid ");
		strSql.append("and c.campaigntype='").append(
				CommonConstDefinition.CAMPAIGNCAMPAIGNTYPE_NORMAL).append("' ");
		strSql.append("where c.obligationflag = '").append(
				CommonConstDefinition.YESNOFLAG_YES).append("' ");
		strSql.append("and cc.status in ('").append(
				CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL).append(
				"','");
		strSql.append(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NEW).append(
				"') ");

		strSql.append("and cc.accountid=").append(accountID);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				custCampaignMap.put(DtoFiller.fillCustomerCampaignDTO(rs),
						new Integer(rs.getInt("campaignid")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignIDByAccountID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return custCampaignMap;
	}

	/**
	 * ���ݵ�ǰ�Ĳ�Ʒidȡ�����������ڸò�Ʒ�Ĳ�Ʒid
	 * 
	 * @param productID
	 * @return
	 */
	public static Collection getDependenceProductByProductID(int productID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList cppList = new ArrayList();

		String strSql = "select  PRODUCTID  from T_PRODUCTDEPENDENCY p "
				+ " where (p.referproductidlist like '" + productID + ",%'"
				+ " or p.referproductidlist like '%," + productID + "'"
				+ " or p.referproductidlist like '%," + productID + ",%'"
				+ " or p.referproductidlist ='" + productID + "') "
				+ " and (p.type ='"
				+ CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D
				+ "' or p.type='"
				+ CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P + "')";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				cppList.add(new Integer(rs.getInt("PRODUCTID")));
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDependenceProductByProductID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return cppList;
	}

	/**
	 * ����ҵ���˻�idȡ�ø�ҵ���˻��µ����в�Ʒ��
	 * 
	 * @param saID
	 * @return
	 */
	public static Collection getCustProductPackageBySAID(int saID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList cppList = new ArrayList();
		String strSql = "select distinct(referpackageid) from t_customerproduct where serviceaccountid="
				+ saID;
		strSql = strSql + "  and STATUS<>'"
				+ CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL + "'";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				cppList.add(new Integer(rs.getInt("REFERPACKAGEID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustProductPackageBySAID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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

		return cppList;
	}

	/**
	 * �����˻�idȡ�ø��˻������в�Ʒ
	 * 
	 * @param saID
	 * @return
	 */
	public static Collection getCustProductByAccountID(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList custProductList = new ArrayList();

		String strSql = "select * from T_CUSTOMERPRODUCT where ACCOUNTID="
				+ accountID;
		strSql = strSql + "  and STATUS<>'"
				+ CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL + "'";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				custProductList.add(DtoFiller.fillCustomerProductDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustProductByAccountID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return custProductList;
	}

	/**
	 * ���ݿͻ�ID��ҵ���˻���IDȡ��ҵ���˻�ID�б�
	 * 
	 * @param customerID
	 * @param serviceAccountID
	 * @return
	 */
	public static Collection getSAIDListByCustIDAndSAID(int customerID,
			int serviceAccountID) {
		String conditionSql = "  STATUS<> 'C'";
		if (serviceAccountID != 0) {
			conditionSql += " and SERVICEACCOUNTID=" + serviceAccountID;
		}
		Collection currentCol = getCurrentServiceAccountByCustomerID(
				customerID, conditionSql);
		return currentCol;
	}

	/**
	 * ���ݿͻ�id ȡ�����пͻ���Ʒ��ҵ���˻�id
	 * 
	 * @param customerID
	 * @param conditionSql
	 * @return
	 */
	public static Collection getCurrentServiceAccountByCustomerID(
			int customerID, String conditionSql) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection currentServiceAccountCol = new ArrayList();
		String strSql = "select SERVICEACCOUNTID  from T_SERVICEACCOUNT WHERE CUSTOMERID=  "
			          + customerID;
	    if (conditionSql != null && !"".equals(conditionSql)) {
		     strSql = strSql + "  and  " + conditionSql;
	    }
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				currentServiceAccountCol.add(new Integer(rs
						.getInt("SERVICEACCOUNTID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCurrentServiceAccountByCustomerID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentServiceAccountCol;
	}

	/**
	 * ͨ�������õ��������������Ŀͻ�����Чҵ���ʻ�ID
	 * 
	 * @param csiID
	 * @return ΪSAID��Integer������б�
	 */
	public static Collection getServiceAccountIDListByCsiID(int csiID) {
		if (csiID == 0)
			return null;

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection currentServiceAccountCol = new ArrayList();
		try {
			String strSql = "select distinct(T.Referserviceaccountid) as said from t_csicustproductinfo t,"
					+ "t_serviceaccount sa where t.referserviceaccountid=sa.serviceaccountid and sa.status <>'"
					+ CommonConstDefinition.SA_STATUS_CANCEL
					+ "' and t.csiid="
					+ csiID;

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
			   if (rs.getInt("said") > 0)
				   currentServiceAccountCol.add(new Integer(rs.getInt("said")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getServiceAccountIDListByCsiID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentServiceAccountCol;
	}

	/**
	 * ���ݲ�Ʒ��idȡ�����в�������Ż�
	 * 
	 * @param packageID
	 * @return
	 */
	public static ArrayList getCampaignIDListByPackageID(int packageID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList currentDataList = new ArrayList();
		try {
			String strSql = "select CAMPAIGNID from t_campaign_agmt_package  WHERE packageid= "
					+ packageID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				currentDataList.add(new Integer(rs.getInt("CAMPAIGNID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignIDListByPackageID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentDataList;
	}

	/**
	 * ҵ���˻�ID��ȡ�ĸ�ҵ���˻������еĲ�Ʒ
	 * 
	 * @param serviceAccountID
	 *            ҵ���˻�id
	 * @param conditionSql
	 *            ��������sql
	 * @return
	 */
	public static ArrayList getAllProductIDListByServiceAccount(
			int serviceAccountID, String status) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList currentDataList = new ArrayList();
		try {
			String strSql = "";
			if (status == null || "".equals(status)) {
				strSql = "SELECT PSID FROM T_CUSTOMERPRODUCT  WHERE SERVICEACCOUNTID= "
						+ serviceAccountID;
			} else {
				strSql = "SELECT PSID FROM T_CUSTOMERPRODUCT  WHERE SERVICEACCOUNTID= "
						+ serviceAccountID + "  and STATUS='" + status + "'";
			}
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				currentDataList.add(new Integer(rs.getInt("PSID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAllProductIDListByServiceAccount", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentDataList;
	}

	/**
	 * ҵ���˻�ID��ȡ�ĸ�ҵ���˻������е������Ʒ
	 * 
	 * @param serviceAccountID
	 *            ҵ���˻�id
	 * @param conditionSql
	 *            ��������sql
	 * @return
	 */
	public static ArrayList getSoftwareProductIDListByServiceAccount(
			int serviceAccountID, String status) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList currentDataList = new ArrayList();
		try {
			String strSql = "";
			if (status == null || "".equals(status)) {
				strSql = "SELECT PSID FROM T_CUSTOMERPRODUCT  WHERE SERVICEACCOUNTID= "
						+ serviceAccountID + " and DeviceID=0";
			} else {
				strSql = "SELECT PSID FROM T_CUSTOMERPRODUCT  WHERE SERVICEACCOUNTID= "
						+ serviceAccountID
						+ " and DeviceID=0"
						+ " and  STATUS='" + status + "'";
			}

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				currentDataList.add(new Integer(rs.getInt("PSID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getSoftwareProductIDListByServiceAccount", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentDataList;
	}

	/**
	 * ҵ���˻�ID��ȡ�ĸ�ҵ���˻������еĲ�Ʒ
	 * 
	 * @param serviceAccountID
	 *            ҵ���˻�id
	 * @param conditionSql
	 *            ��������sql
	 * @return
	 */
	public static ArrayList getProductIDListByServiceAccount(
			int serviceAccountID, String conditionSql) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList currentDataList = new ArrayList();
		try {
			String strSql = "SELECT DISTINCT (PRODUCTID) FROM T_CUSTOMERPRODUCT  WHERE SERVICEACCOUNTID= "
					+ serviceAccountID;
			if (conditionSql != null && !"".equals(conditionSql)) {
				strSql = strSql + "  and  " + conditionSql;
			}

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				currentDataList.add(new Integer(rs.getInt("PRODUCTID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductIDList", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentDataList;
	}

	/**
	 * ҵ���˻�ID��ȡ�ĸ�ҵ���˻������еĿͻ���Ʒ
	 * 
	 * @param serviceAccountID
	 *            ҵ���˻�id
	 * @param conditionSql
	 *            ��������sql
	 * @return
	 */
	public static ArrayList getPsIDListByServiceAccount(int serviceAccountID,
			String conditionSql) {
		return getPsIDListByServiceAccount(serviceAccountID,
				CommonConstDefinition.PRODUCTCLASS_SOFTWARE, conditionSql);
	}

	/**
	 * ����ҵ���ʻ�ȡ��PSIDLIST
	 * 
	 * @param serviceAccountID
	 * @param productClass
	 * @param isDescend
	 * @return
	 */
	public static ArrayList getPsIDListByServiceAccount(int serviceAccountID,
			String productClass, String conditionSql) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList currentDataList = new ArrayList();
		try {
			StringBuffer strSql = new StringBuffer();
			strSql.append("SELECT PSID FROM T_CUSTOMERPRODUCT  WHERE SERVICEACCOUNTID= ")
				  .append(serviceAccountID);

			if (productClass != null && !"".equals(productClass)) {
				if (CommonConstDefinition.PRODUCTCLASS_HARD
						.equals(productClass)) {
					strSql.append(" and DeviceID != 0");
				} else {
					strSql.append(" and DeviceID = 0");
				}
			}
			if (conditionSql != null && !"".equals(conditionSql)) {
				strSql.append(" and ").append(conditionSql);
			}

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				currentDataList.add(new Integer(rs.getInt("PSID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductIDList", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentDataList;
	}

	// ����ǰ����ֲ����CsrBusinessUtility---------------��ʼ
	public static Collection getProductIDsByPackageID(int packageId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection colProducts = null;
		try {
			colProducts = new ArrayList();
			String strSql = "select a.productid from t_packageline a "
					+ "where a.packageid = " + packageId;

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				colProducts.add(new Integer(rs.getInt("productid")));
			}
			
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductIDsByPackageID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return colProducts;
	}

	/**
	 * ȡ����Ч�ĸ���֯������Ŀ
	 * 
	 * @param orgId
	 * @return ��Ч�ĸ���֯������Ŀ
	 */
	public static int getInvalidParentOrgCount(int orgId) {
		String strsql = "select count(*) from T_ORGANIZATION o "
				+ "where status='I' connect by prior o.parentorgid=o.orgid start with o.orgid="
				+ orgId;
		int count =getIntBySQL(strsql);
		return count;
	}

	/**
	 * ���ݲ�Ʒ�б������б��а�����ҵ����
	 * 
	 * @param products
	 * @return�� ҵ��ID ��ɵ�set
	 */
	public static Set belongToServices(Collection productCol) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Set serviceList = new HashSet();
		String sql = "SELECT serv.serviceid FROM t_service serv, t_product prod,t_producttoservice ps "
				+ "WHERE serv.serviceid=ps.serviceid  and "
				+ "prod.productid=ps.productid and  prod.newsaflag='Y'"
				+ " and prod.productid=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			Iterator iterator = productCol.iterator();
			while (iterator.hasNext()) {
				Integer product = (Integer) iterator.next();
				stmt.setInt(1, product.intValue());
				rs = stmt.executeQuery();
				if (rs.next())
					serviceList.add(new Integer(rs.getInt(1)));
				rs.close();
			}
		} catch (SQLException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return serviceList;
	}

	/**
	 * ͨ����Ʒ��id���Ż�idȡ�õ�ǰ��Ʒ�����Ż�id
	 * 
	 * @param packageID
	 * @param campaignID
	 * @return
	 */
	public static int getCampaignIDByPacageIDandcampaignID(int packageID,
			int campaignID) {
		return getIntBySQL(" select CAMPAIGNID from T_CAMPAIGNNEWPACKAGECOND WHERE NEWPACKAGEID ="
				           + packageID);
	}

	public static int getGovernedOrgIDByDistrictID(int districtid) {
		return getIntBySQL("SELECT orgid FROM t_OrgGovernedDistrict where districtid="
				          + districtid);
	}

	public static int getParentOrgIDByOrgID(int orgID) {
		return getIntBySQL("SELECT org.orgid FROM t_organization org, t_organization org1"
				         + " where org.orgid = org1.parentorgid and org1.orgid=" + orgID);
	}

	public static Collection getRoleOrgListByOrgId(int orgid, String role) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT serviceorgid, isfirst FROM t_RoleOrganization"
				+ " where orgid=" + orgid + " and OrgRole='" + role + "'";
		ArrayList roleOrgList = new ArrayList();
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				RoleOrganizationDTO dto = new RoleOrganizationDTO();
				dto.setServiceOrgId(rs.getInt(1));
				dto.setIsFirst(rs.getString(2));
				roleOrgList.add(dto);
			}
			return roleOrgList;
		} catch (SQLException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return null;
	}
	
	/**
	 * ͨ���Ź���ϸ����ȡ���Ź�
	 * 
	 * @param detailNO
	 * @return
	 */
	public static GroupBargainDetailDTO getGroupBargainDetailByNO(
			String detailNO) throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		GroupBargainDetailDTO detailDTO = null;
		String sql = "select * from T_GroupBargainDetail where DetailNo='"
				+ detailNO + "'";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				detailDTO = DtoFiller.fillGroupBargainDetailDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getGroupBargainDetail", e);
			throw new ServiceException("�Ҳ����Ź���ϸ��Ϣ");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return detailDTO;
	}

	/**
	 * ͨ���Ź���ϸidȡ�ö�Ӧ����ϸ��Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public static GroupBargainDetailDTO getGroupBargainDetailByID(int id)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		GroupBargainDetailDTO detailDTO = null;
		String sql = "select * from T_GroupBargainDetail where ID=" + id;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				detailDTO = DtoFiller.fillGroupBargainDetailDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getGroupBargainDetailByID", e);
			throw new ServiceException("�Ҳ����Ź���ϸ��Ϣ");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return detailDTO;
	}

	/**
	 * ͨ���Ź���ϸ����ȡ���Ź�
	 * 
	 * @param detailNO
	 * @return
	 */
	public static GroupBargainDTO getGroupBargainByID(int groupBargainID)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		GroupBargainDTO grouplDTO = null;
		String sql = "select * from T_GroupBargain where ID=" + groupBargainID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				grouplDTO = DtoFiller.fillGroupBargainDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getGroupBargain", e);
			throw new ServiceException("�Ҳ����Ź���ϸ��Ϣ");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return grouplDTO;
	}

	/**
	 * ���ݲ�Ʒ��idȡ���豸������
	 * 
	 * @param productid
	 * @return
	 */
	public static String getDeviceClassByProductID(int productid) {
		return getStringBySQL(" select distinct(DEVICECLASS) DEVICECLASS from T_DEVICEMODEL where DEVICEMODEL in (select DEVICEMODEL from T_DEVICEMATCHTOPRODUCT where PRODUCTID="
				         	  + productid + ")");
	}

	/**
	 * ���ݲ�Ʒ��idȡ���豸���ͺ�
	 * 
	 * @param productid
	 * @return
	 */
	public static Collection getDeviceModelByProductID(int productid) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection deviceModelCol = new ArrayList();
		try {
			String strSql = "select distinct(DEVICEMODEL)  as DEVICEMODEL from T_DEVICEMATCHTOPRODUCT where PRODUCTID="
					      + productid ;

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				deviceModelCol.add(rs.getString("DEVICEMODEL"));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDeviceModelByProductID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return deviceModelCol;
	}

	/**
	 * add by zhouxushun ,date :2005-10-21
	 * ͨ����ͬҵ�����͵�CustomerProductDTOList�õ���ص�ҵ���ʺ�ID��ǰ�᣺productCol���봦����ͬҵ��
	 * 
	 * @param productCol
	 * @return
	 */
	public static int getServiceIDFromCustomerPoductDTOList(
			Collection productCol) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null; 
		int serviceID = 0;
		String sql = "SELECT serv.serviceid FROM t_service serv, t_product prod,t_producttoservice ps "
				   + " WHERE serv.serviceid=ps.serviceid  and "
				   + " prod.productid=ps.productid and  prod.newsaflag='Y'"
				   + " and prod.productid=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			Iterator iterator = productCol.iterator();
			while (iterator.hasNext()) {
				CustomerProductDTO dto = (CustomerProductDTO) iterator.next();
				stmt.setInt(1, dto.getProductID());
				rs = stmt.executeQuery();
				if (rs.next()) {
					serviceID = rs.getInt(1);
					break;
				}
			}
		} catch (SQLException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return serviceID;
	}

	public static Collection getAllProductDepentDefineList(Collection productCol) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList dependList = new ArrayList();
		if (productCol == null || productCol.isEmpty())
			return dependList;
		String sql = "Select * From T_ProductDependency Where productid in ("
				   + productCol.toString().substring(1,productCol.toString().length() - 1) + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductDependencyDTO dto = DtoFiller.fillProductDependencyDTO(rs);
				dependList.add(dto);
			}
		} catch (SQLException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dependList;
	}

	public static Collection getAllProductDepentDefineList() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList dependList = new ArrayList();
		String sql = "Select * From T_ProductDependency";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductDependencyDTO dto = DtoFiller.fillProductDependencyDTO(rs);
				dependList.add(dto);
			}
		} catch (SQLException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dependList;
	}

	/**
	 * add by zhouxushun for open serviceaccount , date 2005-10-21
	 * ����������ҵ���ʻ�ʱ�����ݲ�Ʒ�б������б��а�����ҵ������������ظ���ҵ�����׳�ServiceException�쳣
	 * 
	 * @param products
	 * @return�� ҵ��ID ��ɵ�set
	 * @throws ServiceException
	 */
	public static Collection getServicesListFromCustomerPoductDTOList(
			Collection productCol) throws ServiceException {
		return getServicesListFromCustomerPoductDTOList(productCol, true);
	}

	public static Collection getServicesListFromCustomerPoductDTOList(
			Collection productCol, boolean isSort) throws ServiceException {

		// ҵ�����ʹ洢����ʱ�ã��ж��Ƿ�����ظ�����ҵ������
		Set serviceSet = new HashSet();
		// ����ֵ�������ܴ���ҵ���ʺŵ�ProductID
		Collection saProductList = new ArrayList();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT serv.serviceid FROM t_service serv, t_product prod,t_producttoservice ps "
				+ "WHERE serv.serviceid=ps.serviceid  and "
				+ "prod.productid=ps.productid and  prod.newsaflag='Y'"
				+ " and prod.productid=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);

			Iterator iterator = productCol.iterator();
			while (iterator.hasNext()) {
				CustomerProductDTO dto = (CustomerProductDTO) iterator.next();
				stmt.setInt(1, dto.getProductID());
				rs = stmt.executeQuery();
				if (rs.next()) {
					Integer service = new Integer(rs.getInt(1));
					if (serviceSet.contains(service)) {
						throw new ServiceException("һ��ҵ�������г������λ��������ϵ���ͬҵ��");
					} else {
						serviceSet.add(service);
						saProductList.add(dto);
					}
				}
			}
		} catch (SQLException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		serviceSet.clear();

		// ��saProductList��������������
		if (isSort) {
			Collection saProductDTOList = new ArrayList();
			while (saProductList.size() > 0) {
				Iterator itsa = saProductList.iterator();
				CustomerProductDTO cpLow = (CustomerProductDTO) itsa.next();
				while (itsa.hasNext()) {
					CustomerProductDTO cp = (CustomerProductDTO) itsa.next();
					if (cpLow.getProductID() > cp.getProductID())
						cpLow = cp;
				}
				CustomerProductDTO cpDto = new CustomerProductDTO();
				cpDto.setProductID(cpLow.getProductID());
				cpDto.setReferPackageID(cpLow.getReferPackageID());
				saProductDTOList.add(cpDto);
				saProductList.remove(cpLow);
			}
			return saProductDTOList;
		} else {
			return saProductList;
		}
	}

	/**
	 * add by zhouxushun , date :2005-10-21 ����Ʒ�����Ȩ����
	 * 
	 * @param p1Ϊ���Ĳ�Ʒ
	 * @param p2Ϊ��Ȩ�Ĳ�Ʒ
	 * @return
	 */
	public static boolean prodcutsIsDependency(int p1, int p2) {
		try {
			String type = "'" + CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D
					+ "','" + CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P
					+ "' ";
			Collection dependencyOfBuy = null;
			dependencyOfBuy = getProdDependByProdIdAndType(p1, type);

			if (dependencyOfBuy == null || dependencyOfBuy.isEmpty())
				return false;

			Iterator itDependencyOfBuy = dependencyOfBuy.iterator();
			while (itDependencyOfBuy.hasNext()) {
				ProductDependencyDTO productDependencyDTO = (ProductDependencyDTO) itDependencyOfBuy
						.next();
				String strReferProductIDs = productDependencyDTO
						.getReferProductIDList();
				if (strReferProductIDs == null || "".equals(strReferProductIDs))
					return false;
				String[] strReferProductID = strReferProductIDs.split(",");
				for (int i = 0; i < strReferProductID.length; i++) {
					if (strReferProductID[i].equals(String.valueOf(p2)))
						return true;
				}
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.WARN,
					"����Ʒ�����������(prodcutsIsDependency)", e);
			return false;
		}
		return false;
	}

	/*
	 * �ж�IdΪsettingId��T_CallbackInfoSetting��¼�Ƿ����
	 * ������T_CallbackInfoValue��¼ʱ��Ҫ���ø÷���
	 */
	public static boolean isValidSettingID(int settingId) {
		if (settingId == 0)
			return false;
		boolean ret = false;
		Connection conn = DBUtil.getConnection();
		try {
			String strSql = "select id from T_CallbackInfoSetting where id ='"
					+ settingId + "'";
			ret = DBUtil.hasValidRecordExist(conn, strSql);
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"isValidSettingID", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"isValidSettingID", e);
				}
			}
		}
		return ret;
	}

	/**
	 * add by zhouxushun , date 2005-10-21 �Ѳ�Ʒ����List���CustomerProductDtoList
	 * 
	 * @param packageIDList����Ʒ����List
	 * @return CustomerProductDtoList
	 */
	public static Collection getCustomerProductDTOListByPackageIDList(
			Collection packageIDList) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection colProducts = new ArrayList();
		try {
			String strSql = "select a.productid,a.packageid from t_packageline a "
					+ " where a.packageid in ("
					+ packageIDList.toString().substring(1,
							packageIDList.toString().length() - 1) + ")";
			
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CustomerProductDTO dto = new CustomerProductDTO();
				dto.setProductID(rs.getInt("productid"));
				dto.setReferPackageID(rs.getInt("packageid"));
				colProducts.add(dto);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustomerProductDTOListByPackageID", e);
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
		return colProducts;
	}

	/**
	 * ͨ����ͬ�ŵõ���Ʒ���б�
	 * 
	 * @fiona
	 * @param conID
	 * @return
	 */
	public static Collection getPackageIDByContractID(String conID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection colPackage = new ArrayList();
		try {
			String strSql = "select * from t_contracttopackage  where contractno= '"
					      + conID + "'";
			System.out.println("*************" + strSql);
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				colPackage.add(new Integer(rs.getInt("PRODUCTPACKAGEID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getPackageIDByContractID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return colPackage;
	}

	/**
	 * �õ���ͬDTO
	 * 
	 * @param conID
	 * @return
	 * @throws ServiceException
	 */
	public static ContractDTO getContractDTOByContractNo(String conID)
			throws ServiceException {
		ContractDTO dto = getContractDTOByContractNo(conID, false);
		return dto;
	}

	/**
	 * �õ���ͬDTO,���Ǹ�����Ҫ������ʱ���жϣ�Ҫ�����������ڴ���ϵͳʱ��
	 * 
	 * @param conID
	 * @param checkTimeLength
	 * @return
	 * @throws ServiceException
	 */
	public static ContractDTO getContractDTOByContractNo(String conID,
			boolean checkTimeLength) throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ContractDTO dto = null;
		try {
			String strSql = "select * from t_contract  where contractno='"
					+ conID + "'";
			if (checkTimeLength) {
				strSql = strSql + " and to_timestamp('"
					   + TimestampUtility.getCurrentDateWithoutTime()
					   + "', 'YYYY-MM-DD-HH24:MI:SSxff') <= NormalDate";
			}
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillContractDTO(rs);
			} else
				throw new ServiceException("�ú�ͬ�Ų����ڻ��ߺ�ͬ���ѹ�������ֹ����");

		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getContractDTOByConID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	/**
	 * add by zhouxushun , date :2005-10-21
	 * ǰ��������packageList��campaignListҵ���߼�û���⣬��BusinessRuleService��֤��
	 * ���ݷ�װ��ʽ��packageList��װ�Ķ���ΪInteger��campaignList��װ�Ķ���ҲΪInteger��
	 * ����Map��װ��ʽ��KEY��ΪInteger����ValueΪInteger����
	 * 
	 * @param packageList
	 * @param campaignList
	 * @return
	 */
	public static Map getPackageCampaignMap(Collection packageList,
			Collection campaignList) {

		LogUtility.log(clazz, LogLevel.DEBUG, "����Ĳ�Ʒ���б�IDΪ��" + packageList);
		LogUtility.log(clazz, LogLevel.DEBUG, "������Ż��б�IDΪ��" + campaignList);

		Map map = new HashMap();

		if (packageList == null || campaignList == null
				|| packageList.size() == 0 || campaignList.size() == 0)
			return map;

		Iterator itPackage = packageList.iterator();
		while (itPackage.hasNext()) {
			int packageId = ((Integer) itPackage.next()).intValue();

			Iterator itCampaign = campaignList.iterator();
			while (itCampaign.hasNext()) {
				int campaignId = ((Integer) itCampaign.next()).intValue();

				boolean suport = false;
				try {
					suport = CsrBusinessUtility.supportedPackageInCampaign(
							packageId, campaignId);
				} catch (Exception ignore) {
					LogUtility.log(clazz, LogLevel.DEBUG, "ƥ���Ʒ�����Żݳ�����"
							+ ignore);
				}
				if (suport) {
					map.put(new Integer(packageId), new Integer(campaignId));
				}
			}
		}
		return map;
	}

	/**
	 * add by zhouxushun ,date :2005-10-25 ͨ��DeviceID�õ��豸����
	 * 
	 * @param deviceID
	 * @return
	 * @throws HomeFactoryException
	 * @throws FinderException
	 */
	public static String getDeviceClassByDeviceID(Integer deviceID)
			throws HomeFactoryException, FinderException {
		TerminalDeviceHome tdHome = HomeLocater.getTerminalDeviceHome();
		TerminalDevice td = tdHome.findByPrimaryKey(deviceID);
		return td.getDeviceClass();
	}

	/**
	 * ͨ��DeviceID�õ��豸�����к� add by zhouxushun , date 2005-10-25
	 * 
	 * @param deviceID
	 * @return
	 * @throws HomeFactoryException
	 * @throws FinderException
	 */
	public static String getDeviceSerialNoByDeviceID(Integer deviceID)
			throws HomeFactoryException, FinderException {
		TerminalDeviceHome tdHome = HomeLocater.getTerminalDeviceHome();
		TerminalDevice td = tdHome.findByPrimaryKey(deviceID);
		return td.getSerialNo();
	}

	/**
	 * ����07-1-18�޸�EJBȡֵ��ʽΪJDBC
	 * 
	 * @param operatorid
	 * @return
	 */
	public static int FindOrgIDByOperatorID(int operatorid) {
		return getIntBySQL("select orgid from t_operator where operatorid="
				+ operatorid);
	}

	public static int findIDByDetailNo(String detailNo) {
		return getIntBySQL("select id from t_groupbargaindetail where status ='W' and detailno='"
				+ detailNo + "'");
	}

	public static Collection getDeviceModelDTOByProductID(int productID) {
		if (productID < 1)
			return null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection list = new ArrayList();
		String sqlStr = "select dm.* from t_devicemodel dm,T_DeviceMatchToProduct match where "
				+ "dm.status='"
				+ CommonConstDefinition.DEVICEMODELSTATUS_NORMAL
				+ "' and dm.managedeviceflag='"
				+ CommonConstDefinition.YESNOFLAG_YES
				+ "' and match.devicemodel=dm.devicemodel and match.productid="
				+ productID;
		LogUtility.log(clazz, LogLevel.DEBUG, "�õ�Ӳ���豸:" + sqlStr);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			Integer deviceProductID = new Integer(productID);
			if (rs.next()) {
				list.add(deviceProductID);
			}
		} catch (Exception e) {
			LogUtility
					.log(clazz, LogLevel.DEBUG, "ͨ����ƷID���ҵ��豸������Ϣʧ�ܣ�ԭ�����£�" + e);
			return null;
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return list;
	}

	public static boolean productIDMatchDeviceID(int productID, int deviceID) {
		boolean isCan = false;
		String strSql = "select device.DEVICEID  from T_TERMINALDEVICE device,T_DEVICEMATCHTOPRODUCT product ";
		strSql = strSql
		  	   + " where device.DEVICEID="
		       + deviceID
		   	   + " and product.PRODUCTID="
		  	   + productID
		       + " and device.DEVICEMODEL=product.DEVICEMODEL and device.STATUS='"
			   + CommonConstDefinition.DEVICE_STATUS_WAITFORSELL + "'";

		int deviceId =getIntBySQL(strSql);
		if (deviceId > 0){
			isCan = true;
		}
	
		return isCan;
	}
	
	public static boolean checkCmInfoByDeviceID(int deviceID){
		boolean isCan = false;
		String strSql = " select count(*) ct from T_TERMINALDEVICE t "
			          + " where t.DEVICEID ="+deviceID+" and t.matchflag='N' and t.matchdeviceid =0 "
			          + " and t.status='" + CommonConstDefinition.DEVICE_STATUS_WAITFORSELL + "'";
		
		int ct =getIntBySQL(strSql);
		if (ct > 0){
			isCan = true;
		}
		return isCan;
	}

	/**
	 * ����ƷID���豸�Ƿ�ƥ�� add by zhouxushun , date :2205-10-25
	 * 
	 * @param productID
	 * @param deviceID
	 * @return boolean
	 */
	public static int getOpOrgIDByOperatorID(int operatorID) {
		return getIntBySQL("select ORGID from T_OPERATOR where OPERATORID="+ operatorID);
	}

	/**
	 * ���ݲ���Աidȡ�ò���Ա�ľ�����Ϣ
	 * 
	 * @param operatorID
	 * @return
	 */
	public static OperatorDTO getOperatorDTOByID(int operatorID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		OperatorDTO operatorDTO = null;
		try {
			String strSql = "select * from T_OPERATOR where OPERATORID="
					+ operatorID;

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				operatorDTO = DtoFiller.fillOperatorDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getOperatorDTOByID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return operatorDTO;
	}

	/**
	 * ���ݲ�Ʒ����idȡ�ò�Ʒ���ľ�����Ϣ
	 * 
	 * @param operatorID
	 * @return
	 */
	public static ProductPackageDTO getProductPackageDTOByID(int packageID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductPackageDTO productPackageDTO = null;
		try {
			String strSql = "Select * From t_productpackage  Where packageid="
					+ packageID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				productPackageDTO = DtoFiller.fillProductPackageDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductPackageDTOByID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return productPackageDTO;
	}

	/**
	 * ͨ���ų�ID�õ����ų��µ�����ID���б�
	 * 
	 * @param scheduleID
	 * @return
	 */
	public static Collection getScheduleItemIDsByScheduleID(int scheduleID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		try {
			String strSql = "select ITEMNO from T_BATCHJOBITEM where BATCHID="
					+ scheduleID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new Integer(rs.getInt("ITEMNO")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getScheduleItemIDsByScheduleID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return list;
	}

	/**
	 * ɾ����ѯ�����������queryID�Ľ����
	 * 
	 * @param queryID
	 * @throws SQLException
	 */
	public static void deleteQueryResultItemIDsByQueryID(int queryID)
			throws SQLException {

		Connection con = DBUtil.getConnection();
		try {
			String strSql = "delete T_QueryResultItem where QueryID=" + queryID;
			DBUtil.executeUpdate(con, strSql);
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"deleteQueryResultItemIDsByQueryID", e);
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getQueryResultItemIDsByQueryID", e);
				}
			}
		}
	}

	/**
	 * ͨ��ҵ���ʻ�ID�õ���ҵ���ʻ��µ�Ӳ����Ʒ
	 * 
	 * @param saID
	 * @return Map��װ��ʽΪ�� keyΪ�ò�Ʒ��ID��valueΪdeviceID
	 */
	public static Map getSADeviceIDMapBySAID(int saID) {
		Map deviceIDMap = new HashMap();
		if (saID == 0)
			return null;

		try {
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(saID);

			Iterator itCP = cpList.iterator();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();

				if (cp.getDeviceID() > 0) {
					deviceIDMap.put(new Integer(cp.getProductID()),
							new Integer(cp.getDeviceID()));
				}
			}
		} catch (HomeFactoryException e1) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "�ͻ���Ʒ���ҳ���");
		} catch (FinderException e2) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "�ͻ���Ʒ��λ����");
		}

		return deviceIDMap;
	}

	public static void excuteSQL(String sql) throws SQLException {
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
				"��ʼִ��SQL��ִ�е�SQL���Ϊ��" + sql);
		Connection con = DBUtil.getConnection();
		Statement stmt = con.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (con != null)
			con.close();
		stmt = null;
		con = null;
	}

	/**
	 * 2006-2-17 shuwei ���� ͨ��ҵ���˻��õ�Ӳ����Ʒ�Ŀͻ���Ʒ�źͲ�Ʒ��
	 * 
	 * @param deviceType
	 *            �豸����
	 * @param saID
	 *            ҵ���˻���
	 * @throws SQLException
	 */
	public static Map getDeviceInfoBySAID(int saID, String deviceType)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map res = new HashMap();
		try {
			String strSql = "select a.psid,a.productid "
					+ " from T_CustomerProduct a,T_Product b,T_TerminalDevice c"
					+ " where a.productid = b.productid"
					+ " and a.deviceid = c.deviceid"
					+ " and a.ServiceAccountID ='" + saID + "'"
					+ " and b.ProductClass ='"
					+ CommonConstDefinition.PRODUCTCLASS_HARD + "'"
					+ " and c.DeviceClass ='" + deviceType + "'";

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.put(new Integer(rs.getInt("psid")), new Integer(rs
						.getInt("productid")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDeviceInfoBySAID", e);
			throw new ServiceException("����Ӳ���豸��Ϣʧ�ܣ�");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return res;
	}

	public static String getProductNameByProductID(int productID) {
		return getStringBySQL("select productname from T_PRODUCT where PRODUCTID="
				+ productID);
	}

	public static ProductDTO getProductDTOByProductID(int productID)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductDTO dto = null;
		try {
			String strSql = "select * from T_PRODUCT where PRODUCTID="
					+ productID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillProductDTO(rs);
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductDTOByProductID", e);
			throw new ServiceException("��Ʒ�����з�������");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		if (dto == null)
			throw new ServiceException("�Ҳ�����ز�Ʒ��Ϣ��");
		return dto;
	}

	public static ProductPropertyDTO getProductPropertyDTOByProductIDAndName(
			int id, String name) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductPropertyDTO dto = null;
		try {
			String strSql = "select * from T_PRODUCTPROPERTY where PRODUCTID="
					+ id + " and PROPERTYNAME='" + name + "' ";
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillProductPropertyDTO(rs, "");
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductDTOByProductID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}
	
	
	public static Collection getProductPropertyListByProductID(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection lst = new ArrayList();
		try {
			String strSql = "select * from T_PRODUCTPROPERTY where PRODUCTID="+ id ;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while(rs.next()){
				ProductPropertyDTO dto = DtoFiller.fillProductPropertyDTO(rs, "");
				lst.add(dto);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductDTOByProductID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return lst;
	}

	public static Bundle2CampaignDTO getBundle2CampaignDTOByPackageID(
			int packageID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Bundle2CampaignDTO dto = null;
		try {
			String strSql = "select * from T_BUNDLE2CAMPAIGN where PACKAGEID="
					+ packageID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillBundle2CampaignDTO(rs);
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getBundle2CampaignDTOByPackageID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static AcctItemTypeDTO getAcctItemTypeDTOByAcctTypeID(
			String itemTypeId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AcctItemTypeDTO dto = null;
		try {
			String strSql = "select * from t_acctitemtype where acctitemtypeid='"
					+ itemTypeId + "'";
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillAcctItemTypeDTO(rs);
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAcctItemTypeDTOByPackageID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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

		return dto;
	}

	public static ProductPropertyDTO getProductPropertyDTOByProductIDAndCode(
			int id, String code) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductPropertyDTO dto = null;
		try {
			String strSql = "select * from T_PRODUCTPROPERTY where PRODUCTID="
					+ id + " and propertycode='" + code + "' ";
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillProductPropertyDTO(rs, "");
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductDTOByProductID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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

		return dto;
	}

	public static void addPhoneUseLog(PhoneNoUseLogDTO phoneUseDto) {
		Statement sqlStrStmt = null;
		Connection con = DBUtil.getConnection();
		try {
			String strSql = "insert into T_PhoneNoUseLog(seqno, opid, orgid, createtime, resourceitemid, phoneno, areacode, countrycode, action, description, deviceid, networkid, custid, userid, psid, status, dt_create, dt_lastmod) values(PHONENOUSELOG_SEQNO.nextval,"
					+ phoneUseDto.getOpID()
					+ ","
					+ phoneUseDto.getOrgID()
					+ ",to_timestamp('"
					+ phoneUseDto.getCreateTime()
					+ "', 'YYYY-MM-DD-HH24:MI:SSxff'),"
					+ phoneUseDto.getResourceItemID()
					+ ",'"
					+ phoneUseDto.getPhoneNo()
					+ "','"
					+ phoneUseDto.getAreaCode()
					+ "','"
					+ phoneUseDto.getCountryCode()
					+ "','"
					+ phoneUseDto.getAction()
					+ "','"
					+ phoneUseDto.getDescription()
					+ "',"
					+ phoneUseDto.getDeviceID()
					+ ",'"
					+ phoneUseDto.getNetworkID()
					+ "',"
					+ phoneUseDto.getCustID()
					+ ","
					+ phoneUseDto.getUserID()
					+ ","
					+ phoneUseDto.getPsID()
					+ ",'"
					+ phoneUseDto.getStatus()
					+ "',to_timestamp('"
					+ phoneUseDto.getDtCreate()
					+ "', 'YYYY-MM-DD-HH24:MI:SSxff'),to_timestamp('"
					+ phoneUseDto.getDtLastmod()
					+ "', 'YYYY-MM-DD-HH24:MI:SSxff'))";
			sqlStrStmt = con.createStatement();
			sqlStrStmt.execute(strSql);

		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
					"��ӵ绰����ʹ����־����..." + e.getMessage());
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"addPhoneUseLog", e);
		} finally {
			if (sqlStrStmt !=null){
				try {
					sqlStrStmt.close();
				}catch(SQLException e){
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	/*
	 * Ŀǰ�ٶ��绰������Ψһ�ģ����������ţ�������Ҫ���ݵ绰�������õ�ResourcePhoneNoDTO
	 * û��ʹ��EJB�����ң���ΪphoneNO������ģ���ﲻ��Ψһ�ġ�
	 */
	public static ResourcePhoneNoDTO getDTOByPhoneNo(String phoneNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResourcePhoneNoDTO dto = new ResourcePhoneNoDTO();
		try {
			String strSql = "select * from t_resource_phoneno where phoneno = '"
					      + phoneNo + "'";

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillResourcePhoneNoDTO(rs, "");

		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getDTOByPhoneNo", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static int getResoucePhoneIDByPhoneNo(String phoneNo) {
		if (phoneNo == null || phoneNo.equals(""))
			return 0;
		return getIntBySQL("select itemid from t_resource_phoneno where phoneno = '"+ phoneNo + "'");
	}
	/**
	 * ͨ���Ż�ID�õ��Ż����������򼯺� david.Yang
	 * 
	 * @param campaignID
	 * @return Collection
	 * @throws ServiceException
	 */
	public static Collection getCampaignDistinct(int campaignID)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection lst = new ArrayList();
		try {
			String strSql = " select  c.districtid from t_campaign a ,t_campaigntomarketsegment b,t_marketsegmenttodistrict c"
					+ " where a.campaignid =b.campaignid and b.marketsegmentid =c.marketsegmentid and a.campaignid ="
					+ campaignID;

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				lst.add(new Integer(rs.getInt("districtid")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignDistinct", e);
			throw new ServiceException("ִ��ͨ���Ż�ID�õ��Ż����������򼯺ϲ�ѯ�����쳣�жϣ�");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return lst;
	}

	public static boolean isExitMultiLoginID(int customerID, String loginID)
			throws ServiceException {
		int ct =getIntBySQL("select count (*) count from t_customer t where t.customerid <>"+customerID+"  and t.loginid is not null and t.loginid ="+loginID);
		if (ct > 0){
		    return true;
		}
		return false;
	}

	public static boolean isExitPlanID(int bundlePrepaymentPlanId)
			throws ServiceException {
		int ct =getIntBySQL("select count (*) count from T_BUNDLEPREPAYMENT t where bundlePrepaymentPlanId ="+bundlePrepaymentPlanId);
		
		if (ct > 0){
		    return true;
		}
		return false;
	}

	/**
	 * ���ͻ�������Ʒ�Ƿ������������ ��ȡ�������ڡ�ά����
	 * 
	 * @param CCID
	 * @return
	 * @throws ServiceException
	 */
	public static boolean checkCancleCamp(int CCID) throws ServiceException {
		boolean b = false;
		String status = CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL;
		String timestamp = TimestampUtility.getCurrentDateWithoutTime()
				.toString();
		int ct =getIntBySQL(" select count (*) count from t_customercampaign  where CCID = "
				            + CCID
							+ "  and STATUS = "
							+ status
							+ " and   DATETO  >= to_timestamp("+timestamp+", 'YYYY-MM-DD-HH24:MI:SSxff')");
		if (ct == 0){
			b = true;
		}
		return b;
	}

	/**
	 * ���ݴ���idȡ�ô�����Ϣ
	 * 
	 * @param campaignID
	 * @return
	 */
	public static CampaignDTO getCampaignDTOByID(int campaignID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CampaignDTO campaignDTO = null;
		String querySql = " Select * from t_campaign where campaignid="
				+ campaignID;
		LogUtility.log(clazz, LogLevel.DEBUG, "getCampaignDTOByID", querySql);

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(querySql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				campaignDTO = DtoFiller.fillCampaignDTO(rs);
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getCampaignDTOByID", sqle);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return campaignDTO;
	}

	/**
	 * ȡ�ÿͻ�����ͨ���ͻ�����id
	 * 
	 * @param ccid
	 * @return
	 */
	public static CustomerCampaignDTO getCustomerCampaignDTOByID(int ccid) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CustomerCampaignDTO customerCampaignDTO = null;
		String querySql = " select * from t_customercampaign where ccid="
				+ ccid;
		LogUtility.log(clazz, LogLevel.DEBUG, "getCustomerCampaignDTOByID",
				querySql);

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(querySql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				customerCampaignDTO = DtoFiller.fillCustomerCampaignDTO(rs);
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getCustomerCampaignDTOByID", sqle);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return customerCampaignDTO;
	}

	/**
	 * ȡ�ÿͻ�����ͨ���ͻ�����id
	 * 
	 * @param ccid
	 * @return
	 */
	public static Collection getCustCampaignListByCsiID(int csiid) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection custCampaignCol = new ArrayList();
		String querySql = " select * from t_customercampaign where CsiID="
				+ csiid;
		LogUtility.log(clazz, LogLevel.DEBUG, "getCustCampaignListByCsiID",
				querySql);

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(querySql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				custCampaignCol.add(DtoFiller.fillCustomerCampaignDTO(rs));
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getCustCampaignListByCsiID", sqle);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return custCampaignCol;
	}

	public static HashMap getProductIDAndPackageIDBySAID(int serviceAccountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap map = new HashMap();
		String querySql = " select productid,referpackageid from t_customerproduct where serviceaccountid="
				+ serviceAccountID
				+ " and status <> '"
				+ CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL + "' ";
		LogUtility.log(clazz, LogLevel.DEBUG, "getProductAndCutProdMapBySAID",
				querySql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(querySql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Integer productid = new Integer(rs.getInt("productid"));
				Integer packageid = new Integer(rs.getInt("referpackageid"));
				map.put(productid, packageid);
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getProductIDAndPackageIDBySAID", sqle);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return map;
	}

	public static HashMap getProductIDAndPackageIDByAccountID(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap map = new HashMap();
		String querySql = " select productid,referpackageid from t_customerproduct where accountid="
				+ accountID
				+ " and status <> '"
				+ CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL + "' ";
		LogUtility.log(clazz, LogLevel.DEBUG,
				"getProductIDAndPackageIDByAccountID", querySql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(querySql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Integer productid = new Integer(rs.getInt("productid"));
				Integer packageid = new Integer(rs.getInt("referpackageid"));
				map.put(productid, packageid);
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getProductIDAndPackageIDByAccountID", sqle);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return map;
	}

	public static VOIPProductDTO getVOIPProductDTOByProductIDAndSCode(int id,
			String name) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		VOIPProductDTO dto = null;
		try {
			String strSql = "select * from T_SSIF_PRODUCT where PRODUCTID="
					+ id + " and SSSRVCODE='" + name + "' ";
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillVOIPProductDTO(rs, "");
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getvoipProductDTOByProductIDAndSCode", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static VOIPConditionDTO getVOIPConditionDTOByConditionID(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		VOIPConditionDTO dto = null;

		try {
			String strSql = "select * from T_SSIF_CONDITION where CONDITIONID="+ id;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillVOIPConditionDTO(rs, "");
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getVOIPConditionDTOByConditionID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static VOIPGatewayDTO getVOIPGatewayDTOByPK(String deviceModel,
			String devsType) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		VOIPGatewayDTO dto = null;
		try {
			String strSql = "select * from T_SSIF_DVESMAP where DEVICEMODEL="
					+ "'" + deviceModel + "'" + " AND DEVSTYPE='" + devsType
					+ "'";
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillVOIPGatewayDTO(rs, "");
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getVOIPGatewayDTOByPK", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static String getCustComplainStatusByPK(int custComplainId) {
		return getStringBySQL("select STATUS from T_CUSTOMERCOMPLAIN where CUSTCOMPLAINID="
					        + "'" + custComplainId + "'");
	}
	
	/**
	 * �����豸idȡ���豸��Ϣ
	 * 
	 * @param deviceID
	 * @return
	 */
	public static TerminalDeviceDTO getDeviceByDeviceID(int deviceID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TerminalDeviceDTO terminalDeviceDTO = null;
		try {
			String strSql = "select * from t_terminaldevice where DEVICEID="
					+ deviceID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				terminalDeviceDTO = DtoFiller.fillTerminalDeviceDTO(rs);
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDeviceClassBySerialNo", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return terminalDeviceDTO;
	}

	// ֻ���һ��csiID��Ӧһ��serviceID��������÷�����������첻�㣬�ȴ��Ժ���ƣ�
	public static Collection getReferServiceIDByCsiID(int csiID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection referServiceIDCol = new ArrayList();
		try {
			String strSql = "select distinct(t.referserviceaccountid) from t_csicustproductinfo t where t.referserviceaccountid<>0 and t.csiid="
					      + csiID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next())
				referServiceIDCol.add(new Integer(rs
						.getInt("referserviceaccountid")));
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getReferServiceIDByCsiID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return referServiceIDCol;
	}

	/**
	 * ͨ����Ʒid����������ȡ�ò�Ʒ���������Ĳ�Ʒ��ϵ�б�
	 * 
	 * @param productID
	 * @param type
	 * @return
	 */
	public static Collection getProdDependByProdIdAndType(int productID,
			String type) throws ServiceException {
		if (productID == 0)
			return null;
		if (type == null || "".equals(type))
			throw new ServiceException("��Ʒ��ϵ�������δ֪��");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection returnCol = new ArrayList();

		try {
			String strSql = "Select * From T_ProductDependency Where productid="
					+ productID;
			if (type.indexOf(",") > 0)
				strSql = strSql + " And Type in (" + type + ") ";
			else
				strSql = strSql + " And Type='" + type + "'";

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next())
				returnCol.add(DtoFiller.fillProductDependencyDTO(rs));
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProdDependByProdIdAndType", e);
			throw new ServiceException("��Ʒ������ϵ���ݲ��Ҵ���");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return returnCol;
	}

	/**
	 * ͨ���豸���к�
	 * 
	 * @param serilaNO
	 * @param deviceClass
	 * @return
	 * @throws ServiceException
	 */
	public static TerminalDeviceDTO getDeviceBySerialNo(String serilaNO)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TerminalDeviceDTO terminalDeviceDTO = null;
		try {
			String strSql = "Select * From T_TerminalDevice Where SERIALNO='"
					      + serilaNO + "'";

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next())
				terminalDeviceDTO = DtoFiller.fillTerminalDeviceDTO(rs);
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDeviceBySerialNoAndDeviceClass", e);
			throw new ServiceException("�ն��豸���ݲ��Ҵ���");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		if (terminalDeviceDTO == null)
			throw new ServiceException("���кţ�" + serilaNO + "���Ҳ�����Ӧ���豸��");
		return terminalDeviceDTO;
	}

	/**
	 * ���ݲ�Ʒid���Ҳ�Ʒ�豸ӳ���ϵ
	 * 
	 * @param productID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getDevToProdByProductId(int productID)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection returnCol = new ArrayList();
		
		try {
			String strSql = "Select * From T_DeviceMatchToProduct Where productid="
					+ productID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()){
				returnCol.add(DtoFiller.fillDeviceMatchToProductDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDevToProdByProductId", e);
			throw new ServiceException("��Ʒ���豸����ӳ���ϵ���Ҵ���");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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

		return returnCol;
	}

	/**
	 * ���ݿͻ�Ͷ�ߴ����¼idȡ�ÿͻ�id
	 * 
	 * @param custComplainID
	 * @return
	 */
	public static int getCustomerIDBycustComplainID(int custComplainID) {
		return getIntBySQL(" select customerid from T_CUSTOMERCOMPLAIN  WHERE CUSTCOMPLAINID= "
					      + custComplainID);
	}

	/**
	 * ȡ�����õ����ʼ���
	 */
	public static String getSetOffLevel() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String setOffLevel = CommonConstDefinition.SETOFF_LEVEL_I;
		String strSql = "SELECT SetOffLevel From T_FinancialSetting";

		try {
			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
					"getSetOffLevel", strSql);
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				setOffLevel = rs.getString("SetOffLevel");
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getSetOffLevel", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return setOffLevel;
	}

	public static Map getOrgAndSubOrgConfig() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedHashMap listConfig = new LinkedHashMap();
		String sql = "select orgtype,suborgtype from T_OrgAndSubOrgConfig order by orgtype";

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			String oldOrgType = "";
			String oldSubOrgType = "";
			while (rs.next()) {
				String newOrgType = rs.getString("orgtype");
				String newSubOrgType = rs.getString("suborgtype");
				// �����ǰKEY������KEY���,���VALUEƴ������
				if (oldOrgType.equalsIgnoreCase(newOrgType)) {
					oldSubOrgType += newSubOrgType;
				} else {
					listConfig.put(oldOrgType, oldSubOrgType);
					oldOrgType = newOrgType;
					oldSubOrgType = newSubOrgType;
				}
			}
			listConfig.put(oldOrgType, oldSubOrgType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG, "��ʼ����֯��ϵ"
				+ listConfig);
		return listConfig;
	}
	/**
	 * @param orgID
	 * @return ���ݵ�ǰ����Ա����֯idͨ���ݹ�ķ�ʽ�ҵ��ܹ�����ͻ�����֯id
	 */
	public static int getParentHasCustomerOrgID(int orgID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int currenthasCustomerOrgID = 0;
		int currentParentOrgID = 0;
		String haveCustomerFlag = "";
		try {
			String strSql ="SELECT ORGID,PARENTORGID,HASCUSTOMERFLAG FROM T_ORGANIZATION WHERE STATUS='V'and ORGID="
						  + orgID;

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				currentParentOrgID = rs.getInt("PARENTORGID");
				haveCustomerFlag = rs.getString("HASCUSTOMERFLAG");
				if (!"Y".equals(haveCustomerFlag)) {
					currenthasCustomerOrgID = getParentHasCustomerOrgID(currentParentOrgID);
				} else {
					currenthasCustomerOrgID = orgID;
				}
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getParentHasCustomerOrgID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currenthasCustomerOrgID;
	}

	/**
	 * 
	 * �����豸���ͻ�ø��豸���͵ĳ���
	 */
	public static int getDeviceProviderByDeviceModel(String deviceModel) {

		return getIntBySQL("select  t.providerid from t_devicemodel t where t.devicemodel='"
				+ deviceModel + "'");
	}

	private static double getdoubleBySQL(String sql) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		double f = Double.MIN_VALUE;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				f = rs.getDouble(1);
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getdoubleBySQL", e);
			f = Double.MIN_VALUE;
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getdoubleBySQL", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getdoubleBySQL", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getdoubleBySQL", e);
				}
			}
		}
		return f;
	}

	/**
	 * ȡ��һ���ʻ�������δ���ʷ����ܺ�
	 * 
	 * @param accountID
	 * @return
	 */
	public static double getNoInvoicedAccountItemByAccountID(int accountID) {
		return getdoubleBySQL("select sum(value) from t_accountitem "
				+ " where status<>'I' and invoicedflag='N' and acctid="
				+ accountID);
	}

	/**
	 * ȡ��һ���ʻ�������δ����֧�������ܺ�,�������ͻ�Ԥ����
	 * 
	 * @param accountID
	 * @return
	 */
	public static double getNoInvoicedPaymentRecordByAccountID(int accountID) {
		return getdoubleBySQL("select sum(amount) from t_paymentrecord "
				+ " where status<>'I' and invoicedflag='N' and paytype <>'P' and acctid="
				+ accountID);
	}

	/**
	 * ȡ��һ���ʻ�������δ����Ԥ��ֿ۷����ܺ�
	 * 
	 * @param accountID
	 * @return
	 */
	public static double getNoInvoicedPrePaymentDeductionRecordByAccountID(
			int accountID) {
		return getdoubleBySQL("select sum(amount) from t_prepaymentdeductionrecord "
				+ " where status<>'I' and invoicedflag='N' and acctid="
				+ accountID);
	}

	private static int getIntBySQL(String sql) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		int i = Integer.MIN_VALUE;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "getIntBySQL",
					e);
			i = Integer.MIN_VALUE;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getIntBySQL", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getIntBySQL", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getIntBySQL", e);
				}
			}
		}
		return i;
	}

	private static BigDecimal getBigDecimalBySQL(String sql) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		BigDecimal i = BigDecimal.valueOf(0l);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				i = rs.getBigDecimal(1);
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getBigDecimalBySQL", e);
			i = BigDecimal.valueOf(0l);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getBigDecimalBySQL", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getBigDecimalBySQL", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getBigDecimalBySQL", e);
				}
			}
		}
		return i;
	}

	private static Map getMapBySQL(String sql) {
		return getMapBySQL(sql, true);
	}

	private static Map getMapBySQL(String sql, boolean flag) {
		return getMapBySQL(sql, flag, false);
	}

	private static Map getMapBySQL(String sql, boolean flag, boolean order) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map map = null;

		if (order)
			map = new LinkedHashMap();
		else
			map = new HashMap();

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (flag) {
					map.put(rs.getString(1), rs.getString(2));
				} else {
					map.put(new Integer(rs.getInt(1)), rs.getString(2));
				}

			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "getMapBySQL",
					e);
			map.clear();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getMapBySQL", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getMapBySQL", e);
				}
			}
		}
		return map;
	}

	/**
	 * ������е��豸����/�豸�������ƵĻ���
	 * 
	 * @return map
	 */
	public static Map getDeiceClassMop() {
		Map deviceClassCache = new LinkedHashMap();
		// String sql = "select deviceclass,deviceclass from t_deviceclass";
		// update by chaoqiu on 2007-04-23 �޸ĵõ��豸���ƵĴ��� ������ʾ������Ϣ
		String sql = "select deviceclass,Description from t_deviceclass";
		deviceClassCache = getMapBySQL(sql);
		return deviceClassCache;
	}

	public static String getCommonNameByKey(String cacheName, String key) {
		String sql = "select value from t_commonsettingdata t where t.name ='"
				+ cacheName + "' and key='" + key + "'";
		return getStringBySQL(sql);
	}

	public static String getServiceAccountStatusBySAID(int said) {
		if (said == 0)
			return null;
		String sql = "select status from t_serviceaccount where serviceaccountid="
				+ said;
		return getStringBySQL(sql);
	}

	private static String getStringBySQL(String sql) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String str = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				str = rs.getString(1);
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getStringBySQL", e);
			str = null;
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
					LogUtility.log(BusinessUtility.class, LogLevel.WARN, "getStringBySQL",
							e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getStringBySQL", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.WARN,
							"getStringBySQL", e);
				}
			}
		}
		return str;
	}

	/**
	 * ������Ŀ����idȡ�ö�Ӧ�ķ�������
	 * 
	 * @param itemTypeID
	 * @return
	 * @throws ServiceException
	 */
	public static String getFeeTypeByItemTypeID(String itemTypeID)
			throws ServiceException {
		String feeTpye = null;
		feeTpye = getStringBySQL("Select FeeType From t_acctitemtype Where acctItemTypeID='"
				+ itemTypeID + "'");
		if (feeTpye == null || "".equals(feeTpye))
			throw new ServiceException("���õķ������Ͳ���Ϊ��");
		return feeTpye;
	}

	/**
	 * ��,����acctitemtypeidȡ�÷�����������
	 * 
	 * @param acctItemTypeID
	 * @return
	 */
	public static String getAcctItemTypeName(String acctItemTypeID) {
		return getStringBySQL("select acctitemtypename from t_acctitemtype where acctitemtypeid='"
				+ acctItemTypeID + "'");
	}

	/**
	 * �����ʵ������ɽ�
	 * @author Stone Liang
	 * @param dueDate �ʵ�������
	 * @param value   �ʵ�Ӧ�����
	 * @return ���ɽ�
	 */
	public static double getPunishmentValue(java.sql.Timestamp dueDate,
			double value)throws ServiceException {
		double punishmentValue = 0;
		int startDays = 0;
		double rate = 0;
		String punishmentFlag ="";
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT calculatepunishmentflag,punishmentStartDate,punishmentRate FROM T_financialsetting");
			if (rs.next()) {
				startDays = rs.getInt("punishmentStartDate");
				rate = rs.getDouble("punishmentRate");
				punishmentFlag =rs.getString("calculatepunishmentflag");
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "getPunishmentValue",
					e);
			throw new ServiceException("�������ɽ�ʱ��������");
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
		if ("Y".equals(punishmentFlag)){
		   punishmentValue = value
				* ((TimestampUtility.getCurrentDateWithoutTime().getTime() - dueDate
						.getTime())
						/ (1000 * 3600 * 24) - startDays) * rate;
		   punishmentValue = (Math.round(punishmentValue * 100)) / 100.0f;
		   if (punishmentValue < 0)
			  punishmentValue = 0;
		}
		
		return  (double)(Math.floor(punishmentValue*100)/100);   
	}
	/**
	 * �����ʵ����ɽ�ļƷ�����id
	 * 
	 * @author Yanchun Li
	 * @param invoiceCycleID
	 *            �ʵ���
	 * @return �Ʒ�����id
	 */
	public static int getPunishmentBillingCycleID(int invoiceCycleID)
			throws ServiceException {
		int billingCycleID = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select  BILLINGCYCLEID from T_Bc2IC Where INVOICECYCLEID=?");
			stmt.setInt(1, invoiceCycleID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				billingCycleID = rs.getInt("BILLINGCYCLEID");
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getPunishmentValue", e);
			throw new ServiceException("�������ɽ�Ʒ�����ʱ��������");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return billingCycleID;
	}

	/**
	 * ȡ�ÿͻ�������ص�ַ,����,��һЩ������������,Ŀǰ���ڿͻ��޸���־.
	 * 
	 * @param customerid
	 * @return
	 */
	public static CustomerInfoWrap getCustomerInfoWrapByID(int customerid) {
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		CustomerInfoWrap wrap = new CustomerInfoWrap();

		try {

			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(CommonUtility.getSelectExpress4CustomerInfo("cust."))
					.append(",");
			sql.append(CommonUtility.getSelectExpression4Address("addr."))
					.append(",");
			sql.append(CommonUtility.getSelectExpress4DistrictInfo("dist."))
					.append(",");
			sql.append("com1.value customerType,");
			sql.append("com2.value customerCardType");

			sql
					.append(
							" from t_customer cust join t_address addr on cust.addressid = addr.addressid")
					.append(
							" join t_districtsetting dist on addr.districtid = dist.id")
					.append(
							" join t_commonsettingdata com1 on (com1.name = 'SET_C_CUSTOMERTYPE' and com1.key = cust.customertype)")
					.append(
							" join t_commonsettingdata com2 on (com2.name = 'SET_C_CUSTOMERCARDTYPE' and com2.key = cust.cardtype)")
					.append(" where cust.customerid=?");

			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG, sql
					.toString());

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, customerid);
			rs = stmt.executeQuery();
			if (rs.next()) {
				wrap.setCustDto(DtoFiller.fillCustomerDTO(rs, "cust_"));
				wrap.setAddrDto(DtoFiller.fillAddressDTO(rs, "addr_"));
				wrap.setDistrictSettingDTO(DtoFiller.fillDistrictSettingDTO(rs,
						"dist_"));
				wrap.setCustomerTypeDesc(rs.getString("customerType"));
				wrap.setCustomerCardTypeDesc(rs.getString("customerCardType"));
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getCustomerInfoWrapByID", e);

		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return wrap;
	}

	/**
	 * ȡ���ʻ���ط�װ��Ϣ,
	 * 
	 * @param accountid
	 * @return
	 */
	public static Account2AddressWrap getAccountInfoWrapByID(int accountid) {
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		Account2AddressWrap wrap = new Account2AddressWrap();

		try {

			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(CommonUtility.getSelectExpress4AccountInfo("acct."))
					.append(",");
			sql.append(CommonUtility.getSelectExpression4Address("addr."))
					.append(",");
			sql.append("dist.name distName,");
			sql.append("mop.name mopName");

			sql.append(" from t_account acct")
			   .append(" join t_address addr on acct.addressid = addr.addressid")
			   .append(" join t_districtsetting dist on addr.districtid = dist.id")
			   .append(" join t_methodofpayment mop on acct.mopid=mop.mopid ")
			   .append(" where acct.accountid=?");

			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG, sql
					.toString());

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, accountid);
			rs = stmt.executeQuery();
			if (rs.next()) {
				wrap.setAcctDto(DtoFiller.fillAccountDTO(rs, "acct_"));
				wrap.setAddrDto(DtoFiller.fillAddressDTO(rs, "addr_"));
				wrap.setDistrictName(rs.getString("distName"));
				wrap.setMethodOfPaymentName(rs.getString("mopName"));
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getAccountInfoWrapByID", e);

		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return wrap;
	}

	public static String getDistrictNameById(int id) {
		return getStringBySQL("select name from t_districtsetting where id ="
				+ id);
	}

	public static int getCountByCode(String code) {
		return getIntBySQL("select count(*) from T_MACHINEROOM where MACHINEROOMCODE ='"
				+ code + "'");
	}

	public static int getCountByFiberCode(String code) {
		return getIntBySQL("select count(*) from T_FIBERTRANSMITTER where FIBERTRANSMITTERCODE ='"
				+ code + "'");
	}

	public static int getCountByFiberRecieverCode(String code) {
		return getIntBySQL("select count(*) from T_FIBERRECEIVER where FIBERRECEIVERCODE ='"
				+ code + "'");
	}

	public static int getCountByFiberNodeCode(String code) {
		return getIntBySQL("select count(*) from T_FIBERNODE where FIBERNODECODE ='"
				+ code + "'");
	}

	public static String getMethodOfPaymentNameById(int id) {
		return getStringBySQL("select name from t_methodofpayment where mopid="
				+ id);
	}

	public static String getCustomerNameById(int id) {
		return getStringBySQL("select name from t_customer where customerid="
				+ id);
	}

	public static String getCampaignNameByCCId(int ccid) {
		return getStringBySQL("select c.campaignname from t_campaign c join t_customercampaign cc on c.campaignid=cc.campaignid where cc.ccid="
				+ ccid);
	}

	public static String getPaymentMethedByBundleIdAndFlag(int id, String flag) {
		return getStringBySQL("select c.RfBillingCycleFlag from t_bundlepaymentmethod c  where c.bundleid="
				+ id + " and c.RfBillingCycleFlag='" + flag + "'");
	}

	/**
	 * ����psid����ȡ������Ϣ.����PSID(��Ʒ����)
	 * 
	 * @param psidCol
	 * @return
	 * @throws ServiceException
	 */
	public static String getProductDescListByPSIDList(Collection psidCol)
			throws ServiceException {
		if (psidCol == null || psidCol.isEmpty())
			return "";
		StringBuffer psids = new StringBuffer();
		for (Iterator it = psidCol.iterator(); it.hasNext();) {
			psids.append(it.next());
			if (it.hasNext())
				psids.append(",");
		}
		return getProductDescListByPSIDList(psids.toString());
	}

	/**
	 * ����һ��psid����ȡһ������Ʒ���ơ���Ʒ�ͺźͲ�Ʒ���кŵ��������ϡ����ڲ�Ʒȡ��ʱ��ϵͳ��־��¼��
	 * 
	 * @param psidlist
	 *            ��","�ŷָ�
	 * @return
	 * @throws ServiceException
	 * @throws ServiceException
	 */
	public static String getProductDescLogListByPSIDList(String psidlist)
			throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer desc = new StringBuffer();
		if (psidlist.endsWith(","))
			psidlist = psidlist.substring(0, psidlist.length() - 1);
		String sql = "select cp.psid psid,pro.productname,pro.productid productid,device.devicemodel devicemodel,terminal.serialno serialno from t_customerproduct cp join t_product pro on cp.productid = pro.productid left join t_devicematchtoproduct device on device.productid=pro.productid left join t_terminaldevice terminal on terminal.deviceid=cp.deviceid  where psid in ("
				+ psidlist + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int psid = rs.getInt("psid");
				String productName = rs.getString("productName");
				desc.append("(psid:");
				desc.append(psid);
				desc.append(")");
				desc.append(productName);
				desc.append(",");
				String devicemodel = rs.getString("devicemodel");
				String serialno = rs.getString("serialno");
				if (devicemodel != null && !"".equals(devicemodel)) {
					desc.append("�豸�ͺ�:");
					desc.append(devicemodel + ",");
				}
				if (serialno != null && !"".equals(serialno)) {
					desc.append("���к�:");
					desc.append(serialno + ",");
				}
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductDescListByPSIDList", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return desc.substring(0, desc.length() - 1);
	}

	/**
	 * ����һ��psid����ȡһ������Ʒ���Ƶ���������
	 * 
	 * @param psidlist
	 *            ��","�ŷָ�
	 * @return
	 * @throws ServiceException
	 * @throws ServiceException
	 */
	public static String getProductDescListByPSIDList(String psidlist)
			throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer desc = new StringBuffer();
		if (psidlist.endsWith(","))
			psidlist = psidlist.substring(0, psidlist.length() - 1);
		String sql = "select cp.psid psid,pro.productname productName from t_customerproduct cp join t_product pro on cp.productid = pro.productid where psid in ("
				+ psidlist + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int psid = rs.getInt("psid");
				String productName = rs.getString("productName");
				desc.append("(psid:");
				desc.append(psid);
				desc.append(")");
				desc.append(productName);
				desc.append(",");
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductDescListByPSIDList", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return desc.substring(0, desc.length() - 1);
	}

	/**
	 * ���ݻ���id/����id�ж������ӻ���/����
	 * 
	 * @param id
	 *            ����id/����id
	 * @param type
	 *            ���ͱ�־ ����--"org";����--"dist"
	 * @return �з��� true;�޷���false
	 */
	public static boolean hasSon(String id, String type) {
		boolean hasSon = true;
		String strSql = null;
		if ("org".equals(type))
			strSql = "select count(*) COUNT from t_organization where parentorgid="
					+ id;
		else if ("dist".equals(type))
			strSql = "select count(*) COUNT from t_districtsetting where belongto="
					+ id;
		int count = getIntBySQL(strSql);
		if (count == 0) {
			hasSon = false;
		}
		return hasSon;
	}

	public static String getProductDesByProductID(Collection productidCol) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer cpList = new StringBuffer();
		Iterator itr = productidCol.iterator();
		String idList = "";
		while (itr.hasNext()) {
			Collection productList = (Collection) itr.next();
			for (Iterator ite = productList.iterator(); ite.hasNext();) {
				int productId = ((Integer) (ite.next())).intValue();
				idList += productId;
				idList += ",";
			}
		}
		idList = idList.substring(0, idList.length() - 1);
		String sql = "select productid,productname from t_product where productid in ("
				+ idList + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cpList.append("(");
				cpList.append(rs.getInt("productid"));
				cpList.append(")");
				cpList.append(rs.getString("productname"));
				cpList.append(",");
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductDesByProductID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return cpList.substring(0, cpList.length() - 1);
	}

	public static Collection getCsiCustProductInfoIdByCSIID(int csiid) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection ccpidCol = new ArrayList();
		String sql = "select id from t_csicustproductinfo where csiid=" + csiid;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ccpidCol.add(new Integer(rs.getInt("id")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCsiCustProductInfoIdByCSIID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return ccpidCol;
	}

	public static String getCustomerDeviceProductInfoBySaID(int saID) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String deviceProductInfoStr = "";
		String sql = "select t.devicemodel,t.serialno from t_terminaldevice t,t_customerproduct p ";
		sql = sql + " where p.serviceaccountid =" + saID
				+ " and t.deviceid =p.deviceid and p.deviceid <>0 ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (deviceProductInfoStr.equals("")) {
					deviceProductInfoStr = "�漰�����豸��";
				}
				deviceProductInfoStr = deviceProductInfoStr + "�豸����-->"
						+ rs.getString("devicemodel") + "; " + "�豸���к�-->"
						+ rs.getString("serialno") + ";";

			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustomerDeviceProductInfoBySaID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return deviceProductInfoStr;
	}

	public static String getCommomSettingValue(String name, String key) {
		if (name == null || "".equals(name) || key == null || "".equals(key))
			return null;

		return getStringBySQL("select value from t_commonsettingdata where name='"
				+ name + "' and key='" + key + "' ");
	}

	/**
	 * �����������ʹ�t_csipaymentsettingȡ�ñ��������Ƿ���Ҫ����֧�����õ�����
	 * 
	 * @param csiType
	 * @return
	 */
	public static String getCsiPaymentOption(String csiType) {
		return getStringBySQL("select csipaymentoption from t_csipaymentsetting  Where csitype='"
				+ csiType + "'");
	}

	/**
	 * �õ�����������빺���Ʒ��������DTOList
	 * 
	 * @param campaignID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCampaignCondPackageDTOListByCampaignID(
			int campaignID) throws ServiceException {
		if (campaignID < 1)
			return null;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection campaignCondPackageDTOList = new ArrayList();

		String sql = "select * from T_Campaign_Cond_Package where CampaignID="
				+ campaignID;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				campaignCondPackageDTOList.add(DtoFiller
						.fillCampaignCondPackageDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignCondPackageDTOListByCampaignID", e);
			throw new ServiceException("���Ҵ��������Ӧ�İ�����");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return campaignCondPackageDTOList;
	}

	/**
	 * �õ�����������빺���Ʒ������DTOList
	 * 
	 * @param campaignID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCampaignCondProductDTOListByCampaignID(
			int campaignID) throws ServiceException {
		if (campaignID < 1)
			return null;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection campaignCondPackageDTOList = new ArrayList();

		String sql = "select * from T_Campaign_Cond_Product where CampaignID="
				+ campaignID;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				campaignCondPackageDTOList.add(DtoFiller
						.fillCampaignCondProductDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignCondProductDTOListByCampaignID", e);
			throw new ServiceException("���Ҵ��������Ӧ�Ĳ�Ʒ����");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return campaignCondPackageDTOList;
	}

	/**
	 * �õ�����������빺�����������DTOList
	 * 
	 * @param campaignID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCampaignCondCampaignDTOListByCampaignID(
			int campaignID) throws ServiceException {
		if (campaignID < 1)
			return null;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection campaignCondPackageDTOList = new ArrayList();

		String sql = "select * from T_Campaign_Cond_Campaign where CampaignID="
				+ campaignID;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				campaignCondPackageDTOList.add(DtoFiller
						.fillCampaignCondCampaignDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignCondCampaignDTOListByCampaignID", e);
			throw new ServiceException("���Ҵ��������Ӧ�Ĵ�������");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return campaignCondPackageDTOList;
	}

	/**
	 * ���ݿͻ�ID���ʻ��š�ҵ���ʺŵõ��ͻ������е���Ч�Ż�����ID�б�
	 * 
	 * @param customerID
	 * @param accountID
	 * @param saID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCustCampaignIDListByCustInfo(int customerID,
			int accountID, int saID) throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection campaignIDList = new ArrayList();
		// ���û��ҵ���ʻ�ID�����Ļ���ֱ�ӷ��ؿռ���
		if (saID == 0) {
			return campaignIDList;
		}
		StringBuffer sql = new StringBuffer(
				"select campaignid from t_customercampaign where status='"
						+ CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL
						+ "' ");
		if (customerID > 0)
			sql.append(" and customerid=" + customerID);
		if (accountID > 0)
			sql.append(" and accountid=" + accountID);
		if (saID > 0)
			sql.append(" and serviceaccountid=" + saID);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				campaignIDList.add(new Integer(rs.getInt("campaignid")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustCampaignIDListByCustInfo", e);
			throw new ServiceException("���ҿͻ����е��Żݳ���");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return campaignIDList;
	}

	/**
	 * ���ݿͻ�ID���ʻ��š�ҵ���ʺ�,�����ŵõ��ͻ������е���Ч���ײͻ��ߴ�������
	 * 
	 * @param customerID
	 * @param accountID
	 * @param saID
	 * @param csiID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCustCpListByCustInfo(int customerID,
			int accountID, int saID, int csiID) throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection campaignList = new ArrayList();

		StringBuffer sql = new StringBuffer(
				"select * from t_customercampaign where status='"
						+ CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL
						+ "'");
		if (customerID > 0)
			sql.append(" and customerid=" + customerID);
		if (accountID > 0)
			sql.append(" and accountid=" + accountID);
		if (saID > 0)
			sql.append(" and serviceaccountid=" + saID);
		if (csiID > 0)
			sql.append(" and csiID=" + csiID);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				campaignList.add(DtoFiller.fillCustomerCampaignDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustCpListByCustInfo", e);
			throw new ServiceException("���ҿͻ����е��Żݳ���");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return campaignList;
	}

	/**
	 * ���ݿͻ�ID���ʻ��š�ҵ���ʺŵõ��ͻ������еĲ�Ʒ����ID�б�
	 * 
	 * @param customerID
	 * @param accountID
	 * @param saID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCustProductIDListByCustInfo(int customerID,
			int accountID, int saID) throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection productIDList = new ArrayList();
		// ���û��ҵ���ʻ�ID�����Ļ���ֱ�ӷ��ؿռ���
		if (saID == 0) {
			return productIDList;
		}
		StringBuffer sql = new StringBuffer(
				"select productid from t_customerproduct where status<>'"
						+ CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						+ "' ");
		if (customerID > 0)
			sql.append(" and customerid=" + customerID);
		if (accountID > 0)
			sql.append(" and accountid=" + accountID);
		if (saID > 0)
			sql.append(" and serviceaccountid=" + saID);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				productIDList.add(new Integer(rs.getInt("productid")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustProductIDListByCustInfo", e);
			throw new ServiceException("���ҿͻ����еĲ�Ʒ����");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return productIDList;
	}

	/**
	 * ���ݿͻ�ID���ʻ��š�ҵ���ʺŵõ��ͻ������еĲ�Ʒ������ID�б�
	 * 
	 * @param customerID
	 * @param accountID
	 * @param saID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCustProductPackageIDListByCustInfo(
			int customerID, int accountID, int saID) throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection productPackageIDList = new ArrayList();
		// ���û��ҵ���ʻ�ID�����Ļ���ֱ�ӷ��ؿռ���
		if (saID == 0) {
			return productPackageIDList;
		}
		StringBuffer sql = new StringBuffer(
				"select distinct(ReferPackageID) from t_customerproduct where "
						+ "status<>'"
						+ CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						+ "' and ReferPackageID>0 ");
		if (customerID > 0)
			sql.append(" and customerid=" + customerID);
		if (accountID > 0)
			sql.append(" and accountid=" + accountID);
		if (saID > 0)
			sql.append(" and serviceaccountid=" + saID);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				productPackageIDList.add(new Integer(rs
						.getInt("ReferPackageID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustProductPackageIDListByCustInfo", e);
			throw new ServiceException("���ҿͻ����еĲ�Ʒ������");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return productPackageIDList;
	}

	public static Map getCampaignAgmtProductList(int campaignID)
			throws ServiceException {
		if (campaignID < 1)
			return null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map campaignAgmtProductMap = new HashMap();

		String sql = "select productid,packageid from t_packageline "
				+ " where packageid in (select packageid from t_campaign_agmt_package where campaignid =?) "
				+ "union (select productid,0 packageid from t_campaign_agmt_product where campaignid =?)";
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, campaignID);
			pstmt.setInt(2, campaignID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				campaignAgmtProductMap.put(new Integer(rs.getInt(1)),
						new Integer(rs.getInt(2)));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignAgmtProductList", e);
			throw new ServiceException("���Ҵ��������Ӧ�İ�����");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
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
		return campaignAgmtProductMap;
	}

	/**
	 * �õ��������Ӧ��Э���Ʒ��id�б�
	 * 
	 * @param campaignID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCampaignAgmtPackageListByCampaignID(
			int campaignID) throws ServiceException {
		if (campaignID < 1)
			return null;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection packageIdList = new ArrayList();

		String sql = "Select  PackageID From T_Campaign_Agmt_Package where CampaignID="
				+ campaignID;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				packageIdList.add(new Integer(rs.getInt("PackageID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignAgmtPackageListByCampaignID", e);
			throw new ServiceException("����Э���Ʒ������");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return packageIdList;
	}

	/**
	 * �õ��������Ӧ��Э�����id�б�
	 * 
	 * @param campaignID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCampaignAgmtCampaignListByCampaignID(
			int campaignID) throws ServiceException {
		if (campaignID < 1)
			return null;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection campaignIdList = new ArrayList();

		String sql = "Select  TargetBundleID From T_Campaign_Agmt_Campaign where CampaignID="
				+ campaignID;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				campaignIdList.add(new Integer(rs.getInt("TargetBundleID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCampaignAgmtCampaignListByCampaignID", e);
			throw new ServiceException("����Э���������");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return campaignIdList;
	}

	/**
	 * ���ݴ�����IDȡ�ö�Ӧ�Ĳ�Ʒid�б�
	 * 
	 * @param accountID
	 * @param status
	 * @return
	 */
	public static List getPsIDListByCcID(int CCID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList psIDList = new ArrayList();
		String strSql = "select CustProductID from T_CPCampaignMap where CCID="
				      + CCID;
		try {
			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
					"getPsIDListByCcID", strSql);
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				psIDList.add(new Integer(rs.getInt("CustProductID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getPsIDListByCcID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return psIDList;
	}

	/**
	 * ��һ�������б����ҳ��ײ�Ԥ���Ѽƻ��Ķ������.
	 * 
	 * @param campaignList
	 * @return ����һ��Map����,keyΪ(Integer)campaignID,valueΪArrayList,list�洢����campaignID��Ӧ��bundleprepaymentDTO
	 * @throws ServiceException
	 */

	public static Map getBundlePrePaymentMapWithCampaignList(
			Collection campaignList) throws ServiceException {
		Map bundlePrePaymentMap = new HashMap();
		if (campaignList == null || campaignList.isEmpty())
			return bundlePrePaymentMap;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select * from t_bundleprepayment bp where bp.campaignid in (";
		for (Iterator campaignIt = campaignList.iterator(); campaignIt
				.hasNext();) {
			Integer campaignID = (Integer) campaignIt.next();
			sql += campaignID.intValue();
			if (campaignIt.hasNext())
				sql += ",";
		}
		sql += ")";
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
				"getBundlePrePaymentWithCampaignList:", sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BundlePrepaymentDTO dto = DtoFiller.fillBundlePrepaymentDTO(rs);
				Integer targetBundleID = new Integer(dto.getTargetBundleId());
				if (bundlePrePaymentMap.containsKey(targetBundleID)) {
					((ArrayList) bundlePrePaymentMap.get(targetBundleID))
							.add(dto);
				} else {
					ArrayList prePaymentList = new ArrayList();
					prePaymentList.add(dto);
					bundlePrePaymentMap.put(targetBundleID, prePaymentList);
				}
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getBundlePrePaymentWithCampaignList", e);
			throw new ServiceException("���Ҵ�����Ӧ��Ԥ���Ѽƻ�ʵ�����");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return bundlePrePaymentMap;
	}

	/**
	 * ҵ���˻�ID���߿ͻ���Ʒid��ȡ�ĸ�ҵ���˻������еĲ�Ʒ����
	 * 
	 * @param serviceAccountID
	 *            ҵ���ʻ�id
	 * @param psid
	 *            �ͻ���Ʒid
	 * @param condstr
	 *            �����ַ���,�����sql�����ַ���
	 * @return
	 * @throws ServiceException
	 */
	public static ArrayList getCustProdDTOListBySaAndPsID(int serviceAccountID,
			int psid, String condstr) throws ServiceException {
		ArrayList currentDataList = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String strSql = "SELECT * FROM T_CUSTOMERPRODUCT  WHERE "
					+ "Status<>'"
					+ CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
					+ "'";
			if (serviceAccountID != 0)
				strSql += " and SERVICEACCOUNTID= " + serviceAccountID;
			if (psid != 0)
				strSql += " and PSID= " + psid;
			if (condstr != null && !"".equals(condstr))
				strSql += " and  " + condstr;
			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
					"getCustProdDTOListBySAID", strSql);
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				currentDataList.add(DtoFiller.fillCustomerProductDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustProdDTOListBySAID", e);
			throw new ServiceException("����ҵ���ʻ�ID���ƷIDȡ�ò�Ʒ��Ϣ�Ǵ���");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentDataList;
	}

	/**
	 * ����ҵ���ʻ�idȡ���豸��Ϣ
	 * 
	 * @param serviceAccountID
	 * @return
	 * @throws ServiceException
	 */
	public static ArrayList getDeviceListBySaID(int serviceAccountID)
			throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList currentDataList = new ArrayList();
		try {
			String strSql = "SELECT DISTINCT(DEVICEID) FROM T_CUSTOMERPRODUCT  WHERE DEVICEID<>0 AND  SERVICEACCOUNTID= "
					+ serviceAccountID;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				currentDataList.add(new Integer(rs.getInt("DEVICEID")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDeviceListBySaID", e);
			throw new ServiceException("����ҵ���ʻ�ID(" + serviceAccountID
					+ ")ȡ�豸��Ϣʱ����");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return currentDataList;
	}

	/**
	 * ���ݿͻ���ѡ��Ĳ�ƷID�б�õ��ܿ�ҵ���ʻ��Ĳ�ƷID�б�
	 * ���ݲ�Ʒ�б������б��а�����ҵ������������ظ���ҵ�����׳�ServiceException�쳣 jason.zhou , date
	 * 2007-03-11
	 * 
	 * @param productCol
	 * @return�� ҵ��ID ��ɵ�set
	 * @throws ServiceException
	 */
	public static Collection getSAProductIDListFromPoductIDList(
			Collection productIDCol) throws ServiceException {
		if (productIDCol == null || productIDCol.isEmpty())
			return null;

		// ҵ�����ʹ洢����ʱ�ã��ж��Ƿ�����ظ�����ҵ������
		Set serviceIDSet = new HashSet();
		// ����ֵ�������ܴ���ҵ���ʺŵ�ProductID
		Collection saProductIDList = new ArrayList();

		String strProductIDOfAll = "";
		Iterator itProductID = productIDCol.iterator();
		while (itProductID.hasNext()) {
			Integer productID = (Integer) itProductID.next();
			if (!"".equals(strProductIDOfAll))
				strProductIDOfAll = strProductIDOfAll + ",";
			strProductIDOfAll = strProductIDOfAll + productID.toString();
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sqlOfService = "SELECT serv.serviceid FROM t_service serv, t_product prod,t_producttoservice ps "
				+ "WHERE serv.serviceid=ps.serviceid  and prod.productid=ps.productid and  prod.newsaflag='Y'"
				+ " and prod.productid in (" + strProductIDOfAll + ") ";

		String sqlOfSA = "select PRODUCTID from t_product WHERE NEWSAFLAG='Y' AND PRODUCTID IN ("
				+ strProductIDOfAll + ") ";

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sqlOfService);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Integer serviceID = new Integer(rs.getInt("serviceid"));
				if (serviceIDSet.contains(serviceID))
					throw new ServiceException("һ��ҵ�������г������λ��������ϵ���ͬҵ��");
				else
					serviceIDSet.add(serviceID);
			}

			stmt = con.prepareStatement(sqlOfSA);
			rs = stmt.executeQuery();
			while (rs.next()) {
				saProductIDList.add(new Integer(rs.getInt("PRODUCTID")));
			}

		} catch (SQLException e) {
			saProductIDList = null;
			LogUtility.log(clazz, LogLevel.ERROR, e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return saProductIDList;
	}

	/**
	 * ���ݲ�Ʒ��id��T_PackageToMarketSegment��ȡ���������г������Ĳ�Ʒ��id����
	 * 
	 * @param packageArray
	 * @return
	 */
	public static Collection getPkbyPkFromP2MS(Collection packageArray)
			throws ServiceException {
		Collection res = new ArrayList();
		if (packageArray != null && !packageArray.isEmpty()) {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql = "select PackageID from T_PackageToMarketSegment where PackageID in "
					+ packageArray.toString().replace('[', '(').replace(']',
							')');
			try {
				con = DBUtil.getConnection();
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					res.add(new Integer(rs.getInt("PackageID")));
				}
			} catch (Exception e) {
				LogUtility.log(BusinessUtility.class, LogLevel.WARN,
						"getPkbyPkFromP2MS", e);
				new ServiceException("ȡ��Ʒ�����г��������ô���");
			} finally {
				if (rs !=null) {
					try{
						rs.close();
					}catch (SQLException e){
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
		}
		return res;
	}

	/**
	 * ���ݴ���id��T_CampaignToMarketSegment��ȡ���������г������Ĵ���id����
	 * 
	 * @param campaignArray
	 * @return
	 */
	public static Collection getCPbyCPFromC2MS(Collection campaignArray)
			throws ServiceException {
		Collection res = new ArrayList();
		if (campaignArray != null && !campaignArray.isEmpty()) {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql = "select CampaignID from T_CampaignToMarketSegment where CampaignID in "
					+ campaignArray.toString().replace('[', '(').replace(']',
							')');
			try {
				con = DBUtil.getConnection();
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					res.add(new Integer(rs.getInt("CampaignID")));
				}
			} catch (Exception e) {
				LogUtility.log(BusinessUtility.class, LogLevel.WARN,
						"getCPbyCPFromC2MS", e);
				new ServiceException("ȡ�������г��������ô���");
			} finally {
				if (rs !=null) {
					try{
						rs.close();
					}catch (SQLException e){
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
		}
		return res;
	}

	/**
	 * �����ײͻ��ߴ����ļ���ȡ�ð󶨵��ײͻ��ߴ����Ĳ�Ʒ��id�ļ���
	 * 
	 * @param campaignArray
	 * @return
	 */
	public static Collection getBundle2CampaignPackageCol(
			Collection campaignArray) throws ServiceException {
		Collection res = new ArrayList();
		if (campaignArray != null && !campaignArray.isEmpty()) {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql = "select PackageID from T_Bundle2Campaign where CampaignID in "
					+ campaignArray.toString().replace('[', '(').replace(']',
							')');
			try {
				con = DBUtil.getConnection();
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					res.add(new Integer(rs.getInt("PackageID")));
				}
			} catch (Exception e) {
				LogUtility.log(BusinessUtility.class, LogLevel.WARN,
						"getBundle2CampaignPackageCol", e);
				new ServiceException("ȡ�ð󶨵��ײ͵Ĳ�Ʒ��������");
			} finally {
				if (rs !=null) {
					try{
						rs.close();
					}catch (SQLException e){
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
		}
		return res;
	}
	
	public static BundlePaymentMethodDTO getBundlePaymentMethodDTO(
			int campaignID, String rfBillingCycleFlag) {
		BundlePaymentMethodDTO methodDTO = null;
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String querySql = " select * from t_bundlepaymentmethod where bundleid = ? and rfbillingcycleflag = ?";
		LogUtility.log(clazz, LogLevel.DEBUG, "getBundlePaymentMethodDTO" + ":"
				+ campaignID + ":" + rfBillingCycleFlag + "\n", querySql);

		try {
			stmt = con.prepareStatement(querySql);
			stmt.setInt(1, campaignID);
			stmt.setString(2, rfBillingCycleFlag);
			rs = stmt.executeQuery();
			if (rs.next()) {
				methodDTO = DtoFiller.fillBundlePaymentMethodDTO(rs);
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getBundlePaymentMethodDTO", sqle);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
							"getBundlePaymentMethodDTO", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
							"getBundlePaymentMethodDTO", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
							"getBundlePaymentMethodDTO", e);
				}
			}
		}
		return methodDTO;
	}

	public static boolean getCustCampaignAndServiceAccountID(int customerID,
			int ccID) throws ServiceException {
		String sql = " select count(distinct t.serviceaccountid) from t_Customerproduct t ,t_cpcampaignmap p,t_customercampaign c"
				+ " where t.customerid ="
				+ customerID
				+ " and t.psid =p.custproductid and p.ccid =c.ccid"
				+ " and c.ccid =" + ccID;

		int serviceAcctCount = getIntBySQL(sql);
		if (serviceAcctCount > 1)
			return false;
		else
			return true;
	}

	/**
	 * ��������idȡ��������ص�ʵ�����˵Ŀͻ�����id
	 * 
	 * @param csiID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getCCIDByCsiID(int csiID) throws ServiceException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection ccidCol = new ArrayList();
		String sql = " Select ccid From T_CustomerCampaign Where csiid="
				   + csiID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ccidCol.add(new Integer(rs.getInt("ccid")));
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCCIDByCsiID", e);
			throw new ServiceException(e.toString());
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return ccidCol;

	}
	
	/**
	 * ���ݿͻ��ײ�IDȡ���ҵ���ʻ�,����ز�Ʒ, ����Map
	 * key=serviceaccountid,valuse=ArrayList(������Ӧҵ���ʻ�������λ���ײ�cpMap�еĲ�ƷDTO)
	 * 
	 * @param custCampaignID
	 * @return
	 */
	public static Map getServiceAccountMapByCustCampaignID(int custCampaignID,
			boolean isAllProduct) {
		HashMap result = new HashMap();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("select * from t_customerproduct cp");
			sql.append(" where cp.status<>'C'");
			if (isAllProduct) {
				sql.append(" and cp.serviceaccountid in ");
				sql
						.append(" (select distinct serviceaccountid from t_customerproduct where psid in ");
				sql
						.append(" (select custproductid from t_cpcampaignmap where ccid = ?))");
			} else {
				sql.append(" and cp.psid in ");
				sql
						.append(" (select custproductid from t_cpcampaignmap where ccid = ?)");
			}
			sql.append("order by cp.psid");

			LogUtility.log(clazz, LogLevel.DEBUG,
					"getServiceAccountMapByCustCampaignID:\n", sql.toString());
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, custCampaignID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CustomerProductDTO dto = DtoFiller.fillCustomerProductDTO(rs);
				Integer said = new Integer(dto.getServiceAccountID());
				if (result.containsKey(said)) {
					((ArrayList) result.get(said)).add(dto);
				} else {
					ArrayList cpList = new ArrayList();
					cpList.add(dto);
					result.put(said, cpList);
				}
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.WARN,
					"getServiceAccountMapByCustCampaignID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return result;
	}

	/*
	 * ȡ��δ���˵���Ч����
	 */
	public static Collection getValidNoInvoicedFeeList4Account(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiFeeList = new ArrayList();

		String strSql = "select * from T_ACCOUNTITEM WHERE STATUS<>'I' AND INVOICEDFLAG= 'N' AND ACCTID="
				      + accountID;
		try {
			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
					"getValidNoInvoicedFeeList4Account", strSql);

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO accountItemDTO = DtoFiller.fillAccountItemDTO(rs);
				csiFeeList.add(accountItemDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getValidNoInvoicedFeeList4Account", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiFeeList;
	}

	/*
	 * ȡ��δ���˵���ЧԤ���¼
	 */
	public static Collection getValidNoInvoicedPrePaymentRecord(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiPaymentList = new ArrayList();

		String strSql = "select * from T_PaymentRecord WHERE PayType in('P','RR') AND STATUS<>'I' AND InvoicedFlag = 'N' AND ACCTID="
				+ accountID;
		try {
			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
					"getValidNoInvoicedPayment", strSql);
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PaymentRecordDTO paymentDTO = DtoFiller
						.fillPaymentRecordDTO(rs);
				csiPaymentList.add(paymentDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getValidNoInvoicedPayment", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiPaymentList;
	}

	/*
	 * ȡ��δ���˵���Ч֧��
	 */
	public static Collection getValidNoInvoicedPayment(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiPaymentList = new ArrayList();

		String strSql = "select * from T_PaymentRecord WHERE PayType in('C','N','RF')  AND STATUS<>'I' AND InvoicedFlag = 'N' AND ACCTID="
				+ accountID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PaymentRecordDTO paymentDTO = DtoFiller
						.fillPaymentRecordDTO(rs);
				csiPaymentList.add(paymentDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getValidNoInvoicedPayment", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiPaymentList;
	}

	/*
	 * ȡ��δ���˵���Ч�ֿ�
	 */
	public static Collection getValidNoInvoicedDeduction(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection csiDeductionList = new ArrayList();

		String strSql = "select * from t_Prepaymentdeductionrecord WHERE STATUS<>'I' AND InvoicedFlag = 'N' AND ACCTID="
				      + accountID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PrepaymentDeductionRecordDTO deductionDTO = DtoFiller
						.fillPrepaymentDeductionRecordDTO(rs);
				csiDeductionList.add(deductionDTO);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getValidNoInvoicedDeduction", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return csiDeductionList;
	}

	// ȡ���˻��µ���Ч�ײ�
	public static Collection getValidCustCampaign4Account(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection ccidCol = new ArrayList();

		String strSql = " select ccid from t_Customercampaign cc,T_Campaign cp "
				+ " WHERE cc.STATUS='V' "
				+ " AND cc.campaignid = cp.campaignid "
				+ " and cp.campaigntype = 'B'"
				+ " And cc.AccountID= "
				+ accountID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int ccid = rs.getInt("CCID");
				ccidCol.add(new Integer(ccid));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getValidCustCampaign4Account", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return ccidCol;
	}

	// ȡ��ҵ���˻��µ���Ч�ײ�
	public static Collection getValidCustCampaign4SA(int saID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection ccidCol = new ArrayList();

		String strSql = " select distinct cc.ccid from  t_Customercampaign cc,T_Campaign ca,T_CPCampaignMap cm,T_CustomerProduct cp "
				+ " where cc.ccid = cm.ccid "
				+ " and cm.custproductid = cp.psid "
				+ " and cc.status = 'V' "
				+ " and cc.campaignid = ca.campaignid "
				+ " and ca.campaigntype = 'B' "
				+ " and cp.serviceAccountID ="
				+ saID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
			   int ccid = rs.getInt("CCID");
			   ccidCol.add(new Integer(ccid));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getValidCustCampaign4SA", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return ccidCol;
	}

	// ȡ���˻��µ���Ч�ͻ���Ʒ�Ҳ������ײ�
	public static Collection getValidPSIDNotRelateCampaign(int accountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection psidCol = new ArrayList();

		String strSql = " Select PSID from T_CustomerProduct cp "
				+ " where Status not in ('C', 'I') "
				+ " and (cp.psid not in (Select CustProductID from t_Cpcampaignmap) "
				+ " or psid in ( "
				+ " Select CustProductID from t_Cpcampaignmap cm, t_Customercampaign cc ,T_Campaign cam "
				+ " where cm.ccid = cc.ccid "
				+ " and cam.campaignid = cc.campaignid "
				+ " and (( cam.campaigntype = 'A' ) or "
				+ " ( cam.campaigntype = 'B' and ( "
				+ " (cp.status = 'S' or cp.status = 'H') and (cp.pauseTime < cc.datefrom or cp.pauseTime >cc.dateto) "
				+ " or(cp.status = 'N') and (cp.activatetime < cc.datefrom and cp.activatetime >cc.dateto)))))) "
				+ " AND AccountID=" + accountID;
		try {

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int psid = rs.getInt("PSID");
				psidCol.add(new Integer(psid));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getValidPSIDNotRelateCampaign", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return psidCol;
	}

	// ȡ��ҵ���˻��µ���Ч�ͻ���Ʒ�Ҳ������ײ�
	public static Collection getValidPSIDNotRelateCampaignBySA(int saID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection psidCol = new ArrayList();

		String strSql = " Select PSID from T_CustomerProduct cp "
				+ " where Status not in ('C', 'I') "
				+ " and (cp.psid not in (Select CustProductID from t_Cpcampaignmap) "
				+ " or psid in ( "
				+ " Select CustProductID from t_Cpcampaignmap cm, t_Customercampaign cc ,T_Campaign cam "
				+ " where cm.ccid = cc.ccid "
				+ " and cam.campaignid = cc.campaignid "
				+ " and (( cam.campaigntype = 'A' ) or "
				+ " ( cam.campaigntype = 'B' and ( "
				+ " (cp.status = 'S' or cp.status = 'H') and (cp.pauseTime < cc.datefrom or cp.pauseTime >cc.dateto) "
				+ " or(cp.status = 'N') and (cp.activatetime < cc.datefrom and cp.activatetime >cc.dateto)))))) "
				+ " AND ServiceAccountID=" + saID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int psid = rs.getInt("PSID");
				psidCol.add(new Integer(psid));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getValidCustCampaign4Account", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return psidCol;
	}

	/*
	 * �õ���С��δ��������������
	 */
	public static int getMinCreateInvoiceCycle() {
		return getIntBySQL("select ID from T_BillingCycle where Name = (select Min(Name) from T_BillingCycle where CType = 'I' AND Status Not in ('C','IC','X')) and CType = 'I' AND Status Not in ('C','IC','X')");
	}

	/*
	 * �����ʻ��ŵõ��ͻ���
	 */
	public static int getCustIDByAccountID(int acctID) {
		return getIntBySQL(" select CustomerID from T_Account where AccountID = "+ acctID);
	}

	/**
	 * �Ѵ����psid���ϰ�ҵ���ʻ����,
	 * 
	 * @param cpList
	 * @return map ,key=serviceaccountid,value=arraylist,��ҵ���ʻ���psid����,
	 */
	public static Map splitCustomerProductWithServiceAccount(Collection cpList) {
		Map samap = new LinkedHashMap();
		if (cpList == null || cpList.isEmpty())
			return samap;
		StringBuffer strCpList = new StringBuffer();
		for (Iterator cpit = cpList.iterator(); cpit.hasNext();) {
			strCpList.append(((Integer) cpit.next()));
			if (cpit.hasNext()) {
				strCpList.append(",");
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select psid,serviceaccountid from t_customerproduct where psid in (");
		sql.append(strCpList.toString());
		sql.append(")");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				Integer psid = new Integer(rs.getInt("psid"));
				Integer said = new Integer(rs.getInt("serviceaccountid"));
				ArrayList psidList = null;
				if (samap.containsKey(said)) {
					psidList = (ArrayList) samap.get(said);
				} else {
					psidList = new ArrayList();
					samap.put(said, psidList);
				}
				psidList.add(psid);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"splitCustomerProductWithServiceAccount", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return samap;
	}

	/*
	 * ȡ���ʻ��ϵ�Ԥ��� C �ֽ� T ����
	 */
	public static double getPreDeposit(int acctID, String sType) {
		String field = "";
		if (sType.compareTo("C") == 0)
			field = "CashDeposit";
		if (sType.compareTo("T") == 0)
			field = "TokenDeposit";
		String strSql = "select " + field
				+ " from T_Account where AccountID = " + acctID;

		double preDeposit =getdoubleBySQL(strSql);
		return preDeposit;
	}

	public static String getCashFlagByMopID(int mopID) {
		return getStringBySQL("select CashFlag from T_MethodOfPayment where MopID = "
				+ mopID);
	}

	/*
	 * ����MOPID�õ��ֽ����������� �ֽ�C �������T
	 */
	public static String getDepositFlagByMopID(int mopID) {
		String strSql = "select CashFlag from T_MethodOfPayment where MopID = "
				      + mopID;
		String sType = getStringBySQL(strSql);
		if (sType == null)
			return "T";
		if (sType.compareTo("N") == 0)
			return "T";
		return "C";
	}

	/**
	 * ������еķ���id/�������Ƶ�map
	 * 
	 * @auther chaoqiu 2007-04-10
	 * @return map
	 */
	public static Map getAllDistrictMap() {
		String sql = "select id, name from t_districtsetting";
		return getMapBySQL(sql);

	}

	public static int getAutoNextOrgID(String role, Object sheetDTO,
			String diagnosisResult, String orgSubRole) throws ServiceException {
		int nextOrgID = 0;
		String sql = "";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String troubleType = "";
		String troubleSubType = "";
		int saProductID = 0;
		String isfirst = "";
		String dResult = "";
		String subrole = "";

		int currentPower = 0;
		int curServiceOrgID = 0;

		String troubleType_DTO = null;
		String troubleSubType_DTO = null;
		int sapid = 0;

		if (CommonConstDefinition.ROLEORGANIZATIONORGROLE_REPAIRE.equals(role)
				|| CommonConstDefinition.ROLEORGNIZATIONORGROLE_SERVICE
						.equals(role)) {
			CustomerProblemDTO cpDto = (CustomerProblemDTO) sheetDTO;
			sql = "select * from t_roleorganization where orgrole='"
					+ role
					+ "'"
					+ " and districtid in (select id from t_districtsetting start with id in"
					+ " (select districtid from t_address where addressid="
					+ cpDto.getAddressID() + ") connect by prior belongto=id)";

			sapid = BusinessUtility.getSAProductIDBySAID(cpDto
					.getCustServiceAccountID());
			troubleType_DTO = cpDto.getProblemLevel();
			troubleSubType_DTO = cpDto.getProblemCategory();
		} else if (CommonConstDefinition.ROLEORGNIZATIONORGROLE_TS.equals(role)) {
			CustomerComplainDTO ccDto = (CustomerComplainDTO) sheetDTO;
			sql = "select * from t_roleorganization where orgrole='"
					+ role
					+ "'"
					+ " and districtid in (select id from t_districtsetting start with id in"
					+ "(select districtid from t_address where addressid="
					+ "(select addressid from t_customer where customerid="
					+ ccDto.getCustomerId()
					+ ")) connect by prior belongto=id)";
		} else if (CommonConstDefinition.ROLEORGANIZATIONORGROLE_M.equals(role)) {
			int districtid = Integer.parseInt(sheetDTO + "");
			sql = "select * from t_roleorganization where orgrole='"
					+ role
					+ "'"
					+ " and districtid in (select id from t_districtsetting start with id ="
					+ districtid + " connect by prior belongto = id)";
		}
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
				"getAutoNextOrgID.sql:::", sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			int mostPerfectPower = 0;
			int mostPerfectValue = 0;
			while (rs.next()) {
				currentPower = 0;
				curServiceOrgID = rs.getInt("serviceorgid");
				troubleType = rs.getString("troubletype");
				troubleSubType = rs.getString("troublesubtype");
				isfirst = rs.getString("isfirst");
				saProductID = rs.getInt("saproductid");
				dResult = rs.getString("diagnosisresult");
				subrole = rs.getString("orgsubrole");

				if (subrole != null && orgSubRole != null
						&& subrole.equals(orgSubRole)) {
					currentPower += 32;
				} else if (subrole != null)
					continue;
				if (troubleType != null && troubleType_DTO != null
						&& troubleType.equals(troubleType_DTO)) {
					currentPower += 16;
				} else if (troubleType != null)
					continue;
				if (troubleSubType != null && troubleSubType_DTO != null
						&& troubleSubType.equals(troubleSubType_DTO)) {
					currentPower += 8;
				} else if (troubleSubType != null)
					continue;
				if (saProductID > 0 && sapid > 0 && saProductID == sapid) {
					currentPower += 4;
				} else if (saProductID > 0 && saProductID != sapid) {
					continue;
				}
				if (dResult != null && diagnosisResult != null
						&& dResult.equals(diagnosisResult)) {
					currentPower += 2;
				} else if (dResult != null)
					continue;
				if (isfirst != null && ("Y").equals(isfirst)) {
					currentPower += 1;
				}

				if (currentPower > mostPerfectPower) {
					mostPerfectPower = currentPower;
					mostPerfectValue = curServiceOrgID;
				}
			}
			nextOrgID = mostPerfectValue;
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAutoNextOrgID", e);
			throw new ServiceException(e.toString());
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return nextOrgID;
	}

	public static int getAutoNextOrgID(String role, Object sheetDTO,
			String diagnosisResult) throws ServiceException {
		return getAutoNextOrgID(role, sheetDTO, diagnosisResult, null);
	}

	public static int getAutoNextOrgID(String role, Object sheetDTO)
			throws ServiceException {
		return getAutoNextOrgID(role, sheetDTO, null, null);
	}

	public static String getSystemsettingValueByName(String name) {
		return getStringBySQL("select t.value from t_systemsetting t where t.name ='"
				+ name + "' and t.status ='V'");
	}

	public static String getOrgInfoByOrgID(int orgID) {
		String sql = "select org.orgtype,org.parentorgid from t_organization org where org.orgid="
				+ orgID;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String info = "";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				String orgtype = rs.getString("orgtype");
				int parentorgid = rs.getInt("parentorgid");
				info = orgtype + parentorgid;
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getOrgInfoByOrgID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return info;
	}

	public static int getSAProductIDBySAID(int serviceAccountID)
			throws ServiceException {
		 return getIntBySQL(" select p.productid from t_product p,t_customerproduct cp where "
				           +" p.newsaflag='Y' and p.productid=cp.productid and cp.serviceaccountid="
				           + serviceAccountID);
	}

	/**
	 * ����ҵ���ʻ�id���豸����ȡ�ö�Ӧ���豸��Ϣ
	 * 
	 * @param saID
	 * @param deviceClass
	 * @return
	 * @throws ServiceException
	 */
	public static TerminalDeviceDTO getDeviceInfoBySaIDandClass(int saID,
			String deviceClass) throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		TerminalDeviceDTO terminalDeviceDTO = null;
		String sql = "Select * From t_terminaldevice   Where  deviceclass='"
				+ deviceClass
				+ "' And deviceid In(Select deviceid From t_customerproduct Where serviceaccountid="
				+ saID + ")";
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
				"getDeviceInfoBySaIDandClass=" + sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				terminalDeviceDTO = DtoFiller.fillTerminalDeviceDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDeviceInfoBySaIDandClass", e);
			throw new ServiceException("�Ҳ�����Ȩ��ص��豸��Ϣ");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return terminalDeviceDTO;
	}

	/**
	 * ͨ������ԭ����������͵õ��豸����;�б�����ֵ�á���������
	 * 
	 * @param reason
	 * @return
	 * @throws ServiceException
	 */
	public static String getDevicePurposeStrListByCSICreateReason(
			String csiType, String reason, String deviceModel) {
		LogUtility
				.log(BusinessUtility.class, LogLevel.DEBUG, "����Ĳ���Ϊ��csiType="
						+ csiType + ",reason=" + reason + ",deviceModel="
						+ deviceModel);

		if (csiType == null || "".equals(csiType) || reason == null
				|| "".equals(reason))
			return null;

		// �õ��Ƿ������豸��;��־
		String purposeFlag = getStringBySQL("select  VALUE from T_SystemSetting where name='SET_D_DEVICEPURPOSE' and status='"
				+ CommonConstDefinition.GENERALSTATUS_VALIDATE + "'");
		if (purposeFlag != null
				&& CommonConstDefinition.YESNOFLAG_YES.equals(purposeFlag)) {
			StringBuffer strBuff = new StringBuffer();
			strBuff
					.append("select referPurpose from T_CSITypeReason2Device where CSIType='"
							+ csiType + "' ");
			strBuff.append(" and CSICreateReason='" + reason + "'");
			strBuff.append(" and status='"
					+ CommonConstDefinition.GENERALSTATUS_VALIDATE + "' ");
			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
					"getDevicePurposeStrListByCSICreateReason", strBuff
							.toString());

			String strRet = "";
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(strBuff.toString());
				while (rs.next()) {
					if (rs.getString("referPurpose") != null
							&& rs.getString("referPurpose").length() > 0) {
						if (!"".equals(strRet))
							strRet = strRet + ",";
						strRet = strRet + rs.getString("referPurpose");
					}

				}
			} catch (SQLException e) {
				LogUtility.log(BusinessUtility.class, LogLevel.WARN,
						"getDevicePurposeStrListByCSICreateReason", e);
			} finally {
				if (rs !=null) {
					try{
						rs.close();
					}catch (SQLException e){
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
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "�õ����豸��;�б�Ϊ��"
					+ strRet);
			return strRet;
		}
		return null;
	}

	/**
	 * ������еĲֿ�ID/�ֿ����ƵĻ���
	 * 
	 * @return map
	 */
	public static Map getDepotMop() {
		Map depotCache = new LinkedHashMap();
		String sql = "select depotid,depotname from t_Depot";
		depotCache = getMapBySQL(sql, false);
		return depotCache;
	}

	/**
	 * �õ��豸��;�����ģ���','�ָ�
	 * 
	 * @param
	 * @return
	 */
	public static String getDevicePurposeStrListDes(String purposeStrList) {
		String totalValue = "";
		if (purposeStrList != null) {
			Map devicePurposeCache = getMapBySQL("select key,value from t_commonsettingdata where name='SET_D_DEVICEUSEFORPURPOSE' and status='"
					+ CommonConstDefinition.GENERALSTATUS_VALIDATE + "'");
			String[] purposeArray = purposeStrList.split(",");
			for (int j = 0; j < purposeArray.length; j++) {
				String value = (String) devicePurposeCache.get(purposeArray[j]);
				if (value != null && !"".equals(value)) {
					if (totalValue == "")
						totalValue = value;
					else
						totalValue = totalValue + "," + value;
				}
			}

		}
		return totalValue;
	}

	/**
	 * ������������������ԭ��ȡ��t_csiactionreasondetail�е�������Ϣ.
	 * 
	 * @param csiType
	 * @param detailKey
	 * @return
	 */
	public static String getCsiActionReasonDesc(String csiType, String detailKey) {
		if (csiType == null || detailKey == null || "".equals(csiType)
				|| "".equals(detailKey))
			return "";
		
		String sReturn = "";
		StringBuffer sql = new StringBuffer();
		sql.append("select detail.value ");
		sql
				.append(" from t_csiactionreasonsetting setting join t_csiactionreasondetail detail ");
		sql.append(" on detail.referseqno=setting.seqno ");
		sql.append(" where setting.csitype='").append(csiType).append("'");
		sql.append(" and detail.key='").append(detailKey).append("'");
		
		sReturn = getStringBySQL(sql.toString());
		if(sReturn == null) sReturn = "";
		return sReturn;
	}

	/**
	 * ��psid����,ֻ�����ڲ�Ʒȡ��ʱ,ȡ��˳��Ϊ�����Ʒ>>Ӳ����Ʒ>>����ҵ���ʻ���Ʒ.
	 * 
	 * @param psids
	 * @return
	 */
	public static Collection sortPsidWithCancelCp(Collection psids) {
		if (psids == null || psids.isEmpty())
			return null;
		StringBuffer strPsids = new StringBuffer();
		for (Iterator it = psids.iterator(); it.hasNext();) {
			strPsids.append((Integer) it.next());
			if (it.hasNext()) {
				strPsids.append(",");
			}
		}
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		ArrayList res = new ArrayList();
		StringBuffer sql = new StringBuffer();

		sql.append("select psid from (");
		sql.append(" select cp.psid,decode(product.newsaflag,null,'N',product.newsaflag) newsaflag,");
		sql.append(" product.productname,product.productclass");
		sql.append(" from t_customerproduct cp join t_product product on cp.productid=product.productid");
		sql.append(" where psid in (").append(strPsids).append(")");
		sql.append(" )order by newsaflag asc,productclass desc,psid desc");
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(new Integer(rs.getInt("psid")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"sortingPsidWithCancelCp", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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

		return res;
	}

	public static String getDeviceSwapStatusByReason(String reason) {
		if (reason == null || "".equals(reason))
			return "";

		StringBuffer sql = new StringBuffer();
		sql.append("select ToStatus  from T_SwapDeviceReason2Status ");
		sql.append(" where instr(reasonstrlist,'" + reason
				+ "')>0 and status='"
				+ CommonConstDefinition.GENERALSTATUS_VALIDATE + "'");
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
				"getDeviceSwapStatusByReason������SQL=" + sql.toString());
		return getStringBySQL(sql.toString());
	}

	/**
	 * ��������ԭ��key�õ���Ӧ�����ı�ʾ
	 * 
	 * @param
	 * @return
	 */
	public static String getCsiReasonDesByCon(String csitype, String action,
			String csiReason) {
		String sql = " select detail.value from T_CSIACTIONREASONDETAIL detail,T_CSIACTIONREASONSETTING setting "
				+ "where detail.REFERSEQNO = setting.SEQNO and setting.CSITYPE = '"
				+ csitype + "' and detail.KEY = '" + csiReason + "'";
		if (action != null) {
			sql = sql + " and setting.ACTION = '" + action + "'";
		}
		return getStringBySQL(sql);
	}

	public static String getCustomerNameForBatchItem(String sql) {
		if (sql == null || "".equals(sql))
			return null;

		return getStringBySQL(sql);
	}

	public static double getOwnFeeAmountForBatchItem(String sql) {
		if (sql == null || "".equals(sql))
			return 0;

		return getdoubleBySQL(sql);
	}

	/**
	 * ����������idȡ��������Ӧ�Ŀͻ���Ʒ��id
	 * 
	 * @param csiID
	 * @return
	 */
	public static Collection getPsIDListbyCsiID(int csiID)
			throws ServiceException {
		Collection psIDList = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("Select custproductid From T_CSICUSTPRODUCTINFO Where custproductid<>0 and csiid=?");
			stmt.setInt(1, csiID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				psIDList.add(new Integer(rs.getInt("custproductid")));
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getPsIDListbyCsiID", e);
			psIDList.clear();
			throw new ServiceException("���ݱ��������ţ�" + csiID + "��ȡ�豸��Ϣ�Ƿ�������");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new ServiceException("���ݱ��������ţ�" + csiID
							+ "��ȡ�豸��Ϣ�Ƿ�������");
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new ServiceException("���ݱ��������ţ�" + csiID
							+ "��ȡ�豸��Ϣ�Ƿ�������");
				}
			}
		}
		return psIDList;
	}

	/*
	 * ȡ�ô洢����ִ�к�Ļ��档
	 */
	public static Collection getSpProcessCache(BatchNoDTO iBatchNo) {
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
				"����Ĳ���Ϊ��iBatchNo=" + iBatchNo);

		ArrayList alCache = new ArrayList();
		String sql = "select * from T_SP_OPERATIONCACHE where processstatus='W' and (BatchID = "
				+ iBatchNo.getBatchNo()
				+ " Or BatchID = "
				+ iBatchNo.getBatchNo2() + ")";
		System.out.println(sql);
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG, sql);

		Connection con = null;
		Statement stmt = null;
		ResultSet rs =null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				SpOperationCacheDTO oc = DtoFiller.fillSpOperationCacheDTO(rs);
				alCache.add(oc);

			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getSpProcessCache", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		LogUtility.log(BusinessUtility.class, LogLevel.WARN, "�õ��洢���̲�������");
		return alCache;
	}

	/*
	 * ִ��ĳ��SQL���
	 */
	public static boolean ExecuteSqlstmt(String sql) {
		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG, "����Ĳ���Ϊ��sql="
				+ sql);
		Connection con = null;
		Statement stmt = null;
		boolean bSucc = false;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			bSucc = true;

		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"ExecuteSqlstmt", e);
			System.out.println(e);
		} finally {
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
		return bSucc;
	}

	/**
	 * @param fieldName(PunishmentAcctItemTypeID
	 *            ���ɽ�ForcedDepositAcctItemTypeID Ѻ��ReturnDeviceAcctItemTypeID
	 *            �豸�˷ѣ�
	 * @return
	 * @throws ServiceException
	 */
	public static String getAcctItemTypeIDyFieldName(String fieldName)
			throws ServiceException {
		String accTitemTypeID = getStringBySQL("Select * From t_acctitemtype Where acctitemtypeid In(Select "
				+ fieldName + " From T_FinancialSetting)");
		return accTitemTypeID;
	}

	/**
	 * 
	 * @param start
	 *            ��ʼʱ��
	 * @param end
	 *            ����ʱ��
	 * @param psid
	 *            �ͻ���ƷID
	 * @param excludeCbrid
	 *            ����Ҫ���м���CBRID����Ҫ�����޸Ŀͻ����Ʒѹ���ʱ���е��ж�
	 * @return
	 * @throws ServiceException
	 */
	public static boolean IsCustBillingRuleConflict(Timestamp start,
			Timestamp end, int psid, int excludeCbrid) throws ServiceException {
		if (start == null || end == null || psid == 0)
			return false;
		boolean b = true;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as count from T_CustomerBillingRule t where t.status='"
				  + CommonConstDefinition.GENERALSTATUS_VALIDATE
			  	  + "' and t.PsID=" + psid);
		if (excludeCbrid > 0)
			sql.append(" and t.id<>" + excludeCbrid);
		sql.append(" and ( (t.StartDate<=to_timestamp('").append(
				start.toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		sql.append("  and t.EndDate>=to_timestamp('").append(start.toString())
				.append("', 'YYYY-MM-DD-HH24:MI:SSxff'))");
		sql.append(" or (t.StartDate<=to_timestamp('").append(end.toString())
				.append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		sql.append("  and t.EndDate>=to_timestamp('").append(end.toString())
				.append("', 'YYYY-MM-DD-HH24:MI:SSxff'))");
		sql.append(" or (t.StartDate>=to_timestamp('").append(start.toString())
				.append("', 'YYYY-MM-DD-HH24:MI:SSxff') and "+ "t.EndDate<=to_timestamp('")
				.append(end.toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')))");

		LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
				"IsCustBillingRuleConflict().sql=" + sql.toString());
		int ct =getIntBySQL(sql.toString());
		if (ct == 0){
			b = false;
		}
		return b;
	}

	/**
	 * ���ݲ�Ʒidȡ�ö�Ӧ�Ĳ�Ʒ��Ϣ
	 * 
	 * @param psID
	 * @return
	 * @throws ServiceException
	 */
	public static CustomerProductDTO getCustomerProductDTOByPsID(int psID)
			throws ServiceException {
		CustomerProductDTO custprodDTO = null;
		Connection con = DBUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String querySql = "Select * From t_customerproduct Where  psid= "
				+ psID;
		LogUtility.log(clazz, LogLevel.DEBUG, "getCustomerProductDTOByPsID",
				querySql);

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(querySql);
			if (rs.next()) {
				custprodDTO = DtoFiller.fillCustomerProductDTO(rs);
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getCustomerProductDTOByPsID", sqle);
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
		if (custprodDTO == null)
			throw new ServiceException("���ݲ�Ʒid(" + psID + ")ȡ������Ӧ�Ĳ�Ʒ��Ϣ.");
		return custprodDTO;
	}

	/*
	 * ��֤�Ƿ��Ǵ������A�������
	 */
	public static boolean isACampaign(int CampaignID) {
		String campaignType =getStringBySQL("Select CampaignType From t_Campaign Where  CampaignID= "+ CampaignID);
		boolean bCampaign =false;
		if ("A".compareTo(campaignType) == 0) {
			bCampaign = true;
		}
		return bCampaign;
	}

	public static String getCatvID(int districtId) throws ServiceException {
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs = null;
		try {
			cs = con.prepareCall("{?=call sp_f_getcatvid(?)}");
			cs.setInt(2, districtId);
			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
			cs.execute();
			return cs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException("�ն˱�����ɴ���.");
		} finally {
			try {
				cs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getFieldNameByFieldInterID(String str) {
		return getStringBySQL("select t.FieldName from t_fieldconfig t where t.status ='V' and t.fieldinterid='"
				+ str + "'");
	}

	/**
	 * �Ƿ�����ն˺�
	 * 
	 * @param catvID
	 * @return
	 */
	public static boolean IsExistCatvID(String catvID) {
		if (catvID == null || "".equals(catvID))
			return false;
        boolean ret =false;
		String id =getStringBySQL("select id from t_catvterminal where id='" + catvID + "'");
		
		if (id !=null && !id.trim().equals("")) {
			ret = true;
		}
		
		return ret;
	}

	public static CatvTerminalDTO getCatvTerminalDTOByID(String catvID) {
		if (catvID == null || "".equals(catvID))
			return null;

		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		CatvTerminalDTO terDto = null;
		int ctId = 0;
		String sql = "select * from t_catvterminal where id='" + catvID + "'";
		LogUtility.log(clazz, LogLevel.DEBUG, "IsExistCatvID", sql);
		try {
			con =DBUtil.getConnection();;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				terDto = DtoFiller.fillCatvTerminalDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getCatvTerminalDTOByID", sql);
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
		return terDto;
	}

	/**
	 * ����t_systemsetting��nameȡ�ö�Ӧ��ֵ�����ֻ�������ж��ַ��������õģ�
	 * 
	 * @param conID
	 * @return
	 */
	public static String getSystemSettingValue(String settingName) {
		return getStringBySQL(" Select Value From t_systemsetting Where Name='"
				+ settingName + "'");
	}

	public static int getCustomerCount(String serialNo) {
		return getIntBySQL(" Select Count(*) count From t_customer Where CustomerSerialNo='"
				+ serialNo + "'");
	}

	public static int getEffectiveCATVCsiId(int customerId) {
		return getIntBySQL("select id FROM T_CUSTSERVICEINTERACTION csi where csi.TYPE in('CAA','CAS','CAO','CAR') and csi.CUSTOMERID="
				+ customerId + " AND CSI.STATUS in('N','W','P')");
	}

	/**
	 * @param string
	 * @return
	 */
	public static int isExistCatv(String catvId) {
		return getIntBySQL("select count(*) from t_catvterminal where id ='"
				+ catvId + "'");

	}

	public static int getCatvSAIDByCustomerID(int custId)
			throws ServiceException {
		return getIntBySQL("select serviceaccountid from t_serviceaccount where customerid="
				+ custId + " and serviceid=6");
	}

	public static int getServiceIDBySAID(int SAID) throws ServiceException {
		return getIntBySQL("select serviceid from t_serviceaccount where serviceaccountid="
				+ SAID);
	}

	/**
	 * ���ݿͻ�ID�õ��ͻ������еĲ�Ʒ�豸ID�����Σ��б�
	 * 
	 * @param customerID
	 * @param accountID
	 * @param saID
	 * @return
	 * @throws ServiceException
	 */
	public static Collection getDeviceIDListByCustInfo(int customerID)
			throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Collection deviceIDList = new ArrayList();

		StringBuffer sql = new StringBuffer(
				"select deviceid from t_customerproduct where deviceid<>0 AND status<>'"
						+ CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						+ "' ");
		if (customerID > 0)
			sql.append(" and customerid=" + customerID);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				deviceIDList.add(new Integer(rs.getInt("deviceid")));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDeviceIDListByCustInfo", e);
			throw new ServiceException("���ҿͻ����еĲ�Ʒ����");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return deviceIDList;
	}

	/**
	 * �ն˺��Ƿ��Ѿ����ͻ���ʹ��
	 * 
	 * @param catvID
	 * @return
	 */
	public static boolean IsCustUseCatvID(String catvID) {
		if (catvID == null || "".equals(catvID))
			return false;

		String sql = "select count(*) as custNum from t_customer where catvid='"
				+ catvID
				+ "' and status <>'"
				+ CommonConstDefinition.CUSTOMER_STATUS_CANCEL + "' ";
		LogUtility.log(clazz, LogLevel.DEBUG, "IsCustUseCatvID", sql);

		if (getIntBySQL(sql) > 0)
			return true;

		return false;
	}

	public static void main(String args[]) {
	}

	/**
	 * @param string
	 * @param i
	 * @return
	 */
	public static int calculatePointForIPPV(String caWalletCode,
			BigDecimal value) {
		BigDecimal rate = getBigDecimalBySQL("select rate from t_cawalletdefine where cawalletcode like '"
				+ caWalletCode + "'");
		System.out.println("BigDecimal of wallet rate is : " + rate
				+ " and value* rate is: " + value.multiply(rate));
		System.out.println("The point is : " + value.multiply(rate).intValue());
		return value.multiply(rate).intValue();
	}

	/**
	 * @param string
	 * @return
	 */

	public static int getCAWalletNumInSA(int said, String walletCode) {
		int num = getIntBySQL("SELECT COUNT(*) FROM t_cawallet w WHERE w.status='V' AND w.serviceaccountid="
				+ said + " AND w.cawalletcode='" + walletCode + "' " +
				" and w.scserialno in  " +
				" (select td.serialno from t_terminaldevice td " +
				" left join t_customerproduct cp on td.deviceid =cp.deviceid " +
				" left join t_serviceaccount sc on sc.serviceaccountid =cp.serviceaccountid " +
				" where cp.serviceaccountid="+said+" and cp.status<>'C') ");
		return num;
	}

	public static ArrayList getRequiredCAWalletList() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList waList = new ArrayList();

		String sql = "SELECT * FROM t_cawalletdefine wd WHERE wd.required='Y'";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				waList.add(DtoFiller.fillCAWalletDefineDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getRequiredCAWalletList", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return waList;
	}

	public static CustomerProductDTO getCustomerProductDTOBySerialNo(
			String serialNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CustomerProductDTO dto = null;
		try {
			String strSql = "SELECT * FROM t_customerproduct cp JOIN t_terminaldevice td ON cp.deviceid=td.deviceid WHERE td.serialno='"
					+ serialNo + "'";

			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillCustomerProductDTO(rs);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCustomerProductDTOListByPackageID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static List getCAWalletListByDeviceID(int deviceID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List res = new ArrayList();
		try {
			String strSql = "SELECT * FROM t_cawallet caw JOIN t_terminaldevice ter ON caw.scserialno=ter.serialno WHERE caw.status='V' and ter.deviceid="
					+ deviceID;
			LogUtility.log(BusinessUtility.class, LogLevel.DEBUG,
					"getCAWalletListByDeviceID", strSql);
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoFiller.fillCAWalletDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCAWalletListByDeviceID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return res;
	}

	/**
	 * Ĭ��ֵ�Ƿ��Ѿ�����
	 * 
	 * @param catvID
	 * @return
	 */
	public static boolean IsSetDefault(String name) {
		if (name == null || "".equals(name))
			return false;

		String sql = "select count(*) from t_CommonSettingData where name ='"
				+ name + "' and defaultornot='Y'";
		LogUtility.log(clazz, LogLevel.DEBUG, "IsSetDefault", sql);

		if (getIntBySQL(sql) > 0)
			return true;

		return false;
	}

	/**
	 * ����̯���ƻ�,̯�����������в�ѯ�������Ƿ��Ѿ�����
	 * 
	 * @param catvID
	 * @return
	 */
	public static boolean IsFeeSplitItem(int feeSplitPlanID, int timeCycleNO) {
		if (feeSplitPlanID == 0 || timeCycleNO == 0)
			return false;

		String sql = "select count(*) from t_feesplitplanitem where feeSplitPlanID="
				+ feeSplitPlanID + " and timeCycleNO=" + timeCycleNO;
		LogUtility.log(clazz, LogLevel.DEBUG, "IsSetDefault", sql);

		if (getIntBySQL(sql) > 0)
			return true;

		return false;
	}

	// zyg 2007.12.06 begin
	// �������̯���ƻ���Ŀ�޸ĵ�����
	/**
	 * ����̯���ƻ���Ŀ / ̯���ƻ�/̯�����������в�ѯ�������Ƿ��Ѿ�����̯�������ظ������
	 * 
	 * @param seqno :
	 *            ̯���ƻ���Ŀ��¼ID
	 * @param feeSplitPlanID :
	 *            ̯���ƻ���¼ID
	 * @param timeCycleNO :
	 *            ̯������
	 * @return
	 */
	public static boolean IsFeeSplitItem(int seqno, int feeSplitPlanID,
			int timeCycleNO) {
		if (seqno == 0 || feeSplitPlanID == 0 || timeCycleNO == 0)
			return false;

		String sql = "select count(*) from t_feesplitplanitem where feeSplitPlanID="
				+ feeSplitPlanID
				+ " and timeCycleNO="
				+ timeCycleNO
				+ " and seqno!=" + seqno;
		LogUtility.log(clazz, LogLevel.DEBUG, "IsSetDefault", sql);

		if (getIntBySQL(sql) > 0)
			return true;

		return false;
	}

	// zyg 2007.12.06 end

	public static AccountItemDTO getAcctItemDTOByAiNO(int aiNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AccountItemDTO dto = null;
		String strSql = "SELECT * FROM t_accountitem a WHERE a.ai_no="+ aiNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillAccountItemDTO(rs);
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAcctItemDTOByAiNO", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return dto;
	}

	public static double doubleFormat(double double1) {
		java.text.DecimalFormat format = (java.text.DecimalFormat) java.text.DecimalFormat
				.getInstance();
		// �Ѹ�������ʽ��Ϊ��λС����double
		format.applyPattern(".00");
		return Double.parseDouble(format.format(double1));
	}

	/**
	 * ϵͳ�Ƿ�����豸���
	 * 
	 * @param itemTypeID
	 * @return
	 * @throws ServiceException
	 */
	public static boolean isSystemManagerDeviceMatch() throws ServiceException {
		boolean isManager = Boolean.FALSE.booleanValue();
		String value = getStringBySQL("select value from t_systemsetting where name = 'SET_W_DEVICEMATCH'");
		if (value != null && !"".equals(value))
			if ("Y".equals(value))
				isManager = true;
		return isManager;
	}

	/**
	 * ��ǰ�豸�Ƿ����(ֻ���STB,SC)
	 * 
	 * @param itemTypeID
	 * @return
	 * @throws ServiceException
	 */
	public static boolean isDeviceMatch(String serialNoSTB, String serialNoSC)
			throws ServiceException {
		boolean isDeviceMatch = Boolean.FALSE.booleanValue();
		String matchFlagSTB = "";
		String matchFlagSC = "";
		// ֻ��һ���豸�������С�
		if (!"".equals(serialNoSTB) && "".equals(serialNoSC)) {
			matchFlagSTB = getStringBySQL("select matchflag from t_terminaldevice where serialno='"
					+ serialNoSTB + "'");
			if ("Y".equals(matchFlagSTB))
				isDeviceMatch = true; // �����
		}
		// �������豸�������к����ܿ�
		if (!"".equals(serialNoSTB) && !"".equals(serialNoSC)) {
			matchFlagSTB = getStringBySQL("select matchflag from t_terminaldevice where serialno='"
					+ serialNoSTB + "'");
			matchFlagSC = getStringBySQL("select matchflag from t_terminaldevice where serialno='"
					+ serialNoSC + "'");
			if ("Y".equals(matchFlagSTB) || "Y".equals(matchFlagSC))
				isDeviceMatch = true; // �����
		}
		return isDeviceMatch;
	}

	/**
	 * ��ǰ�豸�Ƿ����(ֻ���STB,SC)
	 * 
	 * @param itemTypeID
	 * @return
	 * @throws ServiceException
	 */
	public static boolean isDeviceMatch(String newSerialNo)
			throws ServiceException {
		boolean isDeviceMatch = Boolean.FALSE.booleanValue();
		String matchFlag = getStringBySQL("select matchflag from t_terminaldevice where serialno='"
				+ newSerialNo + "'");
		if (matchFlag != null && !"".equals(matchFlag))
			if ("Y".equals(matchFlag))
				isDeviceMatch = true;
		return isDeviceMatch;
	}

	public static List getAuthorizationInfoWithSerialNo(String serialNo) {

		Connection con = DBUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			String strSql = "SELECT * FROM t_ossi_authorization WHERE deviceserialno=?";
			ps = con.prepareStatement(strSql);
			ps.setString(1, serialNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				OssAuthorizationDTO dto = DtoFiller.fillOssAuthorizationDTO(rs);
				list.add(dto);
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getAuthorizationInfoWithSerialNo", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
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
		return list;
	}

	/**
	 * ��Թ�ϵ�Ƿ�ָ��Է�(ֻ���STB,SC)
	 * 
	 * @param itemTypeID
	 * @return
	 * @throws ServiceException
	 */
	public static boolean isDeviceMatchRelationship(String serialNoSTB,
			String serialNoSC) throws ServiceException {
		boolean isDeviceMatchRelationship = Boolean.FALSE.booleanValue();
		String matchDeviceIdSTB = "";
		String matchDeviceIdSC = "";
		String deviceIdSTB = "";
		String deviceIdSC = "";
		// ֻ��һ���豸�������С�
		if (!"".equals(serialNoSTB) && "".equals(serialNoSC)) {
			matchDeviceIdSTB = getStringBySQL("select matchdeviceid from t_terminaldevice where serialno='"
					+ serialNoSTB + "'");
			if (!"".equals(matchDeviceIdSTB))
				isDeviceMatchRelationship = false; // û�����ܿ�����������������豸����Թ�ϵΪfalse
		}
		// �������豸�������к����ܿ�
		if (!"".equals(serialNoSTB) && !"".equals(serialNoSC)) {
			deviceIdSTB = getStringBySQL("select deviceid from t_terminaldevice where serialno='"
					+ serialNoSTB + "'");
			matchDeviceIdSTB = getStringBySQL("select matchdeviceid from t_terminaldevice where serialno='"
					+ serialNoSTB + "'");
			deviceIdSC = getStringBySQL("select deviceid from t_terminaldevice where serialno='"
					+ serialNoSC + "'");
			matchDeviceIdSC = getStringBySQL("select matchdeviceid from t_terminaldevice where serialno='"
					+ serialNoSC + "'");
			if (!"".equals(matchDeviceIdSTB) && !"".equals(matchDeviceIdSC)) {
				if (deviceIdSTB.equals(matchDeviceIdSC)
						&& deviceIdSC.equals(matchDeviceIdSTB)) {
					isDeviceMatchRelationship = true; // ��Թ�ϵָ��Է�
				} else {
					isDeviceMatchRelationship = false;
				}
			} else {
				isDeviceMatchRelationship = false;
			}
		}
		return isDeviceMatchRelationship;
	}

	/**
	 * ����oracle�Զ��庯����CheckBankAccount
	 * 
	 * @throws ServiceException
	 */
	public static String callFunctionForCheckBankAccount(int mopid,
			String bankaccount) throws ServiceException {
		Connection con = null;
		java.sql.CallableStatement cstmt = null;
		String str_rtn = "";
		try {
			con = DBUtil.getConnection();
			cstmt = con.prepareCall("{? = call checkBankAccount(?,?)}");
			cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
			cstmt.setInt(2, mopid);
			cstmt.setString(3, bankaccount);
			cstmt.execute();
			str_rtn = cstmt.getString(1);
			System.out.println("�����ʺ�У�麯������ֵ��" + str_rtn);
		} catch (Exception e) {
			System.out.println("|||||||||||||||||||||||||||||||||||||||||||");
			throw new ServiceException("�����ʺ�У�麯�����ô���");
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
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
		return str_rtn;
	}

	public static List getCAWalletDefineByCode(String caCode) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List res = new ArrayList();
		try {
			String strSql = " select * from t_cawalletdefine t where t.cawalletcode='"
					+ caCode + "'";
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoFiller.fillCAWalletDefineDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCAWalletDefineByCode", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return res;
	}

	public static List getDtvMigrationByID(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List res = new ArrayList();
		try {
			String strSql = "select * from t_dtvmigrationarea t where id=" + id;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoFiller.fillDtvMigrationAreaDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getDtvMigrationByID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return res;
	}

	/*
	 * �ж�һ����Ʊ�����Ƿ���״̬��Ϊ"����"�ķ�Ʊ �У�����true;û�У�����false�� wangpeng@20080314
	 */
	public static boolean hasUnCanceledFapiao(String fapiaoType, String volumnNo,String hasAbandonFapiaoSeqno) {
		
		String sqlStr="select count(*) from t_fapiao t1 ,t_fapiaovolumn t2 "
			+ "where t1.volumnseqno=t2.seqno and t1.status<>'CAN' and t2.volumnsn='"
			+ volumnNo + "' and t2.type='" + fapiaoType + "' " +
					"and t1.serailno not in ("+hasAbandonFapiaoSeqno+")";
			
		System.out.println("_______________________sqlStr"+sqlStr);
		
		int count = getIntBySQL(sqlStr);
		

		if (count == 0)
			return false;

		return true;
	}

	/*
	 * ���ݷ�Ʊ��ţ���Ʊ���ͣ�ȡ��Ψһ�ķ�Ʊ��ʵ�塣 wangpeng@20080314
	 */
	public static FapiaoVolumn getFapiaoVolumn(String fapiaoType,
			String volumnNo) throws HomeFactoryException, FinderException {
		
		String sql="select t1.seqno from t_fapiaovolumn t1 "
			+ "where t1.volumnsn='" + volumnNo + "' and t1.type='"
			+ fapiaoType + "'";
		System.out.println("___________SQL,"+sql);
		
		int seqNo ;
		seqNo= getIntBySQL(sql);
		System.out.println("_____________seqNo___"+seqNo);
		
		if(seqNo<0){
			return null;
		}
		else{
			FapiaoVolumnHome faPiaoVolumnHome = HomeLocater.getFapiaoVolumnHome();
			return faPiaoVolumnHome.findByPrimaryKey(new Integer(seqNo));
			
		}
	}

	/*
	 * ����������ѯ��Ʊ String type --��Ʊ/��Ʊ������ String volumnSn --��Ʊ��ֽ�����к� String
	 * serialNo --��Ʊֽ�����к�
	 * 
	 * 
	 * type and volumnSn and serialNo ����Ψһ��λһ�ŷ�Ʊ
	 */
	public static List getFaPiaoDtoByCon(String type, String volumnSn,
			String serialNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer queryBegin = new StringBuffer(
				"select t1.* from t_fapiao t1");
		StringBuffer queryCon = new StringBuffer(" where 1=1");
		if (type != null && !"".equals(type.trim()))
			queryCon.append(" and t1.type='" + type + "'");
		if (volumnSn != null && !"".equals(volumnSn.trim())) {
			queryBegin.append(",t_fapiaovolumn t2");
			queryCon.append(" and t1.volumnseqno = t2.seqno and t2.volumnsn='"
					+ volumnSn + "'");
		}
		if (serialNo != null && !"".equals(serialNo.trim()))
			queryCon.append(" and t1.serailno='" + serialNo + "'");

		List res = new ArrayList();
		try {
			String strSql = "" + queryBegin + queryCon;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoFiller.fillFaPiaoDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getFaPiaoDtoByCon", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return res;
	}

	/*
	 * �жϷ�Ʊ���Ƿ�������ָ������,���򷵻�true
	 * :��Ʊ�������з�Ʊ����ָ������--��Ʊ������ؿ�����
	 * 
	 * ����в���ָ�����еķ�Ʊ�򷵻�false
	 * 
	 */
	public static boolean isBack(String volumnSeqno, String faPiaoSeqNoStr,int depotID) {
		boolean reSult = true;
		String strSql = "select count(*) from t_fapiao t1,t_fapiaovolumn t2 where t1.volumnseqno = t2.seqno and t2.seqno="
					  + volumnSeqno
					  + " and t1.seqno not in("
					  + faPiaoSeqNoStr
					  + ") not (t1.status='SAV' and t1.ADDRESSTYPE='D' and t1.ADDRESSID ="
					  + depotID+") and t1.status not in('"
					  + CommonConstDefinition.FAPIAO_STATUS_CAN
					  + "','"
					  + CommonConstDefinition.FAPIAO_STATUS_DIS + "')";
		int ct =getIntBySQL(strSql); 
		if (ct >0){
			reSult = false;
		} 
		return reSult;
	}

	/*
	 * ����������ѯ��Ʊ String type --��Ʊ���� String volumnSn --��Ʊֽ�����к���ʼ���� String
	 * serialEnd --��Ʊֽ�����к���ֹ����
	 * 
	 * type and volumnSn and serialNo ����Ψһ��λһ�ŷ�Ʊ
	 */
	public static String getFaPiaoSerialNosByCon(String type,
			String serialBegin, String serialEnd,String fromAddressType,String fromAddressID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer queryBegin = new StringBuffer("select t1.* from t_fapiao t1");
		StringBuffer queryCon = new StringBuffer(" where 1=1");
		if (type != null && !"".equals(type.trim()))
			queryCon.append(" and t1.type='" + type + "'");

		if (serialBegin != null && !"".equals(serialBegin.trim()))
			queryCon.append(" and t1.serailno >='" + serialBegin.trim() + "'");
		if (serialEnd != null && !"".equals(serialEnd.trim()))
			queryCon.append(" and t1.serailno <='" + serialEnd.trim() + "'");
		if (fromAddressType != null && !"".equals(fromAddressType.trim())){
			queryCon.append(" and t1.ADDRESSTYPE ='" + fromAddressType.trim() + "'");
			if (fromAddressID != null && !"".equals(fromAddressID.trim()))
				queryCon.append(" and t1.ADDRESSID =" + fromAddressID.trim());
		}
		queryCon.append(" and t1.status not in('CAN','DIS')");

		String res = "";
		try {
			String strSql = "" + queryBegin + queryCon;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (!"".equals(res))
					res = res + "\n";
				res = res + rs.getString("SerailNo");
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getFaPiaoSerialNosByCon", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return res;
	}
	
	
	/*
	 * �жϷ�Ʊ���Ƿ��������,���򷵻�true
	 * ���ڲ���Ŀ�������Ϊ�����Ĺ�Ʊ�򷵻�false
	 * 
	 * 
	 * 
	 */
	public static boolean isMove(String volumnSeqno, String faPiaoSeqNoStr,String toDepotID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean reSult = true;
		String strSql = "select count(*) from t_fapiao t1,t_fapiaovolumn t2 where t1.volumnseqno = t2.seqno and t2.seqno="
					  + volumnSeqno
					  + " and t1.seqno not in("
					  + faPiaoSeqNoStr
					  + ") not (t1.status='SAV' and t1.ADDRESSTYPE='D' and t1.ADDRESSID ="
					  +toDepotID+") and t1.status not in('"
					  + CommonConstDefinition.FAPIAO_STATUS_CAN
					  + "','"
					  + CommonConstDefinition.FAPIAO_STATUS_DIS + "')" ;
		int ct =getIntBySQL(strSql);
		if (ct >0){
			reSult = false;
		} 
		return reSult;
	}

	/*
	 * ���ݷ�Ʊ��,ȡ�÷�Ʊ��ϸDTO�� wangpeng@20080317
	 */
	public static List getFapiaoDetailByFapiaoSeqNo(int fapiaoSeqNo) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List res = new ArrayList();
		try {
			String strSql = "select t.* from t_fapiaodetail t where t.FAPIAOSEQNO="
					+ fapiaoSeqNo;
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoFiller.fillFapiaoDetailDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getFapiaoDetailByFapiaoSN", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return res;
	}
	
	/*
	 * �жϷ�Ʊ���Ƿ���������,���򷵻� true
	 * 
	 * :��Ʊ���´��ڲ�����ָ����֯�����ķ�Ʊ�򷵻�false,���򷵻�true
	 * 
	 */
	public static boolean isUse(String volumnSeqno, String faPiaoSeqNoStr,int toAddressID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean reSult = true;
		String strSql = "select count(*) from t_fapiao t1,t_fapiaovolumn t2 where t1.volumnseqno = t2.seqno and t2.seqno="
					  + volumnSeqno
				  	  + " and t1.seqno not in("
					  + faPiaoSeqNoStr
					  + ") not (t1.status='REC' and t1.ADDRESSTYPE='O' and t1.ADDRESSID ="
					  +toAddressID+") and t1.status not in('"
					  + CommonConstDefinition.FAPIAO_STATUS_CAN
					  + "','"
					  + CommonConstDefinition.FAPIAO_STATUS_DIS + "')";
		int ct =getIntBySQL(strSql);
		if (ct >0){
		    reSult = false;
		} 
		return reSult;
	}
	
	/*
	 * ����һ����Ʊ���е�����δ���ϵķ�ƱDTO,
	 * wangpeng@20080322
	 */
	public static List getUnDisFapiaoDTOByVolumnSN(String type,String volumnSN) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List res = new ArrayList();
		try {
			String strSql = "select t1.* from t_fapiao t1,t_fapiaovolumn t2 " +
					        "where t1.volumnseqno=t2.seqno " +
					        "and t2.type='"+type+
					        "' and t1.status<>'DIS' " +
					        "and t2.volumnsn='"+ volumnSN+"'";
			
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(strSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(DtoFiller.fillFaPiaoDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getFapiaoDTOByVolumnSN", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return res;
	}
	/*
	 * �ж�һ����Ʊ�����Ƿ���״̬��Ϊ"����"�ķ�Ʊ �У�����true;û�У�����false�� wangpeng@20080314
	 */
	public static boolean hasUnDiscardFapiao(String fapiaoType, String volumnNo,String hasDiscardFapiaoSeqno) {
		
		String sqlStr="select count(*) from t_fapiao t1 ,t_fapiaovolumn t2 "
			+ "where t1.volumnseqno=t2.seqno and t1.status<>'DIS' and t2.volumnsn='"
			+ volumnNo + "' and t2.type='" + fapiaoType + "' " +
					"and t1.serailno not in ("+hasDiscardFapiaoSeqno+")";
			
		System.out.println("_______________________sqlStr"+sqlStr);
		
		int count = getIntBySQL(sqlStr);
		
		System.out.println("___________count____"+count);

		if (count == 0)
			return false;

		return true;
	}
	
	/**
	 * �ж���t_cpconfigedproperty(�ͻ���Ʒ����)���м�¼
	 * @param productID
	 * @param propertyCode
	 * @param propertyvalue
	 * @return
	 */
	public static boolean getISExistCpConfigedPropertyByPropertyCodeAndValue(String productID,String propertyCode,String propertyvalue) {
		int ct =getIntBySQL("select count(*) from t_cpconfigedproperty pro left join t_customerproduct cp on cp.psid=pro.psid "+
		 			 "where cp.productid ="+productID+" and pro.propertycode='"+ propertyCode + "' and pro.propertyvalue='"+propertyvalue+"' and pro.status='V'");
		boolean isExist =false;
		if (ct >0) {
			isExist = true;
		}
		return isExist;
	}
	
	
	/**
	 * add by Stone Liang ,date :2006-06-06 ͨ����ƷID������Code���õ���Ʒ����DTO
	 * 
	 * @param productID,
	 *            propertyCode
	 * @return ProductPropertyDTO
	 */
	public static ProductPropertyDTO getProductPropertyDTObyPrimaryKey(
			String productID, String propertyCode) {
		ProductPropertyDTO dto = new ProductPropertyDTO();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select * from t_productproperty where productid = '"
				+ productID + "' and propertyCode = '" + propertyCode + "'";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto = DtoFiller.fillProductPropertyDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getProductPropertyDTObyPrimaryKey", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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

		return dto;
	}
	/*
	 * ���ݷ�Ʊ�ֿ�id�õ���Ʊ�ֿ�����ı�ʾ
	 */
	public static String getFapiaoDeportByID(int id) {
		String sql = "select t.FPDEPOTNAME from T_FAPIAODEOPT t where t.FPDEPORTID ="+id;
				
		return getStringBySQL(sql);
	}
	/*
	 * ������֯����id�õ���֯���������ı�ʾ
	 */
	public static String getOrgNameByID(int id) {
		String sql = "select t.ORGNAME from t_organization t where t.ORGID ="+id;
				
		return getStringBySQL(sql);
	}

	
	/*
	 * wangfang 2008.05.22 fapiaoquerywebaction �л�ȡ��isvolumnmanager����������
	 */
	public static String getFaPiaoSettingByName(String field)
    {
        return getStringBySQL("select " + field + " from T_FAPIAOSETTING");
    }
	
	
	/**
	 * ����oracle�Զ��庯����area_sell_stat
	 * 
	 * @throws ServiceException
	 */
	public static int callProcedureForAreaSellStat(String batchNo,
			String filldate) throws ServiceException {
		Connection con = null;
		java.sql.CallableStatement cstmt = null;
		int retcode = 0;
		String errmsg = "";
		try {
			con = DBUtil.getConnection();
			cstmt = con.prepareCall("{call area_sell_stat(?,?,?,?)}");
			cstmt.setString(1, batchNo);
			cstmt.setString(2, filldate);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			retcode = cstmt.getInt(3);
			errmsg = cstmt.getString(4);
			if(retcode==0)
				throw new ServiceException("���ɻ���������ͳ���ձ���ʱ��ִ�д洢����ʧ��");
		} catch (Exception e) {
			System.out.println("���ô洢���̳���"+e.getStackTrace());
			throw new ServiceException("���ɻ���������ͳ���ձ���ʱ�����ִ���"+errmsg);
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
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
		return retcode;
	}
	
	public static void callProcedureForVodDevicePut(int batchID)throws ServiceException {
		Connection con = null;
		java.sql.CallableStatement cstmt = null;
		int retcode = 0;
		String errmsg = "";
		try {
			con = DBUtil.getConnection();
			cstmt = con.prepareCall("{call Sp_VoDDevice_BatchImport(?,?,?)}");
			cstmt.setInt(1, batchID);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			retcode = cstmt.getInt(2);
			errmsg = cstmt.getString(3);
			if(retcode==0)
				throw new ServiceException("���ɻ�vod�豸ʱ��ִ�д洢����ʧ��,ʧ��ԭ��"+errmsg);
		} catch (Exception e) {
			System.out.println("���ô洢���̳���"+e.getStackTrace());
			throw new ServiceException("����vod�豸��Ϣʱ�����ִ���"+errmsg);
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
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
	}	
	
	public static int getSequenceKey(String sequence) throws ServiceException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int ID=0;
		String sql = "Select "+sequence+".Nextval  ID From dual";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				ID=rs.getInt("ID");
			}
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,"getSequenceKey", e);
			LogUtility.log(clazz, LogLevel.ERROR,"getSequenceKey", sql);
			throw new ServiceException("ȡIDʱ��������");
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return ID;
	}
	
	public static void importVodDeviceHead(VodstbDeviceImportHeadDTO dto)throws ServiceException {
		PreparedStatement preStmt =null;
		Connection con=null;
		String sql=" insert into vod_stbdevice_import_head( "
			      +" BATCHID,BATCHNO,DEVICECLASS,DEVICEMODEL,PROVIDERID,GUARANTEELENGTH,DEPOTID,"
			      +" INANDOUT,OUTORGID,PURPOSESTRLIST,COMMENTS,OPERATORID,STATUS,CREATETIME "
			      +" ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			con = DBUtil.getConnection();
			preStmt =con.prepareStatement(sql);
			preStmt.setInt(1,dto.getBatchID());
			preStmt.setString(2, dto.getBatchNo());
			preStmt.setString(3, dto.getDeviceClass());
			preStmt.setString(4, dto.getDeviceModel());
			preStmt.setInt(5, dto.getProviderID());
			preStmt.setInt(6, dto.getGuaranteeLength());
			preStmt.setInt(7, dto.getDepotID());
			preStmt.setString(8,dto.getInAndOut());
			preStmt.setInt(9, dto.getOutOrgID());
			preStmt.setString(10, dto.getPurposestrList());
			preStmt.setString(11, dto.getComments());
			preStmt.setInt(12, dto.getOperatorID());
			preStmt.setString(13, dto.getStatus());
			preStmt.setTimestamp(14,TimestampUtility.getCurrentTimestamp());
			preStmt.executeUpdate();
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,"importVodDeviceHead", e);	
			throw new ServiceException("����˫���豸��ͷ��Ϣ��������");
		} finally {
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.ERROR, e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(BusinessUtility.class, LogLevel.ERROR, e);
				}
			}
		}
	}
	
	public static void importVodDeviceDetail(int batchID,Collection detailCol) throws ServiceException {
		PreparedStatement preStmt =null;
		Connection con=null;
		String sql=" insert into vod_stbdevice_import_detail ("
			      +" SERIALNO,BATCHID,MACADDRESS,INTERMACADDRESS,STATUS,Description" 
			      +" ) values(?,?,?,?,?,?)" ;
		try {
			con = DBUtil.getConnection();
			preStmt =con.prepareStatement(sql);
			Iterator detailItr =detailCol.iterator();
			while (detailItr.hasNext()){
				VodstbDeviceImportDetailDTO dto =(VodstbDeviceImportDetailDTO)detailItr.next();
				preStmt.setString(1, dto.getSerialNo());
				preStmt.setInt(2, batchID);
				preStmt.setString(3,dto.getMacaddress());
				preStmt.setString(4, dto.getIntermacaddress());
				preStmt.setString(5, dto.getStatus());
				preStmt.setString(6, dto.getDescription());
				preStmt.addBatch();
			}
			preStmt.executeBatch();
		} catch (Exception e) {
				LogUtility.log(BusinessUtility.class, LogLevel.ERROR,"importVodDeviceDetail", e);
				throw new ServiceException("����˫���豸��ϸ��������");
		} finally {
				if (preStmt != null) {
					try {
						preStmt.close();
					} catch (SQLException e) {
						LogUtility.log(BusinessUtility.class, LogLevel.ERROR, e);
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						LogUtility.log(BusinessUtility.class, LogLevel.ERROR, e);
					}
				}
		}
	}
	
	public static String getStbModelByCmModel(String cmModel){
		return getStringBySQL("select t.xmlkey from t_migration_base t where t.basecode ='SET_VOD_MATCHDEVICE' and t.cmskey ='"+cmModel+"'");
	}
	
	public static Collection getVodModel() {
		Collection deviceModelCols = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
        boolean isExist = false; 
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select t.xmlkey from t_migration_base t where t.basecode ='SET_VOD_DEVICE'");
			while (rs.next()) {
				deviceModelCols.add(rs.getString("xmlkey"));
			}
		} catch (SQLException e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getVodModel", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
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
		return deviceModelCols;
	}
	/**
	 * ����ҵ���˻�idȡ�ö�Ӧ�Ĳ�Ʒ��Ϣ
	 * 
	 * @param psID
	 * @return
	 * @throws ServiceException
	 */
	public static CustomerProductDTO getCustomerProductDTOBySaID(int saID)
			throws ServiceException {
		CustomerProductDTO custprodDTO = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String querySql = "select * from t_customerproduct  where status='N' and serviceaccountid="+ saID;
		LogUtility.log(clazz, LogLevel.DEBUG, "getCustomerProductDTOBySaID",
				querySql);

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(querySql);
			if (rs.next()) {
				custprodDTO = DtoFiller.fillCustomerProductDTO(rs);
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"getCustomerProductDTOBySaID", sqle);
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
		if (custprodDTO == null)
			throw new ServiceException("����ҵ���˻�id(" + saID + ")ȡ������Ӧ�Ĳ�Ʒ��Ϣ.");
		return custprodDTO;
	}
	
	public static void checkBatchAddPackageInfo(Collection packageInfoCols) throws ServiceException{
		Iterator pgItr =packageInfoCols.iterator();
		String packageIds ="";
		while (pgItr.hasNext()){
			Integer pgId =(Integer)pgItr.next();
			if (packageIds.equals("")){
				packageIds =pgId.toString();
			}else{
				packageIds =packageIds +","+pgId.toString();
			}
		}
		
		Connection con = null; 
		Statement stmt = null;
		ResultSet rs = null;
		String querySql = " select t.productid,t.productname,pg.packageid,pg.packagename "
                        + " from t_packageline p ,t_product t,t_productpackage pg "
                        + " where p.productid =t.productid "
                        + " and p.packageid =pg.packageid "
                        + " and pg.packageid in ("+packageIds+")"
                        + " and p.productid in ( "
                        + "     select p.productid "
                        + "     from t_packageline p "
                        + "     where p.packageid in ("+packageIds+")"
                        + "     group by p.productid "
                        + "     having count(*) >1 ) "
                        + " order by t.productid ";
		LogUtility.log(clazz, LogLevel.DEBUG, "checkBatchAddPackageInfo",
				querySql);
		Map mp =new HashMap();
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(querySql);
			while (rs.next()) {
				String productname = rs.getString("productname");
				String packagename = rs.getString("packagename");
				if (mp.containsKey(productname)){
					Collection pgCols =(Collection)mp.get(productname);
					pgCols.add(packagename);
				}else{
					Collection pgCols =new ArrayList();
					pgCols.add(packagename);
					mp.put(productname, pgCols);
				}
			}
		} catch (SQLException sqle) {
			LogUtility.log(BusinessUtility.class, LogLevel.ERROR,
					"checkBatchAddPackageInfo", sqle);
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

		if (mp.size() >0){
			String errorInfo ="";
			Iterator mpIterator = mp.entrySet().iterator();
			while (mpIterator.hasNext()) {
				Map.Entry item = (Map.Entry) mpIterator.next();
				errorInfo =errorInfo +item.getKey()+"��"+item.getValue()+"�д��ڣ�";
			}
			throw new ServiceException(errorInfo);
		}
	}
	
	/**
	 * �ͻ�idȡ�÷�ȡ�������ֵ����û�id����
	 * 
	 * @param customerID
	 * @return
	 */
	public static Collection getSaIDColByCustID(int customerID) {
		List result = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select sa.serviceaccountid from t_serviceaccount sa where sa.customerid =? and sa.serviceid<>? and sa.status<>?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, customerID);
			stmt.setInt(2, 6);
			stmt.setString(3, "C");
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new Integer(rs.getInt("serviceaccountid")));
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "getSaIDColByCustID", e);
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
		return result;
	}
	/**
     * ����Э���
     * @param itemCol
     * @throws ServiceException
     */
    public  static void  createProtocol(Collection protocolCol) throws ServiceException {
    	Connection con = null;
		PreparedStatement preStmt = null;
		//�־û�jobItem
		String sqlItem = " insert into t_protocol (CUSTOMERID, PRODUCTPACKAGEID, SINGLEPRICE, FEETYPE, ACCTITEMTYPEID, DATEFROM, DATETO, "
			           + " UsedCount, STATUS, DT_CREATE, DT_LASTMOD)values (?,?,?,?,?,?,?,?,?,?,?)";
    	try {
    		if(protocolCol!=null && !protocolCol.isEmpty()){
    			con = DBUtil.getConnection();
				preStmt = con.prepareStatement(sqlItem);
    			Iterator protocolIter=protocolCol.iterator();
    			while(protocolIter.hasNext()){
    				ProtocolDTO protocolDTO=(ProtocolDTO)protocolIter.next();
    				preStmt.setInt(1, protocolDTO.getCustomerID());
	    			preStmt.setInt(2, protocolDTO.getProductPackageID());
	    			preStmt.setDouble(3, protocolDTO.getSinglePrice());
					preStmt.setString(4, protocolDTO.getFeeType());
					preStmt.setString(5, protocolDTO.getAcctitemTypeID());
					preStmt.setTimestamp(6,protocolDTO.getDateFrom());
					preStmt.setTimestamp(7,protocolDTO.getDateTo());
					preStmt.setInt(8, protocolDTO.getUsedCount());
					preStmt.setString(9, protocolDTO.getStatus());
					preStmt.setTimestamp(10,TimestampUtility.getCurrentTimestamp());
					preStmt.setTimestamp(11,TimestampUtility.getCurrentTimestamp());
					preStmt.addBatch();
    			}
    			preStmt.executeBatch();
    		}
    	} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.WARN,"createProtocol", e);
			throw new ServiceException("����Э��۷�������");
		} finally {
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (SQLException e) {
					LogUtility.log(clazz, LogLevel.WARN,
							"createProtocol", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(clazz, LogLevel.WARN,
							"createProtocol", e);
				}
			}
		}
    }
    /**
     * ɾ��Э���
     * @param protocolCol
     * @throws ServiceException
     */
    public  static void  deleteProtocol(int customerID) throws ServiceException {
    	Connection con = null;
		PreparedStatement preStmt = null;
		//�־û�jobItem
		String sqlItem = "DELETE FROM t_protocol WHERE CUSTOMERID=?";
    	try {
			con = DBUtil.getConnection();
			preStmt = con.prepareStatement(sqlItem);
			preStmt.setInt(1, customerID);
    		preStmt.executeUpdate();
    	} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.WARN,"deleteProtocol", e);
			throw new ServiceException("ɾ��Э��۷�������");
		} finally {
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (SQLException e) {
					LogUtility.log(clazz, LogLevel.WARN,
							"deleteProtocol", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(clazz, LogLevel.WARN,
							"deleteProtocol", e);
				}
			}
		}
    }
    
    
	/**
	 * ���ݿͻ�IDȡ�����е�Э��۵�ӳ��
	 * @param customerID
	 * @return
	 */
	public static Map getProtocolDTOColByCustID(int customerID) {
		Map result = new LinkedHashMap();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * FROM t_protocol WHERE CUSTOMERID=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, customerID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.put(new Integer(rs.getInt("productPackageID")),DtoFiller.fillProtocolDTO(rs));
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "getProtocolDTOColByCustID", e);
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
		return result;
	}
	
	public static Map getProtocolDTOColBySaIds(String saIds,Collection csiPackageArray){
		Map result = new LinkedHashMap();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " select cp.serviceaccountid, t.singleprice,t.feetype,t.acctitemtypeid,t.customerid "
                   + " FROM t_protocol t,t_customerproduct cp "
                   + " WHERE t.customerid =cp.customerid "
                   + " and cp.status !='C' "
                   + " and cp.referpackageid =t.productpackageid "
                   + " and cp.serviceaccountid in ("+saIds+") "
                   + " and cp.referpackageid in ("+csiPackageArray.toString().substring(1,
                		                                   csiPackageArray.toString().length() - 1) + ") "
                   + " and t.singleprice !=0 "
                   + " group by cp.serviceaccountid, t.singleprice,t.feetype,t.acctitemtypeid,t.customerid "
                   + " order by t.acctitemtypeid ";
		System.out.println("sql----->"+sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProtocolDTO dto =new ProtocolDTO();
				String acctItemTypeId =rs.getString("acctitemtypeid");
				dto.setAcctitemTypeID(acctItemTypeId);
				dto.setCustomerID(rs.getInt("customerid"));
				dto.setFeeType(rs.getString("feetype"));
				dto.setSinglePrice(rs.getDouble("singleprice"));
				if (!result.keySet().contains(acctItemTypeId)){
					Collection col =new ArrayList();
					col.add(dto);
					result.put(acctItemTypeId, col);
				}else{
				    ArrayList col =(ArrayList)result.get(acctItemTypeId);
				    col.add(dto);
				}
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN, "getProtocolDTOColByCustID", e);
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
		return result;
	}
    
	public static Collection CaculateFeeForProtocol(String saIds,int usedMonth,Collection csiPackageArray){
		System.out.println("csiPackageArray-------------->"+csiPackageArray);
		Collection result=new ArrayList();
		Map acctItemMap =getProtocolDTOColBySaIds(saIds,csiPackageArray);
		Iterator acctItemIterator = acctItemMap.entrySet().iterator();
		while (acctItemIterator.hasNext()) {
			Map.Entry item = (Map.Entry) acctItemIterator.next();
			Collection protocolCol =(Collection)item.getValue();
			Iterator protocolItr =protocolCol.iterator();
			String feeType ="";
			double acctItemValue =0;
			int customerID =0;
			while (protocolItr.hasNext()){
				ProtocolDTO dto =(ProtocolDTO)protocolItr.next();
				feeType =dto.getFeeType();
				acctItemValue =acctItemValue+dto.getSinglePrice();
				customerID =dto.getCustomerID();
			}
			if (acctItemValue !=0){
				AccountItemDTO accountItemDTO=new AccountItemDTO();
				accountItemDTO.setFeeType(feeType);
				accountItemDTO.setAcctItemTypeID((String)item.getKey());
				accountItemDTO.setForcedDepositFlag("N");
				accountItemDTO.setCustID(customerID);
				accountItemDTO.setValue((double)(ImmediatePayFeeService.double2long(acctItemValue)*usedMonth/100));
			    if (accountItemDTO.getValue() !=0){
				    result.add(accountItemDTO);
			    }
			}
		}
		System.out.println("result---------->"+result);
		return result;	
	}
	
	/**
	 * Э��ۼƷ�
	 * @param packageCol
	 * @param monthLen
	 * @param customerID
	 * @return
	 */
	public static Collection CaculateFeeForProtocol(int buyNum,Collection packageCol,int monthLen,int customerID)throws ServiceException {
		Collection result=new ArrayList();
		if(packageCol!=null && !packageCol.isEmpty()){
			Map protocolMap=getProtocolDTOColByCustID(customerID);
			Iterator packageIter=packageCol.iterator();
			while(packageIter.hasNext()){
				Integer packateID=(Integer)packageIter.next();
				ProtocolDTO protocolDTO =(ProtocolDTO)protocolMap.get(packateID);
				if(protocolDTO!=null){
					AccountItemDTO accountItemDTO=new AccountItemDTO();
					accountItemDTO.setFeeType(protocolDTO.getFeeType());
					accountItemDTO.setAcctItemTypeID(protocolDTO.getAcctitemTypeID());
					accountItemDTO.setForcedDepositFlag("N");
					accountItemDTO.setCustID(customerID);
					if(isHardDevicePakcage(packateID.intValue())){
						accountItemDTO.setValue((double)(ImmediatePayFeeService.double2long(protocolDTO.getSinglePrice()*buyNum/100)));
					}else{
						accountItemDTO.setValue((double)(ImmediatePayFeeService.double2long(protocolDTO.getSinglePrice())*monthLen*buyNum/100));
					}
					
				    if (accountItemDTO.getValue() !=0){
					    result.add(accountItemDTO);
				    }
				}
			}
		}
		return result;
	}
    /**
     * �ж��Ƿ�Ӳ����Ʒ��
     * @param packageID
     * @return
     * @throws ServiceException
     */
    public static boolean  isHardDevicePakcage(int packageID) throws ServiceException {
    	boolean result=false;
    	String sqlItem = "select count(*) from t_product p,t_packageline l where p.productid=l.productid and p.productclass='H' AND l.packageid="+packageID;
    	int ct =getIntBySQL(sqlItem);
    	if (ct >0) {
			result=true;
		}
    	return result;
    }
    
    public static Collection getCustomerSoft(int custId)  throws ServiceException {
    	Connection con = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		Collection resultCol =new ArrayList();
		String sql =" select t.customerid,t.accountid,t.serviceaccountid,t.productid,t.psid,t.linktodevice1, "
                   +" (select td.serialno from t_terminaldevice td where td.deviceid =t.linktodevice1) linkToDevice1SerialNo, "
                   +" t.linktodevice2, "
                   +" (select td.serialno from t_terminaldevice td where td.deviceid =t.linktodevice2) linkToDevice2SerialNo "
                   +" from t_customerproduct t ,t_terminaldevice t1 "
                   +" where t.customerid ="+custId
                   +" and t.deviceid =t1.deviceid "
                   //+" and t1.deviceclass ='SC' "
                   +" and t1.deviceclass ='STB' "//update by luxi 2010-9-26
                   +" and t.status !='C' ";
		System.out.println("sql------->"+sql);
		try {
			con = DBUtil.getConnection();
			preStmt = con.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
			   ArrayList element =new ArrayList();
			   element.add(rs.getString("customerid"));
			   element.add(rs.getString("accountid"));
		  	   element.add(rs.getString("serviceaccountid"));
			   element.add(rs.getString("productid"));
			   element.add(rs.getString("psid"));
			   element.add(rs.getString("linktodevice1"));
			   element.add(rs.getString("linkToDevice1SerialNo"));
			   element.add(rs.getString("linktodevice2"));
			   element.add(rs.getString("linkToDevice2SerialNo"));
			   resultCol.add(element);
		    }
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.WARN,"getCustomerSoft", e);
			throw new ServiceException("ȡ�ͻ������Ʒʱ��������");
		} finally {
			if (rs !=null){
				try {
					rs.close();
				} catch (SQLException e) {
					LogUtility.log(clazz, LogLevel.WARN,
							"getCustomerSoft", e);
				}
			}
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (SQLException e) {
					LogUtility.log(clazz, LogLevel.WARN,
							"getCustomerSoft", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(clazz, LogLevel.WARN,
							"getCustomerSoft", e);
				}
			}
		}
	    return resultCol;
    }
    
    public static void insertOssAuthorization(OssAuthorizationDTO dto)throws ServiceException{
    	Connection con = null;
		PreparedStatement preStmt = null;
		String sqlItem =" insert into t_ossi_authorization(deviceid,deviceserialno,productid,dt_create,dt_lastmod) "
	           +" values(?,?,?,sysdate,sysdate) " ;
		try {
	 	  	con = DBUtil.getConnection();
			preStmt = con.prepareStatement(sqlItem);
			preStmt.setInt(1, dto.getDeviceID());
			preStmt.setString(2, dto.getDeviceSerialNo());
			preStmt.setInt(3,dto.getProductID());
			preStmt.executeUpdate();
		} catch (Exception e) {
		    LogUtility.log(clazz, LogLevel.WARN,"insertSystemEventRecord", e);
		    e.printStackTrace();
		    throw new ServiceException("�����豸��Ȩ״̬����������");
	    } finally {
		    if (preStmt != null) {
			   try {
				   preStmt.close();
			   } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN, "insertSystemEventRecord", e);
			   }
		    }
		    if (con != null) {
			   try {
				   con.close();
			    } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN,"insertSystemEventRecord", e);
			   }
		    }
	     }
    }
    
    public static void insertDevicepreauthrecord(DevicePreauthRecordDTO dto)throws ServiceException{
    	Connection con = null;
		PreparedStatement preStmt = null;
		String sqlItem =" insert into t_devicepreauthrecord(seqno ,  batchid ,  operationtype ,createtime ,  opid , orgid ,devicemodel,"
                       +" deviceid ,serialno ,productid  ,status ,description   ,dt_create  , dt_lastmod    ) "
			           +" values(devicepreauthrecord_seqno.Nextval,?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate) " ;
		try {
 	  	   con = DBUtil.getConnection();
		   preStmt = con.prepareStatement(sqlItem);
		   preStmt.setInt(1, dto.getBatchId());
		   preStmt.setString(2, dto.getOperationType());
		   preStmt.setTimestamp(3, dto.getCreateTime());
		   preStmt.setInt(4, dto.getOpId());
		   preStmt.setInt(5, dto.getOrgId());
		   preStmt.setString(6, dto.getDeviceModel());
		   preStmt.setInt(7, dto.getDeviceID());
		   preStmt.setString(8, dto.getSerialNo());
		   preStmt.setInt(9, dto.getProductId());
		   preStmt.setString(10, dto.getStatus());
		   preStmt.setString(11, dto.getDescription());
		   preStmt.executeUpdate();
	    } catch (Exception e) {
		    LogUtility.log(clazz, LogLevel.WARN,"insertDevicepreauthrecord", e);
		    e.printStackTrace();
		    throw new ServiceException("�����豸��Ȩ��ϸ��������");
	    } finally {
		    if (preStmt != null) {
			   try {
				   preStmt.close();
			   } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN, "insertDevicepreauthrecord", e);
			   }
		    }
		    if (con != null) {
			   try {
				   con.close();
			    } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN,"insertDevicepreauthrecord", e);
			   }
		    }
	     }
    }
    
    public static void insertSystemEventRecord(Collection recordCol,int eventClass,String status,int operatorid) 
    throws ServiceException{
    	Connection con = null;
		PreparedStatement preStmt = null;
		String sqlItem =" insert into t_systemevent(SEQUENCENO,EVENTCLASS,CUSTOMERID,ACCOUNTID,SERVICEACCOUNTID, "
			           +" PRODUCTID,PSID,STBDEVICEID,STBSERIALNO,SCDEVICEID,SCSERIALNO,OPERATORID,STATUS,DT_CREATE,DT_LASTMOD) "
			           +" values(SYSTEMEVENT_SEQUENCENO.Nextval,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate) " ;
		try {
		   if(recordCol!=null && !recordCol.isEmpty()){
		  	   con = DBUtil.getConnection();
			   preStmt = con.prepareStatement(sqlItem);
			   Iterator recordIter=recordCol.iterator();
			   while(recordIter.hasNext()){
				   ArrayList elementList=(ArrayList)recordIter.next();
				   preStmt.setInt(1,eventClass);
				   preStmt.setInt(2, Integer.parseInt((String)elementList.get(0)));
				   preStmt.setInt(3, Integer.parseInt((String)elementList.get(1)));
				   preStmt.setInt(4, Integer.parseInt((String)elementList.get(2)));
				   preStmt.setInt(5, Integer.parseInt((String)elementList.get(3)));
				   preStmt.setInt(6, Integer.parseInt((String)elementList.get(4)));
				   preStmt.setInt(7, Integer.parseInt((String)elementList.get(5)));
				   preStmt.setString(8,  (String)elementList.get(6) ); 
				   preStmt.setInt(9, Integer.parseInt((String)elementList.get(7)));
				   preStmt.setString(10, (String)elementList.get(8));  
				   preStmt.setInt(11, operatorid);
				   preStmt.setString(12, status);
				   preStmt.addBatch();
			   }
			   preStmt.executeBatch();
		   }
	    } catch (Exception e) {
		    LogUtility.log(clazz, LogLevel.WARN,"insertSystemEventRecord", e);
		    e.printStackTrace();
		    throw new ServiceException("����������Ȩ��������");
	    } finally {
		    if (preStmt != null) {
			   try {
				   preStmt.close();
			   } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN, "insertSystemEventRecord", e);
			   }
		    }
		    if (con != null) {
			   try {
				   con.close();
			    } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN,"insertSystemEventRecord", e);
			   }
		    }
	     }
     }
    
    public static  void insertCsiCustProductInfo(CsiCustProductInfoDTO csiCustInfoDto) throws ServiceException{
    	Connection con = null;
		PreparedStatement preStmt = null;
		String sqlItem =" insert into t_csiCustProductInfo(id , csiid  , action , custproductid ,productid ,referpackageid,referdeviceid,referaccountid ,"
                       +" referserviceaccountid , refercampaignid, referccid ,refergroupbargainid , refercontractno ,"
                       +" destproductid  , referdestdeviceid  , status ,dt_create,dt_lastmod , groupno  , sheafno) " 
                       +" values(CSICUSTPRODUCTINFO_ID.Nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate,?,?) ";
		try {
			con = DBUtil.getConnection();
			preStmt = con.prepareStatement(sqlItem);
			preStmt.setInt(1, csiCustInfoDto.getCsiID());
			preStmt.setString(2, csiCustInfoDto.getAction());
			preStmt.setInt(3, csiCustInfoDto.getCustProductID());
			preStmt.setInt(4, csiCustInfoDto.getProductID());
			preStmt.setInt(5, csiCustInfoDto.getReferPackageID());
			preStmt.setInt(6, csiCustInfoDto.getReferDeviceID());
			preStmt.setInt(7, csiCustInfoDto.getReferAccountID());
			preStmt.setInt(8, csiCustInfoDto.getReferServiceAccountID());
			preStmt.setInt(9, csiCustInfoDto.getReferCampaignID());
			preStmt.setInt(10,csiCustInfoDto.getReferCCID());
			preStmt.setInt(11,csiCustInfoDto.getReferGroupBargainID());
			preStmt.setString(12,csiCustInfoDto.getReferContractNo());
			preStmt.setInt(13,csiCustInfoDto.getDestProductID());
			preStmt.setInt(14,csiCustInfoDto.getReferDestDeviceID());
			preStmt.setString(15, csiCustInfoDto.getStatus());
			preStmt.setInt(16, csiCustInfoDto.getGroupNo());
			preStmt.setInt(17, csiCustInfoDto.getSheafNo());
			preStmt.executeUpdate();
		} catch (Exception e) {
		    LogUtility.log(clazz, LogLevel.WARN,"insertCsiCustProductInfo", e);
		    e.printStackTrace();
		    throw new ServiceException("������������ϸ����");
	    } finally {
		    if (preStmt != null) {
			   try {
				   preStmt.close();
			   } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN, "insertCsiCustProductInfo", e);
			   }
		    }
		    if (con != null) {
			   try {
				   con.close();
			    } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN,"insertCsiCustProductInfo", e);
			   }
		    }
	     }
    }
    
    public static void updatePreAuthorizationForTerminalDevice(String flag, int deviceID)throws ServiceException{
    	Connection con = null;
		PreparedStatement preStmt = null;
		String sqlItem ="update t_terminaldevice t set t.preauthorization=? where t.deviceid =?";
		try {
			con = DBUtil.getConnection();
			preStmt = con.prepareStatement(sqlItem);
			preStmt.setString(1, flag);
			preStmt.setInt(2,deviceID);
			preStmt.executeUpdate();
		} catch (Exception e) {
		    LogUtility.log(clazz, LogLevel.WARN,"updatePreAuthorizationForTerminalDevice", e);
		    e.printStackTrace();
		    throw new ServiceException("�����豸Ԥ��Ȩ��־����");
	    } finally {
		    if (preStmt != null) {
			   try {
				   preStmt.close();
			   } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN, "updatePreAuthorizationForTerminalDevice", e);
			   }
		    }
		    if (con != null) {
			   try {
				   con.close();
			    } catch (SQLException e) {
				   LogUtility.log(clazz, LogLevel.WARN,"updatePreAuthorizationForTerminalDevice", e);
			   }
		    }
	     }	
    }
    
    public static String getAsTableShowByOrgIDAndDistrictID(String districtID,String orgID){
		String returnSql="";
		if(districtID!=null && !"".equals(districtID)){
			returnSql= "select id from t_districtsetting connect by prior ID=BELONGTO  start with ID="+districtID ;
		}
		if(orgID!=null && !"".equals(orgID)){
			if(!"".equals(returnSql)){
				returnSql=returnSql+" INTERSECT  " + "select id from t_districtsetting connect by prior ID=BELONGTO  start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+orgID+")"; 
			}else{
				returnSql="select id from t_districtsetting connect by prior ID=BELONGTO  start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+orgID+")" ;
			}
		}
		return "("+returnSql+")";
	}
    
    public static AccountDTO getAccountDTOByCustID(int custID) throws  ServiceException {
		String sql = "select * from t_account where accountid=(select max(accountID) from t_account where customerid="+custID+" and status<>'C')" ;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		AccountDTO dto = new AccountDTO();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto = DtoFiller.fillAccountDTO(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("��ÿͻ��ʻ�����");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e1) {

				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e1) {

				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {

				}
			}
		}
		return dto;
	}
    
    public static String getAcctItemTypeIDyConfig(String name,String key ,String vlaue)throws ServiceException {
		String accTitemTypeID=getStringBySQL("select acctitemtypeid from T_CustomizeFeeConfiguration where  name='"+name+"' and key='"+key+"' and value='"+vlaue+"'");
		return accTitemTypeID;
    }
}
