/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.gml3.iso.bindings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.geotools.gml3.iso.GML;
import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.geometry.ISOGeometryBuilder;
import org.opengis.geometry.aggregate.MultiPoint;
import org.opengis.geometry.aggregate.MultiSurface;
import org.opengis.geometry.primitive.OrientableSurface;
import org.opengis.geometry.primitive.Point;
import org.opengis.geometry.primitive.Surface;


/**
 * Binding object for the type http://www.opengis.net/gml:MultiPolygonType.
 *
 * <p>
 *        <pre>
 *         <code>
 *  &lt;complexType name="MultiPolygonType"&gt;
 *      &lt;annotation&gt;
 *          &lt;documentation&gt;A MultiPolygon is defined by one or more Polygons, referenced through polygonMember elements. Deprecated with GML version 3.0. Use MultiSurfaceType instead.&lt;/documentation&gt;
 *      &lt;/annotation&gt;
 *      &lt;complexContent&gt;
 *          &lt;extension base="gml:AbstractGeometricAggregateType"&gt;
 *              &lt;sequence&gt;
 *                  &lt;element maxOccurs="unbounded" minOccurs="0" ref="gml:polygonMember"/&gt;
 *              &lt;/sequence&gt;
 *          &lt;/extension&gt;
 *      &lt;/complexContent&gt;
 *  &lt;/complexType&gt;
 *
 *          </code>
 *         </pre>
 * </p>
 *
 * @generated
 *
 *
 *
 * @source $URL$
 */
public class MultiPolygonTypeBinding extends AbstractComplexBinding implements Comparable {
    ISOGeometryBuilder gBuilder;

    public MultiPolygonTypeBinding(ISOGeometryBuilder gBuilder) {
        this.gBuilder = gBuilder;
    }

    /**
     * @generated
     */
    public QName getTarget() {
        return GML.MultiPolygonType;
    }

    public int getExecutionMode() {
        return BEFORE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return MultiSurface.class;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value)
        throws Exception {
        List polys = node.getChildValues(Surface.class);
        Set<Surface> set = new HashSet<Surface>(polys);
        return gBuilder.createMultiSurface(set);

        //return gFactory.createMultiPolygon((Polygon[]) polys.toArray(new Polygon[polys.size()]));
    }

    public Object getProperty(Object object, QName name)
        throws Exception {
        if (GML.polygonMember.equals(name)) {
        	MultiSurface multiSurface = (MultiSurface) object;
        	Surface[] members = new Surface[multiSurface.getElements().size()];
        	
        	int i = 0;
        	for (OrientableSurface s : multiSurface.getElements()) {
        		members[i++] = (Surface) s;
        	}
        	GML3EncodingUtils.setChildIDs(multiSurface);

            return members;
        }
        return null;
    }

    /**
     * Implement comparable because both MultiPolygonBinding and MultiSurfaceBinding
     * are bound to the same class, MultiPolygon. Since MultiPolygon is deprecated
     * by gml3 MultiSurface always wins.
     */
    public int compareTo(Object o) {
        if (o instanceof MultiSurfaceTypeBinding) {
            return 1;
        } else {
            return 0;
        }
    }
}
