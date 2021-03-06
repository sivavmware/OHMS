/* ********************************************************************************
 * Constants.java
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
package com.vmware.vrack.hms.common.util;

public class Constants
{

    // public static final String REG_EVENT_URI = "/api/1.0/hms/events/subscribe";
    public static final String HMS_DEFAULT_CONFIG_FILE = "config/hms-config.properties";

    public static final Integer UDP_PORT = 0;

    public static final Integer THREAD_POOL_SIZE = 8;

    public static final Integer MONITORING_THREAD_POOL_SIZE = 20;

    // Monitor Frequency in milliseconds
    public static final Integer HOST_NODE_MONITOR_FREQUENCY = 600000;

    public static final String HMS_REGISTER_NME_URI = "/api/1.0/hms/events/register";

    public static final String HMS_ON_DEMAND_EVENTS_FETCH_URI = "/api/1.0/hms/event/host";

    public static final String HMS_ON_DEMAND_SWITCH_EVENTS_FETCH_URI = "/api/1.0/hms/event/switches";

    public static final String HMS_LOCAL_MONITORED_EVENTS_URI = "/api/1.0/hms/events/monitoredevents";

    public static final String NIC_LINK_DOWN_SENSOR_DISCRETE_VALUE = "NIC Link Down";

    public static final String HMS_OOB_ABOUT_ENDPOINT = "/api/1.0/hms/about";

    public static final String HMS_OOB_HEALTH_MONITOR_ENDPOINT = "/api/1.0/hms/event/host/HMS";

    public static final String HOSTS = "hosts";

    public static final String SWITCHES = "switches";

    public static final String HMS_OOB_CPU_INFO_ENDPOINT = "/api/1.0/hms/host/{host_id}/cpuinfo";

    public static final String HMS_OOB_MEMORY_INFO_ENDPOINT = "/api/1.0/hms/host/{host_id}/memoryinfo";

    public static final String HMS_OOB_NIC_INFO_ENDPOINT = "/api/1.0/hms/host/{host_id}/nicinfo";

    public static final String HMS_OOB_HDD_INFO_ENDPOINT = "/api/1.0/hms/host/{host_id}/storageinfo";

    public static final String HMS_OOB_STORAGE_CONTROLLER_INFO_ENDPOINT =
        "/api/1.0/hms/host/{host_id}/storagecontrollerinfo";

    public static final String HMS_OOB_SUPPORTED_OPEARIONS_ENDPOINT = "/api/1.0/hms/service/operations";

    public static final String HMS_OOB_HOST_INFO_ENDPOINT = "/api/1.0/hms/host/{host_id}";

    public static final String HMS_OOB_SWITCH_INFO_ENDPOINT = "/api/1.0/hms/switches/{switch_id}";

    public static final String HMS_OOB_HOST_POWER_STATUS_ENDPOINT = "/api/1.0/hms/host/{host_id}/powerstatus";

    /**
     * Event Generator ID of HMS That will be used for the events Generated By HMS
     */
    public static final String HMS_EVENT_GENERATOR_ID = "HMS";

    public static final String HMS_INBAND_THREADLOCAL_EVENT_KEY = "Events";

    public static final String GRANT_EXECUTE_RIGHTS = "chmod +x %s";

    public static final String RESOURCE_BUSY = "Resource is Busy";

    public static final String HMS_OOB_UPGRADE_ENDPOINT = "/api/1.0/hms/upgrade";

    public static final String HMS_OOB_UPGRADE_MONITOR_ENDPOINT = "/api/1.0/hms/upgrade/monitor";

    public static final String HMS_OOB_UPLOAD_ENDPOINT = "/api/1.0/hms/upgrade/upload";

    public static final String PRM_HMS_SERVICESTATE_ENDPOINT = "/vrm-ui/api/1.0/prm/hmsservicestate";

    public static final String HMS_OOB_ROLLABACK_ENDPOINT = "/api/1.0/hms/upgrade/rollback";

    public static final String HMS_OOBAGENT_STATUS = "HMS_OOBAGENT_STATUS";

    // FIXME: For RTP1, Management Switch ID is "S0".
    public static final String HMS_MANAGEMENT_SWITCH_ID = "S0";
}
