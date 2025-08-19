import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost"; 
        int port = 5000;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server. Type 'quit' to exit.");

            while (true) {
                System.out.print("Enter your choice (rock/paper/scissors/quit): ");
                String choice = scanner.nextLine();

                out.println(choice);

                if (choice.equalsIgnoreCase("quit")) {
                    System.out.println("Game ended.");
                    break;
                }

                String response = in.readLine();
                System.out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
