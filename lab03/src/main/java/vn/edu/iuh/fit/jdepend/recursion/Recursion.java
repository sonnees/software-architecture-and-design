package vn.edu.iuh.fit.jdepend.recursion;

import org.springframework.stereotype.Component;

@Component
public class Recursion {
    public int getSum(int n){
        if(n==1) return 1;

        return getSum(n-1) + n;
    }

}
