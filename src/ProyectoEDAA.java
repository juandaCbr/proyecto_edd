import Logica.LecturaEscritura;
import Logica.Grafo;

public class ProyectoEDAA {    
    public static void main(String[] args) {
        LecturaEscritura prueva = new LecturaEscritura();
        Grafo grafito = new Grafo();
        Grafo grafite = new Grafo();
        grafito.agregarVertice("3186779701", 12, grafito.new Direccion((byte)1,(byte)1,(byte)1,"Jose"), "Movistar");
        grafito.agregarVertice("3186779488", 10, grafito.new Direccion((byte)1,(byte)1,(byte)1,"Jose"), "Movistar");
        grafito.imprimirGrafo();
        
        prueva.gObjeto(grafito, "Privado\\Gr.txt");
        grafite = (Grafo) prueva.lObjeto("Privado\\Gr.txt");
        grafite.imprimirGrafo();
    }   
}