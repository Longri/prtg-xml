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
import javax.xml.stream.events.XMLEvent;

public class XML {
    public static final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    public static boolean IS_WIN = System.getProperty("os.name").toLowerCase().matches(".*win.*");
    public static final XMLEvent end = eventFactory.createDTD(IS_WIN ? "\r\n" : "\n");
    public static final XMLEvent tab = eventFactory.createDTD("\t");


    public static final String ERROR_XML = "             <prtg>\n" +
            "             <error>1</error>\n" +
            "             <text>Can't create XML: ##REPLACE##</text>\n" +
            "             </prtg>";


    public static final String ERROR_XML_MSG = "<prtg>\n" +
            "\t<error>1</error>\n" +
            "\t<text>##REPLACE##</text>\n" +
            "</prtg>";

}
