import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        int port = 5000; 
        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String clientChoice;

            while ((clientChoice = in.readLine()) != null) {
                if (clientChoice.equalsIgnoreCase("quit")) {
                    System.out.println("Client quit the game.");
                    break;
                }

                String serverChoice = choices[random.nextInt(choices.length)];
                String result = getResult(clientChoice, serverChoice);

                out.println("Server choice: " + serverChoice + " | Result: " + result);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String getResult(String client, String server) {
        if (client.equals(server)) return "Draw";

        switch (client) {
            case "rock":
                return (server.equals("scissors")) ? "You win" : "You lose";
            case "paper":
                return (server.equals("rock")) ? "You win" : "You lose";
            case "scissors":
                return (server.equals("paper")) ? "You win" : "You lose";
            default:
                return "Invalid choice!";
        }
    }
}
