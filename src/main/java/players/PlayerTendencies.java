package players;

import engine.Position;

public class PlayerTendencies {
    int threeRatio;
    int twoRatio;
    int passTendency;
    int shotTendency;
    int rbTendency;
    Position position;
    Player player;

    public PlayerTendencies(Player player,Position position){
        this.player = player;
        this.position = position;
        this.threeRatio = position.getThreeRatio();
        this.twoRatio = position.getTwoRatio();
        this.passTendency = position.getPassTendency();
        this.shotTendency = position.getShotTendency();
        this.rbTendency = position.getRbTendency();

        if(player.shooting > 80){
            shotTendency++;
            passTendency--;
            threeRatio++;
            twoRatio--;
        }
        if(player.rebounding > 80){
            rbTendency++;
        }
        if(player.passing > 80 && player.shooting < 80){
            player.passing++;
        }

    }

    public int getThreeRatio() {
        return threeRatio;
    }

    public int getTwoRatio() {
        return twoRatio;
    }

    public int getPassTendency() {
        return passTendency;
    }

    public int getShotTendency() {
        return shotTendency;
    }

    public int getRbTendency() {
        return rbTendency;
    }
}
