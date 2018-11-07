package commands;

import services.MySQLTools;
import services.Password;

import java.util.Map;

public class AddUser implements Ajax {
    @Override
    public Object doCommand(Map parameters) {
        Password pwd = new Password();
        int allycode = Integer.parseInt(((String[])parameters.get("allycode"))[0]);
        String name = ((String[])parameters.get("login"))[0];
        byte[] saltBytes = pwd.getSalt(15);
        String password = ((String[])parameters.get("password"))[0];
        byte[] passwordBytes = Password.hashWithSalt(password, saltBytes);
        String salt = pwd.base64FromBytes(saltBytes);
        password = pwd.base64FromBytes(passwordBytes);
        return MySQLTools.addUser(allycode, name,salt,password);
    }
}
