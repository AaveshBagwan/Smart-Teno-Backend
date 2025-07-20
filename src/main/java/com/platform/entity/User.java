package com.platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.common.annotations.ValidPassword;
import com.platform.common.annotations.ValidUrl;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table(name = "smt_user")
@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends FirstBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Users_smt_ID_GENERATOR_name")
    @TableGenerator(
            name = "Users_smt_ID_GENERATOR_name",
            allocationSize = 1,
            table = "smt_id_generator",
            pkColumnName = "PK_NAME",
            valueColumnName = "PK_CURR_VALUE",
            pkColumnValue = "user_id"
    )
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @Email(message = "Enter valid emailId", groups = UpdateUserGroup.class)
    @Column(name = "email", unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    /* while updating password */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    @ValidPassword(message = "Invalid new password", groups = UpdateUserGroup.class)
    private String newPassword;

    @Column(name = "address")
    private String address;

    @ValidUrl(groups = UpdateUserGroup.class)
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    public interface UpdateUserGroup {
    }

}
