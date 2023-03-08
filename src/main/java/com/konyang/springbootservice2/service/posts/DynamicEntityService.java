package com.konyang.springbootservice2.service;

import com.konyang.springbootservice2.domain.posts.DynamicEntity;
import com.konyang.springbootservice2.domain.posts.DynamicEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DynamicEntityService {

    private final DynamicEntityRepository dynamicEntityRepository;

    @Autowired
    public DynamicEntityService(DynamicEntityRepository dynamicEntityRepository) {
        this.dynamicEntityRepository = dynamicEntityRepository;
    }

    public void createDynamicEntityFromHtmlFile(String htmlFilePath) {
        // Parse the HTML file and extract the input tags and their values
        Map<String, String> dynamicFields = new HashMap<>();
        dynamicFields.put("input1", "value1");
        dynamicFields.put("input2", "value2");
        // ...
        dynamicFields.put("inputN", "valueN");

        // Create a new DynamicEntity with the dynamic fields
        DynamicEntity dynamicEntity = new DynamicEntity();
        dynamicEntity.setDynamicFields(dynamicFields);

        // Save the entity to the database
        dynamicEntityRepository.save(dynamicEntity);
    }
}
