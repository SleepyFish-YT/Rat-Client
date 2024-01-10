package me.sleepyfish.rat.utils.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.sleepyfish.rat.utils.render.GuiUtils;

import java.io.*;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Config {

    public final File file;
    public final long creationDate;

    public GuiUtils.Button overRect;

    public Config(final File pathToFile) {
        long cdDate;
        this.file = pathToFile;

        if (!file.exists()) {
            cdDate = System.currentTimeMillis();
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                cdDate = getData().get("creationTime").getAsLong();
            } catch (Exception e) {
                cdDate = 0L;
                e.printStackTrace();
            }
        }

        this.creationDate = cdDate;
    }

    public final String getName() {
        return file.getName().replace(".cfg", "");
    }

    public final JsonObject getData() {
        final JsonParser jsonParser = new JsonParser();

        try (final FileReader reader = new FileReader(file)) {
            final Object obj = jsonParser.parse(reader);
            return (JsonObject) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(final JsonObject data) {
        data.addProperty("creationDate", creationDate);

        try (final PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.write(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}