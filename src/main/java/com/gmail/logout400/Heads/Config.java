/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.logout400.Heads;

import org.bukkit.configuration.Configuration;

/**
 *
 * @author Samuel
 */
public class Config {

    public static Messages getMessages() {
        Configuration c = Heads.INSTANCE.yLang;
        return new Messages(
                c.getString("head-added"),
                c.getString("head-given"),
                c.getString("command-usage"),
                c.getString("head-received"),
                c.getString("player-offline"),
                c.getString("unknown-cmd"),
                c.getString("plugin-version"),
                c.getString("reloaded"),
                c.getString("skull-info"),
                c.getString("not-skull"),
                c.getString("permissions"),
                c.getString("console-msg")
        );
    }

    public static class Messages {

        /*Head*/
        public final String headAdded;
        public final String headGiven;
        public final String commandUse;
        public final String headReceived;
        public final String playerOffline;

        /*Heads*/
        public final String unknownCommand;
        public final String pluginVersion;
        public final String reloaded;
        public final String skullInfo;
        public final String notSkull;

        /*Universal*/
        public final String permissions;
        public final String consoleSender;

        public Messages(String headAdded, String headGiven, String commandUse, String headReceived, String playerOffline, String unknownCommand, String pluginVersion, String reloaded, String skullInfo, String notSkull, String permissions, String consoleSender) {
            this.headAdded = headAdded;
            this.headGiven = headGiven;
            this.commandUse = commandUse;
            this.headReceived = headReceived;
            this.playerOffline = playerOffline;
            this.unknownCommand = unknownCommand;
            this.pluginVersion = pluginVersion;
            this.reloaded = reloaded;
            this.skullInfo = skullInfo;
            this.notSkull = notSkull;
            this.permissions = permissions;
            this.consoleSender = consoleSender;
        }

    }
}
