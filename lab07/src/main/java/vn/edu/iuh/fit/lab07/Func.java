package vn.edu.iuh.fit.lab07;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class Func {
    public Func() {
    }

    public Function<Input2Field, Input2Field_ListElementAndX> init() {
        return this::exStringToElements;
    }
    public Function<Input2Field_ListElementAndX, Integer> calculator() {
        return this::calculator;
    }

    private Integer calculator(Input2Field_ListElementAndX elementAndX) {
        List<Element> elements = elementAndX.getElements();
        if(elements.isEmpty()) return 0;
        Element remove = elementAndX.getElements().remove(0);
        log.info("** calculator: dau:{} so:{} mu:{}", remove.getDau(),remove.getSo(), remove.getMu());
        int result = (int) Math.ceil(remove.getSo() * Math.pow(elementAndX.getX(), remove.getMu()));
        if(remove.getDau().equals("-")) result*=-1;

        return result+calculator(elementAndX);

    }

    private Input2Field_ListElementAndX exStringToElements(Input2Field in){
        List<Element> elements = new ArrayList<>();
        Element element = new Element(); // element init
        String pattern = "^[0-9]+$";
        int size = in.getChuoi().length();
        String soChar = "";
        String s = "";
        int mu = 0;
        String dau = "";

        for (int i=0; i<size; i++){
            s = in.getChuoi().charAt(i) +"";
            if(s.equals(" ")) continue; // pass char empty
            log.info("** s: {} | soChar: {} | size: {}",s, soChar, size);

            if((s.equals("+") || s.equals("-")) || i==size-1){

                if(i==0 && i!=size-1){
                    dau = s;
                    continue;
                }
                if(s.equals("x")) {
                    mu=1;
                }
                if(soChar.isEmpty()) soChar="1";

                element.setSo(Integer.parseInt(soChar));
                element.setMu(mu);
                element.setDau(dau);
                elements.add(element);
                element = new Element();// new element
                mu = 0; soChar =""; dau = s; s = "";
                continue;
            }

            if(s.matches(pattern)) {
                soChar= soChar+s;
                continue;
            }

            if(s.equals("x") && (in.getChuoi().charAt(i+1) +"").equals("^")) {
                String muC = "";
                for (int k= 2; k<100; k++){
                    if(i+k == size-1) {
                        muC +=(in.getChuoi().charAt(i+k) +"");
                        i+=k-1;
                        break;
                    }
                    if((in.getChuoi().charAt(i+k) +"").matches(pattern)) muC +=(in.getChuoi().charAt(i+k) +"");
                    else{
                        if(i+k==size-1)
                            muC +=(in.getChuoi().charAt(i+k) +"");
                        i+=k;
                        break;
                    }
                }
                mu = Integer.parseInt(muC);
            }
        }

        return new Input2Field_ListElementAndX(elements,in.getX());
    }

}
