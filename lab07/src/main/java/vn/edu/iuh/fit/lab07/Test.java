package vn.edu.iuh.fit.lab07;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
public class Test {
    static Func func = new Func();
    public static void main(String[] args) {

        Function<Input2Field, Input2Field_ListElementAndX> toListElement = func.init();
        Function<Input2Field_ListElementAndX, Integer> calculator = func.calculator();

        Function<Input2Field, Integer> pipeline = toListElement
                .andThen(calculator);

//        String daThuc = "-52x^33 + 1 +x + 9x^6 - 3x + 6x^2 + 8x^23";
//        String daThuc = "-52x^33 +1 +x + 2x"; // -48
        String daThuc = "1"; // -48
        int x = 1;
        int result = pipeline.apply(new Input2Field(daThuc, x));

        toListElement.apply(new Input2Field(daThuc, x)).getElements().forEach(System.out::print);
        System.out.println();
        System.out.println("Da thuc = "+daThuc+" voi x = "+x+", co ket qua = "+result);
    }
}
