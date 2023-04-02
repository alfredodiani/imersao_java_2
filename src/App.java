import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        //urls das APIs
        //String urlImdb = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        //String urlNasa = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&count=3";
        String urlSteam = "https://store.steampowered.com/api/featured/";

        //requisição HTTP
        var http = new ClienteHttp();
        String json = http.buscaDados(urlSteam);

        //gerador de figurinhas
        GeradorDeFigurinhas gerador = new GeradorDeFigurinhas();

        //exibir todos os dados
        //ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();
        //ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDaSteam();

        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        for (Conteudo conteudo : conteudos) {
            System.out.println(conteudo.getTitulo());
            System.out.println(conteudo.getUrlImagem());

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            gerador.cria(inputStream,"TESTE");
        }
    }
}
