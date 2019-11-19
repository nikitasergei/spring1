package by.nikita.sergei.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@Entity
public class WishList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "wishLists", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Items> items;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
