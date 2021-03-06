/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2015 - 2016, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.gml3.iso.simple;

import java.util.List;
import java.util.Map;

import org.eclipse.xsd.XSDElementDeclaration;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.gml2.SrsSyntax;
import org.geotools.gml2.iso.simple.GMLWriter;
import org.geotools.gml2.iso.simple.GeometryEncoder;
import org.geotools.gml2.iso.simple.QualifiedName;
import org.geotools.gml3.iso.bindings.GML3EncodingUtils;
import org.geotools.gml3.iso.simple.CurveEncoder;
import org.geotools.gml3.iso.simple.EnvelopeEncoder;
import org.geotools.gml3.iso.simple.GeometryCollectionEncoder;
import org.geotools.gml3.iso.simple.LineStringEncoder;
import org.geotools.gml3.iso.simple.LinearRingEncoder;
import org.geotools.gml3.iso.simple.MultiLineStringEncoder;
import org.geotools.gml3.iso.simple.MultiPointEncoder;
import org.geotools.gml3.iso.simple.MultiPolygonEncoder;
import org.geotools.gml3.iso.simple.PointEncoder;
import org.geotools.gml3.iso.simple.PolygonEncoder;
import org.geotools.gml3.iso.v3_2.GML;
import org.geotools.gml3.iso.v3_2.GMLConfiguration_ISO;
import org.geotools.gml3.iso.v3_2.bindings.GML32EncodingUtils;
import org.geotools.xml.Configuration;
import org.geotools.xml.Encoder;
import org.geotools.xml.XSD;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
/**
 * SimpleFeatureCollection encoder delegate for fast GML3 encoding
 * 
 * @author Andrea Aime - GeoSolutions
 */
public class GML32FeatureCollectionEncoderDelegate extends
        org.geotools.gml2.iso.simple.FeatureCollectionEncoderDelegate {

    public GML32FeatureCollectionEncoderDelegate(SimpleFeatureCollection features, Encoder encoder) {
        super(features, encoder, new GML32Delegate(encoder));
    }

    public static class GML32Delegate implements org.geotools.gml2.iso.simple.GMLDelegate {

        SrsSyntax srsSyntax;

        static final QualifiedName MEMBER = new QualifiedName(GML.NAMESPACE,
                GML.member.getLocalPart(), "gml");

        protected QualifiedName member;

        String gmlPrefix;
        String gmlUri;
        GML32EncodingUtils encodingUtils;

        int numDecimals;

        public GML32Delegate(Encoder encoder) {
            this.gmlUri =org.geotools.gml3.iso.v3_2.GML.NAMESPACE;
            this.gmlPrefix = encoder.getNamespaces().getPrefix(gmlUri);
            
            this.member = MEMBER.derive(gmlPrefix, gmlUri);
            this.srsSyntax = (SrsSyntax) encoder.getContext().getComponentInstanceOfType(
                    SrsSyntax.class);
            this.encodingUtils = new GML32EncodingUtils();
            this.numDecimals = getNumDecimals(encoder.getConfiguration());
        }

        private int getNumDecimals(Configuration configuration) {
            GMLConfiguration_ISO config;
            if (configuration instanceof org.geotools.gml3.iso.v3_2.GMLConfiguration_ISO) {
                config = (GMLConfiguration_ISO) configuration;
            } else {
                config = configuration.getDependency(GMLConfiguration_ISO.class);
            }

            if (config == null) {
                return 6;
            } else {
                return config.getNumDecimals();
            }
        }

        public List getFeatureProperties(SimpleFeature f, XSDElementDeclaration element, Encoder e) {
            return encodingUtils.AbstractFeatureTypeGetProperties(f, element,
                    e.getSchemaIndex(), e.getConfiguration());
        }

        public EnvelopeEncoder createEnvelopeEncoder(Encoder e) {
            return new EnvelopeEncoder(e, gmlPrefix, gmlUri);
        }

        public void setSrsNameAttribute(AttributesImpl atts, CoordinateReferenceSystem crs) {

            atts.addAttribute(null, "srsName", "srsName", null,
                    GML3EncodingUtils.toURI(crs, srsSyntax).toString());
        }

        @Override
        public void setGeometryDimensionAttribute(AttributesImpl atts, int dimension) {
            atts.addAttribute(null, "srsDimension", "srsDimension", null, String.valueOf(dimension));

        }

        public void initFidAttribute(AttributesImpl atts) {
            atts.addAttribute(GML.NAMESPACE, "id", "gml:id", null, "");
        }

        public void startFeatures(GMLWriter handler) throws Exception {

        }

        public void startFeature(GMLWriter handler) throws Exception {
            handler.startElement(member, null);
        }

        public void endFeature(GMLWriter handler) throws Exception {
            handler.endElement(member);
        }

        public void endFeatures(GMLWriter handler) throws Exception {

        }

        @Override
        public void registerGeometryEncoders(Map<Class, GeometryEncoder> encoders, Encoder encoder) {
            /*encoders.put(Point.class, new PointEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(MultiPoint.class, new MultiPointEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(LineString.class, new LineStringEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(LinearRing.class, new LinearRingEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(MultiLineString.class, new MultiLineStringEncoder(encoder, gmlPrefix, gmlUri, true));
            encoders.put(Polygon.class, new PolygonEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(MultiPolygon.class, new MultiPolygonEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(CircularString.class, new CurveEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(CompoundCurve.class, new CurveEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(CircularRing.class, new CurveEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(CompoundRing.class, new CurveEncoder(encoder, gmlPrefix, gmlUri));
            encoders.put(GeometryCollection.class, new GeometryCollectionEncoder(encoder, gmlPrefix, gmlUri));*/
        }

        @Override
        public String getGmlPrefix() throws Exception {
            return gmlPrefix;
        }

        @Override
        public boolean supportsTuples() {
            return false;
        }

        @Override
        public void startTuple(GMLWriter handler) throws SAXException {
        }

        @Override
        public void endTuple(GMLWriter handler) throws SAXException {
        }

        @Override
        public XSD getSchema() {
            return GML.getInstance();
        }

        @Override
        public int getNumDecimals() {
            return numDecimals;
        }

        @Override
        public boolean forceDecimalEncoding() {
            return false;
        }

    }
}
