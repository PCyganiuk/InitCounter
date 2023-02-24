package com.venge.initcounter;

public abstract class Creature {
    int initMod,init;
    String name,display;
    Creature(String name ,int init,int initMod){
        this.name = name;
        this.init = init;
        this.initMod = initMod;
    }
}
