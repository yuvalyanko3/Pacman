package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

import controller.SysData;

/**
 * Tests to add / update questions
 */
public class AddQuesTest {
	SysData sd = SysData.getInstance();
	
	@Test
	public void addNewQuestion() {
		String q = "What includes life cycle of software?";
		ArrayList<String> a = new ArrayList<>();
		a.add("Requirements, Development, Testing, Implementation");
		a.add("Development");
		a.add("Requirements, Development ,Implementation");
		a.add("Requirements, Development, Testing");
		String level = "2";
		String correct = "1";
		assertTrue(sd.addQuestion(q, a, correct, level)); 
	}
	
	@Test
	public void addExistingQuestion() {
		String q = "What includes life cycle of software?";
		ArrayList<String> a = new ArrayList<>();
		a.add("Requirements, Development, Testing, Implementation");
		a.add("Development");
		a.add("Requirements, Development ,Implementation");
		a.add("Requirements, Development, Testing");
		String level = "2";
		String correct = "1";
		assertTrue(sd.addQuestion(q, a, correct, level)); 
	}
	
	@Test
	public void addQuestionWithoutAnswers() {
		String q = "What includes life cycle of software?";
		ArrayList<String> a = new ArrayList<>();
		String level = "2";
		String correct = "1";
		assertFalse(sd.addQuestion(q, a, correct, level)); 
	}
	
	@Test
	public void addQuestionEmptyFields() {
		ArrayList<String> a = new ArrayList<>();
		a.add("Requirements, Development, Testing, Implementation");
		a.add("Development");
		a.add("Requirements, Development ,Implementation");
		a.add("Requirements, Development, Testing");
		// all the options
		assertFalse(sd.addQuestion("", a, "2", "1")); 
		assertFalse(sd.addQuestion("test", a, "", "1")); 
		assertFalse(sd.addQuestion("test", a, "2", "")); 
	}
}
