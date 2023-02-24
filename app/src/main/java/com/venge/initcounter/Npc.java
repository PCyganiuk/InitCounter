package com.venge.initcounter;

public class Npc extends Creature{
    int hp;
    Npc(String name, int init, int initMod, int hp){
        super(name,init,initMod);
        this.hp=hp;
        display = init+" "+name+" HP: "+hp;
    }
}
