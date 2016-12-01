package oyster.model;


public class ZoneDetail {

    private int lowestZone;
    private int highestZone;

    public int getLowestZone() {
        return lowestZone;
    }

    public void setLowestZone(int lowestZone) {
        this.lowestZone = lowestZone;
    }

    public int getHighestZone() {
        return highestZone;
    }

    public void setHighestZone(int highestZone) {
        this.highestZone = highestZone;
    }

    @Override
    public String toString() {
        return "ZoneDetail{" +
                "lowestZone=" + lowestZone +
                ", highestZone=" + highestZone +
                '}';
    }
}
