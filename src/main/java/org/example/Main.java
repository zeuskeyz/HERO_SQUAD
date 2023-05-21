package org.example;

import org.example.dao.HeroDao;
import org.example.dao.SquadDao;
import org.example.db.database;
import org.example.model.Hero;
import org.example.model.Squad;
import org.sql2o.Connection;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public abstract class Main {

    public static void main(String[] args) {

        staticFileLocation("/public");
        HandlebarsTemplateEngine views = new HandlebarsTemplateEngine();

        //LANDING PAGE
        get("/", (req,res) -> new ModelAndView(new HashMap<>(),"landing-page.hbs"), views );

        //HOME PAGE
        get("/home", (req,res) -> {

            Map<String, Object> combinedList = new HashMap<>();
            combinedList.put("heroObject", HeroDao.getAllHeroes());
            combinedList.put("squadObject", SquadDao.getAllSquads());
            return new ModelAndView(combinedList, "home.hbs");

        },views);

        //HERO ADDING FORM PAGE
        get("/add-hero", (req,res) -> new ModelAndView(new HashMap<>(),"add-hero.hbs"), views );

        //SENDING HERO DETAILS TO DATABASE
        post("/add-hero", (req,res)-> {

            String hero = req.queryParams("hero");
            Integer age = Integer.parseInt(req.queryParams("age"));
            String power = req.queryParams("power");
            Integer power_score = Integer.parseInt(req.queryParams("power_score"));
            String weakness = req.queryParams("weakness");
            Integer weakness_score = Integer.parseInt(req.queryParams("weakness_score"));
            String squad = "";

            Hero newHero = new Hero(hero.toUpperCase(),age,power,power_score,weakness,weakness_score,squad);
            HeroDao.addHero(newHero);
            res.redirect("/home");
            return null ;
        });

        //SQUAD ADDING FORM PAGE
        get("/add-squad", (req,res) -> new ModelAndView(new HashMap<>(),"add-squad.hbs"), views );

        //SENDING HERO DETAILS TO DATABASE
        post("/add-squad", (req,res)-> {

            String squad = req.queryParams("squad");
            String cause = req.queryParams("cause");
            Integer size = Integer.parseInt(req.queryParams("size"));
            Squad newSquad = new Squad(squad,cause,size);
            SquadDao.addSquad(newSquad);
            res.redirect("/home");
            return null;

        });

        //HERO TO SQUAD ASSIGNMENT FORM
        get("/assign-squad/:squad", (req,res) -> {

            String squad =  req.params("squad");
            Map<String, Object> combinedList = new HashMap<>();

            if(HeroDao.heroCount(squad) < SquadDao.maxSize(squad)) {
                combinedList.put("querySquad", squad);
                combinedList.put("heroObject", HeroDao.membership(squad));
            } else res.redirect("/full-squad");

            return new ModelAndView(combinedList, "assign-squad.hbs");
        },views);

        //ASSIGNING A HER0 TO A SQUAD
        post("/assign-squad/:squad", (req,res) -> {

            String hero = req.queryParams("hero");
            String squad = req.queryParams("squad");
            HeroDao.updateMembership(hero, squad);
            res.redirect("/home");
            return null;

        },views);

        //DELETING A HERO FROM THE PAGE
        get("/delete-hero/:hero", (req,res)-> {

            String name = req.params(":hero");
            HeroDao.deleteHero(name);
            res.redirect("/home");
            return null;

        },views);

        //DELETING A SQUAD FROM THE PAGE
        get("/delete-squad/:squad", (req,res)-> {

            String name = req.params(":squad");
            SquadDao.deleteSquad(name);
            res.redirect("/home");
            return null;

        },views);

        //CREATES A PAGE WITH SEARCHABLE TABLE LIST OF ALL HEROES AND SQUADS
        get("/all", (req,res) -> {

            Map<String, Object> combinedList = new HashMap<>();
            combinedList.put("heroObject", HeroDao.getAllHeroes());
            combinedList.put("squadObject", SquadDao.getAllSquads());
            return new ModelAndView(combinedList, "all.hbs");

        },views);

        //FULL SQUAD ERROR PAGE
        get("/full-squad", (req,res) -> new ModelAndView(new HashMap<>(),"full-squad.hbs"), views );

    }
}

