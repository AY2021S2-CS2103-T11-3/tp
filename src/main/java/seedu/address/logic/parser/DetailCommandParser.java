package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DetailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DetailCommandParser implements Parser<DetailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DetailCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DetailCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    // String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailCommand.MESSAGE_USAGE), pe);
                    String.format(pe.getMessage(), DetailCommand.MESSAGE_USAGE), pe);
        }
    }

}
