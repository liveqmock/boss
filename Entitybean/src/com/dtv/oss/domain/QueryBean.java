package com.dtv.oss.domain;

import javax.ejb.*;
import com.dtv.oss.dto.QueryDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class QueryBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer queryId) throws CreateException {
   // setQueryId(queryId);
    return null;
  }
  public java.lang.Integer ejbCreate(QueryDTO dto) throws CreateException {
    setQueryName(dto.getQueryName());
    setQueryDesc(dto.getQueryDesc());
    setStatus(dto.getStatus());
    setCreateOperatorId(dto.getCreateOperatorId());
    setCreateTime(dto.getCreateTime());
    setScheduleType(dto.getScheduleType());
    setScheduleTime(dto.getScheduleTime());
    setPerformTime(dto.getPerformTime());
    setQueryType(dto.getQueryType());
    setCustStatusList(dto.getCustStatusList());
   setCustTypeList(dto.getCustTypeList());
   setCustCreateTime1(dto.getCustCreateTime1());
   setCustCreateTime2(dto.getCustCreateTime2());
   setCustOpenSourceTypeList(dto.getCustOpenSourceTypeList());
   setCustOpenSourceIdList(dto.getCustOpenSourceIdList());
   setCustName(dto.getCustName());
   setCustAddress(dto.getCustAddress());
   setCustDistrictIdList(dto.getCustDistrictIdList());
   setAccountStatusList(dto.getAccountStatusList());
   setAccountTypeList(dto.getAccountTypeList());
   setAccountMopIdList(dto.getAccountMopIdList());
   setAccountAddress(dto.getAccountAddress());
   setAccountCreateTime1(dto.getAccountCreateTime1());
   setAccountCreateTime2(dto.getAccountCreateTime2());
   setCpStatusList(dto.getCpStatusList());
   setCpProductIdList(dto.getCpProductIdList());
   setCpCreateTime1(dto.getCpCreateTime1());
   setCpCreateTime2(dto.getCpCreateTime2());
   setCpCampaignIdList(dto.getCpCampaignIdList());
   setAccountCashBalance1(dto.getAccountCashBalance1());
   setAccountCashBalance2(dto.getAccountCashBalance2()); 
   setAccountTokenBalance1(dto.getAccountTokenBalance1());
   setAccountTokenBalance2(dto.getAccountTokenBalance2()); 
   setCpCampaignEndTime1(dto.getCpCampaignEndTime1());
   setCpCampaignEndTime2(dto.getCpCampaignEndTime2()); 
   setCpCampaignStartTime1(dto.getCpCampaignStartTime1());
   setCpCampaignStartTime2(dto.getCpCampaignStartTime2()); 
   setAccountDistrictIdList(dto.getAccountDistrictIdList()); 
   setCustCurrentPoints1(dto.getCustCurrentPoints1()); 
   setCustCurrentPoints2(dto.getCustCurrentPoints2()); 
   setCustTotalPoints1(dto.getCustTotalPoints1()); 
   setCustTotalPoints2(dto.getCustTotalPoints2()); 
   setCustomerId(dto.getCustomerId()); 
   setAccountInvoiceCreateTime1(dto.getAccountInvoiceCreateTime1()); 
   setAccountInvoiceCreateTime2(dto.getAccountInvoiceCreateTime2()); 
   setAccountInvoiceStatusList(dto.getAccountInvoiceStatusList()); 
   setProductClassList(dto.getProductClassList()); 
   setBankAccountStatusList(dto.getBankAccountStatusList()); 
  setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
  setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
  setTemplateFlag(dto.getTemplateFlag());

    /**@todo Complete this method*/
    return null;
  }
  public void ejbPostCreate(java.lang.Integer queryId) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(QueryDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setQueryId(java.lang.Integer queryId);
  public abstract void setQueryName(java.lang.String queryName);
  public abstract void setQueryDesc(java.lang.String queryDesc);
  public abstract void setStatus(java.lang.String status);
  public abstract void setCreateOperatorId(int createOperatorId);
  public abstract void setCreateTime(java.sql.Timestamp createTime);
  public abstract void setScheduleType(java.lang.String scheduleType);
  public abstract void setScheduleTime(java.sql.Timestamp scheduleTime);
  public abstract void setPerformTime(java.sql.Timestamp performTime);
  public abstract void setQueryType(java.lang.String queryType);
  public abstract void setCustStatusList(java.lang.String custStatusList);
  public abstract void setCustTypeList(java.lang.String custTypeList);
  public abstract void setCustCreateTime1(java.sql.Timestamp custCreateTime1);
  public abstract void setCustCreateTime2(java.sql.Timestamp custCreateTime2);
  public abstract void setCustOpenSourceTypeList(java.lang.String custOpenSourceTypeList);
  public abstract void setCustOpenSourceIdList(java.lang.String custOpenSourceIdList);
  public abstract void setCustName(java.lang.String custName);
  public abstract void setCustAddress(java.lang.String custAddress);
  public abstract void setCustDistrictIdList(java.lang.String custDistrictIdList);
  public abstract void setAccountStatusList(java.lang.String accountStatusList);
  public abstract void setAccountTypeList(java.lang.String accountTypeList);
  
  public abstract void setAccountMopIdList(java.lang.String accountMopIdList);
  public abstract void setAccountAddress(java.lang.String accountAddress);
  public abstract void setAccountCreateTime1(java.sql.Timestamp accountCreateTime1);
  public abstract void setAccountCreateTime2(java.sql.Timestamp accountCreateTime2);
  public abstract void setCpStatusList(java.lang.String cpStatusList);
  public abstract void setCpProductIdList(java.lang.String cpProductIdList);
  public abstract void setCpCreateTime1(java.sql.Timestamp cpCreateTime1);
  public abstract void setCpCreateTime2(java.sql.Timestamp cpCreateTime2);
  public abstract void setCpCampaignIdList(java.lang.String cpCampaignIdList);
  
  public abstract void setProductClassList(java.lang.String productClassList);
  public abstract void setBankAccountStatusList(java.lang.String bankAccountStatusList);
  
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getQueryId();
  public abstract java.lang.String getQueryName();
  public abstract java.lang.String getQueryDesc();
  public abstract java.lang.String getStatus();
  public abstract int getCreateOperatorId();
  public abstract java.sql.Timestamp getCreateTime();
  public abstract java.lang.String getScheduleType();
  public abstract java.sql.Timestamp getScheduleTime();
  public abstract java.sql.Timestamp getPerformTime();
  public abstract java.lang.String getQueryType();
  public abstract java.lang.String getCustStatusList();
  public abstract java.lang.String getCustTypeList();
  public abstract java.sql.Timestamp getCustCreateTime1();
  public abstract java.sql.Timestamp getCustCreateTime2();
  public abstract java.lang.String getCustOpenSourceTypeList();
  public abstract java.lang.String getCustOpenSourceIdList();
  public abstract java.lang.String getCustName();
  public abstract java.lang.String getCustAddress();
  public abstract java.lang.String getCustDistrictIdList();
  public abstract java.lang.String getAccountStatusList();
  public abstract java.lang.String getAccountTypeList();
   
  public abstract java.lang.String getAccountMopIdList();
  public abstract java.lang.String getAccountAddress();
  public abstract java.sql.Timestamp getAccountCreateTime1();
  public abstract java.sql.Timestamp getAccountCreateTime2();
  public abstract java.lang.String getCpStatusList();
  public abstract java.lang.String getCpProductIdList();
  public abstract java.sql.Timestamp getCpCreateTime1();
  public abstract java.sql.Timestamp getCpCreateTime2();
  public abstract java.lang.String getCpCampaignIdList();
  public abstract java.lang.String getProductClassList();
  public abstract java.lang.String getBankAccountStatusList(); 
  
  public abstract java.sql.Timestamp getDtCreate();
  public abstract java.sql.Timestamp getDtLastmod();
  public abstract double getAccountCashBalance1();
  public abstract double getAccountCashBalance2(); 
  public abstract double getAccountTokenBalance1();
  public abstract double getAccountTokenBalance2(); 
  public abstract java.sql.Timestamp getCpCampaignEndTime1();
  public abstract java.sql.Timestamp getCpCampaignEndTime2(); 
  public abstract java.sql.Timestamp getCpCampaignStartTime1();
  public abstract java.sql.Timestamp getCpCampaignStartTime2(); 
  public abstract java.lang.String getAccountDistrictIdList(); 
  public abstract int getCustCurrentPoints1(); 
  public abstract int getCustCurrentPoints2(); 
  public abstract int getCustTotalPoints1(); 
  public abstract int getCustTotalPoints2(); 
  public abstract int getCustomerId(); 
  
  public abstract java.sql.Timestamp getAccountInvoiceCreateTime1(); 
  public abstract java.sql.Timestamp getAccountInvoiceCreateTime2(); 
  public abstract String getAccountInvoiceStatusList(); 
  public abstract void setAccountInvoiceCreateTime1(java.sql.Timestamp accountInvoiceCreateTime1); 
  public abstract void setAccountInvoiceCreateTime2(java.sql.Timestamp accountInvoiceCreateTime2); 
  public abstract void setAccountInvoiceStatusList(String accountInvoiceStatusList); 
  public abstract void setAccountCashBalance1(double accountCashBalance1);
  public abstract void setAccountCashBalance2(double accountCashBalance2); 
  public abstract void setAccountTokenBalance1(double accountTokenBalance1);
  public abstract void setAccountTokenBalance2(double accountTokenBalance2); 
  public abstract void setCpCampaignEndTime1(java.sql.Timestamp cpCampaignEndTime1);
  public abstract void setCpCampaignEndTime2(java.sql.Timestamp cpCampaignEndTime2);
  public abstract void setCpCampaignStartTime1(java.sql.Timestamp cpCampaignStartTime1);
  public abstract void setCpCampaignStartTime2(java.sql.Timestamp cpCampaignStartTime2);
  public abstract void setAccountDistrictIdList(String accountDistrictIdList); 
  public abstract void setCustCurrentPoints1(int custCurrentPoints1); 
  public abstract void setCustCurrentPoints2(int custCurrentPoints2);
  public abstract void setCustTotalPoints1(int custTotalPoints1);  
  public abstract void setCustTotalPoints2(int custTotalPoints2); 
  public abstract void setCustomerId(int customerId); 
  public abstract void setTemplateFlag(String templateFlag); 
  public abstract java.lang.String getTemplateFlag();
  public void ejbLoad() {
    /**@todo Complete this method*/
  }
  public void ejbStore() {
    /**@todo Complete this method*/
  }
  public void ejbActivate() {
    /**@todo Complete this method*/
  }
  public void ejbPassivate() {
    /**@todo Complete this method*/
  }
  public void unsetEntityContext() {
    this.entityContext = null;
  }
  public void setEntityContext(EntityContext entityContext) {
    this.entityContext = entityContext;
  }
   
  public int ejbUpdate(QueryDTO dto) {
    if (dto.getDtLastmod() == null) {
                          return -1;
                  }
    if (!dto.getDtLastmod().equals(getDtLastmod())) {

                          return -1;
                  } else {
                          try {
                                  EntityBeanAutoUpdate.update(dto, this);
                          } catch (Exception e) {
                                  e.printStackTrace();
                                  return -1;
                          }
                          setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
                          return 0;
                  }
  }

  }
