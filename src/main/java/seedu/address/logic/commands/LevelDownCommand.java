package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.functions.PersonLevelDownFunction;

public class LevelDownCommand extends Command {
    public static final String COMMAND_WORD = "leveldown";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Demotes all students by one level."
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Demoted all students by one level";
    /**
     * Creates a LevelDown object.
     */
    public LevelDownCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateTransformedPersonList(new PersonLevelDownFunction());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LevelUpCommand); // instanceof handles nulls
    }
}


