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
package de.longri.prtg.xml.tags;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import static de.longri.prtg.xml.XML.*;


public class FloatValue extends Value {

    double value = 0;

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void writeTag(int tabCount, XMLEventWriter eventWriter) throws XMLStreamException {
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", getName());

        int floatTabCount = tabCount;

        while (tabCount-- > 0)
            eventWriter.add(tab);

        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(getValue());
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", getName());
        eventWriter.add(eElement);
        eventWriter.add(end);

        //write <float>1</float>
        sElement = eventFactory.createStartElement("", "", "float");

        while (floatTabCount-- > 0)
            eventWriter.add(tab);

        eventWriter.add(sElement);
        // create Content
        characters = eventFactory.createCharacters("1");
        eventWriter.add(characters);
        // create End node
        eElement = eventFactory.createEndElement("", "", "float");
        eventWriter.add(eElement);
        eventWriter.add(end);
    }
}
