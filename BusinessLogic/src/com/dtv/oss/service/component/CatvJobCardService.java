/*
 * Created on 2007-7-24
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.component;

import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.component.JobCardService;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CatvJobCardService extends JobCardService {
	public static final Class clazz = CatvJobCardService.class;
	public CatvJobCardService(ServiceContext context){
		super(context);
	}
	public CatvJobCardService(int jobCardNo, ServiceContext context) {
		super(jobCardNo, context);
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.component.JobCardService#setJobCardType(com.dtv.oss.dto.JobCardDTO)
	 */
	public void setJobCardType(JobCardDTO jcDto) throws ServiceException {
		jcDto.setJobType(CommonConstDefinition.JOBCARD_TYPE_CATV);  

	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.component.JobCardService#relateWithOriginalSheet(java.lang.Object)
	 */
	public void relateWithOriginalSheet(Object cps) throws ServiceException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.component.JobCardService#getOriginalSheet()
	 */
	public Object getOriginalSheet() throws ServiceException {
		load();
		return new Integer(jc.getReferSheetId());
	}
}
