package com.bitozen.fms.service.command.svc.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bitozen.fms.service.command.svc.helper.ServiceHelper;
import com.bitozen.fms.service.common.MetadataCreateDTO;
import com.bitozen.fms.service.common.MetadataDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MetadataAssembler {

	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private ServiceHelper helper;
	
	public MetadataDTO toMetadata(MetadataCreateDTO dto) {
		MetadataDTO meta = new MetadataDTO();
		meta.setEs(dto.getEs());
		meta.setId(dto.getId());
		meta.setTag(dto.getTag());
		meta.setType(helper.findBizparByKey(dto.getType()));
		meta.setVersion(dto.getVersion());
		return meta;
	}
}
