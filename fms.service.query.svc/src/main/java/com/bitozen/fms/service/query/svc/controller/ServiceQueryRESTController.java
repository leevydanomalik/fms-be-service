package com.bitozen.fms.service.query.svc.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bitozen.fms.common.dto.GenericResponseDTO;
import com.bitozen.fms.common.dto.GetListRequestDTO;
import com.bitozen.fms.common.type.ProjectType;
import com.bitozen.fms.common.util.LogOpsUtil;
import com.bitozen.fms.service.common.dto.query.ServiceDTO;
import com.bitozen.fms.service.query.svc.handler.ServiceByIDQuery;
import com.bitozen.fms.service.query.svc.hystrix.ServiceHystrixQueryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ServiceQueryRESTController {

	@Autowired
    private transient QueryGateway gateway;
	
	@Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest httpRequest;
    
    @Autowired
    private ServiceHystrixQueryService service;
    
    @RequestMapping(value = "/query/get.service.version", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody
    String getVersion() {
        return "Service Query Service - version 1.0.0-SNAPSHOT";
    }
    
    @RequestMapping(value = "/query/get.service.by.id/{svcID}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDTO<ServiceDTO>> getServiceByID(@PathVariable("svcID") String svcID) {
    	try {
            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(LogOpsUtil.getLogOps(ProjectType.CQRS, "Service", ServiceQueryRESTController.class.getName(),
                    httpRequest.getRequestURL().toString(),
                    new Date(), "Query", "getServiceByID",
                    "SYSTEM",
                    svcID)));
        } catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
    	GenericResponseDTO<ServiceDTO> response = service.getServiceByID(svcID);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/query/get.service.by.id.new/{svcID}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<CompletableFuture<GenericResponseDTO>> getServiceByIDNew(@PathVariable("svcID") String svcID) {
			try {
				log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(LogOpsUtil.getLogOps(ProjectType.CQRS, "Service", ServiceQueryRESTController.class.getName(),
				httpRequest.getRequestURL().toString(),
				new Date(), "Query", "getServiceByID",
				"SYSTEM",
				svcID)));
			} catch (JsonProcessingException ex) {
				log.info(ex.getMessage());
			}
		CompletableFuture<GenericResponseDTO> response = gateway.query(new ServiceByIDQuery(svcID), GenericResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    @RequestMapping(value = "/query/get.service.all",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseDTO<List<ServiceDTO>>> getServiceAll(@RequestBody GetListRequestDTO dto) {
    	try {
    		log.info(objectMapper.writeValueAsString(
                    LogOpsUtil.getLogOps(ProjectType.CQRS, "Service", ServiceQueryRESTController.class.getName(),
                            httpRequest.getRequestURL().toString(),
                            new Date(), "Query", "getServiceAll",
                            "SYSTEM",
                            dto)));
    	} catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
    	GenericResponseDTO<List<ServiceDTO>> response = service.getServiceAll(dto);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
