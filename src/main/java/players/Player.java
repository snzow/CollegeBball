package players;

import main.Main;

public class Player {
    public String firstName;
    public String lastName;
    public int offense;
    public int defense;
    public PlayerStat gameStats;

    public Player(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.offense = (int)Main.randomNumber(50,85);
        this.defense = (int)Main.randomNumber(50,85);
        this.gameStats = new PlayerStat(this);
    }

    public Player(String firstName, String lastName,int offense, int defense){
        this.firstName = firstName;
        this.lastName = lastName;
        this.offense = offense;
        this.defense = defense;
        this.gameStats = new PlayerStat(this);
    }

    public boolean takeShot(Player marker,int type){
        double makeShot = Main.randomNumber(0,180);
        double required = this.offense - ((double)marker.defense/10);
        if(type == 3){
            required -= 10;
        }
        if(makeShot <= required){
            this.gameStats.shoot(true,type);
            return true;
        }
        this.gameStats.shoot(false,type);
        return false;
    }

    public String toString(){
        return firstName + " " + lastName;
    }
}
