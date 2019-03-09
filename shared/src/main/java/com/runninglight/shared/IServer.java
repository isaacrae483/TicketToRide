package com.runninglight.shared;

import com.runninglight.shared.Cards.DestinationCard;
import com.runninglight.shared.Cards.TrainCard;

public interface IServer {
    boolean login(LoginInfo loginInfo);

    boolean register(LoginInfo loginInfo);

    boolean createGame(GameInfo gameInfo);

    boolean joinGame(User user, Game game);

    boolean leaveGame(User user, Game game);

    boolean sendMessage(Message message, Game game);

    boolean drawCardFromFaceUpToHand(Game game, User user, TrainCard trainCard, int position);

    boolean drawCardFromDeckToHand(Game game, User user);

    Game[] getGameList();

    DestinationCard[] drawDestCards(String gameID, int numCards);

    void returnDestCards(String gameID, String playerName,
                         DestinationCard[] cardsKept, DestinationCard[] cardsToReturn);

    void setTurn(String gameID, String playerName);
}
