package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.service.springcontroller.model.Player;

/**
 * Created by daniel on 10.08.16.
 */
class PlayerQueue {
    private Player player1;
    private Player player2;

    public synchronized boolean isMyTurn(Player player) {
        return (player==player1 || player==player2);
    }

    public void remove(Player player) {
        if(player == player1)
            player1 = null;
        else if(player == player2)
            player2 = null;
    }

    public void add(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isEmpty() {
        return (player1 == null && player2==null);
    }
}
