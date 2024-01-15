package superposiciones;

import java.awt.Graphics2D;

public class Rectangulo {
    
    private int x, y;
    private int ancho, altura;
    private static final int LONGITUD_RECTANGULO = 500;

    public Rectangulo() {}

    public Rectangulo(int x, int y, int ancho, int altura) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.altura = altura;
    }

    public Rectangulo(int x, int y) {
        this.x = x;
        this.y = y;
        this.ancho = LONGITUD_RECTANGULO;
        this.altura = LONGITUD_RECTANGULO;
    }

    public void dibujarRectangulo (Graphics2D g2) {
        if(this.ancho > 0 && this.altura > 0)
            g2.fillRect(x, y, this.ancho, this.altura);
        g2.fillRect(x, y, LONGITUD_RECTANGULO, LONGITUD_RECTANGULO);
    }

    public int getAncho() {
        return this.ancho <= 0 ? LONGITUD_RECTANGULO: this.ancho;
    }

    public int getAltura() {
        return this.altura <= 0 ? LONGITUD_RECTANGULO : this.altura;
    }

    public void setLocalizacion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDimension(int ancho, int altura) {
        this.ancho = ancho;
        this.altura = altura;
    }

    public static int getPreferenciaTamano() {
        return LONGITUD_RECTANGULO;
    }
}
