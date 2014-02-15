package com.dtv.oss.service.ejbevent.logistics;

import java.util.Collection;

import com.dtv.oss.dto.DeviceBatchPreauthDTO;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;


/**
 * �豸��ת�õ�EJBEvent
 * author     ��zhouxushun 
 * date       : 2005-11-30
 * description:
 * @author 250713z
 *
 */
public class DeviceStockEJBEvent extends LogisticsEJBEvent {
	
	//����豸��Ȩ��������Բ�������־������Ϣͷ��¼��DTO
	private DeviceBatchPreauthDTO devBatchPreauthDTO;	

	//����豸��ת��Ϣ��DTO
	private DeviceTransitionDTO devTransDTO;
	
//	����豸��Ϣ��DTO
	private TerminalDeviceDTO terDeviceDTO;
	
	//��Ŵ˴���ת������Ӳ���豸��
	private Collection devArray;
	
	// �豸״̬��Ŀ��״̬
	private String toStatus;
	
	//�����ύ����ֻ��Ԥ��
	private boolean doPost;
	
	//��Ŵ˴���ת������Ӳ���豸�� �������devArray������һ����,���Ͳ�ͬ,Ϊ������Щ���ܵĺ�̨����
	private String devArrayStr;
	
	/**
	 * �յĹ��캯��
	 */
	public DeviceStockEJBEvent(){
	
	}

	  /**
	   * ���캯��1��
	   * @param aActionType ��������
	   *      public static final int DEVICE_IN_STOCK = 11;					//���
	   *      public static final int DEVICE_OUT_STOCK = 12;				//����
	   *      public static final int DEVICE_INVALIDATE = 13;				//����
	   *      public static final int DEVICE_REPAIR = 14;					//����
	   *      public static final int DEVICE_TRANSITION = 15;				//����
	   * @param aDevTransDTO
	   *      com.dtv.oss.dto.scnd.DeviceTransitionDTO
	   * @param aDevArray
	   *      ArrayList of Integer Object������T_TerminalDevice.DeviceID
	   * @param aPost
	   *      ����������Ŀǰ����
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
	   * ���캯��2�������ֹ��޸��豸״̬
	   * @param aActionType ��������
	   *      public static final int DEVICE_MANULCHANGE_STATUS = 16;//�ֹ��޸�״̬
	   * @param aDevTransDTO
	   *      com.dtv.oss.dto.scnd.DeviceTransitionDTO
	   * @param aDevArray
	   *      ArrayList of Integer Object������T_TerminalDevice.DeviceID
	   * @param toStatus
	   *      String,T_DeviceTransitionDetail.toStatus��ֵ���豸״̬Ҫ�����Ŀ��״̬
	   * @param aDoPost
	   *      ����������Ŀǰ����
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