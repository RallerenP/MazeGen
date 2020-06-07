import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.ArrayList;

public class Cell {
    int x;
    int y;

    int w;

    boolean visited = false;

    boolean[] walls;

    Sketch sketch;

    public Cell(int x, int y, int w, Sketch sketch) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.sketch = sketch;
        //TOP //RIGHT //BOTTOM  //LEFT
        walls = new boolean[]{true, true, true, true};
    }

    public void highlight() {
        int x = this.x * w;
        int y = this.y * w;

        sketch.noStroke();
        sketch.fill(0, 255, 150);
        sketch.rect(x, y, w, w);
    }

    public void show() {
        int x = this.x * w;
        int y = this.y * w;
//        if (visited) {
//            sketch.noStroke();
//            sketch.fill(255, 0, 150);
//            sketch.rect(x, y, w, w);
//        }

        sketch.stroke(0);
        sketch.strokeWeight(5);

        if (walls[0])
            sketch.line(x, y, x + w, y);
        if (walls[1])
            sketch.line(x + w, y, x + w, y + w);
        if (walls[2])
            sketch.line(x + w, y + w, x, y + w);
        if (walls[3])
            sketch.line(x, y + w, x, y);



    }

    public FakeCell fake(int x, int y, PGraphics gObj) {
        FakeCell cell = new FakeCell(x, y, w, walls, gObj);
        cell.walls = walls;

        return cell;
    }

    private Cell index(int x, int y) {
        if (x < 0 || y < 0 || x > sketch.cols - 1 || y > sketch.rows - 1) return null;

        return sketch.grid.get(x + y * sketch.cols);
    }

    public Cell checkNeighbors() {
        ArrayList<Cell> neighbors = new ArrayList<>();
        ArrayList<Cell> grid = sketch.grid;

        Cell top = index(x, y - 1);
        Cell right = index(x + 1, y);
        Cell bottom = index(x, y + 1);
        Cell left = index(x - 1, y);

        if (top != null && !top.visited)
            neighbors.add(top);

        if (right != null && !right.visited)
            neighbors.add(right);

        if (bottom != null && !bottom.visited)
            neighbors.add(bottom);

        if (left != null && !left.visited)
            neighbors.add(left);

        if (neighbors.size() > 0) {
            int r = Sketch.floor(sketch.random(0, neighbors.size()));
            return neighbors.get(r);
        } else {
            return null;
        }
    }

    public ArrayList<Cell> getSurrounding() {
        ArrayList<Cell> surrounding = new ArrayList<>();

        Cell top = index(x, y - 1);
        Cell topLeft = index(x - 1, y - 1);
        Cell topRight = index(x + 1, y - 1);
        Cell right = index(x + 1, y);

        Cell bottom = index(x, y + 1);
        Cell bottomRight = index(x + 1, y + 1);
        Cell bottomLeft = index(x - 1, y + 1);
        Cell left = index(x - 1, y);

        surrounding.add(topLeft);
        surrounding.add(top);
        surrounding.add(topRight);
        surrounding.add(left);
        surrounding.add(this);
        surrounding.add(right);
        surrounding.add(bottomLeft);
        surrounding.add(bottom);
        surrounding.add(bottomRight);

        return surrounding;
    };
}
