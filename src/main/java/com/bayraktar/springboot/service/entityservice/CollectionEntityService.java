package com.bayraktar.springboot.service.entityservice;

import com.bayraktar.springboot.dao.CollectionDao;
import com.bayraktar.springboot.entity.Collection;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CollectionEntityService extends BaseEntityService<Collection, CollectionDao>{

    public CollectionEntityService(CollectionDao dao) {
        super(dao);
    }

    public List<Collection> findAllCollectionByUserId(Long id) {
        return getDao().findAllCollectionByUserId(id);
    }

    public Long findAllInterestSumByUserId (Long id) {
        return getDao().findAllInterestSumByUserId(id);
    }

    public List<Collection> findCollectionsBetweenDates(LocalDate startDate, LocalDate endDate){
        return getDao().findAllByCollectionDateGreaterThanEqualAndCollectionDateLessThanEqual(startDate, endDate);
    }
}
