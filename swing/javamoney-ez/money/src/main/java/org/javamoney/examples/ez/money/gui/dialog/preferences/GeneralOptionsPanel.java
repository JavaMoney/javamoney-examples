// GeneralOptionsPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import java.awt.GridBagConstraints;

/**
 * This class facilitates managing general options.
 */
public
final
class
GeneralOptionsPanel
extends PreferencesPanel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 8244153030512586124L;

/**
   * Constructs a new preferences panel.
   */
  public
  GeneralOptionsPanel()
  {
    super(PreferencesKeys.GENERAL);

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    addEmptyCellAt(0, 0);
    add(new DisplayOptionsPanel(), 0, 1, 1, 1, 100, 50);
    addEmptyCellAt(0, 2);
    add(new FileManagerPanel(), 0, 3, 1, 1, 0, 50);
  }
}
