package ahchacha.ahchacha.service;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.User;
import ahchacha.ahchacha.dto.ItemDto;
import ahchacha.ahchacha.repository.ItemRepository;
import ahchacha.ahchacha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
