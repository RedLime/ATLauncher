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
package com.atlauncher.evnt.manager;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.atlauncher.evnt.listener.ConsoleCloseListener;

public final class ConsoleCloseManager {
    private static final List<ConsoleCloseListener> listeners = new LinkedList<>();

    private ConsoleCloseManager() {
    }

    public static synchronized void addListener(ConsoleCloseListener listener) {
        listeners.add(listener);
    }

    public static synchronized void removeListener(ConsoleCloseListener listener) {
        listeners.remove(listener);
    }

    public static synchronized void post() {
        SwingUtilities.invokeLater(() -> {
            for (ConsoleCloseListener listener : listeners) {
                listener.onConsoleClose();
            }
        });
    }
}
