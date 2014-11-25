package org.ydle.network.manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.ydle.config.Config;
import org.ydle.data.memoryprovider.MemoryProvider;
import org.ydle.data.model.Room;
import org.ydle.network.listener.DefaultListener;
import org.ydle.network.parser.LogsParser;
import org.ydle.network.parser.RoomTypesParser;
import org.ydle.network.parser.RoomsParser;
import org.ydle.network.request.CheckServerRequest;
import org.ydle.network.request.DeleteRoomRequest;
import org.ydle.network.request.GetAllLogsRequest;
import org.ydle.network.request.GetAllRoomTypesRequest;
import org.ydle.network.request.GetAllRoomsRequest;
import org.ydle.network.request.GetRoomRequest;
import org.ydle.network.request.PostRoomRequest;
import org.ydle.utils.NetworkUtils;

import java.util.Observable;

/**
 * Created by Jean-Baptiste on 30/01/14.
 */
public class NetworkManager extends Observable {

    private static final String LOG_TAG = NetworkManager.class.getSimpleName();

    private static RequestQueue mRequestQueue;
    private static Context mContext;

    public NetworkManager(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public enum NetworkResult {
        CheckServerSuccess,
        CheckServerError,
        GetAllRoomsSuccess,
        GetAllRoomsError,
        GetRoomSuccess,
        GetRoomError,
        PostRoomSuccess,
        PostRoomError,
        DeleteRoomSuccess,
        DeleteRoomError,
        GetAllRoomTypesSuccess,
        GetAllRoomTypesError,
        GetAllSensorTypesSuccess,
        GetAllSensorTypesError,
        GetAllLogsSuccess,
        GetAllLogsError;
    }

    /**
     * Call CheckServer
     */
    public boolean callCheckServer(String url) {
        boolean result = true;
        if (NetworkUtils.hasConnectivity(mContext)) {
            mRequestQueue.add(new CheckServerRequest(
                    url,
                    new DefaultListener() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    setChanged();
                    notifyObservers(NetworkResult.CheckServerSuccess);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                    setChanged();
                    notifyObservers(NetworkResult.CheckServerError);
                }
            }));
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Call GetAllRooms
     */
    public boolean callGetAllRooms() {
        boolean result = true;
        if (NetworkUtils.hasConnectivity(mContext)) {
            mRequestQueue.add(new GetAllRoomsRequest(new DefaultListener() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    MemoryProvider.getInstance().setRooms(RoomsParser.parseAllRooms(response));
                    MemoryProvider.getInstance().setRoomsError(null);
                    MemoryProvider.getInstance().setRoomsVolleyError(null);
                    setChanged();
                    notifyObservers(NetworkResult.GetAllRoomsSuccess);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                    MemoryProvider.getInstance().setRooms(null);
                    MemoryProvider.getInstance().setRoomsError(null);
                    MemoryProvider.getInstance().setRoomsVolleyError(error);
                    setChanged();
                    notifyObservers(NetworkResult.GetAllRoomsError);
                }
            }));
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Call GetAllRoomTypes
     */
    public boolean callGetAllRoomTypes() {
        boolean result = true;
        if (NetworkUtils.hasConnectivity(mContext)) {
            mRequestQueue.add(new GetAllRoomTypesRequest(new DefaultListener() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    MemoryProvider.getInstance().setRoomTypes(RoomTypesParser.parseAllRoomTypes(response));
                    MemoryProvider.getInstance().setRoomTypesError(null);
                    MemoryProvider.getInstance().setRoomTypesVolleyError(null);
                    setChanged();
                    notifyObservers(NetworkResult.GetAllRoomTypesSuccess);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                    MemoryProvider.getInstance().setRoomTypes(null);
                    MemoryProvider.getInstance().setRoomTypesError(null);
                    MemoryProvider.getInstance().setRoomTypesVolleyError(error);
                    setChanged();
                    notifyObservers(NetworkResult.GetAllRoomTypesError);
                }
            }));
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Call GetRoom
     */
    public boolean callGetRoom(int id) {
        boolean result = true;
        if (NetworkUtils.hasConnectivity(mContext)) {
            mRequestQueue.add(new GetRoomRequest(new DefaultListener() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    try {
                        MemoryProvider.getInstance().updateRoom(RoomsParser.parseARoom(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setChanged();
                    notifyObservers(NetworkResult.GetRoomSuccess);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                    setChanged();
                    notifyObservers(NetworkResult.GetRoomError);
                }
            }, id));
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Call PostRoom
     */
    public boolean callPostRoom(Room room) {
        boolean result = true;
        if (NetworkUtils.hasConnectivity(mContext)) {
            mRequestQueue.add(new PostRoomRequest(new Response.Listener<String>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    super.onResponse(response);
//                    setChanged();
//                    if (response.optInt("code") == 0) {
//                        notifyObservers(NetworkResult.PostRoomSuccess);
//                    } else {
//                        if (Config.displayLog()) {
//                            Log.d(LOG_TAG, "Error inserting room : " + response.optString("result"));
//                        }
//                        notifyObservers(NetworkResult.PostRoomError);
//                    }
//                }
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    super.onErrorResponse(error);
//                    setChanged();
//                    notifyObservers(NetworkResult.PostRoomError);
//                }

                @Override
                public void onResponse(String resp) {
                    JSONObject response = null;
                    try {
                        response = new JSONObject(resp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setChanged();
                    if (response.optInt("code") == 0) {
                        notifyObservers(NetworkResult.PostRoomSuccess);
                    } else {
                        if (Config.displayLog()) {
                            Log.d(LOG_TAG, "Error inserting room : " + response.optString("result"));
                        }
                        notifyObservers(NetworkResult.PostRoomError);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setChanged();
                    notifyObservers(NetworkResult.PostRoomError);
                }
            },
                    room));
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Call DeleteRoom
     */
    public boolean callDeleteRoom(Room room) {
        boolean result = true;
        if (NetworkUtils.hasConnectivity(mContext)) {
            mRequestQueue.add(new DeleteRoomRequest(new Response.Listener<String>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    super.onResponse(response);
//                    setChanged();
//                    if (response.optInt("code") == 0) {
//                        notifyObservers(NetworkResult.PostRoomSuccess);
//                    } else {
//                        if (Config.displayLog()) {
//                            Log.d(LOG_TAG, "Error inserting room : " + response.optString("result"));
//                        }
//                        notifyObservers(NetworkResult.PostRoomError);
//                    }
//                }
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    super.onErrorResponse(error);
//                    setChanged();
//                    notifyObservers(NetworkResult.PostRoomError);
//                }

                @Override
                public void onResponse(String resp) {
                    JSONObject response = null;
                    try {
                        response = new JSONObject(resp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setChanged();
                    if (response.optInt("code") == 0) {
                        notifyObservers(NetworkResult.DeleteRoomSuccess);
                    } else {
                        if (Config.displayLog()) {
                            Log.d(LOG_TAG, "Error deleting room : " + response.optString("result"));
                        }
                        notifyObservers(NetworkResult.DeleteRoomError);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setChanged();
                    notifyObservers(NetworkResult.DeleteRoomError);
                }
            },
                    room));
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Call GetAllLogs
     */
    public boolean callGetAllLogs() {
        boolean result = true;
        if (NetworkUtils.hasConnectivity(mContext)) {
            mRequestQueue.add(new GetAllLogsRequest(new DefaultListener() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    MemoryProvider.getInstance().setLogs(LogsParser.parseAllLogs(response));
                    MemoryProvider.getInstance().setLogsError(null);
                    MemoryProvider.getInstance().setLogsVolleyError(null);
                    setChanged();
                    notifyObservers(NetworkResult.GetAllLogsSuccess);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                    MemoryProvider.getInstance().setRooms(null);
                    MemoryProvider.getInstance().setRoomsError(null);
                    MemoryProvider.getInstance().setRoomsVolleyError(error);
                    setChanged();
                    notifyObservers(NetworkResult.GetAllLogsError);
                }
            }));
        } else {
            result = false;
        }
        return result;
    }


}
