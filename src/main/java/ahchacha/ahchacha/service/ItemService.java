package ahchacha.ahchacha.service;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.User;
import ahchacha.ahchacha.domain.common.enums.Category;
import ahchacha.ahchacha.dto.ItemDto;
import ahchacha.ahchacha.repository.ItemRepository;
import ahchacha.ahchacha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Item save(Long userId, ItemDto.ItemRequestDto itemDto) {
        User user = userRepository.findById(userId) //학번
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        Item item = Item.builder()
                .user(user)
                .title(itemDto.getTitle())
                .firstPrice(itemDto.getFirstPrice())
                .price(itemDto.getPrice())
                .canBorrowDateTime(itemDto.getCanBorrowDateTime())
                .returnDateTime(itemDto.getReturnDateTime())
                .borrowPlace(itemDto.getBorrowPlace())
                .returnPlace(itemDto.getReturnPlace())
                .personOrOfficial(itemDto.getPersonOrOfficial())
                .reservation(itemDto.getReservation())
                .imageUrls(itemDto.getImageUrls())
                .category(itemDto.getCategory())
                .build();
        return itemRepository.save(item);
    }

    public Page<ItemDto.ItemResponseDto> getAllItems(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt")); //최근 작성순

        Pageable pageable = PageRequest.of(page-1, 6, Sort.by(sorts)); //한페이지에 6개
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return ItemDto.toDtoPage(itemPage);
    }

    public Page<ItemDto.ItemResponseDto> getAllItemsByReservation(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("reservation")); //예약가능 여부 정렬 asc : 영문 Y가 N보다 뒤에있어서 오름차순
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page-1, 6, Sort.by(sorts));
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return ItemDto.toDtoPage(itemPage);
    }

    public Page<ItemDto.ItemResponseDto> getAllItemsByPersonOrOfficial(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("personOrOfficial")); // 예약가능여부와 같은 논리
        sorts.add(Sort.Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(page-1, 6, Sort.by(sorts));
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return ItemDto.toDtoPage(itemPage);
    }

    public Page<ItemDto.ItemResponseDto> searchItem(String title, Category category,int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page - 1, 6, Sort.by(sorts));

        Page<Item> itemPage;

        if(title == null){
            itemPage = itemRepository.findByCategoryContaining(category, pageable);
        }
        else if(category == null) {
            itemPage = itemRepository.findByTitleContaining(title, pageable);
        }
        else {
            itemPage = itemRepository.findByTitleContainingOrCategory(title, category, pageable);
        }

        return ItemDto.toDtoPage(itemPage);
    }
}
