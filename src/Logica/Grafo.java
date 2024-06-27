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

    public LinkedList<Vertice> getNod(){
        return nod;
    }
    public LinkedList<LinkedList<Arista>> getAdj() {
        return adj;
    }

    public Grafo() {
        this.V = 0;
        this.adj = new LinkedList<LinkedList<Arista>>();
        this.nod = new LinkedList<Vertice>();
        nod.add(new Vertice(" ", 0, new Direccion((byte)0, (byte)0, (byte)0, " "), " "));
    }

    public void imprimirGrafo() {
        for (int i = 0; i < V; i++) {
            System.out.println("\nVertice: "+ i +", Numero: "+nod.get(i).Numero+", Velocidad: "+nod.get(i).Velocidad_adquirida+", Direccion: "+nod.get(i).direccion.imprimirD()+", Operadora: "+nod.get(i).Operadora);
            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(adj.get(i).get(j).getD() + " ");
            }
        }
        System.out.println("");
    }

    private class Arista implements Serializable{
        private int peso;
        private Vertice Do, Dd;

        public Arista(int o, int d, int peso) {
            this.Do = nod.get(o);
            this.Dd = nod.get(d);
            this.peso = peso;
        }
        public int getO(){
            return nod.indexOf(Do);
        }
        public int getD(){
            return nod.indexOf(Dd);
        }
    }

    private class Vertice implements Serializable{
        private String Numero, Operadora;
        private Direccion direccion = new Direccion();
        private int Velocidad_adquirida;

        public Vertice(String Numero, int Velocidad_adquirida, Direccion direccion, String Operadora){
            this.Numero = Numero;
            this.Velocidad_adquirida = Velocidad_adquirida;
            this.direccion = direccion;
            this.Operadora = Operadora;
        }
    }
    public class Direccion implements Serializable{
        private byte Carrera, Calle, Casa;
        private String extra;
        Direccion(){}
        Direccion(byte Carrera, byte Calle, byte Casa, String extra){
            this.Carrera = Carrera;
            this.Calle = Calle;
            this.Casa = Casa;
            this.extra = extra;
        }
        public String imprimirD(){
            return "Carrera "+Carrera+" "+Calle+" - "+Casa+" "+extra;
        }
    }


    public void agregarVertice(String Numero, int Velocidad_adquirida, Direccion direccion, String Operadora, int u, int v){
        int peso = 5;
        Vertice a = new Vertice(Numero, Velocidad_adquirida, direccion, Operadora);
        nod.add(a);
        if(!nod.contains(new Vertice(" ", 0, new Direccion(direccion.Carrera, (byte)0, (byte)0, " "), " "))){
            nod.add(new Vertice(" ", 0, new Direccion(direccion.Carrera, (byte)0, (byte)0, " "), " "));
            nod.sort((v1, v2) -> v1.direccion.Casa - v2.direccion.Casa);
            nod.sort((v1, v2) -> v1.direccion.Calle - v2.direccion.Calle);
            nod.sort((v1, v2) -> v1.direccion.Carrera - v2.direccion.Carrera);
            adj.add(new LinkedList<Arista>());
            int o = nod.indexOf(new Vertice(" ", 0, new Direccion(direccion.Carrera, (byte)0, (byte)0, " "), " "));
            agregarEnlace(o, peso);
        }else if(!nod.contains(new Vertice(" ", 0, new Direccion(direccion.Carrera, direccion.Carrera, (byte)0, " "), " "))){
            nod.add(new Vertice(" ", 0, new Direccion(direccion.Carrera, direccion.Carrera, (byte)0, " "), " "));
            nod.sort((v1, v2) -> v1.direccion.Casa - v2.direccion.Casa);
            nod.sort((v1, v2) -> v1.direccion.Calle - v2.direccion.Calle);
            nod.sort((v1, v2) -> v1.direccion.Carrera - v2.direccion.Carrera);
            adj.add(new LinkedList<Arista>());
            int p = nod.indexOf(new Vertice(" ", 0, new Direccion(direccion.Carrera, direccion.Carrera, (byte)0, " "), " "));
            agregarEnlace(p, peso);
        }
        nod.sort((v1, v2) -> v1.direccion.Casa - v2.direccion.Casa);
        nod.sort((v1, v2) -> v1.direccion.Calle - v2.direccion.Calle);
        nod.sort((v1, v2) -> v1.direccion.Carrera - v2.direccion.Carrera);
        adj.add(new LinkedList<Arista>());

        int i = nod.indexOf(a);
        agregarEnlace(i, peso);
        
        this.V++;
    }
    public void agregarEnlace(int u, int peso) {
        LinkedList<Arista> aux = new LinkedList<>();
        aux = adj.get(u);
        aux.add(new Arista(u, v, peso));
        adj.set(u, aux);
        aux = adj.get(v);
        aux.add(new Arista(v, u, peso));
        adj.set(v, aux);
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
                W[j.getO()][j.getD()] = j.peso;
                R[j.getO()][j.getD()] = j.getD();
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