// ClipboardMenuController

package org.javamoney.examples.ez.common.utility;

import static java.awt.Toolkit.getDefaultToolkit;
import static java.awt.datatransfer.DataFlavor.stringFlavor;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;

import org.javamoney.examples.ez.common.gui.PopupMenu;

/**
 * This class facilitates providing text components with access to the system's
 * clipboard via a pop-up menu.
 */
public
final
class
ClipboardMenuController
{
  /**
   * Constructs a new controller.
   *
   * @param component The text component to provide access to the clipboard for.
   */
  public
  ClipboardMenuController(JTextComponent component)
  {
    PopupMenu menu = new PopupMenu();

    setTextComponent(component);

    createMenuItems();

    // Add menu items.
    menu.add(getMenuItems()[CUT]);
    menu.add(getMenuItems()[COPY]);
    menu.add(getMenuItems()[PASTE]);
    menu.addSeparator();
    menu.add(getMenuItems()[SELECT_ALL]);

    // Add listeners.
    component.addMouseListener(menu);
    menu.addPopupMenuListener(new PopupMenuHandler());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  boolean
  canPaste()
  {
    Clipboard clipboard = getDefaultToolkit().getSystemClipboard();
    Transferable contents = clipboard.getContents(null);
    String data = null;

    if(contents != null)
    {
      if(contents.isDataFlavorSupported(stringFlavor))
      {
        try
        {
          data = (String)contents.getTransferData(stringFlavor);
        }
        catch(Exception exception)
        {
          // Ignored.
        }
      }
    }

    return data != null;
  }

  private
  void
  createMenuItems()
  {
    ActionController controller = new ActionController();

    itsMenuItems = new JMenuItem[4];

    for(int len = 0; len < getMenuItems().length; ++len)
    {
      getMenuItems()[len] = new JMenuItem(getProperty(KEYS[len]));
      getMenuItems()[len].addActionListener(controller);
    }
  }

  private
  JMenuItem[]
  getMenuItems()
  {
    return itsMenuItems;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ClipboardMenuController." + key);
  }

  private
  JTextComponent
  getTextComponent()
  {
    return itsTextComponent;
  }

  private
  void
  setTextComponent(JTextComponent component)
  {
    itsTextComponent = component;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  ActionController
  implements ActionListener
  {
    public
    void
    actionPerformed(ActionEvent event)
    {
      Object source = event.getSource();

      if(source == getMenuItems()[COPY])
      {
        getTextComponent().copy();
      }
      else if(source == getMenuItems()[CUT])
      {
        getTextComponent().cut();
      }
      else if(source == getMenuItems()[PASTE])
      {
        getTextComponent().paste();
      }
      else if(source == getMenuItems()[SELECT_ALL])
      {
        getTextComponent().selectAll();
      }
    }
  }

  private
  class
  PopupMenuHandler
  implements PopupMenuListener
  {
    public
    void
    popupMenuCanceled(PopupMenuEvent event)
    {
      // Ignored.
    }

    public
    void
    popupMenuWillBecomeInvisible(PopupMenuEvent event)
    {
      // Ignored.
    }

    public
    void
    popupMenuWillBecomeVisible(PopupMenuEvent event)
    {
      boolean hasSelected = getTextComponent().getSelectedText()!= null;
      boolean hasText = getTextComponent().getText().length() != 0;

      getMenuItems()[COPY].setEnabled(hasSelected == true);
      getMenuItems()[CUT].setEnabled(hasSelected == true);
      getMenuItems()[PASTE].setEnabled(canPaste() == true);
      getMenuItems()[SELECT_ALL].setEnabled(hasText == true);

      getTextComponent().getParent().requestFocus();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JMenuItem[] itsMenuItems;
  private JTextComponent itsTextComponent;

  private static final int COPY = 0;
  private static final int CUT = 1;
  private static final int PASTE = 2;
  private static final int SELECT_ALL = 3;

  private static final String[] KEYS = {"copy", "cut", "paste", "all"};
}
