package app;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class TestFrame7 extends JPanel {
    public TestFrame7(Map<String, String> slangMap) {
        setLayout(new BorderLayout());

        JPanel headerpanel = new JPanel();
        JLabel dslabel = new JLabel("Choose District");
        String[] dsarr = slangMap.keySet().toArray(new String[0]);
        JComboBox<String> dscbb = new JComboBox<>(dsarr);
        headerpanel.add(dslabel);
        headerpanel.add(dscbb);
        add(headerpanel, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        String[] ward1arr = slangMap.values().toArray(new String[0]);
        JList<String> wardlist = new JList<>(ward1arr);
        JScrollPane spward = new JScrollPane(wardlist);
        spward.setPreferredSize(new Dimension(200, 100));
        centerPanel.add(spward);

        JLabel additionalLabel = new JLabel("Additional Information:");
        centerPanel.add(additionalLabel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottompanel = new JPanel();
        bottompanel.setLayout(new BoxLayout(bottompanel, BoxLayout.LINE_AXIS));
        JButton okbtn = new JButton("Ok");
        JButton cancelbtn = new JButton("Cancel");
        okbtn.addActionListener(e -> System.out.println("Ok button clicked"));
        cancelbtn.addActionListener(e -> System.out.println("Cancel button clicked"));
        bottompanel.add(okbtn);
        bottompanel.add(Box.createRigidArea(new Dimension(25, 0)));
        bottompanel.add(cancelbtn);

        JPanel bottompanel2 = new JPanel();
        bottompanel2.setLayout(new BoxLayout(bottompanel2, BoxLayout.PAGE_AXIS));
        bottompanel2.add(Box.createRigidArea(new Dimension(0, 5)));
        bottompanel2.add(bottompanel);
        bottompanel2.add(Box.createRigidArea(new Dimension(0, 5)));
        add(bottompanel2, BorderLayout.PAGE_END);
    }

    private static void createAndShowGUI(Map<String, String> slangMap) {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Slang Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestFrame7 newContentPane = new TestFrame7(slangMap);
        newContentPane.setOpaque(true);

        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void runAppUI(Map<String, String> slangMap) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(slangMap));
    }
}
