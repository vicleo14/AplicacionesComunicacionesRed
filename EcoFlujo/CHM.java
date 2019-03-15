import java.net.*;
import java.io.*;
/*
Sin red:
127.0.0.1
ocalhost
*/
public class CHM{
	private static int pto=9000;
	private static Socket cl;
	private static String host;
	private static PrintWriter pw;
	private static BufferedReader br2;
	public void cerrarConexion()
	{
		try{
			System.out.println("Termina aplicación");
			br2.close();
			pw.close();
			cl.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();//Vaciado de pila. Metodos que se ejecutaron hasta la excepcion
		}
		finally
		{
			System.exit(0);
		}	
	}
	public String leerDeFlujo()
	{
		String cad="";
		try{		
			cad=br2.readLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();//Vaciado de pila. Metodos que se ejecutaron hasta la excepcion
		}
		return cad;
	}
	public void escribirAFlujo(String msj)
	{
		try
		{
			pw.println(msj);
			pw.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();//Vaciado de pila. Metodos que se ejecutaron hasta la excepcion
		}
	}
	public static void main(String[] arg)
	{

		BufferedReader br1=new BufferedReader(new InputStreamReader(System.in/*Devuelve in inputstream(bajo nivel)*/));
		
		try
		{
			System.out.println("\nEscribe la direccion IP del servidor:");
			host=br1.readLine();
			CHM chm=new CHM();
			cl=new Socket(host,pto);
			System.out.println("\nConexión con el servidor establecida... Escriba una cadena de texto");

			pw=new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));			
			br2=new BufferedReader(new InputStreamReader(cl.getInputStream()));
			for(;;)
			{
				String msj=br1.readLine();
				chm.escribirAFlujo(msj);
				if(msj.compareToIgnoreCase("salir")==0)
				{
					br1.close();
					chm.cerrarConexion();
				}
				else
				{
					String eco=chm.leerDeFlujo();
					System.out.println("Eco recibido:"+eco);
					System.out.println("Indica otro mensaje:");
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();//Vaciado de pila. Metodos que se ejecutaron hasta la excepcion
		}
	}
}
