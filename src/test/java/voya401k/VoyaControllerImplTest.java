package voya401k;
import org.junit.Assert;
import org.junit.Test;

public class VoyaControllerImplTest {

    @Test
    public void testGetResponse() {
        //TODO: Launch requests do not have intent types, need to find resolution for this issue.
        VoyaControllerImpl controller = new VoyaControllerImpl();
        VoyaRequestImpl req1 = new VoyaRequestImpl(0,0,VoyaRequestType.LAUNCH_REQUEST, "en-US", null);
        VoyaRequestImpl req2 = new VoyaRequestImpl(0, 1111, VoyaRequestType.INTENT_REQUEST, "en-US", VoyaIntentType.SUMMARY);
        VoyaRequestImpl req3 = new VoyaRequestImpl(1, 1111, VoyaRequestType.INTENT_REQUEST, "en-US", VoyaIntentType.SUMMARY);
        VoyaRequestImpl req4 = new VoyaRequestImpl(1, 1111, VoyaRequestType.INTENT_REQUEST, "en-US", VoyaIntentType.YES);


//        Assert.assertEquals("Hi, Welcome to Voya 401K service. To get started please say your PIN",
//                controller.getResponse(req1).getSpeech());
        Assert.assertEquals(false, controller.getResponse(req1).getShouldSessionEnd());

        Assert.assertEquals("Sure Srini, As of 7-25-2018, your account balance is 50000. Your rate of return for the past 12 months is 12 percent, which is above the average portfolio benchmark for this period. Nice job making your money work for you! It looks like you are currently projected to have enough money to retire at age 67. Would you like to hear suggestions to be able retire a little sooner?",
                controller.getResponse(req2).getSpeech());
        Assert.assertEquals(false, controller.getResponse(req2).getShouldSessionEnd());

        Assert.assertEquals("Sure Srini, As of 7-25-2018, your account balance is 50000. Your rate of return for the past 12 months is 12 percent, which is above the average portfolio benchmark for this period. Nice job making your money work for you! It looks like you are currently projected to have enough money to retire at age 67. Would you like to hear suggestions to be able retire a little sooner?",
                controller.getResponse(req3).getSpeech());
        Assert.assertEquals(false, controller.getResponse(req3).getShouldSessionEnd());
        Assert.assertEquals("You are doing a great job of saving 4 percent from your pay. if you increase your savings rate to 6 percent you could retire at age 65. Would you like to increase your savings rate by 2 percent now?",
                controller.getResponse(req4).getSpeech());
        Assert.assertEquals(false, controller.getResponse(req4).getShouldSessionEnd());

        System.out.println(controller.getResponse(req1).getSpeech());


        System.out.println(controller.getResponse(req2).getSpeech());
        System.out.println(controller.getResponse(req3).getSpeech());
        System.out.println(controller.getResponse(req4).getSpeech());

    }
}
