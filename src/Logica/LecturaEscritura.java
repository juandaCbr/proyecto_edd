/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LecturaEscritura implements Serializable{
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
    
    public void gObjeto(Object objeto, String rutaArchivo){
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
    
}


