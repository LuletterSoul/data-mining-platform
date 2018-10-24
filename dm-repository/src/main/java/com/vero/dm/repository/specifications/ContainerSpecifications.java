package com.vero.dm.repository.specifications;


import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;
import com.vero.dm.model.DataSetContainer_;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:18 2018/2/21.
 * @since data-mining-platform
 */

public class ContainerSpecifications
{
    public static Specification<DataSetContainer> findContainersByCollectionId(Integer collectionId)
    {
        return (root, query, cb) -> {
            DataSetCollection collection = new DataSetCollection();
            collection.setCollectionId(collectionId);
            Predicate collectionIdPredicate = cb.equal(
                root.get(DataSetContainer_.DATA_SET_COLLECTION), collection);
            return query.where(collectionIdPredicate).getRestriction();
        };
    }
}
