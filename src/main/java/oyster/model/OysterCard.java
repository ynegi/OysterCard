package oyster.model;

public class OysterCard {

    private JourneyDetails journeyDetails;

    private ZoneDetail zoneDetail;

    private String oysterCardNumber;

    private double cardBalance;

    public JourneyDetails getJourneyDetails() {
        return journeyDetails;
    }

    public void setJourneyDetails(JourneyDetails journeyDetails) {
        this.journeyDetails = journeyDetails;
    }

    public ZoneDetail getZoneDetail() {
        return zoneDetail;
    }

    public void setZoneDetail(ZoneDetail zoneDetail) {
        this.zoneDetail = zoneDetail;
    }

    public String getOysterCardNumber() {
        return oysterCardNumber;
    }

    public void setOysterCardNumber(String oysterCardNumber) {
        this.oysterCardNumber = oysterCardNumber;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    @Override
    public String toString() {
        return "OysterCard{" +
                "journeyDetails=" + journeyDetails +
                ", zoneDetail=" + zoneDetail +
                ", oysterCardNumber='" + oysterCardNumber + '\'' +
                ", cardBalance=" + cardBalance +
                '}';
    }
}
