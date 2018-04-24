package guia.pkg4.cristobal.lagos;

import java.util.ArrayList;
import java.util.List;

public class Mozo {
    private int id;
    private String nombre;
    private List <PedidoMesa> pedidoMesa = new ArrayList<PedidoMesa>();

    public Mozo() {
    }

    public Mozo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List <PedidoMesa> getPedidoMesa() {
        return pedidoMesa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPedidoMesa(List <PedidoMesa> pedidoMesa) {
        this.pedidoMesa = pedidoMesa;
    }
    
    
}
