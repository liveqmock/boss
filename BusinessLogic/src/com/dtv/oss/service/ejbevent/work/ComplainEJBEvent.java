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
 * 接口说明：
 * 1、投诉受理(EJBEvent.ACCEPT_COMPLAIN)
 * 需设置CustomerComplainDTO：ccDto
 * 2、投诉分派（EJBEvent.ASSIGN_COMPLAIN）
 * 需设置CustComplainProcessDTO：ccpDto以及投诉单ID（通过ccpDto的CustComplainId传入）
 * 3、投诉处理(EJBEvent.PROCESS_COMPLAIN)
 * 需设置CustomerComplainDTO：ccDto,CustComplainProcessDTO：ccpDto以及投诉单ID（通过ccDto的CustComplainId传入）
 * 4、投诉回访（EJBEvent.CALL_FOR_COMPLAIN）
 * 需设置CustomerComplainDTO：ccDto,CustComplainProcessDTO：ccpDto以及投诉单ID（通过ccDto的CustComplainId传入）
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
