package com.alejjandrodev.ArcaWareHouse.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class LoggerJsonWriter {


    private static final String LOG_GENERAL_FILE = "chronicle.log";
    private static final String LOG_SUCCESS_FILE = "triumphs.log";
    private static final String LOG_FAILURE_FILE = "mishaps.log";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final ObjectMapper objectMapper;
    private final List<String> generalLogBuffer = new ArrayList<>();
    private final List<String> successLogBuffer = new ArrayList<>();
    private final List<String> failureLogBuffer = new ArrayList<>();
    private boolean isBatchLoggingEnabled = true;

    public LoggerJsonWriter() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void setBatchLoggingEnabled(boolean batchLoggingEnabled) {
        isBatchLoggingEnabled = batchLoggingEnabled;
    }

    public void triumph(String msg, Object data) {
        record(data, "TRIUMPH: " + msg, LOG_GENERAL_FILE, LOG_SUCCESS_FILE, successLogBuffer);
    }

    public void mishap(String msg, Object data) {
        record(data, "MISHAP: " + msg, LOG_GENERAL_FILE, LOG_FAILURE_FILE, failureLogBuffer);
    }

    public void infoMensaje(String msg, Object data) {
        record(data, "INFO: " + msg, LOG_GENERAL_FILE, LOG_SUCCESS_FILE, successLogBuffer);
    }

    public void errorMensaje(String msg, Object data) {
        record(data, "INFO: " + msg, LOG_GENERAL_FILE, LOG_FAILURE_FILE, failureLogBuffer);
    }

    //New methods

    public void debug(String msg, Object data) {
        record(data, "DEBUG: " + msg, LOG_GENERAL_FILE, null, generalLogBuffer);
    }

    public void audit(String msg, Object data) {
        record(data, "AUDIT: " + msg, LOG_GENERAL_FILE, null, generalLogBuffer);
    }

    public void warning(String msg, Object data) {
        record(data, "WARNING: " + msg, LOG_GENERAL_FILE, LOG_GENERAL_FILE, generalLogBuffer);
    }

    public void performance(String msg, Object data) {
        record(data, "PERFORMANCE: " + msg, LOG_GENERAL_FILE, null, generalLogBuffer);
    }

    public void security(String msg, Object data) {
        record(data, "SECURITY: " + msg, LOG_GENERAL_FILE, null, generalLogBuffer);
    }

    public String getRandomMessage(String prefix) {
        String[] messages = {"System is running smoothly", "All services are operational", "No issues detected", "Load average is normal"};
        Random random = new Random();
        int index = random.nextInt(messages.length);
        return prefix + ": " + messages[index];
    }

    public void logRandomEvent(String prefix, Object data) {
        record(data, "EVENT: " + getRandomMessage(prefix), LOG_GENERAL_FILE, null, generalLogBuffer);
    }

    private void record(Object data, String level, String generalFile, String specificFile, List<String> buffer) {
        String timestamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        String jsonString = convertToJson(data);
        String logMessage = String.format("[%s] %s: %s%n", timestamp, level, jsonString);

        if (isBatchLoggingEnabled) {
            generalLogBuffer.add(logMessage);
            if (buffer != null) {
                buffer.add(logMessage);
            }
        } else {
            writeLog(logMessage, generalFile);
            if (specificFile != null) {
                writeLog(logMessage, specificFile);
            }
        }
    }

    public void flushLogs() {
        if (isBatchLoggingEnabled) {
            writeLog(String.join("", generalLogBuffer), LOG_GENERAL_FILE);
            writeLog(String.join("", successLogBuffer), LOG_SUCCESS_FILE);
            writeLog(String.join("", failureLogBuffer), LOG_FAILURE_FILE);
            clearBuffers();
        }
    }

    private String convertToJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (IOException e) {
            System.err.println("Error converting object to JSON: " + e.getMessage());
            return "Error converting to JSON: " + e.getMessage();
        }
    }

    private void writeLog(String message, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.append(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file " + filename + ": " + e.getMessage());
        }
    }

    private void clearBuffers() {
        generalLogBuffer.clear();
        successLogBuffer.clear();
        failureLogBuffer.clear();
    }
}


