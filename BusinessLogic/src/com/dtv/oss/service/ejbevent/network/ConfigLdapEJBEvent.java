package com.dtv.oss.service.ejbevent.network;

import com.dtv.oss.dto.LdapAttrConfigDTO;
import com.dtv.oss.dto.LdapConditionDTO;
import com.dtv.oss.dto.LdapEventCmdMapDTO;
import com.dtv.oss.dto.LdapHostDTO;
import com.dtv.oss.dto.LdapProdToSmsProdDTO;
import com.dtv.oss.dto.LdapProductDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;

public class ConfigLdapEJBEvent extends CsrAbstractEJbevent {

      private LdapHostDTO ldapHostDto;
    
      private LdapProductDTO ldapProductDto; 
      
      private LdapEventCmdMapDTO ldapEventCmdMapDto;
      
      private LdapAttrConfigDTO attrDto;
      
      private LdapConditionDTO condDto;
      
      
      
     
      
	public LdapConditionDTO getCondDto() {
		return condDto;
	}
	public void setCondDto(LdapConditionDTO condDto) {
		this.condDto = condDto;
	}
	/**
	 * @return Returns the ldapProdToSmsProdDto.
	 */
	public LdapProdToSmsProdDTO getLdapProdToSmsProdDto() {
		return ldapProdToSmsProdDto;
	}
	/**
	 * @param ldapProdToSmsProdDto The ldapProdToSmsProdDto to set.
	 */
	public void setLdapProdToSmsProdDto(
			LdapProdToSmsProdDTO ldapProdToSmsProdDto) {
		this.ldapProdToSmsProdDto = ldapProdToSmsProdDto;
	}
      private LdapProdToSmsProdDTO ldapProdToSmsProdDto;
  
	/**
	 * @return Returns the ldapProductDto.
	 */
	public LdapProductDTO getLdapProductDto() {
		return ldapProductDto;
	}
	/**
	 * @param ldapProductDto The ldapProductDto to set.
	 */
	public void setLdapProductDto(LdapProductDTO ldapProductDto) {
		this.ldapProductDto = ldapProductDto;
	}
	/**
	 * @return Returns the ldapHostDto.
	 */
	public LdapHostDTO getLdapHostDto() {
		return ldapHostDto;
	}
	/**
	 * @param ldapHostDto The ldapHostDto to set.
	 */
	public void setLdapHostDto(LdapHostDTO ldapHostDto) {
		this.ldapHostDto = ldapHostDto;
	}
	/**
	 * @return Returns the ldapEventCmdMapDto.
	 */
	public LdapEventCmdMapDTO getLdapEventCmdMapDto() {
		return ldapEventCmdMapDto;
	}
	/**
	 * @param ldapEventCmdMapDto The ldapEventCmdMapDto to set.
	 */
	public void setLdapEventCmdMapDto(LdapEventCmdMapDTO ldapEventCmdMapDto) {
		this.ldapEventCmdMapDto = ldapEventCmdMapDto;
	}
	/**
	 * @return Returns the attrDto.
	 */
	public LdapAttrConfigDTO getAttrDto() {
		return attrDto;
	}
	/**
	 * @param attrDto The attrDto to set.
	 */
	public void setAttrDto(LdapAttrConfigDTO attrDto) {
		this.attrDto = attrDto;
	}
	}