// NetworkPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.money.ApplicationProperties.getProxy;
import static org.javamoney.examples.ez.money.ApplicationProperties.setProxyAddress;
import static org.javamoney.examples.ez.money.ApplicationProperties.setProxyConnectionType;
import static org.javamoney.examples.ez.money.ApplicationProperties.setProxyPort;
import static org.javamoney.examples.ez.money.ApplicationProperties.setProxyType;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.Proxy;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.javamoney.examples.ez.common.gui.ComboBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.RadioButton;
import org.javamoney.examples.ez.common.net.ProxyConnectionTypeKeys;
import org.javamoney.examples.ez.common.net.ProxyWrapper;
import org.javamoney.examples.ez.common.utility.ClipboardMenuController;
import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.examples.ez.common.utility.TextConstrainer;

/**
 * This class facilitates configuring the network.
 */
public
final
class
NetworkPanel
extends PreferencesPanel
{
  /**
   * Constructs a new preferences panel.
   */
  public
  NetworkPanel()
  {
    super(PreferencesKeys.NETWORK);

    setProxyTypeChooser(new ComboBox(PROXY_TYPES));

    createLabels();
    createRadioButtons();
    createFields();

    initializeForm();

    buildPanel();

    // Add listeners.
    getProxyTypeChooser().addActionListener(new ActionHandler());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    // Build panel.
    setFill(GridBagConstraints.BOTH);
    addEmptyCellAt(0, 0);
    add(createProxyPanel(), 0, 1, 1, 1, 100, 100);
  }

  private
  void
  createFields()
  {
    itsFields = new JTextField[2];

    for(int len = 0; len < getFields().length; ++len)
    {
      getFields()[len] = new JTextField();

      // Add listeners.
      getFields()[len].addFocusListener(new FocusHandler());
      new ClipboardMenuController(getFields()[len]);
    }

    getFields()[MANUAL_PORT].setDocument(new TextConstrainer(5, "0123456789"));
  }

  private
  void
  createLabels()
  {
    String gap = ": ";

    itsLabels = new JLabel[3];

    getLabels()[MANUAL_ADDRESS] = new JLabel(getProperty("manual.address") + gap);
    getLabels()[MANUAL_PORT] = new JLabel(getProperty("manual.port") + gap);
    getLabels()[MANUAL_TYPE] = new JLabel(getProperty("manual.type") + gap);
  }

  private
  Panel
  createManualProxyPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.NORTHEAST);
    panel.add(getLabels()[MANUAL_ADDRESS], 0, 0, 1, 1, 75, 25);
    panel.add(getLabels()[MANUAL_PORT], 3, 0, 1, 1, 25, 0);
    panel.add(createManualProxyTypePanel(), 0, 2, 4, 1, 0, 75);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getFields()[MANUAL_ADDRESS], 1, 0, 1, 1, 0, 0);
    panel.add(getFields()[MANUAL_PORT], 4, 0, 1, 1, 0, 0);

    // Aesthetic spacers.
    panel.addEmptyCellAt(1, 1, 25);
    panel.addEmptyCellAt(2, 1);
    panel.addEmptyCellAt(4, 1, 8);

    return panel;
  }

  private
  Panel
  createManualProxyTypePanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.NORTHEAST);
    panel.add(getLabels()[MANUAL_TYPE], 0, 0, 1, 1, 100, 100);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getProxyTypeChooser(), 1, 0, 1, 1, 0, 0);

    // Ensure enough width for the chooser.
    panel.addEmptyCellAt(1, 0, 8);

    return panel;
  }

  private
  Panel
  createProxyPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getRadioButtons()[DIRECT], 0, 0, 1, 1, 100, 25);
    panel.add(getRadioButtons()[MANUAL], 0, 1, 1, 1, 0, 25);

    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createManualProxyPanel(), 0, 2, 1, 1, 0, 50);

    panel.setBorder(createTitledBorder(getProperty("title"), false));
    panel.setInsets(new Insets(25, 25, 100, 200));

    return panel;
  }

  private
  void
  createRadioButtons()
  {
    ActionHandler handler = new ActionHandler();
    ButtonGroup group = new ButtonGroup();

    itsRadioButtons = new RadioButton[2];

    for(int len = 0; len < getRadioButtons().length; ++len)
    {
      getRadioButtons()[len] = new RadioButton();
    }

    // Build buttons.
    buildButton(getRadioButtons()[MANUAL], getProperty("manual"), handler, group);
    buildButton(getRadioButtons()[DIRECT], getProperty("direct"), handler, group);
  }

  private
  void
  enableForm(RadioButton selectedRadioButton)
  {
    boolean enable = (selectedRadioButton == getRadioButtons()[MANUAL]);

    for(int len = 0; len < getLabels().length; ++len)
    {
      getLabels()[len].setEnabled(enable);
    }

    for(int len = 0; len < getFields().length; ++len)
    {
      getFields()[len].setEnabled(enable);
    }

    getProxyTypeChooser().setEnabled(enable);
  }

  private
  JTextField[]
  getFields()
  {
    return itsFields;
  }

  private
  JLabel[]
  getLabels()
  {
    return itsLabels;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("NetworkPanel." + key);
  }

  private
  ComboBox
  getProxyTypeChooser()
  {
    return itsProxyTypeChooser;
  }

  private
  RadioButton[]
  getRadioButtons()
  {
    return itsRadioButtons;
  }

  private
  void
  initializeForm()
  {
    ProxyWrapper proxyWrapper = getProxy();

    // Fill out form.
    getProxyTypeChooser().setSelectedItem(proxyWrapper.getType().toString());

    getFields()[MANUAL_ADDRESS].setText(proxyWrapper.getAddress());
    getFields()[MANUAL_PORT].setText("" + proxyWrapper.getPort());

    // Select the current proxy setting and enable the form.
    getRadioButtons()[proxyWrapper.getConnectionType().ordinal()].setSelected(true);
    enableForm(getRadioButtons()[proxyWrapper.getConnectionType().ordinal()]);
  }

  private
  void
  setProxyTypeChooser(ComboBox chooser)
  {
    itsProxyTypeChooser = chooser;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  ActionHandler
  implements ActionListener
  {
    public
    void
    actionPerformed(ActionEvent event)
    {
      Object source = event.getSource();

      if(source == getProxyTypeChooser())
      {
        Proxy.Type type = Proxy.Type.valueOf(PROXY_TYPES[getProxyTypeChooser().getSelectedIndex()]);

        setProxyType(type);
      }
      else
      {
        enableForm((RadioButton)source);

        if(source == getRadioButtons()[DIRECT])
        {
          setProxyConnectionType(ProxyConnectionTypeKeys.DIRECT);
        }
        else
        {
          setProxyConnectionType(ProxyConnectionTypeKeys.MANUAL);
        }
      }
    }
  }

  private
  class
  FocusHandler
  extends FocusAdapter
  {
    @Override
    public
    void
    focusLost(FocusEvent event)
    {
      if(event.getSource() == getFields()[MANUAL_ADDRESS])
      {
        setProxyAddress(getFields()[MANUAL_ADDRESS].getText());
      }
      else
      {
        int port = -1;

        try
        {
          port = Integer.parseInt(getFields()[MANUAL_PORT].getText());
        }
        catch(Exception exception)
        {
          // Ignored.
        }

        setProxyPort(port);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JTextField[] itsFields;
  private JLabel[] itsLabels;
  private ComboBox itsProxyTypeChooser;
  private RadioButton[] itsRadioButtons;

  private static final int DIRECT = 0;
  private static final int MANUAL = 1;

  private static final int MANUAL_ADDRESS = 0;
  private static final int MANUAL_PORT = 1;
  private static final int MANUAL_TYPE = 2;

  private static final String[] PROXY_TYPES = {"HTTP", "SOCKS"};
}
