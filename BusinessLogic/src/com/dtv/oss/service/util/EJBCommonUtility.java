/*
 * Created on 2005-9-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.util;
import com.dtv.oss.domain.*;
import com.dtv.oss.dto.*;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.dtv.oss.service.factory.*;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EJBCommonUtility {

	/**
	 * 
	 */
	public EJBCommonUtility() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static Customer getCustomerByID(int id) throws HomeFactoryException, FinderException {
		CustomerHome home = HomeLocater.getCustomerHome();
		Customer localobject = home.findByPrimaryKey(new Integer(id));
		return localobject;
	}
	public static Operator getOperatorByID(int id) throws HomeFactoryException, FinderException {
		OperatorHome home = HomeLocater.getOperatorHome();
		Operator localobject = home.findByPrimaryKey(new Integer(id));
		return localobject;
	}
	
	public static SystemLog createSystemLog(SystemLogDTO dto) throws HomeFactoryException, CreateException {
		SystemLogHome home = HomeLocater.getSystemLogHome();
		return home.create(dto);
	}
	
	/** add by david.Yang
	 */
	public static Organization getOrganizationByID(int orgid) throws HomeFactoryException, FinderException {
	   OrganizationHome home =HomeLocater.getOrganizationHome();
	   Organization localobject =home.findByPrimaryKey(new Integer(orgid));
	   return localobject;
    }
	
}
