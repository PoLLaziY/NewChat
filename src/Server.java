import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    ArrayList<Client> clientArrayList = new ArrayList<>();
    ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }
    
    void sendAll(String message) {
        for (Client client: clientArrayList) {
            client.receive(message);
        }
    }

    public void run(){
        try {
            while (true) {
                System.out.println("Waiting...");
                Socket socket = serverSocket.accept();
                clientArrayList.add(new Client(socket, this));
                System.out.println("New client connected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(1234);
        server.run();

    }
}
