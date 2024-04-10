package vn.edu.iuh.fit.dictionary;

import com.example.englishlanguage.English;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
@Component
public class Dictionary extends JFrame {
    private final JTextField inputTextField;
    private final JList<String> wordsList;
    private JTextArea infoTextArea;
    private JScrollPane listScrollPane;
    private JButton searchButton;

    English english = new English();

    public Dictionary() {
        setTitle("Dictionary");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        inputTextField = new JTextField();
        inputTextField.setFont(new Font("Arial", Font.BOLD, 16));
        wordsList = new JList<>();
        wordsList.setListData(english.getListWord().toArray(String[]::new));

        infoTextArea = new JTextArea(5, 20);
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
        listScrollPane = new JScrollPane(wordsList);
        searchButton = new JButton("Search");

        // Layout
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputTextField, BorderLayout.CENTER);
        inputPanel.add(searchButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.WEST);
        add(infoTextArea, BorderLayout.CENTER);

        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = inputTextField.getText();

                log.info("** searchTerm: {}", searchTerm);
                String s = english.getDictionary().get(searchTerm.strip());
                log.info("** infoTextArea: {}", s);
                infoTextArea.setText(s);
                // Call the method in the kernel to search for the term and update UI
                // updateWordsList(kernel.search(searchTerm));
            }
        });
    }

    // Method to update the words list with new data
    private void updateWordsList(String[] words) {
        wordsList.setListData(words);
    }

    public static void main(String[] args) {
        // Create an instance of the UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PluginManager.loadPlugin("EnglishLanguage/modun.jar");
                Dictionary dictionary = new Dictionary();
                dictionary.setLocationRelativeTo(null);
                dictionary.setVisible(true);
            }
        });
    }
}

