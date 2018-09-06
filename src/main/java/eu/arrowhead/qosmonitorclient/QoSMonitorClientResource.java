/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.qosmonitorclient;

import com.google.gson.Gson;
import eu.arrowhead.qosmonitorclient.messages.Event;
import eu.arrowhead.qosmonitorclient.ui.ClientUI;
import java.time.LocalDateTime;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author IL0016G
 */
@Path("qosmonitorclient")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QoSMonitorClientResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveEvent(String eventJson) {
        LocalDateTime time = LocalDateTime.now();
        
        Gson gson = new Gson();
        Event event = gson.fromJson(eventJson, Event.class);

        ClientUI ui = ClientUI.getInstance();
        ui.appendTextToTextArea(
                "[" + time.toString() + "]  "
                + event.toString() + "\n"
        );

        return Response.ok("OK").build();
    }
}
