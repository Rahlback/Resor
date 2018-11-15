package resor.ahlback.rasmus.kollektivresoplanerare.Interfaces;

import org.json.JSONObject;

public interface JsonDownloadResponse {

    void processFinished(JSONObject output, int responseCode);
}
