/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.automowerconnect.internal.handler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.ThingStatusInfo;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.automowerconnect.internal.AutomowerConnectBindingConstants;
import org.openhab.binding.automowerconnect.internal.config.Automower;
import org.openhab.binding.automowerconnect.internal.data.AutomowerConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link DeviceHandler} class ...
 *
 * @author Torben Erler - Initial contribution
 */
public class DeviceHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(DeviceHandler.class);

    private String deviceID;

    private final AutomowerConnectService service = new AutomowerConnectService();

    private ScheduledFuture<?> pollingJob;

    /**
     * @param thing
     */
    public DeviceHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
        logger.debug("Initializing Automower Device handler '{}'", getThing().getUID());

        Configuration config = getThing().getConfiguration();
        this.deviceID = (String) config.get(AutomowerConnectBindingConstants.CONFIG_DEVICE_ID);

        Bridge b = this.getBridge();
        if (b == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE,
                    "Could not find bridge (AutomowerConnect config). Did you select one?");
            return;
        }

        WebserviceHandler handler = (WebserviceHandler) b.getHandler();

        updateStatus(ThingStatus.UNKNOWN);

        pollingJob = scheduler.scheduleWithFixedDelay(() -> {
            try {
                logger.debug("Try to refresh detail data");
                // TODO updateDetailData();
            } catch (RuntimeException r) {
                logger.debug("Caught exception in ScheduledExecutorService of DeviceHandler", r);
                // no status change, since in case of error in here,
                // the old values for opening time will be continue to be used
            }
        }, 15, 86400, TimeUnit.SECONDS);// 24*60*60 = 86400, a whole day in seconds!

        logger.debug("Refresh job scheduled to run every 24 hours for '{}'", getThing().getUID());
    }

    @Override
    public void dispose() {
        if (pollingJob != null) {
            pollingJob.cancel(true);
        }
        super.dispose();
    }

    @Override
    public void bridgeStatusChanged(ThingStatusInfo bridgeStatusInfo) {
        logger.debug("Bridge Status updated to {} for device: {}", bridgeStatusInfo.getStatus(), getThing().getUID());

        if (bridgeStatusInfo.getStatus() != ThingStatus.ONLINE) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE, bridgeStatusInfo.getDescription());
        }
    }

    /***
     * Updates the channels of a mower device item
     *
     * @param station
     */
    public void updateData(Automower automowerDevice) {
        logger.debug("Update Device data '{}'", getThing().getUID());

        // TODO
        /***
         * if (StringUtils.containsOnly(automowerDevice.getDiesel(), "01234567890.")) {
         * DecimalType diesel = new DecimalType(automowerDevice.getDiesel());
         * updateState(CHANNEL_DIESEL, diesel);
         * } else {
         * updateState(CHANNEL_DIESEL, UnDefType.UNDEF);
         * }
         * if (StringUtils.containsOnly(automowerDevice.getE10(), "01234567890.")) {
         * DecimalType e10 = new DecimalType(automowerDevice.getE10());
         * updateState(CHANNEL_E10, e10);
         * } else {
         * updateState(CHANNEL_E10, UnDefType.UNDEF);
         * }
         * if (StringUtils.containsOnly(automowerDevice.getE5(), "01234567890.")) {
         * DecimalType e5 = new DecimalType(automowerDevice.getE5());
         * updateState(CHANNEL_E5, e5);
         * } else {
         * updateState(CHANNEL_E5, UnDefType.UNDEF);
         * }
         *
         * updateState(CHANNEL_STATION_OPEN, (automowerDevice.isOpen() ? OpenClosedType.OPEN : OpenClosedType.CLOSED));
         ***/
        updateStatus(ThingStatus.ONLINE);
    }

    /**
     * @return the deviceID
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * @param deviceID the deviceID to set
     */
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
