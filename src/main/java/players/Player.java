package players;

import engine.Game;
import engine.Position;
import main.Main;

import java.util.ArrayList;

public class Player {
    public String firstName;
    public String lastName;
    public int shooting;
    public int passing;
    public int defense;
    public int rebounding;

    public Position position;
    public Position secondaryPosition;

    public Position positionForTeam;
    public PlayerTendencies tendencies;

    public PlayerStat gameStats;

    public ArrayList<GamePerformance> pastPerformances;

    public Player(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.shooting = Main.randomNumber(50,85);
        this.passing = Main.randomNumber(50,85);
        this.defense = Main.randomNumber(50,85);
        this.rebounding = Main.randomNumber(50,85);
        this.gameStats = new PlayerStat(this);
        setTendencies();
        pastPerformances = new ArrayList<>();


    }

    public Player(String firstName, String lastName,int shooting,int rebounding, int passing, int defense){
        this.firstName = firstName;
        this.lastName = lastName;
        this.shooting = shooting;
        this.defense = defense;
        this.passing = passing;
        this.rebounding = rebounding;
        this.gameStats = new PlayerStat(this);
        setTendencies();
        pastPerformances = new ArrayList<>();
    }

    public Player(String firstName, String lastName, int strength){
        this.firstName = firstName;
        this.lastName = lastName;
        this.shooting = Main.randomNumber(strength-10,strength + 10);
        this.passing = Main.randomNumber(strength-10,strength + 10);
        this.defense = Main.randomNumber(strength-10,strength + 10);
        this.rebounding = Main.randomNumber(strength-10,strength + 10);
        this.gameStats = new PlayerStat(this);
        setTendencies();
        pastPerformances = new ArrayList<>();
    }

    public void assist(){
        gameStats.assists++;
    }

    public boolean takeShot(Player marker, Player assist, int type){
        double makeShot = Main.randomNumber(0,120);
        double required = this.getEffectiveShooting() + ((double)marker.getEffectiveDefense()/10);
        if(type == 3){
            required -= 14;
        }
        else if(type == 1){
            if(makeShot < this.getEffectiveShooting() + 17){
                this.gameStats.shoot(true,type);
                return true;
            }
        }
        if(makeShot <= required){
            this.gameStats.shoot(true,type);
            if(assist != null){
                assist.assist();
            }
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
        double total = shooting + passing + rebounding + defense;
        total = total / 4.0;
        total = (total *gameStats.stamina) / 100;
        return (int)total;
    }

    public int getPoints(){
        return gameStats.points;
    }

    public String toString(){
        return firstName + " " + lastName;
    }

    private void setTendencies(){
        int max = shooting;
        if(rebounding > max){
            max = rebounding;
        }
        if(passing > max){
            max = passing;
        }
        if(defense > max){
            max = defense;
        }
        Position pg = Main.positions.get("PG");
        Position sg = Main.positions.get("SG");
        Position sf = Main.positions.get("SF");
        Position pf = Main.positions.get("PF");
        Position c = Main.positions.get("C");

        if(defense > passing && max == shooting){
            this.position = sg;
            this.secondaryPosition = pg;
        }
        else if(passing >= defense && max == shooting){
            this.position = pg;
            this.secondaryPosition = sg;
        }
        else if(defense > shooting && max == rebounding){
            this.position = c;
            this.secondaryPosition = pf;
        }
        else if(shooting >= defense && max == rebounding){
            this.position = pf;
            this.secondaryPosition = c;
        }
        else if(shooting > rebounding && max == defense){
            this.position = sf;
            this.secondaryPosition = c;
        }
        else if(rebounding >= shooting && max == defense){
            this.position = c;
            this.secondaryPosition = sf;
        }
        else if(shooting > rebounding && max == passing){
            this.position = pg;
            this.secondaryPosition = sf;
        }
        else{
            this.position = pf;
            this.secondaryPosition = sf;
        }
        this.tendencies = new PlayerTendencies(this,this.position);
    }

    public void setPositionForTeam(Position position){
        positionForTeam = position;
    }

    public Position getPositionForTeam(){
        if(positionForTeam != null){
            return positionForTeam;
        }
        return position;
    }

    public String getStringBasicPosition(){
        Position p = position;
        if(p.equals(Main.POINT_GUARD) || p.equals(Main.SHOOTING_GUARD)){
            return "G";
        }
        else if(p.equals(Main.CENTER)){
            return "C";
        }
        else{
            return "F";
        }
    }

    public void printPerformances(){
        System.out.println("--" + this + "--");
        System.out.println("Averages: " + getPpg() + "/" + getApg() + "/" + getRpg());
        for(GamePerformance g : pastPerformances){
            System.out.println(g.toString());
        }
    }

    public int getPpg(){
        double total = 0;
        for(GamePerformance g : pastPerformances){
            total += g.points;
        }
        total = total/pastPerformances.size();
        return (int)total;
    }
    public int getApg(){
        double total = 0;
        for(GamePerformance g : pastPerformances){
            total += g.assists;
        }
        total = total/pastPerformances.size();
        return (int)total;
    }
    public int getRpg(){
        double total = 0;
        for(GamePerformance g : pastPerformances){
            total += g.rebounds;
        }
        total = total/pastPerformances.size();
        return (int)total;
    }
}
