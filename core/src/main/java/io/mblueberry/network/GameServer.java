package io.mblueberry.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import io.mblueberry.Game;
import io.mblueberry.model.packet.*;
import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.entity.MPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class GameServer {
    private final Server server;
    private final Game game;
    private static final int TCP_PORT = 54555;
    private static final int UDP_PORT = 54777;

    public GameServer(Game game) {
        this.game = game;
        server = new Server();
    }

    public void start() {
        Kryo kryo = server.getKryo();
        registerClasses(kryo);
        server.addListener(new Listener() {

            @Override
            public void connected(Connection connection) {
                System.out.println("Клієнт підключений: " + connection.getRemoteAddressTCP());
                PlayerList playerList = new PlayerList();
                ArrayList<PlayerInfo> l = new ArrayList<>();
                for (Entity player : game.world.players) {
                    l.add(new PlayerInfo(player.username, player.x, player.y));
                }
                playerList.players = l;
                System.out.println("playerList = " + playerList);
                 connection.sendTCP(playerList);


            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Клієнт відключений: " + connection.getRemoteAddressTCP());
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerUpdate update) {
                    System.out.println("update = " + update);
                    for (Entity player : game.world.players) {
                    }
                    Entity pl = game.world.getPlayer(update.username);
                    if (pl != null) {

                        pl.x = update.x;
                        pl.y = update.y;
                        pl.currentState = update.animState;
                        System.out.println("Отримано оновлення від клієнта: " + update);
                        server.sendToAllExceptTCP(connection.getID(), update);
                    }
                }
                if (object instanceof SpawnPlayer update) {
                    game.world.players.add(new MPlayer(game, 10, 10, update.username));
                    System.out.println("Отримано оновлення від клієнта: " + update);
                    server.sendToAllExceptTCP(connection.getID(), update);
                }
            }
        });

        try {
            server.bind(TCP_PORT, UDP_PORT);
            server.start();
            System.out.println("Сервер запущений на портах TCP: " + TCP_PORT + ", UDP: " + UDP_PORT);
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

    public void stop() {
        server.stop();
    }

}
