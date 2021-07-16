package com.bitozen.fms.service.projection;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bitozen.fms.common.dto.AggregateStatusDTO;
import com.bitozen.fms.common.dto.CreationalSpecificationDTO;
import com.bitozen.fms.common.dto.GenericAccessTokenDTO;
import com.bitozen.fms.service.common.MetadataDTO;

@Document(value = "services")
public class ServiceEntryProjection {

	 @Id
	 public Long id;
	 public String svcID;
	 public String svcName;
	 public Date svcDate;
	 private MetadataDTO svcMetadata;
	 private GenericAccessTokenDTO svcToken;
	 private AggregateStatusDTO svcWorkProgress;
	 private CreationalSpecificationDTO creational;
	 private String recordID;
	 
	 public ServiceEntryProjection() {
		 
	 }

	public ServiceEntryProjection(@NotNull Long id, @NotNull String svcID, @NotNull String svcName, @NotNull Date svcDate, MetadataDTO svcMetadata,
			GenericAccessTokenDTO svcToken, AggregateStatusDTO svcWorkProgress,
			CreationalSpecificationDTO creational, String recordID) {
		this.id = id;
		this.svcID = svcID;
		this.svcName = svcName;
		this.svcDate = svcDate;
		this.svcMetadata = svcMetadata;
		this.svcToken = svcToken;
		this.svcWorkProgress = svcWorkProgress;
		this.creational = creational;
		this.recordID = recordID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public MetadataDTO getSvcMetadata() {
		return svcMetadata;
	}

	public void setSvcMetadata(MetadataDTO svcMetadata) {
		this.svcMetadata = svcMetadata;
	}

	public GenericAccessTokenDTO getSvcToken() {
		return svcToken;
	}

	public void setSvcToken(GenericAccessTokenDTO svcToken) {
		this.svcToken = svcToken;
	}

	public AggregateStatusDTO getSvcWorkProgress() {
		return svcWorkProgress;
	}

	public void setSvcWorkProgress(AggregateStatusDTO svcWorkProgress) {
		this.svcWorkProgress = svcWorkProgress;
	}

	public CreationalSpecificationDTO getCreational() {
		return creational;
	}

	public void setCreational(CreationalSpecificationDTO creational) {
		this.creational = creational;
	}

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}
}
