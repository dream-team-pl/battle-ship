package dreamteam.battleship.service.springcontroller.registration;

import java.util.Random;

/**
 * Created by ehsan on 18.07.16.
 */
public class KeyGenerator {


    public String generate() {
        StringBuilder builder = new StringBuilder();
        Random rand = new Random(100);
        builder.append(System.currentTimeMillis()).append(rand.nextFloat()).append(new Object().hashCode());
        return builder.toString();
    }
}
