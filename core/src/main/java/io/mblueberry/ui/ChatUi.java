package io.mblueberry.ui;

import io.mblueberry.Game;
import io.mblueberry.core.model.ChatMessage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class ChatUi implements IBaseUi {

    private static final int MAX_MESSAGES = 15;
    private static final int MESSAGE_PADDING = 5;
    private static final int MESSAGE_WIDTH = 250;
    private static final int MESSAGE_HEIGHT = 50;
    private static final int INPUT_MESSAGE_FONT_SIZE = 25;
    private static final int MESSAGE_FONT_SIZE = 20;
    private static final Color BACKGROUND_COLOR = new Color(0f, 0f, 0f, 0.5f);
    private static final Color TEXT_COLOR = Color.WHITE;

    private final List<ChatMessage> chatMessages = new ArrayList<>();
    private final StringBuilder message = new StringBuilder();
    private final Game game;

    public ChatUi(Game game) {
        this.game = game;
    }

    @Override
    public void update() {
        // Implement logic for updating chat UI if necessary
    }

    @Override
    public void draw(Graphics2D g2) {
        drawInputMessage(g2);
        drawMessages(g2);
    }

    private void drawMessages(Graphics2D g2) {
        int x = 50;
        int y = game.getHeight() - MESSAGE_HEIGHT;

        g2.setFont(g2.getFont().deriveFont((float) MESSAGE_FONT_SIZE));

        for (int i = 0; i < Math.min(chatMessages.size(), MAX_MESSAGES); i++) {
            ChatMessage chatMessage = chatMessages.get(chatMessages.size() - 1 - i);
            String message = chatMessage.username + ": " + chatMessage.text;

            // Draw message background
            g2.setColor(BACKGROUND_COLOR);
            g2.fillRect(x - MESSAGE_PADDING, y - MESSAGE_HEIGHT / 2, MESSAGE_WIDTH, MESSAGE_HEIGHT-10);

            // Draw message text
            g2.setColor(TEXT_COLOR);
            g2.drawString(message, x, y);

            y -= MESSAGE_HEIGHT;
        }
    }

    private void drawInputMessage(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont((float) INPUT_MESSAGE_FONT_SIZE));
        g2.setColor(TEXT_COLOR);
        g2.drawString(message.toString(), 100, game.getHeight() - 10);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {
        // Implement mouse wheel handling if necessary
    }

    @Override
    public void handleMouse(MouseEvent e) {

    }

    public void handleKeyBoard(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int keyChar = e.getKeyChar();

        switch (keyCode) {


            case KeyEvent.VK_ENTER -> sendMessage();
            case KeyEvent.VK_ESCAPE -> {
                game.uiState = UiState.HUD;
            }
            case KeyEvent.VK_BACK_SPACE -> {
                if (!message.isEmpty()) {
                    message.deleteCharAt(message.length() - 1);
                }
            }
            default -> {
                if (Character.isLetterOrDigit(keyChar) || Character.isSpaceChar(keyChar)) {
                    message.append(keyChar);
                }
            }
        }
    }

    private void sendMessage() {
        if (!message.isEmpty()) {
            chatMessages.add(new ChatMessage(game.username, message.toString()));
            message.setLength(0);
        }
    }
}
