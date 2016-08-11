package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.service.springcontroller.model.Player;

/**
 * Created by ehsan on 18.07.16.
 */
class Register implements Response {

    public final Player player;

    public Register(Player player) {
        this.player = player;
    }
}
