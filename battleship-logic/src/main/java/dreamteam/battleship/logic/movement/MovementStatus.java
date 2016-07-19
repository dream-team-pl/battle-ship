package dreamteam.battleship.logic.movement;

/**
 * Created by ehsan on 12.07.16.
 */
public enum MovementStatus {

    /**
     * <ul>
     *     <li>When Placing the ships it returns if placing is finished correctly</li>
     *     <li>When shooting the fields it return if a ship is damaged</li>
     * </ul>
     */
    SUCCESS,

    /**
     * <ul>
     *     <li>When placing the ships returns if there is no place for that ship</li>
     *     <li>When shooting the fields it returns if no ship is damaged</li>
     * </ul>
     */
    TRY_AGAIN,

    /**
     * <ul>
     *     <li>When shooting the ships it returns when the the current player won</li>
     * </ul>
     */
    WON,

    /**
     * <ul>
     *     <li>When placing the ships it returns if field that you want to place your ship is invalid</li>
     *     <li>When shooting the field it return if you already shot the field or
     *     the field that you want to shoot is invalid</li>
     * </ul>
     */
    INVALID_MOVEMENT

}
