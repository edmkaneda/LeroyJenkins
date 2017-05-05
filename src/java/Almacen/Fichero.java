/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Almacen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

class AperturaFicheroExcepcion extends Exception{
    public AperturaFicheroExcepcion(String mensaje){
        super(mensaje);
    }
}

class CierreFicheroExcepcion extends Exception{
    public CierreFicheroExcepcion(String mensaje){
        super(mensaje);
    }
}

class MiObjectOutputStream extends ObjectOutputStream
{
        public MiObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }

    
    protected MiObjectOutputStream() throws IOException, SecurityException
    {
        super();
    }

    /** Redefinición del método de escribir 
     * la cabecera para que no haga nada. 
     */
        @Override
    protected void writeStreamHeader() throws IOException
    {
    }

}
/**
 *
 * @author Desarrollo Web
 */
public class Fichero {
    private ObjectInputStream ois=null;
    private ObjectOutputStream oos = null;
    private MiObjectOutputStream moos = null;
    private File f = null;
    private int modo=0;
    private boolean existiaFichero = false;
    
    /**
     * Constructor de la clase Fichero
     * @param nombre - El nombre del fichero a abrir
     * @param modoApertura - El modo de apertura
     *                       wb: Escritura binario
     *                       rb: Lectura binario
     *                       ab: Adición binario
     * @throws colegio.AperturaFicheroExcepcion
     * 
     */
    public Fichero(String nombre, String modoApertura) 
            throws AperturaFicheroExcepcion{
        this.f = new File(nombre);
        switch(modoApertura){ 
            case "wb": //Modo 1
                this.modo=1;
                try {
                    this.oos = new ObjectOutputStream(
                            new FileOutputStream(f)
                               );
                } catch (IOException ex) {
                    throw new AperturaFicheroExcepcion(
                            "Error al abrir el fichero");
                }
                System.err.println("Escribiendo");
                break;
            case "rb": 
                //Modo 2
                this.modo=2;
                try {
                    this.ois = new ObjectInputStream(
                            new FileInputStream(f)
                    );
                } catch (IOException ex) {
                    throw new AperturaFicheroExcepcion(
                            "Error al abrir el fichero");
                }
                System.err.println("Leyendo");
                break;
            case "ab": //Modo 3
                this.modo=3;
                if(f.isFile()){ //Compruebo si es fichero
                    this.existiaFichero=true;
                    try {
                        this.moos = new MiObjectOutputStream(
                            new FileOutputStream(f, true)
                                   );
                    } catch (IOException ex) {
                        throw new AperturaFicheroExcepcion(
                                "Error al abrir el fichero");
                    }
                }else{
                    try {
                        this.oos = new ObjectOutputStream(
                            new FileOutputStream(f, true)
                                   );
                    } catch (IOException ex) {
                        throw new AperturaFicheroExcepcion(
                                "Error al abrir el fichero");
                    }
                }
                System.err.println("Añadiendo");
                break;
            default:
                throw new AperturaFicheroExcepcion(
                        "modo de apertura incorrecto");        
        }
    }
    /**
     * Escribe un objeto en el fichero abierto
     * @param obj 
     */
    public void escribirObjeto(Object obj){
        try{
            if(this.existiaFichero){
                System.err.println("Existia");
                this.moos.writeObject(obj);
            }else{
                System.err.println("No existia");
                this.oos.writeObject(obj);
            }
        }catch(IOException io){
            System.err.println("Error al escribir"+io.getMessage());
        }
    }
    /**
     * Lee un objeto en el fichero abierto
     * @return Object - El objeto leido.
     */
    public Object leerObjeto(){
        Object objeto=null;
        try {
            objeto = this.ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            
        }
        return objeto;
    }
    
    public void close() throws CierreFicheroExcepcion{
        try{
        switch(this.modo){
            case 1:
                this.oos.close();
                break;
            case 2:
                this.ois.close();
                break;
            case 3:
                if(this.existiaFichero)
                    this.moos.close();
                else
                    this.oos.close();
                break;
        }
        }catch(IOException io){
            throw new CierreFicheroExcepcion("Error al cerrar el fichero");
        }
    }
}

