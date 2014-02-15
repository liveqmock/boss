package com.dtv.oss.web.util;

import java.sql.*;
import java.util.*;
import com.dtv.oss.util.*;
import com.dtv.oss.dto.*;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.util.Postern;

public class CustInfoTree {
	Connection con = null;
	
	//客户树上并列显示业务帐户的最大数。超出这个数量，则显示"业务帐户列表"
	private boolean isShowNumberSA;
	
	//树只显示10个业务帐户,通过业务帐户查询而查看的业务帐户ID
	private int selectSAID;
	//是否还有没有显示的业务帐户
	private boolean hasMoreSA;
	
	public boolean isHasMoreSA() {
		return hasMoreSA;
	}

	public void setHasMoreSA(boolean hasMoreSA) {
		this.hasMoreSA = hasMoreSA;
	}

	public int getSelectSAID() {
		return selectSAID;
	}

	public void setSelectSAID(int selectSAID) {
		this.selectSAID = selectSAID;
	}
	
	public boolean getIsShowNumberSA() {
		return isShowNumberSA;
	}

	public void setIsShowNumberSA(boolean isShowNumberSA) {
		this.isShowNumberSA = isShowNumberSA;
	}
	
	public void setConnection(Connection con) {
		this.con = con;
	}

	public CustInfoTree() {
		con = DBUtil.getConnection();
	}

	public CustInfoWrap getByID(int customerID ,int selectsaid) throws WebActionException{
		setSelectSAID(selectsaid);
		return getByID(customerID);
		
	}
	//if some exception occur, the function return CustInfoWrap with non-zero errorcode;
	//else return CustInfoWrap object with zero-value errorcode
	public CustInfoWrap getByID(int customerID) throws WebActionException{
		CustInfoWrap wrap = new CustInfoWrap();
		if (con == null) {
			con = DBUtil.getConnection();
			if (con == null) {
				/*wrap.setErrorCode(-1);
				 return wrap;
				 */
				throw new WebActionException("数据库连接错误！");
			}
		}

		try {
			//Customer base information
			String strSql = "select * from t_customer where customerid = " + customerID ;
//			String strSql = "select * from t_customer where customerid = " + customerID +
//			" and status <> 'C'" ;

			//System.out.println(strSql);
			populateBaseInfo(wrap, execSQL(strSql));

			con.close();
			con = null;
			strSql = "";

		} catch(Exception e) {
			e.printStackTrace();
			throw new WebActionException("一般错误！");
		} finally
		{
			if (con!=null)
			{
				try
				{
					con.close();
				}catch(Exception e)
				{

				}
				con=null;
			}


		}

		return wrap;
	}

	

	/**
	 * 侯瑞军2006-12-30修改，客户下帐户和业务帐户查询规则为，当客户状态为正常时，只能查看非取消状态的帐户和业务帐户，
	 * 当客户状态为取消时，可以查看所有状态的帐户和业务帐户
	 * @param wrap
	 * @param rs
	 * @throws java.sql.SQLException
	 * @throws WebActionException
	 */
	private void populateBaseInfo(CustInfoWrap wrap, ResultSet rs)
	throws java.sql.SQLException, WebActionException {

		CustomerDTO dto = new CustomerDTO();
		while (rs.next()) {
			dto=DtoFiller.fillCustomerDTO(rs);

		}
		
		rs.getStatement().close();
		rs.close();
		
		if (dto.getCustomerID() == 0) {
			//wrap.setErrorCode(-3);
			throw new WebActionException("该客户不存在！");
		}
		wrap.setBaseInfo(dto);
		String customerStatus=dto.getStatus();
		

		//Customer address information
		String strSql = "select * from t_address  " +
		"where addressid= " + dto.getAddressID() ;

		//System.out.println(strSql);
		populateAddrInfo(wrap, execSQL(strSql));

		//account information
		if (CommonKeys.CUSTOMER_STATUS_CANCEL.equals(customerStatus)) {
			strSql = "select * from t_account  " + "where customerid= "
					+ dto.getCustomerID();
		}else{
			strSql = "select * from t_account  " +
			"where status<>'C' and customerid= " + dto.getCustomerID() ;
		}

		//System.out.println(strSql);
		populateAcctInfo(wrap, execSQL(strSql));

		//account information
		//if (CommonKeys.CUSTOMER_STATUS_CANCEL.equals(customerStatus)) {
		//	strSql = "select * from t_serviceaccount a "
		//			+ "where a.customerid = " + dto.getCustomerID();
		//} else {
		//	strSql = "select * from t_serviceaccount a "
		//			+ "where a.customerid = " + dto.getCustomerID()
		//			+ " and a.status <> 'C'";
		//}
		strSql="";
		StringBuffer extroStrSql=new StringBuffer();
		if (CommonKeys.CUSTOMER_STATUS_CANCEL.equals(customerStatus)) {
			strSql = "select rownum myrow,a.* from t_serviceaccount a where a.customerid = " + dto.getCustomerID();
		} else {
			strSql = "select rownum myrow,a.* from t_serviceaccount a where a.customerid = " + dto.getCustomerID() + " and a.status <> 'C' ";
		}
		
		//  hjp  2008-3-18  不并列显示业务帐户，是显示业务帐户列表，进行查询。
		if(!isShowNumberSA){
			if(selectSAID != 0) 
				strSql = strSql + " and a.serviceaccountid="+selectSAID+" " ;
		}
		strSql = strSql + " order by a.serviceaccountid desc";

		populateHasMoreServiceAccount(wrap,execSQL("Select count(*) from ( " + strSql + " ) "));
		
		/**  hjp  2008-3-18 注释
		if(isHasMoreSA()){
			extroStrSql.append("select x.* from (").append(strSql).append(") x where x.myrow >");
			if(getSelectSAID()>0)
				extroStrSql.append("(select x.myrow-5 from (").append(strSql).append(") x where x.serviceaccountid=" +getSelectSAID()+ ") ");
			else
				extroStrSql.append(" 0 ");
			extroStrSql.append("and x.myrow<");
			if(getSelectSAID()>0)
				extroStrSql.append("(select x.myrow+6 from (").append(strSql).append(") x where x.serviceaccountid=" +getSelectSAID()+ ") ");
			else
				extroStrSql.append(" 11 ");
		}
		else **/
			extroStrSql.append(strSql);
		
		//System.out.println("客户信息树,业务帐户查找信息extroStrSql ====" + extroStrSql.toString());
		LogUtility.log(CustInfoTree.class, LogLevel.DEBUG, "客户信息树,业务帐户查找信息 extroStrSql=", extroStrSql.toString());
		populateServiceAccount(wrap, execSQL(extroStrSql.toString()));
		//populateServiceAccount(wrap, execSQL(strSql));
		strSql = "";
	}

	private void populateAddrInfo(CustInfoWrap wrap, ResultSet rs)
	throws java.sql.SQLException {

		AddressDTO addrDTO = new AddressDTO();
		while (rs.next()) {
			addrDTO=DtoFiller.fillAddressDTO(rs);
		}
		
		rs.getStatement().close();
		rs.close();
		
		wrap.setAddrInfo(addrDTO);

	}

	private void populateAcctInfo(CustInfoWrap wrap, ResultSet rs)
	throws java.sql.SQLException {

		Collection lstAcct = new ArrayList();

		while (rs.next()) {
			AcctInfoWrap acctWrap = new AcctInfoWrap();
			AccountDTO acctInfo = new AccountDTO();
			acctInfo=DtoFiller.fillAccountDTO(rs);
			acctWrap.setAcctInfo(acctInfo);
			lstAcct.add(acctWrap);
		}
		
		rs.getStatement().close();
		rs.close();
		
		//Customer base information
		String strSql = "";

		Iterator iterator = lstAcct.iterator();
		while (iterator.hasNext())
		{
			AcctInfoWrap acctWrap = (AcctInfoWrap)iterator.next();
			AccountDTO acctInfo = acctWrap.getAcctInfo();
			strSql = "select * from t_address  " +
			"where addressid= " + acctInfo.getAddressID() ;

			populateBillAddrInfo(acctWrap, execSQL(strSql));


		}

		wrap.setAccountList(lstAcct);
	}

	private void populateBillAddrInfo(AcctInfoWrap wrap, ResultSet rs)
	throws java.sql.SQLException {

		AddressDTO addrDTO = new AddressDTO();
		while (rs.next()) {
			addrDTO=DtoFiller.fillAddressDTO(rs);

		}
		
		rs.getStatement().close();
		rs.close();
		wrap.setBillAddrInfo(addrDTO);

	}

	private void populateServiceAccount(CustInfoWrap wrap , ResultSet rs)
	throws java.sql.SQLException {

		Collection col = new ArrayList();
		ServiceAccountDTO dto;
		while (rs.next()) {
			
			/** hjp  2008-3-18 注释
			if(!isHasMoreSA()){
				if(rs.getInt("myrow")>10){
					setHasMoreSA(true);
				}
			}
			**/
			
			dto=DtoFiller.fillServiceAccountDTO(rs);
			
			dto.setDescription(getSADescForTreeBySADTO(dto));
			col.add(dto);
		}

		rs.getStatement().close();
		rs.close();
		wrap.setServiceAccountList(col);
		wrap.setHasMoreSA(isHasMoreSA());
	}


	private void populateHasMoreServiceAccount(CustInfoWrap wrap , ResultSet rs)throws java.sql.SQLException {
		if (rs.next()) {
				if(rs.getInt(1)>10){
					setHasMoreSA(true);
				}
			}
		rs.getStatement().close();
		rs.close();
		
		wrap.setHasMoreSA(isHasMoreSA());
	}


	private ResultSet execSQL(String strSql) {
		Statement stmt;
		ResultSet rs;

		if (con == null) {
			setConnection(DBUtil.getConnection());
		}

		try {
			//创建一个JDBC声明
			stmt = con.createStatement();

			//查询记录
			rs = stmt.executeQuery(strSql);
			
			System.out.println("CustInfoTree:::strSql:::"+strSql);

		} catch (SQLException e) {
			System.out.println("DBConnection error");
			e.printStackTrace();
			return null;
		}

		return rs;
	}
	
	//取得客户树上的业务帐户显示信息
	private String getSADescForTreeBySADTO(ServiceAccountDTO dto) {
		
		//1．   模拟电视业务 :atv.gif  6
		//2．   数字电视业务 :dtv.gif  1
		//3．   交互电视业务 :itv.gif  5
		//4．   宽带接入业务 :band.gif 2
		//5．   语音业务	  :voip.gif 3
		//6．   宽带增值业务 :inc.gif  4
		
		String description = "业务帐户";
		if(dto.getServiceID()==1){
			byte buf[] =(byte[])(Postern.getServiceIconsCache()).get("dtv");
			if(buf!=null)        //判断是否有图标
			description = description + "<img src=\"img_download.screen?action=dtv\" width=\"16\" height=\"16\">";
			//description = description + "<img src=\"img/dtv.gif\" width=\"13\" height=\"13\">";
		}	
		if(dto.getServiceID()==6){
			byte buf[] =(byte[])(Postern.getServiceIconsCache()).get("atv");
			if(buf!=null)        //判断是否有图标
			description = description + "<img src=\"img_download.screen?action=atv\" width=\"16\" height=\"16\">";
			//description = description + "<img src=\"img/atv.gif\" width=\"13\" height=\"13\">";
		}
		if(dto.getServiceID()==5){
			byte buf[] =(byte[])(Postern.getServiceIconsCache()).get("itv");
			if(buf!=null)        //判断是否有图标
			description = description + "<img src=\"img_download.screen?action=itv\" width=\"16\" height=\"16\">";
			//description = description + "<img src=\"img/itv.gif\" width=\"13\" height=\"13\">";
		}
		if(dto.getServiceID()==2){
			byte buf[] =(byte[])(Postern.getServiceIconsCache()).get("band");
			if(buf!=null)        //判断是否有图标
			description = description + "<img src=\"img_download.screen?action=band\" width=\"16\" height=\"16\">";
			//description = description + "<img src=\"img/band.gif\" width=\"13\" height=\"13\">";
		}
		if(dto.getServiceID()==3){
			byte buf[] =(byte[])(Postern.getServiceIconsCache()).get("voip");
			if(buf!=null)        //判断是否有图标
			description = description + "<img src=\"img_download.screen?action=voip\" width=\"16\" height=\"16\">";
			//description = description + "<img src=\"img/voip.gif\" width=\"13\" height=\"13\">";
		}
		if(dto.getServiceID()==4){
			byte buf[] =(byte[])(Postern.getServiceIconsCache()).get("inc");
			if(buf!=null)        //判断是否有图标
			description = description + "<img src=\"img_download.screen?action=inc\" width=\"16\" height=\"16\">";
			//description = description + "<img src=\"img/inc.gif\" width=\"13\" height=\"13\">";
		}
		
		//description = description + Postern.getServiceNameByID(dto.getServiceID());
		description = description +"<font color=\"#FFFFFF\" style=\"font-weight: normal\" >";
		description = description +"(";
		if(!"".equals(dto.getUserType()) && !"null".equals(dto.getUserType()) && dto.getUserType()!=null )
			description = description + Postern.getCommomsettingData_ValueByKeyAndName("SET_C_USER_TYPE",dto.getUserType()) +",";
		description = description +	Postern.getCommomsettingData_ValueByKeyAndName("SET_C_SERVICEACCOUNTSTATUS",dto.getStatus());
		description = description + ")";
		description = description + "<br>";
		description = description + "</font>";
		//description = description + "<font size=\"1\" style=\"font-weight:normal\" color=\"#CCFFFF\" >";
		description = description + "<font color=\"#FFFFFF\" style=\"font-weight: normal\" >";
		
		String SCSerialNo = Postern.getSCSerialNoByServiceAccountID(dto.getServiceAccountID());
		if(!"".equals(SCSerialNo) && !"null".equals(SCSerialNo) && SCSerialNo!=null ){
			DeviceModelDTO  DMdto = Postern.getDevice2ModelDTOByServiceAccountID(dto.getServiceAccountID());
			//description = description + Postern.getDevice2ClassByServiceAccountID(dto.getServiceAccountID()) + ":" + String.valueOf(Postern.getSCSerialNoByServiceAccountID(dto.getServiceAccountID())).substring(DMdto.getSerialLength()-4,DMdto.getSerialLength()) + " ";
			if((!"N".equals(DMdto.getViewInCdtFlag())) &&DMdto.getViewInCdtFlag()!=null && !"null".equals(DMdto.getViewInCdtFlag()) && !"".equals(DMdto.getViewInCdtFlag())){
				description = description + Postern.getDevice2ClassByServiceAccountID(dto.getServiceAccountID()) + "=" ;
				int from = 0;
				int to = 0;
				int length = 0;
				from = DMdto.getKeySerialNoFrom();
				to = DMdto.getKeySerialNoTo();
				length = SCSerialNo.length();
				
				if (from < 0 || from >= length)          //不合理的数据，全部显示SerialNo
				    from = 0;
				if (to <= 0 || to > length)				//不合理的数据，全部显示SerialNo
				    to = length;
				if (from >= to){
				    from = 0; 
				    to = length;
				}
				description = description + SCSerialNo.substring(from,to) + "<br>";
			}
		}
		
		String STBSerialNo = Postern.getSTBSerialNoByServiceAccountID(dto.getServiceAccountID());
		if(!"".equals(STBSerialNo) && !"null".equals(STBSerialNo) && STBSerialNo!=null ){
			DeviceModelDTO  DMdto = Postern.getDevice1ModelDTOByServiceAccountID(dto.getServiceAccountID());
			//description = description + Postern.getDevice1ClassByServiceAccountID(dto.getServiceAccountID()) + ":" + String.valueOf(Postern.getSTBSerialNoByServiceAccountID(dto.getServiceAccountID())).substring(DMdto.getSerialLength()-4,DMdto.getSerialLength()) + "<br>";
			if((!"N".equals(DMdto.getViewInCdtFlag())) &&DMdto.getViewInCdtFlag()!=null && !"null".equals(DMdto.getViewInCdtFlag()) && !"".equals(DMdto.getViewInCdtFlag())){
				description = description + Postern.getDevice1ClassByServiceAccountID(dto.getServiceAccountID()) + "=" ;
				int from = 0;
				int to = 0;
				int length = 0;
				from = DMdto.getKeySerialNoFrom();
				to = DMdto.getKeySerialNoTo();
				length = STBSerialNo.length();
				
				if (from < 0 || from >= length)          //不合理的数据，全部显示SerialNo
				    from = 0;
				if (to <= 0 || to > length)				//不合理的数据，全部显示SerialNo
				    to = length;
				if (from >= to){
				    from = 0; 
				    to = length;
				}
				description = description + STBSerialNo.substring(from,to) + "<br>";
			}
		}
		if(!"".equals(dto.getServiceCode()) && !"null".equals(dto.getServiceCode()) && dto.getServiceCode()!=null )
			description = description + "号码="+ dto.getServiceCode();
		description = description +"</font>";
		
		return description;
	}
	
}
