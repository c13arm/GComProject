package com.MessageOrdering;

import com.Communication.Message;

import javax.swing.*;

public interface MessageOrdering {
    Message prepareMessage(Message message);
    void receive(Message message);
    Message deliver() throws InterruptedException;
    ListModel<Message> getHoldBackListModel();
}
