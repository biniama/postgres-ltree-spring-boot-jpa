package com.bonial.tmapipoc;

import lombok.Data;
import org.hibernate.annotations.Type;

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

    @Column(name = "path", nullable = false, columnDefinition = "ltree")
    @Type(type = "com.bonial.tmapipoc.LTreeType")
    private String path;
}