package guia.pkg4.cristobal.lagos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class VentasDia {
    private Calendar fecha = new GregorianCalendar().getInstance();
    private List<PedidoMesa> pedidoMesa = new ArrayList<PedidoMesa>();

    public VentasDia() {
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
        
        for (PedidoMesa p:pedidoMesa){
            total += p.calcularTotal();
        }
        
        return total;
    }

    @Override
    public String toString() {
        return "Fecha: " + fecha.get(Calendar.DAY_OF_MONTH) + "/" + fecha.get(Calendar.MONTH) + "/"
                + fecha.get(Calendar.YEAR) + "\tTotal Vendido: " + calcularTotal();
    }
    
    public String toStringMozo(Mozo mozo) {
        if (searchMozoInPedidos(mozo) == true)
            return "Fecha: " + fecha.get(Calendar.DAY_OF_MONTH) + "/" + fecha.get(Calendar.MONTH) + "/"
                    + fecha.get(Calendar.YEAR)
                    + "\tMozo: " + mozo.getNombre()
                    + "\tTotal Vendido: " + calcularTotalPorMozo(mozo);
        else
            return "Ese mozo no llevo a cabo pedidos hoy.";
    }
    
    public String toStringMesa(Mesa mesa) {
        if (searchMesaInPedidos(mesa) == true)
            return "Fecha: " + fecha.get(Calendar.DAY_OF_MONTH) + "/" + fecha.get(Calendar.MONTH) + "/"
                    + fecha.get(Calendar.YEAR)
                    + "\tMesa: " + mesa.getNroMesa()
                    + "\tTotal Vendido: " + calcularTotalPorMesa(mesa);
        else
            return "No hubo pedidos en esa mesa hoy.";
    }

    private boolean searchMozoInPedidos(Mozo mozo) {
        for (PedidoMesa p:pedidoMesa){
            if (p.getMozo().equals(mozo))
                return true;
        }
        return false;
    }
    
    private boolean searchMesaInPedidos(Mesa mesa) {
        for (PedidoMesa p:pedidoMesa){
            if (p.getMesa().equals(mesa))
                return true;
        }
        return false;
    }

    private double calcularTotalPorMozo(Mozo mozo) {
        double total = 0;
        
        for (PedidoMesa p:pedidoMesa)
            if (p.getMozo().equals(mozo))
                total += p.calcularTotal();
        
        return total;
    }
    
    private double calcularTotalPorMesa(Mesa mesa) {
        double total = 0;
        
        for (PedidoMesa p:pedidoMesa)
            if (p.getMesa().equals(mesa))
                total += p.calcularTotal();
        
        return total;
    }
    
}
