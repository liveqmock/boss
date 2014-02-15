/*
 * Created on 2005-12-7
 *
 * @author whq
 * 
 * ���¼�����һ���Է���
 */
package com.dtv.oss.service.util.ImmediateFee;

import java.sql.*;
import java.util.*;
  
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.dto.*;
import com.dtv.oss.service.ServiceException;
//import com.dtv.oss.service.util.BillingObject;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
public class ImmediateFeeCalculator4EventImpl implements java.io.Serializable, ImmediateFeeCalculator4Event {
    private static Class clazz = ImmediateFeeCalculator4EventImpl.class;
    private static final String CNT_TYPE_ACCOUNT = "A";
    private static final String CNT_TYPE_CAMPAIGN = "M";
    
    //definition for CntType field in table T_BrCondtion
    private static final String CNT_TYPE_CUSTOMER = "C";
    
    //definition for status field in talbe T_PaymentRecord
    private static final String SET_F_FTSTATUS_VALID = "V";
    
    //definition for ForcedDepositFlag in table T_AccountItem
    private static final String SET_F_FORCEDDEPOSITFLAG_YES = "Y";
    private static final String SET_F_FORCEDDEPOSITFLAG_NO = "N";
    
    //definition for SET_F_PREPAYMENTTYPE
    private static final String SET_F_PREPAYMENTTYPE_C	= "C";		//�ֽ�
    private static final String SET_F_PREPAYMENTTYPE_T	= "T";		//�������
    
    public static void main(String[] args) throws Exception{
    }
    
    public ImmediateFeeList getCampaignFee(Collection billingObjectCol)
    throws SQLException
	{
        System.out.println("Log out getCampaignFee Message��");
		Connection dbConn = null;
		Statement queryStrStmt = null;
		ResultSet rs = null;

		if (ImmediateFeeCalculator.DEBUGMODE)
		    dbConn = ImmediateFeeCalculator.getDirectJdbcConnection();
		else
		    dbConn = DBUtil.getConnection();
		if (dbConn == null) throw new RuntimeException("DB Connection error.");
		
        if(billingObjectCol == null) return null;
        try{
		    ImmediateFeeList immediateFeeList = new ImmediateFeeList();
		
		    Iterator itBO = billingObjectCol.iterator();
	        while(itBO.hasNext())
	        {

		        BillingObject bo = (BillingObject)itBO.next();
		        int custID = bo.getCustomerID();
		        int acctID = bo.getAccountID();
		        int saID = bo.getServiceAccountID();
		        String acctType = bo.getAcctType();
		        String custType = bo.getCustType();
		        Collection campaignCol = bo.getCampaignCol();
		        if(campaignCol != null)
	    	    {
		    	    Iterator itCampaign = campaignCol.iterator();
		    	    while(itCampaign.hasNext())
		    	    {
		    	        int campaignID = ((Integer)itCampaign.next()).intValue();
		    	        String sql = "select bp.campaignid,bp.acctItemTypeID ,bp.amount,bp.feeType,bp.bundleprepaymentplanid,c.rfbillingcycleflag,c.bundlePrepaymentFlag from  T_BundlePrepayment bp,t_Campaign c where bp.campaignid = c.campaignid and c.status = 'V' and sysdate between c.datefrom and c.dateto and c.campaignid = " + campaignID;

		    	        queryStrStmt = dbConn.createStatement();
    	                rs = queryStrStmt.executeQuery(sql);
    	                String strAcctItemTypeID = "";
    	                String strFeeType = "";
    	                int fspID = 0;
    	                String rfBillingCycleFlag = "";
    	                double Amount = 0;
    	                String BundlePrepaymentFlag = "";
	                    if(rs.next())
	                    {
	                        BundlePrepaymentFlag = rs.getString("bundlePrepaymentFlag");
	                        strAcctItemTypeID = rs.getString("AcctItemTypeID");
	                        strFeeType = rs.getString("FeeType");
	                        Amount = rs.getDouble("Amount");
	                        strFeeType = rs.getString("FeeType");
	                        fspID = rs.getInt("bundleprepaymentplanid");
	                        rfBillingCycleFlag = rs.getString("rfbillingcycleflag");
	                        if(BundlePrepaymentFlag.compareTo("Y") == 0)
	                        {	            
		                        ReturnFee ReturnFeeInfo = new ReturnFee();
		                        ReturnFeeInfo.setAcctItemTypeID(strAcctItemTypeID);
	                            ReturnFeeInfo.setAcctItemTypeName(getAcctItemTypeName(dbConn, strAcctItemTypeID));
		                        ReturnFeeInfo.setFeeType(strFeeType);
		                        ReturnFeeInfo.setFeeName(getFeeTypeName(dbConn, strFeeType));
		                        ReturnFeeInfo.setValue(Amount);
		                        ReturnFeeInfo.setStatus("W");
		                        ReturnFeeInfo.setAccountID(acctID);
		                        ReturnFeeInfo.setCustomerID(custID);
		                        ReturnFeeInfo.setServiceAccountID(saID);
		                        ReturnFeeInfo.setRfBillingcycleFlag(rfBillingCycleFlag);
		                        ReturnFeeInfo.setFspID(fspID);
		                        ReturnFeeInfo.setCCID(campaignID);
		                        if (strFeeType.equals("P"))
	 	                            ReturnFeeInfo.setForcedDepositFlag(ImmediateFeeCalculator4EventImpl.SET_F_FORCEDDEPOSITFLAG_YES);
                                System.out.println(ReturnFeeInfo);
		                        addReturnFeeToList(immediateFeeList,ReturnFeeInfo);
		                    }
	                     }
		    	    }
    		    }
			}
//			convert(immediateFeeList);
		    return immediateFeeList;
		}        
	    finally {
	    
	    if(queryStrStmt != null){
		    try{
		        queryStrStmt.close();
		       } catch(SQLException e){}
	         }
        if(rs != null){
	    try{
	        rs.close();
	       } catch(SQLException e){}
         }
		if(dbConn != null){
		    try{
		        dbConn.close();
		       } catch(SQLException e){}
	         }
	    }
	}
   
      public AccountItemDTO getImmediateFee4CP(int eventClass, 
			 String eventReason,
			 CustomerDTO customerDto) throws SQLException
      {
      	 Connection dbConn = null;
	 if (ImmediateFeeCalculator.DEBUGMODE)
	     dbConn = ImmediateFeeCalculator.getDirectJdbcConnection();
	 else
	     dbConn = DBUtil.getConnection();
	 if (dbConn == null) throw new RuntimeException("DB Connection error.");
	 if (customerDto == null) throw new IllegalArgumentException("����customerDto����.");
	 String custType = customerDto.getCustomerType();
		
	//�ж���ڲ�������ȷ�� (EventClass/CustType�������� )
	if((eventClass == 0) || (custType.equals(""))){
	    throw new IllegalArgumentException("����eventClass/custType����.");
	}
		
		//׼�����ز���
	AccountItemDTO aiDto = null;

	try{
	       //ֻ�����������,�������Żݷ��ʵ�����

	    return getBasicFeeRate(dbConn, eventClass, eventReason, custType);
	} finally {
	    if(dbConn != null){
	        try{
	            dbConn.close();
	        } catch(SQLException e){}
	    }
	}  
      }

    /*
�����¼����ԼƷѶ�����мƷ�
created by liudi
	//  ����  EventClass / EventReason / CustType / AcctType / BillingObjectList ����ҵ���������漰����һ���Է���
	//  EventClass = 120 (�ͻ�����) ʱ, BillingObjectList ������,������ͻ���Ʒ/��Ӫ�̲�Ʒ�޹�
	//  EventClass = 121 (�ͻ�Ǩ��) ʱ, BillingObjectList ������,������ͻ���Ʒ/��Ӫ�̲�Ʒ�޹�
	//  EventClass = 312 (��Ʒ��ͣ) ʱ, BillingObjectList ������,������ͻ���Ʒ/��Ӫ�̲�Ʒ�޹�
	//  EventClass = 313 (��Ʒ��ͣ�ָ�) ʱ, BillingObjectList ������,������ͻ���Ʒ/��Ӫ�̲�Ʒ�޹�
	//  EventClass = 113 (�ͻ�����) ʱ, BillingObjectList ������,������ͻ���Ʒ/��Ӫ�̲�Ʒ�޹�
	//  EventClass = 114 (�ͻ��˻�) ʱ, BillingObjectList ������,������ͻ���Ʒ/��Ӫ�̲�Ʒ�޹�
	//  EventClass = 110 (�ͻ�����) ʱ, BillingObjectList ��Ҫ��,��������Ӫ�̲�Ʒ�й�
	//  EventClass = 310 (�����Ʒ(����Ӳ��/���)) ʱ, BillingObjectList ��Ҫ��,��������Ӫ�̲�Ʒ�й�
	//  EventClass = 315 (��Ʒ����) ʱ, BillingObjectList ��Ҫ��,��������Ӫ�̲�Ʒ�й�
	//  EventClass = 316 (��Ʒ����) ʱ, BillingObjectList ��Ҫ��,��������Ӫ�̲�Ʒ�й�
	//  EventClass = 801 (�ͻ�Ԥ���ʱ�μӴ����) ʱ, BillingObjectList ��Ҫ��,��������Ӫ�̲�Ʒ�й� 
	//  EventClass = 15  (�û�����)   
	//  EventClass = 16  ���Ϸ�
*/
	public ImmediateFeeList getImmediateFee(int eventClass, String eventReason, Collection billingObjectCol) 
	throws SQLException
	{
        System.out.println("Log out getImmediateFee Message��");
		Connection dbConn = null;
		if (ImmediateFeeCalculator.DEBUGMODE)
		    dbConn = ImmediateFeeCalculator.getDirectJdbcConnection();
		else
		    dbConn = DBUtil.getConnection();
		if (dbConn == null) throw new RuntimeException("DB Connection error.");
		
		if(isValidEventClass(eventClass) == false)
		    throw new IllegalArgumentException("�����eventClass��Ч, �Ʒѽӿڲ�֧�ָ��¼���eventClassΪ��"+eventClass);

        if(billingObjectCol == null) throw new IllegalArgumentException("�����billingObjectColΪ��, �Ʒѽӿ��޷�����");
        try{
        
        Collection AllcampaignCol = getAllCampaign(billingObjectCol);
		ImmediateFeeList immediateFeeList = new ImmediateFeeList();
		
		Iterator itBO = billingObjectCol.iterator();
	    while(itBO.hasNext())
	    {
		    BillingObject bo = (BillingObject)itBO.next();
		    int custID = bo.getCustomerID();
		    int acctID = bo.getAccountID();
		    int saID = bo.getServiceAccountID();
		    String acctType = bo.getAcctType();
		    String custType = bo.getCustType();
		    Collection campaignCol = bo.getCampaignCol();
		    String contractNo = bo.getContractNo();
		    int groupBargainID = bo.getGroupBargainID();
		    int destProductID = bo.getDestProductID();

			String userType = bo.getUserType();
    		String catvTermType = bo.getCATVType();
    		String cableType = bo.getCableType();
			String biDirectionFlag = bo.getBiDirectionFlag();
			int orgPortNum = bo.getOrgPortNum();
			int destPortNum = bo.getDestPortNum();

System.out.println("Log out getImmediateFee Message:" + bo);
		    if(isEventClassNotRelateWithCampaign(eventClass))
		    {
		        //�������Ƿ��в�Ʒ
System.out.println("Start Billing NotRelateWithCampaign " + eventClass );
		     	ReturnFee basicReturnFee = getBasicFeeRate(dbConn, eventClass, eventReason, custType, acctType,userType, 0, 0, 0,catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
		     	if(basicReturnFee != null)
		     	{
		     	    basicReturnFee.setAccountID(acctID);
		     	    basicReturnFee.setCustomerID(custID);
		     	    basicReturnFee.setServiceAccountID(saID);
System.out.println("ReturnFee = " + basicReturnFee );			     	    
		    	    addReturnFeeToList(immediateFeeList,basicReturnFee);
		    	}
		    	else
		    	    System.out.println("Do Not Find Fee!");
				if( eventClass == 161 || eventClass == 162 || eventClass == 163 
					|| eventClass == 164 || eventClass == 165 || eventClass == 166 
					|| eventClass == 167 || eventClass == 111 || eventClass == 112
                                        || eventClass == 113 || eventClass == 114 || eventClass == 120
                                        || eventClass == 121 || eventClass == 168 ) //16X �ͻ�����¼���ֻ��Ҫ�Ʒ�һ��
					break;
		    }
		    else if(bo.sHasPackage.compareTo("N") == 0)
		    {
		        //
		    	if(eventClass == 5)
		    	   	caculator5(dbConn,5,bo,immediateFeeList);
				else if(eventClass == 110)
			    {
			    	    	//�����¼�
			        calculator4OpenAccountFor110( custID,acctID,saID,110 , eventReason , 
			    	    	                       custType , dbConn , 
			    	    	                       immediateFeeList , 
			    	    	                       AllcampaignCol,
			    	    	                       groupBargainID,
			    	    	                       contractNo,
			    	    	                       acctType,
                                                   userType,catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
			    	    	                       
//                    caculator4Campaign(eventClass,eventReason,custID,acctID,saID,custType,acctType,dbConn,campaignCol,immediateFeeList);
			    	break;
				}
				else if( (eventClass == 15) ||(eventClass == 16)) //���˷ѡ����Ϸ�
				{
					calculatorFor1516(eventClass,eventReason,bo,dbConn,immediateFeeList,AllcampaignCol,0,"");
				}
		    }
		    else
		    {
				if(eventClass == 5)
		    	   	caculator5(dbConn,5,bo,immediateFeeList);
			    else if(eventClass == 110)
			    {
			    	    	//�����¼�
			        calculator4OpenAccountFor110( custID,acctID,saID,110 , eventReason , 
			    	    	                       custType , dbConn , 
			    	    	                       immediateFeeList , 
			    	    	                       AllcampaignCol,
			    	    	                       groupBargainID,
			    	    	                       contractNo,
			    	    	                       acctType,
                                                   userType,catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
			    	    	                       
//                    caculator4Campaign(eventClass,eventReason,custID,acctID,saID,custType,acctType,dbConn,campaignCol,immediateFeeList);
			    	break;
			    }
			    else if (eventClass == 13)
			    {
			    	//�����ѼƷ�
//			    	����
//			    	calculator413(13,dbConn,immediateFeeList,campaignCol);
			    }
			    else if (eventClass == 310)
			    {
			        calculator310(dbConn,310,bo,immediateFeeList,AllcampaignCol);
			        
			    	//�����Ʒ���򣬲�֧��
			    }
			    else if (eventClass == 610)
			    {
			       	//���ʻ������豸 OK
			    	calculator4610ByAccount(dbConn,610,eventReason,bo,immediateFeeList,AllcampaignCol);
			    }
			    else if( eventClass == 2 )
			    {
			       	//��װ���Է�
			    	calculator42161ByAccount(dbConn,2,eventReason,bo,immediateFeeList);
			    	break;
			    }
			    else if( eventClass == 161)
			    {
			    	//�ͻ�ҵ���˻�����
			        calculator42161ByAccount(dbConn,161,eventReason,bo,immediateFeeList);
					break;
			    }
			    else if(eventClass == 315 || eventClass == 316 || eventClass == 611)
			    {
					HashMap hm = bo.getPackage2ProductMap();
			       	//��Ʒ������ ��֧��
					int productID = 0;
					Integer packageID = new Integer(0);
					Iterator itPackage = hm.keySet().iterator();            
        			if(itPackage.hasNext())
        			{
            			packageID = (Integer)itPackage.next();
            			Collection productCol = (Collection)hm.get(packageID);
            			if(productCol == null) continue;            
		    			Iterator itProduct = productCol.iterator();
		    			if(itProduct.hasNext())
		    			{
							productID = ((Integer)itProduct.next()).intValue();
						}
						else
							continue;
					}
					else
						continue;

					ReturnFee basicReturnFee = getBasicFeeRate(dbConn, eventClass, eventReason, custType, acctType,userType, productID, destProductID, packageID.intValue(),catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
		     		if(basicReturnFee != null)
		     		{
		     	    	basicReturnFee.setAccountID(acctID);
		     	    	basicReturnFee.setCustomerID(custID);
		     	    	basicReturnFee.setServiceAccountID(saID);
System.out.println("ReturnFee = " + basicReturnFee );
		    	    addReturnFeeToList(immediateFeeList,basicReturnFee);
		    		}
			    }
			    else if(eventClass == 308) //�ײ�ת��
			    {
					caculate4308(dbConn,308,eventReason,bo,immediateFeeList);
				}
				else if( (eventClass == 15) ||(eventClass == 16)) //���˷ѡ����Ϸ�
				{
					calculatorFor1516(eventClass,eventReason,bo,dbConn,immediateFeeList,AllcampaignCol,0,"");
				}
			    else 
			    {
			         //��֧��
			    }
			}
		}
//        convert(immediateFeeList);
		return immediateFeeList;
	    }finally {
		if(dbConn != null){
		    try{
		        dbConn.close();
		       } catch(SQLException e){}
	         }
	    }
	}

	private void caculate4308(Connection  dbConn,int eventClass,String eventReason,BillingObject bo,ImmediateFeeList immediateFeeList)throws SQLException
	{
System.out.println("Start billing 308 event");
		if(eventClass != 308) return;
		if(bo == null ) return;
		if(immediateFeeList == null) return;

		String custType = bo.getCustType();
		String acctType = bo.getAcctType();
		String userType = bo.getUserType();
		int orgPackageID = bo.getOrgPackageID();
		int destPackageID = bo.getDestPackageID();

		String queryStr = " select EventReason,CustType,AcctType,UserType,ProductID,DestProductID,PackageID,AcctItemTypeID,Value,CATVTERMTYPE,CABLETYPE,BiDrectionFlag,OldPortNo,NewPortNo from T_BillingRuleItem "
                   + " where EventClass = " + eventClass
                   + " AND packageID = " + orgPackageID
				   + " AND DestPackageID = " + destPackageID
				   + " AND Status = 'V'"
                   + " and sysdate between ValidFrom and ValidTo "
                   + " and Value is not null "
                   + " and AcctItemTypeID is not null "
                   + " and NVL(ProductID , 0) = 0 " 
				   + " order by value asc ";
System.out.println(queryStr);
		ReturnFee rf = getBestMatching(dbConn, eventClass, eventReason, custType, acctType,userType, 0, 0, orgPackageID, queryStr,"","","",0,0);
System.out.println(rf);
		if( rf != null )
			addReturnFeeToList(immediateFeeList,rf);
	}

    private void calculator310(Connection dbConn,int eventClass,BillingObject bo,ImmediateFeeList immediateFeeList,Collection campaignCol)throws SQLException
    {
        if(eventClass != 310) return;
        if(bo == null ) return;
        if(immediateFeeList == null) return;
        
        int accountID = bo.getAccountID();
    	int customerID = bo.getCustomerID();
    	int saID = bo.getServiceAccountID();
    	String acctType = bo.getAcctType();
        String custType = bo.getCustType();
		String userType = bo.getUserType();
        int destProductID = bo.getDestProductID();
        String contractNo = bo.getContractNo();
	    String catvTermType = bo.getCATVType();
    	String cableType = bo.getCableType();
		String biDirectionFlag = bo.getBiDirectionFlag();
		int orgPackageID = bo.getOrgPackageID();
		int destPackageID = bo.getDestPackageID();
		int orgPortNum = bo.getOrgPortNum();
		int destPortNum = bo.getDestPortNum();

        
        HashMap hm = bo.getPackage2ProductMap();
        if(hm == null) return;
        
        Iterator itPackage = hm.keySet().iterator();
            
        while(itPackage.hasNext())
        {
            Integer packageID = (Integer)itPackage.next();
            Collection productCol = (Collection)hm.get(packageID);
            if(productCol == null) continue;
            
		    Iterator itProduct = productCol.iterator();
		    while(itProduct.hasNext())
		    {
		        int productID = ((Integer)itProduct.next()).intValue();
                Collection returnFee = getTotalFeeRate(dbConn, eventClass,
            					   "", custType, 
            					   acctType,userType, productID,0,packageID.intValue(),
            					   campaignCol,0, "",catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
            					   
            	 
                 if(returnFee != null)
                 {
                    Iterator itRF = returnFee.iterator();
                     while(itRF.hasNext())
                     {
                        ReturnFee rf = (ReturnFee)itRF.next();
                        rf.setAccountID(accountID);
                        rf.setCustomerID(customerID);
                        rf.setServiceAccountID(saID);
                        addReturnFeeToList(immediateFeeList,rf);
                      }
                 }
             }
        }
        
        
    }
    private void caculator5(Connection dbConn,int eventClass,BillingObject bo,ImmediateFeeList immediateFeeList)throws SQLException
    {
        //����ǿ��Ԥ�����
        if(eventClass != 5) return;
        if(bo == null ) return;
        if(immediateFeeList == null) return;
        
        int accountID = bo.getAccountID();
    	int customerID = bo.getCustomerID();
    	int saID = bo.getServiceAccountID();
    	String acctType = bo.getAcctType();
        String custType = bo.getCustType();
		String userType = bo.getUserType();
        int destProductID = bo.getDestProductID();
        String contractNo = bo.getContractNo();
        Collection campaignCol = bo.getCampaignCol();
        if(campaignCol == null) return;
        
        Iterator itCampaign = campaignCol.iterator();
        
        HashMap hm = bo.getPackage2ProductMap();
        
        while(itCampaign.hasNext()) // �Դ����ѭ��
        {
            int campaignID = ((Integer)itCampaign.next()).intValue();
            
			if(!BusinessUtility.isACampaign(campaignID)) continue; //������Ǵ����,��ô����.
			
			if(hm == null)//���û�пͻ���Ʒ����ô���ʻ��Żݼ��㡣
			{
			
				String sql = "Select EventReason,CustType,AcctType,UserType,ProductID , DestProductID , PackageID , AcctItemTypeID , Value "
                   + " from T_BillingRuleItem "
                   + " where  EventClass = 5 "
                   + " and Status = 'V' "
                   + " and sysdate between ValidFrom and ValidTo "
                   + " and Value is not null "
                   + " and AcctItemTypeID is not null "
                   + " and NVL(ProductID , 0) = 0 " 
                   + " and CampaignID = " + campaignID
                   + " order by value asc";

	            ReturnFee rf = getBestForceDeposit(dbConn,sql,custType,acctType,userType,0,0);
	            if(rf != null)
	            {
	                rf.setAccountID(accountID);
	                rf.setCustomerID(customerID);
	                rf.setServiceAccountID(saID);
	                rf.setCCID(campaignID);
	                addReturnFeeToList(immediateFeeList,rf);
	            }
			}
			else//����пͻ���Ʒ����ô�Կͻ���Ʒ�����Żݡ�
			{
				//���ڲ�Ʒ���������ۣ���˸ü��������
				Iterator itHS = hm.keySet().iterator();
				while(itHS.hasNext()) //ȡ�ò�Ʒ��
				{
					Integer packageID = (Integer)itHS.next();
					Collection productCol = (Collection)hm.get(packageID);
					Iterator itProduct = productCol.iterator();
					while(itProduct.hasNext()) //ȡ�ð��µĲ�Ʒ
					{
						Integer productID = (Integer)itProduct.next();
						String sql = "Select EventReason,CustType,AcctType,UserType,ProductID , DestProductID , PackageID , AcctItemTypeID , Value "
			                   + " from T_BillingRuleItem "
			                   + " where  EventClass = 5 "
			                   + " and Status = 'V' "
			                   + " and sysdate between ValidFrom and ValidTo "
			                   + " and Value is not null "
			                   + " and AcctItemTypeID is not null "
			                   + " and NVL(ProductID , 0) = " +  productID
						 	   + " and ( Nvl(packageID,0) = 0 or packageID = " + packageID + ")"							   
			                   + " and CampaignID = " + campaignID
			                   + " order by value asc";
System.out.println(sql);
				            ReturnFee rf = getBestForceDeposit(dbConn,sql,custType,acctType,userType,0,0);
				            if(rf != null)
				            {
				                rf.setAccountID(accountID);
				                rf.setCustomerID(customerID);
				                rf.setServiceAccountID(saID);
				                rf.setCCID(campaignID);
				                addReturnFeeToList(immediateFeeList,rf);
				            }
					}
				}
			}			
        }
    }
    
    private ReturnFee getBestForceDeposit(Connection dbConn,String sql,String custType,String acctType,String userType,int packageID,int productID)
    throws SQLException 
    {
        int MostPerfectPower = 0;
		double MostPerfectValue = 0;
		String MostPerfectAcctItemTypeID = "";
	
		//��ʼ����ǰƥ��ֵ
		int CurrentPower = 0;
		String CurrentCustType = "";
		String CurrentAcctType = "";
		String CurrentAcctItemTypeID = "";
		String CurrentUserType = "";
		int CurrentpackageID = 0;
		int CurrentProductID = 0;
		java.math.BigDecimal CurrentValue = null;
		
		Statement queryStrStmt = null;
		ResultSet rs = null;
		
		try{
    		queryStrStmt = dbConn.createStatement();
    		rs = queryStrStmt.executeQuery(sql);
	
            //�������Ƚ�ÿ�����ʹ����ƥ��̶�
            while(rs.next())
            {
    			//��ȡ�����ֶε�ȡֵ
    			CurrentValue = rs.getBigDecimal("Value");
    			CurrentAcctItemTypeID = rs.getString("AcctItemTypeID");
    			CurrentProductID = rs.getInt("ProductID");
    			//����Ȩ�س�ʼֵ
    			CurrentPower = 0;
    
    			CurrentpackageID = rs.getInt("PackageID");

				if(CurrentProductID > 0)
				{
					if(productID > 0)
					{
						if(CurrentProductID == productID)
							CurrentPower += 32;
						else
							continue;
					}
				}

    			if( CurrentpackageID >0 )
    			{
					if(packageID > 0)
    			    {
						if(packageID == CurrentpackageID)
    			        	CurrentPower += 16;
    			    	else
    			        	continue;
					}
    			}
    			
    			CurrentCustType = rs.getString("CustType");
    			if( CurrentCustType != null && CurrentCustType.length()>0 )
				{
					if(custType != null && custType.length()>0 )
					{
						if(CurrentCustType.compareTo(custType)==0)
    			        	CurrentPower += 8;
    			    	else
    			        	continue;
					}
				}
    
    			CurrentAcctType = rs.getString("AcctType");
				if( CurrentAcctType != null && CurrentAcctType.length()>0 )
				{
					if(acctType != null && acctType.length()>0 )
					{
						if(CurrentAcctType.compareTo(acctType)==0)
    			        	CurrentPower += 4;
    			    	else
    			        	continue;
					}
				}
				CurrentUserType = rs.getString("UserType");
				if( CurrentUserType != null && CurrentUserType.length()>0 )
				{
					if(userType != null && userType.length()>0 )
					{
						if(CurrentUserType.compareTo(userType)==0)
    			        	CurrentPower += 2;
    			    	else
    			        	continue;
					}
				}
    			
    			//EventClass �Ѿ�ƥ��
    			CurrentPower += 1;
    	
    			//�Ƚϲ���������Ȩ��ֵ��
    			if(CurrentPower > MostPerfectPower)
    			{
    			    //��������Ȩ��ֵ�ͷ���ֵ
    			    MostPerfectPower = CurrentPower;
    			    MostPerfectValue = CurrentValue.doubleValue();
    			    MostPerfectAcctItemTypeID = CurrentAcctItemTypeID;
                }
                else if (CurrentPower == MostPerfectPower) 
                {
    			    //�������Ȩ��ֵ��ȣ�ȡ���ŷ��ʣ�������С��
    			    if (CurrentValue.doubleValue() < MostPerfectValue) 
    			    {
    			        MostPerfectValue = CurrentValue.doubleValue();
    				    MostPerfectAcctItemTypeID = CurrentAcctItemTypeID;
    			    }
                }
            }
    		  //�Ƿ���ƥ��ķ��ʹ���
		      //�������ֵ��Ϊ0
            if(Math.abs(MostPerfectValue) > 0.001)
            {
				  //׼��������Ϣ
				  ReturnFee ReturnFeeInfo = new ReturnFee();
				  ReturnFeeInfo.setAcctItemTypeID(MostPerfectAcctItemTypeID);
				  ReturnFeeInfo.setAcctItemTypeName(getAcctItemTypeName(dbConn, MostPerfectAcctItemTypeID));
				  ReturnFeeInfo.setFeeType("P");
				  ReturnFeeInfo.setFeeName(getFeeTypeName(dbConn, "P"));
				  ReturnFeeInfo.setValue(MostPerfectValue);
				  ReturnFeeInfo.setStatus("W");
			      ReturnFeeInfo.setForcedDepositFlag(ImmediateFeeCalculator4EventImpl.SET_F_FORCEDDEPOSITFLAG_YES);
				  //���ط�����Ϣ
				  return ReturnFeeInfo;
		      }
		      //�������ֵΪ0
		      else
		      {
		          return null;
		      }
        } 
        finally
        {
        	if(rs != null)
            {
			    try
			    {
			    	rs.close();
			    } 
			    catch(SQLException e){}
		    }
            if(queryStrStmt != null)
            {
			    try
			    {
			        queryStrStmt.close();
			    } 
			    catch(SQLException e){}
		  }
		}
    }
    
    /*
     * ��װ���Էѡ��ͻ�ҵ���˻�����
     */
    private void calculator42161ByAccount(Connection dbConn,int eventClass,String eventReason,BillingObject bo,ImmediateFeeList immediateFeeList)throws SQLException
    {
    	if(eventClass == 2 || eventClass == 161)
    	{
    	    int accountID = bo.getAccountID();
    	    int customerID = bo.getCustomerID();
    	    int saID = bo.getServiceAccountID();
			String acctType = bo.getAcctType();
	        String custType = bo.getCustType();
			String userType = bo.getUserType();
    	    String catvTermType = bo.getCATVType();
    		String cableType = bo.getCableType();
			String biDirectionFlag = bo.getBiDirectionFlag();
			int orgPortNum = bo.getOrgPortNum();
			int destPortNum = bo.getDestPortNum();            
            String contractNo = bo.getContractNo();

            Collection campaignCol = bo.getCampaignCol();
            
            int groupBargainID = bo.getGroupBargainID();
            Collection returnFee = getTotalFeeRate(dbConn, eventClass,
            					   eventReason, custType, 
            					   acctType,userType, 0,0,0,
            					   campaignCol,0, "",catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);

            if( returnFee == null ) return;
            
            Iterator itFee = returnFee.iterator();
            while(itFee.hasNext())
            {
            	ReturnFee rf = (ReturnFee)itFee.next();
                rf.setAccountID(accountID);
                rf.setCustomerID(customerID);
                rf.setServiceAccountID(saID);
                addReturnFeeToList(immediateFeeList,rf);
            }
    	}
    }
    
    private void calculator4610ByAccount(Connection dbConn,int eventClass,String eventReason,BillingObject bo,ImmediateFeeList immediateFeeList,Collection allCampaignCol)
    throws SQLException
    {
    	if(eventClass == 610)
    	{
    	    int accountID = bo.getAccountID();
    	    int customerID = bo.getCustomerID();
    	    int saID = bo.getServiceAccountID();
            String acctType = bo.getAcctType();
            String custType = bo.getCustType();
            int destProductID = bo.getDestProductID();
            String contractNo = bo.getContractNo();
            Collection campaignCol = allCampaignCol;
            int groupBargainID = bo.getGroupBargainID();


			String userType = bo.getUserType();
    		String catvTermType = bo.getCATVType();
    		String cableType = bo.getCableType();
			String biDirectionFlag = bo.getBiDirectionFlag();
			int orgPortNum = bo.getOrgPortNum();
			int destPortNum = bo.getDestPortNum();


            if(bo.sHasPackage.compareTo("Y") != 0) return;
            HashMap hmPackage2Product = (HashMap)bo.getPackage2ProductMap();
            if(hmPackage2Product == null) return;
            
	        Iterator itPackage = hmPackage2Product.keySet().iterator();
	        
	        while (itPackage.hasNext())
	        {
	            Integer packageID = (Integer)itPackage.next();
                Collection productCol = (Collection)hmPackage2Product.get(packageID);
                if(productCol == null) continue;
		        Iterator itProduct = productCol.iterator();
		        

		        while(itProduct.hasNext())	
		        {
		            int productID = ((Integer)itProduct.next()).intValue();
		            String productClass = getProductClass(dbConn, productID);
		            if(productClass.equals("H"))
		            {
		                Collection hardwareTotalReturnFee = getTotalFeeRate(dbConn, 610, 
		                                                            eventReason, 
		                                                            custType, 
		                                                            acctType,
																	userType,
		                                                            productID,
		                                                            destProductID,
		                                                            packageID.intValue(), 
		                                                            campaignCol, 
		                                                            groupBargainID, 
		                                                            contractNo,catvTermType,cableType,biDirectionFlag,
																	orgPortNum,destPortNum);

		                if(hardwareTotalReturnFee != null)
		                {
                            Iterator itFee = hardwareTotalReturnFee.iterator();
                            while(itFee.hasNext())
                            {

                                ReturnFee rf = (ReturnFee)itFee.next();
                                rf.setAccountID(accountID);
                                rf.setCustomerID(customerID);
                                rf.setServiceAccountID(saID);
                                addReturnFeeToList(immediateFeeList,rf);
                            }
                         }
		       }
	          }
    	     }
    	}
    	
    }
	
/**
	����15��16�¼������˷���	
*/
private void calculatorFor1516(int eventClass,String eventReason,BillingObject bo, Connection dbConn, 
            								  ImmediateFeeList immediateFeeList, Collection campaignCol, 
            								  int groupBargainClassID, String condContractNo) throws SQLException
{
System.out.println("EventClass = 15 or 16");
	if( (eventClass != 15) && (eventClass != 16) ) return;
	int accountID = bo.getAccountID();
	int customerID = bo.getCustomerID();
	int saID = bo.getServiceAccountID();
	String custType = bo.getCustType();
	String acctType = bo.getAcctType();
	String userType = bo.getUserType();
    String catvTermType = bo.getCATVType();
    String cableType = bo.getCableType();
	String biDirectionFlag = bo.getBiDirectionFlag();
System.out.println("Ent Print Debug MSG in 15 or 16!");

	int orgPackageID = bo.getOrgPackageID();
	int destPackageID = bo.getDestPackageID();
	int orgPortNum = bo.getOrgPortNum();
	int destPortNum = bo.getDestPortNum();
System.out.println("Start call getTotalFeeRate in 15 or 16!");
	Collection returnFee =  getTotalFeeRate(dbConn, eventClass, eventReason, custType, 
            					   acctType,userType, 0,0,orgPackageID,
            					   campaignCol,0, "",catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
            					   
System.out.println("End call getTotalFeeRate in 15 or 16!");
    if(returnFee != null)
    {
    	Iterator itRF = returnFee.iterator(); 
        while(itRF.hasNext())
        {
           ReturnFee rf = (ReturnFee)itRF.next();
           rf.setAccountID(accountID);
           rf.setCustomerID(customerID);
           rf.setServiceAccountID(saID);
           addReturnFeeToList(immediateFeeList,rf);
         }
    }
}

 /**
     * ����110�¼��Ŀ��������ѣ��������˻���
     * @param eventClass
     * @param eventReason
     * @param custType
     * @param dbConn
     * @param immediateFeeList
     * @param condCampaignIDList
     * @param groupBargainClassID
     * @param condContractNo
     * @param acctType
     * @throws SQLException
     */
    private void calculator4OpenAccountFor110(int customerID,int accountID,int saID,int eventClass, String eventReason, 
            								  String custType, Connection dbConn, 
            								  ImmediateFeeList immediateFeeList, Collection condCampaignIDList, 
            								  int groupBargainClassID, String condContractNo, String acctType,
                                              String userType,String catvTermType,
                                              String cableType,String bidirectionFlag,int orgPortNo,int destPortNo) throws SQLException
    	{

System.out.println("user@liudi@printTraceMessage" + "calculator4OpenAccountFor110" + custType + acctType);
        //���㿪��������
        Collection returnFee = getTotalFeeRate(
        			dbConn, eventClass, eventReason, 
        			custType, acctType,userType,0,0,0,
        			condCampaignIDList, 
        			groupBargainClassID, condContractNo,catvTermType,cableType,bidirectionFlag,orgPortNo,destPortNo);

        //����Ѿ��ҵ���Ӧ�ķ���
        if(returnFee != null)
        {
System.out.println("user@liudi@printTraceMessage" + "calculator4OpenAccountFor110 get the fee" );
            Iterator itFee = returnFee.iterator();
            while(itFee.hasNext())
            {
        	    ReturnFee rf= (ReturnFee)itFee.next();
        	    rf.setAccountID(accountID);
        	    rf.setCustomerID(customerID);
        	    rf.setServiceAccountID(saID);
        	    addReturnFeeToList(immediateFeeList,rf);
        	}
        }
    }
    /**
     * ��������ϸ��ReturnFee�ļ��ϱ�ʾת��ΪAccountItemDTO�ļ��ϱ�ʾ
     * ������ȷ������Ҫ�Ѵ����ع�һ��
     * @param immediateFeeList
     */

    private boolean isEmpty(Collection col) {
        return (col == null) || col.isEmpty();
    }

    /**
     * ���������õ��ʷ���returnFee���뵽�����б�immediateFeeList��
     * @param immediateFeeList
     * @param returnFee
     */
    private void addReturnFeeToList(ImmediateFeeList immediateFeeList, ReturnFee returnFee) {
        //����Ѿ��ҵ���Ӧ�ķ���
        if(returnFee != null){
            //����ñʷ����Ѿ�Ԥ��
            if(returnFee.getStatus().equals("P")){
                immediateFeeList.setTotalValueAlreadyPay(returnFee.getValue() + immediateFeeList.getTotalValueAlreadyPay());
            } else{
                immediateFeeList.setTotalValueMustPay(returnFee.getValue() + immediateFeeList.getTotalValueMustPay());
                
                /*******************************************************************/
                //������ϸ���뵽�б���
                Collection returnFeeList = immediateFeeList.getReturnFeeList();
                returnFeeList.add(returnFee);
                /*******************************************************************/
            }
        }
    }
    
    //����  EventClass / EventReason / CustType / AcctType / ProductID / DestProduct / ackageID ������������
    private ReturnFee getBasicFeeRate(Connection dbConn, 
					int eventClass, 
					String eventReason, 
					String custType, 
					String acctType, 
					String userType,
					int productID, 
					int destProductID, 
					int packageID,
                    String catvTermType,
                    String cableType,
                    String biDirectionFlag,
					int orgPortNum,
					int destPortNum					
					)
		throws SQLException {

        String condProduct="";
        String condDestProduct="";
        String condPackage="";
		String condEventReason = "";

        String condCustType="";
		String condAcctType="";
        String condUserType="";
        String condCATVTermType = "";
        String condCableType = "";
        String condBiDirectionFlag = "";
        String condOrgPortNum="";
        String condDestPortNum = "";
        

        if(custType != null && custType.length() > 0)
            condCustType = " and (NVL(CustType,' ')=' ' OR CustType = '" + custType + "')";

        if(acctType != null && acctType.length() > 0)
            condAcctType = " and (NVL(AcctType,' ')=' ' OR AcctType = '" + acctType + "')";

        if(userType != null && userType.length() > 0)
            condUserType = " and (NVL(UserType,' ')=' ' OR UserType = '" + userType + "')";

        if(catvTermType != null && catvTermType.length() > 0)
            condCATVTermType = " and (NVL(catvTermType,' ')=' ' OR catvTermType = '" + catvTermType + "')";

        if(cableType != null && cableType.length() > 0)
            condCableType = " and (NVL(cableType,' ')=' ' OR cableType = '" + cableType + "')";

        if(biDirectionFlag != null && biDirectionFlag.length() > 0)
            condBiDirectionFlag = " and (NVL(BIDRECTIONFLAG,' ')=' ' OR BIDRECTIONFLAG = '" + biDirectionFlag + "')";
	
        if(orgPortNum != 0)
            condOrgPortNum = " and (NVL(OldPortNo,0) =0 OR OldPortNo = " + orgPortNum + ")";

		if(destPortNum != 0)
            condDestPortNum = " and (NVL(NewPortNo,0) =0 OR NewPortNo = " + destPortNum + ")";

		if( eventReason!= null && eventReason.length() > 0)
            condEventReason = " and (NVL(EventReason,' ')=' ' OR EventReason = '" + eventReason +"')";

        if(productID != 0)
            condProduct = " and (Nvl(productID,0) = 0 or ProductID = " + productID + ")";

        if(destProductID != 0)
            condDestProduct = " and (Nvl(DestProductID,0) = 0 or DestProductID = " + destProductID + ")";

        if(packageID != 0)
            condPackage = " and ( Nvl(packageID,0) = 0 or packageID = " + packageID + ")";

        //��ǰ����
        java.sql.Timestamp currentDate = new java.sql.Timestamp(System.currentTimeMillis());


        String queryStr = "select EventReason,CustType,AcctType, ProductID , DestProductID , PackageID , AcctItemTypeID , Value, CATVTERMTYPE,CABLETYPE,BIDRECTIONFLAG,OLDPORTNO,NEWPORTNO,USERTYPE "
        	                + " from T_BillingRuleItem where "
            				+ " EventClass = " + eventClass + " and Status = 'V' and sysdate between ValidFrom and ValidTo "
            				+ condProduct + condDestProduct + condPackage + condEventReason + condCustType + condAcctType 
                            + condUserType + condCATVTermType + condCableType + condBiDirectionFlag + condOrgPortNum + condDestPortNum
        					+ " and Value is not null and AcctItemTypeID is not null and NVL(CampaignID , 0) = 0 order by value asc";

        LogUtility.log(ImmediateFeeCalculator4EventImpl.class, LogLevel.ERROR, "queryStr in getBasicFeeRate():" + queryStr);
        /*****************************************************/ 
      	//zhang yuguo modified in 2006.09.13 end

        if (ImmediateFeeCalculator.DEBUGMODE)
            System.out.println("queryStr in getBasicFeeRate():" + queryStr);
        return getBestMatching(dbConn, eventClass, eventReason, custType, acctType,userType,productID, destProductID, packageID, queryStr,
                    catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
    }

    /**
     * ����Ŀ���ͷ��࣬�������ƥ�䡣
     * ������ڶ������򣬰�Ȩ�أ�EventClass1���ʻ�����2���ͻ�����4����Ʒ��8����Ʒ16����Ʒ32���¼�ԭ��64���������ŵ�ƥ�䣬
     * �������ƥ���ʱ�򣬻����ڶ���������ôȡ���ŷ��ʡ�
     * 
     */
     
     private ReturnFee getBestMatching(Connection dbConn, 
				      int eventClass, 
				      String eventReason, 				       
					  String custType,					  
	 			      String acctType,
					  String userType, 
				      int productID, 
				      int destProductID, 
				      int packageID, 
				      String queryStr,					  
					  String catvTermType,
					  String cableType,
					  String biDirectionFlag,
					  int orgPortNum,
					  int destPortNum
				      ) 
    	throws SQLException {
    	    int MostPerfectPower = 0;
		double MostPerfectValue = 0;
		String MostPerfectAcctItemTypeID = "";
		String MostPerfectFeeType = "";
	
		//��ʼ����ǰƥ��ֵ
		int CurrentPower = 0;
		String CurrentEventReason = "";
		String CurrentCustType = "";
		String CurrentAcctType = "";
		int CurrentProductID;
		int CurrentDestProductID;
		int CurrentPackageID;
		String CurrentFeeType = "";
		String CurrentAcctItemTypeID = "";
        String CurrentCATVTermType;
        String CurrentCableType;
        String CurrentBIDirectionFlag;
        String CurrentUserType;
        int CurrentOrgPortNum;
        int CurrentDestPortNum;

		java.math.BigDecimal CurrentValue = null;
//		String allowReturn = "";
		
		Statement queryStrStmt = null;
		ResultSet rs = null;
		try{
		  queryStrStmt = dbConn.createStatement();
		  rs = queryStrStmt.executeQuery(queryStr);
	
		  //�������Ƚ�ÿ�����ʹ����ƥ��̶�
		  while(rs.next()){
			//��ȡ�����ֶε�ȡֵ
			CurrentValue = rs.getBigDecimal("Value");
			CurrentAcctItemTypeID = rs.getString("AcctItemTypeID");
			CurrentFeeType = getFeeTypeByAcctItemTypeID(dbConn, rs.getString("AcctItemTypeID"));
			CurrentEventReason = rs.getString("EventReason");
            
			//����Ȩ�س�ʼֵ
			CurrentPower = 0;

			if(CurrentEventReason != null)
			{
				if(eventReason != null)
				{
					if(eventReason.compareTo(CurrentEventReason) == 0)
						CurrentPower += 512;
					else
						continue;
				}
			}
System.out.println("STEP1");

			CurrentProductID = rs.getInt("ProductID");
            if( CurrentProductID != 0 )
            {
                if(CurrentProductID != productID)
                    continue;
            }
System.out.println("STEP2");         
			CurrentDestProductID = rs.getInt("DestProductID");
			if(CurrentDestProductID != 0)
			{
			    if(destProductID != CurrentDestProductID)
					continue;
			}
System.out.println("STEP3");
			CurrentPackageID = rs.getInt("PackageID");
			if(CurrentPackageID != 0 )
			{
		        if(CurrentPackageID == packageID)
		            CurrentPower += 256;
			    else
				    continue;
			}
System.out.println("STEP4");
			CurrentCustType = rs.getString("CustType");
			if(CurrentCustType != null && CurrentCustType.length()>0)
			{
			    if((custType != null) && (CurrentCustType.compareTo(custType)==0))
			        CurrentPower += 128;
			    else
			        continue;
			}
System.out.println("STEP5");
			CurrentAcctType = rs.getString("AcctType");
			if(CurrentAcctType != null && CurrentAcctType.length()>0 )
			{
			    if((acctType != null) && (CurrentAcctType.compareTo(acctType)==0))
			        CurrentPower += 64;
			    else
			        continue;
			    
			}
System.out.println("STEP6");
            CurrentUserType = rs.getString("UserType");
			if(CurrentUserType != null && CurrentUserType.length()>0 )
			{
			    if((userType != null) && (CurrentUserType.compareTo(userType)==0))
			        CurrentPower += 32;
			    else
			        continue;
			    
			}
System.out.println("STEP7");
			//�ն�����
			CurrentCATVTermType = rs.getString("CATVTERMTYPE");
			if(CurrentCATVTermType != null && CurrentCATVTermType.length() > 0)
            {
                if((catvTermType !=null)&& (CurrentCATVTermType.compareTo(catvTermType)==0))
					CurrentPower += 16;
			    else
			        continue;
            }
System.out.println("STEP8");
			//��������
			CurrentCableType = rs.getString("CABLETYPE");
			if(CurrentCableType != null && CurrentCableType.length() > 0)
            {
                if((cableType !=null)&& (CurrentCableType.compareTo(cableType)==0))
					CurrentPower += 8;
			    else
			        continue;
            }
System.out.println("STEP9");
			//˫���־
            CurrentBIDirectionFlag = rs.getString("BiDrectionFlag");
            if(CurrentBIDirectionFlag != null && CurrentBIDirectionFlag.length() > 0)
            {
                if((biDirectionFlag !=null)&& (CurrentBIDirectionFlag.compareTo(biDirectionFlag)==0))
					CurrentPower += 4;
			    else
			        continue;
            }
System.out.println("STEP10");
			//ԭ�˿���
            CurrentOrgPortNum = rs.getInt("OldPortNo");
            if(CurrentOrgPortNum!=0)
			{
				if (orgPortNum == CurrentOrgPortNum)
					CurrentPower += 2;
            }

System.out.println("STEP11");
			//Ŀ��˿���
            CurrentDestPortNum = rs.getInt("NEWPORTNO");
            if(CurrentDestPortNum!=0)
			{
                if(destPortNum != CurrentDestPortNum)
                    continue;
            }
            else
			{
				if(destPortNum != 0)
                	continue;
			}
System.out.println("STEP12");
			//EventClass �Ѿ�ƥ��
			CurrentPower += 1;
	
			//�Ƚϲ���������Ȩ��ֵ��
			if(CurrentPower > MostPerfectPower){
			    //��������Ȩ��ֵ�ͷ���ֵ
			    MostPerfectPower = CurrentPower;
			    MostPerfectValue = CurrentValue.doubleValue();
			    MostPerfectAcctItemTypeID = CurrentAcctItemTypeID;
			    MostPerfectFeeType = CurrentFeeType;
			} else if (CurrentPower == MostPerfectPower) {
			    //�������Ȩ��ֵ��ȣ�ȡ���ŷ��ʣ�������С��
			    if (CurrentValue.doubleValue() < MostPerfectValue) {
			        MostPerfectValue = CurrentValue.doubleValue();
				    MostPerfectAcctItemTypeID = CurrentAcctItemTypeID;
				    MostPerfectFeeType = CurrentFeeType;
			    }
			}
		  }

		  //�Ƿ���ƥ��ķ��ʹ���
//		  if(MostPerfectPower != 0){
		      //�������ֵ��Ϊ0
		      if(Math.abs(MostPerfectValue) > 0.001){

				  //׼��������Ϣ
				  ReturnFee ReturnFeeInfo = new ReturnFee();
				  ReturnFeeInfo.setAcctItemTypeID(MostPerfectAcctItemTypeID);
				  ReturnFeeInfo.setAcctItemTypeName(getAcctItemTypeName(dbConn, MostPerfectAcctItemTypeID));
				  ReturnFeeInfo.setFeeType(MostPerfectFeeType);
				  ReturnFeeInfo.setFeeName(getFeeTypeName(dbConn, MostPerfectFeeType));
				  ReturnFeeInfo.setProductID(productID);
				  ReturnFeeInfo.setValue(MostPerfectValue);
//				  ReturnFeeInfo.setAllowReturn(allowReturn);
				  ReturnFeeInfo.setStatus("W");
				  if (MostPerfectFeeType.equals("P"))
				      ReturnFeeInfo.setForcedDepositFlag(ImmediateFeeCalculator4EventImpl.SET_F_FORCEDDEPOSITFLAG_YES);
				  //���ط�����Ϣ
				  return ReturnFeeInfo;
		      }
		      //�������ֵΪ0
		      else{
		          return null;
		      }
//		  } else{
	//	      return null;
		//  }
		} finally{
			if(rs != null){
				try{
				  rs.close();
				} catch(SQLException e){}
			}
		    if(queryStrStmt != null){
			   try{
			    queryStrStmt.close();
			  } catch(SQLException e){}
		    }
		}
    }
     
    private ReturnFee getBestMatching2(Connection dbConn, 
				      int eventClass, 
				      String eventReason, 
				      String custType, 
	 			      String acctType, 
				      int productID, 
				      int destProductID, 
				      int packageID, 
				      String queryStr,
				      boolean withProduct,  //��ProductID���
				      boolean withpackage, //��Package���
				      boolean withdestProduct //��Ŀ���Ʒ���
				      ) 
    	throws SQLException {
		//��ʼ������ƥ��ֵ
		int MostPerfectPower = 0;
		double MostPerfectValue = 0;
		String MostPerfectAcctItemTypeID = "";
		String MostPerfectFeeType = "";
	
		//��ʼ����ǰƥ��ֵ
		int CurrentPower = 0;
		String CurrentEventReason = "";
		String CurrentCustType = "";
		String CurrentAcctType = "";
		int CurrentProductID;
		int CurrentDestProductID;
		int CurrentPackageID;
		String CurrentFeeType = "";
		String CurrentAcctItemTypeID = "";
		java.math.BigDecimal CurrentValue = null;
//		String allowReturn = "";
		
		Statement queryStrStmt = null;
		ResultSet rs = null;
		
		try{
		  queryStrStmt = dbConn.createStatement();
		  rs = queryStrStmt.executeQuery(queryStr);
	
		  //�������Ƚ�ÿ�����ʹ����ƥ��̶�
		  while(rs.next()){
			//��ȡ�����ֶε�ȡֵ
			CurrentValue = rs.getBigDecimal("Value");
			CurrentAcctItemTypeID = rs.getString("AcctItemTypeID");
			CurrentFeeType = getFeeTypeByAcctItemTypeID(dbConn, rs.getString("AcctItemTypeID"));
			
			//����Ȩ�س�ʼֵ
			CurrentPower = 0;
/*	
			//��ȡ���ֶε�ֵ��������ƥ����������ƥ��Ȩ��ֵ
			CurrentEventReason = rs.getString("EventReason");
			if(CurrentEventReason != null){
			    if((eventReason != null) && (CurrentEventReason.equals(eventReason))){
			        CurrentPower += 64;
				} else{
					continue;
				}
			}

*/

            if(withProduct)
            {
    			CurrentProductID = rs.getInt("ProductID");
                if( productID != 0 )
                {
                    if(CurrentProductID != productID)
                        continue;
                }
                else
                    continue;
            }
            
            if(withdestProduct)
            {
    			CurrentDestProductID = rs.getInt("DestProductID");
    			if(destProductID != 0)
    			{
    			    if(destProductID != CurrentDestProductID)
    					continue;
    			}
    			else
    			    continue;
    		}

            if(withpackage)
            {
    			CurrentPackageID = rs.getInt("PackageID");
    			if(packageID != 0 && CurrentPackageID != 0 )
    			{
    		        if(CurrentPackageID == packageID)
    		            CurrentPower += 8;
    			    else
    				    continue;
    			}
			}

			CurrentCustType = rs.getString("CustType");
			if(custType != null && custType.length()>0 && CurrentCustType != null && CurrentCustType.length() > 0)
			{
			    if(CurrentCustType.compareTo(custType)==0)
			        CurrentPower += 4;
			    else
			        continue;
			}

			CurrentAcctType = rs.getString("AcctType");
			if(acctType != null&& acctType.length()>0 && CurrentAcctType != null && CurrentAcctType.length() > 0)
			{
			    if(CurrentAcctType.compareTo(acctType)==0)
			        CurrentPower += 2;
			    else
			        continue;
			    
			}
			
			//EventClass �Ѿ�ƥ��
			CurrentPower += 1;
	
			//�Ƚϲ���������Ȩ��ֵ��
			if(CurrentPower > MostPerfectPower){
			    //��������Ȩ��ֵ�ͷ���ֵ
			    MostPerfectPower = CurrentPower;
			    MostPerfectValue = CurrentValue.doubleValue();
			    MostPerfectAcctItemTypeID = CurrentAcctItemTypeID;
			    MostPerfectFeeType = CurrentFeeType;
			} else if (CurrentPower == MostPerfectPower) {
			    //�������Ȩ��ֵ��ȣ�ȡ���ŷ��ʣ�������С��
			    if (CurrentValue.doubleValue() < MostPerfectValue) {
			        MostPerfectValue = CurrentValue.doubleValue();
				    MostPerfectAcctItemTypeID = CurrentAcctItemTypeID;
				    MostPerfectFeeType = CurrentFeeType;
			    }
			}
		  }

	
		  //�Ƿ���ƥ��ķ��ʹ���
//		  if(MostPerfectPower != 0){
		      //�������ֵ��Ϊ0
		      if(Math.abs(MostPerfectValue) > 0.001){

				  //׼��������Ϣ
				  ReturnFee ReturnFeeInfo = new ReturnFee();
				  ReturnFeeInfo.setAcctItemTypeID(MostPerfectAcctItemTypeID);
				  ReturnFeeInfo.setAcctItemTypeName(getAcctItemTypeName(dbConn, MostPerfectAcctItemTypeID));
				  ReturnFeeInfo.setFeeType(MostPerfectFeeType);
				  ReturnFeeInfo.setFeeName(getFeeTypeName(dbConn, MostPerfectFeeType));
				  ReturnFeeInfo.setProductID(productID);
				  ReturnFeeInfo.setValue(MostPerfectValue);
//				  ReturnFeeInfo.setAllowReturn(allowReturn);
				  ReturnFeeInfo.setStatus("W");
				  if (MostPerfectFeeType.equals("P"))
				      ReturnFeeInfo.setForcedDepositFlag(ImmediateFeeCalculator4EventImpl.SET_F_FORCEDDEPOSITFLAG_YES);
				  //���ط�����Ϣ
System.out.println("##########################BestMatch##########%%%%%%%%" + "MostPerfectPower = " + ReturnFeeInfo);
				  return ReturnFeeInfo;
		      }
		      //�������ֵΪ0
		      else{
		          return null;
		      }
//		  } else{
	//	      return null;
		//  }
		} finally{
		  if(rs != null){
			 try{
			   rs.close();
			 } catch(SQLException e){}
		  }
		  if(queryStrStmt != null){
			try{
			  queryStrStmt.close();
			} catch(SQLException e){}
		  }
		}
    }
    /*
    ���¼��Ƿ���Ʒ����ӣ���Ʒ�������
    */
    private boolean isRelatePackageBilling(int eventClass)
    {
        if(eventClass == 110) return false;
        return true;
    }
    /**o*k**/
    //��ȡ������������
    private String getFeeTypeName(Connection dbConn, String FeeTypeKey) throws SQLException{
		//���ݷ�����������/��������ȡֵ��������Ϣ
		String queryStr = "select value from t_commonsettingdata where name = 'SET_F_BRFEETYPE' and key = '" + FeeTypeKey + "'";
		Statement queryStrStmt = null;
		ResultSet rs = null;
	
		try{
		    queryStrStmt = dbConn.createStatement();
		    rs = queryStrStmt.executeQuery(queryStr);
	
		    //����з��������ļ�¼
		    if(rs.next()){
		        String feeTypeName = rs.getString("value");
		        return feeTypeName;
		    } else{
		        return null;
		    }
		} finally{
			if(rs != null){
		        try{
		            rs.close();
		        } catch(SQLException e){}
		    }
		    if(queryStrStmt != null){
		        try{
		            queryStrStmt.close();
		        } catch(SQLException e){}
		    }
		}
    }
    /**o*k**/
    //��ȡ��Ŀ��������
    private String getAcctItemTypeName(Connection dbConn, String acctItemTypeID) throws SQLException{
        
		//������Ŀ���ͱ�ʶ��������Ŀ��������
		String queryStr = "select acctitemtypename from t_acctitemtype where acctitemtypeid = '" + acctItemTypeID + "'";
		Statement queryStrStmt = null;
		ResultSet rs = null;
	
		try {
		    queryStrStmt = dbConn.createStatement();
		    rs = queryStrStmt.executeQuery(queryStr);
	
		    //����з��������ļ�¼
		    if(rs.next()){
		        String acctItemTypeName = rs.getString("AcctItemTypeName");
		        return acctItemTypeName;
		    } else{
		        return null;
		    }
		} finally{
			if(rs != null){
		        try{
		            rs.close();
		        } catch(SQLException e){}
		    }
		    if(queryStrStmt != null){
		        try{
		            queryStrStmt.close();
		        } catch(SQLException e){}
		    }
		}
    }
    //ͨ��acctItemTypeID��ȡ��������
    private String getFeeTypeByAcctItemTypeID(Connection dbConn, String acctItemTypeID) throws SQLException{
		String queryStr = "select feeType from T_AcctItemType where acctItemTypeID = '" + acctItemTypeID + "'";
		Statement queryStrStmt = null;
		ResultSet rs = null;
	
		try{
		    queryStrStmt = dbConn.createStatement();
		    rs = queryStrStmt.executeQuery(queryStr);
	
		    //����з��������ļ�¼
		    if(rs.next()){
		        return rs.getString("feeType");
		        
		    } else{
		        return null;
		    }
		} finally{
			if(rs != null){
			   try{
			      rs.close();
			   } catch(SQLException e){}
			}
		    if(queryStrStmt != null){
		        try{
		            queryStrStmt.close();
		        } catch(SQLException e){}
		    }
		}
    }
    
     /*
     * �Ƿ����Ż��޹أ���ֻ����������ʣ��������Żݷ���
     */
    private boolean isEventClassNotRelateWithCampaign(int eventClass) {
        return (eventClass == 1) || (eventClass == 3) || 
        		(eventClass == 113) || (eventClass == 114) || (eventClass == 120) || 
        		(eventClass == 121) || (eventClass == 151) || (eventClass == 154) ||   
        		(eventClass == 162) || (eventClass == 163) || 
        		(eventClass == 164) || (eventClass == 166) || 
        		(eventClass == 312) || (eventClass == 313) || (eventClass == 314) ||
        		(eventClass == 321)|| (eventClass == 168) ;  
        		
    }
    
     /**
     * �����������ʺ��Żݷ�����Ӻ���ܷ���
     * @param dbConn
     * @param EventClass
     * @param EventReason
     * @param CustType
     * @param AcctType
     * @param ProductID
     * @param DestProductID
     * @param PackageID
     * @param CampaignID
     * @param GroupBargainID	Ŀǰ���ã�Ԥ��
     * @param ContractNo		Ŀǰ���ã�Ԥ��
     * @return
     * @throws SQLException
     * by liudi
     */
    private Collection getTotalFeeRate(Connection dbConn, 
            								 int EventClass, 
            								 String EventReason, 
            								 String CustType, 
            								 String AcctType, 
											 String UserType,
            								 int ProductID, 
            								 int DestProductID, 
            								 int PackageID, 
            								 Collection CampaignCol, 
            								 int GroupBargainID, 
            								 String ContractNo,
											 String catvTermType,
					  						 String cableType,
					  						 String biDirectionFlag,
					  						 int orgPortNum,
					  						 int destPortNum 
            								 ) 
    	throws SQLException{
        //������������
		ReturnFee basicReturnFee = getBasicFeeRate(dbConn, EventClass, EventReason, CustType, AcctType,UserType,ProductID, DestProductID, PackageID,catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
		//û�л����ѣ����ؿ�
		if( (basicReturnFee == null) || (Math.abs(basicReturnFee.getValue())<0.001)) return null;
		Collection feeCol = new Vector();	
		
		//�����Żݷ���
		ReturnFee favarableReturnFee = null;
		if((CampaignCol != null)){
		    favarableReturnFee = getFavarableFeeRate(dbConn, EventClass, EventReason, CustType, AcctType,UserType,ProductID, DestProductID, PackageID, CampaignCol, GroupBargainID, ContractNo,catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
		}
		
		if(favarableReturnFee != null && (Math.abs(favarableReturnFee.getValue())>0.001)) 
		//��������Żݷ�
		{
		    if(favarableReturnFee.getAcctItemTypeID().compareTo(basicReturnFee.getAcctItemTypeID())== 0 )
		    //����Ż���Ŀ�������������Ŀ����һ��
		    {
		        if(Math.abs(basicReturnFee.getValue() + favarableReturnFee.getValue()) > 0.001 )
		        {
		            basicReturnFee.setValue(basicReturnFee.getValue() + favarableReturnFee.getValue());
		            feeCol.add(basicReturnFee);
		        }
		    }
		    else
		    //�����Ŀ���Ͳ�ͬ
		    {
		        feeCol.add(basicReturnFee);
		        feeCol.add(favarableReturnFee);
		    }
		}
		else
		    feeCol.add(basicReturnFee);
		    
		return feeCol;
    }
    /**
     * �������������Żݷ���
     * @param dbConn
     * @param eventClass
     * @param eventReason
     * @param custType
     * @param acctType
     * @param productID
     * @param destProductID
     * @param packageID
     * @param campaignID
     * @param groupBargainID	Ŀǰ���ã�Ԥ��
     * @param contractNo	Ŀǰ���ã�Ԥ��
     * @return
     * @throws SQLException
     */
    private ReturnFee getFavarableFeeRate(Connection dbConn, 
            									int eventClass, 
            									String eventReason, 
            									String custType, 
            									String acctType, 
												String userType, 
            									int productID, 
            									int destProductID, 
            									int packageID, 
            									Collection campaignCol, 
            									int groupBargainID, 
            									String contractNo,
					  							String catvTermType,
					  							String cableType,
					  							String biDirectionFlag,
					  							int orgPortNum,
					  							int destPortNum
            									 ) 
    	throws SQLException{
		//��ǰ����
		java.sql.Timestamp currentDate = new java.sql.Timestamp(System.currentTimeMillis());
	
		//�����Ż�����,��ڲ�����CampaignID/GroupBargainID/ContractNo ������һ����Ϊ��
		String favarableCondStr = "";
		if(campaignCol != null)
		{
		    Iterator itCampaign= campaignCol.iterator();
		    String CampaignIDString = "";
		    if(itCampaign.hasNext()){
		        favarableCondStr = String.valueOf((Integer)itCampaign.next());
		    }
		    while(itCampaign.hasNext()){
		        favarableCondStr = favarableCondStr + "," + String.valueOf((Integer)itCampaign.next());
		    }
		}
		else
		    return null;
		    
		favarableCondStr.trim();
		if(favarableCondStr.length() > 0)
		    favarableCondStr = " and CampaignID in(" + favarableCondStr + ")";
		else
		    return null;
		
		String condProduct="";
        String condDestProduct="";
        String condPackage="";
		String condEventReason = "";

		String condCustType="";
		String condAcctType="";
        String condUserType="";
        String condCATVTermType = "";
        String condCableType = "";
        String condBiDirectionFlag = "";
        String condOrgPortNum="";
        String condDestPortNum = "";
        
		if(custType != null && custType.length() > 0)
            condCustType = " and (NVL(CustType,' ')=' ' OR CustType = '" + custType + "')";

        if(acctType != null && acctType.length() > 0)
            condAcctType = " and (NVL(AcctType,' ')=' ' OR AcctType = '" + acctType + "')";

        if(userType != null && userType.length() > 0)
            condUserType = " and (NVL(UserType,' ')=' ' OR UserType = '" + userType + "')";

        if(catvTermType != null && catvTermType.length() > 0)
            condCATVTermType = " and (NVL(catvTermType,' ')=' ' OR catvTermType = '" + catvTermType + "')";

        if(cableType != null && cableType.length() > 0)
            condCableType = " and (NVL(cableType,' ')=' ' OR cableType = '" + cableType + "')";

        if(biDirectionFlag != null && biDirectionFlag.length() > 0)
            condBiDirectionFlag = " and (NVL(BIDRECTIONFLAG,' ')=' ' OR BIDRECTIONFLAG = '" + biDirectionFlag + "')";
	
        if(orgPortNum != 0)
            condOrgPortNum = " and (NVL(OldPortNo,0) =0 OR OldPortNo = " + orgPortNum + ")";

		if(destPortNum != 0)
            condDestPortNum = " and (NVL(NewPortNo,0) =0 OR NewPortNo = " + destPortNum + ")";


		if( eventReason!= null && eventReason.length() > 0)
            condEventReason = " and (NVL(EventReason,' ')=' ' OR EventReason = '" + eventReason +"')";

        if(productID != 0)
            condProduct = " and ( Nvl(ProductID,0) = 0 or ProductID = " + productID + ")";

        if(destProductID != 0)
            condDestProduct = " and ( Nvl(DestProductID,0) = 0 or DestProductID = " + destProductID + ")";

        if(packageID != 0)
            condPackage = " and ( Nvl(packageID,0) = 0 or packageID = " + packageID + ")";
		
		
		String queryStr = "select EventReason , CustType , AcctType,UserType, ProductID , DestProductID, PackageID ,AcctItemTypeID, Value,CATVTERMTYPE,CABLETYPE,BIDRECTIONFLAG,OldPortNo,NewPortNo from T_BillingRuleItem "
			+ " where"					  
			  + " EventClass = " + eventClass
			  + " and Status = 'V' and sysdate between ValidFrom and ValidTo" 
			  + " and Value is not null and AcctItemTypeID is not null and NVL(CampaignID , 0) <> 0 "
			  + condProduct + condDestProduct + condPackage + condEventReason + condCustType
              + condAcctType + condUserType + condCATVTermType + condCableType +  condBiDirectionFlag
              + condOrgPortNum + condDestPortNum
			  + favarableCondStr
			  +" order by value asc ";

		LogUtility.log(ImmediateFeeCalculator4EventImpl.class, LogLevel.ERROR, "queryStr in getBasicFeeRate():" + queryStr);
		
		
		if (ImmediateFeeCalculator.DEBUGMODE) 
		    System.out.println("queryStr in getFavarableFeeRate() method:"+queryStr);
		
		return getBestMatching(dbConn, eventClass, eventReason, custType, acctType,userType, productID, destProductID, packageID, queryStr
								,catvTermType,cableType,biDirectionFlag,orgPortNum,destPortNum);
    }
        /**o*k**/
    //��ȡ��Ʒ���
    private String getProductClass(Connection dbConn, int productID) throws SQLException{
		//���ݲ�ƷID����ò�Ʒ���
		String queryStr = "select productclass from t_product where productid = " + productID;
		Statement queryStrStmt = null;
		ResultSet rs = null;
	
		try {
		    queryStrStmt = dbConn.createStatement();
		    rs = queryStrStmt.executeQuery(queryStr);
	
		    //����з��������ļ�¼
		    if(rs.next()){
		        String productClass = rs.getString("productclass");
		        return productClass;
		    } else{
		        return null;
		    }
		} finally{
			if(rs != null){
		        try{
		            rs.close();
		        } catch(SQLException e){}
		    }
		    if(queryStrStmt != null){
		        try{
		            queryStrStmt.close();
		        } catch(SQLException e){}
		    }
		}
    }
        /*
     * �Ƿ�Ϊ��Ч��eventClass�����Ʒѽӿ��Ƿ�֧�֣�
     * 
     */ 
    private boolean isValidEventClass(int eventClass) {
        return (eventClass == 1) || (eventClass == 2) || (eventClass == 3) || 
				(eventClass == 15) || (eventClass == 16) || 
        		(eventClass == 110) || (eventClass == 113) || (eventClass == 114) ||
        		(eventClass == 120) || (eventClass == 121) || (eventClass == 151) || 
        		(eventClass == 154) || (eventClass == 161) || (eventClass == 162) || 
        		(eventClass == 163) || (eventClass == 164) || (eventClass == 166) ||
        		(eventClass == 310) || (eventClass == 312) || (eventClass == 313) ||  
        		(eventClass == 314) || (eventClass == 315) || (eventClass == 316) || 
        		(eventClass == 321) || (eventClass == 5) || (eventClass == 308)||
        		(eventClass == 610) || (eventClass == 611)|| (eventClass == 168);
    }
    
    private AccountItemDTO getBasicFeeRate(Connection dbConn,int eventClass, String eventReason, String custType)
    {
    	java.sql.Timestamp currentDate = new java.sql.Timestamp(System.currentTimeMillis());

		String condeventReason = "";

		
		if(eventReason!= null && eventReason.length() > 0)
			condeventReason = " AND (EventReason is null or EventReason = '" + eventReason + "')";

        String queryStr = "select EventReason, CustType, AcctType,UserType,ProductID, DestProductID,PackageID,AcctItemTypeID,Value,CATVTERMTYPE,CABLETYPE,BIDRECTIONFLAG,OldPortNo,NewPortNo from T_BillingRuleItem"
        	+ " where"        	
			+ " EventClass = " + eventClass + " and Status = 'V' and sysdate between ValidFrom and ValidTo "	
			+ condeventReason		
			+ " and Value is not null and AcctItemTypeID is not null and NVL(CampaignID , 0) = 0  order by value asc";
        
	LogUtility.log(ImmediateFeeCalculator4EventImpl.class, LogLevel.ERROR, "queryStr in getBasicFeeRate():" + queryStr);

        if (ImmediateFeeCalculator.DEBUGMODE)
            System.out.println("queryStr in getBasicFeeRate():" + queryStr);
try{
        ReturnFee rf = getBestMatching(dbConn, eventClass, eventReason, custType,null,null, 0, 0, 0, queryStr,null,null,null,0,0);
        if (rf == null) return null;
        AccountItemDTO acDto = new AccountItemDTO();
        acDto.setValue(rf.getValue());
        acDto.setAcctItemTypeID(rf.getAcctItemTypeID());
        acDto.setProductID(rf.getProductID());
        acDto.setStatus(com.dtv.oss.service.util.CommonConstDefinition.AISTATUS_VALIDATE);
		acDto.setFeeType(rf.getFeeType());
        if (rf.getForcedDepositFlag() == null)
            acDto.setForcedDepositFlag(ImmediateFeeCalculator4EventImpl.SET_F_FORCEDDEPOSITFLAG_NO);
        else
            acDto.setForcedDepositFlag(rf.getForcedDepositFlag());
        return acDto;

}catch(Exception ex)
{
	ex.printStackTrace();
}
return null;
        
    }
    
    private Collection getAllCampaign(Collection billingObjectCol)
    {
        try
        {
            Vector CampaignCol=new Vector();
            Iterator it = billingObjectCol.iterator();
            while(it.hasNext())
            {
                BillingObject bo = (BillingObject)it.next();
                CampaignCol.addAll(bo.getCampaignCol());
            }
            
            return CampaignCol;
        }
        catch(Exception ex)
        {            
        }
        return null;
    }

	public void calcuGroupBargain(CustServiceInteractionDTO csiDto,Collection billingObjectCol,ImmediateFeeList immediateFeeList)
	throws SQLException,ServiceException
	{
System.out.println("**********************************************");
System.out.println(billingObjectCol);
System.out.println(csiDto);

		if(billingObjectCol == null) return;
		if(immediateFeeList == null) return;
		if(csiDto == null) return;

		Iterator itBO = billingObjectCol.iterator();
		while(itBO.hasNext())
		{
			BillingObject bo = (BillingObject)itBO.next();
			int groupBarginID = bo.getGroupBargainID();

			if(bo.getBOType().compareTo("G") != 0) continue;

			GroupBargainDTO gb = BusinessUtility.getGroupBargainByID(groupBarginID);
System.out.println(gb);
			if(gb == null) throw new ServiceException("�޷�ȡ���Ź���Ϣ���Ź�ȯ��= "+groupBarginID);

			if(gb.getPrepayDepositFee() > 0.0f)
			{
				PaymentRecordDTO pr = new PaymentRecordDTO();
				pr.setAmount(gb.getPrepayDepositFee());			
				pr.setCreateTime(TimestampUtility.getCurrentTimestamp());
				pr.setPaymentTime(TimestampUtility.getCurrentTimestamp());
				pr.setPayType("P");
				pr.setMopID(gb.getMopId());
				pr.setStatus("V");
				pr.setInvoicedFlag("N");
				pr.setCustID(csiDto.getCustomerID());
				pr.setAcctID(csiDto.getAccountID());
				pr.setDtCreate(TimestampUtility.getCurrentTimestamp());
				pr.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				pr.setReferType("M");
				pr.setReferID(csiDto.getId());
				pr.setPrepaymentType(BusinessUtility.getDepositFlagByMopID(gb.getMopId()));
				immediateFeeList.getPaymentList().add(pr);
			}
			if(gb.getPrepayImmediateFee() > 0.0f)
			{
				PaymentRecordDTO pr = new PaymentRecordDTO();
				pr.setAmount(gb.getPrepayImmediateFee());			
				pr.setCreateTime(TimestampUtility.getCurrentTimestamp());
				pr.setPaymentTime(TimestampUtility.getCurrentTimestamp());
				pr.setPayType("C");
				pr.setMopID(gb.getMopId());
				pr.setStatus("V");
				pr.setInvoicedFlag("N");
				pr.setCustID(csiDto.getCustomerID());
				pr.setAcctID(csiDto.getAccountID());
				pr.setDtCreate(TimestampUtility.getCurrentTimestamp());
				pr.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				pr.setReferType("M");
				pr.setReferID(csiDto.getId());
				pr.setPrepaymentType(BusinessUtility.getDepositFlagByMopID(gb.getMopId()));
				immediateFeeList.getPaymentList().add(pr);
			}
		}
	}
}