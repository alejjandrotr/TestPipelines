package com.alejjandrodev.ArcaWareHouse.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggerJsonAdapter implements ILoggerWriter {

    @Autowired
    LoggerJsonWriter loggerJsonWriter;

    @Override
    public void info(String msg, Object data) {
        loggerJsonWriter.infoMensaje(msg, data);
    }

    @Override
    public void error(String msg, Object data) {
        loggerJsonWriter.errorMensaje(msg, data);
    }
}
