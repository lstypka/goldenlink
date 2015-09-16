package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Author;
import pl.jsolve.goldenlink.rest.dto.Category;
import pl.jsolve.goldenlink.rest.dto.Link;
import pl.jsolve.goldenlink.rest.dto.Links;
import pl.jsolve.goldenlink.rest.dto.Tag;
import pl.jsolve.goldenlink.rest.service.CategoryService;
import pl.jsolve.sweetener.text.Strings;

import com.google.common.collect.Lists;

@RestController
public class LinkController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/categories/{categoryId}/links", method = RequestMethod.GET)
	public Links getLinks(@PathVariable("categoryId") String categoryId,
			@RequestParam("page") Integer page,
			@RequestParam("resultsPerPage") Integer resultsPerPage,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "comment", required = false) String comment,
			@RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "date", required = false) String date) {

		System.out.println("SEARCH -> title: " + title + " comment: " + comment
				+ " author: " + author + " tag: " + tag + " date: " + date);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new Links(generateLinks(categoryId, page, resultsPerPage), page,
				resultsPerPage, 53);
	}

	List<String> photos = Lists
			.newArrayList(
					"http://img3.themebin.com/1920x1080/pier_1080.jpg",
					"http://4.bp.blogspot.com/-ckNQzDUbFzM/U4bf9CpGnLI/AAAAAAAANXQ/Le4D3tYr0AM/s1600/moon+hd+wallpaper+1.jpg",
					"http://www.hdwallpapersos.com/wp-content/uploads/2015/02/enjoyable-best-top-wallpaper-for-mobile-download-android-free-pc.jpg",
					"http://th06.deviantart.net/fs71/PRE/i/2011/274/9/3/earth_and_moon_wallpaper_hd_by_loulines-d4bgsel.jpg",
					"http://www.genovic.com/thumbs/2013/02/5/hp-backgrounds-images-hd-wallpapers.jpg",
					"https://lh5.ggpht.com/Wm130XnmZklcFIKLY9M5NgD2-rBh-gM9kMvI6GPa_tdgq-BmU0Q-Rf4VgkLf4wqg6l4=h900");

	List<String> videos = Lists
			.newArrayList(
					"http://www.w3schools.com/html/mov_bbb.mp4",
					"http://download.wavetlan.com/SVV/Media/HTTP/H264/Talkinghead_Media/H264_test1_Talkinghead_mp4_480x360.mp4",
					"http://download.wavetlan.com/SVV/Media/HTTP/MP4/ConvertedFiles/MediaCoder/MediaCoder_test1_1m9s_AVC_VBR_256kbps_640x480_24fps_MPEG2Layer3_CBR_160kbps_Stereo_22050Hz.mp4",
					"http://download.wavetlan.com/SVV/Media/HTTP/MP4/ConvertedFiles/Media-Convert/Media-Convert_test1_36s_AVC_VBR_521kbps_320x240_25fps_AACLCv4_VBR_96kbps_Stereo_44100Hz.mp4",
					"http://download.wavetlan.com/SVV/Media/HTTP/MP4/ConvertedFiles/QuickTime/QuickTime_test1_4m3s_MPEG4SP_CBR_120kbps_480x320_30fps_AAC-LCv4_CBR_32kbps_Stereo_22050Hz.mp4",
					"http://img-9gag-fun.9cache.com/photo/aepOVqW_460sv.mp4");

	List<String> youtube = Lists.newArrayList(
			"https://www.youtube.com/embed/qv3XX-CMeiw",
			"https://www.youtube.com/embed/F7AHvILMcSI",
			"https://www.youtube.com/embed/83VN3DKodNY",
			"https://www.youtube.com/embed/6vR0Ynzrti4",
			"https://www.youtube.com/embed/sDJxU-QoNgU",
			"https://www.youtube.com/embed/QtdEIZdTIsk",
			"https://www.youtube.com/embed/-3THhtq8Ztc");

	private List<Link> generateLinks(String categoryId, Integer page,
			Integer resultsPerPage) {
		List<Link> links = Lists.newArrayList();
		List<Category> mainCategories = categoryService.getMainCategories();

		/*
		 * Category category = categoryService.findCategory(categoryId);
		 */List<Tag> tags = Lists.newArrayList(new Tag(generateId(), "JS"),
				new Tag(generateId(), "Select2"), new Tag(generateId(),
						"AngularJS"), new Tag(generateId(), "Grunt"), new Tag(
						generateId(), "HTML 5"));
		Author author = new Author(generateId(), "Lukasz Stypka");

		for (int i = 0; i < resultsPerPage; i++) {

			String link = "";
			int random = random(4);
			Category category = mainCategories.get(random);
			if (category.getCategoryGroup().equals("links")) {
				link = "https://kevin-brown.com/#/home";
			}
			if (category.getCategoryGroup().equals("photos")) {
				link = photos.get(random(photos.size()));
			}
			if (category.getCategoryGroup().equals("videos")) {
				link = videos.get(random(videos.size()));
			}
			if (category.getCategoryGroup().equals("youtube")) {
				link = youtube.get(random(youtube.size()));
			}

			LocalDateTime expiryDate = LocalDateTime.now();
			if (random(5) < 3) {
				expiryDate = null;
			} else {
				expiryDate = LocalDateTime.now(DateTimeZone.UTC)
						.plusDays(random(365)).plusHours(random(24))
						.plusMinutes(random(60)).plusSeconds(random(60));
			}

			links.add(new Link(generateId(), link,
					"Strona domowa kolesia od select2 Link nr "
							+ ((page * resultsPerPage) + i + 1), getComment(),
					category, tags, LocalDateTime.now(DateTimeZone.UTC),
					expiryDate, author, false));
		}
		return links;
	}

	private String getComment() {
		if (random(5) < 3) {
			return Strings.random(random(10000));
		}
		return "";
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

	private static int random(int max) {
		return (int) (Math.random() * max);
	}
}
