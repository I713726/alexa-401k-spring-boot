package voya401k;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementation of the VoyaController class. This class uses the method getResponse to appropriately answer every
 * request.
 */
public class VoyaControllerImpl implements VoyaController{

    VoyaResponseTranslator translator;
    ArrayList<ArrayList<String>> responses;
    ArrayList<ArrayList<String>> reprompts;

    VoyaControllerImpl() {
        this.initializeResponses();
        this.intializeRepropmts();
        this.translator = new MicrosoftTranslator();
    }

    @Override
    public VoyaResponse getResponse(VoyaRequest request) {
        String speech = "";
        String reprompt = "";
        int questionNumber = 0;
        int userPin = 0;
        boolean shouldSessionEnd = false;
        switch(request.getRequestType()) {
            case LAUNCH_REQUEST:
                speech = this.getResponse(0);
                reprompt = this.getReprompt(0);
                questionNumber = 0;
                userPin = 0;
                shouldSessionEnd = false;
                break;
            case INTENT_REQUEST:
                return this.handleIntentRequest(request);
            case SESSION_END_REQUEST:
                speech = "Have a nice day!";
                questionNumber = 0;
                userPin = 0;
                shouldSessionEnd = true;
                break;
            case STOP_REQUEST:
                questionNumber = 0;
                userPin = 0;
                speech = "";
                reprompt = "";
                break;
            case HELP_REQUEST:
                speech = "Welcome to Voya 401k service, you can ask me different things, like Please tell me how my account is doing?";
                reprompt = "";
                questionNumber = request.getQuestionNo();
                userPin = request.getVoyaPIN();
                shouldSessionEnd  = false;
                break;
            case CANCEL_REQUEST:
                speech = "Ok, thank you for using Voya 401k service, have a nice day!";
                reprompt = "";
                questionNumber = request.getQuestionNo();
                userPin = request.getVoyaPIN();
                shouldSessionEnd = true;
                break;
            default:
            return new VoyaResponseImpl(0, 0,
                    "The skill could not understand your request", "", true);
        }
        if(! request.getLocale().startsWith("en")) {
            speech = translator.translate(speech, request.getLocale());
            reprompt = translator.translate(reprompt, request.getLocale());
        }
        return new VoyaResponseImpl(questionNumber, userPin, speech, reprompt, shouldSessionEnd);
    }

    private VoyaResponse handleIntentRequest(VoyaRequest request) {
        String speech = "";
        String reprompt = "";
        int questionNo = 0;
        int userPin = 0;
        boolean shouldSessionEnd = false;

        if(request.getVoyaPIN() == 0) {
            if(request.getIntent() == VoyaIntentType.NO) {
                speech = "Ok, have a nice day!";
                questionNo = request.getQuestionNo();
                userPin = request.getVoyaPIN();
                shouldSessionEnd = true;
                reprompt = "";
            }
            else {
                speech = "OK, go ahead and say your PIN";
                questionNo = 0;
                userPin = request.getVoyaPIN();
                shouldSessionEnd = false;
                reprompt = "";
            }
        }
        else {
            VoyaUserDataObject userData;
            switch(request.getIntent()) {
                case NO:
                    userData = this.getUserData(request.getVoyaPIN());
                    speech = "Ok " + userData.getFirstName() +
                            "!, I understand thank you for using Voya 401k service, have a nice day!";
                    if(request.getQuestionNo() == 1) {
                        questionNo = 1;
                        userPin = request.getVoyaPIN();
                        shouldSessionEnd = true;
                        reprompt = "";
                    }
                    else if(request.getQuestionNo() == 2) {
                        speech = "Ok, I understand. Would you want to save more in the future? "
                                + "I can sign you up to save 1% more a year from now?";
                        reprompt = "Would you like to sign up to save 1% a year from now?";
                        questionNo = 3;
                        userPin = request.getVoyaPIN();
                        shouldSessionEnd = false;
                    }
                    else {
                        speech = "Ok, " + userData.getFirstName() + ",thank you for using Voya 401k service, have a good day!";
                        reprompt = "";
                        questionNo = 1;
                        userPin = request.getVoyaPIN();
                        shouldSessionEnd = true;
                    }
                    break;
                case YES:
                    if(request.getVoyaPIN() == 0) {
                        speech = "OK, go ahead and say your PIN";
                    }
                    else {
                        userData = this.getUserData(request.getVoyaPIN());
                        if(request.getQuestionNo() == 1) {
                            speech = "You are doing a great job of saving " + (int) (userData.getSavingsRate() * 100) + " percent from your pay." +
                                    " if you increase your savings rate to " + (int)(100 * (userData.getSavingsRateIncrease() + userData.getSavingsRate())) +
                                    " percent you could retire at age " +userData.getLoweredRetirementAge() + ". Would you like to"
                                    +" increase your savings rate by "+ (int) (100 * userData.getSavingsRateIncrease()) + " percent now?";
                            reprompt = "Would you like to increase your savings rate by " + (int)(userData.getSavingsRateIncrease() * 100)
                                    + "percent now?";
                            questionNo = 2;
                            userPin = request.getVoyaPIN();
                            shouldSessionEnd = false;
                        }
                        else if(request.getQuestionNo() == 2 || request.getQuestionNo() == 3) {
                            speech = "OK, great. I\'ve done that for you. Congratulations, your future self will thank you!";
                            questionNo = 0;
                            reprompt = "";
                            userPin = request.getVoyaPIN();
                            shouldSessionEnd = true;
                        }
                        else {
                            speech = "I'm sorry?";
                            questionNo = request.getQuestionNo();
                            userPin = request.getVoyaPIN();
                            shouldSessionEnd = false;
                            reprompt = "";
                        }

                    }
                    break;
                case QUIT:
                    speech = "OK, have a nice day!";
                    break;
                case PIN:
                    try{
                        userData = this.getUserData(request.getVoyaPIN());
                        //This is the point at which an IllegalArgumentException is thrown if the PIN is invalid.

                        speech = "Hi " + userData.getFirstName() + ". How can I help you with your " + userData.getPlanName()
                                + "today?";
                        questionNo = 1;
                        reprompt = speech;
                        userPin = request.getVoyaPIN();
                        shouldSessionEnd = false;

                    } catch(IllegalArgumentException e) {
                        speech = "Sorry, that's not a valid pin";
                        reprompt = speech;
                        shouldSessionEnd = false;
                        questionNo = 0;
                        userPin = 0;
                    }
                    break;
                case SUMMARY:
                    userData = this.getUserData(request.getVoyaPIN());

                    speech = "Sure "
                            + userData.getFirstName() + ", As of " + userData.getLastUpdatedDate() + ", your account balance is "
                            + userData.getAccountBalance() + ". Your rate of return for the past 12 months is "
                            + (int)(userData.getRateOfReturn() * 100) + " percent, which is above the average portfolio benchmark for this period."
                            + " Nice job making your money work for you! It looks like you are currently projected to have "
                            + "enough money to retire at age "
                            + userData.getProjectedRetirementAge()
                            + ". Would you like to hear suggestions to be able retire a little sooner?";
                    reprompt = "\"would you like to hear suggestions to be able to retire a little sooner?\"";
                    questionNo = 1;
                    userPin = request.getVoyaPIN();
                    shouldSessionEnd = false;
                    break;
                case ACCOUNTACTIVITY:
                    userData = this.getUserData(request.getVoyaPIN());
                    DateFormat alexaFormat = new SimpleDateFormat("MM-dd-yyyy");
                    List<String> recentTransactions = userData.getRecentTransactions(request.getStartDate(),
                            request.getEndDate());
                    speech += "Here is your account activity from " + alexaFormat.format(request.getStartDate()) +
                            " to " + alexaFormat.format(request.getEndDate()) +". ";
                    for(String item: recentTransactions) {
                        speech += item +". ";
                    }
                    questionNo = 1;
                    userPin = request.getVoyaPIN();
                    shouldSessionEnd = false;
                    break;
                case BALANCE:
                    userData = this.getUserData(request.getVoyaPIN());

                    speech = "Your account balance is currently" + userData.getAccountBalance() +
                            ". Would you like to hear suggestions to retire sooner?";
                    questionNo = 1;
                    userPin = request.getVoyaPIN();
                    shouldSessionEnd = false;
                    break;
                case SAVINGSRATE:
                    userData = this.getUserData(request.getVoyaPIN());
                    speech = "Sure, your current savings rate is " + userData.getSavingsRate() + ". If you like, I can increase your savings rate by 2% to allow you to retire sooner";
                    questionNo = 2;
                    shouldSessionEnd =false;
                    userPin = request.getVoyaPIN();
                    break;
                case RATEOFRETURN:
                    userData = this.getUserData(request.getVoyaPIN());
                    speech = "Your rate of return for the past 12 months is " + userData.getRateOfReturn() + ". Would you like to hear suggestions to retire sooner?";
                    questionNo = 1;
                    shouldSessionEnd = false;
                    userPin = request.getVoyaPIN();
                    break;
                case RETIREMENTAGE:
                    userData = this.getUserData(request.getVoyaPIN());
                    speech = "OK " + userData.getFirstName() + " you are projected to retire at age " +
                            userData.getProjectedRetirementAge() + ". If you like, I can give you suggestions to retire at age "
                            + userData.getLoweredRetirementAge();
                    questionNo = 1;
                    shouldSessionEnd = false;
                    userPin = request.getVoyaPIN();
            }
        }



        if(! request.getLocale().startsWith("en")) {
            speech = translator.translate(speech, request.getLocale());
            reprompt = translator.translate(speech, request.getLocale());
        }
        return new VoyaResponseImpl(questionNo, userPin, speech, reprompt, shouldSessionEnd);
    }

    private VoyaUserDataObject getUserData(int pin) throws IllegalArgumentException {
        //TODO: Comlete Implementation, this is dummy implementation for testing
        if(pin == 0) {
            throw new IllegalArgumentException("Not a valid pin!");
        }
        AccountTransaction transaction1 = new AccountTransaction(new Date(2018, 8, 25), "Deposit of $10");
        AccountTransaction transaction2 = new AccountTransaction(new Date(2018, 8, 27), "Deposit of $5");
        AccountTransaction transaction3 = new AccountTransaction(new Date(2018, 8, 29), "deposit of $1");
        List<AccountTransaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);

       VoyaUserDataObjectImpl result =  new VoyaUserDataObjectImpl("Srini", "Kunkalaguntla", 50000, "7-25-2018",
                .12, .04);
       result.setTransactions(transactionList);
       return result;
    }


    private void initializeResponses() {
        this.responses = new ArrayList<ArrayList<String>>();

        ArrayList<String> launchGreetings = new ArrayList<String>();
        launchGreetings.add("Hi, Welcome to Voya 401k service! To get started, please say your 4 digit PIN");
        launchGreetings.add("Welcome to Voya 401k service! Please say your 4 digit PIN to get started");
        this.responses.add(launchGreetings);


        ArrayList<String> goodByes = new ArrayList<>();

        goodByes.add("Have a nice day!");
        goodByes.add("Have a good day!");
        goodByes.add("Have a great day!");
        goodByes.add("OK, goodbye!");

        ArrayList<String> thankYouGoodBye = new ArrayList<>();

        ArrayList<String> IUnderstandGoodbye = new ArrayList<>();


    }

    private void intializeRepropmts() {
        this.reprompts = new ArrayList<ArrayList<String>>();

        ArrayList<String> launchReprompts = new ArrayList<String>();
        launchReprompts.add("Please say the PIN you set up when enabling the skill");
        launchReprompts.add("Please say your 4 digit PIN");

        this.reprompts.add(launchReprompts);
    }

    private String getResponse(int i) {
        return this.responses.get(i).get((int)(Math.random() * responses.get(i).size()));
    }

    private String getReprompt(int i) {
        return this.reprompts.get(i).get((int)(Math.random() * reprompts.get(i).size()));
    }
}
