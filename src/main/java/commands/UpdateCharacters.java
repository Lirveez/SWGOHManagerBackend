package commands;

import org.json.JSONArray;
import services.GetResponse;
import services.MySQLTools;

import java.io.IOException;
import java.util.Map;

public class UpdateCharacters implements Ajax {
    @Override
    public Object doCommand(Map parameters) throws IOException {
        JSONArray characters = new JSONArray(GetResponse.getResponse("https://swgoh.gg/api/characters"));
        return "Success. Time spend: "+MySQLTools.insertData(MySQLTools.SET_CHARACTERS,characters.toString())+"ms";
    }
}
