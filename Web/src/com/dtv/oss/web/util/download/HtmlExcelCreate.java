package com.dtv.oss.web.util.download;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dtv.oss.web.exception.WebActionException;
/**
 * 生成html格式的内容,后辍为xls,可用excel打开,推荐使用,不会有内存溢出问题,缺点,excel只能打开65000行,多的数据无法在excel中查看,
 * html头描述从excel导出文件中抄来的,包括table头,不可缺少,否则导出内容中数字内容会被excel自动转换为科学记数法表示.
 * @author 260327h
 *
 */
public class HtmlExcelCreate extends ExcelCreate{

	public String getExcelContent() throws WebActionException {
		StringBuffer res=new StringBuffer();
		res.append(generateHtmlHead());
		res.append(generateTableHead());
		res.append(generateCaptionRow(captionCellList));
		try {
			DownloadDAO dao = DownloadDAO.init(querySql, queryParameter);
			while (dao.hasNext()) {
				Map rowData = dao.next();
				appendMapToStringBuffer(rowData, res);
				rowData.clear();
			}
			if (isStat) {
				List statList = getStatRow();
				appendStatToStringBuffer(statList, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException("文件生成出错."+e.getMessage());
		}
		res.append(generateTableTail());
		return res.toString();
	}

	public File getExcelFile(String fileName) throws WebActionException {
		File file = null;
		try {
			file=new File(fileName);
			java.io.FileOutputStream fos=new FileOutputStream(file);
			java.io.OutputStreamWriter osw=new OutputStreamWriter(fos,"GBK"); 
			BufferedWriter out=new BufferedWriter(osw);
			out.write(generateHtmlHead());
			out.newLine();
			out.write(generateTableHead());
			out.newLine();
			out.write(generateCaptionRow(captionCellList));
			out.newLine();
			DownloadDAO dao = DownloadDAO.init(querySql,queryParameter);
			while (dao.hasNext()) {
				Map rowData = dao.next();
//				System.out.println("getExcelFile.rowData.size() " + rowData.size());
//				System.out.println("getExcelFile.rowData " + rowData);
				appendMapToFileWriter(rowData, out);
				out.newLine();
				rowData.clear();
			}
			if (isStat) {
				List statList = getStatRow();
				appendStatToFileWriter(statList, out);
				out.newLine();
			}
			out.write(generateTableTail());
			out.newLine();
			out.flush();
			out.close();
		} catch (WebActionException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException("文件生成出错."+e.getMessage());
		}
		return file;
	}
	
	private void appendStatToFileWriter(List list,Writer fw) throws WebActionException{
		try {
			for (Iterator it = list.iterator(); it.hasNext();) {
				StringBuffer rowContent = new StringBuffer();
				Map rowData = (Map) it.next();
				rowContent.append(generateTrHead());
				rowContent.append(generateHtmlExcelRow(rowData));
				rowContent.append(generateTrTail());
				fw.write(rowContent.toString());
				fw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
	}
	private void appendStatToStringBuffer(List list,StringBuffer bf) throws WebActionException{
		try {
			for (Iterator it = list.iterator(); it.hasNext();) {
				StringBuffer rowContent = new StringBuffer();
				Map rowData = (Map) it.next();
				rowContent.append(generateTrHead());
				rowContent.append(generateHtmlExcelRow(rowData));
				rowContent.append(generateTrTail());
				bf.append(rowContent.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
	}
	private void appendMapToFileWriter(Map map,Writer fw) throws WebActionException{
		try {
				StringBuffer rowContent = new StringBuffer();
				Map rowData = object2StringMap(map);
				rowContent.append(generateTrHead());
				rowContent.append(generateHtmlExcelRow(rowData));
				rowContent.append(generateTrTail());
				fw.write(rowContent.toString());
				fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
	}
	private void appendMapToStringBuffer(Map map,StringBuffer bf) throws WebActionException{
		try {
				StringBuffer rowContent = new StringBuffer();
				Map rowData = object2StringMap(map);
				rowContent.append(generateTrHead());
				rowContent.append(generateHtmlExcelRow(rowData));
				rowContent.append(generateTrTail());
				bf.append(rowContent.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
	}
	
	private String generateHtmlHead(){
		StringBuffer res=new StringBuffer();
		res.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"").append("\n");
		res.append("xmlns:x=\"urn:schemas-microsoft-com:office:excel\"").append("\n");
		res.append("xmlns=\"http://www.w3.org/TR/REC-html40\">").append("\n");
		res.append("<head>").append("\n");
		res.append("<meta http-equiv=content-type content=\"text/html; charset=gb2312\">").append("\n");
		res.append("</head>").append("\n");
		res.append("<body link=blue vlink=purple>").append("\n");
		res.append("");
		return res.toString();
	}
	private String generateHtmlExcelRow(Map rowData){
		StringBuffer res=new StringBuffer();
		for(Iterator rit=rowData.entrySet().iterator();rit.hasNext();){
			Entry entry=(Entry) rit.next();
			HtmlCell cell=(HtmlCell) entry.getValue();
			String cellContent=cell.getValue();
			res.append(generateCell(cellContent,cell.getValuebgcolor(),cell.getAlign()));
		}
		return res.toString();
	}
	private String generateCaptionRow(List list){
		StringBuffer res=new StringBuffer();
		res.append(generateTrHead());
		for(Iterator it=list.iterator();it.hasNext();){
			HtmlCell cell=(HtmlCell) it.next();
			res.append(generateColumnHead(cell.getCaption(),cell.getCaptionbgcolor()));
		}
		res.append(generateTrTail());
		return res.toString();
	}
	
	private String generateColumnHead(String content,String bgColor){
		StringBuffer res=new StringBuffer();
		res.append("<th");
		if(bgColor!=null&&!"".equals(bgColor)){
			res.append(" bgcolor=\"").append(bgColor).append("\"");
		}
		res.append(">");
		res.append(content);
		res.append("</th>");
		return res.toString();
	}
	private String generateTableHead(){
		StringBuffer res=new StringBuffer();
		res.append("<table x:str border=\"1\" >").append("\n");
		return res.toString();
	}
	private String generateTableTail(){
		StringBuffer res=new StringBuffer();
		res.append("</table>").append("\n");
		res.append("</body>").append("\n");
		res.append("</html>").append("\n");
		return res.toString();
	}
	private String generateTrHead(){
		StringBuffer res=new StringBuffer();
		res.append("<tr>").append("\n");
		return res.toString();
	}
	private String generateTrTail(){
		return "</tr>\n";
	}
	private String generateCell(String content,String bgColor,String align){
		StringBuffer res=new StringBuffer();
		res.append("<td");
		if(bgColor!=null&&!"".equals(bgColor)){
			res.append(" bgcolor=\"").append(bgColor).append("\"");
		}
		if(align!=null&&!"".equals(align)){
			res.append(" align=\"").append(align).append("\"");
		}
		res.append(">");
		res.append(content);
		res.append("</td>");
		return res.toString();
	}
	public static void main(String []args){
		System.out.println("start:"+new Date());
		HtmlExcelCreate ec=new HtmlExcelCreate();
		String sql="select a.*, addr.Postcode addr_Postcode, addr.DistrictID addr_DistrictID,addr.DetailAddress addr_DetailAddress,addr.AddressID addr_AddressID,addr.Dt_Create addr_Dt_Create,addr.Dt_Lastmod addr_Dt_Lastmod from t_customer a left join t_address addr on a.addressid = addr.addressid  , (select addressID from  T_ADDRESS where districtid in  (select Id from T_DISTRICTSETTING  connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid= 1))) viewAddress  where 1=1  and viewAddress.addressID=addr.addressID   and a.addressid = addr.addressid and a.status <> 'C' and (a.CUSTOMERSTYLE <> 'G' or a.customerstyle is null) order by a.CustomerID DESC";
//		String sql="SELECT * FROM (SELECT ROWNUM MYNUM, XXX.* FROM (select a.*, addr.Postcode addr_Postcode, addr.DistrictID addr_DistrictID,addr.DetailAddress addr_DetailAddress,addr.AddressID addr_AddressID,addr.Dt_Create addr_Dt_Create,addr.Dt_Lastmod addr_Dt_Lastmod from t_customer a left join t_address addr on a.addressid = addr.addressid  , (select addressID from  T_ADDRESS where districtid in  (select Id from T_DISTRICTSETTING  connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid= 1))) viewAddress  where 1=1  and viewAddress.addressID=addr.addressID   and a.addressid = addr.addressid and a.status <> 'C' and (a.CUSTOMERSTYLE <> 'G' or a.customerstyle is null) order by a.CustomerID DESC) XXX) WHERE MYNUM BETWEEN 0 AND 5";
//		String str=null;
//		try {
//			ec.init("customer_query_result.do","",sql,null);
////			str = ec.getExcelContent(list);
//			ec.getExcelFile("d:\\htmltest.xls");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("end:"+new Date());
//		System.out.println("str  "+str);
		String action="payment_record_all_query.do";
//		String sql="";
		try {
			ec.init("payment_record_all_query.do","","select * from  T_PaymentRecord where seqno in(15000293,15000176)  order by SEQNO DESC",new Object[]{"VOD演示用户01","M"});
			File file=ec.getExcelFile("d:\\test1.xls");
			System.out.println("file::  "+file);
//			System.out.println("getExcelContent::  "+ec.getExcelContent());
		} catch (WebActionException e) {
			e.printStackTrace();
		}
		System.out.println("end:"+new Date());

	}


}
