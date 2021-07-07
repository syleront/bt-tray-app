package com.syleront.bttrayapp;

// From https://github.com/mkyong/core-java/blob/master/java-basic/src/main/java/com/mkyong/system/OSValidator.java
public class OSValidator {
  public static final String OS = System.getProperty("os.name").toLowerCase();
  public static final boolean IS_WINDOWS = (OS.contains("win"));
  public static final boolean IS_WINDOWS_10 = (IS_WINDOWS && OS.contains("10"));
  public static final boolean IS_MAC = (OS.contains("mac"));
  public static final boolean IS_UNIX = (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
  public static final boolean IS_SOLARIS = (OS.contains("sunos"));
}
