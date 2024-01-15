package lista_fuente;

import java.util.List;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.JOptionPane;

public final class TodoFuente {

    private final Font nombre_fuente;
    private final int tipo_fuente[];/*máximo 3*/
    private final String type;

    public TodoFuente(final Font nombre_fuente, final String type, final int... tipos) {
        this.nombre_fuente = nombre_fuente;
        this.type = type;
        this.tipo_fuente = tipos;
    }

    public Font getFuente() {
        return this.nombre_fuente;
    }

    public String getType() {
        return this.type;
    }

    public int tipo_fuente() {
        int variable = 0;
        if (tipo_fuente.length + 1 == 1 && tipo_fuente != null) {
            variable = tipo_fuente[0];
        } else if (tipo_fuente.length + 1 == 2 && tipo_fuente != null) {
            variable = tipo_fuente[0] | tipo_fuente[1];
        } else if (tipo_fuente.length + 1 == 3) {
            variable = tipo_fuente[0] | tipo_fuente[1] | tipo_fuente[2];
        }
        return variable;
    }
}

class AlmacenFuente {

    private List<Font> fuentes = new ArrayList<>();
    private List<IFuenteListener> onChange = new ArrayList<>();

    public void addListener(IFuenteListener l) {
        onChange.add(l);
    }

    public void removeListener(IFuenteListener l) {
        onChange.remove(l);
    }

    public List<Font> getFuentes() {
        return new ArrayList<>(fuentes);
    }

    public void addFuente(Font font) {
        fuentes.add(font);
        emitChange();
    }

    public void deleteFuente(Font font) {
        Font e = null;
        if (!fuentes.isEmpty() && fuentes.indexOf(font) != -1) {
            if (fuentes.getLast().equals(font)) {
                e = fuentes.remove(fuentes.size() - 1);
            } else if (fuentes.getFirst().equals(font)) {
                e = fuentes.remove(0);
            } else {
                e = fuentes.remove(fuentes.indexOf(font));
            }
            emitRemove(e);
        }
    }

    public int getSize() {
        return fuentes.size();
    }

    private void emitChange() {
        for (IFuenteListener fuente : onChange) {
            fuente.changeLetter();
        }
    }

    private void emitRemove(Font remove) {
        for (IFuenteListener fuente : onChange) {
            fuente.deleteLetter(remove);
        }
    }
}

interface IFuenteListener {

    void changeLetter();

    void deleteLetter(Font delete);
}

class FuenteDispatcher {

    private List<AlmacenFuente> fuentes = new ArrayList<>();

    public void registrarFuente(AlmacenFuente almacen_fuente) {
        fuentes.add(almacen_fuente);
    }

    public void eliminarFuente(AlmacenFuente almacen_fuente) {
        fuentes.add(almacen_fuente);
    }

    public void dispatcherTodoFuente(TodoFuente todo_fuente) {
        for (AlmacenFuente almacen_fuente : fuentes) {
            handleFontAction(almacen_fuente, todo_fuente);
        }
    }

    private void handleFontAction(AlmacenFuente almacen_fuente, TodoFuente todo_fuente) {
        if ("ADD_FONT".equals(todo_fuente.getType())) {
            almacen_fuente.addFuente(todo_fuente.getFuente());
        }
        switch (todo_fuente.getType().trim()) {
            case "ADD_FONT":
                almacen_fuente.addFuente(todo_fuente.getFuente());
                break;
            case "DELETE_FONT":
                almacen_fuente.deleteFuente(todo_fuente.getFuente());
                break;
        }
    }
}

class FondoCambio implements IFuenteListener {

    private AlmacenFuente almacen_fuente;

    public FondoCambio(AlmacenFuente almacen_fuente) {
        this.almacen_fuente = almacen_fuente;
        almacen_fuente.addListener(this);
    }

    @Override
    public void changeLetter() {
        JOptionPane.showMessageDialog(null, "Se ha agregado la fuente: " + almacen_fuente.getFuentes().getLast().getFontName());
    }

    @Override
    public void deleteLetter(Font delete) {
        if (!almacen_fuente.getFuentes().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Se ha eliminado la fuente:" + delete.getFontName());
        } else {
            JOptionPane.showMessageDialog(null, "La lista se encuentra vacía");
        }
    }
}
