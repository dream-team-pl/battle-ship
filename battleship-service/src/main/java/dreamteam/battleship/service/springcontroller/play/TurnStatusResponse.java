package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.service.springcontroller.registration.Player;

import java.util.Map;

/**
 * Created by ehsan on 02.08.16.
 */
public class TurnStatusResponse extends ShootResponse{

    public final Map<Integer, Boolean> myDamages;

    public final boolean isMyTurn;

    public final Player winner;

    public TurnStatusResponse(Map<Integer, Boolean> myDamages, boolean isMyTurn, Player winner) {
        super(null, null);
        this.myDamages = myDamages;
        this.isMyTurn = isMyTurn;
        this.winner = winner;
    }
}
