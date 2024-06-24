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

public abstract class WorkingChannel extends Cancel_Task {

    public enum ValueType {
        INT, FLOAT
    }

    protected final String name;
    protected final ValueType type;
    protected final StringBuilder msgBilder;

    private String value;

    public WorkingChannel(String name, ValueType type, StringBuilder msgBilder) {
        this.name = name;
        this.type = type;
        this.msgBilder = msgBilder;
    }

    public String getName() {
        return this.name;
    }

    public ValueType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public WorkingChannel setValue(int value) {
        this.value = Integer.toString(value);
        return this;
    }

    public WorkingChannel setValue(double value) {
        this.value = Double.toString(value);
        return this;
    }

    public WorkingChannel setValue(long value) {
        this.value = Long.toString(value);
        return this;
    }
}
