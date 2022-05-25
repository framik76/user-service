package com.yanchware.userservice.repository;

import com.yanchware.userservice.entity.Module;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends CrudRepository<Module, Long> {
    List<Module> findModuleBySubscriptionId(long subscriptionId);
}
