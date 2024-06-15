//11/04/24
package Logica;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Grafo implements Serializable{
    private int V;
    private LinkedList<LinkedList<Arista>> adj;
    private LinkedList<Vertice> nod;

    public int getV() {
        return V;
    }

    public LinkedList<LinkedList<Arista>> getAdj() {
        return adj;
    }

    public Grafo() {
        this.V = 0;
        this.adj = new LinkedList<LinkedList<Arista>>();
        this.nod = new LinkedList<Vertice>();
    }

    public void imprimirGrafo() {
        for (int i = 0; i < V; i++) {
            System.out.print("\nVertice: "+ i +", Numero: "+nod.get(i).Numero+", Velocidad: "+nod.get(i).Velocidad_adquirida+", Direccion: "+nod.get(i).Direccion+", Operadora: "+nod.get(i).Operadora+": ");
            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(adj.get(i).get(j).d + " ");
            }
        }
        System.out.println("");
    }

    public class Arista implements Serializable{
        public int o, d, peso;

        public Arista(int o, int d, int peso) {
            this.o = o;
            this.d = d;
            this.peso = peso;
        }
    }

    public class Vertice implements Serializable{
        String Numero;
        int Velocidad_adquirida;
        String Direccion;
        String Operadora;

        public Vertice(String Numero, int Velocidad_adquirida, String Direccion, String Operadora){
            this.Numero = Numero;
            this.Velocidad_adquirida = Velocidad_adquirida;
            this.Direccion = Direccion;
            this.Operadora = Operadora;
        }
    }

    public void agregarVertice(String Numero, int Velocidad_adquirida, String Direccion, String Operadora, int u, int v, int peso){
        nod.add(new Vertice(Numero, Velocidad_adquirida, Direccion, Operadora));
        adj.add(new LinkedList<Arista>());
        agregarArista(u, v, peso);
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
                    if (!visited[i.d]) {
                        stack.push(i.d);
                    }
                }
            }
        }
    }

    /////// ARBOL RECUBRIDOR MINIMO ///////
    public boolean hayCiclo(int origen, int destino) {
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        stack.push(origen);

        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                visited[u] = true;
                for (Arista i : adj.get(u)) {
                    if (i.d == destino) {
                        return true;
                    }
                    if (!visited[i.d]) {
                        stack.push(i.d);
                    }
                }
            }
        }
        return false;
    }

    public Grafo Kruskal() {
        Grafo Arbol_Recubridor_Minimo = new Grafo();
        LinkedList<Arista> AristasOrdenadas = new LinkedList<>();
        for (LinkedList<Arista> i : adj) {
            for (Arista j : i) {
                AristasOrdenadas.add(j);
            }
        }

        Collections.sort(AristasOrdenadas, (a1, a2) -> Integer.compare(a1.peso, a2.peso));
        for (Arista i : AristasOrdenadas) {
            if (!Arbol_Recubridor_Minimo.hayCiclo(i.o, i.d)) {
                Arbol_Recubridor_Minimo.agregarArista(i.o, i.d, i.peso);
            }
        }

        return Arbol_Recubridor_Minimo;
    }

    public Grafo Prims() {
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        Grafo Arbol_Recubridor_Minimo = new Grafo();
        LinkedList<Arista> AristasOrdenadas = new LinkedList<>();
        for (LinkedList<Arista> i : adj) {
            for (Arista j : i) {
                AristasOrdenadas.add(j);
            }
        }
        Collections.sort(AristasOrdenadas, (a1, a2) -> Integer.compare(a1.peso, a2.peso));

        Arista pos = AristasOrdenadas.getFirst();
        Arbol_Recubridor_Minimo.agregarArista(pos.o, pos.d, pos.peso);

        stack.push(pos.o);
        visited[pos.o] = true;
        visited[pos.d] = true;
        while (!stack.isEmpty()) {
            stack.pop();
            for (Arista i : AristasOrdenadas) {
                if (visited[i.o] && !visited[i.d]) {
                    if (!Arbol_Recubridor_Minimo.hayCiclo(i.o, i.d)) {
                        stack.push(i.d);
                        visited[i.d] = true;
                        Arbol_Recubridor_Minimo.agregarArista(i.o, i.d, i.peso);
                        break;
                    }
                }
            }
        }

        return Arbol_Recubridor_Minimo;
    }
    /////// ARBOL RECUBRIDOR MINIMO ///////

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
                    if (!visitado[i.d] && distancia[u] + i.peso < distancia[i.d]) {
                        distancia[i.d] = distancia[u] + i.peso;
                        pq.add(i.d);
                        previo[i.d] = u;
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

    public void Floyd_Warshall() {
        int[][] W = new int[V][V];
        int[][] R = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (j == i) {
                    W[i][j] = 0;
                } else {
                    W[i][j] = 999999999;
                }
                R[i][j] = -1;
            }
        }
        for (LinkedList<Arista> i : adj) {
            for (Arista j : i) {
                W[j.o][j.d] = j.peso;
                R[j.o][j.d] = j.d;
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (W[i][k] + W[k][j] < W[i][j]) {
                        W[i][j] = W[i][k] + W[k][j];
                        R[i][j] = k;
                    }
                }
            }
        }

        System.out.println("\n   Matriz de Pesos");
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (W[i][j] == 999999999)
                    System.out.print("Inf   ");
                else
                    System.out.print(W[i][j] + "   ");
            }
            System.out.println();
        }
        System.out.println("\n   Matriz de Recorridos");
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (R[i][j] == -1)
                    System.out.print("-   ");
                else
                    System.out.print(R[i][j] + "   ");
            }
            System.out.println();
        }
    }
    /////// CAMINO MAS CORTO ///////
}