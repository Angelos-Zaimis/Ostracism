package org.ostracismChain.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Node {

    private String id;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public Node(String id) {
        this.id = id;
    }

    public boolean connectToPeer(String host, int port) {
        try {
            socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to " + host + ":" + port);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (socket != null) socket.close();
            if (writer != null) writer.close();
            if (reader != null) reader.close();
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public String receiveMessage() throws IOException {
        return reader.readLine();
    }

    public String getId() {
        return id;
    }
}
