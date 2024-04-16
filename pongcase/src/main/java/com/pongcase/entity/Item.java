package com.pongcase.entity;

import com.pongcase.constant.ItemSellStatus;
import com.pongcase.dto.ItemFormDto;
import com.pongcase.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity   //이 클래스를 데이터베이스 테이블에 대응되는 엔티티로 선언, jpa가 관리
@Table(name = "item")  //Entity와 매핑할 테이블을 지정,  item 테이블과 매핑하겠다.
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
    @Id    //기본키 설정, entity로 선언한 클래스는 반드시 기본키가 있어야 함
    @Column(name="item_id")  //매핑되는 컬럼명, Item클래스 id와 item 테이블의 item_id 매핑
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 설정전략(생성방법)
                    // jpa구현체(hibernate)가 자동으로 생성 전략 결정(자동으로 생성)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)  //db가 기본키를 생성
   // @GeneratedValue(strategy = GenerationType.SEQUENCE)  //데이터베이스 시퀀스 오브젝트를
                            // 이용한 기본키 생성
   // @GeneratedValue(strategy = GenerationType.TABLE) // 키 생성용 테이블 사용
    private Long id;             //상품코드

    @Column(nullable = false, length = 50, name = "item_name")  //not null, null값 허용하지 않음, 반드시 입력 받기
                            //char(50), varchar(50) - length, 입력 필수
                            // length 를 사용하지 않으면 기본값으로 255까지 가능
    //@Column(nullable = true)   //null값을 허용, 입력은 선택
    private String itemName;      //상품명

    @Column(name="price", nullable = false)  //입력 필수
    private int price;          //가격

    @Column(nullable = false)   //입력 필수
     private int stockNumber;    //재고수량

    @Lob   // text, 이미지, 비디오,... 용량이 큰 데이터를 저장
           // db의 BLOB, CLOB에 대응
    @Column(nullable = false)   //입력 필수
    private String itemDetail;  //상품상세설명

    @Enumerated(EnumType.STRING)  //enum 타입 매핑
    private ItemSellStatus itemSellStatus;  //상품판매상태


//    private LocalDateTime regTime;  //등록시간
//
//
//    private LocalDateTime updateTime;  //수정시간

   //--------------------------------------------
   public void updateItem(ItemFormDto itemFormDto){
       this.itemName = itemFormDto.getItemName();
       this.price = itemFormDto.getPrice();
       this.stockNumber = itemFormDto.getStockNumber();
       this.itemDetail = itemFormDto.getItemDetail();
       this.itemSellStatus = itemFormDto.getItemSellStatus();
   }

   //------- 재고수량 체크 : 수량이 부족할 때 OutofStockException 발생


    //주문기능 처리 : 재고에서 주문 수량 만큼을 감소시키는 작업, 재고가 없다면 품절로 표시
    //                  많으면 주문 안되도록 처리


    // 재고 수량 감소
   public void removeStock(int stockNumber){
       int restStock = this.stockNumber - stockNumber; // 현재 재고 - 주문 수량
       if(restStock<0){ // 재고가 부족할 때(상품의 주문 수량보다 재고가 더 작을 때 예외 발생)
           throw new OutOfStockException("상품의 재고가 부족 (현재 재고 수량: " + this.stockNumber + ")");
       }
       this.stockNumber = restStock; // 주문 후 남은 수량을 상품의 현재 재고로 처리
   }

   //재고수량 추가
    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }


















}

