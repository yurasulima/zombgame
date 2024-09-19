package io.mblueberry.ui;

public class UI {
//
//
//    public BufferedImage heartFull, heartHalf, heartBlank;
//    Graphics2D g2;
//    Game game;
//    int panelWidth = 0;
//    int panelHeight = 0;
//    private Button button;
//    private Button settings;
//    private Slot slot1;
//    private Slot slot2;
//    private Slot slot3;
//    private Slot slot4;
//    boolean drawed = false;
//
//    String currentDialogue;
//
//    public UI(Game game) {
//        this.game = game;
//        HeartItem heart = new HeartItem(game);
//        heartFull = heart.heartFull;
//        heartHalf = heart.heartHalf;
//        heartBlank = heart.heartBlank;
//
//        button = new Button(0, 0, 200, 50, "Start");
//        settings = new Button(0, 0, 200, 50, "Settings");
//        button.setOnClick(() -> {
//            game.gameState = GameState.PLAYING;
//            System.out.println("Start clicked!");
//        });
//        settings.setOnClick(() -> {
//            game.gameState = GameState.PAUSED;
//            System.out.println("settings clicked!");
//        });
//    }
//
//    public void setupUI() {
//
//
////        int buttonX = (panelWidth - button.height ) / 2;
//        int buttonX = (panelWidth / 2) - (button.width / 2);
//        int buttonY = (panelHeight - button.height) / 2 - 30;
//        button.setPosition(buttonX, buttonY);
//        int settingsX = (panelWidth - settings.width) / 2;
//        int settingsY = buttonY + button.height + 10;
//        settings.setPosition(settingsX, settingsY);
//    }
//
//    public void update() {
//        panelHeight = game.getHeight();
//        panelWidth = game.getWidth();
//        setupUI();
//    }
//
//    public void draw(Graphics2D g2) {
//
//        this.g2 = g2;
//        if (game.gameState == GameState.PLAYING) {
//            drawPlayerLife();
//            drawToolbar();
//            if (game.keyHandler.showStata) {
//                drawPlayerStata();
//            }
//        }
//
//        if (game.gameState == GameState.START_SCREEN) {
//            drawBackground();
//            button.draw(g2);
//            settings.draw(g2);
//
//
//        }
//
//
//    }
//
//    public void handleMouseMove(MouseEvent e){
//
//    }
//    public void handleMouseClick(MouseEvent e){
//        button.handleMouseEvent(e);
//        settings.handleMouseEvent(e);
//    }
//    public void drawToolbar(){
//
//
////        int y = game.getHeight() - 140;
////        int x = 10;
//
//
//     //   for (int i = 0; i < 9; i++) {
//
////            slot1 = new Slot(x, y, 64, 64);
////
////            slot1.setOnClick(() -> {
////                System.out.println("slot clicked!");
////            });
////            slot1.draw(g2);
////           // g2.drawRect(x, y, 64, 64);
////            x += 74;
//      //  }
//
//
////        y = game.getHeight() - 140;
////        x = 10;
////
////        for (int i = 0; i < game.player.inventory.size(); i++) {
////            Entity item = game.player.inventory.get(i);
////            if (item != null) {
////                g2.drawImage(item.hold, x, y, 64, 64, null);
////            }
////            x += 74;
////        }
//    }
//
//    public void drawDialogueScreen() {
//
//        //WINDOWS
//        int x = TILE_SIZE / 2;
//        int y = TILE_SIZE / 2;
//        int width = game.getWidth() - (TILE_SIZE * 4);
//        int height = TILE_SIZE * 4;
//        drawSubWindow(x, y, width, height);
//
//
//        g2.setFont(g2.getFont().deriveFont(15F));
//        x += TILE_SIZE;
//        y += TILE_SIZE;
//        for (String s : currentDialogue.split("\n")) {
//            g2.drawString(s, x, y);
//            y += 30;
//        }
//    }
//
//    private void drawPlayerStata() {
//        int frameX = TILE_SIZE * 2;
//        int frameY = TILE_SIZE * 2;
//        int frameW = TILE_SIZE * 5;
//        int frameH = TILE_SIZE * 10;
//        drawSubWindow(frameX, frameY, frameW, frameH);
//
//        g2.setColor(Color.WHITE);
//        g2.setFont(g2.getFont().deriveFont(32F));
//        int textX = frameX + 20;
//        int textY = frameY + TILE_SIZE;
//        final int textH = 32;
//
//        g2.drawString("Level", textX, textY);
//        textY += textH;
//        g2.drawString("Life", textX, textY);
//        textY += textH;
//        g2.drawString("Strength", textX, textY);
//        textY += textH;
//        g2.drawString("Dexterity", textX, textY);
//        textY += textH;
//        g2.drawString("Attack", textX, textY);
//        textY += textH;
//        g2.drawString("Defense", textX, textY);
//        textY += textH;
//        g2.drawString("Exp", textX, textY);
//        textY += textH;
//        g2.drawString("Next Level", textX, textY);
//        textY += textH;
//        g2.drawString("Coin", textX, textY);
//        textY += textH;
//        g2.drawString("Weapon", textX, textY);
//        textY += textH;
//        g2.drawString("Shield", textX, textY);
//
//        int tailX = (frameX + frameW) - 20;
//        textY = frameY + TILE_SIZE;
//        String value;
//
//        value = String.valueOf(game.playerOld.level);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = game.playerOld.life + "/" + game.playerOld.maxLife;
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.strength);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.dexterity);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.attack);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.defense);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.exp);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.nextLevelExp);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.coin);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.rightHand.displayName);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//        textY += textH;
//
//        value = String.valueOf(game.playerOld.leftHand.displayName);
//        textX = getAlignRight(value, tailX, g2);
//        g2.drawString(value, textX, textY);
//
//
//
//    }
//
//    private void drawBackground() {
////        if (!drawed) {
////
////
////            int maxWorldColumn = game.getWidth() + 128 / TILE_SIZE;
////            int maxWorldRow = game.getHeight() + 128 / TILE_SIZE;
////            int tileSize = TILE_SIZE;
////            Tile[] tile = setup();
////            Random random = new Random();
////            for (int row = 0; row < maxWorldRow; row++) {
////                for (int col = 0; col < maxWorldColumn; col++) {
////                    int worldX = col * tileSize;
////                    int worldY = row * tileSize;
////
////                    int i = random.nextInt(2);
////                    g2.drawImage(tile[i].image, worldX, worldY, null);
////                }
////            }
////            drawed = true;
////        }
//
////        Dimension mapSize = game.world.getMapSize("/maps/world2.txt");
////
////        int maxWorldColumn = mapSize.width;
////        int maxWorldRow = mapSize.height;
////        int tileSize = TILE_SIZE;
////
////
////        for (int row = 0; row < maxWorldRow; row++) {
////            for (int col = 0; col < maxWorldColumn; col++) {
////                int tileNum = game.world.mapTileNum[col][row];
////                int worldX = col * tileSize;
////                int worldY = row * tileSize;
////                int screenX = worldX;
////                int screenY = worldY;
////
////                g2.drawImage(game.world.tile[tileNum].getImage(), screenX, screenY, null);
////            }
////        }
//
//    }
//
//
//
//    private void drawPlayerLife() {
////        game.player.maxLife = 10;
////        game.player.life = 9;
//        int x = TILE_SIZE / 2;
//        int y = TILE_SIZE / 2;
//
//        for (int i = 0; i < game.playerOld.maxLife / 2; i++) {
//            g2.drawImage(heartBlank, x, y, null);
//            x += TILE_SIZE / 2;
//        }
//
//        x = TILE_SIZE / 2;
//        for (int i = 0; i < game.playerOld.life / 2; i++) {
//            g2.drawImage(heartFull, x, y, null);
//            x += TILE_SIZE / 2;
//        }
//        if (game.playerOld.life % 2 != 0) {
//            g2.drawImage(heartHalf, x, y, null);
//        }
//    }
//
//    public void drawSubWindow(int x, int y, int width, int height) {
//        Color color = new Color(0, 0, 0, 200);
//        g2.setColor(color);
//        g2.fillRoundRect(x, y, width, height, 35, 35);
//
//
//        g2.setColor(Color.WHITE);
//        g2.setStroke(new BasicStroke(5));
//        g2.drawRoundRect(x, y, width, height, 35, 35);
//
//    }

}
