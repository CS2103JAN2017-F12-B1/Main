package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.Priority;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Status;


//@@author A0140016
/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATETIME_RECUR_FIELD_ID = "#dateTime";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String CATEGORIES_FIELD_ID = "#categories";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDateTimeRecur() {
        return getTextFromLabel(DATETIME_RECUR_FIELD_ID);
    }

    public String getPriority() {
        return Priority.getPriorityValue(getTextFromLabel(PRIORITY_FIELD_ID));
    }

    public String getStatus() {
        String status = getTextFromLabel(STATUS_FIELD_ID);
        if (status.contains(Status.MESSAGE_STATUS_COMPLETED)) {
            return Status.MESSAGE_STATUS_COMPLETED;
        }
        return Status.MESSAGE_STATUS_ONGOING;
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public List<String> getCategories() {
        return getCategories(getCategoriesContainer());
    }

    private List<String> getCategories(Region categoriesContainer) {
        return categoriesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getCategories(UniqueCategoryList categories) {
        return categories
                .asObservableList()
                .stream()
                .map(category -> category.categoryName)
                .collect(Collectors.toList());
    }

    private Region getCategoriesContainer() {
        return guiRobot.from(node).lookup(CATEGORIES_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
        return getFullName().equals(task.getName().name)
                && getPriority().equals(task.getPriority().value)
                && getDescription().equals(task.getDescription().value)
                && getStatus().equals(task.isCompleted().toString())
                && getCategories().equals(getCategories(task.getCategories()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getPriority().equals(handle.getPriority())
                    && getDescription().equals(handle.getDescription())
                    && getStatus().equals(handle.getStatus())
                    && getCategories().equals(handle.getCategories());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getStatus();
    }

}
