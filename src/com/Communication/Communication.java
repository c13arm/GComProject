package com.Communication;

import com.GroupManagement.User;

import java.util.List;

public interface Communication {
    void multicast(List<User> members, Message mess);
    void receive(Message mess);

}
