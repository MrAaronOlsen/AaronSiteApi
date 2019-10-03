package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.models.Budget;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBDeleteTests extends TestServer {

  @Test
  void deleteRecordById() throws ABException {
    Budget insertRecord = new Budget(insertRecord(new Budget.Builder().setName(unique("test")).build()));

    try (DBConnection dbConn = new DBConnection()) {
      DBDelete dbDelete = new DBDelete(dbConn, Table.BUDGET).setId(insertRecord.getId());
      DBResult deleteResult = dbDelete.execute();

      if (deleteResult.hasNext()) {
        DBRecord record = deleteResult.getNext();

        DbQuery query = new DbQuery(dbConn,Table.BUDGET).setIdQuery(record.getId());
        DBResult queryResult = query.execute();

        if (queryResult.hasNext()) {
          Assertions.fail("Query should not have returned any results.");
        }
      } else {
        Assertions.fail("Delete should have returned a record.");
      }

    }
  }
}
