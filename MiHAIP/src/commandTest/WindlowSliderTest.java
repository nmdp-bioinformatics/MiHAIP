package commandTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import command.WindowSlider;

public class WindlowSliderTest {

	@Test
	public void test() {
		WindowSlider wd = new WindowSlider();
		List<String> data = wd.slide("123456789", 5);
		assertEquals("12345", data.get(0));
		assertEquals("23456", data.get(1));
		assertEquals("34567", data.get(2));
		assertEquals("45678", data.get(3));
		assertEquals("56789", data.get(4));
	}
	
	@Test
	public void testSeq() {
		WindowSlider wd = new WindowSlider();
		List<String> data = wd.slide("abcdefghijklmn", 8);
		assertEquals("abcdefg", data.get(0));
		assertEquals("bcdefghi", data.get(1));
		assertEquals("cdefghij", data.get(2));
		assertEquals("defghijk", data.get(3));
		assertEquals("efghijkl", data.get(4));
		assertEquals("fghijklm", data.get(5));
		assertEquals("ghijklmn", data.get(6));
		
	}
}


