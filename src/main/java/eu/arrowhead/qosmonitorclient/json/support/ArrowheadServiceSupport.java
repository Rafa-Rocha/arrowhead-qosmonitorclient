/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.qosmonitorclient.json.support;

import eu.arrowhead.qosmonitorclient.database.ArrowheadService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrowheadServiceSupport {

  private String serviceGroup;
  private String serviceDefinition;
  private List<String> interfaces = new ArrayList<>();
  private Map<String, String> serviceMetadata = new HashMap<>();

  public ArrowheadServiceSupport() {
  }

  public ArrowheadServiceSupport(ArrowheadService service) {
    if (service.getServiceDefinition().contains("_")) {
      String[] parts = service.getServiceDefinition().split("_");
      this.serviceGroup = parts[0];
      this.serviceDefinition = parts[1];
    } else {
      this.serviceDefinition = service.getServiceDefinition();
    }
    this.interfaces = service.getInterfaces();
    this.serviceMetadata = service.getServiceMetadata();
  }

  public ArrowheadServiceSupport(String serviceGroup, String serviceDefinition, List<String> interfaces, Map<String, String> serviceMetadata) {
    this.serviceGroup = serviceGroup;
    this.serviceDefinition = serviceDefinition;
    this.interfaces = interfaces;
    this.serviceMetadata = serviceMetadata;
  }

  public String getServiceGroup() {
    return serviceGroup;
  }

  public void setServiceGroup(String serviceGroup) {
    this.serviceGroup = serviceGroup;
  }

  public String getServiceDefinition() {
    return serviceDefinition;
  }

  public void setServiceDefinition(String serviceDefinition) {
    this.serviceDefinition = serviceDefinition;
  }

  public List<String> getInterfaces() {
    return interfaces;
  }

  public void setInterfaces(List<String> interfaces) {
    this.interfaces = interfaces;
  }

  public Map<String, String> getServiceMetadata() {
    return serviceMetadata;
  }

  public void setServiceMetadata(Map<String, String> serviceMetadata) {
    this.serviceMetadata = serviceMetadata;
  }
}
