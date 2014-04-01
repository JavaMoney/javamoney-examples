// EditTransactionsDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_FORMAT;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.IconKeys.DIALOG_EDIT_TRANSACTIONS;
import static org.javamoney.examples.ez.money.IconKeys.WARNING;
import static org.javamoney.examples.ez.money.KeywordKeys.NOT_CATEGORIZED;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.DataManager.getPayees;
import static org.javamoney.examples.ez.money.model.persisted.transaction.Transaction.MAX_PAYEE_LENGTH;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createAmountFieldEditor;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createCheckNumberFieldEditor;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createNotesFieldEditor;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.showDateDialog;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isExpense;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isTransfer;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.javamoney.examples.ez.money.gui.SelectableComponentPanel;
import org.javamoney.examples.ez.money.gui.chooser.ElementComboBoxChooser;
import org.javamoney.examples.ez.money.model.dynamic.transaction.RegisterTransaction;
import org.javamoney.examples.ez.money.model.persisted.payee.Payee;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.ClipboardMenuController;
import org.javamoney.examples.ez.common.utility.ComboBoxCompleter;
import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.examples.ez.common.utility.TextConstrainer;
import org.javamoney.moneta.Money;

/**
 * This class facilitates providing a way to edit a group of transactions.
 */
public final class EditTransactionsDialog extends ApplicationDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5752972898648767469L;

	/**
	 * Constructs a new dialog.
	 * 
	 * @param list
	 *            The transactions to edit.
	 */
	public EditTransactionsDialog(ArrayList<RegisterTransaction> list) {
		super(675, 375);

		createAmountFields();
		createCategoryChoosers();
		createCheckFields();
		createDateLinks();
		createLists();
		createNotesFields();
		createPayeeChoosers();

		organizeTransactions(list);

		buildPanel();
	}

	/**
	 * This method shows a dialog for editing a group of transactions. This
	 * method returns true if the dialog was accepted, otherwise false.
	 * 
	 * @return true or false.
	 */
	public boolean showDialog() {
		runDialog();

		if (wasAccepted() == true) {
			doEditFor(EXPENSES);
			doEditFor(INCOME);
		}

		return wasAccepted();
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Start of private methods.
	// ////////////////////////////////////////////////////////////////////////////

	private void buildPanel() {
		Panel panel = getContentPane();

		// Build panel.
		panel.setFill(GridBagConstraints.BOTH);
		panel.add(createDialogHeader(), 0, 0, 1, 1, 0, 0);
		panel.add(createEditPanel(), 0, 1, 1, 1, 100, 100);
		panel.add(createOKCancelButtonPanel(new ActionHandler()), 0, 2, 1, 1,
				0, 0);
	}

	private void createAmountFields() {
		itsAmountFields = new JTextField[2];

		for (int len = 0; len < getAmountFields().length; ++len) {
			getAmountFields()[len] = createAmountFieldEditor();

			// Add listeners.
			new ClipboardMenuController(getAmountFields()[len]);
		}
	}

	private void createCategoryChoosers() {
		itsCategoryChoosers = new ElementComboBoxChooser[] {
				new ElementComboBoxChooser(getExpenses()),
				new ElementComboBoxChooser(getIncome()) };

		getCategoryChoosers()[EXPENSES].addNotCategorizedOption();
		getCategoryChoosers()[INCOME].addNotCategorizedOption();
	}

	private void createCheckFields() {
		itsCheckFields = new JTextField[2];

		for (int len = 0; len < getCheckFields().length; ++len) {
			getCheckFields()[len] = createCheckNumberFieldEditor();

			// Add listeners.
			new ClipboardMenuController(getCheckFields()[len]);
		}
	}

	private void createDateLinks() {
		ActionHandler handler = new ActionHandler();

		itsDateLinks = new Link[2];

		for (int len = 0; len < getDateLinks().length; ++len) {
			getDateLinks()[len] = new Link(UI_DATE_FORMAT.format(new Date()));

			// Add listeners.
			getDateLinks()[len].addActionListener(handler);
		}
	}

	private static DialogHeader createDialogHeader() {
		String description = getProperty("header.description");
		String title = getProperty("header.title");

		return new DialogHeader(title, description,
				DIALOG_EDIT_TRANSACTIONS.getIcon());
	}

	private Panel createEditPanel() {
		Panel panel = new Panel();
		JTabbedPane tabs = new JTabbedPane();

		// Build tabs.
		if (getLists().get(EXPENSES).size() != 0) {
			tabs.addTab(getSharedProperty("expenses"),
					createTypePanel(EXPENSES));
		}

		if (getLists().get(INCOME).size() != 0) {
			tabs.addTab(getSharedProperty("income"), createTypePanel(INCOME));
		}

		if (getLists().get(TRANSFERS).size() != 0) {
			tabs.addTab(getSharedProperty("transfers"), createTransferPanel());
		}

		// Build panel.
		panel.setFill(GridBagConstraints.BOTH);
		panel.add(tabs, 0, 0, 1, 1, 100, 100);

		panel.setInsets(new Insets(5, 5, 0, 5));

		return panel;
	}

	private void createLists() {
		itsLists = new ArrayList<ArrayList<Transaction>>();

		for (int len = 0; len < 3; ++len) {
			getLists().add(new ArrayList<Transaction>());
		}
	}

	private void createNotesFields() {
		itsNotesFields = new JTextField[2];

		for (int len = 0; len < getNotesFields().length; ++len) {
			getNotesFields()[len] = createNotesFieldEditor();

			// Add listeners.
			new ClipboardMenuController(getNotesFields()[len]);
		}
	}

	private void createPayeeChoosers() {
		itsPayeeChoosers = new ElementComboBoxChooser[2];

		for (int len = 0; len < getPayeeChoosers().length; ++len) {
			getPayeeChoosers()[len] = new ElementComboBoxChooser(getPayees());

			getPayeeChoosers()[len].clearSelection();
			getPayeeChoosers()[len].setEditable(true);
			getPayeeChoosers()[len].getTextField().setDocument(
					new TextConstrainer(MAX_PAYEE_LENGTH));

			// Add listeners.
			new ClipboardMenuController(getPayeeChoosers()[len].getTextField());
			new ComboBoxCompleter(getPayeeChoosers()[len]);
		}
	}

	private Panel createTransferPanel() {
		Panel panel = new Panel();

		// Build panel.
		panel.setAnchor(GridBagConstraints.EAST);
		panel.add(WARNING.getIcon(), 0, 0, 1, 1, 50, 100);
		panel.addEmptyCellAt(1, 0);

		panel.setAnchor(GridBagConstraints.WEST);
		panel.add(getProperty("transfer_message"), 2, 0, 1, 1, 50, 0);

		return panel;
	}

	private Panel createTypePanel(int type) {
		Panel panel = new Panel();
		String gap = ": ";
		String payee = null;
		int count = getLists().get(type).size();

		// Get correct payee text.
		if (type == EXPENSES) {
			payee = getSharedProperty("to") + gap;
		} else {
			payee = getSharedProperty("from") + gap;
		}

		// Build panel.
		panel.setAnchor(GridBagConstraints.EAST);
		panel.add(getSharedProperty("transactions") + gap, 0, 0, 1, 1, 0, 20);
		panel.add(getSharedProperty("check_number") + gap, 2, 0, 1, 1, 0, 0);
		panel.add(getSharedProperty("date") + gap, 2, 1, 1, 1, 0, 20);
		panel.add(payee, 0, 2, 1, 1, 0, 20);
		panel.add(getSharedProperty("amount") + gap, 2, 2, 1, 1, 0, 0);
		panel.add(getSharedProperty("category") + gap, 0, 3, 1, 1, 0, 20);
		panel.add(getSharedProperty("notes") + gap, 0, 4, 1, 1, 0, 20);

		panel.setAnchor(GridBagConstraints.WEST);
		panel.add("" + count, 1, 0, 1, 1, 100, 0);

		panel.setFill(GridBagConstraints.HORIZONTAL);
		panel.add(new SelectableComponentPanel(getCheckFields()[type]), 3, 0,
				1, 1, 0, 0);
		panel.add(new SelectableComponentPanel(getDateLinks()[type]), 3, 1, 1,
				1, 0, 0);
		panel.add(new SelectableComponentPanel(getPayeeChoosers()[type]), 1, 2,
				1, 1, 100, 0);
		panel.add(new SelectableComponentPanel(getAmountFields()[type]), 3, 2,
				1, 1, 0, 0);
		panel.add(new SelectableComponentPanel(getCategoryChoosers()[type]), 1,
				3, 1, 1, 0, 0);
		panel.add(new SelectableComponentPanel(getNotesFields()[type]), 1, 4,
				1, 1, 0, 0);

		// Aesthetic spacers.
		panel.addEmptyCellAt(2, 4, 10);
		panel.addEmptyCellAt(3, 4, 16);

		panel.setInsets(new Insets(5, 10, 5, 10));

		return panel;
	}

	private void doEditFor(int type) {
		String category = getCategoryChoosers()[type].getSelectedItem();
		String check = getCheckFields()[type].getText();
		String notes = getNotesFields()[type].getText();
		String payee = getPayeeChoosers()[type].getSelectedItem();
		Money amount = Money.of(Double.valueOf(0),
				UI_CURRENCY_SYMBOL.getCurrency());
		Date date = new Date();

		// Collect data.
		if (category.equals(NOT_CATEGORIZED.toString()) == true) {
			category = "";
		}

		try {
			amount = Money
					.of(UI_CURRENCY_FORMAT.parse(getAmountFields()[type]
							.getText()), UI_CURRENCY_SYMBOL.getCurrency());

			if (type == EXPENSES) {
				amount = amount.negate();
			}
		} catch (Exception exception) {
			getAmountFields()[type].setEnabled(false);
		}

		try {
			date = UI_DATE_FORMAT.parse(getDateLinks()[type].getLinkText());
		} catch (Exception exception) {
			getDateLinks()[type].setEnabled(false);
		}

		// Add payee to collection incase it is new.
		if (getPayeeChoosers()[type].isEnabled() == true && payee.length() != 0) {
			getPayees().add(new Payee(payee));
		}

		// Assign data if applicable.
		for (Transaction trans : getLists().get(type)) {
			if (getAmountFields()[type].isEnabled() == true) {
				trans.setAmount(amount);
			}

			if (getCategoryChoosers()[type].isEnabled() == true) {
				trans.setCategory(category);
			}

			if (getCheckFields()[type].isEnabled() == true) {
				trans.setCheckNumber(check);
			}

			if (getDateLinks()[type].isEnabled() == true) {
				trans.setDate(date);
			}

			if (getNotesFields()[type].isEnabled() == true) {
				trans.setNotes(notes);
			}

			if (getPayeeChoosers()[type].isEnabled() == true) {
				trans.setPayee(payee);
			}
		}
	}

	private JTextField[] getAmountFields() {
		return itsAmountFields;
	}

	private ElementComboBoxChooser[] getCategoryChoosers() {
		return itsCategoryChoosers;
	}

	private JTextField[] getCheckFields() {
		return itsCheckFields;
	}

	private Link[] getDateLinks() {
		return itsDateLinks;
	}

	private ArrayList<ArrayList<Transaction>> getLists() {
		return itsLists;
	}

	private JTextField[] getNotesFields() {
		return itsNotesFields;
	}

	private ElementComboBoxChooser[] getPayeeChoosers() {
		return itsPayeeChoosers;
	}

	private static String getProperty(String key) {
		return I18NHelper.getProperty("EditTransactionsDialog." + key);
	}

	private void organizeTransactions(ArrayList<RegisterTransaction> list) {
		for (RegisterTransaction rTrans : list) {
			Transaction trans = rTrans.getTransaction();

			if (isTransfer(trans) == true) {
				getLists().get(TRANSFERS).add(trans);
			} else if (isExpense(trans) == true) {
				getLists().get(EXPENSES).add(trans);
			} else {
				getLists().get(INCOME).add(trans);
			}
		}
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Start of inner classes.
	// ////////////////////////////////////////////////////////////////////////////

	private class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();

			if (source instanceof Link) {
				Link link = (Link) source;
				Date date = showDateDialog(new Date());

				if (date != null) {
					link.setText(UI_DATE_FORMAT.format(date));
				}
			} else {
				setAccepted(event.getActionCommand().equals(ACTION_OK));

				if (wasAccepted() == true) {
					if (decide(getProperty("confirm.title"),
							getProperty("confirm.description")) == true) {
						dispose();
					}
				} else {
					dispose();
				}
			}
		}
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Start of class members.
	// ////////////////////////////////////////////////////////////////////////////

	private JTextField[] itsAmountFields;
	private ElementComboBoxChooser[] itsCategoryChoosers;
	private JTextField[] itsCheckFields;
	private Link[] itsDateLinks;
	private ArrayList<ArrayList<Transaction>> itsLists;
	private JTextField[] itsNotesFields;
	private ElementComboBoxChooser[] itsPayeeChoosers;

	private static final int EXPENSES = 0;
	private static final int INCOME = 1;
	private static final int TRANSFERS = 2;
}
