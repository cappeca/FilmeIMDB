package FilmeIMDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

// Esta classe fica ouvindo uma porta TCP, e a cada conexão
// cria uma thread de tratamento
public class Ouvidor 
{
    private static int SOCKPORT = 3348;

    private Socket socket;
    private ServerSocket serverSocket;
    
    public void ficaOuvindo() 
    {
        try 
        {
            serverSocket = new ServerSocket(SOCKPORT);
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
            return;
        }
        
        while(true)
        {
            try 
            {
                socket = serverSocket.accept();
                new OuvidorThread(socket).start();
            } 
            catch (IOException ex) 
            {
                System.out.println(ex.getMessage());
                return;
            }
        }
    }    
}
