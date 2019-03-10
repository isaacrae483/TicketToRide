package com.runninglight.tickettoride.iview.game;

import com.runninglight.shared.Player;

import java.util.ArrayList;

public interface IPlayerInfoView {
    void updatePlayerInfo(ArrayList<Player> players, Player currentPlayer, String currentTurn);
}
