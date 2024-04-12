package src.main;

import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {

    // constants
    final int cols = 8;
    final int rows = 8;

    public final int tileSize = 100;

    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.setBackground(Color.GREEN);
    }
}