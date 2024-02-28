package vn.edu.iuh.fit.jdepend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.jdepend.recursion.UserRole;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountCreateDTO {
    private String phoneNumber;
    private String password;
    private String userName;
    private Boolean gender;
    private Date birthday;
    private UserRole role;

}
