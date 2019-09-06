import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*; 
import java.io.*; 
import javax.swing.*;

public class STN{
	private Socket socket		 = null; 
	private DataInputStream input = null; 
	private DataOutputStream out	 = null; 
	public String line = "";

	// constructor to put ip address and port 
	public STN(String address, int port) 
	{ 

		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 

			// takes input from terminal 
			input = new DataInputStream(System.in);
			out = new DataOutputStream(socket.getOutputStream()); 

			// sends output to the socket 
			 
		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 

		JFrame f=new JFrame("Chat Client");
		JLabel label = new JLabel("Client");
		label.setBounds(100, 10, 300, 40);

		JTextArea ta = new JTextArea();
		ta.setBounds(100, 60, 200, 50);
		ta.setEditable(false);

		JScrollPane sc = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JTextField t1 = new JTextField();
		t1.setBounds(100, 120, 200, 30); //x, y, width, ht

		JButton b=new JButton("click");
		b.setBounds(130,175,100, 40);
		b.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           try
			{ 
				//line = input.readLine();
				line = t1.getText();
				if (line == "over"){
					try
					{ 
						input.close(); 
						out.close(); 
						socket.close();
						System.out.println("Connection Closed"); 
					} 
						catch(IOException i) 
					{ 
						System.out.println(i); 
					} 
				} 
				out.writeUTF(line); 
			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 
			t1.setText("");
			ta.append("\n");
			ta.append(line);
         }          
      });

		f.add(sc);
		f.add(ta);
		f.add(t1);
		f.add(label);         
		f.add(b);
		f.getContentPane().add(sc);
		          
		f.setSize(400,350);
		f.setLayout(null);
		f.setVisible(true); 

		// establish a connection 
		

		// string to read message from input 



		// keep reading until "Over" is input 
	
	} 


	public static void main(String[] args) {  
		
		STN app = new STN("192.168.0.14", 6666);
		
	} 

}