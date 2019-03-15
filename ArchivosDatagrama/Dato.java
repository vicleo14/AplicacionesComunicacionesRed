/*
	Fecha: 14/03/2019
	Alumno: Morales Flores Victor Leonel
	ESCOM-IPN(MX)
	DESCRIPCION:

	Clase para almacenar arreglos de bytes correspondientes a una parte de un archivo. La clase implementa
	Serializable para poder ser enviada a traves de un flujo. 
*/


import java.io.Serializable;

public class Dato implements Serializable
{
	private int n;
	private byte[] bytes;
	private int t;
	private String nombre;
	/*
		bytes: Buffer de los Dato.
		n: numero de archivo.
		t: tamanio de 'bytes'
	*/
	public Dato(byte[] bytes, int n,int t,String nombre)
	{
		this.n=n;
		this.t=t;
		this.bytes=bytes;
		this.nombre=nombre;
		System.out.println("El archivo creado es el numero:"+n);
	}
	public byte[] getBytes()
	{
		return bytes;
	}
	public int getT()
	{
		return t;
	}
	public int getN()
	{
		return n;
	}
	public String getNombre	()
	{
		return nombre;
	}

}
