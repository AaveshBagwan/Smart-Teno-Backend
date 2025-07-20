package com.platform.entity;

import com.platform.common.utils.CommonUtils;
import com.platform.common.utils.SessionUtils;
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

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "updated_by_id")
    private Long updatedById;

    @PrePersist
    public void addIdsAtPersist() {
        this.createdById = SessionUtils.getUserSession().getUserId();
        this.updatedById = SessionUtils.getUserSession().getUserId();
    }

    @PreUpdate
    public void addIdsAtUpdate() {
        this.updatedById = SessionUtils.getUserSession().getUserId();
    }
}
