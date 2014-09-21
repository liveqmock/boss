<%@ page import="java.io.*,java.util.*" %>
<%@ page import="com.dtv.oss.web.util.*,com.dtv.oss.util.Postern" %>

<% 
	if(WebUtil.StringHaveContent(request.getParameter("actiontype"))){		
		StringBuffer xmlDocResponse = new StringBuffer();
		if("changesel".equals(request.getParameter("actiontype"))){
			String feeType = null;
			if(WebUtil.StringHaveContent(request.getParameter("feeType"))){
				feeType = request.getParameter("feeType");
			}
			if(feeType == null)
				return;
			Map mapAcctItemType = Postern.getAcctitemtype(feeType);			
			xmlDocResponse.append("<acctitemtypes>");
			
			for (Iterator itr = mapAcctItemType.entrySet().iterator(); itr.hasNext();) {
				Map.Entry entry = (Map.Entry) itr.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				xmlDocResponse.append("<acctitemtype>");
				xmlDocResponse.append("<typeid>" + key + "</typeid>");
				xmlDocResponse.append("<typename>" + value + "</typename>");
				xmlDocResponse.append("</acctitemtype>");
			}
			xmlDocResponse.append("</acctitemtypes>");
		}else if("addfee".equals(request.getParameter("actiontype"))){
			xmlDocResponse.append("<fee>");
			xmlDocResponse.append("<feetype>"+request.getParameter("txtFeeType")+"</feetype>");
			xmlDocResponse.append("<acctitemtype>"+request.getParameter("txtAcctItemType")+"</acctitemtype>");
			xmlDocResponse.append("<productid>"+request.getParameter("txtProductID")+"</productid>");
			xmlDocResponse.append("<amount>"+WebUtil.bigDecimalFormat(WebUtil.StringTodouble(request.getParameter("txtAmount")))+"</amount>");
			xmlDocResponse.append("</fee>");
			System.out.println(xmlDocResponse.toString());
		}else if("addpay".equals(request.getParameter("actiontype"))){
			xmlDocResponse.append("<pay>");
			xmlDocResponse.append("<mopid>"+request.getParameter("txtMopID")+"</mopid>");
			xmlDocResponse.append("<ticketno>"+request.getParameter("txtTicketNo")+"</ticketno>");
			xmlDocResponse.append("<value>"+WebUtil.bigDecimalFormat(WebUtil.StringTodouble(request.getParameter("txtValue")))+"</value>");
			xmlDocResponse.append("<preductionvalue>"+WebUtil.bigDecimalFormat(WebUtil.StringTodouble(request.getParameter("txtPreDuctionValue")))+"</preductionvalue>");
			xmlDocResponse.append("</pay>");
			System.out.println(xmlDocResponse.toString());	
		}		
	response.setContentType("text/xml;charset=UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	out.println(xmlDocResponse.toString());
	out.close();
	}
%>