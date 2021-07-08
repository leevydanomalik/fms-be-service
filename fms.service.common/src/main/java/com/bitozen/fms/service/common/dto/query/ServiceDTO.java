package com.bitozen.fms.service.common.dto.query;

import java.io.Serializable;
import java.util.Date;

import com.bitozen.fms.common.dto.AggregateStatusDTO;
import com.bitozen.fms.common.dto.GenericAccessTokenDTO;
import com.bitozen.fms.service.common.MetadataDTO;
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
public class ServiceDTO implements Serializable {

	private String svcID;
	private String svcName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date svcDate;
	private MetadataDTO svcMetadata;
	private GenericAccessTokenDTO svcToken;
	private AggregateStatusDTO svcWorkProgress;
	
	private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT+7")
    private Date createdDate;
    private String updatedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT+7")
    private Date updatedDate;
    private String recordID;
}
