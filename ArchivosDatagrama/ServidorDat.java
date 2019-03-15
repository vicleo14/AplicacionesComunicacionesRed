/*
	Fecha: 14/03/2019
	Alumno: Morales Flores Victor Leonel
	ESCOM-IPN(MX)
	DESCRIPCION:

	Socket de Datagrama que recibe paquetes que contienen instancias de la clase 'Dato' para deserializarlos
	y obtener un arreglo de bytes correspondientes a un archivo enviado desde otro socket de datagrama. 
*/


import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
public class ServidorDat//Esto esta mal
{
	private int port;
	private String path="Recibidos/";
	public ServidorDat(int port)
	{
		this.port=port;
		try
		{	
			DatagramSocket s=new DatagramSocket(port);
			
			System.out.println("Esperando cliente...\nSocket:"+s.getLocalPort());
			int i=0;
			for(;;)
			{
				DatagramPacket p=new DatagramPacket(new byte[65535]/*Tamnio maximo de UDP*/,65535);
				s.receive(p);//Bloqueante

				System.out.println("Datagrama recibido desde:"+p.getAddress()+":"+p.getPort());
				byte[] b=p.getData();
				String nom=new String(b);
				String[] cads=nom.split(";");
				nom=cads[0];
				File archivo=new File(path+nom);
				
				System.out.println(archivo.getAbsolutePath());
				FileOutputStream fos=new FileOutputStream(archivo);
				DataOutputStream dos=new DataOutputStream(fos);
				for(;;)
				{
					p=new DatagramPacket(new byte[65535]/*Tamnio maximo de UDP*/,65535);
					s.receive(p);//Bloqueante
					b=p.getData();
					ByteArrayInputStream bais=new ByteArrayInputStream(b);
					ObjectInputStream ois=new ObjectInputStream(bais);
					Dato d=(Dato)ois.readObject();
					System.out.println("Paquete "+d.getN()+" de "+d.getT());
					dos.write(d.getBytes());
					p=null;
					System.gc();
					if(d.getN()==d.getT())
						break;
				}
				cads=null;
				b=null;
				fos.close();
				dos.close();
				
				System.out.println("Termino de recibir archivo:"+nom);
				
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
