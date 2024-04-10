package com.example.englishlanguage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class English {

    private final String PATH = "C:\\SON.admin\\VIII\\Architecture\\software-architecture-and-design\\lab08\\EnglishLanguage\\src\\main\\resources\\dictionary.txt";
    private final Map<String, String> dictionary;
    private final List<String> listWord;
    private final DictReader dictReader;

    public English() {
        dictReader = new DictReader();
        this.dictionary = dictReader.readDictionary(PATH);
        this.listWord = new ArrayList<>(dictionary.keySet());
    }

    public String sayHello(String name) {
        return "SON NEES "+name;
    }

    public String name() {
        return "English";
    }
}
