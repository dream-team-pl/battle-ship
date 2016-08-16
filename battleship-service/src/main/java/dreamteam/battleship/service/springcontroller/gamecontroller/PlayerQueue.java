package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.service.springcontroller.model.Player;

/**
 * Created by daniel on 10.08.16.
 */
class PlayerQueue {
    private Player player1;
    private Player player2;

    private int player1Shots;
    private int player2Shots;



    public synchronized boolean isMyTurn(Player player) {
        return (player==player1 || player==player2);
    }

    public void remove(Player player) {
        if(player == player1) {
            player1 = null;
            player1Shots = 0;
        } else if(player == player2) {
            player2 = null;
            player2Shots = 0;
        }
    }

    public void add(Player player1, Player player2, int player1Shots, int player2Shots) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Shots = player1Shots;
        this.player2Shots = player2Shots;
    }

    public boolean isEmpty() {
        return (player1 == null && player2==null);
    }

    public int shotsNumber(Player player) {
        return player == player1 ? player1Shots : player2Shots;
    }
}
