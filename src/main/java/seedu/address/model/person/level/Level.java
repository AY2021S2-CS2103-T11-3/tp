package seedu.address.model.person.level;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.level.LevelList.LEVEL_LIST;

import java.util.Optional;
import java.util.Set;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.School;
import seedu.address.model.subject.Subject;

/**
 * Represents a Student's education level in TutorsPet.
 * Guarantees: immutable; is valid as declared in {@link #isValidLevel(String)}
 */
public class Level implements Comparable<Level> {

    public static final String MESSAGE_CONSTRAINTS =
            "Level can only be [pri1] to [pri6], [sec1] to [sec4] or [jc1] to [jc2].";

    private String level;
    private int levelIndex;

    /**
     * Constructs a {@code Level}.
     *
     * @param level A valid education level.
     */
    public Level(String level) {
        requireNonNull(level);
        String editedLevel = level.trim().toLowerCase();
        checkArgument(isValidLevel(editedLevel), MESSAGE_CONSTRAINTS);
        this.level = editedLevel;
        this.levelIndex = assignLevelIndex();
    }

    Level(String level, int index) {
        this.level = level;
        this.levelIndex = index;
    }

    public String getLevel() {
        return level;
    }

    public int getLevelIndex() {
        return levelIndex;
    }

    /**
     * Returns true if a given string is a valid school.
     */
    public static boolean isValidLevel(String test) {
        // ArrayList<String> levelList = new LevelList().LEVEL_LIST;
        boolean result = false;
        for (int i = 1; i < 12; i++) {
            if (test.equals(LEVEL_LIST.get(i))) {
                result = true;
            }
        }
        return result;

        // return LevelList.isValidLevel(test);
    }

    /**
     * Returns an {@code Index} that corresponds to the {@level} string input by the
     * user.
     */
    private int assignLevelIndex() {
        int result = 0;
        for (int i = 1; i < 12; i++) {
            if (level.equals(LEVEL_LIST.get(i))) {
                result = i;
            }
        }
        return result;
    }

    /**
     * Alters {@code Level} to reflect a {@code Level} that is one grade higher.
     */
    public void levelUp() {
        int nextIndex = (levelIndex + 1) % 13;
        String nextLevel = LEVEL_LIST.get(nextIndex);
        if (nextLevel.equals("")) {
            // TODO: implement student graduation exception message
        }
        this.level = nextLevel;
        this.levelIndex = nextIndex;
        // return new Level(nextLevel, nextIndex);
    }

    /**
     * Alters {@code Level} to reflect a {@code Level} that is one grade lower.
     */
    public void levelDown() {
        int prevIndex = (levelIndex - 1) % 13;
        String prevLevel = LEVEL_LIST.get(prevIndex);
        if (prevLevel.equals("")) {
            // TODO: implement student cannot go down anymore levels
        }
        this.level = prevLevel;
        this.levelIndex = prevIndex;
        // return new Level(nextLevel, nextIndex);
    }

    /**
     * Returns a {@code Person} with all the same attributes as the input
     * {@code Person}
     */
    public static Person clonePerson (Person person) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Optional<School> school = person.getSchool();
        Optional<Email> email = person.getEmail();
        Optional<Address> address = person.getAddress();
        Optional<Name> guardianName = person.getGuardianName();
        Optional<Phone> guardianPhone = person.getGuardianPhone();
        Optional<Level> level = person.getLevel();
        Set<Subject> subjects = person.getSubjects();
        Set<Lesson> lessons = person.getLessons();

        return new Person(name, phone, school, email, address, guardianName, guardianPhone, level,
                subjects, lessons);
    }

    /**
     * Returns a {@code Person} with all the same attributes as the input
     * {@code Person} except for the {@code Level}
     */
    public static Person changeLevel(Person person, Optional<Level> newLevel) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Optional<School> school = person.getSchool();
        Optional<Email> email = person.getEmail();
        Optional<Address> address = person.getAddress();
        Optional<Name> guardianName = person.getGuardianName();
        Optional<Phone> guardianPhone = person.getGuardianPhone();
        Optional<Level> level = newLevel;
        Set<Subject> subjects = person.getSubjects();
        Set<Lesson> lessons = person.getLessons();

        return new Person(name, phone, school, email, address, guardianName, guardianPhone, level,
                subjects, lessons);
    }

    @Override
    public String toString() {
        return level;
    }

    @Override
    public int compareTo(Level other) {
        String thisLevel = this.level;
        String otherLevel = other.level;
        return thisLevel.compareTo(otherLevel);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Level // instanceof handles nulls
                && level.equals(((Level) other).level)); // state check
    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }

}
