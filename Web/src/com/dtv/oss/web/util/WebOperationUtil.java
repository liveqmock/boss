package com.dtv.oss.web.util;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.Postern;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import com.dtv.oss.util.DBUtil;

public class WebOperationUtil {
	//------����-------------------------����--------------		
	//Ϊ�˷���������ⲿ�ֵĴ������������ع��������ŵ����������У���������Ĵ��뽫Ҫɾ��	
	/**
	 *  �ж��Ƿ����ܹ�˾
	 * @return int
	 */
	public static boolean isParentCompany(int OrgID)
	{
		OrganizationDTO dto = Postern.getOrganizationByID(OrgID);
		if (dto==null) return false;
		
		if (dto.getOrgType()!=null)
		{
			//SET_S_ORGANIZATIONORGTYPE="C"="�ܹ�˾"
			if (dto.getOrgType().equals("C")) return true;
		}
		
		return false;
		
	}
 
    public static OperatorDTO getOperator(HttpSession session){
       WebCurrentOperatorData wrapOp = CurrentOperator.GetCurrentOperator(session);
       if (wrapOp==null) return null;

       OperatorDTO dtoOp = (OperatorDTO)wrapOp.getCurrentOperator();
       return dtoOp;
    }

	/**
	 *  �ж��Ƿ��Ƿֹ�˾
	 * @return int
	 */
	public static boolean isSubcompany(int OrgID)
	{
		OrganizationDTO dto = Postern.getOrganizationByID(OrgID);
		if (dto==null) return false;
		
		if (dto.getOrgType()!=null)
		{
			//SET_S_ORGANIZATIONORGTYPE="B"="�ֹ�˾"
			if (dto.getOrgType().equals("B")) return true;
		}
		
		return false;
		
	}
	
	/**
	 *  �ж��Ƿ��ǽֵ�վ
	 * @return int
	 */
	public static boolean isStreetStaion(int OrgID)
	{
		OrganizationDTO dto = Postern.getOrganizationByID(OrgID);
		if (dto==null) return false;
		
		if (dto.getOrgType()!=null)
		{
			//SET_S_ORGANIZATIONORGTYPE="S"="�ֵ�վ"
			if (dto.getOrgType().equals("S")) return true;
		}
		
		return false;
		
	}
	 
	/**
	 * ��øù�˾�µĴ����̣��ܹ�˾��ȫ�������̣��ֹ�˾�Ǹ÷ֹ�˾�µĴ�����
	 * @return map of proxy id and name
	 */
	public static Map getProxyByOrgID(int orgID) {
		//��2007-12-4�޸�,ȡ��ǰ����Ա��֯�ϼ����пͻ�����Ȩ����֯.
		int rootOrgID = Postern.getParentHasCustomerOrgID(orgID);
		Map mapProxy=Postern.getAllAgentBeLongToCurr(rootOrgID);
		return mapProxy;
	}
		
	/**
	 *  �ж��Ƿ�������Ƿ��ǿ�����
	 * @return boolean
	 */
	public static boolean isBookingCustServiceInteraction(int csiid)
	{
		String sType = Postern.getCSITypeByID(csiid);
		
		if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(sType)) return true;
		
		return false;
		
	}
	
	/**
	 *  �жϸù����Ƿ�Ϊ�Ѿ�ԤԼ״̬
	 * @return boolean
	 */
	public static boolean isBookingOfJobCardConfirmed(int jcid)
	{
		String sStatus = Postern.getJobCardStatusByID(jcid);
		
		if (CommonConstDefinition.JOBCARD_STATUS_BOOKED.equals(sStatus)) return true;
		
		return false;
		
	}
	
    public static String getFormatTime(int time){
      	String totalTime="";
      	int mins;
      	int seconds;
      	int hours;
        hours=(int)time/3600;
      	mins=(int)time%3600/60;
      	seconds=time%3600%60;
        if(hours>0)
        	totalTime=totalTime+hours+"Сʱ";
        if(mins>0)
        	totalTime=totalTime+mins+"��";
        if(seconds>0)
        	totalTime=totalTime+seconds+"��";
        return totalTime;
         
      }
	/**
	 * ��������������Ʒʱ��Ԥ�Ʒ�
	 * @param csiID
	 * @param operatorID
	 * @throws Exception
	 */
	public static void preCalculateFeeInfo(int csiID,int operatorID) throws Exception{
		FeeService.preCalculateFeeInfo(csiID, operatorID);
	}
	
	/**
	* ��Сд�������ת���ɴ�д
	*/
	public static String convertToChineseNumber(double number){
	   StringBuffer chineseNumber = new StringBuffer();
	   String [] num={"��","Ҽ","��","��","��","��","½","��","��","��"};
	   String [] unit = {"��","��","Բ","ʰ","��","Ǫ","��","ʰ","��","Ǫ","��","ʰ","��","Ǫ","��"};
	   String tempNumber = String.valueOf(Math.round((number * 100)));
	   int tempNumberLength = tempNumber.length();
	   if ("0".equals(tempNumber)){
	      return "��Բ��";
	   }
	   if (tempNumberLength > 15){
	      try {
	         throw new Exception("����ת����Χ.");
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	   boolean preReadZero = true;    //ǰ����ַ��Ƿ����
	   for (int i=tempNumberLength; i>0; i--){
	      if ((tempNumberLength - i + 2) % 4 == 0){
	        //����ڣ�Բ�����ڣ���λ�ϵ��ĸ�����Ϊ��,�����־Ϊδ���㣬��ֻ���㣬�����־Ϊ�Ѷ��㣬���Թ�����λ
	        if (i - 4 >= 0 && "0000".equals(tempNumber.substring(i - 4, i))){
	            if (!preReadZero){
	                chineseNumber.insert(0, "��");
	                preReadZero = true;
	            }
	            i -= 3;    //���滹��һ��i--
	            continue;
	        }
	        //�����ǰλ�ڣ�Բ�����ڣ���λ�ϣ������ñ�־Ϊ�Ѷ��㣨�����ö����־��
	        preReadZero = true;
	      }
	      int digit = Integer.parseInt(tempNumber.substring(i - 1, i));
	      if (digit == 0){
	         //�����ǰλ���㲢�ұ�־Ϊδ���㣬����㣬�����ñ�־Ϊ�Ѷ���
	         if (!preReadZero){
	            chineseNumber.insert(0, "��");
	            preReadZero = true;
	         }
	         //�����ǰλ���㲢���ڣ�Բ�����ڣ���λ�ϣ��������Բ�����ڣ���
	         if ((tempNumberLength - i + 2) % 4 == 0){
	            chineseNumber.insert(0, unit[tempNumberLength - i]);
	         }
	      }
	      //�����ǰλ��Ϊ�㣬�������λ���������ñ�־Ϊδ����
	      else{
	         chineseNumber.insert(0, num[digit] + unit[tempNumberLength - i]);
	         preReadZero = false;
	      }
	   }
       //	����ֽ���λ�ϵ�ֵ��Ϊ�㣬�����һ����������
	   if (tempNumberLength - 2 >= 0 && "00".equals(tempNumber.substring(tempNumberLength - 2, tempNumberLength))){
	     chineseNumber.append("��");
	   }
	   return chineseNumber.toString();
	} 
	
	/**
	 * ��ָ��List������excel ��poi ,���������������ɷ���ͼ��[��״ͼ����ͼ��]��jxl����������ͼ
	 * @param os:��������������ļ���Ҳ�����������
	 * @param dataList:Ҫ����������,��һ��Ϊ��ͷ,���һ��Ϊ����
	 * @param printInfo:����һЩ�������������Ϣ
	 * @param colTypes:���ݴ洢��ʽ ������n;�ַ���s;double��d
	 * @param 
	 * @return
	 */
	public static void writeExcel(OutputStream os, List dataList,String strTitle,String printInfo,String colTypes) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(strTitle);
		//����һ������ˮƽ���е���ʽ������
		HSSFCellStyle style = workbook.createCellStyle();
		//ˮƽ����
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//��ֱ����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//�����Զ����У��Ȳ����ã����ͻ�����
		//style.setWrapText(true);
		
		//����������ʽ������
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont titleFont   =   workbook.createFont();      
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);     
		titleFont.setFontHeightInPoints((short) 11);
		titleStyle.setFont(titleFont);
		//titleStyle.setWrapText(true);
	
		//������ͷ��ʽ������
		HSSFCellStyle tabTitleStyle = workbook.createCellStyle();
		tabTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		tabTitleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		tabTitleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);   
		tabTitleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		tabTitleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		tabTitleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//tabTitleStyle.setWrapText(true);
		
		//�����߿���ʽ������
		HSSFCellStyle borderStyle = workbook.createCellStyle();
		borderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);   
		borderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		borderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		borderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//borderStyle.setWrapText(true);
		
		if (dataList != null && dataList.size()>1) {
			//��ʼ�� begin
			int  beginRow =0;//��ʼ��
		    short  beginCol =0;//��ʼ��
		    int  endRow =0;//������
		    short  endCol =0;//������
		    HSSFRow row = sheet.createRow(beginRow);
		    HSSFCell cell = row.createCell(beginCol);
		    //��ʼ�� end
		    
			//������� begin
		    int col = ((List)dataList.get(1)).size();
			beginRow = 0;
	    	beginCol = (short)0;
	    	endRow = 0;
	    	endCol = (short)(col-1);
	    	sheet.addMergedRegion(new Region(beginRow,beginCol,endRow,endCol));//ָ���ϲ�����
		    row = sheet.createRow(beginRow);
		    cell = row.createCell(beginCol);
		    cell.setCellValue(strTitle);
		    cell.setCellStyle(titleStyle);
			//������� end
		    
		    //���������Ҫ��ӡ��Ϣ begin                                                                            "+allCount;
			beginRow = 1;
	    	beginCol = (short)0;
	    	endRow = 1;
	    	endCol = (short)(col-1);
	    	sheet.addMergedRegion(new Region(beginRow,beginCol,endRow,endCol));//ָ���ϲ�����
		    row = sheet.createRow(beginRow);
		    cell = row.createCell(beginCol);
		    cell.setCellValue(printInfo);
		    //cell.setCellStyle(style);
		    //�����������ڼ��ܼ� end
		    
			//�����ͷ begin
			String tableTitle = (String)dataList.get(0);
			String [] tabTitle = tableTitle.split(";");
		    int titleCount = (tabTitle.length-1)/5;
		    for(int i=0;i<titleCount;i++)
		    {
		    	String eachTitle = tabTitle[i*5+1];
		    	beginRow = new Integer(tabTitle[i*5+2]).intValue()+2;
		    	beginCol = (short)(new Integer(tabTitle[i*5+3]).intValue());
		    	endRow = new Integer(tabTitle[i*5+4]).intValue()+2;
		    	endCol = (short)(new Integer(tabTitle[i*5+5]).intValue());
		    	sheet.addMergedRegion(new Region(beginRow,beginCol,endRow,endCol));//ָ���ϲ�����
		    	
		    	 //��ϲ��õ�����д����
			    row = sheet.createRow(beginRow);
			    cell = row.createCell(beginCol);
			    cell.setCellValue(eachTitle);
			    cell.setCellStyle(tabTitleStyle);
			    
			    //�ӱ߿� begin
			    for(int n=beginRow;n<=endRow;n++)
				{
					   for(short j = beginCol;j<=endCol;j++)
					   {
						   if(n==beginRow && j==beginCol)continue;
						   row = sheet.createRow(n);
						   cell = row.createCell(j);
						   cell.setCellStyle(tabTitleStyle);
					   }
				 }
			    //�ӱ߿� end
		    }
			
			//�����ͷ end
			
		    //�õ����е����ݸ�ʽ
		    String [] colType = colTypes.split(";");    
		    if(colType.length != col)System.out.println("�и�ʽ������� : colType.length != col");
			//д������
		    int dataBeginRow = new Integer(tabTitle[0]).intValue()+2;
			for (int i = 1; i < dataList.size()-1; i++) {
				row = sheet.createRow(dataBeginRow+i-1);
				List rowData = (List) dataList.get(i);
				for (int j = 0; j < col; j++) {
					cell = row.createCell((short) j);
					if (rowData.get(j) != null) {
						if("n".equalsIgnoreCase(colType[j]))
						{
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							cell.setCellValue(WebUtil.StringToDouble(rowData.get(j)+""));
						}
						else
						{
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(rowData.get(j)+"");
						}
							
						
						//�ӱ߿�
						cell.setCellStyle(borderStyle);
					}
				}
			}
		}
		try {

			// �½�һ����ļ���
			// FileOutputStream fOut = new FileOutputStream(fileName);
			// ����Ӧ��Excel ����������
			workbook.write(os);
			os.flush();
			// �����������ر��ļ�
			os.close();
		} catch (Exception e) {
			System.out.println("���� : " + e.getMessage());
		}
		// ���ļ��ӷ����������ص��ͻ���
		// downloadFile(fileName,response);
	}

	public static String changeMoneyToDate(int acctId){
		Connection con = DBUtil.getConnection();
		java.sql.CallableStatement cs=null;
		String retValue="";
		try {  
			cs=con.prepareCall("{? = call change_money_to_date(?)}");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setInt(2, acctId);
			cs.execute();
			retValue = cs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (cs != null) {
				try {
					cs.close();
				} 
				catch (SQLException ee) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} 
				catch (SQLException ee) {
				}
			}
		}
		return retValue;
	}
}