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



/*

<Mode>


Select if the value is an absolute value or counter. The default is Absolute.

Absolute

Difference

 */

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

public enum Mode implements iTAG {

    Absolute, Difference;

    @Override
    public String getName() {
        return "mode";
    }

    @Override
    public String getValue() {
        return this.name();
    }

}
