package com.ChattClient;

import com.Communication.Communication;
import com.GroupManagement.Group;
import com.GroupManagement.GroupManagement;
import com.GroupManagement.User;
import com.MessageOrdering.MessageOrdering;

public class Model {
    public GroupManagement gm;
    public User user;
    public Group group;
    public ReceiveWorker worker;
    public MessageOrdering order;
    public Communication communicationModule;

    public Model() {
        gm = new GroupManagement("localhost");
    }
}
