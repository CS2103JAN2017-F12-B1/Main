# Savvy To-Do - User Guide

By : `Team F12-B1`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)
5. [Keyboard Shortcuts](#5-keyboard-shortcuts)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `SavvyToDo.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Savvy To-Do.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01` :
     adds a contact named `John Doe` to the Savvy To-Do.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task: `add`

Adds a task to the Savvy To-Do<br>
Format: `add TASK_NAME [s/START_DATE] [e/END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`

> Parameters | Description
> -------- | :--------
> TASK_NAME | `Mandatory` Specifies the name of the task.
> START_DATE (See [DATE](#date)) | `Optional` Specifies the starting date and time of the task.
> END_DATE (See [DATE](#date)) | `Optional` Specifies the ending date and time of the task.
> LOCATION | `Optional` Specifies the location where the task happens.
> PRIORITY_LEVEL | `Optional` Specifies the priority level of the task.<br>`Accepts` values `low`, `medium`, `high`<br>`Defaults` to `medium`
> RECURRING_TYPE | `Optional` Specifies the recurring type of the task.<br>`Accepts` values `none`, `daily`, `weekly`, `monthly`, `yearly`<br>`Defaults` to `none`
> NUMBER_OF_RECURRENCE | `Optional` Specifies the number of times the task recurrs. <br>`Defaults` to `1`<br>`Ignored` if RECURRING_TYPE is `none`
> CATEGORY | `Optional` Specifies a custom category for the task. This can be used for keeping track of similar tasks.
> DESCRIPTION | `Optional` Describes the task.

##### Date

> If only the DATE is specified, the TIME defaults to starting at 12am or ending at 11:59pm.<br>If only the TIME is specified, the DATE defaults to today.<br><br>If only `START_DATE` is supplied, the task will be a 1-day event starting from the specified `START_DATE` and ending on the same day at 11:59pm.<br>If only `END_DATE` is supplied, the task will start today at 12am.<br><br>The date and time can be entered in a formal format like <i>17-03-2016</i>, or a natural format like <i>next wednesday, 2pm</i>. The formal format follows the system's settings for whether <i>mm-dd-yyyy</i> or <i>dd-mm-yyyy</i> is used.

Examples:
* `add Project Meeting s/05-10-2016 2pm e/6pm r/daily n/2 c/CS2103 d/Discuss about roles and milestones` <br>
  Add task named, Project Meeting, under CS2103 category. The task is schedule to take place on 5th and 6th of October 2016 from 2pm to 6pm each day.
* `add NUSSU Leadership Camp s/05-10-2016 2pm e/08-10-2016 6pm c/NUSSU`
  Add task named, NUSSU Leadership Camp, under NUSSU category. The 4 day 3 night is schedule to take place from 5th October, 2pm to 8th of October 2016, 6pm.

### 2.3. Listing all tasks : `list`

Shows a list of all tasks in the Savvy To-Do.<br>
Format: `list`

### 2.4. Editing a task : `edit`

Edits an existing task in the Savvy To-Do.<br>
Format: `edit INDEX [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@yahoo.com`<br>
  Edits the phone number and email address of the 1st task to be `91234567` and `johndoe@yahoo.com` respectively.

* `edit 2 Betsy Crower t/`<br>
  Edits the name of the 2nd task to be `Betsy Crower` and clears all existing tags.

### 2.5. Finding all tasks containing any keyword in their name: `find`

Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find John`<br>
  Returns `John Doe` but not `john`
* `find Betsy Tim John`<br>
  Returns Any task having names `Betsy`, `Tim`, or `John`

### 2.6. Deleting a task : `delete`

Deletes the specified task from the Savvy To-Do. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the Savvy To-Do.
* `find Betsy`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Select a task : `select`

Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the Savvy To-Do.
* `find Betsy` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all entries from the Savvy To-Do.<br>
Format: `clear`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

Savvy To-Do data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Savvy To-Do folder.

## 4. Command Summary

* **Add**  `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...` <br>
  e.g. `add James Ho p/22224444 e/jamesho@gmail.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

* **Clear** : `clear`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find `

* **List** : `list` <br>
  e.g.

* **Help** : `help` <br>
  e.g.

* **Select** : `select INDEX` <br>
  e.g.`select 2`


[//]: # (@@author yeejfe)

## 5. Keyboard Shortcuts

Key Codes | Function | Command Box Input
-------- | :--------  | :--------
<kbd>Esc</kbd> | Toggle to show/hide a list of keyboard shortcuts | -
<kbd>Ctrl</kbd> + <kbd>H</kbd> | [Help](#viewing-help--help) | `help`
<kbd>Ctrl</kbd> + <kbd>Q</kbd> | [Exit](#exiting-the-program--exit) | `exit`
<kbd>Ctrl</kbd> + <kbd>D</kbd> | [Clear](#clearing-all-entries--clear) all entries | `clear`
<kbd>Ctrl</kbd> + <kbd>L</kbd> | [List](#listing-all-tasks-list) all unmarked task by date, earliest task first | `list`
<kbd>Ctrl</kbd> + <kbd>P</kbd> | [List](#listing-all-tasks-list) all unmarked task by priority level, highest to lowest | `list priorityLevel`
<kbd>Ctrl</kbd> + <kbd>S</kbd> | [Storage](#storage-location) Popups a directory chooser dialog box to choose a new filepath | `storage NEW_FILEPATH`
<kbd>Ctrl</kbd> + <kbd>Z</kbd> | [Undo](#undo-the-most-recent-operation--undo) | `undo`