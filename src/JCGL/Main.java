package JCGL;

import javax.swing.JFrame;

/**
 * Conway's Game of Life
 * author Djordje Gavrilovic
 * 2019
 */
public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("JAVA CGL");
        frame.setSize(1000,500);
        frame.add(new Game());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
