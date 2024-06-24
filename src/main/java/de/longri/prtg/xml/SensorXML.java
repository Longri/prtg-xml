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


import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.StringWriter;
import java.util.ArrayList;

public class SensorXML {

    private static final String ERROR_XML = "             <prtg>\n" +
            "             <error>1</error>\n" +
            "             <text>Can't create XML: ##REPLACE##</text>\n" +
            "             </prtg>";


    private static final String ERROR_XML_MSG = "<prtg>\n" +
            "\t<error>1</error>\n" +
            "\t<text>##REPLACE##</text>\n" +
            "</prtg>";


    private static final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    private static boolean IS_WIN = System.getProperty("os.name").toLowerCase().matches(".*win.*");
    private static final XMLEvent end = eventFactory.createDTD(IS_WIN ? "\r\n" : "\n");
    private static final XMLEvent tab = eventFactory.createDTD("\t");

    public SensorXML(WorkingChannel... channels) {
        this.addAll(channels);
    }

    private static void createNode(int tabCount, XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {


        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);

        while (tabCount-- > 0)
            eventWriter.add(tab);

        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);

    }

    private static void writeChannel(WorkingChannel channel, XMLEventWriter eventWriter) throws XMLStreamException {


        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", "result"));
        eventWriter.add(end);
        createNode(2, eventWriter, "channel", channel.getName());
        createNode(2, eventWriter, "value", channel.getValue());
        if (channel.getType() == WorkingChannel.ValueType.FLOAT) createNode(2, eventWriter, "float", "1");
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "result"));
        eventWriter.add(end);
    }

    private ArrayList<WorkingChannel> channels = new ArrayList<>();
    private String errorMsg;
    private String message = null;

    public void addAll(WorkingChannel... channels) {
        for (WorkingChannel channel : channels)
            this.channels.add(channel);
    }

    public void addChannel(WorkingChannel channel) {
        channels.add(channel);
    }

    public void setError(String msg) {
        this.errorMsg = msg;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public String getXML() {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        StringWriter stringOut = new StringWriter();

        if (errorMsg != null && !errorMsg.isEmpty()) return ERROR_XML_MSG.replace("##REPLACE##", errorMsg);


        try {
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(stringOut);
            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            // create prtg open tag
            eventWriter.add(eventFactory.createStartElement("", "", "prtg"));
            eventWriter.add(end);

            for (WorkingChannel channel : channels) {
                writeChannel(channel, eventWriter);
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


        String output = stringOut.toString();
        return output;
    }

    public void runChannelWorker() {
        for (WorkingChannel channel : channels) {
            channel.run();
        }
    }
}
