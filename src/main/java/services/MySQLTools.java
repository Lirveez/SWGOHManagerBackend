package services;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.sql.*;
import java.util.StringJoiner;

import static services.Password.base64FromBytes;
import static services.Password.hashWithSalt;


public class MySQLTools {
    private static final String url = "jdbc:mysql://host.sytes.net:3306/db";
    private static final String user = "login";
    private static final String password = "pass";
    private final static int BATCH_SIZE = 100;
    public final static String SET_UNITS = "Units";
    public final static String SET_GUILD_PLAYERS = "players";
    public final static String SET_CHARACTERS = "characters";
    public final static String GET_CHARACTER_BY_ID = "character";
    public final static String GET_PLAYER = "player";
    public final static String GET_GUILD = "guild";
    private static Connection con;
    private static ResultSet rs;
    private static PreparedStatement pst;
    private static long timeSpend;

    public static long insertData(String table, String jsonString) {
        JSONTokener tokener = new JSONTokener(jsonString);
        long startDate = System.currentTimeMillis();
        switch (table) {
            case SET_UNITS: {
                long startTime = System.currentTimeMillis();
                JSONObject jsonObject = new JSONObject(tokener);
                JSONArray players = jsonObject.getJSONArray("players");
                try {
                    con = getConnection();
                    pst = con.prepareStatement("INSERT INTO unit (allycode,baze_id,gear_level,power,level,zeta_abilities,stars, hp,potency,armor,speed,ph_dmg,crit_dmg,crit_ch,spc_dmg,scrit_ch) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE gear_level=?,power=?,level=?,zeta_abilities=?, stars=?, hp=?,potency=?,armor=?,speed=?,ph_dmg=?,crit_dmg=?,crit_ch=?,spc_dmg=?,scrit_ch=?;");
                    for (int i = 0; i < players.length(); i++) {

                        JSONArray units = players.getJSONObject(i).getJSONArray("units");
                        System.out.println("pst_unit for " + players.getJSONObject(i).getJSONObject("data").getString("name") + " i=" + i);
                        for (int j = 0; j < units.length(); j++) {
                            String zetas = parseArray(units.getJSONObject(j).getJSONObject("data").getJSONArray("zeta_abilities"));
                            pst.setInt(1, players.getJSONObject(i).getJSONObject("data").getInt("ally_code"));
                            pst.setString(2, units.getJSONObject(j).getJSONObject("data").getString("base_id"));
                            pst.setInt(3, units.getJSONObject(j).getJSONObject("data").getInt("gear_level"));
                            pst.setInt(4, units.getJSONObject(j).getJSONObject("data").getInt("power"));
                            pst.setInt(5, units.getJSONObject(j).getJSONObject("data").getInt("level"));
                            pst.setString(6, zetas);
                            pst.setInt(7, units.getJSONObject(j).getJSONObject("data").getInt("rarity"));
                            pst.setInt(8, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("1"));
                            pst.setInt(9, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("18"));
                            pst.setInt(10, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("28"));
                            pst.setInt(11, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("5"));
                            pst.setInt(12, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("6"));
                            pst.setInt(13, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("16"));
                            pst.setInt(14, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("14"));
                            pst.setInt(15, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("7"));
                            pst.setInt(16, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("15"));
                            pst.setInt(17, units.getJSONObject(j).getJSONObject("data").getInt("gear_level"));
                            pst.setInt(18, units.getJSONObject(j).getJSONObject("data").getInt("power"));
                            pst.setInt(19, units.getJSONObject(j).getJSONObject("data").getInt("level"));
                            pst.setString(20, zetas);
                            pst.setInt(21, units.getJSONObject(j).getJSONObject("data").getInt("rarity"));
                            pst.setInt(22, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("1"));
                            pst.setInt(23, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("18"));
                            pst.setInt(24, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("28"));
                            pst.setInt(25, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("5"));
                            pst.setInt(26, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("6"));
                            pst.setInt(27, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("16"));
                            pst.setInt(28, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("14"));
                            pst.setInt(29, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("7"));
                            pst.setInt(30, units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("15"));
                            pst.addBatch();
                            if (j % BATCH_SIZE == 0) {

                                pst.executeBatch();
                            }
                        }

                        pst.executeBatch();

                    }
                    con.commit();
                } catch (SQLException sql) {
                    sql.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException se) {
                    }
                    try {
                        pst.close();
                    } catch (SQLException se) {
                    }
                }
                timeSpend = System.currentTimeMillis() - startTime;
                break;
            }
            case SET_GUILD_PLAYERS: {
                long startTime = System.currentTimeMillis();
                JSONObject jsonObject = new JSONObject(tokener);
                JSONArray players = jsonObject.getJSONArray("players");
                try {
                    con = getConnection();
                    pst = con.prepareStatement("DELETE from players where guild_id=?");
                    pst.setInt(1, jsonObject.getInt("guild_id"));
                    pst.execute();
                    pst = con.prepareStatement("INSERT INTO players (ally_code,guild_id,name,galactic_power) VALUES (?,?,?,?);");

                    for (int i = 0; i < players.length(); i++) {
                        pst.setInt(1, players.getJSONObject(i).getJSONObject("data").getInt("ally_code"));
                        pst.setInt(2, jsonObject.getInt("guild_id"));
                        pst.setString(3, players.getJSONObject(i).getJSONObject("data").getString("name"));
                        pst.setInt(4, players.getJSONObject(i).getJSONObject("data").getInt("galactic_power"));
                        pst.addBatch();
                        if (i % BATCH_SIZE == 0) {
                            pst.executeBatch();
                        }
                    }
                    pst.executeBatch();
                    con.commit();

                } catch (SQLException sql) {
                    sql.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException se) {
                    }
                    try {
                        pst.close();
                    } catch (SQLException se) {
                    }
                }
                timeSpend = System.currentTimeMillis() - startTime;
                break;
            }
            case SET_CHARACTERS: {
                JSONArray characters = new JSONArray(tokener);
                try {
                    con = getConnection();
                    pst = con.prepareStatement("INSERT INTO characters (baze_id, char_name, img, categories) VALUES(?,?,?,?) " +
                            "ON DUPLICATE KEY UPDATE char_name=?,img=?,categories=?;");
                    for (int i = 0; i < characters.length(); i++) {
                        String catergories = parseArray(characters.getJSONObject(i).getJSONArray("categories"));
                        pst.setString(1, characters.getJSONObject(i).getString("base_id"));
                        pst.setString(2, characters.getJSONObject(i).getString("name").replace("\"", "'"));
                        pst.setString(3, characters.getJSONObject(i).getString("image"));
                        pst.setString(4, catergories);
                        pst.setString(5, characters.getJSONObject(i).getString("name").replace("\"", "'"));
                        pst.setString(6, characters.getJSONObject(i).getString("image"));
                        pst.setString(7, catergories);
                        pst.addBatch();
                        if (i % BATCH_SIZE == 0)
                            pst.executeBatch();
                    }
                    pst.executeBatch();
                    con.commit();

                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException se) {
                    }
                    try {
                        pst.close();
                    } catch (SQLException se) {
                    }
                }

                timeSpend = System.currentTimeMillis() - startDate;
                break;
            }
        }
        return timeSpend;
    }

    public static String getData(String type, String id) {
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        switch (type) {
            case GET_CHARACTER_BY_ID: {
                try {
                    con = getConnection();

                    pst = con.prepareStatement("SELECT * FROM characters WHERE baze_id like ?");
                    pst.setString(1, "%" + id + "%");
                    con.commit();
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        jsonObject = new JSONObject();
                        jsonObject.put("char_name", rs.getString("char_name"));
                        jsonObject.put("img", rs.getString("img"));
                        jsonObject.put("categories", rs.getString("categories"));
                        jsonArray.put(jsonObject);
                    }


                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException se) { /*can't do anything */ }
                    try {
                        pst.close();
                    } catch (SQLException se) { /*can't do anything */ }
                    try {
                        rs.close();
                    } catch (SQLException se) { /*can't do anything */ }
                }
            }
        }
        return jsonArray.toString();
    }

    public static String addUser(int allycode, String login, String salt, String encrypted_password) {
        TokenManager token = new TokenManager();
        String tokenString = token.createToken(login, allycode);
        try {
            if (!checkLogin(login) && !checkAllycode(allycode)) {
                con = getConnection();
                pst = con.prepareStatement("INSERT INTO users (token, allycode, login, encrypted_password, salt)" +
                        "VALUES (?,?,?,?,?);");
                System.out.println(tokenString);
                pst.setString(1, tokenString);
                pst.setInt(2, allycode);
                pst.setString(3, login);
                pst.setString(4, encrypted_password);
                pst.setString(5, salt);
                pst.executeUpdate();
                con.commit();
            } else
                return "Login or allycode is busy";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "User added";
    }

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        return con;
    }

    private static boolean checkAllycode(int allycode) {
        int result = 0;
        try {
            con = getConnection();
            pst = con.prepareStatement("SELECT allycode FROM users WHERE allycode=?;");
            pst.setInt(1, allycode);
            rs = pst.executeQuery();
            while (rs.next())
                result = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try{
            rs.close();
        }
        catch (SQLException ex){ex.printStackTrace();}
        System.out.println(result+" "+allycode+" "+(result==allycode));
        if (result == allycode)
            return true;
        else
            return false;
    }

    private static JSONObject getUser(String login) {
        JSONObject jsonObject = new JSONObject();
        try {
            con = getConnection();
            pst = con.prepareStatement("SELECT login,allycode FROM users WHERE login=?;");
            pst.setString(1, login);
            rs = pst.executeQuery();
            while (rs.next()) {
                jsonObject.put("login", rs.getString("login"));
                jsonObject.put("allycode", rs.getInt("allycode"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try{
            rs.close();
        }
        catch (SQLException ex){ex.printStackTrace();}
        return jsonObject;
    }
    private static String getOldToken(String login){
        String result = null;
        try{
            con = getConnection();
            pst = con.prepareStatement("SELECT token FROM users where login=?;");
            con.commit();
            pst.setString(1,login);
            rs = pst.executeQuery();
            while(rs.next())
                result = rs.getString("token");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        try {
            con.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }
    private static void renewToken(String login) {
        TokenManager token = new TokenManager();
        try{
            String oldToken = getOldToken(login);
            String strToken = token.createToken(login,token.getAllycode(oldToken));
            con = getConnection();
            pst = con.prepareStatement("UPDATE users SET token=? WHERE login=?;");
            pst.setString(1,strToken);
            pst.setString(2,login);
            pst.execute();
            con.commit();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try{
            rs.close();
        }
        catch (SQLException ex){}
    }

    private static boolean checkLogin(String login) {
        String result = null;
        try {
            con = getConnection();
            pst = con.prepareStatement("SELECT login FROM users WHERE login=?;");
            pst.setString(1, login);
            con.commit();
            rs = pst.executeQuery();
            while (rs.next())
                result = rs.getString(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try{
            rs.close();
        }
        catch (SQLException ex){}
        if (login.equals(result))
            return true;
        else
            return false;
    }

    private static String getSaltForUser(String name) {
        String salt = null;
        try {
            con = getConnection();
            pst = con.prepareStatement("SELECT salt FROM users WHERE login=?;");
            pst.setString(1, name);
            rs = pst.executeQuery();
            con.commit();
            if (rs.next()) {
                salt = rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try{
            rs.close();
        }
        catch (SQLException ex){ex.printStackTrace();}
        return salt;
    }

    private static boolean validateUser(String name, String pass) {
        boolean result = false;
        try {
            con = getConnection();
            pst = con.prepareStatement("SELECT login FROM users WHERE login=? AND encrypted_password=?;");
            pst.setString(1, name);
            pst.setString(2, pass);
            rs = pst.executeQuery();
            con.commit();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try{
            rs.close();
        }
        catch (SQLException ex){ex.printStackTrace();}
        return result;
    }

    public static String authenticate(String login, String givenPwd) {
        if (!checkLogin(login))
            return "Login not found";
        String salt = getSaltForUser(login);
        String result = null;
        if (salt != null) {
            Password pwd = new Password();
            byte[] saltBytes = pwd.bytesFrombase64(salt);
            byte[] passwordBytes = hashWithSalt(givenPwd, saltBytes);
            String password = base64FromBytes(passwordBytes);
            if (validateUser(login, password)) {
                renewToken(login);
                result = "User validated";
            } else
                result = "Incorrect Password";
        }
        return result;
    }

    private static String parseArray(JSONArray jsonArray) {
        StringJoiner result = new StringJoiner(",");
        for (int i = 0; i < jsonArray.length(); i++)
            result.add(jsonArray.getString(i));
        return result.toString();
    }
}
