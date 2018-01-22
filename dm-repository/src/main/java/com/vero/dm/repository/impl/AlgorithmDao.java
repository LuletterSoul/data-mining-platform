package com.vero.dm.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vero.dm.model.Algorithm;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class AlgorithmDao extends BaseDao<Algorithm, String> {

    protected AlgorithmDao() {
        super(Algorithm.class);
    }

    public Algorithm fetchAlgorithm(String algorithmId) {
        String hqlString = "select distinct a from Algorithm a " +
                            "left join fetch a.algorithmParameters where a.algorithmId = :algorithmId";
        return (Algorithm) getSession().createQuery(hqlString).
                setParameter("algorithmId",algorithmId).getSingleResult();
    }

    public List<Algorithm> fetchAlgorithms(List<String> algorithmIds) {
        String hqlString = "select distinct a from Algorithm a " +
                            "left join a.algorithmParameters where a.algorithmId in :algorithmIds";
        return getSession().createQuery(hqlString)
                    .setParameter("algorithmIds",algorithmIds).getResultList();
    }

    public List<String> fetchAlgorithmNames() {
        String hqlString = "select a.algorithmName from Algorithm a";
        return getSession().createQuery(hqlString).getResultList();
    }

}
