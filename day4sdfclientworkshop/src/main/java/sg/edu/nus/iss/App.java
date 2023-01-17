package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket sock = new Socket("localhost", 12345);
        
        try (OutputStream os = sock.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os); 
            DataOutputStream dos = new DataOutputStream(bos); 

            Console cons = System.console(); 
            String readInput = "", receivedMsg = "";

            try (InputStream is = sock.getInputStream()) {
                BufferedInputStream bis = new BufferedInputStream(is); 
                DataInputStream dis = new DataInputStream(bis);

                while (!readInput.equals("close")) {
                    readInput = cons.readLine("Enter a command: ");
                    dos.writeUTF(readInput);
                    dos.flush(); // have to flush otherwise it will not send over  

                    receivedMsg = dis.readUTF(); 
                    System.out.println(receivedMsg);
                }
                dis.close();
                bis.close();
                is.close();
            } catch (EOFException ex) {
                sock.close();
            }
            dos.close();
            bos.close();
            os.close();
        } catch (EOFException ex) {
            sock.close(); 
        }
    }
}
