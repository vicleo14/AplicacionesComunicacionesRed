import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class ServidorDat//Esto esta mal
{
	private int port;
	public ServidorDat(int port)
	{
		this.port=port;
		try
		{	
			DatagramSocket s=new DatagramSocket(port);
			
			System.out.println("Esperando cliente...\nSocket:"+s.getLocalPort());
			
			for(;;)
			{
				DatagramPacket p=new DatagramPacket(new byte[65535]/*Tamnio maximo de UDP*/,65535);
				s.receive(p);//Bloqueante
				System.out.println("Recibió datos...");
				System.out.println("Datagrama recibido desde:"+p.getAddress()+":"+p.getPort());
				String msj= new String(p.getData()/*Arreglo de bytes--Devuelve 65535 bytes por defecto*/,0/*offset*/,p.getLength()/*Carga util*/);
				
				System.out.println("Dato enviado:"+msj);
				s.send(p);//Bloqueante
				//System.out.println("Datagrama enviado desde:"+s.getAddress()+":"+s.getPort());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		ServidorDat sDat=new ServidorDat(1234);
	}
}
