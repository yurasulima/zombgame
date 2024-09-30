package io.mblueberry.util.mapseditor;

import javax.swing.*;
import java.awt.*;

public class MapsEditorMain {



    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MapsEditor mapsEditor = new MapsEditor();
        window.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        window.add(mapsEditor);
        mapsEditor.setup();
        mapsEditor.start();


        window.pack();
        window.setVisible(true);
    }
}
