import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel cardPanel, mainPanel, userJoinPanel, userCreatePanel, buttonPanel, borderTestPanel;
    private JLabel usernameLabel, joinGroupLabel, createGroupLabel, availableGroupsLabel;
    private JButton btn2, createUserButton, joinGroupButton, createGroupButton;
    private JTextField usernameField, joinGroupField, createGroupField;
    private JTextArea availableGroupsArea;
    private CardLayout cardLayout = new CardLayout();

    public GUI() {
        setTitle("GCom");
        setSize(700, 600);
        cardPanel = new JPanel();
        buttonPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        mainPanel = new JPanel();

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(100,25));
        createUserButton = new JButton("Create user");
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(createUserButton);
        cardPanel.add(mainPanel, "1");

        createUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "2");
            }
        });

        userJoinPanel = new JPanel();
        joinGroupLabel = new JLabel("Join group:");
        joinGroupField = new JTextField();
        joinGroupField.setPreferredSize(new Dimension(100, 25));
        joinGroupButton = new JButton("Join");
        userJoinPanel.add(joinGroupLabel);
        userJoinPanel.add(joinGroupField);
        userJoinPanel.add(joinGroupButton);

        userCreatePanel = new JPanel();
        createGroupLabel = new JLabel("Create group");
        createGroupField = new JTextField();
        createGroupField.setPreferredSize(new Dimension(100, 25));
        createGroupButton = new JButton("Create");
        userCreatePanel.add(createGroupLabel);
        userCreatePanel.add(createGroupField);
        userCreatePanel.add(createGroupButton);

        availableGroupsArea = new JTextArea();
        availableGroupsArea.setPreferredSize(new Dimension(200,200));
        availableGroupsLabel = new JLabel("Available groups");

        borderTestPanel = new JPanel();
        borderTestPanel.add(joinGroupLabel, BorderLayout.NORTH);
        borderTestPanel.add(joinGroupField, BorderLayout.NORTH);
        borderTestPanel.add(joinGroupButton, BorderLayout.NORTH);
        borderTestPanel.add(createGroupLabel, BorderLayout.NORTH);
        borderTestPanel.add(createGroupField, BorderLayout.NORTH);
        borderTestPanel.add(createGroupButton, BorderLayout.NORTH);
        borderTestPanel.add(availableGroupsLabel, BorderLayout.CENTER);
        borderTestPanel.add(availableGroupsArea, BorderLayout.SOUTH);
        //borderTestPanel.add(userJoinPanel, BorderLayout.NORTH);

        //borderTestPanel.add(userCreatePanel, BorderLayout.SOUTH);
        //borderTestPanel.add(availableGroupsLabel);
        //borderTestPanel.add(availableGroupsArea, BorderLayout.SOUTH);
        cardPanel.add(borderTestPanel, "2");
        //cardPanel.add(userJoinPanel, "2");
        //cardPanel.add(userCreatePanel, "2");
        //cardPanel.add(userCreatePanel, "2");
        btn2 = new JButton("Show Card 2");
        btn2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "2");
            }
        });
        buttonPanel.add(btn2);
        add(cardPanel, BorderLayout.CENTER);
        //add(userCreatePanel, BorderLayout.CENTER);
        //add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                GUI frame = new GUI();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}