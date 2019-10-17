import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Handles the connection of new clients to the server
 * 
 * @author wd371
 *
 */
public class ClientHandler implements Runnable{
	private ServerSocket ss;
	private int port;
	private Thread t;
	private boolean running;
	
	/**
	 * Constructor. Sets the field values;
	 * 
	 * @param ss
	 * 			The serversocket that the server is running on.
	 */
	public ClientHandler(ServerSocket ss) {
			port = ss.getLocalPort();
			this.ss = ss;
			running = false;
	}
	
	/**
	 * Starts the execution of the thread
	 */
	public void start() {
		if(running) {
			return;
		}
		t = new Thread(this);
		t.start();
	}
	
	/**
	 * Continuously checks for connection of new clients and 
	 * adds them to the currently connected client list.
	 */
	@Override
	 public void run() {
		running = true;
       
        try {
            while (running) {
            	//System.out.println("Waiting for connection on:" + port);
            	Socket request = ss.accept();
                //System.out.println("(SERVER)Server accepted connection on " + port + " ; " + request.getPort() );
                ChatServer.addClient(new Client(request));
            }
        } catch (IOException e) {
        	System.out.println("Client Handler closing down"); 
        }
    }
	
	/**
	 * Updates value of running to stop execution of thread
	 * 
	 * @param running
	 * 			Whether the thread should be running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}
