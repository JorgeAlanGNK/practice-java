package superposiciones;

import java.awt.Graphics2D;
import java.awt.Color;

public class Enlace {
    private int x1, x2, y1, y2;

    public Enlace(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public void dibujarEnlace(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.drawLine(x1, x2, y1, y2);
    }
}
