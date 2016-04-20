package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Navigation provides utility to nagivate on {@link Square}s.
 * 
 * @author Jeroen Roosen 
 */
public final class Navigation {

	private Navigation() {
	}
	
	/**
	 * Calculates the shortest path. This is done by BFS. This search ensures
	 * the traveller is allowed to occupy the squares on the way, or returns the
	 * shortest path to the square regardless of terrain if no traveller is
	 * specified.
	 * 
	 * @param from
	 *            The starting square.
	 * @param to
	 *            The destination.
	 * @param traveller
	 *            The traveller attempting to reach the destination. If
	 *            traveller is set to <code>null</code>, this method will ignore
	 *            terrain and find the shortest path whether it can actually be
	 *            reached or not.
	 * @return The shortest path to the destination or <code>null</code> if no
	 *         such path could be found. When the destination is the current
	 *         square, an empty list is returned.
	 */
	public static List<Direction> shortestPath(Square from, Square to,
			Unit traveller) {
		List<Direction> path = null;
		List<Node> targets = new ArrayList<>();
		Node n = new Node(null, from, null);
		Set<Square> visited = new HashSet<>();

		targets.add(n);

		while (from!=to && !targets.isEmpty()) {
			Node m  = targets.remove(0);
			Square s = m.getSquare();

			visited.add(from);
			addNewTargets(traveller, targets, visited, m, s);

			n = m;
			from = s;
		}

		if(from == to) path = n.getPath();

		return path;
	}



	private static void addNewTargets(Unit traveller, List<Node> targets,
			Set<Square> visited, Node n, Square s) {
		for (Direction d : Direction.values()) {
			Square target = s.getSquareAt(d);

			if (!visited.contains(target)
					&& (traveller == null || target
							.isAccessibleTo(traveller)))
				targets.add(new Node(d, target, n));
		}
	}

	/**
	 * Finds the nearest unit of the given type and returns its location. This
	 * method will perform a breadth first search starting from the given
	 * square.
	 * 
	 * @param type
	 *            The type of unit to search for.
	 * @param currentLocation
	 *            The starting location for the search.
	 * @return The nearest unit of the given type, or <code>null</code> if no
	 *         such unit could be found.
	 */
	public static Unit findNearest(Class<? extends Unit> type,
			Square currentLocation) {
		List<Square> toDo = new ArrayList<>();
		Set<Square> visited = new HashSet<>();
		Unit unit = findUnit(type, currentLocation);

		toDo.add(currentLocation);

		while (!toDo.isEmpty() && unit == null) {
			Square square = toDo.remove(0);

			visited.add(square);
			addNewTargetsToDo(square,visited,toDo);

			unit = findUnit(type, square);
		}

		return unit;
	}

	private static void addNewTargetsToDo(Square s, Set<Square> visited, List<Square> toDo){
		for (Direction d : Direction.values()) {
			Square newTarget = s.getSquareAt(d);

			if (!visited.contains(newTarget) && !toDo.contains(newTarget))
				toDo.add(newTarget);
		}
	}

	/**
	 * Determines whether a square has an occupant of a certain type.
	 * 
	 * @param type
	 *            The type to search for.
	 * @param square
	 *            The square to search.
	 * @return A unit of type T, iff such a unit occupies this square, or
	 *         <code>null</code> of none does.
	 */
	private static Unit findUnit(Class<? extends Unit> type, Square square) {
		Unit unit = null;

		for (Unit u : square.getOccupants())
			if (type.isInstance(u)) {
				unit = u;
				break;
			}

		return unit;
	}

	/**
	 * Helper class to keep track of the path.
	 * 
	 * @author Jeroen Roosen 
	 */
	private static final class Node {

		/**
		 * The direction for this node, which is <code>null</code> for the root
		 * node.
		 */
		private final Direction direction;

		/**
		 * The parent node, which is <code>null</code> for the root node.
		 */
		private final Node parent;

		/**
		 * The square associated with this node.
		 */
		private final Square square;

		/**
		 * Creates a new node.
		 * 
		 * @param d
		 *            The direction, which is <code>null</code> for the root
		 *            node.
		 * @param s
		 *            The square.
		 * @param p
		 *            The parent node, which is <code>null</code> for the root
		 *            node.
		 */
		private Node(Direction d, Square s, Node p) {
			this.direction = d;
			this.square = s;
			this.parent = p;
		}


// --Commented out by Inspection START (16/04/2016 20:06):
//		/**
//		 * @return The direction for this node, or <code>null</code> if this
//		 *         node is a root node.
//		 */
//		private Direction getDirection() {
//			return direction;
//		}
// --Commented out by Inspection STOP (16/04/2016 20:06)


		/**
		 * @return The square for this node.
		 */
		private Square getSquare() {
			return square;
		}


// --Commented out by Inspection START (16/04/2016 20:06):
//		/**
//		 * @return The parent node, or <code>null</code> if this node is a root
//		 *         node.
//		 */
//		private Node getParent() {
//			return parent;
//		}
// --Commented out by Inspection STOP (16/04/2016 20:06)

		/**
		 * Returns the list of values from the root of the tree to this node.
		 * 
		 * @return The list of values from the root of the tree to this node.
		 */
		private List<Direction> getPath() {
			List<Direction> path;

			if (parent != null) {
				path = parent.getPath();
				path.add(direction);
			}else{
				path = new ArrayList<>();
			}

			return path;
		}
	}
}
