package tests.minesweeper.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import solver.component.Rule;
import tests.minesweeper.data.component.SectionTestScenario;
import tests.minesweeper.solver.data.GameBoardTestData;

import static tests.minesweeper.data.TestDataHelper.getGameSquare;
import static tests.minesweeper.data.TestDataHelper.makeCopy;

public class SectionTestScenarios {
	public static final SectionTestScenario SCENARIO_01 = getScenario01();
	public static final SectionTestScenario SCENARIO_02 = getScenario02();
	public static final SectionTestScenario SCENARIO_03 = getScenario03();
	
	public static final SectionTestScenario SCENARIO_SPECIAL_01 = getScenarioSpecial01();
	public static final SectionTestScenario SCENARIO_SPECIAL_02 = getScenarioSpecial02();
	
	private static SectionTestScenario getScenario01() {
		Section section = GameBoardTestScenarios.SCENARIO_01.getExpectedSections().iterator().next();
		
		Set<GameSquare> gameSquares = new HashSet<GameSquare>();
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3));
		
		Map<List<Section>, Set<GameSquare>> expectedContents = new HashMap<>();
		List<Section> ss = new ArrayList<>();
		ss.add(new Section(new HashSet<>(gameSquares)));
		expectedContents.put(ss, gameSquares);
		
		return new SectionTestScenario(Arrays.asList(section), expectedContents, Arrays.asList(new Rule(gameSquares, 1, section)));
	}
	
	private static SectionTestScenario getScenario02() {
		Section section = GameBoardTestScenarios.SCENARIO_03.getExpectedSections().iterator().next();
		
		// # 2
		List<GameSquare> gameSquareResults1 = new ArrayList<GameSquare>();
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		
		// # 3
		List<GameSquare> gameSquareResults2 = new ArrayList<GameSquare>();
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3));
		
		Rule touchingThe2 = createSweeperSet(gameSquareResults1, 2, section);
		Rule touchingThe3 = createSweeperSet(gameSquareResults2, 3, section);
		
		List<Rule> expectedResults = Arrays.asList(
				touchingThe2,
				touchingThe3
				);
		
		Set<GameSquare> resultSet1 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0)));
		List<Section> parentSet1 = Arrays.asList(new Section(new HashSet<>(touchingThe2.getSquares())));
		
		Set<GameSquare> resultSet2 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2)));
		List<Section> parentSet2 = Arrays.asList(new Section(new HashSet<>(touchingThe2.getSquares())),
				new Section(new HashSet<>(touchingThe3.getSquares())));
		
		Set<GameSquare> resultSet3 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3)));
		List<Section> parentSet3 = Arrays.asList(new Section(new HashSet<>(touchingThe3.getSquares())));
		
		Map<List<Section>, Set<GameSquare>> expectedContents = new HashMap<>();
		
		expectedContents.put(parentSet1, resultSet1);
		expectedContents.put(parentSet2, resultSet2);
		expectedContents.put(parentSet3, resultSet3);
		
		return new SectionTestScenario(Arrays.asList(section), expectedContents, expectedResults);
	}
	
	// Similar as above but with 2 flags & a 4 instead of a 3
	private static SectionTestScenario getScenario03() {
		// setup test data
		// make a copy so we don't mess up the original data
		Section section = makeCopy(GameBoardTestScenarios.SCENARIO_03.getExpectedSections().iterator().next());
		List<GameSquare> gameSquareList = new ArrayList<GameSquare>(section.getGameSquares());
		
		getGameSquare(gameSquareList, 1, 2).setValue(SquareValue.FOUR);
		getGameSquare(gameSquareList, 1, 3).setValue(SquareValue.FLAGGED);
		getGameSquare(gameSquareList, 2, 3).setValue(SquareValue.FLAGGED);
		
		// # 2
		List<GameSquare> gameSquareResults1 = new ArrayList<GameSquare>();
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		
		// # 4
		List<GameSquare> gameSquareResults2 = new ArrayList<GameSquare>();
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3));
		
		Rule touchingThe2 = createSweeperSet(gameSquareResults1, 2, section);
		Rule touchingThe4 = createSweeperSet(gameSquareResults2, 2, section);
		
		List<Rule> expectedResults = Arrays.asList(
				touchingThe2,
				touchingThe4
				);
		
		Set<GameSquare> resultSet1 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0)));
		List<Section> parentSet1 = Arrays.asList(new Section(new HashSet<>(touchingThe2.getSquares())));
		
		Set<GameSquare> resultSet2 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2)));
		List<Section> parentSet2 = Arrays.asList(new Section(new HashSet<>(touchingThe2.getSquares())),
				new Section(new HashSet<>(touchingThe4.getSquares())));
		
		Set<GameSquare> resultSet3 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3)));
		List<Section> parentSet3 = Arrays.asList(new Section(new HashSet<>(touchingThe4.getSquares())));
		
		Map<List<Section>, Set<GameSquare>> expectedContents = new HashMap<>();
		
		expectedContents.put(parentSet1, resultSet1);
		expectedContents.put(parentSet2, resultSet2);
		expectedContents.put(parentSet3, resultSet3);
		
		return new SectionTestScenario(Arrays.asList(section), expectedContents, expectedResults);
	}
	
	// Here: https://math.stackexchange.com/questions/3447402/minesweeper-odds-for-this-scenario-2-different-calculations
	private static SectionTestScenario getScenarioSpecial01() {
		Section section = GameBoardTestScenarios.SCENARIO_SPECIAL_01.getExpectedSections().iterator().next();
		
		// # 3 (2. 2)
		List<GameSquare> gameSquareResults1 = new ArrayList<GameSquare>();
		gameSquareResults1.add(new GameSquare("A", SquareValue.BLANK_UNTOUCHED, 1, 1));
		gameSquareResults1.add(new GameSquare("B", SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults1.add(new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 3, 1));
		gameSquareResults1.add(new GameSquare("F", SquareValue.BLANK_UNTOUCHED, 1, 2));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2));
		gameSquareResults1.add(new GameSquare("I", SquareValue.BLANK_UNTOUCHED, 1, 3));
		gameSquareResults1.add(new GameSquare("J", SquareValue.BLANK_UNTOUCHED, 2, 3));
		
		// # 1 (4, 2)
		List<GameSquare> gameSquareResults2 = new ArrayList<GameSquare>();
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1));
		gameSquareResults2.add(new GameSquare("D", SquareValue.BLANK_UNTOUCHED, 4, 1));
		gameSquareResults2.add(new GameSquare("E", SquareValue.BLANK_UNTOUCHED, 5, 1));
		gameSquareResults2.add(new GameSquare("G", SquareValue.BLANK_UNTOUCHED, 3, 2));
		gameSquareResults2.add(new GameSquare("H", SquareValue.BLANK_UNTOUCHED, 5, 2));
		gameSquareResults2.add(new GameSquare("K", SquareValue.BLANK_UNTOUCHED, 4, 3));
		gameSquareResults2.add(new GameSquare("L", SquareValue.BLANK_UNTOUCHED, 5, 3));
		
		// TODO: why expect blank squares in the rules?
		// # 1 (3, 3)
		List<GameSquare> gameSquareResults3 = new ArrayList<GameSquare>();
		gameSquareResults3.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2));
		gameSquareResults3.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3));
		gameSquareResults3.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 3));
		gameSquareResults3.add(new GameSquare("N", SquareValue.BLANK_UNTOUCHED, 2, 4));
		gameSquareResults3.add(new GameSquare("O", SquareValue.BLANK_UNTOUCHED, 3, 4));
		gameSquareResults3.add(new GameSquare("P", SquareValue.BLANK_UNTOUCHED, 4, 4));

		Rule touchingThe3 = createSweeperSet(gameSquareResults1, 3, section);
		Rule touchingThe11 = createSweeperSet(gameSquareResults2, 1, section);
		Rule touchingThe12 = createSweeperSet(gameSquareResults3, 1, section);
		
		List<Rule> expectedResults = Arrays.asList(
				touchingThe3,
				touchingThe11,
				touchingThe12
				);
		
		// Green
		Set<GameSquare> resultSet1 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3)));
		List<Section> parentSet1 = Arrays.asList(new Section(new HashSet<>(touchingThe3.getSquares())));
		
		// Yellow
		Set<GameSquare> resultSet2 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3)));
		List<Section> parentSet2 = Arrays.asList(new Section(new HashSet<>(touchingThe3.getSquares())),
				new Section(new HashSet<>(touchingThe12.getSquares())));
		
		// Light-blue
		Set<GameSquare> resultSet3 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 4),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 4),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4)));
		List<Section> parentSet3 = Arrays.asList(new Section(new HashSet<>(touchingThe12.getSquares())));
		
		// Pink
		Set<GameSquare> resultSet4 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1)));
		List<Section> parentSet4 = Arrays.asList(new Section(new HashSet<>(touchingThe11.getSquares())),
				new Section(new HashSet<>(touchingThe3.getSquares())));
		
		// Brown
		Set<GameSquare> resultSet5 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2)));
		List<Section> parentSet5 = Arrays.asList(new Section(new HashSet<>(touchingThe11.getSquares())),
				new Section(new HashSet<>(touchingThe3.getSquares())),
				new Section(new HashSet<>(touchingThe12.getSquares())));
		
		// Purple
		Set<GameSquare> resultSet6 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 3)));
		List<Section> parentSet6 = Arrays.asList(new Section(new HashSet<>(touchingThe11.getSquares())),
				new Section(new HashSet<>(touchingThe12.getSquares())));
		
		// Orange
		Set<GameSquare> resultSet7 = new HashSet<>(Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 2),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 3)));
		List<Section> parentSet7 = Arrays.asList(new Section(new HashSet<>(touchingThe11.getSquares())));
		
		Map<List<Section>, Set<GameSquare>> expectedContents = new HashMap<>();
		
		expectedContents.put(parentSet1, resultSet1);
		expectedContents.put(parentSet2, resultSet2);
		expectedContents.put(parentSet3, resultSet3);
		expectedContents.put(parentSet4, resultSet4);
		expectedContents.put(parentSet5, resultSet5);
		expectedContents.put(parentSet6, resultSet6);
		expectedContents.put(parentSet7, resultSet7);
		
		return new SectionTestScenario(Arrays.asList(section), expectedContents, expectedResults);
	}
	
	// TODO:
	// Here: https://math.stackexchange.com/questions/3466402/calculating-minesweeper-odds-is-this-calculation-correct
	private static SectionTestScenario getScenarioSpecial02() {
		Section section1 = GameBoardTestData.SPECIAL_SCENARIO_03.getExpectedSections().get(0);
		Section section2 = GameBoardTestData.SPECIAL_SCENARIO_03.getExpectedSections().get(1);
		
		// Green
		final GameSquare A = new GameSquare("A", SquareValue.BLANK_UNTOUCHED, 4, 3);
		final GameSquare B = new GameSquare("B", SquareValue.BLANK_UNTOUCHED, 5, 3);
		final GameSquare F = new GameSquare("F", SquareValue.BLANK_UNTOUCHED, 4, 4);
		final GameSquare I = new GameSquare("I", SquareValue.BLANK_UNTOUCHED, 4, 5);
		
		// Pink
		final GameSquare C = new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 6, 3);
		
		// Yellow
		final GameSquare J = new GameSquare("J", SquareValue.BLANK_UNTOUCHED, 5, 5);
		
		// Brown
		final GameSquare G = new GameSquare("G", SquareValue.BLANK_UNTOUCHED, 6, 4);
		
		// Orange
		final GameSquare D = new GameSquare("D", SquareValue.BLANK_UNTOUCHED, 7, 3);
		final GameSquare E = new GameSquare("E", SquareValue.BLANK_UNTOUCHED, 8, 3);
		final GameSquare H = new GameSquare("H", SquareValue.BLANK_UNTOUCHED, 8, 4);
		final GameSquare L = new GameSquare("L", SquareValue.BLANK_UNTOUCHED, 8, 5);
		
		// Purple
		final GameSquare K = new GameSquare("K", SquareValue.BLANK_UNTOUCHED, 7, 5);
		
		// Light blue
		final GameSquare M = new GameSquare("M", SquareValue.BLANK_UNTOUCHED, 5, 6);
		final GameSquare N = new GameSquare("N", SquareValue.BLANK_UNTOUCHED, 6, 6);
		final GameSquare O = new GameSquare("O", SquareValue.BLANK_UNTOUCHED, 7, 6);
		
		
		// Dark blue
		final GameSquare P = new GameSquare("P", SquareValue.BLANK_UNTOUCHED, 11, 2);
		final GameSquare T = new GameSquare("T", SquareValue.BLANK_UNTOUCHED, 11, 3);
		final GameSquare V = new GameSquare("V", SquareValue.BLANK_UNTOUCHED, 11, 4);
		
		// Beige
		final GameSquare R = new GameSquare("R", SquareValue.BLANK_UNTOUCHED, 13, 2);
		final GameSquare X = new GameSquare("X", SquareValue.BLANK_UNTOUCHED, 13, 4);
		final GameSquare W = new GameSquare("W", SquareValue.BLANK_UNTOUCHED, 12, 4);
		final GameSquare Q = new GameSquare("Q", SquareValue.BLANK_UNTOUCHED, 12, 2);
		
		// Red
		final GameSquare S = new GameSquare("S", SquareValue.BLANK_UNTOUCHED, 14, 2);
		final GameSquare U = new GameSquare("U", SquareValue.BLANK_UNTOUCHED, 14, 3);
		final GameSquare Y = new GameSquare("Y", SquareValue.BLANK_UNTOUCHED, 14, 4);
		
		// (A+B+F+I) + (C) + (G) + (J) = 3
		List<GameSquare> gameSquareResults1 = new ArrayList<GameSquare>();
		gameSquareResults1.add(A);
		gameSquareResults1.add(B);
		gameSquareResults1.add(F);
		gameSquareResults1.add(I);
		gameSquareResults1.add(C);
		gameSquareResults1.add(J);
		gameSquareResults1.add(G);
		
		// (D+E+H+L) (C) + (G) + (K) = 1
		List<GameSquare> gameSquareResults2 = new ArrayList<GameSquare>();
		gameSquareResults2.add(D);
		gameSquareResults2.add(E);
		gameSquareResults2.add(H);
		gameSquareResults2.add(L);
		gameSquareResults2.add(C);
		gameSquareResults2.add(G);
		gameSquareResults2.add(K);
		
		// (M+N+O) + (J) + (K) + (G)         =        1
		List<GameSquare> gameSquareResults3 = new ArrayList<GameSquare>();
		gameSquareResults3.add(M);
		gameSquareResults3.add(N);
		gameSquareResults3.add(O);
		gameSquareResults3.add(J);
		gameSquareResults3.add(K);
		gameSquareResults3.add(G);
		
		// (P+T+V) + (RXWQ)                  =        2
		List<GameSquare> gameSquareResults4 = new ArrayList<GameSquare>();
		gameSquareResults4.add(P);
		gameSquareResults4.add(T);
		gameSquareResults4.add(V);
		gameSquareResults4.add(R);
		gameSquareResults4.add(X);
		gameSquareResults4.add(W);
		gameSquareResults4.add(Q);
		
		// (S+U+Y) + (RXWQ)                  =        1
		List<GameSquare> gameSquareResults5 = new ArrayList<GameSquare>();
		gameSquareResults5.add(S);
		gameSquareResults5.add(U);
		gameSquareResults5.add(Y);
		gameSquareResults5.add(R);
		gameSquareResults5.add(X);
		gameSquareResults5.add(W);
		gameSquareResults5.add(Q);

		Rule rule1 = createSweeperSet(gameSquareResults1, 3, section1);
		Rule rule2 = createSweeperSet(gameSquareResults2, 1, section1);
		Rule rule3 = createSweeperSet(gameSquareResults3, 1, section1);
		Rule rule4 = createSweeperSet(gameSquareResults4, 2, section2);
		Rule rule5 = createSweeperSet(gameSquareResults5, 1, section2);
		
		List<Rule> expectedResults = Arrays.asList(
				rule1,
				rule2,
				rule3,
				rule4,
				rule5
				);
		
		// Green
		Set<GameSquare> resultSet1 = new HashSet<>(Arrays.asList(A,B,F,I));
		List<Section> parentSet1 = Arrays.asList(new Section(new HashSet<>(rule1.getSquares())));
		
		// Yellow
		Set<GameSquare> resultSet2 = new HashSet<>(Arrays.asList(J));
		List<Section> parentSet2 = Arrays.asList(new Section(new HashSet<>(rule1.getSquares())),
				new Section(new HashSet<>(rule3.getSquares())));
		
		// Light-blue
		Set<GameSquare> resultSet3 = new HashSet<>(Arrays.asList(M,N,O));
		List<Section> parentSet3 = Arrays.asList(new Section(new HashSet<>(rule3.getSquares())));
		
		// Pink
		Set<GameSquare> resultSet4 = new HashSet<>(Arrays.asList(C));
		List<Section> parentSet4 = Arrays.asList(new Section(new HashSet<>(rule2.getSquares())),
				new Section(new HashSet<>(rule1.getSquares())));
		
		// Brown
		Set<GameSquare> resultSet5 = new HashSet<>(Arrays.asList(G));
		List<Section> parentSet5 = Arrays.asList(new Section(new HashSet<>(rule2.getSquares())),
				new Section(new HashSet<>(rule1.getSquares())),
				new Section(new HashSet<>(rule3.getSquares())));
		
		// Purple
		Set<GameSquare> resultSet6 = new HashSet<>(Arrays.asList(K));
		List<Section> parentSet6 = Arrays.asList(new Section(new HashSet<>(rule2.getSquares())),
				new Section(new HashSet<>(rule3.getSquares())));
		
		// Orange
		Set<GameSquare> resultSet7 = new HashSet<>(Arrays.asList(D,E,H,L));
		List<Section> parentSet7 = Arrays.asList(new Section(new HashSet<>(rule2.getSquares())));
		
		// Dark-blue
		Set<GameSquare> resultSet8 = new HashSet<>(Arrays.asList(P,T,V));
		List<Section> parentSet8 = Arrays.asList(new Section(new HashSet<>(rule4.getSquares())));
		
		// Beige
		Set<GameSquare> resultSet9 = new HashSet<>(Arrays.asList(Q, R, W, X));
		List<Section> parentSet9 = Arrays.asList(new Section(new HashSet<>(rule4.getSquares())), new Section(new HashSet<>(rule5.getSquares())));
		
		// Red
		Set<GameSquare> resultSet10 = new HashSet<>(Arrays.asList(S,U,Y));
		List<Section> parentSet10 = Arrays.asList(new Section(new HashSet<>(rule5.getSquares())));
		
		Map<List<Section>, Set<GameSquare>> expectedContents = new HashMap<>();
		
		expectedContents.put(parentSet1, resultSet1);
		expectedContents.put(parentSet2, resultSet2);
		expectedContents.put(parentSet3, resultSet3);
		expectedContents.put(parentSet4, resultSet4);
		expectedContents.put(parentSet5, resultSet5);
		expectedContents.put(parentSet6, resultSet6);
		expectedContents.put(parentSet7, resultSet7);
		expectedContents.put(parentSet8, resultSet8);
		expectedContents.put(parentSet9, resultSet9);
		expectedContents.put(parentSet10, resultSet10);
		
		return new SectionTestScenario(Arrays.asList(section1, section2), expectedContents, expectedResults);
	}
	
	private static Rule createSweeperSet(List<GameSquare> gameSquares, int expectedNumberOfMines, Section section) {
		return new Rule(gameSquares, expectedNumberOfMines, section);
	}
}
