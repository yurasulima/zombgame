package io.mblueberry.anim;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.util.List;

public class ItemAnimation {
    private List<BufferedImage> frames; // Масив кадрів анімації
    private int currentFrame; // Поточний кадр
    private int frameCount; // Загальна кількість кадрів
    private int frameDelay; // Затримка між кадрами
    private int frameTicker; // Лічильник затримки
    @Getter
    private boolean finished; // Ознака завершення анімації

    public ItemAnimation(List<BufferedImage> frames, int frameDelay) {
        this.frames = frames;
        this.frameCount = frames.size();
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.frameTicker = 0;
        this.finished = false;
    }

    public void update() {
        if (finished) return; // Якщо анімація завершена, нічого не робимо

        frameTicker++;

        if (frameTicker >= frameDelay) {
            frameTicker = 0; // Скидаємо лічильник затримки
            currentFrame++; // Переходимо до наступного кадру

            if (currentFrame >= frameCount) {
                finished = true; // Якщо дійшли до кінця, відмічаємо завершення анімації
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames.get(Math.min(currentFrame, frameCount - 1)); // Повертаємо поточний кадр
    }

}
