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
        switch(request.getRequestType()) {
            case LAUNCH_REQUEST:
                return this.handleLaunchRequest(request);
            case INTENT_REQUEST:
                return this.handleIntentRequest(request);
            case SESSION_END_REQUEST:
                if(request.getLocale() =="en-US") {
                    return new VoyaResponseImpl(0, 0, "Have a nice day!", "", true);
                }
                else if(request.getLocale() == "es-ES") {
                    return new VoyaResponseImpl(0, 0, "bien tenga un buen día", "", true);
                }
                else {
                    throw new IllegalArgumentException("Locale not supported!");
                }
            case STOP_REQUEST:
                if(request.getLocale() =="en-US") {
                    return new VoyaResponseImpl(0, 0, " ", "", true);
                }
                else if(request.getLocale() == "es-ES") {
                    return new VoyaResponseImpl(0, 0, "", "", true);
                }
                else {
                    throw new IllegalArgumentException("Locale not supported!");
                }
            case HELP_REQUEST:
                if(request.getLocale() =="en-US") {
                    return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(),
                            "Welcome to Voya 401k service, you can ask me different things, l" +
                                    "ike Please tell me how my account is doing?", "", false);
                }
                else if(request.getLocale() == "es-ES") {
                    return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(),
                            "Bienvenido al servicio 401k de Voya, puede preguntarme cosas diferentes, como Por " +
                                    "favor, dígame cómo va mi cuenta.", "", false);
                }
                else {
                    throw new IllegalArgumentException("Locale not supported!");
                }
            case CANCEL_REQUEST:
                if(request.getLocale() =="en-US") {
                    return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(),
                            "Ok, thank you for using Voya 401k service, have a nice day!", "", false);
                }
                else if(request.getLocale() == "es-ES") {
                    return new VoyaResponseImpl(request.getQuestionNo(), request.getVoyaPin(),
                            "Ok, gracias por usar el servicio Voya 401k, ¡ten un buen día!", "", false);
                }
                else {
                    throw new IllegalArgumentException("Locale not supported!");
                }
            default:
                //TODO: Make this make more sense
                throw new IllegalArgumentException("Request not recognized");
        }
    }

    private VoyaResponse handleLaunchRequest(VoyaRequest request) {
        if(request.getLocale() == "en-US") {
            return new VoyaResponseImpl(0, 0, "Hi, Welcome to Voya 401K service. To get " +
                    "started please say your PIN",
                    "To get started, please say the PIN you set up to enable the skill", false);
        }
        else if(request.getLocale() == "es-ES") {
            return new VoyaResponseImpl(0, 0, "Bienvenido al servicio Voya 401k, " +
                    "para comenzar, ¿dices el PIN de cuatro dígitos que configuraste para habilitar la habilidad?",
                    "para comenzar, por favor diga su pin", false);
        }
        else {
            throw new IllegalArgumentException("Locale not supported");
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
                    String speech = "Ok, "
                            + userData.getFirstName()
                            + ", entiendo gracias por usar el servicio Voya 401k, ¡que tengas un buen día!";
                    return new VoyaResponseImpl(1, request.getVoyaPin(), speech,speech, true);
                }
                else {
                    throw new IllegalArgumentException("Unsupported Locale!");
                }

            }
            else if(request.getQuestionNo() == 2) {
                if (request.getLocale().equals("en-US")) {
                    String speech = "Ok, I understand. Would you want to save more in the future? "
                            + "I can sign you up to save 1% more a year from now?";
                    return new VoyaResponseImpl(3, request.getVoyaPin(), speech, speech, false);
                }
                else if (request.getLocale().equals("es-ES")) {
                    String speech = "Vale, entiendo. ¿Te gustaría ahorrar más en el futuro? ¿Puedo inscribirme para ahorrar un 1% más por año a partir de ahora?";
                    return new VoyaResponseImpl(3, request.getVoyaPin(), speech, speech, false);
                }
                else {
                    throw new IllegalArgumentException("Unsupported Locale!");
                }
            }
            else {
                if(request.getLocale().equals("en-US")) {
                    String speech = "Ok, "
                            + userData.getFirstName()
                            + ",thank you for using Voya 401k service, have a good day!";
                    return new VoyaResponseImpl(1, request.getVoyaPin(), speech,speech, true);
                }
                else if(request.getLocale().equals("es-ES")) {
                    String speech = "Ok, "
                            + userData.getFirstName()
                            + ", entiendo gracias por usar el servicio Voya 401k, ¡que tengas un buen día!";
                    return new VoyaResponseImpl(1, request.getVoyaPin(), speech,speech, true);
                }
                else {
                    throw new IllegalArgumentException("Locale not supported");
                }

            }

        }
    }

    private VoyaResponse handlePin(VoyaRequest request) {
        try{
            VoyaUserDataObject userData = getUserData(request.getVoyaPin());

            if(request.getLocale() == "en-US") {
                String speech = "Hi " + userData.getFirstName() + ". How can I help you with your " + userData.getPlanName()
                        + "today?";
                return new VoyaResponseImpl(1, request.getVoyaPin(), speech, speech, false);
            }
            else if(request.getLocale() == "es-ES") {

                String speech = "Hola " + userData.getFirstName()
                        + "! h¿cómo puede ayudarte con tu " + userData.getPlanName() + " hoy?";

                return new VoyaResponseImpl(1, request.getVoyaPin(), speech, speech, false);
            }
            else {
                throw new IllegalArgumentException("Unsupported language");
            }
        } catch(IllegalArgumentException e) {
            if(request.getLocale() == "en-US") {
                String speech = "Sorry, that's not a valid pin";
                //TODO: Figure out if session ends with an invalid pin.
                return new VoyaResponseImpl(0, 0, speech, speech, false);
            }
            else if (request.getLocale() == "es-ES") {
                String speech = "lo siento, ese no es un pin válido";
                return new VoyaResponseImpl(0, 0, speech, speech, false);
            }
            else {
                throw new IllegalArgumentException("Unsupported locale");
            }
        }
    }

    private  VoyaResponse handleQuit(VoyaRequest request) {
        if (request.getLocale() == "en-US") {
            return new VoyaResponseImpl(0, 0,
                    "OK, have a nice day!",
                    null,
                    false);
        }
        else if (request.getLocale() == "es-ES") {
            return new VoyaResponseImpl(0, 0,
                    "¡Bien tenga un buen día!",null,true);
        }
        else {
            throw new IllegalArgumentException("Locale not supported");
        }
    }

    private VoyaResponse handleSummary(VoyaRequest request) {

        VoyaUserDataObject userData = this.getUserData(request.getVoyaPin());
        //TODO: Find some way to get the date
        String dateVal = "";
        if (request.getLocale() == "en-US") {

            String speech = "Sure "
                    + userData.getFirstName() + ", As of "+ dateVal +", your account balance is "
                    + userData.getAccountBalance() + ". Your rate of return for the past 12 months is "
                    + userData.getRateOfReturn() + ", which is above the average portfolio benchmark for this period."
                    + " Nice job making your money work for you! It looks like you are currently projected to have "
                    + "enough money to retire at age "
                    + userData.getProjectedRetirementAge()
                    + ". Would you like to hear suggestions to be able retire a little sooner?";

            return new VoyaResponseImpl(1, request.getVoyaPin(), speech,
                    "would you like to hear suggestions to be able to retire a little sooner?",
                    false);
        }
        else if(request.getLocale() == "es-ES") {
            String speech = "Por supuesto "+ userData.getFirstName() + ", a partir del " + dateVal
                    + " el saldo de su cuenta es de " + userData.getAccountBalance()
                    + ". Su tasa de rendimiento de los últimos 12 meses es del "
                    + userData.getRateOfReturn() + ", que está por encima del punto de referencia de cartera promedio " +
                    "para este período. Parece que en la actualidad se prevé que tendrá dinero suficiente " +
                    "para jubilarse a los "
                    + userData.getProjectedRetirementAge()
                    + " años. ¿Le gustaría recibir sugerencias para poder jubilarse un poco antes?";
            return new VoyaResponseImpl(1, request.getVoyaPin(), speech,
                    "¿Le gustaría escuchar sugerencias para poder retirarse un poco antes?",
                    false);
        }
        else {
            throw new IllegalArgumentException("Unsupported locale");
        }
    }

    private VoyaUserDataObject getUserData(int pin) throws IllegalArgumentException {
        //TODO: Comlete Implementation, this is dummy implementation for testing
        return new VoyaUserDataObjectImpl("Srini", "Kunkalaguntla", 50000, "7-25-2018",
                .12, .04);
    }
}
