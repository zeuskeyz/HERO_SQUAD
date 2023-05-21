package org.example.dao;

import org.example.db.database;
import org.example.model.Hero;
import org.example.model.Squad;
import org.sql2o.Connection;

import java.util.List;

public class HeroDao {

    //GETS A LIST OF ALL THE HEROES IN OUR DATABASE
    public static List<Hero> getAllHeroes (){
        List<Hero> allHeroes = null;

        try(Connection db = database.getConnect().open()){
            String heroes = "SELECT * FROM heroes;";
            allHeroes = db.createQuery(heroes).executeAndFetch(Hero.class);

        } catch (Exception error) {
            System.out.println(error.getMessage());
            return allHeroes;
        }

        return allHeroes;
    }

    //ADDS NEW HERO DETAILS TO THE DATABASE
    public static void addHero ( Hero newHero) {
        try(Connection db = database.getConnect().open()){
            String heroAdd = "INSERT INTO heroes (hero,age,power,power_score,weakness,weakness_score) VALUES (:hero, :age, :power, :power_score, :weakness, :weakness_score);";
            db.createQuery(heroAdd).bind(newHero).executeUpdate();
        } catch (Exception error) { System.out.println(error.getMessage());}
    }

    //CHECKS NUMBER OF HEROES IN A PARTICULAR SQUAD
   public static Integer heroCount (String squad) {
       Integer heroesInSquad = null;
       try (Connection db = database.getConnect().open()) {
           //CHECKS THE NUMBER OF HEROES IN THE PARAM SQUAD
           String heroCounter = "SELECT COUNT(*) FROM heroes WHERE squad = (:squad)";
           heroesInSquad = db.createQuery(heroCounter).addParameter("squad", squad).executeScalar(Integer.class);
       } catch (Exception error) {
           System.out.println(error.getMessage());
       }
       return heroesInSquad;
   }

   //GIVES A LIST OF HEROES WHO ARE EITHER NOT YET IN ANY SQUAD OR NOT IN THE PARAM SQUAD
   public static List<Hero> membership (String squad) {
       List<Hero> allHeroes = null;
       try(Connection db = database.getConnect().open()){
           String heroList = "SELECT * FROM heroes WHERE squad IS NULL OR squad <> (:squad);";
           allHeroes = db.createQuery(heroList).addParameter("squad", squad).executeAndFetch(Hero.class);
       } catch (Exception error) { System.out.println(error.getMessage());}
       return allHeroes;
   }

   //UPDATES THE HERO DETAILS TO INCLUDE THE SQUAD MEMBERSHIP
    public static void updateMembership (String hero, String squad) {
        try(Connection db = database.getConnect().open()){
            String heroUpdate = "UPDATE heroes SET squad = (:squad) WHERE hero = (:hero)";
            db.createQuery(heroUpdate).addParameter("hero", hero).addParameter("squad", squad).executeUpdate();
        } catch (Exception error) { System.out.println(error.getMessage());}
    }

    //DELETES HERO fFROM DATABASE
    public static void deleteHero(String name){
        try(Connection db = database.getConnect().open()){
            String deletedHero = "DELETE FROM heroes WHERE hero = (:hero);";
            db.createQuery(deletedHero).addParameter("hero", name).executeUpdate();
        } catch (Exception error) { System.out.println(error.getMessage());}
    }


}


