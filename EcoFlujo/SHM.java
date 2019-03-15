import java.net.*;
import java.io.*;
/*
Metodos
print()
println()
write()
*/
public class SHM{

	public static void main(String[] arg)
	{
		try
		{
			int pto=9000;
			ServerSocket s=new ServerSocket(pto);
			System.out.println("Servicio iniciado...");
			s.setReuseAddress(true);//
			
			for(;;)
			{
				System.out.println("\nEsperando nuevo cliente...");
				Socket cl=s.accept();
				cl.setSoLinger(true,5000);//Se habilita una vez que se invoca el metodo close()
				PrintWriter pw=new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				BufferedReader br=new BufferedReader(new InputStreamReader(cl.getInputStream()));
				
				System.out.println("Cliente conectado desde:"+cl.getInetAddress()+":"+cl.getPort());
				System.out.println("Preparado para enviar mensaje");
				for(;;)
				{
					String msj=br.readLine();
					if(msj.compareToIgnoreCase("salir")==0)
					{
						pw.close();
						System.out.println("El cliente cerró conexión");
						break;
					}
					else
					{
						
						System.out.println(cl.getInetAddress()+":"+cl.getPort()+" dice: "+msj);
						pw.println(msj);
						pw.flush();//Drena todo lo que se encuentra en el buffer
					}
				}
				
				
				
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();//Vaciado de pila. Metodos que se ejecutaron hasta la excepcion
		}
	}
}
