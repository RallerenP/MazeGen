import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Sketch extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Sketch");
    }

    int cols;
    int rows;

    int cW = 1024 / 8;
    
    ArrayList<Cell> grid = new ArrayList<>();
    Cell current;

    ArrayList<Cell> stack = new ArrayList<>();

    public void settings() {
        size(1024, 1024);


        cols = (int) Math.floor(width / cW);
        rows = (int) Math.floor(width / cW);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Cell cell = new Cell(x, y, cW, this);
                grid.add(cell);
            }
        }

        current = grid.get(0);
    }

    public void setup() {
        frameRate(25);
    }
    
    public void draw() {
        background(255);
        for (Cell cell : grid) {
            cell.show();

        }

        do {
            current.visited = true;
            if (!stack.isEmpty())
                current.highlight();
            Cell next = current.checkNeighbors();

            if (next != null) {
                next.visited = true;

                // Step 2
                stack.add(next);

                // Step 3
                removeWalls(current, next);

                current = next;
            } else if (stack.size() > 0) {
                current = stack.get(stack.size() - 1);
                stack.remove(current);
            }
        } while (!stack.isEmpty());


    }

    public void keyPressed() {
        print(keyCode);
        loadPixels();

        if (keyCode == 10) {
            for (Cell cell : grid) {

                ArrayList<Cell> surrounding = cell.getSurrounding();

                println(surrounding);

                PGraphics pg = createGraphics(3 * cW, 3 * cW);

                pg.beginDraw();
                pg.background(205,0,205);

                int x = 0;
                int y = 0;
                for (Cell curr : surrounding) {
                    int currIndex = surrounding.indexOf(curr);

                    if (curr != null) {
                        FakeCell clone = curr.fake(x, y, pg);
                        println(clone);
                        clone.show();
                    }

                    if (x++ == 2) {
                        y++;
                        x = 0;
                    }
                }

                pg.stroke(255, 0,0);
                pg.circle((pg.width / 2) - (5 / 2), (pg.width / 2) - (5 / 2), 5);

                pg.endDraw();

                PImage img = pg.get();
                img.save(grid.indexOf(cell) + ".png");
            }


        }
    }

    int getPixel(int x, int y) {
        return pixels[y + width * x];
    };

    int getPixelIndex(int x, int y, PImage img) {
        return y + img.width * x;
    }

    void removeWalls(Cell a, Cell b) {
        int x = a.x - b.x;
        if (x == 1) {
            a.walls[3] = false;
            b.walls[1] = false;
        } else if (x == -1) {
            a.walls[1] = false;
            b.walls[3] = false;
        }
        int y = a.y - b.y;
        if (y == 1) {
            a.walls[0] = false;
            b.walls[2] = false;
        } else if (y == -1) {
            a.walls[2] = false;
            b.walls[0] = false;
        }
    }
}
