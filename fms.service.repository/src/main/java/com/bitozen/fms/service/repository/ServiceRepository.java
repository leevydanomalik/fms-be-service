package com.bitozen.fms.service.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bitozen.fms.service.projection.ServiceEntryProjection;

@Repository
@Transactional
public interface ServiceRepository extends PagingAndSortingRepository<ServiceEntryProjection, Long>{

	Optional<ServiceEntryProjection> findOneBySvcID(String svcID);
	
	Page<ServiceEntryProjection> findAll(Pageable pageable);
}
