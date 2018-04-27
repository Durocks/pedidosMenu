package guia.pkg4.cristobal.lagos;

public class Menu {
    private int codigo;
    private String descripcion;
    private double precio;

    public Menu() {
    }

    public Menu(int codigo, String descripcion, double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + "\t\tDescripcion: " + descripcion + "\t\tPrecio=" + precio;
    }
    
    
    
}
