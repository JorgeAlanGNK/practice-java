package ecuaciones;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.Point;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import java.awt.geom.AffineTransform;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import Manejador_eventos.Manejador_Eventos;
import superposiciones.Rectangulo;
import superposiciones.Enlace;

public class Plano_grafica extends javax.swing.JPanel {

    private String nombre_fuente;
    private int fuentes[] = new int[3];
    private int escala = 10;
    private int zoomPointX, zoomPointY;
    private double zoom;
    private int mouseX, mouseY;
    private int centroX, centroY;
    private int OffsetX, OffsetY;
    private static Manejador_Eventos eventos;
    private Rectangulo rect;
    private List<Enlace> intersecciones = new ArrayList<>();

    public Plano_grafica() {
        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // conversión hacia los puntos del rectangulo
        this.OffsetX = getWidth() / 2 - Rectangulo.getPreferenciaTamano() / 2;
        this.OffsetY = getHeight() / 2 - Rectangulo.getPreferenciaTamano() / 2;
        // iniciar el plano cartesiano
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        Font font = new Font(setAttributesFont());
        renderizaciones(g2, font);/* tomar los valores de un componente padre */
        // Se debe de colocar una figura para comenzar a realizar un zoom o movimiento
        // de JPanel, no tiene configuración predeterminada
        // iniciar_zoom(g2);
        g2.setColor(new Color(255, 255, 255));
        rect = new Rectangulo(OffsetX, OffsetY);
        rect.dibujarRectangulo(g2);
        // se dibuja despues las coordenadas, se puede utilizar la clase rectangle2d
        // para saber que se diubja, pero no es recomendable mutar los objetos
        // conversion hacia las orillas del rectangulo en X;Y
        this.centroX = OffsetX + rect.getAncho() / 2;
        this.centroY = OffsetY + rect.getAltura() / 2;
        // intersecciones.add(new Enlace(centroY, centroY, centroX, centroY));
        // establecer las limitantes del área del rectangulo
        int rectX1 = Math.min(OffsetX, centroX);
        int rectX2 = Math.max(OffsetX + Rectangulo.getPreferenciaTamano(), centroX);
        new Enlace(rectX1, centroY, rectX2, centroY).dibujarEnlace(g2);
        int rectY1 = Math.min(OffsetY, centroY);
        int rectY2 = Math.max(OffsetY + Rectangulo.getPreferenciaTamano(), centroY);
        new Enlace(centroX, rectY1, centroX, rectY2).dibujarEnlace(g2);
        ejes_x(g2, rectX1, rectX2, centroX, centroY);
        ejes_y(g2, rectY1, rectY2, centroX, centroY);
    }

    /*Solo utilizar en los métodos de eventos de swing o awt, también sirve para paintComponent*/
    private void repintar(int scaleX, int scaleY) {
        Graphics2D g2 = (Graphics2D) getGraphics();
        g2.clearRect(this.OffsetX, this.OffsetY, rect.getAncho(), rect.getAltura());
        intersecciones.clear();
        // g2.fillRect(this, scaleY, scaleX, scaleY);
        int rectX1 = Math.min(OffsetX, centroX);
        int rectX2 = Math.max(OffsetY + Rectangulo.getPreferenciaTamano(), centroX);
        int rectY1 = Math.min(OffsetY, centroY);
        int rectY2 = Math.max(OffsetY + Rectangulo.getPreferenciaTamano(), centroY);
        ejes_x(g2, rectX1, rectX2, rectY1, rectY2);
        ejes_y(g2, rectY1, rectY2, rectY1, rectY2);
        /*Mover las ejes de y hacia la derecha */
        // rectX1 = rectX1 + scaleX;
        // rectX2 = rectX2 + scaleX;
        /*Mover las ejes de x hacia bottom*/
        // rectY1 = rectY1 + scaleY;
        // rectY1 = rectY2 + scaleY;
    }

    private void renderizaciones(Graphics2D g2, Font letra) {
        /* FontMetrics metrics = g2.getFontMetrics(); */
        g2.setFont(letra);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    }

    private void ejes_x(Graphics2D g2, int rectX1, int rectX2, int centroX, int centroY) {
        /* Configuración de la imagen de la letra */
        /*
         * Dibujo con el plano cartesiano
         * Precaucion cuello de botella en java por demasiados ciclos
         */
        for (int eje_x = centroX, eje_x_neg = centroX, secuencia = 0; eje_x > rectX1
                && eje_x < rectX2; secuencia++, eje_x += escala, eje_x_neg -= escala) {
            g2.drawString(String.valueOf(secuencia), eje_x, centroY);
            g2.drawString(String.valueOf(secuencia), eje_x_neg, centroY);
        }
    }

    private void ejes_y(Graphics2D g2, int rectY1, int rectY2, int centroX, int centroY) {
        for (int eje_y = centroY, secuencia = 0, eje_neg_y = centroY; eje_y > rectY1
                && eje_y < rectY2; eje_y += escala, secuencia++, eje_neg_y -= escala) {
            g2.drawString(String.valueOf(secuencia * -1), centroX, eje_y);
            g2.drawString(String.valueOf(secuencia), centroX, eje_neg_y);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        eventos = Manejador_Eventos.getInstance();
        eventos.componentMoved(this);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        setLayout(null);
        setBackground(new Color(0, 204, 204));
    }// </editor-fold>

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
        if(esContenedor(evt.getPoint())){
            if(evt.getPreciseWheelRotation() >= 1.0) {//para rotacion hacia abajo
                escala -= 10;
                if(escala <= 10) {
                    escala = 10;
                }
            } else if(evt.getPreciseWheelRotation() <= -1.0) {
                escala += 10;
                if(escala >= 70) {
                    escala = 70;
                }
            }
            int scaleY = (evt.getY() - OffsetY) * escala / escala + OffsetY;
            int scaleX = (evt.getX() - OffsetX) * escala / escala + OffsetX;
            repintar(scaleX, scaleY);
            repaint();
            // int scaleY = (evt.getX() - OffsetY) * escala / escala + OffsetY;
        }
    }

    private boolean esContenedor(Point p) {
        return new Rectangle(this.OffsetX, this.OffsetY, rect.getAncho(), rect.getAltura()).contains(p);
    }

    private void formMouseDragged(java.awt.event.MouseEvent evt) {
        if (SwingUtilities.isLeftMouseButton(evt) && esContenedor(evt.getPoint())) {
            
        }
    }

    private void formMousePressed(java.awt.event.MouseEvent evt) {
        mouseX = evt.getX();
        mouseY = evt.getY();
    }

    private Map<TextAttribute, Object> setAttributesFont() {
        Map<TextAttribute, Object> atributos = new HashMap<>();
        atributos.put(TextAttribute.FAMILY, "Arial");
        atributos.put(TextAttribute.SIZE, 8);
        atributos.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        atributos.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
        atributos.put(TextAttribute.FOREGROUND, Color.RED);
        return atributos;
    }

    // Variables declaration - do not modify
    // End of variables declaration
}
