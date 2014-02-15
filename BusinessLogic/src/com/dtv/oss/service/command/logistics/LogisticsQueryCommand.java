package com.dtv.oss.service.command.logistics;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.service.listhandler.csr.TerminateDeviceListhandler;
import com.dtv.oss.service.listhandler.logistics.BatchDeviceListHandler;
import com.dtv.oss.service.listhandler.logistics.DeviceListHandler;
import com.dtv.oss.service.listhandler.logistics.DeviceMatchDetailListhandler;
import com.dtv.oss.service.listhandler.logistics.DeviceMatchListhandler;
import com.dtv.oss.service.listhandler.logistics.DevicePreAuthDetailListhandler;
import com.dtv.oss.service.listhandler.logistics.DevicePreAuthListhandler;
import com.dtv.oss.service.listhandler.logistics.DevicePreauthHisListhandler;
import com.dtv.oss.service.listhandler.logistics.DevicePrematchHisListhandler;
import com.dtv.oss.service.listhandler.logistics.SCDeviceListhandler;
import com.dtv.oss.service.listhandler.logistics.DeviceTransListHandler;
import com.dtv.oss.service.listhandler.logistics.DeviceTransDetailListHandler;
import com.dtv.oss.service.listhandler.logistics.VendorDeviceListHandler;
import com.dtv.oss.service.listhandler.logistics.FaPiaoListHandler;
import com.dtv.oss.service.listhandler.logistics.FapiaoTransDetailListHandler;
import com.dtv.oss.service.listhandler.logistics.VodstbDeviceImportDetailListHandler;
public class LogisticsQueryCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch (event.getType()) {
		
			case LogisticsQueryEJBEvent.QUERY_TYPE_STOCKDEVICE:
				handler = new DeviceListHandler();
				break;
			//批量指定设备记录查询
			case LogisticsQueryEJBEvent.BATCH_QUERY_TYPE_STOCKDEVICE:
				handler = new BatchDeviceListHandler();
				break;
			// 设备流转查询
			case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICETRANS:
				handler = new DeviceTransListHandler();
				break;
			// 设备流转明细查询
			case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICETRANSDETAIL:
				handler = new DeviceTransDetailListHandler();
				break;
			//查询供应商设备信息
			case LogisticsQueryEJBEvent.QUERY_TYPE_VENDOR_DEVICE:
				handler = new VendorDeviceListHandler();
				break;
			case LogisticsQueryEJBEvent.QUERY_TYPE_SCTERMINAL_DEVICE:
				handler = new TerminateDeviceListhandler();
				break;
			case LogisticsQueryEJBEvent.QUERY_TYPE_SCDEVICE:
				handler = new SCDeviceListhandler();
				break;
						// 智能卡批量预授权记录查询
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREAUTH:
			handler = new DevicePreAuthListhandler();
			break;
			
			
		// 智能卡批量预授权记录查询
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREAUTH_DETAIL:
			handler = new DevicePreAuthDetailListhandler();
			break;
			
		// 机顶盒/智能卡批量匹配记录查询
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_MATCH:
			handler = new DeviceMatchListhandler();
			break;
		// 机顶盒/智能卡批量配对记录查询
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_MATCH_DETAIL:
			handler = new DeviceMatchDetailListhandler();
			break;
//			 智能卡预授权历史记录查询
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREAUTH_HISTORY:
			handler = new DevicePreauthHisListhandler();
			break;	
//			 智能卡配對历史记录查询
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREMATCH_HISTORY:
			handler = new DevicePrematchHisListhandler();
			break;		
		case LogisticsQueryEJBEvent.QUERY_VOD_STB_DEVICE_IMPORT_DETAIL:
			handler = new VodstbDeviceImportDetailListHandler();
			break;		
			  //发票查询
/**
*wangfang 2008.05.22
*/
     case LogisticsQueryEJBEvent.QUERY_TYPE_FAPIAO:
        handler = new FaPiaoListHandler();
        break;

/**
*wangfang 2008.05.22
*/

        // 发票流转明细查询
     case LogisticsQueryEJBEvent.QUERY_TYPE_FAPIAOTRANSDETAIL:
       handler = new FapiaoTransDetailListHandler();
       break;        
		default:
		}
		return handler;
	}

}