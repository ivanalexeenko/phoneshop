package com.es.core.service;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService {
    public Optional<Phone> get(Long key);

    public void save(Phone phone);

    public List findAll(int offset, int limit, String search, String orderBy, Boolean isAscend);

    public Integer countPhonesStockBiggerZero(String search);
}
