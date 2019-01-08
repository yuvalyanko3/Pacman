package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import controller.SysData;
import model.Question;

/**
 * Tests to check correct answer
 */
public class AnswerTest {
	SysData sd = SysData.getInstance();
	
	@Test
	public void checkCorrectAnswer() {
		Question q = sd.addRandomQuestion();
		String a = q.getCorrectAnswer(); // correct answer
		assertTrue(q.isCorrect(a)); 
	}
	
	@Test
	public void checkIncorrectAnswer() {
		Question q = sd.addRandomQuestion();
		String a = "0"; // incorrect answer
		assertFalse(q.isCorrect(a)); 
	}
}
