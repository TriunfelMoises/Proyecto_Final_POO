package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor extends Thread
{
  public static Vector usuarios = new Vector();
  
  public static void main (String args[])
  {
    ServerSocket sfd = null;
    try
    {
      sfd = new ServerSocket(7000);
    }
    catch (IOException ioe)
    {
      System.out.println("Comunicación rechazada."+ioe);
      System.exit(1);
    }

    while (true)
    {
      try
      {
        Socket nsfd = sfd.accept();
        System.out.println("Conexion aceptada de: "+nsfd.getInetAddress());
        DataInputStream oos = new DataInputStream(nsfd.getInputStream());
        DataOutputStream escritor =  new DataOutputStream(new FileOutputStream(new File("clinica_respaldo.dat")));
      
        int unByte;
        try {
        	while((unByte = oos.read())!=-1) 
        		escritor.write(unByte);
        	oos.close();
        	escritor.close();
        }
        catch (SocketException se) {
            System.out.println("Cliente desconectado: " + se.getMessage());
        }
        catch (IOException e) {
        	e.printStackTrace();
		}
      }
      catch(IOException ioe)
      {
        System.out.println("Error: "+ioe);
      }
    }
  }
}

