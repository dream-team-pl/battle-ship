package dreamteam.battleship.service.springcontroller.play;

import java.util.Map;

/**
 * Created by ehsan on 02.08.16.
 */
public class TurnStatusResponse {

    public final Map<Integer, Boolean> myDamages;

    public final boolean isMyTurn;

    public TurnStatusResponse(Map<Integer, Boolean> myDamages, boolean isMyTurn) {
        this.myDamages = myDamages;
        this.isMyTurn = isMyTurn;
    }
}
