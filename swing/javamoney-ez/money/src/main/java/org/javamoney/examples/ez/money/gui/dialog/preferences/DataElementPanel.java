// DataElementPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.model.DataCollection;

import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This is the base class for all panels that manage elements.
 */
abstract
class
DataElementPanel
extends PreferencesPanel
{
  /**
   * Constructs a new preferences panel with the specified key.
   *
   * @param key The panel's key.
   */
  protected
  DataElementPanel(PreferencesKeys key)
  {
    super(key);

    setCardPanel(new JPanel(new CardLayout()));
    setChooser(new PreferencesDataElementChooser(key));

    // Add listeners.
    getChooser().addMouseListener(new MouseHandler());
  }

  /**
   * This method prompts the user for a new unique identifier.
   */
  protected
  abstract
  void
  edit();

  /**
   * This method creates a panel with common options for managing elements:
   * New, Edit, and Remove.
   *
   * @param listener The action listener for receiving action events.
   *
   * @return A panel with common options for managing elements.
   */
  protected
  final
  Panel
  createButtonPanel(ActionListener listener)
  {
    JToolBar toolBar = new JToolBar();
    Panel panel = new Panel();
    Dimension separator = new Dimension(15, 10);

    createLinks(listener);

    // Build tool bar.
    toolBar.setFloatable(false);

    toolBar.add(getLinks()[LINK_ADD]);
    toolBar.addSeparator(separator);
    toolBar.add(getLinks()[LINK_REMOVE]);
    toolBar.addSeparator(separator);
    toolBar.add(getLinks()[LINK_EDIT]);

    if(getKey() == PreferencesKeys.EXPENSES || getKey() == PreferencesKeys.INCOME)
    {
      toolBar.addSeparator(separator);
      toolBar.add(getLinks()[LINK_ADD_SUB]);
      toolBar.addSeparator(separator);
      toolBar.add(getLinks()[LINK_CHANGE_GROUP]);
    }

    // Build panel.
    panel.addEmptyCellAt(0, 0);
    panel.add(toolBar, 0, 1, 1, 1, 100, 100);
    panel.addEmptyCellAt(0, 2);

    return panel;
  }

  /**
   *  This method creates a panel that contains two additional panels. The first
   *  additional panel contains the element chooser within a scrollable
   *  view. The second additional panel contains a button for creating a new
   *  element. This is used for when there are no elements to choose from. When
   *  the button is clicked, the specified listener is notified.
   *
   *  @param listener The action listener for receiving action events.
   *
   * @return A panel with the element chooser within a scrollable view.
   */
  protected
  final
  Panel
  createChooserPanel(ActionListener listener)
  {
    Panel panel = new Panel();

    // Add cards to panel.
    getCardPanel().add(new ScrollPane(getChooser()), CARD_CHOOSER);
    getCardPanel().add(createNewPanel(listener), CARD_ADD);

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(getCardPanel(), 0, 0, 1, 1, 100, 100);

    panel.setInsets(new Insets(10, 10, 5, 10));

    // Show the correct card panel.
    showProperChooserPanel();

    return panel;
  }

  /**
   * This method will enable or disable the links depending on whether or not
   * there are any elements available in the chooser.
   */
  protected
  final
  void
  enableLinks()
  {
    boolean enable = getChooser().length() != 0;

    for(Link link : getLinks())
    {
      link.setEnabled(enable);
    }
  }

  /**
   * This method returns this panel's element chooser.
   *
   * @return This panel's element chooser.
   */
  protected
  final
  PreferencesDataElementChooser
  getChooser()
  {
    return itsChooser;
  }

  /**
   * This method returns this panel's element collection.
   *
   * @return This panel's element collection.
   */
  protected
  final
  DataCollection
  getCollection()
  {
    return getChooser().getCollection();
  }

  /**
   * This method shows either the panel that contains the element chooser or
   * the panel for creating a new element.
   * <p>
   * <b>Note:</b> This method should be called every time a change to the
   * collection occurs.
   */
  protected
  final
  void
  showProperChooserPanel()
  {
    if(getCollection().size() == 0)
    {
      ((CardLayout)getCardPanel().getLayout()).show(getCardPanel(), CARD_ADD);
    }
    else
    {
      ((CardLayout)getCardPanel().getLayout()).show(getCardPanel(), CARD_CHOOSER);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  final
  Link
  createLink(String command, ActionListener listener)
  {
    Link link = new Link();

    // Build link.
    buildButton(link, command, listener);

    return link;
  }

  private
  void
  createLinks(ActionListener listener)
  {
    itsLinks = new Link[5];

    getLinks()[LINK_ADD] = createLink(ACTION_ADD, listener);
    getLinks()[LINK_ADD_SUB] = createLink(ACTION_ADD_SUB, listener);
    getLinks()[LINK_EDIT] = createLink(ACTION_EDIT, listener);
    getLinks()[LINK_CHANGE_GROUP] = createLink(ACTION_CHANGE_GROUP, listener);
    getLinks()[LINK_REMOVE] = createLink(ACTION_REMOVE, listener);

    enableLinks();
  }

  private
  Panel
  createNewPanel(ActionListener listener)
  {
    Panel panel = new Panel();
    String key = getCollection().getType().toString().toLowerCase();
    Link link = new Link(getProperty("default") + " " + key, listener, ACTION_ADD);

    // Build link.
    link.setBackground(GUIConstants.COLOR_BACKGROUND_FILL);

    // Build panel.
    panel.setAnchor(GridBagConstraints.NORTHWEST);
    panel.add(link, 0, 0, 1, 1, 100, 100);

    panel.setBackground(GUIConstants.COLOR_BACKGROUND_FILL);
    panel.setBorder(createTitledBorder(""));

    return panel;
  }

  private
  JPanel
  getCardPanel()
  {
    return itsCardPanel;
  }

  private
  Link[]
  getLinks()
  {
    return itsLinks;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("DataElementPanel." + key);
  }

  private
  void
  setCardPanel(JPanel panel)
  {
    itsCardPanel = panel;
  }

  private
  void
  setChooser(PreferencesDataElementChooser chooser)
  {
    itsChooser = chooser;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  MouseHandler
  extends MouseAdapter
  {
    @Override
    public
    void
    mouseClicked(MouseEvent event)
    {
      if(event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 2)
      {
        // Make sure the user double-clicked on an element.
        if(getChooser().getRowForLocation(event.getX(), event.getY()) != -1)
        {
          edit();
        }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JPanel itsCardPanel;
  private PreferencesDataElementChooser itsChooser;
  private Link[] itsLinks;

  private static final int LINK_ADD = 0;
  private static final int LINK_ADD_SUB = 1;
  private static final int LINK_CHANGE_GROUP = 2;
  private static final int LINK_EDIT = 3;
  private static final int LINK_REMOVE = 4;

  private static final String CARD_ADD = "Add";
  private static final String CARD_CHOOSER = "Chooser";

  /**
   * The command to add a new element.
   */
  protected static final String ACTION_ADD = getProperty("add");
  /**
   * The command to add a new subcategory.
   */
  protected static final String ACTION_ADD_SUB = getProperty("sub");
  /**
   * The command to change an existing element.
   */
  protected static final String ACTION_CHANGE_GROUP = getProperty("group");
  /**
   * The command to edit an existing element.
   */
  protected static final String ACTION_EDIT = I18NHelper.getSharedProperty("edit");
  /**
   * The command to remove an element.
   */
  protected static final String ACTION_REMOVE = I18NHelper.getSharedProperty("remove");
}
