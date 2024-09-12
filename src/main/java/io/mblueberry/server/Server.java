package io.mblueberry.server;

import io.mblueberry.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {
    private final ArrayList<ConnectionHandler> connections;
    private ServerSocket serverSocket;
    private boolean done;
    private ExecutorService pool;

    Game game;

    public Server(Game game) {
        this.game = game;
        connections = new ArrayList<>();
        done = false;

    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(999);
            pool = Executors.newCachedThreadPool();
            System.out.println("Server started: ip: " + serverSocket.getInetAddress() + " port: 999");
            while (!done) {
                Socket client = serverSocket.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(client);
                connections.add(connectionHandler);
                pool.execute(connectionHandler);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void broadcast(String message) {
        for (ConnectionHandler connection : connections) {
            if (connection != null) {
                connection.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            pool.shutdown();
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }

            for (ConnectionHandler connection : connections) {
                if (connection != null) {
                    connection.shutdown();
                }

            }
        } catch (IOException e) {
            shutdown();
        }
    }

    class ConnectionHandler implements Runnable {
        private final Socket client;
        private BufferedReader in;
        private PrintWriter out;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Pls enter a nickname");
                String nickname = in.readLine();
                broadcast(nickname + " connected!");
                System.out.println(nickname + " connected!");
                int x = 20;
                int y = 22;
               // game.world.entities.add(new MPlayer(game, x, y, nickname));
                broadcast("/spawn " + nickname + " " + x + " " + y);
                String message;
                while ((message = in.readLine()) != null) {

                    if (message.startsWith("/move")) {
                        System.out.println("move message = " + message);
                        // broadcast(message);


                        String[] data = message.split(" ");
                        System.out.println("dataMole = " + Arrays.toString(data));
                        String name = "test";
                        int newx = Integer.parseInt(data[2]);
                        int newy = Integer.parseInt(data[3]);


                        for (int i = 0; i < game.world.entities.size(); i++) {
                          //  System.out.println("entity.isMPlayer = " + game.world.entities.get(i).name);
//                            if (game.world.entities.get(i).name.equals(name)) {
//                                game.world.entities.get(i).screenX = newx + game.cameraX;
//                                game.world.entities.get(i).screenY = newy + game.cameraY;;
//                           }
                        }



                    }
                    if (message.startsWith("/nick ")) {

                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nickname + " set new nickname: " + messageSplit[1]);
                            System.out.println(nickname + " set new nickname: " + messageSplit[1]);
                            nickname = messageSplit[1];
                        }

                    } else if (message.startsWith("/quit")) {
                        shutdown();
                        broadcast(nickname + " disconnected!");
                        System.out.println(nickname + " disconnected!");

                    } else {
                        broadcast(nickname + ": " + message);
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
    }
}