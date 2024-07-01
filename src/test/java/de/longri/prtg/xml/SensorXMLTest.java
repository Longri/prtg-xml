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

import de.longri.prtg.xml.tags.HDD_Channel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorXMLTest {

    private static final boolean IS_WIN = System.getProperty("os.name").toLowerCase().matches(".*win.*");
    private static final String CRLF_LF = IS_WIN ? "\r\n" : "\n";
    private static final String expectedXml = "" +
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
    private static final String expectedErrorXml = "<prtg>" + CRLF_LF +
            "\t<error>1</error>" + CRLF_LF +
            "\t<text>ERROR MASSAGE</text>" + CRLF_LF +
            "</prtg>";

    @Test
    void getXML() {
        StringBuilder msgBilder = new StringBuilder();
        WorkingChannel channelInt = new WorkingChannel("IntValue", ValueType.INT, msgBilder) {
            @Override
            protected boolean runWork() {
                return true;
            }
        };
        WorkingChannel channelFloat = new WorkingChannel("FloatValue", ValueType.FLOAT, msgBilder) {
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
        sensorXML.setError("ERROR MASSAGE");
        xml = sensorXML.getXML();
        assertEquals(expectedErrorXml, xml);
    }

    @Test
    void getSensorXml() {
        Sensor SENSOR = new Sensor();

        SensorChannel intChanel = SensorChannel.getIntChannel("IntValue");
        SensorChannel floatChanel = SensorChannel.getFloatChannel("FloatValue");

        SENSOR.addChannel(intChanel);
        SENSOR.addChannel(floatChanel);

        intChanel.setValue(27);
        floatChanel.setValue(123.456789);


        SENSOR.setMessage("Message for Sensor");

        String xml = SENSOR.getXML();
        assertEquals(expectedXml, xml);
    }

    @Test
    void getHddSensorTest() {

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<prtg>\n" +
                "\t<result>\n" +
                "\t\t<channel>Demo Disk Free</channel>\n" +
                "\t\t<value>38.4487</value>\n" +
                "\t\t<float>1</float>\n" +
                "\t\t<unit>Percent</unit>\n" +
                "\t\t<mode>Absolute</mode>\n" +
                "\t\t<showChart>1</showChart>\n" +
                "\t\t<showTable>1</showTable>\n" +
                "\t\t<warning>0</warning>\n" +
                "\t\t<LimitMode>1</LimitMode>\n" +
                "\t\t<LimitMaxError>80</LimitMaxError>\n" +
                "\t\t<LimitMaxWarning>37</LimitMaxWarning>\n" +
                "\t\t<LimitWarningMsg>My custom note for warnings</LimitWarningMsg>\n" +
                "\t\t<LimitErrorMsg>My custom note for errors</LimitErrorMsg>\n" +
                "\t</result>\n" +
                "\t<text>Message for Sensor</text>\n" +
                "</prtg>";

        Sensor SENSOR = new Sensor();
        HDD_Channel hddChanel = new HDD_Channel("Demo Disk Free");

        hddChanel.setValue(38.4487, 100);
        hddChanel.setError("80", "My custom note for errors");
        hddChanel.setWarning("37", "My custom note for warnings");

        SENSOR.addChannel(hddChanel);
        SENSOR.setMessage("Message for Sensor");

        String xml = SENSOR.getXML();
        assertEquals(expected, xml);

    }

    @Test
    void getHddSensorErrorTest() {

        String expected = "<prtg>\n" +
                "\t<error>1</error>\n" +
                "\t<text>Error message</text>\n" +
                "</prtg>";

        Sensor SENSOR = new Sensor();
        HDD_Channel hddChanel = new HDD_Channel("Demo Disk Free");

        hddChanel.setValue(38.4487, 100);
        hddChanel.setError("80", "My custom note for errors");
        hddChanel.setWarning("37", "My custom note for warnings");

        SENSOR.addChannel(hddChanel);
        SENSOR.setMessage("Message for Sensor");

        SENSOR.setError("Error message");

        String xml = SENSOR.getXML();
        assertEquals(expected, xml);
    }
}