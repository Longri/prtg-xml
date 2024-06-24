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

public class Channel extends WorkingChannel {

    public Channel(String name, ValueType type, StringBuilder msgBilder) {
        super(name, type, msgBilder);
    }

    public Channel(String channel_name, StringBuilder msgBilder) {
        super(channel_name, ValueType.INT, msgBilder);
    }

    @Override
    protected boolean runWork() {
        return true;
    }
}
