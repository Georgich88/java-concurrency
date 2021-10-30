package com.georgeisaev.fundamentals.threadsafety.atomicity;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.math.BigInteger;

public interface FactorizerServlet extends Servlet {

    default void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    default BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    default BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }

    void service();

}
