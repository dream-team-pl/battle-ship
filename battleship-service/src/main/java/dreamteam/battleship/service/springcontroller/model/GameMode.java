package dreamteam.battleship.service.springcontroller.model;

/**
 * Contains the possible modes of the game
 */
public enum GameMode {
    /**
     * The mode that 2 human players will play with each other just like the
     * classic battleship game
     */
    NORMAL_MODE,

    /**
     * The mode taht 2 human players will play with each other in the
     * Gun Salute way
     */
    GUN_SALUTE_MODE,

    /**
     * Just like The NORMAL_MODE, but one player will play with the machine
     */
    SINGLE_NORMAL_MODE,

    /**
     * Just like The GUN_SALUTE_MODE, but one player will play with the machine
     */
    SINGLE_GUN_SALUTE_MODE;
}
