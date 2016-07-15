package dreamteam.battleship.service.registration;

import java.util.Random;

/**
 * Created by ehsan on 14.07.16.
 */
public class BattleshipGenerator {

    public String generateKey(){
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis()).append("").append(random.nextFloat()).append(hashCode());
        return builder.toString();
    }

}
