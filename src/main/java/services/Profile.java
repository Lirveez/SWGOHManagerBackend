package services;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String name;
    private int ally_code;
    private int galactic_power;
    private List<Unit> units;

    public Profile() {
        this.name = "";
        this.ally_code = 0;
        this.galactic_power = 0;
        this.units = new ArrayList();
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
