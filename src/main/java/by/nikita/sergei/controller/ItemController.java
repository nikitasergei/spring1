package by.nikita.sergei.controller;

import by.nikita.sergei.entity.Items;
import by.nikita.sergei.entity.Type;
import by.nikita.sergei.entity.User;
import by.nikita.sergei.entity.WishList;
import by.nikita.sergei.service.ItemService;
import by.nikita.sergei.service.UserService;
import by.nikita.sergei.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class ItemController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    WishListService wishListService;

    @GetMapping("items")
    public String getItems(Model model,
                           @RequestParam(required = false, defaultValue = "") String filter,
                           @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Items> page;
        if (filter != null && !filter.isEmpty())
            page = itemService.getByFilter(filter, pageable);
        else page = itemService.getAll(pageable);
        model.addAttribute("filter", filter);
        model.addAttribute("page", page);
        model.addAttribute("url", "/items");
        return "items";
    }

    @GetMapping("/items/{kind}")
    private String getAllSneakers(Model model,
                                  @PathVariable String kind,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Type type = null;
        System.out.println(kind);
        switch (kind) {
            case ("sneakers"): {
                type = Type.SNEAKERS;
                break;
            }
            case ("balls"): {
                type = Type.BALLS;
                break;
            }
            case ("equipments"): {
                type = Type.EQUIPMENTS;
                break;
            }
            default: {
                break;
            }
        }
        Page<Items> page;
        if (type != null) {
            page = itemService.getAllByType(type, pageable);
        } else {
            page = itemService.getAll(pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("url", "/items");
        return "items";
    }

    @GetMapping("addItem")
    public String addItem(Model model) {
        model.addAttribute("typeSet", Type.values());
        return "addItem";
    }

    @PostMapping("addItem")
    public String addItemToShop(@Valid Items items,
                                BindingResult bindingResult,
                                Model model,
                                @AuthenticationPrincipal User user,
                                @RequestParam("file") MultipartFile file,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) throws IOException {
        String resultFilename;
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            resultFilename = itemService.getString(file, uploadPath);
            items.setPhotoAddress(resultFilename);
            user.getWishList().getItems().add(items);
            Page<Items> page = itemService.getAll(pageable);
            model.addAttribute("url", "/items");
            model.addAttribute("page", page);
            if (bindingResult.hasErrors()) {
                model.addAttribute("page", page);
                Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
                model.mergeAttributes(errorsMap);
                model.addAttribute("oneItem", items);
                return "addItem";
            } else {
                if (itemService.saveItem(items)) {
                    return "redirect:items";
                } else {
                    model.addAttribute("page", page);
                    model.addAttribute("savingReport", "Эта модель товара уже есть в базе");
                    model.addAttribute("oneItem", items);
                    return "addItem";
                }
            }
        }
        return "items";
    }

    @GetMapping("/itemInfo/{id}/{action}")
    public String isAdded(Model model,
                          @AuthenticationPrincipal User user,
                          @PathVariable String action,
                          @PathVariable String id) {
        Long itemId = Long.parseLong(id);
        Items itemsFromDB = itemService.getItemById(itemId);
        switch (action) {
            case ("add"): {
                user.getWishList().getItems().add(itemsFromDB);
                itemsFromDB.getWishLists().add(user.getWishList());
                userService.updateUserWishList(user);
                itemService.addItemToWishList(itemsFromDB);
                model.addAttribute("messageType", "success");
                model.addAttribute("message", "Item was successfully added");
                break;
            }
            case ("delete"): {
                user.getWishList().getItems().remove(itemsFromDB);
                itemsFromDB.getWishLists().remove(user.getWishList());
                userService.updateUserWishList(user);
                itemService.addItemToWishList(itemsFromDB);
                wishListService.saveWishList(user.getWishList());
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Item was successfully deleted");
                break;
            }
            default: {
                break;
            }
        }
        return "isAdded";
    }


    @GetMapping("itemInfo/{id}")
    public String getMoreInfo(Model model,
                              @PathVariable Long id,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Items item = itemService.getItemById(id);
        if (item != null) {
            model.addAttribute("item", item);
            return "itemInfo";
        } else {
            Page<Items> page = itemService.getAll(pageable);
            model.addAttribute("url", "/items");
            model.addAttribute("page", page);
            return "items";
        }
    }

    @GetMapping("user/wishList")
    public String getUserWishList(Model model,
                                  @AuthenticationPrincipal User user,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Items> page = itemService.getWishListsItems(user.getWishList(), pageable);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("page", page);
        model.addAttribute("url", "wishList");
        return "wishList";
    }

//    @GetMapping("user/wishList/addPayment/{id}")
//    public String addToPayment(Model model,
//                               @PathVariable String id,
//                               @AuthenticationPrincipal User user,
//                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
//        Long itemId = Long.parseLong(id);
//        Items itemFromDB = itemService.getItemById(itemId);
//        model.addAttribute("payment", itemFromDB.getPrice())
//        model.addAttribute("username", user.getUsername());
//        Page<Items> page = itemService.getWishListsItems(user.getWishList(), pageable);
//        model.addAttribute("page", page);
//        model.addAttribute("url", "wishList");
//        return "wishList";
//    }
}
