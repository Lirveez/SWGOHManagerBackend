package commands;

import services.MySQLTools;

import java.io.IOException;
import java.util.Map;

public class GetCharacter implements Ajax {
    @Override
    public Object doCommand(Map parameters) throws IOException {

        return  MySQLTools.getData(MySQLTools.GET_CHARACTER_BY_ID,((String[])parameters.get("baze_id"))[0]);
    }
}
