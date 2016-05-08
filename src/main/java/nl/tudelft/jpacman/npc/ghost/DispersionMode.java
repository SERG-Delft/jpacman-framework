package nl.tudelft.jpacman.npc.ghost;

import java.util.List;

import nl.tudelft.jpacman.board.Direction;

public class DispersionMode implements Strategy{

	Ghost g;
	
	public DispersionMode(Ghost g){
		this.g=g;
	}
	
	public Direction nextMove(){
		
		//si le fantôme a déjà atteint sa maison
		if (g.getAtteintHome()){
			Direction g0 = g.getCheminEnCours()[0];
			g.avancerChemin();
			return g0;
		}
		//sinon soit le fantôme vient d'arriver sur sa case A
		else if (g.getSquare()==g.getHome()){
				g.setAtteintHome(true);
				Direction g0 = g.getCheminEnCours()[0];
				g.avancerChemin();
				return g0;
			}
		// ou bien il n'est pas encore arrivé et il continue d'avancer jusqu'à atteindre A
		else {
			//System.out.println(g.getHome());
			//System.out.println(g.getSquare());
			List<Direction> path = Navigation.shortestPath(g.getSquare(),g.getHome(), 
					g);
			if (path != null && !path.isEmpty()) {
				Direction d = path.get(0);
				//System.out.println(d);
				return d;
			}
			Direction d = g.randomMove();
			return d;
	}
	}
}
