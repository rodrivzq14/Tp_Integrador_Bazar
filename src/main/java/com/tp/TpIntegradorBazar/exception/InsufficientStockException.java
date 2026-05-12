package com.tp.TpIntegradorBazar.exception;


public class InsufficientStockException extends RuntimeException{
    
    public InsufficientStockException (String msje) {
        super(msje);
    }
    
}
