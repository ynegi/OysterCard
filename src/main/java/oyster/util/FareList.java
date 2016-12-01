package oyster.util;

public enum FareList{

    ANYWHERE_IN_ZONE_1(2.50),
    ANY_ONE_ZONE_OUTSIDE_ZONE_1 (2.00),
    ANY_TWO_ZONES_INCLUDING_ZONE_1(3.00),
    ANY_TWO_ZONES_EXCLUDING_ZONE_1(2.25),
    ANY_THREE_ZONES(3.20),
    ANY_BUS_JOURNEY(1.80);

    private double singleFare;

    FareList(double singleFare) {

        this.singleFare = singleFare;
    }

    public double getSingleFare(){

        return this.singleFare;
    }
}

