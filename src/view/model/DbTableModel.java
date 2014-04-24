package view.model;

/**
 * Interface for the sake of having a super type for some Table Model and
 * declaring some common methods.
 */
public interface DbTableModel {

	void delete(int selectedRow);

	void add();

}