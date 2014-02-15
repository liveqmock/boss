/*
 * Created on 2005-11-5
 *
 * @author whq
 */
package com.dtv.oss.service.ejbevent.work;

import java.util.Collection;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.dto.*;

/*
 * 接口说明：
 * 1、创建消息
 * 需设置SendMessageDTO的集合，其中每个SendMessageDTO需设置customerID，serviceAccountID,发送类型,消息内容
 * 2、新增回访信息设置
 * 需设置CallBackInfoSettingDTO：cbisDto
 * 3、更新回访信息设置
 * 需设置CallBackInfoSettingDTO:cbisDto以及ID（通过CallBackInfoSettingDTO的Id字段）
 * 4、删除回访信息设置
 * 需设置IDs：将要删除的记录的ID的集合（Integer的集合形式）
 * 5、新增CallBackInfoValue
 * 需设置CallBackInfoValueDTO：cbivDto,其中settingID必须设置
 * 6、更新CallBackInfoValue
 * 需设置CallBackInfoValueDTO:cbivDto以及ID（通过CallBackInfoValueDTO的Id字段）
 * 7、删除CallBackInfoValue
 * 需设置IDs：将要删除的记录的ID的集合（Integer的集合形式）
 * 8、新增诊断参数
 * 需设置DiagnosisParameterDTO：dpDto
 * 9、更新诊断参数
 * 需设置DiagnosisParameterDTO:dpDto以及ID（通过DiagnosisParameterDTO的Id字段）
 * 10、删除诊断参数
 * 需设置IDs：将要删除的记录的ID的集合（Integer的集合形式）
 */
public class CustomerRelationConfigEJBEvent extends AbstractEJBEvent {
    private Collection sendMessageDtos;
    private BidimConfigSettingDTO cbisDto;
    private BidimConfigSettingValueDTO cbivDto;
    private DiagnosisInfoDTO dpDto;
    private Collection IDs;		//删除记录（包括T_CallbackInfoSetting,T_CallbackInfoValue）时，记录ID的集合(Collection of Integer)
    
    public Collection getSendMessageDtos() {
        return sendMessageDtos;
    }
    public void setSendMessageDtos(Collection sendMessageDtos) {
        this.sendMessageDtos = sendMessageDtos;
    }
    public BidimConfigSettingDTO getCbisDto() {
        return cbisDto;
    }
    public void setCbisDto(BidimConfigSettingDTO cbisDto) {
        this.cbisDto = cbisDto;
    }
    public BidimConfigSettingValueDTO getCbivDto() {
        return cbivDto;
    }
    public void setCbivDto(BidimConfigSettingValueDTO cbivDto) {
        this.cbivDto = cbivDto;
    }
    public Collection getIDs() {
        return IDs;
    }
    public void setIDs(Collection ds) {
        IDs = ds;
    }
    public DiagnosisInfoDTO getDpDto() {
        return dpDto;
    }
    public void setDpDto(DiagnosisInfoDTO dpDto) {
        this.dpDto = dpDto;
    }
}
