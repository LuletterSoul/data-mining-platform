package com.vero.dm.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.model.MiningGrammar;
import com.vero.dm.service.GrammarService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiOperation;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:32 2018/7/24.
 * @since data-mining-platform
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION
                        + ResourcePath.GRAMMAR_PATH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GrammarController
{

    private GrammarService grammarService;

    @Autowired
    public void setAlgorithmService(GrammarService grammarService)
    {
        this.grammarService = grammarService;
    }

    @ApiOperation("获取挖掘语言名称")
    @GetMapping
    public ResponseEntity<List<MiningGrammar>> get()
    {
        return new ResponseEntity<>(grammarService.findAll(), HttpStatus.OK);
    }
}
