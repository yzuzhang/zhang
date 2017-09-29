package com.feicent.zhang.guava.eventbus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.common.eventbus.EventBus;

public class EventBusChat {
	//socket端口号
	private static int socketPort = 6180;
	
	/**
	 * cmd->输入telnet命令登录:
	 * telnet 127.0.0.1 6180
	 * 
	 */
    public static void main(String[] args) {
        EventBus channel = new EventBus();
        ServerSocket socket;
        try {
            socket = new ServerSocket(socketPort);
            while (true) {
                Socket connection = socket.accept();
                UserThread newUser = new UserThread(connection, channel);
                channel.register(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
