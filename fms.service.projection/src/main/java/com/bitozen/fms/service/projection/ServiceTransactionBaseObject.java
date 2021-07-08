package com.bitozen.fms.service.projection;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bitozen.fms.service.common.util.ZonedDateTimeAttributeConverter;

@Entity
@Inheritance
@DiscriminatorColumn(name = "service_obj_disc", length = 50)
@Table(name = "mst_serviceentryprojection")
public abstract class ServiceTransactionBaseObject implements Serializable {

	@Id
    @GenericGenerator(name = "sequence_dep_id", strategy = "com.bitozen.fms.service.projection.common.HibernateIDGenerator")
    @GeneratedValue(generator = "sequence_dep_id")
    private Long id;

    protected String createdBy;
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    protected ZonedDateTime createdDate;
    protected String updatedBy;
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    protected ZonedDateTime updatedDate;
    protected String recordID;

    public ServiceTransactionBaseObject() {
    }

    public ServiceTransactionBaseObject(String createdBy, ZonedDateTime createdDate, String updatedBy, ZonedDateTime updatedDate, String recordID) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.recordID = recordID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }
}
