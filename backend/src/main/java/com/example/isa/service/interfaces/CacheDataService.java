package com.example.isa.service.interfaces;

import com.example.isa.model.CacheData;

public interface CacheDataService {
    public CacheData get(String key);
    public void add(String key, String value);
}
