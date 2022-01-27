package Banco;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Caja {

	private int idCaja;
	private static Queue<Integer> colaSalida;
	Random r;
	private int dinero = 0;
	Semaphore semaforo;
	
	public Semaphore darSemaforo()
	{
		return this.semaforo;
	}
	
	public Caja (int idCaja)
	{
		this.idCaja = idCaja;
		colaSalida = new LinkedList<Integer>();
		
		this.semaforo = new Semaphore(1);
		r = new Random();
	}
	
	public void entrarEnCaja(int idCliente)
	{	
		System.out.println("-Buenos dias senor/a " + idCliente + ". Soy el cajero numero " + (idCaja + 1) + ".\n Desea ingresar o retirar dinero?");
		
		dinero = r.nextInt(1000) + 1001;
		int decisionCliente = r.nextInt(2);
		if(decisionCliente == 0) {
			System.out.println("-Cliente " + idCliente + ": Deseo retirar " + dinero + " euros.");
			dinero *= -1;
		}			
		else
			System.out.println("-Cliente " + idCliente + ": Deseo ingresar " + dinero + " euros.");
		
	}
	
	public void OperacionRealizada(int idCliente)
	{
		CajaFuerte.operarCaja(dinero);
		System.out.println("(OPERACION REALIZADA " + dinero + ")\n Muchas gracias por su tiempo, cajero " + (idCaja + 1));
	}
	
	public synchronized void colaSalida(int idCliente)
	{
		colaSalida.add(idCliente);
		salir();
	}
	
	public synchronized void salir()
	{
		System.out.println("Ha salido el cliente " + colaSalida.poll());
	}
}
