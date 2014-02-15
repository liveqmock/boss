package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import com.dtv.oss.dto.VOIPProductDTO;

public interface VOIPProductHome extends EJBLocalHome{
	public VOIPProduct create(int productid,String sssrvCode)throws CreateException;
	public VOIPProduct create(VOIPProductDTO dto)throws CreateException;
	public VOIPProduct findByPrimaryKey(VOIPProductPK pk)throws FinderException;
}
