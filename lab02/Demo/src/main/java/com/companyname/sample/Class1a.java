package com.companyname.sample;

import lombok.Getter;

import java.util.Date;
import java.util.Objects;

/**
 * @author sonnees
 * @since 2024-01-23
 */
@Getter
public class Class1a {
    private String class1a_string;
    private Boolean class1a_boolean;
    private Date class1a_date;

    public Class1a() {
    }

    @Override
    public String toString() {
        return "Class1a{" +
                "class1a_string='" + class1a_string + '\'' +
                ", class1a_boolean=" + class1a_boolean +
                ", class1a_date=" + class1a_date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class1a class1a = (Class1a) o;
        return Objects.equals(class1a_string, class1a.class1a_string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(class1a_string);
    }
}
