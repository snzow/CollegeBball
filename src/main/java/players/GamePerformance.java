package players;

import teams.Team;

public class GamePerformance {
    public int shots;
    public int madeShots;
    public int threes;
    public int madeThrees;
    public int freeThrows;
    public int madeFreeThrows;
    public int points;
    public int assists;
    public int rebounds;
    public int steals;
    public int blocks;
    public Player player;
    public boolean win;
    public Team team;
    public Team opponent;

    public GamePerformance(int shots, int madeShots, int threes, int madeThrees, int freeThrows, int madeFreeThrows, int points,
                           int assists, int rebounds, int steals, int blocks, Player player, boolean win, Team team, Team opponent) {
        this.shots = shots;
        this.madeShots = madeShots;
        this.threes = threes;
        this.madeThrees = madeThrees;
        this.freeThrows = freeThrows;
        this.madeFreeThrows = madeFreeThrows;
        this.points = points;
        this.assists = assists;
        this.rebounds = rebounds;
        this.steals = steals;
        this.blocks = blocks;
        this.player = player;
        this.win = win;
        this.team = team;
        this.opponent = opponent;
    }

    public String toString(){
        return getWinOrLoss() + " vs. " + opponent + ": " + madeShots + "/" + shots +
                " 3pt: " + madeThrees + "/" + threes + " ft: " + madeFreeThrows + "/" + freeThrows + " " + points + "/" + assists + "/" + rebounds;
    }

    public String getWinOrLoss(){
        if(win){
            return "win";
        }
        return "loss";
    }
}
