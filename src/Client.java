import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    String name = "person";
    static int numberOfPerson = 0;
    Socket socket;
    InputStream is;
    OutputStream os;
    Scanner in;
    PrintStream out;
    Server server;

    public Client (Socket socket, Server server) {
        this.socket = socket;
        new Thread(this).start();
        this.server = server;
        this.name += "_" + ++numberOfPerson;
    }

    void receive(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            in = new Scanner(is);
            out = new PrintStream(os);
            out.println("Welcome to chat!");
            String input = this.name + ": " + in.nextLine();
            while (!input.equals("stop")) {
                server.sendAll(input);
                input = this.name + ": " + in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
