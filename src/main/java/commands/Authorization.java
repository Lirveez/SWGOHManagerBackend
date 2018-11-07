package commands;

import jdk.nashorn.internal.parser.Token;
import org.json.JSONObject;
import services.MySQLTools;
import services.Password;
import services.TokenManager;

import java.io.IOException;
import java.util.Map;

public class Authorization implements Ajax {
    @Override
    public Object doCommand(Map parameters) throws IOException {
        String name = ((String[]) parameters.get("login"))[0];
        String password = ((String[]) parameters.get("password"))[0];
        String response = MySQLTools.authenticate(name, password);
        return response;
    }
}
