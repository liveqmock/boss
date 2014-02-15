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
 * ʹ��jxl api����excel�ļ�,
 * �����ڴ��������,�����������ʱ��,�����Թ�20000����¼����,����20000��,�����׳����ڴ����,���ƺ�jxlʵ���й�,��jxl��,���е�sheet�Ĳ���,��Ҫ�����WritableWorkbook.close��д���ļ�,
 * ���м�û���ṩ���flush��api,�����ڴ�ռ�ù���,
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
			// ���ɱ�ͷ,
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
				// ˢ���п�
				refurbishColumnWidth(rowData.values());
				// ����������
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
			// ����һ���п�
			setColumnWidth(ws);
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException("�ļ����س���.");
		}
		return file;
	}


	/**
	 * ���ݿ�Ȼ���������sheet��ÿ�п��,��sheet����������,
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
	 * ˢ���п��,ÿ����ѭ������,ȡÿ���ַ���Ⱥ���ʷ��ȱȽ�,��ǰ��ȴ�����ʷ���,���û�, ��ȼ���Ϊÿ�ַ�X2+2,�����������,���һЩ,
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
	 * ����������,
	 * 
	 * @param ws
	 * @param rowIndex
	 *            ������
	 * @param rowData
	 *            ������
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
	 * ���ɱ�����
	 * 
	 * @param ws
	 *            ������
	 * @param map
	 *            ����map
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
	 * ���ɱ�ͷ��Ԫ��,ˮƽ����ʹ�ֱ����Ĭ�Ͼ��� ,
	 * 
	 * @param c
	 *            ������
	 * @param r
	 *            ������
	 * @param content
	 *            ����
	 * @param bgColor
	 *            ����ɫ
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
	 * ����jxl��Ԫ��,��ֱ����Ĭ���þ���
	 * 
	 * @param c
	 *            ������
	 * @param r
	 *            ������
	 * @param content
	 *            ��Ԫ������
	 * @param bgColor
	 *            ����ɫ
	 * @param align
	 *            ����
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
	 * ȡ��jxl֧�ֵ���ɫ, jxl api֧�ֵ���ɫ��
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
	 * ȡ��jxl֧�ֵĶ��뷽ʽ, ֻ֧��center/left/right
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
			ec.init("test1","",sql,new Object[]{"VOD��ʾ�û�01","M"});
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
