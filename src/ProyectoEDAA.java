import Logica.LecturaEscritura;
import Logica.Grafo;

public class ProyectoEDAA {    
    public static void main(String[] args) {
        LecturaEscritura prueva = new LecturaEscritura();
        Grafo grafito = new Grafo();
        Grafo grafite = new Grafo();
        grafito.agregarVertice("3186779701", 12, "gdsgdgsgdsg", "Movistar", 0, 0, 5);
        grafito.agregarVertice("3186779488", 10, "gdsgdgsgdsg", "Movistar", 1, 0, 10);
        grafito.imprimirGrafo();
        
        //prueva.gLista(grafito, "proyecto_edd\\Privado\\Gr.txt");
        grafite = (Grafo) prueva.lObjeto("proyecto_edd\\Privado\\Gr.txt");
        grafite.imprimirGrafo();
    }   
}