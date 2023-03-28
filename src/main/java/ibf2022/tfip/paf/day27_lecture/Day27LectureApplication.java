package ibf2022.tfip.paf.day27_lecture;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf2022.tfip.paf.day27_lecture.repository.TaskRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;


@SpringBootApplication
public class Day27LectureApplication implements CommandLineRunner{

	@Autowired
	private TaskRepository taskRepository;
	public static void main(String[] args){
		SpringApplication.run(Day27LectureApplication.class, args);
	}

	@Override
	public void run(String ...args){
		JsonObject task = Json.createObjectBuilder()
				.add("task", "Watch JVM 4")
				.add("priority", 5)
				.add("dueDate", "2023-02-04")
				.build();

		taskRepository.updateActivity();

		// taskRepository.deleteActivitiesWithoutTask();
		// Document result = taskRepository.insertTask(task);
		//System.out.printf(">>> inserted result: ", result);
	}
}
