package sdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {

    public static void main(String[] args) throws IOException {
        
        // #2 start a server with port number , taking care of IOException
        int port = 3000; 
        System.out.printf("Starting server on port number %d\n", port);

        ServerSocket server = new ServerSocket(port); 

        // #3 using a loop, run the server, allowing >1 read attempt from the same client 
        while(true) {

            System.out.println("Waiting for a new connection...");
            Socket sock = server.accept();
            System.out.println("New client connection");

            // #8 execute request from client 
            try {
                while (true) {
                    boolean stop = false; 
                    System.out.println("Got a connection");

                    OutputStream os = sock.getOutputStream();  
                    DataOutputStream dos = new DataOutputStream(os); 
                    InputStream is = sock.getInputStream();
                    DataInputStream dis = new DataInputStream(is); 

                    // this while loop is for running client request 
                    while (!stop) {
                        String oper = dis.readUTF(); 

                        switch (oper) {
                            case Constants.EXIT: // in client side, only exit and operator are written as UTF 
                                stop = true; 
                                break; 
                            case Constants.PLUS: // respond to client with result 
                                float oprd1 = dis.readFloat(); 
                                float oprd2 = dis.readFloat(); 
                                float answer = oprd1 + oprd2; 
                                dos.writeFloat(answer); 
                                dos.flush(); 
                                break; 
                            case Constants.MINUS: 
                                float oprdno1 = dis.readFloat(); 
                                float oprdno2 = dis.readFloat(); 
                                float result = oprdno1 - oprdno2; 
                                dos.writeFloat(result); 
                                dos.flush(); 
                                break;
                            default: 
                        }
                    }
                    try {
                        System.out.println("Server trying to close socket...");
                        sock.close();
                        System.out.println("Socket connection closed");
                        // once stop - true, we also activate break to terminate while true loop 
                        break; 
                    } catch (IOException ex) {
                        System.out.println("Error message: IOException1");
                    }
                }
            } catch (IOException ex) {
                System.err.println("Error message: IOException2");
            }
        }
    }
    
}
