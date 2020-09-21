package FilmeIMDB;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

// Esta classe é a thread que trata as conexões
// e efetua query a cada mensagem recebida
public class OuvidorThread extends Thread
{
    private Socket socket;
    
    public OuvidorThread (Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try 
        {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            String movieQuery;
            AppChallengeLayout applayout = new AppChallengeLayout();

            while(socket.isConnected())
            {
                movieQuery = reader.readLine();
                AcessImdb acessoimdb = new AcessImdb();
                writer.println(applayout.formata(acessoimdb.parse(acessoimdb.busca(applayout.limpa(movieQuery)))));
            }
        }
        catch (Exception ex)
        {
                System.out.println(ex.getMessage());
                return;
        }
    }
}
