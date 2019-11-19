package by.nikita.sergei.service;

import by.nikita.sergei.entity.Items;
import by.nikita.sergei.entity.Type;
import by.nikita.sergei.entity.WishList;
import by.nikita.sergei.repository.ItemsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    ItemsRepo itemsRepo;

    public Page<Items> getAll(Pageable pageable) {
        return itemsRepo.findAll(pageable);
    }

    public Page<Items> getAllByType(Type type, Pageable pageable) {
        return itemsRepo.findAllByType(type, pageable);
    }

    public boolean saveItem(Items items) {
        List list = itemsRepo.findByType(items.getType());
        if (list.contains(itemsRepo.findByModel(items.getModel())))
            return false;
        else {
            itemsRepo.save(items);
            return true;
        }
    }

    public void addItemToWishList(Items item) {
        itemsRepo.save(item);
    }

    public void deleteItemFromWishList (Items item){
        itemsRepo.delete(item);
    }

    public Page<Items> getByFilter(String filter, Pageable pageable) {
        return itemsRepo.findByModel(filter, pageable);
    }

    public Items getItemById(Long id) {
        Optional<Items> item = itemsRepo.findById(id);
        return item.orElse(null);
    }

    public Page<Items> getWishListsItems(WishList wishList, Pageable pageable) {
        return itemsRepo.findByWishLists(wishList, pageable);
    }

    public String getString(MultipartFile file, String uploadPath) throws IOException {
        String resultFilename;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        resultFilename = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));
        return resultFilename;
    }
}
