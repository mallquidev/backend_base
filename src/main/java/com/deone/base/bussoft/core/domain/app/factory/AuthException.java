package com.deone.base.bussoft.core.domain.app.factory;

import com.deone.base.bussoft.core.commons.exception.BadRequest;
import com.deone.base.bussoft.core.commons.exception.ErrorCode;
import com.deone.base.bussoft.core.commons.exception.Unauthorized;

public class AuthException {


    public static BadRequest notAccessSystem() {
        BadRequest badRequest = new BadRequest("No eres un usuario valido del sistema");
        badRequest.setErrorCode(ErrorCode.NOT_ACCESS_SYSTEM);
        return badRequest;
    }
    public static Unauthorized sessionActive(){
        Unauthorized unauthorized = new Unauthorized("Usuario no autenticado o sesión no activa");
        unauthorized.setErrorCode(ErrorCode.USER_NOT_SESSION);
        return unauthorized;
    }
    public static BadRequest invalidCredentials() {
        BadRequest badRequest = new BadRequest("Credenciales inválidas");
        badRequest.setErrorCode(ErrorCode.CREDENTIALS_INVALID);
        return badRequest;
    }
    public static BadRequest userDeleted(String email) {
        BadRequest badRequest = new BadRequest("El usuario con el correo " + email + " ha sido eliminado previamente no puedes iniciar session");
        badRequest.setErrorCode(ErrorCode.USER_NOT_LOGIN_FOR_DELETED);
        return badRequest;
    }
}
