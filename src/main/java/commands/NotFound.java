package commands;

import java.util.Map;
import java.util.TreeMap;

public class NotFound implements Ajax {

    public Object doCommand(Map parameters) {
        return new TreeMap<String, String>(){
            {
                this.put("status", "error");
                this.put("message", "Command not found!");
            }
        };
    }
}
