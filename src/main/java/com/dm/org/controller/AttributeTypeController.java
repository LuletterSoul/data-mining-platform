package com.dm.org.controller;

import com.dm.org.model.AttributeType;
import com.dm.org.service.AttributeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@RestController
@RequestMapping(value = "/attributeTypes")
public class AttributeTypeController {
    private AttributeTypeService attributeTypeService;

    @Autowired
    public void setAttributeTypeService(AttributeTypeService attributeTypeService) {
        this.attributeTypeService = attributeTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AttributeType> fetchOptionalsAttributeTypes() {
        return attributeTypeService.findAll();
    }
}
