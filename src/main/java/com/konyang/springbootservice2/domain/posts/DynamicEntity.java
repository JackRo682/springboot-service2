package com.konyang.springbootservice2.domain.posts;

import javax.persistence.*;
import java.util.Map;

@Entity
public class DynamicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> dynamicFields;

    public void setDynamicFields(Map<String, String> dynamicFields) {
        this.dynamicFields = dynamicFields;
    }
}
// getters and setters