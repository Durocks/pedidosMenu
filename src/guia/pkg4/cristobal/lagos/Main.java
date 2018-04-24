package guia.pkg4.cristobal.lagos;

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
    private static File menuFile = new File ("res/Menu.txt");
    private static File ventasDiaFile = new File ("res/Ventas dia.txt");
    private static File ventasDiaMozoFile = new File ("res/Ventas dia por mozo.txt");
    private static File ventasDiaMesaFile = new File ("res/Ventas dia por mesa.txt");
    
    private static List<Mozo> mozos = new ArrayList<Mozo>();
    private static List<Mesa> mesas = new ArrayList<Mesa>();
    private static List<Menu> menus = new ArrayList<Menu>();
    private static VentasDia ventasDia = new VentasDia();
    
    private static final Scanner std = new Scanner (System.in);
    
    private static final List<PedidoMesa> pedidoMesa = new ArrayList<PedidoMesa>();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        initObjects();
        int opcion = 1;
        while (opcion != 0){
            System.out.println("Elija una opcion\n"
                    + "1 - Agregar Pedido (Mozo)\n"
                    + "2 - Cerrar dia (Administrador)\n"
                    + "3 - Ver todos los pedidos\n"
                    + "4 - Borrar todos los registros\n"
                    + "5 - Generar el archivo de las ventas del dia\n"
                    + "6 - Generar el archivo de las ventas del dia por mozo\n"
                    + "7 - Generar el archivo de las ventas del dia por mesa\n"
                    + "0 - Terminar");
            opcion = Validator.validacionInt();
            switch (opcion) {
                case 1:
                    agregarPedidoMesa();
                    break;
                case 2:
                    cerrarDia();
                    break;
                case 3:
                    verPedidos();
                    break;
                case 4:
                    borrarRegistros();
                    break;
                case 5:
                    generarVentasDia();
                    break;
                case 6:
                    generarVentasDiaMozo();
                    break;
                case 7:
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
        FileWriter fileWrite = new FileWriter(menuFile, true);
        if (!menuFile.exists()){
            try {
                menuFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        else
            System.out.println("El archivo ya existe.");
        
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
                
                try (PrintStream pedidoWrite = new PrintStream (menuFile)) {
                    System.setOut(pedidoWrite);
                    if ((new FileReader(menuFile).read()) == -1){
                        System.out.println("Detalles de los pedidos: \n");
                    }
                    System.out.println(pedidoMesa.get(pedidoMesa.size()-1));
                    System.setOut(pantalla);
                }
            }
            else
                agregar = false;
        }
        System.out.println();
    }

    private static void cerrarDia() {
    }

    private static void verPedidos() {
        if (menuFile.exists()){
            try {
                FileReader fileInput = new FileReader(menuFile);
    //            System.setIn(fileInput);
    //            Scanner fileIn = new Scanner(registro);
                int i;
                if (fileInput.ready()){
                    System.out.println();
                    while ((i=fileInput.read()) != -1){
                        System.out.print((char) i);
                    }
                }
                else
                    System.out.println();
    //            System.setIn(System.in);

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        else
            System.out.println("El archivo no existe o ya fue borrado.");
    }

    private static void borrarRegistros() {
        if (menuFile.exists()){
            menuFile.delete();
            pedidoMesa.clear();
        }
        else
            System.out.println("El archivo no existe o ya fue borrado.");
        
        if (ventasDiaFile.exists()){
            ventasDiaFile.delete();
            pedidoMesa.clear();
            ventasDia = new VentasDia();
        }
        else
            System.out.println("El archivo no existe o ya fue borrado.");
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

    private static void initObjects() {
        menus.add(new Menu(0, "Fideos bolognesa", 120));
        menus.add(new Menu(1, "Hamburguesa con queso", 100));
        menus.add(new Menu(2, "Ribs con BBQ", 150));
        menus.add(new Menu(3, "Ensalada Caesar", 115));
        menus.add(new Menu(4, "Sushi variado", 140));
        
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
        
        try (FileWriter writer = new FileWriter(ventasDiaFile, true)) {
            writer.write(ventasDia.toString() + "\n");
            writer.flush();
        }
                
        try (FileInputStream toRead = new FileInputStream(ventasDiaFile)) {
            System.setIn(toRead);
            try (Scanner reader = new Scanner(toRead)) {
                while (reader.hasNext())
                    System.out.println(reader.nextLine());
                System.setIn(System.in);
            }
        }
        System.out.println();
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
        
        
        try (FileWriter writer = new FileWriter(ventasDiaMozoFile, true)) {
            writer.write(ventasDia.toStringMozo(mozo)+ "\n");
            writer.flush();
        }
                
        try (FileInputStream toRead = new FileInputStream(ventasDiaMozoFile)) {
            System.setIn(toRead);
            try (Scanner reader = new Scanner(toRead)) {
                while (reader.hasNext())
                    System.out.println(reader.nextLine());
                System.setIn(System.in);
            }
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
        
        try (FileWriter writer = new FileWriter(ventasDiaMesaFile, true)) {
            writer.write(ventasDia.toStringMesa(mesa) + "\n");
            writer.flush();
        }
                
        try (FileInputStream toRead = new FileInputStream(ventasDiaMesaFile)) {
            System.setIn(toRead);
            try (Scanner reader = new Scanner(toRead)) {
                while (reader.hasNext())
                    System.out.println(reader.nextLine());
                System.setIn(System.in);
            }
        }
        System.out.println();
    }
    
}
