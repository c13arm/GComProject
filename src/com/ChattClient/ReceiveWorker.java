package com.ChattClient;

import com.Communication.Message;
import com.Communication.MessageType;
import com.GroupManagement.Group;

import javax.swing.*;
import java.util.List;

public class ReceiveWorker extends SwingWorker<Void, Message>
{
    private Group group;
    private DefaultListModel listModelMessagesChat;
    private DefaultListModel userModel;
    private DebugWindow frameDebug;
    private DefaultListModel holdMessages;

    ReceiveWorker(Group group, DefaultListModel listModelMessagesChat, DefaultListModel userModel, DebugWindow frameDebug, DefaultListModel holdMessages)
    {
        this.group = group;
        this.listModelMessagesChat = listModelMessagesChat;
        this.userModel = userModel;
        this.frameDebug = frameDebug;
        this.holdMessages = holdMessages;
    }

    @Override
    protected Void doInBackground() throws Exception
    {
        while (!isCancelled())
        {
            Message newMess = group.deliver();
            publish(newMess);
        }
        return null;
    }

    @Override
    protected void process(List<Message> chunks)
    {
        for (Message m : chunks)
        {
            if (m.getMessageType() == MessageType.MESS)
            {
                if(frameDebug.isVisible())
                {
                }
                listModelMessagesChat.addElement("(" + m.getSender().getName() + "): " + m.getMess());
            }
            else if (m.getMessageType() == MessageType.JOIN)
            {
                userModel.addElement(m.getSender().getName());
                listModelMessagesChat.addElement(m.getSender().getName() + " has joined the group.");
            }
            else if (m.getMessageType() == MessageType.LEAVE)
            {
                userModel.removeElement(m.getSender().getName());
                listModelMessagesChat.addElement(m.getSender().getName() + " has left the group.");
            }

        }
    }
}
