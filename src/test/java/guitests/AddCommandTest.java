package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import savvytodo.commons.core.Messages;
import savvytodo.logic.commands.AddCommand;
import savvytodo.model.task.TaskType;
import savvytodo.testutil.TestTask;
import savvytodo.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    //@@author A0140016B
    @Test
    public void addContinuousTesting() {
        TestTask[] currentList = td.getTypicalTasks();

        //add one event task
        TestTask taskToAdd = td.discussion;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another event task
        taskToAdd = td.interview;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate event task
        commandBox.runCommand(td.discussion.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(eventTaskListPanel.isListMatching(currentList));

        //add conflicting event task
        taskToAdd = td.job;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.presentation);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author A0147827U
    @Test
    public void addFloatingTask() {
        TestTask[] currentList = { };
        //add one floating task
        TestTask taskToAdd = td.floating1;
        assertAddSuccess(taskToAdd, currentList);
        System.out.println(taskToAdd.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate floating task
        commandBox.runCommand(td.floating1.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(floatingTaskListPanel.isListMatching(currentList));

        //add one more floating task
        taskToAdd = td.floating2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

    }
    //@@author A0140016B
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        TaskListPanelHandle targetList;
        if (taskToAdd.getType().getType() == TaskType.EVENT) {
            targetList = eventTaskListPanel;
        } else {
            targetList = floatingTaskListPanel;
        }
        //confirm the new card contains the right data
        TaskCardHandle addedCard = targetList.navigateToTask(taskToAdd.getName().name);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(targetList.isListMatching(expectedList));
    }

}
