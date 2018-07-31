package voya401k;



/**
 * Implementation of the VoyaController class. This class uses the method getResponse to appropriately answer every
 * request.
 */
public class VoyaControllerImpl implements VoyaController{

    /**
     * Returns the appropriate response for the given request. Right now, it just returns one response, but optimally
     * in the future it would have several responses to choose from.
     * @param request
     * @return
     */
    @Override
    public VoyaResponse getResponse(VoyaRequest request) {
        String speech;
        String reprompt;
        switch(request.getRequestType()) {
            case LAUNCH_REQUEST:

                speech = "Hi, Welcome to Voya 401K service. To get started please say your PIN";
                reprompt = "To get started, please say the PIN you set up to enable the skill";
                if(! request.getLocale().startsWith("en")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                    reprompt = new VoyaResponseTextTranslator().translate(reprompt, request.getLocale());
                }
                return new VoyaResponseImpl(0, 0, speech,reprompt, false);
            case INTENT_REQUEST:
                return this.handleIntentRequest(request);
            case SESSION_END_REQUEST:
                speech = "Have a nice day!";
                if(! request.getLocale().startsWith("en")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                }
                return new VoyaResponseImpl(0, 0, "Have a nice day!", "", true);

            case STOP_REQUEST:
                return new VoyaResponseImpl(0, 0, " ", "", true);
            case HELP_REQUEST:
                speech = "Welcome to Voya 401k service, you can ask me different things, like Please tell me how my account is doing?";
                if(! request.getLocale().startsWith("en")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                }
                return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPIN(), speech, "", false);
            case CANCEL_REQUEST:
                speech = "Ok, thank you for using Voya 401k service, have a nice day!";
                if(! request.getLocale().startsWith("en")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                }
                return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPIN(), speech, "", false);
            default:
                //TODO: Make this make more sense
                throw new IllegalArgumentException("Request not recognized");
        }
    }

    private VoyaResponse handleIntentRequest(VoyaRequest request) {
        switch(request.getIntent()) {
            case NO:
                return this.handleNo(request);
            case YES:
                return this.handleYes(request);
            case QUIT:
                return this.handleQuit(request);
            case PIN:
                return this.handlePin(request);
            case SUMMARY:
                return this.handleSummary(request);
        }
        //TODO: SEEMS LIKE A WAY TO RESOLVE POTENTIAL CASES THAT SLIP THROUGH
        return this.handleQuit(request);
    }

    private VoyaResponse handleYes(VoyaRequest request) {
        String speech;
        String reprompt;
        //TODO: HERE WE ASSUME THAT A PIN OF 0 MEANS NO PIN, NOT SURE HOW THIS WILL ACTUALLY BE ENFORCED
        if(request.getVoyaPIN() == 0) {
            speech = "OK, go ahead and say your PIN";
            if(!request.getLocale().startsWith("en-US")) {
                speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
            }
            return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPIN(), speech, speech, false);
        }
        else {
            if(request.getQuestionNo() == 1) {
                VoyaUserDataObject userData = this.getUserData(request.getVoyaPIN());
                speech = "You are doing a great job of saving " + userData.getSavingsRate() + " from your pay." +
                        " if you increase your savings rate to " + userData.getSavingsRateIncrease() +
                        " you could retire at age " + userData.getLoweredRetirementAge() + ". Would you like to"
                        +" increase your savings rate by "+ userData.getSavingsRateIncrease() + " now?";
                reprompt = "Would you like to increase your savings rate by " + userData.getSavingsRateIncrease()
                        + " now?";

                if(! request.getLocale().startsWith("en-US")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                    reprompt = new VoyaResponseTextTranslator().translate(reprompt, request.getLocale());
                }
                return new VoyaResponseImpl(2, request.getVoyaPIN(),speech, reprompt, false);
            }
            else if(request.getQuestionNo() == 2 || request.getQuestionNo() == 3) {
                speech = "OK, great. I\'ve done that for you. Congratulations, your future self will thank you!";
                if(! request.getLocale().startsWith("en-US")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                }
                return new VoyaResponseImpl(0, request.getVoyaPIN(), speech, "", true);
            }
            else {
                speech = "I'm sorry?";
                if (! request.getLocale().startsWith("en-US")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                }
                return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPIN(),
                        speech, speech, false);
            }

        }
    }

    private VoyaResponse handleNo(VoyaRequest request) {
        String speech;
        String reprompt;
        if(request.getVoyaPIN() == 0) {
            speech = "Ok, have a nice day!";
            if (! request.getLocale().startsWith("en")) {
                speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
            }
            return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPIN(), speech, "", true);
        }
        else {

            VoyaUserDataObject userData = this.getUserData(request.getVoyaPIN());
            speech = "Ok " + userData.getFirstName() +
                    "!, I understand thank you for using Voya 401k service, have a nice day!";
            if(request.getQuestionNo() == 1) {
                if (!request.getLocale().startsWith("en")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                }
                return new VoyaResponseImpl(1, request.getVoyaPIN(), speech, speech, true);
            }
            else if(request.getQuestionNo() == 2) {
                speech = "Ok, I understand. Would you want to save more in the future? "
                        + "I can sign you up to save 1% more a year from now?";
                reprompt = "Would you like to sign up to save 1% a year from now?";
                if (!request.getLocale().startsWith("en")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                    reprompt = new VoyaResponseTextTranslator().translate(reprompt, request.getLocale());
                }
                return new VoyaResponseImpl(3, request.getVoyaPIN(), speech, reprompt, false);
            }
            else {
                speech = "Ok, " + userData.getFirstName() + ",thank you for using Voya 401k service, have a good day!";
                if(!request.getLocale().startsWith("en")) {
                    speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
                }
                return new VoyaResponseImpl(1, request.getVoyaPIN(), speech,"", true);


            }

        }
    }

    private VoyaResponse handlePin(VoyaRequest request) {
        String speech;
        try{
            VoyaUserDataObject userData = getUserData(request.getVoyaPIN());
            //This is the point at which an IllegalArgumentException is thrown if the PIN is invalid.

            speech = "Hi " + userData.getFirstName() + ". How can I help you with your " + userData.getPlanName()
                    + "today?";

            if(!request.getLocale().startsWith("en")) {
                speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
            }
            return new VoyaResponseImpl(0, request.getVoyaPIN(), speech, speech, false);

        } catch(IllegalArgumentException e) {
            speech = "Sorry, that's not a valid pin";
            if(request.getLocale().equals("en-US")) {
                speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
            }
            //TODO: Figure out if session ends with an invalid pin.
            return new VoyaResponseImpl(0, 0, speech, speech, false);

        }
    }

    private  VoyaResponse handleQuit(VoyaRequest request) {
        String speech = "Ok, have a nice day!";
        if (!request.getLocale().startsWith("en-US")) {
            speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
        }
        return new VoyaResponseImpl(0, 0, speech,null,false);
    }

    private VoyaResponse handleSummary(VoyaRequest request) {

        VoyaUserDataObject userData = this.getUserData(request.getVoyaPIN());
        //TODO: Find some way to get the date
        String dateVal = "";

        String speech = "Sure "
                + userData.getFirstName() + ", As of " + dateVal + ", your account balance is "
                + userData.getAccountBalance() + ". Your rate of return for the past 12 months is "
                + userData.getRateOfReturn() + ", which is above the average portfolio benchmark for this period."
                + " Nice job making your money work for you! It looks like you are currently projected to have "
                + "enough money to retire at age "
                + userData.getProjectedRetirementAge()
                + ". Would you like to hear suggestions to be able retire a little sooner?";
        String reprompt = "\"would you like to hear suggestions to be able to retire a little sooner?\"";

        if(! request.getLocale().startsWith("en")) {
            //TODO: Maybe enforce proper locale strings?
            speech = new VoyaResponseTextTranslator().translate(speech, request.getLocale());
            reprompt = new VoyaResponseTextTranslator().translate(reprompt, request.getLocale());
        }
        return new VoyaResponseImpl(1, request.getVoyaPIN(), speech, reprompt, false);
    }

    private VoyaUserDataObject getUserData(int pin) throws IllegalArgumentException {
        //TODO: Comlete Implementation, this is dummy implementation for testing
        if(pin == 0) {
            throw new IllegalArgumentException("Not a valid pin!");
        }
        return new VoyaUserDataObjectImpl("Srini", "Kunkalaguntla", 50000, "7-25-2018",
                .12, .04);
    }
}
