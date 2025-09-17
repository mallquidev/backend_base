package com.deone.base.bussoft.core.domain.app.factory;

import com.deone.base.bussoft.core.commons.exception.ErrorCode;
import com.deone.base.bussoft.core.commons.exception.NotFound;

public class NotFoundException {

    public static NotFound userNotFound(Long id) {
        return  new NotFound("Usuario no encontrado con el id: " + id, ErrorCode.USER_NOT_FOUND);
    }
    public static NotFound userNotFoundByEmail(String email) {
        return new NotFound("Usuario no encontrado con el email: " + email, ErrorCode.USER_NOT_FOUND);
    }

}
