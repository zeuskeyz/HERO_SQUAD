package org.example;

import org.example.db.database;
import org.example.model.Hero;
import org.example.model.Squad;
import org.sql2o.Connection;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        HandlebarsTemplateEngine views = new HandlebarsTemplateEngine();

        get("/", (req,res) -> {
            List<Hero> allHeroes = null;
            List<Squad> allSquads = null;
            try(Connection db = database.getConnect().open()){
                String heroes = "SELECT * FROM heroes;";
                String squads = "SELECT * FROM squads";
                allHeroes = db.createQuery(heroes).executeAndFetch(Hero.class);
                allSquads = db.createQuery(squads).executeAndFetch(Squad.class);

            } catch (Exception error) { System.out.println(error.getMessage());}

            Map<String, Object> combinedList = new HashMap<>();
            combinedList.put("hero", allHeroes);
            combinedList.put("squad", allSquads);
            System.out.println(combinedList);

            return new ModelAndView(combinedList, "home.hbs");
            },views);

        get("/assign-squad/:name", (req,res) -> {
            String heroParam = "";
            List<Squad> allSquads = null;
            try(Connection db = database.getConnect().open()){
                String heroes = "SELECT * FROM heroes WHERE name=(:name);";
                String squads = "SELECT * FROM squads";
                heroParam =  req.params(":name");
                allSquads = db.createQuery(squads).executeAndFetch(Squad.class);

            } catch (Exception error) { System.out.println(error.getMessage());}

            Map<String, Object> combinedList = new HashMap<>();
            combinedList.put("hero", heroParam);
            combinedList.put("squad", allSquads);

            return new ModelAndView(combinedList, "assign-squad.hbs");
        },views);

        post("/assign-squad/:name", (req,res) -> {
            String name = req.queryParams("name");
            String squad = req.queryParams("squad");
            Integer age = 0;
            String power = "";
            Integer power_score = 0;
            String weakness = "";
            Integer weakness_score = 0;


            Hero newHero = new Hero(name,age,power,power_score,weakness,weakness_score,squad);

            try(Connection db = database.getConnect().open()){
                String heroUpdate = "UPDATE heroes SET squad = (:squad) WHERE name = (:name)";
                db.createQuery(heroUpdate).bind(newHero).executeUpdate();
            } catch (Exception error) { System.out.println(error.getMessage());}
            res.redirect("/");
            return null;
        },views);


        get("/add-hero", (req,res) -> new ModelAndView(new HashMap<>(),"add-hero.hbs"), views );

        post("/add-hero", (req,res)-> {

            String name = req.queryParams("name");
            Integer age = Integer.parseInt(req.queryParams("age"));
            String power = req.queryParams("power");
            Integer power_score = Integer.parseInt(req.queryParams("power_score"));
            String weakness = req.queryParams("weakness");
            Integer weakness_score = Integer.parseInt(req.queryParams("weakness_score"));
            String squad = "";

            Hero newHero = new Hero(name.toUpperCase(),age,power,power_score,weakness,weakness_score,squad);

            try(Connection db = database.getConnect().open()){
                String heroAdd = "INSERT INTO heroes (name,age,power,power_score,weakness,weakness_score) VALUES (:name, :age, :power, :power_score, :weakness, :weakness_score);";
                db.createQuery(heroAdd).bind(newHero).executeUpdate();

            } catch (Exception error) { System.out.println(error.getMessage());}

            res.redirect("/");

            return null ;
        });

        
        get("/add-squad", (req,res) -> new ModelAndView(new HashMap<>(),"add-squad.hbs"), views );

        post("/add-squad", (req,res)-> {

            String name = req.queryParams("name");
            String cause = req.queryParams("cause");
            Integer size = Integer.parseInt(req.queryParams("size"));
            ArrayList<String> members = new ArrayList<String>();

            Squad newSquad = new Squad(name,cause,size,members);

            try(Connection db = database.getConnect().open()){
                String squadAdd = "INSERT INTO squads (name,cause, size) VALUES (:name, :cause, :size);";
                db.createQuery(squadAdd).bind(newSquad).executeUpdate();

            } catch (Exception error) { System.out.println(error.getMessage());}

            res.redirect("/");

            return null;

        });

    }
}