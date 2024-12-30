package jpabook.japshop.domain;

import jakarta.persistence.*;
import jpabook.japshop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
    joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id"))
    //중간 테이블을 맵핑 해줘야한다.
    //joincolumn은 중가테이블에 있는 카테고리 id
    //inversejoincolumns= category_item 이 테이블의 item쪽에 들어가는걸 맵핑
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @ManyToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
