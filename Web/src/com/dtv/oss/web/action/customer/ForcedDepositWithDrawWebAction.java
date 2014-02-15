package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.ForcedDepositDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.ForcedDepositEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company: Digivision</p>
 * User: Chenjiang
 * Date: 2005-12-7
 * Time: 9:35:26
 * To change this template use File | Settings | File Templates.
 */
public class ForcedDepositWithDrawWebAction extends GeneralWebAction {
	
    protected EJBEvent encapsulateData (HttpServletRequest request) throws WebActionException {
        List fdList = new ArrayList();
         
        String[] seqNo = request.getParameterValues("seqNo");
        String[] dtLastmod = request.getParameterValues("txtDtLastmod");
        String[] withdrawMoney = request.getParameterValues("txtWithdrawMoney");
        if (seqNo == null) {
            throw new WebActionException ("请选定数据！");
        }
        ForcedDepositDTO dto = null;
        int size = seqNo.length;
        for (int i = 0; i < size; i++) {
            dto = new ForcedDepositDTO();
           
            dto.setSeqNo(WebUtil.StringToInt(seqNo[i]));
            
            dto.setDtLastmod(WebUtil.StringToTimestamp(dtLastmod[i]));
            dto.setWithdrawMoney(WebUtil.StringTodouble(withdrawMoney[i]));
            
            fdList.add(dto);
        }
        CustServiceInteractionDTO csiDto =new  CustServiceInteractionDTO();
        csiDto.setId(WebUtil.StringToInt(request.getParameter("txtWithdrawCsiID")));
        ForcedDepositEJBEvent event = new ForcedDepositEJBEvent(EJBEvent.WITHDRAW_FORCED_DEPOSIT);
        event.setForcedDepositCol(fdList);
        event.setCsiDto(csiDto);
        return event;
    }
}
