/*
 * Created on 2005-11-9
 *
 * @author Mac Wang
 * 
 * 该类作为CustomerProblemDTO和CustProblemProcessDTO的包装类
 */
package com.dtv.oss.dto.wrap.work;

import com.dtv.oss.dto.*;

public class CustomerProblem2CPProcessWrap implements java.io.Serializable {
	private CustomerProblemDTO cpDto;
	private CustProblemProcessDTO cppDto;
	
	
	public CustomerProblem2CPProcessWrap() {
		this.cpDto = new CustomerProblemDTO();
		this.cppDto = new CustProblemProcessDTO();
	}
	
	public CustomerProblem2CPProcessWrap(CustomerProblemDTO cpDto,
            CustProblemProcessDTO cppDto) {
        this.cpDto = cpDto;
        this.cppDto = cppDto;
    }
    public CustomerProblemDTO getCpDto() {
        return cpDto;
    }
    public void setCpDto(CustomerProblemDTO cpDto) {
        this.cpDto = cpDto;
    }
    public CustProblemProcessDTO getCppDto() {
        return cppDto;
    }
    public void setCppDto(CustProblemProcessDTO cppDto) {
        this.cppDto = cppDto;
    }
}
