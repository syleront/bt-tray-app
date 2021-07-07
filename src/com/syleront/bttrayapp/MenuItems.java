package com.syleront.bttrayapp;

import java.awt.*;

public class MenuItems {
  public static final CheckboxMenuItem startup = new CheckboxMenuItem("Autostart", Utils.isStartupRegistryKeyExists());
  public static final MenuItem exit = new MenuItem("Exit");
}
