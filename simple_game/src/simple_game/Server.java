package simple_game;

import java.net.*;
import java.util.Random;

class GameServer {
    private DatagramSocket socket;
    private int randomNumber;
    private boolean running;

    public GameServer(int port) throws Exception {
        socket = new DatagramSocket(port);
        Random rand = new Random();
        randomNumber = rand.nextInt(10) + 1; // رقم بين 1 و 10
        running = true;
        System.out.println("Server started. Random number is chosen!");
    }

    public void start() throws Exception {
        byte[] buffer = new byte[256];

        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String guessStr = new String(packet.getData(), 0, packet.getLength());
            int guess = Integer.parseInt(guessStr);

            System.out.println("Client guessed: " + guess);

            String response;
            if (guess > randomNumber) {
                response = "Too high";
            } else if (guess < randomNumber) {
                response = "Too low";
            } else {
                response = "Correct! You win!";
                running = false; // يوقف بعد الإجابة الصح
            }

            byte[] responseBytes = response.getBytes();
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();

            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, clientAddress, clientPort);
            socket.send(responsePacket);
        }

        socket.close();
        System.out.println("Game over. Server closed.");
    }
}
