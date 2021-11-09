package com.example.gos.repos;

import com.example.gos.domain.Buildings;
import com.example.gos.domain.User;
import com.example.gos.domain.dto.BuildingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepo extends CrudRepository<Buildings, Long> {

    @Query("select new com.example.sweater.domain.dto.BuildingDto(" +
            "   b, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Buildings b left join b.likes ml " +
            "group by b")
    Page<BuildingDto> findAll(Pageable pageable, @Param("user") User user);

    @Query("select new com.example.sweater.domain.dto.BuildingDto(" +
            "   b, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Buildings b left join b.likes ml " +
            "where b.name = :name " +
            "group by b")
    Page<BuildingDto> findByName(@Param("name") String name, Pageable pageable, @Param("user") User user);

    @Query("select new com.example.sweater.domain.dto.BuildingDto(" +
            "   b, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Buildings b left join b.likes ml " +
            "where b.author = :author " +
            "group by b")
    static Page<BuildingDto> findByUser(Pageable pageable, @Param("author") User author, @Param("user") User user);
}
