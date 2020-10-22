package com.nathan96169.toolshelper.dispatcher;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Main Way to send event to anther view activity fragment or object.
 */
public class ODispatcher {
    //创建基本线程池
    private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,6,1000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(60));
    private static HashMap<String, ArrayList<OEventObject>> map;
//    private static HashMap<String, ArrayList<WeakReference<OEventObject>>> map;//no useful

    public static void addEventListener(String eventName, OEventObject thisObj) {
        if (map == null) map = new HashMap<>();
        if (map.containsKey(eventName)) {
            ArrayList<OEventObject> arr = map.get(eventName);
            if (!arr.contains(thisObj)) arr.add(thisObj);
        } else {
            ArrayList<OEventObject> arr = new ArrayList<>();
            arr.add(thisObj);
            map.put(eventName, arr);
        }
        Log.e("Event",eventName+"  size:"+map.get(eventName).size());
    }
    public static void removeEventListener(String eventName, OEventObject thisObj) {
        if (map == null || thisObj == null) return;
        if (map.containsKey(eventName)) {
            ArrayList<OEventObject> arr = map.get(eventName);
            if (arr == null || arr.size() == 0) return;
            if (arr.contains(thisObj)) arr.remove(thisObj);
            if (arr.size() == 0) {
                map.remove(eventName);
            }
        }
        Log.e("Event",eventName+"  removeEvent:");
    }

    public static void dispatchEvent(String eventName) {
        dispatchEvent(eventName, "");
    }

    public static void dispatchEvent(final String eventName, final Object paramObj) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (map == null || paramObj == null || map.isEmpty()) return;
                    HashMap<String, ArrayList<OEventObject>> mapCopy = (HashMap<String, ArrayList<OEventObject>>)map.clone();//ConcurrentModificationException
                    Iterator iter = mapCopy.entrySet().iterator();
                    while (iter != null && iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        final String key = (String) entry.getKey();
                        if (key != null && key.equals(eventName)) {
                            ArrayList<OEventObject> arr = (ArrayList<OEventObject>) entry.getValue();
                            ArrayList<OEventObject> arrCopy = (ArrayList<OEventObject>) arr.clone();//ConcurrentModificationException
                            if (arrCopy!=null) {
                                for(final OEventObject object : arrCopy) {
//                                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                                    object.receiveEvent(eventName, paramObj);
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    Log.e("ExceptionDispch",e.toString());
                    return;
                }
            }
        };
        threadPoolExecutor.execute(runnable);
    }

}
