/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2015, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.gml3.iso;

import javax.xml.namespace.QName;

import org.geotools.gml2.SrsSyntax;
import org.geotools.gml2.iso.FeatureTypeCache;
import org.geotools.gml2.iso.bindings.GMLCoordTypeBinding;
import org.geotools.gml2.iso.bindings.GMLCoordinatesTypeBinding;
import org.geotools.gml3.iso.bindings.AbstractFeatureCollectionTypeBinding;
import org.geotools.gml3.iso.bindings.AbstractFeatureTypeBinding;
import org.geotools.gml3.iso.bindings.AbstractGeometryTypeBinding;
import org.geotools.gml3.iso.bindings.AbstractRingPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.BoundingShapeTypeBinding;
import org.geotools.gml3.iso.bindings.ComplexSupportXSAnyTypeBinding;
import org.geotools.gml3.iso.bindings.CompositeSurfaceTypeBinding;
import org.geotools.gml3.iso.bindings.DirectPositionListTypeBinding;
import org.geotools.gml3.iso.bindings.DirectPositionTypeBinding;
import org.geotools.gml3.iso.bindings.DoubleListBinding;
import org.geotools.gml3.iso.bindings.EnvelopeTypeBinding;
import org.geotools.gml3.iso.bindings.FeatureArrayPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.FeaturePropertyTypeBinding;
import org.geotools.gml3.iso.bindings.GML3EncodingUtils;
import org.geotools.gml3.iso.bindings.GeometryPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.IntegerListBinding;
import org.geotools.gml3.iso.bindings.LineStringPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.LineStringSegmentTypeBinding;
import org.geotools.gml3.iso.bindings.LineStringTypeBinding;
import org.geotools.gml3.iso.bindings.LinearRingPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.LinearRingTypeBinding;
import org.geotools.gml3.iso.bindings.LocationPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.MeasureTypeBinding;
import org.geotools.gml3.iso.bindings.MultiCurvePropertyTypeBinding;
import org.geotools.gml3.iso.bindings.MultiCurveTypeBinding;
import org.geotools.gml3.iso.bindings.MultiGeometryPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.MultiGeometryTypeBinding;
import org.geotools.gml3.iso.bindings.MultiLineStringPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.MultiLineStringTypeBinding;
import org.geotools.gml3.iso.bindings.MultiPointPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.MultiPointTypeBinding;
import org.geotools.gml3.iso.bindings.MultiPolygonPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.MultiPolygonTypeBinding;
import org.geotools.gml3.iso.bindings.MultiSurfacePropertyTypeBinding;
import org.geotools.gml3.iso.bindings.MultiSurfaceTypeBinding;
import org.geotools.gml3.iso.bindings.NullTypeBinding;
import org.geotools.gml3.iso.bindings.PointArrayPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.PointPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.PointTypeBinding;
import org.geotools.gml3.iso.bindings.PolygonPatchTypeBinding;
import org.geotools.gml3.iso.bindings.PolygonPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.PolygonTypeBinding;
import org.geotools.gml3.iso.bindings.ReferenceTypeBinding;
import org.geotools.gml3.iso.bindings.SolidPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.SolidTypeBinding;
import org.geotools.gml3.iso.bindings.SurfaceArrayPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.SurfacePatchArrayPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.SurfacePropertyTypeBinding;
import org.geotools.gml3.iso.bindings.SurfaceTypeBinding;
import org.geotools.gml3.iso.bindings.TimeInstantPropertyTypeBinding;
import org.geotools.gml3.iso.bindings.TimeInstantTypeBinding;
import org.geotools.gml3.iso.bindings.TimePeriodTypeBinding;
import org.geotools.gml3.iso.bindings.TimePositionTypeBinding;
import org.geotools.gml3.iso.bindings.TimePositionUnionBinding;
import org.geotools.gml3.iso.bindings.ext.CompositeCurveTypeBinding;
import org.geotools.gml3.iso.smil.SMIL20Configuration;
import org.geotools.gml3.iso.smil.SMIL20LANGConfiguration;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.xlink.XLINKConfiguration;
import org.geotools.xml.Configuration;
import org.geotools.xml.Parser;
import org.geotools.xs.XS;
import org.opengis.geometry.ISOGeometryBuilder;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.picocontainer.MutablePicoContainer;


/**
 * Parser configuration for the gml3 schema.
 *
 * @author Justin Deoliveira, The Open Planning Project
 *
 *
 *
 *
 * @source $URL$
 */
public class GMLConfiguration_ISO extends Configuration {
    
    /**
     * Boolean property which controls whether encoded features should include bounds.
     */
    public static final QName NO_FEATURE_BOUNDS = org.geotools.gml2.GMLConfiguration.NO_FEATURE_BOUNDS;
    
    /**
     * Boolean property which controls whether the FeatureCollection should be encoded with multiple featureMember
     * as opposed to a single featureMembers 
     */
    public static final QName ENCODE_FEATURE_MEMBER = org.geotools.gml2.GMLConfiguration.ENCODE_FEATURE_MEMBER;

    /**
     * Boolean property which controls whether geometry and envelope objects are encoded with an 
     * srs dimension attribute.
     */
    public static final QName NO_SRS_DIMENSION = new QName( "org.geotools.gml", "noSrsDimension" );

    /**
     * Property which engages "fast" gml encoding.
     */
    public static final QName OPTIMIZED_ENCODING = org.geotools.gml2.GMLConfiguration.OPTIMIZED_ENCODING;

    /**
     * extended support for arcs and surface flag
     */
    boolean extArcSurfaceSupport = false;

    /**
     * Srs name style to encode srsName URI's with
     */
    protected SrsSyntax srsSyntax = SrsSyntax.OGC_URN_EXPERIMENTAL;

    /**
     * Number of decimals that should be used for formatting numbers
     */
    private int numDecimals = 6;

    /**
     * The factory used to create geometries
     */
    private ISOGeometryBuilder geometryBuilder;

    private CoordinateReferenceSystem crs;
    
    
    public GMLConfiguration_ISO() {
        this(false);
    }
    
    public GMLConfiguration_ISO(boolean extArcSurfaceSupport) {
        super(GML.getInstance());

        this.extArcSurfaceSupport = extArcSurfaceSupport;
        
        try {
			geometryBuilder = new ISOGeometryBuilder(CRS.decode("EPSG:4329"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //add xlink dependency
        addDependency(new XLINKConfiguration());

        //add smil dependency
        addDependency(new SMIL20Configuration());
        addDependency(new SMIL20LANGConfiguration());

        //add parser properties
        getProperties().add(Parser.Properties.PARSE_UNKNOWN_ELEMENTS);
        getProperties().add(Parser.Properties.PARSE_UNKNOWN_ATTRIBUTES);
    }

    public void setCRS(CoordinateReferenceSystem crs) {
    	this.crs = crs;
    	geometryBuilder.setCoordinateReferenceSystem(crs);
    }
    
    /**
     * Sets the syntax to use for encoding srs uris.
     * <p>
     * If this method is not explicitly called {@link SrsSyntax#URN} is used as the default.
     * </p>
     */
    public void setSrsSyntax(SrsSyntax srsSyntax) {
        this.srsSyntax = srsSyntax;
    }

    /**
     * Returns the syntax to use for encoding srs uris.
     */
    public SrsSyntax getSrsSyntax() {
        return srsSyntax;
    }

    /**
     * Flag that when set triggers extended support for arcs and surfaces.
     */
    public void setExtendedArcSurfaceSupport(boolean arcSurfaceSupport) {
        this.extArcSurfaceSupport = arcSurfaceSupport;
    }
    
    public boolean isExtendedArcSurfaceSupport() {
        return extArcSurfaceSupport;
    }
    
    protected void registerBindings(MutablePicoContainer container) {
        //Types
        container.registerComponentImplementation(GML.AbstractFeatureType,
            AbstractFeatureTypeBinding.class);
        container.registerComponentImplementation(GML.AbstractFeatureCollectionType,
            AbstractFeatureCollectionTypeBinding.class);
        container.registerComponentImplementation(GML.AbstractGeometryType,
            AbstractGeometryTypeBinding.class);
        container.registerComponentImplementation(GML.AbstractRingPropertyType,
            AbstractRingPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.BoundingShapeType,
            BoundingShapeTypeBinding.class);
        //container.registerComponentImplementation(GML.COORDINATESTYPE,CoordinatesTypeBinding.class);
        container.registerComponentImplementation(GML.CoordinatesType,
            GMLCoordinatesTypeBinding.class);
        //container.registerComponentImplementation(GML.COORDTYPE,CoordTypeBinding.class);
        container.registerComponentImplementation(GML.CoordType, GMLCoordTypeBinding.class);
        //container.registerComponentImplementation(GML.CurveArrayPropertyType, CurveArrayPropertyTypeBinding.class);
        //container.registerComponentImplementation(GML.CurveType, CurveTypeBinding.class);
        //container.registerComponentImplementation(GML.CurvePropertyType, CurvePropertyTypeBinding.class);
        //container.registerComponentImplementation(GML.CurveSegmentArrayPropertyType, CurveSegmentArrayPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.DirectPositionListType,
            DirectPositionListTypeBinding.class);
        container.registerComponentImplementation(GML.DirectPositionType,
            DirectPositionTypeBinding.class);
        container.registerComponentImplementation(GML.doubleList, DoubleListBinding.class);
        container.registerComponentImplementation(GML.EnvelopeType, EnvelopeTypeBinding.class);
        container.registerComponentImplementation(GML.FeatureArrayPropertyType,
            FeatureArrayPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.FeaturePropertyType,
            FeaturePropertyTypeBinding.class);
        container.registerComponentImplementation(GML.GeometryPropertyType,
            GeometryPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.integerList, IntegerListBinding.class);
        container.registerComponentImplementation(GML.LinearRingPropertyType,
            LinearRingPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.LinearRingType, LinearRingTypeBinding.class);
        container.registerComponentImplementation(GML.LineStringPropertyType,
            LineStringPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.LineStringSegmentType,
            LineStringSegmentTypeBinding.class);
        container.registerComponentImplementation(GML.LineStringType, LineStringTypeBinding.class);
        container.registerComponentImplementation(GML.LocationPropertyType,
            LocationPropertyTypeBinding.class);

        container.registerComponentImplementation(GML.MeasureType, MeasureTypeBinding.class);
        container.registerComponentImplementation(GML.MultiCurveType, MultiCurveTypeBinding.class);
        container.registerComponentImplementation(GML.MultiCurvePropertyType,
            MultiCurvePropertyTypeBinding.class);
        container.registerComponentImplementation(GML.MultiGeometryType, MultiGeometryTypeBinding.class);
        container.registerComponentImplementation(GML.MultiGeometryPropertyType, MultiGeometryPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.MultiLineStringPropertyType,
            MultiLineStringPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.MultiLineStringType,
            MultiLineStringTypeBinding.class);
        container.registerComponentImplementation(GML.MultiPointPropertyType,
            MultiPointPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.MultiPointType, MultiPointTypeBinding.class);
        container.registerComponentImplementation(GML.MultiPolygonPropertyType,
            MultiPolygonPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.MultiPolygonType,
            MultiPolygonTypeBinding.class);
        container.registerComponentImplementation(GML.MultiSurfaceType,
            MultiSurfaceTypeBinding.class);
        container.registerComponentImplementation(GML.MultiSurfacePropertyType,
            MultiSurfacePropertyTypeBinding.class);
        container.registerComponentImplementation(GML.NullType,
                NullTypeBinding.class);
        container.registerComponentImplementation(GML.PointArrayPropertyType,
            PointArrayPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.PointPropertyType,
            PointPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.PointType, PointTypeBinding.class);
        container.registerComponentImplementation(GML.PolygonPatchType,
                PolygonPatchTypeBinding.class);
        container.registerComponentImplementation(GML.PolygonPropertyType,
            PolygonPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.PolygonType, PolygonTypeBinding.class);
        container.registerComponentImplementation(GML.ReferenceType, ReferenceTypeBinding.class);
        container.registerComponentImplementation(GML.SurfaceArrayPropertyType,
            SurfaceArrayPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.SurfacePropertyType,
            SurfacePropertyTypeBinding.class);
        container.registerComponentImplementation(GML.SurfaceType, SurfaceTypeBinding.class);
        
        container.registerComponentImplementation(GML.CompositeSurfaceType, CompositeSurfaceTypeBinding.class);
        container.registerComponentImplementation(GML.SolidType, SolidTypeBinding.class);
        container.registerComponentImplementation(GML.SolidPropertyType, SolidPropertyTypeBinding.class);
        
        container.registerComponentImplementation(GML.TimeInstantType, TimeInstantTypeBinding.class);
        container.registerComponentImplementation(GML.TimeInstantPropertyType, TimeInstantPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.TimePeriodType, TimePeriodTypeBinding.class);
        container.registerComponentImplementation(GML.TimePositionType, TimePositionTypeBinding.class);
        container.registerComponentImplementation(GML.TimePositionUnion, TimePositionUnionBinding.class);
        
        container.registerComponentImplementation(XS.ANYTYPE, ComplexSupportXSAnyTypeBinding.class);

        //container.registerComponentImplementation(GML.ArcStringType, ArcStringTypeBinding.class);
        //container.registerComponentImplementation(GML.ArcType, ArcTypeBinding.class);
        //container.registerComponentImplementation(GML.RingType, RingTypeBinding.class);
        container.registerComponentImplementation(GML.CompositeCurveType,
                CompositeCurveTypeBinding.class);
        /*container.registerComponentImplementation(GML.CurveArrayPropertyType,
                org.geotools.gml3.iso.bindings.ext.CurveArrayPropertyTypeBinding.class);
        container.registerComponentImplementation(GML.CurvePropertyType,
                org.geotools.gml3.iso.bindings.ext.CurvePropertyTypeBinding.class);
        container.registerComponentImplementation(GML.CurveType,
                org.geotools.gml3.iso.bindings.ext.CurveTypeBinding.class);*/
        //container.registerComponentImplementation(GML.MultiCurveType, org.geotools.gml3.iso.bindings.ext.MultiCurveTypeBinding.class);
        
        
        //extended bindings for arc/surface support
        if (isExtendedArcSurfaceSupport()) {

            //container.registerComponentImplementation(GML.CircleType, CircleTypeBinding.class);
            container.registerComponentImplementation(GML.SurfacePatchArrayPropertyType,
                    SurfacePatchArrayPropertyTypeBinding.class);
            container.registerComponentImplementation(GML.MultiPolygonType, 
                    org.geotools.gml3.iso.bindings.ext.MultiPolygonTypeBinding.class);
            container.registerComponentImplementation(GML.MultiSurfaceType, 
                    org.geotools.gml3.iso.bindings.ext.MultiSurfaceTypeBinding.class);
            container.registerComponentImplementation(GML.PolygonPatchType, 
                    org.geotools.gml3.iso.bindings.ext.PolygonPatchTypeBinding.class);
            container.registerComponentImplementation(GML.SurfaceArrayPropertyType, 
                    org.geotools.gml3.iso.bindings.ext.SurfaceArrayPropertyTypeBinding.class);
            container.registerComponentImplementation(GML.SurfacePatchArrayPropertyType, 
                    org.geotools.gml3.iso.bindings.ext.SurfacePatchArrayPropertyTypeBinding.class);
            container.registerComponentImplementation(GML.SurfacePropertyType, 
                    org.geotools.gml3.iso.bindings.ext.SurfacePropertyTypeBinding.class);
            container.registerComponentImplementation(GML.SurfaceType, 
                    org.geotools.gml3.iso.bindings.ext.SurfaceTypeBinding.class);
        }
    }

    /**
     * Configures the gml3 context.
     * <p>
     * The following factories are registered:
     * <ul>
     * <li>{@link ISOGeometryBuilder}
     * </ul>
     * </p>
     */
    public void configureContext(MutablePicoContainer container) {
        super.configureContext(container);

        container.registerComponentInstance(new FeatureTypeCache());
        container.registerComponentInstance(new XSDIdRegistry());

        //factories
        container.registerComponentInstance(geometryBuilder);
        
        container.registerComponentInstance(new GML3EncodingUtils());
        
        container.registerComponentInstance(new ArcParameters());

        container.registerComponentInstance(srsSyntax);
    }

    /**
     * Returns the number of decimals that should be used for encoding coordinates (defaults to 6)
     * 
     * @return the numDecimals
     */
    public int getNumDecimals() {
        return numDecimals;
    }

    /**
     * Sets the number of decimals that should be used for encoding coordinates
     * 
     * @param numDecimals the numDecimals to set
     */
    public void setNumDecimals(int numDecimals) {
        this.numDecimals = numDecimals;
    }

    /**
     * Retrieves the geometry factory used to build geometries
     * 
     * @return the ISOGeometryBuilder
     */
    public ISOGeometryBuilder getGeometryFactory() {
        return geometryBuilder;
    }

    /**
     * Sets the geometry factory used to build geometry
     * 
     * @param ISOGeometryBuilder the geometryFactory to set
     */
    public void setGeometryFactory(ISOGeometryBuilder geometryBuilder) {
        this.geometryBuilder = geometryBuilder;
    }

}
