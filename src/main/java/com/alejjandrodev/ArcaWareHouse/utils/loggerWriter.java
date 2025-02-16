package com.alejjandrodev.ArcaWareHouse.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class loggerWriter  {


    private static final String LOG_GENERAL_FILE = "general.log";
    private static final String LOG_INFO_FILE = "info.log";
    private static final String LOG_ERROR_FILE = "error.log";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final ObjectMapper objectMapper;

    public loggerWriter() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void info(String msg, Object data) {
        log(data, "INFO: " + msg, LOG_GENERAL_FILE, LOG_INFO_FILE);
    }

    public void error(String msg, Object data) {
        log(data, "ERROR: " + msg, LOG_GENERAL_FILE, LOG_ERROR_FILE);
    }


    private void log(Object data, String level, String generalFile, String specificFile) {
        String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        String jsonString = convertToJson(data);

        String logMessage = String.format("[%s] %s: %s%n", timestamp, level, jsonString);


        writeLog(logMessage, generalFile);
        writeLog(logMessage, specificFile);
    }


    private String convertToJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (IOException e) {
            System.err.println("Error converting object to JSON: " + e.getMessage());  // Log al System.err en caso de error
            return "Error converting to JSON: " + e.getMessage(); //Mensaje de error en el log
        }
    }

    private void writeLog(String message, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) { // Append mode
            writer.append(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file " + filename + ": " + e.getMessage()); // Log al System.err
        }
    }

}
