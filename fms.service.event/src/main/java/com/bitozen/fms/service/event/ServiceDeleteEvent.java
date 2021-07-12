package com.bitozen.fms.service.event;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServiceDeleteEvent {

	private String svcID;
	
	private String updatedBy;
}
