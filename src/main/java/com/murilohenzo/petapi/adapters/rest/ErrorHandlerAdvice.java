package com.murilohenzo.petapi.adapters.rest;

import com.murilohenzo.petapi.presentation.representation.ProblemRepresentation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerAdvice {

  @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
  public ResponseEntity<ProblemRepresentation> jdbcSQLIntegrityConstraintViolationException(JdbcSQLIntegrityConstraintViolationException e, HttpServletRequest request) {

    var status = HttpStatus.BAD_REQUEST;

    log.debug("[D28] - JdbcSQLIntegrityConstraintViolationException:{}", e.getMessage());

    ProblemRepresentation problem = new ProblemRepresentation();
    problem.setMessage("Database integrity error");
    problem.setStatusCode(status.value());
    problem.setPath(request.getRequestURI());
    problem.setTimestamp(Date.from(Instant.now()));
    problem.setTitle("constraint.violation");

    return ResponseEntity.status(status).body(problem);
  }

  @ExceptionHandler(JDBCConnectionException.class)
  public ResponseEntity<ProblemRepresentation> jDBCConnectionException(JDBCConnectionException e, HttpServletRequest request) {

    var status = HttpStatus.INTERNAL_SERVER_ERROR;

    log.debug("[D45] - JdbcSQLIntegrityConstraintViolationException:{}", e.getMessage());

    ProblemRepresentation problem = new ProblemRepresentation();
    problem.setMessage("Database connection error");
    problem.setStatusCode(status.value());
    problem.setPath(request.getRequestURI());
    problem.setTimestamp(Date.from(Instant.now()));
    problem.setTitle("database.connection");

    return ResponseEntity.status(status).body(problem);
  }

  @ExceptionHandler(FileNotFoundException.class)
  public ResponseEntity<ProblemRepresentation> fileNotFoundException(FileNotFoundException e, HttpServletRequest request) {

    var status = HttpStatus.NOT_FOUND;

    log.debug("[D62] - FileNotFoundException:{}", e.getMessage());

    ProblemRepresentation problem = new ProblemRepresentation();
    problem.setMessage(e.getMessage());
    problem.setStatusCode(status.value());
    problem.setPath(request.getRequestURI());
    problem.setTimestamp(Date.from(Instant.now()));
    problem.setTitle("file.not.found");

    return ResponseEntity.status(status).body(problem);
  }

  @ExceptionHandler(InvalidContentTypeException.class)
  public ResponseEntity<ProblemRepresentation> invalidContentTypeException(InvalidContentTypeException e, HttpServletRequest request) {

    var status = HttpStatus.BAD_REQUEST;

    log.debug("[D80] - FileNotFoundException:{}", e.getMessage());

    ProblemRepresentation problem = new ProblemRepresentation();
    problem.setMessage(e.getMessage());
    problem.setStatusCode(status.value());
    problem.setPath(request.getRequestURI());
    problem.setTimestamp(Date.from(Instant.now()));
    problem.setTitle("invalid.content.type");

    return ResponseEntity.status(status).body(problem);
  }

}
