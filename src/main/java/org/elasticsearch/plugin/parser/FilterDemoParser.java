/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.plugin.parser;

import org.apache.lucene.search.Filter;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.index.query.QueryParser;
import org.elasticsearch.index.query.QueryParseContext;
import org.elasticsearch.index.query.QueryParsingException;

import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("deprecation")
public class FilterDemoParser implements QueryParser {

    //private final SearchBiz4Service biz4Service;
    
    public static final String NAME = "demofilter";

    //@Inject
    //public Biz4FilterParser(SearchBiz4Service biz4Service){
    //    this.biz4Service = biz4Service;
    //}
    
    @Override
    public String[] names() {
        return new String[]{ NAME };
    }

    @Override
    public Filter parse(QueryParseContext parseContext) throws IOException, QueryParsingException {
        XContentParser.Token token;
        String name=null;
        String value=null;
        
        HashMap mp=new HashMap<String,String>(10);
        
        while ((token = parseContext.parser().nextToken()) != XContentParser.Token.END_OBJECT) {
            
        	if (token==XContentParser.Token.FIELD_NAME)
        		continue;
        	
        	name=parseContext.parser().currentName();
        	value=parseContext.parser().text();
            mp.put(name, value);
            
            
        }
        
        return new DemoFilter(mp);
    }
}
