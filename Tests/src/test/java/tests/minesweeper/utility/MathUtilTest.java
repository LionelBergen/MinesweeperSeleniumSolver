package tests.minesweeper.utility;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utility.util.MathUtil;

public class MathUtilTest {
	@Test
	public void testAsFloat() {
		assertEquals(Double.valueOf(0.5f), Double.valueOf(MathUtil.asFloat(1, 2)));
		assertEquals(Double.valueOf(0.5f), Double.valueOf(MathUtil.asFloat(5, 10)));
	}
}
