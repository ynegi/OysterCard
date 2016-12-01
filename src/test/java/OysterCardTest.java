import org.junit.Before;
import org.junit.Test;
import oyster.app.OysterCardApp;
import oyster.model.JourneyDetails;
import oyster.model.OysterCard;
import oyster.util.CardResource;
import oyster.util.Constants;
import oyster.util.SwipedMode;
import oyster.util.TravelMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class OysterCardTest {

    Map<String, List<Integer>> zonesStations = new HashMap<>();
    OysterCardApp  oysterCardApp = new OysterCardApp();

    @Before
    public void setUp(){

        zonesStations = CardResource.getMapOfStationAndZones();
    }

   @Test
    public void journeyCoverThreeLegsIncludingTubeAndBus(){

        //Journey by Tube touched in at Holborn
        OysterCard oysterCard = populateOysterCard("1","Holborn",TravelMode.TUBE.toString(),Constants.INCOMPLETE);
        OysterCard oysterCardAfterJourneyLeg1 = oysterCardApp.startJourney(oysterCardApp.topUpOysterCard(oysterCard));

        //Journey by Tube touched out at Earl’s Court
        OysterCard journeyLeg1Completed = oysterCardApp.endJourney(updateOysterCard(oysterCardAfterJourneyLeg1, "Earl’s Court"));
        assertNotNull(journeyLeg1Completed);


        //Journey by Bus 328 bus from Earl’s Court to Chelsea
        OysterCard oysterCardBus = populateOysterCard("1","Earl’s Court",TravelMode.BUS.toString(),Constants.COMPLETE);
        OysterCard oysterCardAfterJourneyLeg2 = oysterCardApp.startJourney(oysterCardBus);
        assertNotNull(oysterCardAfterJourneyLeg2);


        //Journey by Tube touched in at Earl’s court
        OysterCard oysterCard2 = populateOysterCard("1","Earl’s Court",TravelMode.TUBE.toString(),Constants.INCOMPLETE);
        OysterCard oysterCardAfterJourneyLeg3 = oysterCardApp.startJourney(oysterCard2);

        //Journey by Tube touched out at Hammersmith
        oysterCardAfterJourneyLeg3.getJourneyDetails().setEndPoint("Hammersmith");
        oysterCardAfterJourneyLeg3.getJourneyDetails().setSwipedOutMode(SwipedMode.OUT.toString());
        OysterCard journeyLeg3Completed = oysterCardApp.endJourney(updateOysterCard(oysterCardAfterJourneyLeg3, "Earl’s Court"));
        assertNotNull(journeyLeg3Completed);

        oysterCardApp.printReport();
    }

    private OysterCard populateOysterCard(String oysterCardNumber, String startingPoint, String travelMode, String JourneyStatus) {
        OysterCard oysterCard =  new OysterCard();
        oysterCard.setOysterCardNumber(oysterCardNumber);
        JourneyDetails journeyDetails = new JourneyDetails();
        journeyDetails.setStartingPoint(startingPoint);
        journeyDetails.setTravelMode(travelMode);
        journeyDetails.setSwipedInMode(SwipedMode.IN.toString());
        journeyDetails.setJourneyStatus(JourneyStatus);
        oysterCard.setJourneyDetails(journeyDetails);
        return oysterCard;
    }

    private OysterCard updateOysterCard(OysterCard oysterCard, String endPoint) {
        oysterCard.getJourneyDetails().setEndPoint(endPoint);
        oysterCard.getJourneyDetails().setSwipedOutMode(SwipedMode.OUT.toString());
        return oysterCard;
    }

}
