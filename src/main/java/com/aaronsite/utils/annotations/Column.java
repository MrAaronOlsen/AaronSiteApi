package com.aaronsite.utils.annotations;

import com.aaronsite.utils.enums.ColumnType;
import com.aaronsite.utils.enums.Table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
  ColumnType type();
  Table table() default Table.INVALID_TABLE;
}