package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.TableMetadata;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

public class DBQueryStmtBuilder extends DBStmtBuilder {
  private static final String SELECT_ALL = "SELECT * FROM";

  public DBQueryStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBQueryStmtBuilder setWhere(DBWhereStmtBuilder where) {
    this.where = where;
    return this;
  }

  public DBQueryStmtBuilder setIdQuery(String id) {
    this.where = new DBWhereStmtBuilder(id);
    return this;
  }

  public DBPreparedStmt build() throws DatabaseException {
    String sqlStmt = String.format("%s %s %s", SELECT_ALL, tableSchema(), where);

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(sqlStmt);

    TableMetadata tableMetadata = TableMetadata.getTableMetadata(dbConn, table);
    DBStmtSetter stmtBuilder = new DBStmtSetter(prepStmt);

    for (int i = 0; i < where.size(); i++) {
      ColumnMetadata column = tableMetadata.getColumn(where.getColumn(i));
      stmtBuilder.setValue(column, where.getValue(i));
    }

    return prepStmt;
  }
}
