package service;

import header.RequestBody;
import model.UserData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataService {
    public static ConcurrentHashMap<String, UserData> data = new ConcurrentHashMap<>();
    public static List<String> missAccount = Collections.synchronizedList(new ArrayList<String>());
    public static List<String> missImage = Collections.synchronizedList(new ArrayList<String>());
    public static List<String> verifyAccount = Collections.synchronizedList(new ArrayList<String>());
    public static Queue<RequestBody> failList = new LinkedList<>();

}
