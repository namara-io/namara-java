import java.io.*;
import java.io.UnsupportedEncodingException;
import java.net.*;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Namara {

    public String apiKey;
    public boolean debug;
    public String host;
    public String apiVersion;

    public Namara(String apiKey                            ) { this(apiKey, false, "api.namara.io", "v0"); }
    public Namara(String apiKey, boolean debug             ) { this(apiKey, debug, "api.namara.io", "v0"); }
    public Namara(String apiKey, boolean debug, String host) { this(apiKey, debug, host, "V0");            }

    public Namara(String apiKey, boolean debug, String host, String apiVersion) {
        this.apiKey = apiKey;
        this.debug = debug;
        this.host = host;
        this.apiVersion = apiVersion;
    }

    public <T> T get(String dataset, String version, Map<String, String> options) {
        InputStreamReader reader = getInputStreamReader(dataset, version, options);
        if (reader == null) return null;

        return new Gson().fromJson(reader, new TypeToken<T>(){}.getType());
    }

    public String getJson(String dataset, String version, Map<String, String> options) {
        InputStreamReader inputStreamReader = getInputStreamReader(dataset, version, options);
        if (inputStreamReader == null) return null;

        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException ex) {}

        return builder.toString();
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> typeClass)
    {
        return new Gson().fromJson(json, new JsonList<T>(typeClass));
    }

    public String getBasePath(String dataset, String version) {
        return String.format("http://%s/%s/data_sets/%s/data/%s", this.host, this.apiVersion, dataset, version);
    }

    public String getPath(String dataset, String version, Map<String, String> options) {
        String encodedOptions = "";
        try { encodedOptions = encodeOptions(options); } catch (UnsupportedEncodingException ex) {}

        if (!this.isAggregation(options)) {
            return String.format("%s?api_key=%s&%s", this.getBasePath(dataset, version), this.apiKey, encodedOptions);
        } else {
            return String.format("%s/aggregation?api_key=%s&%s", this.getBasePath(dataset, version), this.apiKey, encodedOptions);
        }
    }

    private InputStreamReader getInputStreamReader(String dataset, String version, Map<String, String> options) {
        InputStreamReader reader;
        try {
            URL url = new URL(this.getPath(dataset, version, options));
            URLConnection urlConnection = url.openConnection();
            reader = new InputStreamReader(urlConnection.getInputStream());
        } catch (IOException ex) {
            return null;
        }
        return reader;
    }

    private String encodeOptions(Map<String, String> options) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<?, ?> pair : options.entrySet()) {
            builder.append( String.format("%s=", URLEncoder.encode ( (String) pair.getKey (), "UTF-8" )));
            builder.append( String.format("%s&", URLEncoder.encode ( (String) pair.getValue (), "UTF-8" )));
        }

        return builder.toString();
    }

    private boolean isAggregation(Map options) {
        return (options != null && options.containsKey("operation"));
    }
}