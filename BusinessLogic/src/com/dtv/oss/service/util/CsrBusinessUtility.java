/*
 * Created on 2004-8-9
 *
 * @author Mac Wang
 */
package com.dtv.oss.service.util;

import java.sql.*;
import java.util.*;

import com.dtv.oss.util.*;
import com.dtv.oss.domain.*;
import com.dtv.oss.domain.Organization;
import com.dtv.oss.log.*;
import com.dtv.oss.dto.*;
import javax.ejb.FinderException;

import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.factory.HomeFactoryException;

public class CsrBusinessUtility {
    private static final Class clazz = CsrBusinessUtility.class;
	
	public static boolean supportedPackageInCampaign(int packageid, int campaignid) throws HomeFactoryException, FinderException{
		CampaignAgmtPackageHome  agmtPackageHome =HomeLocater.getCampaignAgmtPackageHome();
		try{
		    CampaignAgmtPackage   agmtPackage =agmtPackageHome.findByPrimaryKey(new CampaignAgmtPackagePK(campaignid,packageid));
		    return true;
		} catch(FinderException e){
			return false;
		}
		
		/*
		CampaignNewPackageCondHome condHome = HomeLocater.getCampaignNewPackageCondHome();
        Collection packageList = condHome.findNewPackageIdByCampainId(campaignid);
        
        if (packageList == null || packageList.isEmpty()) return false;
        boolean existed = false;
        Iterator iterator = packageList.iterator();
        while(iterator.hasNext()) {
            if (packageid == ((Integer)iterator.next()).intValue()) {
                existed = true;
                break;
            }
        }
        
        return existed;
        */
	}
	
	 /**
	 * David.Yang
	 * @param colPackage
	 * @param detailno
	 */
   //根据T_GroupBargainDetail中的detailno来获得产品包的集合
   public static void fillPackageColByGroupBargainDetailNo(Collection colPackage, String detailno) {
	  Connection con = DBUtil.getConnection();
	  PreparedStatement stmt = null;
	  ResultSet rs = null;
	  try {
	      String strSql = "SELECT cnpc.packageid " +
		              " FROM T_GroupBargainDetail gbd, T_GroupBargain gb, T_Campaign c, T_Bundle2Campaign cnpc" +
			      " WHERE gbd.groupbargainid = gb.id and gb.CampaignID=c.CampaignID and cnpc.CampaignID=c.CampaignID " +
			      " and (gbd.status ='S' or gbd.status ='L') " +
			      " and gbd.detailno = '" + detailno + "'";
	      LogUtility.log(BusinessUtility.class,LogLevel.DEBUG,strSql);
	      stmt =con.prepareStatement(strSql);
	      rs = stmt.executeQuery();
	      while(rs.next()) {
		     if (rs.getInt(1) != 0) colPackage.add(new Integer(rs.getInt(1)));
	      }   
	  } catch (SQLException e) {
		LogUtility.log(BusinessUtility.class,LogLevel.WARN,"fillPackageColByGroupBargainDetailNo",e);
	  } finally {
	      if (rs !=null) {
	    	  try{
				  rs.close();
			   }catch (SQLException e) {
			   }
	      }
	      if (stmt !=null) {
	    	  try{
	    		  stmt.close();
			   }catch (SQLException e) {
			   }
	      }
		  if (con != null) {
		      try{
		 	     con.close();	
		      }catch(SQLException e){
		      }
		  }
	   }
   }
   
      
   /**
    *  add by david.Yang
    */
   public static boolean isAgent(int operatorId){
		boolean flag =false;
		try{
		  Operator operator = EJBCommonUtility.getOperatorByID(operatorId);
          int orgID =operator.getOrgID();
          Organization organization =EJBCommonUtility.getOrganizationByID(orgID);
          if (CommonConstDefinition.ORGANIZATIONORGTYPE_PARTNER.equals(organization.getOrgType())
        	 && 	CommonConstDefinition.ORGANIZATIONORGTYPE_STREETSTATION.equals(organization.getOrgSubType())){
        	 flag =true;
          }
		} catch(Exception e){
			 LogUtility.log(clazz,LogLevel.ERROR, e);
			 e.printStackTrace();
		}
        return flag;   
   }   
   
   /**
    * david.Yang
    */
   public static String getSubOrgID(int orgID){
	   Connection con = null;
	   String subOrgID ="";
	   PreparedStatement stmt = null;
	   ResultSet rs = null;
	   try {
		   con = DBUtil.getConnection();
		   String strSql = "select orgid,parentorgid from t_organization connect by prior orgid=parentorgid start with orgid= ?";
		   stmt =con.prepareStatement(strSql);
		   stmt.setInt(1, orgID);
		   rs = stmt.executeQuery();
		   while (rs.next()) {
				 subOrgID =subOrgID + rs.getInt("orgid")+",";
		   }
	   } catch(Exception e){
		    LogUtility.log(clazz,LogLevel.ERROR, e);
			e.printStackTrace();
        }
        finally{
          if(rs != null){
      		 try{
      		   rs.close();
      		 } catch(SQLException e){
      		 }
      	  }
		  if(stmt != null){
			try{
			  stmt.close();
			} catch(SQLException e){
			}
		  }
		  if(con != null){
			try{
			  con.close();
			} catch(SQLException e){
			}
		  }
		}
        if (!subOrgID.equals("")){
        	subOrgID =subOrgID.substring(0,subOrgID.length()-1);
        }
        return subOrgID;
   }
   
   public static String getSubDistrinctID(int distrinctID){
	   Connection con = null;
	   String subDistrinctID ="";
	   PreparedStatement stmt = null;
	   ResultSet rs = null;
	   try {
		   con = DBUtil.getConnection();
		   String strSql = "select id ,name from t_districtsetting connect by prior id=belongto start with id= ?";
		   stmt =con.prepareStatement(strSql);
		   stmt.setInt(1, distrinctID);
		   rs = stmt.executeQuery();
		   while (rs.next()) {
			   subDistrinctID =subDistrinctID +rs.getInt("id")+",";
		   }
	   } catch(Exception e){
		    LogUtility.log(clazz,LogLevel.ERROR, e);
			e.printStackTrace();
       }
       finally{
    	  if(rs != null){
   			 try{
   			  rs.close();
   			} catch(SQLException e){
   			}
   		  }
		  if(stmt != null){
			try{
			  stmt.close();
			} catch(SQLException e){
			}
		  }
		  if(con != null){
			try{
			  con.close();
			} catch(SQLException e){
			}
		  }
		}
       if (!subDistrinctID.equals("")){
    	   subDistrinctID =subDistrinctID.substring(0,subDistrinctID.length()-1);
       }
       return subDistrinctID;
   }
   
   		//根据设备id获得对应的产品iD
	  public static int getProductIDByDeviceID(int  deviceID) {
	  	Connection con = DBUtil.getConnection();
	  	Statement stmt = null;
		ResultSet rs = null;
	  	int productID = 0;
		try {
			String strSql = "select PRODUCTID from T_DEVICEMATCHTOPRODUCT  WHERE DEVICEMODEL in ( select DEVICEMODEL from T_TERMINALDEVICE where DEVICEID="+deviceID+")" ;
			stmt = con.createStatement();
			rs =stmt.executeQuery(strSql);
			if (rs.next()) {
				productID = rs.getInt("PRODUCTID");
			}
		} catch (SQLException e) {
			LogUtility.log(clazz,LogLevel.ERROR, e);
		} finally {
			if (rs != null) {
				try{
					rs.close();	
				}catch(SQLException e){
				}
			}
			if (stmt != null) {
				try{
					stmt.close();	
				}catch(SQLException e){
				}
			}
			if (con != null) {
				try{
					con.close();	
				}catch(SQLException e){
				}
			}
		}
		return productID;
	 }
	  
	  public static java.util.Collection getPackagesByCSIID(int csiid) {
			 Collection colIDs = new ArrayList();
			 Connection con = null;
			 Statement  stmt =null;
			 ResultSet  rs =null;
			 try {
				 String strSql = "select REFERPACKAGEID from t_csicustproductinfo a where a.csiid =" + csiid
				               +" and a.status <> 'I' "
				               +" and (a.refercampaignid in (select b.campaignid from t_campaign b where b.campaigntype ='A')"
				               +"      or a.refercampaignid =0)";
				 System.out.println(strSql);
				 con =DBUtil.getConnection();
				 stmt =con.createStatement();
				 rs = stmt.executeQuery(strSql);
				 while (rs.next()) {
					int referPackageId = rs.getInt("REFERPACKAGEID");
				 	if (referPackageId != 0 && !colIDs.contains(new Integer(referPackageId))){
						   colIDs.add(new Integer(referPackageId));
				 	}
				 }
			 } catch (SQLException e) {
				 LogUtility.log(clazz,LogLevel.ERROR, e);
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
		  return colIDs;	  
     } 
	  
	  public static java.util.Collection getCampaignsByCSIID(int csiid) {
		  Collection colIDs = new ArrayList();
		  Connection con = null;
		  Statement  stmt =null;
	      ResultSet  rs =null;
		  try {
			  String strSql = "select distinct REFERCAMPAIGNID from t_csicustproductinfo a where a.csiid =" + csiid + 
				              " and a.status <> '" + CommonConstDefinition.GENERALSTATUS_INVALID + "'";
			  con = DBUtil.getConnection();
			  stmt =con.createStatement();
		      rs = stmt.executeQuery(strSql);
			  while (rs.next()) {
				 if (rs.getInt("REFERCAMPAIGNID") != 0)
					 colIDs.add(new Integer(rs.getInt("REFERCAMPAIGNID")));
			  }
		   } catch (SQLException e) {
				LogUtility.log(clazz,LogLevel.ERROR, e);
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
			return colIDs;	  
	   }
	  
}
