package JCGL;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Cell class
 * can be dead or alive
 */
class Cell implements Cloneable{

    boolean status; // dead or alive
    int position_x; // column
    int position_y; // row

    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }

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
    static int rows = 23;
    static int columns = 50;
    Cell[][] world; // array of life - cells 50x23
    Cell[][] limbo; // world in between two realms
    Cell cell; // resident of the world
    Cell ghost; // resident of limbo


    public Game() {
        this.setBackground(Color.BLACK);
        startNewYear(year);
    }

    /**
     * deep copy of multidimensional object array
     * @param src - input array
     * @return temp - copied array
     */
    public static Cell[][] MDOAcopy(Cell[][] src) {

        Cell[][] temp = new Cell[rows][columns];

        for (int i = 0; i < src.length; i++) {
            temp[i] = new Cell[src[i].length];

            for (int j = 0; j < src[i].length; j++) {
                try {
                    temp[i][j] = (Cell)src[i][j].clone();
                }catch(CloneNotSupportedException c){
                    System.out.println(c.getMessage());
                }
            }
        }

        return temp;
    }

    /**
     * GAME LOOP
     * 10 start year
     *
     * @param newYear - number of a year to come
     */
    private void startNewYear(int newYear) {
        switch (newYear) {
            case 0:
//                firstLife();
                patternBeginning();
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
     * create the initial world from a pattern
     */
    private void patternBeginning() {

        world = new Cell[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cell = new Cell();
                cell.setPosition_x(column);
                cell.setPosition_y(row);

                // glider
//                if (row == 10 && column == 25) {
//                    cell.setStatus(true);
//                } else if (row == 11 && column == 26) {
//                    cell.setStatus(true);
//                } else if (row == 12 && column == 24) {
//                    cell.setStatus(true);
//                } else if (row == 12 && column == 25) {
//                    cell.setStatus(true);
//                } else if (row == 12 && column == 26) {
//                    cell.setStatus(true);
//                } else {
//                    cell.setStatus(false);
//                }

                // oscillator
//                if (row == 10 && column == 25) {
//                    cell.setStatus(true);
//                } else if (row == 10 && column == 26) {
//                    cell.setStatus(true);
//                } else if (row == 10 && column == 27) {
//                    cell.setStatus(true);
//                } else {
//                    cell.setStatus(false);
//                }

                // crazy tetromino
                if (row == 10 && column == 25) {
                    cell.setStatus(true);
                } else if (row == 11 && column == 24) {
                    cell.setStatus(true);
                } else if (row == 11 && column == 25) {
                    cell.setStatus(true);
                } else if (row == 11 && column == 26) {
                    cell.setStatus(true);
                } else {
                    cell.setStatus(false);
                }

//                // tetrominos' death
//                if (row == 10 && column == 25) {
//                    cell.setStatus(true);
//                } else if (row == 11 && column == 26) {
//                    cell.setStatus(true);
//                } else if (row == 11 && column == 27) {
//                    cell.setStatus(true);
//                } else if (row == 11 && column == 28) {
//                    cell.setStatus(true);
//                } else {
//                    cell.setStatus(false);
//                }



                world[row][column] = cell;
            }
        }

        repaint();
    }

    /**
     * INITIAL LIFE - YEAR 0
     * 20 randomly generate x number of entities/cells
     */
    private void firstLife() {
        world = new Cell[rows][columns];
        Random r;

        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
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
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        limbo = MDOAcopy(world);

        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {

                cell = world[i][j];
                ghost = limbo[i][j];
                int neighbours = getNumberOfNeighbours(i, j);

                // cell is lonely and dies
                if (cell.isAlive() && neighbours < 2) {
                    ghost.setStatus(false);
                    deaths++;
                }

                // cell dies due to overpopulation
                if (cell.isAlive() && neighbours > 3) {
                    ghost.setStatus(false);
                    deaths++;
                }

                // cell is born
                if (!cell.isAlive() && neighbours == 3) {
                    ghost.setStatus(true);
                    births++;
                }
            }
        }

        world = limbo;

        repaint();
    }

    /**
     * inspect the surrounding cells and return the number of alive neighbours
     *
     * @param row    world[]
     * @param column world[][]
     * @return int number of neighbours
     */
    private int getNumberOfNeighbours(int row, int column) {


        int neighbours = 0;


        // terrrrribleeeeee - FIX THIS

        try {
            if (world[row - 1][column - 1].isAlive()) {
                neighbours++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (world[row - 1][column].isAlive()) {
                neighbours++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (world[row - 1][column + 1].isAlive()) {
                neighbours++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (world[row][column - 1].isAlive()) {
                neighbours++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (world[row][column + 1].isAlive()) {
                neighbours++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (world[row + 1][column - 1].isAlive()) {
                neighbours++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (world[row + 1][column].isAlive()) {
                neighbours++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (world[row + 1][column + 1].isAlive()) {
                neighbours++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

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
     *
     * @param g - Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int population = 0;

        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                cell = world[i][j];
                g.setColor(Color.DARK_GRAY);
                g.drawRect(cell.getPosition_x() * 20, cell.getPosition_y() * 20, 20, 20);
                if (cell.isAlive()) {
                    population++;
                    g.setColor(Color.decode("#24b30e"));
                    g.fillOval(cell.getPosition_x() * 20, cell.getPosition_y() * 20, 20, 20);
                    g.setColor(Color.GREEN);
                    g.fillOval(cell.getPosition_x() * 20 + 3, cell.getPosition_y() * 20 + 3, 15, 15);
                    g.setColor(Color.WHITE);
                    g.fillOval(cell.getPosition_x() * 20 + 5, cell.getPosition_y() * 20 + 5, 3, 3);
                }
            }
        }

        g.setColor(Color.RED);
        g.drawString("CGL YEAR: " + year, 20, 35);
        g.drawString("POPULATION: " + population, 20, 55);
        g.drawString("BIRTHS: " + births, 20, 75);
        g.drawString("DEATHS: " + deaths, 20, 95);
        g.drawString("Djordje Gavrilovic", 865, 455);

        year++;
        startNewYear(year);
    }

}
