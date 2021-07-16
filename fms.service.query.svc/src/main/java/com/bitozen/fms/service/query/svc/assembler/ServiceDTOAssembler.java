package com.bitozen.fms.service.query.svc.assembler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bitozen.fms.common.dto.AggregateStatusDTO;
import com.bitozen.fms.common.dto.GenericAccessTokenDTO;
import com.bitozen.fms.service.common.MetadataDTO;
import com.bitozen.fms.service.common.dto.query.ServiceDTO;
import com.bitozen.fms.service.projection.ServiceEntryProjection;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServiceDTOAssembler {

	@Autowired
    private ObjectMapper objectMapper;
	
	public ServiceDTO toDTO(ServiceEntryProjection domain) {
		try {
			return new ServiceDTO(
					domain.getSvcID(),
					domain.getSvcName(),
					domain.getSvcDate(),
					domain.getSvcMetadata(),
					domain.getSvcToken(),
					domain.getSvcWorkProgress(),
					domain.getCreational() == null ? null : domain.getCreational().getCreatedBy(),
                    domain.getCreational() == null ? null : domain.getCreational().getCreatedDate(),
                    domain.getCreational() == null ? null : domain.getCreational().getModifiedBy(),
                    domain.getCreational() == null ? null : domain.getCreational().getModifiedDate(),
                    domain.getRecordID()
					);
		} catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public List<ServiceDTO> toDTOs(List<ServiceEntryProjection> domains) {
		List<ServiceDTO> datas = new ArrayList<>();
		domains.stream().forEach((o) -> {
			datas.add(toDTO(o));
		});
		return datas;
	}
}
