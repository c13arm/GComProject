package com.ChatClient;

import javax.swing.*;

/**
 * This class opens up a new window which is used when debugging
 */
public class DebugWindow extends JFrame {
    private JPanel debugPanel;
    public JButton sendButton;
    public JList messagesList;
    public JList holdMessageList;

    public DebugWindow() {
        messagesList.setBorder(BorderFactory.createTitledBorder("Message"));
        holdMessageList.setBorder(BorderFactory.createTitledBorder("Hold-back queue"));
        this.add(debugPanel);
    }
}
