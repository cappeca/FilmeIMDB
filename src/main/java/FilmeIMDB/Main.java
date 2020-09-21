package FilmeIMDB;

// O objetivo desta aolicação é ouvir em uma porta TCP
// uma busca por filme, enviar de forma assíncrona para o IMDB
// e receber uma lista de filmes baseada nesse nome
public class Main 
{
    public static void main(String[] args) 
    {
        Ouvidor ouvidor = new Ouvidor();
        ouvidor.ficaOuvindo();
    }
}
