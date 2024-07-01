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

import java.util.ArrayList;

/*
	<result>
		......
		<LimitMaxError>80</LimitMaxError>
		<LimitMaxWarning>37</LimitMaxWarning>
		<LimitWarningMsg>My custom note for warnings</LimitWarningMsg>
		<LimitErrorMsg>My custom note for errors</LimitErrorMsg>
		<LimitMode>1</LimitMode>
	</result>
 */

public class Limit extends ArrayList<iTAG> {

    private final TAG LimitMaxError = new TAG("LimitMaxError", "");
    private final TAG LimitMaxWarning = new TAG("LimitMaxWarning", "");
    private final TAG LimitWarningMsg = new TAG("LimitWarningMsg", "");
    private final TAG LimitErrorMsg = new TAG("LimitErrorMsg", "");

    public Limit() {
        this.add(new TAG("LimitMode", "1"));
        this.add(LimitMaxError);
        this.add(LimitMaxWarning);
        this.add(LimitWarningMsg);
        this.add(LimitErrorMsg);
    }

    public void setMaxError(String value) {
        LimitMaxError.setValue(value);
    }

    public void setMaxWarning(String value) {
        LimitMaxWarning.setValue(value);
    }

    public void setWarningMsg(String value) {
        LimitWarningMsg.setValue(value);
    }

    public void setErrorMsg(String value) {
        LimitErrorMsg.setValue(value);
    }
}
