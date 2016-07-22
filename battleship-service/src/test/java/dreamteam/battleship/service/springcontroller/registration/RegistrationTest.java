package dreamteam.battleship.service.springcontroller.registration;

import dreamteam.battleship.service.springcontroller.registration.KeyGenerator;
import dreamteam.battleship.service.springcontroller.registration.Player;
import dreamteam.battleship.service.springcontroller.registration.Registration;
import dreamteam.battleship.service.springcontroller.registration.RegistrationResponse;
import org.springframework.mock.web.MockHttpSession;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ehsan on 18.07.16.
 */

public class RegistrationTest {

    @DataProvider
    protected Object[][] generatedKeys(){
        KeyGenerator generator = new KeyGenerator();
        Set<String> temp = new HashSet<String>();
        return new Object[][]{
                {temp.add(generator.generate())},
                {temp.add(generator.generate())},
                {temp.add(generator.generate())},
                {temp.add(generator.generate())},
                {temp.add(generator.generate())},
                {temp.add(generator.generate())},
                {temp.add(generator.generate())},
        };
    }

    @Test(dataProvider = "generatedKeys")
    public void testIfGeneratedKeyAreUnique(boolean isUnique){
        Assert.assertTrue(isUnique);
    }

    @Test
    public void testIfTheGeneratedKeyReturnsAfterRegistering(){
        MockHttpSession session = new MockHttpSession();

        Registration registration = new Registration();
        RegistrationResponse response = registration.register(session, "name", "surname");

        Assert.assertFalse(response.player.identification.isEmpty());
    }

    @Test
    public void testIfRegistrationCreatesThePlayer(){
        MockHttpSession session = new MockHttpSession();

        Registration registration = new Registration();
        RegistrationResponse response = registration.register(session, "name", "surname");
        Player player = response.player;
        Assert.assertFalse(player==null);
        Assert.assertTrue(player==registration.player);
    }



}
