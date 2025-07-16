package com.platform.entity;

import com.platform.common.annotations.ValidMobileNumber;
import com.platform.common.annotations.ValidPassword;
import com.platform.common.annotations.ValidUrl;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "SMT_USER")
@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends FirstBaseEntity {

    @NotNull(message = "UserId is mandatory", groups = UpdateUserGroup.class)
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
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "MOBILE_NUMBER", unique = true)
    private String mobileNumber;

    @Email(message = "Enter valid emailId", groups = UpdateUserGroup.class)
    @Column(name = "EMAIL", unique = true)
    private String email;

    @ValidPassword(groups = UpdateUserGroup.class)
    @Column(name = "PASSWORD")
    private String password;

    /* while updating password */
    @Transient
    @ValidPassword(message = "Invalid new password", groups = UpdateUserGroup.class)
    private String newPassword;

    @Column(name = "ADDRESS")
    private String address;

    @ValidUrl(groups = UpdateUserGroup.class)
    @Column(name = "PROFILE_IMAGE_URL")
    private String profileImageUrl;

    public interface UpdateUserGroup{}

}
