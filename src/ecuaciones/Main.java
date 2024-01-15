package ecuaciones;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Toolkit;
import java.awt.Color;
import Manejador_eventos.Manejador_Eventos;
import paneles.Operacion;
import superposiciones.Rectangulo;

public class Main extends JFrame {

    private static Manejador_Eventos eventos;
    private Plano_grafica grafica;
    private Operacion funcion;
    private static final int ANCHO = Toolkit.getDefaultToolkit().getScreenSize().width,
            ALTURA = Toolkit.getDefaultToolkit().getScreenSize().height;

    public Main() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        setResizable(true);
        setVisible(true);
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        JLayeredPane posi = new JLayeredPane();
        grafica = new Plano_grafica();
        grafica.setBounds(0, 0, ANCHO, ALTURA);
        posi.setLayer(grafica, JLayeredPane.DEFAULT_LAYER);
        posi.add(grafica);
        funcion = new Operacion();
        funcion.setBounds(0, ALTURA / 2, grafica.getWidth() / 2 - Rectangulo.getPreferenciaTamano() / 2, ALTURA / 2);
        posi.setLayer(funcion, JLayeredPane.MODAL_LAYER);
        posi.add(funcion);
        add(posi);
        eventos = Manejador_Eventos.getInstance();
        eventos.Cerrar_ventana(this);
        eventos.componentResized(this, grafica, funcion);
        pack();
    }

    public static void main(String... args) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }

}
