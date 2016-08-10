package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.Map;

/**
 * Created by ehsan on 02.08.16.
 */
public class TurnStatus extends ShootingResult {

    public final Map<Integer, Boolean> myDamages;

    public final boolean isMyTurn;

    public final Player winner;

    public TurnStatus(Map<Integer, Boolean> myDamages, boolean isMyTurn, Player winner) {
        super(null, null);
        this.myDamages = myDamages;
        this.isMyTurn = isMyTurn;
        this.winner = winner;
    }
}
