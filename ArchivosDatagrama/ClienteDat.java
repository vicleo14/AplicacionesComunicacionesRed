/*
	Fecha: 14/03/2019
	Alumno: Morales Flores Victor Leonel
	ESCOM-IPN(MX)
	DESCRIPCION:

	Socket de Datagrama que lee un archivo para dividirlo en arreglos de bytes. Cada arreglo de bytes se almacena
	en una instancia de la clase 'Dato' que se serializa para ser enviada por el flujo. 
*/


import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.io.FileInputStream;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
/*
	PARA DIFERENTES TIPOS DE DATO
	**LECTURA**
	-->DataInputStream dis=new DataInputStream(new ByteArrayInputStream(p.getData()));
	
	int v1=dis.readInt();
	float v1=dis.readFloat();
	long v1=dis.readLong();
*/

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
	private int tamDato=512;

	
	public ClienteDat()
	{
		try
		{
			cl=new DatagramSocket();
			cl.setReuseAddress(true);
			try
			{
				dst=InetAddress.getByName(host);
			}
			catch(Exception ex)//UnknownHostException
			{
				System.out.println("La dirección no es válida.");
				System.exit(1);				
			}

			JFileChooser jfc=new JFileChooser();
			int r=jfc.showOpenDialog(null);
			if(r==JFileChooser.APPROVE_OPTION)
			{
				File f=jfc.getSelectedFile();
				DataInputStream dis=new DataInputStream(new FileInputStream(f.getAbsolutePath()));
				String nombre=f.getName()+";";
				long tamArc=f.length();
				long  leidos=0,n=0;
				int i=0;
				int total=(int)tamArc/tamDato;

				byte[] bfcad=nombre.getBytes();
				enviar(bfcad);
				//dos.writeUTF(nombre);
				while(tamArc>leidos)
				{
					
					byte[] b=new byte[tamDato];
					n=dis.read(b);
					Dato d=new Dato(b,i,total,nombre);

					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					ObjectOutputStream oos=new ObjectOutputStream(baos);
					oos.writeObject(d);
					oos.flush();
					byte[]b2=baos.toByteArray();
					enviar(b2);
					System.out.println("Se envio "+i);
					leidos+=n;
					i++;
				}
				
					
			}
			//cl.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	public void enviar(byte[] buff)
	{
		try{
			DatagramPacket p=new DatagramPacket(buff,buff.length,dst,pto);
			cl.send(p);
			System.out.println("Se envio");
			p=null;
			System.gc();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		ClienteDat cd=new ClienteDat();	
	}
}
