package nl.tudelft.jpacman.level;


import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.fruit.Fruit;
import nl.tudelft.jpacman.fruit.Pomegranate;
import CraeyeMathieu.ChoiceMonster;
import CraeyeMathieu.Classement;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.specialcase.Bridge;
import nl.tudelft.jpacman.specialcase.SpecialSquare;
import nl.tudelft.jpacman.specialcase.Teleporter;
import nl.tudelft.jpacman.specialcase.Trap;

/**
 * A simple implementation of a collision map for the JPacman player.
 * <p>
 * It uses a number of instanceof checks to implement the multiple dispatch for the 
 * collisionmap. For more realistic collision maps, this approach will not scale,
 * and the recommended approach is to use a {@link CollisionInteractionMap}.
 * 
 * @author Arie van Deursen, 2014
 *
 */

public class PlayerCollisions implements CollisionMap {

	@Override
	public void collide(Unit mover, Unit collidedOn) {
		
		if(collidedOn instanceof Bridge)
		{
		
			
			
			if(((Bridge)collidedOn).knowUnit(mover)==false)
			{
				((Bridge)collidedOn).addUnit(mover);
			}
			
			
			
		}else
		 {
			if (mover instanceof Player)
		    {
			  playerColliding((Player) mover, collidedOn);
		     }
		     else if (mover instanceof Ghost) 
		     {
			ghostColliding((Ghost) mover, collidedOn);
		    }
		 }	
	}
	
	private void playerColliding(Player player, Unit collidedOn)
	{
		if (collidedOn instanceof Ghost) 
		{
		    playerVersusGhost(player, (Ghost) collidedOn);		
		}
		
		if (collidedOn instanceof Pellet) 
		{
			playerVersusPellet(player, (Pellet) collidedOn);
		}	
		if(collidedOn instanceof Teleporter)
		{
			player.occupy(player.getSpawn());
		
		}
		

	}
	
	private void ghostColliding(Ghost ghost, Unit collidedOn) 
	{
		if (collidedOn instanceof Player) 
		{
				playerVersusGhost((Player) collidedOn, ghost);		
		}
		if(collidedOn instanceof Trap)
		{
			ghost.trap(((SpecialSquare)collidedOn));
		}
		
	}
	
	
	/**
	 * Actual case of player bumping into ghost or vice versa.
     *
     * @param player The player involved in the collision.
     * @param ghost The ghost involved in the collision.
	 */

	public void playerVersusGhost(Player player, Ghost ghost)
	{
		if(player.isInvisible()==false)
		{
			if((player.checkOnBridge(player.getSquare())instanceof Bridge)&&(ghost.checkOnBridge(ghost.getSquare())instanceof Bridge))
			{
				Bridge playerBridge=(Bridge) player.checkOnBridge(player.getSquare());
				Bridge ghostBridge=(Bridge) ghost.checkOnBridge(ghost.getSquare());
				
				
				if(playerBridge.getEnterDirection(player).equals(ghostBridge.getEnterDirection(ghost)) )
				{
					player.setAlive(false);
					changePlayer(player);
				}
				
			}else
			{
					player.setAlive(false);
					changePlayer(player);
			}
		}
	}
	
	
	public  void changePlayer(Player player)
	{
		Launcher l=new Launcher();
		
		switch(player.getName())
		{
		case "blinky":
			l.cM.jBlinky.setScore(player.getScore());
		break;
		case "inky":
			l.cM.jInky.setScore(player.getScore());
		break;
		case "clyde":
			l.cM.jClyde.setScore(player.getScore());
		break;
		case "pinky":
			l.cM.jPinky.setScore(player.getScore());
		break;
		
		
		}
		l.launch();
		
	}
	
		

	
	/**
	 * Actual case of player consuming a pellet.
     *
     * @param player The player involved in the collision.
     * @param pellet The pellet involved in the collision.
	 */
	public void playerVersusPellet(Player player, Pellet pellet) 
	{
		pellet.leaveSquare();
		player.addPoints(pellet.getValue());		
	}

}
