package com.bitozen.fms.service.event.handler;

import java.time.ZoneId;
import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bitozen.fms.service.event.ServiceChangeEvent;
import com.bitozen.fms.service.event.ServiceCreateEvent;
import com.bitozen.fms.service.event.ServiceDeleteEvent;
import com.bitozen.fms.service.projection.ServiceEntryProjection;
import com.bitozen.fms.service.repository.ServiceRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServiceEventListener {
	
	@Autowired
	private ServiceRepository repository;
	
	@EventHandler
    public void on(ServiceCreateEvent event) {
		repository.save(new ServiceEntryProjection(
				event.getSvcID(),
                event.getSvcName(),
                event.getSvcDate(),
                event.getSvcMetadata(),
                event.getSvcToken(),
                event.getSvcWorkProgress(),
                event.getCreatedBy(),
                event.getCreatedDate().toInstant().atZone(ZoneId.of("Asia/Jakarta")),
                event.getUpdatedBy(),
                event.getUpdatedDate() != null ? event.getUpdatedDate().toInstant().atZone(ZoneId.of("Asia/Jakarta")) : null,
                event.getRecordID()
				));
	}
	
	@EventHandler
    public void on(ServiceChangeEvent event) {
		Optional<ServiceEntryProjection> data = repository.findOneBySvcID(event.getSvcID());
		data.get().setSvcName(event.getSvcName());
		data.get().setSvcDate(event.getSvcDate());
		data.get().setSvcMetadata(event.getSvcMetadata());
		data.get().setSvcToken(event.getSvcToken());
		data.get().setSvcWorkProgress(event.getSvcWorkProgress());
		data.get().setUpdatedBy(event.getUpdatedBy());
        data.get().setUpdatedDate(event.getUpdatedDate() != null ? event.getUpdatedDate().toInstant().atZone(ZoneId.of("Asia/Jakarta")) : null);
        repository.save(data.get());
	}
	
	@EventHandler
    public void on(ServiceDeleteEvent event) {
		Optional<ServiceEntryProjection> data = repository.findOneBySvcID(event.getSvcID());
		repository.delete(data.get());
	}
}
