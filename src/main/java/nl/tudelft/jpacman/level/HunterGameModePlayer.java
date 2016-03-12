package nl.tudelft.jpacman.level;

/**
 * Created by helldog136 on 2/03/16.
 */
public interface HunterGameModePlayer {
    void setHunter(boolean hunter);
    boolean isHunter();
    int hunted();
    boolean isActive();
    void addPoints(int n);
}
