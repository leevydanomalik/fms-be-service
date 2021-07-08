package com.bitozen.fms.service.query.svc.hystrix;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bitozen.fms.common.dto.GenericResponseDTO;
import com.bitozen.fms.common.dto.GetListRequestDTO;
import com.bitozen.fms.common.type.ProjectType;
import com.bitozen.fms.common.util.LogOpsUtil;
import com.bitozen.fms.service.common.dto.query.ServiceDTO;
import com.bitozen.fms.service.projection.ServiceEntryProjection;
import com.bitozen.fms.service.query.svc.assembler.ServiceDTOAssembler;
import com.bitozen.fms.service.repository.ServiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixTimeoutException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServiceHystrixQueryService {

	 @Autowired
	 ObjectMapper objectMapper;
	 
	 @Autowired
	 ServiceRepository repository;
	 
	 @Autowired
	 ServiceDTOAssembler assembler;
	 
	 @HystrixCommand(fallbackMethod = "defaultGetServiceByIDFallback")
	 @Cacheable(value = "getServiceByIDCache", key = "#p0")
	 public GenericResponseDTO<ServiceDTO> getServiceByID(String svcID) {
		 try {
			 Optional<ServiceEntryProjection> data = repository.findOneBySvcID(svcID);
			 if (!data.isPresent()) {
	                log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(LogOpsUtil.getLogResponse(
	                        ProjectType.CQRS, "Service", new Date(), "Query", new GenericResponseDTO().successResponse().getCode(),
	                        new GenericResponseDTO().successResponse().getMessage())));
	                return new GenericResponseDTO().noDataFoundResponse();
	            }
	            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(LogOpsUtil.getLogResponse(
	                    ProjectType.CQRS, "Service", new Date(), "Query", new GenericResponseDTO().successResponse().getCode(),
	                    new GenericResponseDTO().successResponse().getMessage())));
	            return new GenericResponseDTO().successResponse(assembler.toDTO(data.get()));
		 } catch (Exception e) {
	            log.info(e.getMessage());
	            try {
	                log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
	                        LogOpsUtil.getErrorResponse(ProjectType.CQRS, "Service", new Date(), "Query", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getStackTrace())));
	            } catch (JsonProcessingException ex) {
	                log.info(ex.getMessage());
	            }
	            return new GenericResponseDTO().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getLocalizedMessage());
	        }
	 }
	 
	 public GenericResponseDTO<ServiceDTO> defaultGetServiceByIDFallback(String svcID, Throwable e) throws IOException {
		 return new GenericResponseDTO<ServiceDTO>().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
	                e instanceof HystrixTimeoutException ? "Connection Timeout. Please Try Again Later"
	                        : e instanceof HystrixBadRequestException ? "Bad Request. Please recheck submitted data" : e.getLocalizedMessage());
	 }
	 
	 @HystrixCommand(fallbackMethod = "defaultGetAllServiceFallback")
	 @Cacheable(value = "getServiceAllCache", key = "#p0")
	 public GenericResponseDTO<List<ServiceDTO>> getServiceAll(GetListRequestDTO dto) {
		 try {
			 Pageable page = PageRequest.of(dto.getOffset(), dto.getLimit(), Sort.by("createdDate").descending());
			 Page<ServiceEntryProjection> datas = repository.findAll(page);
			 if (!datas.hasContent()) {
	                log.info(objectMapper.writeValueAsString(LogOpsUtil.getLogResponse(
	                        ProjectType.CQRS, "Service", new Date(), "Query", new GenericResponseDTO().successResponse().getCode(),
	                        new GenericResponseDTO().successResponse().getMessage())));
	                return new GenericResponseDTO().noDataFoundResponse();
	            }
	            log.info(objectMapper.writeValueAsString(LogOpsUtil.getLogResponse(
	                    ProjectType.CQRS, "Service", new Date(), "Query", new GenericResponseDTO().successResponse().getCode(),
	                    new GenericResponseDTO().successResponse().getMessage())));
	            return new GenericResponseDTO().successResponse(assembler.toDTOs(datas.getContent()));
	        } catch (Exception e) {
	            log.info(e.getMessage());
	            try {
	                log.info(objectMapper.writeValueAsString(
	                        LogOpsUtil.getErrorResponse(ProjectType.CQRS, "Service", new Date(), "Query", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getStackTrace())));
	            } catch (JsonProcessingException ex) {
	                log.info(ex.getMessage());
	            }
	            return new GenericResponseDTO().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getLocalizedMessage());
	        }
	 }
	 
	 public GenericResponseDTO<List<ServiceDTO>> defaultGetAllServiceFallback(GetListRequestDTO dto, Throwable e) throws IOException {
		 return new GenericResponseDTO<List<ServiceDTO>>().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
	                e instanceof HystrixTimeoutException ? "Connection Timeout. Please Try Again Later"
	                        : e instanceof HystrixBadRequestException ? "Bad Request. Please recheck submitted data" : e.getLocalizedMessage());
	 }
}
