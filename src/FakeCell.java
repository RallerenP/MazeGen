import processing.core.PGraphics;

import java.util.Arrays;

public class FakeCell {
    int x;
    int y;
    int w;
    boolean[] walls;

    PGraphics pg;

    public FakeCell(int x, int y, int w, boolean[] walls, PGraphics pg) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.walls = walls;
        this.pg = pg;
    }

    @Override
    public String toString() {
        return "FakeCell{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", walls=" + Arrays.toString(walls) +
                ", pg=" + pg +
                '}';
    }

    public void show() {
        int x = this.x * w;
        int y = this.y * w;

       pg.noStroke();
       pg.fill(255);
       pg.rect(x, y, w, w);

        pg.stroke(0);
        pg.strokeWeight(5);

        if (walls[0])
            pg.line(x, y, x + w, y);
        if (walls[1])
            pg.line(x + w, y, x + w, y + w);
        if (walls[2])
            pg.line(x + w, y + w, x, y + w);
        if (walls[3])
            pg.line(x, y + w, x, y);
    }
}
