package FilmeIMDB;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.Runner;

public class ImdbTest
{
    @Test
    public void testQuery()
    {
        String movieQuery = "Walking Dead";        
        AcessImdb acessoimdb = new AcessImdb();
        String movieList = acessoimdb.parse(acessoimdb.busca(movieQuery));
        System.out.println(movieList);
        assertTrue(movieList, movieList != null);
    }

    @Test
    public void testProcesso()
    {
        String movieQuery = "12:Walking Dead";
        AppChallengeLayout applayout = new AppChallengeLayout();
        String movieQueryLimpa = applayout.limpa(movieQuery);
        System.out.println(movieQueryLimpa);
        AcessImdb acessoimdb = new AcessImdb();
        String movieList = acessoimdb.parse(acessoimdb.busca(movieQueryLimpa));
        System.out.println(movieList);
        String finalMovieList = applayout.formata(movieList);
        System.out.println(finalMovieList);
        assertTrue(finalMovieList, finalMovieList != null);
    }
}
