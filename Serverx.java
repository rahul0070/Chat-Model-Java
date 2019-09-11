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
    static int i = 0; 
  
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
        ta.append("CONNECTED CLIENTS: \n");

        while (true)  
        { 
            s = ss.accept(); 
            i++;
  
            System.out.println("Client connected : " + "client"+i ); 

            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
           
            ClientH mtch = new ClientH(s,"client" + i, dis, dos,s.getInetAddress().toString()); 
  
            Thread t = new Thread(mtch);
            clients.add(mtch); 
            System.out.println("All connected clients are : " + clients);
            //ta.setText("");
            ta.append(Integer.toString(i) + "). Client"+Integer.toString(i) + "    IP: " + s.getInetAddress().toString() + "\n"); 
            t.start(); 
             
  
        } 
    } 
} 
  
class ClientH implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    final DataInputStream dis; 
    final DataOutputStream dos;
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
                    	//mc.dos.writeUTF(mc.name);
                        mc.dos.writeUTF(myName + " : " + MsgToSend + "      "+ new java.util.Date().toString());
                        break; 
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