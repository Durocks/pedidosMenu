package guia.pkg4.cristobal.lagos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class VentasDia {
    private Calendar fecha;
    private List<PedidoMesa> pedidoMesa;

    public VentasDia() {
        this.fecha = GregorianCalendar.getInstance();
        this.pedidoMesa = new ArrayList<>();
    }

    public void setPedidoMesa(List<PedidoMesa> pedidoMesa) {
        this.pedidoMesa = pedidoMesa;
    }

    public List<PedidoMesa> getPedidoMesa() {
        return pedidoMesa;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
    
    public double calcularTotal(){
        double total = 0;
        
        total = pedidoMesa.stream().map((p) -> p.calcularTotal()).reduce(total, (accumulator, _item) -> accumulator + _item);
        
        return total;
    }

    @Override
    public String toString() {
        return "Fecha: " + fecha.get(Calendar.DAY_OF_MONTH) + "/" + fecha.get(Calendar.MONTH) + "/"
                + fecha.get(Calendar.YEAR) + "\t\tTotal Vendido: " + calcularTotal();
    }
    
    public String toStringMozo(Mozo mozo) {
        if (searchMozoInPedidos(mozo) == true)
            return "Fecha: " + fecha.get(Calendar.DAY_OF_MONTH) + "/" + fecha.get(Calendar.MONTH) + "/"
                    + fecha.get(Calendar.YEAR)
                    + "\t\tMozo: " + mozo.getNombre()
                    + "\t\tTotal Vendido: " + calcularTotalPorMozo(mozo);
        else
            return "Ese mozo no llevo a cabo pedidos hoy.";
    }
    
    public String toStringMesa(Mesa mesa) {
        if (searchMesaInPedidos(mesa) == true)
            return "Fecha: " + fecha.get(Calendar.DAY_OF_MONTH) + "/" + fecha.get(Calendar.MONTH) + "/"
                    + fecha.get(Calendar.YEAR)
                    + "\t\tMesa: " + mesa.getNroMesa()
                    + "\t\tTotal Vendido: " + calcularTotalPorMesa(mesa);
        else
            return "No hubo pedidos en esa mesa hoy.";
    }

    private boolean searchMozoInPedidos(Mozo mozo) {
        return pedidoMesa.stream().anyMatch((p) -> (p.getMozo().equals(mozo)));
    }
    
    private boolean searchMesaInPedidos(Mesa mesa) {
        return pedidoMesa.stream().anyMatch((p) -> (p.getMesa().equals(mesa)));
    }

    private double calcularTotalPorMozo(Mozo mozo) {
        double total = 0;
        
        total = pedidoMesa.stream().filter((p) -> (p.getMozo().equals(mozo))).map((p) -> p.calcularTotal()).reduce(total, (accumulator, _item) -> accumulator + _item);
        
        return total;
    }
    
    private double calcularTotalPorMesa(Mesa mesa) {
        double total = 0;
        
        total = pedidoMesa.stream().filter((p) -> (p.getMesa().equals(mesa))).map((p) -> p.calcularTotal()).reduce(total, (accumulator, _item) -> accumulator + _item);
        
        return total;
    }
    
}
