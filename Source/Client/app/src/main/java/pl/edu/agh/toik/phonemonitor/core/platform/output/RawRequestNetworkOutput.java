package pl.edu.agh.toik.phonemonitor.core.platform.output;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.toik.phonemonitor.core.common.output.IOutput;
import pl.edu.agh.toik.phonemonitor.gui.Config;

/**
 * Created by Imiolak.
 * 01.06.2016
 */
public class RawRequestNetworkOutput implements IOutput, Response.Listener<JSONObject>, Response.ErrorListener {

    private final String url;

    public RawRequestNetworkOutput(String host, String sensorName) {

        this.url = "http://" + host + ":8080/sensor/" + sensorName + "/";
    }

    @Override
    public void write(String tag, String message) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("value", message);
        requestMap.put("color", tag);
        requestMap.put("timestamp", Long.toString(System.currentTimeMillis() / 1000L));

        JSONObject jsonObject = new JSONObject(requestMap);
        RequestQueue queue = Volley.newRequestQueue(Config.context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                this, this);

        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("REQUEST", "ERROR " + error.getMessage() + " " + url);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("REQUEST", "OK" + url);
    }
}
