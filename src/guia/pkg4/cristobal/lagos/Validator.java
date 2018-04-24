package guia.pkg4.cristobal.lagos;

import java.util.Scanner;

public class Validator {

    private static Scanner std = new Scanner (System.in);
    
    public Validator() {}
    
    public static int validacionInt(){
		int num;
		while(std.hasNextInt()==false){
			System.out.print("El valor ingresado no es numerico. Ingrese nuevamente: ");
			std.nextLine();}
		num=std.nextInt();
		std.nextLine();
		while (num<0){
			System.out.print("El valor ingresado es menor a cero. Ingrese nuevamente: ");
			num=validacionInt();
		}
		return num;
    }
    
    public static boolean validacionSiNo(){
		boolean bool=false;
		String x=std.nextLine();
		while(x.toLowerCase().matches("si")==false && x.toLowerCase().matches("no")==false){
			System.out.print("El valor ingresado no es valido. Ingrese nuevamente: ");
			x=std.nextLine();}
		if (x.toLowerCase().matches("si"))
			bool=true;
		if (x.toLowerCase().matches("no"))
			bool=false;
		return bool;
	}
}
