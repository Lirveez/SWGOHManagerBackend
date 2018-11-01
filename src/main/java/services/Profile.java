package services;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Profile {
    private String name;
    private int ally_code;
    private int galactic_power;
    private List<Unit> units = new ArrayList();

    public Profile(int ally_code) throws IOException {
        JSONObject jsonObject = new JSONObject(GetResponse.getResponse("https://swgoh.gg/api/player/"+ally_code+"/"));
        JSONArray unitsArr = jsonObject.getJSONArray("units");
        JSONObject data = jsonObject.getJSONObject("data");
        this.name = data.getString("name");
        this.galactic_power = data.getInt("galactic_power");
        this.units = getUnits(unitsArr);
    }

    public Profile(JSONObject player) {
        this.name = player.getJSONObject("data").getString("name");
        this.ally_code = player.getJSONObject("data").getInt("ally_code");
        this.galactic_power = player.getJSONObject("data").getInt("galactic_power");
        this.units = getUnits(player.getJSONArray("units"));
    }
    private List<Unit> getUnits(JSONArray jsonUnits){
        List<Unit> units = new ArrayList();
        for(int i=0;i<jsonUnits.length();i++){
            JSONObject unit = jsonUnits.getJSONObject(i).getJSONObject("data");
            JSONObject stats = unit.getJSONObject("stats");
            units.add(new Unit(
                    new Stats(
                            stats.getInt("1"),
                            stats.getInt("5"),
                            stats.getInt("6"),
                            stats.getInt("7"),
                            stats.getInt("14"),
                            stats.getInt("15"),
                            stats.getInt("16"),
                            stats.getInt("18"),
                            stats.getInt("28")
                    ),
                    unit.getInt("gear_level"),
                    unit.getInt("power"),
                    unit.getInt("level"),
                    getZetas(unit.getJSONArray("zeta_abilities")),
                    unit.getInt("rarity"),
                    unit.getString("base_id")));
        }
        return units;
    }
    public List<String> getZetas(JSONArray tmpArr){
        List<String> list = new ArrayList();
        for(int i = 0; i < tmpArr.length(); i++){
            list.add(tmpArr.getString(i));
        }
        return list;
    }
    public String getName(){
        return this.name;
    }
    public int getAlly_code(){
        return this.ally_code;
    }
    public int getGalactic_power(){
        return this.galactic_power;
    }
    public List<Unit> getUnits(){
        return this.units;
    }
    public Unit getUnit(int index){
        return this.units.get(index);
    }
    public void setName(String name){
        this.name = name;
    }

    public void setAlly_code(int ally_code) {
        this.ally_code = ally_code;
    }

    public void setGalactic_power(int galactic_power) {
        this.galactic_power = galactic_power;
    }

    public void setUnit(List<Unit> units) {
        this.units = units;
    }
    public void setUnit(Unit unit){
        this.units.add(unit);
    }
}
