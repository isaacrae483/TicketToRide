package com.runninglight.tickettoride.communication;

import android.util.Log;

import com.runninglight.shared.Cards.CardColor;
import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.Command;
import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Game;
import com.runninglight.shared.GameInfo;
import com.runninglight.shared.IServer;
import com.runninglight.shared.LoginInfo;
import com.runninglight.shared.Message;
import com.runninglight.shared.Player;
import com.runninglight.shared.Results;
import com.runninglight.shared.Route;
import com.runninglight.shared.Serializer;
import com.runninglight.shared.User;
import com.runninglight.shared.state.PlayerState;

import java.util.ArrayList;
import java.util.Random;

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
    private static final String ROUTE = "com.runninglight.shared.Route";
    private static final String DEST_CARD_ARRAY = "[Lcom.runninglight.shared.Cards.DestinationCard;";
    private static final String STRING = "java.lang.String";
    private static final String MESSAGE = "com.runninglight.shared.Message";
    private static final String TRAINCARD = "com.runninglight.shared.Cards.TrainCard";
    private static final String PLAYER_STATE = "com.runninglight.shared.state.PlayerState";

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
    public boolean drawCardFromFaceUpToHand(Game game, User user, TrainCard trainCard, int position)
    {
        // Add card to hand here
        // ClientFacade.getInstance().addCardToFaceUp(game, getRandomTraincard());

        Results results = ClientCommunicator.getInstance().send(getDrawCardFromFaceUpToHandCommand(game, user, trainCard, position));
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
    public boolean drawCardFromDeckToHand(Game game, User user)
    {
        Results results = ClientCommunicator.getInstance().send(getDrawTrainCardFromDeckCommand(game, user));
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
    public void setTurn(String gameID, PlayerState playerState){
        Results results = communicator.send(getSetTurnCommand(gameID, playerState));
        if (!results.isSuccess()) {
            System.out.println(results.getErrorInfo());
        }
    }

    @Override
    public void endGame(String gameID) {
        Results results = communicator.send(getEndGameCommand(gameID));
        if (!results.isSuccess()) {
            System.out.println(results.getErrorInfo());
        }
    }

    @Override
    public void claimRoute(String gameID, Player player, int routeNumber, String color)
    {
        Results results = communicator.send(getClaimRouteCommand(gameID, player, routeNumber, color));
        if (!results.isSuccess()) {
            System.out.println(results.getErrorInfo());
        }
    }


    private Command getClaimRouteCommand(String gameID, Player player, int routeNumber, String color)
    {
        return new Command(
                SERVER_FACADE,
                "claimRoute",
                new String[] {STRING, PLAYER, "int", STRING},
                new Object[] {gameID, player, routeNumber, color} );
    }

    private Command getDrawCardFromFaceUpToHandCommand(Game game, User user, TrainCard trainCard, int position)
    {
        return new Command(
                SERVER_FACADE,
                "drawCardFromFaceUpToHand",
                new String[] {GAME, USER, TRAINCARD, "int"},
                new Object[] {game, user, trainCard, position} );
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

    private Command getDrawTrainCardFromDeckCommand(Game game, User user)
    {
        return new Command(
                SERVER_FACADE,
                "drawCardFromDeckToHand",
                new String[] {GAME, USER},
                new Object[] {game, user} );
    }

    private Command getSetTurnCommand(String gameID, PlayerState playerState)
    {
        return new Command(
                SERVER_FACADE,
                "setTurn",
                new String[] {STRING, PLAYER_STATE},
                new Object[] {gameID, playerState} );
    }

    private Command getEndGameCommand(String gameID)
    {
        return new Command(
                SERVER_FACADE,
                "endGame",
                new String[] {STRING},
                new Object[] {gameID} );
    }

    // Temporary

    private TrainCard getRandomTraincard()
    {
        switch (new Random().nextInt(9))
        {
            case 0: return new TrainCard(CardColor.BLUE);
            case 1: return new TrainCard(CardColor.BLACK);
            case 2: return new TrainCard(CardColor.RED);
            case 3: return new TrainCard(CardColor.GREEN);
            case 4: return new TrainCard(CardColor.WHITE);
            case 5: return new TrainCard(CardColor.PINK);
            case 6: return new TrainCard(CardColor.ORANGE);
            case 7: return new TrainCard(CardColor.YELLOW);
            case 8: return new TrainCard(CardColor.WILD);
        }
        return null;
    }
}