import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * Class handles reading inputs coming from server
 * @author wd371
 *
 */
public class ChatReader implements Runnable {
	private BufferedReader serverIn;
	private Thread t;
	
	/**
	 * Constructor. Sets up reading from server and thread.
	 * 
	 * @param server
	 */
	public ChatReader(Socket server) {
		try {
			serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error has occured trying to read from server");
		}
		t = new Thread(this);
		t.start();
	}
	
	/**
	 * Continuously checks for input from server and outputs anything received.
	 */
	@Override
	public void run() {
		
		while(true) {
			String serverRes;
			try {
				serverRes = serverIn.readLine();
				if(serverRes != null) {
					System.out.println(serverRes);
				}else {
					throw new IOException();
				}
								
			} catch (IOException e) {
				System.out.println("Connection to server has been lost. Please restart chat client.");
				
				try {
					serverIn.close();
					
				} catch (IOException e1) {
					System.out.println("Unable to close chatreader");
				}
				System.exit(0);
				return;
			}
		}
	}
	

}
