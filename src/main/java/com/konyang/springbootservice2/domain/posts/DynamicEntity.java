package com.konyang.springbootservice2.domain.posts;

@Entity
public class DynamicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> dynamicFields;
}
// getters and setters