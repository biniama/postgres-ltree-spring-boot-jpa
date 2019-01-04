package com.bonial.tmapipoc;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "public", name="tree")
@Data
public class Tree {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "letter")
    private String letter;

    @Column(name = "path", columnDefinition = "ltree")
    private String path;
}