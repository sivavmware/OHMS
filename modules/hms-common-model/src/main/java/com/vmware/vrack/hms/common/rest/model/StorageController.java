/* ********************************************************************************
 * StorageController.java
 * 
 * Copyright © 2013 - 2016 VMware, Inc. All Rights Reserved.

 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, without warranties or
 * conditions of any kind, EITHER EXPRESS OR IMPLIED. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * *******************************************************************************/
package com.vmware.vrack.hms.common.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vmware.vrack.hms.common.servernodes.api.storagecontroller.StorageControllerInfo;

/**
 * @author VMware, Inc. Class for Storage Controller (Adaptors) related properties
 */
@JsonIgnoreProperties( ignoreUnknown = true )
@JsonInclude( JsonInclude.Include.NON_NULL )
public class StorageController
    extends FruComponent
{
    private String id;

    /*
     * Storage controller/HBA device name vmhba0, 1, 2 etc...
     */
    private String deviceName;

    /*
     * Number of Storage devices connected
     */
    private int numOfStorageDevicesConnected;

    /*
     * Storage controller driver mpt2sas, megaraid_sas etc...
     */
    private String driver;

    /*
     * Storage Controller firmware version
     */
    private String firmwareVersion;

    /*
     * PCI device id of the controller
     */
    private String pciDeviceId;

    private String operationalStatus;

    private String hostId;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    /*
     * Get the storage controller (Host Bus Adapter) device name
     */
    public String getDeviceName()
    {
        return deviceName;
    }

    /*
     * Set the storage controller (Host Bus Adapter) device name
     */
    public void setDeviceName( String deviceName )
    {
        this.deviceName = deviceName;
    }

    /*
     * Get the number of devices connected to Storage Controller (Host Bus Adapter)
     */
    public int getNumOfStorageDevicesConnected()
    {
        return numOfStorageDevicesConnected;
    }

    /*
     * Set the number of devices connected to Storage Controller (Host Bus Adapter)
     */
    public void setNumOfStorageDevicesConnected( int numOfStorageDevicesConnected )
    {
        this.numOfStorageDevicesConnected = numOfStorageDevicesConnected;
    }

    /*
     * Get the Storage Controller (Host Bus Adapter) driver
     */
    public String getDriver()
    {
        return driver;
    }

    /*
     * Set the Storage Controller (Host Bus Adapter) driver
     */
    public void setDriver( String driver )
    {
        this.driver = driver;
    }

    /*
     * Get the Firmware version of the Storage Controller (Host Bus Adapter)
     */
    public String getFirmwareVersion()
    {
        return firmwareVersion;
    }

    /*
     * Set the Firmware version of the Storage Controller (Host Bus Adapter)
     */
    public void setFirmwareVersion( String firmwareVersion )
    {
        this.firmwareVersion = firmwareVersion;
    }

    /*
     * Get the PCI device id of the controller
     */
    public String getPciDeviceId()
    {
        return pciDeviceId;
    }

    /*
     * Sets the PCI device id of the controller
     */
    public void setPciDeviceId( String pciDeviceId )
    {
        this.pciDeviceId = pciDeviceId;
    }

    public String getOperationalStatus()
    {
        return operationalStatus;
    }

    public void setOperationalStatus( String operationalStatus )
    {
        this.operationalStatus = operationalStatus;
    }

    public String getHostId()
    {
        return hostId;
    }

    public void setHostId( String hostId )
    {
        this.hostId = hostId;
    }

    /**
     * Get the Physical Storage Controller or Host Bus Adapter (HBA) FRU Information. Wrapper method to get the
     * StorageController object for the node
     *
     * @param serverNodeStorageControllerInfo
     * @param nodeID
     * @return StorageController
     */
    public StorageController getStorageController( StorageControllerInfo serverNodeStorageControllerInfo,
                                                   String nodeID )
    {
        StorageController storageController = new StorageController();
        storageController.setId( serverNodeStorageControllerInfo.getId() );
        storageController.setDeviceName( serverNodeStorageControllerInfo.getDeviceName() );
        storageController.setNumOfStorageDevicesConnected( serverNodeStorageControllerInfo.getNumOfStorageDevicesConnected() );
        storageController.setDriver( serverNodeStorageControllerInfo.getDriver() );
        // storageController.setFirmwareVersion(serverNodeStorageControllerInfo.getFirmwareVersion());
        storageController.setPciDeviceId( serverNodeStorageControllerInfo.getPciDeviceId() );
        storageController.setOperationalStatus( serverNodeStorageControllerInfo.getFruOperationalStatus().toString() );
        storageController.setHostId( nodeID );
        storageController.setLocation( serverNodeStorageControllerInfo.getLocation() );
        if ( serverNodeStorageControllerInfo.getComponentIdentifier() != null )
        {
            storageController.setComponentIdentifier( serverNodeStorageControllerInfo.getComponentIdentifier() );
        }
        return storageController;
    }
}
