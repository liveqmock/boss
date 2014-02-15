package com.dtv.oss.web.util.download;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.exception.WebActionException;

/**
 * 使用jxl api生成excel文件,
 * 会有内存溢出问题,在数据量大的时候,本机试过20000条记录可以,大于20000条,就容易出现内存溢出,估计和jxl实现有关,在jxl里,所有的sheet的操作,都要到最后WritableWorkbook.close才写入文件,
 * 而中间没有提供相关flush的api,导致内存占用过大,
 * 
 * @author 260327h
 * 
 */
public class JxlExcelCreate extends ExcelCreate {
	private Map columnWidthMap = new HashMap();

	public String getExcelContent() throws WebActionException {
		return null;
	}

	public File getExcelFile(String fileName) throws WebActionException {
		File file = null;
		try {
			file = new File(fileName);
			WritableWorkbook wwb = Workbook.createWorkbook(file);
			int sheetNumber = 0, rowIndex = 1;
			Map rowData = null;
			WritableSheet ws = wwb.createSheet(file.getName()
					+ (sheetNumber + 1), sheetNumber);
			// 生成表头,
			generateCaptionRow(ws, captionCellList);
//			refurbishColumnWidth(captionCellList);
			DownloadDAO dao = DownloadDAO.init(querySql, queryParameter);
			while (dao.hasNext()) {
				rowData = object2StringMap(dao.next());
//				if (rowIndex == this.MAXRECORDSIZE) {
//					sheetNumber++;
//					setColumnWidth(ws);
//					ws = wwb.createSheet(file.getName() + (sheetNumber + 1),
//							sheetNumber);
//					rowIndex = 0;
//				}
				// 刷新列宽
				refurbishColumnWidth(rowData.values());
				// 生成数据行
				generateJxlExcelRow(ws, rowIndex, rowData);
				rowIndex++;
			}
			if (isStat) {
				List statList = getStatRow();
				for (Iterator it = statList.iterator(); it.hasNext();) {
					Map statRowMap=(Map) it.next();
					generateJxlExcelRow(ws, rowIndex, statRowMap);
					refurbishColumnWidth(statRowMap.values());
					rowIndex++;
				}
			}
			// 设置一下列宽
			setColumnWidth(ws);
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException("文件下载出错.");
		}
		return file;
	}


	/**
	 * 根据宽度缓存来设置sheet中每列宽度,在sheet遍历完后调用,
	 * 
	 * @param ws
	 */
	private void setColumnWidth(WritableSheet ws) {
//		System.out.println("columnWidthMap  " + columnWidthMap);
		for (Iterator rit = columnWidthMap.entrySet().iterator(); rit.hasNext();) {
			Entry entry = (Entry) rit.next();
			Integer col = (Integer) entry.getKey();
			Integer width = (Integer) entry.getValue();
			ws.setColumnView(col.intValue(), width.intValue());
		}
	}

	/**
	 * 刷新列宽度,每次列循环调用,取每列字符宽度和历史宽度比较,当前宽度大于历史宽度,则置换, 宽度计算为每字符X2+2,按汉字来算的,会大一些,
	 * 
	 * @param rowData
	 * @return
	 */
	private Map refurbishColumnWidth(Collection list) {
		if (columnWidthMap == null) {
			columnWidthMap = new HashMap();
		}
		int colIndex = 0;
		for (Iterator rit = list.iterator(); rit.hasNext(); colIndex++) {
			HtmlCell cell = (HtmlCell) rit.next();
			Integer key = new Integer(colIndex);
			String cellContent = cell.getValue();
			String captionContent=cell.getCaption()!=null?cell.getCaption():"";
			String content=cellContent.length()>captionContent.length()?cellContent:captionContent;
			int width = content.length() * 2 + 2;
			int oldWidth = columnWidthMap.containsKey(key) ? ((Integer) columnWidthMap
					.get(key)).intValue()
					: 0;
			if (width > oldWidth) {
				columnWidthMap.put(key, new Integer(width));
			}
		}
		return null;
	}

	/**
	 * 生成内容行,
	 * 
	 * @param ws
	 * @param rowIndex
	 *            行索引
	 * @param rowData
	 *            行数据
	 * @return
	 * @throws WriteException
	 */
	private int generateJxlExcelRow(WritableSheet ws, int rowIndex, Map rowData)
			throws WriteException {
//		System.out.println("rowIndex::  "+rowIndex);
//		System.out.println("rowData::  "+rowData);
		int colIndex = 0;
		for (Iterator rit = rowData.entrySet().iterator(); rit.hasNext(); colIndex++) {
			Entry entry = (Entry) rit.next();
			HtmlCell cell = (HtmlCell) entry.getValue();
			String cellContent = cell.getValue();
			ws.addCell(generateCell(colIndex, rowIndex, cellContent, cell
					.getValuebgcolor(), cell.getAlign()));
		}
		return rowIndex;
	}

	/**
	 * 生成标题行
	 * 
	 * @param ws
	 *            工作表
	 * @param map
	 *            数据map
	 * @return
	 * @throws WriteException
	 */
	private int generateCaptionRow(WritableSheet ws, List list)
			throws WriteException {
		int i = 0;
//		System.out.println("captionList::  "+list);
		for (Iterator it = list.iterator(); it.hasNext(); i++) {
			HtmlCell hCell = (HtmlCell) it.next();
			ws.addCell(generateColumnHead(i, 0, hCell.getCaption(), hCell
					.getCaptionbgcolor()));
		}
		return 0;
	}

	/**
	 * 生成表头单元格,水平对齐和垂直对齐默认居中 ,
	 * 
	 * @param c
	 *            列索引
	 * @param r
	 *            行索引
	 * @param content
	 *            内容
	 * @param bgColor
	 *            背景色
	 * @return
	 * @throws WriteException
	 */
	private WritableCell generateColumnHead(int c, int r, String content,
			String bgColor) throws WriteException {
		jxl.write.WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//		if (bgColor != null && !"".equals(bgColor)) {
			cellFormat.setBackground(getJxlColour(bgColor));
//		}
		jxl.write.WritableCell wc = new jxl.write.Label(c, r, content,
				cellFormat);
		return wc;
	}

	/**
	 * 生成jxl单元格,垂直对齐默认用居中
	 * 
	 * @param c
	 *            列索引
	 * @param r
	 *            行索引
	 * @param content
	 *            单元格内容
	 * @param bgColor
	 *            背景色
	 * @param align
	 *            对齐
	 * @return
	 * @throws WriteException
	 */
	private WritableCell generateCell(int c, int r, String content,
			String bgColor, String align) throws WriteException {
		jxl.write.WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setAlignment(getJxlAlignment(align));
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (bgColor != null && !"".equals(bgColor)) {
			cellFormat.setBackground(getJxlColour(bgColor));
		}
		jxl.write.WritableCell wc = new jxl.write.Label(c, r, content,
				cellFormat);
		return wc;
	}

	/**
	 * 取得jxl支持的颜色, jxl api支持的颜色有
	 * RED/BRIGHT_GREEN/BLUE/YELLOW/PINK/TURQUOISE/DARK_RED/
	 * GREEN/DARK_BLUE/DARK_YELLOW/VIOLET/TEAL/GREY_25_PERCENT/
	 * GREY_50_PERCENT/PERIWINKLE/PLUM2/IVORY /LIGHT_TURQUOISE2/
	 * DARK_PURPLE/CORAL/OCEAN_BLUE/ICE_BLUE/DARK_BLUE2/
	 * PINK2/YELLOW2/TURQOISE2/VIOLET2/DARK_RED2/TEAL2/
	 * BLUE2/SKY_BLUE/LIGHT_TURQUOISE/LIGHT_GREEN/VERY_LIGHT_YELLOW/
	 * PALE_BLUE/ROSE/LAVENDER/TAN/LIGHT_BLUE/AQUA/LIME/GOLD/LIGHT_ORANGE/
	 * ORANGE/BLUE_GREY/GREY_40_PERCENT/DARK_TEAL/SEA_GREEN/DARK_GREEN/OLIVE_GREEN/
	 * BROWN/PLUM/INDIGO/GREY_80_PERCENT/AUTOMATIC/,
	 * 
	 * @param color
	 * @return
	 */
	private Colour getJxlColour(String color) {
		Colour[] colours = Colour.getAllColours();
		for (int i = 0; i < colours.length; i++) {
			if (colours[i].getDescription().equalsIgnoreCase(color)) {
				System.out.println("color1::  "+color);
				return colours[i];
			}
		}
//		System.out.println("getCaptionbgcolor::  ");
		return Colour.UNKNOWN;
	}

	/**
	 * 取得jxl支持的对齐方式, 只支持center/left/right
	 * 
	 * @param align
	 * @return
	 */
	private Alignment getJxlAlignment(String align) {
		if ("center".equalsIgnoreCase(align)) {
			return Alignment.CENTRE;
		} else if ("left".equalsIgnoreCase(align)) {
			return Alignment.LEFT;
		} else if ("right".equalsIgnoreCase(align)) {
			return Alignment.RIGHT;
		}
		return Alignment.GENERAL;
	}

	public static void main(String[] args) {
		System.out.println("start:" + new Date());
		JxlExcelCreate ec = new JxlExcelCreate();
		
		String action="customer_query_result.do";
		String sql="SELECT * FROM (SELECT ROWNUM MYNUM, XXX.* FROM (select a.*, addr.Postcode addr_Postcode, addr.DistrictID addr_DistrictID,addr.DetailAddress addr_DetailAddress,addr.AddressID addr_AddressID,addr.Dt_Create addr_Dt_Create,addr.Dt_Lastmod addr_Dt_Lastmod from t_customer a left join t_address addr on a.addressid = addr.addressid  , (select addressID from  T_ADDRESS where districtid in  (select Id from T_DISTRICTSETTING  connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid= 1))) viewAddress  where 1=1  and viewAddress.addressID=addr.addressID   and a.addressid = addr.addressid and a.status <> 'C' and (a.CUSTOMERSTYLE <> 'G' or a.customerstyle is null) order by a.CustomerID DESC) XXX) WHERE MYNUM BETWEEN 0 AND 5";

		try {
			ec.init("test1","",sql,new Object[]{"VOD演示用户01","M"});
			File file=ec.getExcelFile("d:\\test1.xls");
			System.out.println("file::  "+file);
		} catch (WebActionException e) {
			e.printStackTrace();
		}
//		System.out.println("str  " + str);
		System.out.println("end:" + new Date());

		// try {
		// Method m = Postern.class.getMethod("getProductDTO", null);
		// System.out.println("Method " + m);
		// Object obj = m.invoke(null, null);
		// System.out.println("invoke " + obj);
		// } catch (Exception e) {
		// e.printStackTrace();
		//		}

		//		while(list.size()<30000){
		//			list.addAll(list);
		//		}
		//		System.out.println("list.size()  "+list.size());
		//		File file=ec.getExcelFile(list, "d:/abcd.xls");
		//		System.out.println("file.length() "+file.length());
	}
}
