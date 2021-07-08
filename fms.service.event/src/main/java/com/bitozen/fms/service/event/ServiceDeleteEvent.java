package com.bitozen.fms.service.event;

import java.util.Date;

import lombok.Value;

@Value
public class ServiceDeleteEvent {

	private String svcID;
	
	private String updatedBy;
}
