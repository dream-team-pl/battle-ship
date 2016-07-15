package dreamteam.battleship.service.registration;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertTrue;

/**
 * Created by ehsan on 14.07.16.
 */
public class RegistrationTest {

    @DataProvider
    public Object[][] theSecureSizes(){
        Set<Register> myMap = new HashSet<>();
        Registration reg = new Registration();
        HttpSession session = mock(HttpSession.class);
        return new Object[][]{

        };
    }


    @Test(dataProvider = "theSecureSizes")
    public void testIfEveryKeysAreUnique(boolean bool){
        assertTrue(bool);

    }


}