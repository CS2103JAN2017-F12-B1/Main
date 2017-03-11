package savvytodo.testutil;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.TaskManager;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask assignment, appointment, birthday, meeting, test, presentation, project, discussion, interview;

    public TypicalTestTasks() {
        try {
            assignment = new TaskBuilder().withName("Assignment 1")
                    .withLocation("None").withDescription("2359 Mon 23 Aug")
                    .withPriority("high")
                    .withCategories("friends").build();
            appointment = new TaskBuilder().withName("dental").withLocation("KTPH")
                    .withDescription("Wisdom tooth").withPriority("high")
                    .withCategories("owesMoney", "friends").build();
            birthday = new TaskBuilder().withName("My birthday").withPriority("medium")
                    .withDescription("Celebration @ 1pm").withLocation("wall street").build();
            meeting = new TaskBuilder().withName("CS2103 Project Meeting").withPriority("medium")
                    .withDescription("cornelia@google.com").withLocation("10th street").build();
            test = new TaskBuilder().withName("CS2103 midterm test").withPriority("high")
                    .withDescription("2pm Mon 23 Aug").withLocation("NUS MPSH 4").build();
            presentation = new TaskBuilder().withName("Informal Presentation").withPriority("low")
                    .withDescription("3pm").withLocation("NUS HALL").build();
            project = new TaskBuilder().withName("Project Milestone 2").withPriority("high")
                    .withDescription("anna@google.com").withLocation("4th street").build();

            // Manually added
            discussion = new TaskBuilder().withName("CS2103 Project Discussion").withPriority("low")
                    .withDescription("stefan@mail.com").withLocation("NUS MALL").build();
            interview = new TaskBuilder().withName("Google Interview").withPriority("medium")
                    .withDescription("Prepare for interview questions").withLocation("Google SG").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addCategory(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{assignment, appointment, birthday, meeting, test, presentation, project};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
