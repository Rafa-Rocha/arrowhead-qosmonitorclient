/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.qosmonitorclient.httprequests;

import com.google.gson.Gson;
import eu.arrowhead.qosmonitorclient.database.ArrowheadService;
import eu.arrowhead.qosmonitorclient.database.ArrowheadSystem;
import eu.arrowhead.qosmonitorclient.database.EventFilter;
import eu.arrowhead.qosmonitorclient.database.ServiceRegistryEntry;
import eu.arrowhead.qosmonitorclient.messages.ArrowheadException;
import eu.arrowhead.qosmonitorclient.messages.ExceptionType;
import eu.arrowhead.qosmonitorclient.ui.ClientUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author IL0016G
 */
public class HttpRequestHandler {

    static CloseableHttpClient httpClient = HttpClients.custom().build();

    private static String EVENT_HANDLER_ADDRESS = "http://0.0.0.0:8454/";
    private static String SERVICE_REGISTRY_ADDRESS = "http://0.0.0.0:8442/";

    private static final String SUBSCRIBE_URI = "eventhandler/subscription";
    private static final String SERVICE_REGISTRY_REGISTER_URI = "serviceregistry/register";
    private static final String SERVICE_REGISTRY_UNREGISTER_URI = "serviceregistry/remove";
    private static final String NOTIFY_URI = "qosmonitorclient";

    private static final String EVENT_TYPE = "temperature";
    
    public static String getEventHandlerAddress() {
        return HttpRequestHandler.EVENT_HANDLER_ADDRESS;
    }
    
    public static void setEventHandlerAddress(String newEventHandlerAddress) {
        HttpRequestHandler.EVENT_HANDLER_ADDRESS = newEventHandlerAddress;
    }
    
    public static String getServiceRegistryAddress() {
        return HttpRequestHandler.SERVICE_REGISTRY_ADDRESS;
    }
    
    public static void setServiceRegistryAddress(String newServiceRegistryAddress) {
        HttpRequestHandler.SERVICE_REGISTRY_ADDRESS = newServiceRegistryAddress;
    }

    public static void unsubscribe(StringEntity input) {
        HttpPut putRequest = new HttpPut(EVENT_HANDLER_ADDRESS + SUBSCRIBE_URI);
        HttpResponse response = null;
        putRequest.setEntity(input);
        try {
            response = httpClient.execute(putRequest);
        } catch (IOException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException ex) {

        } catch (UnsupportedOperationException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        String resultString = result.toString();
        System.out.println(resultString);
    }

    public static void subscribe() {
        ClientUI ui = ClientUI.getInstance();
        ui.appendTextToTextArea(
                "[" + LocalDateTime.now().toString() + "]  "
                + "Subscribing to Event Handler...\n"
        );

        HttpPost postRequest = new HttpPost(EVENT_HANDLER_ADDRESS + SUBSCRIBE_URI);

        ArrowheadSystem consumer = new ArrowheadSystem("QoSMonitorClient", "0.0.0.0", 8488, null);
        EventFilter eventFilter = new EventFilter(
                EVENT_TYPE, consumer, new ArrayList<>(), null,
                null, new HashMap<>(), 8488, NOTIFY_URI, false
        );

        Gson gson = new Gson();
        String json = gson.toJson(eventFilter);
        StringEntity input = null;
        try {
            input = new StringEntity(json);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        input.setContentType("application/json");
        postRequest.setEntity(input);
        HttpResponse response = null;
        try {
            response = httpClient.execute(postRequest);
        } catch (IOException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }

        BufferedReader rd = null;

        if (response.getStatusLine().getStatusCode() == Status.NO_CONTENT.getStatusCode()) {
            unsubscribe(input);
            
            try {
                response = httpClient.execute(postRequest);
            } catch (IOException ex) {
                Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
            }
        }

        
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException ex) {

        } catch (UnsupportedOperationException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        String resultString = result.toString();
        System.out.println(resultString);

        ui.appendTextToTextArea(
                "[" + LocalDateTime.now().toString() + "]  "
                + "Subscription successful\n\n"
        );

    }

    public static ServiceRegistryEntry registerToServiceRegistry() {
        ClientUI ui = ClientUI.getInstance();
        ui.appendTextToTextArea(
                "[" + LocalDateTime.now().toString() + "]  "
                + "Registering on Service Registry...\n"
        );

        HttpPost postRequest = new HttpPost(SERVICE_REGISTRY_ADDRESS + SERVICE_REGISTRY_REGISTER_URI);

        ArrowheadSystem provider = new ArrowheadSystem("QoSMonitorClient", "0.0.0.0", 8488, null);
        ArrowheadService providedService = new ArrowheadService("QoSMonitorClient", Collections.singletonList("JSON"), new HashMap<>());

        ServiceRegistryEntry entry = new ServiceRegistryEntry(providedService, provider, 8488, "qosmonitorclient/newevent");

        Gson gson = new Gson();
        String json = gson.toJson(entry);
        StringEntity input = null;
        
        try {
            input = new StringEntity(json);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);            
        }
        
        input.setContentType("application/json");
        postRequest.setEntity(input);
        HttpResponse response = null;
        try {
            response = httpClient.execute(postRequest);
        } catch (ArrowheadException ex) {
            if (ex.getExceptionType() == ExceptionType.DUPLICATE_ENTRY) {
                try {
                    System.out.println("Received DuplicateEntryException from SR, sending delete request and then registering again.");
                    unregisterFromServiceRegistry(entry);
                    response = httpClient.execute(postRequest);
                } catch (IOException ex1) {
                    Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException ex) {

        } catch (UnsupportedOperationException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        String resultString = result.toString();
        System.out.println(resultString);

        ui.appendTextToTextArea(
                "[" + LocalDateTime.now().toString() + "]  "
                + "Register successful\n\n"
        );
        
        return entry;
    }
    
    public static void unregisterFromServiceRegistry(ServiceRegistryEntry entry) {
        Gson gson = new Gson();
        String json;
        StringEntity input = null;
        
        // Unsubscribe from EventHandler
        ArrowheadSystem consumer = new ArrowheadSystem("QoSMonitorClient", "0.0.0.0", 8488, null);
        EventFilter eventFilter = new EventFilter(
                EVENT_TYPE, consumer, new ArrayList<>(), null,
                null, new HashMap<>(), 8488, NOTIFY_URI, false
        );
        json = gson.toJson(eventFilter);
        try {
            input = new StringEntity(json);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        input.setContentType("application/json");
        unsubscribe(input);
        
        // Unregister from Service Registry
        HttpPut putRequest = new HttpPut(SERVICE_REGISTRY_ADDRESS + SERVICE_REGISTRY_UNREGISTER_URI);

        json = gson.toJson(entry);
        
        try {
            input = new StringEntity(json);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        input.setContentType("application/json");
        putRequest.setEntity(input);
        HttpResponse response = null;
        try {
            response = httpClient.execute(putRequest);
        } catch (IOException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException ex) {

        } catch (UnsupportedOperationException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        StringBuilder result = new StringBuilder();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException ex) {
            Logger.getLogger("HttpRequestHandler").log(Level.SEVERE, null, ex);
        }
        String resultString = result.toString();
        System.out.println(resultString);
    }
}
