package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.VulnerableGhost;
import nl.tudelft.jpacman.util.TimerTaskCloneable;
import nl.tudelft.jpacman.util.TimerWithPause;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by coco- on 09-04-16.
 */
public class LevelSuperPellet extends Level {

    private TimerWithPause hunterTime;
    /**
     * The ghost of this level and their timer.
     */
    private Map<VulnerableGhost, TimerWithPause> ghostWithTimer;

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
            hunterTime = new TimerWithPause(new TimerTaskCloneable() {
                @Override
                public void run() {
                    stopGhostHunted();
                }
            });
            hunterTime.schedule(player.getTimeHunter());

            for (Map.Entry<NPC, ScheduledExecutorService> e : this.getNpcs().entrySet()) {
                ((VulnerableGhost) e.getKey()).setHunter(false);
                //((VulnerableGhost) e.getKey()).setHunterMode();
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
            //((VulnerableGhost) e.getKey()).setPoursuiteMode();
            setSpeedNPCs(e.getKey(),1);
        }
    }

    @Override
    public void start() {
        if(!isInProgress() && hunterTime != null){
            hunterTime.resume();
            for (Map.Entry<VulnerableGhost, TimerWithPause> k : ghostWithTimer.entrySet()){
                k.getValue().resume();
            }
        }
        super.start();
    }

    @Override
    public void stop() {
        if(isInProgress() && hunterTime != null){
            hunterTime.pause();
            for (Map.Entry<VulnerableGhost, TimerWithPause> k : ghostWithTimer   .entrySet()){
                k.getValue().pause();
            }
        }
        super.stop();
    }

    /**
     * Add a timer for respawning of death ghost.
     * @param g a death ghost.
     */
    public void addRespawnGhost(final VulnerableGhost g){
        TimerWithPause t = new TimerWithPause(new TimerTaskCloneable() {
            @Override
            public void run() {
                g.respawn();
                stop();
                start();
            }
        });
        t.schedule(VulnerableGhost.RESPAWN_TIME);
        if(ghostWithTimer.containsKey(g))
            ghostWithTimer.replace(g, t);
        else
            ghostWithTimer.put(g, t);
    }

}
