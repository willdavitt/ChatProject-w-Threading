import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Handles the setting up of server and tracks 
 * 
 * @author wd371
 *
 */

public class ChatServer {
	public static final int DEFAULT_PORT=14001;
	private ServerSocket ss;
	private final int port;
	private ClientHandler ch;
	private ServerInput si;
	private volatile static ArrayList<Client> clientList = new ArrayList<Client>();
	//private static GUI ui;
	
	/**
	 * Constructor. Attempts to create the server socket.
	 * On success will start server on fail will terminate
	 * 
	 * @param port
	 * 			The port that the server should run on.
	 */
	public ChatServer(int port){
		this.port = port;
		System.out.println("Starting server on port: " + port);
		try {
			ss = new ServerSocket(port);
			go();
			//ui = new GUI();
		}catch(IOException e) {
			System.out.println("This port is already in use, please run again with a different port");
			System.exit(0);
		}
	}
	
	/**
	 * Outputs all the nicknames of connected clients to the asking client
	 * 
	 * @param c
	 * 			Client asking to see connected clients
	 */
	public static void outputConnectedClients(Client c) {
		c.print("Currently connected users:");
		for(Client client:clientList) {
			if(client.getNickname()==null) {
				c.print("...Awaiting Assignment...");
			}else {
				c.print(client.getNickname());
			}
			
		}
	}
	/**
	 * Adds a client to the list of currently connected clients
	 * 
	 * @param c
	 * 			Client that needs to be added to the list.
	 */
	public static void addClient(Client c) {
		clientList.add(c);
	}
	
	/**
	 * Removes a client from the list of currently connected clients
	 * 
	 * @param c
	 * 			Client that needs to be removed from the list.
	 */
	public static void removeClient(Client c) {
		c.setRunning(false);
		clientList.remove(c);
		
	}
	
	/**
	 * Broadcasts the message passed in argument to all connected clients apart from the sender.
	 * 
	 * @param message
	 * 			The string of the message to be broadcasted to clients
	 * @param sender
	 * 			The client which has sent the message
	 */
	public static void outputMessage(String message, Client sender) {
		
//		if(sender.getNickname().isEmpty()) {
//			sender.setNickname("Anonymous");
//		}
		if(sender.getNickname()==null) {
			sender.setNickname("Anonymous");
		}
		
		if(message != null) {
			System.out.println(sender.getNickname() + ": " + message);
			//ui.displayMessage(sender.getNickname() + ": " + message);
			for(Client client:clientList) {
				if(client.equals(sender)) {
					//If server wishes to send successful notification
				}else {
					client.print(sender.getNickname() + ": " + message);
				}
			}
		}
	}
	
	/**
	 * For server use when it needs to broadcast to all connected users
	 * 
	 * @param message
	 * 			Message to be broadcasted to all connection
	 */
	private static void broadcast(String message) {
		for(Client client:clientList) {
			client.print(message);
		}

	}
	
	/**
	 * Handles server shutdown by stopping execution of all threads.
	 */
	public void shutdownServer() {
		ch.setRunning(false);
		//Iterator used rather than forloop to avoid concurrentModificationException
		Iterator<Client> iter = clientList.iterator();
		while(iter.hasNext()) {
			Client next = iter.next();
			next.print("-----SERVER SHUTDOWN-----");
			next.setRunning(false);
			iter.remove();
		}
		
		try {
			ss.close();
		} catch (IOException e) {
			System.out.println("Failed to close socket");
		}
		
		System.out.println("Server shutdown all threads - now exiting");
		System.exit(0);
	}
	
	/**
	 * Handles the instantiation of objects which must be running before
	 * clients can connect 
	 */
	private void go() {
		si = new ServerInput(this);
		ch = new ClientHandler(ss);
		ch.start();
		
	}	
	
	/**
	 * Starts chat server, allows user to bind new port to the server
	 * If invalid port enterred will attempt default port
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT;

		for(int i=0;i<args.length;i++) {
			if(args[i].equals("-csp")) {
				if(((i+1)<args.length)) {
					try {
						int temp = Integer.valueOf(args[i+1]);
						if(temp<1 || temp >65535) {
							System.out.println("Port number defined is out of range");
						}else {
							port = Integer.valueOf(args[i+1]);
						}
					}catch(NumberFormatException e){
						System.out.println("Port is not numeric");
					}
				}
			}
		}
		new ChatServer(port);
	}
}
