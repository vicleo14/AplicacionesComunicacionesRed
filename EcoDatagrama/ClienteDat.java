import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
/*
	PARA DIFERENTES TIPOS DE DATO
	**LECTURA**
	-->DataInputStream dis=new DataInputStream(new ByteArrayInputStream(p.getData()));
	
	int v1=dis.readInt();
	float v1=dis.readFloat();
	long v1=dis.readLong();
*/
import java.io.IOException;

/*
	**ESCRITURA**
	ByteArrayOutputStream baos=new ByteArrayOutputStream()<-- Nos da la capacidad de escribir bytes
	DataOutputStream dos= new DataOutputStream(baos);
	dos.writeInt(5);
	dos.writeaFloat(8.0f);
	dos.writeLong(7);
	dos.flush();
	byte[] b=daos.toByteArray();
	DatagramPacket p=new DatagramPacket (b,b.length,dst,pto);

*/
public class ClienteDat{
	private DatagramSocket cl;	
	private byte[] b;
	private String host="127.0.0.1";
	private int pto=1234;
	private int limite =2000;
	private InetAddress dst=null;
	public ClienteDat()
	{
		try
		{
			BufferedReader br1=new BufferedReader(new InputStreamReader(System.in/*Devuelve in inputstream(bajo nivel)*/));
			cl=new DatagramSocket();
			cl.setReuseAddress(true);
			
			
			//cl.close();
			/*Otro try-catch*/
			try
			{
				dst=InetAddress.getByName(host);
			}
			catch(Exception ex)//UnknownHostException
			{
				System.out.println("La dirección no es válida.");
				System.exit(1);				
			}
			for(;;)
			{
				System.out.println("Indica un mensaje:");
				String msj=br1.readLine();
				b=msj.getBytes();
				if(msj.compareToIgnoreCase("salir")==0)
				{
					br1.close();
				}
				else
				{
					String msjT="";
					byte[] buff=msj.getBytes();

					if(buff.length>limite)
					{
						ByteArrayInputStream bais=new ByteArrayInputStream(buff);
						int n=0;
						byte[] buff2=new byte[1500];
						while((n=bais.read(buff2))!=-1)
						{
							msjT+=enviar(buff2);
						}
					}
					else
					{
						msjT=enviar(buff);
					}
					
					//System.out.println("Indica otro mensaje:");
				}
			}
			//cl.close();
				
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	public String enviar(byte[] buff)
	{
		String msj="";
		try{
			DatagramPacket p=new DatagramPacket(buff,buff.length,dst,pto);
			cl.send(p);
			DatagramPacket d1=new DatagramPacket(buff,buff.length);
			cl.receive(d1);
			String msj2= new String(d1.getData()/*Arreglo de bytes--Devuelve 65535 bytes por defecto*/,0/*offset*/,d1.getLength()/*Carga util*/);
			System.out.println("Dato recibido:"+msj2);
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return msj;
	}
	public static void main(String[] args)
	{
		ClienteDat cd=new ClienteDat();	
	}
}
