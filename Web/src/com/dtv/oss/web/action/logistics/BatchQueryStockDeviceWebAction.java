package com.dtv.oss.web.action.logistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.util.CurrentOperator;
import com.dtv.oss.web.util.WebUtil;
/**
 * @author caochenyi
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class BatchQueryStockDeviceWebAction extends DownloadWebAction {

	/* ���� Javadoc��
	 * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request)
		throws Exception {
		 CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
		 //���ɱ��β���Ա����������
		 String batchNO=TimestampUtility.getCurrentTimestamp().toString()+"_"+CurrentOperator.GetCurrentOperatorID(request);
         //�����������ݵĴ洢
		 if (WebUtil.StringHaveContent(request.getParameter("txtBatchSerialNo")))
        	 createbatchQuerySerialNORecord(batchNO,request.getParameter("txtBatchSerialNo"));
         //���������Թ���ѯ  
		 dto.setSpareStr1(batchNO);      
       return new LogisticsQueryEJBEvent(dto, pageFrom, pageTo, LogisticsQueryEJBEvent.BATCH_QUERY_TYPE_STOCKDEVICE);
	}
	/**
	 * ������Ĵ���ѯ�����кŽ��д���
	 * @param batchNo
	 * @param batchSerialNo
	 * @throws Exception
	 */
	private void createbatchQuerySerialNORecord(String batchNo,String  batchSerialNo)throws Exception{
		PreparedStatement batchQueryStat=null;
		Connection con=null;
		try {	
			String ctreateSql="insert into GEHUA_DEVICESERIALNO_FOR_QUERY (BATCHNO,SERIALNO)"+
			"values (?,?)";
			con = DBUtil.getConnection();
			batchQueryStat=con.prepareStatement(ctreateSql);
			String[] serialsArray = batchSerialNo.split(",");
			if(serialsArray!=null &&serialsArray.length>0){
				for (int i=0;i<serialsArray.length;i++){
					if(!"".equals(serialsArray[i])){
						batchQueryStat.setString(1, batchNo);
						batchQueryStat.setString(2, serialsArray[i]);
						batchQueryStat.addBatch();
					}
				}
			}
			batchQueryStat.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��������ѯ���к�ʱ��������");
		} finally {
			if (batchQueryStat != null) {
				try {
					batchQueryStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception("��������ѯ���к�ʱ��������");
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception("��������ѯ���к�ʱ��������");
				}
			}
		}
	}	
}
