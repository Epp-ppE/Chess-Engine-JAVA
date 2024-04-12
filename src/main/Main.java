package src.main;

import java.awt.*;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World");
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000,1000));
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.BLACK);

        Board board = new Board();
        frame.add(board);

        frame.setVisible(true);
    }
}


