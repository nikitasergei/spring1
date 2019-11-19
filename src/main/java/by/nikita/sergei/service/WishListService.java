package by.nikita.sergei.service;

import by.nikita.sergei.entity.Items;
import by.nikita.sergei.entity.WishList;
import by.nikita.sergei.repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    @Autowired
    WishListRepo wishListRepo;

    public void saveWishList(WishList wishList) {
        wishListRepo.save(wishList);
    }

    public void save(WishList wishList) {
        wishListRepo.save(wishList);
    }
}
