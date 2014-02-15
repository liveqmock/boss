package com.dtv.oss.service.ejbevent.logistics;

import java.util.Collection;

import com.dtv.oss.dto.VodstbDeviceImportHeadDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

/*
 * 使用的功能点:
 * 1 设备运入
 * 2 发票运入
 * 3 
 */

public class DevicePutIntoDepotEJBEvent extends LogisticsEJBEvent {

  private CommonQueryConditionDTO      commDTO;
  private VodstbDeviceImportHeadDTO    vodstbDeviceHead;
  public  Collection                   vodDevicePutInfoDepotList ;
  private boolean doPost;

  //构造函数
  public DevicePutIntoDepotEJBEvent(int actionType) {
		this.actionType = actionType;
  }
  
  public DevicePutIntoDepotEJBEvent(CommonQueryConditionDTO theDTO,int actionType){
	    this.commDTO = theDTO;
	    this.actionType = actionType;
  }
  
  public void setDevConDTO(CommonQueryConditionDTO theDTO) {
    this.commDTO = theDTO;
  }
  public CommonQueryConditionDTO getDevConDTO() {
    return commDTO;
  }

  public VodstbDeviceImportHeadDTO getVodstbDeviceHead() {
	return vodstbDeviceHead;
  }

  public void setVodstbDeviceHead(VodstbDeviceImportHeadDTO vodstbDeviceHead) {
	this.vodstbDeviceHead = vodstbDeviceHead;
  }

  public Collection getVodDevicePutInfoDepotList() {
	  return vodDevicePutInfoDepotList;
  }
  public void setVodDevicePutInfoDepotList(Collection vodDevicePutInfoDepotList) {
	  this.vodDevicePutInfoDepotList = vodDevicePutInfoDepotList;
  }

  public boolean isDoPost() {
	return doPost;
  }
  public void setDoPost(boolean doPost) {
	this.doPost = doPost;
  }
 
  
}