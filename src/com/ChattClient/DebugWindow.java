package com.ChattClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
