package com.es.core.service;

import com.es.core.exception.GetterInvokerException;
import com.es.core.model.phone.Phone;
import com.es.core.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class HttpSessionPhoneService implements PhoneService {
    private final PhoneDao phoneDao;

    @Autowired
    public HttpSessionPhoneService(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public Optional<Phone> get(Long key) {
        return phoneDao.get(key);
    }

    @Override
    public void save(Phone phone) throws IllegalArgumentException, GetterInvokerException {
        phoneDao.save(phone);
    }

    @Override
    public List findAll(int offset, int limit, String search, String orderBy, Boolean isAscend) {
        return phoneDao.findAll(offset, limit, search, orderBy, isAscend);
    }

    @Override
    public Integer countPhonesStockBiggerZero(String search) {
        return phoneDao.countPhonesStockBiggerZero(search);
    }
}
