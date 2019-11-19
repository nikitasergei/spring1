package by.nikita.sergei.repository;

import by.nikita.sergei.entity.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface WishListRepo extends CrudRepository<WishList, Long> {

    Page<WishList> findAll (Pageable pageable);



}
