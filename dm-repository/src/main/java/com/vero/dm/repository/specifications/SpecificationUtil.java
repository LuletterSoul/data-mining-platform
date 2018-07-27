package com.vero.dm.repository.specifications;


import java.util.List;

import javax.persistence.criteria.Predicate;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 9:37 2018/7/27.
 * @since data-mining-platform
 */

public class SpecificationUtil
{
    public static Predicate[] toPredicates(List<Predicate> predicates)
    {
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
