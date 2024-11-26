package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SlangDictionary {
    public static Map<String, String> slangMap = new HashMap<>();
    public static List<String> searchHistory = new ArrayList<>();
    public static DefaultListModel<String> historyModel = new DefaultListModel<>();

    public static void main(String[] args) {
        loadSlangWords("slang.txt");
        SwingUtilities.invokeLater(SlangDictionary::createAndShowGUI);
    }

    private static void loadSlangWords(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("`");
                if (parts.length == 2) {
                    slangMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading slang words from file.");
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Slang Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        // Results Area
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane resultsScroll = new JScrollPane(resultsArea);
        mainPanel.add(resultsScroll, BorderLayout.CENTER);

        // History Panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setPreferredSize(new Dimension(150, 0));
        historyPanel.add(new JLabel("Search History"), BorderLayout.NORTH);
        JList<String> historyList = new JList<>(historyModel);
        historyPanel.add(new JScrollPane(historyList), BorderLayout.CENTER);
        mainPanel.add(historyPanel, BorderLayout.EAST);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // Search Menu
        JMenu searchMenu = new JMenu("Search");
        JMenuItem searchByDefinitionItem = new JMenuItem("Search by Definition");
        JMenuItem searchBySlangItem = new JMenuItem("Search by Slang");
        searchMenu.add(searchByDefinitionItem);
        searchMenu.add(searchBySlangItem);
        menuBar.add(searchMenu);

        // Manage Menu
        JMenu manageMenu = new JMenu("Manage");
        JMenuItem addSlangItem = new JMenuItem("Add Slang");
        JMenuItem editSlangItem = new JMenuItem("Edit Slang");
        JMenuItem deleteSlangItem = new JMenuItem("Delete Slang");
        JMenuItem resetItem = new JMenuItem("Reset");
        manageMenu.add(addSlangItem);
        manageMenu.add(editSlangItem);
        manageMenu.add(deleteSlangItem);
        manageMenu.add(resetItem);
        menuBar.add(manageMenu);

        // Quiz Menu
        JMenu quizMenu = new JMenu("Quiz");
        JMenuItem randomSlangQuizItem = new JMenuItem("Random Slang Quiz");
        JMenuItem randomDefinitionQuizItem = new JMenuItem("Random Definition Quiz");
        quizMenu.add(randomSlangQuizItem);
        quizMenu.add(randomDefinitionQuizItem);
        menuBar.add(quizMenu);

        frame.setJMenuBar(menuBar);

        // Action for Search by Definition
        searchByDefinitionItem.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(frame, "Enter keyword to search by definition:");
            if (keyword == null || keyword.trim().isEmpty()) return;

            StringBuilder results = new StringBuilder();
            for (Map.Entry<String, String> entry : slangMap.entrySet()) {
                if (entry.getValue().toLowerCase().contains(keyword.toLowerCase())) {
                    results.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
                }
            }

            if (results.length() ==  0) {
                resultsArea.setText("No results found for keyword: " + keyword);
            } else {
                resultsArea.setText(results.toString());
                searchHistory.add(keyword);
                historyModel.addElement(keyword);
            }
        });

        // Action for Search by Slang
        searchBySlangItem.addActionListener(e -> {
            String slang = JOptionPane.showInputDialog(frame, "Enter slang word to search:");
            if (slang == null || slang.trim().isEmpty()) return;

            String definition = slangMap.get(slang);
            if (definition != null) {
                resultsArea.setText(slang + " - " + definition);
                searchHistory.add(slang);
                historyModel.addElement(slang);
            } else {
                resultsArea.setText("No definition found for slang word: " + slang);
            }
        });

        // Action for Add Slang
        addSlangItem.addActionListener(e -> {
            String slang = JOptionPane.showInputDialog(frame, "Enter Slang Word:");
            if (slang == null || slang.trim().isEmpty()) return;

            String definition = JOptionPane.showInputDialog(frame, "Enter Definition:");
            if (definition == null || definition.trim().isEmpty()) return;

            if (slangMap.containsKey(slang)) {
                int choice = JOptionPane.showConfirmDialog(frame, "Slang word already exists. Overwrite?",
                        "Duplicate Slang Word", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) return;
            }

            slangMap.put(slang, definition);
            JOptionPane.showMessageDialog(frame, "Slang word added/ updated successfully.");
        });

        // Action for Edit Slang
        editSlangItem.addActionListener(e -> {
            String slang = JOptionPane.showInputDialog(frame, "Enter Slang Word to Edit:");
            if (slang == null || !slangMap.containsKey(slang)) {
                JOptionPane.showMessageDialog(frame, "Slang word not found.");
                return;
            }

            String definition = JOptionPane.showInputDialog(frame, "Enter New Definition:", slangMap.get(slang));
            if (definition != null) {
                slangMap.put(slang, definition);
                JOptionPane.showMessageDialog(frame, "Slang word updated successfully.");
            }
        });

        // Action for Delete Slang
        deleteSlangItem.addActionListener(e -> {
            String slang = JOptionPane.showInputDialog(frame, "Enter Slang Word to Delete:");
            if (slang == null || !slangMap.containsKey(slang)) {
                JOptionPane.showMessageDialog(frame, "Slang word not found.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this slang word?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                slangMap.remove(slang);
                JOptionPane.showMessageDialog(frame, "Slang word deleted successfully.");
            }
        });

        // Action for Reset
        resetItem.addActionListener(e -> {
            loadSlangWords("slang_original.txt");
            JOptionPane.showMessageDialog(frame, "Slang dictionary reset to original.");
        });

        // Action for Random Slang Quiz
        randomSlangQuizItem.addActionListener(e -> {
            List<String> keys = new ArrayList<>(slangMap.keySet());
            if (keys.size() < 4) {
                JOptionPane.showMessageDialog(frame, "Not enough slang words for the quiz.");
                return;
            }
            String randomSlang = keys.get(new Random().nextInt(keys.size()));
            List<String> options = new ArrayList<>(keys);
            options.remove(randomSlang);
            Collections.shuffle(options);
            options = options.subList(0, 3); // Get 3 other slang words
            options.add(randomSlang); // Add the random slang word to the options
            Collections.shuffle(options); // Shuffle the options

            String message = "Which of the following is the meaning of: " + randomSlang + "?";
            int answer = JOptionPane.showOptionDialog(frame, message, "Random Slang Quiz",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    options.toArray(), options.get(0));

            if (options.get(answer).equals(randomSlang)) {
                JOptionPane.showMessageDialog(frame, "Correct!");
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong! The correct answer was: " + randomSlang);
            }
        });

        // Action for Random Definition Quiz
        randomDefinitionQuizItem.addActionListener(e -> {
            List<String> keys = new ArrayList<>(slangMap.keySet());
            if (keys.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No slang words available for the quiz.");
                return;
            }
            String randomSlang = keys.get(new Random().nextInt(keys.size()));
            String definition = slangMap.get(randomSlang);
            
            List<String> options = new ArrayList<>(keys);
            options.remove(randomSlang);
            Collections.shuffle(options);
            options = options.subList(0, 3); // Get 3 other slang words
            options.add(randomSlang); // Add the correct slang word to the options
            Collections.shuffle(options); // Shuffle the options

            String message = "Which slang word corresponds to the definition: " + definition + "?";
            int answer = JOptionPane.showOptionDialog(frame, message, "Random Definition Quiz",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    options.toArray(), options.get(0));

            if (options.get(answer).equals(randomSlang)) {
                JOptionPane.showMessageDialog(frame, "Correct!");
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong! The correct answer was: " + randomSlang);
            }
        });

        frame.setVisible(true);
    }
}