package com.bitozen.fms.service.event;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServiceCreateEvent {

	private String svcID;
	private String svcName;
	private Date svcDate;
	private String svcMetadata;
	private String svcToken;
	private String svcWorkProgress;
	
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
    private String recordID;
}
