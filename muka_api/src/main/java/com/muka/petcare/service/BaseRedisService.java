package com.muka.petcare.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface BaseRedisService<K, F, V> {
    void set(K key, V value); // lưu cặp key-value vào redis
    void setTimeToLive(K key, long timeoutInDays); // set thời gian tồn tại của cặp key-value trong redis
    void hashSet(K key, F field, V value);

//    boolean hashExists(String key, String field);
//    Object get(String key);
//    public Map<String, Object> getField(String key);
//    Object hashSet(String key, String field);
//    List<Object> hashGetByFieldPrefix(String key, String fieldPrefix);
//    Set<String> getFieldPrefixes(String key);

//    void delete(String key);
//    void delete(String key, String field);
//    void delete(String key, List<String> fields);
}
