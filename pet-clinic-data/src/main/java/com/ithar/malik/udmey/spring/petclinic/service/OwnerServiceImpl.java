package com.ithar.malik.udmey.spring.petclinic.service;

import com.ithar.malik.udmey.spring.petclinic.model.Owner;
import com.ithar.malik.udmey.spring.petclinic.respository.OwnerRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository<Owner, Long> repository;

    public OwnerServiceImpl(OwnerRepository<Owner, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Owner findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find owner with id:"+id));
    }

    @Override
    @Transactional
    public Set<Owner> findAll() {

        Set<Owner> owners = new HashSet<>();

        repository.findAll().forEach(owners::add);

        if (!owners.isEmpty()) {
            return owners;
        }

        return new HashSet<>();
    }

    @Override
    public Owner save(Owner owner) {

        if (owner != null) {
            return repository.save(owner);
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Owner owner) {
        repository.delete(owner);
    }

    @Override
    public Set<Owner> findByLastName(String lastName) {

        Set<Owner> owners = repository.findByLastName(lastName);

        if (!owners.isEmpty()) {
            return owners;
        }

        return new HashSet<>();
    }
}
