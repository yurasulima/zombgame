package io.mblueberry;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.setupGame();
        game.startServer(); // Запускаємо сервер
    }
}