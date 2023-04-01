package players;

public class PlayerStat {
    public int shots;
    public int madeShots;

    public int threes;

    public int madeThrees;
    public int points;
    public int assists;
    public int rebounds;
    public int steals;
    public int blocks;
    public int stamina;
    public Player player;

    public PlayerStat(Player player) {
        this.player = player;
        this.shots = 0;
        this.madeShots = 0;
        this.threes = 0;
        this.madeThrees = 0;
        this.points = 0;
        this.assists = 0;
        this.steals = 0;
        this.blocks = 0;
        this.rebounds = 0;
        this.stamina = 100;
    }

    public void shoot(boolean madeShot,int type){
        shots++;
        if(type == 3){
            threes++;
        }
        if(madeShot){
            madeShots++;
            points += type;
            if(type == 3){
                madeThrees++;
            }
        }


    }

    public void rebound(){
        rebounds++;
    }

    public void resetStats(){
        this.shots = 0;
        this.madeShots = 0;
        this.threes = 0;
        this.madeThrees = 0;
        this.points = 0;
        this.assists = 0;
        this.steals = 0;
        this.blocks = 0;
        this.rebounds = 0;
        this.stamina = 100;
    }

    public String toString(){
        return player.toString() + " " + player.getEffectiveOvr() + " " + madeShots + "/" + shots + " 3pt: " + madeThrees + "/" + threes + " pts/ast/rb: " + points + "/" + assists + "/" + rebounds;
    }
}
