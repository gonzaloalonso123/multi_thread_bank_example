package Banco;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente extends Thread{

	
	private int idCliente;
	private Caja caja;
	private Caja[] cajas;
	private Semaphore semaforo;
	
	public void recibirSemaforo(Semaphore semaforo)
	{
		this.semaforo = semaforo;
	}
		
	public Cliente (int idCliente, Caja[] cajas)
	{
		this.idCliente = idCliente;
		this.cajas = cajas;
	}
	
	public void run()
	{
		System.out.println("Entra en la sucursal el cliente " + idCliente);
		Random r = new Random();
		try {
			System.out.println("El cliente " + idCliente + " esta pensando en que cola situarse...");
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numCaja = r.nextInt(2);
		this.caja = cajas[numCaja];
		//Una caja es asignada aleatoriamente. En ese momento, la variable caja de cliente es asignada y sera usada para llamar a los metodos correspondientes.
		
		System.out.println("El cliente " + idCliente + " se coloca en la cola " + (numCaja + 1));
		
		recibirSemaforo(caja.darSemaforo());
		// La caja asignada le entrega su semaforo al cliente. Todos los clientes a los que se haya asignado dicha caja compartiran un mismo semaforo.
		
		try {
			semaforo.acquire();
			
			//Uno de los clientes adquiere el permiso del semaforo y procedera a ocupar la caja, el resto esperaran su turno.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		caja.entrarEnCaja(idCliente);
		
		//El cliente que acaba de adquirir el semaforo entra en caja, por ello este metodo no es sincrono, el semaforo de cada caja gestionara que solo 1 cliente la ocupe a la vez.
		try {
			sleep(r.nextInt(2001) + 3000);
			
			//Este sleep sirve para dormir entre 2 y 5 segundos antes de que se realice la operacion (un numero de 0 al 3 al que se le sumara 2)
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		caja.OperacionRealizada(idCliente);
		semaforo.release();
		
		//Ya pueden entrar nuevos clientes a la caja. Mientras tanto, la gestion de la cola de salida se llevara en la caja mediante metodos sincronizados.
		
		caja.colaSalida(idCliente);
	}
}
