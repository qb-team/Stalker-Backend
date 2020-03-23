package it.qbteam.pubsub;

import java.util.ArrayList;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {
 
    public static List<String> messageList = new ArrayList<String>();
 
    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        System.out.println("Message received: " + new String(message.getBody()));
    }
}