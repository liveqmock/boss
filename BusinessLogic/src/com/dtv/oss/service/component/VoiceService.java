package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.VoiceFriendPhoneNo;
import com.dtv.oss.domain.VoiceFriendPhoneNoHome;
import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.voice.VoiceEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class VoiceService extends AbstractService {
	private ServiceContext context = null;

	public VoiceService() {
		super();
	}

	public VoiceService(ServiceContext context) {
		super();
		this.context = context;
	}
	/**
	 * 增加亲情号码
	 * @param inEvent
	 */
	public void createFriendPhoneNo(VoiceFriendPhoneNoDTO dto)throws ServiceException{
		try{
			VoiceFriendPhoneNoHome voiceFriendPhoneNoHome=HomeLocater.getVoiceFriendPhoneNoHome();	
			voiceFriendPhoneNoHome.create(dto);
		}catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "新增亲情号码定位出错");
			throw new ServiceException("新增亲情号码定位出错");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "新增亲情号码出错");
			throw new ServiceException("新增亲情号码出错！");
		}
	}
	/**
	 * 删除亲情号码
	 * @param rets
	 * @throws ServiceException
	 */
	public String deleteFriendPhoneNo(String seqID)throws ServiceException{
		try{
			VoiceFriendPhoneNoHome voiceFriendPhoneNoHome=HomeLocater.getVoiceFriendPhoneNoHome();	
			int j;
			String temp;
			String [] rets=seqID.split(";");
			String phoneNo="";
			for(int i=0;i<rets.length;i++){
				temp=rets[i];	
				j=Integer.valueOf(temp).intValue();
				VoiceFriendPhoneNo voiceFriendPhoneNo=voiceFriendPhoneNoHome.findByPrimaryKey(new Integer(j));
				phoneNo=phoneNo + voiceFriendPhoneNo.getPhoneNo()+";";
				voiceFriendPhoneNo.remove();
			}
			return phoneNo;
			}catch (HomeFactoryException e) {
				e.printStackTrace();
				LogUtility.log(this.getClass(), LogLevel.ERROR, "定位要删除的亲情号码出错");
				throw new ServiceException("定位要删除的亲情号码出错");
			}catch(FinderException e) {			
				e.printStackTrace();
				LogUtility.log(this.getClass(), LogLevel.ERROR, "查询要删除的亲情号码出错");
				throw new ServiceException("查询要删除的亲情号码出错");			
			}catch(RemoveException e) {
			    LogUtility.log(this.getClass(),LogLevel.ERROR,"deleteFriendPhoneNo",e);
			    throw new ServiceException("删除亲情号码出错");
			}
		}
	
}
