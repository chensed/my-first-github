package org.elasticsearch.plugin;

import org.elasticsearch.Module.DemoFilterModule;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.inject.multibindings.Multibinder;
import org.elasticsearch.indices.IndicesModule;
import org.elasticsearch.plugin.parser.FilterDemoParser;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.action.cat.AbstractCatAction;

import java.util.Collection;
import java.util.Collections;



public class FilterDemoPlugin extends Plugin {

    @Override public String name() {
    	return "filter_demo";
        
        
    }


    @Override public String description() {
        return "filter demo";
    }

    @Override
    public Collection<Module> nodeModules() {

    	return Collections.<Module>singletonList(new DemoFilterModule());
    	

    }
    
    
    public void onModule(IndicesModule module) {
        module.registerQueryParser(FilterDemoParser.class);
    }

    

}
