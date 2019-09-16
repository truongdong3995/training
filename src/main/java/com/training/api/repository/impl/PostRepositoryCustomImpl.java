package com.training.api.repository.impl;

import com.training.api.entity.TblArea;
import com.training.api.repository.PostRepositoryCustom;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    /**
     * Search area information by post code
     *
     * @param  postCode post code;
     *
     * @return Object {@link TblArea}
     */
    @Override
    public List<TblArea> searchAreaByPostCode(String postCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM tbl_area AS ta ");
        sql.append("INNER JOIN tbl_city AS tc ");
        sql.append("ON ta.city_id = tc.city_id ");
        sql.append("INNER JOIN tbl_post AS tp ");
        sql.append("ON ta.post_id = tp.post_id ");
        sql.append("INNER JOIN tbl_old_post AS top ");
        sql.append("ON ta.old_post_id = top.old_post_id ");
        sql.append("INNER JOIN tbl_prefecture tpf ");
        sql.append("ON tpf.prefecture_id = tc.prefecture_id ");
        sql.append("WHERE tp.post_code = :postCode ");
        Query query = (Query) entityManager.createNativeQuery(sql.toString(), TblArea.class);
        query.setParameter("postCode", postCode);

        return (List<TblArea>) query.getResultList();
    }
}
