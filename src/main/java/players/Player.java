package players;

import main.Main;

public class Player {
    public String firstName;
    public String lastName;
    public int shooting;
    public int passing;
    public int defense;
    public int rebounding;

    public PlayerStat gameStats;

    public Player(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.shooting = Main.randomNumber(50,85);
        this.passing = Main.randomNumber(50,85);
        this.defense = Main.randomNumber(50,85);
        this.rebounding = Main.randomNumber(50,85);
        this.gameStats = new PlayerStat(this);
    }

    public Player(String firstName, String lastName,int shooting,int rebounding, int passing, int defense){
        this.firstName = firstName;
        this.lastName = lastName;
        this.shooting = shooting;
        this.defense = defense;
        this.rebounding = rebounding;
        this.gameStats = new PlayerStat(this);
    }

    public Player(String firstName, String lastName, int strength){
        this.firstName = firstName;
        this.lastName = lastName;
        this.shooting = Main.randomNumber(strength-10,strength + 10);
        this.passing = Main.randomNumber(strength-10,strength + 10);
        this.defense = Main.randomNumber(strength-10,strength + 10);
        this.rebounding = Main.randomNumber(strength-10,strength + 10);
        this.gameStats = new PlayerStat(this);
    }

    public boolean takeShot(Player marker, Player assist, int type){
        double makeShot = Main.randomNumber(0,130);
        double assistVal = 0;
        if(assist != null){
            assistVal = assist.getEffectivePassing();
        }

        double required = this.getEffectiveShooting() + assistVal/8 - ((double)marker.getEffectiveDefense()/10);
        if(type == 3){
            required -= 10;
        }
        else if(type == 1){
            if(makeShot < this.getEffectiveShooting()){
                this.gameStats.shoot(true,type);
                return true;
            }
        }
        if(makeShot <= required){
            this.gameStats.shoot(true,type);
            return true;
        }
        this.gameStats.shoot(false,type);
        return false;
    }

    public void rebound(){
        gameStats.rebound();
    }

    public int getEffectiveShooting(){
        return (shooting * gameStats.stamina) / 100;
    }
    public int getEffectivePassing(){
        return (passing * gameStats.stamina) / 100;
    }

    public int getEffectiveDefense(){
        return (defense* gameStats.stamina) / 100;
    }

    public int getEffectiveRebounding(){
        return (rebounding * gameStats.stamina) / 100;
    }

    public int getEffectiveOvr(){
        return (int) ((((shooting + defense + rebounding + passing)/4.0) * (gameStats.stamina) )/100.0);
    }

    public int getPoints(){
        return gameStats.points;
    }

    public String toString(){
        return firstName + " " + lastName;
    }
}
