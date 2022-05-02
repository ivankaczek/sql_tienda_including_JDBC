
package tienda_app.entities;

public class Fabricante {
   
    private int codigo;
    private String nombre;

    public Fabricante() {
    }

    public Fabricante(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Fabricante{" + "codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
    
    
    
}


/*

fabricante                                  producto
----------                                  ----------                
codigo INT(10) PK                           codigo INT(10) pk
nombre VARCHAR(100)  ++-------------+<----- nombre VARCHAR(100)
                                            precio DOUBLE
                                            codigo_fabricante INT(10) /foreignKey/
*/