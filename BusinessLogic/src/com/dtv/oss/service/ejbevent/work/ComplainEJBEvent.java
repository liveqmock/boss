/*
 * Created on 2005-11-7
 *
 * @author whq
 */
package com.dtv.oss.service.ejbevent.work;

import java.util.Collection;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.dto.*;

/*
 * �ӿ�˵����
 * 1��Ͷ������(EJBEvent.ACCEPT_COMPLAIN)
 * ������CustomerComplainDTO��ccDto
 * 2��Ͷ�߷��ɣ�EJBEvent.ASSIGN_COMPLAIN��
 * ������CustComplainProcessDTO��ccpDto�Լ�Ͷ�ߵ�ID��ͨ��ccpDto��CustComplainId���룩
 * 3��Ͷ�ߴ���(EJBEvent.PROCESS_COMPLAIN)
 * ������CustomerComplainDTO��ccDto,CustComplainProcessDTO��ccpDto�Լ�Ͷ�ߵ�ID��ͨ��ccDto��CustComplainId���룩
 * 4��Ͷ�߻طã�EJBEvent.CALL_FOR_COMPLAIN��
 * ������CustomerComplainDTO��ccDto,CustComplainProcessDTO��ccpDto�Լ�Ͷ�ߵ�ID��ͨ��ccDto��CustComplainId���룩
 */
public class ComplainEJBEvent extends AbstractEJBEvent {
    private CustomerComplainDTO ccDto;
    private CustComplainProcessDTO ccpDto;
    
    public CustomerComplainDTO getCcDto() {
        return ccDto;
    }
    public void setCcDto(CustomerComplainDTO ccDto) {
        this.ccDto = ccDto;
    }
    public CustComplainProcessDTO getCcpDto() {
        return ccpDto;
    }
    public void setCcpDto(CustComplainProcessDTO ccpDto) {
        this.ccpDto = ccpDto;
    }
}
