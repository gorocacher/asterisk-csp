package opencsp.csta.types;


import opencsp.csta.xml.CSTAXmlSerializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DeviceId implements CSTAXmlSerializable {
    String typeOfNumber = "dialingNumber";

    String deviceIdentifier;

    public DeviceId(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public Element toXmlElement(Document doc, String tagName) {
        Element e = doc.createElement(tagName);
        Element deviceId = doc.createElement("deviceIdentifier");
        deviceId.setAttribute("typeOfNumber", typeOfNumber);
        deviceId.setTextContent(deviceIdentifier);
        e.appendChild(deviceId);
        return e;
    }

    public String toString() {
        return deviceIdentifier;
    }
}
