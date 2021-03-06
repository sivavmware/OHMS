/* ********************************************************************************
 * HMSResourceMonitor.java
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
package com.vmware.vrack.hms.common.resource.task.jmx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vmware.vrack.hms.common.exception.HmsException;
import com.vmware.vrack.hms.common.exception.HmsOperationNotSupportedException;
import com.vmware.vrack.hms.common.monitoring.MonitorTask;
import com.vmware.vrack.hms.common.monitoring.MonitoringTaskResponse;
import com.vmware.vrack.hms.common.servernodes.api.ServerComponent;
import com.vmware.vrack.hms.common.servernodes.api.ServerNode;
import com.vmware.vrack.hms.common.servernodes.api.event.ServerComponentEvent;
import com.vmware.vrack.hms.common.util.EventsUtil;

public class HMSResourceMonitor
    extends MonitorTask
{
    private static Logger logger = Logger.getLogger( HMSResourceMonitor.class );

    public HMSResourceMonitor()
    {
        super();
    }

    public HMSResourceMonitor( MonitoringTaskResponse response, ServerComponent component )
    {
        super( response, component );
    }

    public HMSResourceMonitor( MonitoringTaskResponse response )
    {
        super( response );
    }

    public MonitoringTaskResponse call()
        throws Exception
    {
        return executeTask();
    }

    public MonitoringTaskResponse executeTask()
        throws HmsException
    {
        Map<ServerComponent, List<ServerComponentEvent>> componentSensorData =
            new HashMap<ServerComponent, List<ServerComponentEvent>>();
        if ( EventsUtil.isComponentServerApiSupported( response.getSensorInfoProvider(), component,
                                                       node.getServiceObject() ) )
        {
            componentSensorData.put( component,
                                     response.getSensorInfoProvider().getComponentEventList( node.getServiceObject(),
                                                                                             component ) );
        }
        else
        {
            String error =
                String.format( "Operation is not supported for Component %s of Node %s", component, node.getNodeID() );
            logger.error( error );
            throw new HmsOperationNotSupportedException( error );
        }
        ( (ServerNode) node ).setComponentSensorData( componentSensorData );
        return response;
    }
}
