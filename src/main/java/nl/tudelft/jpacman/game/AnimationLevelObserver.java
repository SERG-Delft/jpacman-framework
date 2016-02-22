package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.sprite.WinAnimation;

public class AnimationLevelObserver implements Level.LevelObserver {
    private Game game;
    private WinAnimation animation;

    public AnimationLevelObserver(Game game, WinAnimation animation) {
        this.game = game;
        this.animation = animation;
    }

    @Override
    public void levelWon(Level level) {
        WinJudge judge = new WinJudge(2000,0);

        if(judge.isEpicWin(game.getStepsTaken(), level.getTotalPellets())) {
            animation.playAnimation();
        }
    }

    @Override
    public void levelLost(Level level) {

    }
}
