package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.customer.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class PetService {

    private PetRepository petRepository;

    public Pet savePet(Pet pet){
        Pet savedPet = petRepository.save(pet);
        Customer owner = savedPet.getOwner();
        List<Pet> pets = owner.getPets();
        if (!pets.contains(savedPet)) {
            pets.add(savedPet);
            owner.setPets(pets);
        }
        return savedPet;
    }

    public List<Pet> findAllPet() {
        return petRepository.findAll();
    }

    public Pet findPetById(long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet ID[" + id + "] is not found"));
    }
}
