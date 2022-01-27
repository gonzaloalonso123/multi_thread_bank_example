package Banco;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class GestionBanco {

	public static void main(String[] args) {
		
		Caja[] cajas = new Caja[2];
		for ( int i = 0; i < cajas.length; i++)
		{
			cajas[i] = new Caja(i);
		}
		
		//La idea de tener un array de 2 cajas es para pasarle luego ese array a los clientes, ya que la asignacion se decidira
		//Despues de que los clientes hayan sido construidos.
		
		CajaFuerte.iniciarCaja();
		
		//En caja fuerte hay un integer atomico que sera el balance total del banco. Como es una variable estatica, no crearemos
		//Ninguna instancia de la clase, si no que llamaremos al metodo estatico iniciarCaja cuya funcion es inicializar el
		//AtomicInteger.
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduzca el numero de clientes");
		Cliente[] clientes;
		int numeroC = -1;
		do {
			try {
				numeroC = sc.nextInt();
			}catch(Exception e)
			{
				System.out.println("Introduzca un numero");
			}	
		}while(numeroC < 0); 
		clientes = new Cliente[numeroC];
		
		//Esta estructura esta disenada para que si el usuario introduce caracteres no validos (no integers o integers negativos), el programa
		//No crashee si no que permita reintroducir el dato.
		
			
		for ( int i = 0; i < clientes.length; i++)
		{
			clientes[i] = new Cliente(i + 1, cajas);
			clientes[i].start();
		}
		for ( int i = 0; i < clientes.length; i++)
		{
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Join para que el syso de despues refleje el valor final de dineroTotal.
		
		
		System.out.println("El balance de la caja fuerte es " + CajaFuerte.dineroTotal.get());
	}

}
