package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.models.Budget;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.budgetmonster.models.Budget.NAME;

class DBQueryTests extends TestServer {

  @Test
  void queryByIdReturnsSingleRecord() throws ABException {
    Budget budgetInsert = new Budget(insertRecord(new Budget.Builder().setName(unique("test")).build()));

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.BUDGET).setIdQuery(budgetInsert.getId());

      DBResult result = dbQuery.execute();
      if (result.hasNext()) {
        Budget budgetQuery = new Budget(result.getNext());

        Assertions.assertEquals(budgetInsert.getId(), budgetQuery.getId());
        Assertions.assertEquals(budgetInsert.getName(), budgetQuery.getName());
      } else {
        Assertions.fail("Query should have found record by id.");
      }
    }
  }

  @Test
  void queryByValueReturnsSingleRecord() throws ABException {
    String objectName = unique("test");

    Budget budgetInsert = new Budget(insertRecord(new Budget.Builder().setName(objectName).build()));

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.BUDGET).setQuery(new DBQueryBuilder().add(NAME, objectName));

      DBResult result = dbQuery.execute();
      if (result.hasNext()) {
        Budget budgetQuery = new Budget(result.getNext());

        Assertions.assertEquals(budgetInsert.getId(), budgetQuery.getId());
        Assertions.assertEquals(budgetInsert.getName(), budgetQuery.getName());
      } else {
        Assertions.fail("Query should have found record by id.");
      }
    }
  }

  @Test
  void queryByValueReturnsMultipleRecords() throws ABException {
    String objectName = unique("test");

    insertRecord(new Budget.Builder().setName(objectName).build());
    insertRecord(new Budget.Builder().setName(objectName).build());

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.BUDGET).setQuery(new DBQueryBuilder().add(NAME, objectName));

      DBResult result = dbQuery.execute();

      int count = 0;
      while (result.hasNext()) {
        count ++;
      }

      Assertions.assertEquals(2, count);
    }
  }
}
