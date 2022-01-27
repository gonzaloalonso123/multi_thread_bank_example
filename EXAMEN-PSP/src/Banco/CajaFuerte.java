package Banco;

import java.util.concurrent.atomic.AtomicInteger;

public class CajaFuerte {

	static AtomicInteger dineroTotal;
	
	public static void iniciarCaja()
	{
		dineroTotal = new AtomicInteger(50000);
		//Metodo que inicializa el AtomicInteger
	}
	
	public static void operarCaja(int importe)
	{
		dineroTotal.set(dineroTotal.get() + importe);
		//Metodo que modifica el valor de dineroTotal
	}
}
