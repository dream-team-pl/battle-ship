package dreamteam.battleship.service.springcontroller.model.response;

/**
 * Created by egolesor on 19.07.16.
 */
class Organizer implements Response{

    public final boolean readyToPlay;

    public Organizer(boolean readyToPlay) {
        this.readyToPlay = readyToPlay;
    }
}
