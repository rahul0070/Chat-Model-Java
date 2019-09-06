import javax.swing.*;
import java.net.*; 
import java.io.*;

public class STC{
	private Socket		 socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in	 = null; 

	public STC(){
		JFrame f=new JFrame("Chat Server");
		JLabel label = new JLabel("DATA FROM CLIENT");
		label.setBounds(100, 10, 300, 40);

		JTextArea t1 = new JTextArea();
		t1.setBounds(100, 50, 200, 150); //x, y, width, ht

		JButton b=new JButton("click");
		b.setBounds(130,100,100, 40);

		f.add(t1);
		f.add(label);         
		//f.add(b);
		          
		f.setSize(400,350);
		f.setLayout(null);
		f.setVisible(true); 

		try
			{ 
				server = new ServerSocket(6666); 
				System.out.println("Server started"); 

				socket = server.accept(); 

				in = new DataInputStream( 
					new BufferedInputStream(socket.getInputStream())); 

				String line = ""; 

				// reads message from client until "Over" is sent 
				while (!line.equals("Over")) 
				{ 
					try
					{ 
						line = in.readUTF(); 
						System.out.println(line);
						t1.append("\n");
						t1.append(line);

					} 
					catch(IOException i) 
					{ 
						System.out.println(i); 
					} 
				} 
				System.out.println("Closing connection"); 

				socket.close(); 
				in.close(); 
			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 

	}
			
	public static void main(String[] args) {  
		
		STC app = new STC();
		
	} 
}
