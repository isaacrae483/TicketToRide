package com.runninglight.tickettoride.communication;

import android.util.Log;

import com.runninglight.shared.CardColor;
import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Command;
import com.runninglight.shared.DestinationCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.Message;
import com.runninglight.shared.Player;
import com.runninglight.shared.Results;
import com.runninglight.shared.Serializer;
import com.runninglight.shared.User;

import java.util.ArrayList;

public class ServerProxy implements IServer {
    private static ServerProxy instance = null;
    private ClientCommunicator communicator = ClientCommunicator.getInstance();
    private ClientModel model = ClientModel.getInstance();

    private static final String SERVER_FACADE = "com.runninglight.server.ServerFacade";
    private static final String LOGIN_INFO = "com.runninglight.shared.LoginInfo";
    private static final String GAME_INFO = "com.runninglight.shared.GameInfo";
    private static final String USER = "com.runninglight.shared.User";
    private static final String GAME = "com.runninglight.shared.Game";
    private static final String PLAYER = "com.runninglight.shared.Player";
    private static final String DEST_CARD_ARRAY = "[Lcom.runninglight.shared.DestinationCard;";
    private static final String STRING = "java.lang.String";
    private static final String MESSAGE = "com.runninglight.shared.Message";

    public static ServerProxy getInstance(){
        if(instance == null) {
            instance = new ServerProxy();
        }
        return instance;
    }

    @Override
    public boolean register(LoginInfo loginInfo) {
        Results results = communicator.send(getRegisterCommand(loginInfo));
        if(results.isSuccess()) {
            System.out.println("Register succeeded");
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public boolean login(LoginInfo loginInfo) {
        Results results = communicator.send(getLoginCommand(loginInfo));
        if(results.isSuccess()) {
            System.out.println("Login succeeded");
            model.setCurrentUser(new User(loginInfo.getUserName(), loginInfo.getPassword()));
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public boolean createGame(GameInfo gameInfo) {
        System.out.println("creating game: " + gameInfo.getGameName());
        Results results = communicator.send(getCreateGameCommand(gameInfo));
        if(results.isSuccess()) {
            System.out.println("Game Created Successfully");
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public boolean joinGame(User user, Game game) {
        Results results = communicator.send(getJoinGameCommand(user, game));
        if(results.isSuccess()) {
            System.out.println("Game Joined Successfully");
            model.setCurrentGame(game);
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public boolean leaveGame(User user, Game game){
        Results results = communicator.send(getLeaveGameCommand(user, game));
        if(results.isSuccess()) {
            System.out.println("Game Left Successfully");
            model.setCurrentGame(null);
            return true;
        }
        else{
            System.out.println(results.getErrorInfo());
            return false;
        }
    }

    @Override
    public Game[] getGameList() {
        Results results = communicator.send(getGameListCommand());
        if (results.isSuccess()) {
            System.out.println("Game list retrieved Successfully");

            // Convert Game[] to ArrayList<Game> in a really messy and probably unnecessary way
            Game[] games = new Game[] {};
            games = (Game[]) new Serializer().deserializeObject(results.getData().toString(), games.getClass().getName());
            ArrayList<Game> gameList = new ArrayList<>();
            for (Object game : games) gameList.add((Game) game);
            model.initGamesList(gameList);

            return games;
        }
        else {
            System.out.println(results.getErrorInfo());
            return null;
        }
    }

    @Override
    public DestinationCard[] drawDestCards(String gameID, int numCards){
        Results results = communicator.send(getDrawDestCardsCommand(gameID, numCards));
        if (results.isSuccess()) {
            System.out.println("Destination cards drawn successfully");

            DestinationCard[] cards = new DestinationCard[] {};
            cards = (DestinationCard[]) new Serializer().deserializeObject(results.getData().toString(), cards.getClass().getName());
           // ArrayList<DestinationCard> cardList = new ArrayList<>();
            //for (Object card : cards) cardList.add((DestinationCard) card);
            return cards;
        }
        else {
            System.out.println(results.getErrorInfo());
            return null;
        }
    }

    @Override
    public void returnDestCards(String gameID, String playerName,
                                DestinationCard[] cardsKept, DestinationCard[] cardsToReturn){
        Results results = communicator.send(getReturnDestCardsCommand(gameID, playerName, cardsKept, cardsToReturn));
        if (results.isSuccess()) {
            System.out.println("Destination cards returned successfully");

            for(int i = 0; i < cardsToReturn.length; i++){
                System.out.println("Returned " + cardsToReturn[i]);
            }
            for(int i = 0; i < cardsKept.length; i++){
                System.out.println("Kept " + cardsKept[i]);
            }
        }
        else {
            System.out.println(results.getErrorInfo());
        }
    }

    @Override
    public boolean sendMessage(Message message, Game game)
    {
        Results results = communicator.send(getSendMessageCommand(message, game));
        if (results.isSuccess())
        {
            Log.d("TTR.ServerProxy", "Message sent successfully");
        }
        else
        {
            System.out.println(results.getErrorInfo());
            return false;
        }
        return true;
    }

    @Override
    public TrainCard drawTrainCard(Game game)
    {
        // This does not implement the actual drawTrainCard from server functionality and is a stub
        // for phase 2 testing

        return new TrainCard(CardColor.BLUE);
    }

    private Command getSendMessageCommand(Message message, Game game)
    {
        return new Command(
                SERVER_FACADE,
                "sendMessage",
                new String[] {MESSAGE, GAME},
                new Object[] {message, game} );
    }

    private Command getRegisterCommand(LoginInfo loginInfo)
    {
         return new Command(
                 SERVER_FACADE,
                 "register",
                 new String[] {LOGIN_INFO},
                new Object[] {loginInfo}) ;
    }

    private Command getLoginCommand(LoginInfo loginInfo)
    {
        return new Command(
                SERVER_FACADE,
                "login",
                new String[] {LOGIN_INFO},
                new Object[] {loginInfo} );
    }

    private Command getCreateGameCommand(GameInfo gameInfo)
    {
        return new Command(
                SERVER_FACADE,
                "createGame",
                new String[] {GAME_INFO},
                new Object[] {gameInfo} );
    }

    private Command getJoinGameCommand(User user, Game game)
    {
        return new Command(
                SERVER_FACADE,
                "joinGame",
                new String[] {USER, GAME},
                new Object[] {user, game} );
    }

    private Command getLeaveGameCommand(User user, Game game)
    {
        return new Command(
                SERVER_FACADE,
                "leaveGame",
                new String[] {USER, GAME},
                new Object[] {user, game} );
    }

    private Command getGameListCommand()
    {
        return new Command(
                SERVER_FACADE,
                "getGameList",
                new String[] {},
                new Object[] {} );
    }

    private Command getDrawDestCardsCommand(String gameID, int numCards)
    {
        return new Command(
                SERVER_FACADE,
                "drawDestCards",
                new String[] {STRING, "int"},
                new Object[] {gameID, numCards} );
    }

    private Command getReturnDestCardsCommand(String gameID, String playerName,
                                              DestinationCard[] cardsKept, DestinationCard[] cardsToReturn)
    {
        return new Command(
                SERVER_FACADE,
                "returnDestCards",
                new String[] {STRING, STRING, DEST_CARD_ARRAY, DEST_CARD_ARRAY},
                new Object[] {gameID, playerName, cardsKept, cardsToReturn} );
    }
}