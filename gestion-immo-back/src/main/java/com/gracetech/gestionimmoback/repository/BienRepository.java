package com.gracetech.gestionimmoback.repository;

import com.gracetech.gestionimmoback.model.Bien;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BienRepository extends JpaRepository<Bien, Long> {

    @Query("select b from Bien b where lower(b.description) like lower(concat('%', :query, '%')) or " +
            " lower(b.city) like lower(concat('%', :query, '%'))")
    List<Bien> searchBiensByDescOrCity(@Param("query") String query, Pageable pageable);


    @Query("""
            select b from Bien b 
            inner join Client c on b.client.id = c.id 
            where c.id = ?1
            """)
    List<Bien> findBiensByFirstOwner(Long id);

    @Query(nativeQuery = true,
            value = """
                    select * from gi_bien b
                    inner join gi_client_biens cb on b.id = cb.biens_id
                    where cb.client_id =:clientId
                    """)
    List<Bien> findBiensOwnedByClient(@Param("clientId") Long id);
}
