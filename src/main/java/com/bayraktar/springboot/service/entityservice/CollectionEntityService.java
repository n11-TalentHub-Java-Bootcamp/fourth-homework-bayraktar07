package com.bayraktar.springboot.service.entityservice;

import com.bayraktar.springboot.dao.CollectionDao;
import com.bayraktar.springboot.entity.Collection;
import org.springframework.stereotype.Service;

@Service
public class CollectionEntityService extends BaseEntityService<Collection, CollectionDao>{

    public CollectionEntityService(CollectionDao dao) {
        super(dao);
    }

}
