// ChatClient.java
import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to chat server. Type your messages:");

            // Thread to listen for server messages
            new Thread(() -> {
                String serverMsg;
                try {
                    while ((serverMsg = serverIn.readLine()) != null) {
                        System.out.println("Server: " + serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Main thread to send messages
            String userInput;
            while ((userInput = input.readLine()) != null) {
                out.println(userInput);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
