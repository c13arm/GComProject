package com.MessageOrdering;

import com.Communication.Message;

public interface MessageOrdering {
    Message prepareMessage(Message message);
    void receive(Message message);
    Message deliver() throws InterruptedException;
}
