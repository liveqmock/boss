package com.dtv.oss.service.ejbevent.logistics;

import java.util.Collection;

import com.dtv.oss.dto.DeviceBatchPreauthDTO;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;


/**
 * 设备流转用的EJBEvent
 * author     ：zhouxushun 
 * date       : 2005-11-30
 * description:
 * @author 250713z
 *
 */
public class DeviceStockEJBEvent extends LogisticsEJBEvent {
	
	//存放设备授权操作、配对操作的日志描述信息头记录的DTO
	private DeviceBatchPreauthDTO devBatchPreauthDTO;	

	//存放设备流转信息的DTO
	private DeviceTransitionDTO devTransDTO;
	
//	存放设备信息的DTO
	private TerminalDeviceDTO terDeviceDTO;
	
	//存放此次流转包含的硬件设备号
	private Collection devArray;
	
	// 设备状态的目的状态
	private String toStatus;
	
	//马上提交还是只是预检
	private boolean doPost;
	
	//存放此次流转包含的硬件设备号 与上面的devArray作用是一样的,类型不同,为了与有些功能的后台兼容
	private String devArrayStr;
	
	/**
	 * 空的构造函数
	 */
	public DeviceStockEJBEvent(){
	
	}

	  /**
	   * 构造函数1：
	   * @param aActionType 操作类型
	   *      public static final int DEVICE_IN_STOCK = 11;					//入库
	   *      public static final int DEVICE_OUT_STOCK = 12;				//出库
	   *      public static final int DEVICE_INVALIDATE = 13;				//报废
	   *      public static final int DEVICE_REPAIR = 14;					//送修
	   *      public static final int DEVICE_TRANSITION = 15;				//调拨
	   * @param aDevTransDTO
	   *      com.dtv.oss.dto.scnd.DeviceTransitionDTO
	   * @param aDevArray
	   *      ArrayList of Integer Object，包含T_TerminalDevice.DeviceID
	   * @param aPost
	   *      保留参数，目前无用
	   */
	  public DeviceStockEJBEvent(int aActionType,
	                DeviceTransitionDTO aDevTransDTO,
	                Collection aDevArray,
	                boolean aDoPost){

	    this.devTransDTO = aDevTransDTO;
	    this.devArray = aDevArray;
	    this.actionType = aActionType;
	    this.doPost = aDoPost;

	  }

	  /**
	   * 构造函数2：用于手工修改设备状态
	   * @param aActionType 操作类型
	   *      public static final int DEVICE_MANULCHANGE_STATUS = 16;//手工修改状态
	   * @param aDevTransDTO
	   *      com.dtv.oss.dto.scnd.DeviceTransitionDTO
	   * @param aDevArray
	   *      ArrayList of Integer Object，包含T_TerminalDevice.DeviceID
	   * @param toStatus
	   *      String,T_DeviceTransitionDetail.toStatus的值，设备状态要变更的目的状态
	   * @param aDoPost
	   *      保留参数，目前无用
	   */

	  public DeviceStockEJBEvent(int aActionType,
	                DeviceTransitionDTO aDevTransDTO,
	                Collection aDevArray,
	                String toStatus,
	                boolean aDoPost){

	    this.devTransDTO = aDevTransDTO;
	    this.devArray = aDevArray;
	    this.actionType = aActionType;
	    this.doPost = aDoPost;
	    this.toStatus = toStatus;
	    

	  }


	 

	public Collection getDeviceArray()
	  {
	   return this.devArray;
	  }
	  public void setDeviceArray(Collection aDevArray)
	  {
	    this.devArray = aDevArray;
	  }

	  public DeviceTransitionDTO getDeviceTransitionDTO(){
	    return this.devTransDTO;
	  }

	  public String getToStatus() {
	    return this.toStatus;
	  }

	  public int getActionType(){
	    return this.actionType;
	  }


	public Collection getDevArray() {
		return devArray;
	}

	public void setDevArray(Collection devArray) {
		this.devArray = devArray;
	}


	public DeviceTransitionDTO getDevTransDTO() {
		return devTransDTO;
	}


	public void setDevTransDTO(DeviceTransitionDTO devTransDTO) {
		this.devTransDTO = devTransDTO;
	}
	public TerminalDeviceDTO getTerDeviceDTO() {
		return terDeviceDTO;
	}


	public void setTerDeviceDTO(TerminalDeviceDTO terDeviceDTO) {
		this.terDeviceDTO = terDeviceDTO;
	}


	public boolean isDoPost() {
		return doPost;
	}


	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
	}
	
	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

	public void setDevArrayStr(String devArrayStr) {
		this.devArrayStr = devArrayStr;
	}
	
	 public String getDevArrayStr() {
		return this.devArrayStr;
	}
	public void setDeviceBatchPreauthDTO(DeviceBatchPreauthDTO devBatchPreauthDTO)
        {
		this.devBatchPreauthDTO = devBatchPreauthDTO;
        }
        public DeviceBatchPreauthDTO getDeviceBatchPreauthDTO()
	{
		return  this.devBatchPreauthDTO;
	}

	
}