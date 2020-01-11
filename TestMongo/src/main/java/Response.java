import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

public class Response {
    private String time;
    private Map<Double, Rate> rateMap;

    public JSONObject toJSON(){
        JSONObject jo = new JSONObject();
        if (time != null){
            jo.put("time", time);
        }
        if (rateMap != null){
            JSONArray array = new JSONArray();
            rateMap.forEach((percent, rate) -> {
                JSONObject obj = new JSONObject();
                obj.put("count", rate.getCount());
                obj.put("point", rate.getPoint());
                array.add(obj);
            });
            jo.put("rates", array);
        }
        return jo;
    }
}
