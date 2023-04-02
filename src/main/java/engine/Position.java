package engine;

public class Position {
    String name;

    String shortName;
    int threeRatio;
    int twoRatio;
    int passTendency;
    int shotTendency;
    int rbTendency;

    public Position(String name,String shortName, int threeRatio,int passTendency,int rbTendency){
        this.name = name;
        this.shortName = shortName;
        this.threeRatio = threeRatio;
        this.twoRatio = 10 - threeRatio;
        this.passTendency = passTendency;
        this.shotTendency = 10 - passTendency;
        this.rbTendency = rbTendency;

    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
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
