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

import de.longri.prtg.xml.tags.*;
import de.longri.prtg.xml.tags.Channel;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;

import static de.longri.prtg.xml.XML.*;

public class SensorChannel {

    public static SensorChannel getIntChannel(String name) {
        return new SensorChannel(name, ValueType.INT);
    }

    public static SensorChannel getFloatChannel(String name) {
        return new SensorChannel(name, ValueType.FLOAT);
    }

    private final String NAME;
    private final ValueType TYPE;
    private final ArrayList<iTAG> TAG_LIST = new ArrayList<>();
    private final Value VALUE;

    protected SensorChannel(String name, ValueType type) {
        NAME = name;
        TYPE = type;
        if (TYPE == ValueType.FLOAT) {
            VALUE = new FloatValue();
        } else {
            VALUE = new Value();
        }
        TAG_LIST.add(new Channel(NAME));
        TAG_LIST.add(VALUE);
    }

    private int INT_VAL;

    public void setValue(int value) {
        VALUE.setValue(value);
    }

    private float FLOAT_VAL;

    public void setValue(double value) {
        if (TYPE == ValueType.FLOAT) {
            ((FloatValue) VALUE).setValue(value);
        } else {
            VALUE.setValue((int) value);
        }
    }

    public void write(int tabCount, XMLEventWriter eventWriter) throws XMLStreamException {

        //write <result>
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", "result"));
        eventWriter.add(end);

        tabCount = tabCount + 1;
        for (iTAG tag : TAG_LIST) {
            tag.writeTag(tabCount, eventWriter);
        }

        //write <result>
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "result"));
        eventWriter.add(end);
    }

    protected void addTag(iTAG tag) {
        TAG_LIST.add(tag);
    }

    protected void addTag(String tagName, String tagValue) {
        TAG_LIST.add(new TAG(tagName, tagValue));
    }

    protected void addTag(Limit limit) {
        TAG_LIST.addAll(limit);
    }
}
