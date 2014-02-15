package com.dtv.oss.service.component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.FaPiao;
import com.dtv.oss.domain.FaPiaoHome;
import com.dtv.oss.domain.Fapiao2Payment;
import com.dtv.oss.domain.Fapiao2PaymentHome;
import com.dtv.oss.domain.FapiaoDetail;
import com.dtv.oss.domain.FapiaoDetailHome;
import com.dtv.oss.domain.FapiaoTransition;
import com.dtv.oss.domain.FapiaoTransitionDetail;
import com.dtv.oss.domain.FapiaoTransitionDetailHome;
import com.dtv.oss.domain.FapiaoTransitionHome;
import com.dtv.oss.domain.FapiaoVolumn;
import com.dtv.oss.domain.FapiaoVolumnHome;
import com.dtv.oss.dto.FaPiaoDTO;
import com.dtv.oss.dto.Fapiao2PaymentDTO;
import com.dtv.oss.dto.FapiaoDetailDTO;
import com.dtv.oss.dto.FapiaoTransitionDTO;
import com.dtv.oss.dto.FapiaoTransitionDetailDTO;
import com.dtv.oss.dto.FapiaoVolumnDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.logistics.FaPiaoEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.TimestampUtility;

/**
 * ��Ʊ��ת����� 
 * 
 * @author 
 * 
 */
public class FaPiaoService extends AbstractService {

	private static final Class clazz = FaPiaoService.class;

	private ServiceContext context;

	/**
	 * ���캯��
	 * 
	 * @param s
	 */
	public FaPiaoService(ServiceContext s) {
		this.context = s;
	}

	/**
	 *  ��Ʊ����
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoInStock(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		
		
		if (event.getSerailNoList()==null || event.getSerailNoList().isEmpty())
			throw new ServiceException("û����Ҫ����ķ�Ʊ���к�");
		//���
		checkValideFaPiaoIn(event);
		//LogUtility.log(this.getClass(), LogLevel.DEBUG, "__1_type="+commDto.getSpareStr2());
		if (event.isDoPost())
			//��¼�����Ϣ
			faPiaoTransitionForIn(event);
		this.context.put(Service.PROCESS_RESULT, event.getSerailNoList());

	}

	/**
	 *  ��Ʊ�ؿ�
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoBack(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		//���
		checkValideFaPiaoBack(event);
		if (event.isDoPost())
			//��¼�����Ϣ
			faPiaoTransitionForBack(event);
		this.context.put(Service.PROCESS_RESULT, event.getSerailNoList());

	}

	/**
	 *  ��Ʊ����
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoMove(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		//���
		checkValideFaPiaoMove(event);
		if (event.isDoPost())
			//��¼�����Ϣ
			faPiaoTransitionForMove(event);
		this.context.put(Service.PROCESS_RESULT, event.getSerailNoList());

	}
	
	/**
	 *  ��Ʊ����
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoUse(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		//���
		checkValideFaPiaoUse(event);
		if (event.isDoPost())
			//��¼�����Ϣ
			faPiaoTransitionForUse(event);
		this.context.put(Service.PROCESS_RESULT, event.getSerailNoList());

	}
	
		/**
	 * ��Ʊ����
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
		 * @throws CreateException 
	 * 
	 */
	// ��Ʊ���ϣ�wangpeng@20080313
	public void faPiaoAbandon(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException, CreateException {
	
		CommonQueryConditionDTO commDto = event.getCommDTO();
		String volumnSetting=commDto.getSpareStr4();
		String serialsStr = commDto.getSpareStr1();
		String fapiaoType = commDto.getSpareStr2();
		List dtLastmodList=new ArrayList();
		
		String volumn="";
		if(volumnSetting.equals("Y")){
			volumn= commDto.getSpareStr3();
		}

		if (serialsStr == null || "".equals(serialsStr))
			throw new ServiceException("û����Ҫ����ķ�Ʊ���к�");
	
		String[] serialsArray = serialsStr.split("\n");
		ArrayList serialsList = new ArrayList();
		for (int i = 0; i < serialsArray.length; i++) {
			if (serialsArray[i] != null && !"".equals(serialsArray[i].trim()))
				serialsList.add(serialsArray[i].trim());
		}
		// �������Ϸ�Ʊ�Ƿ����㡰��Ϊ���ϲ��Ҳ�Ϊ���ϡ���״̬
		checkFapiaoStatusForAbandon(serialsList,fapiaoType,volumn);

		
		if(!event.isDoPost()){
			/*
			 * ȡ�����п��������Ϸ�Ʊ�� DtLastmod
			 * �洢��dtLastmodList�С�
			 */
					Iterator iter = serialsList.iterator();
					while (iter.hasNext()) {
						String serial = (String) iter.next();
						List list=BusinessUtility.getFaPiaoDtoByCon(fapiaoType,volumn,serial);
						FaPiaoDTO dto=(FaPiaoDTO)list.get(0);
						
						dtLastmodList.add(dto.getDtLastmod());
					}
		}	
		
		int operID=event.getOperatorID();
		int orgID=BusinessUtility.FindOrgIDByOperatorID(operID);
		
		if (event.isDoPost()) {
			
			//ȡ������dtLastmod,һ���ַ������Զ��Ÿ���
			String dtLastmods=event.getDtLastmods();
			String [] dtLastmodsArr=dtLastmods.split(",");
			
			int arrIndex=0;

			// ��ʼ����ÿһ�Ŵ����Ϸ�Ʊ��������ݱ��
			Iterator i = serialsList.iterator();
			
			String hasAbandonFapiaoSN=new String();
			
			
			/*
			 * Step3.����ƱTransition
			 */
			FapiaoTransitionDTO fapiaoTransitionDto=new FapiaoTransitionDTO();
			int volumnSeqNo=0;//0Ϊû�й���Ʊ��ʱ����Ʊ���ͳһĬ��ֵ��				
			if(volumn!=null&&!"".equals(volumn)){
					FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
					volumnSeqNo=fapiaoVolumn.getSeqNo().intValue();
			}
			fapiaoTransitionDto.setVolumnSeqNo(volumnSeqNo);
			fapiaoTransitionDto.setTotal(serialsList.size());
			fapiaoTransitionDto.setOpID(operID);
			fapiaoTransitionDto.setOrgID(orgID);
			fapiaoTransitionDto.setAction("CANC");
			fapiaoTransitionDto.setStatus("V");
			fapiaoTransitionDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());					
			
			FapiaoTransitionHome faPiaoTransHome = HomeLocater.getFapiaoTransitionHome();
		
			FapiaoTransition faPiaoTrans = faPiaoTransHome.create(fapiaoTransitionDto);
			fapiaoTransitionDto.setSeqNo(faPiaoTrans.getSeqNo().intValue());
			
			while (i.hasNext()) {
				String serial = (String) i.next();
				
				//ȡ����ǰ��Ʊ��dtLastmod
				Timestamp oneDtLastmod=Timestamp.valueOf(dtLastmodsArr[arrIndex]);
				arrIndex++;
				
				/*
				 * Step1.����Ʊ����
				 * 
				 *
				 */	
				List list=BusinessUtility.getFaPiaoDtoByCon(fapiaoType,volumn,serial);
				FaPiaoDTO fapiaoDto=(FaPiaoDTO)list.get(0);
				int fapiaoSeqNo=fapiaoDto.getSeqNo();
				
				String fromStatus=fapiaoDto.getStatus();
				String addressType=fapiaoDto.getAddressType();
				int addressID=fapiaoDto.getAddressID();
				fapiaoDto.setStatus(CommonConstDefinition.FAPIAO_STATUS_CAN);
				fapiaoDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
				fapiaoDto.setDtLastmod(oneDtLastmod);

		 		FaPiaoHome faPiaoHome = HomeLocater.getFaPiaoHome();
				FaPiao fapiao=faPiaoHome.findByPrimaryKey(new Integer(fapiaoSeqNo));
				
				if(fapiao.ejbUpdate(fapiaoDto)==-1){
					throw new ServiceException("Step1.����Ʊ���ݳ���");
				}
				hasAbandonFapiaoSN+="'"+serial+"',";

	
				try {

					/*
					 * Step4.����ƱTransitionDetail
					 */
					FapiaoTransitionDetailDTO faPiaoTransDetailDto=new FapiaoTransitionDetailDTO();
					faPiaoTransDetailDto.setFapiaoSeqNo(fapiaoSeqNo);
					faPiaoTransDetailDto.setVolumnSeqNo(String.valueOf(volumnSeqNo));
					faPiaoTransDetailDto.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
					faPiaoTransDetailDto.setOpID(operID);
					faPiaoTransDetailDto.setOrgID(orgID);
					faPiaoTransDetailDto.setAction("CANC");
					faPiaoTransDetailDto.setFromStatus(fromStatus);
					faPiaoTransDetailDto.setToStatus("CAN");
					
					faPiaoTransDetailDto.setFromAddressID(addressID);
					faPiaoTransDetailDto.setFromAddressType(addressType);					
					faPiaoTransDetailDto.setToAddressID(addressID);
					faPiaoTransDetailDto.setToAddressType(addressType);
					
					faPiaoTransDetailDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
					faPiaoTransDetailDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					
					FapiaoTransitionDetailHome faPiaoTransDetailHome = HomeLocater.getFapiaoTransitionDetailHome();
					FapiaoTransitionDetail faPiaoTransDetail = faPiaoTransDetailHome.create(faPiaoTransDetailDto);
					faPiaoTransDetailDto.setSeqNo(faPiaoTransDetail.getSeqNo().intValue());			
					//System.out.println("---------------------------4------------------------");

					
/*��ʱ������������ʵ��Ĵ洢					
					
					
					//Step5.����ƱFAPIAO2PAYMENT
					 
					Fapiao2PaymentHome paymentHome= HomeLocater.getFapiao2PaymentHome();
					
					List paymentList=BusinessUtility.getFapiao2PaymentByFapiaoSN(fapiaoSeqNo);
					Iterator iter1=paymentList.iterator();
					while(iter1.hasNext()){
						Fapiao2PaymentDTO paymentDTO=(Fapiao2PaymentDTO)iter1.next();
						int paymentSeqNo=paymentDTO.getSeqNo();
						System.out.println("_________________paymentSeqNo,"+paymentSeqNo);
						Fapiao2Payment payment=paymentHome.findByPrimaryKey(new Integer(paymentSeqNo));		
						payment.remove();
					}
					System.out.println("---------------------------5------------------------");					
*/					
					 //Step6.����ƱFAPIAODETAIL
					
					FapiaoDetailHome detailHome= HomeLocater.getFapiaoDetailHome();
					
					List detailList=BusinessUtility.getFapiaoDetailByFapiaoSeqNo(fapiaoSeqNo);
					Iterator iter2=detailList.iterator();
					while(iter2.hasNext()){
						FapiaoDetailDTO detailDTO=(FapiaoDetailDTO)iter2.next();
						int detailSeqNo=detailDTO.getSeqNo();
						//System.out.println("_______________detailSeqNo."+detailSeqNo);
						
						FapiaoDetail detail=detailHome.findByPrimaryKey(new Integer(detailSeqNo));		
						detail.remove();
					}
					//System.out.println("---------------------------6------------------------");

				} catch (CreateException e) {
					LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e);
					throw new ServiceException("������¼����");
				} catch (EJBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoveException e) {
					LogUtility.log(clazz, LogLevel.WARN, "ɾ����¼����" + e);
					throw new ServiceException("ɾ����¼����");
				} 	
				
			}
			hasAbandonFapiaoSN=hasAbandonFapiaoSN.substring(0,hasAbandonFapiaoSN.length()-1);
			
			/*
			 * Step2.����Ʊ�ᡣ
			 * �����Ʊ��SNΪ�գ���Ϊ������Ʊ������ã�
			 * ��ô������Ʊ�ᴦ��
			 * 
			 * ����÷�Ʊ���еķ�Ʊ״̬��Ϊ"CAN"�����ϣ�
			 * ��ô��Ʊ��״̬Ҳ���Ϊ"CAN"��
			 */
			if(volumn!=null&&!volumn.equals("")){
				if(!BusinessUtility.hasUnCanceledFapiao(fapiaoType, volumn,hasAbandonFapiaoSN)){
					
					FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
					
					FapiaoVolumnDTO volumnDto =new FapiaoVolumnDTO();
					volumnDto.setStatus(CommonConstDefinition.FAPIAO_STATUS_CAN);
					volumnDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
					volumnDto.setDtLastmod(fapiaoVolumn.getDtLastmod());
					if(fapiaoVolumn.ejbUpdate(volumnDto)==-1){
						throw new ServiceException("Step2.����Ʊ�����ݳ���");
					}
					//System.out.println("---------------------------2------------------------");						
				}
			}			
	
		}
			
		this.context.put(Service.PROCESS_RESULT, dtLastmodList);
		
	
	}

	
	/**
	 * ��Ʊ����
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 * @throws CreateException 
	 * 
	 */
	// ��Ʊ���ϣ�wangpeng@20080320
	public void faPiaoDiscard(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException, CreateException {
		FaPiaoDTO fapiaoDTO = event.getFaPiaoDTO();
		FapiaoVolumnDTO volumnDTO=event.getFapiaoVolumnDTO();
		boolean isVolumnManage=event.isVolumnManage();
		//List dtLastmodList=new ArrayList();	
		String volumn="";
		String fapiaoType=fapiaoDTO.getType();
		List fapiaoList=new ArrayList();//����FapiaoDTO�ļ���
		/*
		 * ��Ʊ���ű���
		 */		
		if(event.getDiscardStyle().equals("2")){
			String serialsStr = event.getSerailnos();
			if(isVolumnManage){
				volumn= volumnDTO.getVolumnSn();
			}
			if (serialsStr == null || "".equals(serialsStr))
				throw new ServiceException("û����Ҫ���ϵķ�Ʊ���к�");
			String[] serialsArray = serialsStr.split("\n");
			List serialsList = new ArrayList();
			for (int i = 0; i < serialsArray.length; i++) {
				if (serialsArray[i] != null && !"".equals(serialsArray[i].trim()))
					serialsList.add(serialsArray[i].trim());
			}
			// �������Ϸ�Ʊ�Ƿ����㡰��Ϊ���ϲ��Ҳ�Ϊ���ϡ���״̬
			checkFapiaoStatusForDiscard(serialsList,fapiaoType,volumn);
			//ȡ�����з�ƱDTO���洢��fapiaoList�С�
			Iterator iterator=serialsList.iterator();
			while(iterator.hasNext()){
				List tempList=BusinessUtility.getFaPiaoDtoByCon(fapiaoType, volumn, (String)iterator.next());
				if(tempList!=null){
					FaPiaoDTO oneDTO=(FaPiaoDTO)tempList.get(0);
					fapiaoList.add(oneDTO);
				}
			}
			this.context.put(Service.PROCESS_RESULT, fapiaoList);
			if (event.isDoPost()) {
				//��������Ϸ�Ʊ
				discardFapiao(event, volumn, fapiaoType, serialsList);
				if(isVolumnManage){
				/*
				 * �ж��Ƿ�Ҫ����Ʊ�ᣬ
				 * �������Ʊ�ᣬ����Ҫ�ж����ڷ�Ʊ���з�Ʊ�Ƿ񶼱���
				 */			
					String hasDiscardFapiaoSN=new String();
					Iterator iter=serialsList.iterator();
					while(iter.hasNext()){
						hasDiscardFapiaoSN+="'"+(String)iter.next()+"',";
					}
					hasDiscardFapiaoSN=hasDiscardFapiaoSN.substring(0,hasDiscardFapiaoSN.length()-1);				
					//�ж����ڷ�Ʊ���з�Ʊ�Ƿ񶼱���
					if(!BusinessUtility.hasUnDiscardFapiao(fapiaoType, volumn,hasDiscardFapiaoSN)){
						//System.out.println("_____________A");
						FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
						//System.out.println("_____________B");
						FapiaoVolumnDTO volumnDto =new FapiaoVolumnDTO();
						volumnDto.setStatus(CommonConstDefinition.FAPIAO_STATUS_DIS);
						volumnDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
						volumnDto.setDtLastmod(fapiaoVolumn.getDtLastmod());
						if(fapiaoVolumn.ejbUpdate(volumnDto)==-1){
							throw new ServiceException("Step2.����Ʊ�����ݳ���");
						}	
					}
					//System.out.println("_____________C");
				}	
			}
			//System.out.println("_____________D");
		}
		/*
		 * ��Ʊ����������
		 */			
		else if(event.getDiscardStyle().equals("1")&&event.isVolumnManage()){
			volumn= volumnDTO.getVolumnSn();
			// ��鷢Ʊ���Ƿ��Ѿ�����
			if(isFapiaoVolumnDiscard(fapiaoType,volumn)){
				throw new ServiceException("�÷�Ʊ���Ѿ�����");
			}
			//ȡ���ò�������δ���Ϸ�Ʊ			
			fapiaoList=BusinessUtility.getUnDisFapiaoDTOByVolumnSN(fapiaoType, volumn);	
			this.context.put(Service.PROCESS_RESULT, fapiaoList);			
			// �ύ
			if(event.isDoPost()){
				List serialsList=new ArrayList();
				Iterator iter=fapiaoList.iterator();
				while(iter.hasNext()){
					String serial=((FaPiaoDTO)iter.next()).getSerailNo();
					serialsList.add(serial);
				}
				//��Ʊ�ᱨ��---begin
				FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
				FapiaoVolumnDTO volumnDto =new FapiaoVolumnDTO();
				volumnDto.setStatus(CommonConstDefinition.FAPIAO_STATUS_DIS);
				volumnDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
				volumnDto.setDtLastmod(fapiaoVolumn.getDtLastmod());
				if(fapiaoVolumn.ejbUpdate(volumnDto)==-1){
					throw new ServiceException("Step2.����Ʊ�����ݳ���");
				}
				//��Ʊ�ᱨ��---end
				//����Ʊ�д����Ϸ�Ʊ
				discardFapiao(event, volumn, fapiaoType, serialsList);
			}	
		}
		//System.out.println("_____________E");
	}

	private void discardFapiao(FaPiaoEJBEvent event, String volumn,
			String fapiaoType, List serialsList) throws HomeFactoryException,
			FinderException, ServiceException, CreateException {
		// ��ʼ����ÿһ�Ŵ����Ϸ�Ʊ��������ݱ��
		Iterator i = serialsList.iterator();
		int operID=event.getOperatorID();
		int orgID=BusinessUtility.FindOrgIDByOperatorID(operID);	
		int volumnSeqNo=0;//0Ϊû�й���Ʊ��ʱ����Ʊ���ͳһĬ��ֵ��				
		if(volumn!=null&&!"".equals(volumn)){
				FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
				volumnSeqNo=fapiaoVolumn.getSeqNo().intValue();
		}
		/*
		 * Step3.����ƱTransition
		 */
		FapiaoTransitionDTO fapiaoTransitionDto=new FapiaoTransitionDTO();
		fapiaoTransitionDto.setVolumnSeqNo(volumnSeqNo);
		fapiaoTransitionDto.setTotal(serialsList.size());
		fapiaoTransitionDto.setOpID(operID);
		fapiaoTransitionDto.setOrgID(orgID);
		fapiaoTransitionDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_DISCA);
		fapiaoTransitionDto.setStatus("V");
		fapiaoTransitionDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
		fapiaoTransitionDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		fapiaoTransitionDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());					
		FapiaoTransitionHome faPiaoTransHome = HomeLocater.getFapiaoTransitionHome();
		FapiaoTransition faPiaoTrans = faPiaoTransHome.create(fapiaoTransitionDto);
		fapiaoTransitionDto.setSeqNo(faPiaoTrans.getSeqNo().intValue());
		//System.out.println("---------------------------3------------------------");
		
		while (i.hasNext()) {

			String serial = (String) i.next();
			/*
			 * Step1.����Ʊ����
			 */	
			List list=BusinessUtility.getFaPiaoDtoByCon(fapiaoType,volumn,serial);
			FaPiaoDTO fapiaoDto=(FaPiaoDTO)list.get(0);
			int fapiaoSeqNo=fapiaoDto.getSeqNo();
			String fromStatus=fapiaoDto.getStatus();
			String addressType=fapiaoDto.getAddressType();
			int addressID=fapiaoDto.getAddressID();
			fapiaoDto.setStatus(CommonConstDefinition.FAPIAO_STATUS_DIS);
			fapiaoDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
			fapiaoDto.setDtLastmod(fapiaoDto.getDtLastmod());
			fapiaoDto.setDiscardReason(event.getFaPiaoDTO().getDiscardReason());
			FaPiaoHome faPiaoHome = HomeLocater.getFaPiaoHome();
			FaPiao fapiao=faPiaoHome.findByPrimaryKey(new Integer(fapiaoSeqNo));
			if(fapiao.ejbUpdate(fapiaoDto)==-1){
				throw new ServiceException("���·�Ʊ���ݳ���");
			}
			//System.out.println("---------------------------1------------------------");
			try {

				/*
				 * Step4.����ƱTransitionDetail
				 */
				FapiaoTransitionDetailDTO faPiaoTransDetailDto=new FapiaoTransitionDetailDTO();
				faPiaoTransDetailDto.setFapiaoSeqNo(fapiaoSeqNo);
				faPiaoTransDetailDto.setVolumnSeqNo(String.valueOf(volumnSeqNo));
				faPiaoTransDetailDto.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
				faPiaoTransDetailDto.setOpID(operID);
				faPiaoTransDetailDto.setOrgID(orgID);
				faPiaoTransDetailDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_DISCA);
				faPiaoTransDetailDto.setFromStatus(fromStatus);
				faPiaoTransDetailDto.setToStatus(CommonConstDefinition.FAPIAO_STATUS_DIS);
				faPiaoTransDetailDto.setFromAddressID(addressID);
				faPiaoTransDetailDto.setFromAddressType(addressType);					
				faPiaoTransDetailDto.setToAddressID(addressID);
				faPiaoTransDetailDto.setToAddressType(addressType);
				faPiaoTransDetailDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
				faPiaoTransDetailDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				FapiaoTransitionDetailHome faPiaoTransDetailHome = HomeLocater.getFapiaoTransitionDetailHome();
				FapiaoTransitionDetail faPiaoTransDetail = faPiaoTransDetailHome.create(faPiaoTransDetailDto);
				faPiaoTransDetailDto.setSeqNo(faPiaoTransDetail.getSeqNo().intValue());			
				//System.out.println("---------------------------4------------------------");






				 //Step5.����ƱFAPIAO2PAYMENT
				Fapiao2PaymentHome paymentHome= HomeLocater.getFapiao2PaymentHome();
				
				
				
				//ֱ��ɾ���ķ��� chaqiu 20080323 begin
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "__fapiaoSeqNo____="+fapiaoSeqNo);
				Collection paymentList = paymentHome.findByFapiaoSeqNo(fapiaoSeqNo);
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "__paymentList____="+paymentList);
				Iterator iter1=paymentList.iterator();
				while(iter1.hasNext()){
					Fapiao2Payment payment=(Fapiao2Payment)iter1.next();
					LogUtility.log(this.getClass(), LogLevel.DEBUG, "__payment____="+payment);
				payment.remove();
				}
                 //ֱ��ɾ���ķ��� chaqiu 20080323 end
				
/*
				List paymentList=BusinessUtility.getFapiao2PaymentByFapiaoSN(fapiaoSeqNo);
				Iterator iter1=paymentList.iterator();
				while(iter1.hasNext()){
					Fapiao2PaymentDTO paymentDTO=(Fapiao2PaymentDTO)iter1.next();
					int paymentSN=paymentDTO.getSeqNo();
					
					System.out.println("_______________paymentSN__________["+paymentSN+"]");					
					Fapiao2Payment payment=paymentHome.findByPrimaryKey(new Integer(paymentSN));		

					payment.remove();
				}
				System.out.println("---------------------------5------------------------");					

*/
				
				

				 //Step6.ɾ��FAPIAODETAIL				
					FapiaoDetailHome detailHome= HomeLocater.getFapiaoDetailHome();
					
					List detailList=BusinessUtility.getFapiaoDetailByFapiaoSeqNo(fapiaoSeqNo);
					Iterator iter2=detailList.iterator();
					while(iter2.hasNext()){
						FapiaoDetailDTO detailDTO=(FapiaoDetailDTO)iter2.next();
						int detailSeqNo=detailDTO.getSeqNo();
						FapiaoDetail detail=detailHome.findByPrimaryKey(new Integer(detailSeqNo));		
						detail.remove();
					}
					//System.out.println("---------------------------6------------------------");	
			} catch (CreateException e) {
				LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e);
				throw new ServiceException("������¼����");
			} catch (EJBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoveException e) {
				LogUtility.log(clazz, LogLevel.WARN, "ɾ����¼����" + e);
				throw new ServiceException("ɾ����¼����");
			}	
		}
	}
		
	
private boolean isFapiaoVolumnDiscard(String type,String volumn) throws ServiceException{
	
	FapiaoVolumn fapiaoVolumn;
	try {
		fapiaoVolumn = BusinessUtility.getFapiaoVolumn(type, volumn);

		if(fapiaoVolumn==null){
			throw new ServiceException("�÷�Ʊ�᲻����");
		}
		else{
			String status=fapiaoVolumn.getStatus();
			if(status.equals("DIS"))
				return true;
			else 
				return false;
		}
	} catch (HomeFactoryException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FinderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	

	return false;
}
	
	// �������Ϸ�Ʊ�Ƿ����㡰��Ϊ���ϲ��Ҳ�Ϊ���ϡ���״̬
	private void checkFapiaoStatusForAbandon(List serialsList, String fapiaoType, String volumn) throws ServiceException {
		StringBuffer errorMessage=new StringBuffer("");
		boolean errorFlag=false;
		Iterator iter=serialsList.iterator();
		while(iter.hasNext()){
			String serialNo=(String)iter.next();
			String status;
				
				List list=BusinessUtility.getFaPiaoDtoByCon(fapiaoType,volumn,serialNo);
				if(list.size()==0){
					errorFlag=true;
					errorMessage.append(" ��Ʊ ");					
					errorMessage.append(serialNo);
					errorMessage.append(" ������\n");

				}else{
				
					status = ((FaPiaoDTO)list.get(0)).getStatus();
	
					if(status.equals(CommonConstDefinition.FAPIAO_STATUS_CAN)
							||status.equals(CommonConstDefinition.FAPIAO_STATUS_DIS)) {
						errorFlag=true;
						errorMessage.append(serialNo);
						errorMessage.append(" �����ϻ򱨷�\n");
					}
				}
		}
		if(errorFlag) throw new ServiceException(errorMessage.toString());
	}
	
	// �������Ϸ�Ʊ�Ƿ����㡰��Ϊ���ϲ��Ҳ�Ϊ���ϡ���״̬
	private void checkFapiaoStatusForDiscard(List serialsList, String fapiaoType, String volumn) throws ServiceException {
		StringBuffer errorMessage=new StringBuffer("");
		boolean errorFlag=false;
		Iterator iter=serialsList.iterator();
		while(iter.hasNext()){
			String serialNo=(String)iter.next();
			String status;
				
				List list=BusinessUtility.getFaPiaoDtoByCon(fapiaoType,volumn,serialNo);
				if(list.size()==0){
					errorFlag=true;
					errorMessage.append(" ��Ʊ ");					
					errorMessage.append(serialNo);
					errorMessage.append(" ������\n");

				}else{
				
					status = ((FaPiaoDTO)list.get(0)).getStatus();
	
					if(status.equals(CommonConstDefinition.FAPIAO_STATUS_DIS)) {
						errorFlag=true;
						errorMessage.append(serialNo);
						errorMessage.append(" �ѱ��� \n");
					}
				}
		}
		if(errorFlag) throw new ServiceException(errorMessage.toString());
	}
	/**
	 * ��Ʊ����:��¼����
	 * 
	 * @param serialsList
	 * @param opID
	 */
	private void faPiaoTransitionForIn(FaPiaoEJBEvent theEvent) throws ServiceException {
		
		//��Ʊ�Ƿ񰴲���� "Y"--��
		int orgID = BusinessUtility.FindOrgIDByOperatorID(theEvent.getOperatorID());
		//ArrayList deviceList = new ArrayList();
		try {
			
			//��ַ����|��ַID|״̬|������|��������֯
			String addressType = CommonConstDefinition.FAPIAO_ADDRESSTYPE_D;
			int addressid = theEvent.getFaPiaoDTO().getAddressID();
			String status = CommonConstDefinition.FAPIAO_STATUS_SAV;
			int recipientOpID = 0;
			int recipientOrgID = 0;
			String action = CommonConstDefinition.FAPIAOTRANSITION_ACTION_IN;
			
			//��������
			if(theEvent.isDirUse())
			{
				addressType = CommonConstDefinition.FAPIAO_ADDRESSTYPE_O;
				addressid=theEvent.getOutOrgID();
				status=CommonConstDefinition.FAPIAO_STATUS_REC;
				recipientOpID=theEvent.getOperatorID();
				recipientOrgID = orgID;
				action = CommonConstDefinition.FAPIAOTRANSITION_ACTION_INOUT;
				
			}
			
			FapiaoVolumnDTO faVoDto=new FapiaoVolumnDTO();
			FapiaoVolumnHome faVoHome;
			FapiaoVolumn faVo;
			if(theEvent.isVolumnManage())
			{
			/*
			 * ������Ʊ���¼
			 */
			
			
			//����ʱ��
			faVoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			faVoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			//��Ʊ�����к�
			faVoDto.setVolumnSn(theEvent.getFapiaoVolumnDTO().getVolumnSn());
			//����
			faVoDto.setType(theEvent.getFaPiaoDTO().getType());
			
			//����Ա
			faVoDto.setCreatorOpID(theEvent.getOperatorID());
			//����Ա������֯����
			faVoDto.setCreatorOrgID(orgID);
			//״̬ʱ��
			faVoDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
			
			//��ַ����
			faVoDto.setAddressType(addressType);
			//��ַid
			faVoDto.setAddressID(new Integer(addressid).intValue());
			//״̬
			faVoDto.setStatus(status);
			//������
			faVoDto.setRecepientOpID(recipientOpID);
			//��������֯
			faVoDto.setRecipientOrgID(recipientOrgID);
			
			faVoHome = HomeLocater.getFapiaoVolumnHome();
			faVo = faVoHome.create(faVoDto);
			faVoDto.setSeqNo(faVo.getSeqNo().intValue());
			}
			/*
			 * ������תͷ��¼
			 * 
			 * T_FAPIAOTRANSITION
					SEQNO = FAPIAOTRANSITION_SEQNO.nextval;
					ValumnSeqNO=��Ʊ���ϵͳ���кš�
					Total = �ܼ�¼��
					OpID = ����ԱID
					OrgID = ����Ա������֯����
					Action = ����:IN;��������: INOUT
					FileName =������뷽ʽ���ļ�����¼�ļ�����
					Status=��V��;
					CreateTime=SysDate;

			 */
			FapiaoTransitionDTO fapiaoTransitionDto=new FapiaoTransitionDTO();

			fapiaoTransitionDto.setVolumnSeqNo(faVoDto.getSeqNo());
			fapiaoTransitionDto.setTotal(theEvent.getSerailNoList().size());
			fapiaoTransitionDto.setOpID(theEvent.getOperatorID());
			fapiaoTransitionDto.setOrgID(orgID);
			fapiaoTransitionDto.setAction(action);
			if("1".equals(theEvent.getMakeStyle()))
				fapiaoTransitionDto.setFileName(theEvent.getFileName());
			fapiaoTransitionDto.setStatus("V");
			fapiaoTransitionDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			
			FapiaoTransitionHome faPiaoTransHome = HomeLocater.getFapiaoTransitionHome();
			FapiaoTransition faPiaoTrans = faPiaoTransHome.create(fapiaoTransitionDto);
			fapiaoTransitionDto.setSeqNo(faPiaoTrans.getSeqNo().intValue());
			

			/*
			 * ������Ʊ��¼
			 * ������ת��ϸ��¼:������������������,һ�ŷ�Ʊ��Ҫ��¼������ϸ
			 */
			Iterator ite = theEvent.getSerailNoList().iterator();
			while (ite.hasNext()) {
				String serial = (String) ite.next();
				FaPiaoDTO faPiaoDto=new FaPiaoDTO();
				
				//����
				faPiaoDto.setType(theEvent.getFaPiaoDTO().getType());
				//��Ʊ�ֹ����
				faPiaoDto.setSerailNo(serial);
				//��Ʊ��ϵͳ���к�
				faPiaoDto.setVolumnSeqno(faVoDto.getSeqNo());
				//����ԱID
				faPiaoDto.setCreatorOpID(theEvent.getOperatorID());
				//����Ա������֯����
				faPiaoDto.setCreatorOrgID(orgID);
				//״̬ʱ��
				faPiaoDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
				//����ʱ��
				faPiaoDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
				//��ַ����|��ַID|״̬|������|��������֯
				//��ַ����
				faPiaoDto.setAddressType(addressType);
				//��ַid
				faPiaoDto.setAddressID(new Integer(addressid).intValue());
				//״̬
				faPiaoDto.setStatus(status);
				//������
				faPiaoDto.setRecipientOpID(recipientOpID);
				//��������֯
				faPiaoDto.setRecipientOrgID(recipientOrgID);
				
				faPiaoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
				faPiaoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				//modulename �ǿգ�
				faPiaoDto.setModuleName(" ");
				
				if(theEvent.isDirUse())
					faPiaoDto.setRecipientTime(TimestampUtility.getCurrentTimestamp());
				
				FaPiaoHome faPiaoHome = HomeLocater.getFaPiaoHome();
				FaPiao faPiao = faPiaoHome.create(faPiaoDto);
				faPiaoDto.setSeqNo(faPiao.getSeqNo().intValue());
				
				
				/* 
				 * ��¼��Ʊ��ϸ��Ϣ
				 * SEQNO = FAPIAOTRANSITIONDETAIL_SEQNO.nextval;
					VolumnSeqNO = ��Ʊ�����к�
					FapiaoTransSeqNO = ͷ��¼���к�
					FapiaoSeqNo = ��Ʊ���к�
					OpID = ����ԱID
					OrgID = ��֯����ID
					Action = ����:IN;��������: INOUT
					ToStatus = ����:��SAV��,��������:��REP��;--��REC�ɣ���
					ToAddressType= �������Ϊ������ AddressType=��D��,�������Ϊ:�������������AddressType = ��O��;
					ToAddressID = �������Ϊ������ AddressID��д�ֿ�ID���������Ϊ:�������������AddressID��д ��֯����ID

				 */
				
				//������������� ��Ҫ��¼������ϸ action����"inout"
				if(theEvent.isDirUse())
				{
					//�ȼ�¼����
					FapiaoTransitionDetailDTO faPiaoTransDetailDto=new FapiaoTransitionDetailDTO();
					faPiaoTransDetailDto.setVolumnSeqNo(faVoDto.getSeqNo()+"");
					faPiaoTransDetailDto.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
					faPiaoTransDetailDto.setFapiaoSeqNo(faPiao.getSeqNo().intValue());
					faPiaoTransDetailDto.setOpID(theEvent.getOperatorID());
					faPiaoTransDetailDto.setOrgID(orgID);
					faPiaoTransDetailDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_INOUT);//--������Ͷ���(����ȷ�ϵ�)?���˾�����"in"
					faPiaoTransDetailDto.setToStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
					faPiaoTransDetailDto.setToAddressID(theEvent.getFaPiaoDTO().getAddressID());
					faPiaoTransDetailDto.setToAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_D);
					faPiaoTransDetailDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
					faPiaoTransDetailDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					//�������ǿգ���
					faPiaoTransDetailDto.setFromStatus(" ");
					faPiaoTransDetailDto.setFromAddressType(" ");
					
					FapiaoTransitionDetailHome faPiaoTransDetailHome = HomeLocater.getFapiaoTransitionDetailHome();
					FapiaoTransitionDetail faPiaoTransDetail = faPiaoTransDetailHome.create(faPiaoTransDetailDto);
					faPiaoTransDetailDto.setSeqNo(faPiaoTransDetail.getSeqNo().intValue());
					//�ټ�¼����,����������Ĳֿ�������
					
					FapiaoTransitionDetailDTO faPiaoTransDetailDto1=new FapiaoTransitionDetailDTO();
					faPiaoTransDetailDto1.setVolumnSeqNo(faVoDto.getSeqNo()+"");
					faPiaoTransDetailDto1.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
					faPiaoTransDetailDto1.setFapiaoSeqNo(faPiao.getSeqNo().intValue());
					faPiaoTransDetailDto1.setOpID(theEvent.getOperatorID());
					faPiaoTransDetailDto1.setOrgID(orgID);
					faPiaoTransDetailDto1.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_INOUT);//--������Ͷ���(����ȷ�ϵ�)?���˾�����"in"
					faPiaoTransDetailDto1.setFromStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
					faPiaoTransDetailDto1.setFromAddressID(theEvent.getFaPiaoDTO().getAddressID());
					faPiaoTransDetailDto1.setFromAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_D);
					
					faPiaoTransDetailDto1.setToStatus(CommonConstDefinition.FAPIAO_STATUS_REC);
					faPiaoTransDetailDto1.setToAddressID(theEvent.getOutOrgID());
					faPiaoTransDetailDto1.setToAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_O);
					faPiaoTransDetailDto1.setDtCreate(TimestampUtility.getCurrentTimestamp());
					faPiaoTransDetailDto1.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				
					
					FapiaoTransitionDetailHome faPiaoTransDetailHome1 = HomeLocater.getFapiaoTransitionDetailHome();
					FapiaoTransitionDetail faPiaoTransDetail1 = faPiaoTransDetailHome1.create(faPiaoTransDetailDto1);
					faPiaoTransDetailDto1.setSeqNo(faPiaoTransDetail1.getSeqNo().intValue());
				}
				//ֻ����,ֻ��¼һ����ϸ��¼
				else
				{
					
					FapiaoTransitionDetailDTO faPiaoTransDetailDto=new FapiaoTransitionDetailDTO();
					faPiaoTransDetailDto.setVolumnSeqNo(faVoDto.getSeqNo()+"");
					faPiaoTransDetailDto.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
					faPiaoTransDetailDto.setFapiaoSeqNo(faPiao.getSeqNo().intValue());
					faPiaoTransDetailDto.setOpID(theEvent.getOperatorID());
					faPiaoTransDetailDto.setOrgID(orgID);
					faPiaoTransDetailDto.setAction(action);
					faPiaoTransDetailDto.setToStatus(status);
					faPiaoTransDetailDto.setToAddressID(addressid);
					faPiaoTransDetailDto.setToAddressType(addressType);
					faPiaoTransDetailDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
					faPiaoTransDetailDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					//�������ǿգ���
					faPiaoTransDetailDto.setFromStatus(" ");
					faPiaoTransDetailDto.setFromAddressType(" ");
					
					FapiaoTransitionDetailHome faPiaoTransDetailHome = HomeLocater.getFapiaoTransitionDetailHome();
					FapiaoTransitionDetail faPiaoTransDetail = faPiaoTransDetailHome.create(faPiaoTransDetailDto);
					faPiaoTransDetailDto.setSeqNo(faPiaoTransDetail.getSeqNo().intValue());
					
				}
				
				
				
			}
			context.put(com.dtv.oss.service.Service.PROCESS_RESULT, "");

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��Ʊ���кŶ�λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} 
	}	
	/*
	 * ��鷢Ʊ����ʱ��Ʊ�ἰ��Ʊ�Ƿ���ϵͳ�����з�Ʊ��ͻ
	 */
	private void checkValideFaPiaoIn( FaPiaoEJBEvent theEvent) throws HomeFactoryException, FinderException,
			ServiceException {
		//������ļ����룬��Ҫ����ļ��з�Ʊ���кű����Ƿ��ظ�
		String type = theEvent.getFaPiaoDTO().getType();
		String volumnSn = theEvent.getFapiaoVolumnDTO().getVolumnSn();
		List serialsList = theEvent.getSerailNoList();
		StringBuffer errBuff3 = new StringBuffer();
		if("1".equals(theEvent.getMakeStyle()))
		{
			Set tempSet = new HashSet();
			for(int i=0;i<serialsList.size();i++)
				tempSet.add(serialsList.get(i));
			Iterator temIter = tempSet.iterator();
			while(temIter.hasNext())
			{
				String eachSer = (String)temIter.next();
				if(serialsList.indexOf(eachSer)!=serialsList.lastIndexOf(eachSer))
				{
					errBuff3.append(eachSer+",");
				}
			}
			String errStr3 = errBuff3.toString();
			if(errStr3 != null && !"".equals(errStr3))
			{
				throw new ServiceException("�������ļ��з�Ʊ���к�["+errStr3.substring(0,errStr3.length()-1)+"]�����ظ�,����!");
			} 
		}
		
		//�������Ʊ�� ��Ҫ��鷢Ʊ���Ƿ���ϵͳ���Ѵ���,����Ʊ���Ҫ����һ�ᷢƱֻ��һ��¼��,���ֶܷ��
		
		//��Ʊ���ظ���Ϣ
		if(theEvent.isVolumnManage())
		{
			FapiaoVolumnHome faVoHome = HomeLocater.getFapiaoVolumnHome();
			Collection faVoCol = faVoHome.findByTypeAndVolumnSN(type,volumnSn);
			if(faVoCol != null && !faVoCol.isEmpty()) {
				throw new ServiceException("��Ʊ����Ϊ:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",theEvent.getFaPiaoDTO().getType())+",��Ʊ�����к�Ϊ:"+volumnSn+"�ķ�Ʊ���Ѵ���!");
			}
		}
		//��Ʊ�ظ���Ϣ
		StringBuffer errBuff2 = new StringBuffer();
		for (int i=0;i<serialsList.size();i++) {
			String serialNo = (String) serialsList.get(i);
			List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, volumnSn, serialNo);
			if(faPiaoList != null && !faPiaoList.isEmpty())
				errBuff2.append(serialNo+",");
		}
		String errStr2 = errBuff2.toString();
		
		if(errStr2 != null && !"".equals(errStr2))
		{
			if(theEvent.isVolumnManage())
				throw new ServiceException("��Ʊ����Ϊ:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSn+",��Ʊ���к�Ϊ["+errStr2.substring(0,errStr2.length()-1)+"]�ķ�Ʊ�Ѵ���!");
			else
				throw new ServiceException("��Ʊ����Ϊ:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ���к�Ϊ["+errStr2.substring(0,errStr2.length()-1)+"]�ķ�Ʊ�Ѵ���!");
		}
		
	}
	
	
	/**
	 * ��Ʊ�ؿ�:��¼����
	 * 
	 * @param serialsList
	 * @param opID
	 */
	private void faPiaoTransitionForBack(FaPiaoEJBEvent event) throws ServiceException {
		
		//"Y"--�������,��Ҫ�޸ķ�Ʊ����Ϣ,������Ҫ
		int orgID = BusinessUtility.FindOrgIDByOperatorID(event.getOperatorID());
		String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
		String type = event.getFaPiaoDTO().getType();

		int depotID = event.getFaPiaoDTO().getAddressID();
		String fileName = event.getFileName();
		try {

			/*
			 * ������תͷ��¼
			 * 
			 * T_FAPIAOTRANSITION
				SEQNO = FAPIAOTRANSITION_SEQNO.nextval;
				FileName =�ļ���
				Total = �ܼ�¼��
				OpID = ����ԱID
				OrgID = ����Ա������֯����
				Action =  ��BACK
				Status=��V��;
				CreateTime=SysDate;
			 */
			Integer volumnSeqNo = BusinessUtility.getFapiaoVolumn(type, volumnSN).getSeqNo();
			FapiaoTransitionDTO fapiaoTransitionDto=new FapiaoTransitionDTO();
			
			if("1".equals(event.getMakeStyle()))
				fapiaoTransitionDto.setFileName(fileName);
			fapiaoTransitionDto.setTotal(event.getSerailNoList().size());
			fapiaoTransitionDto.setOpID(event.getOperatorID());
			fapiaoTransitionDto.setOrgID(orgID);
			fapiaoTransitionDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_BACK);
			fapiaoTransitionDto.setStatus("V");
			fapiaoTransitionDto.setVolumnSeqNo(volumnSeqNo.intValue());
			fapiaoTransitionDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			
			FapiaoTransitionHome faPiaoTransHome = HomeLocater.getFapiaoTransitionHome();
			FapiaoTransition faPiaoTrans = faPiaoTransHome.create(fapiaoTransitionDto);
			fapiaoTransitionDto.setSeqNo(faPiaoTrans.getSeqNo().intValue());
			
			//���޸ķ�Ʊ����
			String faPiaoSeqNoStr = "";
			//�ؿ��漰���ķ�Ʊ��
			Set faPiaoVo = new HashSet();
			/*
			 * ������ת��ϸ��¼
			 * �޸ķ�Ʊ��¼
			 * 
			 */
			Iterator ite = event.getSerailNoList().iterator();
			while (ite.hasNext()) {
				/*
				 * T_FAPIAOTRANSITIONDETAIL
				SEQNO = FAPIAOTRANSITIONDETAIL_SEQNO.nextval;
				VolumnSeqNO = ��Ʊ�����к�
				FapiaoTransSeqNO = ͷ��¼���к�
				FapiaoSeqNo = ��Ʊ���к�
				OpID = ����ԱID
				OrgID = ��֯����ID
				Action =  ��BACK
				FromStatus = ��REC��
				ToStatus =��SAV;
				FromAddressType= ԭ��Ʊ��ַ����;
				FromAddressID = ԭ��Ʊ��ַID��
				ToAddressType =  ��D��;
				ToAddressID =�ֿ�ID
				 */
				String serial = (String) ite.next();
				//�õ���Ʊ
				List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, volumnSN, serial);
				if(faPiaoList == null)
					throw new ServiceException("��Ʊ��λ����");
				FaPiaoDTO theDto = (FaPiaoDTO)faPiaoList.get(0);
				if(faPiaoSeqNoStr != null)faPiaoSeqNoStr=faPiaoSeqNoStr+",";
				faPiaoSeqNoStr = faPiaoSeqNoStr+theDto.getSeqNo();
				
				faPiaoVo.add(theDto.getVolumnSeqno()+"");
				
				FapiaoTransitionDetailDTO faPiaoTransDetailDto=new FapiaoTransitionDetailDTO();

				faPiaoTransDetailDto.setVolumnSeqNo(theDto.getVolumnSeqno()+"");
				faPiaoTransDetailDto.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
				faPiaoTransDetailDto.setFapiaoSeqNo(theDto.getSeqNo());
				faPiaoTransDetailDto.setOpID(event.getOperatorID());
				faPiaoTransDetailDto.setOrgID(orgID);
				faPiaoTransDetailDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_BACK);
				faPiaoTransDetailDto.setFromStatus(CommonConstDefinition.FAPIAO_STATUS_REC);
				faPiaoTransDetailDto.setToStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
				faPiaoTransDetailDto.setFromAddressType(theDto.getAddressType());
				faPiaoTransDetailDto.setFromAddressID(theDto.getAddressID());
				faPiaoTransDetailDto.setToAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_D);
				faPiaoTransDetailDto.setToAddressID(new Integer(depotID).intValue());
				
				faPiaoTransDetailDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
				faPiaoTransDetailDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				
				FapiaoTransitionDetailHome faPiaoTransDetailHome = HomeLocater.getFapiaoTransitionDetailHome();
				FapiaoTransitionDetail faPiaoTransDetail = faPiaoTransDetailHome.create(faPiaoTransDetailDto);
				faPiaoTransDetailDto.setSeqNo(faPiaoTransDetail.getSeqNo().intValue());
				
				/*
				 * �޸ķ�Ʊ��¼
				 * T_FAPIAO
					Status = ��SAV��
					StatusTime = SysDate;
					AddressType = ��D��
					AddressID =�ֿ�ID
					DT_LASTMOD = SysTimeStamp;

				 */
				FaPiaoHome faPiaoHome = HomeLocater.getFaPiaoHome();
				FaPiao theFaPiao = faPiaoHome.findByPrimaryKey(new Integer(theDto.getSeqNo()));
				theFaPiao.setStatus(faPiaoTransDetailDto.getToStatus());
				theFaPiao.setStatusTime(TimestampUtility.getCurrentTimestamp());
				theFaPiao.setAddressType(faPiaoTransDetailDto.getToAddressType());
				theFaPiao.setAddressID(faPiaoTransDetailDto.getToAddressID());
				theFaPiao.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				
//				�ؿ���Ҫ����������ֶ�
				theFaPiao.setRecipientOpID(0);
				theFaPiao.setRecipientOrgID(0);
				theFaPiao.setRecipientTime(null);
			}
			if(event.isVolumnManage())
			{
			    /*
				 * �޸ķ�Ʊ���¼
				 * �����Ʊ������Ʊ��ȫ�����ؿ⵽ָ���⣬��Ʊ���ַ���͸���Ϊ��D��,��ַID����Ϊ�ֿ�ID
				 * �����Ʊ������ͬһ����Ʊ�ᣬ�ǿ��ܸ��Ķ�����¼.
				*/
				//�ж��ڻؿⷶΧ�ڵķ�Ʊ��Ӧ�ķ�Ʊ������з�Ʊ�Ƿ񶼻ؿ�,���򷵻�true
				Iterator ite1 =  faPiaoVo.iterator();
				while(ite1.hasNext())
				{
					String faPiaoVoSeqNo = ite1.next()+"";
					if(BusinessUtility.isBack(faPiaoVoSeqNo,faPiaoSeqNoStr,depotID));
					{
						FapiaoVolumnHome faPiaoVoHome = HomeLocater.getFapiaoVolumnHome();
						FapiaoVolumn theFaPiaoVo = faPiaoVoHome.findByPrimaryKey(new Integer(faPiaoVoSeqNo));
						theFaPiaoVo.setStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
						theFaPiaoVo.setStatusTime(TimestampUtility.getCurrentTimestamp());
						theFaPiaoVo.setAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_D);
						theFaPiaoVo.setAddressID(new Integer(depotID).intValue());
						theFaPiaoVo.setDtLastmod(TimestampUtility.getCurrentTimestamp());
						
						theFaPiaoVo.setRecepientOpID(0);
						theFaPiaoVo.setRecipientOrgID(0);
					}
					
				}
			}
			
			context.put(com.dtv.oss.service.Service.PROCESS_RESULT, "");

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��Ʊ���кŶ�λ����!");
		}
		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��Ʊ���Ҵ���!");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} 
	}	
	/*
	 * ��Ʊ�ؿ���
	 * 
	 * ��鷢Ʊ�ؿ�ʱ�����ؿ�ķ�Ʊ�Ƿ�����ؿ�����
	 */
	private void checkValideFaPiaoBack(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException,
			ServiceException {
		
		//����
		String type = event.getFaPiaoDTO().getType();
		ArrayList serailNoList = new ArrayList();
		//���÷�Ʊ�����,��Ҫ�ӷ�Ʊ���еõ���Ʊ���к�,��List�ķ�ʽ�洢
		if(event.isVolumnManage())
		{
			String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
			FapiaoVolumn faVo = BusinessUtility.getFapiaoVolumn(type, volumnSN);
			if(faVo == null) throw new ServiceException("��Ʊ����Ϊ"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ�᲻����!");
			//��Ʊ��״̬���:��Ʊ��״̬ӦΪ����ȡ  �б�Ҫ??--��������Ҫ���
			if(!CommonConstDefinition.FAPIAO_STATUS_REC.equals(faVo.getStatus()))
				throw new ServiceException("��Ʊ����Ϊ"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ��״̬��������ȡ,���ܻؿ�!");
			
			
			
			List faDtoList = BusinessUtility.getFaPiaoDtoByCon(type,volumnSN,null);
			if(faDtoList==null || faDtoList.isEmpty())
				throw new ServiceException("��Ʊ����Ϊ"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ����û����Ҫ�ؿ�ķ�Ʊ!");
			for(int i=0;i<faDtoList.size();i++)
			{
				FaPiaoDTO faDto = (FaPiaoDTO)faDtoList.get(i);
				serailNoList.add(faDto.getSerailNo());
			}
			event.setSerailNoList(serailNoList);
		}
		
		List serialsList = event.getSerailNoList();
		
		//������ļ����룬��Ҫ����ļ��з�Ʊ���кű����Ƿ��ظ�
		StringBuffer errBuff3 = new StringBuffer();
		if("1".equals(event.getMakeStyle()))
		{
			Set tempSet = new HashSet();
			for(int i=0;i<serialsList.size();i++)
				tempSet.add(serialsList.get(i));
			Iterator temIter = tempSet.iterator();
			while(temIter.hasNext())
			{
				String eachSer = (String)temIter.next();
				if(serialsList.indexOf(eachSer)!=serialsList.lastIndexOf(eachSer))
				{
					errBuff3.append(eachSer+",");
				}
			}
			String errStr3 = errBuff3.toString();
			if(errStr3 != null && !"".equals(errStr3))
			{
				throw new ServiceException("�������ļ��з�Ʊ���к�["+errStr3.substring(0,errStr3.length()-1)+"]�����ظ�,����!");
			} 
		}
		
		
		/*
		 * ���ؿ�ķ�Ʊ���������Ϊ����ȡ
		 * 
		 */
		
		//��������Դ���
		StringBuffer errBuff1 = new StringBuffer();
		//����״̬����
		StringBuffer errBuff2 = new StringBuffer();
		for (int i=0;i<serialsList.size();i++) {
			String serialNo = (String) serialsList.get(i);
			//��������,��Ʊ�����к�,��Ʊ���кŲ�ѯ��Ʊ
			List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, event.getFapiaoVolumnDTO().getVolumnSn(), serialNo);
			if(faPiaoList == null || faPiaoList.isEmpty())
			{
				errBuff1.append(serialNo+",");
				continue;
			}
			else
			{
				//������ֻ������һ�����
				FaPiaoDTO theFaPiao = (FaPiaoDTO) faPiaoList.get(0);
				if(!CommonConstDefinition.FAPIAO_STATUS_REC.equals(theFaPiao.getStatus()))
				{
					errBuff2.append(serialNo+",");
				}
				
			}
		}
		String errStr1 = errBuff1.toString();
		
		String tempStr="��Ʊ����Ϊ:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type);
		if(event.isVolumnManage())
			tempStr = tempStr+",��Ʊ�����к�Ϊ:"+event.getFapiaoVolumnDTO().getVolumnSn();
		if(errStr1 != null && !"".equals(errStr1))
		{
			
			throw new ServiceException(tempStr+",��Ʊ���к�Ϊ["+errStr1.substring(0,errStr1.length()-1)+"]�ķ�Ʊ������,�޷��ؿ�!");
		}
		
	    String errStr2 = errBuff2.toString();
		if(errStr2 != null && !"".equals(errStr2))
		{
			
			throw new ServiceException(tempStr+",��Ʊ���к�Ϊ["+errStr2.substring(0,errStr2.length()-1)+"]�ķ�Ʊ״̬��������ȡ,���ܻؿ�!");
		}
		
	}

	/**
	 *  ��Ʊ�ؿ� ��ѯ
	 *  ����������ѯ��Ʊ���к�
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoBackQuery(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {

		CommonQueryConditionDTO commDto = event.getCommDTO();
		String type = commDto.getSpareStr2();
		String serialBegin = commDto.getSpareStr3();
		String serialEnd = commDto.getSpareStr4();
		
		String fromAddressType = commDto.getSpareStr5();
		String fromAddressID = commDto.getSpareStr6();
	
		String serialsStr = BusinessUtility.getFaPiaoSerialNosByCon(type,serialBegin,serialEnd,fromAddressType,fromAddressID);

		this.context.put(Service.PROCESS_RESULT, serialsStr);

	}
	
	
	
	/*
	 * ��Ʊ�������
	 * 
	 * 1 ��Ʊ���붼�ڿ����
	 * 
	 */
	private void checkValideFaPiaoMove(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException,
			ServiceException {
		
		
		//����
		String type = event.getFaPiaoDTO().getType();
		ArrayList serailNoList = new ArrayList();
		//���÷�Ʊ�����,��Ҫ�ӷ�Ʊ���еõ���Ʊ���к�,��List�ķ�ʽ�洢
		if(event.isVolumnManage())
		{
			String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
			FapiaoVolumn faVo = BusinessUtility.getFapiaoVolumn(type, volumnSN);
			if(faVo == null) throw new ServiceException("��Ʊ����Ϊ:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ�᲻����!");
			//��Ʊ��״̬���:��Ʊ��״̬ӦΪ���  �б�Ҫ??--��������Ҫ���
			if(!CommonConstDefinition.FAPIAO_STATUS_SAV.equals(faVo.getStatus()))
				throw new ServiceException("��Ʊ����Ϊ"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ��״̬���ǿ��,���ܵ���!");
			
			
			//Ŀ��ֿ���ԭ�ֿ�һ��
			if("D".equals(faVo.getAddressType()) && (faVo.getAddressID()==event.getFaPiaoDTO().getAddressID()))
				throw new ServiceException("Ŀ��ֿ���ԭ�ֿ�һ��,����Ҫ����!");
			
			List faDtoList = BusinessUtility.getFaPiaoDtoByCon(type,volumnSN,null);
			if(faDtoList==null || faDtoList.isEmpty())
				throw new ServiceException("��Ʊ����Ϊ:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ����û�з�Ʊ!");
			for(int i=0;i<faDtoList.size();i++)
			{
				FaPiaoDTO faDto = (FaPiaoDTO)faDtoList.get(i);
				serailNoList.add(faDto.getSerailNo());
			}
			event.setSerailNoList(serailNoList);
			//LogUtility.log(clazz, LogLevel.DEBUG, "___4___="+serialsList);
		}
		
		List serialsList = event.getSerailNoList(); 
		
		//������ļ����룬��Ҫ����ļ��з�Ʊ���кű����Ƿ��ظ�
		StringBuffer errBuff3 = new StringBuffer();
		if("1".equals(event.getMakeStyle()))
		{
			Set tempSet = new HashSet();
			for(int i=0;i<serialsList.size();i++)
				tempSet.add(serialsList.get(i));
			Iterator temIter = tempSet.iterator();
			while(temIter.hasNext())
			{
				String eachSer = (String)temIter.next();
				if(serialsList.indexOf(eachSer)!=serialsList.lastIndexOf(eachSer))
				{
					errBuff3.append(eachSer+",");
				}
			}
			String errStr3 = errBuff3.toString();
			if(errStr3 != null && !"".equals(errStr3))
			{
				throw new ServiceException("�������ļ��з�Ʊ���к�["+errStr3.substring(0,errStr3.length()-1)+"]�����ظ�,����.");
			} 
		}
		
		
		/*
		 * 1 �������ķ�Ʊ���������Ϊ���
		 * 2 �������ķ�Ʊ�ֿ���Ŀ��ֿⲻ��һ��
		 */
		
		//��������Դ���
		StringBuffer errBuff1 = new StringBuffer();
		//����״̬����
		StringBuffer errBuff2 = new StringBuffer();
		
		//�������Դ��Ŀ��һ�´���
		StringBuffer errBuff5 = new StringBuffer();

		for (int i=0;i<serialsList.size();i++) {
			String serialNo = (String) serialsList.get(i);
			//��������,��Ʊ�����к�,��Ʊ���кŲ�ѯ��Ʊ
			List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, event.getFapiaoVolumnDTO().getVolumnSn(), serialNo);
			
			if(faPiaoList == null || faPiaoList.isEmpty())
			{
				errBuff1.append(serialNo+",");
				continue;
			}
			else
			{
				//������ֻ������һ�����
				FaPiaoDTO theFaPiao = (FaPiaoDTO) faPiaoList.get(0);
				if(!CommonConstDefinition.FAPIAO_STATUS_SAV.equals(theFaPiao.getStatus()))
					errBuff2.append(serialNo+",");
				if(CommonConstDefinition.FAPIAO_STATUS_SAV.equals(theFaPiao.getStatus()) 
				  && CommonConstDefinition.FAPIAO_ADDRESSTYPE_D.equals(theFaPiao.getAddressType())
				  && event.getFaPiaoDTO().getAddressID()==theFaPiao.getAddressID())
				{
					errBuff5.append(serialNo+",");
				}
				
			}
		}
		String errStr1 = errBuff1.toString();
		String tempStr="��Ʊ����Ϊ:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type);
		if(event.isVolumnManage())
			tempStr = tempStr+",��Ʊ�����к�Ϊ:"+event.getFapiaoVolumnDTO().getVolumnSn();
		
		if(errStr1 != null && !"".equals(errStr1))
		{
			throw new ServiceException(tempStr+",��Ʊ���к�Ϊ["+errStr1.substring(0,errStr1.length()-1)+"]�ķ�Ʊ������,�޷�����!");
		}
		
	    String errStr2 = errBuff2.toString();
		if(errStr2 != null && !"".equals(errStr2))
		{
			throw new ServiceException(tempStr+",��Ʊ���к�Ϊ["+errStr2.substring(0,errStr2.length()-1)+"]�ķ�Ʊ״̬���ǿ��,���ܵ���!");
		}
		
		 String errStr5 = errBuff5.toString();
		if(errStr5 != null && !"".equals(errStr5))
		{
			throw new ServiceException(tempStr+",��Ʊ���к�Ϊ["+errStr5.substring(0,errStr5.length()-1)+"]�ķ�Ʊ��ַ��������ֿ�һ��,�������!");
		}
	}
	/**
	 * ��Ʊ����:��¼����
	 * 
	 * @param serialsList
	 * @param opID
	 */
	private void faPiaoTransitionForMove(FaPiaoEJBEvent event) throws ServiceException {
		
		
		int orgID = BusinessUtility.FindOrgIDByOperatorID(event.getOperatorID());
		String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
		String type = event.getFaPiaoDTO().getType();
		int depotID = event.getFaPiaoDTO().getAddressID();
		try {

			/*
			 * ������תͷ��¼
			 * 
			 * T_FAPIAOTRANSITION
				SEQNO = FAPIAOTRANSITION_SEQNO.nextval;
				FileName =�ļ���
				Total = �ܼ�¼��
				OpID = ����ԱID
				OrgID = ����Ա������֯����
				Action =  ��CANN��
				Status=��V��;
				CreateTime=SysDate;
			 */
			Integer volumnSeqNo = BusinessUtility.getFapiaoVolumn(type, volumnSN).getSeqNo();
			FapiaoTransitionDTO fapiaoTransitionDto=new FapiaoTransitionDTO();
			
			if("1".equals(event.getMakeStyle()))
				fapiaoTransitionDto.setFileName(event.getFileName());
			fapiaoTransitionDto.setTotal(event.getSerailNoList().size());
			fapiaoTransitionDto.setOpID(event.getOperatorID());
			fapiaoTransitionDto.setOrgID(orgID);
			fapiaoTransitionDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_CANN);
			fapiaoTransitionDto.setStatus("V");
			fapiaoTransitionDto.setVolumnSeqNo(volumnSeqNo.intValue());
			fapiaoTransitionDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			
			FapiaoTransitionHome faPiaoTransHome = HomeLocater.getFapiaoTransitionHome();
			FapiaoTransition faPiaoTrans = faPiaoTransHome.create(fapiaoTransitionDto);
			fapiaoTransitionDto.setSeqNo(faPiaoTrans.getSeqNo().intValue());
			
			//���޸ķ�Ʊ����
			String faPiaoSeqNoStr = "";
			//�ؿ��漰���ķ�Ʊ��
			Set faPiaoVo = new HashSet();
			/*
			 * ������ת��ϸ��¼
			 * �޸ķ�Ʊ��¼
			 * 
			 */
			Iterator ite = event.getSerailNoList().iterator();
			while (ite.hasNext()) {
				/*
				 * T_FAPIAOTRANSITIONDETAIL
					SEQNO = FAPIAOTRANSITIONDETAIL_SEQNO.nextval;
					VolumnSeqNO = ��Ʊ�����к�
					FapiaoTransSeqNO = ͷ��¼���к�
					FapiaoSeqNo = ��Ʊ���к�
					OpID = ����ԱID
					OrgID = ��֯����ID
					Action =  ��CANN
					FromStatus = ��SAV��
					ToStatus =��SAV;
					FromAddressType= ԭ��Ʊ��ַ����;
					FromAddressID = ԭ��Ʊ��ַID��
					ToAddressType =Ŀ�귢Ʊ��ַ����;;
					ToAddressID = �ֿ�ID
				 */
				String serial = (String) ite.next();
				//�õ���Ʊ
				List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, volumnSN, serial);
				if(faPiaoList == null)
					throw new ServiceException("��Ʊ��λ����");
				FaPiaoDTO theDto = (FaPiaoDTO)faPiaoList.get(0);
				if(faPiaoSeqNoStr != null)faPiaoSeqNoStr=faPiaoSeqNoStr+",";
				faPiaoSeqNoStr = faPiaoSeqNoStr+theDto.getSeqNo();
				
				faPiaoVo.add(theDto.getVolumnSeqno()+"");
				
				FapiaoTransitionDetailDTO faPiaoTransDetailDto=new FapiaoTransitionDetailDTO();

				faPiaoTransDetailDto.setVolumnSeqNo(theDto.getVolumnSeqno()+"");
				faPiaoTransDetailDto.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
				faPiaoTransDetailDto.setFapiaoSeqNo(theDto.getSeqNo());
				faPiaoTransDetailDto.setOpID(event.getOperatorID());
				faPiaoTransDetailDto.setOrgID(orgID);
				faPiaoTransDetailDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_CANN);
				faPiaoTransDetailDto.setFromStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
				faPiaoTransDetailDto.setToStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
				faPiaoTransDetailDto.setFromAddressType(theDto.getAddressType());
				faPiaoTransDetailDto.setFromAddressID(theDto.getAddressID());
				faPiaoTransDetailDto.setToAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_D);
				faPiaoTransDetailDto.setToAddressID(new Integer(depotID).intValue());
				
				faPiaoTransDetailDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
				faPiaoTransDetailDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				
				FapiaoTransitionDetailHome faPiaoTransDetailHome = HomeLocater.getFapiaoTransitionDetailHome();
				FapiaoTransitionDetail faPiaoTransDetail = faPiaoTransDetailHome.create(faPiaoTransDetailDto);
				faPiaoTransDetailDto.setSeqNo(faPiaoTransDetail.getSeqNo().intValue());
				
				/*
				 * �޸ķ�Ʊ��¼
				 * T_FAPIAO
					Status = ��SAV��
					StatusTime = SysDate;
					AddressType = ��D��
					AddressID =�ֿ�ID
					DT_LASTMOD = SysTimeStamp;

				 */
				FaPiaoHome faPiaoHome = HomeLocater.getFaPiaoHome();
				FaPiao theFaPiao = faPiaoHome.findByPrimaryKey(new Integer(theDto.getSeqNo()));
				theFaPiao.setStatus(faPiaoTransDetailDto.getToStatus());
				theFaPiao.setStatusTime(TimestampUtility.getCurrentTimestamp());
				theFaPiao.setAddressType(faPiaoTransDetailDto.getToAddressType());
				theFaPiao.setAddressID(faPiaoTransDetailDto.getToAddressID());
				theFaPiao.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			}
			//ֻ�а������ʱ�Ż��漰��Ʊ���޸�,����û�з�Ʊ��ĸ���
			if(event.isVolumnManage())
			{
			    /*
				 * �޸ķ�Ʊ���¼
				 * �����Ʊ������Ʊ��ȫ������������Ʊ���ַ���͸���Ϊ��D��,��ַID����Ϊ�ֿ�ID
				 * �����Ʊ������ͬһ����Ʊ�ᣬ�ǿ��ܸ��Ķ�����¼.
				*/
				
				Iterator ite1 =  faPiaoVo.iterator();
				while(ite1.hasNext())
				{
					String faPiaoVoSeqNo = ite1.next()+"";
					if(BusinessUtility.isMove(faPiaoVoSeqNo,faPiaoSeqNoStr,depotID+""));
					{
						FapiaoVolumnHome faPiaoVoHome = HomeLocater.getFapiaoVolumnHome();
						FapiaoVolumn theFaPiaoVo = faPiaoVoHome.findByPrimaryKey(new Integer(faPiaoVoSeqNo));
						theFaPiaoVo.setStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
						theFaPiaoVo.setStatusTime(TimestampUtility.getCurrentTimestamp());
						theFaPiaoVo.setAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_D);
						theFaPiaoVo.setAddressID(new Integer(depotID).intValue());
						theFaPiaoVo.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					}
					
				}
			}
			
			
			context.put(com.dtv.oss.service.Service.PROCESS_RESULT, "");

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��Ʊ���кŶ�λ����!");
		}
		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��Ʊ���Ҵ���!");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} 
	}	
	
	/*
	 * ��Ʊ���ü��
	 * 
	 * 
	 */
	private void checkValideFaPiaoUse(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException,
			ServiceException {
		
		//����
		String type = event.getFaPiaoDTO().getType();
		ArrayList serailNoList = new ArrayList();
		//���÷�Ʊ�����,��Ҫ�ӷ�Ʊ���еõ���Ʊ���к�,��List�ķ�ʽ�洢
		if(event.isVolumnManage())
		{
			String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
			FapiaoVolumn faVo = BusinessUtility.getFapiaoVolumn(type, volumnSN);
			if(faVo == null) throw new ServiceException("��Ʊ����Ϊ"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ�᲻����!");
			//��Ʊ��״̬���:��Ʊ��״̬ӦΪ���  �б�Ҫ??--��������Ҫ���
			if(!CommonConstDefinition.FAPIAO_STATUS_SAV.equals(faVo.getStatus()))
				throw new ServiceException("��Ʊ����Ϊ"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ��״̬���ǿ��,��������!");
			
			
			List faDtoList = BusinessUtility.getFaPiaoDtoByCon(type,volumnSN,null);
			if(faDtoList==null || faDtoList.isEmpty())
				throw new ServiceException("��Ʊ����Ϊ"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",��Ʊ�����к�Ϊ:"+volumnSN+"�ķ�Ʊ����û�п����õķ�Ʊ!");
			for(int i=0;i<faDtoList.size();i++)
			{
				FaPiaoDTO faDto = (FaPiaoDTO)faDtoList.get(i);
				serailNoList.add(faDto.getSerailNo());
			}
			event.setSerailNoList(serailNoList);
		}
		

		
		List serialsList = event.getSerailNoList();
		
		//������ļ����룬��Ҫ����ļ��з�Ʊ���кű����Ƿ��ظ�
		StringBuffer errBuff3 = new StringBuffer();
		if("1".equals(event.getMakeStyle()))
		{
			Set tempSet = new HashSet();
			for(int i=0;i<serialsList.size();i++)
				tempSet.add(serialsList.get(i));
			Iterator temIter = tempSet.iterator();
			while(temIter.hasNext())
			{
				String eachSer = (String)temIter.next();
				if(serialsList.indexOf(eachSer)!=serialsList.lastIndexOf(eachSer))
				{
					errBuff3.append(eachSer+",");
				}
			}
			String errStr3 = errBuff3.toString();
			if(errStr3 != null && !"".equals(errStr3))
			{
				throw new ServiceException("�������ļ��з�Ʊ���к�["+errStr3.substring(0,errStr3.length()-1)+"]�����ظ�,����.");
			} 
		}
		
		
		/*
		 * 
		 * �����õķ�Ʊ���������Ϊ���
		 *
		 */
		
		if(event.isVolumnManage())
		{
			
		}
		//��������Դ���
		StringBuffer errBuff1 = new StringBuffer();
		//����״̬����
		StringBuffer errBuff2 = new StringBuffer();
		for (int i=0;i<serialsList.size();i++) {
			String serialNo = (String) serialsList.get(i);
			//��������,��Ʊ�����к�,��Ʊ���кŲ�ѯ��Ʊ
			List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, event.getFapiaoVolumnDTO().getVolumnSn(), serialNo);
			if(faPiaoList == null || faPiaoList.isEmpty())
			{
				errBuff1.append(serialNo+",");
				continue;
			}
			else
			{
				//������ֻ������һ�����
				FaPiaoDTO theFaPiao = (FaPiaoDTO) faPiaoList.get(0);
				if(!CommonConstDefinition.FAPIAO_STATUS_SAV.equals(theFaPiao.getStatus()))
				{
					errBuff2.append(serialNo+",");
				}
				
			}
		}
		String errStr1 = errBuff1.toString();
		
		String tempStr="��Ʊ����Ϊ:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type);
		if(event.isVolumnManage())
			tempStr = tempStr+",��Ʊ�����к�Ϊ:"+event.getFapiaoVolumnDTO().getVolumnSn();
		if(errStr1 != null && !"".equals(errStr1))
		{
			
			throw new ServiceException(tempStr+",��Ʊ���к�Ϊ["+errStr1.substring(0,errStr1.length()-1)+"]�ķ�Ʊ������,�޷���ȡ!");
		}
		
	    String errStr2 = errBuff2.toString();
		if(errStr2 != null && !"".equals(errStr2))
		{
			
			throw new ServiceException(tempStr+",��Ʊ���к�Ϊ["+errStr2.substring(0,errStr2.length()-1)+"]�ķ�Ʊ״̬���ǿ��,������ȡ.");
		}
		
	}
	/**
	 * ��Ʊ����:��¼����
	 * 
	 * @param serialsList
	 * @param opID
	 */
	private void faPiaoTransitionForUse(FaPiaoEJBEvent event) throws ServiceException {
		
		//"Y"--�������,��Ҫ�޸ķ�Ʊ����Ϣ,������Ҫ
		int orgID = BusinessUtility.FindOrgIDByOperatorID(event.getOperatorID());
		String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
		String type = event.getFaPiaoDTO().getType();

		int toAddressID = event.getFaPiaoDTO().getAddressID();
		String fileName = event.getFileName();
		try {

			/*
			 * ������תͷ��¼
			 * 
			 * T_FAPIAOTRANSITION
			 * SEQNO = FAPIAOTRANSITION_SEQNO.nextval;
			   FileName =�ļ���
			   Total = �ܼ�¼��
			   OpID = ����ԱID
			   OrgID = ����Ա������֯����
			   Action =  ��OUT��
			   Status=��V��;
			   CreateTime=SysDate;


			 */
			Integer volumnSeqNo = BusinessUtility.getFapiaoVolumn(type, volumnSN).getSeqNo();
			FapiaoTransitionDTO fapiaoTransitionDto=new FapiaoTransitionDTO();
			
			if("1".equals(event.getMakeStyle()))
				fapiaoTransitionDto.setFileName(fileName);
			fapiaoTransitionDto.setTotal(event.getSerailNoList().size());
			fapiaoTransitionDto.setOpID(event.getOperatorID());
			fapiaoTransitionDto.setOrgID(orgID);
			fapiaoTransitionDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_OUT);
			fapiaoTransitionDto.setStatus("V");
			fapiaoTransitionDto.setVolumnSeqNo(volumnSeqNo.intValue());
			fapiaoTransitionDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			fapiaoTransitionDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			
			FapiaoTransitionHome faPiaoTransHome = HomeLocater.getFapiaoTransitionHome();
			FapiaoTransition faPiaoTrans = faPiaoTransHome.create(fapiaoTransitionDto);
			fapiaoTransitionDto.setSeqNo(faPiaoTrans.getSeqNo().intValue());
			
			//���޸ķ�Ʊ����
			String faPiaoSeqNoStr = "";
			//�ؿ��漰���ķ�Ʊ��
			Set faPiaoVo = new HashSet();
			/*
			 * ������ת��ϸ��¼
			 * �޸ķ�Ʊ��¼
			 * 
			 */
			Iterator ite = event.getSerailNoList().iterator();
			while (ite.hasNext()) {
				/*
				 * T_FAPIAOTRANSITIONDETAIL
				SEQNO = FAPIAOTRANSITIONDETAIL_SEQNO.nextval;
				VolumnSeqNO = ��Ʊ�����к�
				FapiaoTransSeqNO = ͷ��¼���к�
				FapiaoSeqNo = ��Ʊ���к�
				OpID = ����ԱID
				OrgID = ��֯����ID
				Action =  ��OUT
				FromStatus = ��SAV��
				ToStatus =��REC;
				FromAddressType= ԭ��Ʊ��ַ����;
				FromAddressID = ԭ��Ʊ��ַID��
				ToAddressType =  ��O��;
				ToAddressID =��֯����ID
				 */
				String serial = (String) ite.next();
				//�õ���Ʊ
				List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, volumnSN, serial);
				if(faPiaoList == null)
					throw new ServiceException("��Ʊ��λ����");
				FaPiaoDTO theDto = (FaPiaoDTO)faPiaoList.get(0);
				if(faPiaoSeqNoStr != null)faPiaoSeqNoStr=faPiaoSeqNoStr+",";
				faPiaoSeqNoStr = faPiaoSeqNoStr+theDto.getSeqNo();
				
				faPiaoVo.add(theDto.getVolumnSeqno()+"");
				
				FapiaoTransitionDetailDTO faPiaoTransDetailDto=new FapiaoTransitionDetailDTO();

				faPiaoTransDetailDto.setVolumnSeqNo(theDto.getVolumnSeqno()+"");
				faPiaoTransDetailDto.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
				faPiaoTransDetailDto.setFapiaoSeqNo(theDto.getSeqNo());
				faPiaoTransDetailDto.setOpID(event.getOperatorID());
				faPiaoTransDetailDto.setOrgID(orgID);
				faPiaoTransDetailDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_OUT);
				faPiaoTransDetailDto.setFromStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
				faPiaoTransDetailDto.setToStatus(CommonConstDefinition.FAPIAO_STATUS_REC);
				faPiaoTransDetailDto.setFromAddressType(theDto.getAddressType());
				faPiaoTransDetailDto.setFromAddressID(theDto.getAddressID());
				faPiaoTransDetailDto.setToAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_O);
				faPiaoTransDetailDto.setToAddressID(new Integer(toAddressID).intValue());
				
				faPiaoTransDetailDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
				faPiaoTransDetailDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				
				FapiaoTransitionDetailHome faPiaoTransDetailHome = HomeLocater.getFapiaoTransitionDetailHome();
				FapiaoTransitionDetail faPiaoTransDetail = faPiaoTransDetailHome.create(faPiaoTransDetailDto);
				faPiaoTransDetailDto.setSeqNo(faPiaoTransDetail.getSeqNo().intValue());
				
				/*
				 * �޸ķ�Ʊ��¼
				 * T_FAPIAO	
					RecipientOpID = ����ԱID��
					RecipientOrgID = ����Ա������֯������
					RecipientTime = SysDate��
					STATUS = ��REC��
					StatusTime = SysDate;
					AddressType = ��O��
					AddressID =��֯����ID
					DT_LastMod = SystimeStamp;


				 */
				FaPiaoHome faPiaoHome = HomeLocater.getFaPiaoHome();
				FaPiao theFaPiao = faPiaoHome.findByPrimaryKey(new Integer(theDto.getSeqNo()));
				theFaPiao.setRecipientOpID(event.getOperatorID());
				theFaPiao.setRecipientOrgID(orgID);
				theFaPiao.setRecipientTime(TimestampUtility.getCurrentTimestamp());
				theFaPiao.setStatus(faPiaoTransDetailDto.getToStatus());
				theFaPiao.setStatusTime(TimestampUtility.getCurrentTimestamp());
				theFaPiao.setAddressType(faPiaoTransDetailDto.getToAddressType());
				theFaPiao.setAddressID(faPiaoTransDetailDto.getToAddressID());
				theFaPiao.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			}
			if(event.isVolumnManage())
			{
			    /*
				 * �޸ķ�Ʊ���¼
				 * �����Ʊ������Ʊ��ȫ�������ã���Ʊ���ַ���͸���Ϊ��O��,��ַID����Ϊ��֯����ID
					�����Ʊ������ͬһ����Ʊ�ᣬ�ǿ��ܸ��Ķ�����¼

				*/
				//�ж������÷�Χ�ڵķ�Ʊ��Ӧ�ķ�Ʊ������з�Ʊ�Ƿ����õ�ָ������֯����,���򷵻�true
				Iterator ite1 =  faPiaoVo.iterator();
				while(ite1.hasNext())
				{
					String faPiaoVoSeqNo = ite1.next()+"";
					if(BusinessUtility.isUse(faPiaoVoSeqNo,faPiaoSeqNoStr,toAddressID));
					{
						FapiaoVolumnHome faPiaoVoHome = HomeLocater.getFapiaoVolumnHome();
						FapiaoVolumn theFaPiaoVo = faPiaoVoHome.findByPrimaryKey(new Integer(faPiaoVoSeqNo));
						theFaPiaoVo.setStatus(CommonConstDefinition.FAPIAO_STATUS_REC);
						theFaPiaoVo.setStatusTime(TimestampUtility.getCurrentTimestamp());
						theFaPiaoVo.setAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_O);
						theFaPiaoVo.setAddressID(new Integer(toAddressID).intValue());
						theFaPiaoVo.setDtLastmod(TimestampUtility.getCurrentTimestamp());
						
						theFaPiaoVo.setRecepientOpID(event.getOperatorID());
						theFaPiaoVo.setRecipientOrgID(orgID);
					}
					
				}
			}
			
			context.put(com.dtv.oss.service.Service.PROCESS_RESULT, "");

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��Ʊ���кŶ�λ����!");
		}
		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��Ʊ���Ҵ���!");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} 
	}	
	
	public static void main(String [] args)
	{
		ArrayList aa=new ArrayList();
		aa.add("1");
		aa.add("2");
		aa.add("3");
		aa.add("2");
		Set bb = new HashSet();
		for(int i=0;i<aa.size();i++)
			bb.add(aa.get(i));
		System.out.println(bb);
		System.out.println(aa);
		System.out.println(aa.indexOf("3")==aa.lastIndexOf("3"));
	}

}
