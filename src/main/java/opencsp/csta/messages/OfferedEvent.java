package opencsp.csta.messages;

import opencsp.csta.types.*;
import opencsp.csta.xml.CSTAXmlSerializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OfferedEvent extends CSTAEvent implements CSTAXmlSerializable {
    private CrossReferenceId monitorCrossRefID;
    private Connection offeredConnection;
    private DeviceId offeredDevice;
    private DeviceId callingDevice;
    private DeviceId calledDevice;
    private DeviceId lastRedirectionDevice;
    private EventCause cause;

    public Element toXmlElement(Document doc, String tagName) {
        Element e = doc.createElement(tagName);
        e.appendChild(monitorCrossRefID.toXmlElement(doc));
        e.appendChild(offeredConnection.toXmlElement(doc, "offeredConnection"));
        e.appendChild(offeredDevice.toXmlElement(doc, "offeredDevice"));
        e.appendChild(callingDevice.toXmlElement(doc, "callingDevice"));
        e.appendChild(calledDevice.toXmlElement(doc, "calledDevice"));
        if(lastRedirectionDevice != null) {
            e.appendChild(lastRedirectionDevice.toXmlElement(doc, "lastRedirectionDevice"));
        } else {
            Element lrd = doc.createElement("lastRedirectionDevice");
            lrd.appendChild(doc.createElement("notRequired"));
            e.appendChild(lrd);
        }
        e.appendChild(cause.toXmlElement(doc));
        return e;
    }
}

