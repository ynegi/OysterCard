package oyster.app;

import oyster.model.OysterCard;
import oyster.model.ZoneDetail;
import oyster.util.Constants;
import oyster.util.FareList;
import oyster.util.TravelMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static oyster.util.CardResource.getMapOfStationAndZones;

public class OysterCardApp {

    List<OysterCard> listOfAllJourneys = new ArrayList<>();

    /*
    Top-up Oyster card with default value of £30
    */
    public OysterCard topUpOysterCard(OysterCard oysterCard){
        oysterCard.setCardBalance(Constants.TOPUP_AMOUNT);
        return oysterCard;
    }

    /*
    Starting point of journey
    */
    public OysterCard startJourney(OysterCard oysterCard){

        Optional<OysterCard> cardFound = findOysterCardById(listOfAllJourneys, oysterCard.getOysterCardNumber());
        if (cardFound.isPresent()) {
            oysterCard.setCardBalance(cardFound.get().getCardBalance());
        }

        if(checkOysterCardBalance(oysterCard.getCardBalance())) {
            if (oysterCard.getJourneyDetails().getTravelMode().equals(TravelMode.TUBE.toString())
                    && oysterCard.getJourneyDetails().getJourneyStatus().equals(Constants.INCOMPLETE)) {
                persistTubeJourneyDetails(oysterCard);
            } else {
                persistBusJourneyDetails(oysterCard);
            }
        }
        return oysterCard;
    }

    /*
        find card details with card id
    */
    private Optional<OysterCard> findOysterCardById(List<OysterCard> journeyDetailsList, String oysterCardId) {
        return journeyDetailsList.stream()
                .filter(o -> o.getOysterCardNumber().equals(oysterCardId))
                .reduce((first, second) -> second);
    }

    /*
    check card balance which should be more than the maximum fare amount which is £3.20
     */
    private boolean checkOysterCardBalance(double oysterCardAmount) {
        OysterCardBalance oysterCardBalance =  a ->   a > Constants.MAXIMUM_FARE;
        return oysterCardBalance.checkCardBalance(oysterCardAmount);
    }

    /*
    Save Tube Journey details
     */
    private void persistTubeJourneyDetails(OysterCard oysterCard) {
        ZoneDetail zoneDetail = new ZoneDetail();

        if(getLowestZone(oysterCard.getJourneyDetails().getStartingPoint()).isPresent())
            zoneDetail.setLowestZone(getLowestZone(oysterCard.getJourneyDetails().getStartingPoint()).get());

        oysterCard.getJourneyDetails().setSingleFare(Constants.MAXIMUM_FARE);

        oysterCard.setZoneDetail(zoneDetail);
        listOfAllJourneys.add(oysterCard);
    }

    private Optional<Integer> getLowestZone(String startingPoint) {
        List<Integer> list = getMapOfStationAndZones().getOrDefault(startingPoint, Collections.emptyList());
        return list.stream().sorted().findFirst();
    }

    private Optional<Integer> getHighestZone(String endPoint) {
        List<Integer> list =  getMapOfStationAndZones().getOrDefault(endPoint, Collections.emptyList());
        return list.stream().sorted().findFirst();
    }

    private void persistBusJourneyDetails(OysterCard oysterCard) {
        oysterCard.getJourneyDetails().setSingleFare(FareList.ANY_BUS_JOURNEY.getSingleFare());
        oysterCard.getJourneyDetails().setJourneyStatus(Constants.COMPLETE);
        oysterCard.setCardBalance(oysterCard.getCardBalance() - FareList.ANY_BUS_JOURNEY.getSingleFare());
        listOfAllJourneys.add(oysterCard);
    }


    public OysterCard endJourney(OysterCard oysterCardAfterJourneyLeg1) {

        Optional<OysterCard> cardFound = findOysterCardById(listOfAllJourneys, oysterCardAfterJourneyLeg1.getOysterCardNumber());

        if(getHighestZone(oysterCardAfterJourneyLeg1.getJourneyDetails().getEndPoint()).isPresent())
            oysterCardAfterJourneyLeg1.getZoneDetail().setHighestZone(getHighestZone(oysterCardAfterJourneyLeg1.getJourneyDetails().getEndPoint()).get());

        return calculateJourneyFare(cardFound.get());
     }

    private OysterCard calculateJourneyFare(OysterCard oysterCard) {
        Optional<String> swipedInValue = Optional.ofNullable(oysterCard.getJourneyDetails().getSwipedInMode());
        Optional<String> swipedOutValue = Optional.ofNullable(oysterCard.getJourneyDetails().getSwipedOutMode());

        if(swipedInValue.isPresent() && swipedOutValue.isPresent()){
            calculateActualFare(oysterCard);
        }
        return oysterCard;
    }

    private void calculateActualFare(OysterCard oysterCard) {

        Optional<Integer> lowestZone = getLowestZone(oysterCard.getJourneyDetails().getStartingPoint());
        Optional<Integer> highestZone = getHighestZone(oysterCard.getJourneyDetails().getEndPoint());

        oysterCard.getJourneyDetails().setSingleFare(getZonesTravelled(lowestZone,highestZone));
        oysterCard.setCardBalance(oysterCard.getCardBalance() - getZonesTravelled(lowestZone,highestZone));
        oysterCard.getJourneyDetails().setJourneyStatus(Constants.COMPLETE);
    }


    private double getZonesTravelled(Optional<Integer> lowestZone, Optional<Integer> highestZone) {

        boolean zone1Present = isZoneOnePresent(lowestZone);

        if(!zone1Present) {
            zone1Present = isZoneOnePresent(highestZone);
        }

        if(highestZone.get() - lowestZone.get() == 0 && zone1Present){
            return FareList.ANYWHERE_IN_ZONE_1.getSingleFare();
        }else if(highestZone.get() - lowestZone.get() == 0 && !zone1Present){
            return FareList.ANY_ONE_ZONE_OUTSIDE_ZONE_1.getSingleFare();
        }else if(highestZone.get() - lowestZone.get() == 1 && zone1Present){
            return FareList.ANY_TWO_ZONES_INCLUDING_ZONE_1.getSingleFare();
        }else if(highestZone.get() - lowestZone.get() == 1 && !zone1Present){
            return FareList.ANY_TWO_ZONES_EXCLUDING_ZONE_1.getSingleFare();
        }else if(highestZone.get() - lowestZone.get() == 2){
            return FareList.ANY_THREE_ZONES.getSingleFare();
        }

        return 0;
    }

    private boolean isZoneOnePresent(Optional<Integer> zone) {
      return zone.filter( x-> x == 1).isPresent();
    }


    public void printReport() {
        listOfAllJourneys.forEach(System.out::println);
    }
}
