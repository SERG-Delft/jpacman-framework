package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.VulnerableGhost;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by coco- on 09-04-16.
 */
public class LevelSuperPellet extends Level {

    private Timer hunterTime;
    /**
     * The ghost of this level and their timer.
     */
    private Map<VulnerableGhost, Timer> ghostWithTimer;

    /**
     * Creates a new level for the board.
     *
     * @param b              The board for the level.
     * @param ghosts         The ghosts on the board.
     * @param startPositions The squares on which players start on this board.
     * @param collisionMap
     */
    public LevelSuperPellet(Board b, List<NPC> ghosts, List<Square> startPositions, CollisionMap collisionMap) {
        super(b, ghosts, startPositions, collisionMap);
        hunterTime =null;
        ghostWithTimer = new HashMap<>();
    }

    /**
     * ghost will be hunted by pacman and their speed is reduce by 50 percent
     * @param player
     */
    public void startGhostHunted(Player player){
        synchronized (getStartStopLock()) {
            if(hunterTime!=null){
                hunterTime.cancel();
            }
            hunterTime = new Timer();
            hunterTime.schedule(new TimerTask(){

                @Override
                public void run() {
                    stopGhostHunted();
                }
            },player.getTimeHunter());

            for (Map.Entry<NPC, ScheduledExecutorService> e : this.getNpcs().entrySet()) {
                ((VulnerableGhost) e.getKey()).setHunter(false);
                setSpeedNPCs(e.getKey(),0.5f);
            }
        }
    }

    /**
     * ghost will hunt pacman and their speed is normal
     */
    private void stopGhostHunted() {
        if(hunterTime != null){
            hunterTime.cancel();
        }
        for (Map.Entry<NPC, ScheduledExecutorService> e : this.getNpcs().entrySet()) {
            ((VulnerableGhost) e.getKey()).setHunter(true);
            setSpeedNPCs(e.getKey(),1);
        }
    }

    @Override
    public void start() {
        if(!isInProgress() && hunterTime != null){
            hunterTime.resume();
            for (Map.Entry<VulnerableGhost, Timer> k : ghostWithTimer.entrySet()){
                k.getValue().resume();
            }
        }
        super.start();
    }

    @Override
    public void stop() {
        if(isInProgress() && hunterTime != null){
            hunterTime.pause();
            for (Map.Entry<VulnerableGhost, Timer> k : ghostWithTimer   .entrySet()){
                k.getValue().pause();
            }
        }
        super.stop();
    }

}
