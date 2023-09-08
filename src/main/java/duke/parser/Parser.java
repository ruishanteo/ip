package duke.parser;

import static duke.parser.TaskParser.parseTask;

import duke.DukeException;
import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.EditCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.UnmarkCommand;

/**
 * Handles the parsing of user input commands and creates corresponding Command objects.
 * This class is responsible for interpreting user input and generating appropriate
 * Command objects based on the input.
 */
public class Parser {

    /**
     * Parses the given input and returns the corresponding Command object.
     *
     * @param input The user input to be parsed.
     * @return A Command object representing the parsed user input.
     * @throws DukeException If the input cannot be parsed or contains errors.
     */
    public static Command parse(String input) throws DukeException {
        if (input.equals("bye")) {
            return new ExitCommand();
        } else if (input.equals("list")) {
            return new ListCommand();
        } else if (input.startsWith("find")) {
            String subInput;
            try {
                subInput = input.substring(5);
                if (subInput.trim().equals("")) {
                    throw new DukeException("The keyword to find cannot be empty.");
                }
            } catch (Exception e) {
                throw new DukeException("The keyword to find cannot be empty.");
            }
            return new FindCommand(subInput);
        } else if (input.startsWith("todo") || input.startsWith("deadline") || (input.startsWith("event"))) {
            return new AddCommand(parseTask(input));
        } else if (input.startsWith("unmark")) {
            if (input.length() < 7) {
                throw new DukeException("Task number to be unmarked cannot be empty.");
            }
            String subInput = input.substring(7);
            int targetIndex;
            try {
                targetIndex = Integer.parseInt(subInput);
            } catch (NumberFormatException e) {
                throw new DukeException("Task to be unmarked must be a number.");
            }
            return new UnmarkCommand(targetIndex);

        } else if (input.startsWith("mark")) {
            if (input.length() < 5) {
                throw new DukeException("Task number to be marked cannot be empty.");
            }
            String subInput = input.substring(5);
            int targetIndex;
            try {
                targetIndex = Integer.parseInt(subInput);
            } catch (NumberFormatException e) {
                throw new DukeException("Task to be marked must be a number.");
            }
            return new MarkCommand(targetIndex);
        } else if (input.startsWith("edit")) {
            String[] split = input.split(" /new ", 2);
            if (split.length == 1) {
                throw new DukeException("Edit must have a /new.");
            } else if (split[0].length() < 5) {
                throw new DukeException("Task number to be edited cannot be empty.");
            }
            String index = split[0].substring(5);
            int targetIndex;
            try {
                targetIndex = Integer.parseInt(index);
            } catch (NumberFormatException e) {
                throw new DukeException("Task to be edited must be a number.");
            }
            String task = split[1];
            return new EditCommand(targetIndex, parseTask(task));
        } else if (input.startsWith("delete")) {
            if (input.length() < 7) {
                throw new DukeException("Task number to be deleted cannot be empty.");
            }
            String subInput = input.substring(7);
            int targetIndex;
            try {
                targetIndex = Integer.parseInt(subInput);
            } catch (NumberFormatException e) {
                throw new DukeException("Task to be deleted must be a number.");
            }
            return new DeleteCommand(targetIndex);
        } else {
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
