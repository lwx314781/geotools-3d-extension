/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *    
 * 	  (c) 2014 - 2015 Open Source Geospatial Foundation - all rights reserved
 * 	  (c) 2012 - 2014 OpenPlans
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
package org.geotools.data.csv.iso.parse;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.csv.iso.CSVFileState;
import org.geotools.feature.ISOAttributeTypeBuilder;
import org.geotools.feature.simple.ISOSimpleFeatureBuilder;
import org.geotools.feature.simple.ISOSimpleFeatureTypeBuilder;
import org.geotools.geometry.iso.io.wkt.GeometryToWKTString;
import org.geotools.geometry.iso.io.wkt.ParseException;
import org.geotools.geometry.iso.io.wkt.WKTReader;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.util.Converters;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.geometry.Geometry;

import com.csvreader.CsvWriter;

public class CSVSpecifiedWKTStrategy extends CSVStrategy {

    private final String wktField;

    public CSVSpecifiedWKTStrategy(CSVFileState csvFileState, String wktField) {
        super(csvFileState);
        this.wktField = wktField;
    }

    @Override
    protected SimpleFeatureType buildFeatureType() {
    	ISOSimpleFeatureTypeBuilder featureBuilder = createBuilder(csvFileState);
        // For WKT strategy, we need to make sure the wktField is recognized as a Geometry
        AttributeDescriptor descriptor = featureBuilder.get(wktField);
        if( descriptor != null ){
            ISOAttributeTypeBuilder attributeBuilder = new ISOAttributeTypeBuilder();
            attributeBuilder.init(descriptor);
            
            //TODO HACK!! HACK!! we should specify CRS from file
            attributeBuilder.setCRS(csvFileState.getCrs());
            attributeBuilder.binding(Geometry.class);
            
            AttributeDescriptor modified = attributeBuilder.buildDescriptor(wktField);
            featureBuilder.set(modified);
        }
        return featureBuilder.buildFeatureType();
    }
    
    @Override
    public void createSchema(SimpleFeatureType featureType) throws IOException {
        List<String> header = new ArrayList<String>();
      
        for (AttributeDescriptor descriptor : featureType.getAttributeDescriptors()) {
            if (descriptor instanceof GeometryDescriptor) {
                header.add(wktField);
            } else {
                header.add(descriptor.getLocalName());
            }
        }
        // Write out header, producing an empty file of the correct type
        CsvWriter writer = new CsvWriter(new FileWriter(this.csvFileState.getFile()),',');
        try {
            writer.writeRecord( header.toArray(new String[header.size()]));
        }
        finally {
            writer.close();
        }
    }
    
    @Override
    public String[] encode(SimpleFeature feature) {
        List<String> csvRecord = new ArrayList<String>();
        for (Property property : feature.getProperties()) {
            String name = property.getName().getLocalPart();
            Object value = property.getValue();
            if (value == null) {
                csvRecord.add("");
            } else if (name.compareTo(wktField) == 0) {
                GeometryToWKTString wkt = new GeometryToWKTString(false);
                String txt = wkt.getString((Geometry)value);
                csvRecord.add(txt);
            } else {
                String txt = Converters.convert(value, String.class);
                csvRecord.add(txt);
            }
        }
        return csvRecord.toArray(new String[csvRecord.size()-1]);
    }

    @Override
    public SimpleFeature decode(String recordId, String[] csvRecord) {
        SimpleFeatureType featureType = getFeatureType();
        ISOSimpleFeatureBuilder builder = new ISOSimpleFeatureBuilder(featureType);
        GeometryDescriptor geometryDescriptor = featureType.getGeometryDescriptor();
        String[] headers = csvFileState.getCSVHeaders();
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            if (i < csvRecord.length) {
                String value = csvRecord[i].trim();
                if (geometryDescriptor != null && header.equals(wktField)) {
                	//TODO WKTReader wktReader = new WKTReader(GeoTools.getDefaultHints());
                    WKTReader wktReader = new WKTReader(csvFileState.getCrs());
                    Geometry geometry;
                    try {
                        geometry = wktReader.read(value);
                    } catch (ParseException e) {
                        // policy decision here that just nulls out unparseable geometry
                        geometry = null;
                    }
                    builder.set(wktField, geometry);
                } else {
                    builder.set(header, value);
                }
            } else {
                builder.set(header, null);
            }
        }
        return builder.buildFeature(csvFileState.getTypeName() + "-" + recordId);
    }

}