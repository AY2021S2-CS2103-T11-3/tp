package seedu.address.model.person.predicate;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Lesson} matches the keyword given.
 */
public class LessonDayPredicate implements Predicate<Person> {
    private final String keyword;

    public LessonDayPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        assert this.keyword != null;
        return person.getLessons().stream()
                .anyMatch(lesson -> StringUtil.containsWordIgnoreCase(this.keyword, lesson.getDayInString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonDayPredicate // instanceof handles nulls
                && keyword.equals(((LessonDayPredicate) other).keyword)); // state check
    }

}
