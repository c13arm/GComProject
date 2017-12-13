package com.ChattClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DebugWindow extends JFrame {
    private JPanel debugPanel;
    public JCheckBox delayCheckBox;
    public JButton sendButton;
    public JList messagesList;
    public JList holdMessageList;
    public JList sentMessageList;

    public DebugWindow() {
        messagesList.setBorder(BorderFactory.createTitledBorder("Message"));
        holdMessageList.setBorder(BorderFactory.createTitledBorder("Hold-back queue"));
        sentMessageList.setBorder(BorderFactory.createTitledBorder("Message ordering"));
        this.add(debugPanel);

        delayCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }
}
