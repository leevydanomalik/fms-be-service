package com.bitozen.fms.service.common.dto.command;

import java.io.Serializable;
import java.util.Date;

import com.bitozen.fms.common.dto.AggregateStatusCreateDTO;
import com.bitozen.fms.common.dto.GenericAccessTokenDTO;
import com.bitozen.fms.service.common.MetadataCreateDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceChangeCommandDTO implements Serializable{

	private String svcID;
	private String svcName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date svcDate;
	private MetadataCreateDTO svcMetadata;
	private GenericAccessTokenDTO svcToken;
	private AggregateStatusCreateDTO svcWorkProgress;
	
	private String updatedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT+7")
    private Date updatedDate;
}
