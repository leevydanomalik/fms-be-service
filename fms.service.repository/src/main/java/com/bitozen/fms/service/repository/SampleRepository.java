package com.bitozen.fms.service.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bitozen.fms.service.projection.SampleEntryProjection;

@Repository
@Transactional
public interface SampleRepository extends PagingAndSortingRepository<SampleEntryProjection, Long>{

	List<SampleEntryProjection> findAll();
}
