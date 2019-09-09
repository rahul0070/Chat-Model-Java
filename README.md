Latest update:
-> Model working with two clients.
-> We need to modify it for multiple clients.

This is the latest update I have done:
-> The Serverx accepts two Clientx ( Two socket objects created using a single server object). 
-> This server passes data from socket 1 (Client 1) to client 2 via Socket  2 and data from Socket 2 to Socket 1 (Client 1)
-> The server also stores a copy of all the data received


Problem with the model:
-> I haven't implemented thread to run both sockets simultaneously.
-> Every time a Client 1tries to send a message, it has to wait until Client 2 sends a message too for the program to proceed.
-> When both the client sends the message, data is passed within these two
-> The problem I faced when I try to implement thread was that the RUN function which is called by the thread creates a single socket instead of 2 thus making it impossible to access the other socket or thread.
