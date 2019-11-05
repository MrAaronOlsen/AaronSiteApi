package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.utils.enums.RequestType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class QueryController extends MasterController {

  @GetMapping("/{table}")
  public Response queryOnTable(
      @RequestHeader Map<String, String> headers,
      @PathVariable(value = "table") String table,
      @RequestParam Map<String, String> params) {

    try {
      return new ResponseBuilder(RequestType.QUERY)
          .setTable(table)
          .setParams(params).build();

    } catch (Throwable e) {
      return ResponseBuilder.handleError(e);
    }
  }

  @GetMapping("/{table}/{id}")
  public Response queryOnTableById(
      @RequestHeader Map<String, String> headers,
      @PathVariable(value = "table") String table,
      @PathVariable(value = "id") String id) {

    try {
      return new ResponseBuilder(RequestType.QUERY_BY_ID)
          .setTable(table)
          .setId(id).build();

    } catch (Throwable e) {
      return ResponseBuilder.handleError(e);
    }
  }
}