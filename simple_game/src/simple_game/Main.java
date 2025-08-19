package simple_game;

public class Main {
    public static void main(String[] args) throws Exception {

    	new Thread(() -> {
            try {
                GameServer server = new GameServer(12345);
                server.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        GameClient client = new GameClient("localhost", 12345);
        client.play();
    }
}
