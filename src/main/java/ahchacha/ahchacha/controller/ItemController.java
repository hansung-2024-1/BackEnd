package ahchacha.ahchacha.controller;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.common.enums.Category;
import ahchacha.ahchacha.dto.ItemDto;
import ahchacha.ahchacha.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "아이템 등록")
    @PostMapping
    public ResponseEntity<ItemDto.ItemResponseDto> create(@RequestBody ItemDto.ItemRequestDto itemRequestDto) {
        Item item = itemService.save(itemRequestDto.getUserId(), itemRequestDto);
        ItemDto.ItemResponseDto itemResponseDto = ItemDto.ItemResponseDto.toDto(item);
        return new ResponseEntity<>(itemResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "등록된 아이템 최신순 조회")
    @GetMapping("/latest")
    public ResponseEntity<Page<ItemDto.ItemResponseDto>> getAllItems(@RequestParam(value = "page", defaultValue = "1")int page) {
        Page<ItemDto.ItemResponseDto> itemsDtoPage = itemService.getAllItems(page);
        return new ResponseEntity<>(itemsDtoPage, HttpStatus.OK);
    }

    @Operation(summary = "등록된 아이템 예약가능 순 조회", description = "YES / NO 순 정렬")
    @GetMapping("/reservation")
    public ResponseEntity<Page<ItemDto.ItemResponseDto>> getAllItemsByReservation(@RequestParam(value = "page", defaultValue = "1")int page) {
        Page<ItemDto.ItemResponseDto> itemsDtoPage = itemService.getAllItemsByReservation(page);
        return new ResponseEntity<>(itemsDtoPage, HttpStatus.OK);
    }

    @Operation(summary = "등록된 아이템 학교 or 개인 조회", description = "OFFICIAL / PERSON 순 정렬")
    @GetMapping("/personOrOfiicial")
    public ResponseEntity<Page<ItemDto.ItemResponseDto>> getAllItemsByPersonOrOfficial(@RequestParam(value = "page", defaultValue = "1")int page) {
        Page<ItemDto.ItemResponseDto> itemsDtoPage = itemService.getAllItemsByPersonOrOfficial(page);
        return new ResponseEntity<>(itemsDtoPage, HttpStatus.OK);
    }


    @Operation(summary = "아이템 검색", description = "제목으로 검색")
    @GetMapping("/search-title")
    public ResponseEntity<Page<ItemDto.ItemResponseDto>> searchItemByTitle(@RequestParam(value = "title") String title,
                                                                     @RequestParam(value = "page", defaultValue = "1") int page) {

        Page<ItemDto.ItemResponseDto> itemPages = itemService.searchItemByTitle(title, page);
        return ResponseEntity.ok(itemPages);
    }

    @Operation(summary = "아이템 검색", description = "카테고리로 검색")
    @GetMapping("/search-category")
    public ResponseEntity<Page<ItemDto.ItemResponseDto>> searchItemByCategory(@RequestParam(value = "category") String category,
                                                                           @RequestParam(value = "page", defaultValue = "1") int page) {

        Page<ItemDto.ItemResponseDto> itemPages = itemService.searchItemByCategory(category, page);
        return ResponseEntity.ok(itemPages);
    }
}
