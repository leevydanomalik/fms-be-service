package com.bitozen.fms.service.projection;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Entity;

@Entity
public class Sample extends TransactionBaseObject implements Serializable {

	private String sampleID;
	
	public Sample() {
		
	}

	public Sample(String sampleID) {
		super();
		this.sampleID = sampleID;
	}

	public Sample(String sampleID, String createdBy, ZonedDateTime createdDate, String updatedBy, ZonedDateTime updatedDate,
			String recordID) {
		super(createdBy, createdDate, updatedBy, updatedDate, recordID);
		this.sampleID = sampleID;
	}

	public String getSampleID() {
		return sampleID;
	}

	public void setSampleID(String sampleID) {
		this.sampleID = sampleID;
	}
}
