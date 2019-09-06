import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*; 
import java.io.*; 
import javax.swing.*;

public class Clientx{
	private Socket socket		 = null; 
	private DataInputStream input = null; 
	private DataOutputStream out	 = null; 
	private DataInputStream in	 = null;
	public String line = "";
	public String line2 = "";

	public Clientx(String address, int port) 
	{ 

		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 

			// takes input from terminal 
			input = new DataInputStream(System.in);
			out = new DataOutputStream(socket.getOutputStream()); 
			in = new DataInputStream( 
					new BufferedInputStream(socket.getInputStream())); 

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
		ta.setBounds(100, 60, 200, 200);
		ta.setEditable(false);

		JScrollPane sc = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JTextField t1 = new JTextField();
		t1.setBounds(100, 270, 200, 30); //x, y, width, ht

		JButton b=new JButton("click");
		b.setBounds(130,350,100, 40);
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
				line2 = in.readUTF();
				System.out.println(line2); 
			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 
			t1.setText("");
			ta.append("\n");
			ta.append(line);
			ta.append("\n");
			ta.append(line2);
         }          
      });

		f.add(sc);
		f.add(ta);
		f.add(t1);
		f.add(label);         
		f.add(b);
		f.getContentPane().add(sc);
		          
		f.setSize(400,500);
		f.setLayout(null);
		f.setVisible(true); 
	
	} 


	public static void main(String[] args) {  
		
		Clientx app = new Clientx("192.168.0.14", 6666);
		
	} 

}