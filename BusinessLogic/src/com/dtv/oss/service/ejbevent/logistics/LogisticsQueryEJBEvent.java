/*
 * Created on 2005-11-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.logistics;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

/**
 * @author 241115c
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LogisticsQueryEJBEvent extends QueryEJBEvent {

	/**
	 * constructor
	 */
	public LogisticsQueryEJBEvent(Object dto, int from, int to, int querytype) {
		super(dto, from, to, querytype);
	}

	/**
	 * �豸����ѯ
	 */
	public static final int QUERY_TYPE_STOCKDEVICE = 1;

	/**
	 * �豸��ת��ʷ��¼��ѯ
	 */
	public static final int QUERY_TYPE_DEVICETRANS = 2;

	/**
	 * �豸��ת��ʷ��¼��ϸ��ѯ
	 */
	public static final int QUERY_TYPE_DEVICETRANSDETAIL = 3;

	/**
	 * ��ѯ����Ա�������۵��豸
	 */
	public static final int QUERY_TYPE_DEVICESELLABLE = 4;
	
	/**
	 * ���ҹ�Ӧ�̵��豸��Ϣ
	 */
	public static final int QUERY_TYPE_VENDOR_DEVICE=5;
	/**
	 * ��ѯ���ܿ�
	 */
	public static final int QUERY_TYPE_SCTERMINAL_DEVICE=6;

	/**
	 * ��ѯ���ܿ�
	 */
	public static final int QUERY_TYPE_SCDEVICE=7;
	/**
	 * ���ܿ�����Ԥ��Ȩ��¼��ѯ
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_PREAUTH=8;

	
	/**
	 * ���ܿ�����Ԥ��Ȩ��¼��ѯ-��ϸ
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_PREAUTH_DETAIL=9;	
	
	/**
	 * ���ܿ�����ƥ���¼��ѯ
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_MATCH=10;
	
	/**
	 * ���ܿ�����ƥ���¼��ѯ-��ϸ
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_MATCH_DETAIL=11;	
	
	/**
	 * ���ܿ�Ԥ��Ȩ��ʷ��¼��ѯ
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_PREAUTH_HISTORY=12;	
	
	/**
	 * ���ܿ������ʷ��¼��ѯ
	 */
	public static final int	QUERY_TYPE_DEVICE_BATCH_PREMATCH_HISTORY=13;

    
	/**
	 * wang fang 2008.05.22 
	 * ��Ʊ��¼��ѯ
	 */
	public static final int QUERY_TYPE_FAPIAO= 14;


	/**
	 * wang fang 2008.05.22 
	 * ��Ʊ��ת��¼��ѯ
	 */
	public static final int QUERY_TYPE_FAPIAOTRANSDETAIL= 15;	
	
	//����ָ���豸��¼��ѯ
	public static final int 	BATCH_QUERY_TYPE_STOCKDEVICE= 16;	
	
	public static final int 	QUERY_VOD_STB_DEVICE_IMPORT_DETAIL= 17;


}