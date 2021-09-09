package com.example.test8_11;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Test811ApplicationTests {

    @Test
    void contextLoads() {
        int a=0;

        double b=0D;
        float c=0f;
        System.out.println(b);
        System.out.println(a==b);
        System.out.println(1/b);
        System.out.println(1/c);
        System.out.println(1/a);


    }

}
