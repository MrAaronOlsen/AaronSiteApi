package com.budgetmonster.server.controllers;

import com.budgetmonster.response.Response;
import com.budgetmonster.response.ResponseBuilder;
import com.budgetmonster.utils.enums.RequestType;
import com.budgetmonster.utils.enums.Table;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.budgetmonster.server.controllers.MasterController.BASE_URL;

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
}
