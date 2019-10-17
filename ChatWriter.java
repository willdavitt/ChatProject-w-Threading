import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatWriter implements Runnable{
	private BufferedReader userIn;
	private PrintWriter serverOut;
	private Thread t;
	private boolean running;
	
	/**
	 * Constructor. Initialises input from user's console and output to server.
	 * 
	 * @param server
	 * 			Socket of server we are connected to
	 */
	public ChatWriter(Socket server) {
		userIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			serverOut = new PrintWriter(server.getOutputStream(), true);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		running=true;
		t = new Thread(this);
		t.start();
	}
	
	/**
	 * Reads user input from console and sends it to the server.
	 */
	@Override
	public void run() {
		while(running) {
			String userInput;
			try {
				userInput = userIn.readLine();
				if(userInput!="") {
					serverOut.println(userInput);
				}
			} catch (IOException e) {
				System.out.println("Error reading from console");
				running = false;
				return;
			}
		}
	}
	
	/**
	 * Updates the value of running to stop the thread.
	 * 
	 * @param running
	 * 			If we want thread to be running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}
