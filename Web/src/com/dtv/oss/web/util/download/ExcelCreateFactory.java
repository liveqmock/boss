package com.dtv.oss.web.util.download;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;

public class ExcelCreateFactory {
	public static final int EXCEL_CREATE_CSV=1;
	public static final int EXCEL_CREATE_HTML=2;

	private ExcelCreateFactory(){}
	
	public static synchronized ExcelCreate getExcelCreate(int index){
		ExcelCreate create=null;
		switch (index){
			case EXCEL_CREATE_CSV:
				create=new CsvExcelCreate();
				LogUtility.log(ExcelCreateFactory.class, LogLevel.DEBUG, "���������Csv��ʽ��excel����,���޷�Ӧ����ʽ.�����");
				break;
			case EXCEL_CREATE_HTML:
				create=new HtmlExcelCreate();
				break;
		}
		return create;
	}
}
