package ecuaciones;

import java.awt.Color;
import java.awt.Graphics;

public final class Plano {

    private lista_plano lista = new lista_plano();

}

class lista_plano {

    private nodo_plano inicio;
    private nodo_plano fin;
    private static int tam;

    public void almacenar(nodo_plano n) {
        if (estaVacio()) {
            inicio = fin = n;
        } else {
            fin.sig = n;
            fin = n;
        }
        tam++;
    }

    public void eliminar(String titulo) {
        if (!estaVacio()) {
            if (inicio.getTitulo_plano().equalsIgnoreCase(titulo)) {
                inicio = inicio.sig;
            } else if (fin.getTitulo_plano().equalsIgnoreCase(titulo)) {
                nodo_plano temp = inicio;
                nodo_plano anterior = null;
                while (temp != null && !temp.getTitulo_plano().equalsIgnoreCase(fin.getTitulo_plano())) {
                    anterior = temp;
                    temp = temp.sig;
                }
                anterior.sig = null;
                fin = anterior;
            } else {
                nodo_plano temp = inicio;
                nodo_plano anterior = null;
                while (!temp.getTitulo_plano().equalsIgnoreCase(titulo)) {
                    anterior = temp;
                    temp = temp.sig;
                }
                anterior.sig = temp.sig;
                if (anterior.sig == null) {
                    fin = anterior;
                }
            }
            tam--;
        }
    }

    public boolean estaVacio() {
        return inicio == null;
    }
}

class nodo_plano {

    protected nodo_plano sig;
    private String titulo_plano;

    public String getTitulo_plano() {
        return this.titulo_plano;
    }

    public void setTitulo_plano(String titulo) {
        this.titulo_plano = titulo;
    }
}
