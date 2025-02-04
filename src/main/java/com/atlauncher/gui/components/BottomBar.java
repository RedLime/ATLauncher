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
package com.atlauncher.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.atlauncher.constants.Constants;
import com.atlauncher.evnt.listener.ThemeListener;
import com.atlauncher.evnt.manager.ThemeManager;
import com.atlauncher.managers.LogManager;
import com.atlauncher.utils.OS;

public abstract class BottomBar extends JPanel implements ThemeListener {
    private static final long serialVersionUID = -7488195680365431776L;

    protected final JButton discordIcon = new SMButton("/assets/image/social/discord.png", "Discord");
    protected final JButton githubIcon = new SMButton("/assets/image/social/github.png", "GitHub");
    protected final JButton twitterIcon = new SMButton("/assets/image/social/twitter.png", "Twitter");
    protected final JButton patreonIcon = new SMButton("/assets/image/social/patreon.png", "Patreon");

    protected final JPanel rightSide = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 8));

    public BottomBar() {
        super(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("BottomBar.dividerColor")));
        this.setPreferredSize(new Dimension(0, 50));

        this.add(this.rightSide, BorderLayout.EAST);
        this.setupSocialButtonListeners();
        this.rightSide.add(this.discordIcon);
        this.rightSide.add(this.githubIcon);
        this.rightSide.add(this.twitterIcon);
        this.rightSide.add(this.patreonIcon);

        ThemeManager.addListener(this);
    }

    private void setupSocialButtonListeners() {
        discordIcon.addActionListener(e -> {
            LogManager.info("Opening Up " + Constants.LAUNCHER_NAME + " Discord");
            OS.openWebBrowser("https://mcsrranked.com/discord");
        });
        githubIcon.addActionListener(e -> {
            LogManager.info("Opening Up " + Constants.LAUNCHER_NAME + " GitHub Page");
            OS.openWebBrowser("https://github.com/RedLime/MCSR-Ranked-Launcher");
        });
        twitterIcon.addActionListener(e -> {
            LogManager.info("Opening Up " + Constants.LAUNCHER_NAME + " Twitter Page");
            OS.openWebBrowser("https://twitter.com/mcsr_ranked/");
        });
        patreonIcon.addActionListener(e -> {
            LogManager.info("Opening Up " + Constants.LAUNCHER_NAME + " Patreon Page");
            OS.openWebBrowser("https://www.patreon.com/mcsrranked");
        });
    }

    public void onThemeChange() {
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("BottomBar.dividerColor")));
    }
}
