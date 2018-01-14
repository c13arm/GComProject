package com.ChattClient;

import com.Communication.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JButton createUserButton;
    public JTextField joinGroupField;
    private JButton joinGroupButton;
    public JTextField createGroupField;
    private JButton createGroupButton;
    public JTextField chatField;
    private JButton chatButton;
    private JButton leaveChatButton;
    private JButton debugButton;
    private JPanel cardPanel;
    private JList<String> userList;
    public JList<String> messagesListChat;
    private JList<String> availableGroupsList;
    public JComboBox<String> communicationTypeBox;
    public JComboBox<String> messageOrderingBox;

    private CardLayout c1 = (CardLayout)cardPanel.getLayout();

    public String username;

    private JFrame frame;

    public DebugWindow frameDebug;
    public DefaultListModel<Message> listModelMessages;
    private DefaultListModel<Message> listModelHeldMessages;
    public DefaultListModel<String> listModelMessagesChat;
    public DefaultListModel<String> listModelUser;
    public DefaultListModel<String> listModelAvailableGroups;

    public View() {
        initView();
        add(mainPanel);
    }

    private void initView() {
        frame = this;
        setTitle("GCom");
        setSize(700, 600);
        frameDebug = new DebugWindow();
        listModelMessages = new DefaultListModel<>();
        listModelHeldMessages = new DefaultListModel<>();
        listModelMessagesChat = new DefaultListModel<>();
        listModelUser = new DefaultListModel<>();
        listModelAvailableGroups = new DefaultListModel<>();
        userList.setBorder(BorderFactory.createTitledBorder("Users"));
        messagesListChat.setBorder(BorderFactory.createTitledBorder("Chat"));
        availableGroupsList.setBorder(BorderFactory.createTitledBorder("Available groups"));
    }

    public void addCreateUserButtonListener(ActionListener createUser) {
        createUserButton.addActionListener(createUser);
    }

    public void addJoinGroupButtonListener(ActionListener joinGroup) {
        joinGroupButton.addActionListener(joinGroup);
    }

    public void addCreateGroupButtonListener(ActionListener createGroup) {
        createGroupButton.addActionListener(createGroup);
    }

    public void addLeaveChatButtonListener(ActionListener leaveChat) {
        leaveChatButton.addActionListener(leaveChat);
    }

    public void addDebugButtonListener(ActionListener debug) {
        debugButton.addActionListener(debug);
    }

    public void addDebugSendButtonListener(ActionListener debugSend) {
        frameDebug.sendButton.addActionListener(debugSend);
    }

    public void addChatButtonListener(ActionListener chat) {
        chatButton.addActionListener(chat);
    }

    public void createUserInfo() {
        username = usernameField.getText();
        userList.setModel(listModelUser);
        availableGroupsList.setModel(listModelAvailableGroups);
        c1.show(cardPanel, "Card2");
        frame.setTitle("GCom - " + username);

        communicationTypeBox.addItem("Non reliable");
        communicationTypeBox.addItem("Basic reliable");
        communicationTypeBox.addItem("Three based reliable");
        messageOrderingBox.addItem("Unordered");
        messageOrderingBox.addItem("Causal ordering");
    }

    public void joinGroupInfo() {
        c1.show(cardPanel, "Card3");
        frame.setTitle("GCom - " + username);
        messagesListChat.setModel(listModelMessagesChat);
    }

    public void createGroupInfo() {
        c1.show(cardPanel, "Card3");
        frame.setTitle("GCom - " + username);
        messagesListChat.setModel(listModelMessagesChat);
    }

    public void leaveChatInfo() {
        listModelUser.clear();
        listModelMessagesChat.clear();
        listModelMessages.clear();
        listModelHeldMessages.clear();
        c1.show(cardPanel, "Card2");
        frame.setTitle("GCom - " + username);
    }

    public void debugInfo() {
        frameDebug.setTitle("GCom debugging - " + username);
        frameDebug.setSize(700, 600);
        frameDebug.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameDebug.setVisible(true);
        frameDebug.messagesList.setModel(listModelMessages);
        messagesListChat.setModel(listModelMessagesChat);
    }
}
