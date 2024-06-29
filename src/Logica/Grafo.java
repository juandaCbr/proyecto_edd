//11/04/24
package Logica;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Grafo implements Serializable {
    private int V;
    private LinkedList<LinkedList<Arista>> adj;
    private LinkedList<Vertice> nod;

    public int getV() {
        return V;
    }

    public LinkedList<Vertice> getNod() {
        return nod;
    }

    public LinkedList<LinkedList<Arista>> getAdj() {
        return adj;
    }

    public Grafo() {
        this.V = 1;
        this.adj = new LinkedList<LinkedList<Arista>>();
        this.nod = new LinkedList<Vertice>();
        nod.add(new Vertice(" ", 0, new Direccion((byte) 0, (byte) 0, (byte) 0, " "), " "));
        adj.add(new LinkedList<Arista>());
    }

    public void imprimirGrafo() {
        for (int i = 0; i < V; i++) {
            if (!nod.get(i).Numero.equals(" ")) {
                System.out.println("\nNumero: " + nod.get(i).Numero + ", Velocidad: " + nod.get(i).Velocidad_adquirida + ", Direccion: " + nod.get(i).direccion.imprimirD() + ", Operadora: " + nod.get(i).Operadora);
            }
        }
        System.out.println("");
    }

    private class Arista implements Serializable {
        private int peso;
        private Vertice Do, Dd;

        public Arista(int o, int d, int peso) {
            this.Do = nod.get(o);
            this.Dd = nod.get(d);
            this.peso = peso;
        }

        @SuppressWarnings("unused")
        public int getO() {
            return nod.indexOf(Do);
        }

        public int getD() {
            return nod.indexOf(Dd);
        }
    }

    private class Vertice implements Serializable {
        private String Numero, Operadora;
        private Direccion direccion;
        private int Velocidad_adquirida;

        public Vertice(String Numero, int Velocidad_adquirida, Direccion direccion, String Operadora) {
            this.Numero = Numero;
            this.Velocidad_adquirida = Velocidad_adquirida;
            this.direccion = direccion;
            this.Operadora = Operadora;
        }
    }

    public class Direccion implements Serializable {
        private byte Carrera, Calle, Casa;
        private String extra;

        public Direccion(byte Carrera, byte Calle, byte Casa, String extra) {
            this.Carrera = Carrera;
            this.Calle = Calle;
            this.Casa = Casa;
            this.extra = extra;
        }

        public String imprimirD() {
            return "Carrera " + Carrera + " " + Calle + " - " + Casa + " " + extra;
        }
    }

    // Con esta funcion se agregan todas las casas que poseen servicio
    public void agregarVertice(String Numero, int Velocidad_adquirida, Direccion direccion, String Operadora) {
        int peso = 5;
        Vertice a = new Vertice(Numero, Velocidad_adquirida, direccion, Operadora);

        Vertice cabezaCalles = new Vertice(" ", 0, new Direccion(direccion.Carrera, (byte) 0, (byte) 0, " "), " ");
        Vertice cabezaCasas = new Vertice(" ", 0, new Direccion(direccion.Carrera, direccion.Calle, (byte) 0, " "),
                " ");
        // Se verifica que su nodo Cabeza de Calle y Casa existan y si no existen se
        // crean
        if (!nod.contains(cabezaCalles)) {
            nod.add(cabezaCalles);
            adj.add(new LinkedList<Arista>());
            int o = nod.indexOf(cabezaCalles);
            agregarEnlace(o, peso);
            this.V++;
        }
        if (!nod.contains(cabezaCasas)) {
            nod.add(cabezaCasas);
            adj.add(new LinkedList<Arista>());
            int p = nod.indexOf(cabezaCasas);
            agregarEnlace(p, peso);
            this.V++;
        }

        // Se ordena el LikedList para evitar conflictos con la ubicacion y asignacion
        // de las aristas
        // nod.sort((v1, v2) -> v1.direccion.Casa - v2.direccion.Casa);
        // nod.sort((v1, v2) -> v1.direccion.Calle - v2.direccion.Calle);
        // nod.sort((v1, v2) -> v1.direccion.Carrera - v2.direccion.Carrera);

        nod.add(a);
        adj.add(new LinkedList<Arista>());
        int i = nod.indexOf(a);
        agregarEnlace(i, peso);

        this.V++;
    }

    // Se enlaza cada nodo de Servicio con su dirección cabeza
    public void agregarEnlace(int u, int peso) {
        LinkedList<Arista> aux, aux3 = new LinkedList<>();
        Vertice aux2;
        aux = adj.get(u);
        aux2 = nod.get(u);

        if (aux2.direccion.Calle == 0) { // Si el nodo que entra es cabesa de Calles se enlaza a su Cabeza de Carreras
            // Vertice cabezaCarreras = new Vertice(" ", 0, new Direccion((byte) 0, (byte)
            // 0, (byte) 0, " "), " ");
            aux.add(new Arista(u, 0, peso));
            adj.set(u, aux);

            aux3 = adj.get(0);
            aux3.add(new Arista(0, u, peso));
            adj.set(0, aux3);
        } else if (nod.get(u).direccion.Casa == 0) { // Si el nodo que entra es cabeza de Casas se enlaza a su Cabeza de
                                                     // Calles
            int i;
            // For para buscar el indice de la cabeza de calles
            for (i = 0; i < V; i++) {
                if ((nod.get(i).direccion.Carrera == aux2.direccion.Carrera) && (nod.get(i).direccion.Calle == 0)) {
                    break;
                }
            }
            aux.add(new Arista(u, i, peso));
            aux.add(new Arista(i, u, peso));
            adj.set(u, aux);

            aux3 = adj.get(i);
            aux3.add(new Arista(i, u, peso));
            adj.set(i, aux3);
        } else {
            int i;
            // For para buscar el indice de la cabeza de calles
            for (i = 0; i < V; i++) {
                if ((nod.get(i).direccion.Carrera == aux2.direccion.Carrera)
                        && (nod.get(i).direccion.Calle == aux2.direccion.Calle)) {
                    break;
                }
            }
            aux.add(new Arista(u, i, peso));
            adj.set(u, aux);

            aux3 = adj.get(i);
            aux3.add(new Arista(i, u, peso));
            adj.set(i, aux3);
        }
    }

    public void agregarArista(int u, int v, int peso) {
        LinkedList<Arista> aux = new LinkedList<>();
        aux = adj.get(u);
        aux.add(new Arista(u, v, peso));
        adj.set(u, aux);
        aux = adj.get(v);
        aux.add(new Arista(v, u, peso));
        adj.set(v, aux);
    }

    public void dfs(int s) {
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        stack.push(s);

        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                visited[u] = true;
                System.out.print(u + " ");
                for (Arista i : adj.get(u)) {
                    if (!visited[i.getD()]) {
                        stack.push(i.getD());
                    }
                }
            }
        }
    }

    /////// CAMINO MAS CORTO ///////
    @SuppressWarnings("unchecked")
    public void Dijkstra(int origen, int destino) {
        int[] distancia = new int[V];
        boolean[] visitado = new boolean[V];
        int[] previo = new int[V];
        Queue<Integer> pq = new LinkedList<>();

        for (int i = 0; i < V; i++) {
            distancia[i] = Integer.MAX_VALUE;
            previo[i] = -1;
        }

        pq.add(origen);
        distancia[origen] = 0;

        while (!pq.isEmpty()) {
            int u = pq.poll();
            if (!visitado[u]) {
                visitado[u] = true;
                for (Arista i : adj.get(u)) {
                    if (!visitado[i.getD()] && distancia[u] + i.peso < distancia[i.getD()]) {
                        distancia[i.getD()] = distancia[u] + i.peso;
                        pq.add(i.getD());
                        previo[i.getD()] = u;
                    }
                }
            }
        }

        if (distancia[destino] != Integer.MAX_VALUE) {
            System.out.println("El camino mas corto desde " + origen + " hasta " + destino + " tiene un peso de "
                    + distancia[destino]);
            System.out.print("El camino es: " + origen);
            int j = destino;
            @SuppressWarnings("rawtypes")
            Stack camino = new Stack();
            camino.push(j);
            do {
                j = previo[j];
                camino.push(j);
            } while (j != origen);
            camino.pop();
            while (!camino.isEmpty()) {
                System.out.print(" -> " + camino.pop());
            }
            System.out.println("");
        } else {
            System.out.println("No existe un camino desde " + origen + " hasta " + destino);
        }
    }
    /////// CAMINO MAS CORTO ///////
}