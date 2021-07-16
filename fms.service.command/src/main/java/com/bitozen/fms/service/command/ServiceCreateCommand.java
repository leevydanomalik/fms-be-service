package com.bitozen.fms.service.command;

import java.util.Date;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class ServiceCreateCommand {

	@TargetAggregateIdentifier
	private String svcID;
	private String svcName;
	private Date svcDate;
	private String svcMetadata;
	private String svcToken;
	private String svcWorkProgress;
	
	private String createdBy;
	private Date createdDate;
    private String recordID;
}
