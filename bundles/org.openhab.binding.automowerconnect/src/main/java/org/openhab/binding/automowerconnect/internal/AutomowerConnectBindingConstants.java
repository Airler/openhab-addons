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
package org.openhab.binding.automowerconnect.internal;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link AutomowerConnectBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Torben Erler - Initial contribution
 */
@NonNullByDefault
public class AutomowerConnectBindingConstants {

    private static final String BINDING_ID = "automowerconnect";

    // List of main device types
    public static final String DEVICE_315X = "AUTOMOWER_315X";
    public static final String DEVICE_430X = "AUTOMOWER_430X";
    public static final String DEVICE_450X = "AUTOMOWER_450X";
    public static final String DEVICE_435X_AWD = "AUTOMOWER_435X_AWD";

    // List of all Thing Type UIDs
    // public static final ThingTypeUID THING_TYPE_SAMPLE = new ThingTypeUID(BINDING_ID, "sample");
    public static final ThingTypeUID BRIDGE_THING_TYPE = new ThingTypeUID(BINDING_ID, "webservice");

    public static final ThingTypeUID MOWER_315X_THING_TYPE = new ThingTypeUID(BINDING_ID, DEVICE_315X);
    public static final ThingTypeUID MOWER_430X_THING_TYPE = new ThingTypeUID(BINDING_ID, DEVICE_430X);
    public static final ThingTypeUID MOWER_450X_THING_TYPE = new ThingTypeUID(BINDING_ID, DEVICE_450X);
    public static final ThingTypeUID MOWER_435X_AWD_THING_TYPE = new ThingTypeUID(BINDING_ID, DEVICE_435X_AWD);

    public static final Set<ThingTypeUID> BRIDGE_THING_TYPES_UIDS = Collections.singleton(BRIDGE_THING_TYPE);
    public static final Set<ThingTypeUID> SUPPORTED_DEVICE_THING_TYPES_UIDS = Collections.unmodifiableSet(
            Stream.of(MOWER_315X_THING_TYPE, MOWER_430X_THING_TYPE, MOWER_450X_THING_TYPE, MOWER_435X_AWD_THING_TYPE)
                    .collect(Collectors.toSet()));
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections
            .unmodifiableSet(Stream.of(BRIDGE_THING_TYPES_UIDS.stream(), SUPPORTED_DEVICE_THING_TYPES_UIDS.stream())
                    .reduce(Stream::concat).orElseGet(Stream::empty).collect(Collectors.toSet()));

    // API Urls
    public static final String AMC_API_IAM_URL = "https://iam-api.dss.husqvarnagroup.net/api/v3/";
    public static final String AMC_API_TRACK_URL = "https://amc-api.dss.husqvarnagroup.net/v1/";

    // List of all Thing config ids
    public static final String CONFIG_USERNAME = "user";
    public static final String CONFIG_PASSWORD = "password";
    public static final String CONFIG_DEVICE_ID = "deviceid";
    public static final String CONFIG_REFRESH = "refresh";

    // List of all Channel ids
    // public static final String CHANNEL_1 = "channel1";

}
