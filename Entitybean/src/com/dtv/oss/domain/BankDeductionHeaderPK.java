package com.dtv.oss.domain;

import java.io.Serializable;

public class BankDeductionHeaderPK implements Serializable {

	public int mopId;

	public int batchNo;

	public BankDeductionHeaderPK() {
	}

	public BankDeductionHeaderPK(int mopId, int batchNo) {
		this.mopId = mopId;
		this.batchNo = batchNo;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				BankDeductionHeaderPK that = (BankDeductionHeaderPK) obj;
				return this.mopId == that.mopId
						&& this.batchNo == that.batchNo;
			}
		}
		return false;
	}

	public int hashCode() {
		return mopId | batchNo;
	}
}