package taflgames.controller.gameloop.code;

import java.util.Optional;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.common.code.Position;
import taflgames.controller.gameloop.api.GameLoop;
import taflgames.model.Match;
import taflgames.model.memento.api.Caretaker;
import taflgames.model.memento.code.CaretakerImpl;

/**
 * An implementation of the GameLoop interface. This class should be used by a Controller
 * class.
 */
public class GameLoopImpl implements GameLoop {
    private final Match match;
    private final Caretaker caretaker;
    private boolean running;

    /**
     * Builds a new GameLoop.
     * @param match the match that this GameLoop will run.
     */
    public GameLoopImpl(final Match match) {
        this.match = match;
        this.running = false;
        this.caretaker = new CaretakerImpl(match);
        this.caretaker.updateHistory(); //register the beginning state of the Match.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGameLoop() {
        this.running = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGameLoop() {
        this.running = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void makeMove(final Position starPosition, final Position endPosition) throws IllegalAccessException {
        this.checkRunning();
        this.match.makeMove(starPosition, endPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() throws IllegalAccessException {
        this.checkRunning();
        this.caretaker.unlockHistory();
        this.caretaker.undo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passTurn() throws IllegalAccessException {
        this.checkRunning();
        this.match.setNextActivePlayer();
        this.caretaker.updateHistory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.match.isOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<MatchResult, MatchResult>> getMatchResult() {
        return this.match.getMatchEndStatus();
    }

    /**
     * {@inheritDoc}
     */
    private void checkRunning() throws IllegalAccessException {
        if (!this.running) {
            throw new IllegalAccessException("The GameLoop is not running!");
        }
    }

}
