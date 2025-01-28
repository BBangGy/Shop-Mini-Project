package jpabook.japshop.service;

import jpabook.japshop.domain.item.Item;
import jpabook.japshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    //상품 서비스는 그냥 단순하게 repository에서 위임만 하는 클래스다.
    private final ItemRepository itemRepository;
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    public List<Item> findItem(){
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
