package it.qbteam.movementtracker.subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface MovementSubscriber extends MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern);
}