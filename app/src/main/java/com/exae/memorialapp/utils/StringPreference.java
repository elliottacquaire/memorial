
package com.exae.memorialapp.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



public final class StringPreference implements StringPreferenceType {
  private final SecurePreferences securePreferences;
  private final String key;
  private final String defaultValue;

  public StringPreference(final @NonNull SecurePreferences securePreferences,
      final @NonNull String key) {
    this(securePreferences, key, null);
  }

  public StringPreference(final @NonNull SecurePreferences securePreferences,
      final @NonNull String key,
      final @Nullable String defaultValue) {
    this.securePreferences = securePreferences;
    this.key = key;
    this.defaultValue = defaultValue;
  }

  @Override
  public String get() {
    return securePreferences.getString(key, defaultValue);
  }

  @Override
  public boolean isSet() {
    return securePreferences.contains(key);
  }

  @Override
  public void set(final @NonNull String value) {
    securePreferences.edit().putString(key, value).apply();
  }

  @Override
  public void delete() {
    securePreferences.edit().remove(key).apply();
  }

  @Override public String key() {
    return key;
  }
}
