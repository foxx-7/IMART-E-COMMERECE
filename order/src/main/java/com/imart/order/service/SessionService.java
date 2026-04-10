package com.imart.order.service;

import com.imart.order.dto.local.CheckOutSession;
import com.imart.order.exception.InvalidSessionException;
import com.imart.order.exception.ResourceNotFoundException;
import com.imart.order.exception.SessionTimeOutException;
import com.imart.order.exception.UnprocessableRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {
    private final long  SESSION_DURATION_HOURS = 42;

    @Qualifier("vaultRedisTemplate")
    private RedisTemplate<String, Object> vaultRedisTemplate;
    private final String SESSION_KEY = "checkout:session:";

    //end user session
   public  void endCheckOutSession(Long userId){
        CheckOutSession session = (CheckOutSession) vaultRedisTemplate.opsForValue().get((SESSION_KEY+userId));
        if(session == null || !session.isActive()){
            throw new InvalidSessionException("session not available or inactive");
        }
        session.setActive(false);
        session.setEnded(true);
   }

   //validate user session
   public boolean validateCheckOutSession(Long userId){
       CheckOutSession session = (CheckOutSession) vaultRedisTemplate.opsForValue().get(SESSION_KEY+userId);
       if(session == null){
           return false;
       }
       Duration duration = Duration.between(session.getCreationTimeStamp(), LocalDateTime.now());
       if (duration.toHours() >= SESSION_DURATION_HOURS){
           session.setActive(false);
           vaultRedisTemplate.opsForValue().set((SESSION_KEY+userId), session);
           return false;
       }
       return session.getUserId().equals(userId);
   }

   //create new session for user
   public CheckOutSession createCheckOutSession(Long userId){

       CheckOutSession session = CheckOutSession.builder()
               .userId(userId)
               .sessionId(String.valueOf(UUID.randomUUID()))
               .isActive(true)
               .isEnded(false)
               .creationTimeStamp(LocalDateTime.now())
               .build();

       vaultRedisTemplate.opsForValue().set((SESSION_KEY+userId), session);

       return session;
   }

   //deactivate user session
   public void disableSession(Long userId,String checkOutSessionId){
       CheckOutSession session = (CheckOutSession) vaultRedisTemplate.opsForValue().get(SESSION_KEY+userId);
       if(session == null){
           throw new UnprocessableRequestException("session for user:" + userId + " could not be resolved");
       }
       Duration duration = Duration.between(session.getCreationTimeStamp(), LocalDateTime.now());
       if (duration.toHours() >= SESSION_DURATION_HOURS){
           throw new SessionTimeOutException("session: " + session.getSessionId() +  " for user:" + userId + " ended");
       }
       if(session.getSessionId().equals(checkOutSessionId) && session.getUserId().equals(userId)){
           throw new UnprocessableRequestException("identity mismatch for user:"+userId+ " with session:" +checkOutSessionId);
       }
      session.setActive(false);
       session.setEnded(false);

       vaultRedisTemplate.opsForValue().set((SESSION_KEY+userId), session);
   }

   public boolean hasActiveSession(Long userId){
       CheckOutSession session = (CheckOutSession) vaultRedisTemplate.opsForValue().get(SESSION_KEY+userId);
       return !(session == null) && session.isActive() && !session.isEnded();
   }
}
