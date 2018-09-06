/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.qosmonitorclient;

import eu.arrowhead.qosmonitorclient.database.ServiceRegistryEntry;
import eu.arrowhead.qosmonitorclient.httprequests.HttpRequestHandler;
import eu.arrowhead.qosmonitorclient.ui.ClientUI;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


/**
 *
 * @author IL0016G
 */
public class QoSMonitorClientMain {
    
    public static final String BASE_URI = "http://0.0.0.0:8488";
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
       
        final HttpServer server = startServer();
        System.out.println("\nJersey app started");
        
        ClientUI ui = ClientUI.getInstance();
        ui.setVisible(true);
  
        ui.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                unregisterFromServiceRegistry(ui.getServiceRegistryEntry());
                server.stop();
            }
        });
    }
    
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example.rest package
        final ResourceConfig rc = new ResourceConfig();
        rc.register(QoSMonitorClientResource.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
    
    private static void unregisterFromServiceRegistry(ServiceRegistryEntry registeredEntry) {
        if (registeredEntry != null) {
            HttpRequestHandler.unregisterFromServiceRegistry(registeredEntry);
            System.out.println("Successfully removed service(s) from ServiceRegistry");
        }
    }
}
