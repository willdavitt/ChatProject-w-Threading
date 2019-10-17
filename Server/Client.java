import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles inputs received by each client.
 * 
 * @author wd371
 *
 */
public class Client implements Runnable {
	private InputStreamReader r;
	private BufferedReader clientIn;
	private PrintWriter clientOut;
	private Thread t;
	private String nickname;
	private boolean running;
	
	/**
	 * Constructor. Starts the client input streams, begins thread.
	 * 
	 * @param s
	 * 			The socket which the client is on.
	 */
	public Client(Socket s) {
		
		try {
			clientOut = new PrintWriter(s.getOutputStream(), true);
			clientOut.println("Welcome to chat server. \nPlease enter a nickname: ");
			r = new InputStreamReader(s.getInputStream());
			clientIn = new BufferedReader(r);
		} catch (IOException e) {
			System.out.println("IO EXCEPTION");
		}
		running = true;
		t = new Thread(this);
		t.start();
	}
	
	 /**
	  * @return The client's nickname
	  */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * Takes an input from the user to set nickname.
	 * This must be completed before any other message can be sent
	 */
	private void setNicknameFromUser() {
		try {
			nickname = clientIn.readLine();
			ChatServer.outputMessage("has connected.", this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets a new nickname for client
	 * 
	 * @param nickname
	 * 			new nickname for client
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/**
	 * Run during the execution of the thread.
	 * Continuously reads messages from client. On receipt broadcasts message
	 * Handles disconnects by cleaning shutting down client object.
	 */
	@Override
	public void run() {
//		setNicknameFromUser();
//		ChatServer.outputConnectedClients(this);
//		
//		while(running) {
//			try {
//				String input = clientIn.readLine();
//				if(input!=null) {
//					ChatServer.outputMessage(input,this);
//				}else {
//					throw new IOException();
//				}
//				
//			} catch (IOException e) {
//				
//				ChatServer.outputMessage("has disconnected", this);
//				ChatServer.removeClient(this);
//
//				try {
//					clientIn.close();
//				} catch (IOException e1) {
//					System.out.println("Failed to close client input stream");
//				}
//				clientOut.close();
//				return;
//				
//			}
//		}
		
		try {
			setNicknameFromUser();
			ChatServer.outputConnectedClients(this);
			while(running) {
			
				String input = clientIn.readLine();
				if(input!=null) {
					ChatServer.outputMessage(input,this);
				}else {
					throw new IOException();
				}
				
			} 
				
			}catch (IOException e) {
				
				ChatServer.outputMessage("has disconnected", this);
				ChatServer.removeClient(this);

				try {
					clientIn.close();
				} catch (IOException e1) {
					System.out.println("Failed to close client input stream");
				}
				clientOut.close();
				return;
		}
		
	}
	
	/**
	 * Sends message to client using print writer.
	 * 
	 * @param message
	 * 			The message that we want to send to the client
	 */
	public void print(String message) {
			clientOut.println(message);
	}
	
	/**
	 * Sets the value of running for if we want to stop or continue execution
	 * 
	 * @param running
	 * 			Whether we want the thread to continue execution
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
}
