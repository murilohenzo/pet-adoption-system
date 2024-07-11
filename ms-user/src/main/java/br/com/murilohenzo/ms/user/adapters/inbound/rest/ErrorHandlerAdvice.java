package br.com.murilohenzo.ms.user.adapters.inbound.rest;

import br.com.murilohenzo.ms.user.domain.exceptions.EntityAlreadyExistsException;
import br.com.murilohenzo.ms.user.presentation.representation.ProblemRepresentation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemRepresentation> entityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;

        log.debug("[D24] - EntityNotFoundException:{}", e.getMessage());

        ProblemRepresentation problem = new ProblemRepresentation();
        problem.setMessage(e.getMessage());
        problem.setStatusCode(status.value());
        problem.setPath(request.getRequestURI());
        problem.setTimestamp(Date.from(Instant.now()));
        problem.setTitle("entity.not.found");

        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ProblemRepresentation> entityAlreadyExistsException(EntityAlreadyExistsException e, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        log.debug("[D40] - EntityAlreadyExistsException:{}", e.getMessage());

        ProblemRepresentation problem = new ProblemRepresentation();
        problem.setMessage(e.getMessage());
        problem.setStatusCode(status.value());
        problem.setPath(request.getRequestURI());
        problem.setTimestamp(Date.from(Instant.now()));
        problem.setTitle("entity.already.exists");

        return ResponseEntity.status(status).body(problem);
    }

}
