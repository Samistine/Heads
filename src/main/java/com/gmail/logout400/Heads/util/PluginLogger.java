package com.gmail.logout400.Heads.util;

import com.gmail.logout400.Heads.Heads;
import java.util.logging.Logger;

public class PluginLogger {

    private final Logger logger;

    public PluginLogger() {
        this.logger = Heads.INSTANCE.getLogger();
    }

    public void info(String message) {
        this.logger.info(message);
    }

    public void warn(String message) {
        this.logger.warning(message);
    }

    public void error(String message) {
        this.logger.severe(message);
    }

    public void infoSpacer() {
        this.logger.info("=========================================================");
    }

    public void errorSpacer() {
        this.logger.severe("=======================================================");
    }

    public void infoHeader() {
        this.logger.info("======================== Heads =========================");
    }

    public void errorHeader() {
        this.logger.severe("======================= Heads ========================");
    }
}
