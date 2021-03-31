package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GUARDIAN_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GUARDIAN_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.subject.Subject;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_SCHOOL, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_GUARDIAN_NAME, PREFIX_GUARDIAN_PHONE, PREFIX_LEVEL,
                        PREFIX_SUBJECT, PREFIX_LESSON);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_SCHOOL).isPresent()) {
            editPersonDescriptor.setSchool(ParserUtil.parseSchool(argMultimap.getValue(PREFIX_SCHOOL).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_GUARDIAN_NAME).isPresent()) {
            editPersonDescriptor.setGuardianName(ParserUtil.parseGuardianName(argMultimap
                    .getValue(PREFIX_GUARDIAN_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_GUARDIAN_PHONE).isPresent()) {
            editPersonDescriptor.setGuardianPhone(ParserUtil.parseGuardianPhone(argMultimap
                    .getValue(PREFIX_GUARDIAN_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            editPersonDescriptor.setLevel(ParserUtil.parseLevel(argMultimap
                    .getValue(PREFIX_LEVEL).get()));
        }
        parseSubjectsForEdit(argMultimap.getAllValues(PREFIX_SUBJECT)).ifPresent(editPersonDescriptor::setSubjects);
        parseLessonsForEdit(argMultimap.getAllValues(PREFIX_LESSON)).ifPresent(editPersonDescriptor::setLessons);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Subject>> parseSubjectsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseSubjects(tagSet));
    }

    private Optional<Set<Lesson>> parseLessonsForEdit(Collection<String> lessons) throws ParseException {
        assert lessons != null;

        if (lessons.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> lessonSet = lessons.size() == 1 && lessons.contains("") ? Collections.emptySet() : lessons;
        return Optional.of(ParserUtil.parseLessons(lessonSet));
    }

}
