package taflgames.view.scenes;

import taflgames.view.View;

/**
 * The screen that appears at the end of the game.
 */
public class GameOverScene extends AbstractScene {

    private static final String GAME_OVER = "Game over";
    /**
     * Builds a new GameOverScene.
     * @param sceneName
     */
    public GameOverScene(final View view) {
        super(GameOverScene.GAME_OVER);
    }
}
