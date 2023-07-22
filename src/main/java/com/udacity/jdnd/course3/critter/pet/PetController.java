package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.customer.Customer;
import com.udacity.jdnd.course3.critter.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        pet = petService.savePet(pet);
        return convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findPetById(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return convertListPetToListPetDTO(petService.findAllPet());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.findCustomerById(ownerId);
        List<Pet> pets = customer.getPets();
        List<PetDTO> PetDTOs = new ArrayList<>();
        for (Pet pet: pets) {
            PetDTOs.add(convertPetToPetDTO(pet));
        }
        return PetDTOs;
    }

    public PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    public List<PetDTO> convertListPetToListPetDTO(List<Pet> pets) {
        List<PetDTO> result = new ArrayList<>();
        pets.forEach(pet -> result.add(convertPetToPetDTO(pet)));
        return result;
    }

    public Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setOwner(customerService.findCustomerById(petDTO.getOwnerId()));
        return pet;
    }
}

