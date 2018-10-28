package services;

public class Unit {
    private Stats stats;
    private int gear_level;
    private int power;
    private int lvl;
    private String[] zetas;
    private int stars;
    private String baze_id;

    public int getGear_level() {
        return gear_level;
    }

    public int getLvl() {
        return lvl;
    }

    public int getPower() {
        return power;
    }

    public int getStars() {
        return stars;
    }

    public Stats getStats() {
        return stats;
    }

    public String getBaze_id() {
        return baze_id;
    }

    public String[] getZetas() {
        return zetas;
    }
    public void setPower(int power) {
        this.power = power;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setZetas(String[] zetas) {
        this.zetas = zetas;
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setGear_level(int gear_level) {
        this.gear_level = gear_level;
    }

    public void setBaze_id(String baze_id) {
        this.baze_id = baze_id;
    }
}
