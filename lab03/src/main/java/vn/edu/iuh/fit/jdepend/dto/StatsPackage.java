package vn.edu.iuh.fit.jdepend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsPackage extends Package{
    private int totalClasses;
    private int concreteClasses;
    private int abstractClasses;
    private int ca;
    private int ce;
    private int a;
    private float i;
    private float d;
    private int v;

    public StatsPackage(String name, int totalClasses, int concreteClasses, int abstractClasses, int ca, int ce, int a, float i, float d, int v) {
        super(name);
        this.totalClasses = totalClasses;
        this.concreteClasses = concreteClasses;
        this.abstractClasses = abstractClasses;
        this.ca = ca;
        this.ce = ce;
        this.a = a;
        this.i = i;
        this.d = d;
        this.v = v;
    }
}
