package players;

public class PlayerStat {
    public int shots;
    public int madeShots;
    public int points;
    public int assists;
    public int rebounds;
    public int steals;
    public int blocks;
    public Player player;

    public PlayerStat(Player player) {
        this.player = player;
        this.shots = 0;
        this.madeShots = 0;
        this.points = 0;
        this.assists = 0;
        this.steals = 0;
        this.blocks = 0;
        this.rebounds = 0;
    }

    public void shoot(boolean madeShot,int type){
        shots++;
        if(madeShot){
            madeShots++;
            points += type;
        }

    }

    public String toString(){
        return player.toString() + " " + madeShots + "/" + shots + " " + points + " pts";
    }
}
