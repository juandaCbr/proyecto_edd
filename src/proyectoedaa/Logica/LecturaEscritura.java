/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoedaa;

import Logica.Movimiento;
import Logica.Persona;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JOptionPane;

public class LecturaEscritura implements Serializable{
    //String rutaUsuarios = "Privado\\"+p.getUsuario()+"_"+p.getContraseña(); //ruta para usuarios
    //String rutaLu = "Privado\\LU.ser"; //ruta para el hashmap de usuarios
    
    //private Map<String, String> Usuarios = leerOCrearHashMapDesdeArchivo(rutaLu);
    
    public void registrarUsuario(Persona p) {
        String rutaUsuarios = ("Privado\\"+p.getUsuario()+"_"+p.getContraseña()+".txt"); //ruta para usuarios
        String rutaLu = "Privado\\LU.txt"; //ruta para el hashmap de usuarios
        Map<String, String> Usuarios = leerOCrearHashMapDesdeArchivo(rutaLu);
        LinkedList lista = new LinkedList();
        if(p.getContraseña().equals(Usuarios.get(p.getUsuario()))){
            JOptionPane.showMessageDialog(null, "El usuario ya existe, intente nuevamente.");
        }
        else{
            lista.add(p);
            guardarObjeto(lista,rutaUsuarios);
            Usuarios.put(p.getUsuario(), p.getContraseña());
            guardarHashMapEnArchivo(Usuarios,rutaLu);
        }
    }
    
    public boolean IngresarUsuario(String usuario,  String contraseña){
        String rutaLu = "Privado\\LU.txt";
        Map<String, String> Usuarios = leerHashMapDesdeArchivo(rutaLu);
        if(contraseña.equals(Usuarios.get(usuario))){
            return true;
        }
        else{
            return false;
        }
    }
        // Método para guardar un objeto en un archivo en una ruta específica
    private static void guardarObjeto(Object objeto, String rutaArchivo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(new File(rutaArchivo)))) {
            salida.writeObject(objeto);
            System.out.println("Objeto guardado con éxito en: " + rutaArchivo);
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void gObjeto(Movimiento objeto, String rutaArchivo){
        LinkedList li = (LinkedList)lObjeto(rutaArchivo);
        for(int i=1; i<li.size(); i++){
            Movimiento mov = (Movimiento)li.get(i);
            if(mov.getFecha().equals(objeto.getFecha()) && mov.getCategoria().equals(objeto.getCategoria()) && (mov.getMonto() == objeto.getMonto())){
                JOptionPane.showMessageDialog(null, "El movimiento ya existe");
                break;
            }else{
                li.add(i, objeto);
                Persona p = (Persona)li.getFirst();
                p.setMovimiento(objeto.getCategoria(), objeto.getMonto());
                li.set(0, p);
                guardarObjeto(li, rutaArchivo);
                break;
            }
        }
        if(li.size() == 1){
                li.add(1, objeto);
                Persona p = (Persona)li.getFirst();
                p.setMovimiento(objeto.getCategoria(), objeto.getMonto());
                li.set(0, p);
                guardarObjeto(li, rutaArchivo);
        }
    }
    public void gLista(Object objeto, String rutaArchivo){
        guardarObjeto(objeto, rutaArchivo);
    }

    // Método para leer un objeto desde un archivo en una ruta específica
    private static Object leerObjeto(String rutaArchivo) {
        Object objetoLeido = null;
        try{
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(new File(rutaArchivo)));
            objetoLeido = entrada.readObject();
            entrada.close();
            return objetoLeido;
        } catch (Exception e) {
            e.getMessage();
        }
        return objetoLeido;
    }
    public Object lObjeto(String rutaArchivo){
        return leerObjeto(rutaArchivo);
    }
    
    
    private static Map<String, String> leerOCrearHashMapDesdeArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        // Verifica si el archivo existe
        if (archivo.exists()) {
            return leerHashMapDesdeArchivo(rutaArchivo);
        } else {
            // Crea un nuevo HashMap si el archivo no existe
            Map<String, String> nuevoHashMap = new HashMap<>();
            guardarHashMapEnArchivo(nuevoHashMap, rutaArchivo);
            return nuevoHashMap;
        }
    }
    
    private static void guardarHashMapEnArchivo(Map<String, String> hashMap, String rutaArchivo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(new File(rutaArchivo)))) {//buscar en un ruta un archivo y lo sobreesscribe
            salida.writeObject(hashMap);
            salida.close();
            System.out.println("HashMap guardado con éxito en: " + rutaArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para leer un HashMap desde un archivo
    private static Map<String, String> leerHashMapDesdeArchivo(String rutaArchivo) {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(new File(rutaArchivo)))) {
            Object objetoLeido = entrada.readObject();
            if (objetoLeido instanceof HashMap) {
                return (HashMap<String, String>) objetoLeido;
            }
            entrada.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}


