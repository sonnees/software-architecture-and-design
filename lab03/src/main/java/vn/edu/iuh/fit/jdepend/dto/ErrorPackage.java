package vn.edu.iuh.fit.jdepend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorPackage extends Package {
    private String content;

    public ErrorPackage(String name, String content) {
        super(name);
        this.content = content;
    }
}
