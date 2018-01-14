package com.ChattClient;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Main {
    static View theView;
    static Controller theController;
    static Model theModel;

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        EventQueue.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                theView = new View();
                theView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                theView.setVisible(true);
            }
        });

        theModel = new Model();
        theController = new Controller(theView, theModel);
    }
}
