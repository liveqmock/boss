package com.dtv.oss.web.util.print;

import java.util.List;

import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.print.PrintCreate;
import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.util.Postern;
import com.dtv.oss.dto.PrintBlockDTO;
import com.dtv.oss.dto.PrintConfigDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
public class PrintMain {
	
	/**
	 * �õ�ȫ����ӡ��html
	 * 
	 * @param request
	 */
	public String getHtmlAll(HttpServletRequest request) throws WebActionException{
		String strHtml =  "";
		String strImgHtml = "";
		try {
		//String action = getUrl(request);
		String printSheetType = (String)request.getParameter("txtPrintSheetType");
		if(printSheetType == null || "null".equals(printSheetType)) printSheetType = "";
		String sheetSubType = (String)request.getParameter("txtSheetSubType");
		if(sheetSubType == null || "null".equals(sheetSubType)) sheetSubType = "";
		String csiReason = (String)request.getParameter("txtCsiReason");
		if(csiReason == null || "null".equals(csiReason)) csiReason = "";
		String printReason = (String)request.getParameter("txtPrintReason");
		if(printReason == null || "null".equals(printReason)) printReason = "";
		
		String csiId = (String)request.getParameter("txtCsiId");
		if(csiId == null || "null".equals(csiId)) csiId="";
		
		String customerID = (String)request.getParameter("txtCustomerID");
		if(customerID == null || "null".equals(customerID)) customerID="";
		
		String jobCardIDList = (String)request.getParameter("txtJobCardIDList");
		String[] jobCardIDs = null;
		if(jobCardIDList!=null && !"".equals(jobCardIDList))
			jobCardIDs = jobCardIDList.split(";");
		
		LogUtility.log(PrintMain.class, LogLevel.WARN,"getHtmlAll  printSheetType:"+printSheetType+",sheetSubType:"+sheetSubType+",csiReason:"+csiReason+",printReason:"+printReason);
		
		int refConfigId = 0; //PrintBlock��refConfigId
		PrintConfigDTO printConfigDTO = Postern.getPrintConfigDTO(printSheetType,sheetSubType,csiReason,printReason);
		if(printConfigDTO == null) return "";
		if(printConfigDTO!=null) refConfigId=printConfigDTO.getId();
		List PrintBlockList = Postern.getPrintBlock(refConfigId);
		if(PrintBlockList.size() == 0) return "";
		

        
        
		PrintBlockDTO dto = null;
		if(jobCardIDs != null){                            //��ӡ����
			System.out.println("::::��ӡ����");
			int rowHeight = printConfigDTO.getRowHeight();
			int pageBreakCount = printConfigDTO.getPageBreakCount();
			if(pageBreakCount ==0) pageBreakCount=1;
			
			System.out.println("::::pageBreakCount"+pageBreakCount);
			int recordCount = 0;
			if(jobCardIDs != null) recordCount = jobCardIDs.length;
			int currentRecordCount = 0;
			int pageCount = 1;
			int allPageCount = 0;
			
			int controlHeight = 0;
			if(pageBreakCount!=0){	
				allPageCount = recordCount/pageBreakCount + 1;
				if(recordCount%pageBreakCount==0)
				{
					allPageCount = recordCount/pageBreakCount;
				}
			}
			if(pageBreakCount==0)
				throw new WebActionException("T_printConfig��pageBreakCount��ÿҳ��ʾ��¼�����������ã�����Ϊ��");
			
			System.out.println("::::pageBreakCount"+pageBreakCount);
			int pageHeight = printConfigDTO.getPageHeight();
			int pageWeight = printConfigDTO.getPageWeight();
			if(pageHeight==0) pageHeight = 297;
			if(pageWeight==0) pageWeight = 210;
			
			if(allPageCount>1){
				strHtml =  "<div style=\"position:relative;width:"+pageWeight+"mm;height:"+pageHeight+"mm;PAGE-BREAK-AFTER:always;\">";
			}else{
				strHtml =  "<div style=\"position:relative;width:"+pageWeight+"mm;height:"+pageHeight+"mm;\">";
			}
			
			strImgHtml = getImgHtml(printSheetType,printConfigDTO);
			strHtml = strHtml + strImgHtml;
			
			for(int i=0;i<PrintBlockList.size();i++){
				dto = (PrintBlockDTO)PrintBlockList.get(i);
				for(int j=0;j<jobCardIDs.length;j++ ){
					System.out.println("jobCardIDs[j]::"+jobCardIDs[j]);
					
					strHtml = strHtml + getHtmlBlock(dto.getId(),new String(jobCardIDs[j]),rowHeight*controlHeight) + "\n";
					currentRecordCount++;
					controlHeight++;
					if((currentRecordCount%pageBreakCount == 0) ||(currentRecordCount%pageBreakCount!=0 && recordCount==currentRecordCount && recordCount>pageBreakCount)){
						/**
						strHtml = strHtml + "<table>"+
											"<tr>"+
											"<td align=\"center\">��"+pageCount+++"ҳ����"+allPageCount+"ҳ</td>"+
											"</tr>"+
											"</table>";
						**/

						if(pageCount==allPageCount-1){
							strHtml = strHtml + "</div><div style=\"position:relative;width:"+pageWeight+"mm;height:"+pageHeight+"mm;\">";
							controlHeight=0;
							pageCount++;
							continue;
						}
						
						if(currentRecordCount!=recordCount && jobCardIDs.length!=1){
							strHtml = strHtml + "</div><div style=\"position:relative;width:"+pageWeight+"mm;height:"+pageHeight+"mm;PAGE-BREAK-AFTER:always;\">";
							controlHeight=0;
							pageCount++;
						}
							//strHtml = strHtml + "<p STYLE=\"page-break-after: always\">&nbsp;</p>";
					}
				}
			}
			strHtml = strHtml + "</div>";
		}
		else if(!"".equals(csiId)){							  //��ӡ����
			System.out.println("::::��ӡ����");
			for(int i=0;i<PrintBlockList.size();i++){
				dto = (PrintBlockDTO)PrintBlockList.get(i);
				strHtml = strHtml + getHtmlBlock(dto.getId(),csiId) + "\n";
			}
		}
		else if(!"".equals(customerID)){					  //�ͻ���Ϣ����
			System.out.println("::::�ͻ���Ϣ����");
			for(int i=0;i<PrintBlockList.size();i++){
				dto = (PrintBlockDTO)PrintBlockList.get(i);
				strHtml = strHtml + getHtmlBlock(dto.getId(),customerID) + "\n";
			}
		}
		
        
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		
		return strHtml;
	}

	
	/**
	 * �õ���ӡ��ĳһ����html�ַ���
	 * 
	 * @param Id 
	 * @param parameter 
	 */
	private String getHtmlBlock(int id,Object parameter) throws WebActionException{
		PrintCreate printCreate = new PrintCreate();
		printCreate.init(id,parameter);
		String HtmlBlock = printCreate.getHtmlString();
		System.out.println("::::::HtmlBlock:"+HtmlBlock);
		return HtmlBlock;
	}
	
	/**
	 * �õ���ӡ��ĳһ����html�ַ���
	 * 
	 * @param Id 
	 * @param parameter 
	 */
	private String getHtmlBlock(int id,Object parameter,int rowHeight) throws WebActionException{
		PrintCreate printCreate = new PrintCreate();
		printCreate.init(id,parameter);
		String HtmlBlock = printCreate.getHtmlString(rowHeight);
		return HtmlBlock;
	}
	
	
	/**
	 * �õ���ӡ��ĳһ����html�ַ���
	 * 
	 * @param Id 
	 * @param parameter 
	 */
	private String getImgHtml(String printSheetType,PrintConfigDTO printConfigDTO) throws WebActionException{
		String HtmlBlock = "";
		if(printConfigDTO != null){
			if(printConfigDTO.getBackImg() != null){
				HtmlBlock = "<div style=\"position:absolute;left:"+printConfigDTO.getLeft()+"px; top:"+printConfigDTO.getTop()+"px; \" >"
							+"<img src=\"img_download.screen?printConfigID="+printConfigDTO.getId()+"\">"
							+"</div>";
			}
		}
		return HtmlBlock;
	}
	
	/**
	 * �õ�action
	 * 
	 * @param request
	 */
	public String getUrl(HttpServletRequest request){
        String fullURL = request.getRequestURI();
        
        // get the screen name
        String selectedURL = null;
        int lastPathSeparator = fullURL.lastIndexOf("/") + 1;
        if (lastPathSeparator != -1) {
            selectedURL = fullURL.substring(lastPathSeparator, fullURL.length());
        }
        
        return selectedURL;
    }
}
