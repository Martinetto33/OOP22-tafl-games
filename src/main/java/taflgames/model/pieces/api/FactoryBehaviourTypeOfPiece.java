package taflgames.model.pieces.api;


/**
 * this factory creates creates the 
 * the different behaviours of piece, also known as
 * the type of piece.
 */
public interface FactoryBehaviourTypeOfPiece {
    /**
     * creates queen's behaviour.
     * @return queen's behaviour
     */
    BehaviourTypeOfPiece createQueenBehaviour();
    /**
     * creates archer's behaviour.
     * @return archer's behaviour
     */
    BehaviourTypeOfPiece createArcherBehaviour();
    /**
     * creates king's behaviour.
     * @return king's behaviour
     */
    BehaviourTypeOfPiece createKingBehaviour();
    /**
     * creates shield's behaviour.
     * @return shield's behaviour
     */
    BehaviourTypeOfPiece createShieldBehaviour();
    /**
     * creates shield's behaviour.
     * @return shield's behaviour
     */
    BehaviourTypeOfPiece createSwapperBehaviour();
    /**
     * creates basic piece's behaviour.
     * @return basic piece's behaviour
     */
    BehaviourTypeOfPiece createBasicPieceBehaviour();
}
