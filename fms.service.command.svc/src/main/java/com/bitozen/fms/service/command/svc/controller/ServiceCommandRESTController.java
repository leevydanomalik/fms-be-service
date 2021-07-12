package com.bitozen.fms.service.command.svc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bitozen.fms.common.dto.GenericResponseDTO;
import com.bitozen.fms.common.type.ProjectType;
import com.bitozen.fms.common.util.LogOpsUtil;
import com.bitozen.fms.service.command.svc.hystrix.ServiceHystrixCommandService;
import com.bitozen.fms.service.common.dto.command.ServiceChangeCommandDTO;
import com.bitozen.fms.service.common.dto.command.ServiceCreateCommandDTO;
import com.bitozen.fms.service.common.dto.command.ServiceDeleteCommandDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ServiceCommandRESTController {

	@Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest httpRequest;
    
    @Autowired
    private ServiceHystrixCommandService service;
    
    @RequestMapping(value = "/command/get.service.version", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    String getVersion() {
        return "Service Command Service - version 1.0.0-SNAPSHOT";
    }
    
    @RequestMapping(value = "/command/get.service.dummy", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    ServiceCreateCommandDTO getDummy() {
    	return new ServiceCreateCommandDTO().getInstance();
    }
    
    @RequestMapping(value = "/command/post.service",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDTO<ServiceCreateCommandDTO>> postService(@RequestBody ServiceCreateCommandDTO dto) {
    	try {
    		log.info(objectMapper.writeValueAsString(
    				LogOpsUtil.getLogOps(ProjectType.CQRS, "Service", ServiceCommandRESTController.class.getName(),
    						httpRequest.getRequestURL().toString(),
                            new Date(), "Command", "Create",
                            dto.getCreatedBy(),
                            dto)));
    	} catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
    	GenericResponseDTO<ServiceCreateCommandDTO> response = service.postService(dto);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @RequestMapping(value = "/command/put.service",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDTO<ServiceChangeCommandDTO>> putService(@RequestBody ServiceChangeCommandDTO dto) {
    	try {
    		log.info(objectMapper.writeValueAsString(
    				LogOpsUtil.getLogOps(ProjectType.CQRS, "Service", ServiceCommandRESTController.class.getName(),
    						httpRequest.getRequestURL().toString(),
                            new Date(), "Command", "Update",
                            dto.getUpdatedBy(),
                            dto)));
    	} catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
    	GenericResponseDTO<ServiceChangeCommandDTO> response = service.putService(dto);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @RequestMapping(value = "/command/delete.service",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDTO<ServiceDeleteCommandDTO>> deleteService(@RequestBody ServiceDeleteCommandDTO dto) {
    	try {
    		log.info(objectMapper.writeValueAsString(
    				LogOpsUtil.getLogOps(ProjectType.CQRS, "Service", ServiceCommandRESTController.class.getName(),
    						httpRequest.getRequestURL().toString(),
                            new Date(), "Command", "Delete",
                            dto.getUpdatedBy(),
                            dto)));
    	} catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
    	GenericResponseDTO<ServiceDeleteCommandDTO> response = service.deleteService(dto);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
