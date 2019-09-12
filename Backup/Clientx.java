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
		//f.add(tid);
		          
		f.setSize(500,500);
		f.setLayout(null);
		f.setVisible(true); 
	

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
				StringTokenizer st = new StringTokenizer(line, "@");
                String message = st.nextToken(); 
                String receiverId = st.nextToken();
				ta.append("\nMe: "+ message + " to "+ receiverId);
				//ta.append(line);

			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 
			t1.setText("");

         }          
      });

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
			}
        }

	} 


	public static void main(String[] args) {  
		
		Clientx app = new Clientx("10.82.27.169", 6666);
		
	} 

}