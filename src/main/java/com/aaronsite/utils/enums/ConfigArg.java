package com.aaronsite.utils.enums;

import com.aaronsite.utils.constants.ConfigArgs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ConfigArg {
  DB_URL(ConfigArgs.DB_URL),
  DB_PW(ConfigArgs.DB_PW, true),
  DB_USER(ConfigArgs.DB_USER),
  DB_SCHEMA(ConfigArgs.DB_SCHEMA),
  API_KEY(ConfigArgs.API_KEY, true),

  // Heroku Configs
  JDBC_DATABASE_URL(ConfigArgs.JDBC_DATABASE_URL, DB_URL, true),
  JDBC_DATABASE_USERNAME(ConfigArgs.JDBC_DATABASE_USERNAME, DB_USER, true),
  JDBC_DATABASE_PASSWORD(ConfigArgs.JDBC_DATABASE_PASSWORD, DB_PW, true, true),
  HEROKU_API(ConfigArgs.HEROKU_API, API_KEY, true, true),

  // Default
  UNKNOWN(ConfigArgs.UNKNOWN);

  private String key;
  private ConfigArg alias;
  private boolean hide;
  private boolean system;

  private static Map<String, ConfigArg> map = new ConcurrentHashMap<>();
  static {
    for (ConfigArg value : ConfigArg.values()) {
      map.put(value.getKey(), value);
    }
  }

  ConfigArg(String key) {
    this(key, null, false, false);
  }

  ConfigArg(String key, boolean hide) {
    this(key, null, hide, false);
  }

  ConfigArg(String key, ConfigArg alias, boolean system) {
    this(key, alias, false, system);
  }

  ConfigArg(String key, ConfigArg alias, boolean hide, boolean system) {
    this.key = key;
    this.alias = alias;
    this.hide = hide;
    this.system = system;
  }

  public static ConfigArg get(String arg) {
    return map.getOrDefault(arg, UNKNOWN);
  }

  public String print(String argValue) {
    return isHide() ? "****" : argValue;
  }

  public String getKey() {
    return key;
  }

  public ConfigArg getAlias() {
    return alias == null ? this : alias;
  }

  public boolean isHide() {
    return hide;
  }

  public boolean isSystem() {
    return system;
  }
}