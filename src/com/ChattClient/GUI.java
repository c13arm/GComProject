package com.ChattClient;

import com.Communication.*;
import com.GroupManagement.Group;
import com.GroupManagement.GroupManagement;
import com.GroupManagement.User;
import com.MessageOrdering.CausalOrdering;
import com.MessageOrdering.MessageOrdering;
import com.MessageOrdering.UnorderedOrdering;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class GUI extends JFrame {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JButton createUserButton;
    private JLabel usernameLabel;
    private JTextField joinGroupField;
    private JButton joinGroupButton;
    private JLabel joinGroupLabel;
    private JTextField createGroupField;
    private JButton createGroupButton;
    private JLabel createGroupLabel;
    private JTextArea availableGroupsArea;
    private JPanel usernamePanel;
    private JPanel joinCreatePanel;
    private JPanel chatPanel;
    private JTextField chatField;
    private JButton chatButton;
    private JButton leaveChatButton;
    private JButton debugButton;
    private JButton backButton;
    private JPanel cardPanel;
    private JList userList;
    private JList messagesListChat;
    private JList availableGroupsList;
    private JRadioButton orderedRadioButton;
    private JComboBox communicationTypeBox;
    private JComboBox messageOrderingBox;

    private CardLayout c1 = (CardLayout)cardPanel.getLayout();

    private String username;

    JFrame frame;

    DebugWindow frameDebug;
    DefaultListModel listModelMessages, listModelHeldMessages, listModelSentMessages, listModelMessagesChat, listModelUser, listModelAvailableGroups;

    //ÄNDRA
    User user;
    Group group;
    ReceiveWorker worker;
    GroupManagement gm;

    public GUI() {
        frame = this;
        setTitle("GCom");
        setSize(700, 600);
        listModelMessages = new DefaultListModel<Message>();
        listModelHeldMessages = new DefaultListModel<Message>();
        listModelSentMessages = new DefaultListModel<Message>();
        listModelMessagesChat = new DefaultListModel();
        listModelUser = new DefaultListModel();
        listModelAvailableGroups = new DefaultListModel();
        userList.setBorder(BorderFactory.createTitledBorder("Users"));
        messagesListChat.setBorder(BorderFactory.createTitledBorder("Chat"));
        availableGroupsList.setBorder(BorderFactory.createTitledBorder("Available groups"));

        gm = new GroupManagement("localhost");

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                username = usernameField.getText();
                userList.setModel(listModelUser);
                availableGroupsList.setModel(listModelAvailableGroups);
                //listModelUser.addElement(username);
                c1.show(cardPanel, "Card2");
                frame.setTitle("GCom - " + username);
                frameDebug = new DebugWindow();

                communicationTypeBox.addItem("Non reliable");
                communicationTypeBox.addItem("Basic reliable");
                communicationTypeBox.addItem("Three based reliable");
                messageOrderingBox.addItem("Unordered");
                messageOrderingBox.addItem("Causal ordering");

                try {
                    user = new User(username);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                try
                {
                    List<String> groups = gm.getGroups();
                    for(String g : groups) {
                        listModelAvailableGroups.addElement(g);
                    }
                } catch (RemoteException e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
            }

        });
        joinGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                c1.show(cardPanel, "Card3");
                frame.setTitle("GCom - " + username);
                messagesListChat.setModel(listModelMessagesChat);



                try {
                    group = gm.joinGroup(joinGroupField.getText(), user);
                } catch (RemoteException | UnknownHostException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                for (User u : group.getMembers())
                {
                    System.out.println(u.getName());
                    listModelUser.addElement(u.getName());
                }
                worker = new ReceiveWorker(group, listModelMessagesChat, listModelUser);
                worker.execute();            }
        });
        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                c1.show(cardPanel, "Card3");
                frame.setTitle("GCom - " + username);
                messagesListChat.setModel(listModelMessagesChat);

                MessageOrdering order = null;
                if(messageOrderingBox.getSelectedItem().equals("Unordered")) {
                    order = new UnorderedOrdering();
                } else if (messageOrderingBox.getSelectedItem().equals("Causal ordering")) {
                    order = new CausalOrdering();
                }

                Communication communicationModule = null;
                if(communicationTypeBox.getSelectedItem().equals("Non reliable")) {
                    communicationModule = new NonReliable(order);
                } else if(communicationTypeBox.getSelectedItem().equals("Basic reliable")) {
                    communicationModule = new Reliable(order);
                } else if(communicationTypeBox.getSelectedItem().equals("Three based reliable")){
                    communicationModule = new TreeBased(order);
                }
                try {
                    System.out.println(createGroupField.getText());
                    group = gm.createGroup(createGroupField.getText(), user, communicationModule);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                for (User u : group.getMembers())
                {
                    listModelUser.addElement(u.getName());
                }
                worker = new ReceiveWorker(group, listModelMessagesChat, listModelUser);
                worker.execute();
            }
        });
        leaveChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                worker.cancel(true);
                group.memberLeft(user);
                listModelUser.clear();
                listModelMessagesChat.clear();
                listModelMessages.clear();
                listModelHeldMessages.clear();
                listModelSentMessages.clear();
                c1.show(cardPanel, "Card2");
                frame.setTitle("GCom - " + username);
            }
        });
        debugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frameDebug.setTitle("GCom debugging - " + username);
                frameDebug.setSize(700, 600);
                frameDebug.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frameDebug.setVisible(true);
                messagesListChat.setModel(listModelMessagesChat);

                frameDebug.sendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(!listModelMessages.isEmpty())
                        {
                            String selected = frameDebug.messagesList.getSelectedValue().toString();
                            int index = frameDebug.messagesList.getSelectedIndex();
                            if(index >= 0)
                            {
                                if(frameDebug.delayCheckBox.isSelected())
                                {
                                    listModelHeldMessages.addElement(selected);
                                    listModelMessages.removeElementAt(index);

                                    // Unordered/Causal
                                }
                                else
                                {
                                    listModelHeldMessages.addElement(selected);
                                    listModelMessages.removeElementAt(index);

                                    // Unordered/Causal
                                }

                            }
                        }
                    }
                });
            }
        });
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //FIXA TRÅDNING
                Message mess = group.createMessage(chatField.getText(), user);
                if(frameDebug.isVisible())
                {

                    frameDebug.messagesList.setModel(listModelMessages);
                    frameDebug.holdMessageList.setModel(listModelHeldMessages);
                    frameDebug.sentMessageList.setModel(listModelSentMessages);
                    listModelMessages.addElement(mess);
                }
                else
                {
                    listModelMessagesChat.addElement("("+username+"): " + chatField.getText());
                    group.multicast(mess);
                }
            }
        });

        add(mainPanel);

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