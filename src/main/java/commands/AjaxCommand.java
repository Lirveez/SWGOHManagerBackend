package commands;

import java.io.IOException;
import java.util.Map;

public interface AjaxCommand {
    Object doCommand(Map parameters) throws IOException;
}