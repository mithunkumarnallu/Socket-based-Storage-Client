/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketbasedstorageclient;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Mithun Nallu
 */
public class SocketBasedStorageClient {

    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    public static void main(String[] args) {
        new SocketBasedStorageClient();
    }

    public SocketBasedStorageClient() {
        Scanner keyboard = new Scanner(System.in);

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 9889);
        //Socket socket = new Socket( "linux02.cs.rpi.edu", 9889 );
            // Socket socket = new Socket( "128.113.126.29", 9889 );

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

            while (true) {
                // Get the radius from the user
                System.out.print("Enter some text: ");
                String command = keyboard.nextLine();

                if (command.startsWith("STORE")) {
                    command += "\n";
                    command += keyboard.nextLine();
                }
                // Send the radius to the server
                toServer.writeUTF(command);
                toServer.flush();
                System.out.println("Sent: " + command);

                // Get area from the server
                String op = fromServer.readUTF();   // BLOCK

                // Display to the text area
                System.out.println("OP received from server is " + op);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
