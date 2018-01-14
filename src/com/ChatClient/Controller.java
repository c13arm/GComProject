package com.ChatClient;

import com.Communication.*;
import com.GroupManagement.User;
import com.MessageOrdering.CausalOrdering;
import com.MessageOrdering.UnorderedOrdering;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This class's purpose is to update the GUI(view) with information from the model class
 */
public class Controller {

    private View theView;
    private Model theModel;

    public Controller(View theView, Model theModel) {
        this.theView = theView;
        this.theModel = theModel;
        addListeners();
    }

    private void addListeners()
    {
        theView.addCreateUserButtonListener(actionEvent -> {
            theView.createUserInfo();
            try {
                theModel.user = new User(theView.username);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.exit(1);
            }
            try
            {
                List<String> groups = theModel.gm.getGroups();
                for(String g : groups) {
                    theView.listModelAvailableGroups.addElement(g);
                }
            } catch (RemoteException e)
            {
                e.printStackTrace();
                System.exit(1);
            }
        });

        theView.addJoinGroupButtonListener(actionEvent -> {
            theView.joinGroupInfo();
            try {
                theModel.group = theModel.gm.joinGroup(theView.joinGroupField.getText(), theModel.user);
            } catch (RemoteException | UnknownHostException e) {
                e.printStackTrace();
                System.exit(1);
            }
            for (User u : theModel.group.getMembers())
            {
                System.out.println(u.getName());
                theView.listModelUser.addElement(u.getName());
            }
            theModel.worker = new ReceiveWorker(theModel.group, theView.listModelMessagesChat, theView.listModelUser, theView.frameDebug);
            theModel.worker.execute();
        });

        theView.addCreateGroupButtonListener(actionEvent -> {
            theView.createGroupInfo();

            if(theView.messageOrderingBox.getSelectedItem().equals("Unordered")) {
                theModel.order = new UnorderedOrdering();
            } else if (theView.messageOrderingBox.getSelectedItem().equals("Causal ordering")) {
                theModel.order = new CausalOrdering();
            }

            if(theView.communicationTypeBox.getSelectedItem().equals("Non reliable")) {
                theModel.communicationModule = new NonReliableMulticastModule(theModel.order);
            } else if(theView.communicationTypeBox.getSelectedItem().equals("Basic reliable")) {
                theModel.communicationModule = new ReliableMulticastModule(theModel.order);
            } else if(theView.communicationTypeBox.getSelectedItem().equals("Three based reliable")){
                theModel.communicationModule = new TreeBasedReliableMultiCastModule(theModel.order);
            }
            try {
                theModel.group = theModel.gm.createGroup(theView.createGroupField.getText(), theModel.user, theModel.communicationModule);
            } catch (RemoteException e) {
                e.printStackTrace();
                System.exit(1);
            }
            for (User u : theModel.group.getMembers())
            {
                theView.listModelUser.addElement(u.getName());
            }
            theModel.worker = new ReceiveWorker(theModel.group, theView.listModelMessagesChat, theView.listModelUser, theView.frameDebug);
            theModel.worker.execute();
        });

        theView.addLeaveChatButtonListener(actionEvent -> {
            theModel.worker.cancel(true);
            theModel.group.memberLeft(theModel.user);
            theView.leaveChatInfo();
        });

        theView.addDebugButtonListener(actionEvent -> {
            theView.debugInfo();
            theView.frameDebug.holdMessageList.setModel(theModel.group.getHoldBackListModel());
        });

        theView.addDebugSendButtonListener(actionEvent -> {
            if(!theView.listModelMessages.isEmpty())
            {
                int index = theView.frameDebug.messagesList.getSelectedIndex();
                if(index >= 0)
                {
                    Message mess = (Message) theView.frameDebug.messagesList.getSelectedValue();
                    theModel.group.multicast(mess);
                    theView.listModelMessages.removeElementAt(index);
                }
            }
        });

        theView.addChatButtonListener(actionEvent -> {
            Message mess = theModel.group.createMessage(theView.chatField.getText(), theModel.user);
            if(theView.frameDebug.isVisible())
            {
                theView.listModelMessages.addElement(mess);
            }
            else
            {
                theView.listModelMessagesChat.addElement("("+theView.username+"): " + theView.chatField.getText());
                theView.messagesListChat.ensureIndexIsVisible(theView.messagesListChat.getModel().getSize()-1);
                theView.chatField.setText("");
                theModel.group.multicast(mess);
            }
        });


    }
}
