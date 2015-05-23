package com.tim.one.collaborator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author josdem
 * @understands A class who knows how to split "one, two" format string in an arrayList<String>
 *
 */

@Component
public class TagSplitter {
	
	public List<String> split(String tags) {
		List<String> tagList = new ArrayList<String>();
		if (!StringUtils.isEmpty(tags)){
			StringTokenizer stringTokenizer = new StringTokenizer(tags, ",");
			while (stringTokenizer.hasMoreElements()){
				tagList.add(stringTokenizer.nextToken().trim());
			}
		}
		return tagList;
	}

}
