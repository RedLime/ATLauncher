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
package com.atlauncher.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.atlauncher.Gsons;
import com.atlauncher.constants.Constants;
import com.atlauncher.data.microsoft.Entitlements;
import com.atlauncher.data.microsoft.LoginResponse;
import com.atlauncher.data.microsoft.OauthTokenResponse;
import com.atlauncher.data.microsoft.Profile;
import com.atlauncher.data.microsoft.XboxLiveAuthResponse;
import com.atlauncher.network.Download;
import com.google.gson.JsonObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Various utility methods for interacting with the Microsoft Auth API.
 */
public class MicrosoftAuthAPI {

    public static OauthTokenResponse tradeCodeForAccessToken(String code) {
        RequestBody data = new FormBody.Builder().add("client_id", Constants.MICROSOFT_LOGIN_CLIENT_ID)
                .add("code", code).add("grant_type", "authorization_code")
                .add("redirect_uri", Constants.MICROSOFT_LOGIN_REDIRECT_URL)
                .add("scope", String.join(" ", Constants.MICROSOFT_LOGIN_SCOPES)).build();

        return Download.build().setUrl(Constants.MICROSOFT_AUTH_TOKEN_URL)
                .header("Content-Type", "application/x-www-form-urlencoded").post(data)
                .asClass(OauthTokenResponse.class, Gsons.DEFAULT);
    }


    public static JsonObject getDeviceAuthCode() {
        RequestBody data = new FormBody.Builder().add("client_id", Constants.MICROSOFT_LOGIN_CLIENT_ID)
            .add("scope", String.join(" ", Constants.MICROSOFT_LOGIN_SCOPES)).build();

        return Download.build().setUrl(Constants.MICROSOFT_DEVICE_CODE_URL)
            .header("Content-Type", "application/x-www-form-urlencoded").post(data).asClass(JsonObject.class, Gsons.DEFAULT);
    }

    public static OauthTokenResponse getDeviceAuthToken(String deviceCode) {
        RequestBody data = new FormBody.Builder().add("client_id", Constants.MICROSOFT_LOGIN_CLIENT_ID)
            .add("grant_type", "urn:ietf:params:oauth:grant-type:device_code")
            .add("device_code", deviceCode).build();

        return Download.build().setUrl(Constants.MICROSOFT_DEVICE_TOKEN_URL)
            .header("Content-Type", "application/x-www-form-urlencoded").post(data).asClass(OauthTokenResponse.class, Gsons.DEFAULT);
    }

    public static OauthTokenResponse refreshAccessToken(String refreshToken) {
        RequestBody data = new FormBody.Builder().add("client_id", Constants.MICROSOFT_LOGIN_CLIENT_ID)
                .add("refresh_token", refreshToken).add("grant_type", "refresh_token")
                .add("scope", String.join(" ", Constants.MICROSOFT_LOGIN_SCOPES))
                .build();

        return Download.build().setUrl(Constants.MICROSOFT_DEVICE_TOKEN_URL)
                .header("Content-Type", "application/x-www-form-urlencoded").post(data)
                .asClass(OauthTokenResponse.class, Gsons.DEFAULT);
    }

    public static XboxLiveAuthResponse getXBLToken(String accessToken) {
        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put("AuthMethod", "RPS");
        properties.put("SiteName", "user.auth.xboxlive.com");
        properties.put("RpsTicket", "d=" + accessToken);

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("Properties", properties);
        data.put("RelyingParty", "http://auth.xboxlive.com");
        data.put("TokenType", "JWT");

        return Download.build().setUrl(Constants.MICROSOFT_XBL_AUTH_TOKEN_URL)
                .header("Content-Type", "application/json").header("Accept", "application/json")
                .header("x-xbl-contract-version", "1")
                .post(RequestBody.create(Gsons.DEFAULT.toJson(data), MediaType.get("application/json; charset=utf-8")))
                .asClass(XboxLiveAuthResponse.class);
    }

    public static XboxLiveAuthResponse getXstsToken(String xblToken) throws IOException {
        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put("SandboxId", "RETAIL");

        List<String> userTokens = new ArrayList<String>();
        userTokens.add(xblToken);
        properties.put("UserTokens", userTokens);

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("Properties", properties);
        data.put("RelyingParty", "rp://api.minecraftservices.com/");
        data.put("TokenType", "JWT");

        return Download.build().setUrl(Constants.MICROSOFT_XSTS_AUTH_TOKEN_URL)
                .header("Content-Type", "application/json").header("Accept", "application/json")
                .header("x-xbl-contract-version", "1")
                .post(RequestBody.create(Gsons.DEFAULT.toJson(data), MediaType.get("application/json; charset=utf-8")))
                .asClassWithThrow(XboxLiveAuthResponse.class);
    }

    public static LoginResponse loginToMinecraft(String xstsToken) {
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("xtoken", xstsToken);
        data.put("platform", "PC_LAUNCHER");

        return Download.build().setUrl(Constants.MICROSOFT_MINECRAFT_LOGIN_URL)
                .header("Content-Type", "application/json").header("Accept", "application/json")
                .post(RequestBody.create(Gsons.DEFAULT.toJson(data), MediaType.get("application/json; charset=utf-8")))
                .asClass(LoginResponse.class);
    }

    public static Entitlements getEntitlements(String accessToken) {
        return Download.build()
                .setUrl(String.format("%s?requestId=%s", Constants.MICROSOFT_MINECRAFT_ENTITLEMENTS_URL,
                        UUID.randomUUID()))
                .header("Authorization", "Bearer " + accessToken).header("Content-Type", "application/json")
                .header("Accept", "application/json").asClass(Entitlements.class);
    }

    public static Profile getMcProfile(String accessToken) throws IOException {
        return Download.build().setUrl(Constants.MICROSOFT_MINECRAFT_PROFILE_URL)
                .header("Authorization", "Bearer " + accessToken).asClassWithThrow(Profile.class);
    }
}
