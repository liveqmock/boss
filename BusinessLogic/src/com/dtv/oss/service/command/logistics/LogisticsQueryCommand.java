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
			//����ָ���豸��¼��ѯ
			case LogisticsQueryEJBEvent.BATCH_QUERY_TYPE_STOCKDEVICE:
				handler = new BatchDeviceListHandler();
				break;
			// �豸��ת��ѯ
			case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICETRANS:
				handler = new DeviceTransListHandler();
				break;
			// �豸��ת��ϸ��ѯ
			case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICETRANSDETAIL:
				handler = new DeviceTransDetailListHandler();
				break;
			//��ѯ��Ӧ���豸��Ϣ
			case LogisticsQueryEJBEvent.QUERY_TYPE_VENDOR_DEVICE:
				handler = new VendorDeviceListHandler();
				break;
			case LogisticsQueryEJBEvent.QUERY_TYPE_SCTERMINAL_DEVICE:
				handler = new TerminateDeviceListhandler();
				break;
			case LogisticsQueryEJBEvent.QUERY_TYPE_SCDEVICE:
				handler = new SCDeviceListhandler();
				break;
						// ���ܿ�����Ԥ��Ȩ��¼��ѯ
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREAUTH:
			handler = new DevicePreAuthListhandler();
			break;
			
			
		// ���ܿ�����Ԥ��Ȩ��¼��ѯ
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREAUTH_DETAIL:
			handler = new DevicePreAuthDetailListhandler();
			break;
			
		// ������/���ܿ�����ƥ���¼��ѯ
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_MATCH:
			handler = new DeviceMatchListhandler();
			break;
		// ������/���ܿ�������Լ�¼��ѯ
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_MATCH_DETAIL:
			handler = new DeviceMatchDetailListhandler();
			break;
//			 ���ܿ�Ԥ��Ȩ��ʷ��¼��ѯ
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREAUTH_HISTORY:
			handler = new DevicePreauthHisListhandler();
			break;	
//			 ���ܿ��䌦��ʷ��¼��ѯ
		case LogisticsQueryEJBEvent.QUERY_TYPE_DEVICE_BATCH_PREMATCH_HISTORY:
			handler = new DevicePrematchHisListhandler();
			break;		
		case LogisticsQueryEJBEvent.QUERY_VOD_STB_DEVICE_IMPORT_DETAIL:
			handler = new VodstbDeviceImportDetailListHandler();
			break;		
			  //��Ʊ��ѯ
/**
*wangfang 2008.05.22
*/
     case LogisticsQueryEJBEvent.QUERY_TYPE_FAPIAO:
        handler = new FaPiaoListHandler();
        break;

/**
*wangfang 2008.05.22
*/

        // ��Ʊ��ת��ϸ��ѯ
     case LogisticsQueryEJBEvent.QUERY_TYPE_FAPIAOTRANSDETAIL:
       handler = new FapiaoTransDetailListHandler();
       break;        
		default:
		}
		return handler;
	}

}