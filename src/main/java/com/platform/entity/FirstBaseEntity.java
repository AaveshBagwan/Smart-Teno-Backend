package com.platform.entity;

import com.platform.common.config.attributeconverter.EnumAttributeConverters;
import com.platform.common.constants.StatusEnum;
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

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    @Column(name = "STATUS")
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
