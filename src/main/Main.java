package src.main;

import java.awt.*;
import javax.swing.*;


public class Main {
    static final Color game_bgColor = new Color(25,29,50);
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World");
        frame.getContentPane().setBackground(game_bgColor);
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000,1000));
        frame.setLocationRelativeTo(null);

        Board board = new Board();
        frame.add(board);

        frame.setVisible(true);
    }
}


