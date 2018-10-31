package services;

public class Stats {
    private int hp_1;
    private int speed_5;
    private int phys_dmg_6;
    private int spec_dmg_7;
    private int crit_14;
    private int spec_crit_15;
    private int crit_dmg_16;
    private int potency_18;
    private int armor_28;

    public Stats(int hp_1, int speed_5, int phys_dmg_6, int spec_dmg_7, int crit_14, int spec_crit_15, int crit_dmg_16, int potency_18, int armor_28) {
        this.hp_1 = hp_1;
        this.speed_5 = speed_5;
        this.phys_dmg_6 = phys_dmg_6;
        this.spec_dmg_7 = spec_dmg_7;
        this.crit_14 = crit_14;
        this.spec_crit_15 = spec_crit_15;
        this.crit_dmg_16 = crit_dmg_16;
        this.potency_18 = potency_18;
        this.armor_28 = armor_28;
    }

    public int getCrit_14() {
        return crit_14;
    }

    public int getArmor_28() {
        return armor_28;
    }

    public int getCrit_dmg_16() {
        return crit_dmg_16;
    }

    public int getHp_1() {
        return hp_1;
    }

    public int getPhys_dmg_6() {
        return phys_dmg_6;
    }

    public int getPotency_18() {
        return potency_18;
    }

    public int getSpec_crit_15() {
        return spec_crit_15;
    }

    public int getSpec_dmg_7() {
        return spec_dmg_7;
    }

    public int getSpeed_5() {
        return speed_5;
    }

    public void setArmor_28(int armor_28) {
        this.armor_28 = armor_28;
    }

    public void setCrit_14(int crit_14) {
        this.crit_14 = crit_14;
    }

    public void setCrit_dmg_16(int crit_dmg_16) {
        this.crit_dmg_16 = crit_dmg_16;
    }

    public void setHp_1(int hp_1) {
        this.hp_1 = hp_1;
    }

    public void setPhys_dmg_6(int phys_dmg_6) {
        this.phys_dmg_6 = phys_dmg_6;
    }

    public void setPotency_18(int potency_18) {
        this.potency_18 = potency_18;
    }

    public void setSpec_crit_15(int spec_crit_15) {
        this.spec_crit_15 = spec_crit_15;
    }

    public void setSpec_dmg_7(int spec_dmg_7) {
        this.spec_dmg_7 = spec_dmg_7;
    }

    public void setSpeed_5(int speed_5) {
        this.speed_5 = speed_5;
    }
}
