/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FilmeIMDB;

/**
 *
 * @author cappeca
 */
public class AppChallengeLayout 
{

    public String limpa(String entrada)
    {
        // tira formato de layout tamanho:query para apenas query
        return(entrada.substring(entrada.indexOf(":") +1));
    }
    
    public String formata (String entrada)
    {
        // coloca o tamanho da string e faz string nova
        StringBuilder saida = new StringBuilder(String.valueOf(entrada.length()));
        saida.append(":");
        saida.append(entrada);
        
        return saida.toString();
    }
}
