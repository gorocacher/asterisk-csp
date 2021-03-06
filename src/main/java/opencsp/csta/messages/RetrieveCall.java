package opencsp.csta.messages;

import opencsp.Log;
import opencsp.csta.Provider;
import opencsp.csta.types.CSTARequest;
import opencsp.csta.types.Call;
import opencsp.csta.types.Connection;
import opencsp.csta.types.DeviceCategory;
import opencsp.csta.xml.CSTAXmlSerializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RetrieveCall extends CSTARequest implements CSTAXmlSerializable {
    private static final String TAG = "RetrieveCall";

    private Connection callToBeRetrieved;

    public Element toXmlElement(Document doc, String tagName) {
        Element e = doc.createElement(tagName);
        e.appendChild(callToBeRetrieved.toXmlElement(doc, "callToBeRetrieved"));
        return e;
    }

    public Connection getCallToBeRetrieved() {
        return callToBeRetrieved;
    }

    public RetrieveCall(String xmlBody) {
        Document xml = documentFromXmlString(xmlBody);
        NodeList callToBeHeldNodes = xml.getElementsByTagName("callToBeRetrieved");
        NodeList callIDNodes = xml.getElementsByTagName("callID");
        NodeList deviceIdNodes = xml.getElementsByTagName("deviceID");

        if(callToBeHeldNodes.getLength() == 1 && callIDNodes.getLength() == 1 && deviceIdNodes.getLength() == 1) {
            Provider p = Provider.getExistingInstance();
            if(p != null) {
                Call call = p.getCallByCallId(Integer.parseInt(callIDNodes.item(0).getTextContent()));
                for(Connection c : call.getConnections()) {
                    //No deviceId Specified
                    if(deviceIdNodes.item(0).getTextContent().length() == 0) {
                        //find the first station involved in this call
                        if (p.findDeviceById(c.getDeviceId()).getCategory().equals(DeviceCategory.Station)) {
                            this.callToBeRetrieved = c;
                            break;
                        }
                    } else {
                        //Find the connection with the device specified by the message
                        if (c.getDeviceId().toString().equals(deviceIdNodes.item(0).getTextContent())) {
                            this.callToBeRetrieved = c;
                            break;
                        }
                    }
                }
            }
        } else {
            Log.e(TAG, "RetrieveCall MUST contain a single callToBeRetrieved with a single callID and a single deviceID.");
        }
    }
}
