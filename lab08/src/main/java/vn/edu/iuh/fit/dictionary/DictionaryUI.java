package vn.edu.iuh.fit.dictionary;

import com.example.englishlanguage.English;
import com.example.language.DictReader;
import com.example.language.Language;
import com.example.vietnameselanguage.VietNam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;

@Slf4j
@SpringBootApplication
public class DictionaryUI extends JFrame {

    private JTextField inputTextField;
    private JList<String> wordsList;
    private JTextArea infoTextArea;
    private JScrollPane listScrollPane;
    private JButton searchButton;
    Language language = null;
    DictReader dictReader = null;
    Map<String, String> dictMap = null;

    public DictionaryUI(@Value("${plugin.lang}") String lang) {

        switch (Objects.requireNonNull(lang)){
            case "english-vietnamese" -> language =  new English();

            case "vietnamese-english" -> language =  new VietNam();

            default -> {}
        }

        dictReader = new DictReader();
        dictMap = dictReader.readDictionary(language.getPATH());

        setTitle(lang);
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputTextField = new JTextField();
        inputTextField.setFont(new Font("Arial", Font.BOLD, 16));
        wordsList = new JList<>();

        wordsList.setListData(dictMap.keySet().toArray(String[]::new));

        infoTextArea = new JTextArea(5, 20);
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
        listScrollPane = new JScrollPane(wordsList);
        searchButton = new JButton("Search");

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputTextField, BorderLayout.CENTER);
        inputPanel.add(searchButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.WEST);
        add(infoTextArea, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = inputTextField.getText();

                log.info("** searchTerm: {}", searchTerm);

                String s = dictMap.get(searchTerm.strip());
                infoTextArea.setText(s);
            }
        });

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
            String lang = context.getEnvironment().getProperty("plugin.lang");
            DictionaryUI dictionaryUI = new DictionaryUI(lang);
            dictionaryUI.setLocationRelativeTo(null);
            dictionaryUI.setVisible(true);
        });
    }
}

