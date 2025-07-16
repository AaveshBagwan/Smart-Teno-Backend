package com.platform.entity;

import com.platform.common.utils.CommonUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SecondBaseEntity extends FirstBaseEntity {

    @Column(name = "CREATED_BY_ID")
    private Long createdById;

    @Column(name = "UPDATED_BY_ID")
    private Long updatedById;

    @PrePersist
    public void addIdsAtPersist() {
        this.createdById = CommonUtils.getUserSession().getUserId();
        this.updatedById = CommonUtils.getUserSession().getUserId();
    }

    @PreUpdate
    public void addIdsAtUpdate() {
        this.updatedById = CommonUtils.getUserSession().getUserId();
    }
}
