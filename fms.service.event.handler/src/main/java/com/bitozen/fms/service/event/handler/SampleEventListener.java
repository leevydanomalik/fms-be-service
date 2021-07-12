package com.bitozen.fms.service.event.handler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bitozen.fms.service.event.SampleCreateEvent;
import com.bitozen.fms.service.projection.SampleEntryProjection;
import com.bitozen.fms.service.repository.SampleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SampleEventListener {
	
	@Autowired
	SampleRepository repository;
	
	@EventHandler
    public void on(SampleCreateEvent event) {
		System.out.println("LISTENER");
		repository.save(new SampleEntryProjection(
				event.getSampleID(),
				null,
				null,
				null,
				null,
				null
				));
	}
}
