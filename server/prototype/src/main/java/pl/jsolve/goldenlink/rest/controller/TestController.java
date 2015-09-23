package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Author;
import pl.jsolve.goldenlink.rest.dto.Category;
import pl.jsolve.goldenlink.rest.dto.Link;
import pl.jsolve.goldenlink.rest.dto.Tag;
import pl.jsolve.goldenlink.rest.service.AuthorService;
import pl.jsolve.goldenlink.rest.service.CategoryService;
import pl.jsolve.goldenlink.rest.service.DashboardTilesService;
import pl.jsolve.goldenlink.rest.service.LinkService;

import com.google.common.collect.Lists;

@RestController
public class TestController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private LinkService linkService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private DashboardTilesService dashboardTilesService;

	static List<String> icons = Lists.newArrayList("fa-asterisk", "fa-bomb", "fa-bell", "fa-book", "fa-bus", "fa-bed", "fa-ban", "fa-check", "fa-dashboard", "fa-diamond", "fa-edit");

	@PostConstruct
	public void setup() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		generateMainCategories();
		generateCategories();
		generateLinks();

		authorService.addAuthor(LUKASZ);
		authorService.addAuthor(TOMEK);
	}

	private Category LINKS = new Category(generateId(), "LINKS", false, null, "LINKS", randomIcon());

	private Category PHOTOS = new Category(generateId(), "PHOTOS", false, null, "PHOTOS", randomIcon());

	private Category VIDEOS = new Category(generateId(), "VIDEOS", false, null, "VIDEOS", randomIcon());

	private Category YOUTUBE = new Category(generateId(), "YOUTUBE", false, null, "VIDEOS", randomIcon());

	private Author LUKASZ = new Author(generateId(), "Lukasz Stypka");

	private Author TOMEK = new Author(generateId(), "Tomek Kurylek");

	private void generateMainCategories() {
		categoryService.createMainCategory(LINKS);
		categoryService.createMainCategory(PHOTOS);
		categoryService.createMainCategory(VIDEOS);
		categoryService.createMainCategory(YOUTUBE);
	}

	private void generateCategories() {
		// LINKS
		Category praca = categoryService.createSubcategory(LINKS.getPublicId(), new Category(generateId(), "Praca", false, null, null, randomIcon()));
		Category futureProcessing = categoryService.createSubcategory(praca.getPublicId(), new Category(generateId(), "Future Processing", false, null, null, randomIcon()));
		Category uczelnia = categoryService.createSubcategory(praca.getPublicId(), new Category(generateId(), "Politechnika Śląska", false, null, null, randomIcon()));
		Category informatyka = categoryService.createSubcategory(LINKS.getPublicId(), new Category(generateId(), "Informatyka", false, null, null, randomIcon()));
		Category programowanie = categoryService.createSubcategory(informatyka.getPublicId(), new Category(generateId(), "Programowanie", false, null, null, randomIcon()));
		Category java = categoryService.createSubcategory(programowanie.getPublicId(), new Category(generateId(), "Java", false, null, null, randomIcon()));
		Category spring = categoryService.createSubcategory(java.getPublicId(), new Category(generateId(), "Spring", false, null, null, randomIcon()));
		Category springFramework = categoryService.createSubcategory(spring.getPublicId(), new Category(generateId(), "Spring Framework", false, null, null, randomIcon()));
		Category springMVC = categoryService.createSubcategory(spring.getPublicId(), new Category(generateId(), "Spring MVC", false, null, null, randomIcon()));

		// PHOTOS
		Category statki = categoryService.createSubcategory(PHOTOS.getPublicId(), new Category(generateId(), "Statki", false, null, null, randomIcon()));

	}

	private void generateLinks() {
		// LINKS
		linkService.addLink(new Link(generateId(), "https://www.future-processing.pl/", "Strona domowa Future Processing", "", categoryService.searchCategories("Future").get(0), Lists.newArrayList(
				new Tag(null, "FutureProcessing"), new Tag(null, "FP")), LocalDateTime.now(), null, TOMEK, false));
		linkService.addLink(new Link(generateId(), "https://spring.io/", "Strona domowa Spring Framework", "", categoryService.searchCategories("Spring Framework").get(0), Lists.newArrayList(new Tag(
				null, "spring")), LocalDateTime.now(), null, LUKASZ, false));

		linkService.addLink(new Link(generateId(), "http://projects.spring.io/spring-boot/", "Strona Spring Boot", "", categoryService.searchCategories("Spring Framework").get(0), Lists.newArrayList(
				new Tag(null, "spring"), new Tag(null, "springBoot")), LocalDateTime.now(), null, LUKASZ, false));

		// PHOTOS
		linkService.addLink(new Link(generateId(), "http://t0.gstatic.com/images?q=tbn:ANd9GcTeB6hfcZ4PZ3fQBGAr8v3-WoMMu8xivw3tJwmNymVUcywNIYAa", "Statek", "", categoryService.searchCategories(
				"Statki").get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://www.royalcaribbeancruises.pl/wp-content/themes/rccl/images/statki-freedom.jpg", "Statek", "", categoryService.searchCategories("Statki")
				.get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "https://upload.wikimedia.org/wikipedia/commons/9/9a/DANA_2004_ubt.jpeg", "Statek", "", categoryService.searchCategories("Statki").get(0), Lists
				.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://www.tapetus.pl/obrazki/n/140143_dwa-statki-pasazerskie.jpg", "Statek", "", categoryService.searchCategories("Statki").get(0), Lists
				.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://www.tapetus.pl/obrazki/n/165618_statek-zachod-slonca-morze.jpg", "Statek", "", categoryService.searchCategories("Statki").get(0), Lists
				.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://www.imperiumtapet.com/media/wallpaper/28760/image/536641a4cff746482b54_1600x1200_cropromiar-niestandardowy.jpg", "Statek", "",
				categoryService.searchCategories("Statki").get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://fri.info.pl/media/img/statki-jachty-sztormy/3.jpg", "Statek", "", categoryService.searchCategories("Statki").get(0), Lists
				.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService
				.addLink(new Link(
						generateId(),
						"https://upload.wikimedia.org/wikipedia/commons/8/87/US_Navy_050608-N-9500T-009_A_barge_flying_the_American_Flag_sails_by_the_Military_Sealift_Command_(MSC)_hospital_ship_USNS_Mercy_(T-AH_19)_prepares_to_moor.jpg",
						"Statek", "", categoryService.searchCategories("Statki").get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://www.tapeciarnia.pl/tapety/normalne/72397_statek_piracki_zachod_slonca.jpg", "Statek", "", categoryService.searchCategories("Statki").get(0),
				Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://t1.gstatic.com/images?q=tbn:ANd9GcTV9_xsL2bv1wrB7jHJDU3baJVygxrcCaLyHT15csmIqcBuo8R9YQ", "Statek", "", categoryService.searchCategories(
				"Statki").get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://bezprzyciskow.pl/wp-content/uploads/2011/10/ships.jpg", "Statek", "", categoryService.searchCategories("Statki").get(0), Lists
				.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://veanka1.blox.pl/resource/stat_3050.jpg", "Statek", "", categoryService.searchCategories("Statki").get(0), Lists.newArrayList(new Tag(null,
				"statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://www.otofotki.pl/img14/obrazki/jo644_statki.jpg", "Statek", "", categoryService.searchCategories("Statki").get(0), Lists
				.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://www.royalcaribbeancruises.pl/wp-content/themes/rccl/images/statki-oasis.jpg", "Statek", "", categoryService.searchCategories("Statki")
				.get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://t0.gstatic.com/images?q=tbn:ANd9GcTUlgvSK6u137rwOCBWxooDV8NfeVV-5r9DCXmAti68FtR9GSuG", "Statek", "", categoryService.searchCategories(
				"Statki").get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "http://ocdn.eu/images/pulscms/Y2I7MDQsMCwxMixkODgsNzljOzA2LDMyMCwxYzI_/26519bdc36546ec99a83a1735ce8b899.jpg", "Statek", "", categoryService
				.searchCategories("Statki").get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));
		linkService.addLink(new Link(generateId(), "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR4GC0GZPJjh3gD9TEGilwQMlIL-41Bl-Nm5UB8HBGnbElx3KtF", "Statek", "", categoryService
				.searchCategories("Statki").get(0), Lists.newArrayList(new Tag(null, "statki")), LocalDateTime.now(), null, LUKASZ, false));

	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

	private static int random(int max) {
		return (int) (Math.random() * max);
	}

	private static String randomIcon() {
		return icons.get(random(icons.size() - 1));
	}

}
