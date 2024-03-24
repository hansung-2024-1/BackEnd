package ahchacha.ahchacha.service;

import ahchacha.ahchacha.aws.AmazonS3Manager;
import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.User;
import ahchacha.ahchacha.domain.Uuid;
import ahchacha.ahchacha.domain.common.enums.Category;
import ahchacha.ahchacha.dto.ItemDto;
import ahchacha.ahchacha.repository.ItemRepository;
import ahchacha.ahchacha.repository.UserRepository;
import ahchacha.ahchacha.repository.UuidRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UuidRepository uuidRepository;
    private final AmazonS3Manager s3Manager;

    @Transactional
    public ItemDto.ItemResponseDto createItem(ItemDto.ItemRequestDto itemDto,
                                        List<MultipartFile> files,
                                        HttpSession session) {
        User user = (User) session.getAttribute("user");

        //이미지 업로드
        List<String> pictureUrls = new ArrayList<>(); // 이미지 URL들을 저장할 리스트
        if (files != null && !files.isEmpty()){
            for (MultipartFile file : files) {
                String uuid = UUID.randomUUID().toString();
                Uuid savedUuid = uuidRepository.save(Uuid.builder()
                        .uuid(uuid).build());
                String pictureUrl = s3Manager.uploadFile(s3Manager.generateItemKeyName(savedUuid), file);
                pictureUrls.add(pictureUrl); // 리스트에 이미지 URL 추가

                System.out.println("s3 url(클릭 시 브라우저에 사진 뜨는지 확인): " + pictureUrl);
            }
        }

        Item item = Item.builder()
                .user(user)
                .title(itemDto.getTitle())
                .pricePerHour(itemDto.getPricePerHour())
                .firstPrice(itemDto.getFirstPrice())
                .canBorrowDateTime(itemDto.getCanBorrowDateTime())
                .returnDateTime(itemDto.getReturnDateTime())
                .borrowPlace(itemDto.getBorrowPlace())
                .returnPlace(itemDto.getReturnPlace())
                .reservation(itemDto.getReservation())
                .imageUrls(pictureUrls)
                .category(itemDto.getCategory())
                .personOrOfficial(user.getPersonOrOfficial())
                .build();

        item.setFirstPrice(itemDto.getPricePerHour());

        Item createdItem = itemRepository.save(item);
        return ItemDto.ItemResponseDto.toDto(createdItem);
    }

    public Optional<ItemDto.ItemResponseDto> getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();

            item.setViewCount(item.getViewCount()+1);
            itemRepository.save(item);
        }

        return optionalItem.map(ItemDto.ItemResponseDto::toDto);
    }

    public Page<ItemDto.ItemResponseDto> getAllItems(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt")); //최근 작성순

        Pageable pageable = PageRequest.of(page-1, 6, Sort.by(sorts)); //한페이지에 6개
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return ItemDto.toDtoPage(itemPage);
    }

    public Page<ItemDto.ItemResponseDto> getAllItemsByViewCount(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("viewCount"));
        sorts.add(Sort.Order.desc("createdAt"));  // 조회수가 동일하면 최신순으로 정렬

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

    public Page<ItemDto.ItemResponseDto> searchItemByTitle(String title,int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page - 1, 6, Sort.by(sorts));

        Page<Item> itemPage;

        itemPage = itemRepository.findByTitleContaining(title, pageable);

        return ItemDto.toDtoPage(itemPage);
    }

    public Page<ItemDto.ItemResponseDto> searchItemByCategory(String categoryStr, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page - 1, 6, Sort.by(sorts));

        // String to Category enum
        Category category = Category.valueOf(categoryStr.toUpperCase());

        Page<Item> itemPage = itemRepository.findByCategory(category, pageable);

        return ItemDto.toDtoPage(itemPage);
    }

    public List<ItemDto.CategoryCountDto> getTopCategoriesByViewCount(int count) {
        List<Object[]> categoryCounts = itemRepository.findTopCategoriesByViewCount(PageRequest.of(0, count));

        List<ItemDto.CategoryCountDto> categoryCountDtos = new ArrayList<>();
        for (Object[] result : categoryCounts) {
            Category category = (Category) result[0];
            Long viewCount = (Long) result[1];
            categoryCountDtos.add(new ItemDto.CategoryCountDto(category, viewCount.intValue())); // viewCount를 int로 변환하여 저장
        }

        return categoryCountDtos;
    }

    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new IllegalArgumentException("Invalid item Id: " + itemId);
        }
        itemRepository.deleteById(itemId);
    }
}
