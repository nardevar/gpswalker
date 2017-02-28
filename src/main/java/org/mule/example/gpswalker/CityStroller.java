/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.example.gpswalker;

import java.util.concurrent.atomic.AtomicBoolean;

import org.mule.api.annotations.expressions.Lookup;
import org.springframework.beans.factory.annotation.Value;

/**
 * Generates a random walk around a city
 * 
 * Ref: http://blogs.mulesoft.com/dev/mule-dev/walk-this-way-building-ajax-apps-
 * with-mule/
 */
public class CityStroller {

	public static final GpsCoord ZURICH = new GpsCoord(47.3667f, 8.5500f);
	public static final GpsCoord CHICAGO = new GpsCoord(41.8369f, -87.6847f);
	public static final GpsCoord SAN_FRANCISCO = new GpsCoord(37.789167f,
			-122.419281f);
	public static final GpsCoord LONDON = new GpsCoord(37.788423f, -122.39823f);
	public static final GpsCoord VALLETTA = new GpsCoord(35.898504f, 14.514313f);

	private double initialAngle = 0.0;

	@Lookup("gps-latitude")
	// @Value("${gps-latitude}")
	private float gpsLatitude;

	@Lookup("gps-latitude")
	// @Value("${gps-longitude}")
	private float gpsLongitude;

	// private volatile GpsCoord currentCoord = new GpsCoord(gpsLatitude,
	// gpsLongitude);
	private volatile GpsCoord currentCoord = ZURICH;
	private AtomicBoolean firstTime = new AtomicBoolean(true);

	public CityStroller() {
		// currentCoord = new GpsCoord(gpsLatitude, gpsLongitude);
		System.out.println("\n\nInitial GpsCoord is "
				+ currentCoord.printCoords());
		initialAngle = Math.random() * Math.PI * 2;
		System.out.println("\ninitial angle = " + initialAngle);
	}

	// could use a better algorithm here or real test data for better results
	public GpsCoord generateNextCoord() {
		if (firstTime.get()) {
			firstTime.set(false);
		} else {
			double dist = Math.random() * 0.02;
			double angle = initialAngle + Math.random() * Math.PI;

			float lat = currentCoord.getLatitude()
					+ (float) (dist * Math.sin(angle));
			float lng = currentCoord.getLongitude()
					+ (float) (dist * Math.cos(angle));

			currentCoord = new GpsCoord(lat, lng);
		}
		return currentCoord;
	}

	public GpsCoord getCurrentCoord() {
		return currentCoord;
	}

	public void setCurrentCoord(GpsCoord currentCoord) {
		this.currentCoord = currentCoord;
		firstTime.set(false);
	}
}
