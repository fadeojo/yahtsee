package com.carleton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;


public class YahtzeeServer {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        System.out.println("Waiting for the client request");
        //creating socket and waiting for client connection
        Socket socket = server.accept();
//        Socket socket2 = server.accept();
        new PlayerServerConnection(socket).invoke();
        socket.close();
        //close the ServerSocket object
        server.close();
    }

    private static class PlayerServerConnection {
        private Socket socket;

        public PlayerServerConnection(Socket socket) {
            this.socket = socket;
        }

        public void invoke() throws IOException, ClassNotFoundException {
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//        ObjectInputStream ois2 = new ObjectInputStream(socket2.getInputStream());
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//        ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());

            //send start message to player1
            YahzeeGameState testMessage = new YahzeeGameState("player1", null, null, null);
            System.out.println("Message Received: " + testMessage.getCurrentPlayer());
            oos.writeObject(testMessage);
            while(true){
                //convert ObjectInputStream object to String
                YahzeeGameState message = (YahzeeGameState) ois.readObject();
                System.out.println("Message Received: " + message.getCurrentPlayer());

                //write object to Socket
                oos.writeObject(message);
                //terminate the server if client sends exit request
                if(message.isExit()) break;
            }
            System.out.println("Shutting down Socket server!!");
            // close resources
            ois.close();
            oos.close();
        }
    }
}

