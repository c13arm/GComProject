package com.MessageOrdering;

import com.Communication.Message;

public class CausalOrdering implements MessageOrdering{
    VectorClock clock;

    @Override
    public Message prepareMessage(Message message) {
        return null;
    }

    @Override
    public void receive(Message message) {

    }

    @Override
    public Message deliver() {
        return null;
    }
}
