package br.com.gft.noticias.config.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.gft.noticias.config.exception.validacaodto.ApiErrorDTO;
import br.com.gft.noticias.config.exception.validacaodto.ErroDeFormularioDTO;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ NoticiaException.class })
    public ResponseEntity<ApiErrorDTO> handleNoticiaException(NoticiaException ex, WebRequest request){

        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        
        return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.status());
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ApiErrorDTO> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){
       
        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.NOT_FOUND, ex.getMessage());
        
        return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.status());
    }

    @ExceptionHandler({ EntityConflictException.class })
    public ResponseEntity<ApiErrorDTO> handleEntityConflictException(EntityConflictException ex, WebRequest request){
        
        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.CONFLICT, ex.getMessage());
        
        return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.status());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        ErroDeFormularioDTO errorDetails = new ErroDeFormularioDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.status(), request);
    }
 
}
