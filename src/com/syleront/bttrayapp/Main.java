package com.syleront.bttrayapp;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Main {
  private static final SystemTray tray = SystemTray.getSystemTray();
  private static final PopupMenu trayPopupMenu = new PopupMenu();
  private static final TrayIcon trayIcon = new TrayIcon(
      Toolkit.getDefaultToolkit().getImage("res/bt.png"),
      "BT Tray Icon",
      trayPopupMenu
  );

  private static void initTrayIcon() {
    trayIcon.setImageAutoSize(true);

    trayIcon.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
        // Check is left mouse click
        if (e.getButton() != 1) {
          return;
        }

        Logger.debug("Handle mouse click");

        try {
          // Opens windows bluetooth settings
          var process = Runtime.getRuntime().exec("explorer.exe ms-settings:bluetooth");
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }
    });

    try {
      // Adding icon to tray
      tray.add(trayIcon);
    } catch (AWTException awtException) {
      awtException.printStackTrace();
    }
  }

  private static void initTrayMenu() {
    MenuItems.startup.addItemListener(e -> {
          if (Utils.isStartupRegistryKeyExists()) {
            // If registry value exists - delete it
            Utils.removeStartupRegistryKeyValue();
            // And set menu item state to false
            MenuItems.startup.setState(false);
          } else {
            // If registry value not exists - set it
            Utils.setStartupRegistryKeyValue();
            // And set menu item state to true
            MenuItems.startup.setState(true);
          }
        }
    );

    // Add exit action
    MenuItems.exit.addActionListener(e -> System.exit(0));

    // Adding menu items to PopupMenu
    trayPopupMenu.add(MenuItems.startup);
    trayPopupMenu.add(MenuItems.exit);
  }

  private static void initApp() {
    Utils.startupChecks();
    initTrayMenu();
    initTrayIcon();
  }

  public static void main(String[] args) throws Exception {
    // Checking whether system are windows 10
    // Because that program useless for other systems
    if (!OSValidator.IS_WINDOWS_10) {
      throw new Exception("This program only for Windows 10 family systems");
    }

    if (!SystemTray.isSupported()) {
      throw new Exception("System tray is not supported in your system");
    }

    initApp();

    Logger.debug("End of main");
  }
}
