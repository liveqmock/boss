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
 * 发票流转类操作 
 * 
 * @author 
 * 
 */
public class FaPiaoService extends AbstractService {

	private static final Class clazz = FaPiaoService.class;

	private ServiceContext context;

	/**
	 * 构造函数
	 * 
	 * @param s
	 */
	public FaPiaoService(ServiceContext s) {
		this.context = s;
	}

	/**
	 *  发票运入
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoInStock(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		
		
		if (event.getSerailNoList()==null || event.getSerailNoList().isEmpty())
			throw new ServiceException("没有需要运入的发票序列号");
		//检查
		checkValideFaPiaoIn(event);
		//LogUtility.log(this.getClass(), LogLevel.DEBUG, "__1_type="+commDto.getSpareStr2());
		if (event.isDoPost())
			//记录相关信息
			faPiaoTransitionForIn(event);
		this.context.put(Service.PROCESS_RESULT, event.getSerailNoList());

	}

	/**
	 *  发票回库
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoBack(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		//检查
		checkValideFaPiaoBack(event);
		if (event.isDoPost())
			//记录相关信息
			faPiaoTransitionForBack(event);
		this.context.put(Service.PROCESS_RESULT, event.getSerailNoList());

	}

	/**
	 *  发票调拨
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoMove(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		//检查
		checkValideFaPiaoMove(event);
		if (event.isDoPost())
			//记录相关信息
			faPiaoTransitionForMove(event);
		this.context.put(Service.PROCESS_RESULT, event.getSerailNoList());

	}
	
	/**
	 *  发票领用
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	
	public void faPiaoUse(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		//检查
		checkValideFaPiaoUse(event);
		if (event.isDoPost())
			//记录相关信息
			faPiaoTransitionForUse(event);
		this.context.put(Service.PROCESS_RESULT, event.getSerailNoList());

	}
	
		/**
	 * 发票作废
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
		 * @throws CreateException 
	 * 
	 */
	// 发票作废，wangpeng@20080313
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
			throw new ServiceException("没有需要运入的发票序列号");
	
		String[] serialsArray = serialsStr.split("\n");
		ArrayList serialsList = new ArrayList();
		for (int i = 0; i < serialsArray.length; i++) {
			if (serialsArray[i] != null && !"".equals(serialsArray[i].trim()))
				serialsList.add(serialsArray[i].trim());
		}
		// 检查待作废发票是否满足“不为作废并且不为报废”的状态
		checkFapiaoStatusForAbandon(serialsList,fapiaoType,volumn);

		
		if(!event.isDoPost()){
			/*
			 * 取得所有可做带作废发票的 DtLastmod
			 * 存储在dtLastmodList中。
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
			
			//取出所有dtLastmod,一个字符串中以逗号隔开
			String dtLastmods=event.getDtLastmods();
			String [] dtLastmodsArr=dtLastmods.split(",");
			
			int arrIndex=0;

			// 开始处理每一张待作废发票的相关数据变更
			Iterator i = serialsList.iterator();
			
			String hasAbandonFapiaoSN=new String();
			
			
			/*
			 * Step3.处理发票Transition
			 */
			FapiaoTransitionDTO fapiaoTransitionDto=new FapiaoTransitionDTO();
			int volumnSeqNo=0;//0为没有管理发票册时，发票册的统一默认值。				
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
				
				//取出当前发票的dtLastmod
				Timestamp oneDtLastmod=Timestamp.valueOf(dtLastmodsArr[arrIndex]);
				arrIndex++;
				
				/*
				 * Step1.处理发票数据
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
					throw new ServiceException("Step1.处理发票数据出错");
				}
				hasAbandonFapiaoSN+="'"+serial+"',";

	
				try {

					/*
					 * Step4.处理发票TransitionDetail
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

					
/*暂时不处理这两个实体的存储					
					
					
					//Step5.处理发票FAPIAO2PAYMENT
					 
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
					 //Step6.处理发票FAPIAODETAIL
					
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
					LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e);
					throw new ServiceException("创建记录出错！");
				} catch (EJBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoveException e) {
					LogUtility.log(clazz, LogLevel.WARN, "删除记录出错：" + e);
					throw new ServiceException("删除记录出错！");
				} 	
				
			}
			hasAbandonFapiaoSN=hasAbandonFapiaoSN.substring(0,hasAbandonFapiaoSN.length()-1);
			
			/*
			 * Step2.处理发票册。
			 * 如果发票册SN为空，即为不管理发票册的配置，
			 * 那么不做发票册处理。
			 * 
			 * 如果该发票册中的发票状态都为"CAN"即作废，
			 * 那么发票册状态也标记为"CAN"。
			 */
			if(volumn!=null&&!volumn.equals("")){
				if(!BusinessUtility.hasUnCanceledFapiao(fapiaoType, volumn,hasAbandonFapiaoSN)){
					
					FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
					
					FapiaoVolumnDTO volumnDto =new FapiaoVolumnDTO();
					volumnDto.setStatus(CommonConstDefinition.FAPIAO_STATUS_CAN);
					volumnDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
					volumnDto.setDtLastmod(fapiaoVolumn.getDtLastmod());
					if(fapiaoVolumn.ejbUpdate(volumnDto)==-1){
						throw new ServiceException("Step2.处理发票册数据出错");
					}
					//System.out.println("---------------------------2------------------------");						
				}
			}			
	
		}
			
		this.context.put(Service.PROCESS_RESULT, dtLastmodList);
		
	
	}

	
	/**
	 * 发票报废
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 * @throws CreateException 
	 * 
	 */
	// 发票报废，wangpeng@20080320
	public void faPiaoDiscard(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException, CreateException {
		FaPiaoDTO fapiaoDTO = event.getFaPiaoDTO();
		FapiaoVolumnDTO volumnDTO=event.getFapiaoVolumnDTO();
		boolean isVolumnManage=event.isVolumnManage();
		//List dtLastmodList=new ArrayList();	
		String volumn="";
		String fapiaoType=fapiaoDTO.getType();
		List fapiaoList=new ArrayList();//所有FapiaoDTO的集合
		/*
		 * 发票单张报废
		 */		
		if(event.getDiscardStyle().equals("2")){
			String serialsStr = event.getSerailnos();
			if(isVolumnManage){
				volumn= volumnDTO.getVolumnSn();
			}
			if (serialsStr == null || "".equals(serialsStr))
				throw new ServiceException("没有需要报废的发票序列号");
			String[] serialsArray = serialsStr.split("\n");
			List serialsList = new ArrayList();
			for (int i = 0; i < serialsArray.length; i++) {
				if (serialsArray[i] != null && !"".equals(serialsArray[i].trim()))
					serialsList.add(serialsArray[i].trim());
			}
			// 检查待作废发票是否满足“不为作废并且不为报废”的状态
			checkFapiaoStatusForDiscard(serialsList,fapiaoType,volumn);
			//取出所有发票DTO，存储到fapiaoList中。
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
				//处理待报废发票
				discardFapiao(event, volumn, fapiaoType, serialsList);
				if(isVolumnManage){
				/*
				 * 判断是否要处理发票册，
				 * 如果管理发票册，则需要判断所在发票册中发票是否都报废
				 */			
					String hasDiscardFapiaoSN=new String();
					Iterator iter=serialsList.iterator();
					while(iter.hasNext()){
						hasDiscardFapiaoSN+="'"+(String)iter.next()+"',";
					}
					hasDiscardFapiaoSN=hasDiscardFapiaoSN.substring(0,hasDiscardFapiaoSN.length()-1);				
					//判断所在发票册中发票是否都报废
					if(!BusinessUtility.hasUnDiscardFapiao(fapiaoType, volumn,hasDiscardFapiaoSN)){
						//System.out.println("_____________A");
						FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
						//System.out.println("_____________B");
						FapiaoVolumnDTO volumnDto =new FapiaoVolumnDTO();
						volumnDto.setStatus(CommonConstDefinition.FAPIAO_STATUS_DIS);
						volumnDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
						volumnDto.setDtLastmod(fapiaoVolumn.getDtLastmod());
						if(fapiaoVolumn.ejbUpdate(volumnDto)==-1){
							throw new ServiceException("Step2.处理发票册数据出错");
						}	
					}
					//System.out.println("_____________C");
				}	
			}
			//System.out.println("_____________D");
		}
		/*
		 * 发票册整本报废
		 */			
		else if(event.getDiscardStyle().equals("1")&&event.isVolumnManage()){
			volumn= volumnDTO.getVolumnSn();
			// 检查发票册是否已经报废
			if(isFapiaoVolumnDiscard(fapiaoType,volumn)){
				throw new ServiceException("该发票册已经报废");
			}
			//取出该册下所有未报废发票			
			fapiaoList=BusinessUtility.getUnDisFapiaoDTOByVolumnSN(fapiaoType, volumn);	
			this.context.put(Service.PROCESS_RESULT, fapiaoList);			
			// 提交
			if(event.isDoPost()){
				List serialsList=new ArrayList();
				Iterator iter=fapiaoList.iterator();
				while(iter.hasNext()){
					String serial=((FaPiaoDTO)iter.next()).getSerailNo();
					serialsList.add(serial);
				}
				//发票册报废---begin
				FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
				FapiaoVolumnDTO volumnDto =new FapiaoVolumnDTO();
				volumnDto.setStatus(CommonConstDefinition.FAPIAO_STATUS_DIS);
				volumnDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
				volumnDto.setDtLastmod(fapiaoVolumn.getDtLastmod());
				if(fapiaoVolumn.ejbUpdate(volumnDto)==-1){
					throw new ServiceException("Step2.处理发票册数据出错");
				}
				//发票册报废---end
				//处理发票中待作废发票
				discardFapiao(event, volumn, fapiaoType, serialsList);
			}	
		}
		//System.out.println("_____________E");
	}

	private void discardFapiao(FaPiaoEJBEvent event, String volumn,
			String fapiaoType, List serialsList) throws HomeFactoryException,
			FinderException, ServiceException, CreateException {
		// 开始处理每一张待报废发票的相关数据变更
		Iterator i = serialsList.iterator();
		int operID=event.getOperatorID();
		int orgID=BusinessUtility.FindOrgIDByOperatorID(operID);	
		int volumnSeqNo=0;//0为没有管理发票册时，发票册的统一默认值。				
		if(volumn!=null&&!"".equals(volumn)){
				FapiaoVolumn fapiaoVolumn=BusinessUtility.getFapiaoVolumn(fapiaoType, volumn);
				volumnSeqNo=fapiaoVolumn.getSeqNo().intValue();
		}
		/*
		 * Step3.处理发票Transition
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
			 * Step1.处理发票数据
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
				throw new ServiceException("更新发票数据出错");
			}
			//System.out.println("---------------------------1------------------------");
			try {

				/*
				 * Step4.处理发票TransitionDetail
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






				 //Step5.处理发票FAPIAO2PAYMENT
				Fapiao2PaymentHome paymentHome= HomeLocater.getFapiao2PaymentHome();
				
				
				
				//直接删除的方法 chaqiu 20080323 begin
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "__fapiaoSeqNo____="+fapiaoSeqNo);
				Collection paymentList = paymentHome.findByFapiaoSeqNo(fapiaoSeqNo);
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "__paymentList____="+paymentList);
				Iterator iter1=paymentList.iterator();
				while(iter1.hasNext()){
					Fapiao2Payment payment=(Fapiao2Payment)iter1.next();
					LogUtility.log(this.getClass(), LogLevel.DEBUG, "__payment____="+payment);
				payment.remove();
				}
                 //直接删除的方法 chaqiu 20080323 end
				
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
				
				

				 //Step6.删除FAPIAODETAIL				
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
				LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e);
				throw new ServiceException("创建记录出错！");
			} catch (EJBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoveException e) {
				LogUtility.log(clazz, LogLevel.WARN, "删除记录出错：" + e);
				throw new ServiceException("删除记录出错！");
			}	
		}
	}
		
	
private boolean isFapiaoVolumnDiscard(String type,String volumn) throws ServiceException{
	
	FapiaoVolumn fapiaoVolumn;
	try {
		fapiaoVolumn = BusinessUtility.getFapiaoVolumn(type, volumn);

		if(fapiaoVolumn==null){
			throw new ServiceException("该发票册不存在");
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
	
	// 检查待作废发票是否满足“不为作废并且不为报废”的状态
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
					errorMessage.append(" 发票 ");					
					errorMessage.append(serialNo);
					errorMessage.append(" 不存在\n");

				}else{
				
					status = ((FaPiaoDTO)list.get(0)).getStatus();
	
					if(status.equals(CommonConstDefinition.FAPIAO_STATUS_CAN)
							||status.equals(CommonConstDefinition.FAPIAO_STATUS_DIS)) {
						errorFlag=true;
						errorMessage.append(serialNo);
						errorMessage.append(" 已作废或报废\n");
					}
				}
		}
		if(errorFlag) throw new ServiceException(errorMessage.toString());
	}
	
	// 检查待作废发票是否满足“不为作废并且不为报废”的状态
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
					errorMessage.append(" 发票 ");					
					errorMessage.append(serialNo);
					errorMessage.append(" 不存在\n");

				}else{
				
					status = ((FaPiaoDTO)list.get(0)).getStatus();
	
					if(status.equals(CommonConstDefinition.FAPIAO_STATUS_DIS)) {
						errorFlag=true;
						errorMessage.append(serialNo);
						errorMessage.append(" 已报废 \n");
					}
				}
		}
		if(errorFlag) throw new ServiceException(errorMessage.toString());
	}
	/**
	 * 发票运入:记录数据
	 * 
	 * @param serialsList
	 * @param opID
	 */
	private void faPiaoTransitionForIn(FaPiaoEJBEvent theEvent) throws ServiceException {
		
		//发票是否按册管理 "Y"--是
		int orgID = BusinessUtility.FindOrgIDByOperatorID(theEvent.getOperatorID());
		//ArrayList deviceList = new ArrayList();
		try {
			
			//地址类型|地址ID|状态|领用人|领用人组织
			String addressType = CommonConstDefinition.FAPIAO_ADDRESSTYPE_D;
			int addressid = theEvent.getFaPiaoDTO().getAddressID();
			String status = CommonConstDefinition.FAPIAO_STATUS_SAV;
			int recipientOpID = 0;
			int recipientOrgID = 0;
			String action = CommonConstDefinition.FAPIAOTRANSITION_ACTION_IN;
			
			//立即领用
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
			 * 创建发票册记录
			 */
			
			
			//创建时间
			faVoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			faVoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			//发票册序列号
			faVoDto.setVolumnSn(theEvent.getFapiaoVolumnDTO().getVolumnSn());
			//类型
			faVoDto.setType(theEvent.getFaPiaoDTO().getType());
			
			//操作员
			faVoDto.setCreatorOpID(theEvent.getOperatorID());
			//操作员所属组织机构
			faVoDto.setCreatorOrgID(orgID);
			//状态时间
			faVoDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
			
			//地址类型
			faVoDto.setAddressType(addressType);
			//地址id
			faVoDto.setAddressID(new Integer(addressid).intValue());
			//状态
			faVoDto.setStatus(status);
			//领用人
			faVoDto.setRecepientOpID(recipientOpID);
			//领用人组织
			faVoDto.setRecipientOrgID(recipientOrgID);
			
			faVoHome = HomeLocater.getFapiaoVolumnHome();
			faVo = faVoHome.create(faVoDto);
			faVoDto.setSeqNo(faVo.getSeqNo().intValue());
			}
			/*
			 * 创建流转头记录
			 * 
			 * T_FAPIAOTRANSITION
					SEQNO = FAPIAOTRANSITION_SEQNO.nextval;
					ValumnSeqNO=发票册的系统序列号。
					Total = 总记录数
					OpID = 操作员ID
					OrgID = 操作员所属组织机构
					Action = 运入:IN;立即领用: INOUT
					FileName =如果运入方式是文件，记录文件名。
					Status=’V’;
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
			 * 创建发票记录
			 * 创建流转明细记录:如果是运入后立即出库,一张发票需要记录两条明细
			 */
			Iterator ite = theEvent.getSerailNoList().iterator();
			while (ite.hasNext()) {
				String serial = (String) ite.next();
				FaPiaoDTO faPiaoDto=new FaPiaoDTO();
				
				//类型
				faPiaoDto.setType(theEvent.getFaPiaoDTO().getType());
				//发票字轨号码
				faPiaoDto.setSerailNo(serial);
				//发票册系统序列号
				faPiaoDto.setVolumnSeqno(faVoDto.getSeqNo());
				//操作员ID
				faPiaoDto.setCreatorOpID(theEvent.getOperatorID());
				//操作员所属组织机构
				faPiaoDto.setCreatorOrgID(orgID);
				//状态时间
				faPiaoDto.setStatusTime(TimestampUtility.getCurrentTimestamp());
				//创建时间
				faPiaoDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
				//地址类型|地址ID|状态|领用人|领用人组织
				//地址类型
				faPiaoDto.setAddressType(addressType);
				//地址id
				faPiaoDto.setAddressID(new Integer(addressid).intValue());
				//状态
				faPiaoDto.setStatus(status);
				//领用人
				faPiaoDto.setRecipientOpID(recipientOpID);
				//领用人组织
				faPiaoDto.setRecipientOrgID(recipientOrgID);
				
				faPiaoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
				faPiaoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				//modulename 非空？
				faPiaoDto.setModuleName(" ");
				
				if(theEvent.isDirUse())
					faPiaoDto.setRecipientTime(TimestampUtility.getCurrentTimestamp());
				
				FaPiaoHome faPiaoHome = HomeLocater.getFaPiaoHome();
				FaPiao faPiao = faPiaoHome.create(faPiaoDto);
				faPiaoDto.setSeqNo(faPiao.getSeqNo().intValue());
				
				
				/* 
				 * 记录发票明细信息
				 * SEQNO = FAPIAOTRANSITIONDETAIL_SEQNO.nextval;
					VolumnSeqNO = 发票册序列号
					FapiaoTransSeqNO = 头记录序列号
					FapiaoSeqNo = 发票序列号
					OpID = 操作员ID
					OrgID = 组织机构ID
					Action = 运入:IN;立即领用: INOUT
					ToStatus = 运入:‘SAV’,立即领用:’REP’;--是REC吧？？
					ToAddressType= 如果操作为：运入 AddressType=’D’,如果操作为:运入后立即领用AddressType = ‘O’;
					ToAddressID = 如果操作为：运入 AddressID填写仓库ID，如果操作为:运入后立即领用AddressID填写 组织机构ID

				 */
				
				//运入后立即领用 需要记录两条明细 action都是"inout"
				if(theEvent.isDirUse())
				{
					//先记录运入
					FapiaoTransitionDetailDTO faPiaoTransDetailDto=new FapiaoTransitionDetailDTO();
					faPiaoTransDetailDto.setVolumnSeqNo(faVoDto.getSeqNo()+"");
					faPiaoTransDetailDto.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
					faPiaoTransDetailDto.setFapiaoSeqNo(faPiao.getSeqNo().intValue());
					faPiaoTransDetailDto.setOpID(theEvent.getOperatorID());
					faPiaoTransDetailDto.setOrgID(orgID);
					faPiaoTransDetailDto.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_INOUT);//--这个类型对吗(刘迪确认的)?个人觉得是"in"
					faPiaoTransDetailDto.setToStatus(CommonConstDefinition.FAPIAO_STATUS_SAV);
					faPiaoTransDetailDto.setToAddressID(theEvent.getFaPiaoDTO().getAddressID());
					faPiaoTransDetailDto.setToAddressType(CommonConstDefinition.FAPIAO_ADDRESSTYPE_D);
					faPiaoTransDetailDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
					faPiaoTransDetailDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					//这两个非空？？
					faPiaoTransDetailDto.setFromStatus(" ");
					faPiaoTransDetailDto.setFromAddressType(" ");
					
					FapiaoTransitionDetailHome faPiaoTransDetailHome = HomeLocater.getFapiaoTransitionDetailHome();
					FapiaoTransitionDetail faPiaoTransDetail = faPiaoTransDetailHome.create(faPiaoTransDetailDto);
					faPiaoTransDetailDto.setSeqNo(faPiaoTransDetail.getSeqNo().intValue());
					//再记录领用,从上面运入的仓库中领用
					
					FapiaoTransitionDetailDTO faPiaoTransDetailDto1=new FapiaoTransitionDetailDTO();
					faPiaoTransDetailDto1.setVolumnSeqNo(faVoDto.getSeqNo()+"");
					faPiaoTransDetailDto1.setFapiaoTransSeqNo(faPiaoTrans.getSeqNo().intValue());
					faPiaoTransDetailDto1.setFapiaoSeqNo(faPiao.getSeqNo().intValue());
					faPiaoTransDetailDto1.setOpID(theEvent.getOperatorID());
					faPiaoTransDetailDto1.setOrgID(orgID);
					faPiaoTransDetailDto1.setAction(CommonConstDefinition.FAPIAOTRANSITION_ACTION_INOUT);//--这个类型对吗(刘迪确认的)?个人觉得是"in"
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
				//只运入,只记录一条明细记录
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
					//这两个非空？？
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
			throw new ServiceException("发票序列号定位错误");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} 
	}	
	/*
	 * 检查发票运入时发票册及发票是否与系统中已有发票冲突
	 */
	private void checkValideFaPiaoIn( FaPiaoEJBEvent theEvent) throws HomeFactoryException, FinderException,
			ServiceException {
		//如果是文件运入，需要检查文件中发票序列号本身是否重复
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
				throw new ServiceException("待导入文件中发票序列号["+errStr3.substring(0,errStr3.length()-1)+"]存在重复,请检查!");
			} 
		}
		
		//如果管理发票册 需要检查发票册是否在系统中已存在,管理发票册的要求是一册发票只能一次录入,不能分多次
		
		//发票册重复信息
		if(theEvent.isVolumnManage())
		{
			FapiaoVolumnHome faVoHome = HomeLocater.getFapiaoVolumnHome();
			Collection faVoCol = faVoHome.findByTypeAndVolumnSN(type,volumnSn);
			if(faVoCol != null && !faVoCol.isEmpty()) {
				throw new ServiceException("发票类型为:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",theEvent.getFaPiaoDTO().getType())+",发票册序列号为:"+volumnSn+"的发票册已存在!");
			}
		}
		//发票重复信息
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
				throw new ServiceException("发票类型为:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSn+",发票序列号为["+errStr2.substring(0,errStr2.length()-1)+"]的发票已存在!");
			else
				throw new ServiceException("发票类型为:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票序列号为["+errStr2.substring(0,errStr2.length()-1)+"]的发票已存在!");
		}
		
	}
	
	
	/**
	 * 发票回库:记录数据
	 * 
	 * @param serialsList
	 * @param opID
	 */
	private void faPiaoTransitionForBack(FaPiaoEJBEvent event) throws ServiceException {
		
		//"Y"--按册管理,需要修改发票册信息,否则不需要
		int orgID = BusinessUtility.FindOrgIDByOperatorID(event.getOperatorID());
		String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
		String type = event.getFaPiaoDTO().getType();

		int depotID = event.getFaPiaoDTO().getAddressID();
		String fileName = event.getFileName();
		try {

			/*
			 * 创建流转头记录
			 * 
			 * T_FAPIAOTRANSITION
				SEQNO = FAPIAOTRANSITION_SEQNO.nextval;
				FileName =文件名
				Total = 总记录数
				OpID = 操作员ID
				OrgID = 操作员所属组织机构
				Action =  ‘BACK
				Status=’V’;
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
			
			//供修改发票册用
			String faPiaoSeqNoStr = "";
			//回库涉及到的发票册
			Set faPiaoVo = new HashSet();
			/*
			 * 创建流转明细记录
			 * 修改发票记录
			 * 
			 */
			Iterator ite = event.getSerailNoList().iterator();
			while (ite.hasNext()) {
				/*
				 * T_FAPIAOTRANSITIONDETAIL
				SEQNO = FAPIAOTRANSITIONDETAIL_SEQNO.nextval;
				VolumnSeqNO = 发票册序列号
				FapiaoTransSeqNO = 头记录序列号
				FapiaoSeqNo = 发票序列号
				OpID = 操作员ID
				OrgID = 组织机构ID
				Action =  ‘BACK
				FromStatus = ‘REC’
				ToStatus =’SAV;
				FromAddressType= 原发票地址类型;
				FromAddressID = 原发票地址ID；
				ToAddressType =  ‘D’;
				ToAddressID =仓库ID
				 */
				String serial = (String) ite.next();
				//得到发票
				List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, volumnSN, serial);
				if(faPiaoList == null)
					throw new ServiceException("发票定位错误");
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
				 * 修改发票记录
				 * T_FAPIAO
					Status = ‘SAV’
					StatusTime = SysDate;
					AddressType = ‘D’
					AddressID =仓库ID
					DT_LASTMOD = SysTimeStamp;

				 */
				FaPiaoHome faPiaoHome = HomeLocater.getFaPiaoHome();
				FaPiao theFaPiao = faPiaoHome.findByPrimaryKey(new Integer(theDto.getSeqNo()));
				theFaPiao.setStatus(faPiaoTransDetailDto.getToStatus());
				theFaPiao.setStatusTime(TimestampUtility.getCurrentTimestamp());
				theFaPiao.setAddressType(faPiaoTransDetailDto.getToAddressType());
				theFaPiao.setAddressID(faPiaoTransDetailDto.getToAddressID());
				theFaPiao.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				
//				回库需要清空这三个字段
				theFaPiao.setRecipientOpID(0);
				theFaPiao.setRecipientOrgID(0);
				theFaPiao.setRecipientTime(null);
			}
			if(event.isVolumnManage())
			{
			    /*
				 * 修改发票册记录
				 * 如果发票所属发票册全部被回库到指定库，发票册地址类型更改为‘D’,地址ID更改为仓库ID
				 * 如果发票不属于同一个发票册，那可能更改多条记录.
				*/
				//判断在回库范围内的发票对应的发票册的所有发票是否都回库,是则返回true
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
			throw new ServiceException("发票序列号定位错误!");
		}
		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("发票查找错误!");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} 
	}	
	/*
	 * 发票回库检查
	 * 
	 * 检查发票回库时检查待回库的发票是否满足回库条件
	 */
	private void checkValideFaPiaoBack(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException,
			ServiceException {
		
		//类型
		String type = event.getFaPiaoDTO().getType();
		ArrayList serailNoList = new ArrayList();
		//启用发票册管理,需要从发票册中得到发票序列号,以List的方式存储
		if(event.isVolumnManage())
		{
			String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
			FapiaoVolumn faVo = BusinessUtility.getFapiaoVolumn(type, volumnSN);
			if(faVo == null) throw new ServiceException("发票类型为"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册不存在!");
			//发票册状态检查:发票册状态应为已领取  有必要??--按册领用要检查
			if(!CommonConstDefinition.FAPIAO_STATUS_REC.equals(faVo.getStatus()))
				throw new ServiceException("发票类型为"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册状态不是已领取,不能回库!");
			
			
			
			List faDtoList = BusinessUtility.getFaPiaoDtoByCon(type,volumnSN,null);
			if(faDtoList==null || faDtoList.isEmpty())
				throw new ServiceException("发票类型为"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册中没有需要回库的发票!");
			for(int i=0;i<faDtoList.size();i++)
			{
				FaPiaoDTO faDto = (FaPiaoDTO)faDtoList.get(i);
				serailNoList.add(faDto.getSerailNo());
			}
			event.setSerailNoList(serailNoList);
		}
		
		List serialsList = event.getSerailNoList();
		
		//如果是文件运入，需要检查文件中发票序列号本身是否重复
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
				throw new ServiceException("待导入文件中发票序列号["+errStr3.substring(0,errStr3.length()-1)+"]存在重复,请检查!");
			} 
		}
		
		
		/*
		 * 待回库的发票必须存在且为已领取
		 * 
		 */
		
		//定义存在性错误
		StringBuffer errBuff1 = new StringBuffer();
		//定义状态错误
		StringBuffer errBuff2 = new StringBuffer();
		for (int i=0;i<serialsList.size();i++) {
			String serialNo = (String) serialsList.get(i);
			//根据类型,发票册序列号,发票序列号查询发票
			List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, event.getFapiaoVolumnDTO().getVolumnSn(), serialNo);
			if(faPiaoList == null || faPiaoList.isEmpty())
			{
				errBuff1.append(serialNo+",");
				continue;
			}
			else
			{
				//理论上只可能有一个结果
				FaPiaoDTO theFaPiao = (FaPiaoDTO) faPiaoList.get(0);
				if(!CommonConstDefinition.FAPIAO_STATUS_REC.equals(theFaPiao.getStatus()))
				{
					errBuff2.append(serialNo+",");
				}
				
			}
		}
		String errStr1 = errBuff1.toString();
		
		String tempStr="发票类型为:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type);
		if(event.isVolumnManage())
			tempStr = tempStr+",发票册序列号为:"+event.getFapiaoVolumnDTO().getVolumnSn();
		if(errStr1 != null && !"".equals(errStr1))
		{
			
			throw new ServiceException(tempStr+",发票序列号为["+errStr1.substring(0,errStr1.length()-1)+"]的发票不存在,无法回库!");
		}
		
	    String errStr2 = errBuff2.toString();
		if(errStr2 != null && !"".equals(errStr2))
		{
			
			throw new ServiceException(tempStr+",发票序列号为["+errStr2.substring(0,errStr2.length()-1)+"]的发票状态不是已领取,不能回库!");
		}
		
	}

	/**
	 *  发票回库 查询
	 *  根据条件查询发票序列号
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
	 * 发票调拨检查
	 * 
	 * 1 发票必须都在库存中
	 * 
	 */
	private void checkValideFaPiaoMove(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException,
			ServiceException {
		
		
		//类型
		String type = event.getFaPiaoDTO().getType();
		ArrayList serailNoList = new ArrayList();
		//启用发票册管理,需要从发票册中得到发票序列号,以List的方式存储
		if(event.isVolumnManage())
		{
			String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
			FapiaoVolumn faVo = BusinessUtility.getFapiaoVolumn(type, volumnSN);
			if(faVo == null) throw new ServiceException("发票类型为:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册不存在!");
			//发票册状态检查:发票册状态应为库存  有必要??--按册领用要检查
			if(!CommonConstDefinition.FAPIAO_STATUS_SAV.equals(faVo.getStatus()))
				throw new ServiceException("发票类型为"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册状态不是库存,不能调拨!");
			
			
			//目标仓库与原仓库一致
			if("D".equals(faVo.getAddressType()) && (faVo.getAddressID()==event.getFaPiaoDTO().getAddressID()))
				throw new ServiceException("目标仓库与原仓库一致,不需要调拨!");
			
			List faDtoList = BusinessUtility.getFaPiaoDtoByCon(type,volumnSN,null);
			if(faDtoList==null || faDtoList.isEmpty())
				throw new ServiceException("发票类型为:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册中没有发票!");
			for(int i=0;i<faDtoList.size();i++)
			{
				FaPiaoDTO faDto = (FaPiaoDTO)faDtoList.get(i);
				serailNoList.add(faDto.getSerailNo());
			}
			event.setSerailNoList(serailNoList);
			//LogUtility.log(clazz, LogLevel.DEBUG, "___4___="+serialsList);
		}
		
		List serialsList = event.getSerailNoList(); 
		
		//如果是文件运入，需要检查文件中发票序列号本身是否重复
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
				throw new ServiceException("待导入文件中发票序列号["+errStr3.substring(0,errStr3.length()-1)+"]存在重复,请检查.");
			} 
		}
		
		
		/*
		 * 1 待调拨的发票必须存在且为库存
		 * 2 待调拨的发票仓库与目标仓库不能一致
		 */
		
		//定义存在性错误
		StringBuffer errBuff1 = new StringBuffer();
		//定义状态错误
		StringBuffer errBuff2 = new StringBuffer();
		
		//定义调拨源和目标一致错误
		StringBuffer errBuff5 = new StringBuffer();

		for (int i=0;i<serialsList.size();i++) {
			String serialNo = (String) serialsList.get(i);
			//根据类型,发票册序列号,发票序列号查询发票
			List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, event.getFapiaoVolumnDTO().getVolumnSn(), serialNo);
			
			if(faPiaoList == null || faPiaoList.isEmpty())
			{
				errBuff1.append(serialNo+",");
				continue;
			}
			else
			{
				//理论上只可能有一个结果
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
		String tempStr="发票类型为:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type);
		if(event.isVolumnManage())
			tempStr = tempStr+",发票册序列号为:"+event.getFapiaoVolumnDTO().getVolumnSn();
		
		if(errStr1 != null && !"".equals(errStr1))
		{
			throw new ServiceException(tempStr+",发票序列号为["+errStr1.substring(0,errStr1.length()-1)+"]的发票不存在,无法调拨!");
		}
		
	    String errStr2 = errBuff2.toString();
		if(errStr2 != null && !"".equals(errStr2))
		{
			throw new ServiceException(tempStr+",发票序列号为["+errStr2.substring(0,errStr2.length()-1)+"]的发票状态不是库存,不能调拨!");
		}
		
		 String errStr5 = errBuff5.toString();
		if(errStr5 != null && !"".equals(errStr5))
		{
			throw new ServiceException(tempStr+",发票序列号为["+errStr5.substring(0,errStr5.length()-1)+"]的发票地址与调拨到仓库一致,不需调拨!");
		}
	}
	/**
	 * 发票调拨:记录数据
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
			 * 创建流转头记录
			 * 
			 * T_FAPIAOTRANSITION
				SEQNO = FAPIAOTRANSITION_SEQNO.nextval;
				FileName =文件名
				Total = 总记录数
				OpID = 操作员ID
				OrgID = 操作员所属组织机构
				Action =  ‘CANN’
				Status=’V’;
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
			
			//供修改发票册用
			String faPiaoSeqNoStr = "";
			//回库涉及到的发票册
			Set faPiaoVo = new HashSet();
			/*
			 * 创建流转明细记录
			 * 修改发票记录
			 * 
			 */
			Iterator ite = event.getSerailNoList().iterator();
			while (ite.hasNext()) {
				/*
				 * T_FAPIAOTRANSITIONDETAIL
					SEQNO = FAPIAOTRANSITIONDETAIL_SEQNO.nextval;
					VolumnSeqNO = 发票册序列号
					FapiaoTransSeqNO = 头记录序列号
					FapiaoSeqNo = 发票序列号
					OpID = 操作员ID
					OrgID = 组织机构ID
					Action =  ‘CANN
					FromStatus = ‘SAV’
					ToStatus =’SAV;
					FromAddressType= 原发票地址类型;
					FromAddressID = 原发票地址ID；
					ToAddressType =目标发票地址类型;;
					ToAddressID = 仓库ID
				 */
				String serial = (String) ite.next();
				//得到发票
				List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, volumnSN, serial);
				if(faPiaoList == null)
					throw new ServiceException("发票定位错误");
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
				 * 修改发票记录
				 * T_FAPIAO
					Status = ‘SAV’
					StatusTime = SysDate;
					AddressType = ‘D’
					AddressID =仓库ID
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
			//只有按册管理时才会涉及发票册修改,否则没有发票册的概念
			if(event.isVolumnManage())
			{
			    /*
				 * 修改发票册记录
				 * 如果发票所属发票册全部被调拨，发票册地址类型更改为‘D’,地址ID更改为仓库ID
				 * 如果发票不属于同一个发票册，那可能更改多条记录.
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
			throw new ServiceException("发票序列号定位错误!");
		}
		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("发票查找错误!");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} 
	}	
	
	/*
	 * 发票领用检查
	 * 
	 * 
	 */
	private void checkValideFaPiaoUse(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException,
			ServiceException {
		
		//类型
		String type = event.getFaPiaoDTO().getType();
		ArrayList serailNoList = new ArrayList();
		//启用发票册管理,需要从发票册中得到发票序列号,以List的方式存储
		if(event.isVolumnManage())
		{
			String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
			FapiaoVolumn faVo = BusinessUtility.getFapiaoVolumn(type, volumnSN);
			if(faVo == null) throw new ServiceException("发票类型为"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册不存在!");
			//发票册状态检查:发票册状态应为库存  有必要??--按册领用要检查
			if(!CommonConstDefinition.FAPIAO_STATUS_SAV.equals(faVo.getStatus()))
				throw new ServiceException("发票类型为"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册状态不是库存,不能领用!");
			
			
			List faDtoList = BusinessUtility.getFaPiaoDtoByCon(type,volumnSN,null);
			if(faDtoList==null || faDtoList.isEmpty())
				throw new ServiceException("发票类型为"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type)+",发票册序列号为:"+volumnSN+"的发票册中没有可领用的发票!");
			for(int i=0;i<faDtoList.size();i++)
			{
				FaPiaoDTO faDto = (FaPiaoDTO)faDtoList.get(i);
				serailNoList.add(faDto.getSerailNo());
			}
			event.setSerailNoList(serailNoList);
		}
		

		
		List serialsList = event.getSerailNoList();
		
		//如果是文件运入，需要检查文件中发票序列号本身是否重复
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
				throw new ServiceException("待导入文件中发票序列号["+errStr3.substring(0,errStr3.length()-1)+"]存在重复,请检查.");
			} 
		}
		
		
		/*
		 * 
		 * 可领用的发票必须存在且为库存
		 *
		 */
		
		if(event.isVolumnManage())
		{
			
		}
		//定义存在性错误
		StringBuffer errBuff1 = new StringBuffer();
		//定义状态错误
		StringBuffer errBuff2 = new StringBuffer();
		for (int i=0;i<serialsList.size();i++) {
			String serialNo = (String) serialsList.get(i);
			//根据类型,发票册序列号,发票序列号查询发票
			List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, event.getFapiaoVolumnDTO().getVolumnSn(), serialNo);
			if(faPiaoList == null || faPiaoList.isEmpty())
			{
				errBuff1.append(serialNo+",");
				continue;
			}
			else
			{
				//理论上只可能有一个结果
				FaPiaoDTO theFaPiao = (FaPiaoDTO) faPiaoList.get(0);
				if(!CommonConstDefinition.FAPIAO_STATUS_SAV.equals(theFaPiao.getStatus()))
				{
					errBuff2.append(serialNo+",");
				}
				
			}
		}
		String errStr1 = errBuff1.toString();
		
		String tempStr="发票类型为:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",type);
		if(event.isVolumnManage())
			tempStr = tempStr+",发票册序列号为:"+event.getFapiaoVolumnDTO().getVolumnSn();
		if(errStr1 != null && !"".equals(errStr1))
		{
			
			throw new ServiceException(tempStr+",发票序列号为["+errStr1.substring(0,errStr1.length()-1)+"]的发票不存在,无法领取!");
		}
		
	    String errStr2 = errBuff2.toString();
		if(errStr2 != null && !"".equals(errStr2))
		{
			
			throw new ServiceException(tempStr+",发票序列号为["+errStr2.substring(0,errStr2.length()-1)+"]的发票状态不是库存,不能领取.");
		}
		
	}
	/**
	 * 发票领用:记录数据
	 * 
	 * @param serialsList
	 * @param opID
	 */
	private void faPiaoTransitionForUse(FaPiaoEJBEvent event) throws ServiceException {
		
		//"Y"--按册管理,需要修改发票册信息,否则不需要
		int orgID = BusinessUtility.FindOrgIDByOperatorID(event.getOperatorID());
		String volumnSN = event.getFapiaoVolumnDTO().getVolumnSn();
		String type = event.getFaPiaoDTO().getType();

		int toAddressID = event.getFaPiaoDTO().getAddressID();
		String fileName = event.getFileName();
		try {

			/*
			 * 创建流转头记录
			 * 
			 * T_FAPIAOTRANSITION
			 * SEQNO = FAPIAOTRANSITION_SEQNO.nextval;
			   FileName =文件名
			   Total = 总记录数
			   OpID = 操作员ID
			   OrgID = 操作员所属组织机构
			   Action =  ‘OUT’
			   Status=’V’;
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
			
			//供修改发票册用
			String faPiaoSeqNoStr = "";
			//回库涉及到的发票册
			Set faPiaoVo = new HashSet();
			/*
			 * 创建流转明细记录
			 * 修改发票记录
			 * 
			 */
			Iterator ite = event.getSerailNoList().iterator();
			while (ite.hasNext()) {
				/*
				 * T_FAPIAOTRANSITIONDETAIL
				SEQNO = FAPIAOTRANSITIONDETAIL_SEQNO.nextval;
				VolumnSeqNO = 发票册序列号
				FapiaoTransSeqNO = 头记录序列号
				FapiaoSeqNo = 发票序列号
				OpID = 操作员ID
				OrgID = 组织机构ID
				Action =  ‘OUT
				FromStatus = ‘SAV’
				ToStatus =’REC;
				FromAddressType= 原发票地址类型;
				FromAddressID = 原发票地址ID；
				ToAddressType =  ‘O’;
				ToAddressID =组织机构ID
				 */
				String serial = (String) ite.next();
				//得到发票
				List faPiaoList = BusinessUtility.getFaPiaoDtoByCon(type, volumnSN, serial);
				if(faPiaoList == null)
					throw new ServiceException("发票定位错误");
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
				 * 修改发票记录
				 * T_FAPIAO	
					RecipientOpID = 操作员ID。
					RecipientOrgID = 操作员所属组织机构。
					RecipientTime = SysDate。
					STATUS = ‘REC’
					StatusTime = SysDate;
					AddressType = ‘O’
					AddressID =组织机构ID
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
				 * 修改发票册记录
				 * 如果发票所属发票册全部被领用，发票册地址类型更改为‘O’,地址ID更改为组织机构ID
					如果发票不属于同一个发票册，那可能更改多条记录

				*/
				//判断在领用范围内的发票对应的发票册的所有发票是否都领用到指定的组织机构,是则返回true
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
			throw new ServiceException("发票序列号定位错误!");
		}
		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("发票查找错误!");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
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
