package com.alejjandrodev.ArcaWareHouse.utils;



public interface ILoggerWriter {
    public void  info(String msg, Object data);
    public void error(String msg, Object data);
}
