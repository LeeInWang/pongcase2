package com.pongcase.repository;

import com.pongcase.constant.ItemSellStatus;
import com.pongcase.dto.ItemSearchDto;
import com.pongcase.dto.MainItemDto;
import com.pongcase.dto.QMainItemDto;
import com.pongcase.entity.Item;
import com.pongcase.entity.QItem;
import com.pongcase.entity.QItemImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;


//인터페이스를 구현
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    //#
    //상품의 판매 상태 조건이 전체(null) 일 경우 null을 리턴, 결과값이 null이면 where절에서
    //해당 조건은 무시됨
    //상품 판매 상태 조건이 null이 아니면 판매중 or 품절 상태라면 해당 조건의 상품만 조회
    //import com.querydsl.core.types.dsl.BooleanExpression;
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return  searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    //#
    //searchDateType 의 값에 의해 dateTime의 값을 이전 시간의 값으로 세팅 후 해당 시간 이후로 등록된
    //상품만 조회
    private  BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    //#
    //searchBy 의 값에 따라서 상품명에 검색어를 포함하고 있는 상품 또는 상품 생성자의 아이디에
    // 검색어를 포함하고 있는 상품을 조회하도록 조건값을 반환
    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemName", searchBy)){
            return QItem.item.itemName.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }




    /*
           -- Predicate : '이 조건이 맞다 '고 판단하는 근거를 함수로 제공
           --  Predicate를 파라미터로 전달하기 위해서는 QueryDslPredicateExecutor  인터페이스를
           -- 상속받아야 함

        < QueryDslPredicateExecutor  인터페이스 정의 메소드 >
        long count(Predicate) : 조건에 맞는 데이터의 총 개수 반환
        boolean exists(Predicate) : 조건에 맞는 데이터 존재 여부 반환
        Iterable findAll(Predicate) : 조건에 맞는 모든 데이터 반환
        Page<T> findAll(Predicate, Pageable) : 조건에 맞는 페이지 데이터 반환
        Iterable findAll(Predicate, Sort) : 조건에 맞는 정렬된 데이터 반환
        T findOne(Predicate) : 조건에 맞는 데이터 1개 반환

     */
    
    
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        List<Item> content = queryFactory   //쿼리 팩토리 이용하여 쿼리를 생성
                .selectFrom(QItem.item)  // 상품 데이터를 조회하기 위해서 QItem의 item을 지정
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),  //where절 콤마(,) - and 조건
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())  // 데이터를 가지고 올 시작 인덱스를 지정
                .limit(pageable.getPageSize())  // 한 번에 가져올 최대 개수 지정
                .fetch();   //조회대상 리스트 반환

        //쿼리를 count 함수로 초기화, 쿼리 결과로 반환되는 항목수 세기
        long total = queryFactory.select(Wildcard.count).from(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),  //등록일 기준으로 필터링
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()), //판매 상태를 기준으로
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())) // like연산자로 필터
                .fetchOne() //쿼리 실행 결과를 하나만 가져오기(지정된 조건과 일치하는 항목의 총 수를 반환)
                ;

        //페이지화된 데이터
        //content : 실제 데이터 목록
        //pageable : 페이지 설정(페이지 크기, 정렬 등....)
        //total : 전체 데이터의 수
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression itemNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemName.like("%" + searchQuery + "%");
                //import org.thymeleaf.util.StringUtils;

    }


    //#검색어
    // 검색어가 null이 아니면 상품명에 해당 검색어가 포횜되는 상품을 조회하는 조건을 반환
    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        List<MainItemDto> content = queryFactory
                .select(
                        new QMainItemDto(   //QMainItemDto의 생성자에 반환할 값들을 넣어 줌
                                item.id,
                                item.itemName,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                )
                .from(itemImg) //
                .join(itemImg.item, item) // itemImg와 item을 내부 조인
                .where(itemImg.repImgYn.eq("Y")) // 상품 이미지의 경우 대표 상품만 불러오게
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
    }







}



