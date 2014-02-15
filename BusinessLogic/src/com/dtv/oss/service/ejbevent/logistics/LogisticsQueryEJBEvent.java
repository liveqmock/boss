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
	 * 设备库存查询
	 */
	public static final int QUERY_TYPE_STOCKDEVICE = 1;

	/**
	 * 设备流转历史纪录查询
	 */
	public static final int QUERY_TYPE_DEVICETRANS = 2;

	/**
	 * 设备流转历史纪录明细查询
	 */
	public static final int QUERY_TYPE_DEVICETRANSDETAIL = 3;

	/**
	 * 查询操作员允许销售的设备
	 */
	public static final int QUERY_TYPE_DEVICESELLABLE = 4;
	
	/**
	 * 查找供应商的设备信息
	 */
	public static final int QUERY_TYPE_VENDOR_DEVICE=5;
	/**
	 * 查询智能卡
	 */
	public static final int QUERY_TYPE_SCTERMINAL_DEVICE=6;

	/**
	 * 查询智能卡
	 */
	public static final int QUERY_TYPE_SCDEVICE=7;
	/**
	 * 智能卡批量预授权记录查询
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_PREAUTH=8;

	
	/**
	 * 智能卡批量预授权记录查询-明细
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_PREAUTH_DETAIL=9;	
	
	/**
	 * 智能卡批量匹配记录查询
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_MATCH=10;
	
	/**
	 * 智能卡批量匹配记录查询-明细
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_MATCH_DETAIL=11;	
	
	/**
	 * 智能卡预授权历史记录查询
	 */
	public static final int QUERY_TYPE_DEVICE_BATCH_PREAUTH_HISTORY=12;	
	
	/**
	 * 智能卡配对历史记录查询
	 */
	public static final int	QUERY_TYPE_DEVICE_BATCH_PREMATCH_HISTORY=13;

    
	/**
	 * wang fang 2008.05.22 
	 * 发票记录查询
	 */
	public static final int QUERY_TYPE_FAPIAO= 14;


	/**
	 * wang fang 2008.05.22 
	 * 发票流转记录查询
	 */
	public static final int QUERY_TYPE_FAPIAOTRANSDETAIL= 15;	
	
	//批量指定设备记录查询
	public static final int 	BATCH_QUERY_TYPE_STOCKDEVICE= 16;	
	
	public static final int 	QUERY_VOD_STB_DEVICE_IMPORT_DETAIL= 17;


}