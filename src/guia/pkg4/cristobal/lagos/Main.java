package guia.pkg4.cristobal.lagos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final PrintStream pantalla = System.out;
    private static final File pedidosMesa = new File ("res/PedidosMesa.txt");
    private static final File ventasDiaFile = new File ("res/Ventas dia.txt");
    private static final File ventasDiaMozoFile = new File ("res/Ventas dia por mozo.txt");
    private static final File ventasDiaMesaFile = new File ("res/Ventas dia por mesa.txt");
    
    private static final List<Mozo> mozos = new ArrayList<Mozo>();
    private static final List<Mesa> mesas = new ArrayList<Mesa>();
    private static final List<Menu> menus = new ArrayList<Menu>();
    private static VentasDia ventasDia = new VentasDia();
    
    private static final Scanner std = new Scanner (System.in);
    
    private static final List<PedidoMesa> pedidoMesa = new ArrayList<PedidoMesa>();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        initObjects();
        borrarRegistros();
        int opcion = 1;
        while (opcion != 0){
            System.out.println("Elija una opcion\n"
                    + "1 - Agregar Pedido (Mozo)\n"
                    + "2 - Ver todos los pedidos\n"
                    + "3 - Borrar todos los registros\n"
                    + "4 - Generar el archivo de las ventas del dia\n"
                    + "5 - Generar el archivo de las ventas del dia por mozo\n"
                    + "6 - Generar el archivo de las ventas del dia por mesa\n"
                    + "0 - Terminar");
            opcion = Validator.validacionInt();
            switch (opcion) {
                case 1:
                    agregarPedidoMesa();
                    break;
                case 2:
                    verPedidos();
                    break;
                case 3:
                    borrarRegistros();
                    break;
                case 4:
                    generarVentasDia();
                    break;
                case 5:
                    generarVentasDiaMozo();
                    break;
                case 6:
                    generarVentasDiaMesa();
                    break;
                case 0:
                    return;
                default:
            }
        }
    }

    private static void agregarPedidoMesa() throws FileNotFoundException, IOException {
        System.out.println();        
        boolean agregar = true;
        while (agregar == true){
            System.out.println("Desea agregar un pedido?(Si/No): ");
            agregar = Validator.validacionSiNo();
            if (agregar == true){
                boolean mozoFound = false;
                Mozo mozo = null;
                do{
                System.out.println("Ingrese el id del mozo: ");
                int idMozo = Validator.validacionInt();
                if (getMozo(idMozo) != null){
                    mozo = getMozo(idMozo);
                    mozoFound = true;
                }
                } while (mozoFound == false);
                boolean mesaFound = false;
                Mesa mesa = null;
                do{
                System.out.println("Ingrese el id del mesa: ");
                int idMesa = Validator.validacionInt();
                if (getMozo(idMesa) != null){
                    mesa = getMesa(idMesa);
                    mesaFound = true;
                }
                } while (mesaFound == false);
                boolean menuFound = false;
                Menu menu = null;
                do{
                System.out.println("Ingrese el codigo del menu: ");
                int codigoMenu = Validator.validacionInt();
                if (getMenu(codigoMenu) != null){
                    menu = getMenu(codigoMenu);
                    menuFound = true;
                }
                } while (menuFound == false);
                System.out.println("Ingrese la cantidad de platos: ");
                int cantidadPlatos = Validator.validacionInt();
                pedidoMesa.add(new PedidoMesa(mozo, mesa, menu, cantidadPlatos));
                ventasDia.getPedidoMesa().add(pedidoMesa.get(pedidoMesa.size()-1));
                System.out.print("\n" + pedidoMesa.get(pedidoMesa.size()-1).toString());
                
                if (!pedidosMesa.exists()){
                    try {
                        pedidosMesa.createNewFile();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(pedidosMesa, true));
                    if ((new FileReader(pedidosMesa).read()) == -1)
                        writer.write("Detalles de los pedidos\n\n");
                    writer.write(inicioHora() + pedidoMesa.get(pedidoMesa.size()-1).toString());
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            else
                agregar = false;
        }
        System.out.println();
    }
        

    private static void verPedidos() {
        if (pedidosMesa.exists()){
            try {
                FileReader reader = new FileReader(pedidosMesa);
                int i;
                if (reader.ready()){
                    System.out.println();
                    while ((i=reader.read()) != -1){
                        System.out.print((char) i);
                    }
                }
                else
                    System.out.println();

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        else
            System.out.println("El archivo no existe o ya fue borrado.");
        System.out.println();
    }

    private static void borrarRegistros() {
        if (pedidosMesa.exists()){
            pedidosMesa.delete();
            pedidoMesa.clear();
        }
        
        if (ventasDiaFile.exists()){
            ventasDiaFile.delete();
            pedidoMesa.clear();
            ventasDia = new VentasDia();
        }
        
        if (ventasDiaMozoFile.exists()){
            ventasDiaMozoFile.delete();
            pedidoMesa.clear();
            ventasDia = new VentasDia();
        }
        
        if (ventasDiaMesaFile.exists()){
            ventasDiaMesaFile.delete();
            pedidoMesa.clear();
            ventasDia = new VentasDia();
        }
        System.out.println("Los registros han sido borrados.\n");
    }
    private static Mozo getMozo(int idMozo) {
        for (Mozo m:mozos){
            if (m.getId() == idMozo)
                return m;
        }
        return null;
    }

    private static Mesa getMesa(int nroMesa) {
        for (Mesa m:mesas){
            if (m.getNroMesa() == nroMesa){
                return m;
            }
        }
        return null;
    }

    private static Menu getMenu(int codigoMenu) {
        for (Menu m:menus){
            if (m.getCodigo() == codigoMenu){
                return m;
            }
        }
        return null;
    }

    private static void initObjects() throws FileNotFoundException {
//        menus.add(new Menu(0, "Fideos bolognesa", 120));
//        menus.add(new Menu(1, "Hamburguesa con queso", 100));
//        menus.add(new Menu(2, "Ribs con BBQ", 150));
//        menus.add(new Menu(3, "Ensalada Caesar", 115));
//        menus.add(new Menu(4, "Sushi variado", 140));
            
        try {
            Scanner reader = new Scanner (new File("res/Menu.txt"));
            while (reader.hasNextLine()){
                String[] data = reader.nextLine().split("\t");
                int codigo = Integer.parseInt(data[0].substring(8));
                String desc = data[2].substring(13);
                double precio = Double.parseDouble(data[4].substring(7));
                menus.add(new Menu(codigo, desc, precio));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
//        menus.forEach(System.out::println);
        
        mozos.add(new Mozo(0, "Jorge"));
        mozos.add(new Mozo(1, "Juan"));
        mozos.add(new Mozo(2, "Mariano"));
        mozos.add(new Mozo(3, "Jose"));
        mozos.add(new Mozo(4, "Nicolas"));
        
        mesas.add(new Mesa(0, 0));
        mesas.add(new Mesa(1, 0));
        mesas.add(new Mesa(2, 0));
        mesas.add(new Mesa(3, 0));
        mesas.add(new Mesa(4, 0));
    }

    private static void generarVentasDia() throws IOException {
        if (ventasDia.calcularTotal() == 0){
            System.out.println("No hubo ventas hoy.\n");
            return;
        }
        if (ventasDiaFile.exists())
            System.out.println("El archivo 'Ventas dia' ya existe");
        else
            ventasDiaFile.createNewFile();
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ventasDiaFile, true));
            writer.write(inicioHora() +  ventasDia.toString() + "\n");
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
        
        try {
            FileReader reader = new FileReader(ventasDiaFile);
            int i;
            while ((i = reader.read()) != -1)
                    System.out.print((char) i);
                System.setIn(System.in);
        System.out.println();
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
    
    private static void generarVentasDiaMozo() throws IOException {
        if (ventasDia.calcularTotal() == 0){
            System.out.println("No hubo ventas hoy.\n");
            return;
        }
        
        boolean mozoFound = false;
                Mozo mozo = null;
                do{
                System.out.println("Ingrese el id del mozo: ");
                int idMozo = Validator.validacionInt();
                if (getMozo(idMozo) != null){
                    mozo = getMozo(idMozo);
                    mozoFound = true;
                }
                } while (mozoFound == false);
        
        if (ventasDiaMozoFile.exists())
            System.out.println("El archivo 'Ventas dia' ya existe");
        else
            ventasDiaMozoFile.createNewFile();
        
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ventasDiaMozoFile, true));
            writer.write(inicioHora() + ventasDia.toStringMozo(mozo)+ "\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println(e);
        }        
        try {
            FileReader reader = new FileReader(ventasDiaMozoFile);
            int i;
            while ((i = reader.read()) != -1)
                    System.out.print((char) i);
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println();
    }
    
    private static void generarVentasDiaMesa() throws IOException {
        if (ventasDia.calcularTotal() == 0){
            System.out.println("No hubo ventas hoy.\n");
            return;
        }
        boolean mesaFound = false;
                Mesa mesa = null;
                do{
                System.out.println("Ingrese el id del mesa: ");
                int idMesa = Validator.validacionInt();
                if (getMozo(idMesa) != null){
                    mesa = getMesa(idMesa);
                    mesaFound = true;
                }
                } while (mesaFound == false);
        
        if (ventasDiaMesaFile.exists())
            System.out.println("El archivo 'Ventas dia' ya existe");
        else
            ventasDiaMesaFile.createNewFile();
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ventasDiaMesaFile, true));
            writer.write(inicioHora() + ventasDia.toStringMesa(mesa)+ "\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println(e);
        }
                
        try {
            FileReader reader = new FileReader(ventasDiaMesaFile);
            int i;
            while ((i = reader.read()) != -1)
                    System.out.print((char) i);
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println();
    }
    
    private static String inicioHora(){
        Calendar time = GregorianCalendar.getInstance();
        String ampm;
        if (time.get(Calendar.AM_PM) == Calendar.AM)
            ampm = "am";
        else
            ampm = "pm";
        return time.get(Calendar.HOUR) + ":" + time.get(Calendar.MINUTE) + ":" + time.get(Calendar.SECOND)
                + " " + ampm + ":\t";
    }
    
}
