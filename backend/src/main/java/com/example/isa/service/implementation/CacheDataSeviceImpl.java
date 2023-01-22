package com.example.isa.service.implementation;

import com.example.isa.model.CacheData;
import com.example.isa.repository.CacheDataRepository;
import com.example.isa.service.interfaces.CacheDataService;
import org.springframework.stereotype.Service;

@Service
public class CacheDataSeviceImpl implements CacheDataService {
    private final CacheDataRepository cacheDataRepository;

    public CacheDataSeviceImpl(CacheDataRepository cacheDataRepository) {
        this.cacheDataRepository = cacheDataRepository;
    }

    @Override
    public CacheData get(String key) {
        return cacheDataRepository.findById(key).orElse(null);
    }

    @Override
    public void add(String key, String value) {
        CacheData cacheData = new CacheData(key, value);
        cacheDataRepository.save(cacheData);
    }

    public void clear(String key) {
        cacheDataRepository.deleteById(key);
    }
}
