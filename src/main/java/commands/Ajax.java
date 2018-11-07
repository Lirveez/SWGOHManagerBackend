package commands;

import java.io.IOException;
import java.util.Map;

public interface Ajax {
    Object doCommand(Map parameters) throws IOException;
}