package component.model;

import java.util.List;

import component.model.gamesquare.GameSquare;

/**
 * Instance of a {@link GameBoard} with regular {@link GameSquare}'s
 * @author Lionel Bergen
 */
public class RegularGameBoard extends GameBoard {
	List<GameSquare> gameBoard;
	
	public RegularGameBoard(List<GameSquare> playableSquares) {
		setGameBoard(playableSquares);
	}

	@Override
	public List<GameSquare> getGameBoard() {
		return this.gameBoard;
	}

	@Override
	protected void setGameBoard(List<GameSquare> gameSquares) {
		this.gameBoard = gameSquares;
	}
}
