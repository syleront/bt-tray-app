package com.syleront.bttrayapp;

// JRegistry from https://jregistry.sourceforge.io/
import com.registry.RegStringValue;
import com.registry.RegistryKey;
import com.registry.ValueType;

import java.util.Objects;

public class Utils {
  // App name that used for windows registry startup value
  public static final String AppName = "BtTrayAppJava";
  // Value that sets if user turns on autostart feature
  private static final String RegistryKeyValueToSet = "javaw -jar \"" + getJarAbsolutePath() + "\"";
  // Registry startup path
  private static final String RunRegistryKeyPath = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run";

  // Method that returns absolute path of current jar file for setting in registry value
  // e.g. D:/Programs/bt-tray-app/file.jar
  public static String getJarAbsolutePath() {
    return new java.io.File(
        Main.class.getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .getPath()
    ).getAbsolutePath();
  }

  // Method that sets startup value in registry
  public static void setStartupRegistryKeyValue() {
    Logger.debug("Set registry key");

    // Getting registry key
    var key = new RegistryKey(RunRegistryKeyPath);
    // Cast as RegStringValue (REG_SZ)
    var value = (RegStringValue) key.getValue(AppName);

    // Check if value is null - creating new one
    if (value == null) {
      value = (RegStringValue) key.newValue(AppName, ValueType.REG_SZ);
    }

    // Check if values is not equal - replacing with new
    if (!value.getValue().equals(RegistryKeyValueToSet)) {
      value.setValue(RegistryKeyValueToSet);
    }
  }

  // Method that removes startup value in registry
  public static void removeStartupRegistryKeyValue() {
    Logger.debug("Remove registry key");

    // Getting registry key
    var key = new RegistryKey(RunRegistryKeyPath);
    // Cast as RegStringValue (REG_SZ)
    var value = (RegStringValue) key.getValue(AppName);

    // Check if value exists - remove it
    if (value != null) {
      key.deleteValue(value);
    }
  }

  // Method that returns string value in registry
  public static String getStartupRegistryKeyValue() {
    var key = new RegistryKey(RunRegistryKeyPath);
    var value = (RegStringValue) key.getValue(AppName);
    return value == null ? null : value.getValue();
  }

  // Simple method to check is startup registry key exists
  public static boolean isStartupRegistryKeyExists() {
    return getStartupRegistryKeyValue() != null;
  }

  // Method for using when program starts
  // Checking whether the values in the registry are exists and equal
  // If exists bot not equal - replacing with correct path to .jar file
  // This is necessary if the user has moved the program folder to another directory
  public static void startupChecks() {
    if (isStartupRegistryKeyExists() && !Objects.equals(getStartupRegistryKeyValue(), RegistryKeyValueToSet)) {
      Logger.debug("Registry key value is bad, replacing with new");
      setStartupRegistryKeyValue();
    }
  }
}
