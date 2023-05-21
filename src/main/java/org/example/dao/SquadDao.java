package org.example.dao;

import org.example.db.database;
import org.example.model.Hero;
import org.example.model.Squad;
import org.sql2o.Connection;

import java.util.List;

public class SquadDao {

    //GETS A LIST OF ALL THE SQUADS IN OUR DATABASE
    public static List<Squad> getAllSquads (){
        List<Squad> allSquads = null;
        try(Connection db = database.getConnect().open()){
            String squads = "SELECT * FROM squads;";
            allSquads = db.createQuery(squads).executeAndFetch(Squad.class);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return allSquads;
        }
        return allSquads;
    }

    //ADDS NEW SQUAD DETAILS TO THE DATABASE
    public static void addSquad(Squad newSquad) {
        try(Connection db = database.getConnect().open()){
            String squadAdd = "INSERT INTO squads (squad,cause, size) VALUES (:squad, :cause, :size);";
            db.createQuery(squadAdd).bind(newSquad).executeUpdate();
        } catch (Exception error) { System.out.println(error.getMessage());}
    }

    //RETURNS THE SET SQUAD SIZE FROM THE DATABASE
    public static Integer maxSize (String squad) {
        List<Squad> squadSize = null;
        try(Connection db = database.getConnect().open()){
            String sizeList = "SELECT size from squads WHERE squad = (:squad)"; //gets the size
            squadSize = db.createQuery(sizeList).addParameter("squad", squad).executeAndFetch(Squad.class);
        } catch (Exception error) { System.out.println(error.getMessage());}
        return squadSize.get(0).getSize();
    }

    //DELETES A SQUAD FROM THE DATABASE
    public static void deleteSquad(String name){
        try(Connection db = database.getConnect().open()){
            String deletedSquad = "DELETE FROM squads WHERE squad = (:squad);";
            db.createQuery(deletedSquad).addParameter("squad", name).executeUpdate();
        } catch (Exception error) { System.out.println(error.getMessage());}
    }

}

