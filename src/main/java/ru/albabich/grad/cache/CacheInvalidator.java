package ru.albabich.grad.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheInvalidator {

    @Scheduled(cron ="@midnight")
    @CacheEvict(allEntries = true, value = "restaurantsAndMenus")
    public void clearCache() {
    }
}
