package com.es.core.dao;

import com.es.core.exception.GetterInvokerException;
import com.es.core.model.phone.Phone;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    void save(Phone phone) throws IllegalArgumentException, GetterInvokerException;

    List findAll(int offset, int limit, String search, String orderBy, Boolean isAscend);

    Integer countPhonesStockBiggerZero(String search);

}