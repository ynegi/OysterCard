package oyster.model;

public class JourneyDetails {

    private String startingPoint;
    private String endPoint;

    private String travelMode;

    private String swipedInMode;
    private String swipedOutMode;

    private double singleFare;
    private String journeyStatus;

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getSwipedInMode() {
        return swipedInMode;
    }

    public void setSwipedInMode(String swipedInMode) {
        this.swipedInMode = swipedInMode;
    }

    public String getSwipedOutMode() {
        return swipedOutMode;
    }

    public void setSwipedOutMode(String swipedOutMode) {
        this.swipedOutMode = swipedOutMode;
    }

    public double getSingleFare() {
        return singleFare;
    }

    public void setSingleFare(double singleFare) {
        this.singleFare = singleFare;
    }

    public String getJourneyStatus() {
        return journeyStatus;
    }

    public void setJourneyStatus(String journeyStatus) {
        this.journeyStatus = journeyStatus;
    }

    @Override
    public String toString() {
        return "JourneyDetails{" +
                ", startingPoint='" + startingPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", travelMode='" + travelMode + '\'' +
                ", swipedInMode='" + swipedInMode + '\'' +
                ", swipedOutMode='" + swipedOutMode + '\'' +
                ", singleFare=" + singleFare +
                ", journeyStatus='" + journeyStatus + '\'' +
                '}';
    }
}
