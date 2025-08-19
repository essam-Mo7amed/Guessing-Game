package simple_game;

import java.net.*;
import java.util.Scanner;

class GameClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public GameClient(String host, int port) throws Exception {
        socket = new DatagramSocket();
        address = InetAddress.getByName(host);
        this.port = port;
    }

    public void play() throws Exception {
        Scanner scanner = new Scanner(System.in);

        boolean playing = true;
        while (playing) {
            int guess = 0;


            while (true) {
                System.out.print("Enter your guess (1-10): ");
                try {
                    guess = Integer.parseInt(scanner.nextLine());
                    if (guess >= 1 && guess <= 10) {
                        break; 
                    } else {
                        System.out.println(" Please enter a number between 1 and 10 only!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input, please enter a number!");
                }
            }

            String guessStr = String.valueOf(guess);
            byte[] buffer = guessStr.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);

            byte[] responseBuffer = new byte[256];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);

            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Server: " + response);

            if (response.contains("Correct")) {
                playing = false;
            }
        }

        socket.close();
    }
}
