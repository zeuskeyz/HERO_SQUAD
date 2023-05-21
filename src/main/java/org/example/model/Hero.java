package org.example.model;

public class Hero {
    private String hero;
    private Integer age;
    private String power;
    private Integer power_score;
    private String weakness;
    private Integer weakness_score;
    private String squad;

    public void setHero(String hero) {this.hero= hero;}
    public void setAge(Integer age) {this.age = age;}
    public void setPower(String power) {this.power = power;}
    public void setPower_score(Integer power_score) {this.power_score = power_score;}
    public void setWeakness(String weakness) {this.weakness = weakness;}
    public void setWeakness_score(Integer weakness_score) {this.weakness_score = weakness_score;}
    public void setSquad(String squad) {this.squad = squad;}

    public String getHero() {return hero;}
    public Integer getAge() {return age;}
    public String getPower() {return power;}
    public Integer getPower_score() {return power_score;}
    public String getWeakness() {return weakness;}
    public Integer getWeakness_score() {return weakness_score;}
    public String getSquad() {return squad;}

    public Hero(String hero, Integer age, String power, Integer power_score, String weakness, Integer weakness_score, String squad) {
        this.hero = hero;
        this.age = age;
        this.power = power;
        this.power_score = power_score;
        this.weakness = weakness;
        this.weakness_score = weakness_score;
        this.squad = squad;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "hero='" + hero + '\'' +
                ", age=" + age +
                ", power='" + power + '\'' +
                ", power_score=" + power_score +
                ", weakness='" + weakness + '\'' +
                ", weakness_score=" + weakness_score +
                ", squad='" + squad + '\'' +
                '}';
    }
}
