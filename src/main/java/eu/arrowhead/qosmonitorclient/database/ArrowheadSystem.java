/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.qosmonitorclient.database;

import eu.arrowhead.qosmonitorclient.json.support.ArrowheadSystemSupport;
import eu.arrowhead.qosmonitorclient.messages.ArrowheadBase;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class for storing Arrowhead Systems in the database. The "system_name"
 * column must be unique.
 */
public class ArrowheadSystem extends ArrowheadBase {

    private static final Set<String> alwaysMandatoryFields = new HashSet<>(Collections.singleton("systemName"));

    private String systemName;

    private String address;

    private Integer port;

    private String authenticationInfo;

    public ArrowheadSystem() {
    }

    public ArrowheadSystem(String systemName, String address, Integer port, String authenticationInfo) {
        this.systemName = systemName;
        this.address = address;
        this.port = port;
        this.authenticationInfo = authenticationInfo;
    }

    public ArrowheadSystem(String json) {
        String[] fields = json.split(",");
        this.systemName = fields[0].equals("null") ? null : fields[0];

        if (fields.length == 4) {
            this.address = fields[1].equals("null") ? null : fields[1];
            this.port = fields[2].equals("null") ? null : Integer.valueOf(fields[2]);
            this.authenticationInfo = fields[3].equals("null") ? null : fields[3];
        }
    }

    public ArrowheadSystem(ArrowheadSystemSupport system) {
        this.systemName = system.getSystemGroup() + "_" + system.getSystemName();
        this.address = system.getAddress();
        this.port = system.getPort();
        this.authenticationInfo = system.getAuthenticationInfo();
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public ArrowheadSystem(ArrowheadSystem system) {
        this.systemName = system.systemName;
        this.address = system.address;
        this.port = system.port;
        this.authenticationInfo = system.authenticationInfo;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(String authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public Set<String> missingFields(boolean throwException, Set<String> mandatoryFields) throws Exception {
        Set<String> mf = new HashSet<>(alwaysMandatoryFields);
        if (mandatoryFields != null) {
            mf.addAll(mandatoryFields);
        }
        Set<String> nonNullFields = getFieldNamesWithNonNullValue();
        for (final String field : mf) {
            if (field.startsWith(getClass().getSimpleName())) {
                nonNullFields = prefixFieldNames(nonNullFields);
                break;
            }
        }
        mf.removeAll(nonNullFields);

        if (throwException && !mf.isEmpty()) {
            throw new Exception("Missing mandatory fields for " + getClass().getSimpleName() + ": " + String.join(", ", mf));
        }
        return mf;
    }

    public String toArrowheadCommonName(String operator, String cloudName) {
        if (systemName.contains(".") || operator.contains(".") || cloudName.contains(".")) {
            throw new IllegalArgumentException("The string fields can not contain dots!");
        }
        //throws NPE if any of the fields are null
        return systemName.concat(".").concat(cloudName).concat(".").concat(operator).concat(".").concat("arrowhead.eu");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArrowheadSystem that = (ArrowheadSystem) o;

        if (!systemName.equals(that.systemName)) {
            return false;
        }
        if (address != null ? !address.equals(that.address) : that.address != null) {
            return false;
        }
        return authenticationInfo != null ? authenticationInfo.equals(that.authenticationInfo) : that.authenticationInfo == null;
    }

    @Override
    public int hashCode() {
        int result = systemName.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (authenticationInfo != null ? authenticationInfo.hashCode() : 0);
        return result;
    }

    //NOTE ArrowheadSystemKeyDeserializer relies on this implementation, do not change it without changing the (String json) constructor
    @Override
    public String toString() {
        return systemName + "," + address + "," + port + "," + authenticationInfo;
    }

}

