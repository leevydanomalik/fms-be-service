package com.bitozen.fms.service.projection;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;

import org.hibernate.annotations.Type;

@Entity
public class ServiceEntryProjection extends ServiceTransactionBaseObject implements Serializable{

	private String svcID;
	private String svcName;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date svcDate;
	@Type(type = "text")
	private String svcMetadata;
	@Type(type = "text")
	private String svcToken;
	@Type(type = "text")
	private String svcWorkProgress;
	
	public ServiceEntryProjection() {
		
	}

	public ServiceEntryProjection(String svcID, String svcName, Date svcDate, String svcMetadata, String svcToken,
			String svcWorkProgress, String createdBy, ZonedDateTime createdDate, String updatedBy,
			ZonedDateTime updatedDate, String recordID) {
		super(createdBy, createdDate, updatedBy, updatedDate, recordID);
		this.svcID = svcID;
		this.svcName = svcName;
		this.svcDate = svcDate;
		this.svcMetadata = svcMetadata;
		this.svcToken = svcToken;
		this.svcWorkProgress = svcWorkProgress;
	}

	public String getSvcID() {
		return svcID;
	}

	public void setSvcID(String svcID) {
		this.svcID = svcID;
	}

	public String getSvcName() {
		return svcName;
	}

	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}

	public Date getSvcDate() {
		return svcDate;
	}

	public void setSvcDate(Date svcDate) {
		this.svcDate = svcDate;
	}

	public String getSvcMetadata() {
		return svcMetadata;
	}

	public void setSvcMetadata(String svcMetadata) {
		this.svcMetadata = svcMetadata;
	}

	public String getSvcToken() {
		return svcToken;
	}

	public void setSvcToken(String svcToken) {
		this.svcToken = svcToken;
	}

	public String getSvcWorkProgress() {
		return svcWorkProgress;
	}

	public void setSvcWorkProgress(String svcWorkProgress) {
		this.svcWorkProgress = svcWorkProgress;
	}
}
