package com.example.englishlanguage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnglishLanguageApplicationTests {

    @Autowired
    English english;
    @Test
    void contextLoads() {
        System.out.println(english.getDictionary().get("apple"));
    }

}
