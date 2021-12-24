package service;

import model.UserData;

import java.util.concurrent.ConcurrentHashMap;

public class DataService {
    public static ConcurrentHashMap<String, UserData> data;
}
