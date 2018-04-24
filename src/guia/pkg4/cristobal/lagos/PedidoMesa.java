package guia.pkg4.cristobal.lagos;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PedidoMesa {
    private Mozo mozo;
    private Mesa mesa;
    private Menu menu;
    private int cantidad;
    private Calendar fecha = new GregorianCalendar().getInstance();

    public PedidoMesa() {
    }

    public PedidoMesa(Mozo mozo, Mesa mesa, Menu menu, int cantidadPlatos) {
        this.mozo = mozo;
        this.mesa = mesa;
        this.menu = menu;
        this.cantidad = cantidadPlatos;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public Mozo getMozo() {
        return mozo;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setMozo(Mozo mozo) {
        this.mozo = mozo;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double calcularTotal(){
        return this.menu.getPrecio()*this.cantidad;
    }

    @Override
    public String toString() {
        return "Mozo: " + mozo.getNombre() + "\tMesa: " + mesa.getNroMesa() + "\tPlato: "
                + menu.getDescripcion() + "\tCantidad: " + cantidad + "\n";
    }
}
