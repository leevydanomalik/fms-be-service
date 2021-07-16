package com.bitozen.fms.service.query.svc.handler;

import com.bitozen.fms.common.dto.GenericResponseDTO;
import com.bitozen.fms.service.common.dto.query.ServiceDTO;
import com.bitozen.fms.service.projection.ServiceEntryProjection;
import com.bitozen.fms.service.query.svc.assembler.ServiceDTOAssembler;
import com.bitozen.fms.service.query.svc.handler.query.ServiceByIDQuery;
import com.bitozen.fms.service.query.svc.handler.query.ServiceGetAllQuery;
import com.bitozen.fms.service.query.svc.handler.query.ServiceGetAllWebQuery;
import com.bitozen.fms.service.query.svc.hystrix.ServiceHystrixQueryService;
import com.bitozen.fms.service.repository.ServiceRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ServiceQueryHandler {

	@Autowired
	ServiceRepository repository;
	 
	@Autowired
	ServiceDTOAssembler assembler;
	 
    @QueryHandler
    public ServiceDTO getServiceByID(ServiceByIDQuery query) {
    	Optional<ServiceEntryProjection> result = repository.findOneBySvcID(query.getSvcID());
    	 if(result.isPresent()){
    		 return assembler.toDTO(result.get());
    	 }
    	 return null;
    }
    
    @QueryHandler
    public List<ServiceDTO> getServiceAll(ServiceGetAllQuery query) {
    	List<ServiceEntryProjection> results = repository.findAll();
    	 if(results != null && !results.isEmpty()){
    		 return assembler.toDTOs(results);
    	 }
    	 return Collections.EMPTY_LIST;
    }
    
    @QueryHandler
    public List<ServiceDTO> getServiceAllWeb(ServiceGetAllWebQuery query) {
    	 Pageable page = PageRequest.of(query.getRequest().getOffset(), query.getRequest().getLimit(), Sort.by("creational.createdDate").descending());
    	 String param = String.valueOf(query.getRequest().getParams().get("param"));
    	 Page<ServiceEntryProjection> datas = repository.findAllForWeb(param, page);
    	 if(datas.hasContent()){
    		 return assembler.toDTOs(datas.getContent());
    	 }
    	 return Collections.EMPTY_LIST;
    }
}