package com.bitozen.fms.service.common.dto.command;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.bitozen.fms.common.dto.AggregateStatusCreateDTO;
import com.bitozen.fms.common.dto.GenericAccessTokenDTO;
import com.bitozen.fms.service.common.MetadataCreateDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class ServiceCreateCommandDTO implements Serializable{

	private String svcID;
	private String svcName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date svcDate;
	private MetadataCreateDTO svcMetadata;
	private GenericAccessTokenDTO svcToken;
	private AggregateStatusCreateDTO svcWorkProgress;
	
	private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT+7")
    private Date createdDate;
    private String updatedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT+7")
    private Date updatedDate;
    private String recordID;
    
    @JsonIgnore
    public ServiceCreateCommandDTO getInstance() {
    	return new ServiceCreateCommandDTO(
    			UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
    			"SERVICE NAME",
    			new Date(),
    			new MetadataCreateDTO().getInstance(),
    			new GenericAccessTokenDTO().getInstance(),
    			new AggregateStatusCreateDTO(),
    			"SYSTEM",
                new Date(),
                "SYSTEM",
                new Date(),
                UUID.randomUUID().toString()
    			);
    }
}
