package com.runninglight.shared;

import com.runninglight.shared.Cards.TrainCard;
import com.runninglight.shared.state.PlayerState;

public interface IClient {

    void addPlayer(User u, Game g);

    void removePlayer(User u, Game g);

    void addGame(Game g);

    void broadcastMessage(Message message, Game game);

    void setDestinationCards(Game g, Player p);

    void addCardToHand(Game game, User user, TrainCard trainCard);

    void addCardToFaceUp(Game game, TrainCard trainCard, int position);

    void setTurn(Game game, PlayerState playerState);

    void endGame(Game game);
}
