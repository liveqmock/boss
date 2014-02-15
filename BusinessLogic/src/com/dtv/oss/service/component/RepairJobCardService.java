/*
 * Created on 2005-9-22
 *
 * @author whq
 */
package com.dtv.oss.service.component;

import java.util.Iterator;

import javax.ejb.CreateException;

import com.dtv.oss.domain.DiagnosisInfoHome;
import com.dtv.oss.domain.MaterialUsageHome;
import com.dtv.oss.dto.DiagnosisInfoDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.MaterialUsageDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.*;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.dto.CustomerProblemDTO;

public class RepairJobCardService extends JobCardService {
    private static final Class clazz = RepairJobCardService.class;
    
    public RepairJobCardService(ServiceContext context) {
        super(context);
    }
    
    public RepairJobCardService(int jobCardNo, ServiceContext context) {
        super(jobCardNo, context);
    }
    
    
    
    public void setJobCardType(JobCardDTO jcDto) throws ServiceException {
        jcDto.setJobType(CommonConstDefinition.JOBCARD_TYPE_REPAIR);

    }
    public void relateWithOriginalSheet(Object cps) throws ServiceException {
        load();
        if (!(cps instanceof CustomerProblemService)) throw new IllegalArgumentException("Parameter of relateWithOriginalSheet() is not proper");
        CustomerProblemDTO dto = ((CustomerProblemService)cps).getDTO();
        jc.setReferSheetId(dto.getId());
        jc.setAddressId(dto.getAddressID());
        jc.setCustId(dto.getCustID());
        jc.setContactPerson(dto.getContactPerson());
        jc.setContactPhone(dto.getContactphone());
        jc.setCustServiceAccountId(dto.getCustServiceAccountID());
        jc.setDueDate(dto.getDueDate());
        jc.setDtLastmod(TimestampUtility.getCurrentTimestamp());
    }
    
    public void recordDiagnosisInfo(java.util.Collection diagnosisInfoDtos) throws ServiceException {
        load();
        super.recordDiagnosisInfo(diagnosisInfoDtos);
        Iterator it = diagnosisInfoDtos.iterator();
        while (it.hasNext()) {
            DiagnosisInfoDTO diDto = (DiagnosisInfoDTO)it.next();
            diDto.setReferSourceType(CommonConstDefinition.DIAGNOSISINFOTYPE_A);
            diDto.setReferSourceId(jc.getReferSheetId());
            try {
                DiagnosisInfoHome home = HomeLocater.getDiagnosisInfoHome();
                home.create(diDto);
            } catch (HomeFactoryException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("创建诊断信息记录时出错。");
            } catch (CreateException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("创建诊断信息记录时出错。");
            }
            
        }        
    }
    
    public Object getOriginalSheet() throws ServiceException {
        load();
        CustomerProblemService cps = new CustomerProblemService(jc.getReferSheetId(), context);
        
        return cps;
    }
    
    //在子类实现中增加对工单类型的判断
    protected void load() throws ServiceException {
        super.load();
        if (jc.getJobType().equals(CommonConstDefinition.JOBCARD_TYPE_REPAIR) == false)
            throw new ServiceException("只有维修工单才能进行此项操作！");
    }
    
    public static void main(String[] args) {
    }
}


