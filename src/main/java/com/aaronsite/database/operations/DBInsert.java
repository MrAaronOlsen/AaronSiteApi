package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBInsertStmtBuilder;
import com.aaronsite.database.statements.DBPreparedStmt;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Model;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;
import com.aaronsite.utils.exceptions.ModelException;

public class DBInsert implements DBOperation {
  private Table table;
  private DBConnection dbConn;
  private DBRecord record;

  public DBInsert(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBInsert addRecord(DBRecord record) {
    this.record = record;
    return this;
  }

  public DBInsert addRecord(Model model) throws ModelException {
    this.record = Model.deserialize(model).buildRecord();
    return this;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBPreparedStmt stmt = new DBInsertStmtBuilder(dbConn, table)
        .setRecord(record)
        .build();

    stmt.execute();
    return stmt.getResult();
  }
}
