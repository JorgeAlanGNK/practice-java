package paneles;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.HashMap;

public class Operacion extends JPanel {

    private JButton aceptar;
    private JTextField expresion, valor_x;
    private JLabel eti1, eti2;

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    }

    public Operacion() {
        setLayout(new GridBagLayout());
        setBackground(Color.RED);
        setBorder(BorderFactory.createTitledBorder(getBorder(), "Expresiones", TitledBorder.CENTER, TitledBorder.DEFAULT_JUSTIFICATION, new Font("Calibri", Font.PLAIN, 14)));
        Font letra = new Font("Arial", Font.BOLD, 12);
        GridBagConstraints gbc = new GridBagConstraints();
        eti1 = new JLabel("Operación");
        eti1.setFont(letra);
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(eti1, gbc);
        eti2 = new JLabel("Configuraciones");
        eti2.setFont(letra);
        gbc.gridy = 0;
        gbc.gridx = 1;
        add(eti2, gbc);
        /*Segunda columna*/
        gbc.anchor = GridBagConstraints.BELOW_BASELINE;
        expresion = new JTextField(10);
        expresion.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(expresion, gbc);
        valor_x = new JTextField(10);
        gbc.insets = new Insets(15,0,15,0);
        valor_x.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        gbc.gridx = 1;
        add(valor_x, gbc);
        aceptar = new JButton("Aceptar");
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.5;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(aceptar, gbc);
    }

    public static Map<TextAttribute, Object> RenderizacionTexto() {
        Map<TextAttribute, Object> attr = new HashMap<>();
        attr.put(TextAttribute.FAMILY, "Arial");
        attr.put(TextAttribute.SIZE, 8);
        attr.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        attr.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
        attr.put(TextAttribute.FOREGROUND, Color.RED);
        return attr;
    }
    
}
