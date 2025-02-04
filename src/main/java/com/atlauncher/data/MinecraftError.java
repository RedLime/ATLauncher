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
package com.atlauncher.data;

import org.mini2Dx.gettext.GetText;

import com.atlauncher.builders.HTMLBuilder;
import com.atlauncher.managers.DialogManager;
import com.atlauncher.utils.OS;

public class MinecraftError {
    static final int OUT_OF_MEMORY = 1;
    static final int CONCURRENT_MODIFICATION_ERROR_1_6 = 2;
    static final int USING_NEWER_JAVA_THAN_8 = 3;
    static final int NEED_TO_USE_JAVA_16_OR_NEWER = 4;
    static final int NEED_TO_USE_JAVA_17_OR_NEWER = 5;

    static void showInformationPopup(int error) {
        switch (error) {
            case MinecraftError.OUT_OF_MEMORY:
                MinecraftError.showOutOfMemoryPopup();
                return;
            case MinecraftError.CONCURRENT_MODIFICATION_ERROR_1_6:
                MinecraftError.showConcurrentModificationError16();
                return;
            case MinecraftError.USING_NEWER_JAVA_THAN_8:
                MinecraftError.showUsingNewerJavaThan8Popup();
                return;
            case MinecraftError.NEED_TO_USE_JAVA_16_OR_NEWER:
                MinecraftError.showNeedToUseJava16OrNewerPopup();
                return;
            case MinecraftError.NEED_TO_USE_JAVA_17_OR_NEWER:
                MinecraftError.showNeedToUseJava17OrNewerPopup();
                return;
        }
    }

    static void showOutOfMemoryPopup() {
        DialogManager.okDialog().setTitle(GetText.tr("About Your Crash"))
                .setContent(new HTMLBuilder().center().text(GetText.tr(
                        "Minecraft has crashed due to insufficent memory being allocated.<br/><br/>Please go to the settings tab and increase the maximum memory option and then try launching the instance again."))
                        .build())
                .setType(DialogManager.INFO).show();
    }

    static void showConcurrentModificationError16() {
        DialogManager.okDialog().setTitle(GetText.tr("About Your Crash"))
                .setContent(new HTMLBuilder().center().text(GetText.tr(
                        "Minecraft has crashed due to an incompatability with Forge and your version of Java.<br/><br/>Please reinstall the instance to automatically fix the problem, and then try launching the instance again."))
                        .build())
                .setType(DialogManager.INFO).show();
    }

    static void showUsingNewerJavaThan8Popup() {
        int ret = DialogManager.okDialog().setTitle(GetText.tr("About Your Crash"))
                .setContent(new HTMLBuilder().center().text(GetText.tr(
                        "Minecraft has crashed due to not being compatible with your Java version.<br/><br/>Most modded Minecraft is only compatible with Java 8, so you must install Java 8 on your computer."))
                        .build())
                .addOption(GetText.tr("Download Java 8"), true).setType(DialogManager.INFO).show();

        if (ret == 1) {
            OS.openWebBrowser("https://atl.pw/java8download");
        }
    }

    static void showNeedToUseJava16OrNewerPopup() {
        DialogManager.okDialog().setTitle(GetText.tr("About Your Crash"))
                .setContent(new HTMLBuilder().center().text(GetText.tr(
                        "Minecraft has crashed due to not being compatible with your Java version.<br/><br/>This version of Minecraft requires Java {0} or newer.<br/><br/>Make sure you've selected the correct Java version in this instances<br/>settings or not disabled the \"Use Java Provided By Minecraft\" setting.<br/><br/>If it's still not working, you may be using a mod that requires Java {0},<br/>but Minecraft only requires Java 8, so you can go into the instances<br/>settings and in the \"Java\" tab, change the \"Runtime Overide\"<br/>option to one that is Java {0} or newer.",
                        "16"))
                        .build())
                .setType(DialogManager.INFO).show();
    }

    static void showNeedToUseJava17OrNewerPopup() {
        DialogManager.okDialog().setTitle(GetText.tr("About Your Crash"))
                .setContent(new HTMLBuilder().center().text(GetText.tr(
                        "Minecraft has crashed due to not being compatible with your Java version.<br/><br/>This version of Minecraft requires Java {0} or newer.<br/><br/>Make sure you've selected the correct Java version in this instances<br/>settings or not disabled the \"Use Java Provided By Minecraft\" setting.<br/><br/>If it's still not working, you may be using a mod that requires Java {0},<br/>but Minecraft only requires Java 8, so you can go into the instances<br/>settings and in the \"Java\" tab, change the \"Runtime Overide\"<br/>option to one that is Java {0} or newer.",
                        "17"))
                        .build())
                .setType(DialogManager.INFO).show();
    }
}
