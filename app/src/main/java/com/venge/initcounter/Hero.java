package com.venge.initcounter;


public class Hero extends Creature{
    Hero(String name,int init,int initMod) {
        super(name ,init, initMod);
        int dmgDone=0,dmgTkn=0,healDone=0,healTkn=0;
        display = init+" "+name;
    }
}
