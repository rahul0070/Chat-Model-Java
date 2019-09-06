import javax.swing.*;
import java.net.*; 
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;


public class STCC{
	public Socket		 socket = null; 
	private ServerSocket server = null; 
	public JTextArea t1 = null; 
	private DataInputStream in = null;

	public STCC(){
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

	}




	private static class Capitalizer implements Runnable {
        	
        	private Socket socket;
        	private DataInputStream in;
        	private JTextArea t1;

        	Capitalizer(Socket socket) {
            	this.socket = socket;
            	this.t1 = new JTextArea();
            	try{
            	this.in = new DataInputStream( 
					new BufferedInputStream(socket.getInputStream())); 
            	}

            	

            	catch(IOException i) 
				{ 
				System.out.println(i); 
				} 

            	JFrame f=new JFrame("Chat Server");
				JLabel label = new JLabel("DATA FROM CLIENT");
				label.setBounds(100, 10, 300, 40); 

				//JTextArea t1 = new JTextArea();
				t1.setBounds(100, 50, 200, 150); //x, y, width, ht

				JButton b=new JButton("click");
				b.setBounds(130,100,100, 40);

				f.add(t1);
				f.add(label);         
				//f.add(b);
				          
				f.setSize(400,350);
				f.setLayout(null);
				f.setVisible(true); 
        	}

        	@Override
		    public void run() {
		        System.out.println("Connected: " + socket);
		   

		           String line = "";

		           	while (!line.equals("Over")) 
					{ 
						try
						{ 
							line = in.readUTF(); 
							t1.append(line);
							t1.append("\n");


						} 
						catch(IOException i) 
						{ 
							System.out.println(i); 
						} 
					}

			}
	}

	

	public static void main(String[] args) throws Exception { 

		//STCC app = new STCC();
		try (var listener = new ServerSocket(6666)) {
            System.out.println("The capitalization server is running...");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Capitalizer(listener.accept()));
            }
        } 
		
	} 
}
