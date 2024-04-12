package src.main;

import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {

    // constants
    final int cols = 8;
    final int rows = 8;

    public final int tileSize = 100;
    
    final Color dark_tile = new Color(0, 0, 0);
    final Color light_tile = new Color(255, 255, 255);

    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        //this.setBackground(Color.GRAY);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if ((r + c) % 2 == 0) {
                    g2d.setColor(light_tile);
                } else {
                    g2d.setColor(dark_tile);
                }
                //(x, y, width, height)
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize); 
            }
        }
    }
}