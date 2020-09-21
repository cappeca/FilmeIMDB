package FilmeIMDB;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.time.Duration;
import static java.time.temporal.ChronoUnit.SECONDS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.*;
import org.jsoup.*;
import org.jsoup.select.Elements;


// Esta classe acessa a pagina do IMDB, executa a query informada e retorna com 
// lista de filmes
public final class AcessImdb 
{
    private static final String BASE_URL = "https://www.imdb.com/";

    public String consulta(String query) throws Exception
    {
        String encodedQuery;
        try 
        {
            encodedQuery = URLEncoder.encode(query, "UTF-8");
        } 
        catch (UnsupportedEncodingException ex) 
        {
            encodedQuery = query;
        }
        StringBuilder sbURL = new StringBuilder(BASE_URL);
        sbURL.append("find?q=");
        sbURL.append(encodedQuery);

        //Bypass certificado https
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() 
        {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
        } };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) { return true; }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        // carrega pagina
        URL url = new URL(sbURL.toString());
        URLConnection con = url.openConnection();

        StringWriter writer = new StringWriter();
        IOUtils.copy(con.getInputStream(), writer);
        return writer.toString();
        
    }
    
    public String busca(String query)
    {
        String resposta;
        try
        {
            resposta = consulta(query);
        }
        catch (Exception ex)
        {
            resposta = "Sem Resposta";
        }
        return resposta;   
    } 
    
    public String parse(String resposta)
    {   
        Document doc = Jsoup.parse(resposta);
        Elements findSection = doc.getElementsByClass("findSection");
        List<String> movies = new ArrayList<>();
        StringBuilder retorno = new StringBuilder("");

        Elements findSectionHeader;
        Elements findList = null;
        Elements resultText = null;
        
        for (int x = 0; x < findSection.size(); x++)
        {
            findSectionHeader = findSection.get(x).getElementsByClass("findSectionHeader");
            if (findSectionHeader.first().child(0).attr("name").equals("tt"))
            {
                findList = findSection.get(x).getElementsByClass("findList");
                resultText = findList.get(0).getElementsByClass("result_text");
            }
        }
        
        if (resultText != null)
        {
            for (int x = 0; x < resultText.size(); x++)
            {
                retorno.append(resultText.get(x).text());
                retorno.append("\n");
            }
            return retorno.toString();                
        }
        else
        {
            return "\n";
        }
    } 
}