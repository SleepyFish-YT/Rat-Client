package me.sleepyfish.rat.utils.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class Config {

    public final File file;
    public final long creationDate;

    public Config(File pathToFile) {
        long cdDate;
        this.file = pathToFile;

        if (!file.exists()) {
            cdDate = System.currentTimeMillis();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                cdDate = getData().get("creationTime").getAsLong();
            } catch (NullPointerException e) {
                cdDate = 0L;
            }
        }

        this.creationDate = cdDate;
    }

    public String getName() {
        return file.getName().replace(".cfg", "");
    }

    public JsonObject getData() {
        JsonParser jsonParser = new JsonParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);
            return (JsonObject) obj;
        } catch (Exception ignored) {
        }

        return null;
    }

    public void save(JsonObject data) {
        data.addProperty("creationDate", creationDate);

        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.write(data.toString());
        } catch (Exception ignored) {
        }
    }

}