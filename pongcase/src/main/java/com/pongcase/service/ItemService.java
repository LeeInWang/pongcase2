package com.pongcase.service;

import com.pongcase.dto.ItemFormDto;
import com.pongcase.dto.ItemImgDto;
import com.pongcase.dto.ItemSearchDto;
import com.pongcase.dto.MainItemDto;
import com.pongcase.entity.Item;
import com.pongcase.entity.ItemImg;
import com.pongcase.repository.ItemImgRepository;
import com.pongcase.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 등록
        //상품 등록 폼으로부터 입력 받은 데이터를 이용해서 item 객체를 생성
        Item item = itemFormDto.createItem();

        itemRepository.save(item); //상품 데이터를 저장

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            //만약 첫번째 이미지일 경우는 대표 상품이미지로 처리하고 나머지는 상품 이미지로 처리
            // 대표 상품 이미지는 'Y',  나머지 상품 이미지는 'N'로 설정
            if (i == 0) {
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }

            //상품 이미지 정보를 저장
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    //4월 11일-- 상품의 세부 정보를 조회하고 해당 상품의 이미지들을 함께 반환
// 상품 데이터를 읽어오는 트랜지션을 읽기 전용으로 설정하여 메소드가 데이터베이스를 변경하지 않게
    //jpa가 변경감지를 수행하지 않아서 성능을 향상 시킴
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){

        //해당 상품 이미지 조회
        //이미지 등록 순으로 가져오기 위해 상품 이미지를 상품 아이디 오름차순으로 처리
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        //조회한 ItemImg 엔티티를 ItemImgDto 객체로 만들어서 리스트에 추가
        for (ItemImg itemImg : itemImgList) {

            //조회된 각 이미지에 대해 ItmeImgDto.of(itemImg)를 후출한 후 결과로
            //ItemImgDto 객체를 생성
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
            //상품 아이디를 통해서 상품 엔티티를 조회
            //상품이 존재하지 않을 경우 EntityNotFoundException을 발생 시킴
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
            //람다식 => 클래스이름(또는 배열타입이름)::new   => 생성자를 참조
            //            클래스객체::인스턴스메소드이름

            ItemFormDto itemFormDto = ItemFormDto.of(item);
            itemFormDto.setItemImgDtoList(itemImgDtoList);
            return itemFormDto;
        }

    //상품 수정
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록 화면으로부터 전달 받은 상품 아이디를 이용해서 상품 엔티티를 조회
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        //상품 등록 화면으로부터 전달 받은 ItemFormDto를 통해 상품 엔티티를 업데이트
        item.updateItem(itemFormDto);

        //상품 이미지 아이디 리스트 조회
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){

            //상품 이미지를 업데이트하기 위해서 updateItemImg()메소드에 상품이미지와
            //상품 이미지 파일 정보를 파라미터로 전달
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();

    }

    //----------- 페이징 처리할 부분
    @Transactional(readOnly = true)   // 상품 조회 조건과 페이지 정보를 파라미터로 받아서 상품 데이터를
      // 조회,   데이터의 수정이 일어나지 않으므로 최적화를 위해  @Transactional(readOnly = true)을 설정
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

}
