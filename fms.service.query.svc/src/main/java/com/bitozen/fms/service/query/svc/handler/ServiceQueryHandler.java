package com.bitozen.fms.service.query.svc.handler;

import com.bitozen.fms.common.dto.GenericResponseDTO;
import com.bitozen.fms.service.common.dto.query.ServiceDTO;
import com.bitozen.fms.service.query.svc.hystrix.ServiceHystrixQueryService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceQueryHandler {

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private ServiceHystrixQueryService sqs;

    /**
     *
     * @param query
     * @param svcID
     * @return
     */
    @QueryHandler
    public GenericResponseDTO handle(ServiceByIDQuery query) {
        return sqs.getServiceByID(query.getSvcID());
    }
}