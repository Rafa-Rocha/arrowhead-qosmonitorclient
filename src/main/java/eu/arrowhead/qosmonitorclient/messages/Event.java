/*
 *  Copyright (c) 2018 AITIA International Inc.
 *
 *  This work is part of the Productive 4.0 innovation project, which receives grants from the
   European Commissions H2020 research and innovation programme, ECSEL Joint Undertaking
   (project no. 737459), the free state of Saxony, the German Federal Ministry of Education and
   national funding authorities from involved countries.
 */
package eu.arrowhead.qosmonitorclient.messages;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Event {

    private String type;
    private String payload;
    private String timestamp;
    private Map<String, String> eventMetadata = new HashMap<>();

    public Event() {
    }

    public Event(String type, String payload, String timestamp, Map<String, String> eventMetadata) {
        this.type = type;
        this.payload = payload;
        this.timestamp = timestamp;
        this.eventMetadata = eventMetadata;
    }

    public Event(String type, String payload, LocalDateTime timestamp, Map<String, String> eventMetadata) {
        this.type = type;
        this.payload = payload;
        this.timestamp = timestamp.toString();
        this.eventMetadata = eventMetadata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getEventMetadata() {
        return eventMetadata;
    }

    public void setEventMetadata(Map<String, String> eventMetadata) {
        this.eventMetadata = eventMetadata;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Event: ");
        sb.append(type);
        sb.append(" = ").append(payload);
        sb.append(" at ").append(timestamp);
        return sb.toString();
    }

}
