/*
 * Created on 2005-11-5
 *
 * @author whq
 */
package com.dtv.oss.service.component;

import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.domain.*;
import com.dtv.oss.service.*;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.util.*;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.dto.*;
import com.dtv.oss.service.Service;

public class CustomerRelationConfigService {
    private static final Class clazz = CustomerRelationConfigService.class;
    /*********************************设置诊断参数相关方法start*****************************************************/
    //新增诊断参数
    public static int createDiagnosisParameter(DiagnosisInfoDTO dpDto) throws ServiceException {
        if (dpDto == null)
            throw new IllegalArgumentException("dpDto is not set properly.");
        try {
        	DiagnosisInfoHome dpHome = HomeLocater.getDiagnosisInfoHome();
        	DiagnosisInfo dp = dpHome.create(dpDto);
            return dp.getId().intValue();
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建诊断参数时出错");
        } catch (CreateException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建诊断参数时出错");
        }
	}
    
    //更新诊断参数
    public static void updateDiagnosisParameter(DiagnosisInfoDTO dpDto) throws ServiceException {
        try {
            if ((dpDto == null) || (dpDto.getId() == 0))
                throw new IllegalArgumentException("dpDto is not set properly.");
            
            DiagnosisInfoHome dpHome = HomeLocater.getDiagnosisInfoHome();
            DiagnosisInfo dp = dpHome.findByPrimaryKey(new Integer(dpDto.getId()));
            if (dp.ejbUpdate(dpDto) == -1)
                throw new RuntimeException("DiagnosisParameter update fail, ID:"+dpDto.getId());
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("更新诊断参数时出错，ID："+dpDto.getId());
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("更新诊断参数时出错，ID："+dpDto.getId());
        }
	}    
    
    //删除诊断参数
    public static void deleteDiagnosisParameter(int dpID) throws ServiceException {
        try {
            if (dpID == 0)
                throw new IllegalArgumentException("dpID is not set properly.");
            
            DiagnosisInfoHome dpHome = HomeLocater.getDiagnosisInfoHome();
            DiagnosisInfo dp = dpHome.findByPrimaryKey(new Integer(dpID));
            dp.remove();
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("删除诊断参数时出错，ID："+dpID);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("删除诊断参数时出错，ID："+dpID);
        } catch (RemoveException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("删除诊断参数时出错，ID："+dpID);
        }
	}     
    /*********************************设置诊断参数相关方法end*****************************************************/
    
    /*********************************设置回访信息相关方法start*****************************************************/
    public static int createCallBackInfoValue(BidimConfigSettingValueDTO cbivDto) throws ServiceException {
        if (cbivDto.getSettingId() == 0)
            throw new IllegalArgumentException("cbivDto is not set properly.");
        try {
            int settingId = cbivDto.getSettingId();
            if (settingId==0 || 
                (BusinessUtility.isValidSettingID(settingId) == false))
                throw new IllegalArgumentException("cbivDto中的settingID设置不正确。");
            
            BidimConfigSettingValueHome cbivHome = HomeLocater.getBidimConfigSettingValueHome();
            BidimConfigSettingValue cbiv = cbivHome.create(cbivDto);
            return cbiv.getId().intValue();
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建CallBackInfoValue信息时出错");
        } catch (CreateException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建CallBackInfoValue信息时出错");
        }
	}
    
    public static void updateCallBackInfoValue(BidimConfigSettingValueDTO cbivDto) throws ServiceException {
        try {
            if ((cbivDto == null) || (cbivDto.getId() == 0))
                throw new IllegalArgumentException("cbivDto is not set properly.");
            
            BidimConfigSettingValueHome cbivHome = HomeLocater.getBidimConfigSettingValueHome();
            BidimConfigSettingValue cbiv = cbivHome.findByPrimaryKey(new Integer(cbivDto.getId()));
//            if (cbiv.ejbUpdate(cbivDto) == -1)
//                throw new RuntimeException("CallBackInfoValue update fail, ID:"+cbivDto.getId());
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找CallBackInfoValue信息时出错，ID："+cbivDto.getId());
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找CallBackInfoValue信息时出错，ID："+cbivDto.getId());
        }
	}

    public static void deleteCallBackInfoValue(int cbivId) throws ServiceException {
        try {
            if (cbivId == 0)
                throw new IllegalArgumentException("cbivId is not set properly.");
            
            BidimConfigSettingValueHome cbivHome = HomeLocater.getBidimConfigSettingValueHome();
            BidimConfigSettingValue cbiv = cbivHome.findByPrimaryKey(new Integer(cbivId));
            cbiv.remove();
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找CallBackInfoValue信息时出错，ID："+cbivId);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找CallBackInfoValue信息时出错，ID："+cbivId);
        } catch (RemoveException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("删除CallBackInfoValue信息时出错，ID："+cbivId);
        }
	}
    
    public static int createCallBackInfoSetting(BidimConfigSettingDTO cbiDto) throws ServiceException {
        try {
        	BidimConfigSettingHome cbiHome = HomeLocater.getBidimConfigSettingHome();
        	BidimConfigSetting cbi = cbiHome.create(cbiDto);
            return cbi.getId().intValue();
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建回访信息时出错");
        } catch (CreateException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建回访信息时出错");
        }
	}
    
    public static void updateCallBackInfoSetting(BidimConfigSettingDTO cbiDto) throws ServiceException {
        if (cbiDto.getId() == 0)
            throw new IllegalArgumentException("cbiDto中的Id没有设置。");
        try {
        	BidimConfigSettingHome cbiHome = HomeLocater.getBidimConfigSettingHome();
        	BidimConfigSetting cbi = cbiHome.findByPrimaryKey(new Integer(cbiDto.getId()));
//            if (cbi.ejbUpdate(cbiDto) == -1)
//                throw new RuntimeException("CallBackInfoSetting update fail, ID:"+cbiDto.getId());

        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找回访信息时出错，回访信息ID："+cbiDto.getId());
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找回访信息时出错，回访信息ID："+cbiDto.getId());
        }
	}
    
    /*
     * 删除T_CallbackInfoSetting信息前必须先删除和其关联的T_CallbackInfoValue信息
     * @param 
     * cbisId: CallBackInfoSetting记录的Id
     */
    public static void deleteCallBackInfoSetting(int cbisId) throws ServiceException {
        if (cbisId == 0)
            throw new IllegalArgumentException("cbiDto中的Id没有设置。");
        try {
            
            //remove all CallBackInfoValue related with this CallBackInfoSetting record
//            CallBackInfoValueHome cbivHome = HomeLocater.getCallBackInfoValueHome();
//            Collection cbivs = cbivHome.findBySettingID(cbisId);
//            Iterator it = cbivs.iterator();
//            while (it.hasNext()) {
//                CallBackInfoValue cbiv = (CallBackInfoValue)it.next();
//                cbiv.remove();
//            }
            
        	BidimConfigSettingHome cbiHome = HomeLocater.getBidimConfigSettingHome();
        	BidimConfigSetting cbi = cbiHome.findByPrimaryKey(new Integer(cbisId));
            cbi.remove();
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找回访信息时出错，回访信息ID："+cbisId);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找回访信息时出错，回访信息ID："+cbisId);
        } catch (RemoveException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("删除回访信息时出错，回访信息ID："+cbisId);
        }
	}
    /*********************************设置回访信息相关方法end*****************************************************/
    
    /*********************************创建消息相关方法start*****************************************************/
    /*
     * 创建消息
     * @param
     * smDto: SendMessageDTO
     * operatorID
     * @return messageID
     */    
    public static int createMessageForEachCustomer(SendMessageDTO smDto, int operatorID) throws ServiceException {
        smDto.setOperatorId(operatorID);
        smDto.setOrgId(new OperatorService(null, operatorID).getOrgID());
        smDto.setCreateTime(TimestampUtility.getCurrentDate());
        smDto.setSourceType(CommonConstDefinition.SENDMESSAGESOURCETYPE_MANU);
        smDto.setStatus(CommonConstDefinition.SENDMESSAGESTATUS_NEW);
        try {
            SendMessageHome smHome = HomeLocater.getSendMessageHome();
            SendMessage sm = smHome.create(smDto);
            return sm.getMessageId().intValue();
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建消息时出错");
        } catch (CreateException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建消息时出错");
        }
        
    }    
    /*********************************创建消息相关方法start*****************************************************/
    
	public static void main(String[] args) {
    }
	
	private class CallBackInfoSettingService {
	    
	}	
}

