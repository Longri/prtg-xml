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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SensorXMLTest {

    private static boolean IS_WIN = System.getProperty("os.name").toLowerCase().matches(".*win.*");
    static final String CRLF_LF = IS_WIN ? "\r\n" : "\n";


    @Test
    void getXML() {

        StringBuilder msgBilder = new StringBuilder();

        String expectedXml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + CRLF_LF +
                "<prtg>" + CRLF_LF +
                "\t<result>" + CRLF_LF +
                "\t\t<channel>IntValue</channel>" + CRLF_LF +
                "\t\t<value>27</value>" + CRLF_LF +
                "\t</result>" + CRLF_LF +
                "\t<result>" + CRLF_LF +
                "\t\t<channel>FloatValue</channel>" + CRLF_LF +
                "\t\t<value>123.456789</value>" + CRLF_LF +
                "\t\t<float>1</float>" + CRLF_LF +
                "\t</result>" + CRLF_LF +
                "\t<text>Message for Sensor</text>" + CRLF_LF +
                "</prtg>";


        WorkingChannel channelInt = new WorkingChannel("IntValue", WorkingChannel.ValueType.INT, msgBilder) {
            @Override
            protected boolean runWork() {
                return true;
            }
        };
        WorkingChannel channelFloat = new WorkingChannel("FloatValue", WorkingChannel.ValueType.FLOAT, msgBilder) {
            @Override
            protected boolean runWork() {
                return true;
            }
        };

        SensorXML sensorXML = new SensorXML();
        sensorXML.addChannel(channelInt);
        sensorXML.addChannel(channelFloat);

        channelInt.setValue(27);
        channelFloat.setValue(123.456789);
        sensorXML.setMessage("Message for Sensor");
        String xml = sensorXML.getXML();

        assertEquals(expectedXml, xml);


        // check Error msg
        expectedXml = "<prtg>" + CRLF_LF +
                "\t<error>1</error>" + CRLF_LF +
                "\t<text>ERROR MASSAGE</text>" + CRLF_LF +
                "</prtg>";

        sensorXML.setError("ERROR MASSAGE");
        xml = sensorXML.getXML();
        assertEquals(expectedXml, xml);


    }
}