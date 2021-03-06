package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.utils.enums.Table.TEST_SIMPLE;

class DBQueryStmtBuilderTests extends TestServer {

  @Test
  void simpleIdQuerySql() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "SELECT * FROM " + getTestSchema(TEST_SIMPLE) + " WHERE id=1";

      DBPreparedStmt stmt = new DBQueryStmtBuilder(conn, TEST_SIMPLE)
          .setIdQuery("1").build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void simpleColumnQuerySql() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "SELECT * FROM " + getTestSchema(TEST_SIMPLE) + " WHERE name='1'";

      DBPreparedStmt stmt = new DBQueryStmtBuilder(conn, TEST_SIMPLE)
          .setWhere(new DBWhereStmtBuilder("name", "1")).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void simpleColumnQueryWithSelectSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "SELECT name FROM " + getTestSchema(TEST_SIMPLE) + " WHERE name='1'";

      DBPreparedStmt stmt = new DBQueryStmtBuilder(conn, TEST_SIMPLE)
          .setSelect(new DBSelectStmtBuilder("name"))
          .setWhere(new DBWhereStmtBuilder("name", "1")).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void simpleColumnQueryWithMultiSelectSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "SELECT name, id FROM " + getTestSchema(TEST_SIMPLE) + " WHERE name='1'";

      DBPreparedStmt stmt = new DBQueryStmtBuilder(conn, TEST_SIMPLE)
          .setSelect(new DBSelectStmtBuilder("name, id"))
          .setWhere(new DBWhereStmtBuilder("name", "1")).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void simpleColumnQueryWithMultiSelectAndSortSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "SELECT name, id FROM " + getTestSchema(TEST_SIMPLE) + " WHERE name='1' ORDER BY name ASC";

      DBPreparedStmt stmt = new DBQueryStmtBuilder(conn, TEST_SIMPLE)
          .setSelect(new DBSelectStmtBuilder("name, id"))
          .setWhere(new DBWhereStmtBuilder("name", "1"))
          .setSort(new DBSortStmtBuilder("name")).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void simpleColumnQueryWithMultiSelectAndMultiSortSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "SELECT name, id FROM " + getTestSchema(TEST_SIMPLE) + " WHERE name='1' ORDER BY name ASC, id DESC";

      DBPreparedStmt stmt = new DBQueryStmtBuilder(conn, TEST_SIMPLE)
          .setSelect(new DBSelectStmtBuilder("name, id"))
          .setWhere(new DBWhereStmtBuilder("name", "1"))
          .setSort(new DBSortStmtBuilder("name, -id")).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }
}
