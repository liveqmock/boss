/*
 * Created on 2005-12-6
 *
 * @author whq
 * 
 * 运营商产品/客户产品基本信息
 */
package com.dtv.oss.service.util.ImmediateFee;


public class ProductInfo implements java.io.Serializable{
  private int productID;
  private int destProductID;
  private int packageID;
  private int campaignID;
  private int groupBargainID;
  private String contractNo;

  public ProductInfo(){

  }

  public ProductInfo(int productID, 
          			 int destProductID, 
          			 int packageID, 
          			 int campaignID, 
          			 int groupBargainID, 
          			 String contractNo){
    this.productID = productID;
    this.destProductID = destProductID;
    this.packageID = packageID;
    this.campaignID = campaignID;
    this.groupBargainID = groupBargainID;
	this.contractNo = contractNo;
  }

  //the getter functions
  public int getProductID(){
	return this.productID;
  }

  public int getDestProductID(){
	return this.destProductID;
  }

  public int getPackageID(){
	return this.packageID;
  }

  public int getCampaignID(){
	return this.campaignID;
  }

  public int getGroupBargainID(){
	return this.groupBargainID;
  }

  public String getcontractNo(){
	return this.contractNo;
  }

  //the setter functions
  public void setProductID(int productID){
	this.productID = productID;
  }

  public void setDestProductID(int destProductID){
	this.destProductID = destProductID;
  }

  public void setPackageID(int packageID){
	this.packageID = packageID;
  }

  public void setCampaignID(int campaignID){
	this.campaignID = campaignID;
  }

  public void setGroupBargainID(int groupBargainID){
	this.groupBargainID = groupBargainID;
  }

  public void setContractNo(String contractNo){
	this.contractNo = contractNo;
  }
  
  public String toString() {
      StringBuffer buf = new StringBuffer(18);
      buf.append("productID="+productID);
      buf.append(",destProductID="+destProductID);
      buf.append(",packageID="+packageID);
      buf.append(",campaignID="+campaignID);
      buf.append(",groupBargainID="+groupBargainID);
      buf.append(",contractNo="+contractNo);
      return buf.toString();
  }
}