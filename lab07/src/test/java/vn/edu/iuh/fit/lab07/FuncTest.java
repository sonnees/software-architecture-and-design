package vn.edu.iuh.fit.lab07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

@SpringBootTest(classes=Func.class)
class FuncTest {
    static Function<Input2Field, Integer> pipeline = null;

    @BeforeAll
    static void Before(){
        Func func = new Func();
        Function<Input2Field, Input2Field_ListElementAndX> toListElement = func.init();
        Function<Input2Field_ListElementAndX, Integer> calculator = func.calculator();
        pipeline = toListElement.andThen(calculator);
    }

    @Test
    void test01() {
        String daThuc = "x";
        int x = 1;
        int result = pipeline.apply(new Input2Field(daThuc, x));

        Assertions.assertEquals(1,result);
    }

    @Test
    void test02() {
        String daThuc = "1";
        int x = 1;
        int result = pipeline.apply(new Input2Field(daThuc, x));

        Assertions.assertEquals(1,result);
    }

    @Test
    void test03() {
        String daThuc = "x^2";
        int x = 1;
        int result = pipeline.apply(new Input2Field(daThuc, x));

        Assertions.assertEquals(1,result);
    }

    @Test
    void test04() {
        String daThuc = "-x^2";
        int x = 2;
        int result = pipeline.apply(new Input2Field(daThuc, x));

        Assertions.assertEquals(-4,result);
    }

    @Test
    void test05() {
        String daThuc = "52x^3 +1 +x + 2x";
        int x = 1;
        int result = pipeline.apply(new Input2Field(daThuc, x));

        Assertions.assertEquals(56,result);
    }

    @Test
    void test06() {
        String daThuc = "52x^33 + 1 +x + 9x^6 - 3x + 6x^2 + 8x^23";
        int x = 1;
        int result = pipeline.apply(new Input2Field(daThuc, x));

        Assertions.assertEquals(74,result);
    }

}
