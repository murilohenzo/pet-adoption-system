package com.murilohenzo.petapi.config;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages", new Locale("pt", "BR"));
  
  private Messages() {}
  
  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}
