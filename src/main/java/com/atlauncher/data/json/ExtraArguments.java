/*
 * MCSR Ranked Launcher - https://github.com/RedLime/MCSR-Ranked-Launcher
 * Copyright (C) 2023 ATLauncher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.atlauncher.data.json;

import com.atlauncher.annot.Json;

@Json
public class ExtraArguments {
    public String arguments;
    public String depends;
    public String dependsGroup;

    public String getArguments() {
        return this.arguments;
    }

    public String getDepends() {
        return this.depends;
    }

    public String getDependsGroup() {
        return this.dependsGroup;
    }

    public boolean hasDepends() {
        return this.depends != null;
    }

    public boolean hasDependsGroup() {
        return this.dependsGroup != null;
    }
}
