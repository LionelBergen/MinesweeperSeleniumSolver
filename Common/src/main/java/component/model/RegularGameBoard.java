package component.model;

import java.util.List;

import component.model.gamesquare.GameSquare;

/**
 * Instance of a {@link GameBoard} with regular {@link GameSquare}'s
 * @author Lionel Bergen
 */
public class RegularGameBoard extends GameBoard {
	public RegularGameBoard(List<GameSquare> playableSquares) {
		setGameBoard(playableSquares);
	}
}
