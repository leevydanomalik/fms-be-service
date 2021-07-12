package com.bitozen.fms.service.command.svc.hystrix;

import java.io.IOException;
import java.util.Date;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bitozen.fms.common.dto.AggregateStatusDTO;
import com.bitozen.fms.common.dto.GenericResponseDTO;
import com.bitozen.fms.common.status.ResponseStatus;
import com.bitozen.fms.common.type.ProjectType;
import com.bitozen.fms.common.util.LogOpsUtil;
import com.bitozen.fms.service.command.ServiceChangeCommand;
import com.bitozen.fms.service.command.ServiceCreateCommand;
import com.bitozen.fms.service.command.ServiceDeleteCommand;
import com.bitozen.fms.service.command.svc.assembler.MetadataAssembler;
import com.bitozen.fms.service.command.svc.helper.ServiceHelper;
import com.bitozen.fms.service.common.dto.command.ServiceChangeCommandDTO;
import com.bitozen.fms.service.common.dto.command.ServiceCreateCommandDTO;
import com.bitozen.fms.service.common.dto.command.ServiceDeleteCommandDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixTimeoutException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServiceHystrixCommandService {

	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private MetadataAssembler metaAssembler;
	
	@Autowired
	private ServiceHelper helper;

    private final CommandGateway commandGateway;
    
    @Autowired
    public ServiceHystrixCommandService(CommandGateway commandGateway) {
    	this.commandGateway = commandGateway;
    }
    
    @HystrixCommand(fallbackMethod = "defaultPostServiceFallback")
    @Caching(
            evict = {
                @CacheEvict(value = "getServiceByIDCache", allEntries = true),
                @CacheEvict(value = "getServiceAllCache", allEntries = true)
            }
    )
    public GenericResponseDTO<ServiceCreateCommandDTO> postService(ServiceCreateCommandDTO dto) {
    	GenericResponseDTO<ServiceCreateCommandDTO> response = new GenericResponseDTO().successResponse();
    	try {
        	ServiceCreateCommand command = new ServiceCreateCommand(
        			dto.getSvcID(),
        			dto.getSvcName(),
        			dto.getSvcDate(),
        			objectMapper.writeValueAsString(metaAssembler.toMetadata(dto.getSvcMetadata())),
        			objectMapper.writeValueAsString(dto.getSvcToken()),
        			objectMapper.writeValueAsString(new AggregateStatusDTO(helper.findBizparByKey(dto.getSvcWorkProgress().getLastKnownStatus()))),
        			dto.getCreatedBy() == null ? "SYSTEM" : dto.getCreatedBy(),
                    dto.getCreatedDate() == null ? new Date() : dto.getCreatedDate(),
                    dto.getUpdatedBy(),
                    dto.getUpdatedDate()== null ? new Date() : dto.getUpdatedDate(),
                    dto.getRecordID()
        			);
        	commandGateway.send(command, new CommandCallback<ServiceCreateCommand, Object>() {
        		@Override
                public void onResult(CommandMessage<? extends ServiceCreateCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                    if(commandResultMessage.isExceptional() == false){
                        try {
                            log.info(objectMapper.writeValueAsString(LogOpsUtil.getLogResponse(
                                    ProjectType.CQRS, "Service", new Date(), "Command", new GenericResponseDTO().successResponse().getCode(),
                                    new GenericResponseDTO().successResponse().getMessage())));
                        } catch (JsonProcessingException ex) {
                            log.info(ex.getMessage());
                        }
                    } else {
                        response.setStatus(ResponseStatus.F);
                        response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
                        response.setMessage(commandResultMessage.exceptionResult().getLocalizedMessage());
                        try {
                            log.info(objectMapper.writeValueAsString(LogOpsUtil.getErrorResponse(
                                    ProjectType.CQRS, "Service", new Date(), "Command", response.getCode(), commandResultMessage.exceptionResult().getStackTrace())));
                        } catch (JsonProcessingException ex) {
                            log.info(ex.getMessage());
                        }
                    }
                }
            });
    	} catch(Exception e) {
    		log.info(e.getMessage());
    	}
    	
        return response;
    }
    
    private GenericResponseDTO<ServiceCreateCommandDTO> defaultPostServiceFallback(ServiceCreateCommandDTO dto, Throwable e) throws IOException {
    	return new GenericResponseDTO<ServiceCreateCommandDTO>().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e instanceof HystrixTimeoutException ? "Connection Timeout. Please Try Again Later"
                        : e instanceof HystrixBadRequestException ? "Bad Request. Please recheck submitted data" : e.getLocalizedMessage());
    }
    
    @HystrixCommand(fallbackMethod = "defaultPutServiceFallback")
    @Caching(
            evict = {
                @CacheEvict(value = "getServiceByIDCache", allEntries = true),
                @CacheEvict(value = "getServiceAllCache", allEntries = true)
            }
    )
    public GenericResponseDTO<ServiceChangeCommandDTO> putService(ServiceChangeCommandDTO dto) {
    	GenericResponseDTO<ServiceChangeCommandDTO> response = new GenericResponseDTO().successResponse();
    	try {
	    	ServiceChangeCommand command = new ServiceChangeCommand(
	    			dto.getSvcID(),
	    			dto.getSvcName(),
	    			dto.getSvcDate(),
	    			objectMapper.writeValueAsString(metaAssembler.toMetadata(dto.getSvcMetadata())),
	    			objectMapper.writeValueAsString(dto.getSvcToken()),
	    			objectMapper.writeValueAsString(new AggregateStatusDTO(helper.findBizparByKey(dto.getSvcWorkProgress().getLastKnownStatus()))),
	                dto.getUpdatedBy(),
	                dto.getUpdatedDate()== null ? new Date() : dto.getUpdatedDate()
	    			);
	    	commandGateway.send(command, new CommandCallback<ServiceChangeCommand, Object>() {
        		@Override
                public void onResult(CommandMessage<? extends ServiceChangeCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                    if(commandResultMessage.isExceptional() == false){
                        try {
                            log.info(objectMapper.writeValueAsString(LogOpsUtil.getLogResponse(
                                    ProjectType.CQRS, "Service", new Date(), "Command", new GenericResponseDTO().successResponse().getCode(),
                                    new GenericResponseDTO().successResponse().getMessage())));
                        } catch (JsonProcessingException ex) {
                            log.info(ex.getMessage());
                        }
                    } else {
                        response.setStatus(ResponseStatus.F);
                        response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
                        response.setMessage(commandResultMessage.exceptionResult().getLocalizedMessage());
                        try {
                            log.info(objectMapper.writeValueAsString(LogOpsUtil.getErrorResponse(
                                    ProjectType.CQRS, "Service", new Date(), "Command", response.getCode(), commandResultMessage.exceptionResult().getStackTrace())));
                        } catch (JsonProcessingException ex) {
                            log.info(ex.getMessage());
                        }
                    }
                }
            });
    	} catch(Exception e) {
    		log.info(e.getMessage());
    	}
        return response;
    }
    
    private GenericResponseDTO<ServiceChangeCommandDTO> defaultPutServiceFallback(ServiceChangeCommandDTO dto, Throwable e) throws IOException {
    	return new GenericResponseDTO<ServiceChangeCommandDTO>().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e instanceof HystrixTimeoutException ? "Connection Timeout. Please Try Again Later"
                        : e instanceof HystrixBadRequestException ? "Bad Request. Please recheck submitted data" : e.getLocalizedMessage());
    }
    
    @HystrixCommand(fallbackMethod = "defaultDeleteServiceFallback")
    @Caching(
            evict = {
                @CacheEvict(value = "getServiceByIDCache", allEntries = true),
                @CacheEvict(value = "getServiceAllCache", allEntries = true)
            }
    )
    public GenericResponseDTO<ServiceDeleteCommandDTO> deleteService(ServiceDeleteCommandDTO dto) {
    	GenericResponseDTO<ServiceDeleteCommandDTO> response = new GenericResponseDTO().successResponse();
    	ServiceDeleteCommand command = new ServiceDeleteCommand(
    			dto.getSvcID(),
                dto.getUpdatedBy()
    			);
    	commandGateway.send(command, new CommandCallback<ServiceDeleteCommand, Object>() {
    		@Override
            public void onResult(CommandMessage<? extends ServiceDeleteCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional() == false){
                    try {
                        log.info(objectMapper.writeValueAsString(LogOpsUtil.getLogResponse(
                                ProjectType.CQRS, "Service", new Date(), "Command", new GenericResponseDTO().successResponse().getCode(),
                                new GenericResponseDTO().successResponse().getMessage())));
                    } catch (JsonProcessingException ex) {
                        log.info(ex.getMessage());
                    }
                } else {
                    response.setStatus(ResponseStatus.F);
                    response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
                    response.setMessage(commandResultMessage.exceptionResult().getLocalizedMessage());
                    try {
                        log.info(objectMapper.writeValueAsString(LogOpsUtil.getErrorResponse(
                                ProjectType.CQRS, "Service", new Date(), "Command", response.getCode(), commandResultMessage.exceptionResult().getStackTrace())));
                    } catch (JsonProcessingException ex) {
                        log.info(ex.getMessage());
                    }
                }
            }
        });
        return response;
    }
    
    private GenericResponseDTO<ServiceDeleteCommandDTO> defaultDeleteServiceFallback(ServiceDeleteCommandDTO dto, Throwable e) throws IOException {
    	return new GenericResponseDTO<ServiceDeleteCommandDTO>().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e instanceof HystrixTimeoutException ? "Connection Timeout. Please Try Again Later"
                        : e instanceof HystrixBadRequestException ? "Bad Request. Please recheck submitted data" : e.getLocalizedMessage());
    }
}
