package sdf;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CalculatorClient {

    public static void main(String[] args) throws IOException {
        
        // #5 set up console reading of host and port number 
        if (args.length < 2) {
            System.out.println("Usage: host, port");
            System.exit(1);
        }
        String host = args[0]; 
        int port = Integer.parseInt(args[1]);

        // #4 set up client connection to server port number
        System.out.printf("Connecting to server %s on port %d\n", host, port);
        Socket sock = new Socket(host, port); 
        System.out.println("Connected to server");

        // #6 creating IOStream, provide set up for the while loop
        OutputStream os = sock.getOutputStream();  
        DataOutputStream dos = new DataOutputStream(os); 
        InputStream is = sock.getInputStream();
        DataInputStream dis = new DataInputStream(is); 

        Console cons = System.console(); 
        boolean stop = false; 

        // #7 while loop to consider >1 console reading by client 
        while (!stop) {

            // read console and store the values             
            String input = cons.readLine("> "); 
            String[] terms = input.trim().split(" "); 
            
            switch (terms.length) {
                case 1: // "exit"  >> write to server so that they know to end as well? 
                    dos.writeUTF(Constants.EXIT); 
                    dos.flush();
                    stop = true; 
                    break; 

                case 3: // oprd operator oprd >> write in + 3 4 order for easier read in server
                    dos.writeUTF(terms[1]); 
                    dos.writeFloat(Float.parseFloat(terms[0]));
                    dos.writeFloat(Float.parseFloat(terms[2])); 
                    dos.flush();

                    // #9 read the result from server 
                    float result = dis.readFloat();
                    System.out.printf("Result: %f\n", result);
                    break; 
                default: // do nth 
            }
        }
        // close the connection after told to exit 
        try {
            sock.close();
        } catch (IOException ex) {
            System.err.println("Error message: IOException3");
        }
    }
    
}
