package org.wecancodeit.reviewsite;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {
	
	@Resource
	TagRepository tagRepo;
	
	@Resource
	ReviewRepository reviewRepo;

	@RequestMapping("/")
	public String findAllReviews(Model model) {
		model.addAttribute("reviews", reviewRepo.findAll());
		return ("reviews");
	}
	
	@PostMapping("/addTag")
	public String addTag(@RequestParam(required = true) String tagName) {
		tagRepo.save(new Tag(tagName));
		return "redirect:/";
	}
	
	@GetMapping("/deleteTag")
	public String removeTag(@RequestParam(required = true) Long tagId) {
		tagRepo.deleteById(tagId);
		return "redirect:/";
	}
	
	@RequestMapping("/review")
	public String findOneReview(@RequestParam(value="id")long id, Model model) throws ReviewNotFoundException {
		Optional<Review> review = reviewRepo.findById(id);
		
		if(review.isPresent()) {
			model.addAttribute("reviews", review.get());
			model.addAttribute("categories", tagRepo.findByReviewsContains(review.get()));
			return "review";
		}
		throw new ReviewNotFoundException();
	}
	
	@RequestMapping("/category")
	public String findOneCategory(@RequestParam(value="id") long id, Model model) throws TagNotFoundException {
		Optional<Tag> category = tagRepo.findById(id);
		
		if(category.isPresent()) {
			model.addAttribute("categories", category.get());
			return "category";
		}
		throw new TagNotFoundException();
	}

	@RequestMapping("/showCategories")
	public String findAllCategories(Model model) {
		model.addAttribute("categories", tagRepo.findAll());
		return "categories";
	}

}
