package CN1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*; 
import java.io.*; 
import javax.swing.*;
import java.util.*;
  
public class Serverx  
{ 
 
    static Vector<ClientH> clients = new Vector<>(); 
    static Vector<MessageQ> messageQ = new Vector<>();
    static int i = 0; 
    //public String username;
  
    public static void main(String[] args) throws IOException  
    { 
    	JFrame f=new JFrame("Chat Server");
		JLabel label = new JLabel("SERVER");
		label.setBounds(50, 10, 300, 40);

		JTextArea ta = new JTextArea();
		ta.setBounds(50, 60, 400, 200);
		ta.setEditable(false);

		f.add(ta);
		f.add(label);

		f.setSize(500,500);
		f.setLayout(null);
		f.setVisible(true); 

        ServerSocket ss = new ServerSocket(6666); 
        System.out.println("Server started and listening for client...");
          
        Socket s; 
        InetAddress local = InetAddress.getLocalHost();
        System.out.println("System IP Address : " + 
                      (local.getHostAddress()).trim());
        ta.append("SERVER STARTED...\n");
        ta.append("Server's IP Address : " + (local.getHostAddress()).trim());
        ta.append("\n\nConnected clients:\n");

        try {
        	
        
        while (true)  
        { 
            s = ss.accept(); 
            i++;
  			
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String username;
            int flag_duplicate = 0;
            username = dis.readUTF();
            for (ClientH obj : clients) {
            	if(obj.name.equals(username) && obj.isloggedin == true)
            	{
            	System.out.println("Username already in use. Please login with a different name");
            	try {
            		flag_duplicate = 1;
            		dis.close();
                	dos.close();
            	s.close();
            	break;}
            	catch (SocketException e) {
            		e.printStackTrace();
            	}
            	
            	
            	}
            	
            	if (obj.name.equals(username) && obj.isloggedin == false) {
            		System.out.println("Reconnecting an existing user");
            		clients.remove(obj);
            		
            		break;
            	}
            	
            }
            for(MessageQ off : messageQ)
            {
            	if(off.name.equals(username)) {
            		System.out.println("Offline messages from " + off.sender + " \n" + off.message);
            	}
            }
            
            if (flag_duplicate != 1) {
            ClientH mtch = new ClientH(s, username, dis, dos,s.getInetAddress().toString());
            System.out.println("Client connected : " + username); 
            Thread t = new Thread(mtch);
            clients.add(mtch); 
            System.out.println("All connected clients are : " + clients);
            //ta.setText("");
            
            ta.append(Integer.toString(i) + "). " + username + "    IP: " + s.getInetAddress().toString() + "\n"); 
            t.start(); 
            }
        } 
      } catch (IOException e) {
    	  e.printStackTrace();
      }
        
    } 
} 

class MessageQ {

	Socket s;
	String message;
	String sender;
	
	String name;

	public MessageQ(Socket s, String message,String sender,String name) {
		this.s = s;
		this.message = message;
		
		this.name = name;
		this.sender = sender;
	}

}
  
class ClientH implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    String name; 
    DataInputStream dis; 
    DataOutputStream dos;
    public FileInputStream file_in;
    String ipadd;
    Socket s; 
    boolean isloggedin; 
      
    public ClientH(Socket s, String name, 
                            DataInputStream dis, DataOutputStream dos, String ipadd) { 


        this.dis = dis; 
        this.dos = dos; 
        this.name = name; 
        this.s = s;
        this.ipadd = ipadd;
        this.isloggedin=true; 
        
    } 
  
    @Override
    public void run() { 
        String received;
        
        try{
        dos.writeUTF(name);
    	}
    	catch (IOException e) { 
                  
                e.printStackTrace(); 
            } 

        while (true)  
        { 
            try
            { 
                received = dis.readUTF();
                System.out.println(received); 

                String myName = name;
                String file_append;
                  
                if(received.equals("logout")){ 
                    this.isloggedin=false; 
                    System.out.println("log status changed");
                   // Serverx.clients.remove(this);
                   // System.out.println("record removed from clients");
                    this.s.close(); 
                    break; 
                } 
                StringTokenizer st = new StringTokenizer(received, "@");
                String MsgToSend = st.nextToken(); 
                String recipient = st.nextToken();

                for (ClientH mc : Serverx.clients)  
                { 
                    if (mc.name.equals(recipient))  
                    { 
                    	if(mc.dos == null)
                    	System.out.println("No dedicated output stream for this client.");
                    	
                    	if(mc.isloggedin == false) {
                    		System.out.println("Client is offline and message is queued");
							MessageQ queued = new MessageQ(mc.s,MsgToSend,this.name,recipient);
							Serverx.messageQ.add(queued);
							System.out.println("Queued messages ");
							for(MessageQ off : Serverx.messageQ)
				            {
				            	System.out.println(off.message);
				            }
                    	}
                    	else {
                        mc.dos.writeUTF(myName + " : " + MsgToSend + "      "+ new java.util.Date().toString());
                        break; 
                    	}
                    } 
                } 

               try { 
 
            		BufferedWriter out = new BufferedWriter( 
                    new FileWriter("Chat_backup.txt", true)); 
            	out.write(myName + " : " + MsgToSend + "      "+ new java.util.Date().toString()); 
            	out.newLine();
            	out.close(); 
        } 
        catch (IOException e) { 
            System.out.println("exception occoured" + e); 
        } 
            } catch (IOException e) { 
                  
                e.printStackTrace(); 
            } 
              
        } 
        try
        { 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 