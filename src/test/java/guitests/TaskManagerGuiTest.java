package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import savvytodo.TestApp;
import savvytodo.commons.core.EventsCenter;
import savvytodo.commons.events.BaseEvent;
import savvytodo.model.TaskManager;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.testutil.TestUtil;
import savvytodo.testutil.TypicalTestTasks;

/**
 * A GUI Test class for TaskManager.
 */
public abstract class TaskManagerGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTasks td = new TypicalTestTasks();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle floatingTaskListPanel;
    protected TaskListPanelHandle eventTaskListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    protected BrowserPanelHandle browserPanel;
    protected StatusBarFooterHandle statusBarHandle;
    protected GuiRobot guiRobot = new GuiRobot();

    private Stage stage;

    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(guiRobot, stage);
            mainMenu = mainGui.getMainMenu();
            floatingTaskListPanel = mainGui.getTaskListPanel();
            eventTaskListPanel = mainGui.getEventTaskListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            browserPanel = mainGui.getBrowserPanel();
            statusBarHandle = mainGui.getStatusBar();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing())
            ;
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TaskManager getInitialData() {
        TaskManager ab = new TaskManager();
        TypicalTestTasks.loadTaskManagerWithSampleData(ab);
        return ab;
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }
    //@@author A0147827U
    /**
     * Asserts the total size of the both task lists is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfTasks = floatingTaskListPanel.getNumberOfTasks() + eventTaskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    /**
     * Asserts the size of the event task list is equal to the given number.
     */
    protected void assertEventListSize(int size) {
        int numberOfTasks = eventTaskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    /**
     * Asserts the size of the floating task list is equal to the given number.
     */
    protected void assertFloatingListSize(int size) {
        int numberOfTasks = floatingTaskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    //@@author
    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }

    public void raise(BaseEvent e) {
        // JUnit doesn't run its test cases on the UI thread. Platform.runLater
        // is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }

    // @@author A0140036X
    /**
     * Simulates a delay.
     * @param milliseconds Time to sleep in milliseconds
     */
    public void sleep(int milliseconds) {
        guiRobot.sleep((milliseconds));
    }

    //@@author A0140036X
    /**
     * Simulates a very long delay
     */
    public void sleep() {
        guiRobot.sleep(200000);
    }
}
