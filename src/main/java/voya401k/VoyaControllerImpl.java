package voya401k;

import com.sun.org.apache.regexp.internal.RE;

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
        switch(request.getRequestType()) {
            case LAUNCH_REQUEST:
                return this.handleLaunchRequest(request);
                break;
            case INTENT_REQUEST:
                return this.handleIntentRequest(request);
                break;
            case SESSION_END_REQUEST:
            case STOP_REQUEST:
            case HELP_REQUEST:
            case CANCEL_REQUEST:
        }
    }

    private VoyaResponse handleLaunchRequest(VoyaRequest request) {

    }

    private VoyaResponse handleIntentRequest(VoyaRequest request) {
        switch(request.getIntent()) {
            case NO:
                this.handleNo(request);
            case YES:
                this.handleYes(request);
            case QUIT:
                this.handleQuit(request);
            case PIN:
                this.handlePin(request);
            case SUMMARY:
                this.handleSummary(request);
        }
    }

    private VoyaResponse handleYes(VoyaRequest request) {
        //TODO: HERE WE ASSUME THAT A PIN OF 0 MEANS NO PIN, NOT SURE HOW THIS WILL ACTUALLY BE ENFORCED
        if(request.getVoyaPin() == 0) {
            if(request.getLocale().equals("en-US")) {
                String speech = "OK, go ahead and say your PIN";
                return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(), speech, speech, false);
            }
            if(request.getLocale().equals("es-ES")) {
                String speech = "OK, solo di el PIN";
                return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(), speech, speech, false);
            }
            else {
                throw new IllegalArgumentException("Unsupported Language!");
            }
        }
        else {
            if(request.getQuestionNo() == 1) {
                VoyaUserDataObject userData = this.getUserData(request.getVoyaPin());

                if(request.getLocale().equals("en-US")) {
                    String speech =                 "You are doing a great job of saving "
                            + userData.getSavingsRate() + " from your pay." +
                            " if you increase your savings rate to" + userData.getSavingsRateIncrease() +
                            " you could retire at age " + userData.getLoweredRetirementAge() + ". Would you like to"
                            +" increase your savings rate by "+ userData.getSavingsRateIncrease() + " now?";

                    return new VoyaResponseImpl(2, request.getVoyaPin(),speech, speech, false);
                }
                else if(request.getLocale().equals("es-ES")) {
                    String speech = "Está haciendo un gran trabajo ahorrando un" + userData.getSavingsRate() + "de su salario." +
                            " si aumenta su tasa de ahorro al "+ userData.getSavingsRateIncrease() +
                            ", podría jubilarse a los "+ userData.getLoweredRetirementAge() + " años" +
                            "¿Le gustaría aumentar su tasa de ahorro en un " + userData.getSavingsRateIncrease() + " ahora?";
                    return new VoyaResponseImpl(2, request.getVoyaPin(),speech, speech, false);
                }
                else{
                    throw new IllegalArgumentException("Locale not supported!");
                }

            }
            else if(request.getQuestionNo() == 2 || request.getQuestionNo() == 3) {
                if(request.getLocale().equals("en-US")) {
                    String speech = "OK, great. I\'ve done that for you. Congratulations, " +
                            "your future self will thank you!";

                    return new VoyaResponseImpl(0, request.getVoyaPin(), speech, speech, true);
                }
                else if(request.getLocale().equals("es-ES")){
                    String speech = "Vale genial. He hecho eso por ti. Felicidades, tu ser futuro te lo agradecerá!";
                    return new VoyaResponseImpl(0, request.getVoyaPin(), speech, speech, true);

                }
                else {
                    throw new IllegalArgumentException("Locale not supported!");
                }
            }
            else {
                if (request.getLocale().equals("en-US")) {
                    return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(),
                            "I'm sorry?", "I'm Sorry?", false);
                }
                else if (request.getLocale().equals("es-ES")) {
                    return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(),
                            "I'm sorry?", "I'm Sorry?", false);
                }
                else {
                    throw new IllegalArgumentException("Locale not supported!");
                }
            }

        }
    }

    private VoyaResponse handleNo(VoyaRequest request) {
        if(request.getVoyaPin() == 0) {
            if (request.getLocale().equals("en-US")) {
                String speech = "OK, have a nice day!";
                return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(), speech, speech, true);
            }
            if (request.getLocale().equals("es-ES")) {
                String speech = "¡Bien tenga un buen día!";
                return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(), speech, speech, true);
            }
            else {
                throw new IllegalArgumentException("Unsupported Locale!");
            }
        }
        else {

            VoyaUserDataObject userData = this.getUserData(request.getVoyaPin());

            if(request.getQuestionNo() == 1) {
                if (request.getLocale().equals("en-US")) {
                    String speech = "Ok " + userData.getFirstName() +
                            "!, I understand thank you for using Voya 401k service, have a nice day!";
                    return new VoyaResponseImpl(1, request.getVoyaPin(), speech, speech, true);
                }
                else if (request.getLocale().equals("es-ES")) {

                }
                else {
                    throw new IllegalArgumentException("Unsupported Locale!");
                }

            }
            else if(request.getQuestionNo() == 2) {
                if (request.getLocale().equals("en-US")) {
                    String speech = "Ok, I understand. Would you want to save more in the future? "
                            + "I can sign you up to save 1% more a year from now?";
                    return new VoyaResponseImpl(3, request.getVoyaPin() speech, speech, false);
                }
                else if (request.getLocale().equals("es-ES")) {

                }
                else {
                    throw new IllegalArgumentException("Unsupported Locale!");
                }
            }
            else {
                if(request.getLocale().equals("es-ES")) {

                }
            }

        }
    }

    private VoyaResponse handlePin(VoyaRequest request) {

    }

    private  VoyaResponse handleQuit(VoyaRequest request) {

    }

    private VoyaResponse handleSummary(VoyaRequest request) {

    }

    private VoyaUserDataObject getUserData(int pin) {

    }
}
