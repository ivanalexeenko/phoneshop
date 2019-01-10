package com.es.core.service;

import com.es.core.dao.StatusDao;
import com.es.core.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusDao statusDao;

    @Autowired
    public StatusServiceImpl(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public void updateStatus(OrderStatus status, String orderId) {
        statusDao.updateStatusWithId(status, orderId);
    }
}
