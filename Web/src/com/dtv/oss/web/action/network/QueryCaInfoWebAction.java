package com.dtv.oss.web.action.network;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CACommandDTO;
import com.dtv.oss.dto.CAConditionDTO;
import com.dtv.oss.dto.CAEventCmdMapDTO;
import com.dtv.oss.dto.CAHostDTO;
import com.dtv.oss.dto.CAParameterDTO;
import com.dtv.oss.dto.CAProductDTO;
import com.dtv.oss.dto.CAProductDefDTO;
import com.dtv.oss.dto.CARecvDTO;
import com.dtv.oss.dto.CASentDTO;
import com.dtv.oss.dto.CasubentitlementDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.dto.wrap.CAEventWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.QueryCaInfoEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
/**
 * CA接口信息查询
 * CA接口下所有查询都是走这里
 * @author 260327h
 *
 */
public class QueryCaInfoWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		String queryFlag=request.getParameter("queryFlag");
		if(!WebUtil.StringHaveContent(queryFlag)){
			throw new WebActionException("查询标志丢失.");
		}
		if(queryFlag.equalsIgnoreCase("cahost")){
			CAHostDTO dto=new CAHostDTO();
			String hostID=request.getParameter("OPHostID");
			if(WebUtil.StringHaveContent(hostID))
				dto.setHostID(WebUtil.StringToInt(hostID));
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_HOST);
		}else if(queryFlag.equalsIgnoreCase("caproduct")){
			CAProductDTO dto=new CAProductDTO();
			String productID=request.getParameter("productID");
			String conditionID=request.getParameter("conditionID");
			if(WebUtil.StringHaveContent(productID))
				dto.setProductId(WebUtil.StringToInt(productID));
			if(WebUtil.StringHaveContent(conditionID))
				dto.setConditionId(WebUtil.StringToInt(conditionID));
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_PRODUCT); 
	   }else if(queryFlag.equalsIgnoreCase("caproductdef")){
				CAProductDefDTO dto=new CAProductDefDTO();
				String hostID=request.getParameter("txtHostID");
				if(WebUtil.StringHaveContent(hostID))
					dto.setHostID(WebUtil.StringToInt(hostID));
				 
				dto.setOpiID(WebUtil.StringToInt(request.getParameter("txtOPI_ID")));
				dto.setCaProductID(request.getParameter("txtCAProductID"));
				
				return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_PRODUCTDEF);	
		}else if(queryFlag.equalsIgnoreCase("cacommand")){
			CACommandDTO dto=new CACommandDTO(); 
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_COMMAND);
		}else if(queryFlag.equalsIgnoreCase("caparameter")){
			CAParameterDTO dto=new CAParameterDTO(); 
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_PARAMETER);
		}else if(queryFlag.equalsIgnoreCase("cacondition")){
			CAConditionDTO dto=new CAConditionDTO(); 
			String condID=request.getParameter("OPConditionID");
			if(WebUtil.StringHaveContent(condID)){
				dto.setCondID(WebUtil.StringToInt(condID));
			}
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_CONDITION);
		}else if(queryFlag.equalsIgnoreCase("caeventcmdmap")){
			CAEventCmdMapDTO dto=new CAEventCmdMapDTO(); 
			String mapID=request.getParameter("OPMapID");
			if(WebUtil.StringHaveContent(mapID)){
				dto.setMapID(WebUtil.StringToInt(mapID));
			}
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_EVENTCMDMAP);
		}else if(queryFlag.equalsIgnoreCase("event")){
			CAEventWrap wrap = new CAEventWrap(); 
			CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
			String opEventID=request.getParameter("OPEventID");
			
			if(WebUtil.StringHaveContent(opEventID)){
				dto.setSpareStr1(opEventID);
			}
			String scSerialNO=request.getParameter("txtSCSerialNo");
			if(WebUtil.StringHaveContent(scSerialNO)){
				dto.setSpareStr2(scSerialNO.trim());
			}
			String srartTime=request.getParameter("txtStartTime");
			if(WebUtil.StringHaveContent(srartTime)){
				dto.setBeginTime(WebUtil.StringToTimestamp(srartTime));
			}
			String endTime=request.getParameter("txtEndTime");
			if(WebUtil.StringHaveContent(endTime)){
				dto.setEndTime(WebUtil.StringToTimestamp(endTime));
			}
			String stbSerialNO=request.getParameter("txtSTBSerialNo");
			if(WebUtil.StringHaveContent(stbSerialNO)){
				dto.setSpareStr3(stbSerialNO.trim());
			}
			 
			String startNO=request.getParameter("txtStartNo");
			if(WebUtil.StringHaveContent(startNO)){
				dto.setDistrict(WebUtil.StringToInt(startNO.trim()));
			}
			String endNO=request.getParameter("txtEndNo");
			if(WebUtil.StringHaveContent(endNO)){
				dto.setStreet(WebUtil.StringToInt(endNO.trim()));
			}
			
			
			String eventClass=request.getParameter("txtEventClass");
			if(WebUtil.StringHaveContent(eventClass)){
				dto.setSpareStr4(eventClass);
			}
			String eventID=request.getParameter("txtEventID");
			if(WebUtil.StringHaveContent(eventID)){
				dto.setSpareStr10(eventID);
			}
			String custmerID=request.getParameter("txtCustmerID");
			if(WebUtil.StringHaveContent(custmerID)){
				dto.setCustomerID(WebUtil.StringToInt(custmerID.trim()));
			}
			String caStatus=request.getParameter("txtStatus");
			if(WebUtil.StringHaveContent(caStatus)){
				dto.setSpareStr5(caStatus);
			}
			 
			wrap.setCaEventDTO(dto);
			return new QueryCaInfoEJBEvent(wrap,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_EVENTLOG);
		}else if(queryFlag.equalsIgnoreCase("recv")){
			CARecvDTO dto=new CARecvDTO(); 
			String queueID=request.getParameter("txtQuereID");
			if(WebUtil.StringHaveContent(queueID)){
				dto.setQueueID(WebUtil.StringToInt(queueID));
			}
			String eventID=request.getParameter("txtEventID");
			if(WebUtil.StringHaveContent(eventID)){
				dto.setEventID(WebUtil.StringToInt(eventID));
			}
			String transID=request.getParameter("txtTransID");
			if(WebUtil.StringHaveContent(transID)){
				dto.setTransID(WebUtil.StringToInt(transID));
			}
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_EVENTRECV);
		}else if(queryFlag.equalsIgnoreCase("sent")){
			CASentDTO dto=new CASentDTO(); 
			String queueID=request.getParameter("txtQuereID");
			if(WebUtil.StringHaveContent(queueID)){
				dto.setQueueID(WebUtil.StringToInt(queueID));
			}
			String eventID=request.getParameter("txtEventID");
			if(WebUtil.StringHaveContent(eventID)){
				dto.setEventID(WebUtil.StringToInt(eventID));
			}
			String transID=request.getParameter("txtTransID");
			if(WebUtil.StringHaveContent(transID)){
				dto.setTransID(WebUtil.StringToInt(transID));
			}
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_EVENTSENT);
			
		}
		 
		else if(queryFlag.equalsIgnoreCase("subentitlement")){
			CasubentitlementDTO dto=new CasubentitlementDTO(); 
			 
			String queueID=request.getParameter("cardSN");
			if(WebUtil.StringHaveContent(queueID)){
				dto.setCardsn(queueID);
			}
			 
			return new QueryCaInfoEJBEvent(dto,pageFrom,pageTo,QueryCaInfoEJBEvent.QUERY_CA_SUBENTITLEMENT);
		}
		 
		return null;
	}


}
