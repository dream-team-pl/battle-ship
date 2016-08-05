package dreamteam.battleship.service.springcontroller.preparation;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.service.springcontroller.model.response.PlayerOrganizer;
import dreamteam.battleship.service.springcontroller.gamecontroller.Bench;
import dreamteam.battleship.service.springcontroller.gamecontroller.GameController;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.registration.Registration;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by egolesor on 19.07.16.
 */
public class PreparePlayerTest {

    @Test
    public void testIfGameControllerDoesntLetTheGameStartWIthOnePlayer(){
        dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer organizer = new dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer();

        MockHttpSession session = new MockHttpSession();

        Registration registration = mock(Registration.class);
        when(registration.getPlayer()).thenReturn(mock(Player.class));

        PlacingShip placingShip = mock(PlacingShip.class);
        when(placingShip.myManager()).thenReturn(mock(DamageManager.class));

        session.setAttribute("registration", registration);
        session.setAttribute("placingShip", placingShip);

        organizer.bench = new Bench();

        PlayerOrganizer response =  organizer.preparePlayer(session);

        assertFalse(response.readyToPlay);
    }

    @Test
    public void testIfOrganizerLetTheGameStart(){
        dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer organizer = new dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer();

        MockHttpSession session = new MockHttpSession();
        Registration registration = mock(Registration.class);
        when(registration.getPlayer()).thenReturn(mock(Player.class));

        PlacingShip placingShip = mock(PlacingShip.class);
        when(placingShip.myManager()).thenReturn(mock(DamageManager.class));

        session.setAttribute("registration", registration);
        session.setAttribute("placingShip", placingShip);
        organizer.bench = new Bench();
        organizer.bench.letSit(new GameController(mock(Player.class), mock(DamageManager.class)));
        PlayerOrganizer response =  organizer.preparePlayer(session);

        assertTrue(response.readyToPlay);

    }

    @Test
    public void testIfTheOrganizerPrepareTheGameControllersForBothPlayers(){
        // register Player1
        Player player1 = mock(Player.class);
        Registration registration1 = mock(Registration.class);
        when(registration1.getPlayer()).thenReturn(player1);

        // Register Player2
        Player player2 = mock(Player.class);
        Registration registration2= mock(Registration.class);
        when(registration2.getPlayer()).thenReturn(player2);

        // the organizer that we are going to test
        dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer organizer1 = new dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer();
        dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer organizer2 = new dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer();

        //singleton Bench
        Bench bench = new Bench();
        organizer1.bench = bench;
        organizer2.bench = bench;

        // the session to add the attributes
        PlacingShip placingShip = mock(PlacingShip.class);
        when(placingShip.myManager()).thenReturn(mock(DamageManager.class));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("registration", registration1);
        session.setAttribute("placingShip", placingShip);

        // prepare player1
        organizer1.preparePlayer(session);

        // session for player2
        session.setAttribute("registration", registration2);

        // second player
        organizer2.preparePlayer(session);

        // the game controller of organizers must be the same
        assertTrue(organizer1.gameController!=null);
        assertTrue(organizer1.gameController==organizer2.gameController);

        // now the bench must be free and ready for a new gameController
        assertTrue(bench.isFree());
    }

}
