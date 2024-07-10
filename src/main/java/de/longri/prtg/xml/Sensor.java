/*
 * Copyright (C) 2024 Longri
 *
 * This file is part of prtg-xml.
 *
 * prtg-xml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * prtg-xml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with prtg-xml. If not, see <https://www.gnu.org/licenses/>.
 */
package de.longri.prtg.xml;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartDocument;
import java.io.StringWriter;
import java.util.ArrayList;

import static de.longri.prtg.xml.Util.createNode;
import static de.longri.prtg.xml.XML.*;

public class Sensor {

    private String message;
    private String errorMsg;

    private final ArrayList<SensorChannel> channelList = new ArrayList<>();

    public void addChannel(SensorChannel channel) {
        channelList.add(channel);
    }

    public String getXML() {

        if (errorMsg != null && !errorMsg.isEmpty()) return ERROR_XML_MSG.replace("##REPLACE##", errorMsg);


        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        StringWriter stringOut = new StringWriter();


        try {
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(stringOut);
            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            // create prtg open tag
            eventWriter.add(eventFactory.createStartElement("", "", "prtg"));
            eventWriter.add(end);

            for (SensorChannel channel : channelList) {
                channel.write(1, eventWriter);
            }

            if (this.message != null)
                createNode(1, eventWriter, "text", this.message);

            eventWriter.add(eventFactory.createEndElement("", "", "prtg"));
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
            eventWriter.close();
        } catch (XMLStreamException e) {
            return ERROR_XML.replace("##REPLACE##", e.getMessage());
        }


        String output = stringOut.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>","<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        return output;

    }

    public void setMessage(String messageForSensor) {
        this.message = messageForSensor;
    }

    public void setError(String errorMessage) {
        this.errorMsg = errorMessage;
    }
}
