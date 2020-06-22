package com.belza.asyncmethodservice.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

    public String log(String uuid, String component, String processDescription) {
        return this.log(uuid, component, processDescription, StringUtils.EMPTY);
    }

    public String log(String uuid, String component, String processDescription, String txnDetails) {
        Map<String, String> map = new HashMap<>();
        this.updateLogMap(map, uuid, component, processDescription, txnDetails);
        String logMsg = map.toString();
        LOGGER.info(logMsg);
        return logMsg;
    }

    private Map<String, String> updateLogMap(Map<String, String> map, String uuid, String component, String processDescription,
                                             String txnDetails) {
        if (StringUtils.isNotEmpty(uuid)) {
            map.put("UUID", uuid);
        }

        if (StringUtils.isNotEmpty(component)) {
            map.put("COMPONENT", component);
        }

        if (StringUtils.isNotEmpty(processDescription)) {
            map.put("PROCESS_DESCRIPTION", processDescription);
        }

        if (StringUtils.isNotEmpty(txnDetails)) {
            map.put("TXN_DETAILS", txnDetails);
        }

        return map;
    }
}
