package com.gmail.logout400.Heads.util;

import com.gmail.logout400.Heads.Heads;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PluginVersion {

    private Heads plugin;
    private URL filesFeed;
    private String version;
    private String link;

    public PluginVersion(String url) {
        this.plugin = Heads.INSTANCE;
        try {
            this.filesFeed = new URL(url);
        } catch (MalformedURLException e) {
            this.plugin.getLogger().log(Level.WARNING, "Error: {0}", e.getMessage());
        }
    }

    public boolean updateNeeded() {
        try {
            InputStream input = this.filesFeed.openConnection().getInputStream();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

            Node latestFile = document.getElementsByTagName("item").item(0);
            NodeList children = latestFile.getChildNodes();

            this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z]", "");
            this.link = children.item(3).getTextContent();
            if (!this.plugin.getDescription().getVersion().equals(this.version)) {
                return true;
            }
        } catch (Exception e) {
            this.plugin.getLogger().log(Level.WARNING, "Error: {0}", e.getMessage());
        }
        return false;
    }

    public String getVersion() {
        return this.version;
    }

    public String getLink() {
        return this.link;
    }
}
