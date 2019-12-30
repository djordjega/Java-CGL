package JCGL;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Cell class
 * can be dead or alive
 */
class Cell {
    boolean status;
    int position_x; // column
    int position_y; // row

    public boolean isAlive() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPosition_x() {
        return position_x;
    }

    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }
}

/**
 * Game class
 * main application logic
 */
public class Game extends JPanel {

    int year = 0;
    int births = 0;
    int deaths = 0;
    Cell[][] world; // array of life - cells 50x20

    public Game() {
        this.setBackground(Color.BLACK);
        startNewYear(year);
    }

    /**
     * GAME LOOP
     * 10 start year
     * @param new_year
     */
    private void startNewYear(int new_year) {
        switch (new_year) {
            case 0:
                firstLife();
                break;
            case 201:
                endWorld();
                break;
            default:
                checkCellStatus();
                break;
        }
    }

    /**
     * INITIAL LIFE - YEAR 0
     * 20 randomly generate x number of entities/cells
     */
    private void firstLife() {
        world = new Cell[20][50];
        Random r;
        Cell cell;

        for(int i=0; i<world.length; i++) {
            for(int j=0; j<world[i].length; j++) {
                r = new Random();
                cell = new Cell();
                boolean initial_status = r.nextBoolean();
                cell.setStatus(initial_status);
                cell.setPosition_x(j);
                cell.setPosition_y(i);
                world[i][j] = cell;
            }
        }
        repaint();
    }

    /**
     * EVALUATE EVERY CELL STATUS
     * 40 check cell status - go trough the rules for every cell
     */
    private void checkCellStatus() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Cell cell;
        boolean status;

        for (int i=0; i<world.length; i++) {
            for (int j=0; j<world[i].length; j++) {
                cell = world[i][j];
                int neighbours = getNumberOfNeighbours(i,j);
                // RULE 1 - Any live cell with fewer than two live neighbours dies (as if by needs caused by underpopulation).
                // RULE 2 - Any live cell with more than three live neighbours dies (as if by overcrowding).
                // RULE 3 - Any live cell with two or three live neighbours lives (unchanged, to the next generation).
                if ( cell.isAlive() && ( neighbours == 2 || neighbours == 3 ) ) {
                    continue;
                } else if ( !cell.isAlive() && neighbours == 3 ) {
                    // RULE 4 - Any dead cell with exactly three live neighbours becomes a live cell (as if by reproduction).
                    cell.setStatus(true);
                    births++;
                } else {
                    cell.setStatus(false);
                    //deaths++; // no good ?!
                }
            }
        }
        repaint();
    }

    /**
     * TODO
     * @param row world[]
     * @param column world[][]
     * @return int number of neighbours
     */
    private int getNumberOfNeighbours(int row, int column) {
        int neighbours=0;

        try {
            if (world[row-1][column-1].isAlive()) { neighbours++; }
        } catch (IndexOutOfBoundsException e) {}

        try {
            if (world[row-1][column].isAlive()) { neighbours++; }
        } catch (IndexOutOfBoundsException e) {}

        try {
            if (world[row-1][column+1].isAlive()) { neighbours++; }
        } catch (IndexOutOfBoundsException e) {}

        try {
            if (world[row][column-1].isAlive()) { neighbours++; }
        } catch (IndexOutOfBoundsException e) {}

        try {
            if (world[row][column+1].isAlive()) { neighbours++; }
        } catch (IndexOutOfBoundsException e) {}

        try {
            if (world[row+1][column-1].isAlive()) { neighbours++; }
        } catch (IndexOutOfBoundsException e) {}

        try {
            if (world[row+1][column].isAlive()) { neighbours++; }
        } catch (IndexOutOfBoundsException e) {}

        try {
            if (world[row+1][column+1].isAlive()) { neighbours++; }
        } catch (IndexOutOfBoundsException e) {}

        return neighbours;
    }

    /**
     * GAME END
     * 50 end
     */
    private void endWorld() {
        System.out.println("This world has reached" + year + " years. It is time to start over and create a brand new one.");
    }

    /**
     * DRAW WORLD-MAP FROM ARRAY OF LIFE
     * 30 populate world
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Cell cell;
        int population = 0;

        for (int i=0; i<world.length; i++) {
            for (int j=0; j<world[i].length; j++) {
                cell = world[i][j];
                g.setColor(Color.DARK_GRAY);
                g.drawRect(cell.getPosition_x()*20, cell.getPosition_y()*20,20,20);
                if (cell.isAlive()) {
                    population++;
                    g.setColor(Color.decode("#24b30e"));
                    g.fillOval(cell.getPosition_x()*20, cell.getPosition_y()*20,20,20);
                    g.setColor(Color.GREEN);
                    g.fillOval(cell.getPosition_x()*20+3, cell.getPosition_y()*20+3,15,15);
                }
            }
        }

        g.setColor(Color.RED);
        g.drawString("CGL YEAR: " + year, 20, 35);
        g.drawString("POPULATION: " + population, 20, 55);
        g.drawString("BIRTHS: " + births, 20, 75);
        g.drawString("DEATHS: " + deaths, 20, 95);

        year++;
        startNewYear(year);
    }

}
