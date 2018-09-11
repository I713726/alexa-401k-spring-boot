package voya401k;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementation of the VoyaController class. This class uses the method getResponse to appropriately answer every
 * request.
 *
 * Question numbers:
 * 0 - PIN
 * 1 - welcome question
 * 2 - would you like to hear suggestions..
 * 3 - save more
 * 4 - save more in future
 * 5 - viewing notifications
 * 6 - responding to notifications
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
        int notificationNumber = 0;
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
                    "The skill could not understand your request", "", true, notificationNumber);
        }
        if(! request.getLocale().startsWith("en")) {
            speech = translator.translate(speech, request.getLocale());
            reprompt = translator.translate(reprompt, request.getLocale());
        }
        return new VoyaResponseImpl(questionNumber, userPin, speech, reprompt, shouldSessionEnd, notificationNumber);
    }

    private VoyaResponse handleIntentRequest(VoyaRequest request) {
        String speech = "";
        String reprompt = "";
        int questionNo = 0;
        int userPin = 0;
        boolean shouldSessionEnd = false;
        int notificationNumber = request.getNotificationNumber();

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
                    else if(request.getQuestionNo() == 6) {
                        userData.getNotifications().get(request.getNotificationNumber() - 1).sendResponse("no");
                        userPin = request.getVoyaPIN();
                        shouldSessionEnd = false;
                        reprompt = "";
                        if(notificationNumber == userData.getNotifications().size()) {
                            speech = "OK, got it. That's all the notifications! Is there anything else I can do to help?";
                            notificationNumber = 0;
                            questionNo = 1;
                        }
                        else {
                            speech = "Ok, got it! ";
                            notificationNumber ++;
                            if(userData.getNotifications().get(notificationNumber - 1).isInteractive()) {
                                speech += userData.getNotifications().get(notificationNumber - 1).getText();
                                questionNo = 6;
                            }
                            else {
                                while(! userData.getNotifications().get(notificationNumber - 1).isInteractive()) {
                                    speech += userData.getNotifications().get(notificationNumber - 1).getText() + ".\n";
                                    notificationNumber ++;
                                    if(notificationNumber > userData.getNotifications().size()) {
                                        speech += "That's all of the notifications, " +
                                                "is there anything else I can help you with?";
                                        break;
                                    }
                                }
                            }
                        }
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
                        else if (request.getQuestionNo() == 6) {
                            userData.getNotifications().get(request.getNotificationNumber() - 1).sendResponse("yes");
                            userPin = request.getVoyaPIN();
                            shouldSessionEnd = false;
                            reprompt = "";
                            if(notificationNumber == userData.getNotifications().size()) {
                                speech = "OK, got it. That's all the notifications! Is there anything else I can do to help?";
                                notificationNumber = 0;
                                questionNo = 1;
                            }
                            else {
                                speech = "Ok, got it! ";
                                notificationNumber ++;
                                if(userData.getNotifications().get(notificationNumber - 1).isInteractive()) {
                                    speech += userData.getNotifications().get(notificationNumber - 1).getText();
                                    questionNo = 6;
                                }
                                else {
                                    while(! userData.getNotifications().get(notificationNumber - 1).isInteractive()) {
                                        speech += userData.getNotifications().get(notificationNumber - 1).getText() + ".\n";
                                        notificationNumber ++;
                                        if(notificationNumber > userData.getNotifications().size()) {
                                            speech += " That's all of the notifications, " +
                                                    "is there anything else I can help you with?";
                                            break;
                                        }
                                    }
                                }
                            }
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
                                + "today? ";
                        if(userData.getNotifications().size() == 1) {
                            speech += "you have a notification";
                        }
                        else if(userData.getNotifications().size() > 1) {
                            speech += "you have " + userData.getNotifications().size() + " notifications";
                        }
                        questionNo = 1;
                        reprompt = speech;
                        userPin = request.getVoyaPIN();
                        shouldSessionEnd = false;

                    } catch(IllegalArgumentException e) {
                        e.printStackTrace();
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
                    if(recentTransactions.size() > 0) {
                        speech += "Here is your account activity from " + alexaFormat.format(request.getStartDate().getTime()) +
                                " to " + alexaFormat.format(request.getEndDate().getTime()) +". ";
                        for(String item: recentTransactions) {
                            speech += item +". ";
                        }
                    }
                    else {
                        speech += "There are no transactions between " + alexaFormat.format(request.getStartDate().getTime()) +
                                " and " + alexaFormat.format(request.getEndDate().getTime()) +". ";
                    }
                    questionNo = 1;
                    userPin = request.getVoyaPIN();
                    shouldSessionEnd = false;
                    break;
                case VIEWNOTIFICATIONS:
                    userData = this.getUserData(request.getVoyaPIN());
                    if(userData.getNotifications().size() == 0) {
                        speech = "There are no notifications";
                        questionNo = 1;
                    }
                    else {
                        speech = "OK, ";
                        questionNo = 5;
                        notificationNumber = 1;
                        speech += userData.getNotifications().get(notificationNumber - 1).getText();
                        if(userData.getNotifications().get(notificationNumber - 1).isInteractive()) {
                            questionNo = 6;
                        }
                        else {
                            while(! userData.getNotifications().get(notificationNumber - 1).isInteractive()) {
                                speech += userData.getNotifications().get(notificationNumber - 1).getText() + ".\n";
                                notificationNumber ++;
                                if(notificationNumber > userData.getNotifications().size()) {
                                    speech += " That's all of the notifications, " +
                                            "is there anything else I can help you with?";
                                }
                            }
                        }
                    }
                    userPin = request.getVoyaPIN();
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
        return new VoyaResponseImpl(questionNo, userPin, speech, reprompt, shouldSessionEnd, notificationNumber);
    }

    private VoyaUserDataObject getUserData(int pin) throws IllegalArgumentException {
        //TODO: Comlete Implementation, this is dummy implementation for testing
        if(pin == 0) {
            System.out.println("pin = 0");
            throw new IllegalArgumentException("Not a valid pin!");
        }

        List<AccountTransaction> transactionList = new ArrayList<>();
        File input = new File("transactionData.html");
        Document document;
        try {
            document = Jsoup.parse(input, "UTF-8", "");
        }
        catch(IOException e) {
            System.out.println("IO error");
            throw new IllegalArgumentException("IO error");
        }

        Element table = document.select("table").get(0);
        Elements rows = table.select("tr");

        for(int i = 1; i < rows.size(); i ++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            System.out.println("Cols size: " + cols.size());
            System.out.println("Rows size: " + rows.size());
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            try {
                GregorianCalendar calendar = new GregorianCalendar();
                //Index out of bounds exception thrown below
                calendar.setTime(format.parse(cols.get(0).text()));
                transactionList.add(new AccountTransaction(calendar,
                        cols.get(1).text(), cols.get(2).text(), Float.parseFloat(cols.get(3).text()),
                        Float.parseFloat(cols.get(4).text()), Float.parseFloat(cols.get(5).text().substring(1))
                        ));
            }
            catch(ParseException e) {
                //Not sure what to do
                System.out.println("Parse exception");
            }

        }



        VoyaNotification paperless = new VoyaNotificationImpl("We noticed you are recieving statments by mail, " +
                "would you like to switch to paperless and recieve all statements online?", true);
        VoyaNotification testNonInteractive = new VoyaNotificationImpl("Test non interactive notification", false);
        List<VoyaNotification> notifications = new ArrayList<>();
        notifications.add(paperless);
        notifications.add(testNonInteractive);

       VoyaUserDataObjectImpl result =  new VoyaUserDataObjectImpl("Srini", "Kunkalaguntla", 50000, "7-25-2018",
                .12, .04);
       result.setTransactions(transactionList);
       result.setNotifications(notifications);
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
