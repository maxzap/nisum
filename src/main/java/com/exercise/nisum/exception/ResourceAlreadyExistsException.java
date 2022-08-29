package com.exercise.nisum.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String property, String value){
        super(String.format("Recurso con la propiedad %s y valor %s existente." +
                "Asegurese de insertar un valor unico para %s", property, value, property));
    }
}
