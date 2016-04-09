package nl.tudelft.jpacman.util;

import java.util.Timer;

/**
 * Timer that can be paused and resume.
 * @author Corentin Ducruet
 */
public class TimerWithPause  {
    private Timer t;
    private long timerEnd, timeRemaining;
    private TimerTaskCloneable task;
    private boolean running;

    /**
     * create a timer that can be paused.
     * @param task
     */
    public TimerWithPause(TimerTaskCloneable task){
        t=new Timer();
        this.task=task;
        running=true;
    }

    /**
     * schedule the task at delay
     * @param delay
     */
    public void schedule(long delay){
        t = new Timer();
        TimerTaskCloneable timerTaskClone = null;
        try {
            timerTaskClone = task.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        t.schedule(timerTaskClone, delay);
        timerEnd = System.currentTimeMillis() + delay;
    }

    /**
     * cancel the timer.
     */
    public void cancel(){
        t.cancel();
        task=null;
    }

    /**
     * resume the timer.
     */
    public void resume(){
        if(!running && task != null){
            if(timeRemaining > 0)
                schedule(timeRemaining);
            running=true;
        }
    }

    /**
     * pause the timer.
     */
    public void pause(){
        if(running && task != null){
            t.cancel();
            t.purge();
            timeRemaining = timerEnd - System.currentTimeMillis();
            running = false;
        }
    }

}
