package com.yanchware.userservice.service;

import com.yanchware.userservice.entity.Module;
import com.yanchware.userservice.entity.Subscription;
import com.yanchware.userservice.model.SubscriptionModelRequest;
import com.yanchware.userservice.model.SubscriptionResponse;
import com.yanchware.userservice.repository.ModuleRepository;
import com.yanchware.userservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ModuleRepository moduleRepository;

    @Transactional
    public SubscriptionResponse createSubscription (SubscriptionModelRequest subscriptionModelRequest) throws Exception {
        try {
            var subscriptionResponse = new SubscriptionResponse();
            var subscription = new Subscription();
            subscription.setTimestamp(new Timestamp(System.currentTimeMillis()));
            subscription.setUserId(subscriptionModelRequest.getUserId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            subscription.setExpireDate(new Timestamp(simpleDateFormat.parse(subscriptionModelRequest.getExpireDate()).getTime()));
            subscription = subscriptionRepository.save(subscription);
            final long subscriptionId = subscription.getId();
            subscriptionResponse.setId(subscriptionId);
            subscriptionResponse.setUserId(subscription.getUserId());
            subscriptionResponse.setExpireDate(simpleDateFormat.format(new Date(subscription.getExpireDate().getTime())));
            subscriptionResponse.setCreationDate(simpleDateFormat.format(new Date(subscription.getTimestamp().getTime())));
            final List<String> modules = new ArrayList<String>();
            subscriptionModelRequest.getModules().forEach(moduleType -> {
                var module = new Module();
                module.setSubscriptionId(subscriptionId);
                module.setType(moduleType);
                module = moduleRepository.save(module);
                modules.add(module.getType());
            });
            subscriptionResponse.setModules(modules);
            return subscriptionResponse;
        } catch (Exception e) {
            throw new Exception("error creating subscription");
        }
    }

    public List<SubscriptionResponse> getAllSubscriptions () {
        var subscriptions = new ArrayList<SubscriptionResponse>();
        subscriptionRepository.findAll().forEach(subscription -> {
            var subscriptionResponse = new SubscriptionResponse();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            subscriptionResponse.setId(subscription.getId());
            subscriptionResponse.setUserId(subscription.getUserId());
            subscriptionResponse.setExpireDate(simpleDateFormat.format(new Date(subscription.getExpireDate().getTime())));
            subscriptionResponse.setCreationDate(simpleDateFormat.format(new Date(subscription.getTimestamp().getTime())));
            var modules = moduleRepository.findModuleBySubscriptionId(subscription.getId());
            subscriptionResponse.setModules(modules.stream().map(Module::getType).collect(Collectors.toList()));
            subscriptions.add(subscriptionResponse);
        });
        return subscriptions;
    }

}
