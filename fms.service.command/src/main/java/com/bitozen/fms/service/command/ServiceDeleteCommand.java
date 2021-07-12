package com.bitozen.fms.service.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class ServiceDeleteCommand {

	@TargetAggregateIdentifier
	private String svcID;
	
	private String updatedBy;
}
