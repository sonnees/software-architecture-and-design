package vn.edu.iuh.fit.jdepend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.jdepend.enums.Type;
import vn.edu.iuh.fit.jdepend.recursion.UserRole;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    private UUID id;
    private String phoneNumber;
    private String pw;
    private UUID userID;
    private Date createAt;
    private Type type;
    private Profile profile;
    private UserRole role;
    private Setting setting;
}
