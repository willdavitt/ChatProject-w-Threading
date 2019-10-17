import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Sets up chat client and tracks threads.
 * 
 * @author wd371
 *
 */
public class ChatClient {
	private Socket server;
	private ChatReader cr;
	private ChatWriter cw;
	public static final int DEFAULT_PORT=14001;
	public static final String DEFAULT_IP="localhost";
	
	/**
	 * Constructor. Creates socket of server we want to connect to.
	 * 
	 * @param address
	 * 			The address of the server we want to connect to
	 * @param port
	 * 			The port of the server we want to connect to
	 */
	public ChatClient(String address, int port) {
		try {
			server = new Socket(address,port);
			go();
		} catch (UnknownHostException e) {
			System.out.println("Unknown host. Please try different.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("This socket could not be connected to.\nPlease try another socket.");
			System.exit(0);
		} 
		
	}
	
	/**
	 * Starts the reading from server and starts allowing user to write to server
	 */
	public void go() {
		cr = new ChatReader(server);
		cw = new ChatWriter(server);
	}
	
	/**
	 * Starts chat client, binds new IP/ port based on program arguments
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		int port = DEFAULT_PORT;
		String address = DEFAULT_IP;
		for(int i=0;i<args.length;i++) {
			if(args[i].equals("-ccp")){
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
			}else if(args[i].equals("-cca")) {
				if(((i+1)<args.length)) {
					address = args[i+1];
				}
			}
		}
		System.out.println("IP: " + address + "\nport: " +port);
		new ChatClient(address,port);
		
	}
}
