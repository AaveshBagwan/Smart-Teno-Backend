package com.platform.entity;

import com.platform.common.annotations.ValidHttpUrl;
import com.platform.common.annotations.ValidUrl;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "smt_property")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Property extends SecondBaseEntity {

    @NotNull(message = "Property ID should not be null", groups = {UpdatePropertyValidatorGrp.class})
    @Id
    @Column(name = "property_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "smt_property_id_generator")
    @TableGenerator(
            name = "smt_property_id_generator",
            allocationSize = 1,
            table = "smt_id_generator",
            pkColumnName = "PK_NAME",
            valueColumnName = "PK_CURR_VALUE",
            pkColumnValue = "property_id"
    )
    private Long propertyId;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Property name should not be null", groups = {AddPropertyValidatorGrp.class})
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Property description should not be blank", groups = {AddPropertyValidatorGrp.class})
    @Column(name = "address")
    private String address;

    @ValidUrl(groups = {AddPropertyValidatorGrp.class, UpdatePropertyValidatorGrp.class})
    @Column(name = "property_image_url")
    private String propertyImgUrl;

    public interface AddPropertyValidatorGrp {}
    public interface UpdatePropertyValidatorGrp {}

}
