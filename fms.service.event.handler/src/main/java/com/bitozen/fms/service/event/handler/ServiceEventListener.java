package com.bitozen.fms.service.event.handler;

import java.time.ZoneId;
import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bitozen.fms.common.dto.AggregateStatusDTO;
import com.bitozen.fms.common.dto.CreationalSpecificationDTO;
import com.bitozen.fms.common.dto.GenericAccessTokenDTO;
import com.bitozen.fms.service.common.MetadataDTO;
import com.bitozen.fms.service.event.ServiceChangeEvent;
import com.bitozen.fms.service.event.ServiceCreateEvent;
import com.bitozen.fms.service.event.ServiceDeleteEvent;
import com.bitozen.fms.service.projection.ServiceEntryProjection;
import com.bitozen.fms.service.repository.ServiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServiceEventListener {
	
	@Autowired
	ServiceRepository repository;
	
	@Autowired
    ObjectMapper mapper;
	
	@SneakyThrows
    @EventHandler
    public void on(ServiceCreateEvent event) {
		repository.save(new ServiceEntryProjection(
				repository.count() + 1,
				event.getSvcID(),
                event.getSvcName(),
                event.getSvcDate(),
                mapper.readValue(event.getSvcMetadata(), MetadataDTO.class),
                mapper.readValue(event.getSvcToken(), GenericAccessTokenDTO.class),
                mapper.readValue(event.getSvcWorkProgress(), AggregateStatusDTO.class),
                new CreationalSpecificationDTO(
                		event.getCreatedBy(),
                        event.getCreatedDate(),
                        null,
                        null),
                event.getRecordID()
				));
	}
	
	@SneakyThrows
    @EventHandler
    public void on(ServiceChangeEvent event) {
		Optional<ServiceEntryProjection> data = repository.findOneBySvcID(event.getSvcID());
		data.get().setSvcName(event.getSvcName());
		data.get().setSvcDate(event.getSvcDate());
		data.get().setSvcMetadata(mapper.readValue(event.getSvcMetadata(), MetadataDTO.class));
		data.get().setSvcToken(mapper.readValue(event.getSvcToken(), GenericAccessTokenDTO.class));
		data.get().setSvcWorkProgress(mapper.readValue(event.getSvcWorkProgress(), AggregateStatusDTO.class));
		data.get().getCreational().setModifiedBy(event.getUpdatedBy());
        data.get().getCreational().setModifiedDate(event.getUpdatedDate() == null ? null : event.getUpdatedDate());
		repository.save(data.get());
	}
	
	@EventHandler
    public void on(ServiceDeleteEvent event) {
		Optional<ServiceEntryProjection> data = repository.findOneBySvcID(event.getSvcID());
		repository.delete(data.get());
	}
}
