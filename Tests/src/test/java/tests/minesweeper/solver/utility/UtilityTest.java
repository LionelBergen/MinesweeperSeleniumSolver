package tests.minesweeper.solver.utility;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.solver.utility.Utility;

public class UtilityTest {
	@Test
	public void test() {
		assertEquals(Double.valueOf(0.5f), Double.valueOf(Utility.asFloat(1, 2)));
		assertEquals(Double.valueOf(0.5f), Double.valueOf(Utility.asFloat(5, 10)));
	}
}
