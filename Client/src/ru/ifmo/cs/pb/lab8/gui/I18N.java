package ru.ifmo.cs.pb.lab8.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * I18N utility class
 */
public final class I18N {

      /**
       * The current selected Locale.
       */
      private static final ObjectProperty<Locale> locale;

      static {
            locale = new SimpleObjectProperty<>(getDefaultLocale());
            locale.addListener(((observable, oldValue, newValue) -> Locale.setDefault(newValue)));
      }

      /**
       * Get the supported Locales.
       */
      public static List<Locale> getSupportedLocales() {
            return new ArrayList<>(Arrays.asList(new Locale("en_US"), new Locale("ru_RU")));
      }

      /**
       * Get the default locale. This is the systems default
       * if contained in the supported locales, english otherwise.
       */
      public static Locale getDefaultLocale() {
            Locale sysDefault = Locale.getDefault();
            return getSupportedLocales().contains(sysDefault) ?
                    sysDefault : Locale.US;
      }

      public static Locale getLocale() {
            return locale.get();
      }

      public static void setLocale(Locale locale) {
            localeProperty().set(locale);
            Locale.setDefault(locale);
      }

      public static ObjectProperty<Locale> localeProperty() {
            return locale;
      }

      /**
       * Gets the string with the given key from the resource bundle for the
       * current locale and uses it as first argument to MessageFormat.format,
       * passing in the optional args and returning the result.
       */
      public static String get(final String key, final Object... args) {
            ResourceBundle bundle = ResourceBundle.getBundle("locale.loc", getLocale());
            return MessageFormat.format(bundle.getString(key), args);
      }

      /**
       * Creates a String binding to a localized String for the given message bundle key
       */
      public static StringBinding createStringBinding(final String key, Object... args) {
            return Bindings.createStringBinding(() -> get(key, args), locale);
      }

      /**
       * Creates a String to a localized String that is computed by calling the given func
       */
      public static StringBinding createStringBinding(Callable<String> func) {
            return Bindings.createStringBinding(func, locale);
      }

      /**
       * Creates a bound Label whose value is computed on language change.
       */
      public static Label labelForValue(Callable<String> func) {
            Label label = new Label();
            label.textProperty().bind(createStringBinding(func));
            return label;
      }

      /**
       * Creates a bound Button for the given resourcebundle key
       */
      public static Button buttonForKe(final String key, final Object... args) {
            Button button = new Button();
            button.textProperty().bind(createStringBinding(key, args));
            return button;
      }
}
