package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    
    // members
    String dirPath = "./data2"; // this sits in my c drive 
    String fileName = "cookie.txt"; 

    List<String> cookieItems = null; 

    // function #1 
    public void readCookieFile() throws FileNotFoundException {
        cookieItems = new ArrayList<>();
        File file = new File(dirPath + File.separator + fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String readString; 

        try {
            while((readString = br.readLine()) != null) {
                cookieItems.add(readString); 
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    // function #2 
    public String returnCookie() {
        Random rand = new Random(); 
        if (cookieItems != null) { return cookieItems.get(rand.nextInt(cookieItems.size()));} 
        else {return "There is no cookie found.";}
    }
    // function #3 to show cookies
    public void showCookies() {
        if (cookieItems != null) {
            // another way to print string list instead of the usual for loop 
            cookieItems.forEach(ci -> System.out.println(ci));
        }
    }
    
}
