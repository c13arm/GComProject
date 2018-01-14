package com.MessageOrdering;

import com.Communication.Message;

import javax.swing.*;

/**
 * Interface for the different types of message orderings(causal/unordered)
 */
public interface MessageOrdering {
    Message prepareMessage(Message message);
    void receive(Message message);
    Message deliver() throws InterruptedException;
    ListModel<Message> getHoldBackListModel();
}
