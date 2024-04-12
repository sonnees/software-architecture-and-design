package com.example.language;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DictReader {
    public Map<String, String> readDictionary(String filePath) {
        Map<String, String> dictionary = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            String key = "";
            StringBuilder value = new StringBuilder();

            while ((line = br.readLine()) != null) {
                line = line.strip();

                if (!line.startsWith("-") && !line.isEmpty()){
                    key = line.strip();
                    continue;
                }

                if (line.startsWith("-")){
                    value.append(line).append("\n");
                    continue;
                }

                if (line.isEmpty()) {
                    dictionary.put(key, value.toString());
                    key = "";
                    value = new StringBuilder();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            System.out.println("Từ: " + entry.getKey());
            System.out.println("Định nghĩa: " + entry.getValue());
            System.out.println();
        }
        return dictionary;
    }
}