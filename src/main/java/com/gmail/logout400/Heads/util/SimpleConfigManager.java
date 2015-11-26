package com.gmail.logout400.Heads.util;

import com.gmail.logout400.Heads.Heads;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class SimpleConfigManager
{
  private Heads plugin;
  
  public SimpleConfigManager()
  {
    this.plugin = Heads.INSTANCE;
  }
  
  public SimpleConfig getNewConfig(String filePath)
  {
    File file = getConfigFile(filePath);
    if (!file.exists()) {
      prepareFile(filePath);
    }
    SimpleConfig config = new SimpleConfig(getConfigContent(filePath), file, getCommentsNum(file));
    
    return config;
  }
  
  private File getConfigFile(String file)
  {
    if ((file.isEmpty()) || (file == null)) {
      return null;
    }
    File configFile;
    if (file.contains("/"))
    {
      if (file.startsWith("/")) {
        configFile = new File(this.plugin.getDataFolder() + file.replace("/", File.separator));
      } else {
        configFile = new File(this.plugin.getDataFolder() + File.separator + file.replace("/", File.separator));
      }
    }
    else
    {
      configFile = new File(this.plugin.getDataFolder(), file);
    }
    return configFile;
  }
  
  public void prepareFile(String filePath, String resource)
  {
    File file = getConfigFile(filePath);
    if (file.exists()) {
      return;
    }
    try
    {
      file.getParentFile().mkdirs();
      file.createNewFile();
      if ((!resource.isEmpty()) && (resource != null)) {
        copyResource(this.plugin.getResource(resource), file);
      }
    }
    catch (IOException e)
    {
      this.plugin.logger.error("Can't create new file (" + file.getName() + ") !");
    }
  }
  
  public void prepareFile(String filePath)
  {
    prepareFile(filePath, null);
  }
  
  public void setHeader(File file, String[] header)
  {
    if (!file.exists()) {
      return;
    }
    try
    {
      StringBuilder config = new StringBuilder("");
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String currentLine;
      while ((currentLine = reader.readLine()) != null)
      {
        config.append(currentLine + "\n");
      }
      reader.close();
      config.append("# +----------------------------------------------------+ #\n");
      for (String line : header)
      {
        int lenght = (50 - line.length()) / 2;
        
        StringBuilder finalLine = new StringBuilder(line);
        for (int i = 0; i < lenght; i++)
        {
          finalLine.append(" ");
          finalLine.reverse();
          finalLine.append(" ");
          finalLine.reverse();
        }
        if (line.length() % 2 != 0) {
          finalLine.append(" ");
        }
        config.append("# | " + finalLine.toString() + " | #\n");
      }
      config.append("# +----------------------------------------------------+ #");
      
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      writer.write(prepareConfigString(config.toString()));
      writer.flush();
      writer.close();
    }
    catch (IOException e)
    {
      this.plugin.logger.error("Can't set header - " + file.getName() + " !");
      return;
    }
  }
  
  public InputStream getConfigContent(File file)
  {
    if (!file.exists())
    {
      this.plugin.logger.error("Can't get content - " + file.getName() + " not existing !");
      return null;
    }
    try
    {
      int commentNum = 0;
      


      String pluginName = this.plugin.getName();
      
      StringBuilder whole = new StringBuilder("");
      
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String currentLine;
      while ((currentLine = reader.readLine()) != null)
      {
        if (currentLine.startsWith("#"))
        {
          String addLine = currentLine.replaceFirst("#", pluginName + "_COMMENT_" + commentNum + ": \"").concat("\"");
          whole.append(addLine + "\n");
          commentNum++;
        }
        else
        {
          whole.append(currentLine + "\n");
        }
      }
      String config = whole.toString();
      InputStream configStream = new ByteArrayInputStream(config.getBytes(Charset.forName("UTF-8")));
      
      reader.close();
      return configStream;
    }
    catch (IOException e)
    {
      this.plugin.logger.error("Can't get content - " + file.getName() + " not existing !");
    }
    return null;
  }
  
  private int getCommentsNum(File file)
  {
    if (!file.exists()) {
      return 0;
    }
    try
    {
      int comments = 0;
      

      BufferedReader reader = new BufferedReader(new FileReader(file));
      String currentLine;
      while ((currentLine = reader.readLine()) != null)
      {
        if (currentLine.startsWith("#")) {
          comments++;
        }
      }
      reader.close();
      return comments;
    }
    catch (IOException e)
    {
      this.plugin.logger.error("Can't get comments - " + file.getName() + " not existing !");
    }
    return 0;
  }
  
  public InputStream getConfigContent(String filePath)
  {
    return getConfigContent(getConfigFile(filePath));
  }
  
  private String prepareConfigString(String configString)
  {
    int lastLine = 0;
    int headerLine = 0;
    
    String[] lines = configString.split("\n");
    StringBuilder config = new StringBuilder("");
    for (String line : lines) {
      if (line.startsWith(getPluginName() + "_COMMENT"))
      {
        String comment = replaceLast("#" + line.trim().substring(line.indexOf(":") + 3), "'", "");
        if (comment.startsWith("# +-"))
        {
          if (headerLine == 0)
          {
            config.append(comment + "\n");
            
            lastLine = 0;
            headerLine = 1;
          }
          else if (headerLine == 1)
          {
            config.append(comment + "\n\n");
            
            lastLine = 0;
            headerLine = 0;
          }
        }
        else
        {
          if (lastLine == 0) {
            config.append(comment + "\n");
          } else if (lastLine == 1) {
            config.append("\n" + comment + "\n");
          }
          lastLine = 0;
        }
      }
      else
      {
        config.append(line + "\n");
        lastLine = 1;
      }
    }
    return config.toString();
  }
  
  public void saveConfig(String configString, File file)
  {
    String configuration = prepareConfigString(configString);
    try
    {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      writer.write(configuration);
      writer.flush();
      writer.close();
    }
    catch (IOException e)
    {
      this.plugin.logger.error("Can't save config - " + file.getName() + " !");
    }
  }
  
  public String getPluginName()
  {
    return this.plugin.getDescription().getName();
  }
  
  private void copyResource(InputStream resource, File file)
  {
    try
    {
      OutputStream out = new FileOutputStream(file);
      

      byte[] buf = new byte[1024];
      int lenght;
      while ((lenght = resource.read(buf)) > 0)
      {
        out.write(buf, 0, lenght);
      }
      out.close();
      resource.close();
    }
    catch (Exception e)
    {
      this.plugin.logger.error("Can't copy resource to " + file.getName() + " !");
    }
  }
  
  private String replaceLast(String text, String regex, String replacement)
  {
    return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
  }
}
