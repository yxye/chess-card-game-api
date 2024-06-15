package com.chess.card.api.event;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.LiteBlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@AllArgsConstructor
@Service
public class GameRoomEventProcessor {
    private final ConcurrentHashMap<String, Disruptor<RoomEvent>> roomDisruptors;
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<UserEventHandler>> roomSubscribers;
    private final ExecutorService executor;

    static final AtomicInteger THREAD_INIT_NUMBER = new AtomicInteger(1);
    private final int bufferSize;
    private String getName(Runnable r) {

        String domainEventHandlerName = "";

        if (r instanceof BatchEventProcessor) {
            try {
                Field eventHandler = BatchEventProcessor.class.getDeclaredField("eventHandler");
                eventHandler.setAccessible(true);
                Object o = eventHandler.get(r);

                if (o instanceof UserEventHandler) {
                    domainEventHandlerName = ((UserEventHandler)o).getName();
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }

            return domainEventHandlerName;
        }

        return domainEventHandlerName;
    }
    private ThreadFactory createThreadFactory(String  roomId) {
        return r -> {
            String domainEventHandlerName = getName(r);

            List<String> nameParamList = new ArrayList<>();
            // 线程名前缀
            nameParamList.add("chess");
            // 主题名
            nameParamList.add(roomId);
            // 领域事件名
            nameParamList.add(domainEventHandlerName);
            // 编号
            nameParamList.add(String.valueOf(THREAD_INIT_NUMBER.getAndIncrement()));

            // 组合成线程名
            String threadName = String.join("-", nameParamList);

            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName(threadName);

            return thread;
        };
    }


    public void startRoom(String roomId) {
        RoomEventFactory factory = new RoomEventFactory();
        // 自定义线程工厂
        ThreadFactory threadFactory = createThreadFactory(roomId);
        WaitStrategy waitStrategy = new LiteBlockingWaitStrategy();
        ProducerType producerType = ProducerType.MULTI;

        Disruptor<RoomEvent> disruptor =new Disruptor<>(RoomEvent::new, bufferSize, threadFactory, producerType, waitStrategy);
        disruptor.start();
        roomDisruptors.put(roomId, disruptor);
        roomSubscribers.put(roomId, new CopyOnWriteArrayList<>());
    }

    public void stopRoom(String roomId) {
        Disruptor<RoomEvent> disruptor = roomDisruptors.remove(roomId);
        if (disruptor != null) {
            disruptor.shutdown();
        }
        roomSubscribers.remove(roomId);
    }

    public void subscribe(String roomId, String userId) {
        UserEventHandler handler = new UserEventHandler(userId);
        roomSubscribers.get(roomId).add(handler);
        roomDisruptors.get(roomId).handleEventsWith(handler);
    }

    public RoomEventProducer getProducer(String roomId) {
        Disruptor<RoomEvent> disruptor = roomDisruptors.get(roomId);
        if (disruptor == null) {
            throw new IllegalStateException("Room " + roomId + " is not started");
        }
        return new RoomEventProducer(disruptor.getRingBuffer());
    }
}

