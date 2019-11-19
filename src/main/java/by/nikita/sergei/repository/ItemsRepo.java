package by.nikita.sergei.repository;

import by.nikita.sergei.entity.Items;
import by.nikita.sergei.entity.Type;
import by.nikita.sergei.entity.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemsRepo extends CrudRepository<Items, Long> {
    Items findByModel(String model);

    Optional<Items> findById(Long id);

    List<Items> findByType(Type type);

    Page<Items> findAll(Pageable pageable);

    Page<Items> findAllByType(Type type, Pageable pageable);

    Page<Items> findByWishLists(WishList wishList, Pageable pageable);

    Page<Items> findByModel(String model, Pageable pageable);

}
