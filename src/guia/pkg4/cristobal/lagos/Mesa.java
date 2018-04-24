package guia.pkg4.cristobal.lagos;

class Mesa {
    private int nroMesa;
    private double total;

    public Mesa() {
    }

    public Mesa(int nroMesa, double total) {
        this.nroMesa = nroMesa;
        this.total = total;
    }

    public int getNroMesa() {
        return nroMesa;
    }

    public double getTotal() {
        return total;
    }

    public void setNroMesa(int nroMesa) {
        this.nroMesa = nroMesa;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
