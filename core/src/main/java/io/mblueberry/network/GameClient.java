package io.mblueberry.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import io.mblueberry.Game;
import io.mblueberry.GameState;
import io.mblueberry.model.packet.*;
import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.entity.MPlayer;

import java.io.IOException;

public class GameClient {
    private Client client;
    private Game game;
    private static final int TIMEOUT = 5000;
    private static final String SERVER_IP = "localhost"; // Замініть на IP сервера
    private static final int TCP_PORT = 54555;
    private static final int UDP_PORT = 54777;

    public GameClient(Game game) {
        this.game = game;
        client = new Client();
    }

    public void start() {
        // Реєстрація класів, які будуть передаватися між клієнтом і сервером
        Kryo kryo = client.getKryo();
        registerClasses(kryo);

        // Додавання обробника повідомлень
        client.addListener(new Listener() {
            @Override
            public void connected(com.esotericsoftware.kryonet.Connection connection) {
                game.guiManager.connectToServerUi.status = "Підключено до сервера";
                System.out.println("Підключено до сервера");
                sendUpdate(new LoginPacket(game.player.username));
            }

            @Override
            public void disconnected(com.esotericsoftware.kryonet.Connection connection) {
                System.out.println("Відключено від сервера");
                game.isOnline = false;
                game.guiManager.disconnectUi.message = "Відключено від сервера";
                game.gameState = GameState.DISCONNECT;
            }

            @Override
            public void received(com.esotericsoftware.kryonet.Connection connection, Object object) {
                // Обробка отриманих повідомлень від сервера
                if (object instanceof PlayerUpdate update) {

                    Entity pl = game.world.getPlayer(update.username);
                    pl.x = update.x;
                    pl.y = update.y;
                    pl.currentState = update.animState;
                    System.out.println("Отримано оновлення від сервера: " + update);
                }
                if (object instanceof PlayerList update) {
                    for (PlayerInfo player : update.players) {
                        game.world.players.add(new MPlayer(game, 10, 10, player.username));
                    }
                    game.guiManager.connectToServerUi.status = "Отримано список гравців";
                    System.out.println("Отримано список гравців");

                    SpawnPlayer spawnPlayer = new SpawnPlayer(game.player.username, 10, 10);
                    sendUpdate(spawnPlayer);
                    game.gameState = GameState.PLAYING;
                }
                if (object instanceof SpawnPlayer update) {

                    game.world.players.add(new MPlayer(game, 10, 10, update.username));
                    game.guiManager.disconnectUi.message = "Спавнимо гравця";
                    System.out.println("Спавнимо гравця");
                }
            }
        });

        try {
            // Запуск клієнта та підключення до сервера
            game.guiManager.connectToServerUi.status = "Підключення...";
            System.out.println("Підключення...");
            client.start();
            client.connect(TIMEOUT, SERVER_IP, TCP_PORT, UDP_PORT);
            System.out.println("Клієнт запущений та підключений до сервера на IP: " + SERVER_IP);
            game.isOnline = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerClasses(Kryo kryo) {
        kryo.register(PlayerUpdate.class);
        kryo.register(SpawnPlayer.class);
        kryo.register(LoginPacket.class);
        kryo.register(PlayerList.class);
        kryo.register(PlayerInfo.class);
        kryo.register(java.util.ArrayList.class);
    }

    public void sendUpdate(Object update) {

        client.sendTCP(update);
    }


    public void stop() {
        client.stop();
    }
}
