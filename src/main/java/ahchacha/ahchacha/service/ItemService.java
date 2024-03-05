package ahchacha.ahchacha.service;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.dto.ItemDto;
import ahchacha.ahchacha.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(ItemDto.ItemRequestDto itemDto) {
        Item item = Item.builder()
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
