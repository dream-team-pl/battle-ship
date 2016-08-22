package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.Map;

/**
 * Created by ehsan on 02.08.16.
 */
class TurnStatus implements Response {

    public final Map<Integer, Boolean> myDamages;

    public final boolean isMyTurn;

    public final Player winner;

    public final int numberOfShots;

    public boolean gameOver;

    public TurnStatus(Map<Integer, Boolean> myDamages, boolean isMyTurn, Player winner, int numberOfShots) {
        this.myDamages = myDamages;
        this.isMyTurn = isMyTurn;
        this.winner = winner;
        this.numberOfShots = numberOfShots;
    }

    public TurnStatus(Map<Integer, Boolean> myDamages, boolean isMyTurn, Player winner, int numberOfShots, boolean gameOver) {
        this.myDamages = myDamages;
        this.isMyTurn = isMyTurn;
        this.winner = winner;
        this.numberOfShots = numberOfShots;
        this.gameOver = gameOver;
    }
}
