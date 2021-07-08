package com.bitozen.fms.service.core;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.bitozen.fms.service.command.ServiceChangeCommand;
import com.bitozen.fms.service.command.ServiceCreateCommand;
import com.bitozen.fms.service.command.ServiceDeleteCommand;
import com.bitozen.fms.service.event.ServiceChangeEvent;
import com.bitozen.fms.service.event.ServiceCreateEvent;
import com.bitozen.fms.service.event.ServiceDeleteEvent;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class Service {

	@AggregateIdentifier
	private String svcID;
	private String svcName;
	private Date svcDate;
	private String svcMetadata;
	private String svcToken;
	private String svcWorkProgress;
	/**
     * Transactional Base Object
     */
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String recordID;
    
    public Service() {
    	
    }
    
    @CommandHandler
    public Service(ServiceCreateCommand command) {
    	apply(new ServiceCreateEvent(
    			command.getSvcID(),
                command.getSvcName(),
                command.getSvcDate(),
                command.getSvcMetadata(),
                command.getSvcToken(),
                command.getSvcWorkProgress(),
                command.getCreatedBy(),
                command.getCreatedDate(),
                command.getUpdatedBy(),
                command.getUpdatedDate(),
                command.getRecordID()
    			));
    }
    
    @CommandHandler
    public void handle(ServiceChangeCommand command) {
    	apply(new ServiceChangeEvent(
    			command.getSvcID(),
                command.getSvcName(),
                command.getSvcDate(),
                command.getSvcMetadata(),
                command.getSvcToken(),
                command.getSvcWorkProgress(),
                command.getUpdatedBy(),
                command.getUpdatedDate()
    			));
    }
    
    @CommandHandler
    public void handle(ServiceDeleteCommand command) {
    	apply(new ServiceDeleteEvent(
    			command.getSvcID(),
                command.getUpdatedBy()
    			));
    }
    
    @EventSourcingHandler
    public void on(ServiceCreateEvent event) {
    	this.svcID = event.getSvcID();
        this.svcName = event.getSvcName();
        this.svcDate = event.getSvcDate();
        this.svcMetadata = event.getSvcMetadata();
        this.svcToken = event.getSvcToken();
        this.svcWorkProgress = event.getSvcWorkProgress();
        this.createdBy = event.getCreatedBy();
        this.createdDate = event.getCreatedDate();
        this.updatedBy = event.getUpdatedBy();
        this.updatedDate = event.getUpdatedDate();
        this.recordID = event.getRecordID();
    }
    
    @EventSourcingHandler
    public void on(ServiceChangeEvent event) {
    	this.svcID = event.getSvcID();
        this.svcName = event.getSvcName();
        this.svcDate = event.getSvcDate();
        this.svcMetadata = event.getSvcMetadata();
        this.svcToken = event.getSvcToken();
        this.svcWorkProgress = event.getSvcWorkProgress();
        this.updatedBy = event.getUpdatedBy();
        this.updatedDate = event.getUpdatedDate();
    }
    
    @EventSourcingHandler
    public void on(ServiceDeleteEvent event) {
    	this.svcID = event.getSvcID();
        this.updatedBy = event.getUpdatedBy();
    }
}
