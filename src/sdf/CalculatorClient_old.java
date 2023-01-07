package sdf;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CalculatorClient_old {

    public static void main(String[] args) throws IOException {
        
        // client connection with port number 5000
        Socket conn = new Socket("127.0.0.1", 5000); 
        System.out.println("Connected to server on localhost: 5000");

        //Get math problem from console eg. > 3 + 4 >> use loop to keep waiting for next consule input 
        Console cons = System.console(); 
        
        // will keep asking for input if it is true 
        while (true) {
            String line = cons.readLine("Please input an operand followed by operator (+/-) followed by operand (space required): ");
            
            // close connection if console reads "exit"  
            if (line == "exit") {
                break; // terminate and exit a loop 
            } 
            // output for server
            OutputStream os = conn.getOutputStream(); 
            ObjectOutputStream oos = new ObjectOutputStream(os); 
            //write lines 
            oos.writeUTF(line); 
            oos.flush(); // flush writer 

            // Get result from server with reader 
            InputStream is = conn.getInputStream(); 
            ObjectInputStream ois = new ObjectInputStream(is); 
            //read the lines 
            String answer = ois.readUTF(); 
            // print result 
            System.out.printf("The answer is: %s\n", answer);        
        }   
        //  connection only ends when user key in "exit"
        conn.close(); 
    }
    
}
