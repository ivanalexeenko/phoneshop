package com.es.core.dao;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    <T> void save(T phone);

    List findAll(int offset, int limit, String search, String orderBy, Boolean isAscend);

    Integer countPhonesStockBiggerZero(String search);
}
