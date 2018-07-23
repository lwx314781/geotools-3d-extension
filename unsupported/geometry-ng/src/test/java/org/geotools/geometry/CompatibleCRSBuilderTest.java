/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2004-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.geometry;

import java.util.ArrayList;
import java.util.List;

import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeocentricCRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.geometry.ISOGeometryBuilder;
import org.opengis.geometry.coordinate.LineString;
import org.opengis.geometry.coordinate.Position;
import org.opengis.geometry.primitive.CurveSegment;
import org.opengis.geometry.primitive.OrientableCurve;
import org.opengis.geometry.primitive.Ring;
import org.opengis.geometry.primitive.Surface;
import org.opengis.geometry.primitive.SurfaceBoundary;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import junit.framework.TestCase;

/**
 * 
 *
 * @source $URL$
 */
public class CompatibleCRSBuilderTest extends TestCase {

	public void testCRSEquals() throws Exception {

		CoordinateReferenceSystem crs = DefaultGeocentricCRS.CARTESIAN;

		CoordinateReferenceSystem crs2 = CRS.parseWKT(crs.toWKT());

		assertTrue(CRS.equalsIgnoreMetadata(crs, crs2));
	}

	public void testPrimFactCRS() throws UnsupportedOperationException,
			FactoryException {
		CoordinateReferenceSystem crs = DefaultGeocentricCRS.CARTESIAN;
		ISOGeometryBuilder builder = new ISOGeometryBuilder(crs);

		CoordinateReferenceSystem crs2 = CRS.parseWKT(crs.toWKT());
		ISOGeometryBuilder builder2 = new ISOGeometryBuilder(crs2);

		// create a list of connected positions
		List<Position> dps = new ArrayList<Position>();
		dps.add(builder.createDirectPosition(new double[] { 20, 10 }));
		dps.add(builder.createDirectPosition(new double[] { 40, 10 }));
		dps.add(builder.createDirectPosition(new double[] { 50, 40 }));
		dps.add(builder.createDirectPosition(new double[] { 30, 50 }));
		dps.add(builder.createDirectPosition(new double[] { 10, 30 }));
		dps.add(builder.createDirectPosition(new double[] { 20, 10 }));

		// create linestring from directpositions
		LineString line = builder.createLineString(dps);

		// create curvesegments from line
		ArrayList<CurveSegment> segs = new ArrayList<CurveSegment>();
		segs.add(line);

		// Create list of OrientableCurves that make up the surface
		OrientableCurve curve = builder.createCurve(segs);
		List<OrientableCurve> orientableCurves = new ArrayList<OrientableCurve>();
		orientableCurves.add(curve);

		// create the interior ring and a list of empty interior rings (holes)
		Ring extRing = builder2.createRing(orientableCurves);
		List<Ring> intRings = new ArrayList<Ring>();

		// create the surfaceboundary from the rings
		SurfaceBoundary sb = builder2.createSurfaceBoundary(extRing, intRings);

		// create the surface
		Surface surface = builder2.createSurface(sb);

	}
}
