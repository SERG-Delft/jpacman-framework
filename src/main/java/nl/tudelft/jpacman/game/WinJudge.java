package nl.tudelft.jpacman.game;

public class WinJudge {
    private int maxSteps;
    private int minPellets;

    public WinJudge(int maxSteps, int minPellets) {
        this.maxSteps = maxSteps;
        this.minPellets = minPellets;
    }

    public boolean isEpicWin(int steps, int pelletsEaten) {
        return steps >= pelletsEaten && steps < maxSteps &&  pelletsEaten > minPellets;
    }
}
