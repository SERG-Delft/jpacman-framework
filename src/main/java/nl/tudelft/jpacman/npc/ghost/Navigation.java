package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Navigation provides utility to nagivate on {@link Square}s.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public final class Navigation {

	/**
	 * The log.
	 */
	private static final Logger log = LoggerFactory.getLogger(Navigation.class);

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
		long t0 = System.currentTimeMillis();
		if (from == to) {
			return new ArrayList<>();
		}

		List<Node> targets = new ArrayList<>();
		Set<Square> visited = new HashSet<>();
		targets.add(new Node(null, from, null));
		while (!targets.isEmpty()) {
			Node n = targets.remove(0);
			Square s = n.getSquare();
			if (s == to) {
				List<Direction> path = n.getPath();
				log.debug("Calculated shortest path for {} in {}ms.",
						traveller, System.currentTimeMillis() - t0);
				return path;
			}
			visited.add(s);
			for (Direction d : Direction.values()) {
				Square target = s.getSquareAt(d);
				if (!visited.contains(target)
						&& (traveller == null || target
								.isAccessibleTo(traveller))) {
					targets.add(new Node(d, target, n));
				}
			}
		}
		log.debug("Failed to find shortest path, took {}ms.",
				System.currentTimeMillis() - t0);
		return null;
	}

	/**
	 * Finds the nearest unit of the given type and returns its location. This
	 * method will perform a breadth first search starting from the given
	 * square.
	 * 
	 * @param T
	 *            The type of unit to search for.
	 * @param currentLocation
	 *            The starting location for the search.
	 * @return The nearest unit of the given type, or <code>null</code> if no
	 *         such unit could be found.
	 */
	public static Unit findNearest(Class<? extends Unit> T,
			Square currentLocation) {
		long t0 = System.currentTimeMillis();
		List<Square> toDo = new ArrayList<>();
		Set<Square> visited = new HashSet<>();

		toDo.add(currentLocation);

		while (!toDo.isEmpty()) {
			Square square = toDo.remove(0);
			Unit unit = findUnit(T, square);
			if (unit != null) {
				log.debug("Found unit in {}ms.", System.currentTimeMillis()
						- t0);
				return unit;
			}
			visited.add(square);
			for (Direction d : Direction.values()) {
				Square newTarget = square.getSquareAt(d);
				if (!visited.contains(newTarget) && !toDo.contains(newTarget)) {
					toDo.add(newTarget);
				}
			}
		}
		log.debug("Failed to find unit, took {}ms.", System.currentTimeMillis()
				- t0);
		return null;
	}

	/**
	 * Determines whether a square has an occupant of a certain type.
	 * 
	 * @param T
	 *            The type to search for.
	 * @param square
	 *            The square to search.
	 * @return A unit of type T, iff such a unit occupies this square, or
	 *         <code>null</code> of none does.
	 */
	private static Unit findUnit(Class<? extends Unit> T, Square square) {
		for (Unit u : square.getOccupants()) {
			if (T.isInstance(u)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Helper class to keep track of the path.
	 * 
	 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
	 */
	private static class Node {

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

		/**
		 * @return The direction for this node, or <code>null</code> if this
		 *         node is a root node.
		 */
		private Direction getDirection() {
			return direction;
		}

		/**
		 * @return The square for this node.
		 */
		private Square getSquare() {
			return square;
		}

		/**
		 * @return The parent node, or <code>null</code> if this node is a root
		 *         node.
		 */
		private Node getParent() {
			return parent;
		}

		/**
		 * Returns the list of values from the root of the tree to this node.
		 * 
		 * @return The list of values from the root of the tree to this node.
		 */
		private List<Direction> getPath() {
			if (getParent() == null) {
				return new ArrayList<>();
			}
			List<Direction> path = parent.getPath();
			path.add(getDirection());
			return path;
		}
	}
}
