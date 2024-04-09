package vn.edu.iuh.fit.translate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Translate extends JFrame {
    private JTextField inputTextField;
    private JList<String> wordsList;
    private JTextArea infoTextArea;
    private JScrollPane listScrollPane;
    private JButton searchButton;

    public Translate() {
        setTitle("Dictionary");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        inputTextField = new JTextField();
        wordsList = new JList<>();
        infoTextArea = new JTextArea();
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
                Translate translate = new Translate();
                translate.setLocationRelativeTo(null);
                translate.setVisible(true);
            }
        });
    }
}

