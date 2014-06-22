// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation 
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;

public class WSVersioningUniverseVersions_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_tagStructure_QNAME = new QName("", "tagStructure");
    private static final QName ns2_WSVersioningUniverseVersions$2d$tagStructure_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSVersioningUniverseVersions-tagStructure");
    private CombinedSerializer ns2_myWSVersioningUniverseVersionsTagStructure_LiteralSerializer;
    
    public WSVersioningUniverseVersions_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSVersioningUniverseVersions_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myWSVersioningUniverseVersionsTagStructure_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.webapp.util.webservices.WSVersioningUniverseVersionsTagStructure.class, ns2_WSVersioningUniverseVersions$2d$tagStructure_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSVersioningUniverseVersions instance = new com.amalto.webapp.util.webservices.WSVersioningUniverseVersions();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_tagStructure_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_tagStructure_QNAME))) {
                    value = ns2_myWSVersioningUniverseVersionsTagStructure_LiteralSerializer.deserialize(ns1_tagStructure_QNAME, reader, context);
                    if (value == null) {
                        throw new DeserializationException("literal.unexpectedNull");
                    }
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.amalto.webapp.util.webservices.WSVersioningUniverseVersionsTagStructure[values.size()];
            member = values.toArray((Object[]) member);
            instance.setTagStructure((com.amalto.webapp.util.webservices.WSVersioningUniverseVersionsTagStructure[])member);
        }
        else if(!(reader.getState() == XMLReader.END)) {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSVersioningUniverseVersions instance = (com.amalto.webapp.util.webservices.WSVersioningUniverseVersions)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.webapp.util.webservices.WSVersioningUniverseVersions instance = (com.amalto.webapp.util.webservices.WSVersioningUniverseVersions)obj;
        
        if (instance.getTagStructure() != null) {
            for (int i = 0; i < instance.getTagStructure().length; ++i) {
                ns2_myWSVersioningUniverseVersionsTagStructure_LiteralSerializer.serialize(instance.getTagStructure()[i], ns1_tagStructure_QNAME, null, writer, context);
            }
        }
    }
}
