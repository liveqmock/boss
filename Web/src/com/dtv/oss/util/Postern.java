package com.dtv.oss.util;

/**
 * 2006-5-11增加public static Map getOrgTree(int orgID,String orgType)方法,便于得到各种TYPE的ORG树,
 * 
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.BidimConfigSettingValueDTO;
import com.dtv.oss.dto.BossConfigurationDTO;
import com.dtv.oss.dto.BrconditionDTO;
import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.dto.BundlePrepaymentDTO;
import com.dtv.oss.dto.CAQueueDTO;
import com.dtv.oss.dto.CARecvDTO;
import com.dtv.oss.dto.CASentDTO;
import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.dto.CampaignAgmtCampaignDTO;
import com.dtv.oss.dto.CampaignCondCampaignDTO;
import com.dtv.oss.dto.CampaignCondPackageDTO;
import com.dtv.oss.dto.CampaignCondProductDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CampaignPaymentAwardDTO;
import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.ContractToPackageDTO;
import com.dtv.oss.dto.CsiActionReasonDetailDTO;
import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.dto.CsiCustProductInfoDTO;
import com.dtv.oss.dto.CsiProcessLogDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerBillingRuleDTO;
import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProblemDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.DeviceClassDTO;
import com.dtv.oss.dto.DeviceModelDTO;
import com.dtv.oss.dto.DeviceBatchPreauthDTO;
import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.dto.DisplayControlDTO;
import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.dto.DynamicFormSettingDTO;
import com.dtv.oss.dto.DynamicShowAttributesDTO;
import com.dtv.oss.dto.ExportDataColumnDefineDTO;
import com.dtv.oss.dto.ExportDataHeadDefineDTO;
import com.dtv.oss.dto.FeeSplitPlanDTO;
import com.dtv.oss.dto.FeeSplitPlanItemDTO;
import com.dtv.oss.dto.GroupBargainDTO;
import com.dtv.oss.dto.GroupBargainDetailDTO;
import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.MarketSegmentDTO;
import com.dtv.oss.dto.MenuConfigurationDTO;
import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.dto.NewCustomerMarketInfoDTO;
import com.dtv.oss.dto.OldCustAccountInfoDTO;
import com.dtv.oss.dto.OldCustomerInfoDTO;
import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.dto.ProtocolDTO;
import com.dtv.oss.dto.QueryDTO;
import com.dtv.oss.dto.ServiceAccountDTO;
import com.dtv.oss.dto.ServiceDTO;
import com.dtv.oss.dto.SystemPrivilegeDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.dto.UserPointsExchangeCondDTO;
import com.dtv.oss.dto.UserPointsExchangeGoodsDTO;
import com.dtv.oss.dto.PrintBlockDTO;
import com.dtv.oss.dto.PrintBlockDetailDTO;
import com.dtv.oss.dto.PrintConfigDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.CommonSettingValue;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.ChnAmt;
import com.dtv.oss.dto.JqPrintConfigDTO;

public class Postern {
	private static Map commonSettingDataCache;// 所有公用数据缓存

	private static Map districtSettingCache;// 所有区域信息缓存

	private static Map orgTreeDistrictSettingCache;// 根据组织机构分类的树形区域信息

	private static Map organizationCache;// 组织机构信息

	private static Map allParentCompanysMap; // = new LinkedHashMap();

	private static Map methodOfPaymentCache;// 付费方式缓存

	private static Map deviceClassCache;// 设备类型缓存

	private static Map deviceModelCache;// 设备型号缓存

	private static Map depotCache;// = 仓库缓存

	private static Map activityCache;// 活动缓存

	private static Map goodsCache;// 货物缓存

	private static Map allFilialesMap;

	private static Map mapAccountTypeOpeningMop;// 付费类型

	private static Map mapPaymentTypeOpeningNoWrapMop;// 未包装的付费方式

	private static Map mapCsiPaymentTypeMop; // 受理单类型的付费方式

	private static Map mapPaymentTypeMop;// 非虚拟货币支付方式

	private static Map mapContractPaymentMop;// 合同预付费方式,wangpeng@20080226

	private static Map mapVPaymentTypeMop; // 虚拟货币付费方式

	private static Map systemEventCache; // 事件名称

	private static Map operatorNameCache; // 操作员名称

	private static Map productNameCache; // 营运商产品

	private static Map systemEventReasonCache; // 系统事件原因

	private static Map commandNameCache; // 命令名称

	private static Map ldapProductNameCache; // 命令名称

	private static Map methodOfPaymentCacheForMarket;

	private static Map districtIDMap;

	private static Map organizationIDMap;
	
	private static Map orgGovernedDistrictMap;
	
	private static Map orgGovernedDistrictMap_1;

	private static Map displayControlCache; // 页面功能点配置信息表

	private static Map fieldNameCache; // 不同版本的显示字段名称

	private static Map systemsettingCache; // 系统全局配置信息

	private static Map systemModuleCache; // 系统模块名

	private static Map systemImgCache; // 系统图片

	private static Map serviceIconsCache; // 系统图片

	private static BossConfigurationDTO bossConfiguration; // 系统配置信
	
	private static Map fapiaoDepotCache;// = new LinkedHashMap();
	static {
		reInit();
	}

	// ------Posten重构代码从这里开始 可重的代码拷贝到这个区域里面--------START----
	/**
	 * 根据TerminalDevice的DeviceId得到dto
	 */
	public static TerminalDeviceDTO getTerminalDeviceByID(int id) {
		TerminalDeviceDTO dto = new TerminalDeviceDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_TerminalDevice where DEVICEID = ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillTerminalDeviceDTO(rs);
			}
		} catch (Exception e) {
				System.out.println("getTerminalDeviceByID exception:"
						+ e.getMessage());
				e.printStackTrace();
			dto = null;
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

	public static TerminalDeviceDTO getTerminalDeviceBySerialNo(String serialNo) {
		TerminalDeviceDTO dto = new TerminalDeviceDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_TerminalDevice where  serialno=? ");
			stmt.setString(1, serialNo);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillTerminalDeviceDTO(rs);
			}
		} catch (Exception e) {
				System.out.println("getTerminalDeviceByID exception:"
						+ e.getMessage());
				e.printStackTrace();
			dto = null;
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
	 * 根据数字用户证号取得对应的客户信息DTO
	 */
	public static CustomerDTO getCustomerByID(int id) {
		CustomerDTO dto = new CustomerDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_customer where customerid= ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillCustomerDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getCustomerByID", e);
			dto = null;
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getCustomerByID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getCustomerByID", e);
				}
			}
		}
		return dto;

	}

	public static Collection getNewCustAccountInfoByCsiId(int csiId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection lst = new ArrayList();
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_newcustaccountinfo where csiid= ? ");
			stmt.setInt(1, csiId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				NewCustAccountInfoDTO dto = DtoFiller
						.fillNewCustAccountInfoDTO(rs);
				lst.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNewCustAccountInfoByCsiId", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getNewCustAccountInfoByCsiId", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getNewCustAccountInfoByCsiId", e);
				}
			}
		}
		return lst;
	}

	public static Collection getOldCustAccountInfoByCsiId(int csiId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection lst = new ArrayList();
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_oldcustaccountinfo where csiid= ? ");
			stmt.setInt(1, csiId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				OldCustAccountInfoDTO dto = DtoFiller
						.fillOldCustAccountInfoDTO(rs);
				lst.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getOldCustAccountInfoByCsiId", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getOldCustAccountInfoByCsiId", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getOldCustAccountInfoByCsiId", e);
				}
			}
		}
		return lst;
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
			LogUtility.log(Postern.class, LogLevel.WARN, "getMapBySQL", e);
			LogUtility.log(Postern.class, LogLevel.DEBUG, "相关SQL=" + sql);
			map.clear();
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
					LogUtility.log(Postern.class, LogLevel.WARN, "getMapBySQL",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "getMapBySQL",
							e);
				}
			}
		}
		return map;
	}

	public static CustServiceInteractionDTO getCsiDTOByCSIID(int id) {
		CustServiceInteractionDTO dto = new CustServiceInteractionDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select *  from t_custserviceinteraction  where  id= ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillCustServiceInteractionDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getCsiDTOByCSIID", e);
			dto = null;
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getCsiDTOByCSIID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getCsiDTOByCSIID", e);
				}
			}
		}
		return dto;

	}

	public static String getOpenSourceTypeByCsiID(int id) {
		return getStringBySQL("select OPENSOURCETYPE from T_NEWCUSTOMERINFO where CSIID="
				+ id);
	}

	// 该方法供DynamicShowAttributeDataInfo使用 --david.yang
	public static String getStringBySQL(String sql, String[] parameter) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String str = "";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			for (int i = 0; i < parameter.length; i++) {
				if (parameter[i] == null)
					continue;
				stmt.setString(i + 1, parameter[i]);
				System.out.println("====parameter[" + i + "]=========="
						+ parameter[i]);
			}
			System.out.println("=======sql========" + sql);

			rs = stmt.executeQuery();
			if (rs.next()) {
				str = rs.getString(1);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getStringBySQL", e);
			str = null;
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getStringBySQL", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getStringBySQL", e);
				}
			}
		}
		return str;
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
			LogUtility.log(Postern.class, LogLevel.WARN, "getStringBySQL", e);
			str = null;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getStringBySQL", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getStringBySQL", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getStringBySQL", e);
				}
			}
		}
		return str;
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
			if (rs == null)
				return -1;
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getIntBySQL", e);
			i = Integer.MIN_VALUE;
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
					LogUtility.log(Postern.class, LogLevel.WARN, "getIntBySQL",
							e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "getIntBySQL",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "getIntBySQL",
							e);
				}
			}
		}
		return i;
	}

	private static double getDoubleBySQL(String sql) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		double d = 0;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				d = rs.getDouble(1);
			}
			if (rs == null)
				return 0;
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getDoubleBySQL", e);
			d = 0;
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDoubleBySQL", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDoubleBySQL", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDoubleBySQL", e);
				}
			}
		}
		return d;
	}

	/**
	 * 根据可管理客户的组织id初始化分区信息
	 */
	public static void initHaveCustomerDistrictSetting() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("SELECT  ORGID FROM T_ORGANIZATION WHERE HasCustomerFlag='Y' and STATUS='V'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				orgTreeDistrictSettingCache.put(String.valueOf(rs
						.getInt("ORGID")), getDistrictSettingByOrgID(rs
						.getInt("ORGID")));
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"initHaveCustomerDistrictSetting", e);
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
				} catch (SQLException sqle) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"initHaveCustomerDistrictSetting", sqle);
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"initHaveCustomerDistrictSetting", sqle);
				}
				conn = null;
			}
		}
	}

	/**
	 * @param orgID
	 * @return 根据当前的组织id对应的所有区域父子关系映射表
	 *         返回的MAP中,key是所有父节点的ID,value为一个arrayList,为KEY父节点下所有子节点的ID
	 */
	public static Map getDistrictSettingForCreateTree() {
		return districtIDMap;
	}

	/**
	 * 返回organization的ID关系表
	 * 
	 * @return
	 */
	public static Map getOrganizationForCreateTree() {
		return organizationIDMap;
	}

	/**
	 * @param orgID
	 * @return 根据当前的组织id获得区域结构列表
	 */
	public static Map getDistrictSettingByOrgID(int orgID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String queryDistrictSettingSQL = "select ID, NAME ,BELONGTO from T_DISTRICTSETTING WHERE STATUS='V' connect by prior ID=BELONGTO and STATUS='V' start with "
				+ "ID IN (SELECT  DISTRICTID from T_ORGGOVERNEDDISTRICT  WHERE ORGID="
				+ orgID + ")";
		Map districtSettingMap = new LinkedHashMap();
		Map currentBelongToMap = new LinkedHashMap();
		String startTree = "|-";
		String middleTree = "";
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(queryDistrictSettingSQL);
			rs = stmt.executeQuery();
			String id = "";
			String name = "";
			String belongTo = "";
			int currentLength = 0;
			while (rs.next()) {
				id = String.valueOf(rs.getInt("ID"));
				name = rs.getString("NAME");
				belongTo = String.valueOf(rs.getInt("BELONGTO"));
				if (currentBelongToMap.containsKey(belongTo)) {
					int currentLevel = Integer
							.parseInt((String) currentBelongToMap.get(belongTo));
					if (!currentBelongToMap.containsKey(new Integer(id))) {
						currentBelongToMap.put(id, String
								.valueOf(currentLevel + 1));
					}
					String tempString = "";
					for (int i = 0; i < currentLevel; i++) {
						tempString += "-";
					}
					middleTree = tempString;
					districtSettingMap.put(id, startTree + middleTree + name);
				} else {
					districtSettingMap.put(id, startTree + middleTree + name);
					currentBelongToMap.put(id, String
							.valueOf(currentLength + 1));
				}
			}
		} catch (SQLException e) {
			districtSettingMap = null;
			currentBelongToMap = null;
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDistrictSettingByOrgID", e);
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
				} catch (SQLException sqle) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
			}
		}
		return districtSettingMap;
	}

	/**
	 * @param orgID
	 * @return 根据操作员当前的组织id取得组织管理的区域信息
	 */
	public static Map getChargeDistrictSettingByOpOrgID(int opOrgID) {
		Map currentOrgDistrictSettingMap = null;
		currentOrgDistrictSettingMap = (Map) orgTreeDistrictSettingCache
				.get(String.valueOf(getParentHasCustomerOrgID(opOrgID)));
		return currentOrgDistrictSettingMap;
	}

	public static int getRootOrganizationID() {
		java.util.Iterator it = organizationCache.keySet().iterator();
		while (it.hasNext()) {
			Organization org = (Organization) organizationCache.get(it.next());
			Organization parentOrg = (Organization) organizationCache.get(org
					.getParentOrgID()
					+ "");
			if (parentOrg == null) {
				return org.getOrgID();
			}
		}
		return 0;
	}

	/**
	 * 原有方法被我修改了,原来方法注释在下面,不用到数据库中去load N次,直接从缓存中检索, 侯
	 * 07-1-16,原方法保存一星期,如果23号之前没有问题,请将注释的方法删除.
	 * 
	 * @param orgID
	 * @return 根据当前操作员的组织id通过递归的方式找到能够管理客户的组织id
	 */
	public static int getParentHasCustomerOrgID(int orgID) {
		Organization org = (Organization) organizationCache.get(orgID + "");
		if ("Y".equals(org.getHasCustomerFlag())) {
			return org.getOrgID();
		} else {
			return getParentHasCustomerOrgID(org.getParentOrgID());
		}
	}

	// ------Posten重构代码到这里结束 可重的代码拷贝到这个区域里面--------END-------
	public static void reInit() {
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"reInit======================");

		commonSettingDataCache = new LinkedHashMap();
		districtSettingCache = new LinkedHashMap();
		organizationCache = new LinkedHashMap();
		orgTreeDistrictSettingCache = new LinkedHashMap();
		allParentCompanysMap = new LinkedHashMap();
		methodOfPaymentCache = new LinkedHashMap();
		deviceClassCache = new LinkedHashMap();
		deviceModelCache = new LinkedHashMap();
		activityCache = new LinkedHashMap();
		goodsCache = new LinkedHashMap();
		mapAccountTypeOpeningMop = new LinkedHashMap();
		mapPaymentTypeOpeningNoWrapMop = new LinkedHashMap();
		mapCsiPaymentTypeMop = new HashMap();
		allFilialesMap = new LinkedHashMap();
		mapVPaymentTypeMop = new LinkedHashMap();
		systemEventCache = new LinkedHashMap();
		operatorNameCache = new LinkedHashMap();
		productNameCache = new LinkedHashMap();
		systemEventReasonCache = new LinkedHashMap();
		methodOfPaymentCacheForMarket = new LinkedHashMap();
		commandNameCache = new LinkedHashMap();
		ldapProductNameCache = new LinkedHashMap();
		districtIDMap = new LinkedHashMap();
		organizationIDMap = new LinkedHashMap();
		orgGovernedDistrictMap =new HashMap();
		orgGovernedDistrictMap_1 =new HashMap();
		displayControlCache = new LinkedHashMap();
		fieldNameCache = new HashMap();
		systemsettingCache = new HashMap();

		systemModuleCache = new HashMap();
		systemImgCache = new HashMap();
		serviceIconsCache = new HashMap();
		bossConfiguration = new BossConfigurationDTO();

		fapiaoDepotCache = new LinkedHashMap();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			// ------Posten中的静态初始化块的重构代码从这里开始 可重的代码拷贝到这个区域里面--------START----

			// 初始化 commonSettingDataCache ，数据来源是t_commonsettingdata表
			String sql = "select name,key,value,defaultOrNot, priority,grade,status  from t_commonsettingdata  order by name,priority,value";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map map = (Map) commonSettingDataCache
						.get(rs.getString("name"));
				if (map == null) {
					map = new LinkedHashMap();
					commonSettingDataCache.put(rs.getString("name"), map);
				}
				map.put(rs.getString("key"), fillCommonSettingDataDTO(rs));
				// System.out.println("map=====61071111======"+map);
			}
			rs.close();

			// 页面可配置元素缓存
			sql = "select * from t_systemdisplayControl order by controlid,disposeOrder";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				DisplayControlDTO dto = DtoFiller.fillDisplayControlDTO(rs);
				String controlID = rs.getString("controlID");
				ArrayList controls = null;

				if (!displayControlCache.containsKey(controlID)) {
					controls = new ArrayList();
					displayControlCache.put(controlID, controls);
				}
				controls = (ArrayList) displayControlCache.get(controlID);
				controls.add(dto);
			}
			// initialize allFilialesMap
			for (Iterator i = organizationCache.values().iterator(); i
					.hasNext();) {
				Organization og = (Organization) i.next();
				if (Constant.ORGANIZATION_TYPE_FILIALE.equals(og.getOrgType())) {
					allFilialesMap.put("" + og.getOrgID(), og);
				}
			}
			rs.close();
			
			// 初始化付费类型
			String sqlAccount = "select * from t_methodofpayment where AccountFlag='Y' and status='V'";
			rs = stmt.executeQuery(sqlAccount);
			while (rs.next()) {
				if (mapAccountTypeOpeningMop == null) {
					mapAccountTypeOpeningMop = new LinkedHashMap();
				}
				mapAccountTypeOpeningMop.put(
						String.valueOf(rs.getInt("MOPID")), rs
								.getString("NAME"));
			}
			rs.close();

			// 初始化付费方式
			String sqlPayment = "select * from t_methodofpayment";
			rs = stmt.executeQuery(sqlPayment);
			while (rs.next()) {
				if (mapPaymentTypeOpeningNoWrapMop == null) {
					mapPaymentTypeOpeningNoWrapMop = new LinkedHashMap();
				}
				mapPaymentTypeOpeningNoWrapMop.put(String.valueOf(rs
						.getInt("MOPID")), rs.getString("NAME"));

				if (rs.getString("CSITYPELIST") != null
						&& "V".equals(rs.getString("status"))
						&& "Y".equals(rs.getString("PaymentFlag"))) {
					String[] csitypeList = rs.getString("CSITYPELIST").split(
							",");
					for (int i = 0; i < csitypeList.length; i++) {
						if (!mapCsiPaymentTypeMop.containsKey(csitypeList[i])) {
							mapCsiPaymentTypeMop.put(csitypeList[i],
									new LinkedHashMap());
						}
					}
				}
			}
			rs.close();

			rs = stmt
					.executeQuery("select * from t_methodofpayment where PaymentFlag ='Y' and status='V' order by mopid");
			while (rs.next()) {
				if (rs.getString("CSITYPELIST") != null) {
					String[] csitypeList = rs.getString("CSITYPELIST").split(
							",");
					for (int i = 0; i < csitypeList.length; i++) {
						if (mapCsiPaymentTypeMop.containsKey(csitypeList[i])) {
							String cashFlag = (rs.getString("CASHFLAG") == null) ? "N"
									: rs.getString("CASHFLAG");
							String openingMopID = rs.getInt("MOPID") + "-"
									+ rs.getString("paytype") + "_" + cashFlag;
							((Map) mapCsiPaymentTypeMop.get(csitypeList[i]))
									.put(openingMopID, rs.getString("NAME"));
						}
					}
				}
			}
			rs.close();

			// initialize T_MethodOfPayment
			rs = stmt.executeQuery("select * from T_MethodOfPayment");
			while (rs.next()) {
				MethodOfPaymentDTO dto = new MethodOfPaymentDTO();
				dto.setMopID(rs.getInt("mopID"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setPayType(rs.getString("payType"));
				dto.setPartnerID(rs.getInt("partnerID"));
				dto.setDtCreate(rs.getTimestamp("dt_Create"));
				dto.setDtLastmod(rs.getTimestamp("dt_Lastmod"));
				dto.setStatus(rs.getString("status"));
				dto.setCashFlag(rs.getString("CASHFLAG"));
				dto.setPaymentflag(rs.getString("Paymentflag"));
				methodOfPaymentCache.put("" + dto.getMopID(), dto);
			}
			rs.close();
			// initialize T_MethodOfPayment
			rs = stmt
					.executeQuery("select * from T_MethodOfPayment where paymentflag = 'Y' and status='V'");
			while (rs.next()) {
				MethodOfPaymentDTO dto = new MethodOfPaymentDTO();
				dto.setMopID(rs.getInt("mopID"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setPayType(rs.getString("payType"));
				dto.setPartnerID(rs.getInt("partnerID"));
				dto.setDtCreate(rs.getTimestamp("dt_Create"));
				dto.setDtLastmod(rs.getTimestamp("dt_Lastmod"));
				dto.setStatus(rs.getString("status"));
				dto.setCashFlag(rs.getString("CASHFLAG"));
				methodOfPaymentCacheForMarket.put("" + dto.getMopID(), dto
						.getName());
			}
			rs.close();

			// 按照可管理客户的组织ID初始化所有的分区信息
			initHaveCustomerDistrictSetting();

			// 初始化所有分区信息
			rs = stmt.executeQuery("select * from t_districtsetting ");
			while (rs.next()) {
				DistrictSetting ds = new DistrictSetting();
				ds.setId(rs.getInt("id"));
				ds.setDistrictCode(rs.getString("districtCode"));
				ds.setName(rs.getString("name"));
				ds.setType(rs.getString("type"));
				ds.setBelongTo(rs.getInt("belongTo"));
				districtSettingCache.put("" + ds.getId(), ds);
			}
			rs.close();

			// 初始化所有组织信息organizationCache
			rs = stmt
					.executeQuery("select * from t_organization where status='V'");
			while (rs.next()) {
				Organization og = new Organization();
				DtoFiller.fillOrganizationDTO(rs, og, null);
				organizationCache.put(String.valueOf(og.getOrgID()), og);
			}
			rs.close();
			// // 设备类型 T_DeviceClass
			rs = stmt.executeQuery("select * from T_DeviceClass ");
			while (rs.next()) {
				deviceClassCache.put(rs.getString("DeviceClass"), rs
						.getString("Description"));
			}
			rs.close();
			// allParentCompanysMap
			rs = stmt
					.executeQuery("select * from t_organization where status='V' and (orgtype='C' or orgtype='B')");
			while (rs.next()) {
				Organization og = new Organization();
				og.setOrgID(rs.getInt("orgID"));
				og.setParentOrgID(rs.getInt("parentOrgID"));
				og.setOrgName(rs.getString("orgName"));
				og.setOrgDesc(rs.getString("orgDesc"));
				og.setOrgType(rs.getString("orgType"));
				og.setOrgSubType(rs.getString("orgSubType"));
				og.setRank(rs.getString("rank"));
				og.setRegisterNo(rs.getString("registerNo"));
				// allParentCompanysMap.put("" + og.getOrgID(), og);
				allParentCompanysMap.put("" + og.getOrgID(), og.getOrgName());
			}
			rs.close();
			// 设备型号 t_devicemodel
			rs = stmt
					.executeQuery("select * from t_devicemodel order by deviceclass,devicemodel ");
			while (rs.next()) {
				String devicemodel = rs.getString("devicemodel");
				deviceModelCache.put(devicemodel, devicemodel);
			}
			rs.close();

			rs = stmt
					.executeQuery("select * from t_userpointsexchangeactivity ");
			while (rs.next()) {
				activityCache.put("" + rs.getInt("ID"), rs.getString("NAME"));
			}
			rs.close();
			// t_userpointsexchangegoods
			rs = stmt.executeQuery("select * from t_userpointsexchangegoods");
			while (rs.next()) {
				goodsCache.put("" + rs.getInt("ID"), rs.getString("NAME"));
			}
			rs.close();
			// 系统事件定义 t_systemeventdef
			rs = stmt.executeQuery("select * from t_systemeventdef ");
			while (rs.next()) {
				systemEventCache.put("" + rs.getInt("eventclass"), rs
						.getString("eventname"));
			}
			rs.close();
			// 系统事件定义 t_systemeventdef
			rs = stmt.executeQuery("select * from t_ldapcommand ");
			while (rs.next()) {
				commandNameCache.put(rs.getString("COMMANDNAME"), rs
						.getString("COMMANDNAME"));
			}
			rs.close();
			// 系统事件定义 t_systemeventdef
			rs = stmt.executeQuery("select * from t_ldapproduct ");
			while (rs.next()) {
				ldapProductNameCache.put(rs.getString("PRODUCTNAME"), rs
						.getString("PRODUCTNAME"));
			}
			rs.close();
			// 系统事件定义 t_systemlog
			rs = stmt
					.executeQuery("select  t.operatorid,t.operatorname from t_operator t");
			while (rs.next()) {
				operatorNameCache.put("" + rs.getInt("operatorid"), rs
						.getString("operatorname"));
			}
			rs.close();
			// 产品 t_product
			rs = stmt.executeQuery("select * from t_product ");
			while (rs.next()) {
				productNameCache.put("" + rs.getInt("productid"), rs
						.getString("productname"));
			}
			rs.close();
			// 系统事件原因
			rs = stmt.executeQuery("select * from t_systemeventreason ");
			while (rs.next()) {
				systemEventReasonCache.put(rs.getString("reasoncode") + ";"
						+ rs.getInt("eventclass"), rs.getString("reasondesc"));
			}
			rs.close();

			/**
			 * districtIDMap/organizationIDMap用于生成页面javascript树的数据,这两个MAP中保存了两棵树的结构,键为ID,值为LIST(键所拥有的孩子ID列表)
			 */
			// 取districtsetting关系
			String queryDistrictSettingSQL = "select ID, BELONGTO from T_DISTRICTSETTING WHERE STATUS='V' connect by prior ID=BELONGTO start with BELONGTO = 0 ORDER BY districtcode DESC";

			rs = stmt.executeQuery(queryDistrictSettingSQL);
			int id = 0;
			int belongTo = 0;
			while (rs.next()) {
				id = rs.getInt("ID");
				belongTo = rs.getInt("BELONGTO");
				if (districtIDMap.get(new Integer(belongTo)) != null) {
					List currentList = (List) districtIDMap.get(new Integer(
							belongTo));
					if (!currentList.contains(new Integer(id))) {
						currentList.add(new Integer(id));
					}
				} else {
					List currentList = new ArrayList();
					currentList.add(new Integer(id));
					districtIDMap.put(new Integer(belongTo), currentList);
				}

			}
			rs.close();
			/**
			 * 
			 */
			String queryOrganizationSQL = "select orgid ,parentorgid from t_organization connect by prior orgid=parentorgid start with parentorgid = 0"
					+ " ORDER BY orgcode DESC,orgtype,orgsubtype,orgid";
	
			rs = stmt.executeQuery(queryOrganizationSQL);
			int orgId = 0;
			int parentOrgId = 0;
			while (rs.next()) {
				orgId = rs.getInt("orgid");
				parentOrgId = rs.getInt("parentorgid");
				if (organizationIDMap.get(new Integer(parentOrgId)) != null) {
					List currentList = (List) organizationIDMap
							.get(new Integer(parentOrgId));
					if (!currentList.contains(new Integer(orgId))) {
						currentList.add(new Integer(orgId));
					}
				} else {
					List currentList = new ArrayList();
					currentList.add(new Integer(orgId));
					organizationIDMap
							.put(new Integer(parentOrgId), currentList);
				}

			}
			rs.close();
			
			rs = stmt.executeQuery("select t.orgid from t_organization t");
			while(rs.next()){
				orgGovernedDistrictMap.put(new Integer(rs.getInt("orgid")),new ArrayList());
				orgGovernedDistrictMap_1.put(new Integer(rs.getInt("orgid")),new ArrayList());
			}
			rs.close();
			
			Iterator orgiter =orgGovernedDistrictMap.keySet().iterator();
			while (orgiter.hasNext()){
				int orgid =((Integer)orgiter.next()).intValue();
				ArrayList distList =(ArrayList)orgGovernedDistrictMap.get(new Integer(orgid));
				
				rs =stmt.executeQuery("select ID, NAME ,BELONGTO from T_DISTRICTSETTING WHERE STATUS='V' connect by prior ID=BELONGTO and STATUS='V' start with "
						            + "ID IN (SELECT  DISTRICTID from T_ORGGOVERNEDDISTRICT  WHERE ORGID="
						            + orgid + ")");
				while (rs.next()) {
					int distId =rs.getInt("ID");
					distList.add(new Integer(distId));
				}
			}
			rs.close();
			
			Iterator orgiter_1 =orgGovernedDistrictMap_1.keySet().iterator();
			while (orgiter_1.hasNext()){
				int orgid =((Integer)orgiter_1.next()).intValue();
				ArrayList distList =(ArrayList)orgGovernedDistrictMap_1.get(new Integer(orgid));
				
				rs =stmt.executeQuery("Select distinct (id), bbb.belongto, name "
                                     +" From T_DISTRICTSETTING bbb where bbb.status ='V' connect by prior belongto = Id start with ID in (select ogd.districtid from t_orggoverneddistrict ogd where orgid ="+orgid+")" 
                                     +" union "
                                     +" Select distinct (id), ccc.belongto, name "
                                     +" From T_DISTRICTSETTING ccc where ccc.status ='V' connect by prior Id = belongto start with belongto in (select ogd.districtid from t_orggoverneddistrict ogd where orgid ="+orgid+")");
				while (rs.next()) {
					int distId =rs.getInt("ID");
					distList.add(new Integer(distId));
				}
			}
			rs.close();

			rs = stmt
					.executeQuery("select t.FIELDINTERID , t.FieldName from t_fieldconfig t where t.status ='V'");
			while (rs.next()) {
				fieldNameCache.put(rs.getString("FIELDINTERID"), rs
						.getString("FieldName"));
			}
			rs.close();

			rs = stmt
					.executeQuery("select t.name, t.value from t_systemsetting t where t.status ='V'");
			while (rs.next()) {
				systemsettingCache.put(rs.getString("name"), rs
						.getString("value"));
			}
			rs.close();

			rs = stmt
					.executeQuery("select t.FIELDINTERID , t.FieldName from t_fieldconfig t where t.status ='V'");
			while (rs.next()) {
				fieldNameCache.put(rs.getString("FIELDINTERID"), rs
						.getString("FieldName"));
			}
			rs.close();

			rs = stmt
					.executeQuery("select distinct(ModuleName) as moduledesc from t_SystemLog order by ModuleName");

			while (rs.next()) {
				if (rs.getString("moduledesc") != null
						&& rs.getString("moduledesc").length() > 0)
					systemModuleCache.put(rs.getString("moduledesc"), rs
							.getString("moduledesc"));
			}
			rs.close();

			rs = stmt
					.executeQuery("select imgListTop,imgLoginLogo,imgTitle from t_bossconfiguration ");

			Blob blob = null;
			byte buf[] = null;
			long pos = 1;
			while (rs.next()) {
				if (rs.getBlob("imgListTop") != null) {
					blob = rs.getBlob("imgListTop");
					buf = blob
							.getBytes(pos, new Long(blob.length()).intValue());
					systemImgCache.put("imgListTop", buf);
				}
				if (rs.getBlob("imgLoginLogo") != null) {
					blob = rs.getBlob("imgLoginLogo");
					buf = blob
							.getBytes(pos, new Long(blob.length()).intValue());
					systemImgCache.put("imgLoginLogo", buf);
				}
				if (rs.getBlob("imgTitle") != null) {
					blob = rs.getBlob("imgTitle");
					buf = blob
							.getBytes(pos, new Long(blob.length()).intValue());
					systemImgCache.put("imgTitle", buf);
				}
			}
			rs.close();

			rs = stmt.executeQuery("select serviceid , icons from t_service ");

			while (rs.next()) {
				if (rs.getBlob("icons") != null) {
					blob = rs.getBlob("icons");
					buf = blob
							.getBytes(pos, new Long(blob.length()).intValue());
					if (rs.getInt("serviceid") == 1)
						serviceIconsCache.put("dtv", buf);
					if (rs.getInt("serviceid") == 2)
						serviceIconsCache.put("band", buf);
					if (rs.getInt("serviceid") == 3)
						serviceIconsCache.put("voip", buf);
					if (rs.getInt("serviceid") == 4)
						serviceIconsCache.put("inc", buf);
					if (rs.getInt("serviceid") == 5)
						serviceIconsCache.put("itv", buf);
					if (rs.getInt("serviceid") == 6)
						serviceIconsCache.put("atv", buf);
				}
			}
			rs.close();

			rs = stmt
					.executeQuery("select * from t_bossconfiguration t Where Rownum=1");

			if (rs.next()) {
				bossConfiguration = DtoFiller.fillBossConfigurationDTO(rs);
			}

			rs.close();

			// 发票仓库 t_fapiaodeopt
			rs = stmt.executeQuery("select * from t_fapiaodeopt ");
			while (rs.next()) {
				fapiaoDepotCache.put("" + rs.getInt("FPDEPORTID"), rs
						.getString("FPDEPOTNAME"));
			}
			rs.close();
			
			// ------Posten中的静态初始化块的重构代码到这里结束 可重的代码拷贝到这个区域里面--------END------
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "reInit", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "reInit", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "reInit", e);
				}
			}
		}
		getDepots(true);
		getAllDeviceClasses(true);
		bossConfiguration = getBossConfigDto();

	}
	
	public static String getVodDeviceModel(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String voddeviceModelStr ="";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs =stmt.executeQuery("select t.xmlkey from t_migration_base t where t.basecode='SET_VOD_DEVICE'");
			while (rs.next()) {
             	if (voddeviceModelStr.equals("")){
	           		voddeviceModelStr =rs.getString("xmlkey");
             	}else{
	           		voddeviceModelStr =voddeviceModelStr+","+rs.getString("xmlkey");
	           	}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getVodDeviceModel",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getVodDeviceModel", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getVodDeviceModel", e);
				}
			}
		}
		return voddeviceModelStr;
	}

	/**
	 * 所有的设备类型
	 */
	public static Map getAllDeviceClasses() {

		return getMapBySQL("select deviceclass,description from t_deviceclass t ");

	}

	// 根据controlID取配置集合
	public static ArrayList getDisplayControlList(String controlID) {
		return (ArrayList) displayControlCache.get(controlID);
	}

	/**
	 * 从reInit()中移出来,key,value都是主键,不要描述了,
	 * 
	 * @param isRefresh
	 * @return
	 */
	private static Map getAllDeviceClasses(boolean isRefresh) {
		if (isRefresh || deviceClassCache == null || deviceClassCache.isEmpty()) {
			deviceClassCache = new LinkedHashMap();
			String sql = "select deviceclass,deviceclass from t_deviceclass";
			deviceClassCache = getMapBySQL(sql);
		}
		return deviceClassCache;
	}

	public static Map getDepots(boolean isRefresh) {
		if (isRefresh || depotCache == null || depotCache.isEmpty()) {
			depotCache = new LinkedHashMap();
			String sql = "select * from t_depot where status='V' ";
			depotCache = getMapBySQL(sql);
		}
		return depotCache;
	}

	/**
	 * 根据参数名称取 键/值 的集合 该方法显示commonSettingData中name为参数的所有值，包含I,V,Grade
	 * <level的所有数据，如果以后要加条件 可以在重载该方法或者再新增一个方法。目前该方法在很多Jsp中被调用！
	 */
	public static Map getHashKeyValueByName(String name) {
		Map map = new LinkedHashMap();
		Map dataCacheMap = (Map) commonSettingDataCache.get(name);
		if (dataCacheMap != null && !dataCacheMap.isEmpty()) {
			Iterator tempMapIterator = dataCacheMap.entrySet().iterator();
			while (tempMapIterator.hasNext()) {
				Map.Entry item = (Map.Entry) tempMapIterator.next();
				CommonSettingValue settingValue = (CommonSettingValue) item
						.getValue();
				map.put((String) item.getKey(), settingValue.getValue());
			}
		}
		return map;
	}

	public static Map getCommonSettingDataCache(String name,
			String showAllFlag, String judgeGradeFlag, int operatorLevel) {
		Map map = new LinkedHashMap();
		Map dataCacheMap = (Map) commonSettingDataCache.get(name);
		if (dataCacheMap != null && !dataCacheMap.isEmpty()) {
			Iterator tempMapIterator = dataCacheMap.entrySet().iterator();
			while (tempMapIterator.hasNext()) {
				Map.Entry item = (Map.Entry) tempMapIterator.next();
				CommonSettingValue settingValue = (CommonSettingValue) item
						.getValue();
				boolean showFlag = ("true".equalsIgnoreCase(showAllFlag) || Constant.GENERAL_STATUS_VALIDATE
						.equals(settingValue.getStatus()));

				if (showFlag) {
					if ("true".equalsIgnoreCase(judgeGradeFlag)) {
						if (operatorLevel >= settingValue.getGrade()) {
							map.put(settingValue.getKey(), settingValue);
						}
					} else {
						map.put(settingValue.getKey(), settingValue);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 获得所有分公司
	 */
	public static Map getParentCompanys() {
		return allParentCompanysMap;
	}

	public static Map getHashKeyValueByName(String name, String keyPrefix,
			String keySubfix, String valuePrefix, String valueSubfix) {
		Map map = (Map) getHashKeyValueByName(name);
		Map newMap = new LinkedHashMap();
		if (map == null) {
			map = new LinkedHashMap();
		}

		if (keyPrefix == null)
			keyPrefix = "";
		if (keySubfix == null)
			keySubfix = "";
		if (valuePrefix == null)
			valuePrefix = "";
		if (valueSubfix == null)
			valueSubfix = "";

		Iterator itMap = map.keySet().iterator();
		while (itMap.hasNext()) {
			String key = (String) itMap.next();
			String value = (String) map.get(key);

			newMap.put(keyPrefix + key + keySubfix, valuePrefix + value
					+ valueSubfix);
		}
		return newMap;
	}

	/**
	 * 获得模块名 select district(ModuleName) from T_SystemLog 返回：collection of
	 * String
	 */
	public static Collection getModueNameOfLog() {
		return getMapBySQL(
				"select distinct(ModuleName),'' from t_SystemLog order by ModuleName",
				true, true).keySet();
	}

	/**
	 * 获得网络接口的种类 返回：collection of String
	 */
	public static Collection getNetInterfaceType(String name) {
		return getMapBySQL(
				"select key,value from t_commonsettingdata where name='" + name
						+ "' and status='V' order by key desc", true, true)
				.entrySet();
	}

	/**
	 * 获得操作名 select district(Operation) from T_SystemLog 返回：collection of String
	 */
	public static Collection getOperationOfLog() {
		return getMapBySQL(
				"select distinct(Operation),'' from t_SystemLog order by operation",
				true, true).keySet();
	}

	/**
	 * 取得系统中所有操作员loginID,以字符串形式返回,中间以逗号分隔.
	 * 
	 * @return
	 */
	public static String getOperationLoginIDList() {
		Collection names = getMapBySQL(
				"select operatorid,loginid from t_operator where status='V'")
				.values();
		StringBuffer res = new StringBuffer();
		java.util.Iterator it = names.iterator();
		while (it.hasNext()) {
			String loginID = (String) it.next();
			res.append(loginID);
			if (it.hasNext()) {
				res.append(",");
			}
		}
		return res.toString();
	}

	/**
	 * name:name of the param key:key for the param
	 */
	public static String getHashValueByNameKey(String name, String key) {
		Map map = getHashKeyValueByName(name);
		if (map != null && map.containsKey(key)) {
			return (String) map.get(key);
		} else {
			return null;
		}
	}

	public static Map getAllOrganizationCache() {
		return organizationCache;
	}

	public static Organization getOrganizationByID(int id) {
		return (Organization) organizationCache.get("" + id);
	}

	/**
	 * 根据分公司(C:总公司; D:部门; B:分公司; P:合作伙伴; O:门店; S:街道站)ID获得名称
	 */
	public static String getOrgNameByID(int id) {
		try {
			if (organizationCache.containsKey("" + id)) {
				return ((Organization) organizationCache.get("" + id))
						.getOrgName();
			} else {
				return null;
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getOrgNameByID", e);
		}
		return null;
	}

	/**
	 * 获得所有分公司
	 */
	public static Map getAllFiliales() {
		return allFilialesMap;
	}

	/**
	 * 根据区ID获得行政区(省、地、县、区)名称
	 */
	public static String getDistrictNameByID(int id) {
		if (districtSettingCache.containsKey("" + id)) {
			return ((DistrictSetting) districtSettingCache.get("" + id))
					.getName();
		} else {
			return null;
		}
	}

	/**
	 * 根据customerID获得其客户所在的行政区
	 */
	public static String getAddressCountyByCustomerID(int id) {
		return getStringBySQL("select a.districtid from t_customer c, t_address a where c.addressid = a.addressid and customerid="
				+ id);
	}

	/**
	 * 根据customerID获得其客户所在的行政区
	 */
	public static String getContractNoBySaIdAndCustId(int saId, int custId) {
		return getStringBySQL("select contractno from t_customercampaign t Where customerid="
				+ custId + " and serviceaccountid=" + saId);
	}

	/**
	 * 根据受理原因和受理类型取得显示内容
	 * 
	 * @param csitype
	 * @param csiReason
	 * @return
	 */
	public static String getCsiReasonByCsiTypeAndReason(String csitype,
			String csiReason) {
		return getStringBySQL("Select Value From t_csiactionreasondetail Where Key='"
				+ csiReason
				+ "'  And Exists (Select SEQNO From T_CSIACTIONREASONSETTING  Where CSITYPE='"
				+ csitype + "' And SEQNO=REFERSEQNO)");
	}

	/**
	 * 所有的仓库
	 */
	public static Map getAllDepot() {
		return depotCache;
	}

	/**
	 * 所有操作员
	 */
	public static Map getAllOperator() {
		return operatorNameCache;
	}

	/**
	 * 根据设备类型获得给设备类型下的所有设备型号
	 */
	public static Map getDeviceModelByClass(String deviceClass) {
		String sql = "select devicemodel, devicemodel  from t_devicemodel where deviceclass='"
				+ deviceClass + "'";
		return getMapBySQL(sql);
	}

	/**
	 * 根据设备类型获得给设备类型下的所有设备型号
	 */
	public static Map getDeviceModelByClass(String deviceClass, String status) {
		if (status == null || status.trim().length() == 0)
			return getDeviceModelByClass(deviceClass);
		else {
			String[] statusArray = status.split(",");
			String destStatus = "";

			for (int i = 0; i < statusArray.length; i++) {
				if (!"".equals(destStatus))
					destStatus = destStatus + ",";

				if (statusArray[i].startsWith("'"))
					destStatus = destStatus + statusArray[i];
				else
					destStatus = destStatus + "'" + statusArray[i] + "'";
			}

			String sql = "select devicemodel, devicemodel  from t_devicemodel where deviceclass='"
					+ deviceClass + "' and Status in (" + destStatus + ")";
			return getMapBySQL(sql);
		}
	}

	public static Map getCsiReasonByType(String csiType) {
		return getMapBySQL("select key, value  from t_csiactionreasondetail where referseqno in( select seqno "
				+ "  from  t_csiactionreasonsetting  where csitype='"
				+ csiType
				+ "')");
	}

	public static Map getCsiReasonByCsitype(String parentValue) {
		return getMapBySQL(" select p.key,p.value from t_csiactionreasonsetting t,t_csiactionreasondetail p "
				+ " where t.csitype ='"
				+ parentValue
				+ "' and t.seqno =p.referseqno"
				+ " and t.status='V' and p.status ='V' ");

	}

	public static Map getCsiReasonByCon(String csiType, String action,
			String status) {

		StringBuffer sqlStr = new StringBuffer(
				"select p.key,p.value from t_csiactionreasonsetting t,t_csiactionreasondetail p where t.seqno =p.referseqno");
		if (csiType != null)
			sqlStr.append(" and t.csitype ='" + csiType + "'");
		if (action != null)
			sqlStr.append(" and t.action ='" + action + "'");
		if (status != null)
			sqlStr.append(" and t.status ='" + status + "' and p.status ='"
					+ status + "'");

		return getMapBySQL(sqlStr.toString());

	}

	/**
	 * 根据设备ID获得设备类型
	 */
	public static Map getDeviceModelByDeviceId(int deviceId) {
		return getMapBySQL("select t2.description, t1.devicemodel  from t_terminaldevice t1,t_deviceclass  t2 where t1.deviceclass=t2.deviceclass and t1.deviceid="
				+ deviceId);
	}

	/**
	 * 
	 * 根据设备类型获得给设备类型的厂商
	 */
	public static Map getDeviceProviderByModel(String deviceModel) {

		return getMapBySQL("select t.orgid ,t.orgname from t_organization t  where t.orgid in (select t1.providerid from t_devicemodel t1 where t1.devicemodel='"
				+ deviceModel + "')");
	}

	/**
	 * 所有LDAPproduct name
	 */
	public static Map getAllLdapProductName() {
		return ldapProductNameCache;
	}

	/**
	 * 获得所有供应商
	 * 
	 * @return
	 */
	public static Map getAllProvider() {

		return getMapBySQL("select * from t_Organization where orgtype ='P' and orgsubtype ='G' order by orgid asc");
	}

	/**
	 * 根据组织角色取得为该组织服务的组织名称
	 */
	public static Map getOrgNameMapByOrgRole(String orgRole) {
		return getMapBySQL("select t.orgid, t.orgName from t_organization t  where   t.orgid in( select r.serviceOrgID from t_roleorganization r  where r.OrgRole='"
				+ orgRole + "')");
	}
	
	public static Map getResourceObjectNumByStatus(String resourceName) {
		Map map = new LinkedHashMap();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select status,count(itemid)num from T_RESOURCE_PHONENO where resourcename='"
					+ resourceName + "'group by status";
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			String status = null;
			int num = 0;
			while (rs.next()) {
				status = (String) rs.getString("status");
				if (status != null) {
					num = rs.getInt("num");
					map.put(status, new Integer(num));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
			}
		}

		return map;
	}

	/**
	 * @param customerid
	 * @return 根据客户id，取得所有状态正常的账户信息
	 */
	public static Map getAccountDataCache(String customerid) {

		Map accountDataCache = new LinkedHashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			// initialize accountDataCache
			String sql = "select * from t_account where CUSTOMERID="
					+ customerid + "and status<>'C' order by accountid desc";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String accountName = rs.getString("ACCOUNTNAME");
				String accountID = String.valueOf(rs.getInt("ACCOUNTID"));
				if (accountName != null && !"".equals(accountName)) {
					accountDataCache.put(accountID, accountName);
				} else {
					accountDataCache.put(accountID, accountID);
				}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getAccountDataCache",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAccountDataCache", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAccountDataCache", e);
				}
			}
		}
		return accountDataCache;
	}

	/**
	 * @param product
	 *            ids
	 * @return 根据产品id，取得所有有效账户信息
	 */
	public static Map getAccountsByProductIDs(String productids,
			String customerid) {
		Map accountDataCache = new LinkedHashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		productids = productids.replace(';', ',');
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = "SELECT acct.accountid, acct.accountname FROM t_account acct, t_customerproduct cp";
			sql = sql  + " where 1 = 1 and cp.accountid = acct.accountid AND acct.status='N'";
			sql = sql  + " and cp.customerid = " + customerid;
			sql = sql  + " and cp.productid IN (" + productids + ")";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String accountName = rs.getString("ACCOUNTNAME");
				String accountID = String.valueOf(rs.getInt("ACCOUNTID"));
				if (accountName != null && !"".equals(accountName)) {
					accountDataCache.put(accountID, accountName);
				} else {
					accountDataCache.put(accountID, accountID);
				}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAccountsByProductIDs", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAccountsByProductIDs", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAccountDataCache", e);
				}
			}
		}
		return accountDataCache;
	}

	/**
	 * @param name
	 * @param key
	 * @return 根据公用数据的name和key，取得和key相关的所有内容（目前放在这里，有可能在p5用不上）
	 */
	public static String getHashValueListByNameKey(String name, String key) {
		Map map = getHashKeyValueByName(name);
		String returnValue = "";
		String currentKey = "";
		String myStandard = "0123456789";
		int count = 0;
		if (key != null && !"".equals(key)) {
			if (map != null) {
				if (key.indexOf(";") == -1) {
					String currentIndex = key.substring(0, 1);
					if (myStandard.indexOf(currentIndex) == -1) {
						Iterator currentIterator = map.keySet().iterator();
						while (currentIterator.hasNext()) {
							currentKey = (String) currentIterator.next();
							if (key.indexOf(currentKey) != -1) {
								if (count == 0) {
									returnValue = (String) map.get(currentKey);
								} else {
									returnValue = returnValue + ";"
											+ (String) map.get(currentKey);
								}
								count++;
							}
						}
					} else {
						returnValue = (String) map.get(key);
					}
				} else {
					String parentKey = key;
					String currentString = "";
					while (parentKey.indexOf(";") != -1) {
						int currentSize = parentKey.indexOf(";");
						currentString = parentKey.substring(0, currentSize);
						parentKey = parentKey.substring(currentSize + 1);
						if (count == 0) {
							returnValue = (String) map.get(currentString);
						} else {
							returnValue = returnValue + ";"
									+ (String) map.get(currentString);

						}
						count++;
					}
					returnValue = returnValue + ";"
							+ (String) map.get(parentKey);
				}
			}
		}
		return returnValue;
	}

	/**
	 * @param deviceCalss
	 * @return 根据设备类型代码取得设备型号，再通过设备型号取得产品id
	 */
	public static Map getProductDataCache(String deviceCalss) {
		Map deviceDataCache = new LinkedHashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = "select * from t_product where PRODUCTID in(select PRODUCTID from t_devicematchtoproduct where DEVICEMODEL in(select DEVICEMODEL from t_devicemodel where DEVICECLASS='"
					+ deviceCalss + "') )";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String productID = String.valueOf(rs.getInt("PRODUCTID"));
				String productName = rs.getString("PRODUCTNAME");
				deviceDataCache.put(productID, productName);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getProductDataCache",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getProductDataCache", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getProductDataCache", e);
				}
			}
		}
		return deviceDataCache;
	}

	/**
	 * @param id
	 * @return 根据id获得团购券明细信息
	 */
	public static AddressDTO getAddressDtoByID(int id) {
		AddressDTO dto = new AddressDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_Address  t where t.addressid= ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillAddressDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "AddressDTO", e);
			dto = null;
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
					LogUtility.log(Postern.class, LogLevel.WARN, "AddressDTO",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "AddressDTO",
							e);
				}
			}
		}
		return dto;
	}

	/**
	 * @param id
	 * @return 根据id获得团购券明细信息
	 */
	public static GroupBargainDetailDTO getGroupBargainDetailByID(int id) {
		GroupBargainDetailDTO dto = new GroupBargainDetailDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_groupBargainDetail where id= ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillGroupBargainDetailDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"GroupBargainDetailDTO", e);
			dto = null;
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"GroupBargainDetailDTO", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"GroupBargainDetailDTO", e);
				}
			}
		}
		return dto;

	}
	
	/**
	 * 获得所有代理商
	 */
	public static Map getProxyOrg(int orgId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map map = new LinkedHashMap();
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("select * from t_organization where status='V' and orgtype='P' and  orgsubtype='S'and parentorgid="
							+ orgId);
			while (rs.next()) {
				Organization og = new Organization();
				DtoFiller.fillOrganizationDTO(rs, og, null);
				map.put("" + og.getOrgID(), og.getOrgName());
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getProxyOrg", e);
			map.clear();

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
					LogUtility.log(Postern.class, LogLevel.WARN, "getProxyOrg",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "getProxyOrg",
							e);
				}
			}
		}
		return map;
	}

	/**
	 * 获得支付方式的所有 代理商
	 */
	public static Map getPaymentProxyOrg() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map map = new LinkedHashMap();
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("select * from t_organization where status='V' and orgtype='P' and  orgsubtype='P'");
			while (rs.next()) {
				Organization og = new Organization();
				DtoFiller.fillOrganizationDTO(rs, og, null);
				map.put("" + og.getOrgID(), og.getOrgName());
				// map.put("" + og.getOrgID(), og.getOrgName());
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getPaymentProxyOrg",
					e);
			map.clear();

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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getPaymentProxyOrg", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getPaymentProxyOrg", e);
				}
			}
		}
		return map;
	}

	/**
	 * 获得所有代理商
	 */
	public static Map getProxyOrg() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map map = new LinkedHashMap();
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("select * from t_organization where status='V' and orgtype='P' and  orgsubtype='S'");
			while (rs.next()) {
				Organization og = new Organization();
				DtoFiller.fillOrganizationDTO(rs, og, null);
				map.put("" + og.getOrgID(), og);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getProxyOrg", e);
			map.clear();

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
					LogUtility.log(Postern.class, LogLevel.WARN, "getProxyOrg",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "getProxyOrg",
							e);
				}
			}
		}
		return map;
	}

	/**
	 * 获得sub configtype
	 */
	public static Map getSubConfigType(String configType) {
		if ("C".equalsIgnoreCase(configType)) {
			return getMapBySQL("select key, value  from t_commonsettingdata where name = 'SET_C_BIDIMCONFIGSETTINGCONFIGSUBTYPE'  "
					+ " and key in ('C','O','R')");
		} else if ("U".equalsIgnoreCase(configType)) {
			return getMapBySQL("select key, value  from t_commonsettingdata where name = 'SET_C_BIDIMCONFIGSETTINGCONFIGSUBTYPE'  "
					+ " and key in ('IU','RU','MU')");

		} else if ("D".equalsIgnoreCase(configType)) {
			return getMapBySQL("select key, value  from t_commonsettingdata where name = 'SET_C_BIDIMCONFIGSETTINGCONFIGSUBTYPE'  "
					+ " and key in ('N','P')");
		}

		return new HashMap();
	}

	/**
	 * 获得受理原因根据受理单类型
	 */
	public static Map getCsiReasonByCsiType(String csiType) {

		return getMapBySQL("select key, value  from t_csiactionreasondetail where referseqno in (select seqno from t_csiactionreasonsetting "
				+ " where csitype= '" + csiType + "')");

	}

	/**
	 * 得到配置数据的默认
	 * 
	 * @param name
	 * @param status
	 * @return
	 */
	public static CommonSettingDataDTO getCommonSettingDataDTOByNameAndByStatus(
			String name, String status) {
		CommonSettingDataDTO dto = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement(" select * from t_commonsettingdata t where t.name=? and t.status= ? and  t.defaultornot='Y'");
			stmt.setString(1, name);
			stmt.setString(2, status);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = new CommonSettingDataDTO();
				dto.setName(rs.getString("name"));
				dto.setKey(rs.getString("key"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCommonSettingDataDTOByNameAndByStatus", e);
			dto = null;
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getCommonSettingDataDTOByNameAndByStatus", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getCommonSettingDataDTOByNameAndByStatus", e);
				}
			}
		}
		return dto;

	}

	/**
	 * 获得eventreason
	 */
	public static Map getEventReasonById(String id) {
		return getMapBySQL("select reasoncode, reasondesc  from t_systemeventreason where eventclass = '"
				+ id + "'");
	}

	public static Map getCsiReason() {
		return getMapBySQL("select key, value  from t_csiactionreasondetail ");
	}

	public static Map getCsiReasonByDevice() {
		return getMapBySQL("select key, value  from t_csiactionreasondetail where referseqno in( select seqno "
				+ "  from  t_csiactionreasonsetting  where csitype='DS' ) ");
	}

	public static int getUsedSheetsByTime(int groupbargainid, Timestamp begin,
			Timestamp end) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuf = new StringBuffer(
				"select count(id) from T_GROUPBARGAINDETAIL where status='R' and GROUPBARGAINID=?");
		if (begin != null) {
			sqlBuf.append(" and USEDDATE>=?");
		}
		if (end != null) {
			sqlBuf.append(" and USEDDATE<=?");
		}

		int usedSheets = 0;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sqlBuf.toString());
			stmt.setInt(1, groupbargainid);
			if (begin != null)
				stmt.setTimestamp(2, begin);
			if (end != null)
				stmt.setTimestamp(3, end);
			rs = stmt.executeQuery();
			if (rs.next())
				usedSheets = rs.getInt(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception rse) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ste) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ex) {
				}
			}
		}
		return usedSheets;
	}

	/**
	 * 得到系统模块的配置信息
	 * 
	 * @return
	 */
	public static Map getSystemModel() {
		if (systemModuleCache == null)
			systemModuleCache = new HashMap();
		return systemModuleCache;
	}

	/**
	 * 得到系统图片的配置信息
	 * 
	 * @return
	 */
	public static Map getSystemImgCache() {
		if (systemImgCache == null)
			systemImgCache = new HashMap();
		return systemImgCache;
	}

	/**
	 * 得到业务图标的配置信息
	 * 
	 * @return
	 */
	public static Map getServiceIconsCache() {
		if (serviceIconsCache == null)
			serviceIconsCache = new HashMap();
		return serviceIconsCache;
	}

	/**
	 * 根据ownerid获得仓库
	 */
	public static String getOrganizationTypeByID(int orgid) {
		return getStringBySQL("select t.ORGTYPE from T_ORGANIZATION t where t.ORGID="
				+ orgid);
	}

	/**
	 * 根据deviceclass获得description
	 */

	public static String getClassNameByDeviceClass(String devclass) {
		return getStringBySQL("select t.description from t_deviceclass t where t.deviceclass='"
				+ devclass + "'");
	}

	/**
	 * 根据deviceclass获得description
	 */

	public static String getProviderNameByID(int id) {
		return getStringBySQL("select t.orgname from t_organization t where t.orgid="
				+ id);
	}

	/**
	 * 根据accountid get the name
	 */

	public static String getAcctNameById(int id) {
		return getStringBySQL("select t.accountname from t_account t where t.accountid="
				+ id);
	}

	/**
	 * 根据csiid获得 prefereddate
	 */
	public static String getPreferedDate(int csiID) {
		return getStringBySQL("select t.prefereddate from t_custserviceinteraction t where t.id="
				+ csiID);
	}

	/**
	 * 根据csiid获得 preferedtime
	 */
	public static String getPreferedTime(int csiID) {
		return getStringBySQL("select t.preferedtime from t_custserviceinteraction t where t.id="
				+ csiID);
	}

	/**
	 * 根据deviceModel获得序列长度
	 */
	public static String getSerialLengthProviderByModel(String deviceModel) {
		return getStringBySQL("select t.seriallength from t_devicemodel t where t.deviceModel='"
				+ deviceModel + "'");
	}

	/**
	 * 根据deviceModel获得设设备必须输入的信息
	 * 
	 * @param deviceModel
	 * @return
	 */
	public static String getFieldNameByModel(String deviceModel) {
		StringBuffer fieldName = new StringBuffer();
		StringBuffer fieldDesc = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_disadditionalinfor where devicemodel=? and status='V' order by indexno";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, deviceModel);
			rs = stmt.executeQuery();
			while (rs.next()) {
				fieldName.append(rs.getString("FIELDNAME")).append("|");
				fieldDesc.append(rs.getString("FIELDDESC")).append("|");
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "取设备必须的输入信息出错！");
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
				} catch (Exception e2) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e3) {

				}
			}
		}
		fieldName = fieldName.toString().endsWith("|") ? fieldName
				.deleteCharAt(fieldName.length() - 1) : fieldName;
		fieldDesc = fieldDesc.toString().endsWith("|") ? fieldDesc
				.deleteCharAt(fieldDesc.length() - 1) : fieldDesc;
		LogUtility.log(Postern.class, LogLevel.WARN, "截取后的fieldName！"
				+ fieldName.toString());
		LogUtility.log(Postern.class, LogLevel.WARN, "截取后的fieldDesc！"
				+ fieldDesc.toString());
		return fieldName.toString() + "," + fieldDesc.toString();
	}

	/**
	 * 根据addressid获得address
	 */
	public static String getAddressById(int id) {
		return getStringBySQL("select detailaddress from t_address where addressid="
				+ id);
	}

	/**
	 * 获得开户时的付费类型
	 * 
	 * @return map
	 */
	public static Map getOpeningMop() {
		if (mapAccountTypeOpeningMop == null) {
			mapAccountTypeOpeningMop = new LinkedHashMap();
		}
		return mapAccountTypeOpeningMop;
	}

	/**
	 * 获得某计费规则的名称
	 */
	public static String getBillingCycleTypeNameByID(int id) {
		return getStringBySQL("select t.Name from t_billingcycletype t where id="
				+ id);
	}

	/**
	 * 得到所有的计费周期 add by jason.zhou
	 * 
	 * @return
	 */
	public static Map getBillingcycle() {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("select ID,NAME from T_BILLINGCYCLE WHERE  CType='B' AND Status='A'");
			while (rs.next()) {
				map.put(String.valueOf(rs.getInt("ID")), rs.getString("NAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "查找所有的计费周期出错！");
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
				} catch (Exception e2) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e3) {

				}
			}
		}
		return map;
	}

	/**
	 * 得到所有的组织机构
	 * 
	 * 
	 * @return
	 */
	public static Map getAllOrg() {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from t_organization t ");
			while (rs.next()) {
				map.put(String.valueOf(rs.getInt("orgid")), rs
						.getString("orgname"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "查找所有组织！");
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
				} catch (Exception e2) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e3) {

				}
			}
		}
		return map;
	}

	/**
	 * 得到所有的计费周期 add by wanghao used for
	 * /conifg/financial_account_cycle_type_editing.jsp 对应计费周期类型
	 * 
	 * @return
	 */
	public static Map getAllChargeCycleType() {
		return getMapBySQL(" Select id,name from t_billingcycletype where ctype='B' order by id asc ");
	}

	/**
	 * 得到所有的帐务周期 add by wanghao
	 * 
	 * @return
	 */
	public static Map getAllAccountCycleType() {
		return getMapBySQL(" Select id,name from t_billingcycletype where Ctype='I' order by id asc ");
	}

	/**
	 * added by wanghao .used for
	 * /conifg/financial_charge_cycle_type_editing.jsp 所有计费周期
	 * 
	 * @return
	 */
	public static Map getAllChargeCycle() {
		return getMapBySQL(
				" Select id,name from t_billingcycle where ctype='B' order by id desc ",
				true, true);
	}

	/**
	 * added by wanghao .used for
	 * /conifg/financial_account_cycle_type_editing.jsp 所有帐务周期
	 * 
	 * @return
	 */
	public static Map getAllAccountCycle() {
		return getMapBySQL(
				" Select id,name from t_billingcycle where ctype='I' order by id desc ",
				true, true);
	}

	/**
	 * 获得系统事件名称
	 */
	public static String getEventNameByEventClass(int eventClass) {
		return getStringBySQL("select t.eventname from t_systemeventdef t where t.eventclass="
				+ eventClass);
	}

	/**
	 * 获得系统事件原因
	 */
	public static String getReasonByReasonCode(String reasonCode, int eventClass) {
		String sql = "select reasondesc from t_systemeventreason  where eventclass="
				+ eventClass + " and reasoncode='" + reasonCode + "'";

		return getStringBySQL(sql);
	}

	/**
	 * 获得报修类别
	 */
	public static String getReferSourceTypeByID(int id) {
		return getStringBySQL("select distinct t.refersourcetype from t_diagnosisinfo t where t.refersourceid="
				+ id);
	}

	/**
	 * 根据accountid获得未付款记录数
	 */
	public static int getUnpaidInvoiceCountByAcctID(int acctID) {
		return getIntBySQL("select count(*) from t_invoice where (status='W' or status='O') and acctID="
				+ acctID);
	}

	public static String getSystemsettingValueByName(String name) {
		return (String) systemsettingCache.get(name);
	}

	public static int getSystemsettingIntValue(String name, int defaultValue) {
		String systemsettingValue = getSystemsettingValueByName(name);
		int returnValue = defaultValue;
		if (systemsettingValue != null && WebUtil.IsInt(systemsettingValue)) {
			returnValue = Integer.parseInt(systemsettingValue);
		}
		return returnValue;
	}

	/**
	 * 获得开户时的付费方式
	 * 
	 * @return map
	 */
	public static Map getOpeningPaymentMop(String csiType) {
		return (mapCsiPaymentTypeMop.get(csiType) == null) ? new HashMap()
				: (Map) mapCsiPaymentTypeMop.get(csiType);
	}

	/**
	 * 获得开户时的未包装的付费方式
	 * 
	 * @return
	 */
	public static Map getOpeningPaymentNoWrapMop() {
		if (mapPaymentTypeOpeningNoWrapMop == null)
			mapPaymentTypeOpeningNoWrapMop = new LinkedHashMap();

		return mapPaymentTypeOpeningNoWrapMop;
	}

	/**
	 * 根据受理单ID获得受理单类型
	 */
	public static String getCSITypeByID(int csiid) {
		return getStringBySQL("select type from t_custserviceinteraction where id="
				+ csiid);
	}

	/**
	 * 根据ownerid获得仓库
	 */
	public static Map getGoodNameByActivityID(int id) {
		return getMapBySQL("select t.id, t.name from t_userpointsexchangegoods t where t.activityid="
				+ id);
	}

	/**
	 * 根据customerID获得 状态
	 */
	public static String getCustomerStatusByID(int id) {
		return getStringBySQL("select status from t_customer where customerid="
				+ id);
	}

	/**
	 * 根据customerid获得未付款记录数
	 */
	public static int getUnpaidInvoiceCountByCustID(int custID) {
		return getIntBySQL("select count(*) from t_invoice where (status='W' or status='O') and custID="
				+ custID);
	}

	public static int getTerminalCountByModelAndClass(String txtDeviceModel,
			String deviceClass) {
		String strSql = "select count(*) from  T_TERMINALDEVICE  where 1=1  and DEVICEMODEL in ('"
				+ txtDeviceModel
				+ "') "
				+ "  and DEVICECLASS='"
				+ deviceClass
				+ "' and STATUS='W'";
		return getIntBySQL(strSql);
	}

	/**
	 * 根据customerID获得其地址
	 */
	public static String getAddressByCustomerID(int id) {
		return getStringBySQL("select a.detailaddress from t_customer c, t_address a where c.addressid = a.addressid and customerid="
				+ id);
	}

	/**
	 * 根据货物id获得货物名称
	 */
	public static String getGoodNameByID(int id) {
		return getStringBySQL("select t.name from t_userpointsexchangegoods t where t.id="
				+ id);
	}

	public static String getFieldNameByFieldInterID(String fieldInterID) {
		return (fieldNameCache.get(fieldInterID) == null) ? "终端编号"
				: (String) fieldNameCache.get(fieldInterID);
	}

	/**
	 * 根据活动id获得活动名称
	 */
	public static String getActivityNameByID(int id) {
		return getStringBySQL("select t.name from t_userpointsexchangeactivity t where t.id="
				+ id);
	}

	/**
	 * 根据addressid获得address
	 */
	public static String getCustomerNameById(int id) {
		return getStringBySQL("select name from t_customer where customerid="
				+ id);
	}

	/**
	 * 兑换活动
	 */
	public static Map getActivityName() {
		String sql = "select * from t_userpointsexchangeactivity ";
		activityCache = getMapBySQL(sql);
		return activityCache;
	}

	/**
	 * 兑换货物
	 */
	public static Map getGoodsName() {
		return goodsCache;
	}

	/**
	 * 根据仓库ID获得仓库名称
	 */
	public static String getDepotNameByID(int id) {
		return getStringBySQL("select t.depotname from t_depot t where t.depotid="
				+ id);
	}

	/**
	 * 根据ownerid获得仓库
	 */
	public static Map getDepotByOwnerID(int ownerID) {
		return getMapBySQL("select t.depotid, t.depotname from t_depot t where t.ownerid="
				+ ownerID);
	}

	/**
	 * 根据ownerid获得仓库
	 */
	public static String getDepotNameByDepotID(int depotID) {
		return getStringBySQL("select  t.depotname from t_depot t where t.depotid="
				+ depotID);
	}

	/**
	 * 所有的设备型号
	 */
	public static Map getAllDeviceModels() {
		return deviceModelCache;
	}

	/**
	 * 所有事件定义
	 */
	public static Map getAllSystemClass() {
		return getMapBySQL("select eventclass a, eventclass b from t_systemeventdef order by eventclass asc");
	}

	/**
	 * 所有事件定义
	 */
	public static Map getAllSystemEvent() {
		return systemEventCache;
	}

	public static Map getAccountIDMapByCustID(int custID) {
		return getMapBySQL(
				"select accountid, accountid from t_account where customerid="
						+ custID, true);
	}

	public static Map getAccountNameMapByCustID(int custID) {
		return getMapBySQL(
				"select accountid, accountname from t_account where customerid="
						+ custID, false);
	}

	/**
	 * 根据工单ID获得工单状态
	 */
	public static String getJobCardStatusByID(int jcid) {
		return getStringBySQL("select status from t_jobcard where jobcardid="
				+ jcid);
	}

	/**
	 * 根据活动id获得活动名称
	 */
	public static String getEveryActivityName(int activityId) {
		return getStringBySQL("select name from t_userpointsexchangeactivity where id="
				+ activityId);
	}

	/**
	 * 根据goodid获得goodname
	 */
	public static String getEveryGoodsName(int goodsId) {
		return getStringBySQL("select name from t_userpointsexchangegoods where id="
				+ goodsId);
	}
	
	public static Collection getInvoiceColByAcctId(int accountId){
    	Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection lst =new ArrayList();
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement("select * from t_invoice t where t.status ='W' and t.acctid="+accountId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				InvoiceDTO dto = DtoFiller.fillInvoiceDTO(rs);
				lst.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getInvoiceColByAcctId", e);
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getInvoiceColByAcctId", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getInvoiceColByAcctId", e);
				}
			}
		}
		return lst;
	}

	/**
	 * @param id
	 * @return 根据团购id获得团购券头信息
	 */
	public static GroupBargainDTO getGroupBargainByID(int id) {
		GroupBargainDTO dto = new GroupBargainDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_groupBargain where id = ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillGroupBargainDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getGroupBargainByID",
					e);
			dto = null;
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
	 * 根据CSIID取得他所有非取消状态的device列表
	 * 
	 * @param customerID
	 * @param notStatus
	 * @return
	 */
	public static Collection getAllDeviceByCSIID(int CSIID, String notStatus) {
		Collection list = new Vector();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select distinct td.* from t_customerproduct t, t_terminaldevice td where t.status <> '"
							+ notStatus
							+ "' and t.deviceid = td.deviceid and t.serviceaccountid in "
							+ "(select distinct (c.referserviceaccountid) from t_csicustproductinfo c where c.csiid = ? ) ");

			stmt.setInt(1, CSIID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				TerminalDeviceDTO dto = DtoFiller.fillTerminalDeviceDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
				System.out.println("getTerminalDeviceByID exception:"
						+ e.getMessage());

				e.printStackTrace();
			    list.clear();
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
	 * 根据CustomerID取得他所有非取消状态的device列表
	 */
	public static Collection getAllDeviceByCustomerID(int customerID,
			String notStatus) {
		Collection list = new Vector();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select distinct td.* from t_customerproduct t, t_terminaldevice td where t.status <> '"
							+ notStatus
							+ "' and t.deviceid = td.deviceid and t.customerid = ? ");

			stmt.setInt(1, customerID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				TerminalDeviceDTO dto = DtoFiller.fillTerminalDeviceDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out.println("getTerminalDeviceByID exception:"
						+ e.getMessage());

				e.printStackTrace();
			}
			list.clear();
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
	 * 根据settingId取得他所有BidimConfigSettingValueDTO列表
	 */
	public static Collection getAllValusDtoByID(int settingId) {
		Collection list = new Vector();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_bidimconfigsettingvalue t where  t.settingid = ? ");
			stmt.setInt(1, settingId);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				BidimConfigSettingValueDTO dto = DtoFiller.fillBidimConfigSettingValueDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("getTerminalDeviceByID exception:"
						+ e.getMessage());
			e.printStackTrace();
			list.clear();
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

	public static List getDistrictIDListByID(int marketSegmentId) {
		List idList = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;
			sql = "select * from t_marketsegmenttodistrict t where  t.marketsegmentid ="
					+ marketSegmentId;

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("DISTRICTID");
				idList.add(new Integer(id));

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDistrictIDListByID", e);
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

		if (idList.size() <= 0)
			idList = null;

		return idList;
	}

	public static List getDistrictIDList() {
		List idList = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;
			sql = "select * from t_districtsetting start with belongto=0  connect by prior id = belongto ";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("ID");
				idList.add(new Integer(id));
			}
		} catch (Exception e) {
			LogUtility
					.log(Postern.class, LogLevel.WARN, "getDistrictIDList", e);
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

		if (idList.size() <= 0)
			idList = null;

		return idList;
	}

	/**
	 * david.Yang 所有的付费方式
	 */
	public static Map getAllMethodOfPayments() {
		return methodOfPaymentCache;
	}

	/**
	 * chen jiang 只有现金跟抵扣券付费
	 */
	public static Map getAllMethodOfPaymentsForMarket() {
		return methodOfPaymentCacheForMarket;
	}

	/**
	 * 得到MOP的Map对象，key为MOP的ID (String Type)，value为MOP的name add by jason.zhou
	 * 
	 * @return
	 */
	public static Map getAllMOPOnlyIdAndName() {
		HashMap map = new LinkedHashMap();
		if (methodOfPaymentCache != null && methodOfPaymentCache.size() > 0) {
			Collection col = methodOfPaymentCache.values();
			Iterator itCol = col.iterator();
			while (itCol.hasNext()) {
				MethodOfPaymentDTO dto = (MethodOfPaymentDTO) itCol.next();
				map.put(String.valueOf(dto.getMopID()), dto.getName());
			}
		}
		return map;
	}

	public static Map getAllProductName() {
		return productNameCache;
	}

	public static Map getMealNameByType(String campaignType) {
		// 客户套餐 T_Campaign
		return getMapBySQL("select TO_CHAR(CampaignID), CampaignName from T_Campaign where CampaignType='"
				+ campaignType + "'");
	}

	// 得到客户套餐/促销活动Map
	public static Map getCampaignNameAndID(String type) {
		String sqlString = "select CampaignID, CampaignName from T_Campaign";
		if ("meal".equals(type))
			sqlString = "select CampaignID, CampaignName from T_Campaign where CampaignType ='B'";

		if ("campaign".equals(type))
			sqlString = "select CampaignID, CampaignName from T_Campaign where CampaignType='A'";

		return getMapBySQL(sqlString);
	}

	// 取得光收机的编码map
	public static Map getFiberReceiverCodeMap() {

		return getMapBySQL("select TO_CHAR(id), FiberReceiverCode from T_FiberReceiver");
	}

	public static Map getFiberTransmitterCodeMap() {

		return getMapBySQL("select TO_CHAR(id), FiberTransmitterCode from T_FiberTransmitter");
	}

	public static Map getRoomNameMap() {

		return getMapBySQL("select TO_CHAR(id), machineroomname from T_machineroom");
	}

	public static Map getAllSAName(String productIDList, String strProductClass) {
		StringBuffer buff = new StringBuffer();
		buff
				.append("select distinct(s.ServiceID), s.ServiceName from T_Service s,T_ProductToService pts,T_Product p ");
		buff
				.append(" where s.ServiceID=pts.ServiceID and pts.ProductID=p.ProductID ");
		if (!(productIDList == null || "".equals(productIDList) || ","
				.equals(productIDList)))
			buff.append(" and pts.ProductID in (" + productIDList + ") ");
		if (!(strProductClass == null || "".equals(strProductClass)))
			buff.append(" and p.ProductClass='" + strProductClass + "'");

		// 订户 T_Service
		return getMapBySQL(buff.toString());
	}

	public static Map getProductGroupByServiceID(String productIDList,
			String strProductClass) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map serMap = new LinkedHashMap();

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;
			sql = "select ptc.SERVICEID SERVICEID,pt.PRODUCTID PRODUCTID,pt.PRODUCTNAME PRODUCTNAME "
					+ "from T_ProductToService ptc,t_product pt "
					+ "where ptc.PRODUCTID = pt.PRODUCTID ";
			if (!(productIDList == null || "".equals(productIDList)))
				sql = sql + " and pt.PRODUCTID in (" + productIDList + ") ";
			if (!(strProductClass == null || "".equals(strProductClass)))
				sql = sql + " and pt.ProductClass='" + strProductClass + "' ";
			sql = sql + " group by ptc.SERVICEID,pt.PRODUCTID,pt.PRODUCTNAME";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String SERVICEID = rs.getString("SERVICEID");
				String PRODUCTID = rs.getString("PRODUCTID");
				String PRODUCTNAME = rs.getString("PRODUCTNAME");
				Map proMap = (Map) serMap.get(SERVICEID);
				if (proMap == null) {
					proMap = new HashMap();
					serMap.put(SERVICEID, proMap);
				}
				proMap.put(PRODUCTID, PRODUCTNAME);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductGroupByServiceID", e);
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
		return serMap;
	}

	public static Map getAllCommandName() {
		return commandNameCache;
	}

	public static Map getEventReason() {
		return systemEventReasonCache;
	}
	
	/**
	 * nile.chen 根据refersourceid 得到诊断type
	 */
	public static List getDiaTypeByCustProbID(int custProId) {
		List typeList = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;

			sql = "select distinct  t.configsubtype  from  t_bidimconfigsetting t where id in( select infosettingid from t_diagnosisinfo where refersourceid="
					+ custProId + ")";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String typeStr;
				typeStr = rs.getString("CONFIGSUBTYPE");
				typeList.add(typeStr);
			}
			rs.close();
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDiaTypeByCustProbID", e);
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

		if (typeList.size() <= 0)
			typeList = null;

		return typeList;
	}

	/**
	 * david.Yang 获得开户时与用户详细地址相关的现有用户信息 import：detailAddress(String) return：List
	 */
	public static List getCheckDetailAddress(String detailAddress) {

		List forCheckDetailAddressList = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;
			if (detailAddress != null && !"".equals(detailAddress)) {
				sql = "select a.* from t_customer a,t_address b where a.addressid=b.addressid and b.detailaddress='"
						+ detailAddress + "'";
			}
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CustomerDTO customerDTO = DtoFiller.fillCustomerDTO(rs);
				forCheckDetailAddressList.add(customerDTO);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCheckDetailAddress", e);
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

		if (forCheckDetailAddressList.size() <= 0)
			forCheckDetailAddressList = null;

		return forCheckDetailAddressList;
	}

	/**
	 * david.Yang 通过id得到区公司的中文名
	 * 
	 * @param id
	 * @return
	 */
	public static String getNamebyDistrictSettingID(int id) {
		return getStringBySQL("select name from t_districtsetting where id="
				+ id);
	}

   /**
	 * david.Yang
	 * 
	 * @return
	 */
	public static JobCardDTO getJobCardDto(String jobType, int referSheetID) {
		String sql = "select * from t_jobcard where JOBTYPE ='" + jobType
				+ "' and REFERSHEETID=" + referSheetID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		JobCardDTO dto = new JobCardDTO();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto = DtoFiller.fillJobCardDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getJobCardDto", e);
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return dto;
	}

	public static JobCardDTO getJobCardDto(int jobCardID) {
		String sql = "select * from T_JOBCARD where JOBCARDID=" + jobCardID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		JobCardDTO dto = new JobCardDTO();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto = DtoFiller.fillJobCardDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getJobCardDto", e);
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return dto;
	}

	/**
	 * 通过referSheetID 得到CustomerProblemDTO fiona
	 * 
	 * @param referSheetID
	 * @return
	 */
	public static CustomerProblemDTO getCustomerProblemDto(int referSheetID) {
		String sql = "select * from T_CustomerProblem where id=" + referSheetID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		CustomerProblemDTO dto = new CustomerProblemDTO();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto = DtoFiller.fillCustomerProblemDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerProblemDto", e);
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return dto;
	}

	/**
	 * 通过客户业务帐户号得到产品 fiona
	 * 
	 * @param cusSerAccID
	 * @return
	 */
	public static String getDeviceByCustServiceAccountID(int cusSerAccID,
			String col) {
		// zyg 2007.04.05
		/***********************************************************************
		 * String sql = "select c.deviceclass,c.devicemodel from
		 * t_serviceaccount a,t_customerproduct b," + " t_terminaldevice c where
		 * a.serviceaccountid= " +cusSerAccID+ " and
		 * a.serviceaccountid=b.serviceaccountid and b.deviceid=c.deviceid"; /
		 **********************************************************************/

		/** ******************************************** */
		String sql = "Select t.deviceclass , t.devicemodel From t_terminaldevice t , t_customerproduct cp Where t.deviceid = cp.deviceid And cp.serviceaccountid = "
				+ cusSerAccID + " And nvl(cp.deviceid,0) <> 0";
		/** ******************************************** */

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String device = "";
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int i = 1;
			if (col.equals("deviceclass")) {
				while (rs.next()) {
					device += i + "." + rs.getString("deviceclass") + " ";
					i++;
				}
			} else if (col.equals("devicemodel")) {
				while (rs.next()) {
					device += i + "." + rs.getString("devicemodel") + " ";
					i++;
				}
			}

		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerProblemDto", e);
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return device;
	}

	/**
	 * Stone Liang 根据业务ID获得业务名称
	 */
	public static String getServiceNameByID(int id) {
		return getStringBySQL("select servicename from t_service where serviceid="
				+ id);
	}

	/**
	 * fiona根据业务账户ID获得业务名称
	 */
	public static String getServiceNameByServiceAccountID(int id) {
		return getStringBySQL("select servicename from t_service ser , t_serviceaccount seracc   where seracc.serviceaccountid="
				+ id + "  and seracc.serviceid=ser.serviceid ");
	}

	public static String getStrictNameByID(int id) {
		return getStringBySQL("select name from t_districtsetting where id="
				+ id);
	}

	public static int getServiceIDByAcctServiceID(int saId) {
		return getIntBySQL("select t.serviceid from t_serviceaccount t where t.serviceaccountid="
				+ saId);
	}

	public static int getServiceCodeByAcctServiceID(int saId) {
		return getIntBySQL("select t.servicecode from t_serviceaccount t where t.serviceaccountid="
				+ saId);
	}

	public static String getStatusByAcctServiceID(int saId) {
		return getStringBySQL("select t.status from t_serviceaccount t where t.serviceaccountid="
				+ saId);
	}

	public static String getCustomerIDByAcctServiceID(int saId) {
		return getStringBySQL("select t.customerid from t_serviceaccount t where t.serviceaccountid="
				+ saId);
	}

	public static Map getPlanMap() {
		return getMapBySQL("select t.FeeSplitPlanID ,t.planName from T_FeeSplitPlan t");
	}

	public static List getServiceIdByAcctServiceId(int id) {
		List serviceIdList = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;
			sql = "select distinct p.serviceid from t_Producttoservice p "
					+ "where p.productid in (select t.productid from t_customerproduct t "
					+ "where t.productid in ( select t1.productid from t_product t1 where t1.productclass='S') and t.serviceaccountid ="
					+ id + ")";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int serviceId;
				serviceId = rs.getInt("SERVICEID");
				serviceIdList.add(new Integer(serviceId));

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getServiceIdByAcctServiceId", e);
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

		if (serviceIdList.size() <= 0)
			serviceIdList = null;

		return serviceIdList;
	}

	/**
	 * Chen jiang 根据积分活动id找出积分条件dto
	 * 
	 * @param activityId
	 * @return
	 */
	public static List getActivityDetailById(int id) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List condDtoList = new ArrayList();
		String sql = "select t.* from t_userpointsexchangecond t where t.activityid="
				+ id;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				UserPointsExchangeCondDTO dto = DtoFiller
						.fillUserPointsExchangeCondDTO(rs);
				condDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getActivityDetailById", e);

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

		if (condDtoList.size() <= 0)
			condDtoList = null;
		return condDtoList;

	}

	/**
	 * Chen jiang 根据积分活动id找出货物dto
	 * 
	 * @param activityId
	 * @return
	 */
	public static List getGoodDetailById(int id) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List goodDtoList = new ArrayList();
		String sql = "select t.* from t_userpointsexchangegoods t where t.activityid="
				+ id;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				UserPointsExchangeGoodsDTO dto = DtoFiller
						.fillUserPointsExchangeGoodsDTO(rs);
				goodDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility
					.log(Postern.class, LogLevel.WARN, "getGoodDetailById", e);

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

		if (goodDtoList.size() <= 0)
			goodDtoList = null;
		return goodDtoList;

	}


	public static List getBillingRuleDTOByType(String cntType) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List brDtoList = new ArrayList();
		String sql = "select t.* from t_brcondition t where t.cntType='"
				+ cntType + "' " + "order by BRCNTID desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				BrconditionDTO dto = DtoFiller.fillBrconditionDTO(rs);
				brDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getBillingRuleDTOByType", e);

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

		if (brDtoList.size() <= 0)
			brDtoList = null;
		return brDtoList;

	}

	/**
	 * Chen jiang 根据campaignID找出关联的市场活动
	 * 
	 * @param campaignID
	 * @return
	 */
	public static List getCampaignCondProductDTOList(int campaignID) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List brDtoList = new ArrayList();
		String sql = "select t.* from T_Campaign_Cond_Product t where campaignid="
				+ campaignID + " order by seqno desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				CampaignCondProductDTO dto = DtoFiller
						.fillCampaignCondProductDTO(rs);
				brDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignCondProductDTOList", e);

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

		if (brDtoList.size() <= 0)
			brDtoList = null;
		return brDtoList;

	}

	/**
	 * Chen jiang 根据campaignID找出关联的市场活动
	 * 
	 * @param campaignID
	 * @return
	 */
	public static List getBundlePayMethodDTOList(int bundleID) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List bmDtoList = new ArrayList();
		String sql = "select t.* from t_bundlepaymentmethod t where bundleid="
				+ bundleID + " order by bundleID desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				BundlePaymentMethodDTO dto = DtoFiller
						.fillBundlePaymentMethodDTO(rs);
				bmDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getBundlePayMethodDTOList", e);

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

		if (bmDtoList.size() <= 0)
			bmDtoList = null;
		return bmDtoList;

	}

	/**
	 * Chen jiang 根据campaignID找出关联的市场活动
	 * 
	 * @param campaignID
	 * @return
	 */
	public static BundlePrepaymentDTO getBundlePrepaymentDTO(int campaignID) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		BundlePrepaymentDTO dto = null;
		// List bmDtoList = new ArrayList();
		String sql = "select t.* from T_BundlePrepayment t where campaignID="
				+ campaignID + " order by campaignID desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dto = DtoFiller.fillBundlePrepaymentDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getBundlePrepaymentDTO", e);

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

	public static List getCampaignCondPackageDTOList(int campaignID) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List brDtoList = new ArrayList();
		String sql = "select t.* from T_Campaign_Cond_Package t where campaignid="
				+ campaignID + " order by seqno desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				CampaignCondPackageDTO dto = DtoFiller
						.fillCampaignCondPackageDTO(rs);
				brDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignCondPackageDTOList", e);

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

		if (brDtoList.size() <= 0)
			brDtoList = null;
		return brDtoList;

	}

	public static List getCampaignAgmtCampaignDTOList(int campaignID) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List brDtoList = new ArrayList();
		String sql = "select t.* from T_CAMPAIGN_AGMT_CAMPAIGN t where campaignid="
				+ campaignID + " order by TargetBundleId desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				CampaignAgmtCampaignDTO dto = DtoFiller
						.fillCampaignAgmtCampaignDTO(rs);
				brDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignAgmtCampaignDTOList", e);

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

		if (brDtoList.size() <= 0)
			brDtoList = null;
		return brDtoList;

	}

	public static CampaignAgmtCampaignDTO getCampaignAgmtCampaignDTO(
			int campaignID, int bundleID) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		CampaignAgmtCampaignDTO dto = null;
		String sql = "select t.* from T_CAMPAIGN_AGMT_CAMPAIGN t where campaignid="
				+ campaignID
				+ " and TargetBundleId ="
				+ bundleID
				+ " order by TargetBundleId desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dto = DtoFiller.fillCampaignAgmtCampaignDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignAgmtCampaignDTOList", e);

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
	 * Chen jiang 根据积分活动id找出货物dto
	 * 
	 * @param campaignID
	 * @return
	 */
	public static List getCampaignCondCampaignDTOList(int campaignID) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List brDtoList = new ArrayList();
		String sql = "select t.* from T_Campaign_Cond_Campaign t where campaignid="
				+ campaignID + " order by seqno desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CampaignCondCampaignDTO dto = DtoFiller
						.fillCampaignCondCampaignDTO(rs);
				brDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignCondCampaignDTOList", e);

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

		if (brDtoList.size() <= 0)
			brDtoList = null;
		return brDtoList;

	}

	public static CampaignCondProductDTO getCampaignCondProductDTO(int seqNo) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		CampaignCondProductDTO dto = null;
		String sql = "select t.* from T_Campaign_Cond_Product t where seqno= "
				+ seqNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dto = DtoFiller.fillCampaignCondProductDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignCondProductDTO", e);

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

	public static BundlePaymentMethodDTO getBundlePaymentMethodDTO(
			int bundleID, String flag) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		BundlePaymentMethodDTO dto = null;
		String sql = "select t.* from t_bundlepaymentmethod t where bundleid= "
				+ bundleID + "" + " and RfBillingCycleFlag='" + flag + "'";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				dto = DtoFiller.fillBundlePaymentMethodDTO(rs);

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getBundlePaymentMethodDTO", e);

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

	public static CampaignCondPackageDTO getCampaignCondPackageDTO(int seqNo) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		CampaignCondPackageDTO dto = null;
		String sql = "select t.* from T_Campaign_Cond_Package t where seqno= "
				+ seqNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dto = DtoFiller.fillCampaignCondPackageDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignCondPackageDTO", e);

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

	public static CampaignCondCampaignDTO getCampaignCondCampaignDTO(int seqNo) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		CampaignCondCampaignDTO dto = null;
		String sql = "select t.* from T_Campaign_Cond_Campaign t where seqno= "
				+ seqNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dto = DtoFiller.fillCampaignCondCampaignDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignCondPackageDTO", e);

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
	 * david.Yang 根据产品ID获得产品名称
	 */
	public static String getProductNameByID(int id) {

		return getStringBySQL("select productname from t_product where productid="
				+ id);
	}

	public static String getConditionNameByID(int id) {

		return getStringBySQL("select CondName from T_LDAPCondition where condid="
				+ id);
	}

	public static String getProductNameByFlag(int id) {

		return getStringBySQL("select productname from t_product where productid="
				+ id + " and newsaflag='Y' and status<>'T'");
	}

	public static Map getProductNameByFlag() {

		return getMapBySQL("select productid ,productname from t_product where  newsaflag='Y' and status<>'T'");
	}

	public static Map getCondMap() {

		return getMapBySQL("select condid ,condname from T_LDAPCondition");
	}

	/**
	 * chen jiang 根据产品ID获得产品SmsProduct名称
	 */
	public static String getSMSProductNameByID(int id) {
		return getStringBySQL("select productname from t_product where  productclass='S' and productid="
				+ id);
	}

	/**
	 * david.Yang 根据产品包ID获得产品包名称
	 */
	public static String getPackagenameByID(int id) {
		return getStringBySQL("select packagename from t_productpackage where packageid="
				+ id);
	}

	/**
	 * nile.chen 根据brcID获得cntname
	 */
	public static String getCntNameByID(int id) {
		return getStringBySQL("select cntname from t_brcondition where brcntid="
				+ id);
	}

	/**
	 * david.Yang 根据优惠活动ID获得优惠活动名称
	 */
	public static String getCampaignNameByID(int id) {
		return getStringBySQL("select campaignname from t_campaign where campaignid="
				+ id);
	}

	/**
	 * david.Yang 根据受理单ID获得相关工单ID
	 */
	public static int getJobCardIDByCsiID(int csiid) {
		return getIntBySQL("select ReferJobCardID from t_custserviceinteraction where id="
				+ csiid);
	}

	/**
	 * nile.chen 根据报修单ID获得相关工单ID
	 */
	public static int getJobCardIDByCustProblemId(int custProblemId) {
		return getIntBySQL("select ReferJobCardID from t_customerproblem where id="
				+ custProblemId);
	}

	/**
	 * david.Yang
	 * 
	 * @param id
	 * @return String
	 */
	public static String getOperatorNameByID(int id) {
		return getStringBySQL("select operatorname from t_operator where operatorid="
				+ id);
	}

	public static int getOperatorIDByLoginID(String loginID) {
		return getIntBySQL("select operatorid from t_operator where loginid='"
				+ loginID + "'");
	}
	
	public static int getOrgIDByOperatorID(String operatorid){
		return getIntBySQL("select orgid from t_operator where operatorid="+ operatorid );
	}

	/**
	 * david.yang
	 * 
	 * @param detailno
	 * @return
	 */
	public static ArrayList getProductPackageDTOByGroupDetail(String detailno) {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select d.* "
				+ " From t_groupbargain a ,t_groupbargaindetail b ,T_BUNDLE2CAMPAIGN c,t_productpackage d"
				+ " Where a.Id =b.groupbargainId And b.detailno ='"
				+ detailno
				+ "' "
				+ " And a.campaignid =c.campaignid And c.PackageID =d.packageId And (b.status ='S' or b.status ='L')";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductPackageDTO dto = DtoFiller.fillProductPackageDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductPackageDTOByGroupDetail", e);
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
	 * david.yang
	 * @param custId
	 * @return
	 */
	public static ArrayList getCustMarketInfoByCustId(int custId) {
		ArrayList marketInfoList = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_newcustomermarketinfo where newcustomerid ="
				+ custId;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				NewCustomerMarketInfoDTO dto = DtoFiller
						.fillNewCustomerMarketInfoDTO(rs);
				marketInfoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustMarketInfoByCustId", e);
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
		return marketInfoList;
	}

	/**
	 * david.Yang
	 * 
	 * @param csiId
	 * @return
	 */
	public static CsiProcessLogDTO getCsiProcessLogDTO(int csiId) {
		CsiProcessLogDTO csiprocesslogDto = new CsiProcessLogDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList lst = new ArrayList();
		String sql = "select * from t_csiprocesslog where csiid =" + csiId;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CsiProcessLogDTO dto = DtoFiller.fillCsiProcessLogDTO(rs);
				lst.add(dto);
			}
			Iterator iter = lst.iterator();
			int count = 0;
			while (iter.hasNext()) {
				CsiProcessLogDTO dto = (CsiProcessLogDTO) iter.next();
				count = count + 1;
				if (count > 1) {
					if (Constant.CUSTSERVICEINTERACTION_STATUS_NEW.equals(dto
							.getAction())) {
						csiprocesslogDto = dto;
						break;
					}
				} else {
					csiprocesslogDto = dto;
				}
			}

		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getCsiProcessLogDTO",
					e);
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
		return csiprocesslogDto;
	}

	public static String getCampaignNameByBargainDetailNo(String bargainDetailNo) {
		return getStringBySQL(" select  c.campaignname from T_GroupBargainDetail gbd,T_GroupBargain gb, T_Campaign c"
				+ " where gbd.detailno ='"
				+ bargainDetailNo
				+ "' and gbd.groupbargainid =gb.id and gb.campaignid =c.campaignid");
	}

	/**
	 * david.Yang 获得某帐务周期的名称
	 */
	public static String getBillingCycleNameByID(int id) {
		return getStringBySQL("select t.Name from t_billingcycle t where ID="
				+ id);
	}

	/**
	 * david.Yang 根据customerID获得 名称
	 */
	public static String getCustomerNameByID(int id) {
		return getStringBySQL("select name from t_customer where customerid="
				+ id);
	}

	/**
	 * Nile.chen根据packageId ,productId 获得 可选标志
	 */
	public static String getOptionFlag(int packageId, int productId) {
		return getStringBySQL("select optionFlag from t_packageline where packageId="
				+ packageId + " and productId = " + productId);
	}

	/**
	 * Nile.chen根据packageId ,productId 获得 可选标志
	 */
	public static int getCampaignIDByPackageID(int packageId) {
		return getIntBySQL("select campaignid from T_BUNDLE2CAMPAIGN where packageId="
				+ packageId);
	}

	/**
	 * Stone Liang
	 * 
	 * @param strid
	 * @return
	 */
	public static AcctItemTypeDTO getAcctItemTypeDTOByAcctItemTypeID(
			String acctItemTypeId) {
		AcctItemTypeDTO dto = new AcctItemTypeDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql = "select * from t_acctitemtype  t where t.acctitemtypeid='"
					+ acctItemTypeId + "'";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillAcctItemTypeDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "AcctItemTypeDTO", e);
			dto = null;
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"AcctItemTypeDTO", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"AcctItemTypeDTO", e);
				}
			}
		}
		return dto;
	}

	public static String getAcctItemTypeByAcctItemTypeID(String id) {
		return getAcctItemTypeDTOByAcctItemTypeID(id).getAcctItemTypeName();
	}

	/**
	 * Stong Liang 取得所有的费用类别ID和名称
	 */
	public static Map getAllFeeType() {
		return getMapBySQL(
				"select acctitemtypeid, acctitemtypename from t_acctitemtype where status='V' Order By feetype Asc",
				true, true);
	}

	/**
	 * Nile chen 取得所有的分区名称
	 */
	public static Map getAllMarketSegmentName() {
		return getMapBySQL("select id, name from t_marketsegment where status='V'");
	}

	/**
	 * Nile chen 创建计费规则时取出客户类型
	 */
	public static Map getBillingRuleByCustType(String cntType) {
		return getMapBySQL("select brcntid, cntname from t_brcondition where status='V' and cnttype ='"
				+ cntType + "'");
	}

	/**
	 * Nile chen 取得所有更支付有关的系统事件
	 * 
	 */
	public static Map getBillingSystemEvent(String type) {
		String quearStr = "select eventclass, eventname from t_systemeventdef where eventtype like '%"
				+ type + "%' order by eventclass asc";
		return getMapBySQL(quearStr, true, true);
	}

	public static Map getAllDistrictName() {
		return getMapBySQL("select id, name from t_districtsetting");
	}

	public static String getNameByMopID(int mopId) {
		return getStringBySQL("select name from t_methodofpayment where AccountFlag='Y' and status='V' and mopid="
				+ mopId);
	}

	public static String getNameByMopIDForPay(int mopId) {
		return getStringBySQL("select name from t_methodofpayment where  mopid="
				+ mopId);
	}

	public static String getCsiPaymentOption(String csiType) {
		return getStringBySQL("select t.CsiPaymentOption from T_CsiPaymentSetting t where t.csiType='"
				+ csiType + "'");
	}

	public static String getAlterFeeFlag(String csiType) {
		return getStringBySQL("select t.AlterFeeFlag from T_CsiPaymentSetting t where t.csiType='"
				+ csiType + "'");
	}

	/**
	 * david.Yang 设备序列号转化成设备号的函数
	 */
	public static int getTerminalDeviceIDBySerialNo(String serial) {
		return getIntBySQL("select DEVICEID from t_TerminalDevice where SERIALNO='"
				+ serial + "'");
	}

	/**
	 * david.Yang 根据customerID获得 名称
	 */
	public static String getNewCustomerNameByCsiID(int csiid) {
		return getStringBySQL("select t.name from t_newcustomerinfo t where csiid="
				+ csiid);
	}

	/**
	 * 根据csiid得到合同编号
	 * 
	 * @param csiid
	 * @return
	 */
	public static String getNewCustomerConNOByCsiID(int csiid) {
		return getStringBySQL("select t.contractno from t_newcustomerinfo t where t.csiid="
				+ csiid);
	}

	/**
	 * david.Yang
	 * 
	 * @return
	 */
	public static AddressDTO getAddressDTO(int csiid) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AddressDTO dto = null;
		String sql = "select t.* from t_address t, t_newcustomerinfo n where n.csiid="
				+ csiid + " and n.fromaddressid=t.addressid";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillAddressDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getAddressDTO", e);
			dto = null;
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

	public static NewCustomerInfoDTO getNewCustomerInfoDTOByCSIID(int csiid) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		NewCustomerInfoDTO dto = null;
		String sql = "select * from t_newcustomerinfo t where t.csiid=" + csiid;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillNewCustomerInfoDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNewCustomerInfoDTOByCSIID", e);
			dto = null;
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

	public static OldCustomerInfoDTO getOldCustomerInfoDTOByCSIID(int csiid) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		OldCustomerInfoDTO dto = null;
		String sql = "select * from t_oldcustomerinfo t where t.csiid=" + csiid;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillOldCustomerInfoDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNewCustomerInfoDTOByCSIID", e);
			dto = null;
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
	 * 根据单据类型和ID,来取得费用集合, 用于判断报修单是否有费用,也可以用来判断
	 * 
	 * @param id
	 * @param type
	 * @return
	 */

	public static Collection getAllFeeListByCsiAndType(int id, String type) {
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		buf.append("select * from t_accountitem where  ReferType='").append(
				type).append("' and ").append("ReferID=").append(id);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO dto = DtoFiller.fillAccountItemDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllFeeListByCsiAndType", e);
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

	public static Collection getAllPaymentListByCsiAndType(int id, String type) {
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		buf.append("select * from t_PaymentRecord  where  SourceType='")
				.append(type).append("' and ").append("SourceRecordID=")
				.append(id);

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				PaymentRecordDTO dto = DtoFiller.fillPaymentRecordDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllPaymentListByCsiAndType", e);
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

	public static Collection getAllPrepaymentDeductionRecordListByCsiAndType(
			int id, String type) {
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		buf
				.append(
						"select *  from t_prepaymentdeductionrecord  where  REFERRECORDTYPE='")
				.append(type).append("' and ").append("REFERRECORDID=").append(
						id);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				PrepaymentDeductionRecordDTO dto = DtoFiller
						.fillPrepaymentDeductionRecordDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllPrepaymentDeductionRecordListByCsiAndType", e);
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
	
	public static Map getAllSAName() {
		return getMapBySQL("select TO_CHAR(ServiceID), ServiceName from T_Service");
	}

	/**
	 * david.Yang
	 * 
	 * @return
	 */
	public static Collection getAllCsiFeeListByCsiAndType(int id, String typeStatus) {
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		buf.append("select * from t_accountitem where  ReferType='").append(
				Constant.AIREFERTYPE_M).append("' and ").append("ReferID=")
				.append(id);
		if ((typeStatus != null) && (!typeStatus.equals("")))
			buf.append(" and status in('").append(typeStatus.replaceAll(";", "','"))
					.append("')");
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO dto = DtoFiller.fillAccountItemDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllCsiFeeListByCsiAndType", e);
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

	public static Collection getAccountItemByInvoiceNo(int invoiceNo) {
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_accountItem where invoiceNo ="
				+ invoiceNo + "order by setoffflag,ai_no";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO dto = DtoFiller.fillAccountItemDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAccountItemByInvoiceNo", e);
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
	 * david.Yang
	 * 
	 * @return
	 */
	public static Collection getAllCsiPaymentListByCsiAndType(int id) {
		Collection list = new Vector();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		buf.append("select * from t_paymentrecord where SourceType='").append(
				Constant.PAYMENTRECORDSOURCETYPE_ACCEPT).append("' and ")
				.append("SourceRecordID=").append(id);

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			System.out.println("buf=========" + buf.toString());
			rs = stmt.executeQuery(buf.toString());
			while (rs.next()) {
				PaymentRecordDTO dto = DtoFiller.fillPaymentRecordDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllCsiPaymentListByCsiAndType", e);
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

	public static Collection getPaymentRecordByInvoiceNo(int invoiceNo) {
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_paymentrecord where invoiceNo ="
				+ invoiceNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PaymentRecordDTO dto = DtoFiller.fillPaymentRecordDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAccountItemByInvoiceNo", e);
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

	public static Collection getPrepaymentDeductionRecord(int invoiceNo) {
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_prepaymentdeductionrecord where invoiceNo ="
				+ invoiceNo;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				PrepaymentDeductionRecordDTO dto = DtoFiller
						.fillPrepaymentDeductionRecordDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getPrepaymentDeductionRecord", e);
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
	 * david.Yang
	 * 
	 * @return
	 */
	public static Map getAcctitemtype(String feetype) {
		Map acctItemTypeMap = new LinkedHashMap();

		String sql = "select ACCTITEMTYPEID,ACCTITEMTYPENAME from T_ACCTITEMTYPE where SHOWLEVEL='1' and status ='V'";

		// modify by zhouxushun
		if (!(feetype == null || "".equals(feetype)))
			sql = sql + "and feetype='" + feetype + "'";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				acctItemTypeMap.put(rs.getString("ACCTITEMTYPEID"), rs
						.getString("ACCTITEMTYPENAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getAcctitemtype", e);
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
		return acctItemTypeMap;
	}

	public static AccountDTO getAccountDto(int accountID) {
		String sql = "select * from T_ACCOUNT where ACCOUNTID =" + accountID;
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
			LogUtility.log(Postern.class, LogLevel.WARN, "getAccountDto", e);
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

	public static DistrictSettingDTO getDistrictDto(int id) {
		String sql = "select * from t_districtsetting where id =" + id;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DistrictSettingDTO dto = new DistrictSettingDTO();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto = DtoFiller.fillDistrictSettingDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getDistrictDto", e);
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

	public static Map getCallbackInfo(String referSourceType, int referSourceID) {
		String sql = "select * from t_callbackinfo t where t.refersourcetype='"
				+ referSourceType + "' and t.refersourceid=" + referSourceID;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap mp = new HashMap();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				// 如果MEMO为空，则再从INFOVALUEID中取值
				String res = rs.getString("MEMO");
				Integer settingID = new Integer(rs.getInt("INFOSETTINGID"));
				if (res == null || "".equals(res)) {
					res = String.valueOf(rs.getInt("INFOVALUEID"));
					if ("0".equals(res))
						res = "";
				}

				// 有可能是多个值---checkBox
				if (mp.containsKey(settingID)) {
					res = mp.get(settingID) + "-" + res;
				}

				mp.put(settingID, res);
			}

		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getCallbackInfo", e);
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
		return mp;
	}

	/**
	 * 通过客户ID和table表得到相应的调查 add by zhouxushun , 2005-12-21
	 * 仅使用于t_custmarketinfo/t_diagnosisinfo/t_newcustomermarketinfo/t_callbackinfo/t_materialusage
	 * 
	 * @param custId
	 * @param table
	 * @param filed
	 * @return
	 */
	public static Map getServeyResultByCustIdForRealUser(int id,
			String tableName, String field) {
		if (id == 0 || tableName == null || "".equals(tableName)
				|| field == null || "".equals(field))
			return null;

		Map resultMap = new LinkedHashMap();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from  " + tableName + " where " + field + "="
				+ id;

		LogUtility.log(Postern.class, LogLevel.DEBUG, "当前执行调查查找的SQL为" + sql);

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 如果MEMO为空，则再从INFOVALUEID中取值
				String res = rs.getString("MEMO");
				Integer settingID = new Integer(rs.getInt("INFOSETTINGID"));
				if (res == null || "".equals(res)) {
					res = String.valueOf(rs.getInt("INFOVALUEID"));
					if ("0".equals(res))
						res = "";
				}
				if (resultMap.containsKey(settingID)) {
					res = resultMap.get(settingID) + "-" + res;
				}

				resultMap.put(settingID, res);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getServeyResultByCustIdForRealUser", e);
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
		return resultMap;
	}

	/**
	 * 客户查询的时候,根据封装的市场信息,取客户ID,返回如"1,2,3"这样的字符串,如果查不到结果,返回"-1";
	 * -1用于和未设置市场信息的空区分.
	 * 
	 * @param markinfoCol
	 * @return
	 */
	public static String getCustomerIdsBycustomerMarketInfo(
			Collection markinfoCol) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String customerIds = "";
		if (markinfoCol == null || markinfoCol.isEmpty())
			return customerIds;
		StringBuffer sql = new StringBuffer();
		String base = "SELECT DISTINCT CUSTOMERID FROM T_CUSTMARKETINFO WHERE 1 = 1 ";
		boolean isDepend = false;
		for (Iterator it = markinfoCol.iterator(); it.hasNext();) {
			NewCustomerMarketInfoDTO dto = (NewCustomerMarketInfoDTO) it.next();
			if (dto.getInfoSettingId() <= 0)
				continue;
			if (dto.getInfoValueId() > 0) {
				if (isDepend)
					sql.append("INTERSECT ");
				sql.append(base).append("AND ");
				sql.append("INFOSETTINGID = ").append(dto.getInfoSettingId())
						.append(" AND INFOVALUEID = ").append(
								dto.getInfoValueId()).append(" ");
				isDepend = true;
			}
			if (dto.getMemo() != null && !"".equals(dto.getMemo())) {
				if (isDepend)
					sql.append("INTERSECT ");
				sql.append(base).append("AND ");
				sql.append("INFOSETTINGID = ").append(dto.getInfoSettingId())
						.append(" AND MEMO = '").append(dto.getMemo()).append(
								"' ");
				isDepend = true;
			}
		}
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getCustomerIdsBycustomerMarketInfo:", sql.toString());
		if (!isDepend)
			return customerIds;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (customerIds.equals("")) {
					customerIds = rs.getString("CustomerId");
				} else {
					customerIds = customerIds + ","
							+ rs.getString("CustomerId");
				}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerIdsBycustomerMarketInfo", e);
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
		if (customerIds == null || "".equals(customerIds)) {
			customerIds = "-1";
		}
		return customerIds;
	}

	/**
	 * add by zhouxushun 2005-10-11
	 * 
	 * @param deviceClass
	 * @return
	 */
	public static String getDeviceClassDescByDeviceClass(String deviceClass) {
		return getStringBySQL("select DESCRIPTION from T_DEVICECLASS where DEVICECLASS='"
				+ deviceClass + "'");
	}

	public static String getDeviceClassDesc(String deviceClass) {
		return getDeviceClassDescByDeviceClass(deviceClass);
	}

	public static String getDeviceModelDesc(String deviceModel) {
		return getStringBySQL("select DESCRIPTION from T_DEVICEMODEL where DEVICEMODEL='"
				+ deviceModel + "'");
	}

	public static CPCampaignMapDTO getCPCampaignMapByCustProductID(
			int custProductID) {
		CPCampaignMapDTO dto = new CPCampaignMapDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from T_CPCAMPAIGNMAP where  status='V' and custproductid = ? ");
			stmt.setInt(1, custProductID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto.setId(rs.getInt("ID"));
				dto.setCustProductID(rs.getInt("CUSTPRODUCTID"));
				dto.setStatus(rs.getString("Status"));
				dto.setCcId(rs.getInt("CCID"));
				dto.setDtCreate(rs.getTimestamp("DT_CREATE"));
				dto.setDtLastmod(rs.getTimestamp("DT_LASTMOD"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCPCampaignMapByCustProductID", e);
			dto = null;
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
	 * 通过调查类型得到相关的调查列表 add by zhouxushun ,for servey , 2005-12-07
	 * 
	 * @param serverType
	 * @return
	 */
	public static Collection getServeyListByServeyType(String serverType,
			String serveySubType, String serviceID) {
		ArrayList resultList = new ArrayList();
		DynamicServeyDTO dto = null;

		Connection con = null;
		java.sql.Statement stmtTopic = null;
		java.sql.Statement stmtContext = null;
		ResultSet rsTopic = null;
		ResultSet rsContext = null;
		String sqlTopic = "select * from T_BIDIMCONFIGSETTING where 1=1 and STATUS='"
				+ Constant.GENERAL_STATUS_VALIDATE + "'";

		try {
			con = DBUtil.getConnection();
			stmtTopic = con.createStatement();
			stmtContext = con.createStatement();
			if (!(serverType == null || "".equals(serverType))) {
				sqlTopic = sqlTopic + "  and CONFIGTYPE='" + serverType + "'";
			}
			if (!(serveySubType == null || "".equals(serveySubType))) {
				sqlTopic = sqlTopic + "  and CONFIGSUBTYPE='" + serveySubType
						+ "'";
			}

			// zyg 2007.10.15 begin
			// 处理多业务安装的情况，将 serviceID 中使用 分号 分割 ，改为 使用 逗号 进行分割
			// 然后改为使用 in 操作进行查询
			// 并支持 配置模块中 不设置 业务类型 的情况
			/** ********************************************* */
			// if (!(serviceID == null || "".equals(serviceID))) {
			// sqlTopic = sqlTopic + " and SERVICEID=" + serviceID;
			// }
			if (!(serviceID == null || "".equals(serviceID))) {
				serviceID = serviceID.replace(';', ',');
				sqlTopic = sqlTopic + "  and (SERVICEID in (" + serviceID
						+ ") or nvl(serviceid,0) = 0) ";
			}

			/** ********************************************* */
			// zyg 2007.10.15 end
			LogUtility.log(Postern.class, LogLevel.DEBUG, "当前执行调查查找的SQL为： "
					+ sqlTopic);

			rsTopic = stmtTopic.executeQuery(sqlTopic);
			while (rsTopic.next()) {
				int serveyID = rsTopic.getInt("ID");

				// 填充dto
				dto = new DynamicServeyDTO();
				boolean allowNullFlag = Constant.GENERAL_FLAG_Y.equals(rsTopic
						.getString("ALLOWNULL"));
				dto.setDescription(rsTopic.getString("NAME"));
				dto.setServeyID(rsTopic.getInt("ID"));
				String valueType = rsTopic.getString("VALUETYPE");
				dto.setShowType(valueType);

				if (!allowNullFlag) {
					dto.setForceWrite(true);
				}
				// 填充dto的map
				if (serveyID > 0
						&& !(Constant.SERVEY_TAGTYPE_TEXT.equals(valueType))
						|| Constant.SERVEY_TAGTYPE_DATE.equals(valueType)) {
					String sqlContext = "select * from T_BIDIMCONFIGSETTINGVALUE where 1=1 and STATUS='"
							+ Constant.GENERAL_STATUS_VALIDATE + "'";
					sqlContext = sqlContext + " and SETTINGID=" + serveyID
							+ "  order by priority,code";
					LogUtility.log(Postern.class, LogLevel.DEBUG,
							"当前执行调查查找内容的SQL为： " + sqlContext);

					rsContext = stmtContext.executeQuery(sqlContext);
					while (rsContext.next()) {
						if ("Y".equalsIgnoreCase(rsContext
								.getString("DEFAULTORNOT")))
							dto.setDefaultList(rsContext.getString("ID"));

						CommonSettingValue settingValueDto = new CommonSettingValue();
						settingValueDto.setKey(rsContext.getString("ID"));
						settingValueDto.setValue(rsContext
								.getString("DESCRIPTION"));
						dto.put(String.valueOf(rsContext.getInt("ID")),
								settingValueDto);
					}
				}
				resultList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getServeyListByServeyType", e);
			dto = null;
		} finally {
			if (stmtTopic != null) {
				try {
					stmtTopic.close();
				} catch (SQLException e) {
				}
			}
			if (stmtContext != null) {
				try {
					stmtContext.close();
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

		return resultList;
	}

	/**
	 * 通过调查设置的ID和其代码得到显示给WEB的值 add by zhouxushun ,for Servey , 2005-12-07
	 * 
	 * @param settingID
	 * @param code
	 * @return
	 */
	public static String getServeyTitleBySettingIDAndCode(int settingID,
			String[] settingValueIdStr) {
		String description = "";
		String settingValueIds = "";
		if (settingValueIdStr != null) {
			for (int i = 0; i < settingValueIdStr.length; i++) {
				if (!"".equals(settingValueIds))
					settingValueIds = settingValueIds + ","
							+ settingValueIdStr[i];
				else
					settingValueIds = settingValueIdStr[i];
			}
		}

		LogUtility.log(Postern.class, LogLevel.DEBUG, "传入的settingID="
				+ settingID + " ,传入的code= " + settingValueIds);

		if (settingID == 0 || "".equals(settingValueIds))
			return null;

		String valueTypeSql = "select VALUETYPE from T_BIDIMCONFIGSETTING where 1=1 and STATUS='"
				+ Constant.GENERAL_STATUS_VALIDATE + "'";
		valueTypeSql = valueTypeSql + " and ID=" + settingID;
		String valueType = getStringBySQL(valueTypeSql);
		if (valueType == null || "".equals(valueType))
			return "";
		// 如果属于文本框填入则直接返回
		if (Constant.SERVEY_TAGTYPE_DATE.equals(valueType)
				|| Constant.SERVEY_TAGTYPE_TEXT.equals(valueType)) {
			return settingValueIds.replaceAll(",", "-");
		}

		String sql = "select DESCRIPTION from T_BIDIMCONFIGSETTINGVALUE where 1=1 and STATUS='"
				+ Constant.GENERAL_STATUS_VALIDATE + "'";
		sql = sql + " and ID in (" + settingValueIds + ")";
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"通过settingID和code得到的SQL查询语句为：" + sql);

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (!description.equals(""))
					description = description + " &nbsp;◆◆◆&nbsp; "
							+ rs.getString(1);
				else
					description = rs.getString(1);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getServeyTitleBySettingIDAndCode", e);
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

		return description;
	}

	public static CustomerProductDTO getCustomerProductDTOByPSID(int psID) {
		CustomerProductDTO dto = new CustomerProductDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("SELECT * FROM T_CUSTOMERPRODUCT WHERE PSID=?");
			stmt.setInt(1, psID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillCustomerProductDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerProductDTOByPSID", e);
			dto = null;
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
	 * 通过客户的产品ID得到产品名
	 * 
	 * @param psid
	 * @return
	 */
	public static String getProductNameByPSID(int psid) {
		CustomerProductDTO dto = getCustomerProductDTOByPSID(psid);

		if (dto != null) {
			return getProductNameByID(dto.getProductID());
		} else {
			return "";
		}
	}

	// 侯2007-12-13修改,从缓存中取值.
	public static String getCommonSettingDataValueByNameAndKey(String name,
			String key) {
		Map map = commonSettingDataCache.containsKey(name) ? (Map) commonSettingDataCache
				.get(name)
				: new HashMap();
		return map.containsKey(key) ? ((CommonSettingValue) map.get(key))
				.getValue() : "";
	}

	/**
	 * 
	 * @param cpID
	 * @return
	 */
	public static Collection getScheduleIDsByCPID(int cpID) {
		Collection idsList = new ArrayList();

		String sql = "select batch.BATCHID,item.STATUS from T_BATCHJOB batch,T_BATCHJOBITEM item where item.PSID="
				+ cpID + " and ";
		sql = sql
				+ " item.BATCHID=batch.BATCHID and ( batch.JOBTYPE='PS' or batch.JOBTYPE = 'PC' or batch.JOBTYPE='PR') and batch.STATUS='N' ";

		LogUtility.log(Postern.class, LogLevel.DEBUG, "sql =" + sql);

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (rs.getInt("BATCHID") > 0) {
					idsList.add(new Integer(rs.getInt("BATCHID")));
				}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getScheduleIDsByCPID", e);
			idsList = null;
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
		return idsList;
	}

	/**
	 * 通过批处理ID得到批处理DTO
	 * 
	 * @param id
	 * @return
	 */
	public static QueryDTO getQueryDTOByQueryID(int id) {
		QueryDTO dto = new QueryDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("SELECT * FROM T_Query WHERE QueryID=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto =DtoFiller.fillQueryDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getQueryDTOByQueryID", e);
			dto = null;
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
	 * 通过批处理任务单ID得到批处理任务单DTO
	 * 
	 * @param id
	 * @return
	 */
	public static BatchJobDTO getBatchJobDTOByBatchID(int id) {
		BatchJobDTO dto = new BatchJobDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("SELECT * FROM T_BATCHJOB WHERE BatchID=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto =DtoFiller.fillBatchJobDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getBatchJobDTOByBatchID", e);
			dto = null;
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
	 * 得到所有有效的产品map，KEY为产品的ID(字符串格式)，value为产品的名称
	 * 
	 * @return
	 */
	public static Map getAllProductIDAndName() {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT PRODUCTID,PRODUCTNAME FROM T_PRODUCT WHERE STATUS='N' order by productid asc");
			while (rs.next()) {
				map.put("" + rs.getInt("PRODUCTID"), rs
						.getString("PRODUCTNAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllProductIDAndName", e);

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

	/**
	 * 得到所有有效的行政区域map，KEY为districtID(字符串格式)，value为name
	 * 
	 * @return
	 */
	public static Map getAllDistrictIDAndName() {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT id,name FROM t_districtsetting WHERE STATUS='V' order by id asc");
			while (rs.next()) {
				map.put("" + rs.getInt("id"), rs.getString("name"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllDistrictIDAndName", e);

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

	/**
	 * 得到所有销售过的产品map(不包括创建但没有销售过的)，KEY为产品的ID(字符串格式)，value为产品的名称
	 * 并按照：硬件、软件，然后按所属的业务种类，然后按产品ID 排序
	 * 
	 * @return
	 */
	public static Map getAllSellProductMap(String serviceId, String productType) {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sqlStrFrom = new StringBuffer();
		StringBuffer sqlStrWhere = new StringBuffer();
		sqlStrFrom
				.append("SELECT pro.PRODUCTID,pro.PRODUCTNAME FROM T_PRODUCT pro,T_ProductToService proser");
		sqlStrWhere
				.append(" WHERE pro.STATUS != 'R' and pro.PRODUCTID=proser.PRODUCTID");
		if ((serviceId != null) && (!"".equals(serviceId))) {
			sqlStrWhere.append(" and proser.SERVICEID=" + serviceId);
		}
		if ((productType != null) && (!"".equals(productType))) {
			sqlStrWhere.append(" and pro.ProductClass='" + productType + "'");
		}
		sqlStrWhere
				.append(" order by pro.ProductClass,proser.SERVICEID,pro.PRODUCTID");
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStrFrom.append(sqlStrWhere).toString());
			while (rs.next()) {
				map.put("" + rs.getInt("PRODUCTID"), rs
						.getString("PRODUCTNAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllProductIDAndName", e);

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

	/**
	 * 得到所有有效的优惠map，KEY为优惠的ID(just a word)，value为优惠的名称
	 * 
	 * @return
	 */
	public static Map getAllCampaignIDAndName() {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT CAMPAIGNID,CAMPAIGNNAME FROM T_CAMPAIGN WHERE STATUS='V' order by campaignid asc");
			while (rs.next()) {
				map.put("" + rs.getInt("CAMPAIGNID"), rs
						.getString("CAMPAIGNNAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllCampaignIDAndName", e);

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

	/**
	 * 得到父组织下的组织
	 * 
	 * @param orgIDs
	 * @return
	 */
	public static Map getOrgIDAndOrgNameByParentOrgIDs(String orgIDs) {

		HashMap map = new LinkedHashMap();

		if (orgIDs == null || "".equals(orgIDs))
			return map;

		// 处理字符串，把格式如 A,B,C 格式转化为 'A','B','C'
		if (orgIDs.indexOf("'") == -1) {
			String str[] = orgIDs.split(",");
			String result = "";
			for (int i = 0; i < str.length; i++) {
				if (!"".equals(result))
					result = result + ",";
				result = result + "'" + str[i] + "'";
			}
			orgIDs = result;
		}

		String sql = "SELECT ORGID,ORGNAME FROM T_ORGANIZATION WHERE STATUS='V' "
				+ "and orgtype in (" + orgIDs + ") and orgsubtype='S'";

		LogUtility.log(Postern.class, LogLevel.DEBUG, "当前执行的SQL语句为：" + sql);
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map.put("" + rs.getInt("ORGID"), rs.getString("ORGNAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getOrgIDAndOrgNameByParentOrgIDs", e);

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

	/**
	 * 得到所有有效的产品包map，KEY为产品包的ID(just int)，value为产品包的名称
	 * 
	 * @return
	 */
	public static Map getAllProductPackageIDAndName() {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT PACKAGEID,PACKAGENAME FROM T_PRODUCTPACKAGE WHERE STATUS='N' order by packageid asc");
			while (rs.next()) {
				map.put("" + rs.getInt("PACKAGEID"), rs
						.getString("PACKAGENAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllProductPackageIDsAndName", e);

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

	/**
	 * 得到所有历史的产品包map，KEY为产品包的ID(just int)，value为产品包的名称
	 * 
	 * @return
	 */
	public static Map getHistoryAllProductPackageIDAndName() {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT PACKAGEID,PACKAGENAME FROM T_PRODUCTPACKAGE WHERE "
							+ " 1=1 AND ( STATUS='C' or STATUS='T' ) ");
			while (rs.next()) {
				map.put("" + rs.getInt("PACKAGEID"), rs
						.getString("PACKAGENAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllProductPackageIDsAndName", e);

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

	/**
	 * 得到所有以销售过的的产品包map（包括正常的，原来销售现在取消或暂停的），KEY为产品包的ID(just int)，value为产品包的名称
	 * 
	 * @return
	 */
	public static Map getAllSellProductPackageIDAndName(String packageType) {
		HashMap map = new LinkedHashMap();

		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sqlFrom = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		sqlFrom
				.append("select propac.packageid PACKAGEID,propac.packagename PACKAGENAME from t_productpackage propac");
		sqlWhere.append(" where propac.status != 'R'");

		if ((packageType != null) && (!"".equals(packageType))) {
			sqlWhere.append(" and propac.PackageClass='" + packageType + "'");
		}
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlFrom.append(sqlWhere).toString());
			while (rs.next()) {
				map.put("" + rs.getInt("PACKAGEID"), rs
						.getString("PACKAGENAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllSellProductPackageIDAndName", e);

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

	/**
	 * 计算帐单的滞纳金
	 * @author Stone Liang
	 * @param dueDate   帐单到期日
	 * @param value     帐单应付金额
	 * @return 滞纳金
	 */
	public static double getPunishmentValue(java.sql.Timestamp dueDate,
			double value) {
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
			LogUtility.log(Postern.class, LogLevel.WARN, "getPunishmentValue",
					e);

		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
		
		return  (float)(Math.floor(punishmentValue*100)/100);   
	}
	/**
	 * @param fieldName(PunishmentAcctItemTypeID
	 *            滞纳金；ForcedDepositAcctItemTypeID 押金，ReturnDeviceAcctItemTypeID
	 *            设备退费）
	 * @return
	 * @throws ServiceException
	 */
	public static String getAcctItemTypeIDyFieldName(String fieldName) {
		String accTitemTypeID = getStringBySQL("Select * From t_acctitemtype Where acctitemtypeid In(Select "
				+ fieldName + " From T_FinancialSetting)");
		return accTitemTypeID;
	}

	/**
	 * 获得批量查询有效记录总数
	 * 
	 * @param queryid
	 *            int
	 * @return int
	 */
	public static int getValidTotalNum(int queryid) {
		return getIntBySQL("SELECT status FROM T_QueryResultItem WHERE QueryID= '"
					+ queryid + "' and status ='V'");
	}

	/**
	 * 获取押金的帐目类型
	 * 
	 * @return String 押金的帐目类型
	 * @throws ServiceException
	 */
	public static String getAcctItemType() {
		return getStringBySQL("select FORCEDDEPOSITACCTITEMTYPEID from T_FINANCIALSETTING");
	}

	/**
	 * 获取productlist
	 * 
	 * @return String productlist
	 * @throws ServiceException
	 */
	public static String getProductIDList() {
		// 获得数据库连接
		Connection dbConn = DBUtil.getConnection();
		if (dbConn == null)
			throw new RuntimeException("DB Connection error.");

		Statement sqlStrStmt = null;
		ResultSet rs = null;
		String productList = ""; // 产品列表

		String sqlStr = "select * from t_product";
		LogUtility.log(Postern.class, LogLevel.DEBUG, sqlStr);
		try {
			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sqlStr);
			while (rs.next()) {
				productList = productList + rs.getInt("PRODUCTID") + ";";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
				} catch (SQLException e) {
				}
			}
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
				}
			}
		}
		return productList;
	}


	public static String getDefaultAuthProductNameList() {
		String IDList = Postern.getCheckedProductIDList();
		String defaultProductNameList = ""; // 产品列表
		if (IDList.length() > 0) {
			String reguIDList = IDList.replaceAll(";", ",");
			// 获得数据库连接
			Connection dbConn = DBUtil.getConnection();
			if (dbConn == null)
				throw new RuntimeException("DB Connection error.");

			Statement sqlStrStmt = null;
			ResultSet rs = null;

			String sqlStr = "select ProductName from t_product where ProductID in("
					+ reguIDList + ")";
			LogUtility.log(Postern.class, LogLevel.DEBUG, sqlStr);
			try {
				sqlStrStmt = dbConn.createStatement();
				rs = sqlStrStmt.executeQuery(sqlStr);
				while (rs.next()) {
					if (defaultProductNameList.length() > 0)
						defaultProductNameList = defaultProductNameList + ";";
					defaultProductNameList = defaultProductNameList
							+ rs.getString("ProductName");
				}
			} catch (Exception e) {
				LogUtility.log(Postern.class, LogLevel.WARN, e);
				e.printStackTrace();
			} finally {
				if (rs !=null) {
					try{
						rs.close();
					}catch (SQLException e){
					}
				}
				if (sqlStrStmt != null) {
					try {
						sqlStrStmt.close();
					} catch (SQLException e) {
					}
				}
				if (dbConn != null) {
					try {
						dbConn.close();
					} catch (SQLException e) {
					}
				}
			}
		}
		return defaultProductNameList;
	}

	/**
	 * 获取checkingproductlist
	 * 
	 * @return String productlist
	 * @throws ServiceException
	 */
	public static String getCheckedProductIDList() {
		// 获得数据库连接
		Connection dbConn = DBUtil.getConnection();
		if (dbConn == null)
			throw new RuntimeException("DB Connection error.");

		Statement sqlStrStmt = null;
		ResultSet rs = null;
		String checkProductList = ""; // 产品列表

		String sqlStr = "select * from t_logisticssetting";
		LogUtility.log(Postern.class, LogLevel.DEBUG, sqlStr);
		try {
			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sqlStr);
			while (rs.next()) {
				int preProd1 = rs.getInt("PREAUTHPRODUCTID1");
				int preProd2 = rs.getInt("PREAUTHPRODUCTID2");
				int preProd3 = rs.getInt("PREAUTHPRODUCTID3");
				int preProd4 = rs.getInt("PREAUTHPRODUCTID4");
				int preProd5 = rs.getInt("PREAUTHPRODUCTID5");

				checkProductList = checkProductList + preProd1 + ";" + preProd2
						+ ";" + preProd3 + ";" + preProd4 + ";" + preProd5;
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
				} catch (SQLException e) {
				}
			}
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
				}
			}
		}
		return checkProductList;
	}

	/**
	 * 通过产品包获取checkingproductlist
	 * 
	 * @param int
	 *            packageID
	 * @return String productlist
	 * @throws ServiceException
	 */
	public static String getCheckedProductIDList(int packageID) {
		// 获得数据库连接
		Connection dbConn = DBUtil.getConnection();
		if (dbConn == null)
			throw new RuntimeException("DB Connection error.");

		Statement sqlStrStmt = null;
		ResultSet rs = null;
		String checkProductList = ""; // 产品列表

		String sqlStr = "select * from t_packageline where packageid = "
				+ packageID;

		LogUtility.log(Postern.class, LogLevel.DEBUG, sqlStr);
		try {
			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sqlStr);
			while (rs.next()) {
				int prodcutId = rs.getInt("PRODUCTID");

				checkProductList = checkProductList + prodcutId + ";";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
				} catch (SQLException e) {
				}
			}
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
				}
			}
		}
		return checkProductList;
	}

	/**
	 * 通过产品包获取checkmarketsementlist
	 * 
	 * @param int
	 *            packageID
	 * @return String productlist
	 * @throws ServiceException
	 */
	public static String getCheckedMarketIDList(int packageID) {
		// 获得数据库连接
		Connection dbConn = DBUtil.getConnection();
		if (dbConn == null)
			throw new RuntimeException("DB Connection error.");

		Statement sqlStrStmt = null;
		ResultSet rs = null;
		String checkMarketList = ""; // 市场分区

		String sqlStr = "select * from t_packagetomarketsegment where packageid = "
				+ packageID;

		LogUtility.log(Postern.class, LogLevel.DEBUG, sqlStr);
		try {
			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sqlStr);
			while (rs.next()) {
				int segmentId = rs.getInt("MARKETSEGMENTID");

				checkMarketList = checkMarketList + segmentId + ";";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
				} catch (SQLException e) {
				}
			}
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
				}
			}
		}
		return checkMarketList;
	}

	/**
	 * 得到组织数 add by jason.zhou
	 * 
	 * @param orgID
	 * @return
	 */
	public static Map getOrgTreeByOrgID(int orgID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String queryDistrictSettingSQL = "select orgid,orgname,parentorgid from t_organization where "
				+ "HASCUSTOMERFLAG='Y'and STATUS='V' connect by prior orgid=parentorgid start with orgid="
				+ orgID;
		Map districtSettingMap = new LinkedHashMap();
		Map currentBelongToMap = new LinkedHashMap();
		String startTree = "|-";
		String middleTree = "";
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(queryDistrictSettingSQL);
			rs = stmt.executeQuery();
			String id = "";
			String name = "";
			String belongTo = "";
			int currentLength = 0;
			while (rs.next()) {
				id = String.valueOf(rs.getInt("orgid"));
				name = rs.getString("orgname");
				belongTo = String.valueOf(rs.getInt("parentorgid"));
				if (currentBelongToMap.containsKey(belongTo)) {
					int currentLevel = Integer
							.parseInt((String) currentBelongToMap.get(belongTo));
					if (!currentBelongToMap.containsKey(new Integer(id))) {
						currentBelongToMap.put(id, String
								.valueOf(currentLevel + 1));
					}
					String tempString = "";
					for (int i = 0; i < currentLevel; i++) {
						tempString += "-";
					}
					middleTree = tempString;
					districtSettingMap.put(id, startTree + middleTree + name);
				} else {
					districtSettingMap.put(id, startTree + middleTree + name);
					currentBelongToMap.put(id, String
							.valueOf(currentLength + 1));
				}
			}
		} catch (SQLException e) {
			districtSettingMap = null;
			currentBelongToMap = null;
			LogUtility
					.log(Postern.class, LogLevel.WARN, "getOrgTreeByOrgID", e);
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
				} catch (SQLException sqle) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getOrgTreeByOrgID", sqle);
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getOrgTreeByOrgID", sqle);
				}
				conn = null;
			}
		}
		return districtSettingMap;
	}

	/**
	 * 得到仓库所有者的树
	 * 
	 * @param orgID
	 * @return
	 */
	public static Map getOwnerTreeByOrgID(int orgID) {
		return getOrgTree(orgID, "C:B");
	}

	/**
	 * 得到出库组织树,设备管理配置页面调用,
	 * 
	 * @param orgID
	 * @return
	 */
	public static Map getOutOrgTree(int orgID) {
		return getOrgTree(orgID, "B:C:D:O:S");
	}

	/**
	 * 取所有组织机构,不用类型过滤
	 * 
	 * @param orgID
	 * @return
	 */
	public static Map getOrgParentTree(int orgID) {
		return getOrgTree(orgID, null);
	}

	/**
	 * 得到一棵组织机构树,
	 * 
	 * @param orgID,顶级机构ID
	 * @param orgType,所要选取的组织机构的类型,用":"分隔,如:"A:B:C"
	 * @return
	 */
	public static Map getOrgTree(int orgID, String orgType) {
		if (orgType == null)
			orgType = "";
		String[] types = orgType.toUpperCase().split(":");
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < types.length; i++) {
			res.append("'");
			res.append(types[i]);
			res.append("'");
			if (i != types.length - 1)
				res.append(",");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer queryDistrictSettingSQL = new StringBuffer();
		queryDistrictSettingSQL.append("select orgid,orgname,parentorgid ");
		queryDistrictSettingSQL.append("from t_organization t ");
		queryDistrictSettingSQL.append("where t.status='V'");
		if (orgType != null && !orgType.equals("")) {
			queryDistrictSettingSQL.append(" and t.orgtype in (");
			queryDistrictSettingSQL.append(res.toString());
			queryDistrictSettingSQL.append(") ");
		}

		LogUtility.log(Postern.class, LogLevel.DEBUG, "获取组织树:\n"
				+ queryDistrictSettingSQL);

		Map districtSettingMap = new LinkedHashMap();
		Map currentBelongToMap = new LinkedHashMap();
		String startTree = "|-";
		String middleTree = "";
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(queryDistrictSettingSQL.toString());
			rs = stmt.executeQuery();
			String id = "";
			String name = "";
			String belongTo = "";
			int currentLength = 0;
			while (rs.next()) {
				id = String.valueOf(rs.getInt("orgid"));
				name = rs.getString("orgname");
				belongTo = String.valueOf(rs.getInt("parentorgid"));
				if (currentBelongToMap.containsKey(belongTo)) {
					int currentLevel = Integer
							.parseInt((String) currentBelongToMap.get(belongTo));
					if (!currentBelongToMap.containsKey(new Integer(id))) {
						currentBelongToMap.put(id, String
								.valueOf(currentLevel + 1));
					}
					String tempString = "";
					for (int i = 0; i < currentLevel; i++) {
						tempString += "-";
					}
					middleTree = tempString;
					districtSettingMap.put(id, startTree + middleTree + name);
				} else {
					districtSettingMap.put(id, startTree + middleTree + name);
					currentBelongToMap.put(id, String
							.valueOf(currentLength + 1));
				}
			}
		} catch (SQLException e) {
			districtSettingMap = null;
			currentBelongToMap = null;
			LogUtility.log(Postern.class, LogLevel.WARN, "getOwnerTreeByOrgID",
					e);
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
				} catch (SQLException sqle) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getOwnerTreeByOrgID", sqle);
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getOwnerTreeByOrgID", sqle);
				}
				conn = null;
			}
		}
		return districtSettingMap;
	}

	/**
	 * 通过客户ID得到客户所属的组织名 add by jason.zhou
	 * 
	 * @param custID
	 * @return
	 */
	public static String getOrgNameByCustomerID(int custID) {
		if (custID == 0)
			return null;

		CustomerDTO dto = getCustomerByID(custID);

		return getOrgNameByID(dto.getOrgID());
	}

	/**
	 * 软件产品列表
	 */
	public static Map getAllSProducts() {
		return getMapBySQL(
				"select productid, productname from t_product where status='N' and productclass='S' order by productid",
				false);
	}

	/**
	 * 产品列表
	 */
	public static Map getAllProducts() {
		return getMapBySQL(
				"select productid, productname from t_product where status='N'  order by productid",
				true);
	}

	/**
	 * 时间段列表
	 */

	public static Map getAllPreferedTime(String time) {
		return getMapBySQL(
				"select key,value from t_commonsettingdata where  status <>'I' and Name ='"
						+ time + "' order by key asc", true, true);
	}

	/**
	 * 资源名称类型
	 */
	public static Map getResourceNames() {
		return getMapBySQL(
				"Select ResourceName,ResourceName From T_ServiceResource Where status='V'",
				true);
	}

	/**
	 * 套餐产品包
	 */
	public static Map getBandleCampaign() {
		return getMapBySQL(
				"select campaignid, campaignname from t_campaign where status='V' and campaigntype='B' order by campaignid",
				true);
	}

	public static Map getAllService() {
		return getMapBySQL("select serviceid, servicename from t_service ");
	}

	public static Map getAllServiceByStatus(String status) {
		return getMapBySQL("select serviceid, servicename from t_service where status= '"
				+ status + "' ");
	}

	public static Map getAllAcctItemType() {
		return getMapBySQL("select acctitemtypeid,	acctitemtypename from T_AcctItemType ");
	}

	public static Map getAcctItemTypeForConfig() {
		return getMapBySQL(" SELECT ACCTITEMTYPEID,ACCTITEMTYPENAME FROM T_ACCTITEMTYPE WHERE SUMMARYAIFLAG='Y' AND STATUS='V' ORDER BY FEETYPE, ACCTITEMTYPEID");
	}

	public static Map getAcctItemTypeWithAdjust() {
		return getMapBySQL(" SELECT ACCTITEMTYPEID,ACCTITEMTYPENAME FROM T_ACCTITEMTYPE WHERE SUMMARYAIFLAG='Y' AND STATUS='V' and feeType<>'P' ORDER BY FEETYPE, ACCTITEMTYPEID");
	}

	public static String getOrgNameByOrgID(int orgId) {
		return getStringBySQL(" SELECT orgname FROM t_organization WHERE orgid="
				+ orgId);
	}

	/**
	 * 查找能够用于升降级的产品
	 * 
	 * @param prodcutID
	 *            int
	 * @param type
	 *            String
	 * @return Map
	 */
	public static Map getDescendAndAscendProductMap(int prodcutID, String type) {
		Map map = new HashMap();
		if (prodcutID == 0)
			return map;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String queryDependProduct = "select referproductidlist "
				+ " from t_productdependency where PRODUCTID=" + prodcutID;
		if (type != null && type.trim().length() > 0)
			queryDependProduct = queryDependProduct + " and TYPE='" + type
					+ "'";

		try {
			System.out.println("queryDependProduct=========="
					+ queryDependProduct);
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(queryDependProduct);
			String referenceID = null;
			String productName = null;
			while (rs.next()) {
				String referProductIDStr = rs.getString("referproductidlist");
				String[] referProductIDList = referProductIDStr.split(",");
				for (int i = 0; i < referProductIDList.length; i++) {
					referenceID = referProductIDList[i];
					if (referenceID != null && !referenceID.equals("0")) {
						if (!map.containsKey(referenceID)) {
							productName = getProductNameByID(Integer.valueOf(
									referenceID).intValue());
							map.put(referenceID, productName);
						}
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("getDescendAndAscendProductMap exception:"
					+ ex.getMessage());
			map.clear();
			ex.printStackTrace();
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
		return map;
	}

	/**
	 * chen jiang
	 * 
	 * @param campaignID
	 * @return
	 */
	public static String getMarketSegmentIDByCampaignID(int campaignID) {
		String marketStr = "";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select a.* "
				+ " From t_marketsegment a  where a.id in ( select t.MarketSegmentID from T_CampaignToMarketSegment t"
				+ " Where t.CampaignID = " + campaignID
				+ " ) And a.status ='V' ";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				MarketSegmentDTO dto = DtoFiller.fillMarketSegmentDTO(rs);
				marketStr = marketStr + dto.getId() + ",";

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getMarketSegmentIDByCampaignID", e);
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
		return marketStr;
	}

	/**
	 * chen jiang
	 * 
	 * @param campaignID
	 * @return
	 */
	public static String getMarketSegmentIDByPackageID(int packageId) {
		String marketStr = "";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select t.* from T_PackageToMarketSegment t Where t.packageid ="
				+ packageId;

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {

				marketStr = marketStr + rs.getInt("MarketSegmentID") + ",";

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getMarketSegmentIDByCampaignID", e);
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
		return marketStr;
	}

	/**
	 * chen jiang
	 * 
	 * @param campaignID
	 * @return
	 */
	public static String getPackageIDByCampaignID(int campaignID) {
		String packageIdStr = "";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select a.* " + " From T_Campaign_Agmt_Package a "
				+ " Where a.CampaignID = " + campaignID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				packageIdStr = packageIdStr + rs.getString("packageID") + ",";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getPackageIDByCampaignID", e);
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
		return packageIdStr;
	}

	/**
	 * chen jiang
	 * 
	 * @param contractNo
	 * @return
	 */
	public static String getPackageIDByContractNo(String contractNo) {
		String packageIdStr = "";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select a.*  From t_contracttopackage a  where a.contractno = '"
				+ contractNo + "'";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ContractToPackageDTO dto = DtoFiller
						.fillContractToPackageDTO(rs);
				packageIdStr = packageIdStr + dto.getProductPackageID() + ";";

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getPackageIDByContractNo", e);
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
		return packageIdStr;
	}

	/**
	 * chen jiang
	 * 
	 * @param campaignID
	 * @return
	 */
	public static String getProductIDByCampaignID(int campaignID) {
		String productIdStr = "";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select a.* " + " From  T_Campaign_Agmt_Product a "
				+ " Where a.CampaignID = " + campaignID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				productIdStr = productIdStr + rs.getString("productid") + ",";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductIDByCampaignID", e);
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
		return productIdStr;
	}

	/**
	 * chen jiang
	 * 
	 * @param detailno
	 * @return
	 */
	public static ArrayList getMarketSegmentDTO() {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select a.* From t_marketsegment a  where  a.status ='V' ";

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				MarketSegmentDTO dto = DtoFiller.fillMarketSegmentDTO(rs);
				list.add(dto);

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getMarketSegmentDTO",
					e);
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
	 * chen jiang
	 * 
	 * @param detailno
	 * @return
	 */
	public static ArrayList getProductPackageDTO(String packageClass,
			int operatorLevel) {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String compareTime = TimestampUtility.getCurrentDateWithoutTime()
				.toString();

		String subSql = "";
		String prioritySql = "";
		if (packageClass != null && !packageClass.trim().equals("")) {
			subSql = " a.packageclass in (" + packageClass + ") and";
		}

		if (operatorLevel != -100) {
			prioritySql = "  and a.grade <=" + operatorLevel;
		}

		String sql = " Select a.* From T_ProductPackage a where a.status ='N' and "
				+ subSql
				+ "  a.datefrom <=to_timestamp('"
				+ compareTime
				+ "','YYYY-MM-DD-HH24:MI:SSxff')"
				+ "  and a.dateto>=to_timestamp('"
				+ compareTime
				+ "','YYYY-MM-DD-HH24:MI:SSxff') "
				+ prioritySql
				+ "  order by a.packagepriority ";
		System.out.println("getProductPackageDTO.sql========" + sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {

				ProductPackageDTO dto = DtoFiller.fillProductPackageDTO(rs);
				list.add(dto);

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductPackageDTO", e);
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

	public static ProductPackageDTO getProductPackageByPackageID(int packageID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " Select a.* From T_ProductPackage a where a.packageid ="
				+ packageID;
		ProductPackageDTO productPackageDto = new ProductPackageDTO();
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				productPackageDto = DtoFiller.fillProductPackageDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductPackageByPackageID", e);
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
		return productPackageDto;
	}

	public static ArrayList getProductDTO() {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long compareTime = TimestampUtility.getCurrentDateWithoutTime()
				.getTime();
		String sql = " Select a.*  From t_product a  where a.status ='N' ";

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductDTO dto = DtoFiller.fillProductDTO(rs);
				if (dto.getDateFrom().getTime() < compareTime
						&& compareTime < dto.getDateTo().getTime())
					list.add(dto);

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getProductDTO", e);
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
	 * chen jiang
	 * 
	 * @param detailno
	 * @return
	 */
	public static ArrayList getCampaignDTO(String campaignType) {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long compareTime = TimestampUtility.getCurrentDateWithoutTime()
				.getTime();
		String sql = " Select a.* from t_campaign a  where a.CampaignType in ("
				+ campaignType
				+ ") and a.status ='V' and a.groupbargainflag ='Y' order by a.campaignid desc";
		System.out.println("sql====" + sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CampaignDTO dto = DtoFiller.fillCampaignDTO(rs);
				if (dto.getDateFrom().getTime() < compareTime
						&& compareTime < dto.getDateTo().getTime())
					list.add(dto);

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getCampaignDTO", e);
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
	 * 得到curOrgID下所有的代理商 add by jason.zhou
	 */
	public static Map getAllAgentBeLongToCurr(int curOrgID) {
		Map map = new LinkedHashMap();

		String sql = "select orgid,orgname,parentorgid from t_organization where  STATUS='V' and orgsubtype='S' "
				+ "connect by prior orgid=parentorgid start with orgid="
				+ curOrgID;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getAllAgentBeLongToCurr", sql);
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map.put(String.valueOf(rs.getInt("orgid")), rs
						.getString("orgname"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllAgentBeLongToCurr", e);
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return map;
	}

	public static ProductDTO getProductDTOByProductID(int productID) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductDTO dto = null;

		String sql = " Select * from  T_Product where ProductID=" + productID;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next())
				dto = DtoFiller.fillProductDTO(rs);
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
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

	public static BossConfigurationDTO getBossConfigDto() {
		return bossConfiguration;
	}

	/**
	 * 根据产品ID取产品属性
	 * 
	 * @param productID
	 * @return
	 */
	public static ArrayList getProductPropertyListByProductID(int productID) {
		ArrayList res = new ArrayList();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductPropertyDTO dto = null;

		String sql = "select * from t_productproperty where productID=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, productID);
			rs = stmt.executeQuery();
			while (rs.next()) {

				dto = DtoFiller.fillProductPropertyDTO(rs);
				res.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductPropertyListByProductID", e);
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

	public static Map getProductPropertyValueByProductID(String prex,
			String psID) {

		Map res = new LinkedHashMap();
		String sql = "select propertycode,propertyvalue from t_cpconfigedproperty where status='V' and psid="
				+ psID;
		Map tmpMap = getMapBySQL(sql);
		Iterator it = tmpMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) tmpMap.get(key);

			key = prex + key;
			res.put(key, value);
		}
		return res;
	}

	public static Map getPropertyValueSetting(String propertyCode) {
		String sql = "select propertyvalue,propertyvalue from t_propertyvaluesetting where propertycode='"
				+ propertyCode + "'";
		return getMapBySQL(sql);
	}

	public static String getPropertyValueByPSIDAndPropertyCode(int psid,
			String propertyCode) {
		return getStringBySQL("select propertyvalue from t_cpconfigedproperty where psid="
				+ psid + " and propertycode='" + propertyCode + "'");
	}

	/**
	 * 根据产品包ID得到产品列表
	 * 
	 * @param packageID
	 * @return
	 */
	public static ArrayList getProductListByProductPackageID(int packageID) {
		ArrayList res = new ArrayList();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductDTO dto = null;

		String sql = "select pr.* from t_productpackage pa,t_packageline l,t_product pr where l.packageid=pa.packageid and l.productid=pr.productid and pa.packageid=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, packageID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillProductDTO(rs);
				res.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductListByProductPackageID", e);
		} finally {
			if (rs !=null){
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
		return res;
	}

	/**
	 * add by Stone Liang ,date :2006-06-01 通过传入的产品ID的字符串，得到这些产品属于的业务的ID的数组
	 * 
	 * @param productList
	 * @return String
	 */

	public static String getServiceIDByProductIDs(String productList) {

		String serviceIDs = "";
		if (productList == null || "".equals(productList)) {
			serviceIDs = "0";
			return serviceIDs;
		}

		String productIDs[] = productList.split(";");
		String strWhere = "";
		for (int i = 0; i < productIDs.length; i++) {
			if (i != 0)
				strWhere = strWhere + " or ";
			strWhere = strWhere + " prod.productid=" + productIDs[i];
		}

		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		String sql = "SELECT serv.serviceid FROM t_service serv, t_product prod,t_producttoservice ps "
				+ "WHERE serv.serviceid=ps.serviceid  and "
				+ "prod.productid=ps.productid and  prod.newsaflag='Y'"
				+ " and (" + strWhere + ")";

		try {
			conn = DBUtil.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);

			int intServiceNum = 0;
			while (rs.next()) {
				if (intServiceNum > 0)
					serviceIDs = serviceIDs + ";";
				serviceIDs = serviceIDs + rs.getInt("serviceid");
				intServiceNum = intServiceNum + 1;
			}
		} catch (SQLException e) {
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return serviceIDs;
	}

	/**
	 * add by Stone Liang ,date :2006-06-12 通过传入的产品包ID的字符串，得到产品所属于的业务的ID的数组
	 * 
	 * @param productPackageList
	 * @return String
	 */
	public static String getServiceIDByProductPackageIDs(
			String productPackageList) {

		String serviceIDs = "";
		String productList = "";
		if (productPackageList == null || "".equals(productPackageList)) {
			serviceIDs = "0";
			return serviceIDs;
		}
		String productPackageIDs[] = productPackageList.split(";");
		// String strWhere = "";
		for (int i = 0; i < productPackageIDs.length; i++) {
			productList = productList
					+ getCheckedProductIDList(Integer
							.parseInt(productPackageIDs[i]));
		}
		serviceIDs = getServiceIDByProductIDs(productList);

		return serviceIDs;
	}

	/**
	 * Chen jiang 根据操作员组id找出对应的权限
	 * 
	 * @param opGroupId
	 * @return
	 */
	public static List getSystemPrivilegeDTOById(int opGroupId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List sysPriDtoList = new ArrayList();
		String sql = "select t.*  from t_systemprivilege t where t.privid  in ( select t1.privid from t_opgrouptoprivilege t1 where t1.opgroupid="
				+ opGroupId + ") order by t.privid asc";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				SystemPrivilegeDTO dto = DtoFiller.fillSystemPrivilegeDTO(rs);
				sysPriDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getSystemPrivilegeDTOById", e);

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

		if (sysPriDtoList.size() <= 0)
			sysPriDtoList = null;
		return sysPriDtoList;

	}

	public static List getNotSystemPrivilegeDTOById(int opGroupId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List sysPriDtoList = new ArrayList();
		String sql = "select t.*  from t_systemprivilege t where  t.privid not in ( select t1.privid from t_opgrouptoprivilege t1 where t1.opgroupid="
				+ opGroupId + ") order by t.privid asc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				SystemPrivilegeDTO dto = DtoFiller.fillSystemPrivilegeDTO(rs);
				sysPriDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNotSystemPrivilegeDTOById", e);

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

		if (sysPriDtoList.size() <= 0)
			sysPriDtoList = null;
		return sysPriDtoList;

	}

	public static List getCurrentHaveOperator(int opGroupId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List operatorDtoList = new ArrayList();
		String sql = "select t.*  from t_operator t where t.operatorid  in ( select t1.operatorid from t_optogroup t1 where t1.opgroupid="
				+ opGroupId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				OperatorDTO dto = DtoFiller.fillOperatorDTO(rs);
				operatorDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getSystemPrivilegeDTOById", e);

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

		if (operatorDtoList.size() <= 0)
			operatorDtoList = null;
		return operatorDtoList;

	}

	public static List getNotCurrentHaveOperator(int opGroupId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List operatorDtoList = new ArrayList();
		String sql = "select t.*  from t_operator t where t.operatorid  not in ( select t1.operatorid from t_optogroup t1 where t1.opgroupid="
				+ opGroupId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				OperatorDTO dto = DtoFiller.fillOperatorDTO(rs);
				operatorDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getSystemPrivilegeDTOById", e);

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

		if (operatorDtoList.size() <= 0)
			operatorDtoList = null;
		return operatorDtoList;

	}

	public static List getCurrentHaveProduct(int packageID) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List productDtoList = new ArrayList();
		String sql = "select t.*  from t_product t where t.status='N' and t.productid  in ( select t1.productid from t_packageline t1 where t1.packageid="
				+ packageID + ") order by t.productid asc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				ProductDTO dto = DtoFiller.fillProductDTO(rs);
				productDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCurrentHaveProduct", e);

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

		if (productDtoList.size() <= 0)
			productDtoList = null;
		return productDtoList;

	}

	/**
	 * Chen jiang 找到产品包中包含的产品
	 * 
	 * @param packageID
	 * @return
	 */
	public static List getCurrentNotHaveProduct(int packageID) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List productDtoList = new ArrayList();
		String sql = "select t.*  from t_product t where t.status='N' and t.productid  not in ( select t1.productid from t_packageline t1 where t1.packageid="
				+ packageID + ") order by t.productid asc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				ProductDTO dto = DtoFiller.fillProductDTO(rs);
				productDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCurrentHaveProduct", e);

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

		if (productDtoList.size() <= 0)
			productDtoList = null;
		return productDtoList;

	}

	/**
	 * Chen jiang 找到当前组织拥有区域
	 * 
	 * @param orgId
	 * @return
	 */
	public static List getCurrentHaveDistrict(int orgId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List operatorDtoList = new ArrayList();
		String sql = "select t.*  from t_districtsetting t where t.id  in ( select t1.districtid from t_orggoverneddistrict t1 where t1.orgid="
				+ orgId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				DistrictSettingDTO dto = DtoFiller.fillDistrictSettingDTO(rs);
				operatorDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCurrentHaveDistrict", e);

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

		if (operatorDtoList.size() <= 0)
			operatorDtoList = null;
		return operatorDtoList;

	}

	public static List getNotCurrentHaveDistrict(int orgId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List operatorDtoList = new ArrayList();
		String sql = "select t.*  from t_districtsetting t where t.id  not in ( select t1.districtid from t_orggoverneddistrict t1 where t1.orgid="
				+ orgId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				DistrictSettingDTO dto = DtoFiller.fillDistrictSettingDTO(rs);
				operatorDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNotCurrentHaveDistrict", e);

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

		if (operatorDtoList.size() <= 0)
			operatorDtoList = null;
		return operatorDtoList;

	}

	/**
	 * Chen jiang 找到当前组织拥有区域
	 * 
	 * @param orgId
	 * @return
	 */
	public static List getCurrentHaveOpGroup(int operatorId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List operatorDtoList = new ArrayList();
		String sql = "select t.*  from t_opgroup t where t.opgroupid  in ( select t1.opgroupid from t_optogroup t1 where t1.operatorid="
				+ operatorId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				OpGroupDTO dto = DtoFiller.fillOpGroupDTO(rs);
				operatorDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCurrentHaveOpGroup", e);

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

		if (operatorDtoList.size() <= 0)
			operatorDtoList = null;
		return operatorDtoList;

	}


	public static List getNotCurrentHaveOpGroup(int operatorId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List operatorDtoList = new ArrayList();
		String sql = "select t.*  from t_opgroup t where t.opgroupid  not in ( select t1.opgroupid from t_optogroup t1 where t1.operatorid="
				+ operatorId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				OpGroupDTO dto = DtoFiller.fillOpGroupDTO(rs);
				operatorDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNotCurrentHaveOpGroup", e);

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

		if (operatorDtoList.size() <= 0)
			operatorDtoList = null;
		return operatorDtoList;

	}

	/**
	 * add by Stone Liang ,date :2006-06-06 通过产品ID和属性Code，得到产品属性DTO
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
			LogUtility.log(Postern.class, LogLevel.WARN,
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

	/**
	 * 通过执行sql语句得到第一个记录的属性值的返回
	 * 
	 * @param sql
	 * @return
	 */
	public static Object excuteQueryBySQL(String sql) {
		Object result = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				result = rs.getObject(1);
		} catch (Exception e) {
			result = null;
			if (Constant.DEBUGMODE)
				System.out
						.println("getStringBySQL exception:" + e.getMessage());
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

	public static int getCustIDByEventID(int eventID) {
		return getIntBySQL("select t.customerid from t_systemevent t where sequenceno="
				+ eventID);
	}

	public static Map getAllVODHostNameID() {
		return getMapBySQL("select hostid,	hostname from T_VODHOST ");
	}

	public static Map getAllLdapHostNameID() {
		return getMapBySQL("select hostid,	hostname from T_LDAPHOST ");
	}

	public static Map getAllCaConditions() {
		return getMapBySQL("select condid a ,condid b from t_cacondition order by condid asc");
	}

	public static Map getAllCaCommands() {
		return getMapBySQL("select commandid a ,commandid b from t_cacommand order by commandid asc");
	}

	public static String getCommandNameByID(int id) {
		return getStringBySQL("select commandname from t_cacommand  where commandid="
				+ id);
	}

	public static String getCatvIdSelectFlag() {
		return getStringBySQL("select t.Key from t_commonsettingdata t where name='SET_S_CATVIDSELECTFLAG'");
	}

	public static String getCycleName(int typeId) {
		return getStringBySQL("select t.name from t_billingcycle t where t.id="
				+ typeId);
	}

	public static String getCurrentUserCount() {
		return getStringBySQL("select count(*) from t_serviceaccount where nvl(status, 'N') <> 'C'");
	}

	public static Map getAllCAHost() {
		return getMapBySQL("select hostid,hostname from t_cahost ");
	}

	public static String getHostNameById(int id) {
		return getStringBySQL("select hostname from t_cahost where hostid="
				+ id);
	}

	public static String getCaProductNameByCaId(String id) {
		return getStringBySQL("select caproductname from t_caproductdef where caproductid='"
				+ id + "'");
	}

	public static String getBatchNoByBatchId(int id) {
		return getStringBySQL("select batchno from t_devicetransition where batchid="
				+ id);
	}

	public static Map getAllProduct() {
		return getMapBySQL("select productid,productname from t_product ");
	}

	public static Map getServiceAccountIDMapByCustID(int custID) {
		return getMapBySQL(
				"select serviceaccountid, serviceaccountid from t_serviceaccount where customerid="
						+ custID, true);
	}

	public static Map getNoCancelServiceAccountIDMapByCustID(int custID) {
		return getMapBySQL(
				"select serviceaccountid, serviceaccountid from t_serviceaccount where status <>'C' and customerid="
						+ custID, true);
	}

	/**
	 * @param customerid
	 * @return 根据客户id，取得所有状态正常的业务账户信息
	 */
	public static Map getNormalServiceAccountMapByCustID(int customerid) {
		Map serviceAccountDataCache = new LinkedHashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = "select * from t_serviceaccount where CUSTOMERID="
					+ customerid + " and status='N'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String serviceAccountName = rs.getString("SERVICEACCOUNTNAME");
				String serviceAccountID = String.valueOf(rs
						.getInt("SERVICEACCOUNTID"));
				if (serviceAccountName != null
						&& !"".equals(serviceAccountName)) {
					serviceAccountDataCache.put(serviceAccountID,
							serviceAccountID + ":" + serviceAccountName);
				} else {
					serviceAccountDataCache.put(serviceAccountID,
							serviceAccountID + ":" + serviceAccountID);
				}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNormalServiceAccountMapByCustID", e);
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
		return serviceAccountDataCache;
	}

	public static CAQueueDTO getCAQueueByID(int queueID) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CAQueueDTO dto = null;

		String sql = "select * from t_caqueue where queueid=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, queueID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillCAQueueDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getCAQueueByID", e);
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

	public static CsiActionReasonDetailDTO getCsiActionReasonDetailDTO(int seqno) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CsiActionReasonDetailDTO dto = null;

		String sql = "select * from t_csiactionreasondetail where seqno=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, seqno);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillCsiActionReasonDetailDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCsiActionReasonDetailDTO", e);
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

	public static String getCustCampaignOrGroupBarginNameByCCID(int ccID) {
		if (ccID == 0)
			return "";

		String ccName = "";

		// //查找优惠
		String sqlFPayment = "select c.CAMPAIGNNAME from t_campaign c,t_customercampaign cc where cc.CAMPAIGNID=c.CAMPAIGNID and cc.CCID="
				+ ccID;
		ccName = getStringBySQL(sqlFPayment);

		// 查找团购
		if (ccName == null || "".equals(ccName)) {
			sqlFPayment = "select g.NAME from T_GROUPBARGAIN g,t_customercampaign cc where cc.GROUPBARGAINID=g.ID and cc.CCID="
					+ ccID;
			ccName = getStringBySQL(sqlFPayment);
		}

		// 查找合同号
		if (ccName == null || "".equals(ccName)) {
			sqlFPayment = "select c.NAME from T_CONTRACT c,t_customercampaign cc where cc.CONTRACTNO=c.CONTRACTNO and cc.CCID="
					+ ccID;
			ccName = getStringBySQL(sqlFPayment);
		}
		return ccName;

	}

	/**
	 * 通过ccid查询客户促销信息
	 * 
	 * @param ccid
	 * @return
	 */
	public static CustomerCampaignDTO getCustomerCampaignByccID(int ccid) {
		CustomerCampaignDTO dto = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("Select * From t_customercampaign where ccid="
							+ ccid);
			if (rs.next()) {
				dto = DtoFiller.fillCustomerCampaignDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerCampaignByccID", e);
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

	public static CampaignDTO getCampaignDTOByCampaignID(int campaiginID) {
		if (campaiginID == 0)
			return null;

		CampaignDTO dto = new CampaignDTO();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from T_Campaign where CampaignID="
					+ campaiginID);
			if (rs.next()) {
				dto = DtoFiller.fillCampaignDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignDTOByCampaignID", e);
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

	public static String getpackagecondByCampaignID(int campaignID) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String strsql = "select * from t_campaign_cond_package t where t.campaignid ="
				+ campaignID;
		String strnewpackageIds = "";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(strsql);
			if (rs.next()) {
				strnewpackageIds = rs.getString("packageidlist");
			}
		} catch (Exception e) {
			System.out.println("getCampaignNewpackagecondByCampaignID()：" + e);
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
		return strnewpackageIds;
	}

	public static Map getAllDistrictSettingCache() {
		return districtSettingCache;
	}

	public static String getSystemSymbolName() {
		// String res=getStringBySQL("select systemsymbolname from
		// t_bossconfiguration ");
		String res = bossConfiguration.getSystemSymbolName();
		return res == null ? "" : res;
	}
	
	/**
	 * 得到组织管理区域
	 * @param orgid
	 * @return
	 */
	public static List getOrgGovernedDistrictByOrgID(int orgid) {
		List distList =(ArrayList)orgGovernedDistrictMap.get(new Integer(orgid));
		return distList;
	}
	
	public static List getDistrictTreePathListWithOrgID(int orgid) {
		List distList =(ArrayList)orgGovernedDistrictMap_1.get(new Integer(orgid));
		return distList;
	}

    public static String getDistrictDesc(int distid) {
		Map ca = getAllDistrictSettingCache();
		String strid = String.valueOf(distid);
		DistrictSetting ds = (DistrictSetting) ca.get(strid);
		if (ds == null)
			return "";
		String desc = ds.getName();
		int belongId = ds.getBelongTo();
		while (belongId > 0) {
			DistrictSetting belongDs = (DistrictSetting) ca.get(belongId + "");
			belongId = belongDs.getBelongTo();
			desc = belongDs.getName() + "◇" + desc;
		}
		return desc;
	}

	/**
	 * 取得组织描述字串,
	 * 
	 * @param districtId
	 * @return
	 */
	public static String getOrganizationDesc(int orgID) {
		String parentDesc = "";
		Organization org = (Organization) organizationCache.get(orgID + "");
		if (org != null) {
			int parentOrgID = org.getParentOrgID();
			String currentDsc = org.getOrgName();

			if (parentOrgID != 0) {
				parentDesc = getOrganizationDesc(parentOrgID);
				parentDesc = parentDesc + "◇" + currentDsc;
			} else
				parentDesc = currentDsc;
		}
		return parentDesc;
	}

	public static String getProductNameByProductID(int productID) {
		return getStringBySQL("select productname from t_product where productid="+productID);	
	}

	public static String getEventNameByEventID(int eventid) throws SQLException {
		return getStringBySQL("select df.eventname from t_systemevent t,t_systemeventdef df "
                             +" where t.sequenceno="+eventid+" and t.eventclass =df.eventclass");
	}

	public static List getDeviceInfoByServiceAccountID(int serviceAccountID)
			throws SQLException {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.status,b.deviceid,b.deviceclass,b.devicemodel,b.serialno,b.macaddress,b.intermacaddress from t_customerproduct a,t_terminaldevice b where a.serviceaccountid=? and a.deviceid=b.deviceid";
		System.out.println("&&&&&&&&&&&&&&&&&&&" + sql);
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, serviceAccountID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TerminalDeviceDTO dto = new TerminalDeviceDTO();
				dto.setDeviceID(rs.getInt("deviceid"));
				dto.setDeviceClass(rs.getString("deviceclass"));
				dto.setSerialNo(rs.getString("serialno"));
				dto.setDeviceModel(rs.getString("devicemodel"));
				dto.setMACAddress(rs.getString("macaddress"));
				dto.setInterMACAddress(rs.getString("intermacaddress"));
				dto.setStatus(rs.getString("status"));
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDeviceInfoByServiceAccountID", e);
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
					rs = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
		}
		return list;
	}

	public static int getDistrictIDByCustomerID(String customerID) {
		return getIntBySQL("select addr.districtid from t_customer cust join t_address addr on cust.addressid=addr.addressid where cust.customerid="
				+ customerID);
	}

	public static java.util.Collection getDeviceIDByCSIID(int csiid) {
		Collection colDevices = new ArrayList();
		Connection con = DBUtil.getConnection();
		Statement stmt = null;
		ResultSet rs =null;
		try {
			String strSql = "select distinct REFERDEVICEID from t_csicustproductinfo a where a.csiid ="
					+ csiid;
			// System.out.println(strSql);
			stmt = con.createStatement();
			rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				if (rs.getInt("REFERDEVICEID") != 0)
					colDevices.add(new Integer(rs.getInt("REFERDEVICEID")));
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.WARN, e);
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
		return colDevices;
	}

	public static Map getCsicustProductinfoBycsiID(int csiId) {
		Map res = new LinkedHashMap();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from t_csicustproductinfo t where t.csiId="
				+ csiId + " ORDER BY referserviceaccountid ";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CsiCustProductInfoDTO dto = DtoFiller
						.fillCsiCustProductInfoDTO(rs);
				Integer said = new Integer(dto.getReferServiceAccountID());
				ArrayList psList = null;
				if (res.containsKey(said)) {
					psList = (ArrayList) res.get(said);
				} else {
					psList = new ArrayList();
					res.put(said, psList);
				}
				psList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCsicustProductinfoBycsiID", e);
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
					rs = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
		}
		return res;
	}

	public static Map getAllVOIPEvent() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from t_systemeventdef where eventclass in( select distinct(eventclass) from t_ssif_eventcmdmap)";
		Map mapVOIPEvent = new LinkedHashMap();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mapVOIPEvent.put("" + rs.getInt("eventclass"), rs
						.getString("eventname"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getAllVOIPEvent", e);
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
					rs = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
		}
		return mapVOIPEvent;
	}

	public static Map getAllVOIPProduct() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.productid,propertyname from t_product a,t_ssif_product b where a.productname=b.propertyname ";
		Map mapVOIPProduct = new LinkedHashMap();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mapVOIPProduct.put("" + rs.getInt("productid"), rs
						.getString("propertyname"));
			}
		} catch (Exception e) {
			LogUtility
					.log(Postern.class, LogLevel.WARN, "getAllVOIPProduct", e);
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
					rs = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
		}
		return mapVOIPProduct;
	}

	public static Collection getServiceAccountDTOByCustomerID(int customerID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList saList = new ArrayList();
		String sql = "select t.* from t_serviceaccount t where t.customerid ="
				+ customerID;
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ServiceAccountDTO dto = DtoFiller.fillServiceAccountDTO(rs);
				saList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getServiceAccountDTOByCustomerID", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					pstmt = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					conn = null;
				}
			}
		}
		return saList;
	}
	
	public static ServiceAccountDTO getServiceAccountDTOBySaID(String saID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ServiceAccountDTO dto =new ServiceAccountDTO();
		String sql = "select t.* from t_serviceaccount t where t.serviceaccountid ="
				+ saID;
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillServiceAccountDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getServiceAccountDTOBySaID", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					pstmt = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					conn = null;
				}
			}
		}
		return dto;
	}

	public static Collection getCustomerProductDTOByServiceAccountID(
			int serviceAccountID) {
		Collection cpList = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select t.* from t_customerproduct t where t.serviceAccountId="
				+ serviceAccountID + " order by t.PSID";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerProductDTO dto = DtoFiller.fillCustomerProductDTO(rs);
				cpList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerProductDTOByServiceAccountID", e);
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return cpList;
	}

	public static Map getProductPackageListWithContractNO(String contractNO) {
		return getMapBySQL("select pack.packageid,pack.packagename from t_productpackage pack, t_contracttopackage cont where pack.packageid = cont.productpackageid and cont.contractno='"
				+ contractNO + "'");
	}

	/**
	 * 通过合同号得到产品包列表
	 * 
	 * @fiona
	 * @param conID
	 * @return
	 */
	public static Collection getPackageIDByContractID(String conID) {
		Connection con = DBUtil.getConnection();
		Collection colPackage = new ArrayList();
		ResultSet rs = null;
		Statement sqlStrStmt = null;
		try {
			String strSql = "select * from t_contracttopackage  where contractno= '"
					+ conID + "'";
			sqlStrStmt = con.createStatement();
			rs = sqlStrStmt.executeQuery(strSql);
			while (rs.next()) {
				colPackage.add(new Integer(rs.getInt("PRODUCTPACKAGEID")));
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getPackageIDByContractID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
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
	 * 得到合同DTO
	 * 
	 * @param conID
	 * @return
	 */
	public static ContractDTO getContractDTOByContractNo(String conID) {
		ContractDTO dto = null;

		Statement sqlStrStmt = null;
		ResultSet rs = null;
		Connection con = DBUtil.getConnection();
		try {
			String strSql = "select * from t_contract  where contractno='"
					+ conID + "'";
			sqlStrStmt = con.createStatement();
			rs = sqlStrStmt.executeQuery(strSql);
			if (rs.next())
				dto = DtoFiller.fillContractDTO(rs, "");
		} catch (SQLException e) {
			dto = null;
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getContractDTOByConID", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
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
	 * 得到合同DTO
	 * 
	 * @param conID
	 * @return
	 */
	public static Map getContractNoByCustId(int custId) {

		return getMapBySQL("select contractno,name from t_contract where usedcustomerid="
				+ custId);

	}

	/**
	 * 得到合同DTO
	 * 
	 * @param conID
	 * @return
	 */
	public static String getContractNameByContractNo(String contractNo) {

		return getStringBySQL("select contractno,name from t_contract where contractno='"
				+ contractNo + "'");

	}

	/**
	 * 得到campaignType
	 * 
	 * @param conID
	 * @return
	 */
	public static String getCampaignByID(int id) {

		return getStringBySQL("select campaigntype from t_campaign where campaignid="
				+ id);

	}

	public static DeviceClassDTO getDeviceClassByProductID(int id) {
		DeviceClassDTO dto = new DeviceClassDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_deviceclass a,t_devicemodel b,t_devicematchtoproduct c where a.deviceclass=b.deviceclass and b.devicemodel=c.devicemodel and c.productid = ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto.setDeviceClass(rs.getString("DEVICECLASS"));
				dto.setDescription(rs.getString("DESCRIPTION"));
			}
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out.println("getDeviceClassByProductID exception:"
						+ e.getMessage());
				e.printStackTrace();
			}
			dto = null;
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

	public static ContractDTO getContractDTOByNo(String id) {
		ContractDTO dto = new ContractDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_contract where contractno = ? ");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto.setContractNo(rs.getString("contractno"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
			}
		} catch (Exception e) {
				System.out.println("getContractDTOByNo exception:"
						+ e.getMessage());
				e.printStackTrace();
			dto = null;
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

	public static Collection getProductPackageIDByContractNo(String id) {
		Collection ppID = new ArrayList();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_contracttopackage where contractno = ? ");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ppID.add(new Integer(rs.getInt("productpackageid")));
			}
		} catch (Exception e) {
			System.out.println("getProductPackageIDByContractNo exception:"
						+ e.getMessage());
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
		return ppID;
	}

	public static String getProductlistByConditionString(String conditionString) {
		conditionString = conditionString.substring(2);
		String[] productID = conditionString.split("`");
		String productlist = "";
		for (int i = 0; i < productID.length; i++) {
			productlist = productlist + " "
					+ getProductNameByProductID(Integer.parseInt(productID[i]));
		}
		return productlist;

	}

	public static String getContractNoByGroupCustID(String custID) {
		return getStringBySQL("select contractno from t_customercampaign where customerid ="+custID);
	}

	public static int getResourcePhoneNoItemIDWithPhoneNo(String phoneNo) {
		if (phoneNo == null || phoneNo.equals(""))
			return 0;
		return getIntBySQL("select itemid from t_resource_phoneno where phoneno='"
				+ phoneNo + "'");
	}

	public static String getSysEventTimeByEventID(int eventid) {
		if (eventid == 0)
			return "";
		String eventtime =getStringBySQL("Select to_char(max(DONETIME))  DONETIME From T_SsIf_Queue Where EVENTID ="+eventid);
		if (eventtime == null)
			eventtime = "";
		return eventtime;
	}

	/**
	 * 得到处理结果
	 * 
	 * @param jobCardID
	 * @return
	 */
	public static String getContactResultByJobCardID(int jobCardID) {
		String contactResult = "";
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select workresult from t_jobcardprocess where jcid=? and action in('RB','B') order by dt_create desc ");
			stmt.setInt(1, jobCardID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				contactResult = rs.getString("workresult");
			}
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out.println("getContactResultByJobCardID exception:"
						+ e.getMessage());
				e.printStackTrace();
			}
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
		return contactResult;
	}

	public static JobCardDTO getJobCardByID(int id) {
		JobCardDTO dto = new JobCardDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_jobcard where jobcardid= ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
		        dto =DtoFiller.fillJobCardDTO(rs);
			}
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out
						.println("getJobCardByID exception:" + e.getMessage());
				e.printStackTrace();
			}
			dto = null;
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
	 * Li yanchun 根据产品id找出设备dto
	 * 
	 * @param productId
	 * @return
	 */
	public static List getDeviceModelDTOByProductID(int productId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List deviceDtoList = new ArrayList();
		String sql = "select t.*  from t_devicemodel t where t.devicemodel  in ( select t1.devicemodel from t_devicematchtoproduct t1 where t1.productid="
				+ productId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				DeviceModelDTO dto = DtoFiller.fillDeviceModelDTO(rs);
				deviceDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDeviceModelDTOByProductID", e);

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

		if (deviceDtoList.size() <= 0)
			deviceDtoList = null;
		return deviceDtoList;
	}

	/**
	 * chenjiang
	 * 
	 * 
	 * @return feeSplitplandtolist
	 */
	public static List getFeeSplitDTOList() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List deviceDtoList = new ArrayList();
		String sql = "select t.*  from t_feesplitplan t order by feeSplitPlanID desc";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				FeeSplitPlanDTO dto = DtoFiller.fillFeeSplitPlanDTO(rs);
				deviceDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getFeeSplitDTOList",
					e);

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

		if (deviceDtoList.size() <= 0)
			deviceDtoList = null;
		return deviceDtoList;
	}

	/**
	 * chenjiang
	 * 
	 * 
	 * @return getFeeSplitItemDTOList
	 */
	public static List getFeeSplitItemDTOList(int id) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List deviceDtoList = new ArrayList();
		String sql = "select t.*  from t_feesplitplanitem t where t.feesplitplanid="
				+ id + " order by seqno desc";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				FeeSplitPlanItemDTO dto = DtoFiller.fillFeeSplitPlanItemDTO(rs);
				deviceDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getFeeSplitItemDTOList", e);

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

		if (deviceDtoList.size() <= 0)
			deviceDtoList = null;
		return deviceDtoList;
	}

	public static FeeSplitPlanItemDTO getFeeSplitPlanItemDTO(int id) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		FeeSplitPlanItemDTO dto = null;
		String sql = "select t.*  from t_feesplitplanitem t where seqno=" + id;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dto = DtoFiller.fillFeeSplitPlanItemDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getFeeSplitDTO", e);

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

	public static FeeSplitPlanDTO getFeeSplitDTO(int id) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		FeeSplitPlanDTO dto = null;
		String sql = "select t.*  from t_feesplitplan t where feeSplitPlanID="
				+ id;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dto = DtoFiller.fillFeeSplitPlanDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getFeeSplitDTO", e);
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
	 * Li yanchun 根据产品id找出可选设备dto
	 * 
	 * @param productId
	 * @return
	 */
	public static List getOptionalDeviceModelDTOByProductID(int productId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List deviceDtoList = new ArrayList();
		String sql = "select t.*  from t_devicemodel t where  t.devicemodel not in ( select t1.devicemodel from t_devicematchtoproduct t1 where t1.productid="
				+ productId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				DeviceModelDTO dto = DtoFiller.fillDeviceModelDTO(rs);
				deviceDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getOptionalDeviceModelDTOByProductID", e);

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
		if (deviceDtoList.size() <= 0)
			deviceDtoList = null;
		return deviceDtoList;
	}

	public static Map getOrgAndSubOrgConfig() {
		LinkedHashMap listConfig = new LinkedHashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select orgtype,suborgtype from T_OrgAndSubOrgConfig order by orgtype";

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			// initialize t_organization
			rs = stmt.executeQuery(sql);
			String oldOrgType = "";
			String oldSubOrgType = "";
			while (rs.next()) {
				String newOrgType = rs.getString("orgtype");
				String newSubOrgType = rs.getString("suborgtype");
				// 如果当前KEY和上轮KEY相等,则把VALUE拼接起来
				if (oldOrgType.equalsIgnoreCase(newOrgType)) {
					oldSubOrgType += newSubOrgType;
				} else {
					listConfig.put(oldOrgType, oldSubOrgType);
					oldOrgType = newOrgType;
					oldSubOrgType = newSubOrgType;
				}
			}
			listConfig.put(oldOrgType, oldSubOrgType);

			rs.close();

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
		LogUtility.log(Postern.class, LogLevel.DEBUG, "初始化组织关系" + listConfig);
		return listConfig;
	}

	private static CommonSettingValue fillCommonSettingDataDTO(ResultSet rs)
			throws SQLException {
		CommonSettingValue dto = new CommonSettingValue();
		dto.setKey(rs.getString("Key"));
		dto.setValue(rs.getString("VALUE"));
		dto.setDefaultOrNot(rs.getString("DEFAULTORNOT"));
		dto.setPriority(rs.getInt("PRIORITY"));
		dto.setGrade(rs.getInt("GRADE"));
		dto.setStatus(rs.getString("STATUS"));
		return dto;
	}

	/**
	 * 取得所有模块的映射的menu集合
	 * 
	 * @param contextparmeter
	 * @return
	 */
	public static Map getSystemConfigMenu(String contextparmeter)
			throws Exception {
		Map systemMenuMap = new LinkedHashMap();
		// reInit();
		Map cacheMap = (Map) commonSettingDataCache.get(contextparmeter);
		if (!cacheMap.isEmpty()) {
			Iterator keyIterator = cacheMap.keySet().iterator();
			while (keyIterator.hasNext()) {
				String key = (String) keyIterator.next();
				systemMenuMap.put(key, getMenuListbyModuleName(key));
			}
		}
		return systemMenuMap;
	}

	/**
	 * 取得模块下面关联的所有menu集合
	 * 
	 * @param modulName
	 * @return
	 */
	private static Collection getMenuListbyModuleName(String modulName)
			throws Exception {
		Collection menuList = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "Select * From T_MENUCONFIGURATION where status='N' Connect By Prior menukey=parentmenu Start With parentmenu ='"
				+ modulName + "' Order By PARENTMENU,PRIORITY";

		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			Map tempMenu = new LinkedHashMap();
			while (rs.next()) {
				MenuConfigurationDTO menuDto = DtoFiller
						.fillMenuConfigurationDTO(rs);
				if (modulName.equals(menuDto.getParentMenu()))
					tempMenu.put(menuDto.getMenuKey(), menuDto);
				else
					((MenuConfigurationDTO) tempMenu.get(menuDto
							.getParentMenu())).addChildMenu(menuDto);
			}
			rs.close();
			menuList.addAll(tempMenu.values());
			tempMenu.clear();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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
		return menuList;
	}

	public static ArrayList getDynamicFormSettingList(String formName) {
		if (formName == null || formName.equals(""))
			return null;
		ArrayList settingList = new ArrayList();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from T_DynamicFormSetting where formName=? and isVisible in ('y','Y','yes','YES') order by arrangeOrder";

		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, formName);
			rs = stmt.executeQuery();
			while (rs.next()) {
				DynamicFormSettingDTO formDto = DtoFiller
						.fillDynamicFormSettingDTO(rs);
				settingList.add(formDto);
			}
			rs.close();
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
		return settingList;
	}

	public static int getMopIDByCustID(int acctID) throws Exception {
		return getIntBySQL("select acct.mopid from t_account acct where acct.accountid="
				+ acctID);
	}

	public static InvoiceDTO getInvoiceDTOByInvoiceNo(int invoiceNo)
			throws Exception {
		InvoiceDTO iDTO = new InvoiceDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement("select * from t_invoice where invoiceno= ? ");
			stmt.setInt(1, invoiceNo);
			rs = stmt.executeQuery();
			if (rs.next()) {
				iDTO = DtoFiller.fillInvoiceDTO(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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
		return iDTO;

	}

	public static boolean getBillBoardCount() throws Exception {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		int count = getIntBySQL("select count(*) from t_billboard where status='V' and to_timestamp('"
				+ currentTime
				+ "','yyyy-MM-dd-HH24:MI:SSxff') between datefrom and dateto ");
		if (count > 0)
			return true;
		else
			return false;
	}

	public static StringBuffer getBankMopBuffer() {
		Map mapBankMop = getAllMethodOfPayments();
		Iterator itBank = mapBankMop.entrySet().iterator();
		String strItem = null;
		MethodOfPaymentDTO dtoMOP = null;
		StringBuffer lstBankMop = new StringBuffer();
		while (itBank.hasNext()) {
			Map.Entry BankItem = (Map.Entry) itBank.next();
			strItem = (String) BankItem.getKey();
			dtoMOP = (MethodOfPaymentDTO) BankItem.getValue();
			if ((dtoMOP.getPayType() != null)
					&& (dtoMOP.getPayType().equals("BP"))) {
				lstBankMop.append(",\"");
				lstBankMop.append(strItem);
				lstBankMop.append("\"");
			}
		}
		return lstBankMop;
	}

	/**
	 * 得到银行支付方式
	 * 
	 * @return
	 */
	public static Map getBankMop() {
		Map mapBankMop = getAllMethodOfPayments();
		Map retMap = new HashMap();
		if (mapBankMop.entrySet() != null) {
			Iterator itBank = mapBankMop.entrySet().iterator();
			MethodOfPaymentDTO dtoMOP = null;
			while (itBank.hasNext()) {
				Map.Entry BankItem = (Map.Entry) itBank.next();
				dtoMOP = (MethodOfPaymentDTO) BankItem.getValue();

				if ((dtoMOP.getPayType() != null)
						&& (dtoMOP.getPayType().equals("BP")))
					retMap
							.put(new Integer(dtoMOP.getMopID()), dtoMOP
									.getName());
			}
		}
		return retMap;
	}

	public static Collection getDynamicShowAttributesDto(String dtoName) {
		Collection showAttributesCols = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from T_DYNAMICSHOWATTRIBUTES t where t.DTONAME='"
				+ dtoName + "' order by t.LABELORDER";
		System.out.println("sql========" + sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				DynamicShowAttributesDTO dto = DtoFiller
						.fillDynamicShowAttributesDTO(rs);
				showAttributesCols.add(dto);
			}
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
		return showAttributesCols;
	}

	public static List getCsiReasonDetailDto(String referSeqNo) {
		List dtoCols = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_csiactionreasondetail t where t.referseqno='"
				+ referSeqNo + "' order by t.seqno desc";
		System.out.println("sql========" + sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CsiActionReasonDetailDTO dto = DtoFiller
						.fillCsiActionReasonDetailDTO(rs);
				dtoCols.add(dto);
			}
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
		return dtoCols;
	}

	public static Map getOpGroup() {
		return getMapBySQL("select OpGroupID,OpGroupName from T_OpGroup");
	}

	public static int getServiceAccountIDByCSIID(int csiid) throws SQLException {
		return getIntBySQL("select referserviceaccountid from t_csicustproductinfo where csiid="
				+ csiid);
	}

	public static String getDymamicShowAttribute(Object objValue,
			String valueTerm) {
		String attrValue = "";
		if (valueTerm.equalsIgnoreCase("operatorName")) {
			CsiProcessLogDTO csiProcessLogDto = getCsiProcessLogDTO(Integer
					.parseInt((String) objValue));
			attrValue = getOperatorNameByID(csiProcessLogDto.getOperatorID());
		} else if (valueTerm.equalsIgnoreCase("distinctName")) {
			AddressDTO addrDto = getAddressDtoByID(Integer
					.parseInt((String) objValue));
			attrValue = getDistrictDesc(addrDto.getDistrictID());
		} else if (valueTerm.equalsIgnoreCase("orgName")) {
			attrValue = getOrganizationDesc(Integer.parseInt((String) objValue));
		}
		return attrValue;
	}

	public static Map getAcctItemMapWithFeeType(String feeType) {
		return getMapBySQL(
				"select acctitemtypeid,acctitemtypename from t_acctitemtype where feetype ='"
						+ feeType + "'", true, true);
	}

	public static String getDistrictDescByCustomerID(int custID) {
		return getDistrictDesc(getDistrictIDByCustomerID(custID + ""));
	}

	/**
	 * Chen jiang 找到产品对应的业务
	 * 
	 * @param packageID
	 * @return
	 */
	public static List getServiceByProduct(int productID) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String compareTime = TimestampUtility.getCurrentDateWithoutTime()
				.toString();

		List serDtoList = new ArrayList();
		String sql = "select t.*  from t_service t where t.status='N' and t.serviceid in ( select t1.serviceid from t_producttoservice t1 where t1.productid="
				+ productID
				+ ") and "
				+ "  t.datefrom <=to_timestamp('"
				+ compareTime
				+ "','YYYY-MM-DD-HH24:MI:SSxff')"
				+ "  and t.dateto>=to_timestamp('"
				+ compareTime
				+ "','YYYY-MM-DD-HH24:MI:SSxff') "
				+ "order by t.serviceid asc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ServiceDTO dto = DtoFiller.fillServiceDTO(rs);
				serDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getServiceByProduct",
					e);

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

		if (serDtoList.size() <= 0)
			serDtoList = null;
		return serDtoList;

	}

	/**
	 * Chen jiang 找到产品对应的业务
	 * 
	 * @param packageID
	 * @return
	 */
	public static List getOptionServiceByProduct(int productID) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String compareTime = TimestampUtility.getCurrentDateWithoutTime()
				.toString();

		List serDtoList = new ArrayList();
		String sql = "select t.*  from t_service t where t.status='N' and t.serviceid not in ( select t1.serviceid from t_producttoservice t1 where t1.productid="
				+ productID
				+ ") and "
				+ "  t.datefrom <=to_timestamp('"
				+ compareTime
				+ "','YYYY-MM-DD-HH24:MI:SSxff')"
				+ "  and t.dateto>=to_timestamp('"
				+ compareTime
				+ "','YYYY-MM-DD-HH24:MI:SSxff') "
				+ "order by t.serviceid asc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ServiceDTO dto = DtoFiller.fillServiceDTO(rs);
				serDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getServiceByProduct",
					e);

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

		if (serDtoList.size() <= 0)
			serDtoList = null;
		return serDtoList;

	}

	/**
	 * 根据psID列表取得有效的terminalDeviceDTO列表.
	 * 
	 * @param psidList
	 *            psid列表,封装为"10,11,12"/"10;11;12"的形式,单个psid以逗号或者分号分隔
	 * @return
	 */
	public static ArrayList getTerminalDeviceListByPsid(String psidList) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList terList = new ArrayList();
		if (psidList == null || psidList.equals(""))
			return terList;
		psidList = psidList.replaceAll(";", ",");
		if (psidList.endsWith(",")) {
			psidList = psidList.substring(0, psidList.length() - 1);
		}
		String sql = "select * from t_terminaldevice " + "where deviceid in "
				+ "(select deviceid from t_customerproduct where psid in ("
				+ psidList + "))";
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getTerminalDeviceListByPsid", sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				TerminalDeviceDTO dto = DtoFiller.fillTerminalDeviceDTO(rs);
				terList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getTerminalDeviceListByPsid", e);

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
		return terList;
	}

	public static ArrayList getProductListByPsid(String psidList) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList terList = new ArrayList();
		if (psidList == null || psidList.equals(""))
			return terList;
		psidList = psidList.replaceAll(";", ",");
		if (psidList.endsWith(",")) {
			psidList = psidList.substring(0, psidList.length() - 1);
		}
		String sql = "select * from t_product " + "where productid in "
				+ "(select productid from t_Customerproduct where psid in ("
				+ psidList + "))";
		LogUtility.log(Postern.class, LogLevel.DEBUG, "getProductListByPsid",
				sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProductDTO dto = DtoFiller.fillProductDTO(rs);
				terList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductListByPsid", e);

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
		return terList;
	}

	/**
	 * 根据psID列表取得有效的客户产品套餐
	 * 
	 * @return
	 */
	public static CPCampaignMapDTO getValidCPCampaignMapByCustProductID(
			int custProductID) {
		CPCampaignMapDTO dto = new CPCampaignMapDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select ccmap.* from T_CPCAMPAIGNMAP ccmap,T_CustomerCampaign cc"
							+ " where ccmap.CCID=cc.CCID and cc.STATUS = 'V'"
							+ " and sysdate>=cc.DATEFROM and sysdate<cc.DATETO and ccmap.CUSTPRODUCTID = ? ");
			stmt.setInt(1, custProductID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillCPCampaignMapDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getValidCPCampaignMapByCustProductID", e);
			dto = null;
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
	 * 根据custProductID及范围取得的客户产品套餐 custProductID:客户产品ID statusStr:状态列表
	 * timeValid:时间是否在有效范围
	 * 
	 * @return
	 */
	public static List getCPCampaignMapListByCustProductID(int custProductID,
			String statusStr, boolean timeValid) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList terList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql
				.append("select ccmap.* from T_CPCAMPAIGNMAP ccmap,T_CustomerCampaign cc where ccmap.CCID=cc.CCID");
		if (custProductID != 0)
			sql.append(" and ccmap.CUSTPRODUCTID=" + custProductID);
		if (statusStr != null && !statusStr.trim().equals(""))
			sql.append(" and cc.STATUS in(" + statusStr + ")");
		if (timeValid)
			sql.append(" and sysdate>=cc.DATEFROM and sysdate<cc.DATETO");
		sql.append(" order by cc.STATUS desc");
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				CPCampaignMapDTO dto = DtoFiller.fillCPCampaignMapDTO(rs);
				terList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCPCampaignMapListByCustProductID", e);
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
		return terList;
	}

	public static ArrayList getCPCampaignListByCustCampaignID(int custCampaignID) {
		ArrayList result = new ArrayList();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_cpcampaignmap where ccid = ?");
			stmt.setInt(1, custCampaignID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CPCampaignMapDTO dto = new CPCampaignMapDTO();
				dto = DtoFiller.fillCPCampaignMapDTO(rs);
				result.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCPCampaignListByCustCampaignID", e);
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

	/**
	 * 用于生成套餐列表,可设置查询套餐还是促销,并可设置是哪个客户下的,
	 * 
	 * @param type
	 *            A,促销,B套餐 必填
	 * 
	 * @param custID
	 *            哪个客户下的,不设置则是系统中全部的
	 * @return
	 */
	public static Map getCampaignMapByTypeORCustID(String type, int custID) {
		StringBuffer sql = new StringBuffer();
		if ((type == null || type.equals("")) && custID == 0) {
			sql.append("select c.campaignid,c.campaignname  from t_campaign c");
		} else if ((type != null && !type.equals("")) && custID == 0) {
			sql.append("select c.campaignid,c.campaignname  from t_campaign c");
			sql.append(" where c.campaigntype = '");
			sql.append(type);
			sql.append("'");
		} else if ((type != null && !type.equals("")) && custID != 0) {
			sql.append("select distinct c.campaignid,c.campaignname");
			sql.append(" from t_campaign c , t_customercampaign cc");
			sql.append(" where c.campaignid = cc.campaignid");
			sql.append(" and c.campaigntype = '");
			sql.append(type);
			sql.append("'");
			sql.append(" and cc.customerid = ");
			sql.append(custID);
		} else if (custID != 0) {
			sql.append("select distinct c.campaignid,c.campaignname");
			sql.append(" from t_campaign c , t_customercampaign cc");
			sql.append(" where c.campaignid = cc.campaignid");
			sql.append(" and cc.customerid = ");
			sql.append(custID);
		}
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getCampaignMapByTypeORCustID:\n", sql.toString());
		return getMapBySQL(sql.toString());
	}

	/**
	 * 开户时根据条件取得要显示的开户套餐
	 * 
	 * @param
	 * @return
	 */
	public static ArrayList getCampaignDTOByCondition(String campaignType,
			String custType, String openSourceType, String csiType,
			String county) {

		StringBuffer sql = new StringBuffer();
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long compareTime = TimestampUtility.getCurrentDateWithoutTime()
				.getTime();
		sql
				.append("select * from t_campaign where Status ='V' and sysDate>=DateFrom and sysDate<DateTo");
		if (campaignType != null && !campaignType.trim().equals(""))
			sql.append(" and CampaignType in (" + campaignType + ")");
		if (custType != null && !custType.trim().equals(""))
			sql.append(" and CustTypeList like '%" + custType + "%'");
		if (openSourceType != null && !openSourceType.trim().equals(""))
			sql.append(" and OpenSourceTypeList like '%" + openSourceType
					+ "%'");
		if (csiType != null && !csiType.trim().equals(""))
			sql.append(" and CsiTypeList like '%" + csiType + "%'");
		if (county != null && !county.trim().equals("")) {
			sql
					.append(" and CampaignID in (select CampaignID from T_CampaignToMarketSegment where MarketSegmentID in"
							+ "(select MarketSegmentID from T_MarketSegmentToDistrict where DistrictID = "
							+ county + ") )");
		}
		sql.append(" order by campainpriority ");
		System.out.println("=====sql====" + sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				CampaignDTO dto = DtoFiller.fillCampaignDTO(rs);
				if (dto.getDateFrom().getTime() < compareTime
						&& compareTime < dto.getDateTo().getTime())
					list.add(dto);

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getCampaignDTO", e);
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
	 * 开户时根据条件取得要显示的产品包
	 * 
	 * @param
	 * @return
	 */
	public static ArrayList getProductPackageDTOByCondition(
			String packageClass, int operatorLevel, String csiType,
			String captureFlag,String custType) {
		ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from T_ProductPackage where status ='N' and sysDate>=DateFrom and sysDate<DateTo");

		if (packageClass != null && !packageClass.trim().equals(""))
			sql.append(" and packageclass in (" + packageClass + ")");
		if (operatorLevel != -100)
			sql.append(" and grade <=" + operatorLevel);
		if (csiType != null && !csiType.trim().equals(""))
			sql.append(" and CsiTypeList like '%" + csiType + "%'");
		if (captureFlag != null && !captureFlag.trim().equals(""))
			sql.append(" and CaptureFlag = '" + captureFlag + "'");
		if (custType != null && !custType.trim().equals(""))
			sql.append(" and CustTypeList like '%" + custType + "%'");

		// 按照包的优先级排序,优先级高的排在前面
		sql.append(" order by packagepriority");
		System.out
				.println("=getProductPackageDTOByCondition.sql========" + sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {

				ProductPackageDTO dto = DtoFiller.fillProductPackageDTO(rs);
				list.add(dto);

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductPackageDTO", e);
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

	public static Map getBundlePaymentMethodByCustomerCampaign(int ccid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select rfbillingcycleflag, bundlepaymentName");
		sql.append(" from t_bundlepaymentmethod");
		sql.append(" where bundleid in");
		sql.append(" (select campaignid from t_customercampaign where ccid = ");
		sql.append(ccid);
		sql.append(")");
		sql.append(" order by rfbillingcycleflag");
		return getMapBySQL(sql.toString(), true, true);
	}

	/**
	 * 根据条件取得诊断信息Map
	 * 
	 * @param customerProblemID:报修单ID,
	 *            referSourceType:诊断信息来源的单据的类型 ，见
	 *            SET_C_DIAGNOSISINFOREFERSOURCETYPE : 报修单/维修工单
	 * @return：map
	 */
	public static Map getDiagnosisInfoByCon(int customerProblemID,
			String referSourceType) {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_diagnosisinfo where 1=1");
		if (customerProblemID != 0)
			sql.append(" and ReferSourceID=" + customerProblemID);
		if (referSourceType != null && !"".equals(referSourceType))
			sql.append(" and ReferSourceType='" + referSourceType + "'");
		Map resultMap = new LinkedHashMap();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"=====getDiagnosisInfoByCon" + sql);

		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				// 如果MEMO为空，则再从INFOVALUEID中取值
				String res = rs.getString("MEMO");
				Integer settingID = new Integer(rs.getInt("INFOSETTINGID"));
				if (res == null || "".equals(res)) {
					res = String.valueOf(rs.getInt("INFOVALUEID"));
					if ("0".equals(res))
						res = "";
				}
				if (resultMap.containsKey(settingID)) {
					res = resultMap.get(settingID) + "-" + res;
				}

				resultMap.put(settingID, res);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDiagnosisInfoByCon", e);
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
		return resultMap;
	}

	/**
	 * 根据客户套餐,取得相关客户产品/业务帐户等一堆信息, 这个方法目前用于客户套餐取消页面,
	 * 
	 * @param customerCampaignID
	 * @return arrayList,存储的是Object[5]数组,cpList[0]=customerProductDto;cpList[1]=productDto;
	 *         cpList[2]=serviceAccountDto;cpList[3]=cpCampaignMapid;cpList[4]=terminaldeviceDTO(如果有的话,可能为空)
	 * 
	 */
	public static ArrayList getAllCustomerProductInfoWithCustomerCampaign(
			int customerCampaignID) {
		ArrayList result = new ArrayList();
		StringBuffer sql = new StringBuffer();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		sql.append("select cp.*,");
		sql
				.append(" p.productid,p.productname,p.productclass,p.description,p.datefrom,p.dateto ,p.newsaflag,");
		sql.append(" decode(cpm.id,null,0,cpm.id) cpmid,");
		sql
				.append(" sa.SERVICEACCOUNTID sa_SERVICEACCOUNTID ,sa.SERVICEID sa_SERVICEID ,");
		sql
				.append(" sa.CUSTOMERID sa_CUSTOMERID ,sa.USERID sa_USERID ,sa.SERVICECODE sa_SERVICECODE ,");
		sql
				.append(" sa.SUBSCRIBERID sa_SUBSCRIBERID ,sa.CREATETIME sa_CREATETIME ,sa.STATUS sa_STATUS ,");
		sql
				.append(" sa.DESCRIPTION sa_DESCRIPTION ,sa.USER_TYPE sa_USER_TYPE ,sa.DT_CREATE sa_DT_CREATE ,");
		sql
				.append(" sa.DT_LASTMOD sa_DT_LASTMOD ,sa.SERVICEACCOUNTNAME sa_SERVICEACCOUNTNAME ,");
		sql.append(" s.servicename,");
		sql.append(" t.DEVICEID ter_DEVICEID,t.DEVICECLASS ter_DEVICECLASS,");
		sql.append(" t.DEVICEMODEL ter_DEVICEMODEL,t.SERIALNO ter_SERIALNO,");
		sql
				.append(" t.MACADDRESS ter_MACADDRESS,t.INTERMACADDRESS ter_INTERMACADDRESS,");
		sql
				.append(" t.OKNUMBER ter_OKNUMBER,t.PREAUTHORIZATION ter_PREAUTHORIZATION,");
		sql.append(" t.STATUS ter_STATUS,t.ADDRESSTYPE ter_ADDRESSTYPE,");
		sql.append(" t.ADDRESSID ter_ADDRESSID,t.BATCHID ter_BATCHID,");
		sql
				.append(" t.LEASEBUY ter_LEASEBUY,t.GUARANTEELENGTH ter_GUARANTEELENGTH,");
		sql
				.append(" t.DATEFROM ter_DATEFROM,t.DATETO ter_DATETO,t.DEPOTID ter_DEPOTID,");
		sql.append(" t.PALLETID ter_PALLETID,t.USEFLAG ter_USEFLAG,");
		sql
				.append(" t.MATCHFLAG ter_MATCHFLAG,t.MATCHDEVICEID ter_MATCHDEVICEID,");
		sql.append(" t.CASETFLAG ter_CASETFLAG,t.CASETDATE ter_CASETDATE,");
		sql
				.append(" t.MSOFLAG ter_MSOFLAG,t.DT_CREATE ter_DT_CREATE,t.DT_LASTMOD ter_DT_LASTMOD,");
		sql
				.append(" t.OWNERTYPE ter_OWNERTYPE,t.OWNERID ter_OWNERID,t.PURPOSESTRLIST ter_PURPOSESTRLIST,t.COMMENTS ter_COMMENTS,");
		sql.append(" dc.description dcDesc,dm.description dmDesc");
		sql
				.append(" from t_customerproduct cp join t_product p on cp.productid = p.productid and cp.status<>'C'");
		sql
				.append(" left join t_cpcampaignmap cpm on cp.psid=cpm.custproductid and cpm.ccid=?");
		sql
				.append(" left join t_serviceaccount sa on cp.serviceaccountid = sa.serviceaccountid");
		sql.append(" left join t_service s on sa.serviceid = s.serviceid");
		sql.append(" left join t_terminaldevice t on cp.deviceid=t.deviceid");
		sql
				.append(" left join t_deviceclass dc on t.deviceclass=dc.deviceclass");
		sql
				.append(" left join t_devicemodel dm on t.devicemodel=dm.devicemodel");
		sql.append(" where cp.serviceaccountid in");
		sql.append(" (select distinct serviceaccountid from t_customerproduct");
		sql.append(" where ");
		sql.append(" psid in (");
		sql
				.append(" select custproductid from t_cpcampaignmap where ccid = ?))");
		sql.append(" order by cp.psid");
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getAllCustomerProductInfoWithCustomerCampaign:", sql);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, customerCampaignID);
			stmt.setInt(2, customerCampaignID);
			rs = stmt.executeQuery();
			HashMap saMap = new HashMap();
			while (rs.next()) {
				Object[] cpList = new Object[5];
				CustomerProductDTO cpDto = DtoFiller.fillCustomerProductDTO(rs);
				ProductDTO pDto = DtoFiller.fillProductDTO(rs);
				ServiceAccountDTO saDto = null;
				Integer said = new Integer(cpDto.getServiceAccountID());
				int deviceid = cpDto.getDeviceID();
				if (saMap.containsKey(said)) {
					saDto = (ServiceAccountDTO) saMap.get(said);
				} else {
					saDto = DtoFiller.fillServiceAccountDTO(rs, "sa_");
					saDto.setServiceAccountName(rs.getString("servicename"));
					saMap.put(said, saDto);
				}
				if (deviceid != 0) {
					TerminalDeviceDTO terDto = DtoFiller.fillTerminalDeviceDTO(
							rs, "ter_");
					terDto.setDeviceClass(rs.getString("dcDesc"));
					terDto.setDeviceModel(rs.getString("dmDesc"));
					cpList[4] = terDto;
				}
				Integer cpm = new Integer(rs.getInt("cpmid"));
				cpList[0] = cpDto;
				cpList[1] = pDto;
				cpList[2] = saDto;
				cpList[3] = cpm;
				result.add(cpList);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllCustomerProductInfoWithCustomerCampaign", e);
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

	/**
	 * 根据套餐或者促销的集合取得绑定到套餐或者促销的产品包id的集合
	 * 
	 * @param campaignArray
	 * @return
	 */
	public static String getBundle2CampaignPackageColStr(String campaignListStr) {
		String res = "";
		if (campaignListStr != null && !"".equals(campaignListStr)) {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String sql = "select PackageID from T_Bundle2Campaign where CampaignID in ("
					+ campaignListStr.replace(';', ',') + ")";

			try {
				con = DBUtil.getConnection();
				System.out.println("++++sql=" + sql);
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					res = res + rs.getInt("PackageID") + ";";
					// res.add(new Integer(rs.getInt("PackageID")));
				}
			} catch (Exception e) {
				LogUtility.log(Postern.class, LogLevel.WARN,
						"getBundle2CampaignPackageColStr", e);
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
	 * 根据旧的套餐ID,取得可转换的目标套餐DTO,和关系描述.
	 * 
	 * @param campaignid
	 * @return
	 */
	public static Map getCustomerBundleTransferTarget(int campaignid) {
		Map result = new LinkedHashMap();
		StringBuffer sql = new StringBuffer();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		sql.append("select cam1.*,pr.relationtype from t_campaign cam1");
		sql
				.append(" join t_bundle2campaign b2c1 on cam1.campaignid=b2c1.campaignid");
		sql
				.append(" join t_packagerelation pr on b2c1.packageid=pr.topackageid");
		sql
				.append(" join t_bundle2campaign b2c2 on pr.frompackageid=b2c2.packageid");
		sql.append(" join t_campaign cam2 on b2c2.campaignid=cam2.campaignid");
		sql.append(" where pr.relationtype='S' and cam2.campaignid=?");

		System.out.println("=====getCustomerBundleTransferTarget:====\n" + sql
				+ "--" + campaignid);
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, campaignid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String relationType = rs.getString("relationtype");
				CampaignDTO cDto = DtoFiller.fillCampaignDTO(rs);
				result.put(cDto, relationType);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerBundleTransferTarget", e);
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

	/**
	 * 取得套餐定义的产品集合,
	 * 
	 * @param campaignID
	 * @return
	 * @throws ServiceException
	 */
	public static Map getCampaignAgmtProductMap(int campaignID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map campaignAgmtProductMap = new LinkedHashMap();

		StringBuffer sql = new StringBuffer();
		sql.append("select p.* from t_product p join t_Campaign_Agmt_Product cap on p.productid=cap.productid")
		   .append(" where cap.campaignid = ? ")
		   .append(" order by p.productid");
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getCampaignAgmtProductMap.sql:", sql);
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, campaignID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO pDto = DtoFiller.fillProductDTO(rs);
				campaignAgmtProductMap.put(new Integer(pDto.getProductID()),
						pDto);
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignAgmtProductMap", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (pstmt !=null) {
				try{
					pstmt.close();
				}catch (SQLException e){
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getCampaignAgmtProductMap", e);
				}
			}
		}
		return campaignAgmtProductMap;
	}

	public static Map getCampaignAgmtPackageMap(int campaignID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map campaignAgmtPackageMap = new LinkedHashMap();

		StringBuffer sql = new StringBuffer();
		sql.append("select p.*,cap.packageid from t_product p join t_Packageline pl on p.productid=pl.productid")
		   .append(" join t_Campaign_Agmt_Package cap on pl.packageid=cap.packageid")
		   .append(" where cap.campaignid = ?")
		   .append(" order by cap.packageid,p.productid");
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getCampaignAgmtPackageMap.sql:", sql);

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, campaignID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Integer packageID = new Integer(rs.getInt("packageid"));
				ProductDTO pDto = DtoFiller.fillProductDTO(rs);
				LinkedHashMap productMap = null;
				if (campaignAgmtPackageMap.containsKey(packageID)) {
					productMap = (LinkedHashMap) campaignAgmtPackageMap
							.get(packageID);
				} else {
					productMap = new LinkedHashMap();
					campaignAgmtPackageMap.put(packageID, productMap);
				}
				productMap.put(new Integer(pDto.getProductID()), pDto);
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignAgmtProductMap", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (pstmt !=null) {
				try{
					pstmt.close();
				}catch (SQLException e){
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getCampaignAgmtProductMap", e);
				}
			}
		}
		return campaignAgmtPackageMap;
	}

	public static Map getDeviceClassMapByProductIDList(String productIDList) {
		productIDList = productIDList.replaceAll(";", ",");
		if (productIDList.startsWith(",")) {
			productIDList = productIDList.substring(1, productIDList.length());
		}
		if (productIDList.endsWith(",")) {
			productIDList = productIDList.substring(0,
					productIDList.length() - 1);
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LinkedHashMap deviceClassMap = new LinkedHashMap();

		StringBuffer sql = new StringBuffer();
		sql
				.append("select dc.*,dmp.productid from t_deviceclass dc")
				.append(
						" join t_devicemodel dm on dc.deviceclass = dm.deviceclass")
				.append(
						" join t_devicematchtoproduct dmp on dm.devicemodel = dmp.devicemodel")
				.append(" where dmp.productid in (").append(productIDList)
				.append(")");
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getDeviceClassMapByProductIDList.sql:>>>>", sql.toString());
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				deviceClassMap.put(new Integer(rs.getInt("productid")),
						DtoFiller.fillDeviceClassDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.ERROR,
					"getDeviceClassMapByProductIDList", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (pstmt !=null) {
				try{
					pstmt.close();
				}catch (SQLException e){
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDeviceClassListProductIDList", e);
				}
			}
		}
		return deviceClassMap;
	}

	/**
	 * 根据产品包列表,取设备列表,用于设备捕获页面.
	 * 
	 * @param packageIDList
	 *            ,产品包列表,以逗号分隔.
	 * @return
	 */
	public static Map getDeviceClassMapByPackageIDList(ArrayList packageIDList) {
		if (packageIDList == null || packageIDList.isEmpty()) {
			return null;
		}
		String strId = packageIDList.toString();
		strId = strId.substring(0, strId.length() - 1);
		strId = strId.substring(1, strId.length());
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LinkedHashMap deviceClassMap = new LinkedHashMap();

		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select DISTINCT dc.*,dmp.productid from t_deviceclass dc")
				.append(
						" join t_devicemodel dm on dc.deviceclass = dm.deviceclass")
				.append(
						" join t_devicematchtoproduct dmp on dm.devicemodel = dmp.devicemodel")
				.append(" JOIN T_PRODUCT P ON DMP.PRODUCTID=P.PRODUCTID")
				.append(" JOIN T_PACKAGELINE PL ON PL.PRODUCTID=P.PRODUCTID")
				.append(" where PL.PACKAGEID in (").append(strId).append(")")
				.append(" ORDER BY dc.deviceclass");
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getDeviceClassMapByPackageIDList.sql:>>>>", sql.toString());
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				deviceClassMap.put(new Integer(rs.getInt("productid")),
						DtoFiller.fillDeviceClassDTO(rs));
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.ERROR,
					"getDeviceClassMapByPackageIDList", e);
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (pstmt !=null) {
				try{
					pstmt.close();
				}catch (SQLException e){
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDeviceClassListProductIDList", e);
				}
			}
		}
		return deviceClassMap;
	}

	public static ArrayList getServiceIDListByPackageList(ArrayList packageList) {

		if (packageList == null || packageList.isEmpty()) {
			return null;
		}
		String strId = packageList.toString();
		strId = strId.substring(0, strId.length() - 1);
		strId = strId.substring(1, strId.length());

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT SERV.SERVICEID");
		sql
				.append(" FROM T_SERVICE SERV,T_PRODUCT PROD,T_PRODUCTTOSERVICE PS,T_PACKAGELINE PL");
		sql
				.append(" WHERE SERV.SERVICEID = PS.SERVICEID AND PROD.PRODUCTID = PS.PRODUCTID AND PL.PRODUCTID = PROD.PRODUCTID");
		sql.append(" AND PROD.NEWSAFLAG = 'Y'");
		sql.append(" AND PL.PACKAGEID IN (").append(strId).append(")");

		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;

		ArrayList res = new ArrayList();
		try {
			conn = DBUtil.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql.toString());

			while (rs.next()) {
				res.add(new Integer(rs.getInt("SERVICEID")));
			}
		} catch (SQLException e) {
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return res;
	}

	public static Map getProductPackageMapByPackageIDList(String ids) {
		if (ids.startsWith(","))
			ids = ids.substring(1, ids.length());
		if (ids.endsWith(","))
			ids = ids.substring(0, ids.length() - 1);
		Map packageMap = new HashMap();

		if (ids == null || ids.trim().equals(""))
			return packageMap;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_productpackage where packageid in ("
				+ ids + ")";
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getProductPackageMapByPackageIDList.sql:>>>>", sql.toString());
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductPackageDTO productPackageDto = DtoFiller
						.fillProductPackageDTO(rs);
				packageMap.put(new Integer(productPackageDto.getPackageID()),
						productPackageDto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductPackageMapByPackageIDList", e);
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
		return packageMap;
	}

	public static Map getCustomerProductMapByPsIDList(String psids) {
		psids = psids.replaceAll(";", ",");
		if (psids.startsWith(","))
			psids = psids.substring(1, psids.length());
		if (psids.endsWith(","))
			psids = psids.substring(0, psids.length() - 1);
		Map productMap = new LinkedHashMap();
		if (psids == null || psids.trim().equals(""))
			return productMap;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		sql.append("select cp.*,");
		sql
				.append(" p.productid,p.productname,p.productclass,p.description,p.datefrom,p.dateto ,p.newsaflag");
		sql
				.append(" from t_customerproduct cp join t_product p on cp.productid=p.productid where cp.psid in (");
		sql.append(psids);
		sql.append(")");
		sql.append(" order by cp.psid");
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getCustomerProductMapByPsIDList.sql:>>>>", sql.toString());
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				CustomerProductDTO cpDto = DtoFiller.fillCustomerProductDTO(rs);
				ProductDTO pDto = DtoFiller.fillProductDTO(rs);
				productMap.put(cpDto, pDto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerProductMapByPsIDList", e);
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
		return productMap;
	}

	/**
	 * 根据页面产品ID列表取ID和名称的列表.
	 * 
	 * @param ids
	 * @return
	 */
	public static Map getProductIDAndNameByIdList(String ids) {
		Map pMap = null;
		if (ids != null && !"".equals(ids)) {
			pMap = getProductMapByProductIDList(ids);
		}
		LinkedHashMap newMap = new LinkedHashMap();
		if (pMap != null && !pMap.isEmpty()) {
			for (Iterator it = pMap.keySet().iterator(); it.hasNext();) {
				Integer pid = (Integer) it.next();
				ProductDTO pDto = (ProductDTO) pMap.get(pid);
				newMap.put(pid.toString(), pDto.getProductName());
			}
		}
		return newMap;
	}

	public static Map getProductIDAndNameByPackIdList(String packageids) {
		Map newMap = new LinkedHashMap();
		if (packageids == null || "".equals(packageids)) {
			return newMap;
		}

		packageids = packageids.replaceAll(";", ",");
		if (packageids.startsWith(","))
			packageids = packageids.substring(1, packageids.length());
		if (packageids.endsWith(","))
			packageids = packageids.substring(0, packageids.length() - 1);
		String sql = "select p.productid,p.productname from t_product p join t_packageline pl on p.productid=pl.productid "
				+ "where pl.packageid in (" + packageids + ")";
		newMap = getMapBySQL(sql, true, true);
		return newMap;
	}

	public static Map getProductMapByProductIDList(String psids) {
		psids = psids.replaceAll(";", ",");
		if (psids.startsWith(","))
			psids = psids.substring(1, psids.length());
		if (psids.endsWith(","))
			psids = psids.substring(0, psids.length() - 1);
		Map productMap = new LinkedHashMap();
		if (psids == null || psids.trim().equals(""))
			return productMap;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		sql.append("select * from t_product where productid in (");
		sql.append(psids);
		sql.append(")");
		sql.append(" order by productid");
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getProductMapByProductIDList.sql:>>>>", sql.toString());
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProductDTO pDto = DtoFiller.fillProductDTO(rs);
				productMap.put(new Integer(pDto.getProductID()), pDto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProductMapByProductIDList", e);
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
		return productMap;
	}

	public static Map getCsiReasonBycsitypeAndaction(String csitype,
			String action) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sqlstr = "select t.* from t_csiactionreasonsetting t where t.csitype ='"
				+ csitype + "' and t.action='" + action + "' and t.status='V'";
		Map csiReasonMap = new HashMap();
		CsiActionReasonSettingDTO csiActionReasonSettingDto = new CsiActionReasonSettingDTO();
		LogUtility.log(Postern.class, LogLevel.DEBUG,
				"getCsiReasonBycsitypeAndaction.sql:>>>>", sqlstr);
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlstr);
			if (rs.next()) {
				csiActionReasonSettingDto = DtoFiller
						.fillCsiActionReasonSettingDTO(rs);
			}
			rs.close();
			if (csiActionReasonSettingDto.getSeqNo() != 0) {
				sqlstr = " select * from t_csiactionreasondetail where referseqno ="
						+ csiActionReasonSettingDto.getSeqNo()
						+ " and status='V'" + " order by priority";
				rs = stmt.executeQuery(sqlstr);
				ArrayList csiactionReasonDetailList = new ArrayList();
				csiReasonMap.put(csiActionReasonSettingDto,
						csiactionReasonDetailList);
				while (rs.next()) {
					CsiActionReasonDetailDTO dto = DtoFiller
							.fillCsiActionReasonDetailDTO(rs);
					csiactionReasonDetailList.add(dto);
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCsiReasonBycsitypeAndaction", e);
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
		LogUtility.log(Postern.class, LogLevel.WARN,
				"getCsiReasonBycsitypeAndaction", csiReasonMap);
		return csiReasonMap;
	}

	public static int getCurrentOrgIDByCPID(int custproblemID) {
		return getIntBySQL("select currentorgid from t_custproblemprocess where custproblemid="
				+ custproblemID + " order by id desc");
	}

	public static int getNextOrgIDByCPID(int custProblemID) {
		return getIntBySQL("select processorgid from t_customerproblem where id="
				+ custProblemID);
	}

	public static boolean buttonCanBeVisibleForCP(int custProblemID, int opID) {
		String isPrecise = getSystemsettingValueByName("SET_W_PRECISEWORK");
		int nextOrgId = getNextOrgIDByCPID(custProblemID);
		if (nextOrgId == opID || isPrecise == null || ("N").equals(isPrecise))
			return true;
		else
			return false;
	}

	public static boolean buttonCanBeVisibleForRepair(int jobCardID, int opID) {
		String isPrecise = getSystemsettingValueByName("SET_W_PRECISEWORK");
		int nextOrgId = getIntBySQL("select processorgid from t_jobcard where jobcardid="
				+ jobCardID);
		if (nextOrgId == opID || isPrecise == null || ("N").equals(isPrecise))
			return true;
		else
			return false;
	}

	public static Collection getCanBeTransferedToOrgID(int opOrgID,
			String role, String subRole) {
		Collection orgCol = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select mts.toorgid,org.orgname from t_manualtransfersetting mts,t_organization org,t_organization porg"
				+ " where mts.toorgid=org.orgid and porg.orgid=org.parentorgid and mts.sheettype='"
				+ role + "' and mts.fromorgid = " + opOrgID;
		if (CommonKeys.ORGANIZATION_ROLE_M.equals(role) && subRole != null
				&& "".equals(subRole))
			sql += " and mts.orgsubrole = " + subRole;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				OrganizationDTO orgDto = new OrganizationDTO();
				orgDto.setOrgID(rs.getInt(1));
				orgDto.setOrgName(rs.getString(2));
				orgCol.add(orgDto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCanBeTransferedToOrgID", e);
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
		return orgCol;
	}

	public static int getCustProblemIDByJobcardID(int jcid) {
		return getIntBySQL("select id from t_customerproblem where referjobcardid="
				+ jcid);
	}

	public static int getAutoNextOrgID(String role, String subrole,
			Object sheetDTO, String diagnosisResult) throws ServiceException {
		int districtid = Integer.parseInt((String) sheetDTO);
		return getAutoNextOrgID(role, null, null, null, diagnosisResult,
				districtid, 0);
	}

	/**
	 * 取得自动流转的组织
	 * 
	 * @param role
	 * @param subType
	 * @param serviceAccount
	 * @param addressId
	 * @return
	 * @throws ServiceException
	 */
	public static int getAutoNextOrgID(String role, String subType,
			int serviceAccount, int addressId) {
		int autoNextOrgId = 0;
		try {
			int sapid = 0;
			if (serviceAccount != 0)
				sapid = BusinessUtility.getSAProductIDBySAID(serviceAccount);
			autoNextOrgId = getAutoNextOrgID(role, subType, null, null, null,
					getDistrictIDByAddressID(addressId), sapid);

		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getAutoNextOrgID", e);
		}
		return autoNextOrgId;
	}

	/*
	 * role --组织角色类型 subrole --组织角色子类型 troubleType_DTO --故障类型 troubleSubType_DTO
	 * --故障子类型 diagnosisResult -- 诊断结果 districtid -- 区域id sapid
	 */
	public static int getAutoNextOrgID(String role, String subrole,
			String troubleType_DTO, String troubleSubType_DTO,
			String diagnosisResult, int districtid, int sapid)
			throws ServiceException {
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
		String orgSubrole = "";

		int currentPower = 0;

		int curServiceOrgID = 0;
		sql = "select * from t_roleorganization where orgrole='"
				+ role
				+ "'"
				+ " and districtid in (select id from t_districtsetting start with id="
				+ districtid + " connect by prior belongto=id)";

		// System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+sql);
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
				orgSubrole = rs.getString("orgsubrole");

				if (orgSubrole != null && subrole != null
						&& orgSubrole.equals(subrole)) {
					currentPower += 32;
				} else if (orgSubrole != null)
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
				} else if (saProductID > 0) {
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
			if (mostPerfectValue == 0)
				mostPerfectValue = curServiceOrgID;
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

	public static int getDistrictIDByAddressID(int addressid)
			throws ServiceException {
		return getIntBySQL("select districtid from t_address addr where addr.addressid="
				+ addressid);
	}

	public static boolean buttonCanBeVisibleForComplain(int custComplainID,
			int opID) {
		String isPrecise = getSystemsettingValueByName("SET_W_PRECISEWORK");
		int nextOrgId = getIntBySQL("select currentworkorgid from t_customercomplain where custcomplainid="
				+ custComplainID);
		if (nextOrgId == opID || isPrecise == null || ("N").equals(isPrecise))
			return true;
		else
			return false;
	}

	/**
	 * 根据付费方式id判断是否是现金支付
	 * 
	 * @param mopID
	 * @return
	 */
	public static boolean isCash(int mopID) {
		String cashFlag =getStringBySQL("select CASHFLAG from T_METHODOFPAYMENT WHERE MOPID="+ mopID);
		boolean isCash = false;
		if ("Y".equals(cashFlag)) {
			 isCash = true;
     	}
		return isCash;
	}

	/**
	 * 根据子客户id得到对应的集团客户id
	 * 
	 * @param conID
	 * @return
	 */
	public static String getGroupCustomerIDBySingleCustomerID(
			String singleCustomerID) {
		return getStringBySQL("select groupcustid from t_customer where customerid="
				+ singleCustomerID);
	}

	public static String getCsiLogReasonDescritionByLogID(int logID) {
		String sql = " select c.value from t_custserviceinteraction a ,t_csiactionreasonsetting b,t_csiactionreasondetail c ,t_csiprocesslog d "
				+ " where d.id ="
				+ logID
				+ " and a.id =d.csiid and a.type =b.csitype and d.workresultreason =c.key  and b.seqno =c.referseqno";
		return getStringBySQL(sql);
	}

	/**
	 * 根据受理原因key得到对应的中文表示
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

	/**
	 * 得到所有产品的Map
	 * 
	 * @param productclass
	 *            类别：硬件/软件
	 * @param status
	 *            状态
	 * @return
	 */
	public static Map getHardProductMap(String productclass, String status) {
		StringBuffer sql = new StringBuffer()
				.append("select productid,productname from t_product where 1=1");
		if (productclass != null) {
			sql.append(" and productclass='" + productclass + "'");
		}
		if (status != null) {
			sql.append(" and status='" + sql + "'");
		}
		return getMapBySQL(sql.toString());
	}

	/**
	 * 根据设备ID得到设备已经预授权的产品名称
	 * 
	 * @param deviceID
	 * @return
	 */
	public static String getOSSIAuthorizationProductNameByDeviceID(int deviceID) {
		if (deviceID == 0)
			return "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs =null;
		String ret = "";
		String strSql = "select distinct(p.productname) from t_ossi_authorization a,t_product p where a.productid=p.productid and a.devicesn='"
				+ deviceID + "' and a.operate='A'";
		try {
			LogUtility.log(Postern.class, LogLevel.DEBUG,
					"getOSSIAuthorizationProductNameByDeviceID", strSql);
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				if (!"".equals(ret))
					ret = ret + "，";
				ret = ret + rs.getString("productname");
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getOSSIAuthorizationProductNameByDeviceID", e);
			ret = "";
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (stmt !=null) {
				try{
					stmt.close();
				}catch (SQLException e){
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}
		return ret;
	}

	/**
	 * 得到非无效MOP的Map对象，key为MOP的ID (String Type)，value为MOP的name
	 * 
	 * @return
	 */
	public static Map getAllValidMOPOnlyIdAndName() {

		return getAllValidMOPAndOnlyIdAndNameByPaymentFlag(null);
	}

	/**
	 * 得到非无效MOP的Map对象，key为MOP的ID (String Type)，value为MOP的name
	 * 
	 * @return
	 */
	public static Map getAllValidMOPAndOnlyIdAndNameByPaymentFlag(String flag) {
		HashMap map = new LinkedHashMap();
		if (methodOfPaymentCache != null && methodOfPaymentCache.size() > 0) {
			Collection col = methodOfPaymentCache.values();
			Iterator itCol = col.iterator();
			while (itCol.hasNext()) {
				MethodOfPaymentDTO dto = (MethodOfPaymentDTO) itCol.next();

				if ("I".equals(dto.getStatus()))
					continue;
				if (flag != null && flag.trim().length() > 0)
					if (!flag.equalsIgnoreCase(dto.getPaymentflag()))
						continue;

				map.put(String.valueOf(dto.getMopID()), dto.getName());
			}
		}
		return map;
	}

	/**
	 * 根据客户id得到指定状态的业务帐户id-name的map custID：客户id 必填
	 * showName：true-显示名称；false-不显示名称 status：状态，要写成满足sql的条件,null表示不限状态
	 * 
	 * @return Map
	 */
	public static Map getServiceAccountMapByCustID(int custID,
			boolean showName, String status) {
		String sqlStr = "";
		if (showName) {
			sqlStr = "select seracct.serviceaccountid,seracct.serviceaccountid ||':'||nvl(ser.servicename,seracct.serviceaccountid) "
					+ "from t_serviceaccount seracct,t_service ser "
					+ "where ser.serviceid (+)= seracct.serviceid and seracct.customerid="
					+ custID;
		} else {
			sqlStr = "select serviceaccountid, serviceaccountid from t_serviceaccount seracct where seracct.customerid="
					+ custID;
		}
		if (status != null) {
			sqlStr = sqlStr + " and seracct.status " + status;
		}
		return getMapBySQL(sqlStr, true);
	}

	/**
	 * 根据客户id,业务帐户id及产品id得到客户产品map
	 * 
	 * @return Map
	 */
	public static Map getPsMapByCon(int custID, int serviceAccountID,
			int productID, boolean showName, String status) {
		StringBuffer sqlShowBuffer = new StringBuffer(
				"select distinct custpro.psid,custpro.psid");
		StringBuffer sqlTableBuffer = new StringBuffer(
				" from t_customerproduct custpro");
		StringBuffer sqlWhereBuffer = new StringBuffer(" where 1=1");
		if (showName) {
			sqlShowBuffer = new StringBuffer(
					"select distinct custpro.psid,custpro.psid ||':'||nvl(pro.productname,custpro.psid)");
			sqlTableBuffer.append(",t_product pro");
			sqlWhereBuffer.append(" and pro.productid (+)= custpro.productid");
		}
		if (custID != 0) {
			sqlWhereBuffer.append(" and custpro.customerid=" + custID);
		}
		if (serviceAccountID != 0) {
			sqlWhereBuffer.append(" and custpro.serviceAccountID="
					+ serviceAccountID);
		}
		if (productID != 0) {
			sqlWhereBuffer.append(" and custpro.productID=" + productID);
		}
		if (status != null) {
			sqlWhereBuffer.append(" and custpro.status" + status);
		}
		sqlWhereBuffer.append(" order by custpro.psid");
		return getMapBySQL(sqlShowBuffer.append(sqlTableBuffer).append(
				sqlWhereBuffer).toString(), true);
	}

	public static void main(String[] args) {
		// System.out.println("=====getMenuListbyModuleName======="+getMenuListbyModuleName("B"));
		// System.out.println(getDeviceByCustServiceAccountID(80000007,"deviceclass"));
		String a = null;
		String aa = "aa;" + a;
		System.out.println(aa);
	}

	/**
	 * @param customerid
	 * @return 根据客户id，取得所有状态非取消的业务账户信息
	 */
	public static Map getNotCancleServiceAccountMapByCustID(int customerid) {
		Map serviceAccountDataCache = new LinkedHashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = "select * from t_serviceaccount where CUSTOMERID="
					+ customerid
					+ " and (status='N' or status='H' or status='I' or status='S')";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String serviceAccountName = rs.getString("SERVICEACCOUNTNAME");
				String serviceAccountID = String.valueOf(rs
						.getInt("SERVICEACCOUNTID"));
				if (serviceAccountName != null
						&& !"".equals(serviceAccountName)) {
					serviceAccountDataCache.put(serviceAccountID,
							serviceAccountID + ":" + serviceAccountName);
				} else {
					serviceAccountDataCache.put(serviceAccountID,
							serviceAccountID + ":" + serviceAccountID);
				}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNormalServiceAccountMapByCustID", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getNormalServiceAccountMapByCustID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getNormalServiceAccountMapByCustID", e);
				}
			}
		}
		return serviceAccountDataCache;
	}

	/**
	 * @param ServiceAccountID
	 * @return 根据ServiceAccountID，取得CustomerProduct信息
	 */
	public static Map getCustomerProductByServiceAccountID(
			String serviceAccountID) {
		return getMapBySQL(
				"select t_customerproduct.psid as psid,t_customerproduct.psid || '：' || t_product.productname as productname from t_customerproduct left join t_product on t_customerproduct.productid=t_product.productid where t_customerproduct.status='N' and serviceAccountID='"
						+ serviceAccountID + "'", true, true);
	}

	/**
	 * 通过客户的ID得到个性化费率
	 * 
	 * @param psid
	 * @return
	 */
	public static CustomerBillingRuleDTO getCustomerBillingRuleID(int ID) {
		CustomerBillingRuleDTO dto = new CustomerBillingRuleDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("SELECT * FROM t_customerbillingrule WHERE ID=?");
			stmt.setInt(1, ID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillCustomerBillingRuleDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerProductDTOByPSID", e);
			dto = null;
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
	 * 统计用 根据条件得到客户产品包map
	 */
	public static Map getAllCustPackageMapByCon(String psPackageIDList,
			String packageClass, String status) {
		StringBuffer buff = new StringBuffer();
		buff
				.append("select distinct(p.PackageID), p.PackageName from T_ProductPackage p where 1=1");
		if (!(psPackageIDList == null || "".equals(psPackageIDList) || ","
				.equals(psPackageIDList)))
			buff.append(" and p.PackageID in (" + psPackageIDList + ") ");
		if (!(packageClass == null || "".equals(packageClass)))
			buff.append(" and p.PackageClass='" + packageClass + "'");
		if (!(status == null || "".equals(status)))
			buff.append(" and p.Status" + status);
		buff.append(" order by p.packageid");

		// 订户 T_Service
		return getMapBySQL(buff.toString());
	}

	/**
	 * 通过模拟终端流水号得到模拟终端
	 * 
	 * @param psid
	 * @return
	 */
	public static CatvTerminalDTO getCatvTerminalByID(String ID) {
		if (ID == null || "".equals(ID))
			return null;

		CatvTerminalDTO dto = new CatvTerminalDTO();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("SELECT * FROM t_CatvTerminal WHERE ID=?");
			stmt.setString(1, ID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = DtoFiller.fillCatvTerminalDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCustomerProductDTOByPSID", e);
			dto = null;
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
	 * 根据光节点id得到光节点编码
	 * 
	 * @param conID
	 * @return
	 */
	public static String getFiberNodeCodeByID(int ID) {
		return getStringBySQL(" select fibernodecode from t_fibernode where id="
				+ ID);
	}

	/**
	 * 根据map取得对应公用数据的键值对列表
	 * 
	 * @param mapName
	 * @return
	 */
	public static Map getCommonDateKeyAndValueMap(String mapName) {
		return getMapBySQL(
				"select key,value from t_commonsettingdata where  status <>'I' and Name ='"
						+ mapName + "' order by priority,key", true, true);
	}

	/**
	 * 根据t_systemsetting的name取得对应的值（这个只是用来判断字符串条件用的）
	 * 
	 * @param conID
	 * @return
	 */
	public static String getSystemSettingValue(String settingName) {
		return getStringBySQL(" Select Value From t_systemsetting Where Name='"
				+ settingName + "' and status='V'");

	}

	/**
	 * 查看是否存在正在处理的 模拟电视业务停机/复机/增端 受理信息 返回结果
	 */
	public static String CSIInfoForCATVPauseOrResume(String customerID,
			String csiType) {
		String CSIInfo = null;
		if (customerID == null || csiType == null)
			return CSIInfo;

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_custserviceinteraction where customerid = ? and (type = ? or type ='CAA') and status in ('N','W','P')";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, customerID);
			stmt.setString(2, csiType);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String temCsiType = rs.getString("TYPE");
				if ("CAA".equals(temCsiType))
					CSIInfo = "存在等待处理的增端单,请检查!";
				else if ("CAS".equals(temCsiType))
					CSIInfo = "存在等待处理的停机单,请不要重复受理!";
				else if ("CAR".equals(temCsiType))
					CSIInfo = "存在等待处理的复机单,请不要重复受理!";
				break;
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getPalletsByDepotID",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"hasCSIForCATVPauseOrResume", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"hasCSIForCATVPauseOrResume", e);
				}
			}
		}
		return CSIInfo;
	}

	/**
	 * 是否存在CATVID
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

	public static String getProductByServiceAccountID(String productClass,int saID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String productName = "";
		String sqlStr =" Select p.Productname  Productname From t_product p,t_customerproduct cp "
			          +" Where cp.productid=p.productid And cp.status!='C'  "
			          +" and p.productclass in ("+productClass+") And cp.serviceaccountid="+saID+" order by p.ProductClass,cp.PSID";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			while (rs.next()) {
				if ("".equals(productName))
					productName = rs.getString("Productname");
				else
					productName = productName + ";"
							+ rs.getString("Productname");
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllProductByServiceAccountID", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllProductByServiceAccountID", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllProductByServiceAccountID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllProductByServiceAccountID", e);
				}
			}
		}
		return productName;
	}

	public static String getPackageNameByServiceAccountID(int saID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String packagename = "";
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement(" select distinct p.packagename from t_productpackage p,t_customerproduct cp "
                                     +" where  p.packageid =cp.referpackageid "
                                     +" and cp.serviceaccountid =? "
                                     +" And (cp.status='I' or cp.status='N') "
                                     +" order by p.packagename ");
			stmt.setInt(1, saID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				if ("".equals(packagename))
					packagename = rs.getString("packagename");
				else
					packagename = packagename + ";"
							+ rs.getString("packagename");
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getPackageNameByServiceAccountID", e);
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
		return packagename;
	}
	/**
	 * 根据受理单id取得对应的产品包信息
	 * 
	 * @param csiID
	 * @return
	 */
	public static Map getPackageCampaignMapByCsiID(int csiID) {
		Map packageAndCampaignMap = new LinkedHashMap();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer()
				.append(" Select Distinct( referpackageid) referpackageid ,refercampaignid From t_csicustproductinfo Where csiid="
						+ csiID);
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				packageAndCampaignMap.put(new Integer(rs
						.getInt("referpackageid")), new Integer(rs
						.getInt("refercampaignid")));

			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCanBeTransferedToOrgID", e);
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
		return packageAndCampaignMap;
	}

	public static String getSumFeeValue(int csiid) {
		String sql = "Select Sum(Value) Value From T_ACCOUNTITEM Where REFERTYPE='M' And REFERID="
				+ csiid;
		return getStringBySQL(sql);
	}

	public static String getSumPaymentValue(int csiid) {
		String sql = "Select Sum(amount) Value From t_paymentrecord Where SourceType='M' And SourceRecordID="
				+ csiid;
		return getStringBySQL(sql);
	}

	/**
	 * 得到所有产品的Map
	 * 
	 * @param productclass
	 *            类别：硬件/软件
	 * @param status
	 *            状态
	 * @return
	 */
	public static Map getHardProductMap(String productclass, String status,
			String productIDList) {
		StringBuffer sql = new StringBuffer()
				.append("select productid,productname from t_product where 1=1");
		if (productclass != null) {
			sql.append(" and productclass='" + productclass + "'");
		}
		if (status != null) {
			sql.append(" and status='" + sql + "'");
		}
		if (productIDList != null && !productIDList.trim().equals("")) {
			sql.append(" and PRODUCTID in (" + productIDList + ")");
		}
		return getMapBySQL(sql.toString());
	}

	/**
	 * 根据列表判断有没有硬件
	 * 
	 * @return
	 */
	public static boolean containsHardProduct(String productIDList) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select productclass from t_product where 1=1";
		if (productIDList == null || productIDList.trim().equals(""))
			return true;
		else {
			sql = sql + " and PRODUCTID in (" + productIDList + ")";
		}
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String productClass = rs.getString("PRODUCTCLASS");

				if ("H".equals(productClass)) {
					return true;
				}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "containsHardProduct",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"hasCSIForCATVPauseOrResume", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"hasCSIForCATVPauseOrResume", e);
				}
			}
		}
		return false;
	}

	/**
	 * 根据帐户ID(acctid)取得此帐户总的余额（这个是为帐户资料显示用的）
	 * 
	 * @param conID
	 * @return
	 */
	public static String getBalanceByAcctId(int acctid) {
		// 得到支付
		String amount = getStringBySQL("select nvl(sum(pr.amount),0.0) amountSum from T_PaymentRecord pr where pr.acctid = "
				+ acctid + " and pr.status = 'V' ");
		// 得到费用
		String value = getStringBySQL("select nvl(sum(ai.value),0.0) valueSum from T_AccountItem ai where ai.feetype<>'P' and ai.acctid = "
				+ acctid + " and ai.status in ('V','1','3') ");

		LogUtility.log(Postern.class, LogLevel.WARN, "支付amount：" + amount);
		LogUtility.log(Postern.class, LogLevel.WARN, "费用value：" + value);

		if (amount != null && value != null) {
			LogUtility.log(Postern.class, LogLevel.WARN, "帐户总的余额 ："
					+ (Double.parseDouble(amount) - Double.parseDouble(value)));
			double doubl = Double.parseDouble(amount)
					- Double.parseDouble(value);
			DecimalFormat df = new DecimalFormat("#0.00");
			return df.format(doubl);
		} else {
			LogUtility.log(Postern.class, LogLevel.WARN, "return  0");
			return "0.00";
		}
	}

	/**
	 * 根据帐户ID得到帐户名称
	 * 
	 * @param ID
	 * @return
	 */
	public static String getAccountNameByID(String id) {
		if (id == null)
			return null;
		return getStringBySQL("select accountname from t_account where accountid="
				+ id);
	}

	/**
	 * 根据设备id得到用途的中文表示
	 * 
	 * @param
	 * @return
	 */
	public static String getPurposeStrListByID(String id) {
		if (id == null)
			return null;
		String purposeList = getStringBySQL("select PurposeStrList from t_terminaldevice where SerialNo='"
				+ id + "'");
		if (purposeList == null)
			return null;
		String purposeStrList = null;
		Map allMap = getHashKeyValueByName("SET_D_DEVICEUSEFORPURPOSE");
		String[] temPurpose = purposeList.split(",");
		if (temPurpose == null)
			return null;
		for (int i = 0; i < temPurpose.length; i++) {
			String eachPurposeStr = (String) allMap.get(temPurpose[i]);
			if (eachPurposeStr != null) {
				if (purposeStrList != null)
					purposeStrList = purposeStrList + "," + eachPurposeStr;
				else
					purposeStrList = eachPurposeStr;
			}
		}
		return purposeStrList;
	}

	public static PaymentRecordDTO getPayRecordDTO(int paySeqNo){
		String sql ="select * from t_paymentRecord where seqno="+paySeqNo;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PaymentRecordDTO dto =new PaymentRecordDTO();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto =DtoFiller.fillPaymentRecordDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getPayRecordDTO", e);
		} finally {
			if (rs !=null) {
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

	public static AccountDTO getAccountDTOByCustID(int custID) {
		String sql = "select * from t_account where accountid=(select max(accountID) from t_account where customerid="
				+ custID + " and status<>'C')";
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
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAccountDTOByCustID", e);
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

	/**
	 * 通过CCID得到优惠的名称
	 * 
	 * @param ccid
	 * @return
	 */
	public static String getCampaignNameByCustCampaignID(int ccid) {
		if (ccid == 0 || ccid < 0)
			return "";

		return getStringBySQL("select c.campaignname from t_campaign c,t_customercampaign cp "
				+ "where cp.campaignid=c.campaignid and cp.ccid=" + ccid);
	}

	public static String getNameByMopIDAndPaymentFlag(int mopId) {
		return getStringBySQL("select name from t_methodofpayment where PaymentFlag='Y' and status='V' and mopid="
				+ mopId);
	}

	/**
	 * 根据活动id（campaignID）找出奖励金额
	 * 
	 * @param activityId
	 * @return
	 */
	public static List getCampaignPaymentAwardDTOList(int campaignID) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List brDtoList = new ArrayList();
		String sql = "select t.* from t_campaignpaymentaward t where campaignid="
				+ campaignID + " order by seqno desc ";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CampaignPaymentAwardDTO dto = DtoFiller
						.fillCampaignPaymentAwardDTO(rs);
				brDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCampaignPaymentAwardDTOList", e);

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

		if (brDtoList.size() <= 0)
			brDtoList = null;
		return brDtoList;

	}
	
	public static Collection getProtocolDTOCol(int customerID){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List protocolDtoList = new ArrayList();
		String sql = "select t.* from t_protocol t where customerID="+ customerID ;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProtocolDTO dto = DtoFiller.fillProtocolDTO(rs);
				protocolDtoList.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getProtocolDTOCol", e);

		} finally {
			if (rs !=null) {
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
		return protocolDtoList;
	}

	public static Map getMethodOfPaymentMap() {

		return getMapBySQL("select mopid ,name  from t_methodofpayment where status='V' and paymentFlag='Y' ");

	}

	public static Map getMethodBankDeductionOfPaymentMap() {

		return getMapBySQL("select mopid ,name  from t_methodofpayment where status = 'V' and accountflag = 'Y' and paytype = 'BP' ");

	}
	
	/**
	 * 
	 * @param baithID
	 * @return
	 */
	public static ArrayList[] getCustomerNameAndProductNameWithBatchJob(
			int baithID) {
		ArrayList[] list = new ArrayList[2];
		ArrayList clist = new ArrayList();
		ArrayList plist = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuffer psql = new StringBuffer();
			psql.append("SELECT DISTINCT PR.PRODUCTNAME ");
			psql.append("FROM T_BATCHJOBITEM BJI ");
			psql.append("JOIN T_CUSTOMERPRODUCT CP ON BJI.PSID = CP.PSID ");
			psql.append("JOIN T_PRODUCT PR ON CP.PRODUCTID = PR.PRODUCTID ");
			psql.append("WHERE BATCHID = ? ");
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(psql.toString());

			stmt.setInt(1, baithID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				plist.add(rs.getString("PRODUCTNAME"));
			}
			rs.close();
			stmt.close();
			StringBuffer csql = new StringBuffer();
			csql.append("SELECT distinct CUST.NAME CUSTNAME ");
			csql.append("FROM T_BATCHJOBITEM BJI ");
			csql
					.append("JOIN T_CUSTOMER CUST ON BJI.CUSTOMERID = CUST.CUSTOMERID ");
			csql.append("WHERE BATCHID = ? ");
			stmt = con.prepareStatement(csql.toString());

			stmt.setInt(1, baithID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				clist.add(rs.getString("CUSTNAME"));
			}
			list[0] = plist;
			list[1] = clist;
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out.println("getTerminalDeviceByID exception:"
						+ e.getMessage());

				e.printStackTrace();
			}
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

	public static String getMopIdByBatchNo(int batchNo) {
		return getStringBySQL("select mopid from t_bankdeductionheader where batchNo="
				+ batchNo);
	}

	public static String getBillingCycleByBatchNo(int batchNo) {
		return getStringBySQL("select BillingCycle from t_bankdeductionheader where batchNo="
				+ batchNo);
	}

	public static String getInvoiceIdBySeqNo(int seqno) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String returnInvoiceId = "";
		int invoiceId = 0;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement(" select invoiceId from t_bankdeduction_invoicedetail where status = 'V' and seqno=?");
			stmt.setInt(1, seqno);
			rs = stmt.executeQuery();

			while (rs.next()) {
				invoiceId = rs.getInt("invoiceId");
				if (invoiceId != 0)
					returnInvoiceId = returnInvoiceId
							+ String.valueOf(invoiceId) + "/";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllProductByServiceAccountID", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllProductByServiceAccountID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllProductByServiceAccountID", e);
				}
			}
		}
		if (!"".equals(returnInvoiceId))
			returnInvoiceId = returnInvoiceId.substring(0, returnInvoiceId
					.length() - 1);
		return returnInvoiceId;
	}

	public static Map getCAWalletDefineMap() {

		return getMapBySQL(
				"select cawalletcode,cawalletname from t_cawalletdefine where status = 'V' order by cawalletcode ",
				true, true);

	}

	public static Map getAllCAWalletRate() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map rateMap = new LinkedHashMap();
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select cawalletcode,rate from t_cawalletdefine order by cawalletcode");
			rs = stmt.executeQuery();

			while (rs.next()) {
				rateMap.put(rs.getString(1), rs.getBigDecimal(2) + "");
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getAllCAWalletRate",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllCAWalletRate", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllCAWalletRate", e);
				}
			}
		}
		return rateMap;
	}

	// 根据业务帐户得到客户产品字符串 ，客户产品名称1,客户产品名称2；
	public static String getCustomerProductDescByServiceAccountID(
			int ServiceAccountID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String productDesc = "";
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement(" Select p.Productname Productname,p.Productid Productid From t_product p,t_customerproduct cp Where cp.productid=p.productid And cp.status<>'C' And cp.serviceaccountid=? ");
			stmt.setInt(1, ServiceAccountID);
			rs = stmt.executeQuery();

			while (rs.next()) {
				productDesc = productDesc + rs.getString("Productname") + ",";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllProductByServiceAccountID", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllProductByServiceAccountID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getAllProductByServiceAccountID", e);
				}
			}
		}
		if (!"".equals(productDesc))
			productDesc = productDesc.substring(0, productDesc.length() - 1);
		return productDesc;
	}

	public static String getServiceIDByCSIID(int csiId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String productList = "";
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select t.productid from t_csicustproductinfo t where t.csiId=?");
			stmt.setInt(1, csiId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				productList = productList + rs.getInt(1) + ";";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getServiceIDByCSIID",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getServiceIDByCSIID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getServiceIDByCSIID", e);
				}
			}
		}
		if (!"".equals(productList))
			productList = productList.substring(0, productList.length() - 1);

		return getServiceIDByProductIDs(productList);
	}

	public static String getAllWalletCodeBySAID(int said) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String codeList = "";
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select c.cawalletcode from t_cawallet c where c.serviceaccountid=? "
							+ " and c.scserialno in "
							+ " (select td.serialno from t_terminaldevice td "
							+ " left join t_customerproduct cp on td.deviceid =cp.deviceid "
							+ " left join t_serviceaccount sc on sc.serviceaccountid =cp.serviceaccountid "
							+ " where cp.serviceaccountid=? and cp.status<>'C' ) "
							+ " and c.status='V'");
			stmt.setInt(1, said);
			stmt.setInt(2, said);
			rs = stmt.executeQuery();
			while (rs.next()) {
				codeList = codeList + rs.getInt(1) + ";";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllWalletCodeBySAID", e);
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
		if (!"".equals(codeList))
			codeList = codeList.substring(0, codeList.length() - 1);

		return codeList;
	}

	public static String getRequiredCAWalletList() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String codeList = "";
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("SELECT * FROM t_cawalletdefine wd WHERE wd.required='Y'");

			rs = stmt.executeQuery();
			while (rs.next()) {
				codeList = codeList + rs.getInt(1) + "," + rs.getString(2)
						+ ";";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
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
		if (!"".equals(codeList))
			codeList = codeList.substring(0, codeList.length() - 1);

		return codeList;
	}

	public static Map gettAcctitemtypeByFeeType(String feetype) {
		String sql = "select ACCTITEMTYPEID,ACCTITEMTYPENAME from T_ACCTITEMTYPE";
		if (!(feetype == null || "".equals(feetype)))
			sql = sql + " where feetype='" + feetype + "'";

		return getMapBySQL(sql, true, true);

	}

	/**
	 * 通过传入的产品包ID的字符串，得到这些产品名称字符串
	 * 
	 * @param productList
	 * @return String
	 */
	public static String getPackageNameByPackageIDs(String packageIdList) {
		String PackageNames = "";
		String packageIDs[] = packageIdList.split(",");

		for (int i = 0; i < packageIDs.length; i++) {
			PackageNames = PackageNames
					+ getPackagenameByID(Integer.parseInt(packageIDs[i])) + ",";
		}

		if (!"".equals(PackageNames))
			PackageNames = PackageNames.substring(0, PackageNames.length() - 1);

		return PackageNames;
	}

	public static String getMarketSegmentName(int id) {
		return getStringBySQL("select name from t_marketsegment where id=" + id);
	}

	/**
	 * 通过传入的市场分区ID，得到对应的行政区域
	 * 
	 * @param productList
	 * @return String
	 */
	public static String getDistrictNameByMarketSegmentId(int id) {
		String DistrictName = "";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select districtid from t_marketsegmenttodistrict where marketsegmentid="
							+ id);

			rs = stmt.executeQuery();
			while (rs.next()) {
				DistrictName = DistrictName
						+ getNamebyDistrictSettingID(rs.getInt("districtid"))
						+ ",";
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDistrictNameByMarketSegmentId", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDistrictNameByMarketSegmentId", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDistrictNameByMarketSegmentId", e);
				}
			}
		}
		if (!"".equals(DistrictName))
			DistrictName = DistrictName.substring(0, DistrictName.length() - 1);

		return DistrictName;
	}

	// 根据设备需要列号得到设备明细信息
	public static Map getDeviceTransitionDetailDTOBySerialno(String serialno) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map dtoMap = new HashMap();
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_devicetransitiondetail where serialno= ? ");
			stmt.setString(1, serialno);
			rs = stmt.executeQuery();
			while (rs.next()) {
				DeviceTransitionDetailDTO dto = DtoFiller
						.fillDeviceTransitionDetailDTO(rs);
				dtoMap.put(rs.getInt("batchId") + "", dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDeviceTransitionDetailDTOBySerialno", e);
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
		return dtoMap;
	}


	// 小写金额转换大写金额
	public static String getMoneyCHN(double fee) {
		String str = "";
		ChnAmt chn = new ChnAmt(WebUtil.bigDecimalFormat(fee).toString());
		while (chn.next()) {
			str += chn.getResult();
		}
		return str;
	}

	public static Object[] getExportDataDefine(String url, String subType) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		if (url == null && "".equals(url)) {
			return list.toArray();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("HEAD.ID HEAD_ID,");
		sql.append("HEAD.ACTION HEAD_ACTION,");
		sql.append("HEAD.SUBTYPE HEAD_SUBTYPE,");
		sql.append("HEAD.SQLDESCRIPTION HEAD_SQLDESCRIPTION,");
		sql.append("HEAD.CUSTOMSQL HEAD_CUSTOMSQL,");
		sql.append("HEAD.DESCRIPTION HEAD_DESCRIPTION,");
		sql.append("HEAD.EXPORTFILENAME HEAD_EXPORTFILENAME,");
		sql.append("HEAD.STATUS HEAD_STATUS,");
		sql.append("HEAD.DT_LASTMOD HEAD_DT_LASTMOD,");
		sql.append("HEAD.DT_CREATE HEAD_DT_CREATE,");
		sql.append("COL.* ");
		sql.append("FROM T_EXPORTDATAHEADDEFINE HEAD ");
		sql.append("JOIN T_EXPORTDATACOLUMNDEFINE COL ON HEAD.ID = COL.REFID ");
		sql.append("WHERE HEAD.ACTION = '");
		sql.append(url);
		sql.append("' ");
		// AND INSTR(',M,', ',' || HEAD.SUBTYPE || ',') > 0
		if (subType != null && !"".equals(subType)) {
			sql.append("AND INSTR(',' || HEAD.SUBTYPE || ',',',");
			sql.append(subType.trim());
			sql.append(",') > 0 ");
		}
		sql.append("AND HEAD.STATUS = 'V' ");
		sql.append("AND COL.STATUS = 'V' ");
		sql.append("ORDER BY ARRANGEORDER");
		System.out.println("getExportDataDefine:::::" + sql.toString());
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			ExportDataHeadDefineDTO head = null;
			List collist = new ArrayList();
			while (rs.next()) {
				head = DtoFiller.fillExportDataHeadDefineDTO(rs, "HEAD_");
				ExportDataColumnDefineDTO dto = DtoFiller
						.fillExportDataColumnDefineDTO(rs);
				collist.add(dto);
			}
			list.add(head);
			list.add(collist);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtility.log(Postern.class, LogLevel.WARN, "getExportDataDefine",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getExportDataDefine", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getExportDataDefine", e);
				}
			}
		}
		return list.toArray();
	}

	/**
	 * 临时方法,测试用,为批量增机选择设备页面 填入设备序列号，
	 * 
	 * @param deviceMap
	 * @param num
	 * @return
	 */
	public static List getSerialno(Map deviceMap, int start, int num) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map[] list = new Map[num];
		String sql = "select * from (select rownum mynum, xxx.* from (select ter.serialno from  T_TERMINALDEVICE ter"
				+ " JOIN t_devicematchtoproduct dmp ON ter.devicemodel=dmp.devicemodel AND ter.matchflag<>'Y'"
				+ " where 1=1 and STATUS='W' AND dmp.productid=?"
				+ " order by DEVICEID desc) xxx ) where mynum between ? and ?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			for (Iterator it = deviceMap.entrySet().iterator(); it.hasNext();) {
				Entry en = (Entry) it.next();
				Integer productid = (Integer) en.getKey();
				DeviceClassDTO dto = (DeviceClassDTO) en.getValue();
				stmt.setInt(1, productid.intValue());
				stmt.setInt(2, start);
				stmt.setInt(3, num);
				rs = stmt.executeQuery();
				int index = 0;
				while (rs.next()) {
					Map serialMap = new HashMap();
					serialMap.put(dto.getDeviceClass(), rs
							.getString("serialno"));
					System.out.println("serialMap========" + serialMap);
					if (list[index] == null) {
						list[index] = serialMap;
					} else {
						list[index].putAll(serialMap);
					}
					index++;
				}
				rs.close();
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getSerialno", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN, "getSerialno",
							e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN, "getSerialno",
							e);
				}
			}
		}
		return java.util.Arrays.asList(list);
	}

	public static Map getProductByAccountID(int accountID) {
		return getMapBySQL("select p.productid||';'||cp.psid,p.productname from t_product p,t_customerproduct cp where p.productid=cp.productid and cp.accountid = "
				+ accountID + " and cp.status <>'C'");
	}

	/**
	 * 获取配对是否立即授权标志
	 * 
	 * @return String preAuthorImm
	 * @throws ServiceException
	 */
	public static String getLogisticsSettingPreAuthImm() {
		String preAuthorImm = "N"; // 配对立即预授权
		String  matchHandPreth = getStringBySQL("select MATCHANDPREAUTH from t_logisticssetting");
        if (matchHandPreth !=null && !matchHandPreth.trim().equals("")){
        	preAuthorImm =matchHandPreth;
        }
		return preAuthorImm;
	}
	
	/**
	 * 根据受理单ID获得业务帐号ID
	 */
	public static String getServiceAccountIdByCsiId(int CsiId) {
		return getStringBySQL("select cm.serviceaccountid from t_custserviceinteraction t , t_customercampaign cm where t.id = "
				+ CsiId + " and cm.ccid = to_number(t.comments)");
	}

	/**
	 * 根据设备序列号得到设备已经预授权的产品名称
	 * 
	 * @param deviceID
	 *            200
	 * @return
	 */
	public static String getOSSIAuthorizationProductNameByDeviceSerialno(
			String Serialno) {
		if (Serialno == null)
			return "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String ret = "";
		String strSql = "select distinct(p.productname) from t_ossi_authorization a,t_product p where a.productid=p.productid and a.deviceserialno='"
				+ Serialno + "'";
		try {
			LogUtility.log(Postern.class, LogLevel.DEBUG,
					"getOSSIAuthorizationProductNameByDeviceSerialno", strSql);
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			while (rs.next()) {
				if (!"".equals(ret))
					ret = ret + "，";
				ret = ret + rs.getString("productname");
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getOSSIAuthorizationProductNameByDeviceSerialno", e);
			ret = "";
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}
		return ret;
	}

	// 获得预授权操作Key、Value映射
	public static Map getAllPreAuth() {
		return getMapBySQL("select t.key,t.value from t_commonsettingdata t "
				+ "where t.name = 'SET_D_DEVICEPREAUTH_OPERATIONTYPE' and t.key in ('AP','DP','DA')");
	}

	public static DeviceBatchPreauthDTO getDeviceBatchPreauthDTOByBatchID(
			int batchId) {
		DeviceBatchPreauthDTO dto = new DeviceBatchPreauthDTO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_devicebatchpreauth where batchid= ? ");
			stmt.setInt(1, batchId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillDeviceBatchPreauthDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDeviceBatchPreauthDTOByBatchID", e);
			dto = null;
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDeviceBatchPreauthDTOByBatchID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDeviceBatchPreauthDTOByBatchID", e);
				}
			}
		}
		return dto;

	}

	public static String getProductNameListByBatchId(int batchId) {
		// 获得数据库连接
		Connection dbConn = null;
		Statement sqlStrStmt = null;
		ResultSet rs = null;
		StringBuffer productNameList = new StringBuffer("");

		String sqlStr = "select p.productname from t_product p where p.productid in "
				+ "(select t.productid from t_devicepreauthrecord t where t.batchid="
				+ batchId + ")";

		LogUtility.log(Postern.class, LogLevel.DEBUG, sqlStr);
		try {
			dbConn = DBUtil.getConnection();
			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sqlStr);

			if (rs.next()) {
				productNameList.append(rs.getString(1));
			}
			while (rs.next()) {
				productNameList.append("," + rs.getString(1));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
				} catch (SQLException e) {
				}
			}
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
				}
			}
		}
		return productNameList.toString();
	}

	public static String getCommomsettingData_ValueByKeyAndName(String name,
			String key) {
		return getStringBySQL("select t.value from t_commonsettingdata t "
				+ "where t.name = '" + name + "' and t.key='" + key + "'");

	}

	/**
	 * 取得机顶盒/智能卡的所有设备型号
	 */
	public static Map getAllDeviceModelsForSTBOrSC() {
		return getMapBySQL("select devicemodel,description from t_devicemodel where deviceclass in ('STB','SC')");
	}

	// 获得配对操作Key、Value映射
	public static Map getAllPreMatch() {
		return getMapBySQL("select t.key,t.value from t_commonsettingdata t "
				+ "where t.name = 'SET_D_DEVICEPREAUTH_OPERATIONTYPE' and t.key in ('AM','DM')");
	}

	/**
	 * 获得合同预付款方式,wangpeng@20080226
	 */
	public static Map getContractPaymentMop() {
		return getMapBySQL("select t.mopid,t.name from t_methodofpayment t where t.status = 'V' and t.PaymentFlag = 'Y'");
	}

	/**
	 * 根据ID取得打印用到的背景图片,返回Blob类型的数据
	 */
	public static byte[] getPrintConfigImgByID(int id) {
		// 获得数据库连接
		Connection dbConn = DBUtil.getConnection();
		if (dbConn == null)
			throw new RuntimeException("DB Connection error.");

		Statement sqlStrStmt = null;
		ResultSet rs = null;
		Blob blob = null;
		byte buf[] = null;

		String sqlStr = "select backImg from t_printConfig where id=" + id
				+ " and status='V'";

		LogUtility.log(Postern.class, LogLevel.DEBUG, sqlStr);
		try {
			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sqlStr);

			if (rs.next()) {
				blob = rs.getBlob("backImg");
			}
			if (blob != null) {
				long pos = 1;
				buf = blob.getBytes(pos, new Long(blob.length()).intValue());
			}

		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
				}
			}
		}
		return buf;
	}
	
	/*
	 * 得到客户树中业务帐户显示的最大数量
	 */
	public static int getTreeShowSaMaxNum() {
		int TreeShowSaMaxNum = 0;
		String strTreeShowSaMaxNum = getSystemSettingValue("SET_V_CUSTOMERTREE_SHOW_SERVICEACCOUNT_MAXNUM");
		if (strTreeShowSaMaxNum != null && !"".equals(strTreeShowSaMaxNum))
			TreeShowSaMaxNum = Integer.parseInt(strTreeShowSaMaxNum);
		return TreeShowSaMaxNum;
	}

	/*
	 * 根据客户ID取得客户下业务帐户的个数
	 */
	public static int getSACountByCustomerID(int customerID) {
		String customerStatus = "";
		customerStatus = getCustomerStatusByID(customerID);
		String strSql = "";
		if (CommonKeys.CUSTOMER_STATUS_CANCEL.equals(customerStatus)) {
			strSql = "select rownum myrow,a.* from t_serviceaccount a where a.customerid = "
					+ customerID;
		} else {
			strSql = "select rownum myrow,a.* from t_serviceaccount a where a.customerid = "
					+ customerID + " and a.status <> 'C' ";
		}
		strSql = "Select count(*) from ( " + strSql + " ) ";
		return getIntBySQL(strSql);
	}

	/*
	 * 根据客户信息ID，取得客户下最新的一个业务帐户
	 */
	public static int getLatestSAIDByCustomerID(int customerID) {
		String customerStatus = "";
		customerStatus = getCustomerStatusByID(customerID);
		String strSql = "";
		if (CommonKeys.CUSTOMER_STATUS_CANCEL.equals(customerStatus)) {
			strSql = "select max(a.serviceaccountid) from t_serviceaccount a where a.customerid = "
					+ customerID;
		} else {
			strSql = "select max(a.serviceaccountid) from t_serviceaccount a where a.customerid = "
					+ customerID + "  and a.status <> 'C' ";
		}
		return getIntBySQL(strSql);
	}

	/*
	 * 根据业务帐户，取得设备2(智能卡)序列号
	 */
	public static String getSCSerialNoByServiceAccountID(int ServiceAccountID) {
		String sqlSC = "select distinct td.serialno from t_customerproduct cp "
				+ "left join t_terminaldevice td on cp.linktodevice2=td.deviceid "
				+ "where serviceaccountid=" + ServiceAccountID;
		String SCSerialNo = getStringBySQL(sqlSC);
		if (SCSerialNo == null)
			SCSerialNo = "";
		return SCSerialNo;
	}

	/*
	 * 根据业务帐户，取得设备1（机顶盒）序列号
	 */
	public static String getSTBSerialNoByServiceAccountID(int ServiceAccountID) {
		String sqlSTB = "select distinct td.serialno from t_customerproduct cp "
				+ "left join t_terminaldevice td on cp.linktodevice1=td.deviceid "
				+ "where serviceaccountid=" + ServiceAccountID;
		String STBSerialNo = getStringBySQL(sqlSTB);
		if (STBSerialNo == null)
			STBSerialNo = "";
		return STBSerialNo;
	}

	/*
	 * 根据业务帐户，取得设备1（机顶盒）型号
	 */
	public static String getDevice1ClassByServiceAccountID(int ServiceAccountID) {
		String sql = "select distinct td.deviceclass from t_customerproduct cp "
				+ "left join t_terminaldevice td on cp.linktodevice1=td.deviceid "
				+ "where serviceaccountid=" + ServiceAccountID;
		String description = getStringBySQL(sql);
		if (description == null)
			description = "";
		return description;
	}

	/*
	 * 根据业务帐户，取得设备2(智能卡)型号
	 */
	public static String getDevice2ClassByServiceAccountID(int ServiceAccountID) {
		String sql = "select distinct td.deviceclass from t_customerproduct cp "
				+ "left join t_terminaldevice td on cp.linktodevice2=td.deviceid "
				+ "where serviceaccountid=" + ServiceAccountID;
		String description = getStringBySQL(sql);
		if (description == null)
			description = "";
		return description;
	}

	/*
	 * 根据业务帐户，取得设备1的devicemodelDTO
	 */
	public static DeviceModelDTO getDevice1ModelDTOByServiceAccountID(
			int ServiceAccountID) {
		String sql = "select distinct dm.* from t_customerproduct cp "
				+ "left join t_terminaldevice td on cp.linktodevice1=td.deviceid "
				+ "left join t_devicemodel dm on dm.devicemodel=td.devicemodel "
				+ "where serviceaccountid=" + ServiceAccountID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DeviceModelDTO dto = new DeviceModelDTO();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto = DtoFiller.fillDeviceModelDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDevice1ModelDTOByServiceAccountID", e);
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

	/*
	 * 根据业务帐户，取得设备2(智能卡)devicemodelDTO
	 */
	public static DeviceModelDTO getDevice2ModelDTOByServiceAccountID(
			int ServiceAccountID) {
		String sql = "select distinct dm.* from t_customerproduct cp "
				+ "left join t_terminaldevice td on cp.linktodevice2=td.deviceid "
				+ "left join t_devicemodel dm on dm.devicemodel=td.devicemodel "
				+ "where serviceaccountid=" + ServiceAccountID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DeviceModelDTO dto = new DeviceModelDTO();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dto = DtoFiller.fillDeviceModelDTO(rs);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDevice2ModelDTOByServiceAccountID", e);
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

	public static Map getAllBandCommand() {
		return getMapBySQL("select commandname,description from t_bbicommand ");
	}

	public static int getEventClassByEventID(int eventID) {
		return getIntBySQL("select t.EventClass from t_systemevent t where sequenceno="
				+ eventID);
	}

	/**
	 * wangfang 2008.05.28 
	 	 * @param csiId  预约增机单ID
	 	 * @return CsiProcessLogDTO : 预约增机单确认的处理日志记录
	 */
	public static CsiProcessLogDTO getCsiProcessLogActionDTO(int csiId) {
		CsiProcessLogDTO csiprocesslogDto = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from t_csiprocesslog where action='P' and csiid =" + csiId;
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				csiprocesslogDto = new CsiProcessLogDTO();
				csiprocesslogDto = DtoFiller.fillCsiProcessLogDTO(rs);
			}
		} 
		catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getCsiProcessLogDTO",e);
			csiprocesslogDto = null;
		} 
		finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (stmt != null) 
			{
				try 
				{
					stmt.close();
				} 
				catch (SQLException e) 
				{
					csiprocesslogDto = null;
				}
			}
			
			if (con != null) 
			{
				try 
				{
					con.close();
				} 
				catch (SQLException e) 
				{
					csiprocesslogDto = null;
				}
			}
		}
		
		return csiprocesslogDto;
	}
	/**
	 * 根据产品包列表字符串和产品类型取得产品集合
	 * @param packageID
	 * @param productClass
	 * @return
	 */
	public static Collection getProductIDByPackageID(int packageID,String productClass) {
		ArrayList lst = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = "select distinct(b.PRODUCTID) from T_PACKAGELINE b,T_PRODUCT p where b.PACKAGEID ="
					+ packageID
					+ " and p.PRODUCTCLASS='"
					+ productClass
					+ "' and b.PRODUCTID=p.PRODUCTID";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				lst.add(new Integer(rs.getInt("PRODUCTID")));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, "getProductIDByPackageID",e);
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
	public static String getAcctItmeTypeIDByProductID(int productID) {
		return getStringBySQL("Select acctItemTypeID From t_billingrule Where eventclass=17 And status='V' And Sysdate Between validfrom And validto And  productid="
				+ productID);
	}
	public static int getVodstbDeviceImportCount(int batchID){
		return getIntBySQL("Select count(*) from vod_stbdevice_import_detail t where t.batchID="+batchID);
	}
	public static String getCmModelByStbModel(String stbModel){
		return getStringBySQL("select t.cmskey from t_migration_base t where t.basecode='SET_VOD_MATCHDEVICE' and t.xmlkey='"+stbModel+"'");
	}

	//产品迁移时取其他所有正常的业务账户
	public static Map getOtherServiceAccount(int customerid,int serviceaccountid) {
		return getMapBySQL("select serviceaccountid a,serviceaccountid b from t_serviceaccount where status='N' and customerid="+customerid+" and serviceaccountid<>"+serviceaccountid+" order by serviceaccountid");
	}
	public static boolean ishasSameProduct(String said,String psid){
		int count = getIntBySQL("select cp2.* from t_customerproduct cp,t_customerproduct cp2 where cp.productid=cp2.productid and cp2.serviceaccountid="+said+" and cp.psid="+psid);
		if (count > 0)
			return true;
		else
			return false;
	}
	/**
	 * @param customerid
	 * @return 根据客户id，取得所有状态正常和已预退的业务账户信息
	 */
	public static Map getNPServiceAccountMapByCustID(int customerid) {
		Map serviceAccountDataCache = new LinkedHashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = "select * from t_serviceaccount where CUSTOMERID="+customerid+" and status in ('N','P')";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String serviceAccountName = rs.getString("SERVICEACCOUNTNAME");
				String serviceAccountID = String.valueOf(rs.getInt("SERVICEACCOUNTID"));
				if (serviceAccountName != null&& !"".equals(serviceAccountName)) {
					serviceAccountDataCache.put(serviceAccountID,serviceAccountID + ":" + serviceAccountName);
				} else {
					serviceAccountDataCache.put(serviceAccountID,
							serviceAccountID + ":" + serviceAccountID);
				}
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getNPServiceAccountMapByCustID", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getNPServiceAccountMapByCustID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getNPServiceAccountMapByCustID", e);
				}
			}
		}
		return serviceAccountDataCache;
	}
	public static boolean isCPSANN(String said,String psid){
		int count = getIntBySQL("select * from t_customerproduct where status='N' and psid="+psid);
		int count2= getIntBySQL("select * from t_serviceaccount where status='N' and serviceaccountid="+said);
		if (count > 0 && count2>0)
			return false;
		else
			return true;
	}
	
	/**
	 * 根据printSheetType打印类型（受理单，工单等等，见打印类型公用数据）,sheetSubType打印子类型,csiReason受理原因（针对受理单）,printReason打印原因（登记原因，费用原因。针对受理单），取得PrintConfigDTO
	 */
	public static PrintConfigDTO getPrintConfigDTO(String printSheetType,
			String sheetSubType, String csiReason, String printReason) {

		PrintConfigDTO dto = null;

		int MostPerfectPower = 0;
		int ID = 0;

		// 初始化当前匹配值
		int CurrentPower = 0;
		int CurrentID = 0;
		String rsSheetSubType = "";
		String rsCsiReason = "";
		String rsPrintReason = "";

		// 获得数据库连接
		Connection dbConn = DBUtil.getConnection();
		if (dbConn == null)
			throw new RuntimeException("DB Connection error.");

		Statement sqlStrStmt = null;
		ResultSet rs = null;

		try {

			String sql = "select id,printsheettype,sheetsubtype,csireason,printreason from t_printconfig where printsheettype='"
					+ printSheetType + "'";
			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sql);
			// 搜索并比较每条费率规则的匹配程度
			while (rs.next()) {
				// 获取三个字段的取值
				CurrentID = rs.getInt("id");
				rsSheetSubType = rs.getString("sheetsubtype");
				rsCsiReason = rs.getString("csireason");
				rsPrintReason = rs.getString("printreason");

				// 设置权重初始值
				CurrentPower = 0;
				if (rsSheetSubType != null && !"".equals(rsSheetSubType)) {
					if (rsSheetSubType.equals(sheetSubType))
						CurrentPower += 16;
					else
						continue;
				}

				if (rsPrintReason != null && !"".equals(rsPrintReason)) {
					if (rsPrintReason.equals(printReason))
						CurrentPower += 8;
					else
						continue;
				}

				if (rsCsiReason != null && !"".equals(rsCsiReason)) {
					if (rsCsiReason.equals(csiReason))
						CurrentPower += 4;
					else
						continue;
				}

				// PrintSheetType已经匹配
				CurrentPower += 1;
				// 比较并更新最优权重值，
				if (CurrentPower > MostPerfectPower) {
					// 更新最优权重值和ID
					MostPerfectPower = CurrentPower;
					ID = CurrentID;
					LogUtility.log(Postern.class, LogLevel.WARN,
							"T_printconfig ID:::::" + ID);
				}
			}

			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("select * from t_printConfig where id=" + ID);

			LogUtility.log(Postern.class, LogLevel.DEBUG, sqlStr);

			sqlStrStmt = dbConn.createStatement();
			rs = sqlStrStmt.executeQuery(sqlStr.toString());
			if (rs.next())
				dto = DtoFiller.fillPrintConfigDTO(rs);

		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN, e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
					rs.close();
				}catch (SQLException e){
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
				}
			}
		}
		return dto;
	}
	
	/**
	 * 获取配置打印信息T_PrintBlock表
	 * 
	 * @return List
	 * @throws ServiceException
	 */
	public static List getPrintBlock(int refConfigId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List collist = new ArrayList();
		// if(url==null&&"".equals(url)){return null;}
		if (refConfigId == 0) {
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("ID,");
		sql.append("refConfigId,");
		sql.append("CUSTOMSQL,");
		sql.append("DESCRIPTION,");
		sql.append("MARGINLEFT,");
		sql.append("MARGINTOP,");
		sql.append("STATUS,");
		sql.append("TEXTSIZE,");
		sql.append("DT_LASTMOD,");
		sql.append("DT_CREATE ");
		sql.append("FROM T_PrintBlock ");
		sql.append("WHERE refConfigId = " + refConfigId);
		// sql.append("WHERE ACTION = '");
		// sql.append(url);
		// sql.append("' ");
		// AND INSTR(',M,', ',' || HEAD.SUBTYPE || ',') > 0
		/**
		 * if(subType!=null&&!"".equals(subType)){ sql.append(" AND INSTR(',' ||
		 * TYPE || ',',',"); sql.append(subType.trim()); sql.append(",') > 0 "); }
		 * if(csiReason!=null&&!"".equals(csiReason)){ sql.append(" AND
		 * CSIREASON = '+csiReason+' "); }
		 */
		sql.append(" AND STATUS = 'V' ");
		LogUtility.log(Postern.class, LogLevel.WARN, "getPrintBlock:::::"
				+ sql.toString());
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			PrintBlockDTO head = null;

			while (rs.next()) {
				head = DtoFiller.fillPrintBlockDTO(rs);
				collist.add(head);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtility.log(Postern.class, LogLevel.WARN, "getPrintBlock", e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getPrintBlock", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getPrintBlock", e);
				}
			}
		}
		return collist;
	}
	/**
	 * 获取配置打印信息 ，T_PrintBlock表和T_PrintBlockDetail表
	 * 
	 * @return Object[]数组
	 * @throws ServiceException
	 */
	public static Object[] getPrintDataDefine(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		if (id == 0) {
			return list.toArray();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("HEAD.ID HEAD_ID,");
		sql.append("HEAD.ID HEAD_REFCONFIGID,");
		sql.append("HEAD.CUSTOMSQL HEAD_CUSTOMSQL,");
		sql.append("HEAD.DESCRIPTION HEAD_DESCRIPTION,");
		sql.append("HEAD.MARGINLEFT HEAD_MARGINLEFT,");
		sql.append("HEAD.MARGINTOP HEAD_MARGINTOP,");
		sql.append("HEAD.TEXTSIZE HEAD_TEXTSIZE,");
		sql.append("HEAD.STATUS HEAD_STATUS,");
		sql.append("HEAD.DT_LASTMOD HEAD_DT_LASTMOD,");
		sql.append("HEAD.DT_CREATE HEAD_DT_CREATE,");
		sql.append("COL.* ");
		sql.append("FROM T_PrintBlock HEAD ");
		sql.append("JOIN T_PrintBlockDetail COL ON HEAD.ID = COL.REFBLOCKID ");
		sql.append("WHERE HEAD.id = " + id);
		// AND INSTR(',M,', ',' || HEAD.SUBTYPE || ',') > 0
		sql.append(" AND HEAD.STATUS = 'V' ");
		sql.append(" AND COL.STATUS = 'V' ");
		LogUtility.log(Postern.class, LogLevel.WARN, "getPrintBlockDetail:::::"
				+ sql.toString());
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			PrintBlockDTO head = null;
			List collist = new ArrayList();
			while (rs.next()) {
				head = DtoFiller.fillPrintBlockDTO(rs, "HEAD_");
				PrintBlockDetailDTO dto = DtoFiller.fillPrintBlockDetailDTO(rs);
				collist.add(dto);
			}
			list.add(head);
			list.add(collist);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtility.log(Postern.class, LogLevel.WARN, "getPrintBlockDetail",
					e);
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
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getPrintBlockDetail", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getPrintBlockDetail", e);
				}
			}
		}
		return list.toArray();
	}
	
	public static String getSerialNoByCommentsAndClass(String comments,String deviceClass) {
		return getStringBySQL(" select td.serialno from t_customerproduct cp ,t_terminaldevice td "
                             +" where cp.serviceaccountid in ( "
                             +" select cpg.serviceaccountid from t_customercampaign cpg "
                             +" where cpg.ccid ="+comments+") "
                             +" and cp.deviceid =td.deviceid "
                             +" and td.deviceclass ='"+deviceClass+"'");
	}
	
	public static ArrayList getCARecvList(int queueID) {
		ArrayList res = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CARecvDTO dto = null;

		String sql = "select * from t_carecv where queueid=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, queueID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillCARecvDTO(rs);
				res.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCARecvList", e);
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

	public static ArrayList getCASentList(int queueid) {
		ArrayList res = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CASentDTO dto = null;

		String sql = "select * from t_casent where queueid=?";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, queueid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				dto = DtoFiller.fillCASentDTO(rs);
				res.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(BusinessUtility.class, LogLevel.WARN,
					"getCASentList", e);
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
	 * 得到所有销售过的产品map(不包括创建但没有销售过的)，KEY为产品的ID(字符串格式)，value为产品的名称
	 * 
	 * @return
	 */
	public static Map getAllSellProductIDAndName(String serviceId,String productType) {
		HashMap map = new LinkedHashMap();
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sqlStrFrom  = new StringBuffer();
		StringBuffer sqlStrWhere  = new StringBuffer();
		sqlStrFrom.append("SELECT pro.PRODUCTID,pro.PRODUCTNAME FROM T_PRODUCT pro");
		sqlStrWhere.append(" WHERE pro.STATUS != 'R'");
		if((serviceId != null) && (!"".equals(serviceId))){
			sqlStrFrom.append(",T_ProductToService proser");
			sqlStrWhere.append(" and pro.PRODUCTID=proser.PRODUCTID and proser.SERVICEID="+serviceId);
		}
		if((productType != null) && (!"".equals(productType))){
			sqlStrWhere.append(" and pro.ProductClass='"+productType+"'");
		}
			
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery(sqlStrFrom.append(sqlStrWhere).toString());
			while (rs.next()) {
				map.put("" + rs.getInt("PRODUCTID"), rs
						.getString("PRODUCTNAME"));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAllProductIDAndName", e);

		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
	
	public static Map getRuleDistByOrgID(int orgId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map ruleMap = new HashMap();
		String sql =" select t.id , t.name , (select name from t_districtsetting dis where dis.id =t.belongto) belongName "
			       +" from t_districtsetting t "
			       +" connect by prior t.id =t.belongto start with  t.id  in ( select t1.districtid from t_orggoverneddistrict t1 where t1.orgid="
				   + orgId + ")";
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String qy =rs.getString("belongName");
				qy =qy +"-" +rs.getString("name");
				ruleMap.put(new Integer(rs.getInt("id")), qy);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getRuleDistByOrgID", e);

		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
		return ruleMap;
	}
	
	public static int getFoundCustomerCountByBatchNo(int batchNo){
		return  getIntBySQL("select count(*) from t_foundcustomerdetail t where t.batchNo="+batchNo);
	}
	
	public static Map getFoundCustomerSortDetail(int batchNo){
		Map mp = new HashMap();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql =" select t1.belongto,t1.id,sum(t.oncepaymoney) oncepaymoney, "
			       +" sum(t.prepaymoney) prepaymoney ,count(*) ct"
			       +" from t_foundcustomerdetail t,t_districtsetting t1 "
			       +" where t.batchNo=? and t.distrinctid =t1.id"
			       +" group by  t1.belongto,t1.id";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, batchNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int   belongto =rs.getInt("belongto");
		        int   distId =rs.getInt("id");
		        float oncepaymoney =rs.getFloat("oncepaymoney");
		        float prepaymoney =rs.getFloat("prepaymoney");
		        int   count =rs.getInt("ct");
		        String distName =((DistrictSetting)getAllDistrictSettingCache().get(""+belongto)).getName();
		        distName =distName+"--";
		        distName =distName+((DistrictSetting)getAllDistrictSettingCache().get(""+distId)).getName();
		        Collection moneyList =new ArrayList();
		        moneyList.add(new Float(oncepaymoney));
		        moneyList.add(new Float(prepaymoney));
		        moneyList.add(new Integer(count));
		        mp.put(distName, moneyList);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getFoundCustomerSortDetail", e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
		return mp;
	}
	
	public static Map getDeviceSerialNoByCSIandClass(int csiid,
			String deviceClass) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map productMap =new HashMap();
		String sql = " Select t.productid, "
		           + " (select td.serialno from t_terminaldevice td,t_customerproduct cp "
				   + " where td.deviceid =cp.deviceid and cp.serviceaccountid =t.referserviceaccountid "
				   + " and td.deviceclass ='"+deviceClass+"') serialno "
				   + " From t_csicustproductinfo t Where t.csiid="+csiid
				   + " order by t.productid ";
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String productid =rs.getString("productid");
				String serialno =rs.getString("serialno");
				if (productid !=null && serialno !=null){
				   if (productMap.containsKey(productid)){
					   Collection serialNoCol =(Collection)productMap.get(productid);
					   serialNoCol.add(serialno);
				   }else{
				 	   Collection serialNoCol =new ArrayList();
					   serialNoCol.add(serialno);
					   productMap.put(productid,serialNoCol);
				   }
				}
			}
		} catch (SQLException e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getDeviceSerialNoByCSIandClass", e);
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
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}
		return productMap;
	}
	
	public static boolean isSaNewFlag(String serialNo){
		String newFlag =getStringBySQL(" select pr.newsaflag from t_devicematchtoproduct t,t_terminaldevice td,t_product pr "
                                      +" where t.devicemodel =td.devicemodel "
                                      +" and t.productid =pr.productid "
                                      +" and td.serialno ='"+serialNo+"'");
		if ("Y".equals(newFlag)){
			return true;
		}else{
			return false;
		}
	}
	
	public static int getExportCustomerCountByBatchNo(int batchNo){
		return  getIntBySQL("select count(*) from t_exportcustomerdetail t where t.batchNo="+batchNo);
	}
	
	public static Map getExportCustomerSortDetail(int batchNo){
		Map mp = new HashMap();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql =" select t1.belongto,t1.id,count(*) ct"
			       +" from t_exportcustomerdetail t,t_districtsetting t1 "
			       +" where t.batchNo=? and t.distrinctid =t1.id"
			       +" group by  t1.belongto,t1.id";
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, batchNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int   belongto =rs.getInt("belongto");
		        int   distId =rs.getInt("id");
		        int   count =rs.getInt("ct");
		        String distName =((DistrictSetting)getAllDistrictSettingCache().get(""+belongto)).getName();
		        distName =distName+"--";
		        distName =distName+((DistrictSetting)getAllDistrictSettingCache().get(""+distId)).getName();
		        mp.put(distName, new Integer(count));
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getFoundCustomerSortDetail", e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
		return mp;
	}
	
	public static Map getConfigCustomizeFeeMap(String name,String key) {
		 return getMapBySQL("select value as key,value as value from T_CustomizeFeeConfiguration where status='V' and   name='"+name+"' and key='"+key+"'");
	}
	
	public static String getMarketInfoInfoSettingValue(int customerid) {
		int infosetID=0;
		String value=getSystemSettingValue("SET_C_MARKETINFOSETID_FOR_GHPORTNUM_CONFIG");
		if(value!=null && !"".equals(value))infosetID=Integer.parseInt(value);
		return getStringBySQL("select code from t_bidimconfigsettingvalue sett,t_custmarketinfo mark where sett.settingid=mark.infosettingid and  sett.id=mark.infovalueid and mark.customerid="+customerid+" and mark.infosettingid="+infosetID);
	}
	
	public static int getSaCountByAcctIDAndStatusAndServiceID(int acctID,String status,int serviceId) {
		return getIntBySQL(" select count(*)  count from t_serviceaccount  t "
                          +" where t.serviceid="+serviceId+"  and t.status in ("+status+") "
                          +" and t.serviceaccountid "
                          +" in (select distinct cp.serviceaccountid from t_customerproduct cp where cp.status !='C' and cp.accountid="+acctID+")");
	}
	
	public static String getDistrictDescByID(int districtID) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String detailName="";
		try {
			String sql="Select Name From T_DISTRICTSETTING  Where belongto<> 0 connect by prior belongto =Id   start with Id=?";
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, districtID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				detailName=rs.getString("Name")+detailName;
			}
		} catch (Exception e) {
			LogUtility
					.log(Postern.class, LogLevel.WARN, "getDistrictDescByID", e);
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDistrictDescByID", e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					LogUtility.log(Postern.class, LogLevel.WARN,
							"getDistrictDescByID", e);
				}
			}
		}
		return detailName;
	}
	
	public static Collection getAcctFeeForPrint(String ai_no,int acctId,String createStartDate,String createEndDate,int txtFrom ,int txtTo){
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		if (ai_no !=null && !ai_no.equals("")){
			buf.append("select rownum, t.* from t_accountitem t where  t.ai_no in (").append(ai_no).append(")");
		} else{
			buf.append("select rownum, t.* from t_accountitem t where t.acctid =").append(acctId);
			if (createStartDate !=null && !createStartDate.equals("")){
				buf.append(" and t.createtime >=to_date('").append(createStartDate).append(" 00:00:00','YYYY-MM-DD HH24:mi:ss')");
			}
			if (createEndDate !=null && !createEndDate.equals("")){
				buf.append(" and t.createtime <=to_date('").append(createEndDate).append(" 23:59:59','YYYY-MM-DD HH24:mi:ss')");
			}
		}
		buf.append(" and rownum between ").append(txtFrom).append(" and ").append(txtTo);
		buf.append(" order by t.ai_no desc");
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				AccountItemDTO dto = DtoFiller.fillAccountItemDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAcctFeeForPrint", e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
	
	public static Collection getAcctPaymentRecordForPrint(String seqno,int acctId,String createStartDate,String createEndDate,int txtFrom ,int txtTo){
		Collection list = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer buf = new StringBuffer();
		if (seqno !=null && !seqno.equals("")){
			buf.append("select rownum,t.* from t_paymentrecord t where t.seqno in (").append(seqno).append(")");
		} else{
			buf.append("select rownum,t.* from t_paymentrecord t where  t.acctid =").append(acctId);
			if (createStartDate !=null && !createStartDate.equals("")){
				buf.append(" and t.createtime >=to_date('").append(createStartDate).append(" 00:00:00','YYYY-MM-DD HH24:mi:ss')");
			}
			if (createEndDate !=null && !createEndDate.equals("")){
				buf.append(" and t.createtime <=to_date('").append(createEndDate).append(" 23:59:59','YYYY-MM-DD HH24:mi:ss')");
			}
		}
		buf.append(" and rownum between ").append(txtFrom).append(" and ").append(txtTo);
		buf.append(" order by t.seqno desc");
		
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				PaymentRecordDTO dto = DtoFiller.fillPaymentRecordDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getAcctPaymentRecordForPrint", e);
			e.printStackTrace();
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
	
	public static String getSimpleDetailAddress(int addressID){
		AddressDTO addDTO = getAddressDtoByID(addressID);
		String detailAddress = WebUtil.NullToString(addDTO.getDetailAddress());
		String districtDesc =getSimpleDist(addDTO.getDistrictID());
		if(!"".equals(detailAddress))detailAddress = districtDesc+"◇"+detailAddress;
		return detailAddress;
	}
	
	private static String getSimpleDist(int districtID){
    	return getStringBySQL(" Select "
                             +" (select t.name from T_DISTRICTSETTING t where t.id =t1.belongto) || '◇' || name "
                             +" From T_DISTRICTSETTING  t1 "
                             +" where t1.id =" +districtID);
    }
	
	public static Collection getJqPrintConfigDTO(int belongto,String pageType){
		ArrayList list = new ArrayList();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con
					.prepareStatement("select * from t_jq_printconfig where belongto = ? and pagetype =? order by position_height,position_prior ");
			stmt.setInt(1, belongto);
			stmt.setString(2, pageType);
			rs = stmt.executeQuery();
			while (rs.next()) {
				JqPrintConfigDTO  dto =DtoFiller.fillJqPrintConfigDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out.println("getJqPrintConfigDTO exception:"
						+ e.getMessage());
				e.printStackTrace();
			}
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
	
	public static String getJqPrintConfigValue(String sql){
		return getStringBySQL(sql);
	}
	
	public static Collection getServiceAccountDto(String serviceAcctIds,String status){
		ArrayList list = new ArrayList();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql="select * from t_serviceAccount where serviceaccountid in ("+ serviceAcctIds+") " ;
			if (status !=null){
				sql = sql +" and status in ("+status+")";
			}		
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ServiceAccountDTO  dto =DtoFiller.fillServiceAccountDTO(rs);
				list.add(dto);
			}
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out.println("getServiceAccountDto exception:"
						+ e.getMessage());
				e.printStackTrace();
			}
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
	
	public static Collection getPrecinctIdColsByIds(String ids){
		ArrayList list = new ArrayList();
		Connection con = null;
		java.sql.PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			String sql ="select distinct t.id from t_districtsetting t connect by prior Id = belongto start with belongto in ("+ids+")";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String id = String.valueOf(rs.getInt("id"));
				list.add(id);
			}
		} catch (Exception e) {
			if (Constant.DEBUGMODE) {
				System.out.println("getPrecinctIdColsByIds exception:"
						+ e.getMessage());
				e.printStackTrace();
			}
		} finally {
			if (rs !=null) {
				try{
				   rs.close();
				}catch(SQLException e){	
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
	public static Map getSessionName() {
		return getMapBySQL("select t.name,t.name from t_billingcycle t where t.id >178 and t.ctype='B' order by t.id desc",true,true);
	}
	
	/**
	 * 丽江项目
	 *  获得开户时与用户许可证号相关的现有用户信息
	 */
	public static List getCheckCatvid(String catvid) {

		List forCheckCatvidList = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;
			if (catvid != null && !"".equals(catvid)) {
				sql = "select a.* from t_customer a where a.catvid='"+ catvid + "'";
			}
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CustomerDTO customerDTO = DtoFiller.fillCustomerDTO(rs);
				forCheckCatvidList.add(customerDTO);
			}
			rs.close();
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCheckDetailAddress", e);
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

		if (forCheckCatvidList.size() <= 0)
			forCheckCatvidList = null;

		return forCheckCatvidList;
	}
	/**
	 * 丽江项目
	 *  基本产品的到期时间
	 */
	public static String getBaseProductDateTo(String custType,int said) {

		String dateTo = ""; 
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;
			if("ZBG".equals(custType))
				sql = "select cpd.endtime dateto from t_customerproduct cpd,t_customer cu where cu.customerid=cpd.customerid and cpd.productid=10000110 and cpd.status <>'C' and cpd.serviceaccountid="+said;
			else 
				sql = "select cpn.dateto from t_customerproduct cpd,t_customer cu,t_cpcampaignmap cpp,t_customercampaign cpn where cu.customerid=cpd.customerid and cpd.productid=10000110 and cpp.custproductid=cpd.psid and cpp.ccid=cpn.ccid and cpn.status='V' and cpd.status <>'C' and cpd.serviceaccountid="+said;
				
			
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dateTo = rs.getDate("dateto").toString();
			}
			rs.close();
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getBaseProductDateTo", e);
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

		return dateTo;
	}

	/*
	 * 得到受理的软件产品
	 */
	public static String getCsiProduct(int csiid) {

		String productName = ""; 
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = "select distinct p.productname from t_csicustproductinfo csip, t_product p where csip.productid = p.productid and csip.referdeviceid = 0 and csiid ="+csiid;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if(!"".equals(productName))productName=productName+",";
				productName = productName+rs.getString("productname");
			}
			rs.close();
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCsiProduct", e);
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

		return productName;
	}
	/*
	 * 得到受理的软件产品包
	 */
	public static String getCsiPackage(int csiid) {

		String packageName = ""; 
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			String sql = null;
			
				sql = "select distinct ck.packagename from t_csicustproductinfo csip,t_productpackage ck where csip.referpackageid = ck.packageid and csip.referdeviceid=0 and csiid ="+csiid;
			
			
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if(!"".equals(packageName))packageName=packageName+",";
				packageName = packageName+rs.getString("packagename");
			}
			rs.close();
		} catch (Exception e) {
			LogUtility.log(Postern.class, LogLevel.WARN,
					"getCsiPackage", e);
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

		return packageName;
	}
	//获取发票号码 20140222
    /*
	 * 得到受理的软件产品包
	 */
    public static String getFapiaoSerialNo() {


        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String result="";
        try {
            con = DBUtil.getConnection();
            stmt = con.createStatement();
            String sql = null;

            sql = "select no from t_fapiaoserialno where rownum<2";
						
						
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
              result=  rs.getString("no");
            }
            rs.close();
        } catch (Exception e) {
            LogUtility.log(Postern.class, LogLevel.WARN,
                    "getFapiaoSerialNo", e);
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

        return result;
    }
}
