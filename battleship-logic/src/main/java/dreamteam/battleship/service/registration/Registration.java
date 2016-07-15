package dreamteam.battleship.service.registration;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;

/**
 * Created by ehsan on 14.07.16.
 */
@RestController
@Scope("session")
@CrossOrigin("*")
public class Registration {

    @RequestMapping(name = "/register")
    public Register registration(HttpSession sessionObj, @RequestParam(name = "name")String name,
                                 @RequestParam(name = "name")String surname){

        String key = new BattleshipGenerator().generateKey();

        return new Register("ehsan", "golesorkhi", key, PlayerType.HUMAN);
    }

}
