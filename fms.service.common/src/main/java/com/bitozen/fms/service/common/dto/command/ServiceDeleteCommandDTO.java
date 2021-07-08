package com.bitozen.fms.service.common.dto.command;

import java.io.Serializable;

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
public class ServiceDeleteCommandDTO implements Serializable{

	private String svcID;
	
	private String updatedBy;
}
