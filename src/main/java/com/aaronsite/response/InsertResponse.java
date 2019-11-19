package com.aaronsite.response;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBInsert;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Model;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ResponseException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static com.aaronsite.utils.exceptions.ResponseException.Code.MODEL_PROCESSING_ERROR;

class InsertResponse {

  static Response build(Table table, String body) throws ABException {
    return new Response(executeInsert(table, ResponseUtils.convertBodyToModel(table, body)));
  }

  private static List<ResponseData> executeInsert(Table table, Model model) throws ABException {
    List<ResponseData> results = new LinkedList<>();
    Function<DBRecord, Model> dataBuilder = Model.getModel(table);

    try {
      Model.process(model);
    } catch (IllegalAccessException e) {
      throw new ResponseException(MODEL_PROCESSING_ERROR, e.getMessage());
    }

    try (DBConnection dbConn = new DBConnection()) {
      DBInsert insert = new DBInsert(dbConn, table).addRecord(model);
      DBResult result = insert.execute();

      while (result.hasNext()) {
        results.add(dataBuilder.apply(result.getNext()));
      }
    }

    return results;
  }
}
