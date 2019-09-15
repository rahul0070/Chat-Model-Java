
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*; 
import java.io.*; 
import javax.swing.*;
import java.util.*;

public class Clientx{

	private Socket socket		 = null; 
	private DataInputStream input = null; 
	private DataOutputStream out	 = null; 
	private DataInputStream in	 = null;
	public String line = "";
	public String line2 = "";
	public int inc = 0;

	public Clientx(String address, int port) 
	{ 

		JFrame f=new JFrame("Chat Client");
		JLabel label = new JLabel("Client");
		label.setBounds(50, 10, 300, 40);

		JTextArea ta = new JTextArea();
		ta.setBounds(50, 60, 400, 200);
		ta.setEditable(false);

		JTextField tid = new JTextField();
		tid.setBounds(50, 400, 100, 50);
		
		JScrollPane sc = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		f.getContentPane().add(sc);

		JTextField t1 = new JTextField();
		t1.setBounds(50, 280, 400, 30); //x, y, width, ht

		JButton b=new JButton("Send");
		b.setBounds(50,350,100, 40);
		f.getRootPane().setDefaultButton(b);


		f.add(sc);
		f.add(ta);
		f.add(t1);
		f.add(label);         
		f.add(b);
		          
		f.setSize(500,500);
		f.setLayout(null);
		
	

		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 

			input = new DataInputStream(System.in);
			out = new DataOutputStream(socket.getOutputStream()); 
			in = new DataInputStream( 
					new BufferedInputStream(socket.getInputStream())); 

		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 

		Scanner take = new Scanner(System.in);
		String username;
		String valid = "";

		ta.append("Enter the preferred user name for your client");
		System.out.println("Entered preferred username: ");
		username = take.nextLine();
		
		try
		{
			out.writeUTF(username);
			valid = in.readUTF();
			System.out.println(valid);
		}
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 

		if (valid == "n"){
			System.out.println("Username already exists.");
		}

		f.setVisible(true);

		b.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           try
			{ 
				//line = input.readLine();
				line = t1.getText();
				if (line == "logout"){
					try
					{ 
						input.close(); 
						out.close(); 
						socket.close();
						ta.append("Connection Closed"); 
					} 
						catch(IOException i) 
					{ 
						i.printStackTrace(); 
						
					} 
				} 
				out.writeUTF(line);
				StringTokenizer st = new StringTokenizer(line, "@");
                String message = st.nextToken(); 
                String receiverId = st.nextToken();
				ta.append("\nMe: "+ message + " to "+ receiverId);

			} 
			catch(IOException i) 
			{ 
				i.printStackTrace();
				
			} 
			t1.setText("");

         }          
      });

		ta.setText("");
		while(true){
			inc = inc + 1;
			try{
        		line2 = in.readUTF();
        		if(inc == 1){
        			//String incs = Integer.toString(inc);
        			ta.append("WELCOME TO THE NETWORK");
        			//tid.setText("Your client ID is: client"+incs);
        			ta.append("\n\nYour client ID is: "+line2);
        		}
        		else{
				ta.append("\n");
			    ta.append(line2);
				}
			}
			catch(IOException i){
				System.out.println(i);
				break;
			}
        }

	} 


	public static void main(String[] args) {  
		Clientx app = new Clientx("192.168.0.14", 6666);
	} 

}