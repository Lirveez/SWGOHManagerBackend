package commands;

import org.json.JSONArray;
import org.json.JSONObject;
import services.GetResponse;
import services.Profile;

import java.io.IOException;
import java.util.Map;

public class AnalyseRequirementsCommand implements AjaxCommand{
    @Override
    public Object doCommand(Map parameters) throws IOException {

      /*  JSONObject jsonObject = GetResponse.getResponse("https://swgoh.gg/api/guild/",26825);
        JSONArray players = jsonObject.getJSONArray("players");
        Profile[] guild = new Profile[players.length()];
        for (int i=0;i<players.length();i++){
            guild[i] = new Profile(players.getJSONObject(i));
        }*/
        return parameters.get("compare").toString();
    }
}
