package it.qbteam.pubsub;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RedisMessageSubscriber implements MessageListener {
 
    public static List<String> messageList = new ArrayList<String>();
    public int countere=0;
 
    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        countere++;
        System.out.println("Message received");
    }

public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(RedisMessageSubscriber.class, args);
    RedisMessageSubscriber rms = new RedisMessageSubscriber();
    while (rms.countere==0) {

        System.out.println("attendo");
        Thread.sleep(2000L);
    }
  }
}