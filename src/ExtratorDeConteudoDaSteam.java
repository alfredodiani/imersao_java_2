
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


public class ExtratorDeConteudoDaSteam implements ExtratorDeConteudo{
    
    public List<Conteudo> extraiConteudos(String json){

        List<Conteudo> conteudos = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray gamesWinArray = jsonObject.getJSONArray("featured_win");
        
        for (int i = 0; i < gamesWinArray.length(); i++) {
            JSONObject game = gamesWinArray.getJSONObject(i);
            String name = game.getString("name");
            String largeCapsuleImage = game.getString("large_capsule_image");
            String urlImage = largeCapsuleImage.replace("\\", "");

            conteudos.add(new Conteudo(name, urlImage));
        }

        return conteudos;
    }
}
