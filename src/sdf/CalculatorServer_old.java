package sdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer_old {

    public static void main(String[] args) throws IOException {
        
        // start a server with port number 
        System.out.println("Starting server on port 5000"); 
        ServerSocket server = new ServerSocket(5000); 

        // while loop to connect client to server 
        while (true) {
            
            //waiting for connection 
            System.out.println("Waiting for incoming connection...");
            
            // client connected to server 
            Socket conn = server.accept();
            System.out.println("Client connected");
            
            // receive and read the input from client 
            InputStream is = conn.getInputStream(); 
            ObjectInputStream ois = new ObjectInputStream(is); 
            //read the lines 
            String input = ois.readUTF(); 

            // split and typecast the operand and the operator into int
            String[] split = input.split(" "); 
            int oprd1 = Integer.parseInt(split[0]);
            int oprd2 = Integer.parseInt(split[2]);
            // to read from string to operator using unicode 
            char operator = split[1].charAt(0); 
            
            System.out.printf(">>> Received from client: %d operator:%s %d \n", oprd1, operator, oprd2);
            // calculate; plus or minus sign 
            int answer;
            if (operator == '+') {
                System.out.println("Calculating summation");
                answer = oprd1 + oprd2; 

            } else { // do minus
                System.out.println("Calculating substration");
                answer = oprd1 - oprd2; 
            } 
            // Change answer back to string 
            String stringAnswer = String.valueOf(answer); 

            // return the result to client using output 
            OutputStream os = conn.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os); 
                //write lines
            oos.writeUTF(stringAnswer);
            oos.flush();   

            // flush writer and connection 
            conn.close();    
        }
    }

}