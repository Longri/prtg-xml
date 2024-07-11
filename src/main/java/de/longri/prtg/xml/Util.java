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
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import static de.longri.prtg.xml.XML.*;

public class Util {
    public static void createNode(int tabCount, XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {


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

    public static void writeChannel(WorkingChannel channel, XMLEventWriter eventWriter) throws XMLStreamException {
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", "result"));
        eventWriter.add(end);
        createNode(2, eventWriter, "channel", channel.getName());
        createNode(2, eventWriter, "value", channel.getValue());
        if (channel.getType() == ValueType.FLOAT) createNode(2, eventWriter, "float", "1");
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "result"));
        eventWriter.add(end);
    }

    public static String formatBytes(double bytes) {
        if (bytes < 1024) {
            return String.format("%.2f Bytes", bytes);
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024 * 1024));
        } else if (bytes < 1024L * 1024 * 1024 * 1024) {
            return String.format("%.2f GB", bytes / (1024 * 1024 * 1024));
        } else {
            return String.format("%.2f TB", bytes / (1024L * 1024 * 1024 * 1024));
        }
    }

}
