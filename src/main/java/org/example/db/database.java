package org.example.db;

import org.sql2o.Sql2o;

public class database {
   private static final Sql2o connection = new Sql2o (

           "jdbc:postgresql://localhost:5432/hero_squad",
           "postgres",
           "javaDB"
   );
   public static Sql2o getConnect() { return connection; }
}
