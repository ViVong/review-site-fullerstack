package org.wecancodeit.reviewsite;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewItemRestController {

	@Resource
	ReviewRepository reviewRepo;

	@GetMapping("/api/tasks")
	public Iterable<Review> getAllTasks() {
		return reviewRepo.findAll();
	}

	@PostMapping("/api/tasks")
	public Review createTask(@RequestBody Review newReview) {
		return reviewRepo.save(newReview);
	}

	@GetMapping("/api/tasks/{id}")
	public Review viewTask(@PathVariable Long id) {
		return reviewRepo.findById(id).get();
	}

	@DeleteMapping("/api/tasks/{id}")
	public boolean deleteTask(@PathVariable Long id) {
		if (reviewRepo.findById(id).isPresent()) {
			reviewRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@PutMapping("/api/tasks")
	public Review updateTask(@RequestBody Review taskItemUpdate) {
		return reviewRepo.save(taskItemUpdate);
	}
}
