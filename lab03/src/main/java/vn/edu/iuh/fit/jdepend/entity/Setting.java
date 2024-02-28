package vn.edu.iuh.fit.jdepend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.jdepend.enums.AllowMessaging;
import vn.edu.iuh.fit.jdepend.enums.ShowBirthday;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Setting {
    private AllowMessaging allowMessaging;
    private ShowBirthday showBirthday;
}
