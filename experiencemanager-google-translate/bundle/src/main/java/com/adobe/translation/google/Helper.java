/*
 *   Copyright 2013 Adobe
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */


package com.adobe.translation.google;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.collection.ResourceCollection;

public class Helper {

    /**
     * Default log.
     */
    protected final Logger log = LoggerFactory.getLogger(Translator.class);

    /**
     * helper
     */
    public List<String> getPaths(String path, ResourceCollection rcCollection) {
        List<String> paths = new ArrayList<String>();
        // If there is no collection or the path is under the content branch, assume this is
        // not a workflow package
        if (rcCollection == null || path.startsWith("/content")) {
            paths.add(path);
        } else {
            log.debug("ResourceCollection detected " + rcCollection.getPath());
            // this is a resource collection. the collection itself is not
            // replicated. only its members
            try {
                List<Node> members = rcCollection.list(new String[]{"cq:Page", "dam:Asset"});
                for (Node member : members) {
                    String mPath = member.getPath();
                    paths.add(mPath);
                }
            } catch (RepositoryException re) {
                log.error("Cannot build path list out of the resource collection " + rcCollection.getPath());
            }
        }
        return paths;
    }
}
