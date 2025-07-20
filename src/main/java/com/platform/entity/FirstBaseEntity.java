package com.platform.entity;

import com.platform.common.config.attributeconverter.EnumAttributeConverters;
import com.platform.common.model.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FirstBaseEntity {

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "status")
    @Convert(converter = EnumAttributeConverters.StatusConverter.class)
    private StatusEnum status;

    @PrePersist
    public void prePersistEntity() {
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdateEntity() {
        this.updatedOn = LocalDateTime.now();
    }

}
