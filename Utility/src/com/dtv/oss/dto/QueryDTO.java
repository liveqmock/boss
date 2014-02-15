package com.dtv.oss.dto;

import java.sql.Timestamp;

public class QueryDTO
     implements ReflectionSupport, java.io.Serializable {
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int queryId;
  private String queryName;
  private String queryDesc;
  private int createOperatorId;
  private Timestamp createTime;
  private String scheduleType;
  private Timestamp scheduleTime;
  private Timestamp performTime;
  private String queryType;
  private String custStatusList;
  private String custTypeList;
  private Timestamp custCreateTime1;
  private Timestamp custCreateTime2;
  private String custOpenSourceTypeList;
  private String custOpenSourceIdList;
  private int custCurrentPoints1;
  private int custCurrentPoints2;
  private int custTotalPoints1;
  private int custTotalPoints2;
  private int customerId;
  private String custName;
  private String custAddress;
  private String custDistrictIdList;
  private String accountStatusList;
  private String accountTypeList;
  private double accountCashBalance1;
  private double accountCashBalance2;
  private double accountTokenBalance1;
  private double accountTokenBalance2;
  private String accountMopIdList;
  private String accountDistrictIdList;
  private String accountAddress;
  private Timestamp accountCreateTime1;
  private Timestamp accountCreateTime2;
  private String cpStatusList;
  private Timestamp cpCreateTime1;
  private Timestamp cpCreateTime2;
  private String cpProductIdList;
  private String templateFlag;
/**
 * @return Returns the bankAccountStatusList.
 */
public String getBankAccountStatusList() {
	return bankAccountStatusList;
}
/**
 * @param bankAccountStatusList The bankAccountStatusList to set.
 */
public void setBankAccountStatusList(String bankAccountStatusList) {
	this.bankAccountStatusList = bankAccountStatusList;
	put("bankAccountStatusList");
}
  private String cpCampaignIdList;
  private String bankAccountStatusList;
/**
 * @return Returns the productClassList.
 */
public String getProductClassList() {
	return productClassList;
}
/**
 * @param productClassList The productClassList to set.
 */
public void setProductClassList(String productClassList) {
	this.productClassList = productClassList;
	put("productClassList");
}
  private Timestamp cpCampaignStartTime1;
  private Timestamp cpCampaignStartTime2;
  private Timestamp cpCampaignEndTime1;
  private Timestamp cpCampaignEndTime2;
  
  private Timestamp accountInvoiceCreateTime1;
  private Timestamp accountInvoiceCreateTime2;
  private String accountInvoiceStatusList;
  private String productClassList;
  
   
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    put("status");
  }

   

  public Timestamp getDtCreate() {
    return dtCreate;
  }

  public void setDtCreate(Timestamp dtCreate) {
    this.dtCreate = dtCreate;
  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public int getQueryId() {
    return queryId;
  }

  public void setQueryId(int queryId) {
    this.queryId = queryId;
  }

  public String getQueryName() {
    return queryName;
  }

  public void setQueryName(String queryName) {
    this.queryName = queryName;
    put("queryName");
  }

  public String getQueryDesc() {
    return queryDesc;
  }

  public void setQueryDesc(String queryDesc) {
    this.queryDesc = queryDesc;
    put("queryDesc");
  }

  public int getCreateOperatorId() {
    return createOperatorId;
  }

  public void setCreateOperatorId(int createOperatorId) {
    this.createOperatorId = createOperatorId;
    put("createOperatorId");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
    put("createTime");
  }

  public String getScheduleType() {
    return scheduleType;
  }

  public void setScheduleType(String scheduleType) {
    this.scheduleType = scheduleType;
    put("scheduleType");
  }

  public Timestamp getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(Timestamp scheduleTime) {
    this.scheduleTime = scheduleTime;
    put("scheduleTime");
  }

  public Timestamp getPerformTime() {
    return performTime;
  }

  public void setPerformTime(Timestamp performTime) {
    this.performTime = performTime;
    put("performTime");
  }

  public String getQueryType() {
    return queryType;
  }

  public void setQueryType(String queryType) {
    this.queryType = queryType;
    put("queryType");
  }

  public String getCustStatusList() {
    return custStatusList;
  }

  public void setCustStatusList(String custStatusList) {
    this.custStatusList = custStatusList;
    put("custStatusList");
  }

  public String getCustTypeList() {
    return custTypeList;
  }

  public void setCustTypeList(String custTypeList) {
    this.custTypeList = custTypeList;
    put("custTypeList");
  }

  public Timestamp getCustCreateTime1() {
    return custCreateTime1;
  }

  public void setCustCreateTime1(Timestamp custCreateTime1) {
    this.custCreateTime1 = custCreateTime1;
    put("custCreateTime1");
  }
  public Timestamp getCustCreateTime2() {
     return custCreateTime2;
   }

   public void setCustCreateTime2(Timestamp custCreateTime2) {
     this.custCreateTime2 = custCreateTime2;
     put("custCreateTime2");
   }

  public String getCustOpenSourceTypeList() {
    return custOpenSourceTypeList;
  }

  public void setCustOpenSourceTypeList(String custOpenSourceTypeList) {
    this.custOpenSourceTypeList = custOpenSourceTypeList;
    put("custOpenSourceTypeList");
  }

  public String getCustOpenSourceIdList() {
    return custOpenSourceIdList;
  }

  public void setCustOpenSourceIdList(String custOpenSourceIdList) {
    this.custOpenSourceIdList = custOpenSourceIdList;
    put("custOpenSourceIdList");
  }

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
    put("custName");
  }

  public String getCustAddress() {
    return custAddress;
  }

  public void setCustAddress(String custAddress) {
    this.custAddress = custAddress;
    put("custAddress");
  }

  public String getCustDistrictIdList() {
    return custDistrictIdList;
  }

  public void setCustDistrictIdList(String custDistrictIdList) {
    this.custDistrictIdList = custDistrictIdList;
    put("custDistrictIdList");
  }

  public String getAccountStatusList() {
    return accountStatusList;
  }

  public void setAccountStatusList(String accountStatusList) {
    this.accountStatusList = accountStatusList;
    put("accountStatusList");
  }

  public String getAccountTypeList() {
    return accountTypeList;
  }

  public void setAccountTypeList(String accountTypeList) {
    this.accountTypeList = accountTypeList;
    put("accountTypeList");
  }

   


  public String getAccountMopIdList() {
    return accountMopIdList;
  }

  public void setAccountMopIdList(String accountMopIdList) {
    this.accountMopIdList = accountMopIdList;
    put("accountMopIdList");
  }

  public String getAccountAddress() {
    return accountAddress;
  }

  public void setAccountAddress(String accountAddress) {
    this.accountAddress = accountAddress;
    put("accountAddress");
  }

  public Timestamp getAccountCreateTime1() {
    return accountCreateTime1;
  }

  public void setAccountCreateTime1(Timestamp accountCreateTime1) {
    this.accountCreateTime1 = accountCreateTime1;
    put("accountCreateTime1");
  }

  public Timestamp getAccountCreateTime2() {
    return accountCreateTime2;
  }

  public void setAccountCreateTime2(Timestamp accountCreateTime2) {
    this.accountCreateTime2 = accountCreateTime2;
    put("accountCreateTime2");
  }

  public String getCpStatusList() {
    return cpStatusList;
  }

  public void setCpStatusList(String cpStatusList) {
    this.cpStatusList = cpStatusList;
    put("cpStatusList");
  }

  public String getCpProductIdList() {
    return cpProductIdList;
  }

  public void setCpProductIdList(String cpProductIdList) {
    this.cpProductIdList = cpProductIdList;
    put("cpProductIdList");
  }

  public Timestamp getCpCreateTime1() {
    return cpCreateTime1;
  }

  public void setCpCreateTime1(Timestamp cpCreateTime1) {
    this.cpCreateTime1 = cpCreateTime1;
    put("cpCreateTime1");
  }

  public Timestamp getCpCreateTime2() {
    return cpCreateTime2;
  }

  public void setCpCreateTime2(Timestamp cpCreateTime2) {
    this.cpCreateTime2 = cpCreateTime2;
    put("cpCreateTime2");
  }

  public String getCpCampaignIdList() {
    return cpCampaignIdList;
  }

  public void setCpCampaignIdList(String cpCampaignIdList) {
    this.cpCampaignIdList = cpCampaignIdList;
    put("cpCampaignIdList");
  }

   

/**
 * @return Returns the accountCashBalance1.
 */
public double getAccountCashBalance1() {
	return accountCashBalance1;
}
/**
 * @param accountCashBalance1 The accountCashBalance1 to set.
 */
public void setAccountCashBalance1(double accountCashBalance1) {
	this.accountCashBalance1 = accountCashBalance1;
	 put("accountCashBalance1");
}
/**
 * @return Returns the accountCashBalance2.
 */
public double getAccountCashBalance2() {
	return accountCashBalance2;
}
/**
 * @param accountCashBalance2 The accountCashBalance2 to set.
 */
public void setAccountCashBalance2(double accountCashBalance2) {
	this.accountCashBalance2 = accountCashBalance2;
	put("accountCashBalance2");
}
/**
 * @return Returns the accountDistrictIdList.
 */
public String getAccountDistrictIdList() {
	return accountDistrictIdList;
}
/**
 * @param accountDistrictIdList The accountDistrictIdList to set.
 */
public void setAccountDistrictIdList(String accountDistrictIdList) {
	this.accountDistrictIdList = accountDistrictIdList;
	put("accountDistrictIdList");
}
/**
 * @return Returns the accountTokenBalance1.
 */
public double getAccountTokenBalance1() {
	return accountTokenBalance1;
}
/**
 * @param accountTokenBalance1 The accountTokenBalance1 to set.
 */
public void setAccountTokenBalance1(double accountTokenBalance1) {
	this.accountTokenBalance1 = accountTokenBalance1;
	put("accountTokenBalance1");
}
/**
 * @return Returns the accountTokenBalance2.
 */
public double getAccountTokenBalance2() {
	return accountTokenBalance2;
}
/**
 * @param accountTokenBalance2 The accountTokenBalance2 to set.
 */
public void setAccountTokenBalance2(double accountTokenBalance2) {
	this.accountTokenBalance2 = accountTokenBalance2;
	put("accountTokenBalance2");
}
/**
 * @return Returns the cpCampaignEndTime1.
 */
public Timestamp getCpCampaignEndTime1() {
	return cpCampaignEndTime1;
}
/**
 * @param cpCampaignEndTime1 The cpCampaignEndTime1 to set.
 */
public void setCpCampaignEndTime1(Timestamp cpCampaignEndTime1) {
	this.cpCampaignEndTime1 = cpCampaignEndTime1;
	put("cpCampaignEndTime1");
}
/**
 * @return Returns the cpCampaignEndTime2.
 */
public Timestamp getCpCampaignEndTime2() {
	return cpCampaignEndTime2;
}
/**
 * @param cpCampaignEndTime2 The cpCampaignEndTime2 to set.
 */
public void setCpCampaignEndTime2(Timestamp cpCampaignEndTime2) {
	this.cpCampaignEndTime2 = cpCampaignEndTime2;
	put("cpCampaignEndTime2");
}
/**
 * @return Returns the cpCampaignStartTime1.
 */
public Timestamp getCpCampaignStartTime1() {
	return cpCampaignStartTime1;
}
/**
 * @param cpCampaignStartTime1 The cpCampaignStartTime1 to set.
 */
public void setCpCampaignStartTime1(Timestamp cpCampaignStartTime1) {
	this.cpCampaignStartTime1 = cpCampaignStartTime1;
	put("cpCampaignStartTime1");
}
 
/**
 * @return Returns the cpCampaignStartTime2.
 */
public Timestamp getCpCampaignStartTime2() {
	return cpCampaignStartTime2;
}
/**
 * @param cpCampaignStartTime2 The cpCampaignStartTime2 to set.
 */
public void setCpCampaignStartTime2(Timestamp cpCampaignStartTime2) {
	this.cpCampaignStartTime2 = cpCampaignStartTime2;
	put("cpCampaignStartTime2");
}
/**
 * @return Returns the custCurrentPoints1.
 */
public int getCustCurrentPoints1() {
	return custCurrentPoints1;
}
/**
 * @param custCurrentPoints1 The custCurrentPoints1 to set.
 */
public void setCustCurrentPoints1(int custCurrentPoints1) {
	this.custCurrentPoints1 = custCurrentPoints1;
	put("custCurrentPoints1");
}
/**
 * @return Returns the custCurrentPoints2.
 */
public int getCustCurrentPoints2() {
	return custCurrentPoints2;
}
/**
 * @param custCurrentPoints2 The custCurrentPoints2 to set.
 */
public void setCustCurrentPoints2(int custCurrentPoints2) {
	this.custCurrentPoints2 = custCurrentPoints2;
	put("custCurrentPoints2");
}
/**
 * @return Returns the customerId.
 */
public int getCustomerId() {
	return customerId;
}
/**
 * @param customerId The customerId to set.
 */
public void setCustomerId(int customerId) {
	this.customerId = customerId;
	put("customerId");
}
/**
 * @return Returns the custTotalPoints1.
 */
public int getCustTotalPoints1() {
	return custTotalPoints1;
}
/**
 * @param custTotalPoints1 The custTotalPoints1 to set.
 */
public void setCustTotalPoints1(int custTotalPoints1) {
	this.custTotalPoints1 = custTotalPoints1;
	put("custTotalPoints1");
}
/**
 * @return Returns the custTotalPoints2.
 */
public int getCustTotalPoints2() {
	return custTotalPoints2;
}
/**
 * @param custTotalPoints2 The custTotalPoints2 to set.
 */
public void setCustTotalPoints2(int custTotalPoints2) {
	this.custTotalPoints2 = custTotalPoints2;
	put("custTotalPoints2");
}
public String getTemplateFlag() {
    return templateFlag;
  }

public void setTemplateFlag(String templateFlag) {
    this.templateFlag = templateFlag;
    put("templateFlag");
  }
public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        QueryDTO that = (QueryDTO) obj;
        return ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
                 ( ( (this.getAccountDistrictIdList() == null) && (that.getAccountDistrictIdList() == null)) ||
                   (this.getAccountDistrictIdList() != null &&
                       this.getAccountDistrictIdList().equals(that.getAccountDistrictIdList()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
              this.getQueryId()== that.getQueryId() &&
              this.getCustCurrentPoints1()== that.getCustCurrentPoints1() &&
              this.getCustCurrentPoints2()== that.getCustCurrentPoints2() &&
            ( ( (this.getQueryName() == null) && (that.getQueryName() == null)) ||
             (this.getQueryName() != null &&
              this.getQueryName().equals(that.getQueryName()))) &&
            ( ( (this.getQueryDesc() == null) && (that.getQueryDesc() == null)) ||
             (this.getQueryDesc() != null &&
              this.getQueryDesc().equals(that.getQueryDesc()))) &&
            this.getCreateOperatorId() == that.getCreateOperatorId() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            ( ( (this.getScheduleType() == null) && (that.getScheduleType() == null)) ||
             (this.getScheduleType() != null &&
              this.getScheduleType().equals(that.getScheduleType()))) &&
            ( ( (this.getScheduleTime() == null) && (that.getScheduleTime() == null)) ||
             (this.getScheduleTime() != null &&
              this.getScheduleTime().equals(that.getScheduleTime()))) &&
            ( ( (this.getPerformTime() == null) && (that.getPerformTime() == null)) ||
             (this.getPerformTime() != null &&
              this.getPerformTime().equals(that.getPerformTime()))) &&
            ( ( (this.getQueryType() == null) && (that.getQueryType() == null)) ||
             (this.getQueryType() != null &&
              this.getQueryType().equals(that.getQueryType()
                                         ))) &&
            ( ( (this.getCustStatusList() == null) && (that.getCustStatusList() == null)) ||
             (this.getCustStatusList() != null &&
              this.getCustStatusList().equals(that.getCustStatusList()))) &&
            ( ( (this.getCustTypeList() == null) && (that.getCustTypeList() == null)) ||
             (this.getCustTypeList() != null &&
              this.getCustTypeList().equals(that.getCustTypeList()))) &&
            ( ( (this.getCustCreateTime1() == null) && (that.getCustCreateTime1() == null)) ||
             (this.getCustCreateTime1() != null &&
              this.getCustCreateTime1().equals(that.getCustCreateTime1()))) &&
           ( ( (this.getCustCreateTime2() == null) && (that.getCustCreateTime2() == null)) ||
            (this.getCustCreateTime2() != null &&
             this.getCustCreateTime2().equals(that.getCustCreateTime2()))) &&

            ( ( (this.getCustOpenSourceTypeList() == null) &&
               (that.getCustOpenSourceTypeList() == null)) ||
             (this.getCustOpenSourceTypeList() != null &&
              this.
              getCustOpenSourceTypeList().equals(that.getCustOpenSourceTypeList()))) &&
            ( ( (this.getCustOpenSourceIdList() == null) &&
               (that.getCustOpenSourceIdList() == null)) ||
             (this.getCustOpenSourceIdList() != null &&
              this.
              getCustOpenSourceIdList().equals(that.getCustOpenSourceIdList()))) &&
            ( ( (this.getCustName() == null) && (that.getCustName() ==
                                                 null)) ||
             (this.getCustName() != null &&
              this.getCustName().equals(that.getCustName()))) &&
              ( ( (this.getProductClassList() == null) && (that.getProductClassList() == null)) ||
                    (this.getProductClassList() != null && this.getProductClassList().equals(that.getProductClassList()))) &&
            ( ( (this.getCustAddress() == null) && (that.getCustAddress() == null)) ||
             (this.getCustAddress() != null &&
              this.getCustAddress().equals(that.getCustAddress()))) &&
            ( ( (this.getCustDistrictIdList() == null) &&
               (that.getCustDistrictIdList() == null)) ||
             (this.getCustDistrictIdList() != null &&
              this.getCustDistrictIdList().equals(that.getCustDistrictIdList()))) &&
            ( ( (this.getAccountStatusList() == null) &&
               (that.getAccountStatusList() == null)) ||
             (this.getAccountStatusList() != null &&
              this.getAccountStatusList().equals(that.getAccountStatusList()))) &&
            ( ( (this.getAccountTypeList() == null) && (that.getAccountTypeList() == null)) ||
             (this.getAccountTypeList() != null &&
              this.getAccountTypeList().equals(that.getAccountTypeList()))) &&
            this.getAccountCashBalance1() == that.getAccountCashBalance1() &&
             this.getAccountTokenBalance2() == that.getAccountTokenBalance2() &&
             this.getCustomerId() == that.getCustomerId() &&
            ( ( (this.getAccountMopIdList() == null) &&
               (that.getAccountMopIdList() == null)) ||
             (this.getAccountMopIdList() != null && this.getAccountMopIdList
              ().equals(that.getAccountMopIdList()))) &&
            ( ( (this.getAccountAddress() == null) && (that.getAccountAddress() == null)) ||
             (this.getAccountAddress() != null &&
              this.getAccountAddress().equals(that.getAccountAddress()))) &&
            ( ( (this.getAccountCreateTime1() == null) &&
               (that.getAccountCreateTime1() == null)) ||
             (this.getAccountCreateTime1() != null &&
              this.getAccountCreateTime1().equals(that.getAccountCreateTime1()))) &&
              ( ( (this.getBankAccountStatusList() == null) &&
                    (that.getBankAccountStatusList() == null)) ||
                  (this.getBankAccountStatusList() != null &&
                   this.getBankAccountStatusList().equals(that.getBankAccountStatusList()))) &&
            ( ( (this.getAccountCreateTime2() == null) &&
               (that.getAccountCreateTime2() == null)) ||
             (this.getAccountCreateTime2() != null &&
              this.getAccountCreateTime2().equals(that.getAccountCreateTime2()))) &&
            ( ( (this.getCpStatusList() == null) && (that.getCpStatusList() == null)) ||
             (this.getCpStatusList() != null &&
              this.getCpStatusList().equals(that.getCpStatusList()))) &&
            ( ( (this.getCpProductIdList() == null) && (that.getCpProductIdList() == null)) ||
             (this.getCpProductIdList() != null &&
              this.getCpProductIdList().equals(that.getCpProductIdList()))) &&
            ( ( (this.getCpCreateTime1() == null) &&
               (that.getCpCreateTime1() == null)) ||
             (this.getCpCreateTime1() != null &&
              this.getCpCreateTime1().equals(that.getCpCreateTime1()))) &&
            ( ( (this.getCpCreateTime2() == null) && (that.getCpCreateTime2() == null)) ||
             (this.getCpCreateTime2() != null &&
              this.getCpCreateTime2().equals(that.getCpCreateTime2()))) &&
            ( ( (this.getCpCampaignIdList() == null) &&
               (that.getCpCampaignIdList() == null)) ||
             (this.getCpCampaignIdList() != null &&
              this.getCpCampaignIdList().equals(that.getCpCampaignIdList()))) &&
            ( ( (this.getCpCampaignStartTime1() == null) && (that.getCpCampaignStartTime1() == null)) ||
             (this.getCpCampaignStartTime1() != null &&
              this.getCpCampaignStartTime1().equals(that.getCpCampaignStartTime1()))) &&
              ( ( (this.getCpCampaignEndTime1() == null) && (that.getCpCampaignEndTime1() == null)) ||
                    (this.getCpCampaignEndTime1() != null &&
                     this.getCpCampaignEndTime1().equals(that.getCpCampaignEndTime1()))) &&
               ( ( (this.getAccountInvoiceCreateTime1() == null) && (that.getAccountInvoiceCreateTime1() == null)) ||
                 (this.getAccountInvoiceCreateTime1() != null &&
                       this.getAccountInvoiceCreateTime1().equals(that.getAccountInvoiceCreateTime1()))) &&	
                ( ( (this.getAccountInvoiceCreateTime2() == null) && (that.getAccountInvoiceCreateTime2() == null)) ||
                (this.getAccountInvoiceCreateTime2() != null &&
                   this.getAccountInvoiceCreateTime2().equals(that.getAccountInvoiceCreateTime2()))) &&				 
                   ( ( (this.getAccountInvoiceStatusList() == null) && (that.getAccountInvoiceStatusList() == null)) ||
                        (this.getAccountInvoiceStatusList() != null &&
                           this.getAccountInvoiceStatusList().equals(that.getAccountInvoiceStatusList()))) &&					 
              ( ( (this.getCpCampaignEndTime2() == null) && (that.getCpCampaignEndTime2() == null)) ||
                   (this.getCpCampaignEndTime2() != null &&
                             this.getCpCampaignEndTime2().equals(that.getCpCampaignEndTime2()))) &&		 
            ( ( (this.getCpCampaignStartTime2() == null) && (that.getCpCampaignStartTime2() == null)) ||
             (this.getCpCampaignStartTime2() != null &&
             this.getCpCampaignStartTime2().equals(that.getCpCampaignStartTime2())))&&
             
             ( ( (this.getTemplateFlag() == null) && (that.getTemplateFlag() == null)) ||
                     (this.getTemplateFlag() != null &&
                      this.getTemplateFlag().equals(that.getTemplateFlag())))
             ;
      }
    }
    return false;
  }

  public int hashCode() {
    return toString().hashCode();
  }
  public String toString()
          {
                  StringBuffer buf = new StringBuffer(256);
                 
                  buf.append(",").append(queryId);
                  buf.append(",").append(queryName);
                  buf.append(",").append(queryDesc);
                  buf.append(",").append(createOperatorId);
                  buf.append(",").append(createTime);
                  buf.append(",").append(scheduleType);
                  buf.append(",").append(scheduleTime);
                  buf.append(",").append(performTime);
                  buf.append(",").append(status);
                  buf.append(",").append(queryType);
                  buf.append(",").append(custStatusList);
                  buf.append(",").append(custTypeList);
                  buf.append(",").append(custCreateTime1);
                  buf.append(",").append(custCreateTime2);
                  buf.append(",").append(custOpenSourceTypeList);
                  buf.append(",").append(custOpenSourceIdList);
                  buf.append(",").append(custName);
                  buf.append(",").append(custAddress);
                  buf.append(",").append(custDistrictIdList);
                  buf.append(",").append(accountStatusList);
                  buf.append(",").append(dtCreate);
                  buf.append(",").append(accountTypeList);
                  buf.append(",").append(dtLastmod);
                  buf.append(",").append(accountCashBalance1);
                  buf.append(",").append(accountCashBalance2);
                  buf.append(",").append(accountTokenBalance1);
                   buf.append(",").append(accountTokenBalance2);
                  buf.append(",").append(accountMopIdList);

                  buf.append(",").append(accountAddress);
                  buf.append(",").append(accountCreateTime1);
                  buf.append(",").append(accountCreateTime2);
                  buf.append(",").append(cpStatusList);

                  buf.append(",").append(cpProductIdList);
                  buf.append(",").append(cpCreateTime1);
                  buf.append(",").append(cpCreateTime2);
                  buf.append(",").append(cpCampaignIdList);
                  buf.append(",").append(cpCampaignStartTime1);
                  buf.append(",").append(cpCampaignStartTime2);
                  buf.append(",").append(cpCampaignEndTime1);
                  buf.append(",").append(cpCampaignEndTime2);
                  buf.append(",").append(accountDistrictIdList);
                  
                  buf.append(",").append(cpCampaignEndTime1);
                  buf.append(",").append(cpCampaignEndTime2);
                  buf.append(",").append(cpCampaignStartTime1);
                  buf.append(",").append(cpCampaignStartTime2);
                  buf.append(",").append(custCurrentPoints1);
                  buf.append(",").append(custCurrentPoints2);
                  buf.append(",").append(customerId);
                  buf.append(",").append(custTotalPoints1);
                  buf.append(",").append(custTotalPoints2);
                  buf.append(",").append(accountInvoiceCreateTime1);
                  buf.append(",").append(accountInvoiceCreateTime2);
                  buf.append(",").append(accountInvoiceStatusList);
                  buf.append(",").append(productClassList );
                  buf.append(",").append(bankAccountStatusList );
                  buf.append(",").append(templateFlag );
                  return buf.toString();
          }


          private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }


/**
 * @return Returns the accountInvoiceCreateTime1.
 */
public Timestamp getAccountInvoiceCreateTime1() {
	return accountInvoiceCreateTime1;
}
/**
 * @param accountInvoiceCreateTime1 The accountInvoiceCreateTime1 to set.
 */
public void setAccountInvoiceCreateTime1(Timestamp accountInvoiceCreateTime1) {
	this.accountInvoiceCreateTime1 = accountInvoiceCreateTime1;
	put("accountInvoiceCreateTime1");
}
/**
 * @return Returns the accountInvoiceCreateTime2.
 */
public Timestamp getAccountInvoiceCreateTime2() {
	return accountInvoiceCreateTime2;
}
/**
 * @param accountInvoiceCreateTime2 The accountInvoiceCreateTime2 to set.
 */
public void setAccountInvoiceCreateTime2(Timestamp accountInvoiceCreateTime2) {
	this.accountInvoiceCreateTime2 = accountInvoiceCreateTime2;
	put("accountInvoiceCreateTime2");
}
/**
 * @return Returns the accountInvoiceStatusList.
 */
public String getAccountInvoiceStatusList() {
	return accountInvoiceStatusList;
}
/**
 * @param accountInvoiceStatusList The accountInvoiceStatusList to set.
 */
public void setAccountInvoiceStatusList(String accountInvoiceStatusList) {
	this.accountInvoiceStatusList = accountInvoiceStatusList;
	put("accountInvoiceStatusList");
}
  }

