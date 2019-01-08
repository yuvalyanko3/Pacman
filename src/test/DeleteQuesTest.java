package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import controller.SysData;
import model.Question;

/**
 * Tests to add / update questions
 */
public class DeleteQuesTest {
	SysData sd = SysData.getInstance();

	@Test
	public void deleteExistingQuestion() {
		Question q = sd.addRandomQuestion();
		// valid input
		assertTrue(sd.deleteQuestion(q.getQuestionString()));
	}

	@Test
	public void deleteInvalidQuestion() {
		// invalid input
		String s = "question test";
		assertFalse(sd.deleteQuestion(s));
	}
}
