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
	//------区域-------------------------结束--------------		
	//为了方便起见，这部分的代码依保留，重构代码必须放到以上区域中，以外区域的代码将要删除	
	/**
	 *  判断是否是总公司
	 * @return int
	 */
	public static boolean isParentCompany(int OrgID)
	{
		OrganizationDTO dto = Postern.getOrganizationByID(OrgID);
		if (dto==null) return false;
		
		if (dto.getOrgType()!=null)
		{
			//SET_S_ORGANIZATIONORGTYPE="C"="总公司"
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
	 *  判断是否是分公司
	 * @return int
	 */
	public static boolean isSubcompany(int OrgID)
	{
		OrganizationDTO dto = Postern.getOrganizationByID(OrgID);
		if (dto==null) return false;
		
		if (dto.getOrgType()!=null)
		{
			//SET_S_ORGANIZATIONORGTYPE="B"="分公司"
			if (dto.getOrgType().equals("B")) return true;
		}
		
		return false;
		
	}
	
	/**
	 *  判断是否是街道站
	 * @return int
	 */
	public static boolean isStreetStaion(int OrgID)
	{
		OrganizationDTO dto = Postern.getOrganizationByID(OrgID);
		if (dto==null) return false;
		
		if (dto.getOrgType()!=null)
		{
			//SET_S_ORGANIZATIONORGTYPE="S"="街道站"
			if (dto.getOrgType().equals("S")) return true;
		}
		
		return false;
		
	}
	 
	/**
	 * 获得该公司下的代理商，总公司是全部代理商，分公司是该分公司下的代理商
	 * @return map of proxy id and name
	 */
	public static Map getProxyByOrgID(int orgID) {
		//侯2007-12-4修改,取当前操作员组织上级具有客户管理权的组织.
		int rootOrgID = Postern.getParentHasCustomerOrgID(orgID);
		Map mapProxy=Postern.getAllAgentBeLongToCurr(rootOrgID);
		return mapProxy;
	}
		
	/**
	 *  判断是否该受理单是否是开户单
	 * @return boolean
	 */
	public static boolean isBookingCustServiceInteraction(int csiid)
	{
		String sType = Postern.getCSITypeByID(csiid);
		
		if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(sType)) return true;
		
		return false;
		
	}
	
	/**
	 *  判断该工单是否为已经预约状态
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
        	totalTime=totalTime+hours+"小时";
        if(mins>0)
        	totalTime=totalTime+mins+"分";
        if(seconds>0)
        	totalTime=totalTime+seconds+"秒";
        return totalTime;
         
      }
	/**
	 * 用来处理新增产品时候预计费
	 * @param csiID
	 * @param operatorID
	 * @throws Exception
	 */
	public static void preCalculateFeeInfo(int csiID,int operatorID) throws Exception{
		FeeService.preCalculateFeeInfo(csiID, operatorID);
	}
	
	/**
	* 将小写的人民币转化成大写
	*/
	public static String convertToChineseNumber(double number){
	   StringBuffer chineseNumber = new StringBuffer();
	   String [] num={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
	   String [] unit = {"分","角","圆","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟","万"};
	   String tempNumber = String.valueOf(Math.round((number * 100)));
	   int tempNumberLength = tempNumber.length();
	   if ("0".equals(tempNumber)){
	      return "零圆整";
	   }
	   if (tempNumberLength > 15){
	      try {
	         throw new Exception("超出转化范围.");
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	   boolean preReadZero = true;    //前面的字符是否读零
	   for (int i=tempNumberLength; i>0; i--){
	      if ((tempNumberLength - i + 2) % 4 == 0){
	        //如果在（圆，万，亿，万）位上的四个数都为零,如果标志为未读零，则只读零，如果标志为已读零，则略过这四位
	        if (i - 4 >= 0 && "0000".equals(tempNumber.substring(i - 4, i))){
	            if (!preReadZero){
	                chineseNumber.insert(0, "零");
	                preReadZero = true;
	            }
	            i -= 3;    //下面还有一个i--
	            continue;
	        }
	        //如果当前位在（圆，万，亿，万）位上，则设置标志为已读零（即重置读零标志）
	        preReadZero = true;
	      }
	      int digit = Integer.parseInt(tempNumber.substring(i - 1, i));
	      if (digit == 0){
	         //如果当前位是零并且标志为未读零，则读零，并设置标志为已读零
	         if (!preReadZero){
	            chineseNumber.insert(0, "零");
	            preReadZero = true;
	         }
	         //如果当前位是零并且在（圆，万，亿，万）位上，则读出（圆，万，亿，万）
	         if ((tempNumberLength - i + 2) % 4 == 0){
	            chineseNumber.insert(0, unit[tempNumberLength - i]);
	         }
	      }
	      //如果当前位不为零，则读出此位，并且设置标志为未读零
	      else{
	         chineseNumber.insert(0, num[digit] + unit[tempNumberLength - i]);
	         preReadZero = false;
	      }
	   }
       //	如果分角两位上的值都为零，则添加一个“整”字
	   if (tempNumberLength - 2 >= 0 && "00".equals(tempNumber.substring(tempNumberLength - 2, tempNumberLength))){
	     chineseNumber.append("整");
	   }
	   return chineseNumber.toString();
	} 
	
	/**
	 * 将指定List导出到excel 用poi ,将来可以用来生成分析图形[柱状图，饼图等]，jxl好象不能生成图
	 * @param os:输出流，可以是文件，也可以是浏览器
	 * @param dataList:要导出的数据,第一条为表头,最后一条为列数
	 * @param printInfo:定义一些描述表的其他信息
	 * @param colTypes:数据存储方式 整数－n;字符－s;double－d
	 * @param 
	 * @return
	 */
	public static void writeExcel(OutputStream os, List dataList,String strTitle,String printInfo,String colTypes) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(strTitle);
		//创建一个上下水平居中的样式供调用
		HSSFCellStyle style = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//垂直居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//设置自动换行，先不设置，看客户需求
		//style.setWrapText(true);
		
		//创建标题样式供调用
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont titleFont   =   workbook.createFont();      
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);     
		titleFont.setFontHeightInPoints((short) 11);
		titleStyle.setFont(titleFont);
		//titleStyle.setWrapText(true);
	
		//创建表头样式供调用
		HSSFCellStyle tabTitleStyle = workbook.createCellStyle();
		tabTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		tabTitleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		tabTitleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);   
		tabTitleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		tabTitleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		tabTitleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//tabTitleStyle.setWrapText(true);
		
		//创建边框样式供调用
		HSSFCellStyle borderStyle = workbook.createCellStyle();
		borderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);   
		borderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		borderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		borderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//borderStyle.setWrapText(true);
		
		if (dataList != null && dataList.size()>1) {
			//初始化 begin
			int  beginRow =0;//起始行
		    short  beginCol =0;//起始列
		    int  endRow =0;//结束行
		    short  endCol =0;//结束列
		    HSSFRow row = sheet.createRow(beginRow);
		    HSSFCell cell = row.createCell(beginCol);
		    //初始化 end
		    
			//处理标题 begin
		    int col = ((List)dataList.get(1)).size();
			beginRow = 0;
	    	beginCol = (short)0;
	    	endRow = 0;
	    	endCol = (short)(col-1);
	    	sheet.addMergedRegion(new Region(beginRow,beginCol,endRow,endCol));//指定合并区域
		    row = sheet.createRow(beginRow);
		    cell = row.createCell(beginCol);
		    cell.setCellValue(strTitle);
		    cell.setCellStyle(titleStyle);
			//处理标题 end
		    
		    //处理相关需要打印信息 begin                                                                            "+allCount;
			beginRow = 1;
	    	beginCol = (short)0;
	    	endRow = 1;
	    	endCol = (short)(col-1);
	    	sheet.addMergedRegion(new Region(beginRow,beginCol,endRow,endCol));//指定合并区域
		    row = sheet.createRow(beginRow);
		    cell = row.createCell(beginCol);
		    cell.setCellValue(printInfo);
		    //cell.setCellStyle(style);
		    //处理生成日期及总计 end
		    
			//处理表头 begin
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
		    	sheet.addMergedRegion(new Region(beginRow,beginCol,endRow,endCol));//指定合并区域
		    	
		    	 //向合并好的区域写内容
			    row = sheet.createRow(beginRow);
			    cell = row.createCell(beginCol);
			    cell.setCellValue(eachTitle);
			    cell.setCellStyle(tabTitleStyle);
			    
			    //加边框 begin
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
			    //加边框 end
		    }
			
			//处理表头 end
			
		    //得到各列的数据格式
		    String [] colType = colTypes.split(";");    
		    if(colType.length != col)System.out.println("列格式定义出错 : colType.length != col");
			//写入数据
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
							
						
						//加边框
						cell.setCellStyle(borderStyle);
					}
				}
			}
		}
		try {

			// 新建一输出文件流
			// FileOutputStream fOut = new FileOutputStream(fileName);
			// 把相应的Excel 工作簿存盘
			workbook.write(os);
			os.flush();
			// 操作结束，关闭文件
			os.close();
		} catch (Exception e) {
			System.out.println("出错 : " + e.getMessage());
		}
		// 将文件从服务器上下载到客户端
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