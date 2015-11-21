package com.siddharth.aprwalkathon;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Siddharth on 7/25/2015.
 */
public class Tracks {

    private static ArrayList<Track> jsonStrings = new ArrayList<>();
    private static Context mContext;
    private static final String JSONString1 = "{\n" +
            "  \"snappedPoints\": [\n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.923104160888801, \n" +
            "        \"longitude\": 77.69459399572277\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.921951900000002, \n" +
            "        \"longitude\": 77.694555\n" +
            "      }, \n" +
            "      \"turn\": 1\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.921952223780888, \n" +
            "        \"longitude\": 77.69454287962004\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.921961099999999, \n" +
            "        \"longitude\": 77.69421059999999\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9219699, \n" +
            "        \"longitude\": 77.6940047\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.921984199999999, \n" +
            "        \"longitude\": 77.6936502\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9219887, \n" +
            "        \"longitude\": 77.6934799\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.921994300000001, \n" +
            "        \"longitude\": 77.69304799999999\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922001499999997, \n" +
            "        \"longitude\": 77.6928927\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922030300000001, \n" +
            "        \"longitude\": 77.6924346\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922053400000001, \n" +
            "        \"longitude\": 77.6922828\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922053516279576, \n" +
            "        \"longitude\": 77.69227670894762\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922063000000001, \n" +
            "        \"longitude\": 77.6917799\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922072799999997, \n" +
            "        \"longitude\": 77.6916873\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922090499999998, \n" +
            "        \"longitude\": 77.6911154\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922113999999999, \n" +
            "        \"longitude\": 77.6905134\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.92211411409211, \n" +
            "        \"longitude\": 77.69050678423163\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922123800000001, \n" +
            "        \"longitude\": 77.6899451\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922160799999997, \n" +
            "        \"longitude\": 77.689386\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922160982356916, \n" +
            "        \"longitude\": 77.68937959926582\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9221717, \n" +
            "        \"longitude\": 77.68900339999999\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9221807, \n" +
            "        \"longitude\": 77.68879969999999\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9221875, \n" +
            "        \"longitude\": 77.68864049999999\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9221915, \n" +
            "        \"longitude\": 77.688499\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922189238590185, \n" +
            "        \"longitude\": 77.68844616948324\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922186799999999, \n" +
            "        \"longitude\": 77.6883892\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.922180100000002, \n" +
            "        \"longitude\": 77.688336\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9221678, \n" +
            "        \"longitude\": 77.688248\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9221471, \n" +
            "        \"longitude\": 77.688088\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9221431, \n" +
            "        \"longitude\": 77.6879491\n" +
            "      }, \n" +
            "      \"turn\": -1\n" +
            "    }, \n" +
            "    {\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 12.9209162982885, \n" +
            "        \"longitude\": 77.68790184792499\n" +
            "      }, \n" +
            "      \"turn\": 0\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        private final String  jsonStringHM = "";

    private static boolean initialized = false;

    public static String loadJSONFromAsset(String track_name) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open(track_name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    
    public static void init(Context context){
        mContext=context;
        if(!initialized){

            //jsonStrings.add(new Track(JSONString1, "Test Track."));
            String jsonHalfMarathon = loadJSONFromAsset("halfmarathon.json");
            jsonStrings.add(new Track(jsonHalfMarathon, "Half Marathon"));
            String jsonTenK = loadJSONFromAsset("tenk.json");
            jsonStrings.add(new Track(jsonTenK, "Ten K"));
            String jsonFiveK = loadJSONFromAsset("fivek.json");
            jsonStrings.add(new Track(jsonFiveK, "Five K"));
            String jsonTwoK = loadJSONFromAsset("twok.json");
            jsonStrings.add(new Track(jsonTwoK, "Two K"));
            initialized = true;
        }
        
    }

    public static Track getTrack(int index){
        return jsonStrings.get(index);
    }

    public static int getLength(){
        return jsonStrings.size();
    }

    public static ArrayList<String> getArrayDescriptions(){
        ArrayList<String> descriptions = new ArrayList<>();
        for(Track track : jsonStrings){
            descriptions.add(track.description);
        }
        return descriptions;
    }

    public static class Track{
        public String JSONString;
        public String description;

        Track(String JSONString, String description){
            this.JSONString = JSONString;
            this.description = description;
        }

    }

}
