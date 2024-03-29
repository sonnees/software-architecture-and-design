package vn.edu.iuh.fit.jdepend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    private String userName;
    private Boolean gender;
    private Date birthday;
    private String avatar;
    private String background;
}
