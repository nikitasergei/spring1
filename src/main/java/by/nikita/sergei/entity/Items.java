package by.nikita.sergei.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity

public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String photoAddress;

    @NotBlank(message = "Заполни поле")
    private String model;

    private Double size;

    @NotNull
    private Double price;

    private boolean isDeleted = false;

    @NotNull
    private Integer quantity;

    @NotEmpty(message = "Choose condition of your item")
    private String condition;

    @NotEmpty(message = "Напишите информацию о своем товаре")
    private String notes;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<WishList> wishLists;

}
