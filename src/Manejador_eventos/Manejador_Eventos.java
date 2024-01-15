package Manejador_eventos;

import java.awt.event.AWTEventListener;
import java.awt.AWTEvent;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import superposiciones.Rectangulo;

public class Manejador_Eventos {

    private static Manejador_Eventos eventos;

    private Manejador_Eventos() {
    }

    public static synchronized Manejador_Eventos getInstance() {
        if (eventos == null) {
            eventos = new Manejador_Eventos();
        }
        return eventos;
    }

    public void Cerrar_ventana(JFrame main) {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                main.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                if (event.getID() == WindowEvent.WINDOW_CLOSING) {
                    int valor = JOptionPane.showConfirmDialog(main, "¿Quiere cerrar la aplicación?",
                            "Aplicación dice...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (valor == JOptionPane.YES_OPTION) {
                        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        main.dispose();
                    }
                }
            }
        }, AWTEvent.WINDOW_EVENT_MASK);
    }

    public void componentResized(JFrame window, JPanel predeterminado, JPanel modal) {
        Toolkit.getDefaultToolkit().addAWTEventListener((event) -> {
            if (event.getID() == ComponentEvent.COMPONENT_RESIZED) {

            }
        }, AWTEvent.COMPONENT_EVENT_MASK);
    }

    public void componentMoved(JPanel panel) {
        Toolkit.getDefaultToolkit().addAWTEventListener((e) -> {
            if (e.getID() == ComponentEvent.COMPONENT_MOVED) {
                int offsetX = panel.getWidth() / 2 - Rectangulo.getPreferenciaTamano() / 2;
                int offsetY = panel.getHeight() / 2  - Rectangulo.getPreferenciaTamano() / 2;
                if (new Rectangle(offsetX, offsetY, Rectangulo.getPreferenciaTamano(), Rectangulo.getPreferenciaTamano())
                        .contains(0, 0, panel.getWidth(), panel.getHeight())) {
                    System.out.println("lo tiene");
                }
            }
        }, ComponentEvent.COMPONENT_EVENT_MASK);
    }

}
