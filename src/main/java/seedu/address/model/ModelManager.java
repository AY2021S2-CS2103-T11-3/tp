package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.date.ImportantDate;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final DatesBook datesBook;
    private final UserPrefs userPrefs;
    private final FilteredList<ImportantDate> filteredImportantDates;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<ImportantDate> sortedImportantDates;
    private final SortedList<Person> sortedPersons;
    private final ObservableList<ImportantDate> transformedImportantDates;
    private final ObservableList<Person> transformedPersons;
    private Person selectedPerson;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyDatesBook datesBook) {
        super();
        requireAllNonNull(addressBook, userPrefs, datesBook);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.datesBook = new DatesBook(datesBook);
        filteredImportantDates = new FilteredList<>(this.datesBook.getImportantDatesList());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedImportantDates = new SortedList<>(this.datesBook.getImportantDatesList());
        sortedPersons = new SortedList<>(this.addressBook.getPersonList());
        transformedImportantDates = FXCollections.observableArrayList(this.datesBook.getImportantDatesList());
        transformedPersons = FXCollections.observableArrayList(this.addressBook.getPersonList());
        selectedPerson = null;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new DatesBook());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getDatesBookFilePath() {
        return userPrefs.getDatesBookFilePath();
    }

    @Override
    public void setDatesBookFilePath(Path datesBookFilePath) {
        requireNonNull(datesBookFilePath);
        userPrefs.setDatesBookFilePath(datesBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson;
    }

    @Override
    public void setSelectedPerson(Person person) {
        this.selectedPerson = person;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void filterPerson(Predicate<Person> predicate) {
        updateFilteredPersonList(predicate);
    }



    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        transformedPersons.setAll(filteredPersons);
        /*
        requireNonNull(predicate);
        FilteredList<Person> newFilteredPersons = transformedPersons.filtered(predicate);
        newFilteredPersons.setPredicate(predicate);
        transformedPersons.setAll(newFilteredPersons);
        */
    }

    //=========== Sorted Person List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the sorted list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getSortedPersonList() {
        return sortedPersons;
    }

    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) throws NullPointerException {
        requireNonNull(comparator);
        sortedPersons.setComparator(comparator);
        transformedPersons.setAll(sortedPersons);
        /*
        requireNonNull(comparator);
        SortedList<Person> newSortedPersons = transformedPersons.sorted(comparator);
        newSortedPersons.setComparator(comparator);
        transformedPersons.setAll(newSortedPersons);
         */
    }

    //=========== Transformed Person List Accessors =============================================================

    @Override
    public ObservableList<Person> getTransformedPersonList() {
        return transformedPersons;
    }

    @Override
    public void filterThenSortPersonList(Predicate<Person> predicate, Comparator<Person> comparator)
            throws NullPointerException {

        requireNonNull(comparator);
        filteredPersons.setPredicate(predicate);
        transformedPersons.setAll(filteredPersons);
        SortedList<Person> newSortedPersons = transformedPersons.sorted(comparator);
        newSortedPersons.setComparator(comparator);
        transformedPersons.setAll(newSortedPersons);
    }

    //=========== DatesBook ================================================================================

    @Override
    public void setDatesBook(ReadOnlyDatesBook datesBook) {
        this.datesBook.resetData(datesBook);
    }

    @Override
    public ReadOnlyDatesBook getDatesBook() {
        return datesBook;
    }


    @Override
    public boolean hasImportantDate(ImportantDate importantDate) {
        requireNonNull(importantDate);
        return datesBook.hasImportantDate(importantDate);
    }

    @Override
    public void deleteImportantDate(ImportantDate target) {
        datesBook.removeImportantDate(target);
        // TODO: change switch to model.updateSortedImportantDatesList(); after implementing sorting
        updateFilteredImportantDatesList(PREDICATE_SHOW_ALL_IMPORTANT_DATES);
    }

    @Override
    public void addImportantDate(ImportantDate importantDate) {
        datesBook.addImportantDate(importantDate);
        // TODO: change switch to model.updateSortedImportantDatesList(); after implementing sorting
        updateFilteredImportantDatesList(PREDICATE_SHOW_ALL_IMPORTANT_DATES);
    }

    @Override
    public void filterImportantDates(Predicate<ImportantDate> predicate) {
        updateFilteredImportantDatesList(predicate);
    }



    //=========== Filtered Important Dates List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<ImportantDate> getFilteredImportantDatesList() {
        return filteredImportantDates;
    }

    @Override
    public void updateFilteredImportantDatesList(Predicate<ImportantDate> predicate) {
        requireNonNull(predicate);
        filteredImportantDates.setPredicate(predicate);
        transformedImportantDates.setAll(filteredImportantDates);
    }

    //=========== Sorted Important Dates List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the sorted list of {@code ImportantDate}
     */
    @Override
    public ObservableList<ImportantDate> getSortedImportantDatesList() {
        return sortedImportantDates;
    }

    @Override
    public void updateSortedImportantDatesList(Comparator<ImportantDate> comparator) throws NullPointerException {
        requireNonNull(comparator);
        sortedImportantDates.setComparator(comparator);
        transformedImportantDates.setAll(sortedImportantDates);
    }

    //=========== Transformed Important Dates List Accessors ========================================================

    @Override
    public ObservableList<ImportantDate> getTransformedImportantDatesList() {
        return transformedImportantDates;
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && datesBook.equals(other.datesBook);
    }

}
