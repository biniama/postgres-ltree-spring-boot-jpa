package com.bonial.tmapipoc;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(schema = "public", name="facts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@TypeDefs({
//        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
//        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
//        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
//        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
//        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
public class Facts {

    @Id
    @Column(name = "path", nullable = false, columnDefinition = "ltree")
    private String path;

    /*@Column(name = "visible")
    private Boolean visible;*/

    @Column(name = "boost")
    private Integer boost;

    //@Column(name = "rules", columnDefinition = "jsonb[]")
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Rule> rules;
}

@Builder
@Data
class Rule implements Serializable {

    private String placement;

    private Boolean visible;

    private Integer boost;
}