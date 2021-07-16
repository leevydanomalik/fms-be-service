package com.bitozen.fms.service.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitozen.fms.service.projection.ServiceEntryProjection;

@Repository
@Transactional
public interface ServiceRepository extends MongoRepository<ServiceEntryProjection, Long>{

	Optional<ServiceEntryProjection> findOneBySvcID(@NotNull String svcID);
	
	@Query(value = "{$or: [{ 'svcID' : {$regex: ?0,$options: 'i'}}," +
            " {'svcName' : {$regex: ?0,$options: 'i'}}]}")
	Page<ServiceEntryProjection> findAllForWeb(@NotNull String param, @NotNull Pageable pageable);
	
	Page<ServiceEntryProjection> findAll(@NotNull Pageable pageable);
	
	List<ServiceEntryProjection> findAll();
	
	long count();
}
