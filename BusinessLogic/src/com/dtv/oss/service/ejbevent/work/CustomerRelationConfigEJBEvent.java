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
 * �ӿ�˵����
 * 1��������Ϣ
 * ������SendMessageDTO�ļ��ϣ�����ÿ��SendMessageDTO������customerID��serviceAccountID,��������,��Ϣ����
 * 2�������ط���Ϣ����
 * ������CallBackInfoSettingDTO��cbisDto
 * 3�����»ط���Ϣ����
 * ������CallBackInfoSettingDTO:cbisDto�Լ�ID��ͨ��CallBackInfoSettingDTO��Id�ֶΣ�
 * 4��ɾ���ط���Ϣ����
 * ������IDs����Ҫɾ���ļ�¼��ID�ļ��ϣ�Integer�ļ�����ʽ��
 * 5������CallBackInfoValue
 * ������CallBackInfoValueDTO��cbivDto,����settingID��������
 * 6������CallBackInfoValue
 * ������CallBackInfoValueDTO:cbivDto�Լ�ID��ͨ��CallBackInfoValueDTO��Id�ֶΣ�
 * 7��ɾ��CallBackInfoValue
 * ������IDs����Ҫɾ���ļ�¼��ID�ļ��ϣ�Integer�ļ�����ʽ��
 * 8��������ϲ���
 * ������DiagnosisParameterDTO��dpDto
 * 9��������ϲ���
 * ������DiagnosisParameterDTO:dpDto�Լ�ID��ͨ��DiagnosisParameterDTO��Id�ֶΣ�
 * 10��ɾ����ϲ���
 * ������IDs����Ҫɾ���ļ�¼��ID�ļ��ϣ�Integer�ļ�����ʽ��
 */
public class CustomerRelationConfigEJBEvent extends AbstractEJBEvent {
    private Collection sendMessageDtos;
    private BidimConfigSettingDTO cbisDto;
    private BidimConfigSettingValueDTO cbivDto;
    private DiagnosisInfoDTO dpDto;
    private Collection IDs;		//ɾ����¼������T_CallbackInfoSetting,T_CallbackInfoValue��ʱ����¼ID�ļ���(Collection of Integer)
    
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
