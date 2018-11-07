package commands;

import org.json.JSONObject;
import services.GetResponse;
import services.MySQLTools;

import java.io.IOException;
import java.util.Map;

public class UpdateGuild implements Ajax {
    @Override
    public Object doCommand(Map parameters) throws IOException {
        int guild_id =  Integer.parseInt(((String[]) parameters.get("guild_id"))[0]);
        JSONObject jsonObject = new JSONObject(GetResponse.getResponse("https://swgoh.gg/api/guild/"+guild_id+"/"));
        jsonObject.put("guild_id",guild_id);
       /* Profile[] guild = new Profile[players.length()];
        for (int i=0;i<players.length();i++){
            guild[i] = new Profile(players.getJSONObject(i));
        }*/
        return "Success. Time spend: "+(MySQLTools.insertData(MySQLTools.SET_GUILD_PLAYERS,jsonObject.toString())+
                MySQLTools.insertData(MySQLTools.SET_UNITS,jsonObject.toString()))+"ms";
    }
}
