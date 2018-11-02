package services;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.sql.*;
import java.util.StringJoiner;
import java.util.Date;

public class MySQLTools {
    private static final String url = "jdbc:mysql://host:port/databasename";
    private static final String user = "login";
    private static final String password = "password";
    public final static String UNITS = "Units";
    public final static String GUILD_PLAYERS = "players";
    public final static String CHARACTERS = "characters";
    private final static int BATCH_SIZE = 100;
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static long timeSpend;

    public static long insertData(String table, String jsonString){
        JSONTokener tokener = new JSONTokener(jsonString);
        long startDate = System.currentTimeMillis();
        switch (table){
            case UNITS:{
                long startTime = System.currentTimeMillis();
                JSONObject jsonObject = new JSONObject(tokener);
                JSONArray players = jsonObject.getJSONArray("players");
                try {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    con = DriverManager.getConnection(url, user, password);
                    stmt = con.createStatement();
                    con.setAutoCommit(false);
                    PreparedStatement pst = con.prepareStatement("INSERT INTO unit (allycode,baze_id,gear_level,power,level,zeta_abilities,stars, hp,potency,armor,speed,ph_dmg,crit_dmg,crit_ch,spc_dmg,scrit_ch) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE gear_level=?,power=?,level=?,zeta_abilities=?, stars=?, hp=?,potency=?,armor=?,speed=?,ph_dmg=?,crit_dmg=?,crit_ch=?,spc_dmg=?,scrit_ch=?;");
                    for(int i=0;i<players.length();i++){

                    JSONArray units = players.getJSONObject(i).getJSONArray("units");
                        System.out.println("pst_unit for " + players.getJSONObject(i).getJSONObject("data").getString("name")+ " i="+i);
                    for(int j=0;j<units.length();j++) {
                        String zetas = parseArray(units.getJSONObject(j).getJSONObject("data").getJSONArray("zeta_abilities"));
                        pst.setInt(1,players.getJSONObject(i).getJSONObject("data").getInt("ally_code"));
                        pst.setString(2, units.getJSONObject(j).getJSONObject("data").getString("base_id"));
                        pst.setInt(3, units.getJSONObject(j).getJSONObject("data").getInt("gear_level"));
                        pst.setInt(4, units.getJSONObject(j).getJSONObject("data").getInt("power"));
                        pst.setInt(5, units.getJSONObject(j).getJSONObject("data").getInt("level"));
                        pst.setString(6, zetas);
                        pst.setInt(7, units.getJSONObject(j).getJSONObject("data").getInt("rarity"));
                        pst.setInt(8,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("1"));
                        pst.setInt(9,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("18"));
                        pst.setInt(10,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("28"));
                        pst.setInt(11,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("5"));
                        pst.setInt(12,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("6"));
                        pst.setInt(13,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("16"));
                        pst.setInt(14,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("14"));
                        pst.setInt(15,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("7"));
                        pst.setInt(16,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("15"));
                        pst.setInt(17, units.getJSONObject(j).getJSONObject("data").getInt("gear_level"));
                        pst.setInt(18, units.getJSONObject(j).getJSONObject("data").getInt("power"));
                        pst.setInt(19, units.getJSONObject(j).getJSONObject("data").getInt("level"));
                        pst.setString(20, zetas);
                        pst.setInt(21, units.getJSONObject(j).getJSONObject("data").getInt("rarity"));
                        pst.setInt(22,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("1"));
                        pst.setInt(23,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("18"));
                        pst.setInt(24,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("28"));
                        pst.setInt(25,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("5"));
                        pst.setInt(26,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("6"));
                        pst.setInt(27,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("16"));
                        pst.setInt(28,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("14"));
                        pst.setInt(29,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("7"));
                        pst.setInt(30,units.getJSONObject(j).getJSONObject("data").getJSONObject("stats").getInt("15"));
                        pst.addBatch();
                        if(j%BATCH_SIZE==0){

                            System.out.println("Trying to execute");
                            pst.executeBatch();

                            System.out.println("execute over");
                        }
                    }

                        System.out.println("execute remaining");
                    pst.executeBatch();
                        System.out.println("Over");

                    }
                    con.commit();
                    pst.close();
                }catch (SQLException sql){
                    sql.printStackTrace();
                }finally {
                    try { con.close(); } catch(SQLException se) {}
                    try { stmt.close(); } catch(SQLException se) {}
                }
                timeSpend = System.currentTimeMillis() - startTime;
                break;
            }
            case GUILD_PLAYERS:{
                long startTime = System.currentTimeMillis();
                JSONObject jsonObject = new JSONObject(tokener);
                JSONArray players = jsonObject.getJSONArray("players");
                try {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    con = DriverManager.getConnection(url, user, password);
                    stmt = con.createStatement();
                    con.setAutoCommit(false);
                    PreparedStatement pst = con.prepareStatement("INSERT INTO players (ally_code,guild_id,name,galactic_power) VALUES (?,?,?,?)" +
                            "ON DUPLICATE KEY UPDATE guild_id=?,name=?,galactic_power=?;");

                    for(int i=0;i<players.length();i++){
                        pst.setInt(1,players.getJSONObject(i).getJSONObject("data").getInt("ally_code"));
                        pst.setInt(2,jsonObject.getInt("guild_id"));
                        pst.setString(3, players.getJSONObject(i).getJSONObject("data").getString("name"));
                        pst.setInt(4,players.getJSONObject(i).getJSONObject("data").getInt("galactic_power"));
                        pst.setInt(5,jsonObject.getInt("guild_id"));
                        pst.setString(6, players.getJSONObject(i).getJSONObject("data").getString("name"));
                        pst.setInt(7,players.getJSONObject(i).getJSONObject("data").getInt("galactic_power"));
                        pst.addBatch();
                    }
                    pst.executeBatch();
                    con.commit();
                    pst.close();
                }catch (SQLException sql){
                    sql.printStackTrace();
                }finally {
                    try { con.close(); } catch(SQLException se) {}
                    try { stmt.close(); } catch(SQLException se) {}
                }
                timeSpend = System.currentTimeMillis() - startTime;
                break;
            }
            case CHARACTERS:{
                JSONArray characters = new JSONArray(tokener);
                try {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    con = DriverManager.getConnection(url, user, password);
                    stmt = con.createStatement();
                    con.setAutoCommit(false);
                    PreparedStatement pst = con.prepareStatement("INSERT INTO characters (baze_id, char_name, img, categories) VALUES(?,?,?,?) " +
                            "ON DUPLICATE KEY UPDATE char_name=?,img=?,categories=?;");
                for(int i=0;i<characters.length();i++) {
                    String catergories = parseArray(characters.getJSONObject(i).getJSONArray("categories"));
                    pst.setString(1,characters.getJSONObject(i).getString("base_id"));
                    pst.setString(2,characters.getJSONObject(i).getString("name").replace("\"", "'"));
                    pst.setString(3,characters.getJSONObject(i).getString("image"));
                    pst.setString(4,catergories);
                    pst.setString(5,characters.getJSONObject(i).getString("base_id"));
                    pst.setString(6,characters.getJSONObject(i).getString("name").replace("\"", "'"));
                    pst.setString(7,characters.getJSONObject(i).getString("image"));
                    pst.addBatch();
                    if(i%BATCH_SIZE==0)
                        pst.executeBatch();
                }
                    pst.executeBatch();
                   // con.commit();
                    pst.close();
                    } catch (SQLException sqlEx){
                        sqlEx.printStackTrace();
                    }finally {
                        try { con.close(); } catch(SQLException se) {}
                        try { stmt.close(); } catch(SQLException se) {}
                    }

                timeSpend = System.currentTimeMillis() - startDate;
                break;
            }
        }
        return timeSpend;
    }
    private static String parseArray(JSONArray jsonArray){
        StringJoiner result = new StringJoiner(",");
        for (int i=0;i<jsonArray.length();i++)
            result.add(jsonArray.getString(i));
        return result.toString();
    }
}
