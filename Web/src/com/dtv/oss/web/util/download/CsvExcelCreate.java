package com.dtv.oss.web.util.download;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dtv.oss.web.exception.WebActionException;
/**
 * 生成csv格式的数据文件,可用excel打开,比较轻巧,缺点,没有格式支持,
 * @author 260327h
 *
 */
public class CsvExcelCreate extends ExcelCreate {
	public String getExcelContent() throws WebActionException {
		return null;
	}

	public File getExcelFile(String fileName) throws WebActionException {
		File file = new File(fileName);
		try {
			java.io.FileOutputStream fos=new FileOutputStream(file);
			java.io.OutputStreamWriter osw=new OutputStreamWriter(fos,"GBK"); 
			BufferedWriter out=new BufferedWriter(osw);
			out.write(generateCaptionRow(captionCellList));
			out.newLine();
			DownloadDAO dao = DownloadDAO.init(querySql, queryParameter);
			while (dao.hasNext()) {
				Map rowData = object2StringMap(dao.next());
				StringBuffer rowContent = new StringBuffer();
				rowContent.append(generateCsvExcelRow(rowData));
				out.write(rowContent.toString());
				out.newLine();
				out.flush();
			}
			if (isStat) {
				List statList = getStatRow();
				System.out.println("statList:" + statList);
				for (Iterator it = statList.iterator(); it.hasNext();) {
					out.write(generateCsvExcelRow((Map) it.next()));
					out.newLine();
				}
				out.flush();
			}
			out.newLine();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}

		return file;
	}
	
	private String generateCaptionRow(List list){
		StringBuffer res=new StringBuffer();
		for(Iterator it=list.iterator();it.hasNext();){
			HtmlCell cell=(HtmlCell) it.next();
			res.append(cell.getCaption()).append(",");
		}
		res.append("\n");
		return res.toString();
	}	
	private String generateCsvExcelRow(Map rowData){
		StringBuffer res=new StringBuffer();
		System.out.println("rowData:"+rowData);
		for(Iterator rit=rowData.entrySet().iterator();rit.hasNext();){
			Entry entry=(Entry) rit.next();
			HtmlCell cell=(HtmlCell) entry.getValue();
			String cellContent=cell.getValue();
			res.append(cellContent).append(",");
		}
		res.append("\n");
		return res.toString();
	}
	public static void main(String []args){
		CsvExcelCreate ec=new CsvExcelCreate();
		System.out.println("start:"+new Date());

		String action="customer_query_result.do";
		String sql="SELECT * FROM (SELECT ROWNUM MYNUM, XXX.* FROM (select a.*, addr.Postcode addr_Postcode, addr.DistrictID addr_DistrictID,addr.DetailAddress addr_DetailAddress,addr.AddressID addr_AddressID,addr.Dt_Create addr_Dt_Create,addr.Dt_Lastmod addr_Dt_Lastmod from t_customer a left join t_address addr on a.addressid = addr.addressid  , (select addressID from  T_ADDRESS where districtid in  (select Id from T_DISTRICTSETTING  connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid= 1))) viewAddress  where 1=1  and viewAddress.addressID=addr.addressID   and a.addressid = addr.addressid and a.status <> 'C' and (a.CUSTOMERSTYLE <> 'G' or a.customerstyle is null) order by a.CustomerID DESC) XXX) WHERE MYNUM BETWEEN 0 AND 5";

		try {
			ec.init("test1","",sql,new Object[]{"VOD演示用户01","M"});
			File file=ec.getExcelFile("d:\\test1.csv");
			System.out.println("file::  "+file);
		} catch (WebActionException e) {
			e.printStackTrace();
		}
		
		System.out.println("start:"+new Date());
	}
}
