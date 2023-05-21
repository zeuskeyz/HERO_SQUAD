package org.example.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Squad {
    private  String squad;
    private  String cause;
    private  Integer size;

    public String getSquad() {
        return squad;
    }

    public void setSquad(String squad) {
        this.squad = squad;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


    public Squad(String squad, String cause, Integer size) {
        this.squad = squad;
        this.cause = cause;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Squad{" +
                "squad='" + squad + '\'' +
                ", cause='" + cause + '\'' +
                ", size=" + size +
                '}';
    }
}
