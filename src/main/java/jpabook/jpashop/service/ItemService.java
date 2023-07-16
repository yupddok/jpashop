package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId,  String name, int price, int stockQuantity) {
        // id를 기반으로 실제 영속상태 엔티티찾아옴
        Item findItem = itemRepository.findOne(itemId);

        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

//        return findItem;

        // @Transactional -> transaction commit -> flush (영속성 컨텍스트 엔티티 중 변경 감지) -> 쿼리실행
        
//        itemRepository.save(findItem);  // 호출할 필요없음

        // 1. 변경감지기능 정리
        // 영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정하는 방법
        // 트랜잭션 안에서 엔티티를 다시 조회, 변경할 값 선택
        // 트랜잭션 커밋 시점에 변경 감지 기능 동작해 데이터 베이스에 update sql 실행

        // 2. 병합 정리
        // 준영송상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능
        // em.merge();
        // 파라미터로 넘어온 준영속 엔티티의 식별자 값으로 1차 캐시에서 엔티티를 조회한다.
        // 만약 1차 캐시에 엔티티가 없으면 데이터베이스에서 엔티티를 조회하고, 1차 캐시에 저장한다.
        // 조회한 영속 엔티티(mergeMember)에 member 엔티티 값을 채워 넣는다.
        // 영속상태인 mergeMember 반환
        // 주의 : 변경감지기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합 사용시 모든 속성이 변경
        //       병합시 값이 없으면 null로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체)

        // merge 쓰지 말자
        // 엔티티를 변경할 때는 항상 변경 감지를 사용하세요.


        // set 남발 X. 의미있는 메소드 사용하는거 권장
        // ex.
//        findItem.change(price, name);
//        findItem.addStock(price, name);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
