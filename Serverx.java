import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*; 
import java.io.*; 
import javax.swing.*;

public class Serverx{
	private Socket		 socket1 = null;
	private Socket		 socket2 = null; 
	private ServerSocket server = null; 
	private DataInputStream in1	 = null; 
	private DataOutputStream out1 = null;
	private DataInputStream in2	 = null; 
	private DataOutputStream out2 = null;
	private String line1 = "";
	private String line2 = "";

	public Serverx(){
		JFrame f=new JFrame("Server");
		JLabel label = new JLabel("Server");
		label.setBounds(100, 10, 300, 40);

		JTextArea ta = new JTextArea();
		ta.setBounds(100, 60, 200, 150);
		ta.setEditable(false);

		JScrollPane sc = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JTextField t1 = new JTextField();
		t1.setBounds(100, 180, 200, 30); //x, y, width, ht

		JButton b=new JButton("click");
		b.setBounds(130,220,100, 40);
		

		f.add(sc);
		f.add(ta);
		//f.add(t1);
		f.add(label);         
		//f.add(b);
		f.getContentPane().add(sc);
		          
		f.setSize(400,500);
		f.setLayout(null);
		f.setVisible(true); 


		try
			{ 
				server = new ServerSocket(6666); 
				System.out.println("Server started");


				socket1 = server.accept(); 
				System.out.println("Waiting for second client...");
				socket2 = server.accept();
				System.out.println("Recieved second client.");

				in1 = new DataInputStream( 
					new BufferedInputStream(socket1.getInputStream())); 
				out1 = new DataOutputStream(socket1.getOutputStream());


				in2 = new DataInputStream( 
					new BufferedInputStream(socket2.getInputStream())); 
				out2 = new DataOutputStream(socket2.getOutputStream());


				// reads message from client until "Over" is sent 
				while (true) 
				{ 
					try
					{ 
						if(in1.available()>0){

						line1 = in1.readUTF();
						out2.writeUTF(line1);
						ta.append("\nClient 1: ");
						ta.append(line1);
						}

						if(in2.available()>0){
			
						line2 = in2.readUTF();
						out1.writeUTF(line2);
						ta.append("\nClient 2: ");
						ta.append(line2); 
						}
						
					} 
					catch(IOException i) 
					{ 
						System.out.println(i); 
					} 
				} 
				System.out.println("Closing connection"); 

				socket1.close(); 
				in1.close(); 
			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 

	}
			
	public static void main(String[] args) {  
		
		Serverx app = new Serverx();
		
	} 
}
