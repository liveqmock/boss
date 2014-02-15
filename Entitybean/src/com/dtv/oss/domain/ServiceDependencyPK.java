package com.dtv.oss.domain;

import java.io.Serializable;

public class ServiceDependencyPK implements Serializable {

	public int serviceId;

	public int referServiceId;

	public ServiceDependencyPK() {
	}

	public ServiceDependencyPK(int serviceId, int referServiceId) {
		this.serviceId = serviceId;
		this.referServiceId = referServiceId;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				ServiceDependencyPK that = (ServiceDependencyPK) obj;
				return this.serviceId == that.serviceId
						&& this.referServiceId == that.referServiceId;
			}
		}
		return false;
	}

	public int hashCode() {
		return serviceId | referServiceId;
	}
}