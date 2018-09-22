package com.example.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

@RestController
@RequestMapping("/sdgfg")
public class SearchController {

    private static Logger logger = LoggerFactory.getLogger(SearchController.class);

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping(value = "/search")
    public void search() {
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withSearchType(SearchType.QUERY_THEN_FETCH).withIndices("es_item_sku").withTypes("item_info")
                .withQuery(boolQueryBuilder);
        Object queryData = elasticsearchTemplate.queryForList(searchQueryBuilder.build(), JsonNode.class);

        try {
            logger.debug("result:{}", mapper.writeValueAsString(queryData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
