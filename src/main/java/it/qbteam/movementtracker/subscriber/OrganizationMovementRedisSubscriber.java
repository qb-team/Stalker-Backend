package it.qbteam.movementtracker.subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import java.util.List;

import org.springframework.stereotype.Service;

public class OrganizationMovementRedisSubscriber extends OrganizationMovementSubscriber {
    public static List<String> messageList = new ArrayList<String>();
    public int countere = 0;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        countere++;
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("provaFile.txt"));
            writer.write(message.toString());
            writer.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}