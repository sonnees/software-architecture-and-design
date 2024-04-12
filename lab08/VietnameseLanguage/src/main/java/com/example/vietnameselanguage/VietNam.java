package com.example.vietnameselanguage;
import com.example.language.Language;
import lombok.Getter;


@Getter
public class VietNam implements Language {

    private final String PATH = "C:\\SON.admin\\VIII\\Architecture\\software-architecture-and-design\\lab08\\VietnameseLanguage\\src\\main\\resources\\dictionary.txt";

    @Override
    public String sayHello(String name) {
        return "SON NEES "+name;
    }

    @Override
    public String name() {
        return "VietNam";
    }
}
