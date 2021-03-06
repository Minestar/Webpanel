/*
 * Copyright (C) 2014 MineStar.de 
 * 
 * This file is part of AdminStuff.
 * 
 * AdminStuff is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * AdminStuff is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AdminStuff.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.Webpanel.units;

import java.util.UUID;

public class UserData {

    // 1000 * minutes * seconds
    private static final long timeoutInMS = 1000 * 60 * 5;

    private final String userName;
    private String token;
    private final int hashCode;
    private long lastUsed;
    private int level = -1;

    public UserData(String userName, int level) {
        this.userName = userName;
        this.hashCode = this.userName.hashCode();
        this.level = level;
        this.updateToken();
    }

    public String getUserName() {
        return userName;
    }

    public void updateToken() {
        this.token = UUID.randomUUID().toString();
        this.lastUsed = System.currentTimeMillis();
    }

    public int getLevel() {
        return level;
    }

    public String getToken() {
        return token;
    }

    public boolean isValid(String token) {
        return this.token.equals(token) && System.currentTimeMillis() < this.lastUsed + timeoutInMS;
    }

    public void refreshToken() {
        this.lastUsed = System.currentTimeMillis();
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    public void invalidateToken() {
        this.lastUsed = 0;
        this.token = "";
    }
}
