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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.automowerconnect.internal.AutomowerConnectBindingConstants;
import org.openhab.binding.automowerconnect.internal.config.Automower;
import org.openhab.binding.automowerconnect.internal.config.AutomowerConnectToken;
import org.openhab.binding.automowerconnect.internal.data.AutomowerConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link WebserviceHandler} class ...
 *
 * @author Torben Erler - Initial contribution
 */
public class WebserviceHandler extends BaseBridgeHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String username;
    private String password;

    private AutomowerConnectToken token;

    private int refreshInterval;

    private final AutomowerConnectService service = new AutomowerConnectService();
    private Map<String, Automower> mowerMap;
    private ScheduledFuture<?> pollingJob;

    public WebserviceHandler(Bridge bridge) {
        super(bridge);
        mowerMap = new HashMap<>();
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
        logger.debug("Initialize Bridge");

        Configuration config = getThing().getConfiguration();
        this.username = (String) config.get(AutomowerConnectBindingConstants.CONFIG_USERNAME);
        this.password = (String) config.get(AutomowerConnectBindingConstants.CONFIG_PASSWORD);
        this.refreshInterval = ((BigDecimal) config.get(AutomowerConnectBindingConstants.CONFIG_REFRESH)).intValue();

        updateStatus(ThingStatus.UNKNOWN);

        int pollingPeriod = this.refreshInterval;
        pollingJob = scheduler.scheduleWithFixedDelay(() -> {
            logger.debug("Try to refresh data");
            try {
                updateAutomowerConnectData();
                updateAutomowerThings();
            } catch (RuntimeException r) {
                logger.debug("Caught exception in ScheduledExecutorService of BridgeHandler. RuntimeException: ", r);
                updateStatus(ThingStatus.OFFLINE);
            }
        }, pollingPeriod, pollingPeriod, TimeUnit.MINUTES);

        logger.debug("Refresh job scheduled to run every {} min. for '{}'", pollingPeriod, getThing().getUID());
    }

    @Override
    public void dispose() {
        if (pollingJob != null) {
            pollingJob.cancel(true);
        }

        super.dispose();
    }

    public void updateAutomowerConnectData() {
        try {
            String deviceIDsString = "";
            // TODO

            if (deviceIDsString.isEmpty()) {
                logger.debug("No device id's found. Nothing to update");
                return;
            }

            // TODO
        } catch (Exception e/*** ParseException e ***/ // TODO
        ) {
            logger.error("ParseException: ", e);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
        }
    }

    /***
     * Updates all registered Automowers with new data
     */
    public void updateAutomowerThings() {
        logger.debug("UpdateStationThings: getThing().getThings().size {}", getThing().getThings().size());

        for (Thing thing : getThing().getThings()) {
            DeviceHandler mowh = (DeviceHandler) thing.getHandler();
            Automower mow = this.mowerMap.get(mowh.getDeviceID());
            if (mow == null) {
                logger.debug("Mower with id {} is not updated!", mowh.getDeviceID());
            } else {
                mowh.updateData(mow);
            }

        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the token
     */
    public AutomowerConnectToken getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(AutomowerConnectToken token) {
        this.token = token;
    }

    /**
     * @return the refreshInterval
     */
    public int getRefreshInterval() {
        return refreshInterval;
    }

    /**
     * @param refreshInterval the refreshInterval to set
     */
    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

}
