package teams;

public class Season {
    int gamesPlayed;
    int wins;
    int losses;

    public Season(){
        this.gamesPlayed =0;
        this.wins = 0;
        this.losses = 0;
    }

    public void loseGame(){
        gamesPlayed++;
        losses++;
    }

    public void winGame(){
        gamesPlayed++;
        wins++;
    }

    public String toString(){
        return wins + "-" + losses;
    }

    public int getPoints(){
        return wins*2;
    }
}
