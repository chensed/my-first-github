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

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.FixedBitSet;
import org.apache.lucene.document.Document;

import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;


import java.io.IOException;
import java.util.*;


public class DemoFilter extends Filter {
    
    ESLogger logger = Loggers.getLogger(DemoFilter.class);
    
    ESLogger dlogger = Loggers.getLogger("debugLog");
    
        
    private HashMap paramMap=null;
    
    public DemoFilter(HashMap map){
    	paramMap =map;
    }
    
    private Map<Term,Set<String>> searchedTerms = new HashMap<Term,Set<String>>();

    @SuppressWarnings("deprecation")
	@Override
    public DocIdSet getDocIdSet(LeafReaderContext context, Bits acceptDocs) throws IOException {
        
    	long startTime = System.currentTimeMillis();
        int max = context.reader().maxDoc();
        
        OpenBitSet  bits = new OpenBitSet(max);
        
        //OpenBitSet filterBits = new OpenBitSet(max);
        //DocIdSet it;
            
        DocsEnum termDocs =null;
        
        String name=null,val=null;    
        //int ct=context.reader().docFreq(new Term("title","testtitle1"));
        
        if (paramMap.size()==1){ 
            
          Iterator iter = paramMap.entrySet().iterator();
          if (iter.hasNext()) {
               Map.Entry entry = (Map.Entry) iter.next();
               name = (String)entry.getKey();
               val = (String)entry.getValue();
          }
            
           termDocs =context.reader().termDocsEnum(new Term(name,val));
        }
        
        if(termDocs == null){
             return null;
        }
            
        while(termDocs.nextDoc() != DocsEnum.NO_MORE_DOCS){
           bits.set(termDocs.docID());
        }

                       
        //}
        long endTime = System.currentTimeMillis();
        dlogger.debug("Filtering offline offers spend time: {}", endTime - startTime);
        return bits;
    }
    
    
	@Override
	public String toString(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	

}


