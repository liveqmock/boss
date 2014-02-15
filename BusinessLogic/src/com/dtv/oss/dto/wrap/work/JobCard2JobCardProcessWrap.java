/*
 * Created on 2004-8-16
 *
 * @author Mac Wang
 */
package com.dtv.oss.dto.wrap.work;

import com.dtv.oss.dto.*;

public class JobCard2JobCardProcessWrap implements java.io.Serializable {
	private JobCardDTO jobCardDto;
	private JobCardProcessDTO jobCardProcessDto;
	
	
	public JobCard2JobCardProcessWrap() {
		this.jobCardDto = new JobCardDTO();
		this.jobCardProcessDto = new JobCardProcessDTO();
	}
	
	public JobCard2JobCardProcessWrap(JobCardDTO jobCardDto, JobCardProcessDTO jobCardProcessDto) {
		this.jobCardDto = jobCardDto;
		this.jobCardProcessDto = jobCardProcessDto;
	}
	
	public JobCardDTO getJobCardDto() {
		return jobCardDto;
	}
	public void setJobCardDto(JobCardDTO jobCardDto) {
		this.jobCardDto = jobCardDto;
	}
	public JobCardProcessDTO getJobCardProcessDto() {
		return jobCardProcessDto;
	}
	public void setJobCardProcessDto(JobCardProcessDTO jobCardProcessDto) {
		this.jobCardProcessDto = jobCardProcessDto;
	}
}
