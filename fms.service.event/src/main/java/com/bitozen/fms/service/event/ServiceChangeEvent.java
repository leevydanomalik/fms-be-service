package com.bitozen.fms.service.event;

import java.util.Date;

import lombok.Value;

@Value
public class ServiceChangeEvent {

	private String svcID;
	private String svcName;
	private Date svcDate;
	private String svcMetadata;
	private String svcToken;
	private String svcWorkProgress;
	
	private String updatedBy;
	private Date updatedDate;
}
