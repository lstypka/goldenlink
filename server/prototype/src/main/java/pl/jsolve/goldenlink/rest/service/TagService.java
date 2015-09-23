package pl.jsolve.goldenlink.rest.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import pl.jsolve.goldenlink.rest.dto.Tag;
import pl.jsolve.sweetener.collection.Maps;

@Service
public class TagService {

	private Map<String, Tag> tags = Maps.newHashMap();

	public List<Tag> getTags() {
		return Lists.newArrayList(tags.values());
	}

	public Tag addTag(Tag tag) {
		if (tags.containsKey(tag.getLabel())) {
			return tags.get(tag.getLabel());
		}

		Tag newTag = new Tag(generateId(), tag.getLabel());
		tags.put(tag.getLabel(), newTag);
		return newTag;
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

}
