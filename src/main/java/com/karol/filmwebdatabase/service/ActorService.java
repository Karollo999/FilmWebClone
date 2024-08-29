package com.karol.filmwebdatabase.service;

import com.karol.filmwebdatabase.model.Actor;
import com.karol.filmwebdatabase.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Optional<Actor> getActorById(Long id) {
        return actorRepository.findById(id);
    }


	public Actor saveActor(Actor actor) {
		return actorRepository.save(actor);
	}

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }

   public Actor updateActor(Long id, Actor actorDetails) {
		Actor actor = actorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Actor not found"));

		actor.setFirstName(actorDetails.getFirstName());
		actor.setLastName(actorDetails.getLastName());
		actor.setBirthDate(actorDetails.getBirthDate());
		actor.setNationality(actorDetails.getNationality());
		actor.setBiography(actorDetails.getBiography());

		return actorRepository.save(actor);
	}
}
