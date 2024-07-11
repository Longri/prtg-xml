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

import de.longri.prtg.xml.SensorChannel;
import de.longri.prtg.xml.Util;
import de.longri.prtg.xml.ValueType;

import java.math.BigDecimal;
import java.math.RoundingMode;


/*
	<result>
		<channel>Demo Disk Free</channel>
		<unit>Percent</unit>
		<float>1</float>
		<mode>Absolute</mode>
		<showChart>1</showChart>
		<showTable>1</showTable>
		<warning>0</warning>
		<value>38.4487</value>
		<LimitMaxError>80</LimitMaxError>
		<LimitMaxWarning>37</LimitMaxWarning>
		<LimitWarningMsg>My custom note for warnings</LimitWarningMsg>
		<LimitErrorMsg>My custom note for errors</LimitErrorMsg>
		<LimitMode>1</LimitMode>
	</result>
 */

public class HDD_Channel extends SensorChannel {

    public static int ROUND_COUNT = 2;

    private double percentValue;
    private final Limit limit;
    private final TAG TEXT = new TAG("Text", "");

    public HDD_Channel(String name) {
        super(name, ValueType.FLOAT);
        this.addTag(Unit.Percent);
        this.addTag(Mode.Absolute);
        this.addTag("showChart", "1");
        this.addTag("showTable", "1");
        this.addTag("warning", "0");
        this.addTag(TEXT);

        limit = new Limit();
        this.addTag(limit);
        limit.setMaxError("90");
        limit.setMaxWarning("75");
        limit.setErrorMsg("Error");
        limit.setWarningMsg("Warning");
    }

    public void setValue(double value, double maxValue) {
        percentValue = value / maxValue * 100;
        setValue(percentValue);
        setTEXT(Util.formatBytes(value), Util.formatBytes(maxValue));
    }

    @Override
    public void setValue(double value) {
        super.setValue(roundValue(value));
    }

    public void setError(String value, String msg) {
        limit.setMaxError(value);
        limit.setErrorMsg(msg);
    }

    public void setWarning(String value, String msg) {
        limit.setWarningMsg(msg);
        limit.setMaxWarning(value);
    }

    protected void setTEXT(String usage, String max) {
        TEXT.setValue(usage + "/" + max);
    }

    protected void setTEXT(String text) {
        TEXT.setValue(text);
    }

    public static double roundValue(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(ROUND_COUNT, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
