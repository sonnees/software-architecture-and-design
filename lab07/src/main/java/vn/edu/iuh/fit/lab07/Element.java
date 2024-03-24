package vn.edu.iuh.fit.lab07;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Element {
    private String dau;
    private int so;
    private int mu;

    @Override
    public String toString() {
        return dau + so +(mu != 0 ? "x^"+mu : "");
    }
}
