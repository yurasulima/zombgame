package io.mblueberry.server;

import io.mblueberry.object.entity.MPlayer;
import io.mblueberry.object.entity.Player;
import io.mblueberry.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class Client extends Thread {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    private String nickname = "";
    Game game;

    public Client(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        try {
            client = new Socket("0.0.0.0", 999);

            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inputHandler = new InputHandler();
            Thread thread = new Thread(inputHandler);
            thread.start();
            String newMessage;

            while ((newMessage = in.readLine()) != null) {
                if (newMessage.startsWith("/setnick")) {
                    String[] data = newMessage.split(" ");
                    nickname = data[1];
                }
                if (newMessage.startsWith("/move")) {
                    String[] data = newMessage.split(" ");
                    System.out.println("dataMole = " + Arrays.toString(data));
                    String name = data[1];
                    int x = Integer.parseInt(data[2]);
                    int y = Integer.parseInt(data[3]);

                    game.world.entities.add(new MPlayer(game, x, y, name));
                }
                if (newMessage.startsWith("/spawn")) {
                    String[] data = newMessage.split(" ");
                    System.out.println("dataspawn = " + Arrays.toString(data));
                    String name = data[1];
                    int x = Integer.parseInt(data[2]);
                    int y = Integer.parseInt(data[3]);

                    if (nickname.equals(name)) {
                        game.player = new Player(game, game.keyHandler, x, y, name);
                    } else {
                        game.world.entities.add(new MPlayer(game, x, y, name));
                    }


                    System.out.println("spawned");
                } else {
                    System.out.println("newMessage = " + newMessage);
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void sendMessage(String message) {

        out.println(message);
    }

    public void shutdown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    class InputHandler implements Runnable {

        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inputReader.readLine();

                    if (message.equals("/quit")) {
                        inputReader.close();
                        out.println(message);
                        shutdown();

                    }  else {
                        out.println(message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }


    }

//    public static void main(String[] args) {
//        Client client1 = new Client();
//        client1.run();
//    }
}
