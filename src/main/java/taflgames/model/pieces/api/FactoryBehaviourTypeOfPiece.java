package taflgames.model.pieces.api;


/**
 * this factory creates creates the 
 * the different types of pieces
 */
public interface FactoryBehaviourTypeOfPiece {
    BehaviourTypeOfPiece createQueenBehaviour();
    BehaviourTypeOfPiece createArcherBehaviour();
    BehaviourTypeOfPiece createKingBehaviour();
    BehaviourTypeOfPiece createShieldBehaviour();
    BehaviourTypeOfPiece createSwapperBehaviour();
    BehaviourTypeOfPiece createBasicPieceBehaviour();
}
