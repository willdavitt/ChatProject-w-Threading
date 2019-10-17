import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Handles all inputs made on the command line for the server
 * 
 * @author wd371
 *
 */
public class ServerInput implements Runnable {
	private BufferedReader serverIn;
	private Thread t;
	private ChatServer cs;
	private boolean running;
	
	/**
	 * Constructor. Sets up thread running and input stream.
	 * 
	 * @param cs
	 * 			Instance of chat server
	 */
	public ServerInput(ChatServer cs) {
		running = true;
		this.cs = cs;
		serverIn = new BufferedReader(new InputStreamReader(System.in));
		t = new Thread(this);
		t.start();
	}
	
	/**
	 * Continuously checks input from server console and will exit on command.
	 */
	public void run() {
		
		while(running) {
			String input = "";
			try {
				input = serverIn.readLine();
			} catch (IOException e) {
				System.out.println("Error occurred reading from console");
			}
			
			if(input.equals("EXIT")) {
				//EXIT
				System.out.println("SERVER CLOSING DOWN");
				running = false;
				cs.shutdownServer();
			}
		}
		
	}
	
	/**
	 * 
	 * @return
	 * 		Returns whether the thread is currently running.
	 */
	public boolean getRunning() {
		return running;
	}
}
